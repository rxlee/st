<!-- 河长的主界面 -->
<template>
  <div>
    <el-row>
      <el-col v-if="menu === 0">
        <el-card
          v-for="r in records"
          :key="r.id"
          style="margin-bottom: 20px"
          @click.native="openDetail(r)"
        >
          <div slot="header" class="clearfix">
            {{ r.id }}采样
            <el-tag type="warning" size="mini" style="float: right"
              >排污采样</el-tag
            >
          </div>
          <p>{{ r.stime | fmt }}采样</p>
        </el-card>

        <!-- 巡查 -->
        <el-card
          v-for="i in inspects"
          :key="i.id"
          style="margin-bottom: 20px"
          @click.native="openInspectDetail(i)"
        >
          <div slot="header" class="clearfix">
            {{ i.id }}巡查
            <el-tag
              v-if="i.step === 50"
              type="warning"
              size="mini"
              style="float: right"
              >追踪中</el-tag
            >
            <el-tag
              v-if="i.step === 0"
              type="warning"
              size="mini"
              style="float: right"
              >待审批</el-tag
            >
          </div>
          <p>{{ i.umemo }}</p>
        </el-card>
        <!-- 河长的待办，包含多种内容。
        <ol>
          <li>自动采样但还没有送检的列表，等待下发任务；</li>
          <li>巡查员问题上报，可以选择忽略或要求巡查员做后续处理，若有后续处理，需要跟踪到巡查员给出反馈；</li>
          <li>送检后生成检测报告，上级管理部门发现问题，把报告转发过来，要求处理。</li>
        </ol> -->
      </el-col>
      <el-col v-if="menu === 1">
        任务或问题跟踪：
        <ol>
          <li>
            送检的，从下发到检测完成，检测完成就不再出现在跟踪列表，但如果上级根据检测结果做后续处理，那就出现在待办，然后安排下去以后又出现在这里，需要跟踪；
          </li>
          <li>巡查的，若需要巡查员跟踪处理，则一直跟踪到巡查员完成反馈；</li>
          <li>
            根据检测报告（仅限上级要求的，不是每一个检测报告都跟踪）去线下通知执法机关执法，执法过程不需记录，最后执法结果简要记录到系统中，该跟踪完成
          </li>
        </ol>
      </el-col>
    </el-row>
    <el-dialog title="样品送检" :visible.sync="dialogVisible" width="85%">
      <el-form label-width="70px">
        <el-form-item label="辖区">
          <el-input v-model="record.rname" readonly
        /></el-form-item>
        <el-form-item label="水站">
          <el-input v-model="record.sname" readonly
        /></el-form-item>
        <el-form-item label="样品编号">
          <el-input v-model="record.bottle" readonly
        /></el-form-item>
        <el-form-item label="送样人">
          <el-select v-model="newone.delivery" placeholder="请选择送样人">
            <el-option
              v-for="d in deliveries"
              :key="d.uid"
              :label="d.uname + ' / ' + d.uphone"
              :value="d.uid"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="检测机构">
          <el-input v-model="newone.corp" readonly />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="Submit"> 派发任务 </el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-dialog
      title="巡查处理"
      :visible.sync="dialogInspectVisible"
      width="60%"
    >
      <p>{{ inspect.utime | fmt }}上报</p>
      <p>{{ inspect.umemo }}</p>
      <el-button type="primary" @click="inspectSubmit(100)"> 忽略</el-button>
      <el-button type="primary" @click="inspectSubmit(50)"> 追踪处理</el-button>
    </el-dialog>
  </div>
</template>

<script>
import moment from 'moment'
import { mapGetters } from 'vuex'
import { dispatchTask, queryRegionByStaff, listUndetectedSampleRecorsInRegion, listDeliveriesInRegion } from '@/api/detect-process.js'
import { getInspectByRgionId, approveInspect } from '@/api/inspect.js'
export default {
  name: 'RiverChief',
  components: {},
  filters: {
    fmt: d => moment(d).format('YYYY-MM-DD HH:mm')
  },
  data() {
    return {
      menu: 0,
      records: [],
      dialogVisible: false,
      record: {},
      deliveries: [],
      newone: {
        delivery: null,
        corp: null
      },
      regionId: 0,
      inspects: [],
      dialogInspectVisible: false,
      inspect: {},
      amemo: null
    }
  },
  computed: {
    ...mapGetters([
      'uid'
    ])
  },
  created: function() {
    // 初始化菜单
    this.$parent.initNav({
      menus: ['河长待办', '河长在办'],
      changed: this.navChanged
    })
    // 查找用户辖区id
    queryRegionByStaff(this.uid).then(response => {
      this.regionId = response.data.id
      //获取送样人
      listDeliveriesInRegion(this.regionId).then(response => {
        this.deliveries = response.data
      })
      // 列出代办任务
      listUndetectedSampleRecorsInRegion(this.regionId).then(resp => {
        this.records = resp.data
      })
      //代办巡查任务
      this.listInspect()
    })


  },
  watch: {
    'newone.delivery': function(val) {
      if (val == null) {
        this.newone.corp = ''
      } else {
        for (let i = 0; i < this.deliveries.length; i++) {
          const d = this.deliveries[i]
          if (d.uid === val) {
            this.newone.corp = d.cname
            return
          }
        }
      }
    }
  },
  methods: {
    listInspect: function() {
      //获取巡查列表
      getInspectByRgionId(this.regionId).then(response => {
        this.inspects = []
        for (var i = 0; i < response.data.length; i++) {
          if (response.data[i].step !== 100) {
            this.inspects.push(response.data[i])
          }
        }
      })
    },
    inspectSubmit: function(num) {
      if (num === 100) this.amemo = "忽略"
      if (num === 50) this.amemo = "追踪处理"
      console.log(this.inspect.id);
      approveInspect(this.inspect.id, { aid: this.uid, amemo: this.amemo }).then(resp => {
        this.listInspect()
        this.dialogInspectVisible = false

      })
    },
    openDetail(task) {
      this.record = task
      this.dialogVisible = true
    },
    openInspectDetail: function(data) {
      this.inspect = data
      this.dialogInspectVisible = true
    },
    navChanged: function(index) {
      this.menu = index
    },
    Submit() {
      dispatchTask({
        record: this.record.id,
        delivery: this.newone.delivery,
        dispatcher: this.uid
      }).then(response => {
        if (response.code = 20000) {
          this.dialogVisible = false
          this.list()
        }
      })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>

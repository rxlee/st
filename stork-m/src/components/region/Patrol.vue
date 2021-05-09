<!-- 区域巡查员的主界面 -->
<template>
  <div>
    <el-row>
      <el-col v-if="menu === 0">
        <el-card
          v-for="i in inspects"
          :key="i.id"
          style="margin-bottom: 20px"
          @click.native="openInspectDetail(i)"
        >
          <div slot="header" class="clearfix">{{ i.id }}巡查</div>
          <p>{{ i.umemo }}</p>
        </el-card>
        <!-- 巡查员的待办，主要是指之前上报的问题，河长要求跟踪的，其他内容暂不定。 -->
      </el-col>
      <el-col v-if="menu === 1">
        打卡。地图显示位置。后台保存打卡位置。
      </el-col>
      <el-col v-if="menu === 2">
        <el-form>
          <el-form-item label="情况说明">
            <el-input v-model="umemo" type="textarea" rows="4"> </el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="Submit"> 提交 </el-button>
          </el-form-item>
        </el-form>

        <!-- 问题上报。问题上报主要包括两类，一类是发现了疑似排污，另一类是巡检设备发现设备异常。 -->
      </el-col>
    </el-row>
    <el-dialog title="巡查情况" :visible.sync="dialogVisible" width="80%">
      <el-form>
        <p>{{ inspect.utime | fmt }}上报</p>
        <p>{{ inspect.umemo }}</p>
        <el-form-item label="处理结果">
          <el-input v-model="result" type="textarea" rows="4"> </el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="inspectSubmit"> 确认提交</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import moment from 'moment'
import { mapGetters } from 'vuex'
import { addInspect } from '@/api/inspect.js'
import { queryRegionByStaff } from '@/api/detect-process.js'
import { getInspectByRgionId, inspectResult } from '@/api/inspect.js'
export default {
  name: 'RegionPatrol',
  components: {},
  filters: {
    fmt: d => moment(d).format('YYYY-MM-DD HH:mm')
  },
  data() {
    return {
      menu: 0,
      tasks: [],
      umemo: null,
      inspects: [],
      dialogVisible: false,
      result: null,
      inspect: {}
    }
  },
  computed: {
    ...mapGetters([
      'uid'
    ])
  },
  created: function() {
    queryRegionByStaff(this.uid).then(response => {
      this.regionId = response.data.id
      getInspectByRgionId(response.data.id).then(response => {
        for (var i = 0; i < response.data.length; i++) {
          if (response.data[i].step === 50) {
            this.inspects.push(response.data[i])
          }
        }
      })
    })
    // 初始化菜单
    this.$parent.initNav({
      menus: ['巡查待办', '巡查打卡', '问题上报'],
      changed: this.navChanged
    })
  },
  methods: {
    openInspectDetail: function(i) {
      this.inspect = i
      this.dialogVisible = true
    },
    // 
    navChanged: function(index) {
      this.menu = index
    },
    Submit() {
      addInspect({ rid: this.regionId, uid: this.uid, umemo: this.umemo }).then(resp => {
      })
    },
    inspectSubmit: function() {
      inspectResult(this.inspect.id, { result: this.result }).then(resp => {
        this.dialogVisible = false
        queryRegionByStaff(this.uid).then(response => {
          this.regionId = response.data.id
          getInspectByRgionId(response.data.id).then(response => {
            this.inspects = []
            for (var i = 0; i < response.data.length; i++) {
              if (response.data[i].step === 50) {
                this.inspects.push(response.data[i])
              }
            }
          })
        })
      })

    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>

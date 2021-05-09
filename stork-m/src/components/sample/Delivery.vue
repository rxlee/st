<!-- 检测机构送样人员的主界面 -->
<template>
  <div>
    <el-row>
      <el-col v-if="menu === 0">
        <el-card
          v-for="t in tasks1"
          :key="t.id"
          style="margin-bottom: 20px"
          @click.native="openDetail(t)"
        >
          <div slot="header" class="clearfix">
            {{ t.code }}
            <el-tag type="warning" size="mini" style="float: right"
              >待取样</el-tag
            >
          </div>
          <p>水站 / 样品： {{ t.sampleSname }} / #{{ t.sampleBottle }}瓶</p>
          <p>任务派发时间： {{ t.dispatchTime | fmt }}</p>
        </el-card>
      </el-col>
      <el-col v-if="menu === 1">
        <el-card
          v-for="t in tasks2"
          :key="t.id"
          style="margin-bottom: 20px"
          @click.native="openDetail2(t)"
        >
          <div slot="header" class="clearfix">
            {{ t.code }}
            <el-tag type="warning" size="mini" style="float: right"
              >待送样</el-tag
            >
          </div>
          <p>水站 / 样品： {{ t.sampleSname }} / #{{ t.sampleBottle }}瓶</p>
          <p>任务派发时间： {{ t.dispatchTime | fmt }}</p>
        </el-card>
      </el-col>
    </el-row>
    <el-dialog title="任务信息" :visible.sync="dialogVisible" width="80%">
      <p>{{ task.dispatchTime | fmt }} 派发</p>
      <p>{{ task.sampleRname }}辖区</p>
      <p>{{ task.sampleSname }}水站</p>
      <p>{{ task.sampleBottle }}号样品</p>
      <p>距离限定取样时间还有 {{ task.dispatchTime | limit }}分钟</p>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary">开锁</el-button>
        <el-button type="primary" @click="sampleSubmit">取样完成</el-button>
      </span>
    </el-dialog>
    <el-dialog title="任务信息" :visible.sync="dialogVisible2" width="80%">
      <p>{{ task.dispatchTime | fmt }} 派发</p>
      <p>{{ task.sampleRname }}辖区</p>
      <p>{{ task.sampleSname }}水站</p>
      <p>{{ task.sampleBottle }}号样品</p>
      <p>距离限定取样时间还有 {{ task.dispatchTime | limit }}分钟</p>
      <span slot="footer" class="dialog-footer">
        <el-form>
          <el-form-item label="检验人">
            <el-select v-model="test" placeholder="请选择检测人">
              <el-option
                v-for="t in tests"
                :key="t.id"
                :label="t.uname + ' / ' + t.uphone"
                :value="t"
              /> 
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="deliverySubmit"
              >送样完成</el-button
            >
          </el-form-item>
        </el-form>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import moment from 'moment'
import { mapGetters } from 'vuex'
import { listTaskByDelivery, sampleSuccess, deliverySuccess, queryTestByStaffId } from '@/api/detect-process.js'

export default {
  name: 'SampleDelivery',
  components: {},
  filters: {
    fmt: d => moment(d).format('YYYY-MM-DD HH:mm'),
    limit: t => {
      return 52//(t + 120 * 60 * 1000 - new Date().getTime()) / 1000 / 60
    }
  },
  data() {
    return {
      menu: 0,
      tasks: [],
      tasks1: [],
      tasks2: [],
      dialogVisible: false,
      dialogVisible2: false,
      task: {},
      tests: [],
      test: {}
    }
  },
  computed: {
    ...mapGetters([
      'uid',
      'uname',
      'uphone',
      'urole'
    ])
  },
  created: function() {
    this.init()
    this.$parent.initNav({
      menus: ['我的待办', '我的在办'],
      changed: this.navChanged
    })
    queryTestByStaffId(this.uid).then(response => {
      this.tests = response.data
    })
  },
  methods: {
    init() {
      listTaskByDelivery(this.uid).then(resp => {
        this.tasks = resp.data
        this.tasks1 = []
        this.tasks2 = []
        for (var i = 0; i < this.tasks.length; i++) {
          if (this.tasks[i].step === 0)
            this.tasks1.push(this.tasks[i])
          else if (this.tasks[i].step === 10)
            this.tasks2.push(this.tasks[i])
        }
      })
    },
    openDetail(task) {
      this.dialogVisible = !this.dialogVisible
      this.task = task
      // console.log('准备弹窗 task ==== ', task)
    },
    openDetail2(task) {
      this.dialogVisible2 = !this.dialogVisible2
      this.task = task
      // console.log('准备弹窗 task ==== ', task)
    },
    navChanged: function(index) {
      this.menu = index
    },
    sampleSubmit: function() {
      sampleSuccess(this.task.id).then(response => {
        if (response.code == 20000) {
          this.dialogVisible = false
          this.init()
        }
      })
    },
    deliverySubmit: function() {
      deliverySuccess(this.task.id, this.test).then(response => {
        if (response.code == 20000) {
          this.dialogVisible2 = false
          this.init()
        }
      })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>

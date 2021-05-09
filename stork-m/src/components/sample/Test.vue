<!-- 检测机构检测人员的主界面 -->
<template>
  <div>
    <el-row>
      <el-col v-if="menu === 0">
        <el-card
          v-for="t in tasks"
          :key="t.id"
          style="margin-bottom: 20px"
          @click.native="openDetail(t)"
        >
          <div slot="header" class="clearfix">{{ t.id }}收到</div>
          <p>{{ t.deliveryTime | fmt }}开始送达</p>
        </el-card>
        <!-- 待办，送样人员点了“送达”，本人（检测人员）还没有点“确认收到”的任务列表，操作主要是可以点一下“确认收到”，该任务就进入“检测”阶段 -->
      </el-col>
      <el-col v-if="menu === 1">
        <el-card
          v-for="t in tasks2"
          :key="t.id"
          style="margin-bottom: 20px"
          @click.native="openDetail2(t)"
        >
          <div slot="header" class="clearfix">{{ t.id }}检验</div>
          <p>{{ t.deliveryArrivedTime | fmt }}送达</p>
        </el-card>
        <!-- 正在检测还没有完成的任务，可以点击完成。 -->
      </el-col>
    </el-row>

    <el-dialog title="任务信息" :visible.sync="dialogVisible" width="80%">
      <p>{{ task.deliveryArrivedTime | fmt }} 样品送达</p>
      <p>{{ task.sampleRname }}辖区</p>
      <p>{{ task.sampleSname }}水站</p>
      <p>{{ task.sampleBottle }}号样品</p>
      <p>{{ task.chargeCname }}检测机构</p>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="Submit">确认收货</el-button>
      </span>
    </el-dialog>
    <el-dialog title="任务信息" :visible.sync="dialogVisible2" width="80%">
      <p>{{ task.deliveryArrivedTime | fmt }} 样品送达</p>
      <p>{{ task.sampleRname }}辖区</p>
      <p>{{ task.sampleSname }}水站</p>
      <p>{{ task.sampleBottle }}号样品</p>
      <p>{{ task.chargeCname }}检测机构</p>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="chargeSubmit">检验成功</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import moment from 'moment'
import { mapGetters } from 'vuex'
import { listTaskByTest, receive, chargeComplete } from '@/api/detect-process.js'

export default {
  name: 'SampleTest',
  components: {},
  filters: {
    fmt: d => moment(d).format('YYYY-MM-DD HH:mm')
  },
  data() {
    return {
      menu: 0,
      tasks: [],
      task: {},
      dialogVisible: false,
      dialogVisible2: false,
      tasks2: []
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
      menus: ['我的待办', '我的在办'],
      changed: this.navChanged
    })
    listTaskByTest(this.uid).then(resp => {
      for (var i = 0; i < resp.data.length; i++) {
        if (resp.data[i].step === 10)
          this.tasks.push(resp.data[i])
      }
    })
    listTaskByTest(this.uid).then(resp => {
      for (var i = 0; i < resp.data.length; i++) {
        if (resp.data[i].step === 20)
          this.tasks2.push(resp.data[i])
      }
    })
  },
  methods: {
    openDetail(task) {
      this.task = task
      this.dialogVisible = true
      // console.log('准备弹窗 task ==== ', task)
    },
    openDetail2(task) {
      this.task = task
      this.dialogVisible2 = true
    },
    chargeSubmit: function() {
      chargeComplete(this.task.id).then(resp => {
        this.dialogVisible2 = false
        listTaskByTest(this.uid).then(response => {
          this.tasks2 = []
          for (var i = 0; i < response.data.length; i++) {
            if (response.data[i].step === 20)
              this.tasks2.push(response.data[i])
          }
        })
      })
    },
    // 
    navChanged: function(index) {
      this.menu = index
    },

    Submit: function() {
      receive(this.task.id).then(resp => {
        this.dialogVisible = false
        listTaskByTest(this.uid).then(resp => {
          this.tasks = []
          for (var i = 0; i < resp.data.length; i++) {
            if (resp.data[i].step === 10)
              this.tasks.push(resp.data[i])
          }
        })
        listTaskByTest(this.uid).then(response => {
          this.tasks2 = []
          for (var i = 0; i < response.data.length; i++) {
            if (response.data[i].step === 20)
              this.tasks2.push(response.data[i])
          }
        })
      })

    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>

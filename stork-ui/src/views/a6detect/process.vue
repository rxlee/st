<template>
  <div class="app-container">
    <div>
      <el-row :gutter="12">
        <el-col :span="10">
          <el-card v-for="t in tasks" :key="t.id" style="margin-bottom: 20px">
            <p>任务编号：{{ t.code }}</p>
            <p>辖区名称：{{ t.sampleRname }}</p>
            <p>水站名称：{{ t.sampleSname }}</p>
            <p>采样时间：{{ t.sampleTime | fmt }}</p>
            <p>样瓶编号：{{ t.sampleBottle }}</p>
            <p>送达时间：{{ t.deliveryArrivedTime | fmt }}</p>
            <el-button @click="open(t)">提交报告</el-button>
          </el-card>
        </el-col>
      </el-row>
      <el-dialog title="检测报告" :visible.sync="dialogVisible" width="40%">
        <el-form>
          <el-form-item label="情况说明">
            <el-input v-model="umemo" type="textarea" rows="4" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="Submit"> 提交 </el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
      <!-- 建设中，功能稍后开放（手机端优先提供此功能） -->
    </div>
  </div>
</template>
<script>
import { mapGetters } from 'vuex'
import { formatDate } from '@/api/common'
import { listTaskByTest, chargeComplete } from '@/api/detect-process'
export default {
  name: 'DetectProcess',
  filters: {
    fmt: function(date) {
      return formatDate(new Date(date), 'yyyy-MM-dd hh:mm')
    }
  },
  data() {
    return {
      task: {},
      tasks: [],
      umemo: null,
      dialogVisible: false
    }
  },
  computed: {
    ...mapGetters([
      'uid'
    ])
  },
  created: function() {
    listTaskByTest(this.uid).then(resp => {
      for (var i = 0; i < resp.data.length; i++) {
        if (resp.data[i].step === 20) {
          this.tasks.push(resp.data[i])
        }
      }
      this.task = this.tasks[0]
    })
  },
  methods: {
    open: function(t) {
      this.dialogVisible = true
      this.task = t
    },
    Submit: function() {
      chargeComplete(this.task.id, { processId: this.task.id, content: this.umemo }).then(resp => {
        this.dialogVisible = false
      })
    }
  }
}
</script>

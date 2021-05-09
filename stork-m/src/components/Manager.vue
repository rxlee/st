<!-- 主管领导的主界面 -->
<template>
  <div>
    <el-row>
      <el-col v-if="menu === 0">
        排污监测。送检任务当中已经完成检测的列表（取最近5天的，5天以上没处理的，系统认为被忽略了），可以点开查看检测报告。
        根据检测报告的内容，可以选择忽略还是要求河长做后续处理。做完这个选择后，该任务不再出现在这个列表。
      </el-col>
      <el-col v-if="menu === 1">
        要求河长做后续执法处理的显示在这里，包括“正在处理的”和“处理完毕的”两类
      </el-col>
      <el-col v-if="menu === 2">
        根据不同辖区，看辖区的人员、水站等
      </el-col>
    </el-row>

  </div>
</template>

<script>
import moment from 'moment'
import { mapGetters } from 'vuex'

export default {
  name: 'Manager',
  components: { },
  filters: {
    fmt: d => moment(d).format('YYYY-MM-DD HH:mm')
  },
  data () {
    return {
      menu: 0,
      tasks: []
    }
  },
  computed: {
    ...mapGetters([
      'uid'
    ])
  },
  created: function () {
    // 初始化菜单
    this.$parent.initNav({
      menus: ['排污检测', '处理跟踪', '组织结构'],
      changed: this.navChanged
    })
  },
  methods: {
    openDetail(task) {
      console.log('准备弹窗 task ==== ', task)
    },
    // 
    navChanged: function(index) {
      this.menu = index
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>

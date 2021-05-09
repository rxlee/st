<!-- 辖区内业务人员的主界面 -->
<template>
  <div>
    <el-row>
      <el-col v-if="menu === 0">
        待办，本辖区河长启动的执法任务，没完成的出现在这里，需要本人去跟踪完成情况，并且可以反馈处理结果。
        结果反馈以后就不再出现在此列表。
      </el-col>
    </el-row>

  </div>
</template>

<script>
import moment from 'moment'
import { mapGetters } from 'vuex'

export default {
  name: 'RegionOfficer',
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
      menus: ['执法跟踪'],
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

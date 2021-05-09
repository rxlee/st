<template>
  <div>
    {{ uname }}
    <el-button type="danger" @click="logout">退出登录</el-button>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'PersonProfile',
  data () {
    return {
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
  methods: {
    logout: function () {
      this.$router.push('/')
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>

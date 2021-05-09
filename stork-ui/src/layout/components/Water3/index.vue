<template>
  <!-- 这个界面是系统登录后显示的界面，整体是个iframe，加载了一个分支导航页面，通过这个页面去往不同的分支 -->
  <!-- 只有在分支导航页面中点击了“管理后台”，才会通过iframe向本组件发送一个postMessage，内容是manage，这时候本组件会通知父组件跳往后台，其他情况下都是iframe中的页面切换 -->
  <iframe
    :src="url"
    frameborder="0"
    scrolling="no"
    height="100%"
    width="100%"
  />
</template>
<script>

export default {
  data() {
    return {}
  },
  computed: {
    url: function() {
      // 测试环境大前端跑在9527端口，需要改为88端口，生产环境不需要改
      return process.env.NODE_ENV === 'development' ? 'http://localhost:88/water3.html' : '/water3.html'
    }
  },
  created: function() {
    window.addEventListener('message', (e) => {
      if (e.data === 'manage') {
        this.$parent.gotoManagement()
      }
    })
  },
  methods: {
  }
}
</script>

<style lang="scss" scoped>
</style>

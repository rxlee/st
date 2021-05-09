<template>
  <div style="height:100%">

    <!-- 新加的界面，一进来可选进入大屏展示还是进入后台管理 -->
    <div v-if="mode === 'guide'" style="height:100%;overflow:hidden">
      <water3 />
    </div>

    <!-- 下面这是框架原来自带的后台管理界面 -->
    <div v-if="mode !== 'guide'">
      <div :class="classObj" class="app-wrapper">
        <div v-if="device==='mobile'&&sidebar.opened" class="drawer-bg" @click="handleClickOutside" />
        <sidebar class="sidebar-container" />
        <div :class="{hasTagsView:needTagsView}" class="main-container">
          <div :class="{'fixed-header':fixedHeader}">
            <navbar />
            <tags-view v-if="needTagsView" />
          </div>
          <app-main />
          <right-panel v-if="showSettings">
            <settings />
          </right-panel>
        </div>
      </div>
    </div>
  </div>

</template>

<script>
import RightPanel from '@/components/RightPanel'
import { AppMain, Navbar, Settings, Sidebar, TagsView, Water3 } from './components'
import ResizeMixin from './mixin/ResizeHandler'
import { mapState } from 'vuex'
import { mapGetters } from 'vuex'

export default {
  name: 'Layout',
  components: {
    AppMain,
    Navbar,
    RightPanel,
    Settings,
    Sidebar,
    TagsView,
    Water3
  },
  mixins: [ResizeMixin],
  data: function() {
    return {
      mode: 'guide'
    }
  },
  computed: {
    ...mapGetters([
      'roles'
    ]),
    ...mapState({
      sidebar: state => state.app.sidebar,
      device: state => state.app.device,
      showSettings: state => state.settings.showSettings,
      needTagsView: state => state.settings.tagsView,
      fixedHeader: state => state.settings.fixedHeader
    }),
    classObj() {
      return {
        hideSidebar: !this.sidebar.opened,
        openSidebar: this.sidebar.opened,
        withoutAnimation: this.sidebar.withoutAnimation,
        mobile: this.device === 'mobile'
      }
    }
  },
  methods: {
    handleClickOutside() {
      this.$store.dispatch('app/closeSideBar', { withoutAnimation: false })
    },
    // 跳转去管理后台，由water3组件调用（用户点击了“管理后台”之后触发）
    gotoManagement() {
      this.mode = 'management'
    }
  }
}
</script>

<style lang="scss" scoped>
  @import "~@/styles/mixin.scss";
  @import "~@/styles/variables.scss";

  .app-wrapper {
    @include clearfix;
    position: relative;
    height: 100%;
    width: 100%;

    &.mobile.openSidebar {
      position: fixed;
      top: 0;
    }
  }

  .drawer-bg {
    background: #000;
    opacity: 0.3;
    width: 100%;
    top: 0;
    height: 100%;
    position: absolute;
    z-index: 999;
  }

  .fixed-header {
    position: fixed;
    top: 0;
    right: 0;
    z-index: 9;
    width: calc(100% - #{$sideBarWidth});
    transition: width 0.28s;
  }

  .hideSidebar .fixed-header {
    width: calc(100% - 54px)
  }

  .mobile .fixed-header {
    width: 100%;
  }
</style>

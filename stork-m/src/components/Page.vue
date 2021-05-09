<template>
  <div id="div-in-page">
    <!-- 顶端bar -->
    <nav class="navbar fixed-top">
      <span class="title"> <img src="../assets/logo.png" /> 三水协同 </span>
    </nav>

    <!-- 底端bar -->
    <nav class="navbar navbar-expand-sm bg-light fixed-bottom">
      <ul class="navbar-nav">
        <li
          v-for="(m, i) in menus"
          :key="i"
          :class="menu === i ? 'nav-item active' : 'nav-item'"
          @click="menu = i"
        >
          {{ m }}
        </li>
      </ul>
      <ul class="navbar-nav navbar-right">
        <li :class="menu === 9999 ? 'active' : ''" @click="menu = 9999">
          我的资料
        </li>
      </ul>
    </nav>

    <div class="container-fluid" style="margin-top: 80px; margin-bottom: 50px">
      <component :is="page" v-if="menu != 9999" />
      <person-profile v-if="menu == 9999" />
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import PersonProfile from '@/components/common/PersonProfile' // “个人资料”通用页面
import Admin from './Admin'
import Manager from './Manager'
import SampleDelivery from './sample/Delivery'
import SampleTest from './sample/Test'
import RiverChief from './region/RiverChief'
import RegionPatrol from './region/Patrol'
import RegionOfficer from './region/Officer'


// 所有角色对应的页面（子组件）
const rolePages = {
  'admin': 'Admin',
  'manager': 'Manager',
  'sample-delivery': 'SampleDelivery',
  'sample-test': 'SampleTest',
  'river-chief': 'RiverChief',
  'region-administra': 'RegionAdministra',
  'region-patrol': 'RegionPatrol',
  'region-officer': 'RegionOfficer'
}

export default {
  name: 'Page',
  components: {
    PersonProfile, Admin, Manager, SampleDelivery, SampleTest, RiverChief, RegionPatrol, RegionOfficer
  },
  data() {
    return {
      page: 'Admin',
      menus: [], // 所有菜单
      menu: 0, // 当前菜单
      menuChanged: null // 菜单变化时的回调（子页面的传过来的）
    }
  },
  watch: {
    'menu': function(val) { // 当用户点击菜单项，触发菜单切换
      if (val == 9999) return // “我的资料”菜单是9999，不触发子组件的页面切换
      this.$nextTick(() => { // 其他菜单项会触发子组件切换页面
        this.menuChanged(val)
      })
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
    this.displayByRole(this.urole)
  },
  methods: {
    displayByRole: function(role) {
      const page = rolePages[role]
      if (!page) {
        this.$message({ message: '无法识别身份角色', type: 'error' })
        return
      } else {
        this.page = page // 显示角色对应的页面
      }
    },
    /** 这是给子组件调用的钩子方法，子组件通过传递菜单配置项，来初始化菜单 */
    initNav: function(nav) {
      this.menus = nav.menus
      this.menuChanged = nav.changed // 这是子组件传来的回调钩子函数，当用户在本组件内点击菜单项，会把菜单项index传回子组件，子组件负责切换页面
    }
  }
}
</script>

<!-- Add 'scoped' attribute to limit CSS to this component only -->
<style scoped>
</style>

<!-- 超级管理员的主界面 -->
<template>
  <div>

    <el-row>
      <el-col v-if="menu === 0">
        <h4>系统中可用的账号</h4>
        <el-alert
          title="用手机号码登录。密码默认123456，或询问账号所有人"
          type="success">
        </el-alert>
        <el-table :data="accounts">
          <el-table-column prop="name" label="姓名" width="100px" />
          <el-table-column prop="telephone" label="手机号码" />
          <el-table-column prop="roles" label="身份权限">
            <template slot-scope="scope">
              {{ scope.row.role | roleName }}
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>

  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { getAllUsers, roleName } from '@/api/account.js'

export default {
  name: 'Admin',
  components: { },
  filters: {
    roleName: role => roleName(role)
  },
  data () {
    return {
      menu: 0,
      accounts: []
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
  created: function () {
    getAllUsers().then(resp => {
      console.log(resp.data)
      this.accounts = resp.data
    })
    this.$parent.initNav({
      menus: ['系统账号'],
      
      changed: this.navChanged
    })
    // this.$nextTick( () => {
    //   this.$emit('init-nav', { a: 1 })
    // })
    
  },
  methods: {
    navChanged: function(index) {
      this.menu = index
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>

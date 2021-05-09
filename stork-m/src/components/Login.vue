<template>
  <div style="height:100%;background-color: #b3dcec;">
      <div style="padding-top: 30%;margin-left: auto;margin-right: auto;width: 75%;">
        <el-card>
          <div slot="header" class="clearfix">“三水”协同移动端</div>
          <el-form>
            <el-form-item label>
              <el-input placeholder="账号（手机号）" v-model="login.username"></el-input>
            </el-form-item>
            <el-form-item label>
              <el-input type="password" placeholder="密码" v-model="login.password"></el-input>
            </el-form-item>
            <el-button @click="loginSubmit" type="primary" style="width: 100%;">登 录</el-button>
          </el-form>
        </el-card>
      </div>
  </div>
</template>

<script>
import { login, getUserInfo } from '@/api/account.js'
import { mapMutations } from 'vuex'

export default {
  name: 'Login',
  data () {
    return {
      accountId: undefined,
      login: {
        username: 'admin',
        password: 'zkcy2020'
      }
    }
  },
  methods: {
    ...mapMutations({
      setUser: 'setUser'
    }),
    loginSubmit: function () {
      login(this.login).then(resp => {
        getUserInfo(resp.data.token).then(resp => {
          this.setUser({user: resp.data})
          this.$router.push('page')
        })
      })
    }
  }
}
</script>

<style scoped>
html, body {
  height:100%;
}
.el-container {
  height:100%;
}
</style>

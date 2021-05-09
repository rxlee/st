vue = new Vue({
  el: '#app',
  data: {
    accountId: undefined,
    login: {
      username: 'admin',
      password: 'zkcy2020'
    }
  },
  created: function () { },
  methods: {
    loginSubmit: function () {
      Http.post({
        url: '/stork/api/account/login',
        data: this.login
      }).then(res => {
        if (res.code != 20000) {
          this.$message({
            message: res.message,
            type: 'error'
          })
          return
        }
        this.redirectByRole(res.data.token)
      })
    },
    redirectByRole: function (token) {
      Http.get({
        url: '/stork/api/account/info',
        params: {
          token: token
        }
      }).then(res => {
        const role = res.data.roles[0]//现在每个用户只有一个角色了
        window.localStorage.setItem('uid', res.data.uid)
        window.localStorage.setItem('uname', res.data.name)
        window.localStorage.setItem('uphone', res.data.telephone)
        window.localStorage.setItem('role', role)
        switch (role) {
          case Role.sampleDelivery:// 送样
            window.location = 'sample-delivery.html'
            break;
          case Role.sampleTest: // 检测
            window.location = 'sample-test.html'
            break;
          case Role.riverChief: // 河长
            window.location = 'river-chief.html'
            break;
          case Role.regionAdministra: // 镇长
            window.location = 'region-administra.html'
            break;
          case Role.regionPatrol: // 巡查员
            window.location = 'region-patrol.html'
            break;
          case Role.regionOfficer: // 镇文员
            window.location = 'region-officer.html'
            break;
          case Role.manager: // 领导
            window.location = 'manager.html'
            break;
          case Role.admin: // 超级管理员
            window.location = 'admin.html'
            break;
          default:
            this.$message({
              message: '无法识别身份角色',
              type: 'error'
            })
            break;
        }
      })
    }
  }
})
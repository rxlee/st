<template>
  <div class="app-container">
    <el-row :gutter="12">
      <el-col :span="16">
        <el-card>
          <div slot="header" class="clearfix">
            <span>用户列表</span>
          </div>
          <el-table :data="accounts">
            <el-table-column
              label="姓名"
              prop="name"
            />
            <el-table-column
              label="手机号码"
              prop="telephone"
            >
              <template slot-scope="scope">
                {{ scope.row.telephone === 'admin' ? '--' : scope.row.telephone }}
              </template>
            </el-table-column>
            <el-table-column
              label="账号状态"
              property="status"
            >
              <template slot-scope="scope">
                {{ scope.row.status == 1 ? '正常' : '禁用' }}
              </template>
            </el-table-column>
            <el-table-column
              label="操作"
              width="280"
            >
              <template slot-scope="scope">
                <el-popconfirm :title="'确定要禁用此账号（' + scope.row.name + '）？'" @onConfirm="ban(scope.row.id, true)">
                  <el-button
                    slot="reference"
                    :disabled="scope.row.status == 0"
                    type="danger"
                    size="small"
                  >
                    禁用
                  </el-button>
                </el-popconfirm>
                &nbsp;
                <el-popconfirm :title="'确定要恢复此账号（' + scope.row.name + '）？'" @onConfirm="ban(scope.row.id, false)">
                  <el-button
                    slot="reference"
                    :disabled="scope.row.status == 1"
                    type="success"
                    size="small"
                  >
                    恢复
                  </el-button>
                </el-popconfirm>
                &nbsp;
                <el-button type="warning" size="small" @click="resetPasswordStart(scope.row)">重置密码...</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card>
          <div slot="header" class="clearfix">
            <span>新加用户</span>
          </div>
          <el-form ref="formAdd" :model="newone" label-width="100px">
            <el-form-item
              label="姓名"
              prop="name"
              :rules="[{required: true, message: '必须填写真实姓名', trigger: 'blur'}]"
            >
              <el-input v-model="newone.name" />
            </el-form-item>
            <el-form-item
              label="手机号码"
              prop="telephone"
              :rules="[{required: true, message: '必须填写手机号码', trigger: 'blur'}]"
            >
              <el-input v-model="newone.telephone" />
            </el-form-item>
            <el-form-item
              label="初始密码"
              prop="password"
              :rules="[{required: true, message: '必须填写初始密码', trigger: 'blur'}]"
            >
              <el-input v-model="newone.password" type="password" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" size="small" @click="registerSubmit">注册</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
    <el-dialog
      title="重置密码"
      :visible.sync="dialogVisible"
      width="400px"
    >
      <p>通常在用户忘记密码的情况下由超级管理员为其重置密码，重置后应提醒用户用新密码登录并尽快修改。</p>
      <p>用户原密码仍然可用但用户想修改密码的情况，应由用户登录后在个人信息中自行修改，不需由超级管理员帮助重置。</p>
      <el-form ref="formResetPassword" :model="changed" label-width="100px">
        <el-form-item label="用户姓名"> {{ current.name }} </el-form-item>
        <el-form-item label="手机号码"> {{ current.telephone }} </el-form-item>
        <el-form-item
          label="新密码"
          prop="password"
          :rules="[{required: true, message: '必须填写新密码，不想重置请点“取消”或关闭此对话框', trigger: 'blur'}]"
        >
          <el-input v-model="changed.password" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="resetPasswordSubmit">保 存</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
// import DropdownMenu from '@/components/Share/DropdownMenu'

import { listAccounts, addAccount } from '@/api/account'

export default {
  name: 'SystemUsers',
  // components: { DropdownMenu },
  data() {
    return {
      accounts: [], // 所有账号信息
      newone: { // 正在新建的账号信息
        name: '',
        telephone: '',
        password: ''
      },
      current: {}, // 当前正在修改密码的对象
      dialogVisible: false, // 修改密码的对话框是否打开
      changed: {
        password: ''
      }
    }
  },
  created: function() {
    listAccounts({ }).then(response => {
      this.accounts = response.data
    })
  },
  methods: {
    // 填加新用户提交
    registerSubmit: function() {
      this.$refs['formAdd'].validate((valid) => {
        if (valid) {
          addAccount(this.newone).then(response => {
            this.accounts.unshift(response.data)
          })
        } else {
          return false
        }
      })
    },
    // 禁用或恢复一个账号
    ban: function(id, banned) {
      console.log('ban:', id, banned)
    },
    // 开始重置密码
    resetPasswordStart: function(account) {
      this.current = account
      this.changed.password = ''
      this.dialogVisible = true
    },
    resetPasswordSubmit: function() {
      this.$refs['formResetPassword'].validate((valid) => {
        if (valid) {
          // add(this.newone).then(response => {
          //   console.log(response)
          // })
          this.dialogVisible = false
        } else {
          return false
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
</style>

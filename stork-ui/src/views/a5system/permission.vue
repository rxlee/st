<template>
  <div class="app-container">
    <el-row :gutter="12">
      <el-col :span="16">
        <el-card>
          <div slot="header" class="clearfix">
            <span>用户列表</span>
          </div>
          <el-table :data="accounts">
            <el-table-column label="姓名" prop="name" />
            <el-table-column label="手机号码" prop="telephone">
              <template slot-scope="scope">
                {{ scope.row.telephone === 'admin' ? '--' : scope.row.telephone }}
              </template>
            </el-table-column>
            <el-table-column label="角色" prop="role">
              <template slot-scope="scope">
                {{ rname(scope.row.role) }}
              </template>
            </el-table-column>
            <el-table-column label="账号状态" property="status">
              <template slot-scope="scope">
                {{ scope.row.status == 1 ? '正常' : '禁用' }}
              </template>
            </el-table-column>
            <el-table-column label="操作" width="120">
              <template slot-scope="scope">
                <el-button type="primary" size="small" @click="rolesStart(scope.row)">修改权限...</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card>
          <div slot="header" class="clearfix">
            <span>权限操作说明</span>
          </div>
          <p>用户的操作权限，与“角色”挂钩，每种角色拥有特定的一组权限</p>
          <p>给用户设定一个角色，也就为其授予了这个角色对应的权限</p>
          <p>系统支持的角色及其对应的权限如下：</p>
          <ul class="help">
            <li><b>超级管理员</b>：拥有一切权限，且账号不可禁用、不可修改（可修改密码）</li>
            <li><b>待激活用户</b>：除了登录系统并维护个人资料之外没有任何其他权限，通常是新登记的账号</li>
            <li><b>领导</b>：无限制查看所有信息，并拥有审批权限</li>
            <li><b>维护人员</b>：查看设备运行状态，设备现场维护</li>
            <li><b>应急人员</b>：应急事件全流程处理，包括立案、处理、结案等</li>
            <li><b>监察人员</b>：监察执法全流程处理，包括立案、处理、结案等</li>
            <li><b>网站管理员</b>：可管理公共网站后台，发布内容和回复公众咨询等</li>
          </ul>
        </el-card>
      </el-col>
    </el-row>
    <el-dialog
      :title="'设置权限：' + current.name + ' / ' + current.telephone"
      :visible.sync="dialogVisible"
      width="680px"
    >
      <el-form ref="formResetPermission" :model="changed" label-width="0">
        <el-form-item>
          <el-radio-group v-model="changed.roleSpecial">
            <el-radio label="admin">超级管理员</el-radio>
            <el-radio label="guest">未激活用户</el-radio>
            <el-radio label="official">领导</el-radio>
            <el-radio label="">执行人员</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item v-if="changed.roleSpecial === ''">
          <el-divider />
          <h3>执行人员具体权限</h3>
          <p>注意：最多可选3个，最少1个</p>
          <el-checkbox-group v-model="changed.selectedRoles" :min="1" :max="3">
            <el-checkbox v-for="(v,k) in selectableRoles" :key="v" :label="k"> {{ v }}</el-checkbox>
          </el-checkbox-group>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="rolesSubmit">保 存</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>

import { Message } from 'element-ui'
import { listAccounts, listRoles, code2Name, setRoles } from '@/api/account'

export default {
  name: 'SystemPermission',
  data() {
    return {
      accounts: [], // 所有账号信息
      current: {}, // 当前正在修改权限的对象
      dialogVisible: false, // 修改权限的对话框是否打开
      selectableRoles: {},
      changed: { // 修改后的权限
        roleSpecial: '',
        selectedRoles: []
      }
    }
  },
  created: function() {
    listAccounts({ }).then(response => {
      this.accounts = response.data
    })
    const roles = listRoles()
    for (const i in roles) {
      if (i === 'admin' || i === 'guest' || i === 'official') continue
      this.selectableRoles[i] = roles[i]
    }
  },
  methods: {
    // 把数据库里的角色列表转为中文角色列表
    rname: function(code) {
      const names = []
      code.split(',').forEach(code => { names.push(code2Name(code)) })
      return names.join('，')
    },
    // 修改权限提交
    rolesSubmit: function() {
      let role
      if (this.changed.roleSpecial !== '') {
        role = this.changed.roleSpecial
      } else {
        role = this.changed.selectedRoles.join(',')
      }
      if (role && role.length > 0) {
        setRoles(this.current.id, role).then(response => {
          this.current.role = role
        })
        this.dialogVisible = false
      } else {
        Message({
          message: '必须指定角色',
          type: 'error',
          duration: 5 * 1000
        })
      }
    },
    // 开始修改权限
    rolesStart: function(account) {
      this.current = account
      this.changed.selectedRoles = []
      switch (account.role) {
        case 'guest':
        case 'admin':
        case 'official':
          this.changed.roleSpecial = account.role
          break
        default:
          this.changed.roleSpecial = ''
          this.changed.selectedRoles = account.role.split(',')
          break
      }
      this.dialogVisible = true
    }
  }
}
</script>

<style lang="scss" scoped>
  .help {
    line-height: 150%;
    li {
      margin-bottom: 8px;
    }
  }
</style>

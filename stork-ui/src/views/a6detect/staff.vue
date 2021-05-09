<template>
  <div class="app-container">
    <el-row :gutter="12">
      <el-col :span="14">
        <el-card>
          <div>
            <el-form>
              <el-form-item label="检测机构">
                <el-select v-model="newone.corp" placeholder="请选择一个机构">
                  <el-option
                    v-for="s in corps"
                    :key="s.id"
                    :label="s.name"
                    :value="s.id"
                  />
                </el-select>
              </el-form-item>
            </el-form>
          </div>
          <div slot="header" class="clearfix">
            <span>
              <i class="el-icon-document-copy" />
              现有人员
            </span>
          </div>
          <el-table :data="staffs" height="580px">
            <el-table-column label="姓名" prop="uname" />
            <el-table-column label="电话" prop="uphone" />
            <el-table-column label="职责">
              <template slot-scope="scope">
                <span>
                  {{ scope.row.utype == 1 ? "样品取送人员" : "样品检验人员" }}
                </span>
              </template>
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-link type="danger" @click="deleteStaff(scope.row)">
                  删除
                </el-link>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
      <el-col :span="10">
        <el-card>
          <div slot="header" class="clearfix">
            <span> <i class="el-icon-magic-stick" /> 添加检测人员</span>
          </div>
          <el-form>
            <el-form-item label="人员">
              <el-select v-model="newone.staff" placeholder="请选择一个人员">
                <el-option
                  v-for="s in accounts"
                  :key="s.id"
                  :label="s.name"
                  :value="s.id"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="人员职责">
              <el-radio-group v-model="newone.type" size="mini">
                <el-radio-button :label="1">取样送样</el-radio-button>
                <el-radio-button :label="2">样品检验</el-radio-button>
              </el-radio-group>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" :disabled="newone.corp == null || newone.staff == null" @click="addSubmit"> 添加 </el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import { listAllCorps, listStaff, addStaff, deleteStaff } from '@/api/detect'
import { listAccounts, setRoles } from '@/api/account'
import { Message } from 'element-ui'
export default {
  name: 'DetectStaff',
  data() {
    return {
      corps: [], // 所有机构
      staffs: [], // 当前选择的机构下所有人员
      accounts: [], // 系统中所有账号
      newone: {
        corp: null,
        staff: null,
        type: 1
      }
    }
  },
  watch: {
    // 监听当前机构选择，有变化则刷新机构下的人员列表
    'newone.corp': function(val) {
      listStaff(val).then(response => {
        this.staffs = response.data
      })
    }
  },
  created: function() {
    listAllCorps().then(response => {
      this.corps = response.data
    })
    listAccounts().then(response => {
      this.accounts = response.data
    })
  },
  methods: {
    addSubmit: function() {
      setRoles(this.newone.staff, this.newone.utype === 1 ? 'sample-delivery' : 'sample-test')
      addStaff(this.newone.corp, this.newone).then(response => {
        if (response.code !== 20000) {
          Message({
            message: response.message || '未知错误',
            type: 'error',
            duration: 5 * 1000
          })
        } else {
          Message({
            message: '添加人员成功',
            type: 'success',
            duration: 5 * 1000
          })
          listStaff(this.newone.corp).then(response => {
            this.staffs = response.data
          })
        }
      })
    },
    deleteStaff: function(row) {
      deleteStaff(row.cid, row.uid)
      listStaff(this.newone.corp).then(response => {
        this.staffs = response.data
      })
    }
  }
}
</script>

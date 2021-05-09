<template>
  <div class="app-container">
    <el-row :gutter="12">
      <el-col :span="14">
        <el-card>
          <div>
            <el-form>
              <el-form-item label="辖区">
                <el-select v-model="newone.region" placeholder="请选择一个辖区">
                  <el-option
                    v-for="s in regions"
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
              辖区现有人员
            </span>
          </div>
          <el-table :data="tableDate" height="580px">
            <el-table-column label="姓名" prop="uname" />
            <el-table-column label="电话" prop="uphone" />
            <el-table-column label="职责" prop="utype">
              <template slot-scope="scope">
                <span>
                  {{ scope.row.utype | duty }}
                </span>
              </template>
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-link type="danger" @click="removeStaff(scope.row)">
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
            <span> <i class="el-icon-magic-stick" /> 添加负责人员</span>
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
            <el-form-item label="职责">
              <el-radio-group v-model="newone.type" size="small">
                <el-radio-button label="1">河长</el-radio-button>
                <el-radio-button label="2">镇办负责人</el-radio-button>
                <el-radio-button label="3">巡查员</el-radio-button>
                <el-radio-button label="4">业务员</el-radio-button>
              </el-radio-group>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="addSubmit"> 保 存 </el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import { listAccounts } from '@/api/account'
import { Message } from 'element-ui'
import { listRegion, listStaffInRegion, addStaffInRegion, removeStaffInRegion } from '@/api/region'
export default {
  filters: {
    duty: function(code) {
      switch (code) {
        case 1: return '河长'
        case 2: return '镇办负责人'
        case 3: return '巡查员'
        case 4: return '业务员'
        default: return ''
      }
    }
  },
  data() {
    return {
      tableDate: [],
      regions: [],
      accounts: [],
      newone: {
        region: null,
        account: null,
        type: 1
      }
    }
  },
  watch: {
    'newone.region': function(val) {
      if (val == null) return
      listStaffInRegion(val).then(response => {
        this.tableDate = response.data
      })
    }
  },
  created: function() {
    listRegion().then(response => {
      this.regions = response.data
    })
    listAccounts().then(response => {
      this.accounts = response.data
    })
  },
  methods: {
    removeStaff: function(row) {
      removeStaffInRegion(row.rid, row.uid)
      listStaffInRegion(this.newone.region).then(response => {
        this.tableDate = response.data
      })
    },
    addSubmit: function() {
      addStaffInRegion(this.newone.region, this.newone).then(response => {
        if (response.code !== 20000) {
          Message({
            messasge: response.message || '未知错误',
            type: 'error',
            duration: 5 * 1000
          })
        } else {
          Message({
            message: '辖区新增人员完成',
            type: 'success',
            duration: 5 * 1000
          })
          listStaffInRegion(this.newone.region).then(response => {
            this.tableDate = response.data
          })
        }
      })
    },
    selectAccount: function(val) {
      this.newone.uid = val
    }
  }
}
</script>

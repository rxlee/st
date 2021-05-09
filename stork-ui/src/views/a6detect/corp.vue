<template>
  <div class="app-container">
    <el-row :gutter="12">
      <el-col :span="16">
        <el-card>
          <div slot="header" class="clearfix">
            <span>
              <i class="el-icon-document-copy" />
              检测机构
            </span>
          </div>
          <el-table :data="corps" :height="540">
            <el-table-column label="全称" prop="name" />
            <el-table-column label="简称" prop="alias" />
            <el-table-column label="地址" prop="address" />
            <el-table-column label="机构类型">
              <template slot-scope="scope">
                <span>
                  {{ scope.row.category == 1 ? "标准检测" : "非标检测" }}
                </span>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card height="800">
          <div slot="header" class="clearfix">
            <span> <i class="el-icon-magic-stick" /> 添加检测机构</span>
          </div>
          <el-form ref="formAdd" :model="newone" label-width="96px">
            <el-form-item
              label="全称"
              prop="name"
              :rules="[{ required: true, message: '必须输入一个名称', trigger: 'blur', }]"
            >
              <el-input v-model="newone.name" placeholder="工商注册全称" />
            </el-form-item>
            <el-form-item label="简称">
              <el-input v-model="newone.alias" placeholder="便于沟通和显示的简称" />
            </el-form-item>
            <el-form-item label="地址">
              <el-input v-model="newone.address" type="textarea" :rows="2" placeholder="机构详细地址" />
            </el-form-item>
            <el-form-item label="机构类型">
              <el-radio-group v-model="newone.category" size="mini">
                <el-radio-button :label="1">标准检测</el-radio-button>
                <el-radio-button :label="0">非标检测</el-radio-button>
              </el-radio-group>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="addSubmit">
                保 存
              </el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>
<script >
import { addCorp, listAllCorps } from '@/api/detect'
import { Message } from 'element-ui'
export default {
  data() {
    return {
      newone: {
        name: '',
        alias: '',
        address: '',
        category: 1
      },
      corps: []
    }
  },
  created: function() {
    listAllCorps().then(response => {
      this.corps = response.data
    })
  },
  methods: {
    addSubmit: function() {
      this.$refs['formAdd'].validate((valid) => {
        if (valid) {
          addCorp(this.newone).then(response => {
            if (response.code !== 20000) {
              Message({
                message: response.message || '未知错误',
                type: 'error',
                duration: 5 * 1000
              })
            } else {
              Message({
                message: '添加机构成功',
                type: 'success',
                duration: 5 * 1000
              })
              this.newone.name = ''
              this.newone.alias = ''
              this.newone.address = ''
              listOrange().then(response => {
                this.corps = response.data
              })
            }
          })
        } else {
          return false
        }
      })
    }
  }
}
</script>


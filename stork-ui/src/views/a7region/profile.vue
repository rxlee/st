<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="16">
        <el-card>
          <div slot="header" class="clearfix">
            <span> <i class="el-icon-document-copy" /> 现有辖区</span>
          </div>
          <el-form :inline="true">
            <el-form-item label="名称关键字查询">
              <el-input v-model="filter.name" placeholder="完整名称或名称中的关键字" />
            </el-form-item>
            <el-form-item>
              <el-button style="" type="primary" @click="query"> 查询 </el-button>
            </el-form-item>
          </el-form>
          <el-table :data="regions" height="580px">
            <el-table-column label="辖区全称" prop="name" />
            <el-table-column label="辖区简称" prop="alias" width="180" />
            <el-table-column label="详细" width="96">
              <template slot-scope="scope">
                <el-link>详细</el-link>
              </template>
            </el-table-column>
          </el-table>
          <div style="text-align:center">
            <el-pagination
              background
              layout="prev, pager, next"
              :total="10"
            />
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card>
          <div slot="header" class="clearfix">
            <span> <i class="el-icon-magic-stick" /> 新增辖区</span>
          </div>
          <el-form label-width="60px">
            <el-form-item label="名称">
              <el-input v-model="newone.name" placeholder="辖区名称" />
            </el-form-item>
            <el-form-item label="简称">
              <el-input v-model="newone.alias" placeholder="辖区简称" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="addRegion"> 保 存 </el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import { Message } from 'element-ui'
import { listRegion, addRegion, listRegionByName } from '@/api/region'
export default {
  data() {
    return {
      regions: [],
      filter: {
        name: ''
      },
      newone: {
        name: '',
        alias: ''
      }
    }
  },
  created: function() {
    listRegion().then(response => {
      this.regions = response.data
    })
  },
  methods: {
    query: function() {
      listRegionByName(this.filter.name)
    },
    addRegion: function() {
      addRegion(this.newone).then(response => {
        if (response.code !== 20000) {
          Message({
            message: response.message || '未知错误',
            type: 'error',
            duration: 5 * 1000
          })
        } else {
          Message({
            message: '新增辖区成功',
            type: 'success',
            duration: 5 * 1000
          })
          this.newone.name = ''
          this.newone.alias = ''
        }
      })
    }
  }
}
</script>

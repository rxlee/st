<template>
  <div class="app-container">
    <el-row>
      <el-col>
        <el-form :inline="true" :model="queryFilter">
          <el-form-item label="名称关键字">
            <el-input v-model="queryFilter.name" />
          </el-form-item>
          <el-form-item label="所属辖区">
            <el-select v-model="queryFilter.region" filterable placeholder="全部">
              <el-option :value="null">全部</el-option>
              <el-option
                v-for="r in regions"
                :key="r.id"
                :label="r.name"
                :value="r.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="设备S/N">
            <el-input v-model="queryFilter.sn" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" size="small">过滤</el-button>
            <el-button type="success" size="small" @click="startCreate">新增水站</el-button>
          </el-form-item>
        </el-form>
      </el-col>
      <el-col>
        <el-table :data="stations" height="580">
          <el-table-column label="全称" fixed width="340" prop="name" />
          <el-table-column label="简称" prop="alias" />
          <el-table-column label="编号" prop="code" width="196" />
          <el-table-column label="所属辖区" prop="rname" width="200">
            <template slot-scope="scope">
              {{ scope.row.rname ? scope.row.rname : '(未设置)' }}
            </template>
          </el-table-column>
          <el-table-column label="设备S/N" prop="sn" width="96">
            <template slot-scope="scope">
              {{ scope.row.sn ? scope.row.sn : '(未设置)' }}
            </template>
          </el-table-column>
          <el-table-column label="监控S/N" prop="vsn" width="136">
            <template slot-scope="scope">
              {{ scope.row.vsn ? scope.row.vsn : '(未设置)' }}
            </template>
          </el-table-column>
          <el-table-column label="修改" width="80">
            <template slot-scope="scope">
              <el-link type="primary" @click="startModify(scope)">修改</el-link>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
      <el-col style="margin-top:20px;text-align:center">
        <el-pagination
          background
          layout="prev, pager, next"
          :total="10"
        />
      </el-col>
    </el-row>

    <!-- 填加和修改的弹窗，data字段是传入一些必要的静态数据，动态数据通过方法传入，内详 -->
    <station-profile-dialog ref="dialog" :data="{ regions: regions}" />

  </div>
</template>

<script>

import { Message } from 'element-ui'
import StationProfileDialog from './components/StationProfileDialog'
import { listStation, queryStationById } from '@/api/station'
import { listRegion } from '@/api/region'

export default {
  name: 'StationProfile',
  components: { StationProfileDialog },
  data() {
    return {
      regions: [], // 辖区列表
      stations: [], // 水站列表
      queryFilter: {
        name: null,
        region: null,
        sn: null
      }
    }
  },
  created: function() {
    // 读取全部水站，遍历显示
    listStation().then(response => {
      this.stations = response.data
    })
    listRegion().then(response => {
      this.regions = response.data
    })
  },
  methods: {
    startCreate: function() {
      this.$refs.dialog.startCreate({ complete: this.createComplete })
    },
    startModify: function(scope) {
      this.$refs.dialog.startModify({
        index: scope.$index,
        data: scope.row,
        complete: this.modifyComplete
      })
    },
    // 给子组件的钩子方法。单个水站修改后刷新，回传参数是修改的行索引，本方法内应刷新这一行
    modifyComplete: function(i) {
      queryStationById(this.stations[i].id).then(response => {
        Object.assign(this.stations[i], response.data)
        Message({
          message: '修改完成，数据已更新',
          type: 'success',
          duration: 5 * 1000
        })
      })
    },
    // 给子组件的钩子方法。新增一个水站后的处理，回传参数是新增的水站信息
    createComplete: function(station) {
      this.stations.unshift(station)
      Message({
        message: '新增完成，数据已更新',
        type: 'success',
        duration: 5 * 1000
      })
    }
  }
}
</script>

<style lang="scss" scoped>
</style>

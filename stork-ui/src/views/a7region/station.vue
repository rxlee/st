<template>
  <div class="app-container">
    <el-row :gutter="12">
      <el-col :span="14">
        <el-card>
          <div>
            <el-form>
              <el-form-item>
                <el-select v-model="region" placeholder="请选择一个辖区">
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
              辖区现有水站
            </span>
          </div>
          <el-table :data="stations" height="580px">
            <el-table-column label="水站名字" prop="name" />
            <el-table-column label="编号" prop="code" />
            <el-table-column label="说明" prop="memo" />
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-link type="danger" @click="removeStation(scope.row)">
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
            <span> <i class="el-icon-magic-stick" /> 添加水站 </span>
          </div>
          <el-form>
            <el-form-item label="水站">
              <el-select v-model="station" placeholder="请选择一个水站">
                <el-option
                  v-for="s in allStations"
                  :key="s.id"
                  :label="s.name"
                  :value="s.id"
                />
              </el-select>
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
import { Message } from 'element-ui'
import { listRegion, findRegionById } from '@/api/region'
import { listStationByRegionId, removeStation, listStation, updateRegion } from '@/api/station'
export default {
  data() {
    return {
      regions: [],
      stations: [],
      station: {},
      allStations: [],
      region: null

    }
  },
  watch: {
    'region': function(val) {
      if (val == null) return
      listStationByRegionId(val).then(response => {
        this.stations = response.data
      })
    }
  },
  created: function() {
    listRegion().then(response => {
      this.regions = response.data
    })
    listStation().then(resp => {
      this.allStations = resp.data
    })
  },
  methods: {
    removeStation: function(row) {
      removeStation(row.id).then(resp => {
        listStationByRegionId(this.region).then(response => {
          this.stations = response.data
        })
      })
    },
    addSubmit: function() {
      findRegionById(this.region).then(response => {
        updateRegion(this.station, response.data).then(resp => {
          if (resp.code !== 20000) {
            Message({
              messasge: response.message || '未知错误',
              type: 'error',
              duration: 5 * 1000
            })
          } else {
            Message({
              message: '新增水站完成',
              type: 'success',
              duration: 5 * 1000
            })
          }
          listStationByRegionId(this.region).then(response => {
            this.stations = response.data
          })
        })
      })
    }
  }
}
</script>

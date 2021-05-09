<template>
  <div class="app-container">
    <el-row>
      <el-col>
        <el-form :inline="true">
          <el-form-item label="水站">
            <el-select v-model="filter.station" placeholder="全部">
              <el-option label="全部" :value="null" />
              <el-option v-for="s in stations" :key="s.id" :label="s.name" :value="s.id" />
            </el-select>
          </el-form-item>
          <el-form-item label="进度">
            <el-select v-model="filter.step" placeholder="全部">
              <el-option label="全部" :value="null" />
              <el-option v-for="s in steps" :key="s" :label="s" :value="s" />
            </el-select>
          </el-form-item>
          <el-form-item label="任务派发时间">
            <el-date-picker
              v-model="filter.datetime"
              type="datetimerange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit">开始查询</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-row>
      <el-col>
        <el-table :data="tableData" border height="580px">
          <el-table-column label="任务编号" prop="code" fixed width="180" />
          <el-table-column label="派发时间" prop="dispatchTime" width="150">
            <template slot-scope="scope">
              {{ scope.row.dispatchTime | fmt }}
            </template>
          </el-table-column>
          <el-table-column label="水站" prop="sampleSalias" width="240" />
          <el-table-column label="进度" prop="step" width="80">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.step == 0" type="warning">待取样</el-tag>
              <el-tag v-if="scope.row.step == 10">送样中</el-tag>
              <el-tag v-if="scope.row.step == 20" type="success">检测中</el-tag>
              <el-tag v-if="scope.row.step == 30" type="info">完成</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="送样人" prop="deliveryUname" width="80" />
          <el-table-column label="检测机构" prop="chargeCalias" width="100" />
          <el-table-column label="取样时间" prop="deliveryTime" width="" />
          <el-table-column label="样品送达时间" prop="" width="" />
          <el-table-column label="检测员" prop="" width="" />
          <el-table-column label="检测完成时间" prop="" width="" />
          <el-table-column label="非标检测机构" prop="" width="" />
          <el-table-column label="非标检测员" prop="" width="" />
          <el-table-column label="非标检测完成时间" prop="" width="" />
        </el-table>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import { listStation } from '@/api/station'
// import { Message } from 'element-ui'
import { formatDate } from '@/api/common'
import { listAllTask } from '@/api/detect'
export default {
  name: 'TaskQuery',
  filters: {
    fmt: function(date) {
      return formatDate(new Date(date), 'yyyy-MM-dd hh:mm')
    }
  },
  data() {
    return {
      stations: [],
      steps: [],
      filter: {
        station: null,
        datetime: null,
        step: null
      },
      tableData: [],
      stationNames: {}
    }
  },
  created: function() {
    listStation().then(response => {
      this.stations = response.data
      for (let i = 0; i < this.stations.length; i++) {
        const s = this.stations[i]
        this.stationNames[s.id] = s.name
      }
    })
    listAllTask().then(resp => {
      this.tableData = resp.data
    })
  },
  methods: {
    onSubmit: function() {

    }
  }
}
</script>


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
          <el-form-item label="报警类型">
            <el-select v-model="filter.type" placeholder="全部">
              <el-option label="全部" :value="null" />
              <el-option label="采样数据超标" value="1" />
              <el-option label="硬件运行异常" value="2" />
            </el-select>
          </el-form-item>
          <el-form-item label="报警日期范围">
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
        <el-table
          :data="tableData"
          border
          height="580px"
        >
          <el-table-column prop="rtime" fixed width="140" label="数据时间">
            <template slot-scope="scope">{{ scope.row.alarmTime | fmt }}</template>
          </el-table-column>
          <el-table-column prop="mainType" label="报警类型" width="140" />
          <el-table-column prop="subType" label="报警子类型" width="140" />
          <el-table-column prop="summary" label="报警内容" />
        </el-table>
      </el-col>
      <el-col style="margin-top:12px;text-align:center">
        <el-pagination
          background
          layout="prev, pager, next"
          :total="0"
        />
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { Message } from 'element-ui'
import { formatDate } from '@/api/common'
import { listStation } from '@/api/station'
import { queryAlarmHistory } from '@/api/alarm'

export default {
  name: 'MonitorAlarm',
  filters: {
    fmt: function(date) {
      return formatDate(new Date(date), 'yyyy-MM-dd hh:mm')
    }
  },
  data() {
    return {
      stations: [],
      filter: {
        station: null,
        datetime: null,
        type: null
      },
      tableData: []
    }
  },
  created: function() {
    listStation().then(response => {
      this.stations = response.data
    })
  },
  methods: {
    onSubmit: function() {
      const from = this.filter.datetime ? this.filter.datetime[0] : null
      const to = this.filter.datetime ? this.filter.datetime[1] : null
      queryAlarmHistory(this.filter.station, from, to, 0, 15).then(response => {
        this.tableData = response.data
        if (this.tableData.length === 0) {
          Message({
            message: '查询完成，但没有符合条件的数据',
            type: 'warning',
            duration: 5 * 1000
          })
        } else {
          Message({
            message: '查询完成',
            type: 'success',
            duration: 5 * 1000
          })
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
</style>

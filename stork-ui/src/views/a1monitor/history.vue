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
          <el-form-item label="日期范围">
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
            <el-button type="warning" @click="onExport" :disabled="tableData == null || tableData.length==0">数据导出</el-button>
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
          <el-table-column fixed width="140" label="水站">
            <template slot-scope="scope">{{ stationNames[scope.row.stationId] }}</template>
          </el-table-column>
          <el-table-column fixed width="140" label="数据时间">
            <template slot-scope="scope">{{ scope.row.rtime | fmt }}</template>
          </el-table-column>
          <el-table-column prop="t" label="水温" />
          <el-table-column prop="d" label="深度" />
          <el-table-column prop="s" label="流速" />
          <el-table-column prop="ph" label="酸碱度" />
          <el-table-column prop="oxy" label="溶解氧" />
          <el-table-column prop="cd" label="电导率" />
          <el-table-column prop="tb" label="浊度">
            <template slot-scope="scope">{{ handleE(scope.row.tb) }}</template>
          </el-table-column>
          <el-table-column prop="cod" label="COD" />
          <el-table-column prop="nh3" label="氨氮" />
          <el-table-column prop="p" label="总磷" />
          <el-table-column prop="p" label="综合评价">
            <template slot-scope="scope">
              <el-tag :type="evaType(scope.row.eva)">{{ eva(scope.row.eva) }}</el-tag>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
      <el-col style="margin-top:12px;text-align:center">
        <el-pagination
          background
          layout="prev, pager, next"
          :total="100"
        />
      </el-col>
    </el-row>


    <el-dialog
      title="数据导出"
      :visible.sync="dialogVisible"
      width="40%"
    >
      <el-form label-width="96px">
        <el-form-item label="指定水站">
          <el-select v-model="exportFilter.station" placeholder="全部">
            <el-option label="全部" :value="null" />
            <el-option v-for="s in stations" :key="s.id" :label="s.name" :value="s.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="导出时段">
          <el-radio-group v-model="exportFilter.datetime">
            <el-radio-button label="当天" />
            <el-radio-button label="前一天" />
            <el-radio-button label="当月" />
            <el-radio-button label="前一月" />
            <el-radio-button label="当年" />
            <el-radio-button label="前一年" />
          </el-radio-group>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { Message } from 'element-ui'
import { formatDate } from '@/api/common'
import { listStation } from '@/api/station'
import { queryRealtimeHistory } from '@/api/realtime'

export default {
  name: 'MonitorHistory',
  filters: {
    fmt: function(date) {
      return formatDate(new Date(date), 'yyyy-MM-dd hh:mm')
    }
  },
  data() {
    return {
      dialogVisible: false,
      stations: [],
      filter: {
        station: null,
        datetime: null
      },
      exportFilter: {
        station: null,
        datetime: '当天'
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
  },
  methods: {
    onExport: function() {
      this.dialogVisible = true
    },
    handleE: function(e) {
      return ('x' + e).indexOf('e') > 0 ? 0 : e
    },
    eva: function(v) {
      return ['优', '良', '差'][v]
    },
    evaType: function(v) {
      return ['success', 'warning', 'danger'][v]
    },
    onSubmit: function() {
      const from = this.filter.datetime ? this.filter.datetime[0] : null
      const to = this.filter.datetime ? this.filter.datetime[1] : null
      queryRealtimeHistory(this.filter.station, from, to, 0, 15).then(response => {
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

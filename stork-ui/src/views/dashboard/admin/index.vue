<template>
  <div class="app-container">
    <el-row :gutter="20" style="margin-bottom:20px">
      <el-col :span="12">
        <el-card style="height:320px">
          <div slot="header" class="clearfix">
            <span> <i class="el-icon-success" /> 近期水站水质评价</span>
          </div>
          <el-table :data="evaluates" :show-header="false">
            <el-table-column prop="station">
              <template slot-scope="scope">
                {{ getStationName(scope.row.station) }}
              </template>
            </el-table-column>
            <el-table-column prop="eva" width="96">
              <template slot-scope="scope">
                <el-tag :type="evaType(scope.row.eva)">{{ eva(scope.row.eva) }}</el-tag>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card style="height:320px">
          <div slot="header" class="clearfix">
            <span> <i class="el-icon-message-solid" /> 近期报警信息</span>
          </div>
          <el-table :data="alarms" :show-header="false">
            <el-table-column prop="title" />
            <el-table-column prop="time" width="96" />
          </el-table>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" style="margin-bottom:20px">
      <el-col :span="12">
        <el-card style="height:320px">
          <div slot="header" class="clearfix">
            <span> <i class="el-icon-warning" /> 近期应急事件</span>
          </div>
          <el-table :data="urgents" :show-header="false">
            <el-table-column prop="title" />
            <el-table-column prop="time" width="96" />
          </el-table>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card style="height:320px">
          <div slot="header" class="clearfix">
            <span> <i class="el-icon-star-on" /> 近期监察记录</span>
          </div>
          <el-table :data="enforces" :show-header="false">
            <el-table-column prop="title" />
            <el-table-column prop="time" width="96" />
          </el-table>
        </el-card>
      </el-col>
    </el-row><el-row :gutter="20" style="margin-bottom:20px">
      <el-col :span="12">
        <el-card style="height:320px">
          <div slot="header" class="clearfix">
            <span> <i class="el-icon-warning" /> 近期公众咨询建议</span>
          </div>
          <el-table :data="praises" :show-header="false">
            <el-table-column prop="title" />
            <el-table-column prop="time" width="96" />
          </el-table>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card style="height:320px">
          <div slot="header" class="clearfix">
            <span> <i class="el-icon-star-on" /> 近期公众监督投诉</span>
          </div>
          <el-table :data="punishes" :show-header="false">
            <el-table-column prop="title" />
            <el-table-column prop="time" width="96" />
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { listStation } from '@/api/station'
import { allNewestEvas } from '@/api/realtime'

export default {
  name: 'DashboardAdmin',
  components: {
  },
  data() {
    return {
      stations: [],
      filter: {
        station: null,
        datetime: null
      },
      alarms: [],
      evaluates: [],
      urgents: [],
      enforces: [],
      praises: [],
      punishes: [],
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
      allNewestEvas().then(response1 => {
        this.evaluates = response1.data
      })
    })
  },
  methods: {
    getStationName: function(id) {
      return this.stationNames[id]
    },
    eva: function(v) {
      return ['优', '良', '差'][v]
    },
    evaType: function(v) {
      return ['success', 'warning', 'danger'][v]
    }
  }
}
</script>

<style lang="scss" scoped>
</style>

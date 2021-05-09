<template>
  <div class="app-container">
    <el-row>
      <el-col>
        <el-form :inline="true">
          <el-form-item label="水站">
            <el-select v-model="filter.station" placeholder="请选择">
              <el-option v-for="s in stations" :key="s.id" :label="s.name" :value="s.id" />
            </el-select>
          </el-form-item>
          <el-form-item label="仪表">
            <el-select v-model="filter.device" placeholder="全部" :disabled="true">
              <el-option label="全部" :value="null" />
              <el-option label="流速" value="s" />
              <el-option label="多参数" value="g" />
              <el-option label="总磷" value="p" />
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
            <el-button type="primary" @click="onSubmit">开始生成</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="span">
        <el-card v-show="filter.device === 's' || filter.device === null" style="margin-bottom:20px">
          <div slot="header" class="clearfix">
            <span>流速仪</span>
          </div>
          <div id="chartDiv1" style="width:100%;height:300px" />
        </el-card>
      </el-col>
      <el-col :span="span">
        <el-card v-show="filter.device === 'p' || filter.device === null" style="margin-bottom:20px">
          <div slot="header" class="clearfix">
            <span>总磷</span>
          </div>
          <div id="chartDiv2" style="width:100%;height:300px" />
        </el-card>
      </el-col>
      <el-col :span="span">
        <el-card v-show="filter.device === 'g' || filter.device === null" style="margin-bottom:20px">
          <div slot="header" class="clearfix">
            <span>多参数（1）</span>
          </div>
          <div id="chartDiv3" style="width:100%;height:300px" />
        </el-card>
      </el-col>
      <el-col :span="span">
        <el-card v-show="filter.device === 'g' || filter.device === null" style="margin-bottom:20px">
          <div slot="header" class="clearfix">
            <span>多参数（2）</span>
          </div>
          <div id="chartDiv4" style="width:100%;height:300px" />
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { Message } from 'element-ui'
import { listStation } from '@/api/station'
import { queryRealtimes } from '@/api/realtime'

import echarts from 'echarts'

export default {
  name: 'MonitorTrend1',
  // components: { LineChart },
  data() {
    return {
      stations: [],
      filter: {
        station: null,
        datetime: null,
        device: null
      }
    }
  },
  computed: {
    span: function() {
      return this.filter.device == null ? 12 : 24
    }
  },
  created: function() {
    listStation().then(response => {
      this.stations = response.data
      // const c = echarts.init(this.$refs['chartDiv1']) // document.getElementById('chartDiv0')
    })
  },
  methods: {
    handleE: function(e) {
      return ('x' + e).indexOf('e') > 0 ? 0 : e
    },
    onSubmit: function() {
      if (this.filter.station === null) {
        Message({
          message: '必须选择一个水站',
          type: 'error',
          duration: 5 * 1000
        })
        return
      }
      // for (let i = 1; i < 5; i++) {
      //   document.getElementById('chartDiv' + i).innerHTML = ''
      // }
      this.$nextTick(() => {
        this.renderCharts()
      })
    }, // onSubmit
    renderCharts: function() {
      const from = this.filter.datetime ? this.filter.datetime[0] : null
      const to = this.filter.datetime ? this.filter.datetime[1] : null
      // if (this.filter.device != null) {
      queryRealtimes(this.filter.station, from, to, 's').then(response => {
        const tdata = []
        const ddata = []
        const sdata = []
        const datas = response.data
        for (let i = 0; i < datas.length; i++) {
          const d = datas[i]
          tdata.push([d.rtime, d.t])
          ddata.push([d.rtime, d.d])
          sdata.push([d.rtime, d.s])
        }
        echarts.init(document.getElementById('chartDiv1')).setOption({
          tooltip: { show: true },
          legend: { show: true, data: ['水温', '水深', '流速'] },
          xAxis: { type: 'time' },
          yAxis: { type: 'value' },
          series: [
            { name: '水温', type: 'line', data: tdata },
            { name: '水深', type: 'bar', data: ddata, itemStyle: { color: '#ffcc33' }},
            { name: '流速', type: 'line', data: sdata }
          ]
        }, { notMerge: false }) // setOption
      }) // queryRealtimes
      queryRealtimes(this.filter.station, from, to, 'p').then(response => {
        const pdata = []
        const datas = response.data
        for (let i = 0; i < datas.length; i++) {
          const d = datas[i]
          pdata.push([d.rtime, d.val])
        }
        echarts.init(document.getElementById('chartDiv2')).setOption({
          tooltip: { show: true },
          xAxis: { type: 'time' },
          yAxis: { type: 'value' },
          series: [
            { name: '总磷', type: 'line', data: pdata }
          ]
        }, { notMerge: false }) // setOption
      }) // queryRealtimes
      queryRealtimes(this.filter.station, from, to, 'g').then(response => {
        const cond = []
        const ph = []
        const oxy = []
        const turb = []
        const cod = []
        const nh3 = []
        const datas = response.data
        for (let i = 0; i < datas.length; i++) {
          const d = datas[i]
          cond.push([d.rtime, d.v1])
          ph.push([d.rtime, d.v2])
          oxy.push([d.rtime, d.v3])
          turb.push([d.rtime, this.handleE(d.v4)])
          cod.push([d.rtime, d.v5])
          nh3.push([d.rtime, d.v6])
        }
        echarts.init(document.getElementById('chartDiv3')).setOption({
          tooltip: { show: true },
          legend: { show: true, data: ['电导率', '酸碱度', '溶解氧'] },
          xAxis: { type: 'time' },
          yAxis: { type: 'value' },
          series: [
            { name: '电导率', type: 'line', data: cond },
            { name: '酸碱度', type: 'bar', data: ph },
            { name: '溶解氧', type: 'line', data: oxy }
          ]
        }, { notMerge: false }) // setOption
        echarts.init(document.getElementById('chartDiv4')).setOption({
          tooltip: { show: true },
          legend: { show: true, data: ['浊度', '氧化还原电位', '氨氮'] },
          xAxis: { type: 'time' },
          yAxis: { type: 'value' },
          series: [
            { name: '浊度', type: 'line', data: turb },
            { name: '氧化还原电位', type: 'line', data: cod },
            { name: '氨氮', type: 'line', data: nh3 }
          ]
        }, { notMerge: false }) // setOption
      }) // queryRealtimes
      // }// if
    } // renderCharts
  }
}
</script>

<style lang="scss" scoped>
</style>

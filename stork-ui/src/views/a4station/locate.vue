<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="7">
        <el-table
          :data="stations"
          highlight-current-row
          @current-change="handleCurrentChange"
        >
          <el-table-column property="name" label="水站名" />
          <el-table-column property="sn" label="设备号" width="72" />
          <el-table-column label="经纬度">
            <template slot-scope="scope">
              {{ scope.row.longitude }}, {{ scope.row.latitude }}
            </template>
          </el-table-column>
        </el-table>
        <el-button type="primary" @click="save">保 存</el-button>
      </el-col>
      <el-col :span="17">
        <div id="mapDiv" style="height:580px" />
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { Message } from 'element-ui'
import T from '@/TMap.js'
import { listStation, modifyStationLocateBatch } from '@/api/station'
export default {
  name: 'EquipLocate',
  // components: { DropdownMenu },
  data() {
    return {
      map: null,
      stations: [],
      current: null,
      markers: {}
    }
  },
  created: function() {
    listStation().then(response => {
      this.stations = response.data
      this.initMap()
    })
  },
  methods: {
    initMap: function() {
      T.init().then((T) => {
        this.map = new T.Map('mapDiv')
        const map = this.map
        map.centerAndZoom(new T.LngLat(117.10533, 38.9068), 11)
        map.addEventListener('click', e => {
          this.locate(T, e.lnglat.getLng(), e.lnglat.getLat())
        })
        for (let i = 0; i < this.stations.length; i++) {
          const s = this.stations[i]
          if (s.longitude && s.latitude) {
            const m = new T.Marker(new T.LngLat(s.longitude, s.latitude))
            const l = new T.Label({ text: s.name, position: m.getLngLat(), offset: new T.Point(3, -20) })
            this.markers[s.id] = [m, l]
            map.addOverLay(m)
            map.addOverLay(l)
          }
        }
      })
    },
    handleCurrentChange: function(current) {
      this.current = current
    },
    locate: function(T, lng, lat) {
      if (!this.current) return
      this.current.longitude = lng
      this.current.latitude = lat
      const exist = this.markers[this.current.id]
      if (exist != null) {
        this.map.removeOverLay(exist[0])
        this.map.removeOverLay(exist[1])
      }
      const m = new T.Marker(new T.LngLat(lng, lat))
      const l = new T.Label({ text: this.current.name, position: m.getLngLat(), offset: new T.Point(3, -20) })
      this.markers[this.current.id] = [m, l]
      this.map.addOverLay(m)
      this.map.addOverLay(l)
    },
    renderCurrent: function() {
    },
    save: function() {
      const locates = []
      for (let i = 0; i < this.stations.length; i++) {
        const s = this.stations[i]
        locates.push({ station: s.id, lng: s.longitude, lat: s.latitude })
      }
      modifyStationLocateBatch(locates).then(response => {
        Message({
          message: '水站定位保存完成',
          type: 'success',
          duration: 5 * 1000
        })
      })
    }
  }
}
</script>

<style lang="scss" scoped>
</style>

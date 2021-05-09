<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="mapMode ? 19 : 0">
        <div id="mapDiv" style="width:100%;height:580px;border:solid 1px #666" />
      </el-col>
      <el-col :span="mapMode ? 5 : 24">
        <el-button v-if="mapMode" type="primary" size="small" @click="mapMode = !mapMode">
          <i class="el-icon-d-arrow-left" /> 显示完整数据
        </el-button>
        <el-button v-if="!mapMode" type="primary" size="small" @click="mapMode = !mapMode">
          <i class="el-icon-d-arrow-right" /> 在地图中显示
        </el-button>
        <el-table :data="stations" :height="520" :border="!mapMode">
          <el-table-column prop="name" />
          <el-table-column label="水温" v-if="!mapMode" />
          <el-table-column label="流速" v-if="!mapMode" />
          <el-table-column label="pH" v-if="!mapMode" />
          <el-table-column label="氨氮" v-if="!mapMode" />
          <el-table-column label="COD" v-if="!mapMode" />
          <el-table-column label="浊度" v-if="!mapMode" />
          <el-table-column label="总磷" v-if="!mapMode" />
          <el-table-column label="评价" prop="eva" width="54">
            <template slot-scope="scope">
              <el-tag :type="scope.row.eva | evaCol">{{ scope.row.eva | evalbl }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="排放" prop="eva" width="96">
            <template slot-scope="scope">
              <el-link :type="scope.row.blow == '正在' ? 'danger' : 'info'">{{ scope.row.blow }}</el-link>
            </template>
          </el-table-column>
        </el-table>
        <div style="text-align:center">
          <el-pagination
            background
            layout="prev, pager, next"
            :total="6">
          </el-pagination>
        </div>
      </el-col>
    </el-row>
    <el-dialog
      :title="'实时视频：' + (current ? current.name : '')"
      :visible.sync="dialogVisible"
      width="520px"
      :before-close="handleClose"
    >
      <iframe
        id="iframe"
        ref="iframe"
        src=""
        frameborder="0"
        width="480"
        height="360"
        scrolling="no"
      />
    </el-dialog>

  </div>
</template>

<script>
import { Message } from 'element-ui'
import T from '@/TMap.js'
import { listStation } from '@/api/station'
import { allNewest } from '@/api/realtime'

const cirles = [
  {color:"#FFFFFF",weight:2,opacity:1,fillColor:"#67C23A",fillOpacity:1,lineStyle:"solid"},
  {color:"#FFFFFF",weight:2,opacity:1,fillColor:"#E6A23C",fillOpacity:1,lineStyle:"solid"},
  {color:"#FFFFFF",weight:2,opacity:1,fillColor:"#F56C6C",fillOpacity:1,lineStyle:"solid"}
]
const evaColors = ['success', 'warning', 'danger']
const evaLabels = ['优', '良', '差']

export default {
  name: 'MonitorRealtime',
  filters: {
    evalbl: function(val) {
      return evaLabels[val]
    },
    evaCol: function(val) {
      return evaColors[val]
    }
  },
  data() {
    return {
      stations: [
        { id: 1, sn: 161, name: '测试水站1', longitude: 117.1534, latitude: 38.91508, eva: 0, blow: '3天前' },
        { id: 2, sn: 162, name: '测试水站2', longitude: 117.14447, latitude: 38.90546, eva: 1, blow: '正在' },
        { id: 3, sn: 163, name: '测试水站3', longitude: 117.09023, latitude: 38.84666, eva: 2, blow: '1小时前' },
        { id: 4, sn: 164, name: '测试水站4', longitude: 117.10533, latitude: 38.86858, eva: 0, blow: '18小时前' },
        { id: 5, sn: 165, name: '测试水站5', longitude: 117.12799, latitude: 38.88943, eva: 0, blow: '10天前' },
        { id: 6, sn: 166, name: '测试水站6', longitude: 117.0916, latitude: 38.91935, eva: 0, blow: '4天前' }
      ],
      realtimes: {},
      current: null,
      timer: null,
      map: null,
      markers: {},
      mapMode: true,
      infoWindow: null
    }
  },
  computed: {
    dialogVisible: function() {
      return this.current != null
    }
  },
  created: function() {
    // listStation().then(response => {
      // this.stations = response.data
      this.initMap()
    // })
    // this.timer = setInterval(() => {
    //   this.fetchData()
    // }, 1000 * 60)
  },
  beforeDestroy: function() {
    if (this.timer) clearInterval(this.timer)
  },
  methods: {
    initMap: function() {
      T.init().then((T) => {
        this.map = new T.Map('mapDiv')
        const map = this.map
        map.centerAndZoom(new T.LngLat(117.10533, 38.9068), 11)
        var imageURL = "http://t0.tianditu.gov.cn/img_w/wmts?" +
                "SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=img&STYLE=default&TILEMATRIXSET=w&FORMAT=tiles" +
                "&TILEMATRIX={z}&TILEROW={y}&TILECOL={x}&tk=0557bed1bcbd62526ef6f1ef76e668a7";
            //创建自定义图层对象
            var lay = new T.TileLayer(imageURL, {minZoom: 1, maxZoom: 18})
            //将图层增加到地图上
            map.addLayer(lay)
        map.addEventListener('click', e => {
          // alert(e.lnglat.getLng() + ',' + e.lnglat.getLat())
          // this.locate(T, e.lnglat.getLng(), e.lnglat.getLat())
        })
        for (let i = 0; i < this.stations.length; i++) {
          const s = this.stations[i]
          if (s.longitude && s.latitude) {
            var bg = new T.Circle(
              new T.LngLat(s.longitude + .0015, s.latitude - .0015),
              500,
              {color:"#000",weight:2,opacity:1,fillColor:"#000",fillOpacity:1,lineStyle:"solid"}
            )
            var circle = new T.Circle(
              new T.LngLat(s.longitude, s.latitude),
              500,
              cirles[s.eva]
            )
            circle['sn'] = s.sn
            circle['name'] = s.name
            map.addOverLay(bg)
            map.addOverLay(circle)
            circle.addEventListener('mouseover', e => {
              const sn = e.target.sn
              if (this.infoWindow != null && this.infoWindow.sn !== sn) {
                map.removeOverLay(this.infoWindow)
                this.infoWindow = null
              }
              if(this.infoWindow != null) return
              var infoWin = new T.InfoWindow()
              infoWin.setLngLat(e.lnglat)
              infoWin['sn'] = s.sn
              //设置信息窗口要显示的内容
              infoWin.setContent(
                '<div>'+ e.target.name+'</div><hr><div>水温：4.2℃</div>'
                +'<div>流速：0.2m/s</div>'
                +'<div>pH：6.3</div>'
                +'<div>COD：1.2mg/L</div>'
                +'<div>氨氮：0.2mg/L</div>'
                +'<div><button>视频</button></div>')
              //向地图上添加信息窗口
              map.addOverLay(infoWin)
              this.infoWindow = infoWin
            })
            
            // const m = new T.Marker(new T.LngLat(s.longitude, s.latitude))
            // const l = new T.Label({ text: s.name, position: m.getLngLat(), offset: new T.Point(3, -20) })
            // this.markers[s.id] = [m, l]
            // map.addOverLay(m)
            // map.addOverLay(l)
          }
        }
      })
    },
    locate: function(T, lng, lat) {
    },
    startVideo: function(station) {
      if (!station.videoToken || !station.videoSn) {
        Message({
          message: station.name + ' 未配置视频参数，无法播放',
          type: 'error',
          duration: 5 * 1000
        })
        return
      }
      this.current = station
      this.$nextTick(() => {
        const token = station.videoToken ? station.videoToken : 'at.7kz7c0ja9h9dnxa2b5zapryja5xn6sas-3ut9fp4wr0-11xbpmj-r1yqnhiyt'
        const sn = station.videoSn ? station.videoSn : 'E86337727'
        const url = (process.env.NODE_ENV === 'development' ? 'http://localhost:88' : '') +
          '/v/?sn=' +
          sn +
          '&token=' +
          token +
          '&t=' +
          new Date().getTime()
        this.$refs['iframe'].src = url
      })
    },
    handleClose: function() {
      this.current = null
      this.$refs['iframe'].contentWindow.postMessage('stop', '*')
    }
  }
}
</script>

<style lang="scss" scoped>
</style>

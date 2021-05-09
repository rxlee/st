var Clock = function(vue) {
  this.vue = vue
  var $this = this
  this.update = function() {
    const now = new Date()
    $this.vue.date = now.format('yyyy年MM月dd日')
    $this.vue.time = now.format('hh:mm:ss')
  }
}

var MiniChart = function(vue)
{
  this.vue = vue
  const Charts = window.Charts.default
  this.chart = new Charts(vue.$refs.ch)
  var $this = this
  this.update = function(data) {
    //注意这个输入参数data是写在series.data
    if(!data) {
      // 没输入的情况下模拟生成
      data = []
      for (let i = 0; i < 10; i++) {
        data.push(parseInt(Math.random()*4))
      }
    }
    $this.chart.setOption({
      grid: { bottom: 0, top: 10, style:{color: 'rgba(0, 0, 0, 1)'}},
      xAxis: {
        axisLabel:{show:false},
        name: '',
        data: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
      },
      yAxis: {
        axisLabel:{show:false},
        name: '',
        data: 'value'
      },
      series: [
        {
          data: data,//[1200, 2230, 1900, 2100, 3500, 4200, 3985],
          type: 'line',
          lineArea: {
            show: true,
            gradient: ['rgba(55, 162, 218, 0.6)', 'rgba(55, 162, 218, 0)']
          }
        }
      ]
    })
  }
  this.update()
}

var Radar = function(vue) {
  this.vue = vue
  this.chart = null
  this.eva = new Eva()
  var $this = this
  this.init = function() {
    const Charts = window.Charts.default
    $this.chart = new Charts(document.getElementById('eva-radar-chart'))
    $this.update()
  }
  this.update = function() {
    $this.chart.setOption($this.eva.getOption())
  }
}
var Eva = function() {
  this.getOption = function() {
    return {
      radar: {
        indicator: [
          { name: '站 8D', max: 4 },
          { name: '站 7D', max: 4 },
          { name: '站 3F', max: 4 },
          { name: '站 2C', max: 4 },
          { name: '站 1C', max: 4 }
        ],
        polygon: true,
        splitLine: {
          style: { stroke: 'rgba(251, 114, 147, .5)', fill: 'rgba(255, 94, 117, .25)' }
        },
        axisLabel: {
          color: ['#6f99c9']
        }
      },
      series: [
        {
          type: 'radar',
          point: { radius: 4 },
          label: { formatter: function(v) { return (v == 0 ? '优' : ( v == 1 ? '良' : '差'))}, style: { fontSize: 14, color: '#ffffff'} },
          data: [1, 2, 2, 4, 1, 3]
        }
      ]
    }
  } // getOption
}

var SimulateRealtime = function() {
  this.getData = function() {
    return {
      t: (Math.random() * 3 + 10).toFixed(1) + '℃',
      s: (Math.random() * 1 + 0.2).toFixed(1) + 'm/s',
      tb: (Math.random() * 50 + 30).toFixed(1) + 'mg/L',
      ph: (Math.random() * 2 + 5).toFixed(1) + '',
      cd: (Math.random() * 100 + 100).toFixed(1) + 'mg/L',
      cod: (Math.random() * 40 + 30).toFixed(1) + 'S/m',
      nh: (Math.random() * 0.5 + 0.2).toFixed(1) + 'mg/L',
      oxy: (Math.random() * 0.5 + 0.4).toFixed(1) + 'mg/L',
    }
  }
}
var vue = new Vue({
  el: '#app',
  data: {
    videoOn: false,
    evaRadar: null,//左上角综合评价雷达图的组件对象
    clock: null,//时钟控件
    date: '',// 日期数据，用于时钟显示
    time: '',// 日期时间数据，用于时钟显示
    tickCount: 0,// 计时器计数
    stations: [
      { title: '青年渠八排干汇入雨污混排口', code: '120118111000QNQ008D'},
      { title: '青年渠七排干汇入雨污混排口', code: '120118111000QNQ007D', eva: 1},
      { title: '孟家房子七排干稻田沥水排放口', code: '120118111204QPG003F', align: 'center'},
      { title: '团泊东团泊湖庭院（美湖院）七排干雨水排放口', code: '120118111201QPG002C', align: 'right'},
      { title: '团泊东松江芳湖园七排干雨水排放口', code: '120118111201QPG001C', align: 'right'}
    ],
    config: {// 模拟显示滚动窗的数据
      oddRowBGC: 'rgba(0,0,0,0)',
      evenRowBGC: 'rgba(0,0,0,0)',
      columnWidth: [88,340],
      data: [
        ['<span class="lunbo-time">11-19 18:24<span>', '<span class="lunbo-content">水站芳草园采样数据报警：酸碱度3.5</span>'],
        ['<span class="lunbo-time">11-19 18:24<span>', '<span class="lunbo-content">水站泊湖庭院设备离线：自2020-11-19 20:43:24</span>'],
        ['<span class="lunbo-time">11-19 18:24<span>', '<span class="lunbo-content">水站芳草园采样数据报警：酸碱度3.5</span>'],
        ['<span class="lunbo-time">11-19 18:24<span>', '<span class="lunbo-content">水站芳草园采样数据报警：酸碱度3.5</span>'],
        ['<span class="lunbo-time">11-19 18:24<span>', '<span class="lunbo-content">水站泊湖庭院采样数据报警：酸碱度3.5</span>'],
        ['<span class="lunbo-time">11-19 18:24<span>', '<span class="lunbo-content">水站芳草园采样数据报警：酸碱度3.5</span>'],
        ['<span class="lunbo-time">11-19 18:24<span>', '<span class="lunbo-content">水站芳草园采样数据报警：酸碱度3.5</span>'],
        ['<span class="lunbo-time">11-19 18:24<span>', '<span class="lunbo-content">水站七干排稻田采样数据报警：酸碱度3.5</span>'],
        ['<span class="lunbo-time">11-19 18:24<span>', '<span class="lunbo-content">水站芳草园采样数据报警：酸碱度3.5</span>'],
      ]
    }
  },
  created: function() {
    this.clock = new Clock(this)
    this.evaRadar = new Radar(this)
    setInterval(() => {
      if (++this.tickCount == 0xFFFF) this.tickCount = 0
      this.tick()
    }, 500)
  },
  mounted: function() {
    setTimeout(() => {
      this.evaRadar.init()
    })
  },
  methods: {
    tick: function() {
      this.clock.update()
      if( this.tickCount % 20 == 0){
        this.evaRadar.update()
      }
    },
    videoStart:function(index){
      this.videoOn = true
      this.$nextTick(() => {
        console.log(this.$refs.iframe)
        const vconfig = {
          1:{sn:'E86337588',token:'at.7kz7c0ja9h9dnxa2b5zapryja5xn6sas-3ut9fp4wr0-11xbpmj-r1yqnhiyt'},
          2:{sn:'E86337727',token:'at.7kz7c0ja9h9dnxa2b5zapryja5xn6sas-3ut9fp4wr0-11xbpmj-r1yqnhiyt'}
        }[index]
        if(!vconfig || !this.$refs.iframe) return
        const url = 
            '/v/2.html?sn=' +
            vconfig.sn +
            '&token=' +
            vconfig.token +
            '&t=' +
            new Date().getTime()
        this.$refs['iframe'].src = url
      })
    }
  }
})
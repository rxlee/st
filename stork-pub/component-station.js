Vue.component( 'station-block', {
  template: '#station-block',
  props: {
    title: { type: String, default: '...' },
    code: { type: String, default: '...' },
    align: { type: String, defualt: 'left' },
    eva: { type: Number, default: 0 }
  },
  data: function() {
    return {
      realtime: {},
      borderColor: ['#1f93cc','#4fc3df'],
      config: { 
        data: [
          { name: '水温', value: 120 },
          { name: '流速', value: 120 },
          { name: '酸碱', value: 71 }, 
          { name: '溶氧', value: 66 }, 
          { name: '浊度', value: 80 }, 
          { name: 'COD', value: 35 }, 
          { name: '氨氮', value: 17 }, 
          { name: '电导', value: 25 }, 
          { name: '总磷', value: 15 }
        ]
      }//config
    }
  },
  created: function() {
    this.realtime = new SimulateRealtime().getData()
  },
  watch: {
    eva(val) {
    }
  },
  mounted: function() {
    console.log('eva...............', this.eva)
    if(this.eva === null && this.eva === undefined) return;
    if(this.eva === 1) this.borderColor = ['#9f303c','#ff333f']
    new MiniChart(this)
    // axios.get('http://47.99.62.184:88/stork/api/station')
  }
})
vue = new Vue({
  el: '#app',
  data: {
      func: 'todo',
      doneList: [
        {station: '团泊支路排污口', bottle: '2', time: '11-23 15:38'},
        {station: '美湖园排污口', bottle: '5', time: '11-17 09:41'}
      ],
      myprofile: {
        name: '张送样',
        telephone: '15502212345',
        role: '采样送样人员'
      },
      profileMode: 'show'
  },
  created: function() {

  },
  methods: {
  }
})
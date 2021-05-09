Date.prototype.format = function (fmt) { //author: meizz 
  var o = {
      "M+": this.getMonth() + 1, //月份 
      "d+": this.getDate(), //日 
      "h+": this.getHours(), //小时 
      "m+": this.getMinutes(), //分 
      "s+": this.getSeconds(), //秒 
      "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
      "S": this.getMilliseconds() //毫秒 
  };
  if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
  for (var k in o)
  if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
  return fmt;
}
const Role = {
  sampleDelivery: 'sample-delivery',
  sampleTest: 'sample-test',
  riverChief: 'river-chief',
  regionAdministra: 'region-administra',
  regionPatrol: 'region-patrol',
  regionOfficer: 'region-officer',
  manager: 'manager',
  admin: 'admin'
}

var Http = new (function() {
  this.get = function(r) {
    const config = { method:'get', url: r.url }
    if (r.params) config['params'] = r.params
    console.log('config ========= ', config)
    return new Promise(function(resolve, reject) {
      axios(config).then(res => {
        resolve(res.data)
      })
    })
  }
  this.post = function(r) {
    const config = { method:'post', url: r.url }
    if (r.params) config['params'] = r.params
    if (r.data) config['data'] = r.data
    return new Promise(function(resolve, reject) {
      axios(config).then(res => {
        resolve(res.data)
      })
    })
  }
})
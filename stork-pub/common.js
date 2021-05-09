/**
 * 页面引用这个js，就可以读取所有地址参数，所有参数自动装在params对象中，可以通过params.xxxx引用
 */
var params = {}
if (window.location.search && window.location.search.length > 1) {
  var vars = window.location.search.substring(1).split("&")
  for (var i = 0; i < vars.length; i++) {
    var pair = vars[i].split("=")
    params[pair[0]] = pair[1]
  }
}

Vue.prototype.site = {
  title : '天津河道水资源生态监控',
  base: '/stork/api/'
}

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
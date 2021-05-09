# 业务类的总包
biz是business的简写，表示业务逻辑  
主要包括数据监控、监察执法、应急指挥、网站内容管理、系统管理、设备管理等模块  
每个模块分别设置子包，子包内又包含service,controller,dao等下级包  
具体如：  
* 数据监控 monitor
* 应急指挥 urgent
* 监察执法 enforce
* 水站管理 equip
* 网站内容管理 site
* 系统管理 sys

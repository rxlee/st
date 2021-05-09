-- 系统账号，用户
DROP TABLE IF EXISTS tb_account;
CREATE TABLE IF NOT EXISTS tb_account (
 id INT AUTO_INCREMENT PRIMARY KEY,
 name VARCHAR(16) COMMENT '姓名',
 telephone VARCHAR(11) COMMENT '手机号码，也作为登录名',
 password VARCHAR(64) COMMENT '登录密码，加密的',
 salt VARCHAR(8) COMMENT '登录密码加盐，随机字串',
 role VARCHAR(16) DEFAULT 'guest' COMMENT '角色，只支持单角色',
 avatar VARCHAR(256) COMMENT '头像图片文件地址',
 status INT DEFAULT 0 COMMENT '账号状态，0：未激活；1：正常；-1：禁用'
);


-- 水站
DROP TABLE IF EXISTS tb_station;
CREATE TABLE IF NOT EXISTS tb_station (
 id INT AUTO_INCREMENT PRIMARY KEY,
 name VARCHAR(64) COMMENT '水站全称',
 alias VARCHAR(24) COMMENT '水站简称，用户起的便于识别的名称',
 code VARCHAR(48) COMMENT '水站编号',
 memo VARCHAR(64) COMMENT '水站说明，如安装地点等',
 rid INT COMMENT '所属辖区id',
 rname VARCHAR(64) COMMENT '所属辖区名称',
 maintain_time DATETIME COMMENT '维护结束的时间，在此之前处于维护状态，通常是在用户启动维护状态时把这个时间设置为1小时之后',
 lng REAL COMMENT '经度',
 lat REAL COMMENT '纬度',
 sn INT COMMENT '水站设备号，写死在硬件中的',
 vsn VARCHAR(16) COMMENT '视频设备sn',
 vtoken VARCHAR(80) COMMENT '视频设备token'
);

-- 报警阈值
DROP TABLE IF EXISTS tb_threshold;
CREATE TABLE IF NOT EXISTS tb_threshold (
 id INT AUTO_INCREMENT PRIMARY KEY,
 station_id INT COMMENT '水站id',
 indicator VARCHAR(16) COMMENT '参数指标名称',
 bottom REAL COMMENT '最低',
 top REAL COMMENT '最高'
);
INSERT INTO tb_threshold (indicator,bottom,top)VALUES('streamspeed',0,8);
INSERT INTO tb_threshold (indicator,bottom,top)VALUES('temp',-5,40);
INSERT INTO tb_threshold (indicator,bottom,top)VALUES('ph',4,11);
INSERT INTO tb_threshold (indicator,bottom,top)VALUES('phosphorus',0,1);
INSERT INTO tb_threshold (indicator,bottom,top)VALUES('nh3',0,5);
INSERT INTO tb_threshold (indicator,bottom,top)VALUES('oxy',0,20);
INSERT INTO tb_threshold (indicator,bottom,top)VALUES('cod',0,50);
INSERT INTO tb_threshold (indicator,bottom,top)VALUES('cond',0,100);
INSERT INTO tb_threshold (indicator,bottom,top)VALUES('turb',0,800);


-- 报警记录
DROP TABLE IF EXISTS tb_alarm;
CREATE TABLE IF NOT EXISTS tb_alarm (
 id INT AUTO_INCREMENT PRIMARY KEY,
 station_id INT COMMENT '水站id',
 main_type VARCHAR(16) COMMENT '报警类型，目前2种，一种是采样数据超标，值是data，另一种是硬件运行状态异常，值是status',
 sub_type VARCHAR(16) COMMENT '报警子类型，是主类型main_type的附加，如水质采样数据超标，主类型只表明是data，但这个子类型标明具体哪个指标超标，如酸碱度ph',
 summary VARCHAR(256) COMMENT '报警概要，自行组织的一段描述，如：水质指标【酸碱度】于2020-11-11 14:23:42 采样值（3.2）过低',
 alarm_time DATETIME COMMENT '报警时间'
);

-- 实时数据：流速仪
DROP TABLE IF EXISTS tb_realtime_streamspeed;
CREATE TABLE IF NOT EXISTS tb_realtime_streamspeed (
 id INT AUTO_INCREMENT PRIMARY KEY,
 station_id INT COMMENT '水站id，注意不是sn',
 rtime DATETIME COMMENT '采样时间',
 day DATETIME COMMENT '所在天的时间，当天的起点，用于统计',
 month DATETIME COMMENT '所在月的时间，当月的起点，用于统计',
 year DATETIME COMMENT '所在年的时间，当年的起点，用于统计',
 t REAL COMMENT '值：温度',
 d REAL COMMENT '值：水深',
 s REAL COMMENT '值：流速',
 et Integer COMMENT '评价：温度。0正常，1接近限值，2超标，3严重超标。下同',
 ed INT COMMENT '评价：水深',
 es INT COMMENT '评价：流速'
);
-- 实时数据：多参数
DROP TABLE IF EXISTS tb_realtime_general;
CREATE TABLE IF NOT EXISTS tb_realtime_general (
 id INT AUTO_INCREMENT PRIMARY KEY,
 station_id INT COMMENT '水站id，注意不是sn',
 rtime DATETIME COMMENT '采样时间',
 day DATETIME COMMENT '所在天的时间，当天的起点，用于统计',
 month DATETIME COMMENT '所在月的时间，当月的起点，用于统计',
 year DATETIME COMMENT '所在年的时间，当年的起点，用于统计',
 v1 REAL COMMENT '值：电导率',
 v2 REAL COMMENT '值：PH',
 v3 REAL COMMENT '值：溶解氧',
 v4 REAL COMMENT '值：浊度',
 v5 REAL COMMENT '值：CODcr',
 v6 REAL COMMENT '值：氨氮',
 e1 INT COMMENT '评价：电导率。evaluate评价值，0正常，1接近限值，2超标，3严重超标。下同',
 e2 INT COMMENT '评价：PH',
 e3 INT COMMENT '评价：溶解氧',
 e4 INT COMMENT '评价：浊度',
 e5 INT COMMENT '评价：CODcr',
 e6 INT COMMENT '评价：氨氮'
);
-- 实时数据：总磷
DROP TABLE IF EXISTS tb_realtime_phosphorus;
CREATE TABLE IF NOT EXISTS tb_realtime_phosphorus (
 id INT AUTO_INCREMENT PRIMARY KEY,
 station_id INT COMMENT '水站id，注意不是sn',
 rtime DATETIME COMMENT '采样时间',
 day DATETIME COMMENT '所在天的时间，当天的起点，用于统计',
 month DATETIME COMMENT '所在月的时间，当月的起点，用于统计',
 year DATETIME COMMENT '所在年的时间，当年的起点，用于统计',
 val REAL COMMENT '值',
 eva INT COMMENT '评价：总磷。0正常，1接近限值，2超标，3严重超标'
);


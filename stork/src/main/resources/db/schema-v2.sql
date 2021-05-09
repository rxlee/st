-- 检测机构
DROP TABLE IF EXISTS tb_detect_corp;
CREATE TABLE IF NOT EXISTS tb_detect_corp (
 id INT AUTO_INCREMENT PRIMARY KEY,
 name VARCHAR(32) COMMENT '检测机构全称',
 alias VARCHAR(16) COMMENT '检测机构简称',
 address VARCHAR(128) COMMENT '检测机构地址',
 lng REAL COMMENT '检测机构地址定位的经度',
 lat REAL COMMENT '检测机构地址定位的纬度',
 category INT DEFAULT 1 COMMENT '机构类型，1标准；0非标',
 status INT DEFAULT 1 COMMENT '状态。1正常，0不可用'
);

-- 检测人员。这些人员是隶属于检测机构的，但不一定在服务合同里。签订合同时可以从这个表里选择人员进入合同。
DROP TABLE IF EXISTS tb_detect_staff;
CREATE TABLE IF NOT EXISTS tb_detect_staff (
 id INT AUTO_INCREMENT PRIMARY KEY,
 uid INT COMMENT '检测人员账号id',
 uname VARCHAR(16) COMMENT '检测人员姓名',
 uphone VARCHAR(11) COMMENT '检测人员手机号',
 utype INT DEFAULT 1 COMMENT '检测人员类型，1取样送样人员（外业）；2检验员',
 cid INT COMMENT '检测机构id',
 cname VARCHAR(32) COMMENT '检测机构全称',
 calias VARCHAR(16) COMMENT '检测机构简称'
);

-- 辖区
DROP TABLE IF EXISTS tb_region;
CREATE TABLE IF NOT EXISTS tb_region (
 id INT AUTO_INCREMENT PRIMARY KEY,
 name VARCHAR(32) COMMENT '辖区全称',
 alias VARCHAR(16) COMMENT '辖区简称',
 outline VARCHAR(1024) COMMENT '辖区范围，地图边界描点集合'
);
-- 辖区地理范围
DROP TABLE IF EXISTS tb_region_outline;
CREATE TABLE IF NOT EXISTS tb_region_outline (
 id INT AUTO_INCREMENT PRIMARY KEY,
 rid INT COMMENT '辖区id',
 outline VARCHAR(9999) COMMENT '辖区范围，地图边界描点坐标集合'
);

-- 辖区负责人
DROP TABLE IF EXISTS tb_region_staff;
CREATE TABLE IF NOT EXISTS tb_region_staff (
 id INT AUTO_INCREMENT PRIMARY KEY,
 rid INT COMMENT '辖区id',
 uid INT COMMENT '账号id',
 uname VARCHAR(16) COMMENT '姓名',
 uphone VARCHAR(11) COMMENT '电话',
 utype INT COMMENT '人员类型，1河长；2镇长或镇办负责人；3巡查员；4业务文员'
);

-- 检测服务合同
DROP TABLE IF EXISTS tb_detect_contract;
CREATE TABLE IF NOT EXISTS tb_detect_contract(
 id INT AUTO_INCREMENT PRIMARY KEY,
 cid INT COMMENT '机构id',
 cname VARCHAR(32) COMMENT '机构名称',
 calias VARCHAR(16) COMMENT '机构简称',
 rid INT COMMENT '辖区id',
 rname VARCHAR(32) COMMENT '辖区名称',
 ralias VARCHAR(16) COMMENT '辖区简称',
 start_time DATETIME COMMENT '开始时间',
 end_time DATETIME COMMENT '结束时间'
);

-- 检测服务执行人员。这个是通过合同与具体辖区相关联的具体执行人员，这里面的人员一定在tb_detect_staff表里
DROP TABLE IF EXISTS tb_detect_executor;
CREATE TABLE IF NOT EXISTS tb_detect_executor(
 id INT AUTO_INCREMENT PRIMARY KEY,
 cid INT COMMENT '合同id',
 uid INT COMMENT '关联人id',
 uname VARCHAR(16) COMMENT '关联人姓名',
 uphone VARCHAR(11) COMMENT '关联人电话'
);

-- 送检流程
DROP TABLE IF EXISTS tb_detect_process;
CREATE TABLE IF NOT EXISTS tb_detect_process (
 id INT AUTO_INCREMENT PRIMARY KEY,
 code VARCHAR(24) COMMENT '任务编号',
 sample_rid INT COMMENT '辖区id',
 sample_rname VARCHAR(64) COMMENT '辖区名称',
 sample_ralias VARCHAR(32) COMMENT '辖区简称',
 sample_sid INT COMMENT '水站id',
 sample_sname  VARCHAR(64) COMMENT '水站名称',
 sample_salias VARCHAR(32) COMMENT '水站简称',
 sample_time DATETIME COMMENT '采样时间',
 sample_bottle INT COMMENT '样瓶编号',
 
 dispatch_uid INT COMMENT '送检任务派发人id',
 dispatch_uname VARCHAR(16) COMMENT '送检任务派发人姓名',
 dispatch_time DATETIME COMMENT '送检任务派发时间',
 
 delivery_uid INT COMMENT '送检人id',
 delivery_uname VARCHAR(16) COMMENT '送检人姓名',
 delivery_time DATETIME COMMENT '实际取样（开始送样）时间',
 delivery_arrived_time DATETIME COMMENT '样品送达时间，送样人员送达时由系统记录',
 
 charge_cid INT COMMENT '检测机构id',
 charge_cname VARCHAR(32) COMMENT '检测机构全称',
 charge_calias VARCHAR(16) COMMENT '检测机构简称',
 charge_uid INT COMMENT '检测人id。检测人由送样人员在送达时选择',
 charge_uname VARCHAR(16) COMMENT '检测人姓名',
 charge_accept_time DATETIME COMMENT '样品接收时间。检测人员实际收到样品时点击确认，由系统记录该时间',
 charge_complete_time DATETIME COMMENT '检测完成时间',
 
 chargex_cid INT COMMENT '非标检测机构id',
 chargex_cname VARCHAR(32) COMMENT '非标检测机构全称',
 chargex_calias VARCHAR(16) COMMENT '非标检测机构简称',
 chargex_uid INT COMMENT '非标检测人id',
 chargex_uname VARCHAR(16) COMMENT '非标检测人姓名',
 chargex_accept_time DATETIME COMMENT '非标样品接收时间',
 chargex_complete_time DATETIME COMMENT '非标检测完成时间',
 
 step INT COMMENT '进度。0派发待取样；10已取样送样中；20已送达检测中；30检测完成'
);

-- 排放和自动采样记录
DROP TABLE IF EXISTS tb_sample_record;
CREATE TABLE IF NOT EXISTS tb_sample_record (
 id INT AUTO_INCREMENT PRIMARY KEY,
 stime DATETIME COMMENT '采样时间',
 rid INT COMMENT '区域',
 rname VARCHAR(32),
 ralias VARCHAR(16),
 sid INT COMMENT '水站',
 sname VARCHAR(32),
 salias VARCHAR(16),
 bottle INT COMMENT '采样瓶编号',
 detect_id INT COMMENT '采样任务编号，关联tb_detect_process，没有下发任务时为null或空白',
 -- 以下为新增 2020.12.11
 scode VARCHAR(48) COMMENT '水站的编码，从tb_station带过来，生成报告时需要用到',
 discharge_start DATETIME COMMENT '排放开始时间',
 discharge_end DATETIME COMMENT '排放结束时间',
 photo VARCHAR(48) COMMENT '排放照片'
);

-- 巡查情况
DROP TABLE IF EXISTS tb_inspection;
CREATE TABLE IF NOT EXISTS tb_inspection (
 id INT AUTO_INCREMENT PRIMARY KEY,
 utime DATETIME COMMENT '上报时间',
 uid INT COMMENT '上报人id',
 umemo VARCHAR(16) COMMENT '上报问题描述',
 atime DATETIME COMMENT '审批时间',
 aid INT COMMENT '审批人id',
 amemo VARCHAR(16) COMMENT '审批说明',
 rid INT COMMENT '辖区id',
 result VARCHAR(16) COMMENT '处理结果',
 step int COMMENT '处理进度',
 lng REAL COMMENT '经度',
 lat REAL COMMENT '纬度'
);

-- 检测报告
DROP TABLE IF EXISTS tb_detect_report;
CREATE TABLE IF NOT EXISTS tb_detect_report (
 id INT AUTO_INCREMENT PRIMARY KEY,
 process_id INT COMMENT '送检流程id',
 ptime DATETIME COMMENT '提交时间',
 pname VARCHAR(16) COMMENT '检测人姓名',
 pid INT COMMENT '检测人id',
 rid INT COMMENT '辖区id',
 sid INT COMMENT '水站id',
 stime DATETIME COMMENT '采样时间',
 charge_content VARCHAR(1024) COMMENT '标准内容',
 chargex_content VARCHAR(1024) COMMENT '非标准内容',
 v1 INT COMMENT '总排放量，生成报告的时候，根据排污采样记录中的起止时间，到实时数据表中去积分计算',
 v2 INT COMMENT '氨氮排放量，类似于总排放量',
 v3 INT COMMENT 'CODcr排放量，类似于总排放量'
 v4 INT COMMENT '总磷排放量，类似于总排放量',
);
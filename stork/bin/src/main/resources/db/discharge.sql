-- 流速数据
-- 这个不是呈现到界面的实时流速数据，而是监控排放的流速数据，比界面上的数据更密集，而且会定期删除（超过1个月）
-- 暂定每1分钟一次数据
DROP TABLE IF EXISTS tb_streamspeed;
CREATE TABLE IF NOT EXISTS tb_streamspeed (
 id INT AUTO_INCREMENT PRIMARY KEY,
 sn INT COMMENT '水站sn',
 stime DATETIME COMMENT '数据采集时间',
 s FLOAT '流速值',
 d FLOAT '水深值',
);

-- 流速统计
-- 帮助判断排放的，定时根据tb_streamspeed的数据做统计，统计结果记录在这里，统计周期比采样周期短，暂定10分钟一次
-- 主要统计一定时长内（如1个月）的流速最大最小值，然后可以用最大最小的中位值(max-min)/2 + min作为判断突发排污的阈值，超过这个中位值认为在排污
DROP TABLE IF EXISTS tb_streamspeed;
CREATE TABLE IF NOT EXISTS tb_streamspeed (
 id INT AUTO_INCREMENT PRIMARY KEY,
 sn INT COMMENT '水站sn',
 vmax FLOAT '统计期间的最大值',
 vmin FLOAT '统计期间的最小值',
);

-- 排放记录
-- 每个站会记录最近2次流速，加上最新一次测量值，如果3个值持续升高
DROP TABLE IF EXISTS tb_discharge;
CREATE TABLE IF NOT EXISTS tb_ (
 id INT AUTO_INCREMENT PRIMARY KEY,
 sn INT COMMENT '水站sn',
 start_time DATETIME COMMENT '排放开始时间',
 end_time DATETIME COMMENT '排放结束时间',
 base_val FLOAT COMMENT '基准值。每个站会记录最近2次流速，未',
 doubt_val FLOAT COMMENT '疑似值',
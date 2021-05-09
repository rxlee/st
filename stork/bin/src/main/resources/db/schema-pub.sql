-- 公开发布的信息（文章）
DROP TABLE IF EXISTS tb_article;
CREATE TABLE IF NOT EXISTS tb_article (
 id INT AUTO_INCREMENT PRIMARY KEY,
 title VARCHAR(64) COMMENT '标题',
 content VARCHAR(2048) COMMENT '正文',
 category VARCHAR(24) COMMENT '类别',
 publish_time TIMESTAMP COMMENT '发布时间',
 publisher VARCHAR(32) COMMENT '作者',
 page_view INT COMMENT '阅读数',
 attach_image VARCHAR(128) COMMENT '配图地址',
 attach_name VARCHAR(128) COMMENT '附件显示名称',
 attach_url VARCHAR(128) COMMENT '附件下载地址'
);

-- 政民互动
DROP TABLE IF EXISTS tb_conversition;
CREATE TABLE IF NOT EXISTS tb_conversition (
 id INT AUTO_INCREMENT PRIMARY KEY,
 title VARCHAR(64) COMMENT '标题',
 content VARCHAR(512) COMMENT '正文',
 category VARCHAR(24) COMMENT '类别',
 post_time TIMESTAMP COMMENT '提交时间',
 poster VARCHAR(32) COMMENT '作者',
 reply_content VARCHAR(512) COMMENT '反馈内容',
 reply_time TIMESTAMP COMMENT '反馈时间'
);
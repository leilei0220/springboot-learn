-- 文件元数据表
CREATE TABLE IF NOT EXISTS `file_metadata` (
  `file_key` VARCHAR(64) NOT NULL COMMENT '文件唯一Key（主键）',
  `file_name` VARCHAR(255) NOT NULL COMMENT '原始文件名',
  `md5` VARCHAR(32) NOT NULL COMMENT '文件MD5值',
  `file_size` BIGINT NOT NULL COMMENT '文件大小（字节）',
  `temp_path` VARCHAR(500) NOT NULL COMMENT '文件在存储中的路径（临时目录）',
  `business_path` VARCHAR(500) DEFAULT NULL COMMENT '文件在存储中的路径（业务目录，移动后更新）',
  `storage_type` VARCHAR(20) NOT NULL COMMENT '存储类型：minio 或 oss',
  `status` VARCHAR(20) NOT NULL DEFAULT 'UPLOADING' COMMENT '文件状态：UPLOADING-上传中, UPLOADED-已上传, MOVED-已移动到业务目录',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`file_key`),
  INDEX `idx_md5` (`md5`),
  INDEX `idx_status` (`status`),
  INDEX `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件元数据表';


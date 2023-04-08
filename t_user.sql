CREATE TABLE `t_user` (
  `id` VARCHAR(64) NOT NULL  COMMENT '用户id',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` char(32) NOT NULL COMMENT '密码',
  `salt` char(36) DEFAULT NULL COMMENT '盐值',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话号码',
  `email` varchar(30) DEFAULT NULL COMMENT '电子邮箱',
  `gender` int DEFAULT NULL COMMENT '性别:0-女，1-男',
  `avatar` varchar(50) DEFAULT NULL COMMENT '头像',
  `is_deleted` int DEFAULT NULL COMMENT '是否删除：0-未删除，1-已删除',
  `created_date` datetime DEFAULT NULL COMMENT '日志-创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '日志-最后修改时间',
  `expire_time` bigint DEFAULT NULL COMMENT 'token过期时间',
  `LoginTime` bigint DEFAULT NULL COMMENT 'token开始时间',` bigint DEFAULT NULL COMMENT 'token开始时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
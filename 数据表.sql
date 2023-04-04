
产品表

CREATE TABLE `product_info` (
  `id` VARCHAR(64) NOT NULL COMMENT '产品id',
  `name` VARCHAR(255) NOT NULL COMMENT '产品名字',
  `description` TEXT NOT NULL COMMENT '描述',
  `cid` VARCHAR(64) NOT NULL COMMENT '分类id',
  `status` INT NOT NULL COMMENT '产品状态',
  `created_date` DATE NOT NULL COMMENT '创建时间',
  `update_date` DATE NOT NULL COMMENT '更新日期',
  `is_deleted` INT NOT NULL COMMENT '是否删除',
  `sort` INT NOT NULL COMMENT '用于轮播图的排序',
  `pub_code` VARCHAR(64) NOT NULL COMMENT '产品单号',
  `weight` INT NOT NULL COMMENT '权重',
  `cover_url` varchar(100) DEFAULT NULL COMMENT '图片地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

产品分类表
CREATE TABLE `product_category` (
  `id` VARCHAR(64) NOT NULL COMMENT '产品分类id',
  `name` VARCHAR(255) NOT NULL COMMENT '产品分类名字',
  `created_date` DATE NOT NULL COMMENT '创建时间',
  `update_date` DATE NOT NULL COMMENT '更新日期',
  `is_deleted` INT NOT NULL COMMENT '是否删除',
  `workflow_id` VARCHAR(64) NOT NULL COMMENT '分类对应审批流程id',
  `workflow_formula` TEXT NOT NULL COMMENT '审批流程公式',
  `description` TEXT NOT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

产品发布表
CREATE TABLE `publish_approve` (
  `id` VARCHAR(64) NOT NULL COMMENT 'id',
  `created_date` DATE NOT NULL COMMENT '创建时间',
  `update_date` DATE NOT NULL COMMENT '更新日期',
  `is_deleted` INT NOT NULL COMMENT '是否删除',
  `pub_code` VARCHAR(64) NOT NULL COMMENT '产品单号',
  `product_id` VARCHAR(64) NOT NULL COMMENT '产品id',
  `approve_status` INT NOT NULL COMMENT '审批状态',
  `approver` VARCHAR(64) NOT NULL COMMENT '审批人',
  `product_name` VARCHAR(100) NOT NULL COMMENT '产品名字',
  `cid` VARCHAR(32) NOT NULL COMMENT '分类id',
  `approve_time`DATE NOT NULL COMMENT '审批时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


订阅产品表
CREATE TABLE `subscribe_approve` (
  `id` VARCHAR(64) NOT NULL COMMENT 'id',
  `created_date` DATE NOT NULL COMMENT '创建时间',
  `update_date` DATE NOT NULL COMMENT '更新日期',
  `is_deleted` INT NOT NULL COMMENT '是否删除',
  `sub_code` VARCHAR(64) NOT NULL COMMENT '订阅单号',
  `product_id` VARCHAR(64) NOT NULL COMMENT '产品id',
  `approve_status` INT NOT NULL COMMENT '审批状态',
  `approver` VARCHAR(64) NOT NULL COMMENT '审批人',
  `product_name` VARCHAR(100) NOT NULL COMMENT '产品名字',
  `cid` VARCHAR(32) NOT NULL COMMENT '分类id',
  `approve_time`DATE NOT NULL COMMENT '审批时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


订阅审批表
CREATE TABLE `workflow_subscribe_approve` (
  `id` VARCHAR(64) NOT NULL COMMENT 'id',
  `created_date` DATE NOT NULL COMMENT '创建时间',
  `update_date` DATE NOT NULL COMMENT '更新日期',
  `is_deleted` INT NOT NULL COMMENT '是否删除',
  `sub_code` VARCHAR(64) NOT NULL COMMENT '订阅单号',
  `node_approval_status` INT NOT NULL COMMENT '节点审批状态',
  `node_approver` VARCHAR(64) NOT NULL COMMENT '节点审批人',
  `cid` VARCHAR(32) NOT NULL COMMENT '分类id',
  `user_list` VARCHAR(64) NOT NULL COMMENT 'node节点审批人',
  `workflow_id` VARCHAR(64) NOT NULL COMMENT '审批流id',
  `node_id` VARCHAR(64) NOT NULL COMMENT '审批流节点',
  `approve_time`DATE NOT NULL COMMENT '审批时间'
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

发布审批表
CREATE TABLE `workflow_publish_approve` (
  `id` VARCHAR(64) NOT NULL COMMENT '产品分类id',
  `created_date` DATE NOT NULL COMMENT '创建时间',
  `update_date` DATE NOT NULL COMMENT '更新日期',
  `is_deleted` INT NOT NULL COMMENT '是否删除',
  `pub_code` VARCHAR(64) NOT NULL COMMENT '发布单号',
  `node_approve_status` INT NOT NULL COMMENT '节点审批状态',
  `node_approver` VARCHAR(64) NOT NULL COMMENT '节点审批人',
  `cid` VARCHAR(32) NOT NULL COMMENT '分类id',
  `user_list` VARCHAR(64) NOT NULL COMMENT 'node节点审批人',
  `workflow_id` VARCHAR(64) NOT NULL COMMENT '审批流id',
  `node_id` VARCHAR(64) NOT NULL COMMENT '审批流节点',
  `approve_time`DATE NOT NULL COMMENT '审批时间'
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


图片的修改表
CREATE TABLE `product_cover` (
  `id` VARCHAR(64) NOT NULL COMMENT '产品分类id',
  `created_date` DATE NOT NULL COMMENT '创建时间',
  `update_date` DATE NOT NULL COMMENT '更新日期',
  `is_deleted` INT NOT NULL COMMENT '是否删除',
  `name` varchar(100)  NULL COMMENT '图片名字',
  `address` VARCHAR(100) NOT NULL COMMENT 'url地址',
  `status` INT NOT NULL COMMENT '图片转态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;








https://blog.csdn.net/sjhuangx/article/details/100548057

https://juejin.cn/post/7018521754125467661





     

     




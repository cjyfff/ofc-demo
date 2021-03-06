create database ofc_demo default charset utf8 COLLATE utf8_general_ci;

DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_id` char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单id',
  `handle_status` int(12) NULL DEFAULT NULL COMMENT '处理状态',
  `status` int(12) NULL DEFAULT NULL COMMENT '订单状态',
  `create_at` timestamp(6) NULL DEFAULT NULL COMMENT '创建时间',
  `update_at` timestamp(6) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE `index_order_id`(`order_id`) USING BTREE,
  INDEX `index_status`(`status`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;


DROP TABLE IF EXISTS `order_status_exc_log`;
CREATE TABLE `order_status_exc_log` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`order_id` CHAR(24) NULL DEFAULT NULL COMMENT '订单id',
	`status` INT(12) NULL DEFAULT NULL COMMENT '订单状态',
	`status_exc_time` INT(12) NULL DEFAULT NULL COMMENT '订单状态处理次数',
	`create_at` TIMESTAMP(6) NULL DEFAULT NULL COMMENT '创建时间',
	PRIMARY KEY (`id`),
	INDEX `index_order_id_status` (`order_id`, `status`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;


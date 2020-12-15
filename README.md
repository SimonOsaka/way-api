#way项目的接口api
> 提供所有相关项目接口  

1. iOS/Android/H5移动端接口
项目包结构无前缀
2. SP平台接口
项目包结构包含/sp/
3. MP平台接口
项目包结构包含/mp/ 

> 系统环境变量
```shell script
# API_TOKEN
export API_TOKEN_SECRET='45784C6585D6EDDF'

# MYSQL
export ONTHEWAY_MYSQL_USERNAME='root'
export ONTHEWAY_MYSQL_PASSWORD='123456'
export ONTHEWAY_MYSQL_URL='jdbc:mysql://db.dev.jicu.vip:3306/on_the_way?useUnicode=true&characterEncoding=utf-8&useSSL=false&zeroDateTimeBehavior=convertToNull&serverTimezone=GMT%2B8'
```

> mysql数据库结构

```mysql
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sp_user_shop
-- ----------------------------
DROP TABLE IF EXISTS `sp_user_shop`;
CREATE TABLE `sp_user_shop` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_login_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '用户登录id',
  `shop_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '商家id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除。0：未删除，1：已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=31098 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for user_address
-- ----------------------------
DROP TABLE IF EXISTS `user_address`;
CREATE TABLE `user_address` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_login_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '用户id',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '地址名称',
  `longitude` decimal(10,6) NOT NULL COMMENT '地址经度',
  `latitude` decimal(10,6) NOT NULL COMMENT '地址纬度',
  `tag` varchar(7) NOT NULL DEFAULT '' COMMENT '地址标签（空,home,company,school）',
  `is_deleted` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除（0：否，1：是）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户地址表';

-- ----------------------------
-- Table structure for user_device
-- ----------------------------
DROP TABLE IF EXISTS `user_device`;
CREATE TABLE `user_device` (
  `user_login_id` bigint(20) unsigned NOT NULL COMMENT 'user_login的id',
  `device_token` varchar(255) NOT NULL DEFAULT '' COMMENT 'ios的deviceToken',
  `jpush_reg_id` varchar(255) NOT NULL DEFAULT '' COMMENT 'jpush的RegistrationID',
  `latitude` decimal(10,6) NOT NULL COMMENT '设备的纬度',
  `longitude` decimal(10,6) NOT NULL COMMENT '设备的经度',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`user_login_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for user_feedback
-- ----------------------------
DROP TABLE IF EXISTS `user_feedback`;
CREATE TABLE `user_feedback` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `feedback_os_type` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '系统类型0：未知，1：android，2：ios',
  `feedback_type` tinyint(4) NOT NULL COMMENT '反馈类型0：未知，1：商家反馈，2：商品反馈，3：优惠反馈，4：账号反馈，99：投诉建议',
  `feedback_app_version` varchar(10) NOT NULL DEFAULT '' COMMENT 'app版本',
  `feedback_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '反馈时间',
  `user_login_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '登录用户id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `feedback_content` varchar(100) NOT NULL DEFAULT '' COMMENT '反馈建议内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='反馈和建议表';

-- ----------------------------
-- Table structure for user_login
-- ----------------------------
DROP TABLE IF EXISTS `user_login`;
CREATE TABLE `user_login` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `login_tel` varchar(11) NOT NULL DEFAULT '' COMMENT '登录手机号',
  `valid_code` varchar(6) NOT NULL DEFAULT '' COMMENT '验证码',
  `valid_code_expire` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '验证码过期时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_used` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '是否可用。0：可用，1：禁用',
  `is_deleted` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除。0：未删除，1：已删除',
  `login_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '登录时间',
  `logout_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '退出登录时间',
  `login_name` varchar(16) NOT NULL DEFAULT '' COMMENT '用户名',
  `login_password` varchar(32) NOT NULL DEFAULT '' COMMENT '用户密码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for user_profile
-- ----------------------------
DROP TABLE IF EXISTS `user_profile`;
CREATE TABLE `user_profile` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_login_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '用户登录id',
  `user_nick_name` varchar(15) NOT NULL DEFAULT '' COMMENT '用户昵称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `address_name` varchar(32) NOT NULL DEFAULT '' COMMENT '地址名称',
  `address_longitude` decimal(10,6) NOT NULL COMMENT '地址经度',
  `address_latitude` decimal(10,6) NOT NULL COMMENT '地址纬度',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for way_article_post
-- ----------------------------
DROP TABLE IF EXISTS `way_article_post`;
CREATE TABLE `way_article_post` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `commodity_id` bigint(20) unsigned NOT NULL COMMENT '商品id',
  `subject` varchar(20) NOT NULL DEFAULT '' COMMENT '标题',
  `post_content_id` bigint(20) unsigned NOT NULL COMMENT '文章内容id',
  `audit_action` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '动作。0：无，1：提交，2：通过，3：驳回',
  `is_deleted` tinyint(4) unsigned NOT NULL DEFAULT '3' COMMENT '是否删除。0：正常，1：删除，2：审核中，3：编辑中（驳回）',
  `published_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '发布时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='商家文章主体表';

-- ----------------------------
-- Table structure for way_article_post_content
-- ----------------------------
DROP TABLE IF EXISTS `way_article_post_content`;
CREATE TABLE `way_article_post_content` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `content` varchar(800) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='商家文章内容表';

-- ----------------------------
-- Table structure for way_article_post_log
-- ----------------------------
DROP TABLE IF EXISTS `way_article_post_log`;
CREATE TABLE `way_article_post_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `article_post_id` bigint(20) unsigned NOT NULL COMMENT '文章主体id',
  `event_type` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '事件类型。1：创建，2：更新，3：删除，4：提交，5：审核通过，6：审核驳回（内容）',
  `event_content` varchar(20) NOT NULL DEFAULT '' COMMENT '事件内容',
  `event_source` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '事件来源。0：SP，1：MP',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for way_city
-- ----------------------------
DROP TABLE IF EXISTS `way_city`;
CREATE TABLE `way_city` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL DEFAULT '',
  `adcode` varchar(6) NOT NULL DEFAULT '',
  `citycode` varchar(4) NOT NULL DEFAULT '',
  `pinyin_short` varchar(20) NOT NULL DEFAULT '' COMMENT '拼音首字母缩写',
  `pinyin_all` varchar(100) NOT NULL DEFAULT '' COMMENT '拼音全部字母',
  `is_used` tinyint(4) unsigned NOT NULL DEFAULT '0',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `id_UNIQUE` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3545 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='城市、县';

-- ----------------------------
-- Table structure for way_commodity
-- ----------------------------
DROP TABLE IF EXISTS `way_commodity`;
CREATE TABLE `way_commodity` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '商品名称',
  `price` decimal(10,1) unsigned NOT NULL COMMENT '商品价格',
  `img_url` varchar(300) NOT NULL DEFAULT '' COMMENT '商品图片链接',
  `img_url_1` varchar(300) NOT NULL DEFAULT '' COMMENT '商品图片链接1',
  `img_url_2` varchar(300) NOT NULL DEFAULT '' COMMENT '商品图片链接2',
  `img_url_3` varchar(300) NOT NULL DEFAULT '' COMMENT '商品图片链接3',
  `img_url_4` varchar(300) NOT NULL DEFAULT '' COMMENT '商品图片链接4',
  `shop_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '商铺主键id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除。0：未删除，1：已删除',
  `name_pinyin` varchar(500) NOT NULL DEFAULT '' COMMENT '商品名称完整拼音',
  `name_py` varchar(100) NOT NULL DEFAULT '' COMMENT '商品名称缩写拼音',
  `abstract_word_ids` json NOT NULL COMMENT '抽象词id的json数组',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `id_UNIQUE` (`id`) USING BTREE,
  KEY `idx_status` (`is_deleted`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12026135 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='商品信息';

-- ----------------------------
-- Table structure for way_commodity_abstract_word
-- ----------------------------
DROP TABLE IF EXISTS `way_commodity_abstract_word`;
CREATE TABLE `way_commodity_abstract_word` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(10) DEFAULT '' COMMENT '抽象商品名称',
  `shop_cate_leaf_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '商家分类叶子id',
  `json_data` json NOT NULL COMMENT '当前节点的所有json数据',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除。（0：否，1：是）',
  `leaf` tinyint(4) unsigned GENERATED ALWAYS AS (json_extract(`json_data`,'$.leaf')) VIRTUAL NOT NULL COMMENT '是否叶子节点。（0：否，1：是）',
  `pid` int(10) unsigned GENERATED ALWAYS AS (ifnull(json_extract(json_extract(`json_data`,'$.path'),concat('$[',(json_length(json_extract(`json_data`,'$.path[*]')) - 1),']')),0)) VIRTUAL NOT NULL COMMENT '父节点id',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_leaf` (`leaf`) USING BTREE COMMENT '是否叶子节点索引'
) ENGINE=InnoDB AUTO_INCREMENT=477 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='商品抽象词表';

-- ----------------------------
-- Table structure for way_commodity_abstract_word_relationship
-- ----------------------------
DROP TABLE IF EXISTS `way_commodity_abstract_word_relationship`;
CREATE TABLE `way_commodity_abstract_word_relationship` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `abstract_word_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '商品抽象词主键id',
  `abstract_word_ids` json NOT NULL COMMENT '关系抽象词json数组',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除。（0：正常，1：删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='商品抽象词网络关系表';

-- ----------------------------
-- Table structure for way_commodity_log
-- ----------------------------
DROP TABLE IF EXISTS `way_commodity_log`;
CREATE TABLE `way_commodity_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `commodity_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '商品id，对应way_commodity的id',
  `content` varchar(100) NOT NULL DEFAULT '' COMMENT '日志内容',
  `type` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '日志类型',
  `source` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '日志来源',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=27469 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for way_discount
-- ----------------------------
DROP TABLE IF EXISTS `way_discount`;
CREATE TABLE `way_discount` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `commodity_name` varchar(100) NOT NULL DEFAULT '' COMMENT '商品名称',
  `commodity_cate` varchar(10) NOT NULL DEFAULT '' COMMENT '商品分类',
  `commodity_price` decimal(8,2) unsigned NOT NULL COMMENT '商品价格',
  `shop_position` varchar(100) NOT NULL DEFAULT '' COMMENT '商家位置',
  `shop_lng` decimal(10,6) NOT NULL COMMENT '商家经度',
  `shop_lat` decimal(10,6) NOT NULL COMMENT '商家经度',
  `user_login_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '用户登录id',
  `commodity_real` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '真实数量（用户点击）',
  `commodity_unreal` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '不真实数量（用户点击）',
  `limit_time_expire` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '优惠限时过期',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除。0：未删除，1：已删除，2：未知',
  `ad_code` varchar(6) NOT NULL DEFAULT '' COMMENT '区域编码',
  `city_code` varchar(4) NOT NULL DEFAULT '' COMMENT '城市编码',
  `commodity_approve` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '审核通过。0：通过，1：不通过，2：未审核',
  `commodity_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT 'way_commodity的主键id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for way_discount_jpush
-- ----------------------------
DROP TABLE IF EXISTS `way_discount_jpush`;
CREATE TABLE `way_discount_jpush` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `discount_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '优惠id',
  `has_pushed` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '是否已经推送。（0：否，1：是）',
  `pushed_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '推送时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_has_pushed` (`has_pushed`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='优惠信息是否推送表';

-- ----------------------------
-- Table structure for way_discount_real
-- ----------------------------
DROP TABLE IF EXISTS `way_discount_real`;
CREATE TABLE `way_discount_real` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_login_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '用户登录id',
  `discount_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '优惠id',
  `real_type` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '真实类型。0：真实，1：不真实',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除。0：未删除，1：已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for way_land
-- ----------------------------
DROP TABLE IF EXISTS `way_land`;
CREATE TABLE `way_land` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `prop_key` varchar(32) NOT NULL DEFAULT '',
  `prop_val` varchar(256) NOT NULL DEFAULT '',
  `is_deleted` tinyint(4) unsigned DEFAULT '0',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for way_shop
-- ----------------------------
DROP TABLE IF EXISTS `way_shop`;
CREATE TABLE `way_shop` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `shop_name` varchar(30) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '商家名称',
  `shop_address` varchar(200) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '商家地址',
  `shop_tel` varchar(15) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '商家电话',
  `shop_business_time1` varchar(11) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '商家营业时间1',
  `shop_business_time2` varchar(11) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '商家营业时间2',
  `shop_cate_leaf_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '商家分类末级id',
  `shop_info` varchar(100) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '商家信息',
  `shop_lat` decimal(10,6) NOT NULL COMMENT '商铺纬度',
  `shop_lng` decimal(10,6) NOT NULL COMMENT '商家经度',
  `shop_logo_url` varchar(300) NOT NULL DEFAULT '' COMMENT '商家logo',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除',
  `ad_code` varchar(6) NOT NULL DEFAULT '' COMMENT '区域编码',
  `city_code` varchar(4) NOT NULL DEFAULT '' COMMENT '城市编码',
  `shop_pinyin` varchar(150) NOT NULL DEFAULT '' COMMENT '商家名称拼音全写',
  `shop_py` varchar(30) NOT NULL DEFAULT '' COMMENT '商家名称拼音缩写',
  `shop_head_tel` varchar(13) NOT NULL DEFAULT '' COMMENT '商家负责人手机号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12026135 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='店铺信息';

-- ----------------------------
-- Table structure for way_shop_cate_leaf
-- ----------------------------
DROP TABLE IF EXISTS `way_shop_cate_leaf`;
CREATE TABLE `way_shop_cate_leaf` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `shop_cate_root_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '商家分类根id',
  `cate_name` varchar(10) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '商家分类名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for way_shop_cate_root
-- ----------------------------
DROP TABLE IF EXISTS `way_shop_cate_root`;
CREATE TABLE `way_shop_cate_root` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `cate_name` varchar(10) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '商家分类名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for way_shop_extra
-- ----------------------------
DROP TABLE IF EXISTS `way_shop_extra`;
CREATE TABLE `way_shop_extra` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `shop_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '商家id，表way_shop',
  `owner_type` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '商家是自行创建，还是管理人员创建。0：未知，1：商家自行创建，2：管理人员创建',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=32776 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='商家额外配置';

-- ----------------------------
-- Table structure for way_shop_follow
-- ----------------------------
DROP TABLE IF EXISTS `way_shop_follow`;
CREATE TABLE `way_shop_follow` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `shop_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '商家id',
  `user_login_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '用户登录id',
  `has_followed` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '已关注。0：关注，1：未关注',
  `is_deleted` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除。0：正常，1：删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='商家关注表';

-- ----------------------------
-- Table structure for way_shop_log
-- ----------------------------
DROP TABLE IF EXISTS `way_shop_log`;
CREATE TABLE `way_shop_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `shop_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '商家id，对应way_shop的id',
  `content` varchar(100) NOT NULL DEFAULT '' COMMENT '日志内容',
  `type` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '日志类型',
  `source` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '日志来源',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=27490 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for way_shop_qualification
-- ----------------------------
DROP TABLE IF EXISTS `way_shop_qualification`;
CREATE TABLE `way_shop_qualification` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `shop_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '商家id',
  `idcard_front_img_url` varchar(255) NOT NULL DEFAULT '' COMMENT '身份证正面url',
  `idcard_back_img_url` varchar(255) NOT NULL DEFAULT '' COMMENT '身份证背面url',
  `idcard_hand_img_url` varchar(255) NOT NULL DEFAULT '' COMMENT '手持身份证url',
  `business_license_img_url` varchar(255) NOT NULL DEFAULT '' COMMENT '营业执照url',
  `shop_outside_img_url` varchar(255) NOT NULL DEFAULT '' COMMENT '门店门脸url',
  `shop_inside_img_url` varchar(255) NOT NULL DEFAULT '' COMMENT '门店内部url',
  `other1_img_url` varchar(255) NOT NULL DEFAULT '' COMMENT '其它资料1url',
  `other2_img_url` varchar(255) NOT NULL DEFAULT '' COMMENT '其它资料2url',
  `other3_img_url` varchar(255) NOT NULL DEFAULT '' COMMENT '其它资料3url',
  `other4_img_url` varchar(255) NOT NULL DEFAULT '' COMMENT '其它资料4url',
  `other5_img_url` varchar(255) NOT NULL DEFAULT '' COMMENT '其它资料5url',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=32777 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
```


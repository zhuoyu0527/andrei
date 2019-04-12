/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 80011
Source Host           : localhost:3306
Source Database       : andrei

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2019-04-12 10:41:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_resource`
-- ----------------------------
DROP TABLE IF EXISTS `t_resource`;
CREATE TABLE `t_resource` (
  `resource_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `resource_name` varchar(255) NOT NULL COMMENT '资源名称',
  `resource_code` varchar(255) DEFAULT NULL COMMENT '资源编码',
  `url` varchar(255) DEFAULT NULL COMMENT '资源访问路径',
  `method_name` varchar(255) DEFAULT NULL COMMENT '方法名称',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `resource_type` int(2) DEFAULT '1' COMMENT '资源类型, 1菜单, 2按钮',
  `parent_id` int(10) DEFAULT NULL COMMENT '父级资源id',
  `show_order` int(10) DEFAULT '0' COMMENT '顺序',
  `key_value` varchar(255) DEFAULT NULL COMMENT '值',
  `level` int(10) DEFAULT NULL COMMENT '层级',
  `creator` int(10) DEFAULT NULL COMMENT '建立人id',
  `create_time` int(10) DEFAULT NULL COMMENT '建立时间',
  `updator` int(10) DEFAULT NULL COMMENT '更新人id',
  `update_time` int(10) DEFAULT NULL COMMENT '更新时间',
  `disabled` int(2) DEFAULT '0' COMMENT '是否有效, 0:有效, 1:无效',
  `status` int(2) DEFAULT '0' COMMENT '状态, 0:正常,1:停用',
  `resource_desc` varchar(255) DEFAULT NULL COMMENT '资源描述',
  `is_show` int(2) DEFAULT '0' COMMENT '是否显示',
  PRIMARY KEY (`resource_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1027 DEFAULT CHARSET=utf8 COMMENT='资源表';

-- ----------------------------
-- Records of t_resource
-- ----------------------------
INSERT INTO `t_resource` VALUES ('1017', '系统设置', 'sysuser', null, null, '&#xe630;', '1', '0', '19', null, '1', null, null, null, null, '0', '0', null, '0');
INSERT INTO `t_resource` VALUES ('1018', '系统用户', 'sysuser', 'sysuser/list', null, 'icon-text', '1', '1017', '20', null, '2', null, null, null, null, '0', '0', null, '0');
INSERT INTO `t_resource` VALUES ('1019', '角色管理', 'sysuser', 'role/list', null, 'icon-text', '1', '1017', '21', null, '2', null, null, null, null, '0', '0', null, '0');

-- ----------------------------
-- Table structure for `t_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `role_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_name` varchar(255) NOT NULL COMMENT '角色名称',
  `role_code` varchar(255) DEFAULT NULL COMMENT '角色编码',
  `role_desc` varchar(255) DEFAULT NULL COMMENT '角色描述',
  `creator` int(10) DEFAULT NULL COMMENT '建立人id',
  `create_time` int(10) DEFAULT NULL COMMENT '建立日期',
  `updator` int(10) DEFAULT NULL COMMENT '更新人id',
  `update_time` int(10) DEFAULT NULL COMMENT '更新日期',
  `disabled` int(2) DEFAULT NULL COMMENT '是否有效,0:有效, 1:无效',
  `status` int(2) DEFAULT NULL COMMENT '状态, 0:正常, 1:停用',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8 COMMENT='系统角色表';

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('1', '超级管理员', 'ROLE_ADMIN', '超级管理员最大的角色', '1', '1531965250', '1', '1533191475', '0', '0');
INSERT INTO `t_role` VALUES ('42', '测试角色', 'ROLE-9b92e09420ec45a0ae697d5658cddd8c', '测试', '1', '1554947237', '1', '1554949702', '0', '0');

-- ----------------------------
-- Table structure for `t_role_resource`
-- ----------------------------
DROP TABLE IF EXISTS `t_role_resource`;
CREATE TABLE `t_role_resource` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int(10) DEFAULT NULL COMMENT '角色id',
  `resource_id` int(10) DEFAULT NULL COMMENT '资源id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3109 DEFAULT CHARSET=utf8 COMMENT='角色与菜单(按钮)对应表';

-- ----------------------------
-- Records of t_role_resource
-- ----------------------------
INSERT INTO `t_role_resource` VALUES ('18', '1', '1017');
INSERT INTO `t_role_resource` VALUES ('19', '1', '1018');
INSERT INTO `t_role_resource` VALUES ('20', '1', '1019');
INSERT INTO `t_role_resource` VALUES ('3104', '42', '1018');
INSERT INTO `t_role_resource` VALUES ('3105', '42', '1019');
INSERT INTO `t_role_resource` VALUES ('3108', '42', '1017');

-- ----------------------------
-- Table structure for `t_sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user`;
CREATE TABLE `t_sys_user` (
  `sys_user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '登录名',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码',
  `realname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '真实姓名',
  `department` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '部门',
  `position` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '职位',
  `status` tinyint(2) DEFAULT NULL COMMENT '状态, 0正常, 1停用',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `creator` int(11) DEFAULT NULL COMMENT '建立人',
  `create_time` int(10) DEFAULT NULL COMMENT '建立时间',
  `updator` int(11) DEFAULT NULL COMMENT '更新人',
  `update_time` int(10) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`sys_user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of t_sys_user
-- ----------------------------
INSERT INTO `t_sys_user` VALUES ('1', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '管理员', '', '', '0', '', null, null, '1', '1554957743');
INSERT INTO `t_sys_user` VALUES ('31', 'test', 'e10adc3949ba59abbe56e057f20f883e', '测试', '测试', '测试', '0', '', '1', '1554947216', '31', '1554958031');

-- ----------------------------
-- Table structure for `t_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int(10) DEFAULT NULL COMMENT '角色id',
  `sys_user_id` int(10) DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=362 DEFAULT CHARSET=utf8 COMMENT='用户与角色关联表';

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES ('357', '1', '1');
INSERT INTO `t_user_role` VALUES ('358', '42', '1');
INSERT INTO `t_user_role` VALUES ('361', '42', '31');

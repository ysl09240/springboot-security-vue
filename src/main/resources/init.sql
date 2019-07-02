
CREATE DATABASE /*!32312 IF NOT EXISTS*/`hmhc_db` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `hmhc_db`;

/*Table structure for table `bus_alarm_record` */

DROP TABLE IF EXISTS `bus_alarm_record`;

CREATE TABLE `bus_alarm_record` (
  `id` varchar(36) NOT NULL COMMENT '主键Id',
  `ci_id` varchar(30) DEFAULT NULL COMMENT '采集识别编码',
  `area_code` varchar(50) DEFAULT NULL COMMENT '区域编码',
  `st_id` varchar(50) DEFAULT NULL COMMENT '采集点',
  `chan_id` varchar(50) DEFAULT NULL COMMENT '通道id',
  `rsn_alarm` varchar(50) DEFAULT NULL COMMENT '报警原因分类',
  `rsn_desc` varchar(100) DEFAULT NULL COMMENT '报警原因描述',
  `s_name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `s_gender` varchar(1) DEFAULT NULL COMMENT '性别',
  `cer_type` varchar(3) DEFAULT NULL COMMENT '证件类型',
  `cer_no` varchar(18) DEFAULT NULL COMMENT '证件号码',
  `s_country` varchar(3) DEFAULT NULL COMMENT '国籍',
  `s_nation` varchar(2) DEFAULT NULL COMMENT '民族',
  `op_teller` varchar(50) DEFAULT NULL COMMENT '操作人',
  `op_time` varchar(14) DEFAULT NULL COMMENT '操作时间',
  `create_user_id` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_id` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='报警信息记录';

/*Table structure for table `bus_cer_record` */

DROP TABLE IF EXISTS `bus_cer_record`;

CREATE TABLE `bus_cer_record` (
  `id` varchar(36) NOT NULL COMMENT '主键id',
  `ci_id` varchar(50) DEFAULT NULL COMMENT '采集识别编码',
  `s_name` varchar(54) DEFAULT NULL COMMENT '姓名',
  `s_gender` varchar(1) DEFAULT NULL COMMENT '性别',
  `s_country` varchar(3) DEFAULT NULL COMMENT '国籍',
  `s_nation` varchar(2) DEFAULT NULL COMMENT '民族',
  `s_birth` datetime DEFAULT NULL COMMENT '出生日期',
  `cer_type` varchar(3) DEFAULT NULL COMMENT '证件类型',
  `cer_no` varchar(18) DEFAULT NULL COMMENT '证件号码',
  `cer_auth` varchar(100) DEFAULT NULL COMMENT '签发机关',
  `cer_valid` varchar(50) DEFAULT NULL COMMENT '有效期限',
  `addr1_code` varchar(6) DEFAULT NULL COMMENT '户籍地址_行政区划代码',
  `addr1` varchar(100) DEFAULT NULL COMMENT '户籍地址',
  `addr2_code` varchar(6) DEFAULT NULL COMMENT '现住址_行政区划代码',
  `addr2` varchar(100) DEFAULT NULL COMMENT '现住址',
  `tel1` varchar(20) DEFAULT NULL COMMENT '手机号码1',
  `tel2` varchar(20) DEFAULT NULL COMMENT '手机号码2',
  `f_id` varchar(50) DEFAULT NULL COMMENT '人员类别id',
  `op_teller` varchar(50) DEFAULT NULL COMMENT '操作人',
  `op_time` datetime DEFAULT NULL COMMENT '操作时间',
  `create_user_id` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_id` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='身份信息采集识别信息';

/*Table structure for table `bus_city` */

DROP TABLE IF EXISTS `bus_city`;

CREATE TABLE `bus_city` (
  `city_id` varchar(50) NOT NULL COMMENT 'city_id',
  `parent_id` varchar(50) DEFAULT NULL COMMENT 'parent_id',
  `city_code` varchar(50) DEFAULT NULL COMMENT 'city_code',
  `city` varchar(100) DEFAULT NULL COMMENT 'city',
  `level` tinyint(4) DEFAULT NULL COMMENT 'level',
  `create_user_id` varchar(36) DEFAULT NULL COMMENT 'create_user_id',
  `create_time` datetime DEFAULT NULL COMMENT 'create_time',
  `update_user_id` varchar(36) DEFAULT NULL COMMENT 'update_user_id',
  `update_time` datetime DEFAULT NULL COMMENT 'update_time',
  `mj_id` varchar(40) DEFAULT NULL COMMENT 'mj_id',
  `upload_city_id` varchar(36) DEFAULT NULL COMMENT 'upload_city_id',
  PRIMARY KEY (`city_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='地区';

/*Table structure for table `bus_dept` */

DROP TABLE IF EXISTS `bus_dept`;

CREATE TABLE `bus_dept` (
  `code` varchar(50) NOT NULL COMMENT '机构Id',
  `zzcode` varchar(12) DEFAULT NULL COMMENT '公安机构代码',
  `code_spelling` varchar(100) DEFAULT NULL COMMENT '机构中文拼音',
  `code_name` varchar(100) DEFAULT NULL COMMENT '机构名称',
  `code_abr` varchar(100) DEFAULT NULL COMMENT '机构名称描述',
  `address` varchar(100) DEFAULT NULL COMMENT '机构地址',
  `contract` varchar(50) DEFAULT NULL COMMENT '联系电话',
  `phone` varchar(50) DEFAULT NULL COMMENT '手机号',
  `sup_code` varchar(50) DEFAULT NULL COMMENT '父级机构ID',
  `invalid` varchar(2) DEFAULT NULL COMMENT '有效标志',
  `sortid` varchar(8) DEFAULT NULL COMMENT '排序ID',
  `ext1` varchar(50) DEFAULT NULL COMMENT '扩展字段1',
  `ext2` varchar(50) DEFAULT NULL COMMENT '扩展字段2',
  `create_user_id` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_id` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `type` varchar(10) DEFAULT NULL COMMENT '机构类别',
  `province_id` varchar(36) DEFAULT NULL COMMENT '省',
  `city_id` varchar(36) DEFAULT NULL COMMENT '市',
  `area_id` varchar(36) DEFAULT NULL COMMENT '县',
  `longitude` decimal(9,6) DEFAULT NULL COMMENT '经度',
  `latitude` decimal(8,6) DEFAULT NULL COMMENT '纬度',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公安组织机构代码';

/*Table structure for table `bus_dict_data` */

DROP TABLE IF EXISTS `bus_dict_data`;

CREATE TABLE `bus_dict_data` (
  `id` varchar(36) NOT NULL COMMENT '主键Id',
  `name` varchar(255) DEFAULT NULL COMMENT '字段名称',
  `value` varchar(255) DEFAULT NULL COMMENT '字段值',
  `type` varchar(32) DEFAULT NULL COMMENT '字段类型',
  `del_flag` varchar(2) DEFAULT NULL COMMENT '删除标志',
  `create_user_id` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_id` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `order_num` int(11) DEFAULT NULL COMMENT '显示顺序',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据字典';

/*Table structure for table `bus_iris_info` */

DROP TABLE IF EXISTS `bus_iris_info`;

CREATE TABLE `bus_iris_info` (
  `id` varchar(36) NOT NULL COMMENT '主键id',
  `ir_id` varchar(30) DEFAULT NULL COMMENT '虹膜id',
  `s_id` varchar(30) DEFAULT NULL COMMENT '人员编号',
  `iris_l` varchar(2000) DEFAULT NULL COMMENT '虹膜数据信息（左眼）',
  `iris_r` varchar(2000) DEFAULT NULL COMMENT '虹膜数据信息（右眼）',
  `tel1` varchar(20) DEFAULT NULL COMMENT '手机号1',
  `tel2` varchar(20) DEFAULT NULL COMMENT '手机号2',
  `tel1_old` varchar(20) DEFAULT NULL COMMENT '旧手机号1',
  `tel2_old` varchar(20) DEFAULT NULL COMMENT '旧手机号2',
  `sec_lev` varchar(2) DEFAULT NULL COMMENT '人员安全级别',
  `flg_diff` varchar(50) DEFAULT NULL COMMENT '证件不一致标志',
  `face_name` varchar(50) DEFAULT NULL COMMENT '人脸图像名称',
  `coll_time` varchar(14) DEFAULT NULL COMMENT '采集时间',
  `dev_type` varchar(50) DEFAULT NULL COMMENT '设备类型',
  `userid` varchar(20) DEFAULT NULL COMMENT '用户id',
  `op_teller` varchar(50) DEFAULT NULL COMMENT '操作人',
  `op_time` varchar(14) DEFAULT NULL COMMENT '操作时间',
  `flg_offline` varchar(50) DEFAULT NULL COMMENT '离线采集标志',
  `reason_coll` varchar(50) DEFAULT NULL COMMENT '采集原因（人员分类）',
  `reason_coll_desc` varchar(100) DEFAULT NULL COMMENT '采集原因描述',
  `coll_num` varchar(50) DEFAULT NULL COMMENT '采集编号',
  `coll_name` varchar(80) DEFAULT NULL COMMENT '采集人',
  `flg_force` varchar(50) DEFAULT NULL COMMENT '强制采集标志',
  `area_code` varchar(50) DEFAULT NULL COMMENT '区域编码',
  `flg_cer` varchar(50) DEFAULT NULL COMMENT '无证标志',
  `flg_eye` varchar(50) DEFAULT NULL COMMENT '眼睛采集识别标志',
  `flg_typein` varchar(50) DEFAULT NULL COMMENT '录入标志',
  `flg_face` varchar(50) DEFAULT NULL COMMENT '人脸图像标志',
  `path_face` varchar(100) DEFAULT NULL COMMENT '人脸图像相对全路径',
  `path_cer` varchar(100) DEFAULT NULL COMMENT '证件相对全路径名',
  `flg_quick` varchar(50) DEFAULT NULL COMMENT '快速采集识别标志',
  `flg_wo` varchar(50) DEFAULT NULL COMMENT '是否为工作对象',
  `jzup_time` varchar(14) DEFAULT NULL COMMENT '警综更新时间',
  `time_jz` varchar(10) DEFAULT NULL COMMENT '警综耗时',
  `st_id` varchar(50) DEFAULT NULL COMMENT '采集点',
  `flg_sync` varchar(50) DEFAULT NULL COMMENT '同步标识',
  `flg_upload` varchar(50) DEFAULT NULL COMMENT '数据上报标识',
  `iris_face` varchar(2000) DEFAULT NULL COMMENT '虹膜照片数据信息',
  `iris_template_l` longblob COMMENT '左眼虹膜模板数据信息',
  `iris_template_r` longblob COMMENT '右眼虹膜模板数据信息',
  `iris_template` varchar(2000) DEFAULT NULL COMMENT '虹膜模板数据信息',
  `create_user_id` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_id` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `q_l` varchar(4) DEFAULT NULL COMMENT '左眼质量',
  `q_r` varchar(4) DEFAULT NULL COMMENT '右眼质量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='虹膜信息';

/*Table structure for table `bus_iris_record` */

DROP TABLE IF EXISTS `bus_iris_record`;

CREATE TABLE `bus_iris_record` (
  `id` varchar(36) NOT NULL COMMENT '主键Id',
  `ci_id` varchar(30) DEFAULT NULL COMMENT '采集识别编码',
  `area_code` varchar(50) DEFAULT NULL COMMENT '区域编码',
  `op_type` varchar(50) DEFAULT NULL COMMENT '操作类型',
  `flg_quick` varchar(50) DEFAULT NULL COMMENT '快速采集识别标志',
  `flg_match_to_coll` varchar(50) DEFAULT NULL COMMENT '提示转采集标志',
  `flg_offline` varchar(50) DEFAULT NULL COMMENT '离线采集',
  `flg_march` varchar(50) DEFAULT NULL COMMENT '匹配结果标志',
  `s_id` varchar(50) DEFAULT NULL COMMENT '人员编码',
  `ir_id` varchar(50) DEFAULT NULL COMMENT '虹膜id',
  `st_id` varchar(50) DEFAULT NULL COMMENT '采集点id',
  `st_x` varchar(15) DEFAULT NULL COMMENT '采集点坐标x',
  `st_y` varchar(15) DEFAULT NULL COMMENT '采集点坐标y',
  `dzmc` varchar(100) DEFAULT NULL COMMENT '业务发生地点_地址名称',
  `chan_id` varchar(50) DEFAULT NULL COMMENT '通道id',
  `sec_lev` varchar(2) DEFAULT NULL COMMENT '人员安全级别',
  `f_lst` varchar(50) DEFAULT NULL COMMENT '关键特征',
  `lev_id` varchar(50) DEFAULT NULL COMMENT '定级id',
  `rsn_lev` varchar(50) DEFAULT NULL COMMENT '定级原因类别',
  `rsn_detail` varchar(100) DEFAULT NULL COMMENT '具体描述',
  `op_teller` varchar(50) DEFAULT NULL COMMENT '操作人',
  `op_time` varchar(20) DEFAULT NULL COMMENT '操作时间',
  `time_coll` varchar(10) DEFAULT NULL COMMENT '采集耗时',
  `time_verify` varchar(10) DEFAULT NULL COMMENT '对比时间',
  `time_jz` varchar(10) DEFAULT NULL COMMENT '警综耗时',
  `time_sum` varchar(10) DEFAULT NULL COMMENT '总耗时',
  `site_type` varchar(50) DEFAULT NULL COMMENT '采集点类型',
  `dev_type` varchar(50) DEFAULT NULL COMMENT '设备型号',
  `reason_coll` varchar(50) DEFAULT NULL COMMENT '人员分类',
  `reason_coll_desc` varchar(100) DEFAULT NULL COMMENT '采集原因描述',
  `coll_num` varchar(50) DEFAULT NULL COMMENT '采集编号',
  `coll_name` varchar(80) DEFAULT NULL COMMENT '采集人',
  `flg_force` varchar(50) DEFAULT NULL COMMENT '强制采集标志',
  `flg_cer` varchar(50) DEFAULT NULL COMMENT '无证标志',
  `flg_eye` varchar(50) DEFAULT NULL COMMENT '眼睛采集识别标志',
  `flg_typein` varchar(50) DEFAULT NULL COMMENT '录入标志',
  `flg_face` varchar(50) DEFAULT NULL COMMENT '人脸图像标志',
  `path_face` varchar(100) DEFAULT NULL COMMENT '人脸图像存储相对路径',
  `flg_alarm` varchar(50) DEFAULT NULL COMMENT '报警标志',
  `flg_wo` varchar(50) DEFAULT NULL COMMENT '是否工作对象',
  `flg_focus` varchar(2) DEFAULT NULL COMMENT '临时关注标志',
  `q_l` varchar(4) DEFAULT NULL COMMENT '左眼质量',
  `q_r` varchar(4) DEFAULT NULL COMMENT '右眼质量',
  `create_user_id` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_id` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='虹膜采集识别记录';

/*Table structure for table `bus_worker_info` */

DROP TABLE IF EXISTS `bus_worker_info`;

CREATE TABLE `bus_worker_info` (
  `id` varchar(36) NOT NULL COMMENT '主键Id',
  `s_id` varchar(30) DEFAULT NULL COMMENT '人员编号',
  `ir_id` varchar(30) DEFAULT NULL COMMENT '虹膜id',
  `s_gender` varchar(1) DEFAULT NULL COMMENT '性别',
  `s_name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `s_country` varchar(3) DEFAULT NULL COMMENT '国籍',
  `s_nation` varchar(2) DEFAULT NULL COMMENT '民族',
  `s_birth` datetime DEFAULT NULL COMMENT '出生日期',
  `cer_type` varchar(3) DEFAULT NULL COMMENT '证件类型',
  `cer_no` varchar(18) DEFAULT NULL COMMENT '证件号码',
  `cer_auth` varchar(100) DEFAULT NULL COMMENT '签发机关',
  `cer_valid` varchar(50) DEFAULT NULL COMMENT '有效期限',
  `addr1_code` varchar(6) DEFAULT NULL COMMENT '户籍地址_行政区划代码',
  `addr1` varchar(100) DEFAULT NULL COMMENT '户籍地址',
  `addr2_code` varchar(6) DEFAULT NULL COMMENT '现住址_行政区划代码',
  `addr2` varchar(100) DEFAULT NULL COMMENT '现住址',
  `flg_bind` varchar(50) DEFAULT NULL COMMENT '绑定标志',
  `flg_focus` varchar(2) DEFAULT NULL COMMENT '临时关注标志',
  `focus_end_date` varchar(8) DEFAULT NULL COMMENT '临时关注结束日志',
  `focus_desc` varchar(100) DEFAULT NULL COMMENT '临时关注备注',
  `check_state` varchar(10) DEFAULT NULL COMMENT '核查状态',
  `create_user_id` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_id` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `head_image` varchar(200) DEFAULT NULL COMMENT '身份证图像',
  `s_sign` varchar(50) DEFAULT NULL COMMENT '身份标签',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='人员信息';

/*Table structure for table `sys_menu` */

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `id` varchar(36) NOT NULL COMMENT '主键id',
  `label` varchar(30) DEFAULT NULL COMMENT '功能名称',
  `url` varchar(60) DEFAULT NULL COMMENT '请求url',
  `code` varchar(30) DEFAULT NULL COMMENT '组件名称',
  `path` varchar(30) DEFAULT NULL COMMENT '前端路由',
  `icon` varchar(30) DEFAULT NULL COMMENT '图标样式',
  `parent_id` varchar(36) DEFAULT NULL COMMENT '父菜单Id',
  `type` int(11) DEFAULT NULL COMMENT '菜单类型',
  `permission` varchar(50) DEFAULT NULL COMMENT '权限标识',
  `order` int(11) DEFAULT NULL COMMENT '排序位',
  `create_user_id` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_id` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='功能表';

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` varchar(36) NOT NULL COMMENT '主键id',
  `name` varchar(30) DEFAULT NULL COMMENT '角色名称',
  `code` varchar(30) DEFAULT NULL COMMENT '角色编码',
  `descs` varchar(100) DEFAULT NULL,
  `create_user_id` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_id` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

/*Table structure for table `sys_role_menu` */

DROP TABLE IF EXISTS `sys_role_menu`;

CREATE TABLE `sys_role_menu` (
  `id` varchar(36) NOT NULL COMMENT '主键Id',
  `role_id` varchar(36) DEFAULT NULL COMMENT '角色id',
  `menu_id` varchar(36) DEFAULT NULL COMMENT '功能Id',
  `create_user_id` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_id` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色功能表';

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` varchar(36) NOT NULL COMMENT '主键id',
  `username` varchar(30) DEFAULT NULL COMMENT '用户名',
  `password` varchar(80) DEFAULT NULL COMMENT '登录密码',
  `real_name` varchar(10) DEFAULT NULL COMMENT '用户姓名',
  `phone` varchar(13) DEFAULT NULL,
  `dept_id` varchar(36) DEFAULT NULL COMMENT '所属部门ID',
  `create_user_id` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_id` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `jz_code` varchar(50) DEFAULT NULL COMMENT '警务编码',
  `state` varchar(2) DEFAULT NULL COMMENT '启动停用状态',
  `cer_f_path` varchar(255) DEFAULT NULL COMMENT '身份证人像面',
  `cer_b_path` varchar(255) DEFAULT NULL COMMENT '身份证国徽面',
  `jw_f_path` varchar(255) DEFAULT NULL COMMENT '警务证人像面',
  `jw_b_path` varchar(255) DEFAULT NULL COMMENT '警务证国徽面',
  `cer_no` varchar(18) DEFAULT NULL COMMENT '身份证号码',
  `gender` varchar(10) DEFAULT NULL COMMENT '性别',
  `is_enabled` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `id` varchar(36) NOT NULL COMMENT '主键Id',
  `user_id` varchar(36) DEFAULT NULL COMMENT '用户Id',
  `role_id` varchar(36) DEFAULT NULL COMMENT '角色Id',
  `create_user_id` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_id` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表角色表';



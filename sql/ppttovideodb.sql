/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50616
 Source Host           : localhost:3306
 Source Schema         : ppttovideodb

 Target Server Type    : MySQL
 Target Server Version : 50616
 File Encoding         : 65001

 Date: 06/04/2025 22:37:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for digital_human_table
-- ----------------------------
DROP TABLE IF EXISTS `digital_human_table`;
CREATE TABLE `digital_human_table`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '虚拟人名称',
  `pic_url` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '虚拟人图片url',
  `adddate` datetime(0) DEFAULT NULL COMMENT '添加时间',
  `userid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `id_UNIQUE`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '虚拟数字人表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of digital_human_table
-- ----------------------------
INSERT INTO `digital_human_table` VALUES (-1, '隐藏数字人', 'digithumans/kong.png', '2025-03-22 14:57:40', 0);
INSERT INTO `digital_human_table` VALUES (1, '哪吒', 'digithumans/2ebf34ea-7e88-4c80-8e24-f537fc7302d8.png', '2025-03-15 07:16:47', 0);
INSERT INTO `digital_human_table` VALUES (18, '男主播', 'digithumans/7a0dd8e7-738d-4095-b881-e43481115fe6.png', '2025-03-15 07:16:55', 0);
INSERT INTO `digital_human_table` VALUES (19, '女主播', 'digithumans/1f8520ad-0154-427a-b629-3a22a175a02f.png', '2025-03-15 07:17:01', 0);
INSERT INTO `digital_human_table` VALUES (20, '申公豹', 'digithumans/6dff3547-41d6-4c4a-b2cf-15e6481de711.png', '2025-03-15 07:34:14', 0);
INSERT INTO `digital_human_table` VALUES (23, '男2', 'digithumans/c192a19e-dc51-4b64-9d77-3ec0e7c974eb.png', '2025-04-05 12:14:53', 2);

-- ----------------------------
-- Table structure for exportvideo_table
-- ----------------------------
DROP TABLE IF EXISTS `exportvideo_table`;
CREATE TABLE `exportvideo_table`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `pptid` int(11) DEFAULT NULL COMMENT '导出pptid',
  `userid` int(11) DEFAULT NULL COMMENT '导出用户id',
  `adddate` datetime(0) DEFAULT NULL COMMENT '提交时间',
  `process` int(11) DEFAULT NULL COMMENT '导出进度',
  `video_url` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '导出视频地址',
  `video_length` float DEFAULT NULL COMMENT '视频时长',
  `priority` int(11) DEFAULT NULL COMMENT '优先级1-10越高,数值越大越先处理',
  `donedate` datetime(0) DEFAULT NULL COMMENT '处理完成时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `id_UNIQUE`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '导出视频表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of exportvideo_table
-- ----------------------------
INSERT INTO `exportvideo_table` VALUES (18, 14, 0, '2025-04-04 08:17:57', 100, 'genrator-vedios/18/export.mp4', 52, 1, '2025-04-04 13:55:13');

-- ----------------------------
-- Table structure for ppt_page_table
-- ----------------------------
DROP TABLE IF EXISTS `ppt_page_table`;
CREATE TABLE `ppt_page_table`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pptid` int(11) DEFAULT NULL COMMENT 'pptid',
  `pageindex` int(11) DEFAULT NULL COMMENT '页索引',
  `pageurl` varchar(245) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '页面url',
  `pagecontent` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '页面讲解内容 不能超过4096字符',
  `soundid` int(11) DEFAULT NULL COMMENT '声音设置',
  `digitalhunmanid` int(11) DEFAULT NULL COMMENT '虚拟人id',
  `dh_posleft` int(11) DEFAULT NULL COMMENT '虚拟人x坐标 像素',
  `dh_postop` int(11) DEFAULT NULL COMMENT '虚拟人y坐标 像素',
  `dh_width` int(11) DEFAULT NULL COMMENT '虚拟人宽度 像素',
  `dh_height` int(11) DEFAULT NULL COMMENT '虚拟人高度 像素',
  `vediolength` float DEFAULT NULL COMMENT '视频时长',
  `isbgtransprent` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `id_UNIQUE`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 754 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'ppt页面表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ppt_page_table
-- ----------------------------
INSERT INTO `ppt_page_table` VALUES (343, 14, 1, 'uploads/a02ce42a-2107-4f29-acc6-1eb9d2eefd1f/1.png', '下面给大家介绍一下PPT数字人制作教程', 1, 1, 1694, 573, 256, 256, 0, 0);
INSERT INTO `ppt_page_table` VALUES (344, 14, 2, 'uploads/a02ce42a-2107-4f29-acc6-1eb9d2eefd1f/2.png', '数字人管理，支持用户上传个人图片快速生成高度仿真的专属数字人形象。', 1, 1, 1694, 573, 256, 256, 0, 0);
INSERT INTO `ppt_page_table` VALUES (345, 14, 3, 'uploads/a02ce42a-2107-4f29-acc6-1eb9d2eefd1f/3.png', '声音管理平台，支持用户上传个性化声音样本进行高精度声音克隆。', 1, 1, 1694, 573, 256, 256, 0, 0);
INSERT INTO `ppt_page_table` VALUES (346, 14, 4, 'uploads/a02ce42a-2107-4f29-acc6-1eb9d2eefd1f/4.png', 'PPT管理  支持PPT格式文件上传 智能PPT页面管理功能   讲解文稿管理 AI智能生成  数字人播报配置   声音设置  视频输出功能  ', 1, 1, 1694, 573, 256, 256, 0, 0);
INSERT INTO `ppt_page_table` VALUES (347, 14, 5, 'uploads/a02ce42a-2107-4f29-acc6-1eb9d2eefd1f/5.png', '支持导出视频，显示导出进度 , 导出完成后视频下载等功能', 1, 1, 1694, 573, 256, 256, 0, 0);
INSERT INTO `ppt_page_table` VALUES (348, 14, 6, 'uploads/a02ce42a-2107-4f29-acc6-1eb9d2eefd1f/6.png', '最后谢谢观看 ', 1, 1, 1694, 573, 256, 256, 0, 0);
INSERT INTO `ppt_page_table` VALUES (693, 52, 1, 'uploads/66c45d67-ba23-4782-bf63-16291bd4f185/1.png', '专注实景数字化智慧应用 \n智慧实景指挥平台产品解决方案 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (694, 52, 2, 'uploads/66c45d67-ba23-4782-bf63-16291bd4f185/2.png', '建设背景 \n指挥大屏发展趋势 \n1.0阶段 \n目前市面上95%的指挥中心管理模式 \n无数字场景，纯监控展示\n基于基础的IOT及摄像头进行直接展示，如视频矩阵墙\n基础硬件设备投入为主，智能化管理水平低下 \n2.0阶段 \n2D平面及大数据叠加的指挥中心 \n基于2D地图或GIS场景，但细节不足，无法实现精细化呈现\n对IOT设备数据进行展示\n有一定的智慧化管理水平，但大多以宏观数据展示为主，无更深度的数据挖掘和应用 \n3.0阶段 \n基于实时视频及3D空间、多元大数据采集的指挥中心 \n基于实景地图和3D数字孪生场景1:1还原，包括道路、建筑、园林、植被、室内等各种细节\n对IOT终端及用户数据进行统一展示、分析、控制、仿真和推演等\n实现精细化、智慧化管理 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (695, 52, 3, 'uploads/66c45d67-ba23-4782-bf63-16291bd4f185/3.png', '', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (696, 52, 4, 'uploads/66c45d67-ba23-4782-bf63-16291bd4f185/4.png', '直观：最自然、全景式、实体化、融合的视频实景应用，真实地描述和反映设备的实体关系及属性，可满足不同用户需求。\n\n同步：机场日常车流量、环境等数据都可通过信息标签查询、预警，实现同步运行。\n\n精确：精确的实体、位置和层级描述，提供准确的全景展示。\n\n智能：实现各类业务系统集成，实体信息与经营管理协同，为机场安保管理和运营管理提供技术支撑，通过远程访问实现协同工作，达到信息的充分共享。 \n智慧实景指挥平台是基于物联网、增强现实、智能分析、GIS、大数据与软件集成等多种先进技术，构建的实景式、网格化立体防控系统，实现在统一门户下完成重点区域的安保防控业务。\n系统支持将设备的实时数据，将设备运行情况、质量跟踪状态各种数据集成展示，完成实体信息、人员管理等无缝结合，达到信息充分共享，有利于实时监控防范损失、减少开支，提高管理效率，实现可视化实时监控。 \nAR \n解决思路 \nAR展示 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (697, 52, 5, 'uploads/66c45d67-ba23-4782-bf63-16291bd4f185/5.png', 'AI \n基于深度学习算法的多项智能手段应用，为安防管理提供智能化决策分析 \n通过后台AI算法引擎，实现对现有摄像机的利旧接入并赋能，支持人脸识别、口罩识别、玩手机识别、安全帽检测、反光衣检测、烟雾识别等异常事件的联动预警，视频智能分析及警情的实时定位，并支持联动预案，快速获取现场情况，支撑更高效、更贴合实际的应急处置方案 \n解决思路 \nAI赋能 \n人脸识别 \n口罩识别 \n人员徘徊 \n人员翻墙 \n人员入侵 \n拨打电话 \n玩手机识别 \n抽烟识别 \n飞行物体检测 \n睡岗检测 \n人员摔倒识别 \n反光衣检测 \n安全帽检测 \n落水识别 \n垃圾检测 \n算法还在不断优化······ \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (698, 52, 6, 'uploads/66c45d67-ba23-4782-bf63-16291bd4f185/6.png', '建设目标 \n整合业务资源，数据实景可视化上图 \n可视化决策支撑门户 \n管控手段融合，智能预警联防联控 \n全智能预警预判机制 \n全局宏观调度，局部实景联动指挥 \n立体化协同指挥体系 \n预案实景部署，保障业务有序进行 \n科学化预案规划管理 \n通过赋予非AR监控前端设备智慧感知应用，在视频监控或事件检测基础上，联合前端监测设备和业务系统的多维异构数据进行场景业务感知工作，将各类信息源产生各种不同类型的数据接入系统平台进行视频AR融合展示。同时结合AI视频数据结构化关联应用，对机械运作和厂区安防的日常管理监测运营、特殊应急响应事件的业务和警情能更加准确全面的识别和发现。 \n全方位监控、精准故障排查，智慧预警，稳定运营 \n告警联动 \n全量聚合 \n智慧监控 \n管理方案 \n数据感知 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (699, 52, 7, 'uploads/66c45d67-ba23-4782-bf63-16291bd4f185/7.png', '平台架构 \n信息采集层 \n数据层 \n服务层 \n业务层 \n访问层 \nWEB平台 \nAPP \n微信小程序 \n管理人员 \n业务人员 \n视频采集 \n视频传输 \nAI分析 \n报警管理 \n视频数据 \n设备数据 \n报警数据 \n业务数据 \n地图数据 \n普通监控 \n数据处理 \n用户管理 \n权限管理 \n消息推送 \n报警联动 \n预警研判 \n全景巡查 \n标签管理 \n设备管理 \n地图管理 \n更多… \n热成像摄像机 \n远距离高清摄像机 \n无人机 \n卫星图斑 \n安\n全\n支\n撑\n保\n障 \n手持终端 \n智慧实景指挥平台\n \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (700, 52, 8, 'uploads/66c45d67-ba23-4782-bf63-16291bd4f185/8.png', '平台功能 \n综合展示 \n通过多系统业务数据融合，有效整合视频监控、停车系统、门禁系统、报警杆等其它日常管理及安全防控相关设备数据，并能够将其在视频实景地图上可视化展示。通过视频一张图，实现多维数据的高效呈现和定位，实现业务系统的统一集成和管理，达成各系统“1+1>2”的效果，提升安全防控的效率和质量。 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (701, 52, 9, 'uploads/66c45d67-ba23-4782-bf63-16291bd4f185/9.png', '平台功能 \n视频实景地图 \n1、系统通过增强现实技术，能够将监控视频画面中的重点单位、警力、警情、摄像头、人员卡口以及辅助卡口信息，通过不同的标签业务图层呈现视频实景地图效果，使指挥人员可非常直观的了解区域情况。\n\n2、区别于传统的监控资源二维图上可视化方式，低点的各类防控资源在视频实景地图上展示，能够最直观精确的了解的各类资源的分布，各类资源支持直观的点击获取，支持搜索查询，可以更高效的调用和获取，有助于提升日常视频巡逻及应急指挥效率。 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (702, 52, 10, 'uploads/66c45d67-ba23-4782-bf63-16291bd4f185/10.png', '平台功能 \n高低点视频联动 \n1、高点摄像机以其高视野、大场景的视频画面能够实现重点区域的全方位巡视，并支持在高点摄像机视频画面中联动调阅周边视频监控，通过高低联动，实现“高点看全局、低点看细节”的立体实景视频联动。\n\n2、兼顾整体与局部，同时可查看多个视频监控画面，可以采用全局-局部、局部-周边的方式进行联动视频查看。相较于传统监控系统的网格式方式更有利于快速定位。 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (703, 52, 11, 'uploads/66c45d67-ba23-4782-bf63-16291bd4f185/11.png', '平台功能 \n视频自动巡查 \n平台支持设置视频巡检预案，定时自动启动视频巡检预案，支持针对不同时段、不同情况下的多种巡检预案设置。实现远程巡检的目的，改变传统人员走访式巡检管理，提升巡检效率，夯实巡检质量。 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (704, 52, 12, 'uploads/66c45d67-ba23-4782-bf63-16291bd4f185/12.png', '平台功能 \n告警信息管理 \n平台支持对低点摄像机视频进行智能分析，主动侦测视频中人员的举动行为，当侦测到周界附近人员的异常行为时（如攀爬围墙、入侵警戒线），即时触发报警，自动在系统界面弹窗报警提示监控人员，并联动打开关联的摄像机，播放现场实时视频，让监控人员更好了解现场情况，作出合理的应急处理措施。 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (705, 52, 13, 'uploads/66c45d67-ba23-4782-bf63-16291bd4f185/13.png', '平台功能 \n监控数据回放 \n平台支持监控视频的查看回放，可根据时间查询回放视频。录像回放可进行开始、暂停、停止、抓图、快放、慢放等操作。可根据时间段查询下载视频录像数据。 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (706, 52, 14, 'uploads/66c45d67-ba23-4782-bf63-16291bd4f185/14.png', '平台功能 \n灵活的组件管理 \n    灵活的组件管理可以帮助用户加快实现产品或服务创新节奏，快速适应用户需求变化， 增强产品在互联网时代的竞争力。\n    组件管理主要由业务目的、业务活动、业务对象、业务服务、业务策略和组件治理等六部分组成。业务组件形成了组件内高内聚和组件间低耦合的一个边界：一个业务组件内部支持紧密耦合，保证应用的性能；业务组件之间要求松散耦合，支持应用组装和组件的重用。 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (707, 52, 15, 'uploads/66c45d67-ba23-4782-bf63-16291bd4f185/15.png', '平台功能 \n三维空间管理 \n平台支持三维模型导入，并在三维空间中设置相应点位信息。\n1、播放终端没有特别要求，一般大众化电脑均能播放；\n2、无需下载播放插件，省去访问者下载的麻烦，播放浏览无障碍；\n3、三维实景展示达到了最佳效果，有极强的临场。 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (708, 52, 16, 'uploads/66c45d67-ba23-4782-bf63-16291bd4f185/16.png', '平台功能 \n网格化管理 \n平台可实现人口与场景空间绑定，在实景画面中呈现楼宇房屋布局并可联动绑定住户信息，显示住户基础档案信息、为社区网格化管理提供帮助。 \n8号楼 \n人员信息 \n房屋信息 \n车辆信息 \n平面图 \n8号楼-601 \n人员信息 \n房屋信息 \n车辆信息 \n平面图 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (709, 52, 17, 'uploads/66c45d67-ba23-4782-bf63-16291bd4f185/17.png', '平台功能 \n智能算法分析 \n平台针对用户基于自身AI研发实力及产品服务形态提供一系列视觉智能识别行业解决方案服务。平台目前已具备多种算法且算法库在不断扩容，支持智慧安防、智慧园区、智慧港口、智慧电力等多个不同行业算法应用。\n\n视频兼容：平台算法可兼容已有的视频监控摄像头；\n事前告警：通过智能识别分析，及时识别异常情况，异常事件自动存储；\n功能叠加：可根据实际需求开通相应算法，灵活便捷。 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (710, 52, 18, 'uploads/66c45d67-ba23-4782-bf63-16291bd4f185/18.png', '平台功能 \n灵活系统对接 \n应急系统 \n考勤系统 \n道闸系统 \n综治网格 \n多系统融合，实现一张屏多图层展示 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (711, 52, 19, 'uploads/66c45d67-ba23-4782-bf63-16291bd4f185/19.png', '下级平台\n支持向上级平台推送视频流、数据信息及告警信息等，实现上级单位和本级单位之间的无缝协同 \n实现多级联动管理 \n上级平台\n支持下级各个平台节点高清视频、数据信息及告警信息等接入，实现统一调度及管控 \n下级平台一 \n上级 \n下级平台二 \n下级平台三 \n下级平台四 \n下级平台···N \n集中管控 \n平台功能 \n级联管理 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (712, 52, 20, 'uploads/66c45d67-ba23-4782-bf63-16291bd4f185/20.png', '展示效果 \n指挥中心 \n一机多屏构建以重点区域为牵引，宏观、重点区域、局部微观全兼顾的城市管理新模式 \n在地图上可直观查看防控资源的分布，可选取摄像机，实现在系统主屏上切换播放 \n地图屏 \n主要显示高点视频实景地图，通过标签联动技术实现管理应用及数据的可视化展示 \n实景应用屏 \n支持推送过来的低点摄像机视频的轮巡播放，支持4/9/16/25等分割画面播放 \n低点联动屏 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (713, 52, 21, 'uploads/66c45d67-ba23-4782-bf63-16291bd4f185/21.png', '产品形态 \nJSD-R100 \nJSD-R100 \n（前置子平台，软/硬件一体AR盒子，100路视频接入） \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (714, 52, 22, 'uploads/66c45d67-ba23-4782-bf63-16291bd4f185/22.png', '产品形态 \nJSD-RI-5008 \nJSD-RI-5008 \n前置子平台，软/硬件AR+AI一体机\n500路视频接入，8路AI \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (715, 52, 23, 'uploads/66c45d67-ba23-4782-bf63-16291bd4f185/23.png', '应用价值 \n集约化建设 \n智慧安防 \n实景化管理 \n前端广泛接入 \n大数据可视分析 \n集成联动、互联互通、节约资源、数据共享的“集约化”建设理念，实现安全管理系统从自动，到智能，再到智慧的跨越式发展。 \n通过AR增强现实技术，能够实现基于视频画面的实时数据管理，对接入的系统和数据进行实景化管理，并形成系统和数据的联动应用 \n接入视频、人员、门禁、消防系统。对接入的系统进行集中展示和远程控制，实现高效、智能、先进的安全预警处置管理。 \n利用数据可视化技术将多方系统数据汇聚融合，形成多维度统计分析报表，并为用户提供空间、负荷信息的实时动态全掌握，助力安全防范做到“科学防治、精准施措”。 \n智慧实景指挥平台是运用人工智能、大数据、物联网和设备监控技术加强安保和信息管理；通过先进技术，保障业务运营安全，同时减少人工的干预、即时正确地采集各类人员数据、环境数据，整合各业务系统数据资源，构建一个高效智能的安防平台 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (716, 52, 24, 'uploads/66c45d67-ba23-4782-bf63-16291bd4f185/24.png', '应用价值 \n通过多维实景地图应用体系，构建从全局至微观状态的无缝链接、层级渐进式的管理模型，有效支撑场景化、精细化的管控需求 \n场景化态势管控 \n1 \n基于多维实景地图应用体系，充分利旧，有效融合各类业务数据并建立场景关联关系，实现更直观、更便捷的场景管理\n\n \n一体化的数据管理 \n2 \n针对不同场景下的防控需求，设置告警联动预案，建立智能化的报警响应机制，构建自动化的全过程防控模型 \n智能化联防联控 \n3 \n感知数据！赋能未来！ \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (717, 52, 25, 'uploads/66c45d67-ba23-4782-bf63-16291bd4f185/25.png', '专注实景数字化智慧应用 \n谢谢观看看，期待与您的合作 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (718, 53, 1, 'uploads/035549ac-d6a1-4d1d-ba03-8aa4b2bc1be7/1.png', '2024 \n年，南海区在区委、区政府领导下，经济社会平稳健康发展，预计全年地区生产总值突破 \n4000 \n亿元。 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (719, 53, 2, 'uploads/035549ac-d6a1-4d1d-ba03-8aa4b2bc1be7/2.png', '人文经济成为新增长点，常住人口预计破 \n370 \n万，技能人才增 \n7.8%  \n。文旅活动火爆，旅游收入和接待游客量大幅增长， \n“ \n两新 \n” \n政策带动消费品销售额超 \n40 \n亿。工业投资高位增长，占固投比重升至 \n44% \n，超 \n7 \n成规上工业企业数智化转型 \n  \n。 \n“3+3” \n新型产业集群有 \n276 \n个项目落地。 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (720, 53, 3, 'uploads/035549ac-d6a1-4d1d-ba03-8aa4b2bc1be7/3.png', '工程建设和环境改善成效显著，融入大湾区交通网，新改建公园和自行车道，河涌水质提升， \n“ \n无废城市 \n” \n建设扎实。 \n“ \n百千万工程 \n” \n深入推进，打造全域土地综合整治 \n“ \n南海样板 \n”  \n，村集体和村民改革意识增强。 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (721, 53, 4, 'uploads/035549ac-d6a1-4d1d-ba03-8aa4b2bc1be7/4.png', '民生事业也取得新突破， \n10 \n项民生实事完成，教育、医疗更优质均衡，就业、住房保障有力，社会治安防控体系完善。营商环境持续优化， \n“ \n政务服务合伙人 \n” \n、 \n“ \n智能办 \n” \n提升服务效率 \n  \n。 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (722, 54, 1, 'uploads/b5905608-a254-44d4-a0fd-fec917f14841/1.png', '2024 \n年，南海区在区委、区政府领导下，经济社会平稳健康发展，预计全年地区生产总值突破 \n4000 \n亿元。 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (723, 54, 2, 'uploads/b5905608-a254-44d4-a0fd-fec917f14841/2.png', '人文经济成为新增长点，常住人口预计破 \n370 \n万，技能人才增 \n7.8%  \n。文旅活动火爆，旅游收入和接待游客量大幅增长， \n“ \n两新 \n” \n政策带动消费品销售额超 \n40 \n亿。工业投资高位增长，占固投比重升至 \n44% \n，超 \n7 \n成规上工业企业数智化转型 \n  \n。 \n“3+3” \n新型产业集群有 \n276 \n个项目落地。 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (724, 54, 3, 'uploads/b5905608-a254-44d4-a0fd-fec917f14841/3.png', '工程建设和环境改善成效显著，融入大湾区交通网，新改建公园和自行车道，河涌水质提升， \n“ \n无废城市 \n” \n建设扎实。 \n“ \n百千万工程 \n” \n深入推进，打造全域土地综合整治 \n“ \n南海样板 \n”  \n，村集体和村民改革意识增强。 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (725, 54, 4, 'uploads/b5905608-a254-44d4-a0fd-fec917f14841/4.png', '民生事业也取得新突破， \n10 \n项民生实事完成，教育、医疗更优质均衡，就业、住房保障有力，社会治安防控体系完善。营商环境持续优化， \n“ \n政务服务合伙人 \n” \n、 \n“ \n智能办 \n” \n提升服务效率 \n  \n。 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (726, 15, 1, 'uploads/008436e5-5796-4538-bd03-219b1328e380/1.png', '2024 \n年，南海区在区委、区政府领导下，经济社会平稳健康发展，预计全年地区生产总值突破 \n4000 \n亿元。 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (727, 15, 2, 'uploads/008436e5-5796-4538-bd03-219b1328e380/2.png', '人文经济成为新增长点，常住人口预计破 \n370 \n万，技能人才增 \n7.8%  \n。文旅活动火爆，旅游收入和接待游客量大幅增长， \n“ \n两新 \n” \n政策带动消费品销售额超 \n40 \n亿。工业投资高位增长，占固投比重升至 \n44% \n，超 \n7 \n成规上工业企业数智化转型 \n  \n。 \n“3+3” \n新型产业集群有 \n276 \n个项目落地。 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (728, 15, 3, 'uploads/008436e5-5796-4538-bd03-219b1328e380/3.png', '工程建设和环境改善成效显著，融入大湾区交通网，新改建公园和自行车道，河涌水质提升， \n“ \n无废城市 \n” \n建设扎实。 \n“ \n百千万工程 \n” \n深入推进，打造全域土地综合整治 \n“ \n南海样板 \n”  \n，村集体和村民改革意识增强。 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (729, 15, 4, 'uploads/008436e5-5796-4538-bd03-219b1328e380/4.png', '民生事业也取得新突破， \n10 \n项民生实事完成，教育、医疗更优质均衡，就业、住房保障有力，社会治安防控体系完善。营商环境持续优化， \n“ \n政务服务合伙人 \n” \n、 \n“ \n智能办 \n” \n提升服务效率 \n  \n。 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (730, 16, 1, 'uploads/f83ed43e-b8a6-4dbb-97a4-968b2e1f8c39/1.png', '下面给大家介绍一下 \nPPT \n数字人制作教程 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (731, 16, 2, 'uploads/f83ed43e-b8a6-4dbb-97a4-968b2e1f8c39/2.png', '数字人管理，支持上传图片生成专属数字人 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (732, 16, 3, 'uploads/f83ed43e-b8a6-4dbb-97a4-968b2e1f8c39/3.png', '声音管理，支持上传声音进行声音克隆 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (733, 16, 4, 'uploads/f83ed43e-b8a6-4dbb-97a4-968b2e1f8c39/4.png', 'PPT \n管理，支持上传 \nPPT \n，页面管理，讲解文稿生成，设置数字人，设置声音 \n, \n导出视频 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (734, 16, 5, 'uploads/f83ed43e-b8a6-4dbb-97a4-968b2e1f8c39/5.png', '支持提交导出视频，导出进度显示 \n, \n视频下载等 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (735, 16, 6, 'uploads/f83ed43e-b8a6-4dbb-97a4-968b2e1f8c39/6.png', '最好谢谢观看 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (736, 17, 1, 'uploads/06241373-9a48-4447-ae1a-bc3676f5b8b6/1.png', '下面给大家介绍一下 \nPPT \n数字人制作教程 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (737, 17, 2, 'uploads/06241373-9a48-4447-ae1a-bc3676f5b8b6/2.png', '数字人管理，支持上传图片生成专属数字人 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (738, 17, 3, 'uploads/06241373-9a48-4447-ae1a-bc3676f5b8b6/3.png', '声音管理，支持上传声音进行声音克隆 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (739, 17, 4, 'uploads/06241373-9a48-4447-ae1a-bc3676f5b8b6/4.png', 'PPT \n管理，支持上传 \nPPT \n，页面管理，讲解文稿生成，设置数字人，设置声音 \n, \n导出视频 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (740, 17, 5, 'uploads/06241373-9a48-4447-ae1a-bc3676f5b8b6/5.png', '支持提交导出视频，导出进度显示 \n, \n视频下载等 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (741, 17, 6, 'uploads/06241373-9a48-4447-ae1a-bc3676f5b8b6/6.png', '最好谢谢观看 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (742, 18, 1, 'uploads/6aefcc21-c1e3-488e-88a0-caebdc718ba3/1.png', '2024 \n年，南海区在区委、区政府领导下，经济社会平稳健康发展，预计全年地区生产总值突破 \n4000 \n亿元。 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (743, 18, 2, 'uploads/6aefcc21-c1e3-488e-88a0-caebdc718ba3/2.png', '人文经济成为新增长点，常住人口预计破 \n370 \n万，技能人才增 \n7.8%  \n。文旅活动火爆，旅游收入和接待游客量大幅增长， \n“ \n两新 \n” \n政策带动消费品销售额超 \n40 \n亿。工业投资高位增长，占固投比重升至 \n44% \n，超 \n7 \n成规上工业企业数智化转型 \n  \n。 \n“3+3” \n新型产业集群有 \n276 \n个项目落地。 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (744, 18, 3, 'uploads/6aefcc21-c1e3-488e-88a0-caebdc718ba3/3.png', '工程建设和环境改善成效显著，融入大湾区交通网，新改建公园和自行车道，河涌水质提升， \n“ \n无废城市 \n” \n建设扎实。 \n“ \n百千万工程 \n” \n深入推进，打造全域土地综合整治 \n“ \n南海样板 \n”  \n，村集体和村民改革意识增强。 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (745, 18, 4, 'uploads/6aefcc21-c1e3-488e-88a0-caebdc718ba3/4.png', '民生事业也取得新突破， \n10 \n项民生实事完成，教育、医疗更优质均衡，就业、住房保障有力，社会治安防控体系完善。营商环境持续优化， \n“ \n政务服务合伙人 \n” \n、 \n“ \n智能办 \n” \n提升服务效率 \n  \n。 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (746, 19, 1, 'uploads/3d53683e-ff68-48e4-bc4e-f794b0f2801d/1.png', '2024 \n年，南海区在区委、区政府领导下，经济社会平稳健康发展，预计全年地区生产总值突破 \n4000 \n亿元。 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (747, 19, 2, 'uploads/3d53683e-ff68-48e4-bc4e-f794b0f2801d/2.png', '人文经济成为新增长点，常住人口预计破 \n370 \n万，技能人才增 \n7.8%  \n。文旅活动火爆，旅游收入和接待游客量大幅增长， \n“ \n两新 \n” \n政策带动消费品销售额超 \n40 \n亿。工业投资高位增长，占固投比重升至 \n44% \n，超 \n7 \n成规上工业企业数智化转型 \n  \n。 \n“3+3” \n新型产业集群有 \n276 \n个项目落地。 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (748, 19, 3, 'uploads/3d53683e-ff68-48e4-bc4e-f794b0f2801d/3.png', '工程建设和环境改善成效显著，融入大湾区交通网，新改建公园和自行车道，河涌水质提升， \n“ \n无废城市 \n” \n建设扎实。 \n“ \n百千万工程 \n” \n深入推进，打造全域土地综合整治 \n“ \n南海样板 \n”  \n，村集体和村民改革意识增强。 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (749, 19, 4, 'uploads/3d53683e-ff68-48e4-bc4e-f794b0f2801d/4.png', '民生事业也取得新突破， \n10 \n项民生实事完成，教育、医疗更优质均衡，就业、住房保障有力，社会治安防控体系完善。营商环境持续优化， \n“ \n政务服务合伙人 \n” \n、 \n“ \n智能办 \n” \n提升服务效率 \n  \n。 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (750, 20, 1, 'uploads/005032ee-ac93-4b43-a443-149d9dcd12bc/1.png', '2024 \n年，南海区在区委、区政府领导下，经济社会平稳健康发展，预计全年地区生产总值突破 \n4000 \n亿元。 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (751, 20, 2, 'uploads/005032ee-ac93-4b43-a443-149d9dcd12bc/2.png', '人文经济成为新增长点，常住人口预计破 \n370 \n万，技能人才增 \n7.8%  \n。文旅活动火爆，旅游收入和接待游客量大幅增长， \n“ \n两新 \n” \n政策带动消费品销售额超 \n40 \n亿。工业投资高位增长，占固投比重升至 \n44% \n，超 \n7 \n成规上工业企业数智化转型 \n  \n。 \n“3+3” \n新型产业集群有 \n276 \n个项目落地。 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (752, 20, 3, 'uploads/005032ee-ac93-4b43-a443-149d9dcd12bc/3.png', '工程建设和环境改善成效显著，融入大湾区交通网，新改建公园和自行车道，河涌水质提升， \n“ \n无废城市 \n” \n建设扎实。 \n“ \n百千万工程 \n” \n深入推进，打造全域土地综合整治 \n“ \n南海样板 \n”  \n，村集体和村民改革意识增强。 \n', 1, 1, 1, 1, 300, 300, 0, 0);
INSERT INTO `ppt_page_table` VALUES (753, 20, 4, 'uploads/005032ee-ac93-4b43-a443-149d9dcd12bc/4.png', '民生事业也取得新突破， \n10 \n项民生实事完成，教育、医疗更优质均衡，就业、住房保障有力，社会治安防控体系完善。营商环境持续优化， \n“ \n政务服务合伙人 \n” \n、 \n“ \n智能办 \n” \n提升服务效率 \n  \n。 \n', 1, 1, 1, 1, 300, 300, 0, 0);

-- ----------------------------
-- Table structure for ppt_table
-- ----------------------------
DROP TABLE IF EXISTS `ppt_table`;
CREATE TABLE `ppt_table`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '名称',
  `fileurl` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件地址',
  `adddate` datetime(0) DEFAULT NULL COMMENT '添加时间',
  `userid` int(11) DEFAULT NULL COMMENT '用户id',
  `pagecount` int(11) DEFAULT NULL COMMENT '页数',
  `videocount` float DEFAULT NULL COMMENT '视频时长',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `id_UNIQUE`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'ppt文件表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ppt_table
-- ----------------------------
INSERT INTO `ppt_table` VALUES (14, 'ppt数字人制作教程', 'uploads/a02ce42a-2107-4f29-acc6-1eb9d2eefd1f.pptx', '2025-03-22 03:24:47', 0, 6, 47.25, NULL);
INSERT INTO `ppt_table` VALUES (20, '佛山', 'uploads/005032ee-ac93-4b43-a443-149d9dcd12bc.pptx', '2025-04-05 12:13:24', 2, 4, 116, NULL);

-- ----------------------------
-- Table structure for sound_table
-- ----------------------------
DROP TABLE IF EXISTS `sound_table`;
CREATE TABLE `sound_table`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '声音名称',
  `audio_url` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '样本声音url\n',
  `audio_content` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '样本声音内容',
  `adddate` datetime(0) DEFAULT NULL COMMENT '添加日期',
  `userid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '声音表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sound_table
-- ----------------------------
INSERT INTO `sound_table` VALUES (1, '哪吒', 'voices/612b8d6f-bfd9-45b8-aca9-9ef8f2dce93b.mp3', '还吃,收你们来了', '2025-03-15 07:26:28', 0);
INSERT INTO `sound_table` VALUES (4, 'jefflee', 'voices/0e3c253f-a412-4170-9560-dcba76190b9c.mp3', '专业从事指挥信息通讯技术的国家级高新技术企业', '2025-03-15 07:05:59', 0);
INSERT INTO `sound_table` VALUES (6, '申公豹', 'voices/b7ee00f3-60e3-47f0-943f-5b51976ce177.mp3', '人心中的成见是一座大山，任你怎么努力都休想搬动', '2025-03-15 07:27:04', 0);
INSERT INTO `sound_table` VALUES (7, '女主播', 'voices/2b26f0b7-d000-4363-bffb-418ea58efe87.wav', '人心中的成见是一座大山，任你怎么努力都休想搬动', '2025-03-15 07:37:28', 0);
INSERT INTO `sound_table` VALUES (8, '男主播', 'voices/13458281-158a-4fdb-bf49-988c58e704bf.mp3', '人心中的成见是一座大山，任你怎么努力都休想搬动', '2025-03-15 07:43:22', 0);
INSERT INTO `sound_table` VALUES (10, '云建', 'voices/73407efd-827c-4dcb-b667-9419dbe33d92.mp3', '人心中的成见是一座大山，任你怎么努力都休想搬动', '2025-04-05 12:14:01', 2);

-- ----------------------------
-- Table structure for user_table
-- ----------------------------
DROP TABLE IF EXISTS `user_table`;
CREATE TABLE `user_table`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户昵称',
  `password` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录密码',
  `adddate` datetime(0) DEFAULT NULL COMMENT '添加日期',
  `videolen` float DEFAULT NULL COMMENT '可以导出视频时长(秒)',
  `outdate` datetime(0) DEFAULT NULL COMMENT '会员过期日期',
  `usevideolen` float DEFAULT NULL COMMENT '使用导出视频时长(秒)',
  `iconurl` varchar(145) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `updatedate` datetime(0) DEFAULT NULL,
  `phone` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '手机号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `id_UNIQUE`(`id`) USING BTREE,
  UNIQUE INDEX `name_UNIQUE`(`name`) USING BTREE,
  UNIQUE INDEX `phone_UNIQUE`(`phone`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user_table
-- ----------------------------
INSERT INTO `user_table` VALUES (1, 'admin', 'admin', '2025-03-15 15:15:15', 100000, '2035-03-15 15:15:15', 0, NULL, NULL, NULL);
INSERT INTO `user_table` VALUES (2, 'jeff', 'lch78983017', '2025-04-05 09:48:26', NULL, NULL, NULL, NULL, '2025-04-05 09:48:26', '13316438841');

SET FOREIGN_KEY_CHECKS = 1;

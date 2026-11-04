/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : online_exam

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 18/1/2023 12:39:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `adminId` int(0) NOT NULL AUTO_INCREMENT COMMENT 'ID号',
  `adminName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '姓名',
  `sex` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '性别',
  `tel` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '电话号码',
  `email` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '电子邮箱',
  `pwd` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '密码',
  `cardId` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '身份证号',
  `role` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '角色(0管理员，1教师，2学生)',
  PRIMARY KEY (`adminId`) USING BTREE,
  INDEX `sex`(`sex`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9991 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '管理员信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (9991, '超级管理员', '男', '13652222222', '1253578873@qq.com', '$2a$10$WqcbjOiItLfnRA.wog3mu.F7lvAAqcK1zXX4yaLAEwVYns0dzzx0C', '551444444444447887', '0');

-- ----------------------------
-- Table structure for da
-- 说明：大题作答与互评表。*该表在原导出脚本中缺失*，此处依据
--      DaQuestionMapper（insert/update/updateScore）与 DaQuestion 实体补全。
-- ----------------------------
DROP TABLE IF EXISTS `da`;
CREATE TABLE `da`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `studentId` int(0) NULL DEFAULT NULL COMMENT '学号',
  `questionId` int(0) NULL DEFAULT NULL COMMENT '试题编号',
  `answerMe` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '学生作答内容',
  `scoreMe` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '教师评分',
  `isTag` int(0) NULL DEFAULT 0 COMMENT '状态(0待互评,1已评分,3教师重评)',
  `examCode` int(0) NULL DEFAULT NULL COMMENT '作业编号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_da_student_question`(`studentId`, `questionId`) USING BTREE,
  INDEX `idx_da_exam`(`examCode`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '大题作答表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for da_question
-- 说明：大题题库表。*该表在原导出脚本中缺失*，此处依据
--      DaQuestionMapper 的字段与 big-question 相关 SQL 补全。
-- ----------------------------
DROP TABLE IF EXISTS `da_question`;
CREATE TABLE `da_question`  (
  `questionId` int(0) NOT NULL AUTO_INCREMENT COMMENT '试题编号',
  `subject` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '作业科目',
  `question` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '问题题目',
  `answer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '参考答案',
  `analysis` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '题目解析',
  `score` int(0) NULL DEFAULT 5 COMMENT '分数',
  `section` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '所属章节',
  `level` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '难度等级',
  PRIMARY KEY (`questionId`) USING BTREE,
  INDEX `idx_da_question_subject`(`subject`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '大题题库表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for exam_manage
-- ----------------------------
DROP TABLE IF EXISTS `exam_manage`;
CREATE TABLE `exam_manage`  (
  `examCode` int(0) NOT NULL AUTO_INCREMENT COMMENT '作业编号',
  `description` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '该次作业介绍',
  `source` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '课程名称',
  `paperId` int(0) NULL DEFAULT NULL COMMENT '试卷编号',
  `examDate` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '作业日期',
  `totalTime` int(0) NULL DEFAULT NULL COMMENT '持续时长',
  `grade` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '年级',
  `term` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '学期',
  `major` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '专业',
  `institute` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '学院',
  `totalScore` int(0) NULL DEFAULT NULL COMMENT '总分',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '作业类型',
  `tips` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '考生须知',
  PRIMARY KEY (`examCode`) USING BTREE,
  INDEX `idx_exam_paper`(`paperId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20190023 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '作业管理表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of exam_manage
-- ----------------------------
INSERT INTO `exam_manage` VALUES (20230001, '2023年上期期末作业', '计算机网络', 1001, '2023-03-21', 90, '2023', '1', '计算机科学与技术', '软件工程学院', 86, '期末作业', '快乐千万条，学习第一条，平时不努力，作业两行泪。');
INSERT INTO `exam_manage` VALUES (20230002, '2023年上期期末作业', '数据库理论', 1002, '2023-03-07', 90, '2023', '2', '网络工程', '软件工程学院', 100, '期末作业', '快乐千万条，学习第一条，平时不努力，作业两行泪。');
INSERT INTO `exam_manage` VALUES (20230003, '2023年下期期末作业', '数据结构', 1003, '2023-02-27', 90, '2023', '1', '软件工程', '软件工程学院', 100, '期末作业', '快乐千万条，学习第一条，平时不努力，作业两行泪。');
INSERT INTO `exam_manage` VALUES (20230005, '2023年上期期末作业', '计算机导论', 1004, '2023-03-14', 90, '2023', '1', '计算机科学与技术', '软件工程学院', 100, '期末作业', '快乐千万条，学习第一条，平时不努力，作业两行泪。');
INSERT INTO `exam_manage` VALUES (20230006, '2023年上期期末作业', '软件工程', 1005, '2023-03-21', 120, '2023', '1', '计算机科学与技术', '软件工程学院', 100, '期末作业', '快乐千万条，学习第一条，平时不努力，作业两行泪。');
INSERT INTO `exam_manage` VALUES (20230007, '2023年上期期末作业', '操作系统', 1006, '2023-03-13', 120, '2023', '2', '计算机科学与技术', '软件工程学院', 100, '期末作业', '快乐千万条，学习第一条，平时不努力，作业两行泪。');
INSERT INTO `exam_manage` VALUES (20230008, '2023年下期期末作业', 'C语言', 1007, '2023-03-13', 120, '2023', '1', '信息工程', '软件工程学院', 100, '期末作业', '快乐千万条，学习第一条，平时不努力，作业两行泪。');
INSERT INTO `exam_manage` VALUES (20230009, '2023年上期期末作业', '高等数学', 1008, '2023-03-07', 90, '2023', '1', '信息工程', '软件工程学院', 100, '期末作业', '快乐千万条，学习第一条，平时不努力，作业两行泪。');
INSERT INTO `exam_manage` VALUES (20230013, '测试', '测试', 1009, '2023-04-24', 120, '2023', NULL, '测试', '测试', 100, '期末作业', '请不要带与作业无关的书籍。');
INSERT INTO `exam_manage` VALUES (20230014, 'Java程序设计', 'Java程序设计', 1010, '2023-03-01', 60, '2023', NULL, '软件工程', '计算机学院', 100, '期末作业', '请规范作答');
INSERT INTO `exam_manage` VALUES (20230015, '作业', 'C#', 1011, '2023-03-13', 5, '2023', NULL, '软件工程', '计算机学院', 30, '期末考', '请规范作答2');
INSERT INTO `exam_manage` VALUES (20230016, '高等数学作业', '高等数学', 1012, '2023-03-20', 90, '2023', NULL, '数学工程', '数学院', NULL, '期末作业', NULL);
INSERT INTO `exam_manage` VALUES (20230017, '程序测试', '计算机网络', 1013, '2023-03-21', 90, '2023', NULL, '软件工程', '计算机学院', 0, '期末', NULL);
INSERT INTO `exam_manage` VALUES (20230018, '333', '333', 1014, '2023-03-22', 90, '2023', NULL, '333', '333', NULL, '333', '333');
INSERT INTO `exam_manage` VALUES (20230024, '测试', '计算机网络', 1015, '2023-11-18', 90, '2023', NULL, '网络工程', '计算机学院', NULL, '期中测试', '沉着应试');
INSERT INTO `exam_manage` VALUES (20230025, '测试', '测试', 1016, '2023-11-18', 90, '测试', NULL, '测试', '测试', NULL, '测试', '测试');
INSERT INTO `exam_manage` VALUES (20230026, '测试', '测试2', 1017, '2023-11-18', 90, '测试', NULL, '测试', '测试', NULL, '测试', '测试');
INSERT INTO `exam_manage` VALUES (20230027, '测试', '测试3', 1018, '2023-11-18', 90, '测试', NULL, '测试', '测试', NULL, '测试', '测试');

-- ----------------------------
-- Table structure for fill_question
-- ----------------------------
DROP TABLE IF EXISTS `fill_question`;
CREATE TABLE `fill_question`  (
  `questionId` int(0) NOT NULL AUTO_INCREMENT COMMENT '试题编号',
  `subject` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '作业科目',
  `question` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '试题内容',
  `answer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '正确答案',
  `analysis` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '题目解析',
  `score` int(0) NULL DEFAULT 2 COMMENT '分数',
  `level` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '难度等级',
  `section` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '所属章节',
  PRIMARY KEY (`questionId`) USING BTREE,
  INDEX `idx_fill_subject`(`subject`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10029 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '填空题题库' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of fill_question
-- ----------------------------
INSERT INTO `fill_question` VALUES (10000, '计算机网络', '从计算机网络系统组成的角度看，计算机网络可以分为()和()', '通信子网资源子网', NULL, 2, '3', NULL);
INSERT INTO `fill_question` VALUES (10001, '计算机网络', '收发电子邮件，属于ISO/OSI RM中 ()层的功能。', '应用', NULL, 2, '1', NULL);
INSERT INTO `fill_question` VALUES (10002, '计算机网络', '在TCP/IP层次模型中与OSI参考模型第四层相对应的主要协议有()和(),其中后者提供无连接的不可靠传输服', 'TCP（传输控制协议） UDP（用户数据报协议） ', NULL, 2, '2', NULL);
INSERT INTO `fill_question` VALUES (10003, '计算机网络', '计算机网络中常用的三种有线媒体是 (),()和 ()', '同轴电缆.双绞线 光纤', NULL, 2, '1', NULL);
INSERT INTO `fill_question` VALUES (10004, '计算机网络', '国内最早的四大网络包括原邮电部的ChinaNet. 原电子部的ChinaGBN. 教育部的()和中科院的CSTnet', 'CERnet (或中国教育科研网)', NULL, 2, '4', NULL);
INSERT INTO `fill_question` VALUES (10005, '计算机网络', '复盖一个国家，地区或几个洲的计算机网络称为()，在同一建筑或复盖几公里内范围的网络称为()，而介于两者之间的是()', ' 广域网       局域网     城域网', NULL, 2, '1', NULL);
INSERT INTO `fill_question` VALUES (10006, '计算机网络', 'Outlook等常用电子邮件软件接收邮件使用的协议是(),发送邮件时使用的协议是()', 'POP3    SMTP    ', NULL, 2, '1', NULL);
INSERT INTO `fill_question` VALUES (10007, '计算机网络', '通信系统中，称调制前的电信号为()信号，调制后的信号为调制信号', '基带', NULL, 2, '1', NULL);
INSERT INTO `fill_question` VALUES (10008, '计算机网络', '按照IPV4标准,IP地址205.3.127.13属于()类地址', 'C', NULL, 2, '1', NULL);
INSERT INTO `fill_question` VALUES (10009, '计算机网络', '计算机网络采用()技术，而传统电话网络则采用()技术', '分组交换电路交换', NULL, 2, '1', NULL);
INSERT INTO `fill_question` VALUES (10010, '计算机网络', '计算机内传输的信号是()，而公用电话系统的传输系统只能传输()', '数字信号模拟信号', NULL, 2, '1', NULL);
INSERT INTO `fill_question` VALUES (10011, '计算机网络', '通信系统中，称调制前的电信号为()，调制后的信号叫()。', '基带信号调制信号', NULL, 2, '1', NULL);
INSERT INTO `fill_question` VALUES (10012, '计算机网络', 'IP地址分()和()两个部分', '网络号主机号', NULL, 2, '1', NULL);
INSERT INTO `fill_question` VALUES (10013, '计算机网络', ' IP地址协议作网间网中()层协议，提供无连接的数据报传输机制，IP数据报也分为()和()两个部分', '网络报头数据区', NULL, 2, '2', NULL);
INSERT INTO `fill_question` VALUES (10014, '计算机网络', '()是一个简单的远程终端协议。', 'TELNET', NULL, 2, '1', NULL);
INSERT INTO `fill_question` VALUES (10015, '计算机网络', '在同一个系统内，相邻层之间交换信息的连接点称之为()，而低层模块向高层提供功能性的支持称之为()。', '接口服务', NULL, 2, '1', NULL);
INSERT INTO `fill_question` VALUES (10016, '计算机网络', 'Internet广泛使用的电子邮件传送协议是()', 'SMTP', NULL, 2, '1', NULL);
INSERT INTO `fill_question` VALUES (10017, '计算机网络', '按交换方式来分类，计算机网络可以分为电路交换网，  报文交换网  和()三种', '分组交换网', NULL, 2, '3', NULL);
INSERT INTO `fill_question` VALUES (10018, '计算机网络', 'Intranet分层结构包括网络、(),应用三个层次。', '服务', NULL, 2, '1', NULL);
INSERT INTO `fill_question` VALUES (10019, '计算机网络', 'WWW上的每一个网页都有一个独立的地址，这些地址称为  ()', '统一资源定位器/URL ', NULL, 2, '2', NULL);
INSERT INTO `fill_question` VALUES (10020, '计算机网络', '分组交换网中，附加信息用来在网络中进行路由选择、() 和流量控制', '差错纠正  ', NULL, 2, '4', NULL);
INSERT INTO `fill_question` VALUES (10021, '计算机网络', '根据IEEE802模型的标准将数据链路层划分为LLC子层和 ()子层。', ' MAC ', NULL, 2, '3', NULL);
INSERT INTO `fill_question` VALUES (10022, '计算机网络', '据交换的路由信息的不同，路由算法可以分为两大类：  ()  和链路状态算法', '距离向量算法', NULL, 2, '3', NULL);
INSERT INTO `fill_question` VALUES (10023, '计算机网络', '假定某信道受奈氏准则限制的最高码元速率为2000码元/秒。如果采用振幅调制，把码元的振幅划分为16个不同等级来传送，那么可以获得的数据率为 () b/s。', '80000 ', NULL, 2, '5', NULL);
INSERT INTO `fill_question` VALUES (10024, '计算机网络', '交换型以太网系统中的 ()  ，以其为核心联接站点或者网段，端口之间帧的输入和输出已不再受到CSMA/CD媒体访问控制协议的约束。', '以太网交换器 ', NULL, 2, '5', NULL);
INSERT INTO `fill_question` VALUES (10025, '计算机网络', '局域网络参考模型是以 ()标准为基础的', 'IEEE802', NULL, 2, '5', NULL);
INSERT INTO `fill_question` VALUES (10026, '计算机网络', '路由器的核心是 () 。', ' 路由表', NULL, 2, '3', NULL);
INSERT INTO `fill_question` VALUES (10027, '计算机网络', '若 HDLC 帧数据段中出现比特串“ 01011111110 ”，则比特填充后的输出为()', '10111110110', NULL, 2, '5', NULL);
INSERT INTO `fill_question` VALUES (10028, '计算机网络', '数字调制的三种基本形式：移幅键控法ASK、 ()、移相键控法PSK', '移频键控法FSK', NULL, 2, '5', NULL);

-- ----------------------------
-- Table structure for judge_question
-- ----------------------------
DROP TABLE IF EXISTS `judge_question`;
CREATE TABLE `judge_question`  (
  `questionId` int(0) NOT NULL AUTO_INCREMENT COMMENT '试题编号',
  `subject` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '作业科目',
  `question` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '试题内容',
  `answer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '正确答案',
  `analysis` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '题目解析',
  `score` int(0) NULL DEFAULT 2 COMMENT '分数',
  `level` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '难度等级',
  `section` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '所属章节',
  PRIMARY KEY (`questionId`) USING BTREE,
  INDEX `idx_judge_subject`(`subject`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10012 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '判断题题库表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of judge_question
-- ----------------------------
INSERT INTO `judge_question` VALUES (10001, '计算机网络', '与有线网相比,无线网的数据传输率一般相对较慢', 'T', NULL, 2, '1', '');
INSERT INTO `judge_question` VALUES (10002, '计算机网络', 'OSI参考模型中,不同节点的同等层具有不同的功能', 'F', NULL, 2, '1', 'OSI模型');
INSERT INTO `judge_question` VALUES (10003, '计算机网络', '普通电脑不能作为服务器', 'F', NULL, 2, '1', NULL);
INSERT INTO `judge_question` VALUES (10004, '计算机网络', '没有网线的电脑不能连入互联网', 'F', NULL, 2, '1', NULL);
INSERT INTO `judge_question` VALUES (10005, '计算机网络', '网卡必须安装驱动程序', 'T', NULL, 2, '2', NULL);
INSERT INTO `judge_question` VALUES (10006, '计算机网络', 'UTP为屏蔽双绞线', 'F', NULL, 2, '2', NULL);
INSERT INTO `judge_question` VALUES (10007, '计算机网络', '网络接口卡又称为网卡,它是构成网络的基本部件', 'T', NULL, 2, '2', NULL);
INSERT INTO `judge_question` VALUES (10008, '计算机网络', '无线AP可以成倍地扩展网络覆盖范围', 'T', NULL, 2, '3', NULL);
INSERT INTO `judge_question` VALUES (10009, '计算机网络', 'SMTP是一组用于由源地址到目的地址传送邮件的协议', 'T', NULL, 2, '4', NULL);
INSERT INTO `judge_question` VALUES (10010, '计算机网络', '任务管理器可以关闭所有的进程', 'F', NULL, 2, '3', NULL);
INSERT INTO `judge_question` VALUES (10011, '计算机网络', '利用BT下载时,用户越多,下载速度越快', 'T', NULL, 2, '2', NULL);
INSERT INTO `judge_question` VALUES (10012, '计算机网络', 'INTERNET上向朋友发送电子邮件,必须知道对方的真实姓名和家庭住址', 'F', NULL, 2, '1', NULL);

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '留言编号',
  `title` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '标题',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '留言内容',
  `time` date NULL DEFAULT NULL COMMENT '留言时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '留言表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (37, '张三', '计算机网络作业好难', '2023-04-04');
INSERT INTO `message` VALUES (38, '李四', '练习了很多遍还是很难', '2023-04-04');

-- ----------------------------
-- Table structure for multi_question
-- ----------------------------
DROP TABLE IF EXISTS `multi_question`;
CREATE TABLE `multi_question`  (
  `questionId` int(0) NOT NULL AUTO_INCREMENT COMMENT '试题编号',
  `subject` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '作业科目',
  `question` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '问题题目',
  `answerA` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '选项A',
  `answerB` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '选项B',
  `answerC` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '选项C',
  `answerD` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '选项D',
  `rightAnswer` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '正确答案',
  `analysis` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '题目解析',
  `score` int(0) NULL DEFAULT 2 COMMENT '分数',
  `section` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '所属章节',
  `level` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '难度等级',
  PRIMARY KEY (`questionId`) USING BTREE,
  INDEX `idx_multi_subject`(`subject`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10034 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '选择题题库表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of multi_question
-- ----------------------------
INSERT INTO `multi_question` VALUES (10000, '计算机网络', 'DNS 服务器和DHCP服务器的作用是什么（）', '将IP地址翻译为计算机名，为客户机分配IP地址', '将IP地址翻译为计算机名、解析计算机的MAC地址', '将计算机名翻译为IP地址、为客户机分配IP地址', '将计算机名翻译为IP地址、解析计算机的MAC地址', 'C', '', 2, '应用层', '2');
INSERT INTO `multi_question` VALUES (10001, '计算机网络', 'HTTP协议通常使用什么协议进行传输（）', 'ARP', 'DHCP', 'UDP', 'TCP', 'D', NULL, 2, '应用层', '2');
INSERT INTO `multi_question` VALUES (10003, '计算机网络', '查看DNS缓存记录的命令（）', 'ipconfig/displaydns', 'nslookup', 'ipconfig/release', 'ipconfig/flushdns', 'A', NULL, 2, '应用层', '3');
INSERT INTO `multi_question` VALUES (10004, '计算机网络', 'DHCP(        )报文的目的IP地址为255.255.255.255', 'DhcpDisover', 'DhcpOffer', 'DhcpAck', 'DhcpNack', 'A', NULL, 2, '应用层', '2');
INSERT INTO `multi_question` VALUES (10005, '计算机网络', '下列地址中，（  ）不是DHCP服务器分配的IP地址', '196.254.109.100', '169.254.12.42', '69.254.48.45', '96.254.54.15', 'B', NULL, 2, '应用层', '2');
INSERT INTO `multi_question` VALUES (10006, '计算机网络', 'DHCP通常可以为客户端自动配置哪些网络参数（）', 'IP，掩码，网关，DNS', 'IP，掩码，域名，SMTP', '网关，掩码，浏览器，FTP', 'IP，网关，DNS，服务器', 'A', NULL, 2, '应用层', '2');
INSERT INTO `multi_question` VALUES (10007, '计算机网络', 'DNS服务器在名称解析过程中正确的查询顺序为（）', '本地缓存记录→区域记录→转发域名服务器→根域名服务器', '区域记录→本地缓存记录→转发域名服务器→根域名服务器', '本地缓存记录→区域记录→根域名服务器→转发域名服务器', '区域记录→本地缓存记录→根域名服务器→转发域名服务器', 'A', NULL, 2, '应用层', '3');
INSERT INTO `multi_question` VALUES (10008, '计算机网络', '在TCP/IP协议中，序号小于（  ）的端口称为熟知端口(well-known port)。', '1024', '64', '256', '128', 'A', NULL, 2, '传输层', '1');
INSERT INTO `multi_question` VALUES (10009, '计算机网络', '在Internet上用TCP/IP播放视频，想用传输层的最快协议，以减少时延，要使用（ ）', 'UDP协议的低开销特性', 'UDP协议的高开销特性', 'TCP协议的低开销特性', 'TCP协议的高开销特性', 'A', NULL, 2, '传输层', '2');
INSERT INTO `multi_question` VALUES (10010, '计算机网络', '在TCP协议中采用（ ）来区分不同的应用进程', '端口号', 'IP地址', '协议类型', 'MAC地址', 'A', NULL, 2, '传输层', '3');
INSERT INTO `multi_question` VALUES (10011, '计算机网络', '可靠的传输协议中的“可靠”指的是（ ）', '使用面向连接的会话', '使用“尽力而为”的传输', '使用滑动窗口来维持可靠性', '使用确认重传机制来确保传输的数据不丢失', 'D', NULL, 2, '传输层', '2');
INSERT INTO `multi_question` VALUES (10012, '计算机网络', '假设拥塞窗口为50KB，接收窗口为80KB，TCP能够发送的最大字节数为（ ）', '50KB', '80KB', '130KB', '30KB', 'A', NULL, 2, '传输层', '4');
INSERT INTO `multi_question` VALUES (10013, '计算机网络', '主机A向主机B发送一个（SYN=1，seq=2000）的TCP报文，期望与主机B建立连接，若主机B接受连接请求，则主机B发送的正确有TCP报文可能是（ ）', '（SYN=0,ACK=0,seq=2001,ack=2001）', '（SYN=1,ACK=1,seq=2000,ack=2000）', '•	C.（SYN=1,ACK=1,seq=2001,ack=2001）', '（SYN=0,ACK=1,seq=2000,ack=2000）', 'C', NULL, 2, '传输层', '2');
INSERT INTO `multi_question` VALUES (10014, '计算机网络', '主机A向主机B连续发送了两个TCP报文段，其序号分别为70和100。试问： （1）第一个报文段携带了（）个字节的数据？', ' 70', '30', '100', '170', 'B', NULL, 2, '传输层', '3');
INSERT INTO `multi_question` VALUES (10015, '计算机网络', 'PCM脉码调制的过程（ ）', '采样、量化、编码', '量化、编码、采样', '编码、量化、采样', '采样、编码、量化', 'A', NULL, 2, '物理层', '4');
INSERT INTO `multi_question` VALUES (10016, '计算机网络', '若某采用4相位调制的通信链路的数据传输速率为2400bps，则该链路的波特率为（）', '600Baud', '1200Baud', '4800Baud', '9600Baud', 'B', NULL, 2, '物理层', '1');
INSERT INTO `multi_question` VALUES (10017, '计算机网络', '以下关于数据传输速率的描述中，错误的是( )', '数据传输速率表示每秒钟传输构成数据代码的二进制比特数', '对于二进制数据，数据传输速率为S=1/T (bps)', '常用的数据传输速率单位有: 1Mbps=1.024×106bps', '数据传输速率是描述数据传输系统性能的重要技术指标之一', 'C', NULL, 2, '物理层', '2');
INSERT INTO `multi_question` VALUES (10018, '计算机网络', '以下关于时分多路复用概念的描述中，错误的是.(  ).', '时分多路复用将线路使用的时间分成多个时间片', '时分多路复用分为同步时分多路复用与统计时分多路复用', '时分多路复用使用“帧”与数据链路层“帧”的概念、作用是不同的', '统计时分多路复用将时间片预先分配给各个信道', 'D', NULL, 2, '物理层', '2');
INSERT INTO `multi_question` VALUES (10019, '计算机网络', '1000BASE-T标准支持的传输介质是（）', '双绞线', '同轴电缆', '光纤', '无线电', 'A', NULL, 2, '物理层', '1');
INSERT INTO `multi_question` VALUES (10020, '计算机网络', '一个以太网交换机，读取整个数据帧，对数据帧进行差错校验后再转发出去，这种交换方式称为 （）', '直通交换', '无碎片交换', '无差错交换', '存储转发交换', 'D', NULL, 2, '数据链路层', '2');
INSERT INTO `multi_question` VALUES (10021, '计算机网络', '关于VLAN，下面的描述中正确的是（）', '一个新的交换机没有配置VLAN', '通过配置VLAN减少了冲突域的数量', '一个VLAN不能跨越多个交换机', '各个VLAN属于不同的广播域', 'D', NULL, 2, '数据链路层', '2');
INSERT INTO `multi_question` VALUES (10022, '计算机网络', '以太网协议中使用物理地址作用是什么？', '.用于不同子网中的主机进行通信', '作为第二层设备的唯一标识', '用于区别第二层第三层的协议数据单元', '保存主机可检测未知的远程设备', 'B', NULL, 2, '数据链路层', '2');
INSERT INTO `multi_question` VALUES (10023, '计算机网络', '以太网采用的CSMA/CD协议，当冲突发生时要通过二进制指数后退算法计算后退延时， 关于这个算法，以下论述中错误的是 （）', '冲突次数越多，后退的时间越短', '平均后退次数的多少与负载大小有关', '后退时延的平均值与负载大小有关', '重发次数达到一定极限后放弃发送', 'A', NULL, 2, '数据链路层', '3');
INSERT INTO `multi_question` VALUES (10024, '计算机网络', '以下关于交换机获取与其端口连接设备的MAC地址的叙述中，正确的是（）', '交换机从路由表中提取设备的MAC地址', '交换机检查端口流入分组的源地址', '交换机之间互相交换地址表', '网络管理员手工输入设备的MAC地址', 'B', NULL, 2, '数据链路层', '2');
INSERT INTO `multi_question` VALUES (10025, '计算机网络', '如果G (x）为11010010，以下4个CRC校验比特序列中只有哪个可能是正确的 ？', '1101011001', '101011011', '11011011', '1011001', 'B', NULL, 2, '数据链路层', '1');
INSERT INTO `multi_question` VALUES (10026, '计算机网络', '以下关于Ethernet物理地址的描述中，错误的是', 'Ethernet物理地址又叫做MAC地址', '48位的Ethernet物理地址允许分配的地址数达到247个', '网卡的物理地址写入主机的EPROM中', '每一块网卡的物理地址在全世界是唯一的', 'C', NULL, 2, '数据链路层', '3');
INSERT INTO `multi_question` VALUES (10027, '计算机网络', '下列帧类型中，不属于HDLC帧类型的是（）', '信息帧', '确认帧', '监控帧', '无编号帧', 'B', NULL, 2, '数据链路层', '1');
INSERT INTO `multi_question` VALUES (10028, '计算机网络', '通过交换机连接的一组站点，关于它们的广播域和冲突域说法正确的是（）', '组成一个冲突域，但不是一个广播域', '组成一个广播域，但不是一个冲突域', '组成一个冲突域，也是一个广播域', '既不一个冲突域，也不是一个广播域', 'B', NULL, 2, '数据链路层', '3');
INSERT INTO `multi_question` VALUES (10029, '计算机网络', '数据链路层的数据单位是（）', '帧', '字节', '比特', '分组', 'A', NULL, 2, '数据链路层', '1');
INSERT INTO `multi_question` VALUES (10030, '计算机网络', 'LAN参考模型可分为物理层、（ ）', 'MAC，LLC等三层', 'LLC，MHS等三层', 'MAC，FTAM等三层', 'LLC，VT等三层', 'A', NULL, 2, '数据链路层', '3');
INSERT INTO `multi_question` VALUES (10031, '测试', '测试', 'A', 'B', 'C', 'D', 'B', '解析', 2, '测试', '4');
INSERT INTO `multi_question` VALUES (10032, '计算机网络', 'DNS 服务器和DHCP服务器的作用是（）', 'A', 'B', 'C', 'D', 'B', '哦解析', 2, '网络层', '2');
INSERT INTO `multi_question` VALUES (10033, 'Java程序设计', 'DNS 服务器和DHCP服务器的作用是（）', '11', '12', '13', '14', 'B', '', 2, '概论', '2');

-- ----------------------------
-- Table structure for paper_manage
-- ----------------------------
DROP TABLE IF EXISTS `paper_manage`;
CREATE TABLE `paper_manage`  (
  `paperId` int(0) NULL DEFAULT NULL COMMENT '试卷编号',
  `questionType` int(0) NULL DEFAULT NULL COMMENT '题目类型',
  `questionId` int(0) NULL DEFAULT NULL COMMENT '题目编号',
  INDEX `idx_paper_paper`(`paperId`) USING BTREE,
  INDEX `idx_paper_paper_type`(`paperId`, `questionType`) USING BTREE,
  INDEX `idx_paper_question`(`questionId`) USING BTREE,
  UNIQUE INDEX `uk_paper_question`(`paperId`, `questionType`, `questionId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '试卷管理表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of paper_manage
-- ----------------------------
INSERT INTO `paper_manage` VALUES (1001, 1, 10000);
INSERT INTO `paper_manage` VALUES (1001, 1, 10016);
INSERT INTO `paper_manage` VALUES (1001, 1, 10032);
INSERT INTO `paper_manage` VALUES (1001, 1, 10025);
INSERT INTO `paper_manage` VALUES (1001, 1, 10030);
INSERT INTO `paper_manage` VALUES (1001, 1, 10017);
INSERT INTO `paper_manage` VALUES (1001, 1, 10027);
INSERT INTO `paper_manage` VALUES (1001, 1, 10023);
INSERT INTO `paper_manage` VALUES (1001, 1, 10014);
INSERT INTO `paper_manage` VALUES (1001, 1, 10009);
INSERT INTO `paper_manage` VALUES (1001, 1, 10008);
INSERT INTO `paper_manage` VALUES (1001, 1, 10011);
INSERT INTO `paper_manage` VALUES (1001, 1, 10010);
INSERT INTO `paper_manage` VALUES (1001, 1, 10021);
INSERT INTO `paper_manage` VALUES (1001, 1, 10022);
INSERT INTO `paper_manage` VALUES (1001, 1, 10026);
INSERT INTO `paper_manage` VALUES (1001, 2, 10017);
INSERT INTO `paper_manage` VALUES (1001, 2, 10012);
INSERT INTO `paper_manage` VALUES (1001, 2, 10004);
INSERT INTO `paper_manage` VALUES (1001, 2, 10003);
INSERT INTO `paper_manage` VALUES (1001, 2, 10008);
INSERT INTO `paper_manage` VALUES (1001, 2, 10020);
INSERT INTO `paper_manage` VALUES (1001, 2, 10013);
INSERT INTO `paper_manage` VALUES (1001, 2, 10006);
INSERT INTO `paper_manage` VALUES (1001, 2, 10015);
INSERT INTO `paper_manage` VALUES (1001, 3, 10002);
INSERT INTO `paper_manage` VALUES (1001, 3, 10009);
INSERT INTO `paper_manage` VALUES (1001, 3, 10003);
INSERT INTO `paper_manage` VALUES (1001, 3, 10004);
INSERT INTO `paper_manage` VALUES (1001, 3, 10012);
INSERT INTO `paper_manage` VALUES (1001, 3, 10011);
INSERT INTO `paper_manage` VALUES (1001, 3, 10006);
INSERT INTO `paper_manage` VALUES (1001, 3, 10008);
INSERT INTO `paper_manage` VALUES (1001, 1, 10006);
INSERT INTO `paper_manage` VALUES (1001, 1, 10029);
INSERT INTO `paper_manage` VALUES (1001, 2, 10021);
INSERT INTO `paper_manage` VALUES (1001, 2, 10010);
INSERT INTO `paper_manage` VALUES (1001, 2, 10009);
INSERT INTO `paper_manage` VALUES (1010, 1, 10023);
INSERT INTO `paper_manage` VALUES (1010, 1, 10026);
INSERT INTO `paper_manage` VALUES (1010, 1, 10010);
INSERT INTO `paper_manage` VALUES (1010, 1, 10003);
INSERT INTO `paper_manage` VALUES (1010, 1, 10001);
INSERT INTO `paper_manage` VALUES (1010, 2, 10026);
INSERT INTO `paper_manage` VALUES (1010, 2, 10018);
INSERT INTO `paper_manage` VALUES (1010, 2, 10022);
INSERT INTO `paper_manage` VALUES (1010, 2, 10006);
INSERT INTO `paper_manage` VALUES (1010, 2, 10010);
INSERT INTO `paper_manage` VALUES (1010, 3, 10002);
INSERT INTO `paper_manage` VALUES (1010, 3, 10012);
INSERT INTO `paper_manage` VALUES (1010, 3, 10004);
INSERT INTO `paper_manage` VALUES (1010, 3, 10009);
INSERT INTO `paper_manage` VALUES (1010, 3, 10005);
INSERT INTO `paper_manage` VALUES (1010, 1, 10033);
INSERT INTO `paper_manage` VALUES (1011, 1, 10014);
INSERT INTO `paper_manage` VALUES (1011, 1, 10003);
INSERT INTO `paper_manage` VALUES (1011, 1, 10010);
INSERT INTO `paper_manage` VALUES (1011, 1, 10007);
INSERT INTO `paper_manage` VALUES (1011, 1, 10006);
INSERT INTO `paper_manage` VALUES (1011, 2, 10002);
INSERT INTO `paper_manage` VALUES (1011, 2, 10017);
INSERT INTO `paper_manage` VALUES (1011, 2, 10022);
INSERT INTO `paper_manage` VALUES (1011, 2, 10025);
INSERT INTO `paper_manage` VALUES (1011, 2, 10020);
INSERT INTO `paper_manage` VALUES (1011, 3, 10008);
INSERT INTO `paper_manage` VALUES (1011, 3, 10007);
INSERT INTO `paper_manage` VALUES (1011, 3, 10003);
INSERT INTO `paper_manage` VALUES (1011, 3, 10002);
INSERT INTO `paper_manage` VALUES (1011, 3, 10012);
INSERT INTO `paper_manage` VALUES (1012, 1, 10025);
INSERT INTO `paper_manage` VALUES (1012, 1, 10007);
INSERT INTO `paper_manage` VALUES (1012, 1, 10030);
INSERT INTO `paper_manage` VALUES (1012, 1, 10012);
INSERT INTO `paper_manage` VALUES (1012, 1, 10013);
INSERT INTO `paper_manage` VALUES (1012, 1, 10026);
INSERT INTO `paper_manage` VALUES (1012, 2, 10013);
INSERT INTO `paper_manage` VALUES (1012, 2, 10009);
INSERT INTO `paper_manage` VALUES (1012, 2, 10010);
INSERT INTO `paper_manage` VALUES (1012, 2, 10022);
INSERT INTO `paper_manage` VALUES (1012, 2, 10011);
INSERT INTO `paper_manage` VALUES (1012, 2, 10015);
INSERT INTO `paper_manage` VALUES (1012, 2, 10018);
INSERT INTO `paper_manage` VALUES (1012, 2, 10025);
INSERT INTO `paper_manage` VALUES (1012, 2, 10005);
INSERT INTO `paper_manage` VALUES (1012, 2, 10026);
INSERT INTO `paper_manage` VALUES (1012, 2, 10006);
INSERT INTO `paper_manage` VALUES (1012, 2, 10017);
INSERT INTO `paper_manage` VALUES (1012, 3, 10008);
INSERT INTO `paper_manage` VALUES (1012, 3, 10002);
INSERT INTO `paper_manage` VALUES (1012, 3, 10007);
INSERT INTO `paper_manage` VALUES (1012, 3, 10006);
INSERT INTO `paper_manage` VALUES (1012, 3, 10004);
INSERT INTO `paper_manage` VALUES (1012, 3, 10005);
INSERT INTO `paper_manage` VALUES (1012, 3, 10003);
INSERT INTO `paper_manage` VALUES (1012, 3, 10012);
INSERT INTO `paper_manage` VALUES (1012, 3, 10009);
INSERT INTO `paper_manage` VALUES (1001, 1, 10024);
INSERT INTO `paper_manage` VALUES (1001, 1, 10003);
INSERT INTO `paper_manage` VALUES (1001, 2, 10001);
INSERT INTO `paper_manage` VALUES (1001, 3, 10005);
INSERT INTO `paper_manage` VALUES (1013, 1, 10008);
INSERT INTO `paper_manage` VALUES (1013, 1, 10018);
INSERT INTO `paper_manage` VALUES (1013, 1, 10027);
INSERT INTO `paper_manage` VALUES (1013, 1, 10000);
INSERT INTO `paper_manage` VALUES (1013, 1, 10016);
INSERT INTO `paper_manage` VALUES (1013, 2, 10012);
INSERT INTO `paper_manage` VALUES (1013, 2, 10005);
INSERT INTO `paper_manage` VALUES (1013, 2, 10002);
INSERT INTO `paper_manage` VALUES (1013, 2, 10013);
INSERT INTO `paper_manage` VALUES (1013, 3, 10011);
INSERT INTO `paper_manage` VALUES (1013, 3, 10008);
INSERT INTO `paper_manage` VALUES (1013, 3, 10001);
INSERT INTO `paper_manage` VALUES (1013, 3, 10009);
INSERT INTO `paper_manage` VALUES (1013, 3, 10002);
INSERT INTO `paper_manage` VALUES (1013, 3, 10004);
INSERT INTO `paper_manage` VALUES (1013, 3, 10012);
INSERT INTO `paper_manage` VALUES (1013, 3, 10006);
INSERT INTO `paper_manage` VALUES (1013, 3, 10007);
INSERT INTO `paper_manage` VALUES (1013, 3, 10003);
INSERT INTO `paper_manage` VALUES (1013, 3, 10005);
INSERT INTO `paper_manage` VALUES (1015, 1, 10005);
INSERT INTO `paper_manage` VALUES (1015, 1, 10012);
INSERT INTO `paper_manage` VALUES (1015, 2, 10015);
INSERT INTO `paper_manage` VALUES (1015, 2, 10017);
INSERT INTO `paper_manage` VALUES (1015, 3, 10010);
INSERT INTO `paper_manage` VALUES (1015, 3, 10001);
INSERT INTO `paper_manage` VALUES (1015, 1, 10015);
INSERT INTO `paper_manage` VALUES (1015, 1, 10001);
INSERT INTO `paper_manage` VALUES (1015, 1, 10006);

-- ----------------------------
-- Table structure for replay
-- ----------------------------
DROP TABLE IF EXISTS `replay`;
CREATE TABLE `replay`  (
  `messageId` int(0) NULL DEFAULT NULL COMMENT '留言编号',
  `replayId` int(0) NOT NULL AUTO_INCREMENT COMMENT '回复编号',
  `replay` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '内容',
  `replayTime` date NULL DEFAULT NULL COMMENT '回复时间',
  PRIMARY KEY (`replayId`) USING BTREE,
  INDEX `idx_replay_message`(`messageId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '回复表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of replay
-- ----------------------------
INSERT INTO `replay` VALUES (37, 22, '真的难', '2023-04-04');
INSERT INTO `replay` VALUES (37, 23, '同感', '2023-04-04');

-- ----------------------------
-- Table structure for score
-- ----------------------------
DROP TABLE IF EXISTS `score`;
CREATE TABLE `score`  (
  `scoreId` int(0) NOT NULL AUTO_INCREMENT COMMENT '分数编号',
  `examCode` int(0) NULL DEFAULT NULL COMMENT '作业编号',
  `studentId` int(0) NULL DEFAULT NULL COMMENT '学号',
  `subject` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '课程名称',
  `ptScore` int(0) NULL DEFAULT NULL COMMENT '是否及格，0不及格，1及格',
  `etScore` int(0) NULL DEFAULT NULL COMMENT '成绩',
  `dScore` int(0) NULL DEFAULT 0 COMMENT '主观题成绩',
  `score` int(0) NULL DEFAULT NULL COMMENT '试卷满分',
  `answerDate` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '答题日期',
  PRIMARY KEY (`scoreId`) USING BTREE,
  UNIQUE INDEX `uk_score_exam_student`(`examCode`, `studentId`) USING BTREE,
  INDEX `idx_score_exam`(`examCode`) USING BTREE,
  INDEX `idx_score_student`(`studentId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 149 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '成绩管理表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of score
-- ----------------------------
INSERT INTO `score` VALUES (153, 20190024, 20224084, '计算机网络', 1, 12, 0, 18, '2023-11-18');
INSERT INTO `score` VALUES (154, 20230001, 20224084, '计算机网络', 0, 6, 0, 86, '2023-11-18');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `studentId` int(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `studentName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '姓名',
  `grade` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '年级',
  `major` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '专业',
  `clazz` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '班级',
  `institute` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '学院',
  `tel` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '电话号码',
  `email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '电子邮件',
  `pwd` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '密码',
  `cardId` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '身份证号',
  `sex` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '性别',
  `role` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '2' COMMENT '角色(0管理员，1教师，2学生)',
  PRIMARY KEY (`studentId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20225009 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '学生信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (20224001, '王六', '2023', '计算机科学与技术', '2', '软件工程学院', '13585411132', 'gblw2331@163.com', '$2a$10$WqcbjOiItLfnRA.wog3mu.F7lvAAqcK1zXX4yaLAEwVYns0dzzx0C', '500234597988722353', '男', '2');
INSERT INTO `student` VALUES (20224084, '张三', '2023', '计算机科学与技术', '2', '软件工程学院', '13658555557', '13658555557@sina.cn', '$2a$10$WqcbjOiItLfnRA.wog3mu.F7lvAAqcK1zXX4yaLAEwVYns0dzzx0C', '500574111114022353', '男', '2');
INSERT INTO `student` VALUES (20225003, '李四', '2023', '信息工程', '1', '软件工程学院', '15512345425', '15512345425@163.com', '$2a$10$WqcbjOiItLfnRA.wog3mu.F7lvAAqcK1zXX4yaLAEwVYns0dzzx0C', '500237781114022353', '男', '2');
INSERT INTO `student` VALUES (20225007, '王五', '2023', '网络工程', '1', '软件工程学院', '18777778457', '187777457@163.com', '$2a$10$WqcbjOiItLfnRA.wog3mu.F7lvAAqcK1zXX4yaLAEwVYns0dzzx0C', '500234565548022353', '女', '2');
INSERT INTO `student` VALUES (20225008, '徐七', '2023', '信息工程', '2', '软件工程学院', '15111619564', '1511164@163.com', '$2a$10$WqcbjOiItLfnRA.wog3mu.F7lvAAqcK1zXX4yaLAEwVYns0dzzx0C', '500234111114022353', '男', '2');
INSERT INTO `student` VALUES (20225009, '郑八', '2023', '软件工程', '1班', '计算机学院', '13355555555', '123@qq.com', '$2a$10$WqcbjOiItLfnRA.wog3mu.F7lvAAqcK1zXX4yaLAEwVYns0dzzx0C', '445444444444444444', '男', '2');

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `teacherId` int(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `teacherName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '姓名',
  `institute` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '学院',
  `sex` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '性别',
  `tel` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '电话号码',
  `email` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '邮箱',
  `pwd` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '密码',
  `cardId` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '身份证号',
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '职称',
  `role` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '角色（0管理员，1教师，2学生）',
  PRIMARY KEY (`teacherId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20081006 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '教师信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES (20081001, '张小风', '软件工程学院', '男', '13500008441', '1322@163.com', '$2a$10$WqcbjOiItLfnRA.wog3mu.F7lvAAqcK1zXX4yaLAEwVYns0dzzx0C', '465111111111111111', '讲师', '1');
INSERT INTO `teacher` VALUES (20081002, '王大志', '软件工程学院', '男', '12312213128', '1231221312@163.com', '$2a$10$WqcbjOiItLfnRA.wog3mu.F7lvAAqcK1zXX4yaLAEwVYns0dzzx0C', '455111111111111233', '讲师', '1');
INSERT INTO `teacher` VALUES (20081003, '李四', '计算机学院', '男', '13333333333', NULL, '$2a$10$WqcbjOiItLfnRA.wog3mu.F7lvAAqcK1zXX4yaLAEwVYns0dzzx0C', '441111111111111111', '教师', '1');
INSERT INTO `teacher` VALUES (20081005, '李白', '计算机学院', '女', '15877777777', NULL, '$2a$10$WqcbjOiItLfnRA.wog3mu.F7lvAAqcK1zXX4yaLAEwVYns0dzzx0C', '445111111111111111', '普通人', '1');
INSERT INTO `teacher` VALUES (20081006, '测试', '测试', '测试', '测试', NULL, '$2a$10$7PpjaUkZLSV4abgr9DO.peT4w.Ic/oPV77d38HzM716DaXCx2IU1O', '测试', '测试', '1');

SET FOREIGN_KEY_CHECKS = 1;

-- ============================================================
-- 演示数据补充：题库扩容 / 大题 / 试卷关联 / 成绩 / 留言 / 作答
-- 说明：ID 段 20001 起，避免与既有自增数据冲突
-- ============================================================
INSERT INTO `multi_question` (questionId, subject, question, answerA, answerB, answerC, answerD, rightAnswer, analysis, score, section, level) VALUES (20001, '数据结构', '栈的特点是（ ）', '先进先出', '后进先出', '随机存取', '双端插入', 'B', '', 2, '基础', '2');
INSERT INTO `multi_question` (questionId, subject, question, answerA, answerB, answerC, answerD, rightAnswer, analysis, score, section, level) VALUES (20002, '数据结构', '二叉树的遍历中，可以得到有序序列的是（ ）', '先序', '中序（BST）', '后序', '层序', 'B', '', 2, '基础', '2');
INSERT INTO `multi_question` (questionId, subject, question, answerA, answerB, answerC, answerD, rightAnswer, analysis, score, section, level) VALUES (20003, '数据结构', '时间复杂度为 O(nlogn) 的排序是（ ）', '冒泡排序', '插入排序', '归并排序', '选择排序', 'C', '', 2, '基础', '2');
INSERT INTO `multi_question` (questionId, subject, question, answerA, answerB, answerC, answerD, rightAnswer, analysis, score, section, level) VALUES (20004, '数据结构', '哈希表解决冲突的常用方法不包括（ ）', '开放定址法', '链地址法', '再哈希法', '二分查找法', 'D', '', 2, '基础', '2');
INSERT INTO `multi_question` (questionId, subject, question, answerA, answerB, answerC, answerD, rightAnswer, analysis, score, section, level) VALUES (20005, '数据结构', '图的广度优先遍历通常借助（ ）实现', '栈', '队列', '堆', '树', 'B', '', 2, '基础', '2');
INSERT INTO `multi_question` (questionId, subject, question, answerA, answerB, answerC, answerD, rightAnswer, analysis, score, section, level) VALUES (20006, '数据结构', 'n 个结点的完全二叉树高度为（ ）', 'n', 'log2n 向上取整 + 1', 'n/2', '2n', 'B', '', 2, '基础', '2');
INSERT INTO `multi_question` (questionId, subject, question, answerA, answerB, answerC, answerD, rightAnswer, analysis, score, section, level) VALUES (20007, '数据库理论', '下列属于 DDL 语句的是（ ）', 'SELECT', 'INSERT', 'CREATE', 'UPDATE', 'C', '', 2, '基础', '2');
INSERT INTO `multi_question` (questionId, subject, question, answerA, answerB, answerC, answerD, rightAnswer, analysis, score, section, level) VALUES (20008, '数据库理论', '事务的 ACID 中 I 表示（ ）', '原子性', '一致性', '隔离性', '持久性', 'C', '', 2, '基础', '2');
INSERT INTO `multi_question` (questionId, subject, question, answerA, answerB, answerC, answerD, rightAnswer, analysis, score, section, level) VALUES (20009, '数据库理论', '消除非主属性对码的部分依赖属于（ ）', '1NF', '2NF', '3NF', 'BCNF', 'B', '', 2, '基础', '2');
INSERT INTO `multi_question` (questionId, subject, question, answerA, answerB, answerC, answerD, rightAnswer, analysis, score, section, level) VALUES (20010, '数据库理论', '用于分组统计结果的关键字是（ ）', 'ORDER BY', 'GROUP BY', 'HAVING', 'DISTINCT', 'B', '', 2, '基础', '2');
INSERT INTO `multi_question` (questionId, subject, question, answerA, answerB, answerC, answerD, rightAnswer, analysis, score, section, level) VALUES (20011, '数据库理论', '下列会触发唯一约束冲突的是（ ）', '插入重复主键', '查询空值', '更新非索引列', '删除不存在行', 'A', '', 2, '基础', '2');
INSERT INTO `multi_question` (questionId, subject, question, answerA, answerB, answerC, answerD, rightAnswer, analysis, score, section, level) VALUES (20012, '数据库理论', '视图的主要作用是（ ）', '提高写入速度', '简化查询与控制访问', '自动建索引', '压缩数据', 'B', '', 2, '基础', '2');
INSERT INTO `multi_question` (questionId, subject, question, answerA, answerB, answerC, answerD, rightAnswer, analysis, score, section, level) VALUES (20013, '操作系统', '进程与线程的本质区别是（ ）', '是否占用内存', '是否拥有独立地址空间', '是否有优先级', '是否可并发', 'B', '', 2, '基础', '2');
INSERT INTO `multi_question` (questionId, subject, question, answerA, answerB, answerC, answerD, rightAnswer, analysis, score, section, level) VALUES (20014, '操作系统', '产生死锁的四个必要条件不包括（ ）', '互斥', '请求与保持', '可剥夺', '循环等待', 'C', '', 2, '基础', '2');
INSERT INTO `multi_question` (questionId, subject, question, answerA, answerB, answerC, answerD, rightAnswer, analysis, score, section, level) VALUES (20015, '操作系统', '页面置换算法中可能出现 Belady 异常的是（ ）', 'LRU', 'FIFO', 'OPT', 'CLOCK', 'B', '', 2, '基础', '2');
INSERT INTO `multi_question` (questionId, subject, question, answerA, answerB, answerC, answerD, rightAnswer, analysis, score, section, level) VALUES (20016, '操作系统', '时间片轮转调度适用于（ ）', '批处理系统', '分时系统', '实时系统', '单道系统', 'B', '', 2, '基础', '2');
INSERT INTO `multi_question` (questionId, subject, question, answerA, answerB, answerC, answerD, rightAnswer, analysis, score, section, level) VALUES (20017, '操作系统', '临界区问题的解决必须满足（ ）', '忙等待', '互斥、前进、有限等待', '优先级反转', '可剥夺', 'B', '', 2, '基础', '2');
INSERT INTO `multi_question` (questionId, subject, question, answerA, answerB, answerC, answerD, rightAnswer, analysis, score, section, level) VALUES (20018, '操作系统', '文件系统中 inode 主要记录（ ）', '文件内容', '文件元数据', '磁盘分区表', '进程信息', 'B', '', 2, '基础', '2');
INSERT INTO `multi_question` (questionId, subject, question, answerA, answerB, answerC, answerD, rightAnswer, analysis, score, section, level) VALUES (20019, '软件工程', '瀑布模型的主要缺点是（ ）', '无法编码', '需求变更代价大', '不支持测试', '没有文档', 'B', '', 2, '基础', '2');
INSERT INTO `multi_question` (questionId, subject, question, answerA, answerB, answerC, answerD, rightAnswer, analysis, score, section, level) VALUES (20020, '软件工程', '敏捷开发强调（ ）', '详尽前期设计', '快速迭代与反馈', '一次性交付', '固定需求', 'B', '', 2, '基础', '2');
INSERT INTO `multi_question` (questionId, subject, question, answerA, answerB, answerC, answerD, rightAnswer, analysis, score, section, level) VALUES (20021, '软件工程', '单元测试通常由（ ）编写', '用户', '开发人员', '运维', '产品经理', 'B', '', 2, '基础', '2');
INSERT INTO `multi_question` (questionId, subject, question, answerA, answerB, answerC, answerD, rightAnswer, analysis, score, section, level) VALUES (20022, '软件工程', 'UML 中表示用例之间包含关系的是（ ）', '泛化', '包含', '聚合', '组合', 'B', '', 2, '基础', '2');
INSERT INTO `multi_question` (questionId, subject, question, answerA, answerB, answerC, answerD, rightAnswer, analysis, score, section, level) VALUES (20023, '软件工程', '代码评审的主要目的是（ ）', '提高编译速度', '尽早发现缺陷', '压缩代码量', '增加注释', 'B', '', 2, '基础', '2');
INSERT INTO `multi_question` (questionId, subject, question, answerA, answerB, answerC, answerD, rightAnswer, analysis, score, section, level) VALUES (20024, '软件工程', '持续集成的核心实践是（ ）', '手工回归', '频繁合并并自动构建测试', '季度发布', '离线打包', 'B', '', 2, '基础', '2');
INSERT INTO `fill_question` (questionId, subject, question, answer, analysis, score, level, section) VALUES (20001, '数据结构', '顺序表中插入元素平均需移动约____个元素', 'n/2', '', 2, '2', '基础');
INSERT INTO `fill_question` (questionId, subject, question, answer, analysis, score, level, section) VALUES (20002, '数据结构', '深度优先遍历可借助____实现', '栈', '', 2, '2', '基础');
INSERT INTO `fill_question` (questionId, subject, question, answer, analysis, score, level, section) VALUES (20003, '数据结构', '快速排序平均时间复杂度为____', 'O(nlogn)', '', 2, '2', '基础');
INSERT INTO `fill_question` (questionId, subject, question, answer, analysis, score, level, section) VALUES (20004, '数据结构', '满二叉树第 k 层最多有____个结点', '2^(k-1)', '', 2, '2', '基础');
INSERT INTO `fill_question` (questionId, subject, question, answer, analysis, score, level, section) VALUES (20005, '数据库理论', 'SQL 中用于去重的关键字是____', 'DISTINCT', '', 2, '2', '基础');
INSERT INTO `fill_question` (questionId, subject, question, answer, analysis, score, level, section) VALUES (20006, '数据库理论', '事务提交使用____语句', 'COMMIT', '', 2, '2', '基础');
INSERT INTO `fill_question` (questionId, subject, question, answer, analysis, score, level, section) VALUES (20007, '数据库理论', '外键用于保证____完整性', '参照', '', 2, '2', '基础');
INSERT INTO `fill_question` (questionId, subject, question, answer, analysis, score, level, section) VALUES (20008, '数据库理论', '索引可以显著加快____操作', '查询', '', 2, '2', '基础');
INSERT INTO `fill_question` (questionId, subject, question, answer, analysis, score, level, section) VALUES (20009, '操作系统', '进程的三种基本状态是就绪、运行和____', '阻塞', '', 2, '2', '基础');
INSERT INTO `fill_question` (questionId, subject, question, answer, analysis, score, level, section) VALUES (20010, '操作系统', '信号量的值小于 0 表示有进程在____', '等待', '', 2, '2', '基础');
INSERT INTO `fill_question` (questionId, subject, question, answer, analysis, score, level, section) VALUES (20011, '操作系统', '虚拟内存的核心是____技术', '分页', '', 2, '2', '基础');
INSERT INTO `fill_question` (questionId, subject, question, answer, analysis, score, level, section) VALUES (20012, '操作系统', '死锁预防中破坏循环等待可用____分配法', '资源有序', '', 2, '2', '基础');
INSERT INTO `fill_question` (questionId, subject, question, answer, analysis, score, level, section) VALUES (20013, '软件工程', '软件生命周期包含需求、设计、____、测试和维护', '编码', '', 2, '2', '基础');
INSERT INTO `fill_question` (questionId, subject, question, answer, analysis, score, level, section) VALUES (20014, '软件工程', '衡量模块独立性的两个指标是耦合和____', '内聚', '', 2, '2', '基础');
INSERT INTO `fill_question` (questionId, subject, question, answer, analysis, score, level, section) VALUES (20015, '软件工程', '回归测试用于验证____未被破坏', '既有功能', '', 2, '2', '基础');
INSERT INTO `fill_question` (questionId, subject, question, answer, analysis, score, level, section) VALUES (20016, '软件工程', '版本控制工具 Git 中提交命令是____', 'git commit', '', 2, '2', '基础');
INSERT INTO `judge_question` (questionId, subject, question, answer, analysis, score, level, section) VALUES (20001, '数据结构', '栈支持先进先出', '×', '', 2, '2', '基础');
INSERT INTO `judge_question` (questionId, subject, question, answer, analysis, score, level, section) VALUES (20002, '数据结构', '平衡二叉树查找最坏为 O(logn)', '√', '', 2, '2', '基础');
INSERT INTO `judge_question` (questionId, subject, question, answer, analysis, score, level, section) VALUES (20003, '数据结构', '队列只能在队尾插入', '√', '', 2, '2', '基础');
INSERT INTO `judge_question` (questionId, subject, question, answer, analysis, score, level, section) VALUES (20004, '数据结构', '哈希表查找一定为 O(1)', '×', '', 2, '2', '基础');
INSERT INTO `judge_question` (questionId, subject, question, answer, analysis, score, level, section) VALUES (20005, '数据库理论', '主键可以为 NULL', '×', '', 2, '2', '基础');
INSERT INTO `judge_question` (questionId, subject, question, answer, analysis, score, level, section) VALUES (20006, '数据库理论', '视图是虚表，不存储数据', '√', '', 2, '2', '基础');
INSERT INTO `judge_question` (questionId, subject, question, answer, analysis, score, level, section) VALUES (20007, '数据库理论', 'DELETE 会删除表结构', '×', '', 2, '2', '基础');
INSERT INTO `judge_question` (questionId, subject, question, answer, analysis, score, level, section) VALUES (20008, '数据库理论', '索引越多写入越快', '×', '', 2, '2', '基础');
INSERT INTO `judge_question` (questionId, subject, question, answer, analysis, score, level, section) VALUES (20009, '操作系统', '线程切换开销小于进程切换', '√', '', 2, '2', '基础');
INSERT INTO `judge_question` (questionId, subject, question, answer, analysis, score, level, section) VALUES (20010, '操作系统', '死锁一定可以由系统自动解除', '×', '', 2, '2', '基础');
INSERT INTO `judge_question` (questionId, subject, question, answer, analysis, score, level, section) VALUES (20011, '操作系统', '分页会产生内部碎片', '√', '', 2, '2', '基础');
INSERT INTO `judge_question` (questionId, subject, question, answer, analysis, score, level, section) VALUES (20012, '操作系统', '实时系统更关注响应时间', '√', '', 2, '2', '基础');
INSERT INTO `judge_question` (questionId, subject, question, answer, analysis, score, level, section) VALUES (20013, '软件工程', '需求分析阶段可以忽略非功能需求', '×', '', 2, '2', '基础');
INSERT INTO `judge_question` (questionId, subject, question, answer, analysis, score, level, section) VALUES (20014, '软件工程', '单元测试应覆盖边界值', '√', '', 2, '2', '基础');
INSERT INTO `judge_question` (questionId, subject, question, answer, analysis, score, level, section) VALUES (20015, '软件工程', '重构会改变外部行为', '×', '', 2, '2', '基础');
INSERT INTO `judge_question` (questionId, subject, question, answer, analysis, score, level, section) VALUES (20016, '软件工程', '代码评审只关注格式', '×', '', 2, '2', '基础');
INSERT INTO `da_question` (questionId, subject, question, answer, analysis, score, level, section) VALUES (20001, '数据结构', '简述栈与队列的区别，并各举一个应用场景。', '栈后进先出，用于表达式求值/函数调用；队列先进先出，用于任务调度/BFS。', '从出入顺序与应用两方面作答', 5, '3', '综合');
INSERT INTO `da_question` (questionId, subject, question, answer, analysis, score, level, section) VALUES (20002, '数据结构', '说明快速排序的最坏时间复杂度及其出现条件。', '最坏 O(n^2)，出现在每次划分极不均衡时（如已有序且取端点为基准）。', '需说明划分不均衡', 5, '3', '综合');
INSERT INTO `da_question` (questionId, subject, question, answer, analysis, score, level, section) VALUES (20003, '数据库理论', '请说明事务的 ACID 含义，并举例说明隔离性的作用。', '原子性、一致性、隔离性、持久性；隔离性避免脏读/不可重复读/幻读。', '四点齐全并举例', 5, '3', '综合');
INSERT INTO `da_question` (questionId, subject, question, answer, analysis, score, level, section) VALUES (20004, '数据库理论', '什么是数据库第三范式？请举例说明如何将表规范到 3NF。', '在 2NF 基础上消除非主属性对码的传递依赖。', '说明传递依赖', 5, '3', '综合');
INSERT INTO `da_question` (questionId, subject, question, answer, analysis, score, level, section) VALUES (20005, '操作系统', '请说明进程与线程的区别，并说明何时优先使用线程。', '线程共享地址空间、切换开销小；I/O 密集或需并发处理时优先线程。', '从资源与调度说明', 5, '3', '综合');
INSERT INTO `da_question` (questionId, subject, question, answer, analysis, score, level, section) VALUES (20006, '操作系统', '解释死锁产生的四个必要条件，并给出两种预防策略。', '互斥、请求与保持、不可剥夺、循环等待；可用资源有序分配、一次性分配。', '条件与策略对应', 5, '3', '综合');
INSERT INTO `da_question` (questionId, subject, question, answer, analysis, score, level, section) VALUES (20007, '软件工程', '简述敏捷开发与瀑布模型的主要差异。', '瀑布阶段顺序推进、变更成本高；敏捷迭代交付、持续反馈。', '从迭代与反馈说明', 5, '3', '综合');
INSERT INTO `da_question` (questionId, subject, question, answer, analysis, score, level, section) VALUES (20008, '软件工程', '说明单元测试、集成测试与系统测试的区别。', '分别验证单元、模块接口、整体系统功能与性能。', '三层测试范围', 5, '3', '综合');
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1002, 1, 20019);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1002, 1, 20020);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1002, 1, 20021);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1002, 1, 20022);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1002, 1, 20023);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1002, 2, 20011);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1002, 2, 20012);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1002, 2, 20013);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1002, 3, 20011);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1002, 3, 20012);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1003, 1, 20020);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1003, 1, 20021);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1003, 1, 20022);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1003, 1, 20023);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1003, 1, 20024);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1003, 2, 20012);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1003, 2, 20013);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1003, 2, 20014);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1003, 3, 20012);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1003, 3, 20013);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1004, 1, 20021);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1004, 1, 20022);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1004, 1, 20023);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1004, 1, 20024);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1004, 1, 20001);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1004, 2, 20013);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1004, 2, 20014);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1004, 2, 20015);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1004, 3, 20013);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1004, 3, 20014);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1005, 1, 20022);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1005, 1, 20023);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1005, 1, 20024);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1005, 1, 20001);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1005, 1, 20002);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1005, 2, 20014);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1005, 2, 20015);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1005, 2, 20016);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1005, 3, 20014);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1005, 3, 20015);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1006, 1, 20023);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1006, 1, 20024);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1006, 1, 20001);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1006, 1, 20002);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1006, 1, 20003);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1006, 2, 20015);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1006, 2, 20016);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1006, 2, 20001);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1006, 3, 20015);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1006, 3, 20016);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1007, 1, 20024);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1007, 1, 20001);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1007, 1, 20002);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1007, 1, 20003);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1007, 1, 20004);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1007, 2, 20016);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1007, 2, 20001);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1007, 2, 20002);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1007, 3, 20016);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1007, 3, 20001);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1008, 1, 20001);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1008, 1, 20002);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1008, 1, 20003);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1008, 1, 20004);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1008, 1, 20005);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1008, 2, 20001);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1008, 2, 20002);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1008, 2, 20003);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1008, 3, 20001);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1008, 3, 20002);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1009, 1, 20002);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1009, 1, 20003);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1009, 1, 20004);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1009, 1, 20005);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1009, 1, 20006);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1009, 2, 20002);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1009, 2, 20003);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1009, 2, 20004);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1009, 3, 20002);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1009, 3, 20003);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1014, 1, 20007);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1014, 1, 20008);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1014, 1, 20009);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1014, 1, 20010);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1014, 1, 20011);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1014, 2, 20007);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1014, 2, 20008);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1014, 2, 20009);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1014, 3, 20007);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1014, 3, 20008);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1016, 1, 20009);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1016, 1, 20010);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1016, 1, 20011);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1016, 1, 20012);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1016, 1, 20013);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1016, 2, 20009);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1016, 2, 20010);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1016, 2, 20011);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1016, 3, 20009);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1016, 3, 20010);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1017, 1, 20010);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1017, 1, 20011);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1017, 1, 20012);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1017, 1, 20013);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1017, 1, 20014);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1017, 2, 20010);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1017, 2, 20011);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1017, 2, 20012);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1017, 3, 20010);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1017, 3, 20011);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1018, 1, 20011);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1018, 1, 20012);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1018, 1, 20013);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1018, 1, 20014);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1018, 1, 20015);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1018, 2, 20011);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1018, 2, 20012);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1018, 2, 20013);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1018, 3, 20011);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1018, 3, 20012);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1001, 4, 20001);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1001, 4, 20002);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1001, 4, 20003);
INSERT INTO `paper_manage` (paperId, questionType, questionId) VALUES (1001, 4, 20004);
INSERT INTO `score` (examCode, studentId, subject, ptScore, etScore, dScore, score, answerDate) VALUES (20230001, 20224001, '计算机网络', 0, 40, 0, 60, '2026-03-21') ON DUPLICATE KEY UPDATE ptScore = VALUES(ptScore), etScore = VALUES(etScore), dScore = VALUES(dScore), score = VALUES(score), answerDate = VALUES(answerDate);
INSERT INTO `score` (examCode, studentId, subject, ptScore, etScore, dScore, score, answerDate) VALUES (20230001, 20224084, '计算机网络', 0, 47, 3, 60, '2026-03-22') ON DUPLICATE KEY UPDATE ptScore = VALUES(ptScore), etScore = VALUES(etScore), dScore = VALUES(dScore), score = VALUES(score), answerDate = VALUES(answerDate);
INSERT INTO `score` (examCode, studentId, subject, ptScore, etScore, dScore, score, answerDate) VALUES (20230001, 20225003, '计算机网络', 0, 54, 6, 60, '2026-03-23') ON DUPLICATE KEY UPDATE ptScore = VALUES(ptScore), etScore = VALUES(etScore), dScore = VALUES(dScore), score = VALUES(score), answerDate = VALUES(answerDate);
INSERT INTO `score` (examCode, studentId, subject, ptScore, etScore, dScore, score, answerDate) VALUES (20230001, 20225007, '计算机网络', 1, 61, 9, 60, '2026-03-24') ON DUPLICATE KEY UPDATE ptScore = VALUES(ptScore), etScore = VALUES(etScore), dScore = VALUES(dScore), score = VALUES(score), answerDate = VALUES(answerDate);
INSERT INTO `score` (examCode, studentId, subject, ptScore, etScore, dScore, score, answerDate) VALUES (20230001, 20225008, '计算机网络', 1, 68, 0, 60, '2026-03-25') ON DUPLICATE KEY UPDATE ptScore = VALUES(ptScore), etScore = VALUES(etScore), dScore = VALUES(dScore), score = VALUES(score), answerDate = VALUES(answerDate);
INSERT INTO `score` (examCode, studentId, subject, ptScore, etScore, dScore, score, answerDate) VALUES (20230001, 20225009, '计算机网络', 1, 75, 3, 60, '2026-03-26') ON DUPLICATE KEY UPDATE ptScore = VALUES(ptScore), etScore = VALUES(etScore), dScore = VALUES(dScore), score = VALUES(score), answerDate = VALUES(answerDate);
INSERT INTO `score` (examCode, studentId, subject, ptScore, etScore, dScore, score, answerDate) VALUES (20230002, 20224001, '计算机网络', 1, 82, 6, 40, '2026-03-27') ON DUPLICATE KEY UPDATE ptScore = VALUES(ptScore), etScore = VALUES(etScore), dScore = VALUES(dScore), score = VALUES(score), answerDate = VALUES(answerDate);
INSERT INTO `score` (examCode, studentId, subject, ptScore, etScore, dScore, score, answerDate) VALUES (20230002, 20224084, '计算机网络', 0, 43, 9, 40, '2026-03-28') ON DUPLICATE KEY UPDATE ptScore = VALUES(ptScore), etScore = VALUES(etScore), dScore = VALUES(dScore), score = VALUES(score), answerDate = VALUES(answerDate);
INSERT INTO `score` (examCode, studentId, subject, ptScore, etScore, dScore, score, answerDate) VALUES (20230002, 20225003, '计算机网络', 0, 50, 0, 40, '2026-03-21') ON DUPLICATE KEY UPDATE ptScore = VALUES(ptScore), etScore = VALUES(etScore), dScore = VALUES(dScore), score = VALUES(score), answerDate = VALUES(answerDate);
INSERT INTO `score` (examCode, studentId, subject, ptScore, etScore, dScore, score, answerDate) VALUES (20230002, 20225007, '计算机网络', 0, 57, 3, 40, '2026-03-22') ON DUPLICATE KEY UPDATE ptScore = VALUES(ptScore), etScore = VALUES(etScore), dScore = VALUES(dScore), score = VALUES(score), answerDate = VALUES(answerDate);
INSERT INTO `score` (examCode, studentId, subject, ptScore, etScore, dScore, score, answerDate) VALUES (20230002, 20225008, '计算机网络', 1, 64, 6, 40, '2026-03-23') ON DUPLICATE KEY UPDATE ptScore = VALUES(ptScore), etScore = VALUES(etScore), dScore = VALUES(dScore), score = VALUES(score), answerDate = VALUES(answerDate);
INSERT INTO `score` (examCode, studentId, subject, ptScore, etScore, dScore, score, answerDate) VALUES (20230002, 20225009, '计算机网络', 1, 71, 9, 40, '2026-03-24') ON DUPLICATE KEY UPDATE ptScore = VALUES(ptScore), etScore = VALUES(etScore), dScore = VALUES(dScore), score = VALUES(score), answerDate = VALUES(answerDate);
INSERT INTO `score` (examCode, studentId, subject, ptScore, etScore, dScore, score, answerDate) VALUES (20230003, 20224001, '计算机网络', 1, 78, 0, 40, '2026-03-25') ON DUPLICATE KEY UPDATE ptScore = VALUES(ptScore), etScore = VALUES(etScore), dScore = VALUES(dScore), score = VALUES(score), answerDate = VALUES(answerDate);
INSERT INTO `score` (examCode, studentId, subject, ptScore, etScore, dScore, score, answerDate) VALUES (20230003, 20224084, '计算机网络', 1, 85, 3, 40, '2026-03-26') ON DUPLICATE KEY UPDATE ptScore = VALUES(ptScore), etScore = VALUES(etScore), dScore = VALUES(dScore), score = VALUES(score), answerDate = VALUES(answerDate);
INSERT INTO `score` (examCode, studentId, subject, ptScore, etScore, dScore, score, answerDate) VALUES (20230003, 20225003, '计算机网络', 0, 46, 6, 40, '2026-03-27') ON DUPLICATE KEY UPDATE ptScore = VALUES(ptScore), etScore = VALUES(etScore), dScore = VALUES(dScore), score = VALUES(score), answerDate = VALUES(answerDate);
INSERT INTO `score` (examCode, studentId, subject, ptScore, etScore, dScore, score, answerDate) VALUES (20230003, 20225007, '计算机网络', 0, 53, 9, 40, '2026-03-28') ON DUPLICATE KEY UPDATE ptScore = VALUES(ptScore), etScore = VALUES(etScore), dScore = VALUES(dScore), score = VALUES(score), answerDate = VALUES(answerDate);
INSERT INTO `score` (examCode, studentId, subject, ptScore, etScore, dScore, score, answerDate) VALUES (20230003, 20225008, '计算机网络', 1, 60, 0, 40, '2026-03-21') ON DUPLICATE KEY UPDATE ptScore = VALUES(ptScore), etScore = VALUES(etScore), dScore = VALUES(dScore), score = VALUES(score), answerDate = VALUES(answerDate);
INSERT INTO `score` (examCode, studentId, subject, ptScore, etScore, dScore, score, answerDate) VALUES (20230003, 20225009, '计算机网络', 1, 67, 3, 40, '2026-03-22') ON DUPLICATE KEY UPDATE ptScore = VALUES(ptScore), etScore = VALUES(etScore), dScore = VALUES(dScore), score = VALUES(score), answerDate = VALUES(answerDate);
INSERT INTO `score` (examCode, studentId, subject, ptScore, etScore, dScore, score, answerDate) VALUES (20230005, 20224001, '计算机网络', 1, 74, 6, 40, '2026-03-23') ON DUPLICATE KEY UPDATE ptScore = VALUES(ptScore), etScore = VALUES(etScore), dScore = VALUES(dScore), score = VALUES(score), answerDate = VALUES(answerDate);
INSERT INTO `score` (examCode, studentId, subject, ptScore, etScore, dScore, score, answerDate) VALUES (20230005, 20224084, '计算机网络', 1, 81, 9, 40, '2026-03-24') ON DUPLICATE KEY UPDATE ptScore = VALUES(ptScore), etScore = VALUES(etScore), dScore = VALUES(dScore), score = VALUES(score), answerDate = VALUES(answerDate);
INSERT INTO `score` (examCode, studentId, subject, ptScore, etScore, dScore, score, answerDate) VALUES (20230005, 20225003, '计算机网络', 0, 42, 0, 40, '2026-03-25') ON DUPLICATE KEY UPDATE ptScore = VALUES(ptScore), etScore = VALUES(etScore), dScore = VALUES(dScore), score = VALUES(score), answerDate = VALUES(answerDate);
INSERT INTO `score` (examCode, studentId, subject, ptScore, etScore, dScore, score, answerDate) VALUES (20230005, 20225007, '计算机网络', 0, 49, 3, 40, '2026-03-26') ON DUPLICATE KEY UPDATE ptScore = VALUES(ptScore), etScore = VALUES(etScore), dScore = VALUES(dScore), score = VALUES(score), answerDate = VALUES(answerDate);
INSERT INTO `score` (examCode, studentId, subject, ptScore, etScore, dScore, score, answerDate) VALUES (20230005, 20225008, '计算机网络', 0, 56, 6, 40, '2026-03-27') ON DUPLICATE KEY UPDATE ptScore = VALUES(ptScore), etScore = VALUES(etScore), dScore = VALUES(dScore), score = VALUES(score), answerDate = VALUES(answerDate);
INSERT INTO `score` (examCode, studentId, subject, ptScore, etScore, dScore, score, answerDate) VALUES (20230005, 20225009, '计算机网络', 1, 63, 9, 40, '2026-03-28') ON DUPLICATE KEY UPDATE ptScore = VALUES(ptScore), etScore = VALUES(etScore), dScore = VALUES(dScore), score = VALUES(score), answerDate = VALUES(answerDate);
INSERT INTO `score` (examCode, studentId, subject, ptScore, etScore, dScore, score, answerDate) VALUES (20230010, 20224001, '计算机网络', 1, 70, 0, 40, '2026-03-21') ON DUPLICATE KEY UPDATE ptScore = VALUES(ptScore), etScore = VALUES(etScore), dScore = VALUES(dScore), score = VALUES(score), answerDate = VALUES(answerDate);
INSERT INTO `score` (examCode, studentId, subject, ptScore, etScore, dScore, score, answerDate) VALUES (20230010, 20224084, '计算机网络', 1, 77, 3, 40, '2026-03-22') ON DUPLICATE KEY UPDATE ptScore = VALUES(ptScore), etScore = VALUES(etScore), dScore = VALUES(dScore), score = VALUES(score), answerDate = VALUES(answerDate);
INSERT INTO `score` (examCode, studentId, subject, ptScore, etScore, dScore, score, answerDate) VALUES (20230010, 20225003, '计算机网络', 1, 84, 6, 40, '2026-03-23') ON DUPLICATE KEY UPDATE ptScore = VALUES(ptScore), etScore = VALUES(etScore), dScore = VALUES(dScore), score = VALUES(score), answerDate = VALUES(answerDate);
INSERT INTO `score` (examCode, studentId, subject, ptScore, etScore, dScore, score, answerDate) VALUES (20230010, 20225007, '计算机网络', 0, 45, 9, 40, '2026-03-24') ON DUPLICATE KEY UPDATE ptScore = VALUES(ptScore), etScore = VALUES(etScore), dScore = VALUES(dScore), score = VALUES(score), answerDate = VALUES(answerDate);
INSERT INTO `score` (examCode, studentId, subject, ptScore, etScore, dScore, score, answerDate) VALUES (20230010, 20225008, '计算机网络', 0, 52, 0, 40, '2026-03-25') ON DUPLICATE KEY UPDATE ptScore = VALUES(ptScore), etScore = VALUES(etScore), dScore = VALUES(dScore), score = VALUES(score), answerDate = VALUES(answerDate);
INSERT INTO `score` (examCode, studentId, subject, ptScore, etScore, dScore, score, answerDate) VALUES (20230010, 20225009, '计算机网络', 0, 59, 3, 40, '2026-03-26') ON DUPLICATE KEY UPDATE ptScore = VALUES(ptScore), etScore = VALUES(etScore), dScore = VALUES(dScore), score = VALUES(score), answerDate = VALUES(answerDate);
INSERT INTO `score` (examCode, studentId, subject, ptScore, etScore, dScore, score, answerDate) VALUES (20230016, 20224001, '计算机网络', 1, 66, 6, 40, '2026-03-27') ON DUPLICATE KEY UPDATE ptScore = VALUES(ptScore), etScore = VALUES(etScore), dScore = VALUES(dScore), score = VALUES(score), answerDate = VALUES(answerDate);
INSERT INTO `score` (examCode, studentId, subject, ptScore, etScore, dScore, score, answerDate) VALUES (20230016, 20224084, '计算机网络', 1, 73, 9, 40, '2026-03-28') ON DUPLICATE KEY UPDATE ptScore = VALUES(ptScore), etScore = VALUES(etScore), dScore = VALUES(dScore), score = VALUES(score), answerDate = VALUES(answerDate);
INSERT INTO `score` (examCode, studentId, subject, ptScore, etScore, dScore, score, answerDate) VALUES (20230016, 20225003, '计算机网络', 1, 80, 0, 40, '2026-03-21') ON DUPLICATE KEY UPDATE ptScore = VALUES(ptScore), etScore = VALUES(etScore), dScore = VALUES(dScore), score = VALUES(score), answerDate = VALUES(answerDate);
INSERT INTO `score` (examCode, studentId, subject, ptScore, etScore, dScore, score, answerDate) VALUES (20230016, 20225007, '计算机网络', 0, 41, 3, 40, '2026-03-22') ON DUPLICATE KEY UPDATE ptScore = VALUES(ptScore), etScore = VALUES(etScore), dScore = VALUES(dScore), score = VALUES(score), answerDate = VALUES(answerDate);
INSERT INTO `score` (examCode, studentId, subject, ptScore, etScore, dScore, score, answerDate) VALUES (20230016, 20225008, '计算机网络', 0, 48, 6, 40, '2026-03-23') ON DUPLICATE KEY UPDATE ptScore = VALUES(ptScore), etScore = VALUES(etScore), dScore = VALUES(dScore), score = VALUES(score), answerDate = VALUES(answerDate);
INSERT INTO `score` (examCode, studentId, subject, ptScore, etScore, dScore, score, answerDate) VALUES (20230016, 20225009, '计算机网络', 0, 55, 9, 40, '2026-03-24') ON DUPLICATE KEY UPDATE ptScore = VALUES(ptScore), etScore = VALUES(etScore), dScore = VALUES(dScore), score = VALUES(score), answerDate = VALUES(answerDate);
INSERT INTO `message` (id, title, content, time) VALUES (3, '关于作业提交时间', '老师，作业截止前提交失败的话可以补交吗？', '2026-03-20');
INSERT INTO `message` (id, title, content, time) VALUES (4, '组卷题目重复', '同一张试卷里会不会出现重复题目？', '2026-03-21');
INSERT INTO `message` (id, title, content, time) VALUES (5, '成绩计算方式', '请问最终成绩是客观题加主观题一起算吗？', '2026-03-22');
INSERT INTO `message` (id, title, content, time) VALUES (6, '互评怎么打分', '互评时分数只能给整数吗？', '2026-03-23');
INSERT INTO `message` (id, title, content, time) VALUES (7, '练习模式有用吗', '练习模式的成绩会计入总分吗？', '2026-03-24');
INSERT INTO `message` (id, title, content, time) VALUES (8, '题目解析看不到', '有的题目没有解析，是没录入吗？', '2026-03-25');
INSERT INTO `message` (id, title, content, time) VALUES (9, '建议增加错题本', '希望以后能按错题自动汇总。', '2026-03-26');
INSERT INTO `message` (id, title, content, time) VALUES (10, '移动端适配', '手机上打开答题页体验还可以，点赞。', '2026-03-27');
INSERT INTO `replay` (messageId, replay, replayTime) VALUES (3, '可以补交，但需要任课教师同意。', '2026-03-20');
INSERT INTO `replay` (messageId, replay, replayTime) VALUES (4, '组卷时同一题目不会重复抽取。', '2026-03-21');
INSERT INTO `replay` (messageId, replay, replayTime) VALUES (5, '是的，客观题与主观题分数相加。', '2026-03-22');
INSERT INTO `replay` (messageId, replay, replayTime) VALUES (6, '目前只支持整数分。', '2026-03-23');
INSERT INTO `replay` (messageId, replay, replayTime) VALUES (7, '练习模式不计入成绩。', '2026-03-24');
INSERT INTO `replay` (messageId, replay, replayTime) VALUES (8, '部分旧题确实没有解析，已反馈补充。', '2026-03-25');
INSERT INTO `replay` (messageId, replay, replayTime) VALUES (9, '这个建议很好，已记录。', '2026-03-26');
INSERT INTO `replay` (messageId, replay, replayTime) VALUES (10, '感谢反馈。', '2026-03-27');
INSERT INTO `replay` (messageId, replay, replayTime) VALUES (4, '补充：如有重复请截图反馈。', '2026-03-22');
INSERT INTO `replay` (messageId, replay, replayTime) VALUES (5, '满分以试卷设置为准。', '2026-03-23');
INSERT INTO `replay` (messageId, replay, replayTime) VALUES (7, '可以反复练习熟悉题型。', '2026-03-24');
INSERT INTO `replay` (messageId, replay, replayTime) VALUES (9, '后续版本会考虑。', '2026-03-28');
INSERT INTO `da` (studentId, questionId, answerMe, isTag, scoreMe) VALUES (20224001, 20001, '栈是后进先出，用于函数调用；队列先进先出，用于任务调度。', 1, '5');
INSERT INTO `da` (studentId, questionId, answerMe, isTag, scoreMe) VALUES (20224001, 20002, '最坏 O(n^2)，每次划分极不均衡时出现。', 1, '4');
INSERT INTO `da` (studentId, questionId, answerMe, isTag, scoreMe) VALUES (20224001, 20003, 'ACID 分别表示原子性、一致性、隔离性、持久性。', 1, '5');
INSERT INTO `da` (studentId, questionId, answerMe, isTag, scoreMe) VALUES (20224001, 20004, '3NF 要消除非主属性对码的传递依赖。', 1, '4');
INSERT INTO `da` (studentId, questionId, answerMe, isTag, scoreMe) VALUES (20224084, 20001, '栈后进先出，队列先进先出。', 1, '4');
INSERT INTO `da` (studentId, questionId, answerMe, isTag, scoreMe) VALUES (20224084, 20002, '最坏情况是 O(n^2)。', 1, '3');
INSERT INTO `da` (studentId, questionId, answerMe, isTag, scoreMe) VALUES (20224084, 20003, '隔离性可以避免脏读。', 1, '4');
INSERT INTO `da` (studentId, questionId, answerMe, isTag, scoreMe) VALUES (20225003, 20001, '栈用于递归，队列用于缓冲。', 1, '3');
INSERT INTO `da` (studentId, questionId, answerMe, isTag, scoreMe) VALUES (20225003, 20002, '快排最坏 O(nlogn)。', 1, '2');
INSERT INTO `da` (studentId, questionId, answerMe, isTag, scoreMe) VALUES (20225007, 20003, 'ACID 是事务的四个特性。', 1, '4');
INSERT INTO `da` (studentId, questionId, answerMe, isTag, scoreMe) VALUES (20225008, 20001, '栈和队列都是线性表。', 1, '3');
INSERT INTO `da` (studentId, questionId, answerMe, isTag, scoreMe) VALUES (20225009, 20002, '取中间值为基准可以避免最坏情况。', 1, '5');

-- 按计分规则重算作业总分（选择/填空/判断 2 分，大题 5 分）
UPDATE `exam_manage` e SET e.totalScore = (
  SELECT COALESCE(SUM(CASE WHEN pm.questionType = 4 THEN 5 ELSE 2 END), 0)
  FROM paper_manage pm WHERE pm.paperId = e.paperId
);

-- 按已评分大题重算成绩表中的主观题得分
UPDATE `score` s SET s.dScore = (
  SELECT COALESCE(SUM(CAST(NULLIF(TRIM(d.scoreMe), '') AS SIGNED)), 0)
  FROM da d
  WHERE d.studentId = s.studentId
    AND d.questionId IN (
      SELECT pm.questionId FROM paper_manage pm
      JOIN exam_manage em ON em.paperId = pm.paperId
      WHERE em.examCode = s.examCode AND pm.questionType = 4)
);

-- ============================================================
-- 补充「待互评」的大题作答（isTag = 0），用于作业互评页面演示
-- ============================================================
INSERT INTO `da` (studentId, questionId, answerMe, isTag) VALUES (20224084, 20004, '3NF 需要消除非主属性对码的传递依赖，比如把学院信息单独建表。', 0) ON DUPLICATE KEY UPDATE answerMe = VALUES(answerMe), isTag = 0;
INSERT INTO `da` (studentId, questionId, answerMe, isTag) VALUES (20224084, 20005, '进程有独立地址空间，线程共享，因此线程切换更轻量。', 0) ON DUPLICATE KEY UPDATE answerMe = VALUES(answerMe), isTag = 0;
INSERT INTO `da` (studentId, questionId, answerMe, isTag) VALUES (20225003, 20005, '死锁的四个条件：互斥、请求与保持、不可剥夺、循环等待。', 0) ON DUPLICATE KEY UPDATE answerMe = VALUES(answerMe), isTag = 0;
INSERT INTO `da` (studentId, questionId, answerMe, isTag) VALUES (20225003, 20006, '敏捷强调小步迭代和持续反馈，瀑布强调阶段顺序。', 0) ON DUPLICATE KEY UPDATE answerMe = VALUES(answerMe), isTag = 0;
INSERT INTO `da` (studentId, questionId, answerMe, isTag) VALUES (20225007, 20006, '单元测试验证单个函数，集成测试验证模块之间的接口。', 0) ON DUPLICATE KEY UPDATE answerMe = VALUES(answerMe), isTag = 0;
INSERT INTO `da` (studentId, questionId, answerMe, isTag) VALUES (20225007, 20007, '栈适合表达式求值，队列适合任务排队。', 0) ON DUPLICATE KEY UPDATE answerMe = VALUES(answerMe), isTag = 0;
INSERT INTO `da` (studentId, questionId, answerMe, isTag) VALUES (20225008, 20007, '快排最坏是 O(n^2)，取中间值做基准可以缓解。', 0) ON DUPLICATE KEY UPDATE answerMe = VALUES(answerMe), isTag = 0;
INSERT INTO `da` (studentId, questionId, answerMe, isTag) VALUES (20225008, 20008, 'ACID 中的隔离性用于避免脏读和不可重复读。', 0) ON DUPLICATE KEY UPDATE answerMe = VALUES(answerMe), isTag = 0;

-- ============================================================
-- 题库数据规范化：难度等级统一为 1(易) / 2(中) / 3(难)
-- 原数据存在 4、5 等越界值，统一按最接近档位收敛
-- ============================================================
UPDATE `multi_question` SET `level` = CASE WHEN `level` IN ('1','2','3') THEN `level` ELSE '1' END;
UPDATE `fill_question`  SET `level` = CASE WHEN `level` IN ('1','2','3') THEN `level` ELSE '1' END;
UPDATE `judge_question` SET `level` = CASE WHEN `level` IN ('1','2','3') THEN `level` ELSE '1' END;
UPDATE `da_question`    SET `level` = CASE WHEN `level` IN ('1','2','3') THEN `level` ELSE '3' END;

-- 判断题答案统一为前端约定：T(正确) / F(错误)
UPDATE `judge_question` SET `answer` = CASE
  WHEN `answer` IN ('√','对','T') THEN 'T'
  WHEN `answer` IN ('×','错','F') THEN 'F'
  ELSE `answer` END;

-- 填空题多空答案统一用 '|' 分隔，去掉括号注释，便于判分
UPDATE `fill_question` SET `answer` = '通信子网|资源子网' WHERE `questionId` = 10000;
UPDATE `fill_question` SET `answer` = 'TCP|UDP' WHERE `questionId` = 10002;
UPDATE `fill_question` SET `answer` = '同轴电缆|双绞线|光纤' WHERE `questionId` = 10003;
UPDATE `fill_question` SET `answer` = 'CERnet' WHERE `questionId` = 10004;
UPDATE `fill_question` SET `answer` = '广域网|局域网|城域网' WHERE `questionId` = 10005;
UPDATE `fill_question` SET `answer` = 'POP3|SMTP' WHERE `questionId` = 10006;
UPDATE `fill_question` SET `answer` = '分组交换|电路交换' WHERE `questionId` = 10009;

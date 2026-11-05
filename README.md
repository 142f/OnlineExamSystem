# OnlineExamSystem 在线考试系统

基于 Spring Boot + Vue 前后端分离实现的在线考试系统，包含管理员、教师、学生三类角色，
支持题库管理（选择题 / 填空题 / 判断题 / 简答题）、试卷组卷、在线答题、自动与人工阅卷、成绩统计与留言反馈等功能。

## 技术栈

| 层次 | 技术 |
| --- | --- |
| 后端 | Spring Boot 2.1.2、MyBatis-Plus 3.5.0、Druid 连接池、MySQL 8、JDK 1.8 |
| 前端 | Vue 2.5、Vue Router 3、Vuex、Element UI、ECharts、Axios、Webpack 3 |
| 数据库 | MySQL（数据库名 `online_exam`） |

## 目录结构

```
OnlineExamSystem
├── OnlineExamSystemApi - idea/            # 后端 Spring Boot 工程
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/rabbiter/oes/
│       │   ├── config/                    # 跨域、异常处理、数据源等配置
│       │   ├── controller/                # 接口层
│       │   ├── entity/                    # 实体类
│       │   ├── mapper/                    # MyBatis-Plus 映射接口
│       │   ├── service/ + serviceimpl/    # 业务逻辑
│       │   ├── interceptor/               # 登录拦截器
│       │   ├── util/ vo/                  # 工具类与视图对象
│       │   └── OnlineExamSystemApplication.java
│       └── resources/
│           ├── application.properties.example   # 配置模板（复制后填写本机信息）
│           └── application.properties           # 本地配置，已被 .gitignore 忽略
├── OnlineExamSystemVue/                   # 前端 Vue 工程
│   ├── build/                             # webpack 构建脚本（源码）
│   ├── config/                            # 开发/生产环境配置
│   ├── src/                               # 组件、路由、Vuex、静态资源
│   ├── static/                            # 静态资源
│   ├── package.json
│   └── README.md
├── online_exam.sql                        # 数据库建表与初始数据脚本
├── 在线考试系统课程设计报告.docx            # 课程设计文档
└── .gitignore
```

## 环境要求

- JDK 1.8
- Maven 3.6+
- MySQL 8.0+
- Node.js 16.x（推荐 16.13.2）+ npm

## 快速开始

### 1. 初始化数据库

```sql
CREATE DATABASE online_exam DEFAULT CHARACTER SET utf8mb4;
```

导入根目录下的 `online_exam.sql`（命令行或 Navicat 等工具均可）：

```bash
mysql -u root -p online_exam < online_exam.sql
```

### 2. 启动后端

复制配置模板并填写本机的数据库账号密码：

```bash
cd "OnlineExamSystemApi - idea/src/main/resources"
copy application.properties.example application.properties   # Linux/macOS 使用 cp
```

编辑 `application.properties`：

```properties
spring.datasource.username=root
spring.datasource.password=你的数据库密码
spring.datasource.url=jdbc:mysql://localhost:3306/online_exam?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
```

然后在工程根目录执行：

```bash
mvn spring-boot:run
```

后端默认端口为 `9201`。

### 3. 启动前端

```bash
cd OnlineExamSystemVue
npm install --force          # 建议使用 Node.js 16.x
npm run dev
```

前端默认端口为 `9202`，开发环境通过 `config/index.js` 中的代理把 `/api` 转发到
`http://localhost:9201`，如修改了后端端口请同步调整该配置。

浏览器访问 <http://localhost:9202>。

### 4. 生产环境打包

```bash
cd OnlineExamSystemVue
npm run build                # 产物输出到 dist/
```


### 5. 已有数据库的升级（重要）

本仓库的 `online_exam.sql` 已补全早期导出脚本缺失的结构（`da`、`da_question` 表与 `score.dScore` 列）。
如果你使用的是历史库，请先执行以下迁移，否则 `/exams`、`/score/{page}/{size}/{studentId}` 等接口会因
`Table 'online_exam.da_question' doesn't exist` / `Unknown column 'dScore'` 报 500：

```sql
-- 密码改为 BCrypt 存储后需要 60 字符，历史库请先扩容
ALTER TABLE `admin`   MODIFY COLUMN `pwd` varchar(100) NULL COMMENT '密码(BCrypt)';
ALTER TABLE `teacher` MODIFY COLUMN `pwd` varchar(100) NULL COMMENT '密码(BCrypt)';
ALTER TABLE `student` MODIFY COLUMN `pwd` varchar(100) NULL COMMENT '密码(BCrypt)';

ALTER TABLE score ADD COLUMN dScore int(0) NULL DEFAULT 0 COMMENT '主观题成绩' AFTER etScore;

CREATE TABLE IF NOT EXISTS da_question (
  questionId int(0) NOT NULL AUTO_INCREMENT COMMENT '试题编号',
  subject    varchar(20)  NULL COMMENT '作业科目',
  question   varchar(255) NULL COMMENT '问题题目',
  answer     varchar(255) NULL COMMENT '参考答案',
  analysis   varchar(255) NULL COMMENT '题目解析',
  score      int(0) NULL DEFAULT 5 COMMENT '分数',
  section    varchar(20)  NULL COMMENT '所属章节',
  level      varchar(1)   NULL COMMENT '难度等级',
  PRIMARY KEY (questionId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='大题题库表';

CREATE TABLE IF NOT EXISTS da (
  id         int(0) NOT NULL AUTO_INCREMENT,
  studentId  int(0) NULL COMMENT '学号',
  questionId int(0) NULL COMMENT '试题编号',
  answerMe   text NULL COMMENT '学生作答内容',
  scoreMe    varchar(20) NULL COMMENT '教师评分',
  isTag      int(0) NULL DEFAULT 0 COMMENT '0待互评/1已评分/3教师重评',
  examCode   int(0) NULL COMMENT '作业编号',
  PRIMARY KEY (id),
  UNIQUE KEY uk_da_student_question (studentId, questionId),
  KEY idx_da_exam (examCode)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='大题作答表';
```

### 6. 单元测试

```bash
cd "OnlineExamSystemApi - idea"
mvn test
```

## 运行参数

| 配置项 | 默认值 | 说明 |
| --- | --- | --- |
| `server.port` | 9201 | 后端端口，需与前端 `config/index.js` 的代理目标一致 |
| `app.kill-port-on-startup` | `false` | 启动时是否结束占用上述端口的进程（仅开发用；开启后只处理 LISTENING 状态且非自身的 PID） |

## 初始账号

数据库脚本已内置演示数据，密码均为 `123456`：

| 角色 | 账号 | 说明 |
| --- | --- | --- |
| 管理员 | 9991 | 超级管理员 |
| 教师 | 20081001 等 | 教师端登录 |
| 学生 | 20224001 等 | 学生端登录 |

## 认证与权限

### 登录态

- 登录成功后，用户身份（id / 姓名 / 角色）写入**服务端 HttpSession**；前端使用的 `rb_token`、`rb_role`
  Cookie 仅用于页面路由与展示，**不再作为鉴权依据**（客户端无法通过改 Cookie 提权）。
- 会话重建：登录时会先失效旧会话再建立新会话，避免会话固定攻击。

### 密码存储

- 密码使用 **BCrypt**（`spring-security-crypto`）存储与校验，数据库中形如 `$2a$10$...`（60 字符）。
- 兼容历史数据：若库中仍是明文，登录成功后会**自动升级**为 BCrypt 密文，无需手工刷库。
- 演示账号密码仍为 `123456`（脚本中已是密文）。

### 角色与接口权限

| 角色 | 编码 | 权限范围 |
| --- | --- | --- |
| 管理员 | 0 | 全部接口 |
| 教师 | 1 | 除“管理员/教师账号管理”外的全部接口（组卷、改卷、学生管理、全员成绩等） |
| 学生 | 2 | 考试列表与详情、试卷与题目只读、本人成绩与资料、留言互动；不能管理学生/教师、不能改卷 |

- 未登录：`401` + 「登录状态已失效，请重新登录」
- 越权：`403` + 「无权限访问该资源」
- 学生的 `/student/{id}`、`/score/...` 仅允许访问本人；`PUT /studentPWD` 仅允许修改本人密码。
- 前端在 `main.js` 中统一拦截：收到 `401` 清理本地登录痕迹并跳回登录页，收到 `403` 提示「无权限访问该资源」。

### 其它防护

| 项 | 说明 |
| --- | --- |
| 登录失败限流 | 同一「账号 + 来源 IP」连续失败 5 次锁定 5 分钟，返回 `code=429`；计数在内存中，重启清空（多实例部署需换 Redis） |
| CORS 白名单 | 默认仅允许 `http://localhost:9202`、`http://127.0.0.1:9202`、`http://localhost:9201`、`http://127.0.0.1:9201`，可通过 `app.cors.allowed-origins` 覆盖；不再使用 `*` |
| Cookie 内容 | `rb_token` 改为随机串（原先写入身份证号），`JSESSIONID` 为 HttpOnly |
| SQL 日志 | 由 `StdOutImpl` 改为 `Slf4jImpl`，生产默认不打印 SQL |

## 配置与安全说明

- `src/main/resources/application.properties` 含数据库账号密码，属于本地私有配置，
  已被 `.gitignore` 排除，仓库中仅保留 `application.properties.example` 模板。
- `target/`、`dist/`、`node_modules/`、IDE 配置（`.idea/`）等生成物同样不会提交。
- 如需修改配置项，请在本地复制模板后再编辑，切勿将真实密码提交到仓库。

## 容器化部署（Docker）

仓库自带后端 / 前端 Dockerfile 与 `docker-compose.yml`，可一键拉起「MySQL + 后端 + 前端」：

```bash
# 首次启动会自动建库并导入 online_exam.sql 种子数据
docker compose up -d --build

# 查看状态 / 日志
docker compose ps
docker compose logs -f backend
```

| 服务 | 容器 | 端口 | 说明 |
| --- | --- | --- | --- |
| db | oes-db | 3307→3306 | MySQL 8，数据卷 `db_data` 持久化 |
| backend | oes-api | 9201→9201 | Spring Boot fat jar，`--spring.profiles.active=docker` |
| frontend | oes-web | 9202→80 | Nginx 托管 `dist`，`/api` 反向代理到 `backend:9201` |

可配置项（`.env` 或环境变量）：

```bash
DB_PASSWORD=your-password     # 数据库 root 密码，默认 123456
CORS_ORIGINS=http://localhost:9202
JAVA_OPTS=-Xms256m -Xmx512m
```

说明：

- 后端镜像基于 `maven:3.8.6-openjdk-8` 构建、`eclipse-temurin:8-jre` 运行，构建产物为可 `java -jar` 的 fat jar。
- 本地私有配置 `src/main/resources/application.properties` 已加入 `.dockerignore`，**不会打进镜像**；容器内使用
  `application-docker.properties` + `DB_URL`/`DB_USER`/`DB_PASSWORD` 环境变量。
- 前端镜像基于 `node:16-alpine` 构建（项目为 webpack 3，需 Node 16）、`nginx:1.25-alpine` 运行。

## 索引与查询优化

`online_exam.sql` 的建表语句已内置以下索引（历史库请执行对应 `ALTER TABLE ... ADD INDEX`）：

| 表 | 索引 | 对应查询 |
| --- | --- | --- |
| paper_manage | idx_paper_paper(paperId)、idx_paper_paper_type(paperId, questionType)、idx_paper_question(questionId) | 组卷、试卷详情、总分聚合 |
| exam_manage | idx_exam_paper(paperId) | 由 paperId 反查 examCode |
| score | idx_score_exam(examCode)、idx_score_student(studentId) | 学生成绩列表、作业成绩统计 |
| multi_question / fill_question / judge_question / da_question | idx_*_subject(subject) | 组卷随机抽题 |
| replay | idx_replay_message(messageId) | 留言回复列表 |

实测（20 万行基准表，200 次取均值）：

| 查询 | 加索引前 | 加索引后 |
| --- | --- | --- |
| `where studentId = ?` | 22.8 ms（ALL 全表扫描） | 0.029 ms（ref） |
| `where examCode = ?` | 22.9 ms（ALL） | 0.061 ms（ref） |
| `paper_manage where paperId = ?` | 55.4 ms（ALL） | 0.049 ms（ref） |


## 配置文件说明

### 前端（OnlineExamSystemVue）

| 文件 | 作用 | 关键说明 |
| --- | --- | --- |
| `.npmrc` | npm 源配置 | 原值为 `registry.npm.taobao.org`，**证书已过期**，安装依赖会报 `CERT_HAS_EXPIRED`。请改为 `registry=https://registry.npmmirror.com` |
| `package.json` | 依赖与脚本 | 运行依赖：vue 2.5 / vue-router 3 / vuex 3 / element-ui 2.4 / axios 1.7.9 / echarts 5.4.3 / js-cookie 3.0.5；`npm run dev` 启动开发服务（9202），`npm run build` 打包到 `dist/` |
| `build/webpack.base.conf.js` | webpack 基础配置 | **babel-loader 的 `include` 中必须包含 `resolve('node_modules/axios')`**，否则 axios 1.x 的 ESM 语法会导致 `Failed to compile` |
| `build/webpack.dev.conf.js` | 开发环境配置 | 端口 9202、HMR、代理见 `config/index.js` |
| `build/webpack.prod.conf.js` | 生产构建配置 | 输出到 `dist/`，含 gzip / 代码分割 |
| `config/index.js` | 环境参数 | `dev.port=9202`；`proxyTable['/api']` 指向 `http://localhost:9201`，改后端端口需同步修改 |
| `config/dev.env.js` / `prod.env.js` | 环境变量 | 仅区分 `NODE_ENV` |
| `.babelrc` | babel 预设 | vue 2 + stage-2 语法，改动会影响所有组件编译 |
| `.postcssrc.js` | 样式后处理 | autoprefixer 等 |
| `Dockerfile` / `nginx.conf` | 容器化 | 构建产物由 nginx 托管，`/api` 反向代理到 backend:9201 |
| `static/` | 静态资源目录 | webpack copy 插件依赖该目录存在（当前仅保留 `.gitkeep`） |

### 后端（OnlineExamSystemApi - idea）

| 文件 | 作用 | 关键说明 |
| --- | --- | --- |
| `src/main/resources/application.properties` | **本地私有配置** | 含数据库账号密码，已被 `.gitignore` 排除，仓库只提交 `application.properties.example` 模板 |
| `application.properties.example` | 配置模板 | 复制为 `application.properties` 后填写本机 MySQL 账号密码即可启动 |
| `application-docker.properties` | 容器专用配置 | 全部走环境变量：`DB_URL` / `DB_USER` / `DB_PASSWORD` / `CORS_ORIGINS`，不含任何真实口令 |
| `pom.xml` | Maven 构建 | Spring Boot 2.1.2 + MyBatis-Plus 3.5.0 + Druid；已配置 `spring-boot-maven-plugin` 生成可 `java -jar` 的 fat jar |
| `Dockerfile` / `.dockerignore` | 后端镜像 | 多阶段构建；`.dockerignore` 排除了含真实密码的本地 `application.properties` |

### 根目录

| 文件 | 作用 | 关键说明 |
| --- | --- | --- |
| `online_exam.sql` | 数据库脚本 | 建库建表 + 索引 + 种子数据 + 演示数据；末尾含幂等的归一化 `UPDATE`（难度等级、判断题 T/F、填空多空 `\|` 分隔、总分重算） |
| `docker-compose.yml` | 一键部署 | db(3307) / backend(9201) / frontend(9202)；数据库密码通过 `DB_PASSWORD` 环境变量覆盖 |
| `.gitignore` | 忽略规则 | 排除依赖、构建产物、IDE 配置、本地私有配置与密钥 |
| `sql/migrations/` | 历史库迁移脚本 | 去重、加唯一键、utf8mb4 转换等一次性脚本 |

### 常见问题

1. **安装依赖失败（CERT_HAS_EXPIRED）**：`.npmrc` 中的淘宝源证书过期，改用
   `npm install --legacy-peer-deps --registry https://registry.npmmirror.com`。
2. **dev 编译报 axios 语法错误**：确认 `build/webpack.base.conf.js` 的 babel `include` 包含 `node_modules/axios`，并重启 dev server。
3. **图表空白**：echarts 5 需使用命名空间导入 `import * as echarts from 'echarts'`。

## 许可证

本项目仅用于学习与课程设计交流。
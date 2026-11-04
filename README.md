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

## 初始账号

数据库脚本已内置演示数据，密码均为 `123456`：

| 角色 | 账号 | 说明 |
| --- | --- | --- |
| 管理员 | 9991 | 超级管理员 |
| 教师 | 20081001 等 | 教师端登录 |
| 学生 | 20224001 等 | 学生端登录 |

## 配置与安全说明

- `src/main/resources/application.properties` 含数据库账号密码，属于本地私有配置，
  已被 `.gitignore` 排除，仓库中仅保留 `application.properties.example` 模板。
- `target/`、`dist/`、`node_modules/`、IDE 配置（`.idea/`）等生成物同样不会提交。
- 如需修改配置项，请在本地复制模板后再编辑，切勿将真实密码提交到仓库。

## 许可证

本项目仅用于学习与课程设计交流。

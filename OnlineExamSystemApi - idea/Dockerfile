# ---------- 构建阶段 ----------
FROM maven:3.8.6-openjdk-8 AS build
WORKDIR /build

# 先拷 pom，利用 Docker 层缓存减少重复下载依赖
COPY pom.xml .
RUN mvn -B -ntp -q dependency:resolve || true

COPY src ./src
RUN mvn -B -ntp -DskipTests package

# ---------- 运行阶段 ----------
FROM eclipse-temurin:8-jre
WORKDIR /app

# 时区与语言，避免中文乱码与 UTC 时间偏差
ENV TZ=Asia/Shanghai \
    LANG=C.UTF-8 \
    JAVA_OPTS=""

COPY --from=build /build/target/*.jar app.jar

EXPOSE 9201

HEALTHCHECK --interval=30s --timeout=5s --start-period=60s --retries=5 \
    CMD wget -qO- http://127.0.0.1:9201/login >/dev/null 2>&1 || exit 1

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar --spring.profiles.active=docker"]

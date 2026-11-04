# ---------- 构建阶段 ----------
FROM node:16-alpine AS build
WORKDIR /app

# 项目使用 webpack 3，需 Node 16 及 --force 安装依赖
COPY package*.json .npmrc* ./
RUN npm install --force --no-audit --no-fund

COPY . .
RUN npm run build

# ---------- 运行阶段 ----------
FROM nginx:1.25-alpine
ENV TZ=Asia/Shanghai
COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/dist /usr/share/nginx/html
EXPOSE 80

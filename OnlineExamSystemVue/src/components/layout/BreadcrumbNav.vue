<template>
  <div class="oes-breadcrumb">
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: homePath }">首页</el-breadcrumb-item>
      <el-breadcrumb-item v-if="parentTitle">{{ parentTitle }}</el-breadcrumb-item>
      <el-breadcrumb-item v-if="currentTitle">{{ currentTitle }}</el-breadcrumb-item>
    </el-breadcrumb>
    <span class="oes-breadcrumb__date">{{ today }}</span>
  </div>
</template>

<script>
export default {
  name: 'BreadcrumbNav',
  data() {
    return {
      today: ''
    }
  },
  computed: {
    // 一级路由（布局）标题，例如“管理后台”“学生端”
    parentTitle() {
      const matched = this.$route.matched
      if (matched.length > 1 && matched[0].meta && matched[0].meta.title) {
        return matched[0].meta.title
      }
      return ''
    },
    // 当前页面标题，来自路由 meta.title
    currentTitle() {
      return (this.$route.meta && this.$route.meta.title) || ''
    },
    homePath() {
      return this.$route.path.indexOf('/student') === 0 ? '/student' : '/index'
    }
  },
  created() {
    const now = new Date()
    this.today = now.getFullYear() + ' 年 ' + (now.getMonth() + 1) + ' 月 ' + now.getDate() + ' 日'
  }
}
</script>

<style scoped>
.oes-breadcrumb {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 56px;
  padding: 0 24px;
  background: #fff;
  border-bottom: 1px solid var(--oes-border);
}

.oes-breadcrumb .el-breadcrumb {
  font-size: 13px;
}

.oes-breadcrumb__date {
  color: var(--oes-text-muted);
  font-size: 12px;
}

@media (max-width: 768px) {
  .oes-breadcrumb__date {
    display: none;
  }
}
</style>

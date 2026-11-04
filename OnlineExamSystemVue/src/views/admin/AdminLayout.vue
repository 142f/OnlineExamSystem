<!--
 * @Description: 教师 / 管理员后台布局
 * @Author: Rabbiter
 * @Date: 2023-03-08 20:38:49
-->
<template>
  <div id="index" class="oes-layout">
    <header1></header1>
    <section class="oes-layout__body">
      <div class="oes-layout__side">
        <mainLeft></mainLeft>
      </div>
      <div class="oes-layout__main">
        <navigator></navigator>
        <router-view></router-view>
      </div>
    </section>
  </div>
</template>

<script>
import header from '@/components/layout/AppHeader'
import mainLeft from '@/components/layout/SideMenu'
import navigator from '@/components/layout/BreadcrumbNav'
export default {
  components: {
    header1: header,
    mainLeft: mainLeft,
    navigator: navigator
  },
  data() {
    return {
      username: '许如梦'
    }
  },
  methods: {

  }
}
</script>

<style scoped>
.oes-layout {
  min-height: 100vh;
  background: var(--oes-bg);
}

.oes-layout__body {
  display: flex;
  align-items: stretch;
  min-height: calc(100vh - var(--oes-header-height));
}

.oes-layout__side {
  flex: 0 0 auto;
  display: flex;
  align-items: stretch;
}

.oes-layout__main {
  flex: 1;
  min-width: 0;
  background: var(--oes-bg);
}

.oes-layout__main >>> .oes-page {
  padding: 24px;
}

@media (max-width: 992px) {
  .oes-layout__side {
    display: none;
  }
}
</style>
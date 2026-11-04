<template>
  <div class="oes-empty">
    <i class="oes-empty__icon" :class="icon"></i>
    <p class="oes-empty__text">{{ text }}</p>
    <div v-if="$slots.default" class="oes-empty__action">
      <slot></slot>
    </div>
  </div>
</template>

<script>
export default {
  name: 'EmptyState',
  props: {
    text: { type: String, default: '暂无数据' },
    icon: { type: String, default: 'el-icon-folder-opened' }
  }
}
</script>

<style scoped>
.oes-empty__action {
  margin-top: 12px;
}
</style>

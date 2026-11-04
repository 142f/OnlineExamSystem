<template>
  <div class="oes-page-header">
    <div class="oes-page-header__main">
      <h2 class="oes-page-header__title">{{ title }}</h2>
      <p v-if="subtitle" class="oes-page-header__subtitle">{{ subtitle }}</p>
    </div>
    <div class="oes-page-header__actions">
      <slot name="actions"></slot>
    </div>
  </div>
</template>

<script>
export default {
  name: 'PageHeader',
  props: {
    title: { type: String, required: true },
    subtitle: { type: String, default: '' }
  }
}
</script>

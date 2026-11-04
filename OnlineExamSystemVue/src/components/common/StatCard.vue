<template>
  <div class="oes-stat-card">
    <div class="oes-stat-card__icon" :style="{ background: softColor, color: color }">
      <i :class="icon"></i>
    </div>
    <div class="oes-stat-card__body">
      <div class="oes-stat-card__value">{{ displayValue }}</div>
      <div class="oes-stat-card__label">{{ label }}</div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'StatCard',
  props: {
    label: { type: String, required: true },
    value: { type: [Number, String], default: 0 },
    icon: { type: String, default: 'el-icon-data-line' },
    color: { type: String, default: '#3d7eff' }
  },
  computed: {
    displayValue() {
      return this.value === null || this.value === undefined || this.value === '' ? '-' : this.value
    },
    softColor() {
      return this.color.replace('#', '') === '3d7eff' ? '#ecf3ff' : 'rgba(61,126,255,.10)'
    }
  }
}
</script>

<style scoped>
.oes-stat-card {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 20px;
  background: var(--oes-surface);
  border-radius: var(--oes-radius);
  box-shadow: var(--oes-shadow-sm);
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.oes-stat-card:hover {
  transform: translateY(-2px);
  box-shadow: var(--oes-shadow);
}

.oes-stat-card__icon {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 48px;
  height: 48px;
  border-radius: 12px;
  font-size: 22px;
  flex-shrink: 0;
}

.oes-stat-card__value {
  font-size: 26px;
  font-weight: 600;
  color: var(--oes-text);
  line-height: 1.2;
}

.oes-stat-card__label {
  margin-top: 4px;
  font-size: 13px;
  color: var(--oes-text-muted);
}
</style>

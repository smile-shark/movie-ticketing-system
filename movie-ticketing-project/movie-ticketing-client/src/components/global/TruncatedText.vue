<template>
  <div 
      :class="['text-truncate', customClass]" 
      :style="{ '--lines': lines, ...customStyle }"
  >
      <slot></slot>
  </div>
</template>

<script>
export default {
  name: 'TruncatedText',
  props: {
      lines: {
          type: Number,
          default: 2
      },
      customClass: {
          type: String,
          default: ''
      },
      customStyle: {
          type: Object,
          default: () => ({})
      }
  }
}
</script>

<style scoped>
.text-truncate {
  display: -webkit-box;
  overflow: hidden;
  text-overflow: ellipsis;
  -webkit-line-clamp: var(--lines);
  -webkit-box-orient: vertical;
}
</style>    
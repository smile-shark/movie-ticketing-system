<template>
    <transition-group
      appear
      name="staggered-fade"
      tag="div"
      class="animate-container"
      @before-enter="beforeEnter"
      @enter="enter"
    >
      <slot></slot>
    </transition-group>
  </template>
  
  <script>
  export default {
    methods: {
      beforeEnter(el) {
        el.style.opacity = 0
      },
      enter(el, done) {
        const delay = el.dataset.index * 100 // 每个元素延迟100ms
        setTimeout(() => {
          el.style.transition = 'opacity 0.5s ease'
          el.style.opacity = 1
          done()
        }, delay)
      }
    }
  }
  </script>
  
  <style scoped>
  .animate-container {
    display: block;
    width: 100%;
  }
  
  /* 动画样式 - 不影响布局 */
  .staggered-fade-enter-active {
    transition: opacity 0.5s ease;
  }
  .staggered-fade-enter {
    opacity: 0;
  }
  .staggered-fade-move {
    transition: transform 0.5s;
  }
  </style>
<template>
    <div :style="{ backgroundColor: color ,border:`${borderColor} 1px solid`}" class="seat">
      <div class="seat-back"></div>
      <div class="seat-bottom"></div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'Seat',
    props: {
      color: {
        type: String,
        default: 'gray' // 默认颜色为红色
      },
      x:{
        type: Number,
        primary: false,
        default: 1
      },
      y:{
        type: Number,
        primary: false,
        default: 1
      },
      borderColor:{
        type: String,
        default: 'black',
        primary: false
      }
    }
  }
  </script>
  
  <style scoped>
  .seat {
    display: inline-block;
    width: 20px;
    height: 20px;
    position: relative;
    border-radius: 5px;
    cursor: pointer;
  }
  
  .seat-back {
    width: 100%;
    height: 70%;
    border-radius: 5px 5px 0 0;
  }
  
  .seat-bottom {
    width: 100%;
    height: 30%;
    border-radius: 0 0 5px 5px;
    position: absolute;
    bottom: 0;
  }
  </style>
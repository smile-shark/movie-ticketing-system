<template>
  <div id="app" style="height: 100vh;">
    <router-view/>
  </div>
</template>

<style>
body {
  margin: 0;
  padding: 0;
}
@media screen and (max-width: 768px) {
  .el-message {
    min-width: 60% !important;
    max-width: 90% !important;
    font-size: 14px !important;
    padding: 10px !important;
  }
  
  .el-message-box {
    width: 80% !important;
  }
}
</style>

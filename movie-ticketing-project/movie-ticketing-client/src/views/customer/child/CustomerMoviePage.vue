<template>
  <div :style="{minHeight: '80vh'}">
    <router-view></router-view>
    <!-- 这个流给列表的组件 -->
    <!-- <el-empty style="min-height: 80vh;">
        <el-button @click="$router.push('/customer/home')">返回首页</el-button>
    </el-empty> -->
  </div>
</template>

<script>
export default {

}
</script>

<style>

</style>
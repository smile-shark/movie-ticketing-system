<template>
  <div class="container">
    <div style="width: 45vh;">
      <el-row>
          <el-col :span="24" style="display: flex; justify-content: center; align-items: center;">
              <img src="https://img.axureshop.com/db/65/7f/db657fd4b7a84b5aa76b1c2d09d02108/images/0_1%E7%99%BB%E5%BD%95/u32.png" class="logo"/>
          </el-col>
      </el-row>
      <el-row style="margin-top: 3vh;">
          <el-col :span="24">
              平台管理登录
          </el-col>
      </el-row>
      <el-row style="margin-top: 20px;">
        <el-col :span="24">
          <el-form ref="form" label-width="0px">
            <el-form-item >
              <el-input v-model="account" placeholder="请输入账号"></el-input>
            </el-form-item>
            <el-form-item >
              <el-input type="password" v-model="password" placeholder="请输入密码"></el-input>
            </el-form-item>
            <el-form-item>
              <el-checkbox v-model="remember">记住我</el-checkbox>
            </el-form-item>
            <el-form-item>
              <el-button type="warning" style="width: 100%;" @click="platformAdminLogin">登录</el-button>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import { myApi } from '@/axios/my'
export default {
  data(){
    return{
      account:'',
      password:'',
      remember:false
    }
  },methods:{
    platformAdminLogin(){
      myApi.platformLogin(this.account, this.password).then(res=>{
        if(res.data.code === 200){
          this.$message.success(res.data.message)
          localStorage.setItem('platformInfo',JSON.stringify(res.data.data))
          this.$router.push('/platform/home')
          if(this.remember){
            localStorage.setItem('platformAccount', this.account)
            localStorage.setItem('platformPassword', this.password)
            localStorage.setItem('platformRemember', this.remember)
          }else{
            localStorage.removeItem('platformAccount')
            localStorage.removeItem('platformPassword')
            localStorage.removeItem('platformRemember')
          }
        }else{
          this.$message.error(res.data.message)
        }
      }).catch(err=>{
        console.log(err)
      })
    }
  },
  mounted(){
    try{
      if(localStorage.getItem('platformRemember')){
        this.account = localStorage.getItem('platformAccount')
        this.password = localStorage.getItem('platformPassword')
        this.remember = Boolean(localStorage.getItem('platformRemember'))
      }
    }catch(e){
      console.log(e)
    }
  }
}
</script>

<style>
.container{
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
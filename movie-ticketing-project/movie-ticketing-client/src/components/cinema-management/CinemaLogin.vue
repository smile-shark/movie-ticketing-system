<template>
    <div style="width: 45vh;">
      <el-row>
          <el-col :span="24" style="display: flex; justify-content: center; align-items: center;">
              <img src="https://img.axureshop.com/db/65/7f/db657fd4b7a84b5aa76b1c2d09d02108/images/0_1%E7%99%BB%E5%BD%95/u32.png" class="logo"/>
          </el-col>
      </el-row>
      <el-row style="margin-top: 3vh;">
          <el-col :span="24">
              影院管理登录
          </el-col>
      </el-row>
      <el-row style="margin-top: 20px;">
        <el-col :span="24">
          <el-form ref="form" label-width="0px">
            <el-form-item >
              <el-input v-model="email" placeholder="请输入邮箱"></el-input>
            </el-form-item>
            <el-form-item >
              <el-input type="password" v-model="password" placeholder="请输入密码"></el-input>
            </el-form-item>
            <el-form-item>
              <el-checkbox v-model="remember">记住我</el-checkbox>
            </el-form-item>
            <el-form-item>
              <el-button type="warning" style="width: 100%;" @click="cinemaManagementLogin">登录</el-button>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24" style="text-align: right;">
          <el-link type="text" style="color:#ebb563" @click="toRegister">注册账户</el-link>
        </el-col>
      </el-row>
    </div>
  </template>
  
  <script>
  export default {
    data(){
      return{
        email: '',
        password: '',
        remember: false
      }
    },
    methods:{
        toRegister(){
            this.$emit('switch-another')
        },
        cinemaManagementLogin(){
          if(!/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(this.email)){
            this.$message.error('请输入正确的邮箱格式')
            return
          }
          if(this.password.length<6 || this.password.length>20){
            this.$message.error('密码长度必须在6-20位之间')
            return
          }
          this.$api.cinemaManagementLogin(this.email,this.password).then(res=>{
            if(res.data.code === 200){
              localStorage.setItem('cinemaManagementToken',res.data.data.token)
              localStorage.setItem('cinemaManagementInfo',JSON.stringify(res.data.data))
              this.$message({
                type:'success',
                message: res.data.message,
                duration: 1000
              })
              this.$router.push('/cinema/home')
              if(this.remember){
                localStorage.setItem('cinemaManagementEmail',this.email)
                localStorage.setItem('cinemaManagementPassword',this.password)
                localStorage.setItem('cinemaManagementRemember',this.remember)
              }else{
                localStorage.removeItem('cinemaManagementEmail')
                localStorage.removeItem('cinemaManagementPassword')
                localStorage.removeItem('cinemaManagementRemember')
              }
            }else{
              this.$message.error(res.data.message)
            }
          })
        }
    },
    mounted(){
      try{
        if(localStorage.getItem('cinemaManagementRemember')){
          this.email = localStorage.getItem('cinemaManagementEmail')
          this.password = localStorage.getItem('cinemaManagementPassword')
          this.remember = Boolean(localStorage.getItem('cinemaManagementRemember'))
        }
      }catch(e){
        console.log(e)
      }
    }
  }
  </script>
  
  <style>
  .logo{
      height: 8vh;
  }
  </style>
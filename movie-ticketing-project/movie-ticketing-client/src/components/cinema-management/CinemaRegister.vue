<template>
  <div style="width: 45vh;">
    <el-row>
        <el-col :span="24" style="display: flex; justify-content: center; align-items: center;">
            <img src="https://img.axureshop.com/db/65/7f/db657fd4b7a84b5aa76b1c2d09d02108/images/0_1%E7%99%BB%E5%BD%95/u32.png" class="logo"/>
        </el-col>
    </el-row>
    <el-row style="margin-top: 3vh;">
        <el-col :span="24">
            影院管理注册
        </el-col>
    </el-row>
    <el-row style="margin-top: 20px;">
      <el-col :span="24">
        <el-form ref="form" label-width="0px">
          <el-form-item >
            <el-input v-model="userEmail"  placeholder="请输入邮箱"></el-input>
          </el-form-item>
          <el-form-item >
            <el-input v-model="emailCode" placeholder="输入验证码" style="width: 70%;"></el-input>
            <el-button type="primary" size="small" style="margin-left: 10px;" @click="getEmailVerifyCode"
            :disabled="sendCooling">
              <span v-if="sendCooling">{{sendCoolingMessage}}</span>
              <span v-else>获取验证码</span>
            </el-button>
          </el-form-item>
          <el-form-item >
            <el-input type="password" v-model="password" placeholder="6 - 20位密码，区分大小写"></el-input>
          </el-form-item>
          <el-form-item >
            <el-input type="password" v-model="confirmPassword" placeholder="确认密码"></el-input>
          </el-form-item>
          <el-form-item>
            <el-checkbox v-model="agreementProtocol">同意
                <span style="color:#ebb563;"> “看吧电影用户协议”</span>
            </el-checkbox>
          </el-form-item>
          <el-form-item>
            <el-button type="warning" style="width: 100%;" @click="cinemaManagementRegister">注册</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24" style="text-align: right;">
        <el-link type="text" style="color:#ebb563" @click="toLogin">使用已有账户登录</el-link>
      </el-col>
    </el-row>

    <el-dialog
      title="请输入验证码"
      :visible.sync="imageVerifyDialog"
      width="30%">
      <div style="display: flex;align-items: center;">
        <el-input v-model="imageVerifyCode" placeholder="请输入验证码" style="width: 80%;"></el-input>
        <div style="height: 3vh;border: gray 1px solid;display: inline-block;min-width: 14%;margin-left: 10px;">
          <img :src="imageVerifyUrl" @click.prevent="getImageVerifyCode" alt="刷新" style="widows: 100%;height: 100%;"/>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="verifyImageVerifyCode">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { utils } from '@/utils/globalUtils'
export default {
  data(){
    return{
      isPerson:true,
      imageVerifyDialog:false,
      imageVerifyCode:'',
      imageVerifyUrl:'',
      sendCooling:false,
      coolingTimes:0,
      sendCoolingMessage:'0秒后重试',
      userEmail:'',
      emailVerifyCode:'',
      password:'',
      confirmPassword:'',
      agreementProtocol:false,
      emailCode:''
    }
  },
  methods:{
    toLogin(){
        this.$emit('switch-another')
    },
    getImageVerifyCode() {
      let key='redis-sk-MATKmIISApWiByAc'
      this.imageVerifyUrl = `https://api.qster.top/API/v2/ImgVerifyCode?t=${Date.now()}&qskey=${key}`;
    },
    verifyImageVerifyCode(){
      if(this.imageVerifyCode.length!=4){
        this.$message.error('请输入正确的验证码')
        return
      }
      this.$apis.getVerifyCode(this.imageVerifyUrl,this.imageVerifyCode).then(res=>{
        if(res.data.status){
          this.$message.success(res.data.messages)
          this.isPerson=true
          this.imageVerifyDialog = false
          // 发送邮箱验证码
          this.getEmailVerifyCode()
        }else{
          this.$message.error(res.data.messages)
        }
      })
    },
    getEmailVerifyCode(){
      // 如果没有通过人机验证就弹出人机验证的弹窗
      // 验证邮箱格式
      if(!utils.verifyEmailFormat(this.userEmail)){
        this.$message.error('请输入正确的邮箱格式')
        return
      }
      if(!this.isPerson){
        this.imageVerifyDialog = true
        this.getImageVerifyCode()
      }else{
        this.sendCooling = true
        this.coolingTimes = 60
        let timer=setInterval(()=>{
          this.coolingTimes--
          this.sendCoolingMessage = this.coolingTimes + '秒后重试'
          if(this.coolingTimes<=0){
            this.sendCooling = false
            this.sendCoolingMessage = '获取验证码'
            clearInterval(timer)
          }
        },1000)
        this.$api.sendMailVerifyCode(this.userEmail).then(res=>{
          if(res.data.success){
            this.$message.success(res.data.message)
          }else{
            this.$message.error(res.data.message)
             this.sendCooling = false
             this.sendCoolingMessage = '获取验证码'
             clearInterval(timer)
          }
        }).catch(e=>{
             this.$message.error('发送失败，请稍后再试')
             this.sendCooling = false
             this.sendCoolingMessage = '获取验证码'
             clearInterval(timer)
        })
      }
    },
    cinemaManagementRegister(){
      // 验证邮箱格式
      if(!/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(this.userEmail)){
        this.$message.error('请输入正确的邮箱格式')
        return
      }
      // 验证密码格式
      if(this.password.length<6 || this.password.length>20){
        this.$message.error('密码长度必须在6-20位之间')
        return
      }
      // 验证确认密码
      if(this.password!==this.confirmPassword){
        this.$message.error('两次密码输入不一致')
        return
      }
      // 验证协议
      if(!this.agreementProtocol){
        this.$message.error('请同意用户协议')
        return
      }
      this.$api.cinemaManagementRegister(this.userEmail,this.password,this.emailCode).then(res=>{
        if(res.data.success){
          this.$message.success(res.data.message)
          this.toLogin()
        }else{
          this.$message.error(res.data.message)
        }
      })
    }
  }
}
</script>

<style>
.logo{
    height: 8vh;
}
</style>
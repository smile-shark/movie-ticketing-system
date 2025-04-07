<template>
  <div style="width: 45vh;">
    <el-row>
        <el-col :span="24" style="display: flex; justify-content: center; align-items: center;">
            <img src="https://img.axureshop.com/db/65/7f/db657fd4b7a84b5aa76b1c2d09d02108/images/0_1%E7%99%BB%E5%BD%95/u32.png" class="logo"/>
        </el-col>
    </el-row>
    <el-row style="margin-top: 3vh;">
        <el-col :span="24">
            注册
        </el-col>
    </el-row>
    <el-row style="margin-top: 20px;">
      <el-col :span="24">
        <el-form ref="form" label-width="0px">
          <el-form-item >
            <el-input  placeholder="请输入邮箱"></el-input>
          </el-form-item>
          <el-form-item >
            <el-input  placeholder="输入验证码" style="width: 70%;"></el-input>
            <el-button type="primary" size="small" style="margin-left: 10px;">获取验证码</el-button>
          </el-form-item>
          <el-form-item >
            <el-input type="password"  placeholder="6 - 20位密码，区分大小写"></el-input>
          </el-form-item>
          <el-form-item >
            <el-input type="password"  placeholder="确认密码"></el-input>
          </el-form-item>
          <el-form-item>
            <el-checkbox >同意
                <span style="color:#ebb563;"> “看吧电影用户协议”</span>
            </el-checkbox>
          </el-form-item>
          <el-form-item>
            <el-button type="warning" style="width: 100%;">注册</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24" style="text-align: right;">
        <el-link type="text" style="color:#ebb563" @click="toLogin">使用已有账户登录</el-link>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  methods:{
    toLogin(){
        this.$emit('switch-another')
    }
  }
}
</script>

<style>
.logo{
    height: 8vh;
}
</style>
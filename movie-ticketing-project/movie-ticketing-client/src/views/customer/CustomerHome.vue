<template>
  <div id="app">
    <el-container style="background-color: #f0f2f5;height: 100vh;">
      <el-header class="header" style="padding:0 18%;">
        <el-row style="height: 9vh;">
            <el-col :span="24" class="title-container">
                <div style="padding:5px;display: flex;align-items: center;">
                    <el-image src="https://img.axureshop.com/db/65/7f/db657fd4b7a84b5aa76b1c2d09d02108/images/0_1%E7%99%BB%E5%BD%95/u32.png"
                style="width: 180px; height: 50px;position:relative;top: 5px;" fit="contain">
                    </el-image>
                </div>
            </el-col>
        </el-row>
        <el-dropdown @command="handleDropdownCommand" style="cursor: pointer;">
          <span class="el-dropdown-link">
            {{ city.marketName }}<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item :command="market" v-for="market in markets" :key="market.marketId">
              {{ market.marketName }}
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
        <el-menu :default-active="activeIndex" class="nav-menu" mode="horizontal" 
        background-color="#03071E" text-color="#fff" active-text-color="#ff7b32"
        router>
          <el-menu-item index="/customer/home">首页</el-menu-item>
          <el-menu-item index="/customer/home/movie">影片</el-menu-item>
          <el-menu-item >影院</el-menu-item>
          <el-menu-item >口碑榜</el-menu-item>
        </el-menu>
        <el-input placeholder="请输入电影名称" prefix-icon="el-icon-search" class="search-input"></el-input>
        <el-avatar  :src="circleUrl"></el-avatar>
        <span class="login-text" style="font-size: 14px;" v-if="customerInfo">{{customerInfo.userName}}</span>
        <el-button v-else type="text" class="login-button" @click="$router.push('/customer/login')">请登录</el-button>
        <el-button  v-if="customerInfo" type="text" class="login-button" @click="$router.push('/customer/login')" icon="el-icon-right">退出</el-button>
      </el-header>
      <el-main >
        <div style="padding:0 18%;">
          <router-view style="background-color: white;"></router-view>
        <div style="padding: 10px;text-align: center;font-size: 14px;color: #999;">
          看吧电影-相约电影院，享受好时光！
        </div>
        </div>
      </el-main>
    </el-container>
    <el-dialog
      title="选择所在的城市"
      :visible.sync="finstMarketSelectDialog"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :show-close="false"
    >
      
      <el-button type="primary" style="background-color: #01030f;border:none" 
      v-for="market in markets" :key="market.marketId"
      @click="customerFirstSelectMarket(market)" >
        {{ market.marketName }}
      </el-button>
    </el-dialog>
  </div>
</template>
<script>
import { myApi } from '@/axios/my';
export default {
  data() {
    return {
      activeIndex: '/customer/home',
      circleUrl: "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
      city: {
        marketId: "bacf76f7b4d34e64ba15f11d7ca54eb7",
        economizeId: "bacf76f7b4d34e64ba15f11d7ca54eb7",
        marketName: "成都市",
      },
      markets: [],
      customerInfo:null,
      finstMarketSelectDialog: false,
    };
  },
  methods: {
    handleDropdownCommand(command) {
      this.customerFirstSelectMarket(command)
    },
    customerFirstSelectMarket(market){
      this.city=market
      // 添加到数据库中
      myApi.updateUserMarket(this.customerInfo.userId,market.marketId).then(res=>{
        if(res.data.code==200){
          this.customerInfo.marketId=market.marketId
          this.customerInfo.market=market
          localStorage.setItem('customerInfo',JSON.stringify(this.customerInfo))
          this.finstMarketSelectDialog=false
        }
      })
    }
  },
  mounted(){
    this.defaultActive=this.$route.path
    this.customerInfo=JSON.parse(localStorage.getItem('customerInfo'))
    if(this.customerInfo){
      this.circleUrl=this.customerInfo.userProfilePicture
      if(this.customerInfo.market){
        this.city=this.customerInfo.market
      }else{
        // 弹出选择框
        this.finstMarketSelectDialog=true
      }
    }
    myApi.selectAllMarket().then(res=>{
      if(res.data.code == 200){
        this.markets = res.data.data
      }
    })
  },
  watch:{
    '$route'(to,from){
        let currentPath = to.path;
        // 正则表达式匹配32位数字和小写字母组成的字符串
        const regex = /\/[0-9a-z]{32}$/;
        if (regex.test(currentPath)) {
            // 如果匹配到，去掉最后符合条件的部分
            currentPath = currentPath.replace(regex, '');
        }
        this.defaultActive = currentPath;
        if(currentPath=='/customer/home/movie/detail'){
          this.activeIndex='/customer/home/movie'
        }
    }
  },
};
</script>
<style scoped>
.header {
  background-color: #03071E;
  color: #fff;
  padding: 0;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.logo {
  display: flex;
  align-items: center;
  margin-right: 20px;
}
.logo-img {
  width: 30px;
  height: 30px;
  margin-right: 10px;
}
.logo-text {
  font-size: 20px;
  font-weight: bold;
}
.nav-menu {
  margin-right: 20px;
}
.search-input {
  width: 300px;
  margin-right: 20px;
}
.login-button {
  color: #fff;
}
</style>
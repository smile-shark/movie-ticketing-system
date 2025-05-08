<template>
  <div id="app">
    <el-container style="background-color: #f0f2f5; height: 100vh">
      <el-header class="header">
        <el-row style="height: 9vh">
          <el-col :span="24" class="title-container">
            <div
              style="padding: 5px; display: flex; align-items: center"
              v-if="!utils.getsmall()"
            >
              <el-image
                src="https://img.axureshop.com/db/65/7f/db657fd4b7a84b5aa76b1c2d09d02108/images/0_1%E7%99%BB%E5%BD%95/u32.png"
                style="width: 180px; height: 50px; position: relative; top: 5px"
                fit="contain"
              >
              </el-image>
            </div>
          </el-col>
        </el-row>
        <el-dropdown
          @command="handleDropdownCommand"
          style="cursor: pointer"
          v-if="!utils.getsmall()"
        >
          <span class="el-dropdown-link">
            {{ city.marketName
            }}<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item
              :command="market"
              v-for="market in markets"
              :key="market.marketId"
            >
              {{ market.marketName }}
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
        <el-menu
          v-if="!utils.getsmall()"
          :default-active="activeIndex"
          mode="horizontal"
          background-color="#03071E"
          text-color="#fff"
          active-text-color="#ff7b32"
          router
        >
          <el-menu-item index="/customer/home">首页</el-menu-item>
          <el-menu-item index="/customer/home/movie">影片</el-menu-item>
          <!-- <el-menu-item >影院</el-menu-item> -->
          <el-menu-item index="/customer/home/rating-list">评分榜</el-menu-item>
        </el-menu>
        <el-input
          v-model="searchText"
          @keyup.enter.native="handleSearch"
          placeholder="请输入电影名称"
          class="search-input"
        >
        <el-button slot="append" icon="el-icon-search"  @click="handleSearch"></el-button>
      </el-input>
        <el-avatar :src="circleUrl"></el-avatar>
        <el-dropdown v-if="customerInfo&&!utils.getsmall()">
          <span
            class="login-text el-dropdown-link"
            style="font-size: 14px; color: white"
            >{{ customerInfo.userName }}</span
          >
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item
              @click.native="$router.push('/customer/home/movie/order-list')"
              >订单</el-dropdown-item
            >
          </el-dropdown-menu>
        </el-dropdown>
        <el-button
          v-else-if="!utils.getsmall()"
          type="text"
          class="login-button"
          @click="$router.push('/customer/login')"
          >请登录</el-button
        >
        <el-button
          v-if="customerInfo"
          type="text"
          class="login-button"
          @click="$router.push('/customer/login')"
          :icon="utils.getsmall()?'':'el-icon-right'"
          >退出</el-button
        >
      </el-header>
      <el-main class="home-main">
        <el-row>
          <el-col :xs="0" :sm="4" :md="4"> &nbsp; </el-col>
          <el-col :xs="24" :sm="16" :md="16">
            <router-view style="background-color: white"></router-view>
            <div
              style="
                padding: 10px;
                text-align: center;
                font-size: 14px;
                color: #999;
              "
            >
              看吧电影-相约电影院，享受好时光！
            </div>
          </el-col>
          <el-col :xs="0" :sm="4" :md="4"> &nbsp; </el-col>
        </el-row>
      </el-main>
      <el-footer
        v-if="utils.getsmall()"
        style="padding: 0; position: sticky; bottom: -1px; width: 100%"
      >
        <el-menu
          stretch
          :default-active="activeIndex"
          mode="horizontal"
          background-color="#03071E"
          text-color="#fff"
          active-text-color="#ff7b32"
          class="evenly-spaced-menu"
          router
        >
          <el-menu-item index="/customer/home">首页</el-menu-item>
          <el-menu-item index="/customer/home/movie">影片</el-menu-item>
          <!-- <el-menu-item >影院</el-menu-item> -->
          <el-menu-item index="/customer/home/rating-list">评分榜</el-menu-item>
          <el-menu-item index="/customer/home/movie/order-list">订单</el-menu-item>
        </el-menu>
      </el-footer>
    </el-container>
    <el-dialog
      title="选择所在的城市"
      :visible.sync="finstMarketSelectDialog"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :show-close="false"
    >
      <el-button
        type="primary"
        style="background-color: #01030f; border: none"
        v-for="market in markets"
        :key="market.marketId"
        @click="customerFirstSelectMarket(market)"
      >
        {{ market.marketName }}
      </el-button>
    </el-dialog>
  </div>
</template>
<script>
import { myApi } from "@/axios/my";
import { utils } from "@/utils/globalUtils";
export default {
  data() {
    return {
      activeIndex: "/customer/home",
      circleUrl:
        "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
      city: {
        marketId: "bacf76f7b4d34e64ba15f11d7ca54eb7",
        economizeId: "bacf76f7b4d34e64ba15f11d7ca54eb7",
        marketName: "成都市",
      },
      markets: [],
      customerInfo: null,
      finstMarketSelectDialog: false,
      searchText: "",
      utils,
    };
  },
  methods: {
    handleDropdownCommand(command) {
      this.customerFirstSelectMarket(command);
    },
    customerFirstSelectMarket(market) {
      this.city = market;
      // 添加到数据库中
      myApi
        .updateUserMarket(this.customerInfo.userId, market.marketId)
        .then((res) => {
          if (res.data.code == 200) {
            this.customerInfo.marketId = market.marketId;
            this.customerInfo.market = market;
            // 更新localstorage的marketId
            localStorage.setItem(
              "customerInfo",
              JSON.stringify(this.customerInfo)
            );
            this.finstMarketSelectDialog = false;
          }
        });
    },
    handleSearch() {
      if (!this.searchText?.trim()) {
        this.$message.warning("请输入搜索内容");
        return;
      }
      this.$router.push({
        name: "MovieListView",
        params: { movieName: this.searchText.trim() },
      });
    },
  },
  mounted() {
    this.customerInfo = JSON.parse(localStorage.getItem("customerInfo"));
    if (this.customerInfo) {
      this.circleUrl = this.customerInfo.userProfilePicture;
      if (this.customerInfo.market) {
        this.city = this.customerInfo.market;
      } else {
        // 弹出选择框
        this.finstMarketSelectDialog = true;
      }
    }
    myApi.selectAllMarket().then((res) => {
      if (res.data.code == 200) {
        this.markets = res.data.data;
      }
    });
    this.defaultActive = this.$route.path;
  },
  watch: {
    $route: {
      immediate: true, // 初始化时立即执行
      handler(to) {
        let currentPath = to.path;

        // 清理32位哈希（如果存在）
        currentPath = currentPath.replace(/\/[0-9a-z]{32}$/, "");

        // 判断是否是影片路径
        this.activeIndex = currentPath.startsWith("/customer/home/movie")
          ? "/customer/home/movie"
          : currentPath;
      },
    },
  },
};
</script>
<style scoped>
.header {
  background-color: #03071e;
  color: #fff;
  padding: 0;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 18%
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

@media (max-width: 768px) {
  /* 电影卡片列宽占满一行 */
  .home-main {
    padding: 0;
    /* max-height: 85vh; */
  }

  .evenly-spaced-menu {
    display: flex !important;
    justify-content: space-around !important;
  }

  .evenly-spaced-menu .el-menu-item {
    flex: 1 !important;
    text-align: center !important;
  }
  .search-input {
    width: 70vw;
    margin-right:0;
  }
  .header {
    padding: 0 2%
  }
}
</style>

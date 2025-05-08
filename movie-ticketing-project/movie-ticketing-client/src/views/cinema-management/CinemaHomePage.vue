<template>
  <div class="manage_page">
    <el-row style="height: 9vh">
      <el-col :span="24" class="title-container">
        <div style="padding: 5px; display: flex; align-items: center">
          <el-image
            src="https://img.axureshop.com/db/65/7f/db657fd4b7a84b5aa76b1c2d09d02108/images/0_1%E7%99%BB%E5%BD%95/u32.png"
            style="width: 180px; height: 60px"
            fit="contain"
          >
          </el-image>
          <span style="margin-left: 5px; color: #fdc31d; font-weight: bold">
            当前
            <span style="font-size: 20px; color: #feac28">
              {{
                cinemaList.find((cinema) => cinema.cinemaId == cinemaId)
                  ? cinemaList.find((cinema) => cinema.cinemaId == cinemaId)
                      .cinemaName
                  : ""
              }}
            </span>
            影院后台管理
          </span>
          <div
            style="
              display: inline-block;
              position: absolute;
              right: 20px;
              color: #3477f4;
            "
          >
            <div
              v-if="cinemaManagementInfo"
              style="display: flex; align-items: center"
            >
              <span style="margin-right: 20px">
                <el-select
                  style="
                    border-top-right-radius: 0;
                    border-bottom-right-radius: 0;
                  "
                  v-model="cinemaId"
                  filterable
                  placeholder="选择影院"
                  @change="refreshWindow(cinemaId)"
                >
                
                  <el-option
                    v-for="(cinema, index) in cinemaList"
                    :key="index"
                    :label="cinema.cinemaName"
                    :value="cinema.cinemaId"
                  ></el-option>
                </el-select>
                <el-button
                  type="primary"
                  style="
                    border-top-left-radius: 0;
                    border-bottom-left-radius: 0;
                  "
                  @click="selectCinemaDialogVisibleEnd = true"
                >
                  添加影院
                </el-button>
              </span>
              <el-avatar
                :size="40"
                :src="cinemaManagementInfo.cinemaManagementProfilePicture"
                style="margin-right: 20px"
              ></el-avatar>
              <el-link @click="$router.push('/cinema/admin')">退出</el-link>
            </div>
            <div v-else>
              <el-link @click="$router.push('/cinema/admin')">去登录</el-link>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
    <el-row style="height: 91vh">
      <el-col
        :span="3"
        style="
          height: 100%;
          background-color: white;
          border-right: 1px solid #c0c6cc;
        "
      >
        <el-scrollbar
          style="height: 100%"
          wrap-class="scrollbar"
          view-class="scroll-view"
        >
          <div>
            <el-menu
              ref="menuRef"
              :default-active="defaultActive"
              router
              background-color="white"
              text-color="black"
            >
              <el-menu-item index="/cinema/home">
                <i class="el-icon-menu"></i>首页
              </el-menu-item>
              <el-submenu index="">
                <span slot="title">
                  <i class="el-icon-video-camera-solid"></i>放映厅管理</span
                >
                <el-menu-item index="/cinema/home/add-screening-room"
                  >放映厅添加</el-menu-item
                >
                <el-menu-item index="/cinema/home/list-screening-room"
                  >放映厅列表</el-menu-item
                >
              </el-submenu>
              <el-submenu index="">
                <span slot="title"><i class="el-icon-film"></i>排片管理</span>
                <el-menu-item index="/cinema/home/add-slice-arrangement"
                  >添加新的排片</el-menu-item
                >
                <el-menu-item index="/cinema/home/list-slice-arrangement"
                  >排片列表</el-menu-item
                >
              </el-submenu>
              <el-submenu index="">
                <span slot="title"><i class="el-icon-s-grid"></i>其他管理</span>
                <el-menu-item index="/cinema/home/info-management">
                  影院信息管理
                </el-menu-item>
              </el-submenu>
            </el-menu>
          </div>
        </el-scrollbar>
      </el-col>
      <el-col :span="21" style="height: 100%; overflow: auto">
        <el-row>
          <el-col :span="24" style="padding: 10px">
            <router-view></router-view>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
    <!-- 默认选择第一个影院的数据展示，没有就出现这个弹窗创建，控制台的数据按照影院来展示 -->
    <el-dialog
      :visible.sync="selectCinemaDialogVisible"
      :close-on-press-escape="false"
      :close-on-click-modal="false"
      :show-close="false"
      title="你的账号下还没有管理的影院，请先创建影院"
    >
      <CinemaManagementCreateCinema></CinemaManagementCreateCinema>
    </el-dialog>
    <el-dialog :visible.sync="selectCinemaDialogVisibleEnd" title="创建影院">
      <CinemaManagementCreateCinema></CinemaManagementCreateCinema>
    </el-dialog>
  </div>
</template>

<script>
import { myApi } from "@/axios/my";
import CinemaManagementCreateCinema from "@/components/cinema-management/CinemaManagementCreateCinema.vue";

export default {
  components: {
    CinemaManagementCreateCinema,
  },
  data() {
    return {
      defaultActive: "/cinema/home",
      breadcrumbList: [],
      cinemaManagementInfo: {},
      cinemaList: [],
      selectCinemaDialogVisible: false,
      selectCinemaDialogVisibleEnd: false,
      cinemaId: "",
    };
  },
  methods: {
    refreshWindow(cinemaId) {
      this.updageLocalStorage(cinemaId);
      window.location.reload();
    },
    updageLocalStorage(cinemaId) {
      localStorage.setItem("cinemaId", cinemaId);
    },
  },
  watch: {
    $route(to, from) {
      let currentPath = to.path;
      // 正则表达式匹配32位数字和小写字母组成的字符串
      const regex = /\/[0-9a-z]{32}$/;
      if (regex.test(currentPath)) {
        // 如果匹配到，去掉最后符合条件的部分
        currentPath = currentPath.replace(regex, "");
      }
      this.defaultActive = currentPath;
    },
  },
  mounted() {
    this.$refs.menuRef.open("");
    if (localStorage.getItem("cinemaManagementInfo")) {
      this.cinemaManagementInfo = JSON.parse(
        localStorage.getItem("cinemaManagementInfo")
      );
    }
    this.defaultActive = this.$route.path;
    myApi
      .selectCinemaByCinemaManagementId(
        this.cinemaManagementInfo.cinemaManagementId
      )
      .then((res) => {
        if (res.data.code == 200) {
          if (res.data.data.length == 0) {
            this.selectCinemaDialogVisible = true;
            this.$alert("您的账号下还没有管理的影院，请先创建影院", "提示", {
              confirmButtonText: "确定",
            });
          } else {
            this.cinemaList = res.data.data;
            if (this.cinemaList.length == 1) {
              this.cinemaId = this.cinemaList[0].cinemaId;
              this.updageLocalStorage(this.cinemaId);
            } else {
              if (localStorage.getItem("cinemaId")) {
                this.cinemaId = localStorage.getItem("cinemaId");
              } else {
                this.cinemaId = this.cinemaList[0].cinemaId;
                this.updageLocalStorage(this.cinemaId);
              }
            }
          }
        } else {
          this.$message.error(res.data.message);
        }
      })
      .catch((e) => {
        console.log(e);
      });
  },
};
</script>

<style scoped>
.manage_page {
  height: 100vh;
}
.title-container {
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  z-index: 100;
  position: relative;
}
</style>

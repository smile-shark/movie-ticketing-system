<template>
  <div style="padding: 20px; color: #5a5a5a">
    <el-row>
      <el-col :span="24">
        <span style="font-weight: bold"> 订单支付 </span>
      </el-col>
    </el-row>
    <el-row :style="utils.getsmall() ? '' : 'padding:3rem'">
      <el-col :span="24" style="font-size: 1.2rem; text-align: center">
        <span style="font-weight: bold"> 提交成功 </span>
      </el-col>
      <el-col :span="24" style="text-align: center; padding: 1rem">
        <span>
          订单提交成功，请在15分钟内尽快付款！订单号:
          <p>
            {{ orderId }}
          </p>
        </span>
      </el-col>
      <el-col
        :span="24"
        style="text-align: center; border: solid 1px #e9e9e9; padding: 2rem"
      >
        <el-row>
          <el-col :span="24">
            <span style="font-weight: bold"> 应付金额： </span>
            <span style="color: #ff6e4d; font-size: 1.2rem">
              ￥{{ order.voteAllPrice }}
            </span>
          </el-col>
          <el-col :span="24">
            <el-image
              @click="updateOrderStatus"
              src="https://img.axureshop.com/db/65/7f/db657fd4b7a84b5aa76b1c2d09d02108/images/2_5%E8%AE%A2%E5%8D%95%E6%94%AF%E4%BB%98/u882.png"
              :style="utils.getsmall() ? '' : 'height: 15rem;'"
            ></el-image>
          </el-col>
        </el-row>
      </el-col>
      <el-col :span="24" style="margin-top: 2rem">
        <el-row>
          <el-col :span="24">
            <div style="position: relative; padding: 10px">
              <div style="font-size: 18px; font-weight: bold">
                相关推荐 ({{ hotScreeningInProgresData.total }})
              </div>
              <div
                style="
                  position: absolute;
                  right: 0;
                  top: 0;
                  padding: 10px;
                  font-size: 14px;
                "
              >
                <el-link
                  :underline="false"
                  @click="$router.push('/customer/home/movie')"
                >
                  更多<i class="el-icon-arrow-right"></i>
                </el-link>
              </div>
            </div>
          </el-col>
        </el-row>
        <el-row>
          <AnimateView>
            <el-col
              :xs="12"
              :sm="8"
              :md="6"
              v-for="(movie, index) in hotScreeningInProgresData.list"
              :key="index"
              :data-index="index"
            >
              <MovieListCard :movie="movie"></MovieListCard>
            </el-col>
          </AnimateView>
        </el-row>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { myApi } from "@/axios/my";
import { utils } from "@/utils/globalUtils";
import MovieListCard from "@/components/global/MovieListCard.vue";
import AnimateView from "@/components/global/AnimateView.vue";
export default {
  components: {
    MovieListCard,
    AnimateView,
  },
  data() {
    return {
      hotScreeningInProgresData: {
        total: 0,
      },
      orderId: null,
      order: {
        voteAllPrice: 0,
      },
      utils,
    };
  },
  methods: {
    selectHotScreeningInProgress() {
      myApi
        .selectMovieList({
          already: 0,
          size: 4,
        })
        .then((res) => {
          if (res.data.code == 200) {
            this.hotScreeningInProgresData = res.data.data;
          }
        });
    },
    selectOrderByOrderId() {
      myApi
        .selectOrderByOrderId(this.orderId)
        .then((res) => {
          if (res.data.code == 200) {
            this.order = res.data.data;
          } else {
            this.$message.error(res.data.message);
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },
    updateOrderStatus() {
      myApi
        .updateOrderStatus(this.orderId, 1)
        .then((res) => {
          if (res.data.code == 200) {
            // 支付成功生成二维码转跳到可验证的票页面
            this.$message.success("支付成功");
            this.$router.push({
              name: "CustomerVerifyVotePage",
              params: {
                orderId: this.orderId,
              },
            });
          } else {
            this.$message.error(res.data.message);
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },
  },
  mounted() {
    this.orderId = this.$route.params.orderId;
    this.selectHotScreeningInProgress();
    this.selectOrderByOrderId();
  },
};
</script>

<style></style>

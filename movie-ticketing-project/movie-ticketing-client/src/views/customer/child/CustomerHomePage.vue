<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-carousel
          :height="utils.getsmall() ? '20vh' : '30vh'"
          :type="utils.getsmall() ? '' : 'card'"
          indicator-position="outside"
        >
          <el-carousel-item
            v-for="(mobileDispaly, index) in mobileDisplays"
            :key="index"
            style="height: 100%"
          >
            <el-image
              :src="mobileDispaly.mobileDisplayImage"
              style="width: 100%; height: 100%"
              fit="fill"
              @click="
                $router.push({
                  name: 'CustomerMovieDetailPage',
                  params: { movieId: mobileDispaly.movieId },
                })
              "
            >
              <div
                slot="error"
                class="image-slot"
                style="
                  background-color: #f9f9f9;
                  display: flex;
                  align-items: center;
                  justify-content: center;
                "
              >
                <i class="el-icon-picture-outline"></i>
              </div>
            </el-image>
          </el-carousel-item>
        </el-carousel>
      </el-col>
    </el-row>
    <el-row
      style="padding: 20px"
      :hidden="hotScreeningInProgresData.total == 0"
    >
      <el-col :span="24">
        <el-row>
          <el-col :span="24">
            <div
              style="
                background-color: #f2f2f2;
                position: relative;
                padding: 10px;
              "
            >
              <div
                style="text-align: center; font-size: 18px; font-weight: bold"
              >
                正在热映 ({{ hotScreeningInProgresData.total }})
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
    <el-row
      style="padding: 20px"
      :hidden="hotScreeningInProgresData.total == 0"
    >
      <el-col :span="24">
        <el-row>
          <el-col :span="24">
            <div
              style="
                background-color: #f2f2f2;
                position: relative;
                padding: 10px;
              "
            >
              <div
                style="text-align: center; font-size: 18px; font-weight: bold"
              >
                即将上映 ({{ soonScreeningInProgresData.total }})
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
              v-for="(movie, index) in soonScreeningInProgresData.list"
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
import MovieListCard from "@/components/global/MovieListCard.vue";
import AnimateView from "@/components/global/AnimateView.vue";
import { utils } from '@/utils/globalUtils';
export default {
  components: {
    MovieListCard,
    AnimateView,
  },
  data() {
    return {
      mobileDisplays: [],
      hotScreeningInProgresData: {
        total: 0,
      },
      soonScreeningInProgresData: {
        total: 0,
      },
      utils
    };
  },
  methods: {
    selectAllAllowedMobileDisplays() {
      myApi
        .selectAllAllowedMobileDisplays()
        .then((res) => {
          if (res.data.code == 200) {
            this.mobileDisplays = res.data.data;
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    selectHotScreeningInProgress() {
      myApi
        .selectMovieList({
          random: true,
          already: 0,
          size: 4,
        })
        .then((res) => {
          if (res.data.code == 200) {
            this.hotScreeningInProgresData = res.data.data;
          }
        });
    },
    selectSoonScreeningInProgress() {
      myApi
        .selectMovieList({
          random: true,
          already: 0,
          size: 4,
        })
        .then((res) => {
          if (res.data.code == 200) {
            this.soonScreeningInProgresData = res.data.data;
          }
        });
    }
  },
  mounted() {
    this.selectAllAllowedMobileDisplays();
    this.selectHotScreeningInProgress();
    this.selectSoonScreeningInProgress();
  },
};
</script>

<style scoped>
</style>

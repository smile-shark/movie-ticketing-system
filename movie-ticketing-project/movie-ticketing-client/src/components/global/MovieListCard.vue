<template>
  <el-card class="movie-card" :body-style="{ padding: '0px' }" shadow="hover">
      <div :style="{ position: 'relative', height: cardHeight }" @click="toMovieDetail">
          <el-image :src="movie.posterImage" fit="fill" :style="{ height: '100%' }">
              <div slot="error" class="image-slot">
                  <i class="el-icon-picture-outline"></i>
              </div>
          </el-image>
          <div class="movie-info" :style="{ padding: '5px', position: 'absolute', bottom: '0', backgroundColor: 'rgba(255, 255, 255,0.7)', width: '96%' }">
              <span class="movie-title">{{ movie.movieName }}</span>
              <span class="movie-rating">{{ movie.score }}</span>
          </div>
      </div>
      <el-button @click="toMovieDetail"
                 :style="{ color: '#ff7140', border: 'none', width: '100%', fontSize: buttonFontSize, fontWeight: 'bold' }">选座购票</el-button>
  </el-card>
</template>

<script>
export default {
  props: {
      movie: Object,
  },
  data() {
      return {
          cardHeight: this.getCardHeight(),
          buttonFontSize: this.getButtonFontSize()
      };
  },
  methods: {
      toMovieDetail() {
          this.$router.push({ name: "CustomerMovieDetailPage", params: { movieId: this.movie.movieId } });
      },
      getCardHeight() {
          if (window.innerWidth < 768) {
              return '30vh';
          }
          return '45vh';
      },
      getButtonFontSize() {
          if (window.innerWidth < 768) {
              return '14px';
          }
          return '18px';
      },
      handleResize() {
          this.cardHeight = this.getCardHeight();
          this.buttonFontSize = this.getButtonFontSize();
      }
  },
  mounted() {
      window.addEventListener('resize', this.handleResize);
  },
  beforeDestroy() {
      window.removeEventListener('resize', this.handleResize);
  }
};
</script>

<style scoped>
.movie-card {
  margin: 10px;
  text-align: center;
}

.movie-info {
  display: flex;
  justify-content: space-between;
  margin: 10px 0;
}

.movie-title {
  font-weight: bold;
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: normal;
}

.movie-rating {
  color: #ff9900;
}
</style>    
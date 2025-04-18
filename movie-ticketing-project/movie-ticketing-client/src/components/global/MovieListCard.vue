<template>
    <el-card class="movie-card" :body-style="{ padding: '0px' }" shadow="hover">
        <div style="position: relative;height: 45vh;">
            <el-image :src="movie.posterImage" fit="fill" style="height: 100%;">
                <div slot="error" class="image-slot">
                    <i class="el-icon-picture-outline"></i>
                </div>
            </el-image>
            <div class="movie-info" style="padding:5px;
            position: absolute;bottom:0;background-color: rgba(255, 255, 255,0.7);width: 96%;">
                    <span class="movie-title">{{ movie.movieName }}</span>
                    <span class="movie-rating">{{ movie.score }}</span>
                </div>
            </div>
        <el-button @click="buyTickets" 
        style="color: #ff7140;border:none;width: 100%;font-size: 18px;font-weight: bold;">选座购票</el-button>
    </el-card>
  </template>
  
  <script>
  export default {
    props: {
      movie: Object,
    },
    methods: {
      buyTickets() {
        console.log('购票按钮被点击');
        // 这里可以添加购票逻辑
      }
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
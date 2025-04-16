<template>
    <div class="movie-info">
      <el-row>
        <el-col :span="4">
          <el-image :src="movie.posterImage" alt="电影海报" class="movie-poster" style="height:280px;width:210px;">
            <div slot="error" class="image-slot" style="display:flex;align-items:center;justify-content:center;height:100%;
            background-color:#c9c9c9;color:#ccc;font-size:24px;font-weight:bold">
                <i class="el-icon-picture-outline"></i>
            </div>
          </el-image>
        </el-col>
        <el-col :span="20">
          <div class="movie-details">
            <div class="movie-title">{{ movie.movieName }}
                <span style="color:#ff7b4b;font-size:20px;margin-left:10px">{{movie.score}}分</span>
            </div>
            <div class="movie-rating">
                <el-tag type="danger" size="mini" v-for="item in movie.tags" :key="item.movieTypeId" class="movie-tag">{{ item.movieTypeName }}</el-tag>
                <el-tag type="warning" size="mini" class="movie-tag">{{ movie.issuingRegion }}</el-tag>
                <el-tag type="success" size="mini" class="movie-tag">{{ movie.movieDuration }}</el-tag>
            </div>
            <p class="movie-director">导演: {{ movie.director }}</p>
            <p class="movie-actors">主演: {{ movie.stars }}</p>
            <p class="movie-actors">上映时间: {{ formatDate(movie.movieStartTime) }}</p>
            <p class="movie-actors">剧情介绍: {{ movie.introduction }}</p>
          </div>
        </el-col>
      </el-row>
    </div>
  </template>
  
  <script>
  import dayjs from 'dayjs';
  export default {
    name: 'MovieInfo',
    props: {
      movie: {
        type: Object,
        required: true
      }
    },
    methods: {
      formatDate(date) {
        return dayjs(date).format('YYYY-MM-DD');
      }
    }
  }
  </script>
  
  <style scoped>
  .movie-info {
    padding: 20px;
    border: 1px solid #ebeef5;
    border-radius: 4px;
  }
  
  .movie-poster {
    width: 100%;
    border-radius: 4px;
  }
  
  .movie-details {
    padding-left: 20px;
  }
  
  .movie-title {
    font-size: 24px;
    font-weight: bold;
    color: #333;
  }
  
  .movie-rating {
    margin-top: 10px;
    margin-bottom: 10px;
  }
  
  .movie-tag {
    margin-right: 5px;
  }
  
  .movie-runtime,
  .movie-director,
  .movie-actors,
  .movie-plot {
    margin-bottom: 10px;
    color: #666;
    width: 80%
  }
  
  .el-button {
    margin-right: 10px;
  }
  </style>
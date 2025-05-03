<template>
    <div class="movie-info">
      <el-row>
        <el-col :span="4">
          <el-image :src="movie.posterImage" alt="电影海报" class="movie-poster" style="height:100%;width:100%">
            <div slot="error" class="image-slot" style="display:flex;align-items:center;justify-content:center;height:100%;
            background-color:#f5f7fa;min-height:35vh;
            ;color:#ccc;font-size:24px;font-weight:bold">
                <i class="el-icon-picture-outline"></i>
            </div>
          </el-image>
        </el-col>
        <el-col :span="20">
          <div class="movie-details">
            <div class="movie-title">{{ movie.movieName }}
            </div>
            <div class="movie-rating">
                <el-tag type="danger" size="mini" v-for="item in movie.tags" :key="item.movieTypeId" class="movie-tag">{{ item.movieTypeName }}</el-tag>
                <el-tag type="warning" size="mini" class="movie-tag">{{ movie.issuingRegion }}</el-tag>
                <el-tag type="success" size="mini" class="movie-tag">{{ movie.movieDuration }}</el-tag>
            </div>
            <p class="movie-director">
              <truncated-text :lines="1">
                影院:  {{ sliceArrangement.cinema.cinemaName }}
              </truncated-text>
            </p>
            <p class="movie-actors">
              <truncated-text :lines="1">
                影厅: {{ sliceArrangement.screeningRoom.screeningRoomName }}
              </truncated-text>
            </p>
            <p class="movie-actors">
              <truncated-text :lines="1">
                场次:  {{ utils.formatTimestampToYYYMMDDHHMMSS(sliceArrangement.sliceArrangementStartTime) }}
              </truncated-text>
            </p>
            <p class="movie-actors">票价: 
              ￥{{ sliceArrangement.votePrice }}/张
            </p>
            <p style="color: #666;" >座位:<span style="color:#ff5c33; word-break: break-word;">
              <span v-for="(seat,index) in selectedSeat" :key="index">
                {{ seat.x }}排{{ seat.y }}座
              </span>
            </span> 
          </p>
          </div>
        </el-col>
      </el-row>
    </div>
  </template>
  
  <script>
  import TruncatedText from './TruncatedText.vue';
  import dayjs from 'dayjs';
  import { utils } from '@/utils/globalUtils';
  export default {
    components:{
      TruncatedText
    },
    name: 'MovieInfo',
    data(){
      return{
        utils
      }
    },
    props: {
      movie: {
        type: Object,
        required: false,
        default: () => ({
            posterImage:'',
            movieName: 'defaultName',
            score: 0,
            tags: [],
            movieDuration: 'defaultDuration',
            issuingRegion:'defaultRegion',
            star: '',
            director: '',
            movieStartTime: '',
            introduction: ''
        })
      },
      sliceArrangement:{
        type:Object,
        required:false,
        default:{
          cinema:{
            cinemaName:'影院名称'
          },
          screeningRoom:{
            screeningRoomName:'影厅名称'
          },
          sliceArrangementStartTime:new Date(),
          votePrice:9.9,
        }
      },
      selectedSeat:{
          type: Array,
          required:false,
          default:[]
      }
    },
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
    margin-bottom: 5px;
    color: #666;
    width: 100%;
    display: -webkit-box; /* 用于旧版 Safari */
    -webkit-line-clamp: 2; /* 限制两行 */
    -webkit-box-orient: vertical; /* 垂直排列 */
    overflow: hidden; /* 隐藏溢出的内容 */
    text-overflow: ellipsis; /* 使用省略号表示溢出的内容 */
    white-space: normal; /* 允许换行 */
    line-height: 1.2; /* 设置行高，根据需要调整 */
    max-height: 3em; /* 两行的最大高度，根据行高调整 */
  }
  .el-button {
    margin-right: 10px;
  }
  </style>
<template>
  <div>
    <MovieCard :movie="movieData" />
    <FillMovieInfo :movie-data="movieData"/>
    <el-button type="primary" @click="insertMovie">立即添加</el-button>
  </div>
</template>

<script>
import MovieCard from '@/components/global/MovieCard';
import { myApi } from '@/axios/my';
import FillMovieInfo from '@/components/global/FillMovieInfo.vue';

export default {
  components: {
    MovieCard,FillMovieInfo
  },
  data() {
    return {
      movieData: {
        posterImage:'https://pic3.yzzyimg.online/upload/vod/2022-03-21/202203211647870271.jpg',
        movieName: '流浪地球',
        score: 9.3,
        tags: [
          {
            movieTypeId:"106dee87d0c64094a31f114381999b51",
            movieTypeName:"惊悚"
          }
        ],
        movieDuration: '120分钟',
        issuingRegion:'中国大陆',
        star: '张艺谋、张译、刘昊然',
        director: '郭帆',
        movieStartTime: '2022-03-22',
        introduction: '太阳即将毁灭，人类在地球表面建造出巨大的推进器，寻找新家园。然而宇宙之路危机四伏，为了拯救地球，为了人类能在漫长的2500年后抵达新的家园，流浪地球时代的年轻人挺身而出...'
      },
    };
  },
  methods:{
    insertMovie(){
      {
        if(this.movieData.posterImage.trim()=='') {
          this.$message.error('图片不能为空')
          return
        }
        if(this.movieData.movieName.trim()=='') {
          this.$message.error('电影名称不能为空')
          return
        }
        if(this.movieData.tags.length==0) {
          this.$message.error('电影类型不能为空')
          return
        }
        if(this.movieData.director.trim()=='') {
          this.$message.error('导演不能为空')
          return
        }
        if(this.movieData.star.trim()=='') {
          this.$message.error('主演不能为空')
          return
        }
        if(this.movieData.introduction.trim()=='') {
          this.$message.error('剧情介绍不能为空')
          return
        }
        if(this.movieData.movieStartTime=='') {
          this.$message.error('上映日期不能为空')
          return
        }
        if(this.movieData.issuingRegion.trim()==''){
          this.$message.error('发行地区不能为空')
          return
        }
        if(!this.movieData.score){
          this.$message.error('评分不能为空')
          return
        }
        if(this.movieData.movieDuration.trim()==''){
          this.$message.error('电影时长不能为空')
          return
        }
      }
      myApi.insertMovie(this.movieData).then(res=>{
        if(res.data.code==200){
          this.$message.success(res.data.message)
        }else{
          this.$message.error(res.data.message)
        }
      }).catch(e=>{
        console.log(e)
      })
    }
  },
  mounted(){
  },
  watch:{
    movieData(newValue,oldValue){
      console.log(newValue.tags)
    }
  }
}
</script>
<style scoped>

</style>
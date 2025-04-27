<template>
  <div>
    <MovieCard :movie="movieData" />
    <FillMovieInfo :movie-data="movieData"/>
    <el-button type="primary" @click="updateMovie">提交修改信息</el-button>
    <el-dialog title="请选择需要修改数据的电影"
      width="20%"
      :visible.sync="notSelectedMovie"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :show-close="false"
      :modal-append-to-body="false">
      <el-button type="primary" style="width: 100%;" @click="$router.push({name:'PlatformMovieListPage'})">去选择</el-button>

      </el-dialog>

  </div>
</template>

<script>
import MovieCard from '@/components/global/MovieCard';
import { myApi } from '@/axios/my';
import FillMovieInfo from '@/components/global/FillMovieInfo.vue';

export default {
  components: {
    MovieCard,
    FillMovieInfo
  },
  data() {
    return {
      notSelectedMovie:false,
      movieId:'',
      movieData: {
        movieId:'',
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
      },
    };
  },
  methods:{
    updateMovie(){
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
      myApi.updateMovie(this.movieData).then(res=>{
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
    this.movieId = this.$route.params.movieId
    if(!this.movieId){
      this.notSelectedMovie=true
      return
    }else{
      myApi.selectMovieListPlatform({movieId:this.movieId,page:1,size:1}).then(res=>{
        if(res.data.code==200){
          this.movieData=res.data.data.list[0]
        }
      })
    }
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
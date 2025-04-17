<template>
  <div>
    <MovieCard :movie="movieData" />
    <el-row>
      <el-col :span="24" style="margin-top: 10px;">
        <el-form ref="form" :model="movieData" label-width="80px">
        <el-form-item label="电影名称">
          <el-col :span="4">
            <el-input v-model="movieData.movieName" placeholder="输入电影名称"></el-input>
          </el-col>
          <el-col :span="20">
            <el-form-item label="图片路径">
              <el-input v-model="movieData.posterImage" placeholder="上传图片或使用url"></el-input>
            </el-form-item>
          </el-col>
          
        </el-form-item>
        <el-form-item label="电影类型">
          <el-col :span="4">
            <el-select value-key="movieTypeId" multiple v-model="movieData.tags" placeholder="选择电影类型">
              <el-option :label="item.movieTypeName" :value="item" v-for="item in movieType" :key="item.movieTypeId"></el-option>
            </el-select>
          </el-col>
          <el-col :span="6">
            <el-form-item label="上映日期">
              <el-date-picker type="date" placeholder="选择日期" v-model="movieData.movieStartTime"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="发行地区">
              <el-input v-model="movieData.issuingRegion" placeholder="输入发行地区"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="评分">
              <el-input v-model="movieData.score" placeholder="输入评分"></el-input>
            </el-form-item>
          </el-col>
        </el-form-item>
        <el-form-item label="导演">
          <el-col :span="4">
            <el-input v-model="movieData.director" placeholder="输入导演"></el-input>
          </el-col>
          <el-col :span="6">
            <el-form-item label="主演">
              <el-input v-model="movieData.star" placeholder="输入主演"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="14">
            <el-form-item label="上传图片">
              <el-upload
                action="javascript:void(0)"
                :http-request="customUpload"
                :on-remove="fileDelete"
                accept=".png,.jpg,.jpeg"
                :limit="1"
                class="upload-demo"
                :file-list="fileList">
                <el-button size="small" type="primary">点击上传</el-button>
                <span slot="tip" class="el-upload__tip" style="margin-left: 10px;">只能上传jpg/png文件，且不超过500kb</span>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-form-item>
        <el-form-item label="剧情介绍">
          <el-input type="textarea" v-model="movieData.introduction" placeholder="输入剧情介绍"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="insertMovie">立即添加</el-button>
        </el-form-item>
      </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import MovieCard from '@/components/global/MovieCard';
import { myApi } from '@/axios/my';

export default {
  components: {
    MovieCard
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
      movieType:[],
      fileList:[]
    };
  },
  methods:{
    customUpload(options){
      const {file}= options
      const formData = new FormData()
      formData.append('file',file);
      myApi.fileUpload(formData).then(resp=>{
        if(resp.data.code==200){
          this.movieData.posterImage=resp.data.data.fileUrl
          this.$message.success(resp.data.message)
        }else{
          this.$message.error(resp.data.message)
        }
        console.log(this.fileList)
      }).catch(e=>{
        console.log(e)
      })
    },
    fileDelete(){
      myApi.fileDelete(this.movieData.posterImage).then(res=>{
        if(res.data.code==200){
          this.$message.success(res.data.message)
          this.movieData.posterImage=''
        }else{
          this.$message.error(res.data.message)
        }
      }).catch(e=>{
        console.log(e)
      })
    },
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
    myApi.selectAllMovieType().then(res=>{
      this.movieType = res.data.data
    })
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
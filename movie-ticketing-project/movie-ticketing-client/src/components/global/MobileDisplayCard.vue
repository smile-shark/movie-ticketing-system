<template>
    <el-card class="movie-card" shadow="hover">
      <div class="movie-image">
        <div v-if="mobileDisplay.mobileDisplayImage" style="position: relative;"
         @mouseover="showDeleteImage=true" @mouseleave="showDeleteImage=false">
          <el-image :src="mobileDisplay.mobileDisplayImage" fit="fill">
              <div slot="error" class="image-slot">
                <i class="el-icon-picture-outline" style="width: 100%;"></i>
              </div>
          </el-image>
          <transition name="fade">
          <div v-if="showDeleteImage" style="position: absolute;top: 0;left: 0;width: 100%;height: 100%;
              background-color: rgba(0,0,0,0.3);display: flex;justify-content: center;align-items: center;
              color:white;font-size: 24px;cursor: pointer;">
                    <i class="el-icon-delete" @click="fileDelete"></i>
          </div>
          </transition>
        </div>
        <el-upload
          v-else class="avatar-uploader"
          action=""
          :show-file-list="false"
          list-type="picture-card"
          :http-request="customUpload" style="width: 100%;height: 100%;">
          
          <i class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </div>
      <div class="movie-info">
        <el-input v-model="mobileDisplay.mobileDisplayImage" placeholder="图片地址"></el-input>
        <p></p>
        <el-select
          filterable v-model="mobileDisplay.movieId" placeholder="关联的电影">
          <el-option
            v-for="movie in movies"
            :key="movie.movieId"
            :value="movie.movieId"
            :label="movie.movieName">
          </el-option>
        </el-select>
        <p></p>
        <el-date-picker
          v-model="mobileDisplay.mobileDisplayEndTime"
          type="date"
          placeholder="到期的时间">
        </el-date-picker>
      </div>
      <div class="movie-actions">
        <el-button type="success" size="mini" @click="insertMobileDisplay" v-if="mobileDisplay.isNew">添加</el-button>
        <div v-else>
        <el-button type="primary" size="mini" @click="updateMobileDisplay">更新</el-button>
        <el-button type="danger" size="mini" @click="deleteMobileDisplay">删除</el-button>
        </div>
      </div>
    </el-card>
  </template>
  
  <script>
import { myApi } from '@/axios/my';

  export default {
    props: {
      mobileDisplay: {
        type: Object,
        default: () => ({})
      },
      isNew:{
        type: Boolean,
        default:false
      },
      searchMobileDisplays:Function
    },
    data() {
      return {
        selectedMovie: null,
        expiryDate: '',
        movies: [],
        showDeleteImage:false
      };
    },
    mounted(){
        this.selectSimpleMovieList()
    },
    methods: {
      verify(){
        if(this.mobileDisplay.movieId==''){
          this.$message.error('请选择关联的电影');
          return false;
        }
        if(this.mobileDisplay.mobileDisplayEndTime==''){
          this.$message.error('请选择到期的时间');
          return false;
        }
        if(this.mobileDisplay.mobileDisplayImage==''){
          this.$message.error('请上传图片');
          return false;
        }
        return true;
      },
      selectSimpleMovieList(){
        myApi.selectSimpleMovieList().then(res=>{
          if(res.data.code==200){
            this.movies=res.data.data;
          }else{
            this.$message.error(res.data.message);
          }
        })
      },
      insertMobileDisplay(){
        if(!this.verify()) return
        myApi.insertMoblieDidpslay(this.mobileDisplay).then(res => {
          if (res.data.code == 200) {
            this.$message.success(res.data.message);
            this.searchMobileDisplays()
          } else {
            this.$message.error(res.data.message);
          }
        });
      },
      updateMobileDisplay() {
        if(!this.verify()) return
        myApi.updateMobileDisplay(this.mobileDisplay).then(res => {
          if (res.data.code == 200) {
            this.$message.success(res.data.message);
            this.searchMobileDisplays()
          } else {
            this.$message.error(res.data.message);
          }
        });
      },
      deleteMobileDisplay() {
        this.$confirm('此操作将永久删除该条数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          myApi.deleteMobileDisplay(this.mobileDisplay.mobileDisplayImage).then(res=>{
            if(res.data.code==200){
              this.$message.success(res.data.message)
              this.searchMobileDisplays()
            }else{
              this.$message.error(res.data.message)
            }
          })
        }).catch(() => {
        })
      },
      customUpload(options){
            const {file}= options
            const formData = new FormData()
            formData.append('file',file);
            myApi.fileUpload(formData).then(resp=>{
                if(resp.data.code==200){
                this.mobileDisplay.mobileDisplayImage=resp.data.data.fileUrl
                this.$message.success(resp.data.message)
                }else{
                this.$message.error(resp.data.message)
                }
            }).catch(e=>{
                console.log(e)
            })
        },
        fileDelete(){
            myApi.fileDelete(this.mobileDisplay.mobileDisplayImage).then(res=>{
                if(res.data.code==200){
                    this.$message.success(res.data.message)
                }else{
                    this.$message.error(res.data.message)
                }
                this.mobileDisplay.mobileDisplayImage=''
            }).catch(e=>{
                console.log(e)
                this.mobileDisplay.mobileDisplayImage=''
            })
        },
    },
  }
  
  </script>
  
  <style scoped>
  .fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}
.fade-enter,
.fade-leave-to {
  opacity: 0;
}
    .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 80px;
    height: 80px;
    line-height: 80px;
    text-align: center;
  }
  .avatar {
    width: 80px;
    height: 80px;
    display: block;
  }
  .movie-card {
    width: 300px;
    margin: 20px;
    text-align: center;
  }
  .movie-image {
    width: 100%;
    min-height: 60px;
  }
  .movie-info {
    margin: 10px 0;
  }
  .movie-actions {
    margin-top: 20px;
  }
  .image-slot {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100%;
    background-color: #f5f7fa;
    color: #8c939d97;
    font-size: 24px;
  }
  </style>
<template>
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
    </el-form>
</template>

<script>
import { myApi } from '@/axios/my';
export default {
    name: "FillMovieInfo",
    props: {
        movieData: {
            type: Object,
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
    },
    data(){
        return{
            movieType:[],
            fileList:[]
        }
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
    },
    mounted(){
        myApi.selectAllMovieType().then(res=>{
            this.movieType = res.data.data
        })
    }
}
</script>

<style>

</style>
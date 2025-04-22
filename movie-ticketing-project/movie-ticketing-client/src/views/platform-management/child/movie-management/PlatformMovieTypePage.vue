<template>
  <div>
    <el-row>
        <el-col :span="12">
            <h2>电影类型池</h2>
            <div style="min-height: 40vh;border: 1px solid #dcdfe6;padding:20px;border-radius: 5px;">
                <el-tag type="danger" v-for="tag in movieTypes" :key="tag.movieTypeId" style="margin:3px" closable
                @close="deleteMovieType(tag.movieTypeId)">{{tag.movieTypeName}}</el-tag>
            </div>
        </el-col>
        <el-col :span="12">
            <h2>添加/搜索电影类型</h2>
            <div style="padding:20px;">
                <div style="padding:10px;">
                    <el-input v-model="newMovieType" placeholder="请输入电影类型"></el-input>   
                </div>
                <div style="padding:10px;">
                    <el-button type="primary" @click="addMovieType">添加</el-button>
                    <el-button type="primary" @click="searchMovieType">搜索</el-button>
                </div>
            </div>
        </el-col>
    </el-row>
  </div>
</template>

<script>
import { myApi } from '@/axios/my';
export default {
    data(){
        return{
            movieTypes:[],
            newMovieType:''
        }
    },
    methods:{
        selectAllMovieType(){
            myApi.selectAllMovieType().then(res=>{
                if(res.data.code==200){
                    this.movieTypes = res.data.data
                }else{
                    this.$message.error(res.data.message)
                }
            }).catch(e=>{
                console.log(e)
            })
        },
        addMovieType(){
            if(this.newMovieType==''){
                this.$message.error('请输入电影类型')
                return
            }
            myApi.insertMovieType(this.newMovieType).then(res=>{
                if(res.data.code==200){
                    this.$message.success(res.data.message)
                    this.selectAllMovieType()
                }else{
                    this.$message.error(res.data.message)
                }
            }).catch(e=>{
                console.log(e)
            })
        },
        searchMovieType(){
            myApi.selectMovieTypesByName(this.newMovieType).then(res=>{
                if(res.data.code==200){
                    this.movieTypes=res.data.data
                    this.$message.success(res.data.message)
                }else{
                    this.$message.error(res.data.message)
                }
            }).catch(e=>{
                console.log(e)
            })
        },
        deleteMovieType(movieTypeId){
            myApi.deleteMovieType(movieTypeId).then(res=>{
                if(res.data.code==200){
                    this.$message.success(res.data.message)
                    this.selectAllMovieType()
                }else{
                    this.$message.error(res.data.message)
                }
            })
        }
    },
    mounted(){
       this.selectAllMovieType()
    }
}
</script>

<style>

</style>
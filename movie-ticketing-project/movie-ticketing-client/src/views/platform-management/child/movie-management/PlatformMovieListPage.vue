<template>
  <div> 
    <el-row>
        <el-col :span="24">
            <el-table :data="movieListInfo.list">
                <el-table-column label="电影名称">
                    <template slot-scope="scope">
                        <TruncatedText :lines="1">
                            {{ scope.row.movieName }}
                        </TruncatedText>
                    </template>
                </el-table-column>
                <el-table-column label="电影类型">
                    <template slot-scope="scope">
                        <TruncatedText :lines="1">
                            <el-tag type="danger" size="mini" v-for="item in scope.row.tags" :key="item.movieTypeId" style="margin-right:5px">{{ item.movieTypeName }}</el-tag>
                        </TruncatedText>
                    </template>
                </el-table-column>
                <el-table-column label="导演" prop="director"></el-table-column>
                <el-table-column label="主演">
                    <template slot-scope="scope">
                        <TruncatedText>{{ scope.row.star}}</TruncatedText>
                    </template>
                </el-table-column>
                <el-table-column label="发行地" prop="issuingRegion"></el-table-column>
                <el-table-column label="时长" prop="movieDuration"></el-table-column>
                <el-table-column label="简介">
                    <template slot-scope="scope">
                        <TruncatedText>{{ scope.row.introduction }}</TruncatedText>
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button type="text" @click="handleEdit(scope.row.movieId)">编辑</el-button>
                        <el-button type="text" @click="handleDelete(scope.row.movieId)">下架</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-col>
    </el-row>
    <el-row>
        <el-col :span="24" style="padding:20px;">
            <el-pagination
                background
                layout="prev, pager, next"
                :page-size="size"
                @current-change="selectMovieListPlatform"
                :total="movieListInfo.total">
            </el-pagination>
        </el-col>
    </el-row>
  </div>
</template>

<script>
import { myApi } from '@/axios/my';
import TruncatedText from '@/components/global/TruncatedText.vue';
export default {
    components:{
        TruncatedText
    },
    data(){
        return {
            page:1,
            size:10,
            movieListInfo:{
                total:0,
                list:[]
            }
        }
    },
    methods:{
        selectMovieListPlatform(page=1){
            this.page=page
            myApi.selectMovieListPlatform({page:this.page,size:this.size}).then(res=>{
                if(res.data.code==200){
                    this.movieListInfo=res.data.data
                }
            })
        },
        handleEdit(movieId){
            this.$router.push({name:'PlatformMovieUpdatePage',params: { movieId: movieId } })
        },
    },
    mounted(){
        this.selectMovieListPlatform()
    }
}
</script>

<style>

</style>
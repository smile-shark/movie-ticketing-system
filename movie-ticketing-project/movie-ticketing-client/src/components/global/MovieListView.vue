<template>
  <div>
    <el-row>
        <el-col :span="24" v-if="hotScreeningInProgresData.list.length==0">
            <el-empty style="min-height: 80vh;">
                <el-button @click="$router.push('/customer/home')">返回首页</el-button>
            </el-empty>
        </el-col>
        <el-col :span="6" v-for="(movie,index) in hotScreeningInProgresData.list" :key="index">
            <MovieListCard :movie="movie"></MovieListCard>
        </el-col>
    </el-row>
    <el-row>
        <el-col :span="24" style="padding:20px;display: flex;justify-content: center;align-items: center;" class="jp_pageBox">
            <el-pagination
            background
            :hide-on-single-page="hotScreeningInProgresData.list.length==0"
            layout="prev, pager, next"
            :page-size="size"
            @current-change="handlePageChange"
            :total="hotScreeningInProgresData.total">
            </el-pagination>
        </el-col>
    </el-row>
  </div>
</template>

<script>
import { myApi } from '@/axios/my';
import MovieListCard from './MovieListCard.vue';
export default {
    components:{
        MovieListCard
    },
    props:{
        classification:String
    },
    data(){
        return{
            hotScreeningInProgresData:{
                total:0,
                list:[]
            },
            page:1,
            size:8,
            already:null,
            movieName:null
        }
    },
    mounted(){
        // 根据这个来区分是正在热映还是即将上映
        switch(this.classification){
            case '1': this.already=0;break;
            case '2': this.already=1;break;
            default: this.already=null;break;
        }
        if(this.$route.params.movieName){
            this.movieName=this.$route.params.movieName
        }
        this.handlePageChange()
    },
    methods:{
        handlePageChange(page=1){
            this.page=page
            myApi.selectMovieList({movieName:this.movieName,already:this.already,page:this.page,size:this.size}).then(res=>{
                if(res.data.code==200){
                    this.hotScreeningInProgresData=res.data.data
                }
            }).catch(e=>{
                console.log(e)
            })
        }
    }
}
</script>

<style scoped>
.el-pagination.is-background .btn-next, 
.el-pagination.is-background .btn-prev, 
.el-pagination.is-background .el-pager li{
    background-color: #fff !important;
    border:solid 1px #ccc !important;
    border-radius: 15% !important;
}
.jp_pageBox .el-pagination.is-background .el-pager .active {
    background-color: #ff7b32 !important;
    border:none !important;
    }
</style>
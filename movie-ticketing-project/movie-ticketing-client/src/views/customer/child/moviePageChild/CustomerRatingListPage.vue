<template>
  <div>
    <el-row>
        <el-col :span="24">
            <MovieRatingCard v-for="(movie,index) in movies" :key="index"
            :movie="movie"></MovieRatingCard>
        </el-col>
    </el-row>
  </div>
</template>

<script>
import { myApi } from '@/axios/my';
import MovieRatingCard from '@/components/global/MovieRatingCard.vue';
export default {
    components:{
        MovieRatingCard
    },
    data(){
        return {
            movies:[]
        }
    },
    methods:{
        selectMovieListOrderByScore(){
            myApi.selectMovieListOrderByScore().then(res=>{
                if(res.data.code==200){
                    this.movies=res.data.data
                }else{
                    this.$message.error(res.data.message)
                }
            }).catch(e=>{
                console.log(e)
            })
        }
    },
    mounted(){
        this.selectMovieListOrderByScore()
    }
}
</script>

<style>

</style>
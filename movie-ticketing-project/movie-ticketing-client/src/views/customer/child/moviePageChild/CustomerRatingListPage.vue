<template>
  <div>
    <el-row>
        <el-col :span="24">
           <AnimateView>
            <MovieRatingCard v-for="(movie,index) in movies" :key="index" :data-index="index"   
            :movie="movie">
            {{ index+1 }}
        </MovieRatingCard>
           </AnimateView>
        </el-col>
    </el-row>
  </div>
</template>

<script>
import { myApi } from '@/axios/my';
import MovieRatingCard from '@/components/global/MovieRatingCard.vue';
import AnimateView from '@/components/global/AnimateView.vue';
export default {
    components:{
        MovieRatingCard,AnimateView
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
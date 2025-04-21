<template>
  <div>
    <el-row v-if="movieData!=null">
      <el-col :span="24">
        <MovieCard :movie="movieData"></MovieCard>
      </el-col>
    </el-row>
    <el-row v-else>
      <el-col :span="24">
        <el-empty style="min-height: 80vh;">
          <el-button @click="$router.push('/customer/home')">返回首页</el-button>
        </el-empty>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { myApi } from '@/axios/my';
import MovieCard from '@/components/global/MovieCard.vue';
export default {
  components:{
    MovieCard
  },
  data(){
    return{
      movieData:null
    }
  },
  mounted(){
    myApi.selectMovieList({movieId:this.$route.params.movieId,size:1,page:1}).then(res=>{
      if(res.data.code==200){
        if(res.data.data.list.length>0){
          this.movieData=res.data.data.list[0];
        }
      }
    }).catch(e=>{
      console.log(e)
    })
  }
}
</script>

<style>

</style>
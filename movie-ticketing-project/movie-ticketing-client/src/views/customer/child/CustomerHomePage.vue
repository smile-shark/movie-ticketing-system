<template>
  <div>
    <el-row>
        <el-col :span="24">
            <!-- <el-carousel height="52vh"> -->
            <el-carousel height="30vh" type="card">
                <el-carousel-item v-for="(mobileDispaly,index) in mobileDisplays" :key="index" style="height: 100%;" >
                    <el-image :src="mobileDispaly.mobileDisplayImage" style="width: 100%;height: 100%" fit="fill"
                     @click="$router.push({name:'CustomerMovieDetailPage',params:{movieId:mobileDispaly.movieId}})">
                        <div slot="error" class="image-slot" style="background-color: #f9f9f9;display: flex;align-items: center;justify-content: center;">
                            <i class="el-icon-picture-outline"></i>
                        </div>
                    </el-image>
                </el-carousel-item>
            </el-carousel>
        </el-col>
    </el-row>
    <el-row style="padding: 20px;" :hidden="hotScreeningInProgresData.total==0">
        <el-col :span="24">
            <el-row>
                <el-col :span="24">
                    <div style="background-color: #f2f2f2;position:relative;padding:10px;">
                        <div style="text-align: center;font-size: 18px;font-weight: bold;">
                            正在热映 ({{ hotScreeningInProgresData.total }})
                        </div>
                        <div style="position: absolute;right: 0;top: 0;padding: 10px;font-size: 14px;">
                            <el-link :underline="false">
                                更多<i class="el-icon-arrow-right"></i>
                            </el-link>
                        </div>
                    </div>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="6" v-for="(movie,index) in hotScreeningInProgresData.list" :key="index">
                    <MovieListCard :movie="movie"></MovieListCard>
                </el-col>
            </el-row>
        </el-col>
    </el-row>
    <el-row style="padding: 20px;" :hidden="hotScreeningInProgresData.total==0">
        <el-col :span="24">
            <el-row>
                <el-col :span="24">
                    <div style="background-color: #f2f2f2;position:relative;padding:10px;">
                        <div style="text-align: center;font-size: 18px;font-weight: bold;">
                            即将上映 ({{ hotScreeningInProgresData.total }})
                        </div>
                        <div style="position: absolute;right: 0;top: 0;padding: 10px;font-size: 14px;">
                            <el-link :underline="false">
                                更多<i class="el-icon-arrow-right"></i>
                            </el-link>
                        </div>
                    </div>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="6" v-for="(movie,index) in hotScreeningInProgresData.list" :key="index">
                    <MovieListCard :movie="movie"></MovieListCard>
                </el-col>
            </el-row>
        </el-col>
    </el-row>
  </div>
</template>

<script>
import { myApi } from '@/axios/my';
import MovieListCard from '@/components/global/MovieListCard.vue';
export default {
    components:{
        MovieListCard
    },
    data(){
        return {
            mobileDisplays:[],
            hotScreeningInProgresData:{
                total:0,
            }
        }
    },
    methods:{
        selectAllAllowedMobileDisplays(){
            myApi.selectAllAllowedMobileDisplays().then(res=>{
                if(res.data.code==200){
                    this.mobileDisplays=res.data.data
                }
            }).catch(err=>{
                console.log(err)
            })
        },
        selectHotScreeningInProgress(){
            myApi.selectMovieList(
                {
                    already: 0,
                    size: 4
                }
            ).then(res=>{
                if(res.data.code==200){
                    this.hotScreeningInProgresData=res.data.data
                }
            })
        }
    },
    mounted(){
        this.selectAllAllowedMobileDisplays()
        this.selectHotScreeningInProgress()
    }
}
</script>

<style>

</style>
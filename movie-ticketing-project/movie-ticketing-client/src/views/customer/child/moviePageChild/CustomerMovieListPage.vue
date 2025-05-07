<template>
  <div style="padding:20px;">
    <el-row>
        <el-col :span="24" >
            <el-image v-if="advertisement.advertisementImage" style="min-width: 100%;" :src="advertisement.advertisementImage" @click="toAdvertisementPage">
                <div slot="error" class="image-slot" style="background-color: #f5f7fa;
                display: flex;align-items: center;justify-content: center;min-height: 20vh;">
                    <i class="el-icon-picture-outline"></i>
                </div>
            </el-image>
        </el-col>
    </el-row>
    <el-row>
        <el-col :span="24">
            <el-menu :default-active="classification" class="el-menu-demo" mode="horizontal"
            active-text-color="#ff7b32">
                <el-menu-item index="1">正在热映</el-menu-item>
                <el-menu-item index="2">即将上映</el-menu-item>
            </el-menu>
        </el-col>
    </el-row>
    <el-row>
        <el-col :span="24">
            <MovieListView :classification="classification"></MovieListView>
        </el-col>
    </el-row>
  </div>
</template>

<script>
import { myApi } from '@/axios/my';
import MovieListView from '@/components/global/MovieListView.vue';
export default {
    components:{
        MovieListView
    },
    data(){
        return{
            classification: '1',
            advertisement:{},
        }
    },
    mounted(){
        myApi.selectOneAdvertisementByRandom().then(res=>{
            if(res.data.code==200){
                this.advertisement=res.data.data
            }
            console.log(this.advertisement)
        }).catch(e=>{
            console.log(e)
        })
    },
    methods:{
        toAdvertisementPage(){
            window.open(this.advertisement.advertisementUrl,'_blank')
        }
    }
}   
</script>

<style>

</style>
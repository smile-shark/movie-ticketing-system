<template>
  <div>
    <el-row>
        <el-col :span="24">
            <MovieCard v-if="movie.movieName" :movie="movie"></MovieCard>
            <MovieCard v-else></MovieCard>
        </el-col>
    </el-row>
    <el-row>
        <el-col :span="24" style="padding: 20px;">
            <el-form :model="sliceArrangement" label-width="100px">
                <el-col :span="6">
                    <el-form-item label="选择电影">
                        <el-select v-model="sliceArrangement.movieId" placeholder="请选择电影"
                        filterable @change="cinemaSelectMovieList">
                            <el-option v-for="(movie,index) in movies" :key="index"
                             :label="movie.movieName" :value="movie.movieId"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="选择放映厅">
                        <el-select v-model="sliceArrangement.screeningRoomId" placeholder="请选择放映厅"
                        filterable>
                            <el-option v-for="(screeningRoom,index) in screeningRooms" :key="index"
                             :label="screeningRoom.screeningRoomName" :value="screeningRoom.screeningRoomId"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="开始时间">
                        <el-date-picker v-model="sliceArrangement.sliceArrangementStartTime" type="datetime" placeholder="选择开始时间"
                        @change="handleStartTimeChange"></el-date-picker>
                    </el-form-item>
                    <el-form-item label="结束时间">
                        <el-date-picker v-model="sliceArrangement.sliceArrangementEndTime" type="datetime" placeholder="选择结束时间"></el-date-picker>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="票价">
                        <el-input v-model="sliceArrangement.votePrice" placeholder="请输入票价"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    &nbsp;
                </el-col>
                <el-col :span="6">
                    <el-form-item>
                        &nbsp;
                    </el-form-item>
                    <el-form-item>
                        &nbsp;
                    </el-form-item>
                    <el-form-item>
                        &nbsp;
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="insertSliceArrangement">创建排片</el-button>
                    </el-form-item>
                </el-col>
            </el-form>
        </el-col>
    </el-row>
  </div>
</template>

<script>
import { myApi } from '@/axios/my';
import dayjs from 'dayjs';
import MovieCard from '@/components/global/MovieCard.vue';
export default {    
    components:{
        MovieCard
    },
    data(){
        return{
            sliceArrangement:{
                cinemaId:'',
                movieId:'',
                screeningRoomId:'',
                votePrice:'',
                sliceArrangementStartTime:'',
                sliceArrangementEndTime:'',
                layout:''
            },
            movies:[],
            screeningRooms:[],
            movie:{}
        }
    },
    methods:{
        cinemaSelectSimpleMovieList(){
            myApi.cinemaSelectSimpleMovieList().then(res=>{
                if(res.data.code==200){
                    this.movies=res.data.data
                }
            }).catch(e=>{
                console.log(e)
            })
        },
        cinemaSelectMovieList(){
            myApi.cinemaSelectMovieList({movieId:this.sliceArrangement.movieId,page:1,size:1}).then(res=>{
                if(res.data.code==200){
                    this.movie=res.data.data.list[0]
                }else{
                    this.$message.error(res.data.message)
                }
            }).catch(e=>{
                console.log(e)
            })
        },
        selectSimpleScreeningRoom(){
            myApi.selectSimpleScreeningRoom().then(res=>{
                if(res.data.code==200){
                    this.screeningRooms=res.data.data
                }
            }).catch(e=>{
                console.log(e)
            })
        },
        insertSliceArrangement(){
            if(localStorage.getItem('cinemaId')){
                this.sliceArrangement.cinemaId=localStorage.getItem('cinemaId')
            }else{
                this.$message.error('请选择影院')
                return
            }
            if(this.sliceArrangement.movieId==''){
                this.$message.error('请选择电影')
                return
            }
            if(this.sliceArrangement.screeningRoomId==''){
                this.$message.error('请选择放映厅')
                return
            }
            try{
                this.sliceArrangement.votePrice=Number(this.sliceArrangement.votePrice)
            }catch(e){
                this.$message.error('请输入正确的价格')
                return
            }
            if(this.sliceArrangement.sliceArrangementStartTime==''){
                this.$message.error('请选择开始时间')
                return
            }
            if(this.sliceArrangement.sliceArrangementEndTime==''){
                this.$message.error('请选择结束时间')
                return
            }
            if(this.sliceArrangement.sliceArrangementStartTime>=this.sliceArrangement.sliceArrangementEndTime){
                this.$message.error('结束时间必须大于开始时间')
                return
            }
            if(this.sliceArrangement.sliceArrangementStartTime.getTime&&this.sliceArrangement.sliceArrangementStartTime.getTime()<new Date().getTime()){
                this.$message.error('开始时间必须大于当前时间')
                return
            }
            // 根据选择的放映厅查询对应的座位布局，然后进行处理
            myApi.selectScreeningRoomByScreeningRoomId(this.sliceArrangement.screeningRoomId).then(res=>{
                if(res.data.code==200){
                    let layout=JSON.parse(res.data.data.layout)
                    for(let row of layout){
                        for(let seat of row){
                            if(seat.seat){
                                seat.color='white'
                                seat.borderColor='black'
                                // 是否已被订购
                                seat.isBuy=false
                                // 是否选择
                                seat.isSelected=false
                            }else{
                                seat.color='white'
                                seat.borderColor='white'
                            }
                        }
                    }
                    this.sliceArrangement.layout=JSON.stringify(layout)
                    myApi.insertSliceArrangement(this.sliceArrangement).then(res=>{
                        if(res.data.code==200){
                            this.$message.success(res.data.message)
                        }else{
                            this.$message.error(res.data.message)
                        }
                    }).catch(e=>{
                        console.log(e)
                    })
                }else{
                    this.$message.error('布局获取失败')
                }
            }).catch(e=>{
                console.log(e)
            })
        },
        handleStartTimeChange() {
            // 确保 sliceArrangementStartTime 是一个 Date 对象
            let startTime = new Date(this.sliceArrangement.sliceArrangementStartTime);
            console.log(startTime);

            // 创建一个新的 Date 对象，表示开始时间加上两个小时
            let endTime = new Date(startTime.getTime() + 1000 * 60 * 60 * 2);
            this.sliceArrangement.sliceArrangementEndTime = endTime;

            // 如果需要，将 endTime 转换回字符串或其他格式
            this.sliceArrangement.sliceArrangementEndTime = endTime.toISOString();
        }
    },
    mounted(){
        this.cinemaSelectSimpleMovieList()
        this.selectSimpleScreeningRoom()
    }
}
</script>

<style>

</style>
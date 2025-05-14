<template>
  <div style="padding:20px">
    <el-row>
        <el-col :span="24">
            <el-row v-if="cinema!=null">
                <el-col :span="5">
                <el-image :src="cinema.cinemaBrand.cinemaBrandLogo" v-if="!utils.getsmall()"
                style="width: 180px;height: 180px;border-radius: 5px;" fit="cover"/>
                </el-col>
                <el-col :span="19" class="cinema-info">
                <div style="padding-bottom: 20px;font-weight:100;font-size: 1.5rem;">{{ cinema.cinemaName }}</div>
                <div style="font-size:14px;">
                    <div class="rating">
                        <span class="activity">活动</span>
                        <span class="score">评分：{{ cinema.cinemaPoints }}分</span>
                    </div>
                    <div class="address">
                        地址：{{ cinema.cinemaAddress }}
                        <el-link href="#">【查看地图】</el-link>
                    </div>
                    <div class="phone">
                        电话：{{ cinema.cinemaPhone }}
                    </div>
                </div>
                </el-col>
            </el-row>
        </el-col>
    </el-row>
    <el-row>
        <el-col :span="24">
            <MovieCard :movie="movie" v-if="movie!=null"></MovieCard>
        </el-col>
    </el-row>
    <el-row>
        <el-col :span="24">
            <h3>选择时间</h3>
            <el-table :data="sliceArrangements" style="width: 100%;">
                <el-table-column label="开始时间">
                    <template slot-scope="scope">
                        <TruncatedText :lines="1">
                            {{ utils.formatTimestampToYYYMMDDHHMMSS(scope.row.sliceArrangementStartTime) }}
                        </TruncatedText>
                    </template>
                </el-table-column>
                <el-table-column label="结束时间">
                    <template slot-scope="scope">
                        <TruncatedText :lines="1">
                            {{ utils.formatTimestampToYYYMMDDHHMMSS(scope.row.sliceArrangementEndTime) }}
                        </TruncatedText>
                    </template>
                </el-table-column>
                <el-table-column label="放映厅">
                    <template slot-scope="scope">
                        <TruncatedText :lines="1">
                            {{ scope.row.screeningRoom.screeningRoomName }}
                        </TruncatedText>
                    </template>
                </el-table-column>
                <el-table-column label="售价（元）">
                    <template slot-scope="scope">
                        <TruncatedText :lines="1" :customStyle="{color:'#ff7b32'}">
                           ￥{{ scope.row.votePrice }}
                        </TruncatedText>
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button type="warning" @click="selectSliceArrangement(scope.row)" size="small">选座购票</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-col>
        <el-col :span="24" style="padding:20px;">
            <el-pagination
            background
            layout="prev, pager, next"
            @current-change="customerSelectSliceArrangementBySliceArrangement(1)"
            :page-size="size"
            :total="total">
            </el-pagination>
        </el-col>
    </el-row>
  </div>
</template>

<script>
import { myApi } from '@/axios/my'
import { utils } from '@/utils/globalUtils'
import MovieCard from '@/components/global/MovieCard.vue'
import TruncatedText from '@/components/global/TruncatedText.vue'
export default {
    components:{
        MovieCard,TruncatedText
    },
    data(){
        return{
            movieId:null,
            cinemaId:null,
            cinema:null,
            movie:null,
            page:1,
            size:5,
            total:0,
            sliceArrangements:[],
            utils
        }
    },
    methods:{
        selectMovieList(){
            myApi.selectMovieList({movieId:this.movieId,page:1,size:1}).then(res=>{
                if(res.data.code==200){
                    this.movie=res.data.data.list[0]
                }else{
                    this.$message.error(res.data.message)
                }
            }).catch(e=>{
                console.log(e)
            })
        },
        customerSelectSliceArrangementBySliceArrangement(page=1){
            this.page=page
            myApi.customerSelectSliceArrangementBySliceArrangement(
                {movieId:this.movieId,cinemaId:this.cinemaId,startTime:new Date(),page:this.page,size:this.size}
            ).then(res=>{
                if(res.data.code==200){
                    this.total=res.data.data.total
                    this.sliceArrangements=res.data.data.list
                }else{
                    this.$message.error(res.data.message)
                }
            }).catch(e=>{
                console.log(e)
            })
        },
        selectSliceArrangement(row){
            this.$router.push({
                name:'CustomerSelectSeatPage',
                params:{
                    sliceArrangementId:row.sliceArrangementId
                }
            })
        }
    },
    mounted(){
        this.movieId=this.$route.query.movieId
        this.cinemaId=this.$route.query.cinemaId
        myApi.selectCinemaByCinemaId(this.cinemaId).then(res=>{
            if(res.data.code==200){
                this.cinema=res.data.data
            }else{
                this.$message.error(res.data.message)
            }
        }).catch(e=>{
            console.log(e)
        })
        this.selectMovieList(),
        this.customerSelectSliceArrangementBySliceArrangement()
    }
}
</script>


<style scoped>

.cinema-image img {
  width: 100%;
  height: auto;
}

.cinema-info {
  padding: 10px;
}

.rating {
  color: #ff9800;
  margin-bottom: 10px;
}

.activity {
  background-color: #ff5722;
  color: white;
  padding: 2px 5px;
  border-radius: 3px;
  margin-right: 5px;
}

.score {
  color: white;
  padding: 2px 5px;
  background-color: #f6b836;
  border-radius: 3px;
  display: inline-block;
  margin-left: 5px;
}

.address a {
  color: #409EFF;
  text-decoration: none;
}

.phone {
  margin-top: 5px;
}

.price {
  margin-top: 10px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.price span {
  font-size: 18px;
  color: #ff5722;
}

.price .el-button {
  margin-left: 10px;
}
</style>

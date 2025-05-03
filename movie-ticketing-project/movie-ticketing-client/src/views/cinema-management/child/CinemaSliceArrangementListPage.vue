<template>
  <div>
    <el-row>
        <el-col :span="24">
            <span style="padding:10px">
                选择指定电影：
                <el-select v-model="movieId" filterable clearable>
                    <el-option v-for="(moive,index) in movies" :key="index"
                        :label="moive.movieName" :value="moive.movieId"></el-option>
                </el-select>
            </span>
            <span style="padding:10px">
                选择指定放映厅：
                <el-select v-model="screeningRoomId" filterable clearable>
                    <el-option v-for="(screeningRoom,index) in screeningRooms" :key="index"
                        :label="screeningRoom.screeningRoomName" :value="screeningRoom.screeningRoomId"></el-option>
                </el-select>
            </span>
            <span style="padding:10px">
                指定时间之后：
                <el-date-picker v-model="startTIme" type="datetime" placeholder="选择日期时间"></el-date-picker>
            </span>
            <span style="padding:10px">
                指定时间之前：
                <el-date-picker v-model="entTime" type="datetime" placeholder="选择日期时间"></el-date-picker>
            </span>

        </el-col>
    </el-row>
    <el-row>
        <el-col :span="24">
            <el-table :data="sliceArrangements" style="width: 100%;min-height: 60vh;">
                <el-table-column label="电影">
                    <template slot-scope="scope">
                        <TruncatedText :lines="1">
                        {{ scope.row.movie.movieName }}
                        </TruncatedText>
                    </template>
                </el-table-column>
                <el-table-column label="电影院">
                    <template slot-scope="scope">
                        <TruncatedText :lines="1">
                            {{ scope.row.cinema.cinemaName }}
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
                <el-table-column label="放映时长">
                    <template slot-scope="scope">
                        <TruncatedText :lines="1">
                            {{ utils.timeStampToMinutes(scope.row.sliceArrangementEndTime-scope.row.sliceArrangementStartTime) }} 分钟
                        </TruncatedText>
                    </template>
                </el-table-column>
                <el-table-column label="放映时间">
                    <template slot-scope="scope">
                        <TruncatedText :lines="1">
                            {{ utils.formatTimestampToYYYMMDDHHMMSS(scope.row.sliceArrangementStartTime) }} - {{ utils.formatTimestampToYYYMMDDHHMMSS(scope.row.sliceArrangementEndTime) }}
                        </TruncatedText>
                    </template>
                </el-table-column>
                <el-table-column label="票价">
                    <template slot-scope="scope">
                       ￥ {{ scope.row.votePrice }} 
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button type="text" @click="handleEdit(scope.row)">详情</el-button>
                        <el-button type="text" @click="getOrderList(scope.row)">订单列表</el-button>
                        <el-button type="text" @click="handleDelete(scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-col>
    </el-row>
    <el-row>
        <el-col :span="24" style="padding: 20px;display: flex;justify-content: center;">
            <el-pagination
            background
            layout="prev, pager, next"
            :page-size="size"
            :total="total">
            </el-pagination>
            <el-button type="primary" size="small" @click="selectSliceArrangementBySliceArrangement(1)">搜索</el-button>
        </el-col>
    </el-row>
    <el-dialog title="排片详情" :visible.sync="sliceArrangementDialogVisible" width="80%">
        <el-form v-if="showSliceArrangement.movie" label-width="100px">
            <el-form-item label="电影名称：">
                {{ showSliceArrangement.movie.movieName }}
            </el-form-item>
            <el-form-item label="影院：">
                {{ showSliceArrangement.cinema.cinemaName }}
            </el-form-item>
            <el-form-item label="放映厅：">
                {{ showSliceArrangement.screeningRoom.screeningRoomName }}
            </el-form-item>
            <el-form-item label="放映时长：">
                {{ utils.timeStampToMinutes(showSliceArrangement.sliceArrangementEndTime-showSliceArrangement.sliceArrangementStartTime) }} 分钟
            </el-form-item>
            <el-form-item label="放映时间：">
                {{ utils.formatTimestampToYYYMMDDHHMMSS(showSliceArrangement.sliceArrangementStartTime) }} - {{ utils.formatTimestampToYYYMMDDHHMMSS(showSliceArrangement.sliceArrangementEndTime) }}
            </el-form-item>
            <el-form-item label="票价：">
               ￥{{ showSliceArrangement.votePrice }}
            </el-form-item>
            <el-form-item label="座位详情：">
                <div class="layoutCss">
                    <SliceArrangementSeatLayout :layout="showSliceArrangement.layout"
                    :is-cinema-management="true"></SliceArrangementSeatLayout>
                </div>
            </el-form-item>
        </el-form>
    </el-dialog>

    <el-dialog title="订单列表" :visible.sync="orderListDialogVisible" width="80%">
        <el-table v-if="orderDate" :data="orderDate.list" style="min-height: 50vh;">
            <el-table-column label="订单号" prop="orderId"></el-table-column>
            <el-table-column label="用户" prop="user.userName"></el-table-column>
            <el-table-column label="总票价">
                <template slot-scope="scope">
                    ￥{{ scope.row.voteAllPrice }}
                </template>
            </el-table-column>
            <el-table-column label="座位">
                <template slot-scope="scope">
                    <TruncatedText :lines="1">
                        <span v-for="(seat,index) in JSON.parse(scope.row.seats)" :key="index">
                            {{  seat.x }}排{{ seat.y }}座
                        </span>
                    </TruncatedText>
                </template>
            </el-table-column>
            <el-table-column label="购票状态">
                <template slot-scope="scope">
                    <span v-if="scope.row.votePayState==0">
                        未支付
                    </span>
                    <span v-if="scope.row.votePayState==1">
                        已支付未使用
                    </span>
                    <span v-if="scope.row.votePayState==2">
                        已支付已使用
                    </span>
                    <span v-if="scope.row.votePayState==3">
                        支付超时
                    </span>
                    <span v-if="scope.row.votePayState==4">
                        已取消支付
                    </span>
                </template>
            </el-table-column>
            <el-table-column label="订单创建时间">
                <template slot-scope="scope">
                    <TruncatedText :lines="1">
                        {{ utils.formatTimestampToYYYMMDDHHMMSS(scope.row.createTime) }}
                    </TruncatedText>
                </template>
            </el-table-column>
        </el-table>
        <el-row>
            <el-col :span="24" style="padding:20px;display: flex;justify-content: center;">
                <el-pagination
                background
                layout="prev, pager, next"
                @current-change="selectOrderBySliceArrangementId"
                :page-size="orderSize"
                :total="orderDate.total">
                </el-pagination>
            </el-col>
        </el-row>
    </el-dialog>
  </div>
</template>
qq
<script>
import { myApi } from '@/axios/my';
import { utils } from '@/utils/globalUtils';
import TruncatedText from '@/components/global/TruncatedText.vue';
import SliceArrangementSeatLayout from '@/components/global/SliceArrangementSeatLayout.vue';
export default {
    components:{
        TruncatedText,
        SliceArrangementSeatLayout
    },
    data(){
        return{
            sliceArrangements:[],
            page:1,
            size:10,
            total:0,
            cinemaId:null,
            movies:[],
            movieId:null,
            screeningRoomId:null,
            screeningRooms:[],
            startTIme:null,
            entTime:null,
            utils,
            sliceArrangementDialogVisible:false,
            showSliceArrangement:{},
            orderDate:{
                total:0
            },
            orderListDialogVisible:false,
            orderPage:1,
            orderSize:10,
            showsliceArrangementId:null
        }
    },
    methods:{
        selectSliceArrangementBySliceArrangement(page=1){
            if(page){
                this.page = page;
            }
            myApi.selectSliceArrangementBySliceArrangement({
                cinemaId:this.cinemaId,
                movieId:this.movieId,
                screeningRoomId:this.screeningRoomId,
                startTime:this.startTIme,
                entTime:this.entTime,
                page:this.page,
                size:this.size
            }).then(res=>{
                if(res.data.code==200){
                    this.$message.success(res.data.message)
                    this.total=res.data.data.total
                    this.sliceArrangements=res.data.data.list
                    for(let sliceArrangement of this.sliceArrangements){
                        sliceArrangement.layout=JSON.parse(sliceArrangement.layout)
                    }
                }else{
                    this.$message.error(res.data.message)
                }
            }).catch(e=>{
                console.log(e)
            })
        },
        handleEdit(row){
            this.showSliceArrangement=row
            this.sliceArrangementDialogVisible=true
        },
        handleDelete(row){

        },
        getOrderList(row){
            this.orderListDialogVisible=true
            this.showsliceArrangementId=row.sliceArrangementId
            this.selectOrderBySliceArrangementId()
        },
        selectOrderBySliceArrangementId(page=1){
            this.orderPage=page
            myApi.selectOrderBySliceArrangementId(this.showsliceArrangementId,this.orderPage,this.orderSize).then(res=>{
                if(res.data.code==200){
                    this.orderDate=res.data.data
                }else{
                    this.$message.error(res.data.message)
                }
            }).catch(e=>{
                console.log(e)
            })
        }
    },
    mounted(){
        myApi.cinemaSelectSimpleMovieList().then(res=>{
            if(res.data.code==200){
                this.movies=res.data.data
            }
        })
        myApi.selectSimpleScreeningRoom().then(res=>{
            if(res.data.code==200){
                this.screeningRooms=res.data.data
            }
        })
        let cinemaId=localStorage.getItem('cinemaId')
        if(cinemaId){
            this.cinemaId=cinemaId
        }else{
            this.$message.error('请先选择影院')
        }
        this.selectSliceArrangementBySliceArrangement()
    }
}
</script>

<style scoped>
.layoutCss div{
 line-height: normal;
}
</style>
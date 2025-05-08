<template>
  <div>
    <el-row style="padding: 20px;">
        <el-card shadow="hover">
            <div slot="header" class="clearfix">
                <span>{{ order.sliceArrangement.movie.movieName }}</span>
                <span style="float: right; padding: 3px 0" type="text">
                    票价：
                    <span style="color:#ff7b32;font-size: .8rem;">
                        ￥{{ order.voteAllPrice }}
                    </span>
                </span>
            </div>
            <div>
                <TruncatedText :lines="1" class="text-center">
                    <span style="font-size: .9rem;color:#c9c9c9;">影院：</span>
                    {{ order.sliceArrangement.cinema.cinemaName }}
                    <TruncatedText :lines="1" :customStyle="{float: 'right'}" v-if="!utils.getsmall()">
                        <span style="font-size: .9rem;color:#c9c9c9;">场次：</span>
                        {{ utils.formatTimestampToYYYMMDDHHMMSS(order.sliceArrangement.sliceArrangementStartTime) }}
                        - {{ utils.formatTimestampToYYYMMDDHHMMSS(order.sliceArrangement.sliceArrangementEndTime) }}
                    </TruncatedText>
                </TruncatedText>
                <TruncatedText :lines="1" class="text-center" v-if="utils.getsmall()">
                        <span style="font-size: .9rem;color:#c9c9c9;">场次：</span>
                        {{ utils.formatTimestampToYYYMMDDHHMMSS(order.sliceArrangement.sliceArrangementStartTime) }}
                        - {{ utils.formatTimestampToYYYMMDDHHMMSS(order.sliceArrangement.sliceArrangementEndTime) }}
                    </TruncatedText>
                <TruncatedText :lines="1" class="text-center">
                    <span style="font-size: .9rem;color:#c9c9c9;">影厅：</span>
                    {{ order.sliceArrangement.screeningRoom.screeningRoomName }}
                    <TruncatedText :lines="1" :customStyle="{float: 'right'}"  v-if="!utils.getsmall()">
                        <span style="font-size: .9rem;color:#c9c9c9;">订单状态：</span>
                        <span v-if="order.votePayState==0">未支付
                        </span>
                        <span v-if="order.votePayState==1">已支付未使用</span>
                        <span v-if="order.votePayState==2">已使用</span>
                        <span v-if="order.votePayState==3">支付超时</span>
                        <span v-if="order.votePayState==4">已取消</span>
                    </TruncatedText>
                </TruncatedText>
                <TruncatedText :lines="1" class="text-center" v-if="utils.getsmall()">
                        <span style="font-size: .9rem;color:#c9c9c9;">订单状态：</span>
                        <span v-if="order.votePayState==0">未支付
                        </span>
                        <span v-if="order.votePayState==1">已支付未使用</span>
                        <span v-if="order.votePayState==2">已使用</span>
                        <span v-if="order.votePayState==3">支付超时</span>
                        <span v-if="order.votePayState==4">已取消</span>
                    </TruncatedText>
                    <el-statistic v-if="order.votePayState==0&&!utils.getsmall()" :value="new Date(order.payTimeOut)" time-indices :value-style="{color:'red'}">
                        <template slot="prefix">
                            剩余支付时间
                        </template>
                    </el-statistic>
                <TruncatedText :lines="1" class="text-center">
                    <span style="font-size: .9rem;color:#c9c9c9;">座位：</span>
                    <span v-for="(seat,index) in JSON.parse(order.seats)" :key="index">
                        {{ seat.x }}列{{ seat.y }}座
                    </span>
                    <span  v-if="!utils.getsmall()">
                        <el-button type="success" class="float-right" v-if="order.votePayState==0" @click="$router.push({
                        name:'CustomerPayOrderPage',
                        params:{
                            orderId:order.orderId
                        }
                    })">去支付</el-button>
                    <el-button type="danger" class="float-right" v-if="order.votePayState==0" @click="updateOrderStatus">取消支付</el-button>
                    <el-button type="primary" class="float-right" v-if="order.votePayState==1" @click="$router.push({
                        name:'CustomerVerifyVotePage',
                        params:{
                            orderId:order.orderId
                        }
                    })">查看订单</el-button>
                    </span>
                </TruncatedText>
                <TruncatedText :lines="1" class="text-center" v-if="utils.getsmall()">
                    <el-statistic v-if="order.votePayState==0" :value="new Date(order.payTimeOut)" time-indices :value-style="{color:'red'}">
                    <template slot="prefix">
                        剩余支付时间
                    </template>
                </el-statistic>
                </TruncatedText>
                <TruncatedText  v-if="utils.getsmall()">
                    <el-button type="success" class="float-right" v-if="order.votePayState==0" @click="$router.push({
                    name:'CustomerPayOrderPage',
                    params:{
                        orderId:order.orderId
                    }
                })">去支付</el-button>
                <el-button type="danger" class="float-right" v-if="order.votePayState==0" @click="updateOrderStatus">取消支付</el-button>
                <el-button type="primary" class="float-right" v-if="order.votePayState==1" @click="$router.push({
                    name:'CustomerVerifyVotePage',
                    params:{
                        orderId:order.orderId
                    }
                })">查看订单</el-button>
                </TruncatedText>
            </div>
        </el-card>
    </el-row>
  </div>
</template>

<script>
import { utils } from '@/utils/globalUtils';
import { myApi } from '@/axios/my';
import TruncatedText from '../global/TruncatedText.vue';
export default {
    components:{
        TruncatedText
    },
    props:{
        order:{
            type:Object,
            required:false,
            default:()=>{
                return {
                    orderId:null,
                    sliceArrangement:{
                        movie:{
                            movieName:'defaultMovieName',
                        },
                        cinema:{
                            cinemaName:'defaultCinemaName'
                        },
                        screeningRoom:{
                            screeningRoomName:'defaultScreeningRoomName'
                        },
                        sliceArrangementStartTime:0, // 时间戳
                        sliceArrangementEndTime:0, // 时间戳
                    },
                    seats:'[]', // json 字符串数组
                    voteAllPrice:29.66,
                    votePayState: 1 ,
                    payTimeOut: 0
                }
            }
        }
    },
    data(){
        return{
            utils
        }
    },
    mounted(){
        
    },
    methods:{
        updateOrderStatus(){
            myApi.updateOrderStatus(this.order.orderId,4).then(res=>{
                if(res.data.code==200){
                    this.$message.success('订单取消成功');
                    this.order.votePayState = 4;
                }else{
                    this.$message.error(res.data.message);
                }
            }).catch(e=>{
                console.log(e)
            })
        }
    }
}
</script>

<style scoped>
.text-center{
    margin:9px 0;
}
.float-right{
    float: right;
    margin-left: 10px;
}
</style>
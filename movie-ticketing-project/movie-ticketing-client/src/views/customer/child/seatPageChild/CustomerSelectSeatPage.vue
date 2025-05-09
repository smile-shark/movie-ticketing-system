<template>
  <div>
    <el-row>
        <el-col :span="24" style="padding:20px">
            <SliceArrangementSeatLayout v-if="sliceArrangement" :layout="sliceArrangement.layout" :selected-seat="selectedSeat"></SliceArrangementSeatLayout>
        </el-col>
    </el-row>
    <el-row>
        <el-col :span="24">
            <SliceArrangementVoteCard v-if="movie" 
            :slice-arrangement="sliceArrangement"
            :movie="movie" 
            :selected-seat="selectedSeat"></SliceArrangementVoteCard>
        </el-col>
    </el-row>
    <el-row>
        <el-col :span="24" style="height: 4rem;line-height: 4rem;">
            <span style="color:#ff7b32;font-size: 1.5rem;padding:20px;">
                总价：{{ (selectedSeat.length * sliceArrangement?.votePrice).toFixed(2) }}元
            </span>
            <button class="pay-btn" @click="insertOrder">
                立即支付
            </button>
        </el-col>
    </el-row>
  </div>
</template>

<script>
import { myApi } from '@/axios/my';
import SliceArrangementSeatLayout from '@/components/global/SliceArrangementSeatLayout.vue';
import SliceArrangementVoteCard from '@/components/global/SliceArrangementVoteCard.vue';
export default {
    components:{
        SliceArrangementSeatLayout,
        SliceArrangementVoteCard
    },
    data(){
        return{
            sliceArrangement:null,
            sliceArrangementId:null,
            movie:null,
            selectedSeat:[],
            customerId:null
        }
    },
    methods:{
        customerSelectSliceArrangementBySliceArrangement(){
            myApi.customerSelectSliceArrangementBySliceArrangement({
                sliceArrangementId:this.sliceArrangementId,page:1,size:1
            }).then(res=>{
                if(res.data.code==200){
                    if(res.data.data.list.length==0){
                        this.$message.error('该场次暂无可选座位')
                        return
                    }
                    this.sliceArrangement=res.data.data.list[0]
                    this.sliceArrangement.layout=JSON.parse(this.sliceArrangement.layout)
                    myApi.selectMovieList({
                        movieId:this.sliceArrangement.movieId,page:1,size:1
                    }).then(res=>{
                        if(res.data.code==200){
                            if(res.data.data.list.length==0){
                                this.$message.error('该电影暂无票房数据')
                                return
                            }
                            this.movie=res.data.data.list[0]
                        }
                    })
                }else{
                    this.$message.error(res.data.message)
                }
            }).catch(e=>{
                console.log(e)
            })
        },
        insertOrder(){
            if(this.selectedSeat.length==0){
                this.$message.error('请选择座位')
                return
            }   
            if(this.customerId==null){
                this.$message.error('请先登录')
                return
            }
            if(!this.sliceArrangement.votePrice){
                this.$message.error('该场次暂无票价')
                return
            }
            if(!this.sliceArrangement.sliceArrangementId){
                this.$message.error('该场次暂无票价')
                return
            }

            myApi.insertOrder({
                userId:this.customerId,
                sliceArrangementId:this.sliceArrangement.sliceArrangementId,
                voteAllPrice:(this.sliceArrangement.votePrice * this.selectedSeat.length).toFixed(2),
                seats:JSON.stringify(this.selectedSeat),
                layout:JSON.stringify(this.sliceArrangement.layout)
            }).then(res=>{
                if(res.data.success){
                    this.$message.success(res.data.message)
                    // 转跳到支付页面
                    this.$router.push({
                        name:'CustomerPayOrderPage',
                        params:{
                            orderId:res.data.data
                        }
                    })
                }else{
                    this.$message.error(res.data.message)
                }
            }).catch(e=>{
                console.log(e)
            })
        }
    },
    mounted(){
        this.sliceArrangementId=this.$route.params.sliceArrangementId
        this.customerSelectSliceArrangementBySliceArrangement()
        let customerId=JSON.parse(localStorage.getItem('customerInfo')).userId
        this.customerId=customerId
    },
    watch:{
    }
}
</script>

<style scoped>
.pay-btn{
    background-color: #ff7b32;
    border:none;
    color:white;
    height: 4rem;
    width: 20rem;
    float:right;
    font-size: 1.5rem;
}
@media screen and (max-width: 768px) {
    .pay-btn{
        width: 100%;
    }
}
</style>
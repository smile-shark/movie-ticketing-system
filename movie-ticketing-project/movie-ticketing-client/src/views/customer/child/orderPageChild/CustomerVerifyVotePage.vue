<template>
  <div style="padding:20px">
    <el-row v-if="order">
      <el-col :span="24">
        <el-row>
          <el-col :span="utils.getsmall()?24:12">
            <div class="line-card">
              <TruncatedText :lines="1">
                影院：{{ order.sliceArrangement.cinema.cinemaName }}
              </TruncatedText>
            </div>
            <div class="line-card">
              <TruncatedText :lines="1">
                影厅： {{ order.sliceArrangement.screeningRoom.screeningRoomName }}
              </TruncatedText>
            </div>
            <div class="line-card">
              <TruncatedText :lines="1">
                电影： {{ order.sliceArrangement.movie.movieName }}
              </TruncatedText>
            </div>
            <div class="line-card">
                座位： <span v-for="(seat,index) in JSON.parse(order.seats)"
                :key="index">
                {{ seat.x }}排{{ seat.y }}座
              </span>
            </div>
            <div class="line-card">
              <TruncatedText :lines="1">
                票价： ￥{{ order.voteAllPrice }}
              </TruncatedText>
            </div>
            <div class="line-card">
              <TruncatedText :lines="1">
                售票时间： {{ utils.formatTimestampToYYYMMDDHHMMSS(order.createTime) }}
              </TruncatedText>
            </div>
            <div class="line-card">
              <TruncatedText :lines="1">
                放映日期： {{ utils.formatTimestampToYYYMMDDHHMMSS(order.sliceArrangement.sliceArrangementStartTime) }}
              </TruncatedText>
            </div>
          </el-col>
          <el-col :span="utils.getsmall()?24:12">
            <div class="line-card">
                订单号： {{ order.orderId }}
            </div>
            <div>
              <canvas ref="qrCanvas"></canvas>
              <!-- <img src='@/assets/verifyMovieVote.png'/> -->
            </div>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { myApi } from '@/axios/my';
import { utils } from '@/utils/globalUtils';
import QRcode from 'qrcode';
import TruncatedText from '@/components/global/TruncatedText.vue';
export default {
  components:{
    TruncatedText
  },
  data(){
    return{
      utils,
      orderId:null,
      order:null
    }
  },
  methods:{
    getnerateQRcode(){
      const canvas = this.$refs.qrCanvas;
      QRcode.toCanvas(canvas, this.orderId,(error)=>{
        if(error) console.log(error)
        else console.log('QR code generated successfully')
      })
    }
  },
  mounted(){
    this.orderId = this.$route.params.orderId;
    // 获取订单的信息
    myApi.selectOrderDetailByOrderId(this.orderId).then(res=>{
      if(res.data.code==200){
        this.order=res.data.data
        if(this.order.votePayState!==1){
          this.$message.error('该订单已失效')
          this.$router.push('/customer/home')
        }else{
          this.$nextTick(() => {
            this.getnerateQRcode();
          });
        }
      }else{
        this.$message.error(res.data.message)
      }
    }).catch(e=>{
      console.log(e)
    })
  }
}
</script>

<style scoped>
.line-card{
  padding:10px
}
</style>
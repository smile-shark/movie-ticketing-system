<template>
  <div class="qr-scanner-container">
    <el-card class="scanner-card">
      <div slot="header" class="clearfix">
        <span style="font-size: 18px; font-weight: bold;">二维码扫描</span>
      </div>
      
      <!-- 扫描区域 -->
      <div id="qr-scanner" class="scanner-view"></div>
      
      <!-- 操作按钮 -->
      <div class="action-buttons">
        <el-button 
          type="primary" 
          icon="el-icon-camera" 
          @click="startScanner"
          :disabled="isScanning"
        >
          开始扫描
        </el-button>
        <el-button 
          type="danger" 
          icon="el-icon-switch-button" 
          @click="stopScanner"
          :disabled="!isScanning"
        >
          停止扫描
        </el-button>
      </div>
      
      <!-- 扫描结果 -->
      <el-divider></el-divider>
      <div class="result-section">
        <div
          v-if="orderId">
          <el-alert 
          :title="'扫描结果: ' + orderId+ ' =====> '+returnMessage" 
          :type="orderState==200?'success':'error'" 
          :closable="false"
          show-icon
        ></el-alert>
        <el-row v-if="orderData">
          <el-col :span="24">
            <el-form label-width="80px">
              <el-form-item label="电影">
                <TruncatedText :lines="1">
                  {{ orderData.sliceArrangement.movie.movieName }}
                </TruncatedText>
              </el-form-item>
              <el-form-item label="影院">
                <TruncatedText :lines="1">
                  {{ orderData.sliceArrangement.cinema.cinemaName }}
                </TruncatedText>
              </el-form-item>
              <el-form-item label="影厅">
                <truncated-text :lines="1">
                  {{ orderData.sliceArrangement.screeningRoom.screeningRoomName }}
                </truncated-text>
              </el-form-item>
              <el-form-item label="场次">
                {{ utils.formatTimestampToYYYMMDDHHMMSS(orderData.sliceArrangement.sliceArrangementStartTime) }}
                -
                {{ utils.formatTimestampToYYYMMDDHHMMSS(orderData.sliceArrangement.sliceArrangementEndTime) }}
              </el-form-item>
              <el-form-item label="总价">
                <truncated-text :lines="1">
                  {{ orderData.voteAllPrice }}
                </truncated-text>
              </el-form-item>
              <el-form-item label="座位">
                <span v-for="(seat,index) in JSON.parse(orderData.seats)" :key="index">
                  {{ seat.x }}排{{ seat.y }}座
                </span>
              </el-form-item>
            </el-form>
          </el-col>
        </el-row>
        </div>
        <el-empty v-else description="暂无扫描结果"></el-empty>
      </div>
    </el-card>
  </div>
</template>

<script>
import { Html5Qrcode } from "html5-qrcode";
import { myApi } from "@/axios/my";
import { utils } from "@/utils/globalUtils";
import TruncatedText from "@/components/global/TruncatedText.vue";

export default {
  components:{
    TruncatedText
  },
  data() {
    return {
      utils,
        cinemaId:null,
        orderId: null,
        html5QrcodeScanner: null,
        isScanning: false,
        orderState:null,
        returnMessage:null,
        orderData:null
    };
  },
  methods: {
    startScanner() {
      this.html5QrcodeScanner = new Html5Qrcode("qr-scanner");
      const config = { fps: 10, qrbox: { width: 250, height: 250 } };

      this.html5QrcodeScanner.start(
        { facingMode: "environment" },
        config,
        (decodedText) => {
          this.orderId = decodedText;
          console.log("扫描结果:", decodedText);
          // 扫描完成后关闭扫描器
          this.cinemaManagementSelectOrderDetailByOrderId()
          this.updateOrderStateByOrderIdAndState(this.cinemaId,this.orderId,2,1)
          this.stopScanner();
        },
        (error) => {
          console.log("扫描中...")
        }
      ).then(() => {
        this.isScanning = true;
      }).catch((err) => {
        this.$message.error("扫描器初始化失败:" + err);
      });
    },
    stopScanner() {
      if (this.html5QrcodeScanner) {
        this.html5QrcodeScanner.stop().then(() => {
          console.log("扫描器已停止");
          this.isScanning = false;
        }).catch((err) => {
          console.error("停止失败:", err);
        });
      }
    },
    updateOrderStateByOrderIdAndState(cinemaId,orderId,state,paramState){
        myApi.updateOrderStateByOrderIdAndState(cinemaId,orderId,state,paramState).then(res=>{
            this.orderState=res.data.code
            if(res.data.code==200){
                this.$message.success('订单验证成功')
                this.returnMessage='订单验证成功'
            }else{
                this.$message.error(res.data.message)
                this.returnMessage=res.data.message
            }
        }).catch(e=>console.log(e))
    },
    cinemaManagementSelectOrderDetailByOrderId(){
      myApi.cinemaManagementSelectOrderDetailByOrderId(this.orderId).then(res=>{
        if(res.data.code==200){
            this.orderData=res.data.data
        }else{
          this.$message.error(res.data.message)
        }
      }).catch(e=>console.log(e)) 
    }
  },
  beforeDestroy() {
    this.stopScanner();
  },
  mounted(){
    this.cinemaId=localStorage.getItem('cinemaId')
  }
};
</script>

<style scoped>
.qr-scanner-container {
  max-width: 800px;
  margin: 20px auto;
  padding: 20px;
}

.scanner-card {
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.scanner-view {
  width: 100%;
  height: 300px;
  margin: 0 auto;
  background-color: #f5f7fa;
  border-radius: 4px;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
}

.action-buttons {
  margin-top: 20px;
  display: flex;
  justify-content: center;
  gap: 20px;
}

.action-buttons .el-button {
  width: 120px;
}

.result-section {
  margin-top: 20px;
  min-height: 60px;
}

@media (max-width: 768px) {
  .qr-scanner-container {
    padding: 10px;
  }
  
  .scanner-view {
    height: 250px;
  }
}
</style>
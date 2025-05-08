<template>
  <el-card style="background-color: #f7f7f7;margin-bottom: 20px;">
    <el-col :span="4" v-if="!utils.getsmall()">
      <el-image :src="cinema.cinemaBrand.cinemaBrandLogo"
      style="width: 150px;height: 150px;border-radius: 5px;" 
      fit="cover"/>
    </el-col>
    <el-col :span="utils.getsmall()? 24 : 15" class="cinema-info">
      <div style="padding-bottom: 20px;font-weight:bold;">{{ cinema.cinemaName }}</div>
      <div style="font-size: 14px;">
        <div class="rating">
          <span class="activity">活动</span>
          <span class="score">评分：{{ cinema.cinemaPoints }}分</span>
        </div>
        <div class="address">
          地址：{{ cinema.cinemaAddress }}
           <!-- <a href="#">【查看地图】</a> -->
        </div>
        <div class="phone">
          电话：{{ cinema.cinemaPhone }}
        </div>
      </div>
    </el-col>
    <el-col :span="utils.getsmall()? 24 : 5">
      <div class="price" style="display: flex;align-items: center;justify-content: center;height: 15vh;">
          <span style="font-weight: bold;">¥ {{ cinema.lowestPrice }}
            <span style="font-size:1rem;color:#a9a9a8;font-weight: normal;">起</span>
          </span>
          <el-button type="warning" @click="selectScreeningRoom">选座购票</el-button>
        </div>
    </el-col>
  </el-card>
</template>

<script>
import { utils } from '@/utils/globalUtils';
export default {
  data(){
    return{
      utils
    }
  },
  props: {
    cinema: {
      type: Object,
      required: true,
      default: () => ({
        cinemaId: 'cinemaId',
        cinemaName: '影院名称',
        cinemaAddress: '成华区建设南支路4号',
        cinemaPhone: '028-84773773',
        cinemaPoints: 9.2,
        lowestPrice: 29,
        cinemaBrand: {
          cinemaBrandLogo: 'https://img2.baidu.com/it/u=334115622,2222940209&fm=253&fmt=auto&app=138&f=JPEG?w=750&h=500' // 请替换为有效的图片URL
        }
      })
    },
    movieId: {
      type: String,
      required: true,
      default: 'movieId'
    }
  },
  methods:{
    selectScreeningRoom(){
      this.$router.push({
        name:'CustomerSelectScreeningRoomPage',
        query:{
          movieId:this.movieId,
          cinemaId:this.cinema.cinemaId
        }
      })
    }
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
@media screen and (max-width: 768px) {
  .score{
    margin-left:0;
  }
}
</style>


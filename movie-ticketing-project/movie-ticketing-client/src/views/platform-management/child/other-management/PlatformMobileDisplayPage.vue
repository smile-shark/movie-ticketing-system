<template>
  <div>
    <el-row>
      <el-col :span="24" style="display: flex; justify-content: left; align-items: center; flex-wrap: wrap;">
        <mobile-display-card 
          :search-mobile-displays="getMobleDisplay"
          v-for="(mobileDisplay, index) in mobileDisplays" 
          :key="index"
          :mobile-display="mobileDisplay"
          style="min-width: 300px; margin: 10px;"
        ></mobile-display-card>
        <el-card 
          shadow="hover" 
          style="min-width: 300px; min-height: 40vh; display: flex; justify-content: center; align-items: center; cursor: pointer; border: dashed 2px #409eff; margin: 10px;"
          @click.native="insertMobileDisplay"
        >
          <i class="el-icon-plus" @click.stop="insertMobileDisplay"></i>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import { myApi } from '@/axios/my';
import MobileDisplayCard from '@/components/global/MobileDisplayCard.vue';
export default {
  data(){
    return{
      mobileDisplays:[]
    }
  },
  components:{
      MobileDisplayCard
    },
  methods:{
    insertMobileDisplay(){
      this.mobileDisplays.push({
        movieId:null,
        mobileDisplayImage:'',
        mobileDisplayEndTime:'',
        isNew:true
      })
    },
    getMobleDisplay(){
      myApi.getMobleDisplay().then(res=>{
        if(res.data.code==200){
          this.mobileDisplays=res.data.data
        }else{
          this.$message.error(res.data.message)
        }
      })
    }
  },
  mounted(){
   this.getMobleDisplay()
  }
}
</script>

<style scoped>
</style>
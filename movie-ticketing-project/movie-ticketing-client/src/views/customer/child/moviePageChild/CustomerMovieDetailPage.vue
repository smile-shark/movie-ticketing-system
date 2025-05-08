<template>
  <div>
    <el-row v-if="movieData!=null">
      <el-col :span="24">
        <el-row>
          <el-col :span="24">
            <MovieCard :movie="movieData"></MovieCard>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24" style="padding:20px">
            <el-row>
              <el-col :span="24" style="border:solid #e4e4e4 1px;padding:10px">
                <el-form label-width="60px">
                  <el-form-item label="品牌：">
                    <el-radio-group v-model="selectedBrandId" size="small" fill="#ff7b32" @input="customerSelectLowPriceCinema(1)">
                      <el-radio-button v-for="(cinemaBrand,index) in cinemaBrands" :key="index"
                      :label="cinemaBrand.cinemaBrandId">
                      {{ cinemaBrand.cinemaBrandName }}
                    </el-radio-button>
                    </el-radio-group> 
                  </el-form-item>
                  <el-form-item label="区域：">
                    <div style=" overflow-x: auto;">
                      <el-radio-group v-model="selectedCountyId" size="small" fill="#ff7b32" @input="customerSelectLowPriceCinema(1)">
                        <el-radio-button v-for="(county,index) in counties" :key="index"
                        :label="county.countyId">
                        {{ county.countyName }}
                      </el-radio-button>
                      </el-radio-group> 
                    </div>
                  </el-form-item>
                </el-form>
              </el-col>
            </el-row>
            <el-row>
              <h3 style="color:#5a5a61">影院列表</h3>
              <el-col :span="24" style="padding:20px">
                <customer-buy-ticket-and-cinema-card v-for="(cinema,index) in cinemas" :key="index"
                :cinema="cinema"
                :movie-id="movieId"></customer-buy-ticket-and-cinema-card>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-pagination
                 @current-change="customerSelectLowPriceCinema"
                  background
                  layout="prev, pager, next"
                  :page-size="size"
                  :total="total">
                </el-pagination>
              </el-col>
            </el-row>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
    <el-row v-else>
      <el-col :span="24">
        <el-empty style="min-height: 80vh;">
          <el-button @click="$router.push('/customer/home')">返回首页</el-button>
        </el-empty>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { myApi } from '@/axios/my';
import MovieCard from '@/components/global/MovieCard.vue';
import CustomerBuyTicketAndCinemaCard from '@/components/customer/CustomerBuyTicketAndCinemaCard.vue';
export default {
  components:{
    MovieCard,
    CustomerBuyTicketAndCinemaCard
  },
  data(){
    return{
      movieData:null,
      selectedBrandId:null,
      cinemaBrands:[],
      customerInfo:null,
      selectedCountyId:null,
      counties:[],
      movieId:null,
      page:1,
      size:5,
      total:0,
      cinemas:[]
    }
  },
  mounted(){
    let customerInfo=JSON.parse(localStorage.getItem('customerInfo'))
    if(customerInfo){
      this.customerInfo=customerInfo
    }else{
      this.$message.error('请先登录')
      this.$router.push('')
      return
    }
    this.movieId=this.$route.params.movieId
    myApi.selectMovieList({movieId:this.movieId,size:1,page:1}).then(res=>{
      if(res.data.code==200){
        if(res.data.data.list.length>0){
          this.movieData=res.data.data.list[0];
        }
      }
    }).catch(e=>{
      console.log(e)
    }),
    myApi.customerSelectAllCinemaBrand().then(res=>{
      if(res.data.code==200){
        this.cinemaBrands.push({
          cinemaBrandId:null,
          cinemaBrandName:'全部'
        })
        this.cinemaBrands.push(...res.data.data)
      }
    })
    myApi.customerSelectCountyByMarketId(this.customerInfo.marketId).then(res=>{
      if(res.data.code==200){
        this.counties.push({
          countyId:null,
          countyName:'全部'
        })
        this.counties.push(...res.data.data)
      }
    })
    this.customerSelectLowPriceCinema()
  },
  methods:{
    customerSelectLowPriceCinema(page=1){
      if(page){
        this.page=page
      }
      myApi.customerSelectLowPriceCinema(this.selectedCountyId,this.selectedBrandId,this.movieId,this.page,this.size).then(res=>{
        if(res.data.code==200){
          this.cinemas=res.data.data.list
          this.total=res.data.data.total
          if(this.total==0){
            this.$message.warning('当前时间段没有影院可选')
          }
        }else{
          this.$message.error(res.data.message)
        }
      }).catch(e=>{
        console.log(e) 
      })
    }
  },
  watch:{

  }
}
</script>

<style>

</style>
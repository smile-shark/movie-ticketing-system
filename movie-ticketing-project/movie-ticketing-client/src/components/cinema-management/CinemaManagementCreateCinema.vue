<template>
  <div>
    <h3 slot="title">添加新的影院</h3>
    <el-row>
        <el-col :span="12">
            <el-form :model="cinema" label-width="100px">
                <el-form-item label="影院品牌">
                    <el-select v-model="cinema.cinemaBrandId" placeholder="请选择影院品牌" filterable>
                        <el-option v-for="(brand,index) in cinemaBrands" :key="index"
                        :label="brand.cinemaBrandName" :value="brand.cinemaBrandId">
                        <div style="display: flex;align-items: center;">
                            <el-image :src="brand.cinemaBrandLogo" style="width: 30px;"></el-image>
                        {{ brand.cinemaBrandName }}
                        </div>
                    </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="所在省">
                    <el-select v-model="cinema.economizeId" placeholder="请选择所在省" filterable 
                    @change="selectMarketByEconomizeId">
                        <el-option v-for="(economize,index) in economizes" :key="index"
                        :label="economize.economizeName" :value="economize.economizeId"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="所在市">
                    <el-select v-model="cinema.marketId" placeholder="请选择所在市" filterable
                    @change="selectCountyByMarketId">
                        <el-option v-for="(market,index) in markets" :key="index"
                        :label="market.marketName" :value="market.marketId"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="所在县区">
                    <el-select v-model="cinema.countyId" placeholder="请选择所在县区" filterable>
                        <el-option v-for="(county,index) in counties" :key="index"
                        :label="county.countyName" :value="county.countyId"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="影院名称">
                    <el-input v-model="cinema.cinemaName" placeholder="请输入影院名称"></el-input>
                </el-form-item>
                <el-form-item label="影院地址">
                    <el-input v-model="cinema.cinemaAddress" placeholder="请输入影院地址"></el-input>
                </el-form-item>
            </el-form>
        </el-col>
        <el-col :span="12">
            <el-form :model="cinema" label-width="100px">
                <el-form-item label="Logo">
                    <el-image :src="cinemaBrands.filter(item=>item.cinemaBrandId==cinema.cinemaBrandId)[0] ?
                    cinemaBrands.filter(item=>item.cinemaBrandId==cinema.cinemaBrandId)[0].cinemaBrandLogo : ''" style="width: 30px;height: 30px;">
                        <div slot="error" class="image-slot" style="background-color: #c8ccd4;display: flex;align-items: center;justify-content: center ;height: 30px;width: 30px;color: #fff;font-size: 12px;position: absolute;top: 0;right: 0;bottom: 0;left: 0;opacity: 0.8;" >
                            <i class="el-icon-picture-outline"></i>
                        </div></el-image>
                </el-form-item>
                <el-form-item label="联系电话">
                    <el-input v-model="cinema.cinemaPhone" placeholder="请输入联系电话"></el-input>
                </el-form-item>
                <el-form-item label="官方邮箱">
                    <el-input v-model="cinema.cinemaEmail" placeholder="请输入官方邮箱"></el-input>
                </el-form-item>
                <el-form-item label="官方网站">
                    <el-input v-model="cinema.cinemaUrl" placeholder="请输入官方网站"></el-input>
                </el-form-item>
                <el-form-item label="营业开始时间">
                    <el-time-select
                    v-model="cinema.cinemaStartTime"
                    :picker-options="{
                        start: '00:00',
                        step: '00:30',
                        end: '24:00'
                    }"
                    placeholder="请选择营业时间开始">
                    </el-time-select>
                </el-form-item>
                <el-form-item label="营业结束时间">
                    <el-time-select
                    v-model="cinema.cinemaEndTime"
                    :picker-options="{
                        start: '00:00',
                        step: '00:30',
                        end: '24:00'
                    }"
                    placeholder="请选择营业时间结束">
                    </el-time-select>
                </el-form-item>
            </el-form>
        </el-col>
        <el-col :span="24" style="display: flex;justify-content: center;">
            <el-button type="primary" @click="addCinema">添加影院</el-button>
        </el-col>
    </el-row>
  </div>
</template>

<script>
import { myApi } from '@/axios/my';
import { utils } from '@/utils/globalUtils';
export default {
    data(){
        return{
            cinema:{
                cinemaBrandId:'', // 影院品牌需要选择
                cinemaManagementId:'', // 管理员的id
                economizeId:'', // 所在省的id
                marketId:'',// 市的id
                countyId:'', // 县区的id 选择
                cinemaName:'', // 影院的名称
                cinemaAddress:'', // 影院的地址
                cinemaPhone:'', // 联系电话
                cinemaEmail:'', // 官方邮箱
                cinemaUrl:'',// 官方网站
                cinemaStartTime:'', // 营业时间开始
                cinemaEndTime:'', // 营业时间结束
            },
            cinemaBrands:[], // 影院品牌列表
            economizes:[], 
            markets:[],
            counties:[]
        }
    },
    methods:{
        selectMarketByEconomizeId(){
            this.cinema.marketId=''
            this.cinema.countyId=''
            myApi.selectMarketByEconomizeId(this.cinema.economizeId).then(res=>{
                if(res.data.code==200){
                    this.markets=res.data.data
                }else{
                    this.$message.error(res.data.message)
                }
            })
        },
        selectCountyByMarketId(){
            this.cinema.countyId=''
            myApi.selectCountyByMarketId(this.cinema.marketId).then(res=>{
                if(res.data.code==200){
                    this.counties=res.data.data
                }else{
                    this.$message.error(res.data.message)
                }
            })
        },
        addCinema(){
            if(this.cinema.cinemaBrandId==''){
                this.$message.error('请选择影院品牌')
                return
            }
            if(this.cinema.countyId==''){
                this.$message.error('请选择所在县区')
                return
            }
            if(this.cinema.cinemaName==''){
                this.$message.error('请输入影院名称')
                return
            }
            if(this.cinema.cinemaAddress==''){
                this.$message.error('请输入影院地址')
                return
            }
            if(this.cinema.cinemaPhone==''){
                this.$message.error('请输入联系电话')
                return
            }
            if(this.cinema.cinemaEmail==''){
                this.$message.error('请输入官方邮箱')
                return
            }
            if(this.cinema.cinemaUrl==''){
                this.$message.error('请输入官方网站')
                return
            }
            if(this.cinema.cinemaStartTime==''){
                this.$message.error('请选择营业时间开始')
                return
            }
            if(this.cinema.cinemaEndTime==''){
                this.$message.error('请选择营业时间结束')
                return
            }
            if(!localStorage.getItem('cinemaManagementInfo')){
                this.$message.error('请先登录')
                this.$router.push('/cinema/admin')
                return
            }
            this.cinema.cinemaManagementId=JSON.parse(localStorage.getItem('cinemaManagementInfo')).cinemaManagementId
            myApi.insertCinema(this.cinema).then(res=>{
                if(res.data.code==200){
                    this.$message.success(res.data.message)
                    window.location.reload()
                }else{
                    this.$message.error(res.data.message)
                }
            }).catch(e=>{
                console.log(e)
            })
        }
    },
    mounted(){
        myApi.selectAllCinemaBrand().then(res=>{
            if(res.data.code==200){
                this.cinemaBrands=res.data.data
            }else{
                this.$message.error(res.data.message)
            }
        }).catch(e=>{
            console.log(e)
        })
        myApi.selectAllEconomize().then(res=>{
            if(res.data.code==200){
                this.economizes=res.data.data
            }else{
                this.$message.error(res.data.message)
            }
        }).catch(e=>{
            console.log(e)
        })
    }
}
</script>

<style>

</style>
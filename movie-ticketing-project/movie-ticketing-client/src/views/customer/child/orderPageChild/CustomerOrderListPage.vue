<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-tabs v-model="filterText" stretch @tab-click="selectOrderDetailListByOrder" >
          <el-tab-pane label="全部" name="null"></el-tab-pane>
          <el-tab-pane label="未支付" name="0"></el-tab-pane>
          <el-tab-pane label="未使用" name="1"></el-tab-pane>
          <el-tab-pane label="已使用" name="2"></el-tab-pane>
          <el-tab-pane label="支付超时" name="3"></el-tab-pane>
          <el-tab-pane label="已取消支付" name="4"></el-tab-pane>
        </el-tabs>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24">
        <AnimateView>
          <CustomerOrderListCard v-for="(order,index) in orderList" :key="index" :data-index="index"
          :order="order"></CustomerOrderListCard>
        </AnimateView>
      </el-col>
      <el-col :span="24" v-if="orderList.length==0">
        <el-empty style="min-height: 80vh;">
          <el-button @click="$router.push('/customer/home')">去购票</el-button>
        </el-empty>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24" style="padding: 20px;">
        <el-pagination
        hide-on-single-page
        background
        layout="prev, pager, next"
        :page-size="size"
        @current-change="selectOrderDetailListByOrder"
        :total="total">
      </el-pagination>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { myApi } from '@/axios/my';
import CustomerOrderListCard from '@/components/customer/CustomerOrderListCard.vue';
import AnimateView from '@/components/global/AnimateView.vue';
export default {
  components:{
    CustomerOrderListCard,AnimateView
  },
  data(){
    return{
      filterText: "null",
      page: 1,
      size:5,
      total:0,
      orderList:[]
    }
  },
  methods:{
    selectOrderDetailListByOrder(tab={name: "null"},page=1){
      if(typeof tab == 'number'){
        page=tab
        tab={name: "null"}
      }
      typeof page == 'number' ? this.page=page : this.page=1
      let votePayState=tab.name == 'null' ? null :Number(tab.name)
      myApi.selectOrderDetailListByOrder({
        votePayState: votePayState,
        page: this.page,
        size: this.size
      }).then(res => {
        if(res.data.code==200){
          this.total=res.data.data.total
          this.orderList=res.data.data.list
        }
      }).catch(e=>{
        console.log(e)
      })
    }
  },
  mounted(){
    this.selectOrderDetailListByOrder()
  }
}
</script>

<style>

</style>
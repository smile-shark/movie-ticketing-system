<template>
  <div>
    <el-row>
      <el-col :span="4" class="title-card">
        <el-card shadow="hover">
          <el-statistic title="活跃用户数">
            <template slot="formatter">
              <DigitalAnimation :order-all-limit="activeCustomerTotal" :decimal-places="0">

              </DigitalAnimation>/<DigitalAnimation :order-all-limit="customerTotal" :decimal-places="0">

              </DigitalAnimation>
            </template>
          </el-statistic>
        </el-card>
      </el-col>
      <el-col :span="4" class="title-card">
        <el-card shadow="hover">
          <el-statistic
            group-separator=","
            title="在营影院数量"
          >
            <template slot="formatter">
              <DigitalAnimation :order-all-limit="cinemaTotal" :decimal-places="0"></DigitalAnimation>
            </template>
          </el-statistic>
        </el-card>
      </el-col>
      <el-col :span="4" class="title-card">
        <el-card shadow="hover">
          <el-statistic
            group-separator=","
            title="上线电影数量"
          >
            <template slot="formatter">
              <DigitalAnimation :order-all-limit="movieTotal" :decimal-places="0"></DigitalAnimation>
            </template>
          </el-statistic>
        </el-card>
      </el-col>
      <el-col :span="4" class="title-card">
        <el-card shadow="hover">
          <el-statistic
            group-separator=","
            title="一个月内排片总数量"
          >
            <template slot="formatter">
              <DigitalAnimation :order-all-limit="aMonthSliceArrangementTotal" :decimal-places="0"></DigitalAnimation>
            </template>
          </el-statistic>
        </el-card>
      </el-col>
      <el-col :span="4" class="title-card">
        <el-card shadow="hover">
          <el-statistic
            group-separator=","
            :precision="2"
            title="订单总额"
          >
            <template slot="prefix"> ￥ </template>
            <template slot="formatter">
              <DigitalAnimation :order-all-limit="orderAllLimit"></DigitalAnimation>
            </template>
          </el-statistic>
        </el-card>
      </el-col>
      <el-col :span="4" class="title-card">
        <el-card shadow="hover">
          <el-statistic
            group-separator=","
            :precision="2"
            title="有效营业额"
          >
            <template slot="prefix"> ￥ </template>
            <template slot="formatter">
              <DigitalAnimation :order-all-limit="effectiveOrderLimit"></DigitalAnimation>
            </template>
          </el-statistic>
        </el-card>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <LineGraPh :data="turnoverData"></LineGraPh>
      </el-col>
      <el-col :span="6">
        <div style="padding: 10px">
          <el-card shadow="hover">
            <el-statistic
              group-separator=","
              title="申请数量"
            >
            <template slot="formatter">
              <DigitalAnimation :order-all-limit="applyTotal" :decimal-places="0"></DigitalAnimation>
            </template>
            </el-statistic>
          </el-card>
        </div>
        <PieChart :data="movieRevenueData"></PieChart>
      </el-col>
      <el-col :span="6">
        <div style="padding: 10px">
          <el-card shadow="hover">
            <el-statistic
              group-separator=","
              :precision="2"
              title="订单支付率"
            >
              <template slot="suffix">%</template>
              
            <template slot="formatter">
              <DigitalAnimation :order-all-limit="(payedOrderTotal / OrderTotal) * 100"></DigitalAnimation>
            </template>
            </el-statistic>
          </el-card>
        </div>
        <PieChart :data="cinemaRevenueData"></PieChart>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { myApi } from "@/axios/my";
import DigitalAnimation from "@/components/global/DigitalAnimation.vue";
import LineGraPh from "@/components/global/LineGraPh.vue";
import PieChart from "@/components/global/PieChart.vue";
import { utils } from "@/utils/globalUtils";
export default {
  components: {
    LineGraPh,
    PieChart,
    DigitalAnimation
  },
  data() {
    return {
      customerTotal: 0,
      activeCustomerTotal: 0,
      cinemaTotal: 0,
      movieTotal: 0,
      aMonthSliceArrangementTotal: 0,
      orderAllLimit: 0,
      effectiveOrderLimit: 0,
      payedOrderTotal: 1,
      OrderTotal: 1,
      applyTotal: 0,
      turnoverData: {
        title: { text: "营业额变化趋势" },
        tooltip: { trigger: "axis" },
        xAxis: {
          type: "category",
          data: [],
        },
        yAxis: { type: "value" },
        series: [
          {
            name: "有效额度",
            data: [],
            type: "line",
            smooth: true,
            symbol: "circle",
            symbolSize: 8,
          },
          {
            name: "总额度",
            data: [],
            type: "line",
            smooth: true,
            symbol: "circle",
            symbolSize: 8,
          },
        ],
      },
      movieRevenueData: {
        title: { text: "各电影营收占比" },
        tooltip: { trigger: "item" },
        series: [
          {
            name: "营收占比",
            type: "pie",
            radius: "60%",
            data: [
              { value: 1048, name: "A" },
              { value: 735, name: "B" },
              { value: 580, name: "C" },
            ],
          },
        ],
      },
      cinemaRevenueData: {
        title: { text: "各影院营收占比" },
        tooltip: { trigger: "item" },
        series: [
          {
            name: "营收占比",
            type: "pie",
            radius: "60%",
            data: [
              { value: 1048, name: "A" },
              { value: 735, name: "B" },
              { value: 580, name: "C" },
            ],
          },
        ],
      },
    };
  },
  mounted() {
    myApi
      .activeUserTotal()
      .then((res) => {
        if (res.data.code == 200) {
          this.activeCustomerTotal = res.data.data;
        } else {
          this.$message.error(res.data.message);
        }
      })
      .catch((e) => console.log(e));
    myApi
      .userTotal()
      .then((res) => {
        if (res.data.code == 200) {
          this.customerTotal = res.data.data;
        } else {
          this.$message.error(res.data.message);
        }
      })
      .catch((e) => console.log(e));
    myApi
      .cinemaTotal()
      .then((res) => {
        if (res.data.code == 200) {
          this.cinemaTotal = res.data.data;
        } else {
          this.$message.error(res.data.message);
        }
      })
      .catch();
    myApi
      .aMonthHasSliceArrangementTotal()
      .then((res) => {
        if (res.data.code == 200) {
          this.aMonthSliceArrangementTotal = res.data.data;
        } else {
          this.$message.error(res.data.message);
        }
      })
      .catch((e) => console.log(e));
    myApi
      .movieTotal()
      .then((res) => {
        if (res.data.code == 200) {
          this.movieTotal = res.data.data;
        } else {
          this.$message.error(res.data.message);
        }
      })
      .catch((e) => console.log(e));
    myApi
      .orderTotalAmount()
      .then((res) => {
        if (res.data.code == 200) {
          this.orderAllLimit = res.data.data;
        } else {
          this.$message.error(res.data.message);
        }
      })
      .catch((e) => console.log(e));
    myApi
      .payedOrderTotalAmount()
      .then((res) => {
        if (res.data.code == 200) {
          this.effectiveOrderLimit = res.data.data;
        } else {
          this.$message.error(res.data.message);
        }
      })
      .catch((e) => console.log(e));
    myApi
      .orderTotal()
      .then((res) => {
        if (res.data.code == 200) {
          this.OrderTotal = res.data.data;
        } else {
          this.$message.error(res.data.message);
        }
      })
      .catch((e) => console.log(e));
    myApi
      .payedOrderTotal()
      .then((res) => {
        if (res.data.code == 200) {
          this.payedOrderTotal = res.data.data;
        } else {
          this.$message.error(res.data.message);
        }
      })
      .catch((e) => console.log(e));
    myApi
      .applyForNewMovieTotal()
      .then((res) => {
        if (res.data.code == 200) {
          this.applyTotal = res.data.data;
        } else {
          this.$message.error(res.data.message);
        }
      })
      .catch((e) => console.log(e));
    // 1. 初始化数据容器
    const xAxisData = [];
    const seriesData = []; // 第一条线数据
    const seriesData2 = []; // 第二条线数据

    // 2. 生成请求数组
    const requests = [];
    const requests2 = [];
    for (let i = 10; i >= 0; i--) {
      xAxisData.push(i === 0 ? "本周" : `${i}周前`);

      requests.push(
        myApi.selectOrderVoteAllPriceByTimeNotCinema(
          utils.getTimeForAnyWeek(i + 1),
          utils.getTimeForAnyWeek(i)
        )
      );
      requests2.push(
        myApi.selectOrderVotePriceByTimeNotCinema(
          utils.getTimeForAnyWeek(i + 1),
          utils.getTimeForAnyWeek(i)
        )
      );
    }

    // 3. 并行执行所有请求
    Promise.all([Promise.all(requests), Promise.all(requests2)])
      .then(([results1, results2]) => {
        // 处理第一条线数据
        results1.forEach((res, index) => {
          seriesData[index] = res.data.code === 200 ? res.data.data || 0 : 0;
          if (res.data.code !== 200) this.$message.error(res.data.message);
        });

        // 处理第二条线数据
        results2.forEach((res, index) => {
          seriesData2[index] = res.data.code === 200 ? res.data.data || 0 : 0;
          if (res.data.code !== 200) this.$message.error(res.data.message);
        });

        // 4. 一次性更新图表（关键！）
        this.$set(this.turnoverData, "xAxis", {
          ...this.turnoverData.xAxis,
          data: xAxisData,
        });

        this.$set(this.turnoverData, "series", [
          {
            ...this.turnoverData.series[0],
            name: "有效额度", // 添加图例名称
            data: seriesData,
            itemStyle: { color: "#FF6384" }, // 第一条线颜色
            lineStyle: { color: "#FF6384", width: 2 },
          },
          {
            ...this.turnoverData.series[0],
            name: "总额度", // 添加图例名称
            data: seriesData2,
            type: "line",
            itemStyle: { color: "#36A2EB" }, // 第二条线颜色
            lineStyle: { color: "#36A2EB", width: 2 },
          },
        ]);
      })
      .catch((e) => {
        console.error(e);
        this.$message.error("数据加载失败");
      });

      this.movieRevenueData.series[0].data = [];
    myApi
      .selectChartValueByTimeAndMovieNotCinema(
        11,
        utils.getTimeForAnyWeek()
      )
      .then((res) => {
        if (res.data.code == 200) {
          this.movieRevenueData.series[0].data = res.data.data;
        } else {
          this.$message.error(res.data.message);
        }
      })
      .catch((e) => {
        console.log(e);
      });
    this.cinemaRevenueData.series[0].data = [];
    myApi
      .selectChartValueByTimeGroupCinema(
        11,
        utils.getTimeForAnyWeek()
      )
      .then((res) => {
        if (res.data.code == 200) {
          this.cinemaRevenueData.series[0].data = res.data.data;
        } else {
          this.$message.error(res.data.message);
        }
      })
      .catch((e) => {
        console.log(e);
      });
  },
};
</script>

<style scoped>
.title-card {
  padding: 10px;
}
</style>

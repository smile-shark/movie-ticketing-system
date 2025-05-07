<template>
  <div>
    <el-row>
      <el-col :span="6" style="padding: 10px">
        <el-card shadow="hover">
          <el-statistic
            group-separator=","
            title="近一周售票量"
          >
            <template slot="prefix">
              <span v-if="orderNum < 10"> 😔 </span>
              <span v-else> 😋 </span>
            </template>
            <template slot="formatter">
              <DigitalAnimation :order-all-limit="orderNum" :decimalPlaces="0"></DigitalAnimation>
            </template>
          </el-statistic>
        </el-card>
      </el-col>
      <el-col :span="6" style="padding: 10px">
        <el-card shadow="hover">
          <el-statistic
            group-separator=","
            title="近一周排片量🤔"
          >
            <template slot="formatter">
              <DigitalAnimation :order-all-limit="sliceArrangementNum" :decimalPlaces="0"></DigitalAnimation>
            </template>
          </el-statistic>
        </el-card>
      </el-col>
      <el-col :span="6" style="padding: 10px">
        <el-card shadow="hover">
          <el-statistic
            group-separator=","
            title="近一周营业额"
          >
            <template slot="prefix">
              <i class="el-icon-s-flag" style="color: red"></i>
              ￥
            </template>
            <template slot="formatter">
              <DigitalAnimation :order-all-limit="orderAllPrice"></DigitalAnimation>
            </template>
            <template slot="suffix">
              <i class="el-icon-s-flag" style="color: blue"></i>
            </template>
          </el-statistic>
        </el-card>
      </el-col>
      <el-col :span="6" style="padding: 10px">
        <el-card shadow="hover">
          <el-statistic
            group-separator=","
            title="总营业额🙂"
          >
            <template slot="prefix">
              ￥
            </template>
            <template slot="formatter">
              <DigitalAnimation :order-all-limit="turnover"></DigitalAnimation>
            </template>
          </el-statistic>
        </el-card>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <LineGraPh :data="turnoverData">
          <el-input-number
            v-model="turnoverTime"
            controls-position="right"
            @change="selectNewTurnoverData"
            :min="5"
            :max="100"
            style="width: 7rem"
          >
          </el-input-number>
          <span style="padding: 0.5rem; color: #656d91">周内</span>
        </LineGraPh>
      </el-col>
      <el-col :span="6">
        <PieChart :data="movieRevenueData"></PieChart>
      </el-col>
      <el-col :span="6">
        <PieChart :data="screeningRoomRevenueData"></PieChart>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { myApi } from "@/axios/my";
import { utils } from "@/utils/globalUtils";
import DigitalAnimation from "@/components/global/DigitalAnimation.vue";
import Charts from "@/components/global/Charts.vue";
import LineGraPh from "@/components/global/LineGraPh.vue";
import PieChart from "@/components/global/PieChart.vue";
export default {
  components: {
    Charts,
    LineGraPh,
    PieChart,
    DigitalAnimation
  },
  data() {
    return {
      orderNum: 0,
      sliceArrangementNum: 0,
      orderAllPrice: 0,
      turnover: 0,
      utils,
      cinemaId: null,
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
            name: "营业额（元）",
            data: [],
            type: "line",
            smooth: true,
          },
        ],
      },
      turnoverTime: 10,
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
      screeningRoomRevenueData: {
        title: { text: "各影厅营收占比" },
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
  methods: {
    selectNewTurnoverData() {
      // 1. 先清空并初始化数据容器（避免异步导致的顺序问题）
      const xAxisData = [];
      const seriesData = [];

      // 2. 生成请求数组
      const requests = [];
      for (let i = this.turnoverTime; i >= 0; i--) {
        xAxisData.push(i === 0 ? "本周" : `${i}周前`);

        requests.push(
          myApi.selectOrderVoteAllPriceByTime(
            this.cinemaId,
            utils.getTimeForAnyWeek(i + 1),
            utils.getTimeForAnyWeek(i)
          )
        );
      }

      // 3. 并行执行所有请求
      Promise.all(requests)
        .then((results) => {
          results.forEach((res, index) => {
            if (res.data.code === 200) {
              seriesData[index] = res.data.data || 0; // 确保数据位置正确
            } else {
              this.$message.error(res.data.message);
              seriesData[index] = 0; // 错误时填充默认值
            }
          });

          // 4. 一次性更新图表数据（重要！）
          this.$set(this.turnoverData, "xAxis", {
            ...this.turnoverData.xAxis,
            data: xAxisData,
          });

          this.$set(this.turnoverData, "series", [
            {
              ...this.turnoverData.series[0],
              data: seriesData,
            },
          ]);

          // 5. 如果使用原生 ECharts 实例，可能需要调用 myChart.setOption()
        })
        .catch((e) => {
          console.error(e);
          this.$message.error("数据加载失败");
        });
      this.selectChartValueByTimeAndMovie()
      this.selectChartValueByTimeAndScreeningRoom()
    },
    selectChartValueByTimeAndMovie() {
      this.movieRevenueData.series[0].data = [];
      myApi
        .selectChartValueByTimeAndMovie(
          this.cinemaId,
          this.turnoverTime + 1,
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
    },
    selectChartValueByTimeAndScreeningRoom() {
      this.screeningRoomRevenueData.series[0].data = [];
      myApi
        .selectChartValueByTimeAndScreeningRoom(
          this.cinemaId,
          this.turnoverTime + 1,
          utils.getTimeForAnyWeek()
        )
        .then((res) => {
          if (res.data.code == 200) {
            this.screeningRoomRevenueData.series[0].data = res.data.data;
          } else {
            this.$message.error(res.data.message);
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },
    selectOrderNumByTime(startTime, endTime) {
      myApi
        .selectOrderNumByTime(this.cinemaId, startTime, endTime)
        .then((res) => {
          if (res.data.code == 200) {
            this.orderNum = res.data.data;
          } else {
            this.$message.error(res.data.message);
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },
    selectSliceArrangementNumByTime(startTime, endTime) {
      myApi
        .selectSliceArrangementNumByTime(this.cinemaId, startTime, endTime)
        .then((res) => {
          if (res.data.code == 200) {
            this.sliceArrangementNum = res.data.data;
          } else {
            this.$message.error(res.data.message);
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },
    selectOrderVoteAllPriceByTime(startTime, endTime) {
      myApi
        .selectOrderVoteAllPriceByTime(this.cinemaId, startTime, endTime)
        .then((res) => {
          if (res.data.code == 200) {
            this.orderAllPrice = res.data.data;
          } else {
            this.$message.error(res.data.message);
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },
    selectAllOrderPrice() {
      myApi
        .selectAllOrderPrice(this.cinemaId)
        .then((res) => {
          if (res.data.code == 200) {
            this.turnover = res.data.data;
          } else {
            this.$message.error(res.data.message);
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },
  },
  mounted() {
    this.cinemaId = localStorage.getItem("cinemaId");
    this.selectOrderNumByTime(
      utils.getTimeForAnyWeek(1),
      utils.getTimeForAnyWeek()
    );
    this.selectSliceArrangementNumByTime(
      utils.getTimeForAnyWeek(1),
      utils.getTimeForAnyWeek()
    );
    this.selectOrderVoteAllPriceByTime(
      utils.getTimeForAnyWeek(1),
      utils.getTimeForAnyWeek()
    );
    this.selectAllOrderPrice();
    this.selectNewTurnoverData();
  },
};
</script>

<style></style>

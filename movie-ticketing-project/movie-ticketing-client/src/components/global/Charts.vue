<template>
    <div class="charts-container">
      <!-- 折线图 -->
      <div class="chart-wrapper">
        <h3>折线统计图</h3>
        <v-chart class="chart" :option="lineOption" />
      </div>
  
      <!-- 两个饼图并排 -->
      <div class="pie-container">
        <div class="chart-wrapper">
          <h3>饼图 1</h3>
          <v-chart class="chart" :option="pieOption1" />
        </div>
        <div class="chart-wrapper">
          <h3>饼图 2</h3>
          <v-chart class="chart" :option="pieOption2" />
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import { use } from "echarts/core";
  import { CanvasRenderer } from "echarts/renderers";
  import { LineChart, PieChart } from "echarts/charts";
  import {
    TitleComponent,
    TooltipComponent,
    LegendComponent,
    GridComponent,
  } from "echarts/components";
  import VChart from "vue-echarts";
  
  use([
    CanvasRenderer,
    LineChart,
    PieChart,
    TitleComponent,
    TooltipComponent,
    LegendComponent,
    GridComponent,
  ]);
  
  export default {
    components: {
      VChart,
    },
    data() {
      return {
        // 折线图配置
        lineOption: {
          title: { text: "折线图示例" },
          tooltip: { trigger: "axis" },
          xAxis: {
            type: "category",
            data: ["周一", "周二", "周三", "周四", "周五", "周六", "周日"],
          },
          yAxis: { type: "value" },
          series: [
            {
              data: [150, 230, 224, 218, 135, 147, 260],
              type: "line",
              smooth: true,
            },
          ],
        },
  
        // 饼图1配置
        pieOption1: {
          title: { text: "饼图1" },
          tooltip: { trigger: "item" },
          series: [
            {
              name: "比例",
              type: "pie",
              radius: "50%",
              data: [
                { value: 1048, name: "A" },
                { value: 735, name: "B" },
                { value: 580, name: "C" },
              ],
            },
          ],
        },
  
        // 饼图2配置
        pieOption2: {
          title: { text: "饼图2" },
          tooltip: { trigger: "item" },
          series: [
            {
              name: "比例",
              type: "pie",
              radius: ["40%", "70%"], // 环形图
              data: [
                { value: 1048, name: "X" },
                { value: 735, name: "Y" },
                { value: 580, name: "Z" },
              ],
            },
          ],
        },
      };
    },
  };
  </script>
  
  <style scoped>
  .charts-container {
    padding: 20px;
  }
  .chart-wrapper {
    margin-bottom: 30px;
  }
  .chart {
    height: 400px;
  }
  .pie-container {
    display: flex;
    gap: 20px;
  }
  .pie-container .chart-wrapper {
    flex: 1;
  }
  </style>
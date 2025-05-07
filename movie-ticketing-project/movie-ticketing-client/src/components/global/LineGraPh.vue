<template>
  <div class="charts-container">
    <div class="chart-wrapper">
        <div style="position:absolute;top:0;right: 2rem;">
            <slot></slot>
        </div>
      <v-chart class="chart" :option="data" />
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
  TitleComponent,
  TooltipComponent,
  LegendComponent,
  GridComponent,
]);

export default {
  props: {
    data: {
      type: Object,
      required: false,
      default: () => {
        return {
          title: { text: "折线图示例" },
          tooltip: { trigger: "axis" },
          xAxis: {
            type: "category",
            data: ["周一", "周二", "周三", "周四", "周五", "周六", "周日"],
          },
          yAxis: { type: "value" },
          series: [
            {
              name: "邮件营销",
              data: [150, 230, 224, 218, 135, 147, 260],
              type: "line",
              smooth: true,
              symbol: 'circle',
              symbolSize:8
            },
            {
              name: "联盟广告",
              data: [120, 280, 214, 208, 105, 137, 280],
              type: "line",
              smooth: true,
              symbol: 'circle',
              symbolSize:8
            },
          ],
        };
      },
    },
  },
  components: {
    VChart,
  },
};
</script>

<style scoped>
.charts-container {
  padding: 20px;
}
.chart-wrapper {
  margin-bottom: 30px;
  position: relative;
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

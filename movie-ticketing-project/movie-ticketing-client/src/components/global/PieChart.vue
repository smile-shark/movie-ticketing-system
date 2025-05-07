<template>
  <div class="charts-container">
    <div class="pie-container">
      <div class="chart-wrapper">
        <v-chart class="chart" :option="data" />
      </div>
    </div>
  </div>
</template>

<script>
import { use } from "echarts/core";
import { CanvasRenderer } from "echarts/renderers";
import { PieChart } from "echarts/charts";
import {
  TitleComponent,
  TooltipComponent,
  LegendComponent,
  GridComponent,
} from "echarts/components";
import VChart from "vue-echarts";

use([
  CanvasRenderer,
  PieChart,
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
          title: { text: "饼图" },
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
        };
      },
    },
  },
  components: {
    VChart,
  },
  mounted(){
    const colors=Array.from({length:6},()=>`hsl(${Math.floor(Math.random()*360)},70%,60%)`)
    this.data.color=colors
  }
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

<template>
  <div>
    <el-row>
        <el-col :span="24" style="padding-left: 20px;">
            屏幕方向
        </el-col>
        <el-col :span="20" style="padding: 20px; overflow-x: auto;">
            <span v-if="layout.length == 0">&nbsp;</span>
            <div style="display: inline-block;">
                <div v-for="(row, index) in layout" :key="index" style="white-space: nowrap;">
                <Seat @click.native="selectSeat(seat)"
                    v-for="(seat, seatIndex) in row" 
                    :key="seatIndex"
                    :color="seat.color"
                    :border-color="seat.borderColor"
                    :x="seat.x" 
                    :y="seat.y"
                    style="display: inline-block; margin-right: 5px;"
                />
                </div>
            </div>
        </el-col>
        <el-col :span="4">
            <p style="display: flex;align-items: center;justify-content: center;height: 100%;">
                空白部分为走廊
            </p>
            <p style="display: flex;align-items: center;justify-content: center;height: 100%;" v-if="!isCinemaManagement">
                座位（可选择）：
                <Seat :color="'white'"></Seat>
            </p>
            <p style="display: flex;align-items: center;justify-content: center;height: 100%;" v-else>
                座位（未售）：
                <Seat :color="'white'"></Seat>
            </p>
            <p style="display: flex;align-items: center;justify-content: center;height: 100%;" v-if="!isCinemaManagement">
                座位（不可选）：
                <Seat :color="'red'"></Seat>
            </p>
            <p style="display: flex;align-items: center;justify-content: center;height: 100%;" v-else>
                座位（已售）：
                <Seat :color="'red'"></Seat>
            </p>
            <p style="display: flex;align-items: center;justify-content: center;height: 100%;"  v-if="!isCinemaManagement">
                座位（已选择）：
                <Seat :color="'green'"></Seat>
            </p>
        </el-col>
    </el-row>
  </div>
</template>

<script>
import Seat from './Seat.vue';
export default {
    components:{
        Seat
    },
    props:{
        layout:{
            type:Array,
            required:false,
            default:()=>[
                [
                    {
                        x:1,
                        y:1,
                        seat:true,
                        color:'white',
                        isBuy:false,
                        isSelected:false,
                        borderColor:'black'
                    }
                ]
            ]
        },
        isCinemaManagement:{
            type:Boolean,
            required:false,
            default:false
        }
    },
    methods:{
        selectSeat(seat){
            if(!seat.isBuy&&!this.isCinemaManagement){
                seat.isSelected = !seat.isSelected;
                seat.color = seat.isSelected ? 'green' : 'white';
            }
        }
    }
}
</script>

<style>

</style>
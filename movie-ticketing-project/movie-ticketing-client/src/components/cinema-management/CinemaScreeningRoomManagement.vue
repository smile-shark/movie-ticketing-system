<template>
  <div>
    <el-row>
        <el-col :span="24">
            <el-form :model="screeningRoom" label-width="100px">
                <el-col :span="5">
                    <el-form-item label="放映厅名称">
                        <el-input v-model="screeningRoom.screeningRoomName" placeholder="请输入放映厅名称"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="3" style="display: flex;align-items: center;justify-content: center;height: 40px;line-height: 40px;">
                    行数：{{screeningRoom.rowAll}}
                    列数：{{screeningRoom.colAll}}
                </el-col>
                <el-col :span="2" style="display: flex;align-items: center;justify-content: center;">
                    <el-button type="primary" size="small" @click="addRow">添加列</el-button>
                </el-col>
                <el-col :span="2" style="display: flex;align-items: center;justify-content: center;">
                    <el-button type="warning" size="small" @click="reduceRow">减少列</el-button>
                </el-col>
                <el-col :span="2" style="display: flex;align-items: center;justify-content: center;">
                    <el-button type="primary" size="small" @click="addCol">添加行</el-button>
                </el-col>
                <el-col :span="2" style="display: flex;align-items: center;justify-content: center;">
                    <el-button type="warning" size="small" @click="reduceCol">减少行</el-button>
                </el-col>
            </el-form>
        </el-col>
    </el-row>
    <el-row>
        <el-col :span="24" style="padding-left: 20px;">
            屏幕方向
        </el-col>
        <el-col :span="20" style="padding: 20px; overflow-x: auto;">
            <span v-if="screeningRoom.layout.length == 0">&nbsp;</span>
            <div style="display: inline-block;">
                <div v-for="(row, index) in screeningRoom.layout" :key="index" style="white-space: nowrap;">
                <Seat @click.native="updateSeat(seat)"
                    v-for="(seat, seatIndex) in row" 
                    :key="seatIndex"
                    :color="seat.color" 
                    :x="seat.x" 
                    :y="seat.y"
                    style="display: inline-block; margin-right: 5px;"
                />
                </div>
            </div>
        </el-col>
        <el-col :span="4">
            <p style="display: flex;align-items: center;justify-content: center;height: 100%;">
                走廊： 
              <Seat :color="'white'"></Seat>
            </p>
            <p style="display: flex;align-items: center;justify-content: center;height: 100%;">
                座位：
                <Seat></Seat>
            </p>
        </el-col>
    </el-row>
  </div>
</template>

<script>
import Seat from '../global/Seat.vue';
export default {
    props:{
        screeningRoom:{
            type:Object,
            required:true,
            default:()=>{
                return{
                    cinemaId:'',
                    screeningRoomName:'',
                    rowAll:0,
                    colAll:0,
                    layout:[
                        [
                            {
                                color:'gray',
                                seat:true,
                                x:0,
                                y:0
                            }
                        ]
                    ]
                }
            }
        }
    },
    components:{
        Seat
    },
    methods:{
        addRow(){
            this.screeningRoom.rowAll++;
            for(let i=0;i<this.screeningRoom.layout.length;i++){
                this.screeningRoom.layout[i].push({
                    color:'gray',
                    seat:true,
                    x:i+1,
                    y:this.screeningRoom.rowAll
                })
            }
        },
        reduceRow(){
            if(this.screeningRoom.rowAll>0){
                for(let i=0;i<this.screeningRoom.layout.length;i++){
                    this.screeningRoom.layout[i].pop();
                }
                this.screeningRoom.rowAll--;
            }else{
                this.$message.error('至少要保留一行');
            }
        },
        addCol(){
            this.screeningRoom.colAll++;
            // 首先要确定每行的列数，根据列的数量来生成行中需要的座位
            let newRow=[];
            for(let i=0;i<this.screeningRoom.layout[0].length;i++){
                newRow.push({
                    color:'gray',
                    seat:true,
                    x:this.screeningRoom.colAll,
                    y:i+1
                })
            }
            this.screeningRoom.layout.push(newRow)
        },
        reduceCol(){
            if(this.screeningRoom.colAll>0){
                this.screeningRoom.layout.pop();
                this.screeningRoom.colAll--;
            }else{
                this.$message.error('至少要保留一列');
            }
        },
        updateSeat(seat){
            seat.color=seat.color=='gray'?'white':'gray';
            seat.seat=!seat.seat
        }
    }
}
</script>

<style>

</style>
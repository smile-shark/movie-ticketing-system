<template>
  <div>
    <el-row>
        <el-col :span="24">
            <el-table :data="screeningRooms" style="width: 100%">
                <el-table-column label="放映厅名称" prop="screeningRoomName"></el-table-column>
                <el-table-column label="放映厅座位行数">
                    <template slot-scope="scope">
                        {{ scope.row.rowAll }} 行
                    </template>
                </el-table-column>
                <el-table-column label="放映厅座位列数">
                    <template slot-scope="scope">
                        {{ scope.row.colAll }} 列   
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button type="text" @click="handleEdit(scope.row)">编辑</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-col>
    </el-row>
    <el-dialog title="放映厅编辑"
    :visible.sync="updateScreeningRoomVisible"
    width="80%"
    @close="windowReload">
        <el-row>
            <el-col :span="24">
                <CinemaScreeningRoomUpdatePageVue :screening-room="updateScreeningRoom"></CinemaScreeningRoomUpdatePageVue>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <el-button type="primary" @click="updateScreeningRoomFun">更新数据</el-button>
            </el-col>
        </el-row>
    </el-dialog>
  </div>
</template>

<script>
import { myApi } from '@/axios/my';
import CinemaScreeningRoomUpdatePageVue from './CinemaScreeningRoomUpdatePage .vue';
export default {
    components:{
        CinemaScreeningRoomUpdatePageVue
    },
    data(){
        return{
            screeningRooms:[],
            cinemaId:'',
            updateScreeningRoom:{},
            updateScreeningRoomVisible:false
        }
    },
    mounted(){
        let cinemaId=localStorage.getItem('cinemaId')
        if(!cinemaId){
            this.$message.error('请先选择影院')
            return
        }
        this.cinemaId=cinemaId
        myApi.selectScreeningRoomByCinemaId(this.cinemaId).then(res=>{
            if(res.data.code==200){
                this.screeningRooms=res.data.data
                for(let screeningRoom of this.screeningRooms){
                    screeningRoom.layout=JSON.parse(screeningRoom.layout)   
                }
            }else{
                this.$message.error(res.data.message)
            }
        }).catch(e=>{
            console.log(e)
        })
    },
    methods:{
        handleEdit(row){
            this.updateScreeningRoom=row
            this.updateScreeningRoomVisible=true
        },
        windowReload(){
            window.location.reload()
        },
        updateScreeningRoomFun(){
            if(this.rowAll<=0 || this.colAll<=0){
                this.$message.error('请选择布局');
                return;
            }
            if(this.updateScreeningRoom.screeningRoomName==''){
                this.$message.error('请输入影厅名称');
                return;
            }
            myApi.updateScreeningRoom(this.updateScreeningRoom).then(res=>{
                if(res.data.code==200){
                    this.$message.success(res.data.message)
                    this.updateScreeningRoomVisible=false
                }else{
                    this.$message.error(res.data.message)
                }
            }).catch(e=>{
                console.log(e)
            })
        }
    }
}
</script>

<style>

</style>
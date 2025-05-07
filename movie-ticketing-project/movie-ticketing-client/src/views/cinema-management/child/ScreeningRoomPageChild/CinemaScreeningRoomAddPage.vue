<template>
  <div>
    <el-row>
        <el-col :span="24">
            <CinemaScreeningRoomManagement :screening-room="screeningRoom"></CinemaScreeningRoomManagement>
        </el-col>
    </el-row>
    <el-row>
        <el-col :span="24" style="padding:20px;">
            <el-button type="primary" @click="insertScreeningRoom">添加</el-button>
        </el-col>
    </el-row>
  </div>
</template>

<script>
import { myApi } from '@/axios/my';
import CinemaScreeningRoomManagement from '@/components/cinema-management/CinemaScreeningRoomManagement.vue';
// 是否需要获取数据由外部决定，然后传递到组件中，因为添加时是不需要查询数据的
// 其他地方可能会用到影院的数据，可以在获取的时候将数据存储在localStorage中
// 将添加和修改后保存的按钮放在外部
export default {
    components:{
        CinemaScreeningRoomManagement
    },
    data(){
        return{
            screeningRoom:{
                cinemaId:'',
                screeningRoomName:'',
                rowAll:1,
                colAll:1,
                layout:[
                        [
                            {
                                color:'gray',
                                seat:true,
                                x:1,
                                y:1
                            }
                        ]
                    ]
            }
        }
    },
    methods:{
        insertScreeningRoom(){
            if(!localStorage.getItem('cinemaId')){
                this.$message.error('没有选择影院');
                return;
            }
            this.screeningRoom.cinemaId=localStorage.getItem('cinemaId')
            if(this.rowAll<=0 || this.colAll<=0){
                this.$message.error('请选择布局');
                return;
            }
            if(this.screeningRoom.screeningRoomName==''){
                this.$message.error('请输入影厅名称');
                return;
            }
            myApi.insertScreeningRoom(this.screeningRoom).then(res=>{
                if(res.data.code==200){
                    this.$message.success(res.data.message);
                }else{
                    this.$message.error(res.data.message);
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
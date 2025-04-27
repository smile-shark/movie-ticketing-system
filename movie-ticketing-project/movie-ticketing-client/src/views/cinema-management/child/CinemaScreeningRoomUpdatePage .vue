<template>
  <div>
    <el-row>
        <el-col :span="24">
            <CinemaScreeningRoomManagement :screening-room="screeningRoom"></CinemaScreeningRoomManagement>
        </el-col>
    </el-row>
  </div>
</template>

<script>
import CinemaScreeningRoomManagement from '@/components/cinema-management/CinemaScreeningRoomManagement.vue';
// 有界面但是不需要路由
export default {
    components:{
        CinemaScreeningRoomManagement
    },  
    props:{
        screeningRoom:{
            type:Object,
            default:()=>{
                return{
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
        }
    },
    data(){
        return{
        }
    }
}
</script>

<style>

</style>
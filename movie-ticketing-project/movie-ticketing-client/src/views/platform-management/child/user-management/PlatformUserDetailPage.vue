<template>
  <div style="position: relative;">
    <!-- 展示的组件可以是公用的，但是可以直接修改信息的不能是公用的，所以这个查询需要在外部 -->
    <UserDetailInfo :user="user" :is-platform="true"></UserDetailInfo>
    <div style="position: absolute;top: 10vh;right: 0;">
        <h3>管理可修改</h3>
        <el-row>
            <el-col :span="24">
                <p>用户积分</p>
                <el-input v-model="user.userPoints" placeholder="用户积分"></el-input>
            </el-col>
            <el-col :span="24">
                <p>用户等级</p>
                <el-select v-model="user.userLevel" placeholder="用户等级">
                    <el-option v-for="item in 10" :key="item" :value="item" :label="`LEVEL ${item}`"></el-option>
                </el-select>
            </el-col>
        </el-row>
    </div>
    <el-dialog title="请选择用户"
      width="20%"
      :visible.sync="notSelectedUser"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :show-close="false"
      :modal-append-to-body="false">
      <el-button type="primary" style="width: 100%;" @click="$router.push({name:'PlatformUserListPage'})">去选择</el-button>
      </el-dialog>
  </div>
</template>

<script>
import { myApi } from '@/axios/my';
import UserDetailInfo from '@/components/global/UserDetailInfo.vue';
export default {
    components:{
        UserDetailInfo
    },
    data(){
        return{
            user:{},
            notSelectedUser:false
        }
    },
    mounted(){
        if(this.$route.params.userId){
            // 根据传递的用户id去查询详细的用户信息
            myApi.selectUsersByUser(1,1,{userId:this.$route.params.userId}).then(res=>{
                if(res.data.code==200){
                    if(res.data.data.list.length>0){
                        this.user=res.data.data.list[0]
                    }
                }else{
                    this.$message.error(res.data.message)
                }
            }).catch(e=>{
                console.loog(e)
            })


        }else{
            this.notSelectedUser=true
        }
    }
}
</script>

<style>

</style>
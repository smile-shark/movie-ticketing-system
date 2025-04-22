<template>
  <div>
    <el-row>
        <el-col :span="18">
            <el-table :data="userData.list" style="min-height:79vh;">
                <el-table-column label="用户邮箱" prop="userEmail">
                    <template slot-scope="scope">
                        <TruncatedText :lines="1">
                            {{ scope.row.userEmail }}
                        </TruncatedText>
                    </template>
                </el-table-column>
                <el-table-column label="用户头像">
                    <template slot-scope="scope">
                        <el-avatar :size="30" :src="scope.row.userProfilePicture">
                            <img src="https://cube.elemecdn.com/e/fd/0fc7d20532fdaf769a25683617711png.png"/>
                        </el-avatar>
                    </template>
                </el-table-column>
                <el-table-column label="用户名字" prop="userName">
                    <template slot-scope="scope">
                        <TruncatedText :lines="1">
                            {{ scope.row.userName }}
                        </TruncatedText>
                    </template>
                </el-table-column>
                <el-table-column label="用户等级" prop="userLevel">
                    <template slot-scope="scope">
                        LEVEL {{ scope.row.userLevel }}
                    </template>
                </el-table-column>
                <el-table-column label="用户地区">
                    <template slot-scope="scope">
                        <div v-for="(market,index) in marketList.filter(item=>item.marketId==scope.row.marketId)" :key="index">
                            {{ market.marketName }}
                        </div>
                    </template>
                </el-table-column>
                <el-table-column label="账号状态" prop="userState">
                    <template slot-scope="scope">
                        <el-tag type="success" v-if="scope.row.userState==0">正常</el-tag>
                        <el-tag type="warning" v-if="scope.row.userState==1">冻结</el-tag>  
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button type="text" @click="handleEdit(scope.row.userId)">编辑</el-button>
                        <el-button type="text" @click="handleFreeze(scope.row)" v-if="scope.row.userState==0">冻结</el-button>
                        <el-button type="text" @click="handleNotFreeze(scope.row.userId)" v-if="scope.row.userState==1">解冻</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div style="padding:20px">
                <el-pagination
                background
                layout="prev, pager, next"
                :page-size="size"
                @current-change="selectUsersByUser"
                :total="userData.total">
                </el-pagination>
            </div>
        </el-col>
        <el-col :span="6" style="padding:20px;">
            <h3>查询用户参数</h3>
            <el-form :model="user">
                <el-form-item label="邮箱">
                    <el-input v-model="user.userEmail" placeholder="请输入邮箱"></el-input>
                </el-form-item>
                <el-form-item label="用户名字">
                    <el-input v-model="user.userName" placeholder="请输入用户名字"></el-input>
                </el-form-item>
                <el-form-item label="用户等级">
                    <el-select v-model="user.userLevel">
                        <el-option label="LEVEL 1" value="1"></el-option>
                        <el-option label="LEVEL 2" value="2"></el-option>
                        <el-option label="LEVEL 3" value="3"></el-option>
                        <el-option label="LEVEL 4" value="4"></el-option>
                        <el-option label="LEVEL 5" value="5"></el-option>
                        <el-option label="LEVEL 6" value="6"></el-option>
                        <el-option label="LEVEL 7" value="7"></el-option>
                        <el-option label="LEVEL 8" value="8"></el-option>
                        <el-option label="LEVEL 9" value="9"></el-option>
                        <el-option label="LEVEL 10" value="10"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="账号状态">
                    <el-select v-model="user.userState">
                        <el-option label="正常" value="0"></el-option>
                        <el-option label="冻结" value="1"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="地区">
                    <el-select v-model="user.marketId">
                        <el-option v-for="market in marketList" 
                        :key="market.marketId" 
                        :value="market.marketId"
                        :label="market.marketName">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="selectUsersByUser">查询</el-button>
                </el-form-item>
            </el-form> 
        </el-col>
    </el-row>
  </div>
</template>

<script>
import { myApi } from '@/axios/my';
import TruncatedText from '@/components/global/TruncatedText.vue';
export default {
    components:{
        TruncatedText
    },
    data(){
        return{
            user:{
                userEmail:'',
                userName:'',
                userLevel:null,
                userState:null,
                marketId:null
            },
            userData:{
                list:[],
                total:0
            },
            page:1,
            size:10,
            marketList:[]
        }
    },
    methods:{
        selectUsersByUser(page=1){
            if(typeof page == Int32Array){
                this.page=page;
            }else{
                this.page=1
            }
            myApi.selectUsersByUser(this.page,this.size,this.user).then(res=>{
                if(res.data.code==200){
                    this.userData=res.data.data;
                    this.$message.success(res.data.message)
                }else{
                    this.$message.error(res.data.message)
                }
            }).catch(e=>{
                console.log(e)
            })
        },
        handleEdit(userId){
            // 将id传递到另一个详细信息的页面去
            this.$router.push({name:'PlatformUserDetailPage',params:{userId:userId}})
        },
        handleFreeze(row){
            // 冻结账户需要填写原因

        },
        handleNotFreeze(userId){

        }
    },
    mounted(){
        myApi.selectAllMarket().then(res=>{
            if(res.data.code==200){
                this.marketList=res.data.data;
            }
        })
        this.selectUsersByUser();
    }
}
</script>

<style>

</style>
<template>
  <div style="padding:40px;">
    <h3>个人信息</h3>
    <el-row>
        <el-col :span="24">
            <p>头像</p>
            <el-row>
                <el-col :span="2">
                    <el-avatar :size="60" :src="user.userProfilePicture">
                        <i class="el-icon-picture-outline"></i>
                    </el-avatar>
                </el-col>
                <el-col :span="18" style="min-height: 100%;">
                    <el-upload
                    action="javascript:void(0)"
                    :http-request="customUpload"
                    :on-remove="fileDelete"
                    accept=".png,.jpg,.jpeg"
                    :limit="1"
                    class="upload-demo"
                    :file-list="fileList" style="height: 100%;">
                        <el-button size="small" type="primary" style="position: relative;" icon="el-icon-upload2">更新头像</el-button>
                        <div slot="tip" class="el-upload__tip" >只能上传jpg/png文件，且不超过500kb</div>
                    </el-upload>
                </el-col>
            </el-row>
        </el-col>
        <el-col :span="24">
            <p>昵称<span style="color: red;">*</span></p>
            <el-col :span="6">
                <el-input v-model="user.userName" placeholder="请输入昵称"></el-input>
            </el-col>
        </el-col>
        <el-col :span="24">
            <p>地区</p>
            <el-col :span="6">
                <el-select v-model="user.marketId" placeholder="请选择地区">
                    <el-option v-for="market in markets" :key="market.marketId" :label="market.marketName" :value="market.marketId"></el-option>
                </el-select>
            </el-col>
        </el-col>
        <el-col :span="24" style="padding-top: 20px;">
            <el-button type="success" size="small" @click="updateUserInfo">更新信息</el-button>
        </el-col>
    </el-row>
  </div>
</template>

<script>
import { myApi } from '@/axios/my'
export default {
    data(){
        return{
            fileList:[],
            markets:[]
        }
    },
    props:{
        user:{
            type:Object,
            default:()=>{
                return{
                    userEmail:'defaultEmail',
                    userName:'defaultName',
                    userProfilePicture:'',
                    userPoints:0,
                    userLevel:1,
                    userState:0,
                    marketId:'',
                }
            }
        },
        isPlatform:{
            type:Boolean,
            primary:false,
            default:false
        }
    },
    methods:{
        customUpload(options){
            const {file}= options
            const formData = new FormData()
            formData.append('file',file);
            myApi.fileUpload(formData).then(resp=>{
                if(resp.data.code==200){
                    this.user.userProfilePicture=resp.data.data.fileUrl
                    this.$message.success(resp.data.message)
                }else{
                    this.$message.error(resp.data.message)
                }
            }).catch(e=>{
                console.log(e)
            })
        },
        fileDelete(){
            myApi.fileDelete(this.user.userProfilePicture).then(res=>{
                if(res.data.code==200){
                    this.$message.success(res.data.message)
                    this.user.userProfilePicture=''
                }else{
                    this.$message.error(res.data.message)
                }
            }).catch(e=>{
                console.log(e)
            })
        },
        updateUserInfo(){
            if(this.isPlatform){
                myApi.platformUpdateUserByUserId(this.user).then(res=>{
                    if(res.data.code==200){
                        this.$message.success(res.data.message)
                    }else{
                        this.$message.error(res.data.message)
                    }
                }).catch(e=>{
                    console.log(e)
                })
            }else{
                myApi.updateUserByUserId(this.user).then(res=>{
                    if(res.data.code==200){
                        this.$message.success(res.data.message)
                    }else{
                        this.$message.error(res.data.message)
                    }
                }).catch(e=>{
                    console.log(e)
                })
            }
        }
    },
    mounted(){
        myApi.selectAllMarket().then(res=>{
            if(res.data.code==200){
                this.markets=res.data.data
            }else{
                this.$message.error(res.data.message)
            }
        })
    }
}
</script>

<style>

</style>
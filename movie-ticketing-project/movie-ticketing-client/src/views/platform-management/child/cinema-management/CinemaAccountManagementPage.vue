<template>
  <div>
    <el-row>
        <el-col :span="24">
            <el-table :data="cinemaManagements" height="600">
                <el-table-column label="影院管理账号" prop="cinemaManagementEmail"></el-table-column>
                <el-table-column label="管理头像">
                    <template slot-scope="scope">
                        <el-avatar :src="scope.row.cinemaManagementProfilePicture"></el-avatar>
                    </template>
                </el-table-column>
                <el-table-column label="影院数量">
                    <template slot-scope="scope">
                       {{ scope.row.cinemas.length }}
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button type="primary" size="small" @click="showCinema(scope.row)">查看对应影院</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-col>
    </el-row>
    <el-row>
        <el-col :span="24" style="padding:20px">
            <el-pagination
                layout="prev, pager, next"
                :total="total"
                :page-size="size"
                @current-change="selectAllCinemaManagement"
                background></el-pagination>
        </el-col>
    </el-row>

    <el-dialog title="账号对应影院列表" :visible.sync="cinemaShowDialog" width="100%">
        <el-table :data="cinemasShow" height="400">
            <el-table-column label="影院名称" prop="cinemaName"/>
            <el-table-column label="影院地址" prop="cinemaAddress"/>
            <el-table-column label="影院电话" prop="cinemaPhone"/>
            <el-table-column label="影院邮箱" prop="cinemaEmail"/>
            <el-table-column label="影院官网">
                <template slot-scope="scope">
                    <el-link v-if="scope.row.cinemaUrl!=='无'" :href="scope.row.cinemaUrl" target="_blank">
                        {{ scope.row.cinemaUrl }}
                    </el-link>
                    <span v-else>
                        {{ scope.row.cinemaUrl }}
                    </span>
                </template>
            </el-table-column>
            <el-table-column label="影院评分" prop="cinemaPoints"></el-table-column>
            <el-table-column label="影院营业时间">
                <template slot-scope="scope">
                    {{ scope.row.cinemaStartTime }} 至 {{ scope.row.cinemaEndTime }}
                </template>
            </el-table-column>
        </el-table>
    </el-dialog>
  </div>
</template>

<script>
import { myApi } from '@/axios/my';
import { utils } from '@/utils/globalUtils';
export default {
    data(){
        return{
            utils,
            cinemaManagements:[],
            total:0,
            page:1,
            size:10,
            cinemaShowDialog:false,
            cinemasShow:[]
        }
    },
    methods:{
        selectAllCinemaManagement(page=1){
            this.page = page;
            myApi.selectAllCinemaManagement(this.page,this.size).then(res=>{
                if(res.data.code==200){
                    this.cinemaManagements = res.data.data.list;
                    this.total=res.data.data.total;
                }else{
                    this.$message.error(res.data.message);
                }
            }).catch(e=>console.log(e))
        },
        showCinema(row){
            this.cinemasShow = row.cinemas;
            this.cinemaShowDialog = true;
        }
    },
    mounted(){
        this.selectAllCinemaManagement();
    }
}
</script>

<style>

</style>
<template>
  	<div class="manage_page">
        <el-row style="height: 9vh;">
            <el-col :span="24" class="title-container">
                <div style="padding:5px;display: flex;align-items: center;">
                    <el-image src="https://img.axureshop.com/db/65/7f/db657fd4b7a84b5aa76b1c2d09d02108/images/0_1%E7%99%BB%E5%BD%95/u32.png"
                style="width: 180px; height: 60px" fit="contain">
                    </el-image>
                    <span style="margin-left: 5px;color: #fdc31d;font-weight: bold;">后台管理</span>
                    <div style="display: inline-block;position: absolute;right: 20px;color:#3477f4">
                        <div v-if="platformInfo.platformManagementName" style="display: flex;align-items: center;">
                            <el-avatar :size="40" :src="platformInfo.platformManagementProfilePicture"></el-avatar>
                            <span class="login-text" style="font-size: 14px;color:black;margin:0 20px;font-weight: bold;" >
                                {{platformInfo.platformManagementName}}
                            </span>
                            <el-link @click="$router.push('/all/admin')">退出</el-link>
                        </div>
                        <div v-else>
                            <el-link @click="$router.push('/all/admin')">去登录</el-link>
                        </div>
					</div>
                </div>
            </el-col>
        </el-row>
		<el-row style="height: 91vh;">
            <el-col :span="3" style="height: 100%;background-color: white; border-right: 1px solid #c0c6cc;">
                <el-scrollbar   style="height: 100%;" wrap-class="scrollbar" view-class="scroll-view">
                    <div>
                        <el-menu :default-active="defaultActive"  router background-color="white" text-color="black">
                            <el-menu-item index="/platform/home">
                            <i class="el-icon-menu"></i>首页
                            </el-menu-item>
                            <el-submenu index="">
                            <span slot="title"> <i class="el-icon-video-camera-solid"></i>电影管理</span>
                            <el-menu-item index="/platform/home/list-movie">电影信息列表</el-menu-item>
                            <el-menu-item index="/platform/home/insert-movie">新增电影</el-menu-item>
                            <el-menu-item index="/platform/home/update-movie">电影信息校正</el-menu-item>
                            <el-menu-item index="/platform/home/type-movie">电影类型管理</el-menu-item>
                            </el-submenu>
                            <el-submenu index="">
                            <span slot="title"><i class="el-icon-user-solid"></i>用户管理</span>
                            <el-menu-item index="/platform/home/list-user">用户列表</el-menu-item>
                            <el-menu-item index="/platform/home/detail-user">用户详细</el-menu-item>
                            </el-submenu>
                            <el-submenu index="">
                            <span slot="title"><i class="el-icon-s-cooperation"></i>影院管理</span>
                            <el-menu-item>影院账号管理</el-menu-item>
                            <el-menu-item>影院信息管理</el-menu-item>
                            <el-menu-item>影院放映厅管理</el-menu-item>
                            <el-menu-item>影院数据统计</el-menu-item>
                            </el-submenu>
                            <el-submenu index="">
                            <span slot="title"><i class="el-icon-s-grid"></i>其他管理</span>
                            <el-menu-item index="/platform/home/mobile-display">用户轮动图管理</el-menu-item>
                            <el-menu-item>广告管理</el-menu-item>
                            </el-submenu>
                        </el-menu>
                    </div>
                </el-scrollbar>
            </el-col>
			<el-col :span="21" style="height: 100%;overflow: auto;">
				<el-row>
                    <el-col :span="24" style="padding:10px;">
                        <router-view></router-view>
                    </el-col>
                </el-row>
			</el-col>
		</el-row>
  	</div>
</template>

<script>

export default {
    data(){
        return{
            defaultActive: '/platform/home',
            breadcrumbList: [],
            platformInfo:{}
        }
    },
    methods:{
    },
    watch:{
        '$route'(to,from){
            let currentPath = to.path;
            // 正则表达式匹配32位数字和小写字母组成的字符串
            const regex = /\/[0-9a-z]{32}$/;
            if (regex.test(currentPath)) {
                // 如果匹配到，去掉最后符合条件的部分
                currentPath = currentPath.replace(regex, '');
            }
            this.defaultActive = currentPath;
        }
    },
    mounted(){
        if(localStorage.getItem('platformInfo')){
            this.platformInfo=JSON.parse(localStorage.getItem('platformInfo'))
        }
        this.defaultActive=this.$route.path
    }
}
</script>

<style>
.manage_page{
	height: 100vh;
}
.title-container{
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    z-index: 100;
    position: relative;
}
</style>
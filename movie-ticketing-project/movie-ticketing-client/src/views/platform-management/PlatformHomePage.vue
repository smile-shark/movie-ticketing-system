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
						<el-link @click="$router.push('/all/admin')">退出</el-link>
					</div>
                </div>
            </el-col>
        </el-row>
		<el-row style="height: 91vh;">
	  		<el-col :span="3"  
			style="min-height: 100%; background-color: white;border-right:1px solid #c0c6cc;">
				<el-menu :default-active="defaultActive" 
				style="min-height: 100%;" router       
				background-color="white"
      			text-color="black">
					<el-menu-item index="/platform/home">
						<i class="el-icon-menu"></i>首页
					</el-menu-item>
					<el-submenu index="1">
						<template slot="title">
                            <i class="el-icon-s-data"></i>
                            <span slot="title">数据管理</span>
                        </template>
                        <el-submenu index="1-1">
                            <span slot="title">电影管理</span>
                            <el-menu-item index="/platform/home/list-movie">电影信息列表</el-menu-item>
                            <el-menu-item index="/platform/home/insert-movie">新增电影</el-menu-item>
                            <el-menu-item index="/platform/home/update-movie">电影信息校正</el-menu-item>
                        </el-submenu>
					</el-submenu>
				</el-menu>
			</el-col>
			<el-col :span="20" style="height: 100%;overflow: auto;">
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
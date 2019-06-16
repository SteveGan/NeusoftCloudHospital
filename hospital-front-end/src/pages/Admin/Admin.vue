<template>
  <el-container style="height: 700px;">
    <!--  侧边栏  -->
    <el-aside width="200px">
      <el-menu
        default-active="1"
        class="el-menu-vertical-demo"
        :router="activeRouter">
        <el-menu-item index="/home/admin/user">
          <i class="el-icon-s-custom"></i>
          <span slot="title">用户管理</span>
        </el-menu-item>
        <el-menu-item index="/home/admin/department">
          <i class="el-icon-s-home"></i>
          <span slot="title">科室管理</span>
        </el-menu-item>
        <el-menu-item index="/home/admin/registrationlevel">
          <i class="el-icon-s-operation"></i>
          <span slot="title">挂号级别管理</span>
        </el-menu-item>
        <el-menu-item index="/home/admin/paytype">
          <i class="el-icon-s-check"></i>
          <span slot="title">结算类别管理</span>
        </el-menu-item>
        <el-menu-item index="/home/admin/disease">
          <i class="el-icon-s-order"></i>
          <span slot="title">诊断目录管理</span>
        </el-menu-item>
        <el-menu-item index="/home/admin/nonmedicineproject">
          <i class="el-icon-first-aid-kit"></i>
          <span slot="title">非药品收费项目管理</span>
        </el-menu-item>
        <el-submenu index="7">
          <template slot="title">
            <i class="el-icon-location"></i>
            <span>医生排班管理</span>
          </template>
          <el-menu-item index="arrangementrule">
            <i class="el-icon-edit"></i>
            <span slot="title">设置排班规则</span>
          </el-menu-item>
          <el-menu-item index="schedule">
            <i class="el-icon-document-checked"></i>
            <span slot="title">生成排班计划</span>
          </el-menu-item>
        </el-submenu>
      </el-menu>
    </el-aside>
    <!--  主页部分  -->
    <el-main class="main-container">
      <router-view/>
    </el-main>
  </el-container>
</template>

<script>
  export default {
    name: "Admin",
    data: () =>{
      return {
        activeRouter: true,
      }
    },
    methods: {
    }
  }

</script>

<style lang="css" scoped>
  .container {
    margin: 0;
    padding: 0;
    height: 100%;
  }
  .el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 200px;
    min-height: 400px;
  }
  .main-container{
    background-color: #f5f5f5;
    margin: 0;
    padding: 0;
    height: 100%;
  }
</style>

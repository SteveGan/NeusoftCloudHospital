<template lang="html">
  <el-container style="height: 500vh;">
    <!--  侧边栏  -->
    <el-aside width="200px">
      <el-menu
        default-active="1"
        class="el-menu-vertical-demo"
        :router="activeRouter"
        @open="handleOpen"
        @close="handleClose">
        <el-menu-item index="/home/finicialadmin/priceadmin">
          <i class="el-icon-coin"></i>
          <span slot="title">费用科目管理</span>
        </el-menu-item>
        <el-menu-item index="/home/finicialadmin/dailycheck">
          <i class="el-icon-copy-document"></i>
          <span slot="title">门诊日结核对</span>
        </el-menu-item>
        <el-menu-item index="/home/finicialadmin/departmentworkloadanalysis">
          <i class="el-icon-data-analysis"></i>
          <span slot="title">门诊科室工作量统计</span>
        </el-menu-item>
        <el-menu-item index="/home/finicialadmin/userworkloadanalysis">
          <i class="el-icon-data-line"></i>
          <span slot="title">门诊医生工作量统计</span>
        </el-menu-item>
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
  name: 'FinicialAdmin'
}
</script>

<style lang="css" scoped>
</style>

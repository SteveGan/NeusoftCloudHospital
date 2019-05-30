<template>
  <div>
    <el-menu  class="el-menu-demo align-end" mode="horizontal" @select="handleSelect">
      <el-menu-item class="justify-start">
        <img src="@/assets/icons/project_logo_complete.png" class="project-logo"/>
      </el-menu-item>
      <el-submenu index="">
        <template slot="title">切换身份</template>
        <el-menu-item index="2-1">选项1</el-menu-item>
        <el-menu-item index="2-2">选项2</el-menu-item>
        <el-menu-item index="2-3">选项3</el-menu-item>
      </el-submenu>
      <el-submenu index="1">
        <template slot="title">
          <!--   用户头像     -->
          <img :src='avatar' class="avatar"/>
          <span class="user-name">
                  {{this.$store.state.user.name}}
                </span>
        </template>
        <el-menu-item index="1-2">
          <i class="el-icon-edit"></i>
          编辑个人信息
        </el-menu-item>
        <el-menu-item>
          <i class="el-icon-s-tools"></i>
          个性化设置
        </el-menu-item>
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>

export default {
  name: 'StatusBar',
  data() {
      return {
        avatar: require('@/assets/images/avatar_default_blue.svg'),
      }
    },
  methods: {
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    }
  },
  mounted: function () {
      if(this.$store.state.user.avatar !== ''){
        this.avatar = this.$store.state.user.avatar
      }
  }
}

</script>

<style lang="css" scoped>
  .project-logo{
    widows: 100px;
    height: 40px;
  }
  .avatar{
    width: 35px;
    height: 35px;
    border-radius: 100%;
  }
  .user-name{
    margin-left: 10px;
  }
  .align-end{
    display: flex;
    justify-content: flex-end;
  }
  .justify-start{
    margin-right: auto;
  }
</style>

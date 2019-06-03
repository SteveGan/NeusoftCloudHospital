<template lang="html">
  <div class="user-info">
    <!-- 用户基本信息区，左照片，右ID和姓名 -->
    <div class="user-basic-info">
      <!-- 照片 -->
      <div class="avatar-container">
        <img :src="this.userInfo.avatar" />
      </div>
      <!-- 基本信息 -->
      <div class="basic-info-container">
        <span>{{this.userInfo.userName}} (ID: {{this.userInfo.userId}})</span>
      </div>
    </div>
    <!-- 分割线 -->
    <el-divider></el-divider>
    <!-- 用户角色区,使用用户角色卡片 -->
    <div class="">
      <user-roles :roles="this.userInfo.roles"></user-roles>
    </div>
  </div>
</template>
<script>

import UserRoles from '@/components/user/UserRoles/UserRoles'

export default {
  name: 'UserBasicInfo',
  components: {
    'user-roles': UserRoles
  },
  props:{
    userInfo: Object
  }
}
</script>

<style lang="css" scoped>
.user-basic-info{
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  align-items: center;
}
.basic-info-container{
  margin-left: 20px;
  font-size: 16px;
}
.avatar-container img{
  width: 50px;
  height: 50px;
  border-radius: 100%;
}
.title{
  font-size: 27px;
}
</style>

<template lang="html">
  <div class="role-area">
    <!--  标题："部门与职位"      -->
    <div>
      <p class="title">部门与职位</p>
    </div>
    <!--   内容：一堆卡片    -->
    <div class="card-container">
      <role-card v-for="roleObj in this.roles" :key="roleObj.id" :role="roleObj"></role-card>
    </div>
  </div>
</template>

<script>
import RoleCard from './Child/RoleCard'
export default {
  name: 'UserRoles',
  components: {
    'role-card': RoleCard
  },
  props:{
    roles: Array
  }
}
</script>

<style lang="css" scoped>
.role-area{
  padding-left: 20px;
  padding-right: 20px;
}
.card-container{
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
}
.title{
  margin-left: 30px;
  font-size: 27px;
}
</style>

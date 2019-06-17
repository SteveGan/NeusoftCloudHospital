<template lang="html">
<div class="main-box">
  <el-card shadow="hover" class="main-card">
    <div class="role-area">
      <!--  标题："部门与职位"      -->
      <div>
        <p class="title">部门与职位</p>
      </div>
      <div class="card-container">
        <role-card v-for="roleObj in this.roles" :key="roleObj.id" :role="roleObj"></role-card>
      </div>
    </div>
  </el-card>
  </div>
</template>
<script>
import RoleCard from "./Child/RoleCard";

export default {
  name: "UserRoles",
  components: {
    "role-card": RoleCard
  },
  props: {
    roles: Array
  }
};
</script>

<style lang="css" scoped>
.main-box {
  background-color: #f2f7f8;
  margin: 0px;
}
.main-card{
  margin: 15px 30px 25px 30px;
}
.role-area {
  padding-left: 20px;
  padding-right: 20px;
}
.card-container {
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  flex-wrap: wrap;
}
.title {
  margin-left: 30px;
  font-size: 27px;
}
</style>

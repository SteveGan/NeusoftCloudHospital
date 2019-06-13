<template lang="html">
  <el-card shadow="hover">
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

<template>
  <div>
    <!-- 部门与职位部分 -->
    <div>
      <user-roles :roles="this.$store.state.user.roles"></user-roles>
    </div>

    <!-- 统计数据部分 -->
    <div>
      <el-card shadow="hover" class="main-card">
        <div class="role-area">
          <div>
            <p class="title">我的</p>
          </div>
          <div class="card-container">
            <role-card v-for="roleObj in this.roles" :key="roleObj.id" :role="roleObj"></role-card>
          </div>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
import UserRoles from "@/components/user/UserRoles/UserRoles";

export default {
  name: "Main",
  components: {
    "user-roles": UserRoles
  }
};
</script>


<style lang="css" scoped>
.container {
  padding-left: 40px;
  padding-right: 40px;
}
.main-box {
  background-color: #f2f7f8;
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

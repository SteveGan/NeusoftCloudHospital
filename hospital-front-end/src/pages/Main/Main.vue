<template>
  <div>
    <!--  部门与职位部分    -->
    <div>
      <user-roles :roles="this.$store.state.user.roles"></user-roles>
    </div>
    <!--  统计数据部分    -->
    <div>
      <!--  标题："数据统计"      -->
      <!-- <div>
        <p class="title">历史数据统计</p>
      </div> -->
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
</style>

<template>
  <div>
    <el-menu class="el-menu-demo align-end" mode="horizontal">
      <el-menu-item class="justify-start" @click="handleClickLogo">
        <img src="@/assets/icons/project_logo_complete.png" class="project-logo">
      </el-menu-item>
      <el-menu-item>
        <el-dropdown>
          <span class="el-dropdown-link">
            {{this.$store.getters['user/currentRoleDescription']}}
            <i
              class="el-icon-arrow-down el-icon--right"
            ></i>
          </span>
          <el-dropdown-menu slot="dropdown" @command="handleSelectRole">
            <el-dropdown-item
              v-for="role in this.$store.getters['user/roles']"
              v-bind:key="role.roleId"
              command="role"
            >{{role.departmentName}}:{{role.positionName}}</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-menu-item>
      <el-submenu index="1">
        <template slot="title">
          <!--   用户头像     -->
          <img :src="avatar" class="avatar">
          <span class="user-name">{{this.$store.state.user.name}}</span>
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
import { routeByPositionId } from "@/utils/path";
export default {
  name: "StatusBar",
  data() {
    return {
      avatar: require("@/assets/images/avatar_default_blue.svg")
    };
  },
  methods: {
    handleSelectRole(role) {
      console.log(role);
      const positionId = role.positionId;
      console.log("push to ");
      this.$router.push({ path: routeByPositionId(positionId) });
    },
    handleClickLogo() {
      this.$router.push({ path: "/home" });
    }
  },
  mounted: function() {
    if (this.$store.state.user.avatar !== "") {
      this.avatar = this.$store.state.user.avatar;
    }
  }
};
</script>

<style lang="css" scoped>
.project-logo {
  widows: 100px;
  height: 40px;
}
.avatar {
  width: 35px;
  height: 35px;
  border-radius: 100%;
}
.user-name {
  margin-left: 10px;
}
.align-end {
  display: flex;
  justify-content: flex-end;
  background-image: linear-gradient(to right, #00bdda 0%, #0178bc 100%);
  
}
.justify-start {
  margin-right: auto;
}
.el-dropdown-link {
  cursor: pointer;
  color: #fff;
}
.el-icon-arrow-down {
  font-size: 12px;
}
.demonstration {
  display: block;
  color: #8492a6;
  font-size: 14px;
  margin-bottom: 20px;
}
</style>

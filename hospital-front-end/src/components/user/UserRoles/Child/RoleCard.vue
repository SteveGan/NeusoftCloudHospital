<template>
  <el-card class="box-card role-card" shadow="hover">
    <div slot="header" class="clearfix">
      <i :class="icon"></i>
      <span>{{realDepartmentName}}</span>
      <el-button style="float: right; padding: 3px 0" type="text" @click="handleClick">进入角色</el-button>
    </div>
    <div>
      <p class="role-info">
        <span>职位：</span>
        {{realPositionName}}
      </p>
    </div>
    <div v-if="realTitleName!=null">
      <p class="role-info">
        <span>职称：</span>
        {{realTitleName}}
      </p>
    </div>
  </el-card>
</template>

<script>
import { routeByPositionId } from "@/utils/path";

export default {
  name: "RoleCard",
  props: {
    role: Object
  },
  data() {
    return {
      icon: "el-icon-s-custom",
      departmentName: "暂无部门名称",
      positionName: "默认职位",
      titleName: "无"
    };
  },
  methods: {
    handleClick() {
      console.log("Clicked");
      console.log(this.role.positionId);
      // 设置当前的角色id
      this.$store.commit("user/setCurrentRole", this.role);
      // 跳转到相应页面
      const positionId = this.role.positionId;
      this.$router.push({ path: routeByPositionId(positionId) });
    }
  },
  computed: {
    realDepartmentName() {
      if (this.departmentName !== "") return this.role.departmentName;
      else return this.departmentName;
    },
    realPositionName() {
      if (this.positionName !== "") return this.role.positionName;
      else return this.positionName;
    },
    realTitleName() {
      if (this.role.titleName !== "") return this.role.titleName;
      else return this.titleName;
    }
  }
};
</script>

<style>
.role-card {
  margin: 20px 30px;
  min-width: 250px;
  max-width: 250px;
}
.role-info {
  font-size: 16px;
}
</style>

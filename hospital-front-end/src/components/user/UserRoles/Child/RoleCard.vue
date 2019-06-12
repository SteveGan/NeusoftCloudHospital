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
    <div>
      <p class="role-info">
        <span>职称：</span>
        {{realTitleName}}
      </p>
    </div>
    <div></div>
  </el-card>
</template>

<script>
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
      var path = "";
      switch (positionId) {
        //收费员
        case 1:
          path = "/home/cashier";
          break;
        case 2:
          path = "/home/outpatientdoctor";
          break;
        case 3:
          path = "/home/techdoctor";
          break;
        case 4:
          path = "/home/drugstation";
          break;
        case 5:
          path = "/home/cashier";
          break;
        case 6:
          path = "/home/admin";
          break;
      }
      console.log(path);
      this.$router.push({ path: path });
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
}
.role-info {
  font-size: 16px;
}
</style>

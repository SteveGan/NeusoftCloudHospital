<template lang="html">
<div class="container"  style="height: 600px;">
  <!-- 科室管理功能区，做成卡片的样子 -->
  <el-card class="box-card" shadow="hover">
    <!-- 标题 -->
    <div slot="header" class="clearfix">
      <i class="el-icon-s-home"></i>
      <span style="padding-left: 20px;">新增排班规则</span>
    </div>

    <el-form :inline="true">
      <el-form-item label="科室选择">
        <el-select placeholder="请选择科室" v-model="departmentId">
          <el-option v-for="department in departments" v-bind:key="department.name"  :label="department.name" :value="department.id"></el-option>
        </el-select>
      </el-form-item>
      <el-button type="text" icon="el-icon-document-add" @click="search">查询</el-button>

      <el-form-item label="规则名称">
        <el-input placeholder="请输入规则名称" v-model="ruleName"></el-input>
      </el-form-item>
      <el-button type="text" icon="el-icon-folder-checked" @click="save">保存</el-button>
    </el-form>

    <el-table :data="roles" border style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column prop="roleName" label="医生姓名" width="180">
      </el-table-column>
      <el-table-column prop="monAm" label="星期一上午" width="80">
        <template slot-scope="scope">
          <el-checkbox v-model="scope.row.monAm"></el-checkbox>
        </template>
      </el-table-column>
      <el-table-column prop="monPm" label="星期一下午" width="80">
        <template slot-scope="scope">
          <el-checkbox v-model="scope.row.monPm"></el-checkbox>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</div>
</template>

<script>
import rule from "@/api/arrangement/rule";
import register from "@/api/register";

export default {
  name: 'ArrangentRuleAdmin',
    data() {
      return {
        registrationLevelId: "",

        departments: [],
        departmentId: "",
        roles: [],
        multipleSelection: []
      }
  },


  methods: {
    save() {
      console.log(this.multipleSelection)
    },

    handleSelectionChange(val) {
      this.multipleSelection = val;
    },

    search() {
      rule.listAllRolesByDepartmentId(this.departmentId).then(response => {
        console.log(response.data);
        var roles = response.data.data.roles;

        for(var i=0; i<roles.length; i++){
          roles[i].monAm = 0;
          roles[i].monPm = 0;
          roles[i].tueAm = 0;
          roles[i].tuePm = 0;
          roles[i].wedAm = 0;
          roles[i].wedPm = 0;
          roles[i].thuAm = 0;
          roles[i].thuPm = 0;
          roles[i].friAm = 0;
          roles[i].friPm = 0;
          roles[i].satAm = 0;
          roles[i].satPm = 0;
          roles[i].sunAm = 0;
          roles[i].sunPm = 0;
        }

        this.roles = roles;
      })
    }
  },

  mounted() {
    register
      .listAllDepartments()
      .then(response => {
        console.log(response.data);
        const data = response.data.data;
        this.departments = data;
      })
      .catch(error => {
        // alert("get error")
      });
  },

}
</script>

<style lang="css" scoped>
</style>

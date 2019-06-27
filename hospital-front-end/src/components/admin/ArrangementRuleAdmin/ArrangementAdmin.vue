<template lang="html">
<div class="container"  style="height: 100vh;">
  <!-- 科室管理功能区，做成卡片的样子 -->
  <el-card class="box-card" shadow="hover">
    <!-- 标题 -->
    <div slot="header" class="clearfix">
      <i class="el-icon-s-grid"></i>
      <span style="padding-left: 20px;">排班结果管理</span>
    </div>

    <el-form :inline="true">
      <el-form-item label="科室选择">
        <el-select placeholder="请选择科室" v-model="departmentId">
          <el-option v-for="department in departments" v-bind:key="department.name"  :label="department.name" :value="department.id"></el-option>
        </el-select>
      </el-form-item>
      <el-button type="text" icon="el-icon-document-add" @click="search">查询</el-button>

      <el-button style="float:right" type="text" icon="el-icon-folder-checked" @click="save" :loading="loading">保存</el-button>
      <el-form-item style="float:right" label="规则名称">
        <el-input placeholder="请输入规则名称" v-model="ruleName"></el-input>
      </el-form-item>
    </el-form>

    <el-table :data="roles" border style="width: 100%" @selection-change="handleSelectionChange" v-loading="loading">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column prop="roleName" label="医生姓名" width="90">
      </el-table-column>
      <el-table-column prop="monAm" label="星期一上午" width="81">
        <template slot-scope="scope">
          <el-checkbox v-model="scope.row.monAm"></el-checkbox>
        </template>
      </el-table-column>
      <el-table-column prop="monPm" label="星期一下午" width="81">
        <template slot-scope="scope">
          <el-checkbox v-model="scope.row.monPm"></el-checkbox>
        </template>
      </el-table-column>
      <el-table-column prop="tueAm" label="星期二上午" width="81">
        <template slot-scope="scope">
          <el-checkbox v-model="scope.row.tueAm"></el-checkbox>
        </template>
      </el-table-column>
      <el-table-column prop="tuePm" label="星期二下午" width="81">
        <template slot-scope="scope">
          <el-checkbox v-model="scope.row.tuePm"></el-checkbox>
        </template>
      </el-table-column>
      <el-table-column prop="wedAm" label="星期三上午" width="81">
        <template slot-scope="scope">
          <el-checkbox v-model="scope.row.wedAm"></el-checkbox>
        </template>
      </el-table-column>
      <el-table-column prop="wedPm" label="星期三下午" width="81">
        <template slot-scope="scope">
          <el-checkbox v-model="scope.row.wedPm"></el-checkbox>
        </template>
      </el-table-column>
      <el-table-column prop="thuAm" label="星期四上午" width="81">
        <template slot-scope="scope">
          <el-checkbox v-model="scope.row.thuAm"></el-checkbox>
        </template>
      </el-table-column>
      <el-table-column prop="thuPm" label="星期四下午" width="81">
        <template slot-scope="scope">
          <el-checkbox v-model="scope.row.thuPm"></el-checkbox>
        </template>
      </el-table-column>
      <el-table-column prop="friAm" label="星期五上午" width="81">
        <template slot-scope="scope">
          <el-checkbox v-model="scope.row.friAm"></el-checkbox>
        </template>
      </el-table-column>
      <el-table-column prop="friPm" label="星期五下午" width="81">
        <template slot-scope="scope">
          <el-checkbox v-model="scope.row.friPm"></el-checkbox>
        </template>
      </el-table-column>
      <el-table-column prop="satAm" label="星期六上午" width="81">
        <template slot-scope="scope">
          <el-checkbox v-model="scope.row.satAm"></el-checkbox>
        </template>
      </el-table-column>
      <el-table-column prop="satPm" label="星期六下午" width="81">
        <template slot-scope="scope">
          <el-checkbox v-model="scope.row.satPm"></el-checkbox>
        </template>
      </el-table-column>
      <el-table-column prop="sunAm" label="星期日上午" width="81">
        <template slot-scope="scope">
          <el-checkbox v-model="scope.row.sunAm"></el-checkbox>
        </template>
      </el-table-column>
      <el-table-column prop="sunPm" label="星期日下午" width="81">
        <template slot-scope="scope">
          <el-checkbox v-model="scope.row.sunPm"></el-checkbox>
        </template>
      </el-table-column>                                          

    </el-table>
  </el-card>
</div>
</template>

<script>
import rule from "@/api/arrangement/rule";
import register from "@/api/register";
import { successDialog, failDialog } from "@/utils/notification";

export default {
  name: 'ArrangentAdmin',
    data() {
      return {
        currentRoleId: "",
      }
  },


  methods: {

  },

  mounted() {
    // 初始化操作员id
    const currentRoleId = this.$store.getters["user/currentRoleId"];
    this.currentRoleId = currentRoleId;
  },

}
</script>

<style lang="css" scoped>
</style>

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
      <el-form-item label="科室选择" required>
        <el-select placeholder="请选择科室" v-model="departmentId">
          <el-option v-for="department in departments" v-bind:key="department.name"  :label="department.name" :value="department.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="起始时间" required>
          <el-date-picker type="date" value-format="yyyy-MM-dd" v-model="startDate" placeholder="选择起始时间" class="date-selection"></el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间" required>
          <el-date-picker type="date" value-format="yyyy-MM-dd" v-model="endDate" placeholder="选择结束时间" class="date-selection"></el-date-picker>
      </el-form-item>
      <el-button type="text" :disabled="departmentId==''||startDate==''||endDate==''" icon="el-icon-document-add" @click="search">查询</el-button>
    </el-form>

    <el-calendar :range="dateRange" v-if="ifCalendarShow">                         
      <template slot="dateCell" slot-scope="{date, data}">
        <p :class="data.isSelected ? 'is-selected' : ''">
          {{ data.day.split('-').slice(1).join('-') }} {{ data.isSelected ? '✔️' : ''}}
          {{ }}
        </p>
      </template>
    </el-calendar> 
  </el-card>
</div>
</template>

<script>
import rule from "@/api/arrangement/rule";
import register from "@/api/register";
import { successDialog, failDialog } from "@/utils/notification";

export default {
  name: 'ArrangementAdmin',
    data() {
      return {
        currentRoleId: "",

        departments: [],
        departmentId: "",

        startDate: "",
        endDate: "",
        dateRange: [],
        ifCalendarShow: false,
        arrangements: []
      }
  },

  methods: {

    search(){
      this.ifCalendarShow = true
      var result = []
      result.push(this.startDate)
      result.push(this.endDate)
      this.dateRange = result;

      rule.listArrangements(this.startDate, this.endDate, this.departmentId).then(response => {
        console.log(response.data);
        const data = response.data.data;
        this.arrangements = data;
      })
    }

  },

  mounted() {
    // 初始化操作员id
    const currentRoleId = this.$store.getters["user/currentRoleId"];
    this.currentRoleId = currentRoleId;

    // 读取部门
    register.listAllDepartments().then(response => {
      console.log(response.data);
      const data = response.data.data;
      this.departments = data;
    })
  },

}
</script>

<style lang="css" scoped>
.is-selected {
    color: #1989FA;
  }
</style>

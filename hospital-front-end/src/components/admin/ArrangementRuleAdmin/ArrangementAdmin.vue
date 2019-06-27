<template lang="html">
<div class="container" style="height: 100vh; margin: 20px 20px 0 20px;">
  <!-- 科室管理功能区，做成卡片的样子 -->
  <el-card class="box-card" shadow="hover" v-loading="loading1">
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
          <el-date-picker :picker-options="pickerOptions1" type="date" value-format="yyyy-MM-dd" v-model="startDate" placeholder="选择起始时间" class="date-selection"></el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间" required>
          <el-date-picker :picker-options="pickerOptions2" type="date" value-format="yyyy-MM-dd" v-model="endDate" placeholder="选择结束时间" class="date-selection"></el-date-picker>
      </el-form-item>
      <el-button type="text" :loading="loading1" :disabled="departmentId==''||startDate==''||endDate==''" icon="el-icon-document-add" @click="search">查询</el-button>
    </el-form>
  </el-card>

  <el-card class="box-card" shadow="hover" style="margin-top: 20px;">
    <!-- 标题 -->
    <div slot="header" class="clearfix">
      <i class="el-icon-document"></i>
      <span style="padding-left: 20px;">排班日程</span>
    </div>
    <el-calendar :range="dateRange" v-if="ifCalendarShow">                         
      <template slot="dateCell" slot-scope="{date, data}">
        <p :class="arrangements[data.day].length>0 ? 'is-selected' : ''">
          {{ data.day.split('-').slice(1).join('-') }} {{ arrangements[data.day].length>0 ? '✔️' : ''}}
          <el-button v-if="arrangements[data.day].length>0" type="text" icon="el-icon-document-add" @click="seemore(arrangements[data.day])">详情</el-button>
        </p>
      </template>
    </el-calendar>
  </el-card>

  <el-card class="box-card" shadow="hover" style="margin-top: 20px;">
    <!-- 标题 -->
    <div slot="header" class="clearfix">
      <i class="el-icon-paperclip"></i>
      <span style="padding-left: 20px;">详情浏览</span>
    </div>
    <el-table :data="arrangement" style="width: 100%">
      <el-table-column type="index" width="50"></el-table-column>
      <el-table-column prop="userName" label="医生姓名"></el-table-column>
      <el-table-column prop="appointmentLeft" label="剩余号量"></el-table-column>
      <el-table-column prop="maxAppointment" label="挂号上限"></el-table-column>
      <el-table-column prop="registrationLevel" label="挂号级别"></el-table-column>
      <el-table-column prop="timeSlot" label="出诊时段"></el-table-column>
    </el-table>
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
        arrangements: {},
        arrangement: [],

        loading1: false,

        pickerOptions1: { 
         disabledDate(time) {
          var a = new Array("日", "一", "二", "三", "四", "五", "六");  
          var week = new Date(time).getDay();  
          var str = a[week];
            return str != "一";
         }
        },

        pickerOptions2: { 
         disabledDate(time) {
          var a = new Array("日", "一", "二", "三", "四", "五", "六");  
          var week = new Date(time).getDay();  
          var str = a[week];
            return str != "日";
         }
        }
      }
  },

  computed: {
    departmentName(departmentId) {
        for(var i=0; i<this.departments.length; i++){
          if (this.departments[i].id == departmentId) {
            return this.departments[i].name
          }
        }
      }
  },

  methods: {
    seemore(arrangement) {
      this.arrangement = arrangement;
    },

    search(){
      this.loading1 = true;
      this.ifCalendarShow = true
      var result = []
      result.push(this.startDate)
      result.push(this.endDate)
      this.dateRange = result;

      rule.listArrangements(this.startDate, this.endDate, this.departmentId).then(response => {
        console.log(response.data);
        const data = response.data.data;
        this.arrangements = data;
        this.loading1 = false;
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

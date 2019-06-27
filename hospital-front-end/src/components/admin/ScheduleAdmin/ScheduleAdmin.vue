<template lang="html">
<div class="container"  style="height: 100vh;">
  <!-- 科室管理功能区，做成卡片的样子 -->
  <el-card class="box-card" shadow="hover">
    <!-- 标题 -->
    <div slot="header" class="clearfix">
      <i class="el-icon-document-checked"></i>
      <span style="padding-left: 20px;">生成排班计划</span>
    </div>

    <el-form :inline="true">
      <el-form-item label="科室选择">
        <el-select placeholder="请选择科室" v-model="departmentId">
          <el-option v-for="department in departments" v-bind:key="department.name"  :label="department.name" :value="department.id"></el-option>
        </el-select>
      </el-form-item>
      <el-button type="text" icon="el-icon-document-add" @click="search">查询</el-button>

      <el-button style="float:right" type="text" icon="el-icon-folder-checked" @click="generate">生成</el-button>
      <el-form-item style="float:right" label="结束时间">
          <el-date-picker type="date" value-format="yyyy-MM-dd" v-model="endDate" placeholder="选择结束时间" class="date-selection"></el-date-picker>
      </el-form-item>
      <el-form-item style="float:right" label="起始时间">
          <el-date-picker type="date" value-format="yyyy-MM-dd" v-model="startDate" placeholder="选择起始时间" class="date-selection"></el-date-picker>
      </el-form-item>
    </el-form>

    <el-table :data="rules" border style="width: 100%" highlight-current-row @current-change="handleCurrentChange">
    <el-table-column type="expand">
      <template slot-scope="props">
        <el-table :data="props.row.arrangementRule" border style="width: 100%">
          <el-table-column type="index" width="50">
          </el-table-column>
          <el-table-column prop="name" label="医生姓名">
          </el-table-column>
          <el-table-column prop="ruleTime" label="排班时间">
          </el-table-column>
          <el-table-column prop="maxAppointment" label="预约上限">
          </el-table-column>
          <el-table-column prop="registrationLevelId" label="挂号级别">
          </el-table-column>
          <el-table-column prop="titleId" label="职称">
          </el-table-column>
        </el-table>
      </template>
    </el-table-column>
      <el-table-column prop="ruleId" label="规则ID">
      </el-table-column>
      <el-table-column prop="ruleName" label="规则名称">
      </el-table-column>
      <el-table-column prop="adminId" label="操作员">
      </el-table-column>
    </el-table>
  </el-card>
</div>
</template>

<script>
import rule from "@/api/arrangement/rule";
import register from "@/api/register";

export default {
  name: 'ScheduleAdmin',
  data() {
    return {
      departments: [],
      departmentId: "",

      startDate: "",
      endDate: "",

      rules: [],
      id: 1,
      currentRow: {}
    }
  },

  methods: {
    generate(){
      var arrangementParam = {};
      arrangementParam.startDate = this.startDate;
      arrangementParam.endDate = this.endDate;
      arrangementParam.departmentId = this.departmentId;
      arrangementParam.id = this.currentRow.ruleId;
      rule.insertArrangement(arrangementParam).then(response => {
        console.log(response.data);
         this.success("排班计划生成");
      })
    },

    search() {
      rule.listArrangementRules(this.departmentId).then(response => {
        console.log(response.data);
        const rules = response.data.data.arrangementRules;

        this.success("搜索");
        this.rules = rules;
      })
    },
      // 成功提示
      success(msg) {
        this.$message({
          message: msg+'成功',
          type: 'success'
        });
      },
      
      // 失败提示
      fail(msg) {
        this.$message.error(msg+'失败');
      },
      handleCurrentChange(val) {
        this.currentRow = val;
      }
  },

  mounted() {
    register.listAllDepartments().then(response => {
      console.log(response.data);
      const data = response.data.data;
      this.departments = data;
    })
  },
}
</script>

<style lang="css" scoped>
</style>

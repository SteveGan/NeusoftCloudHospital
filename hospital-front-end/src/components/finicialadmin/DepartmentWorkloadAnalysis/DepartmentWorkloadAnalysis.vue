<template lang="html">
  <div>
    <el-card style="margin: 5px 4px;s" shadow="hover">
      <div slot="header">
        <i class="el-icon-search"></i>
        <span>检索条件</span>
      </div>
      <el-form :inline="true">
          <el-form-item label="起始时间">
              <el-date-picker type="datetime" value-format="yyyy-MM-dd hh:mm:ss" v-model="startDate" placeholder="选择起始时间" class="date-selection"></el-date-picker>
          </el-form-item>
          <el-form-item label="结束时间">
              <el-date-picker type="datetime" value-format="yyyy-MM-dd hh:mm:ss" v-model="endDate" placeholder="选择结束时间" class="date-selection"></el-date-picker>
          </el-form-item>
          <el-button type="text" icon="el-icon-document-add" :loading="loading" @click="search">查询</el-button>
      </el-form>
    </el-card>

    <el-card style="margin: 5px 4px;s" shadow="hover">
      <div slot="header">
        <i class="el-icon-data-analysis"></i>
        <span>门诊科室工作量统计（开单科室）</span>
      </div> 
      <el-table :data="clinicianDepartment" style="width: 100%" v-loading="loading">
          <el-table-column type="index" width="50">
          </el-table-column>
          <el-table-column prop="departmentName" label="科室名称">
          </el-table-column>
          <el-table-column prop="visits" label="看诊人次">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.visits==null?"0":scope.row.visits }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="invoiceAmount" label="发票数量">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.invoiceAmount==null?"0":scope.row.invoiceAmount }}</span>
            </template>            
          </el-table-column>
          <el-table-column prop="检查费" label="检查费">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.检查费==null?"0.00":scope.row.检查费 }}</span>
            </template>            
          </el-table-column>
          <el-table-column prop="检验费" label="检验费">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.检验费==null?"0.00":scope.row.检验费 }}</span>
            </template>            
          </el-table-column>
          <el-table-column prop="成药" label="成药费">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.成药==null?"0.00":scope.row.成药 }}</span>
            </template>            
          </el-table-column>
          <el-table-column prop="处置" label="处置费">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.处置==null?"0.00":scope.row.处置 }}</span>
            </template>            
          </el-table-column>
      </el-table>
    </el-card>

    <el-card style="margin: 5px 4px;s" shadow="hover">
      <div slot="header">
        <i class="el-icon-data-line"></i>
        <span>门诊科室工作量统计（执行科室）</span>
      </div> 
      <el-table :data="technicianDepartment" style="width: 100%" v-loading="loading">
          <el-table-column type="index" width="50">
          </el-table-column>        
          <el-table-column prop="departmentName" label="科室名称">
          </el-table-column>
          <el-table-column prop="visits" label="看诊人次">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.visits==null?"0":scope.row.visits }}</span>
            </template>            
          </el-table-column>
          <el-table-column prop="invoiceAmount" label="发票数量">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.invoiceAmount==null?"0":scope.row.invoiceAmount }}</span>
            </template>            
          </el-table-column>
          <el-table-column prop="检查费" label="检查费">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.检查费==null?"0.00":scope.row.检查费 }}</span>
            </template>            
          </el-table-column>
          <el-table-column prop="检验费" label="检验费">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.检验费==null?"0.00":scope.row.检验费 }}</span>
            </template>            
          </el-table-column>
          <el-table-column prop="成药" label="成药费">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.成药==null?"0.00":scope.row.成药 }}</span>
            </template>            
          </el-table-column>
          <el-table-column prop="处置" label="处置费">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.处置==null?"0.00":scope.row.处置 }}</span>
            </template>            
          </el-table-column>
      </el-table>
    </el-card>    
  </div>
</template>

<script>
import statistics from '@/api/finicial/statistics'
import { successDialog, failDialog } from "@/utils/notification";

export default {
  name: 'DepartmentWorkloadAnalysis',

  data() {
    return{
      startDate: "",
      endDate: "",

      clinicianDepartment: [],
      technicianDepartment: [],
      loading: false
    }
  },

  methods: {
    search() {
      this.loading = true;
      statistics.clinicianDepartmentStatistics(this.startDate, this.endDate).then(
        response => {
          const data = response.data.data;
          this.clinicianDepartment = data;
          console.log(this.clinicianDepartment);
        }
      ),

      statistics.technicianDepartmentStatistics(this.startDate, this.endDate).then(
        response => {
          this.loading = false;
          const data = response.data.data
          this.technicianDepartment = data
          console.log(this.technicianDepartment)

          successDialog("查询成功");
      },
        error => {
          this.loading = false;
          failDialog("[查询失败]" + error.data.data.message + "(" + error.data.data.code + ")");
      })
    },
  },

}
</script>

<style lang="css" scoped>
</style>

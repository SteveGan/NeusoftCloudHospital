<template lang="html">
  <div>
    <el-card style="margin: 5px 4px;s" shadow="hover">
      <div slot="header">
          <span>检索条件</span>
      </div>
      <el-form :inline="true">
          <el-form-item label="起始时间">
              <el-date-picker type="datetime" value-format="yyyy-MM-dd hh:mm:ss" v-model="startDate" placeholder="选择起始时间" class="date-selection"></el-date-picker>
          </el-form-item>
          <el-form-item label="结束时间">
              <el-date-picker type="datetime" value-format="yyyy-MM-dd hh:mm:ss" v-model="endDate" :picker-options="pickerOptions" placeholder="选择结束时间" class="date-selection"></el-date-picker>
          </el-form-item>
          <el-button type="text" icon="el-icon-document-add" @click="search">查询</el-button>
      </el-form>
    </el-card>

    <el-card style="margin: 5px 4px;s" shadow="hover">
      <div slot="header">
        <span>门诊科室工作量统计（开单科室）</span>
      </div> 
      <el-table :data="clinicianDepartment" style="width: 100%">
          <el-table-column type="index" width="50">
          </el-table-column>
          <el-table-column prop="departmentName" label="科室名称">
          </el-table-column>
          <el-table-column prop="visits" label="看诊人次">
          </el-table-column>
          <el-table-column prop="invoiceAmount" label="发票数量">
          </el-table-column>
          <el-table-column prop="检查费" label="检查费">
          </el-table-column>
          <el-table-column prop="检验费" label="检验费">
          </el-table-column>
          <el-table-column prop="成药" label="成药费">
          </el-table-column>
          <el-table-column prop="处置" label="处置费">
          </el-table-column>
      </el-table>
    </el-card>

    <el-card style="margin: 5px 4px;s" shadow="hover">
      <div slot="header">
        <span>门诊科室工作量统计（执行科室）</span>
      </div> 
      <el-table :data="technicianDepartment" style="width: 100%">
          <el-table-column type="index" width="50">
          </el-table-column>        
          <el-table-column prop="departmentName" label="科室名称">
          </el-table-column>
          <el-table-column prop="visits" label="看诊人次">
          </el-table-column>
          <el-table-column prop="invoiceAmount" label="发票数量">
          </el-table-column>
          <el-table-column prop="检查费" label="检查费">
          </el-table-column>
          <el-table-column prop="检验费" label="检验费">
          </el-table-column>
          <el-table-column prop="成药" label="成药费">
          </el-table-column>
          <el-table-column prop="处置" label="处置费">
          </el-table-column>
      </el-table>
    </el-card>    
  </div>
</template>

<script>
import statistics from '@/api/finicial/statistics'
export default {
  name: 'DepartmentWorkloadAnalysis',

  data() {
    return{
      startDate: "",
      endDate: "",

      clinicianDepartment: [],
      technicianDepartment: []
    }
  },

  methods: {
    search() {
      statistics.clinicianDepartmentStatistics(this.startDate, this.endDate).then(response => {
        const data = response.data.data;
        this.clinicianDepartment = data
        console.log(this.clinicianDepartment)
        
        if(response.data.code===200){
            this.success("查询");
        } else {
            this.fail("查询");
        }
      }),

      statistics.technicianDepartmentStatistics(this.startDate, this.endDate).then(response => {
        const data = response.data.data
        this.technicianDepartment = data
        console.log(this.technicianDepartment)
      })
    },

    // 成功提示
    success(msg) {
      this.$message({
        message: msg + "成功",
        type: "success"
      });
    },

    // 失败提示
    fail(msg) {
      this.$message.error(msg + "失败");
    },
  },

}
</script>

<style lang="css" scoped>
</style>

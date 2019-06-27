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
              <el-date-picker type="datetime" value-format="yyyy-MM-dd hh:mm:ss" v-model="endDate" splaceholder="选择结束时间" class="date-selection"></el-date-picker>
          </el-form-item>
          <el-button type="text" icon="el-icon-document-add" :loading="loading" @click="search">查询</el-button>
      </el-form>
    </el-card>

    <el-card style="margin: 5px 4px;s" shadow="hover">
      <div slot="header">
        <span>门诊医生工作量统计</span>
      </div> 
      <el-table :data="clinicianDoctor" style="width: 100%" v-loading="loading" highlight-current-row @current-change="handleCurrentChange">
          <el-table-column type="index" width="50">
          </el-table-column>
          <el-table-column prop="userName" label="医生姓名">
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
          <el-table-column prop="挂号费" label="挂号费">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.挂号费==null?"0.00":scope.row.挂号费 }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="检验费" label="检验费">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.检验费==null?"0.00":scope.row.检验费 }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="检验材料费" label="检验材料费">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.检验材料费==null?"0.00":scope.row.检验材料费 }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="超声检查费" label="超声检查费">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.超声检查费==null?"0.00":scope.row.超声检查费 }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="超声材料费" label="超声材料费">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.超声材料费==null?"0.00":scope.row.超声材料费 }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="放射检查费" label="放射检查费">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.放射检查费==null?"0.00":scope.row.放射检查费 }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="放射材料费" label="放射材料费">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.放射材料费==null?"0.00":scope.row.放射材料费 }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="西药费" label="西药费">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.西药费==null?"0.00":scope.row.西药费 }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="中成药费" label="中成药费">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.中成药费==null?"0.00":scope.row.中成药费 }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="中草药费" label="中草药费">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.中草药费==null?"0.00":scope.row.中草药费 }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="处置费" label="处置费">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.处置费==null?"0.00":scope.row.处置费 }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="处置材料费" label="处置材料费">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.处置材料费==null?"0.00":scope.row.处置材料费 }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="检查费" label="检查费">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.检查费==null?"0.00":scope.row.检查费 }}</span>
            </template>
          </el-table-column>
      </el-table>
    </el-card>

    <el-card style="margin: 5px 4px;s" shadow="hover" v-if="showCharts">
      <div slot="header">
        <span>工作量详情</span>
      </div> 
      <div id="myChart" :style="{width: '300px', height: '300px'}"></div>
    </el-card>  

  </div>
</template>

<script>
import statistics from '@/api/finicial/statistics';
import { successDialog, failDialog } from "@/utils/notification";
import echarts from 'echarts';

export default {
  name: 'UserWorkloadAnalysis',

  data() {
    return{
      startDate: "",
      endDate: "",
      clinicianDoctor: [],
      loading: false,
      showCharts: true
    }
  },

  methods: {
    search() {
      this.loading = true;
      statistics.clinicianDoctorStatistics(this.startDate, this.endDate).then(
        response => {
          this.loading = false;
          const data = response.data.data;
          this.clinicianDoctor = data
          console.log(this.clinicianDepartment)
          
          successDialog("查询成功");
        },
        error => {
          this.loading = false;
          failDialog("[查询失败]" + error.data.data.message + "(" + error.data.data.code + ")");
        }
      )
    },

    handleCurrentChange(val) {
      this.showCharts = true;
      console.log(val);
      this.drawChart(val);
    },

    drawChart(val) {
      // 基于准备好的dom，初始化echarts实例
      let myChart = this.$echarts.init(document.getElementById('myChart'))
      // 绘制图表
      myChart.setOption({
      title : {
        text: val.userName + '工作量详情',
        subtext: val.userName,
        x:'center'
      },
      tooltip : {
          trigger: 'item',
          formatter: "{a} <br/>{b} : {c} ({d}%)"
      },
      series : [
          {
              name: '费用占比',
              type: 'pie',
              radius : '55%',
              center: ['50%', '60%'],
              data:[
                  {value:val.挂号费, name:'挂号费'},
                  {value:val.检验费, name:'检验费'},
                  {value:val.检验材料费, name:'检验材料费'},
                  {value:val.超声检查费, name:'超声检查费'},
                  {value:val.超声材料费, name:'超声材料费'},
                  {value:val.放射检查费, name:'放射检查费'},
                  {value:val.放射材料费, name:'放射材料费'},
                  {value:val.西药费, name:'西药费'},
                  {value:val.中成药费, name:'中成药费'},
                  {value:val.中草药费, name:'中草药费'},
                  {value:val.处置费, name:'处置费'},
                  {value:val.处置材料费, name:'处置材料费'},
                  {value:val.检查费, name:'检查费'},
              ],
              itemStyle: {
                  emphasis: {
                      shadowBlur: 10,
                      shadowOffsetX: 0,
                      shadowColor: 'rgba(0, 0, 0, 0.5)'
                  }
              }
          }
      ]
      });
    }
  },
}
</script>

<style lang="css" scoped>
</style>

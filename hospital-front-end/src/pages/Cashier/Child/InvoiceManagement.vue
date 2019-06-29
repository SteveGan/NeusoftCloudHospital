<template lang="html">
  <div class="toll-admin">
    <div class="search-board">
      <el-card shadow="hover" class="">
            <div slot="header">
                <i class="el-icon-paperclip"></i>
                <span>发票使用情况</span>
            </div>
            <div id="myChart" :style="{width: '300px', height: '300px'}"></div>
      </el-card>

    </div>

    <div class="edit-board">
      <el-card shadow="hover">
        <div slot="header">
          <i class="el-icon-edit"></i>
          <span>导入发票</span>
        </div>

        <el-form :inline="true">
            <el-form-item label="起始号段">
                <el-input placeholder="请输入起始号段" v-model="startCode" clearable></el-input>
            </el-form-item>
            <el-form-item label="结束号段">
                <el-input placeholder="请输入结束号段" v-model="endCode" clearable></el-input>
            </el-form-item>
            <el-button type="text" :loading="loading1" icon="el-icon-document-add" @click="insertInvoice">导入</el-button>
        </el-form>
      </el-card>

      <el-card shadow="hover" style="margin: 20px 0;">
        <div slot="header">
          <i class="el-icon-tickets"></i>
          <span>发票一览</span>
        </div>

        <el-table :data="showedInvoices" stripe style="width: 100%">
            <el-table-column type="index" width="50"></el-table-column>
            <el-table-column prop="id" label="发票号"></el-table-column>
            <el-table-column prop="status" label="发票状态"></el-table-column>
        </el-table>
        <div class="block">
            <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="currentPage"
              :page-sizes="[10, 30, 50, 100]"
              :page-size="currentSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="invoiceList.length">
            </el-pagination>
          </div>
      </el-card>
    </div>

  </div>
</template>

<script>
import { successDialog, failDialog } from "@/utils/notification";
import invoice from '@/api/invoice'

export default {
  name: 'TollAdmin',
  data() {
    return{
        startCode: "",
        endCode: "",

        invoiceInfo: [],
        invoiceList: [],

        loading1: false,
      showedInvoices: [],
      currentPage: 1,
      currentSize: 10
    }
  },

  methods: {
    insertInvoice() {
        this.loading1 = true;
        invoice.insertInvoices(this.startCode, this.endCode).then(
        response => {
        console.log(response.data.data);
        
        this.loading1 = false;
        successDialog("导入成功");
        this.getInvoiceInfo();
      }, error => {
        this.loading1 = false;
        failDialog("[导入失败]" + error.data.data.message + "(" + error.data.data.code + ")");
      })
    },
    handleSizeChange(val) {
    console.log(`每页 ${val} 条`);
    this.currentSize=val;
    this.setShowedInvoices();
    },
    handleCurrentChange(val) {
    console.log(`当前页: ${val}`);
    this.currentPage=val;
    this.setShowedInvoices();
    },
    drawChart(val) {
      // 基于准备好的dom，初始化echarts实例
      let myChart = this.$echarts.init(document.getElementById('myChart'))
      // 绘制图表
      myChart.setOption({
      title : {
        text: '发票使用状态分布',
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
              center: ['50%', '50%'],
              data:[
                  {value:val.未使用, name:'未使用'},
                  {value:val.使用中, name:'使用中'},
                  {value:val.已使用, name:'已使用'},
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
    },

    getInvoiceInfo() {
        invoice.invoiceInfo().then(
            response => {
                console.log(response.data.data);
                this.invoiceInfo = response.data.data;
                this.drawChart(this.invoiceInfo);
            }
        ),

        invoice.listInvoice().then(
            response => {
                console.log(response.data.data);
                this.invoiceList = response.data.data;
                this.setShowedInvoices();
            }
        )
    },

    setShowedInvoices() {
        console.log(this.currentPage);
        this.showedInvoices = this.invoiceList.slice((this.currentPage-1)*this.currentSize, (this.currentPage-1)*this.currentSize+this.currentSize);
     }
  },

  mounted() {
    this.getInvoiceInfo();
  }

}
</script>

<style lang="css" scoped>
.toll-admin{
  display: flex;
  flex-direction: row;
}

.search-board{
  width: 400px;
  margin: 0px 30px 30px 25px;
}

.edit-board{
    width: 960px;
    margin-right: 30px;
}
</style>
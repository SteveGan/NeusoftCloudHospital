<template lang="html">
  <div class="toll-admin">
    <!-- 左侧进行病历筛选 以及 展示基本信息用于确认 -->
    <div class="search-board">
      <!-- 患者选择 + 基本信息展示 -->
      <el-card shadow="hover">
        <div slot="header">
          <span>查询缴费记录</span>
        </div>
        <!-- 主体区域 -->
        <div>
          <!-- 搜索栏 -->
          <div class="search-bar">
            <el-input placeholder="搜索内容" class="input-with-select" v-model="input" style="width: 350px;">
              <el-select slot="prepend" placeholder="病历号" class="select-box" style="width: 100px;">
                <el-option label="病历号" value="0"></el-option>
                <el-option label="用户ID" value="1"></el-option>
              </el-select>
              <el-button slot="append" icon="el-icon-search" @click="getpaymentInfo"></el-button>
            </el-input>
          </div>
          <!-- 患者基本信息 -->
          <div class="patient-info">
            <el-form ref="form" label-width="80px" label-position="left" v-loading="loading1">
              <el-form-item label="姓名" :disabled="true">
                <el-input v-model="patientInfo.name"></el-input>
              </el-form-item>
              <el-form-item label="身份证号">
                <el-input v-model="patientInfo.idCard"></el-input>
              </el-form-item>
              <el-form-item label="性别" :disabled="true">
                <el-input v-model="patientInfo.gender"></el-input>
              </el-form-item>
              <el-form-item label="地址" :disabled="true">
                <el-input v-model="patientInfo.address"></el-input>
              </el-form-item>
              <el-form-item label="出生日期" :disabled="true">
                <el-input v-model="patientInfo.birthday"></el-input>
              </el-form-item>
            </el-form>
          </div>
        </div>
      </el-card>

      <el-card shadow="hover" style="margin-top: 30px">
        <div slot="header">
          <span>发票清单</span>
        </div>

        <el-table :data="invoiceCollection" stripe style="width: 100%">
          <el-table-column type="index" label="序号">
          </el-table-column>          
          <el-table-column prop="invoiceCode" label="发票号" style="width: 100%">
          </el-table-column>
          <el-table-column prop="status" label="状态" style="width: 60%">
          </el-table-column>
          <el-table-column fixed="right" label="操作" width="100">
            <template slot-scope="scope">
              <el-button @click="print(scope.row)" type="text" size="small" :disabled="scope.row.status!=2&&scope.row.status!=4">补打</el-button>
              <el-button @click="reprint(scope.row)" type="text" :disabled="scope.row.status!=2" size="small">重打</el-button>
            </template>
          </el-table-column>
        </el-table>      
      </el-card>
    </div>
    <!-- 右侧显示列表，展示已交费和未交费项目 -->
    <div class="edit-board" >
      <el-card shadow="hover" style="margin: 0 30px 30px 0;">
        <div slot="header">
          <i class="el-icon-news"></i>
          <span>待缴费清单</span>
        </div>
        <!-- 操作按钮 -->
        <div class="action-bar">
          <el-button type="text" icon="el-icon-success" @click="pay">结算</el-button>
        </div>
        <!-- 缴费项目表 -->
        <div class="列表">
          <el-table v-loading="loading2" :data="chargeItems" @selection-change="handleChargeSelectionChange">
            <el-table-column type="selection" width="55">
            </el-table-column>
            <!-- <el-table-column label="发票号" prop="invoiceCode" width="110">
            </el-table-column> -->
            <el-table-column label="项目类型" prop="type">
            </el-table-column>
            <el-table-column label="项目编号" prop="collectionId">
            </el-table-column>
            <el-table-column label="数量" prop="amount">
            </el-table-column>
            <el-table-column label="金额" prop="totalMoney">
            </el-table-column>
            <el-table-column label="开立状态" prop="itemStatus">
            </el-table-column>
            <el-table-column label="执行科室" prop="departmentId">
            </el-table-column>
          </el-table>
        </div>
      </el-card>

      <el-card shadow="hover" style="margin: 30px 30px 30px 0;">
        <div slot="header">
          <i class="el-icon-finished"></i>
          <span>已缴费清单</span>
        </div>
        <!-- 主体区域 -->
          <!-- 操作按钮 -->
        <div class="action-bar">
          <el-button type="text" icon="el-icon-success" @click="withdraw">退费</el-button>
        </div>
        <!-- 退费项目表 -->
        <div class="列表">
          <el-table :data="withdrawItems" @selection-change="handleWithdrawSelectionChange">
            <el-table-column type="selection" width="55">
            </el-table-column>
            <el-table-column label="发票号" prop="type" width="110">
            </el-table-column>
            <el-table-column label="项目类型" prop="collectionId" width="80">
            </el-table-column>
            <el-table-column label="项目编号" prop="collectionId" width="80">
            </el-table-column>
            <el-table-column label="可退数量" prop="remainAmount" width="80">
            </el-table-column>
            <el-table-column label="金额" prop="totalMoney" width="80">
            </el-table-column>
            <!-- <el-table-column label="开立时间" prop="gmtCreate">
            </el-table-column> -->
            <el-table-column label="开立状态" prop="itemStatus" width="80">
            </el-table-column>
            <el-table-column label="执行科室" prop="departmentId" width="80">
            </el-table-column>
            <el-table-column label="退费数量" fixed="right" width="200">
              <template slot-scope="scope">
                <el-input-number v-model="scope.row.returnAmount" :disabled="scope.row.projectStatus==5||scope.row.type=='检查费'||scope.row.type=='检验费'" :min=1 :max="scope.row.amount" label="描述文字"></el-input-number>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-card>
    </div>

    <el-dialog title="缴费确认" :visible.sync="dialogTableVisible">
      <el-form>
        <el-form-item label="应付总额" :label-width="formLabelWidth">
          <el-input v-model="shouldPay" autocomplete="off" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="实付总额" :label-width="formLabelWidth">
          <el-input v-model="realPay" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="找零" :label-width="formLabelWidth">
          <el-input v-model="balance" autocomplete="off" :disabled="true"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogTableVisible = false">取 消</el-button>
        <el-button type="primary" @click="charge">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="发票打印" :visible.sync="invoicePrinterVisible" width="70%">
      <div id="printContent">
        <div>
          <h3 style="text-align:center;">熙康云医院 · 发票单</h3>
          <h6 style="text-align:center;margin-top: -12px;">地址:沈阳市浑南区创新路195号&nbsp;&nbsp;&nbsp;门诊部:024-88886666</h6>
          <hr style="width: 630px;text-align:center;" />
          <h5 style="text-align:left;margin-left: 200px;">
            结算日期:{{this.today}}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            病历号:{{this.input}}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            发票号:{{this.chargeInvoice}}
          </h5>

          <div style="margin: 0 0 30px 200px;">
              <el-table :data="tempData" border style="width: 70%">
                <el-table-column type="index" width="50"></el-table-column>
                <el-table-column label="项目类型" prop="type">
                </el-table-column>
                <el-table-column label="项目编号" prop="collectionId">
                </el-table-column>
                <el-table-column label="数量" prop="amount">
                </el-table-column>
                <el-table-column label="金额" prop="totalMoney">
                </el-table-column>
                <el-table-column label="执行科室" prop="departmentId">
                </el-table-column>
              </el-table>
          </div>

          <el-form :inline="true" class="demo-form-inline" style="margin: 0 0 30px 200px;">
            <el-form-item label="应付金额">
              <el-input v-model="shouldPay"></el-input>
            </el-form-item>
            <el-form-item label="收费员">
              <el-input v-model="currentRoleId"></el-input>
            </el-form-item>
          </el-form>
        </div>
        <button class="btn no-print" v-print="'#printContent'">打印</button>
      </div>

    </el-dialog>
  </div>
</template>

<script>
import charge from '@/api/charge'
import { successDialog, failDialog } from "@/utils/notification";

export default {
  name: 'TollAdmin',
  data() {
    return{
      // 缴费对话框
      dialogTableVisible: false,
      invoicePrinterVisible: false,
      tempData: [],

      formLabelWidth: '120px',
      shouldPay: 0,
      realPay: 0,

      input: "",

      patientInfo: {},
      invoiceCollection: [],
      transactionLogs: {},
      chargeItems: [],
      withdrawItems: [],

      chargeSelection: [],
      withdrawSelection: [],
      num: 0,

      // 当前操作员
      currentRoleId: "",

      // 返回的发票号
      chargeInvoice: 0,

      //常量表
      constant: {},
      jiaofeiStatus: {
        1: "未缴费",
        2: "已缴费",
        3: "已退费",
        4: "冲正",
        5: "作废",
        6: "冻结"
      },
      chufangStatus: {
        1: "暂存",
        2: "开立",
        3: "作废",
        4: "已登记",
        5: "已退药/执行完毕"
      },

      loading1: false,
      loading2: false,
    }
  },

  computed: {
    usedTime(gmtCreate){
      return gmtCreate.substr(0,9);
    },

    balance() {
      return (this.realPay - this.shouldPay).toFixed(2);
    }
  },

  mounted(){
    const currentRoleId = this.$store.getters['user/currentRoleId'];
    this.currentRoleId = currentRoleId;

    // 默认设置看诊日期为今天
    this.today = this.getNowFormatDate();
  },

  methods: {

    // 发票补打
    print(row) {
      console.warn(row.invoiceCode)
      charge.print(row.invoiceCode).then(
        response => {
          const data = response.data.data
          console.log(data);
          this.tempData = data;
          this.chargeInvoice = row.invoiceCode;
          this.invoicePrinterVisible = true;
          successDialog("补打成功");
      }, error => {
          failDialog("[补打失败]" + error.data.data.message + "(" + error.data.data.code + ")");
      })      
    },

    // 发票重打
    reprint(row){
      console.info(row)
      console.warn(row.invoiceCode)
      charge.reprint(row.invoiceCode).then(
        response => {
          const data = response.data.data
          console.log(data);
          this.tempData = row.logs;
          this.chargeInvoice = data;
          this.invoicePrinterVisible = true;
          successDialog("重打成功");
      }, error => {
          failDialog("[重打失败]" + error.data.data.message + "(" + error.data.data.code + ")");
      })  
    },

    jiaofeiFormatter(row) {
      const transactionLogStatus = row.transactionLogStatus
      return this.jiaofeiStatus[transactionLogStatus];
    },

    chufangFormatter(row) {
      const status = row.status;
      return this.chufangStatus[status];
    },

    // 付款
    pay() {
      this.shouldPay = 0;
      this.dialogTableVisible = true;
      for(var i=0; i<this.chargeSelection.length;i++){
        this.chargeSelection[i].cashierId = this.currentRoleId;
        this.shouldPay += this.chargeSelection[i].totalMoney;
      }
    },

    // 缴费
    charge() {
      this.tempData = this.chargeSelection;
      console.warn("up")
      console.log(this.chargeSelection)
      console.warn("mid")
      console.log(this.tempData);
      console.warn("down")
      this.dialogTableVisible = false;
      this.loading2 = true;
      
      charge.charge(this.chargeSelection).then(
        response => {
        console.log(response.data.data);
        this.getpaymentInfo();
        this.loading2 = false;
        successDialog("缴费成功");
        this.chargeInvoice = response.data.data;
        this.invoicePrinterVisible = true;
      }, error => {
        this.loading2 = false;
        failDialog("[缴费失败]" + error.data.data.message + "(" + error.data.data.code + ")");
      })
    },

    // 退费
    withdraw() {
      for(var i=0; i<this.withdrawSelection.length; i++){
        this.withdrawSelection[i].newCashierId = this.currentRoleId;
      }
      charge.withdraw(this.withdrawSelection).then(
        response => {
          console.log(response.data.data)
          this.getpaymentInfo();
          successDialog("退费成功");
      }, error =>{
        failDialog("[退费失败]" + error.data.data.message + "(" + error.data.data.code + ")");
      })
    },

    handleChargeSelectionChange(val) {
      this.chargeSelection = val;
    },

    handleWithdrawSelectionChange(val) {
      this.withdrawSelection = val;
    },

    // 计算当前日期
    getNowFormatDate() {
      var date = new Date();
      var seperator1 = "-";
      var year = date.getFullYear();
      var month = date.getMonth() + 1;
      var strDate = date.getDate();
      if (month >= 1 && month <= 9) {
        month = "0" + month;
      }
      if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
      }
      var currentdate = year + seperator1 + month + seperator1 + strDate;
      return currentdate;
    },
    
    // 获取病历号所对应的缴费状态
    getpaymentInfo(){
      this.chargeItems = [];
      this.withdrawItems = [];
      this.chargeSelection.length = 0;
      this.withdrawSelection.length = 0;
      this.loading1 = true;
      console.log(this.input)
      charge.getpaymentInfo(this.input).then(
        response => {
        console.log(response.data.data)
        const data = response.data.data;
        this.patientInfo = data.patientInfo;

        this.invoiceCollection = data.paymentInfo.invoiceCollection;
        this.transactionLogs = data.paymentInfo.transactionLogs;
        successDialog("查询成功");
        this.loading1 = false;
          for(var j=0;j<this.transactionLogs.length;j++){
            if(this.transactionLogs[j].status===1){
              this.transactionLogs[j].itemStatus = this.chufangStatus[this.transactionLogs[j].projectStatus]
              this.chargeItems.push(this.transactionLogs[j]);
            }
          }
          for(var j=0;j<this.transactionLogs.length;j++){
            if(this.transactionLogs[j].status===2){
              this.transactionLogs[j].itemStatus = this.chufangStatus[this.transactionLogs[j].projectStatus]
              this.transactionLogs[j].returnAmount = this.transactionLogs[j].amount - this.transactionLogs[j].remainAmount;
              if(this.transactionLogs[j].type=="检查费"||this.transactionLogs[j].type=="检验费"){
                this.transactionLogs[j].remainAmount = 1;
                this.transactionLogs[j].returnAmount = 1;
              }
              this.withdrawItems.push(this.transactionLogs[j])
            }
          }
        console.log("end")
      }, error => {
        failDialog("[查询失败]" + error.data.data.message + "(" + error.data.data.code + ")");
        this.loading1 = false;
      })
    }
  }
}
</script>

<style lang="css" scoped>

.search-board{
  width: 400px;
  margin: 0px 30px 30px 25px;
}

.patient-info{
    padding-top: 10px;
    width: 350px;
}

.toll-admin{
  display: flex;
  flex-direction: row;
}

.action-bar{
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
}


.input-with-select .el-input-group__prepend {
  background-color: #fff;
}

.select-box{
  width: 140px;
}

.user-selector{
  width: 50%;
}

</style>

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
            <el-input placeholder="搜索内容" class="input-with-select" v-model="input">
              <el-select slot="prepend" placeholder="病历号" class="select-box" style="width: 100px;">
                <el-option label="病历号" value="0"></el-option>
                <el-option label="用户ID" value="1"></el-option>
              </el-select>
              <el-button slot="append" icon="el-icon-search" @click="getpaymentInfo"></el-button>
            </el-input>
          </div>
          <!-- 患者基本信息 -->
          <div class="patient-info">
            <el-form ref="form" label-width="80px" label-position="left">
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

      <el-card shadow="hover">
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
        </el-table>      
      </el-card>
    </div>
    <!-- 右侧显示列表，展示已交费和未交费项目 -->
    <div class="edit-board">
      <el-card shadow="hover">
        <div slot="header">
          <span>待缴费清单</span>
        </div>
        <!-- 主体区域 -->
        <div>
          <!-- 操作按钮 -->
          <div class="action-bar">
            <el-button type="text" icon="el-icon-success" @click="charge">结算</el-button>
          </div>
          <!-- 缴费项目表 -->
          <div class="列表">
            <el-table :data="chargeItems" style="width: 100%" @selection-change="handleChargeSelectionChange">
              <el-table-column type="selection" width="55">
              </el-table-column>
              <el-table-column label="发票号" prop="invoiceCode" width="110">
              </el-table-column>
              <el-table-column label="项目类型" prop="type">
              </el-table-column>
              <el-table-column label="项目名称" prop="id">
              </el-table-column>
              <el-table-column label="数量" prop="amount">
              </el-table-column>
              <el-table-column label="金额" prop="totalMoney">
              </el-table-column>
              <el-table-column label="开立时间" prop="usedTime(gmtCreate)">
              </el-table-column>
              <!-- <el-table-column label="缴费状态" prop="status">
              </el-table-column> -->
              <el-table-column label="开立状态" prop="itemStatus">
              </el-table-column>
              <el-table-column label="执行科室" prop="departmentId">
              </el-table-column>
            </el-table>
          </div>
        </div>
      </el-card>

      <el-card shadow="hover">
        <div slot="header">
          <span>已缴费清单</span>
        </div>
        <!-- 主体区域 -->
        <div>
          <!-- 操作按钮 -->
          <div class="action-bar">
            <el-button type="text" icon="el-icon-success" @click="withdraw">退费</el-button>
            <el-button type="text" icon="el-icon-printer">发票重印</el-button>
            <el-button type="text" icon="el-icon-printer">发票补打</el-button>
          </div>
          <!-- 缴费项目表 -->
          <div class="列表">
            <el-table :data="withdrawItems" style="width: 100%" @selection-change="handleWithdrawSelectionChange">
              <el-table-column type="selection" width="55">
              </el-table-column>
              <el-table-column label="发票号" prop="invoiceCode" width="110">
              </el-table-column>
              <el-table-column label="项目类型" prop="type" width="80">
              </el-table-column>
              <el-table-column label="项目名称" prop="id" width="80">
              </el-table-column>
              <el-table-column label="可退数量" prop="remainAmount" width="80">
              </el-table-column>
              <el-table-column label="金额" prop="totalMoney" width="50">
              </el-table-column>
              <el-table-column label="开立时间" prop="gmtCreate">
              </el-table-column>
              <!-- <el-table-column label="开立状态" prop="itemStatus">
              </el-table-column> -->
              <el-table-column label="执行科室" prop="departmentId">
              </el-table-column>
              <el-table-column label="退费数量" fixed="right" width="200">
                <template slot-scope="scope">
                  <el-input-number v-model="scope.row.returnAmount" :min=1 :max="scope.row.amount" label="描述文字"></el-input-number>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
import charge from '@/api/charge'

export default {
  name: 'TollAdmin',
  data() {
    return{
      input: "10000015",

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
        4: "已取药",
        5: "已退药"
      }
    }
  },

  computed: {
    usedTime(gmtCreate){
      return gmtCreate.substr(0,9)
    }
  },

  mounted(){
    const currentRoleId = this.$store.getters['user/currentRoleId'];
    this.currentRoleId = currentRoleId;
  },

  methods: {

    jiaofeiFormatter(row) {
      const transactionLogStatus = row.transactionLogStatus
      return this.jiaofeiStatus[transactionLogStatus];
    },

    chufangFormatter(row) {
      const status = row.status;
      return this.chufangStatus[status];
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
    
    // 缴费
    charge() {
      for(var i=0; i<this.chargeSelection.length;i++){
        chargeSelection[i].cashierId = this.currentRoleId;
      }
      charge.charge(this.chargeSelection).then(response => {
        console.log(response.data.data)
        if(response.data.code===200){
          this.success("缴费");
        } else {
          this.fail("缴费");
        }        
      }).catch(error => {
        
      })
    },

    // 退费
    withdraw() {
      charge.withdraw(this.withdrawSelection, this.currentRoleId).then(response => {
        console.log(response.data.data)
        if(response.data.code===200){
          this.success("退费");
        } else {
          this.fail("退费");
        }        
      }).catch(error => {
        
      })
    },

    handleChargeSelectionChange(val) {
      this.chargeSelection = val;
    },

    handleWithdrawSelectionChange(val) {
      this.withdrawSelection = val;
    },  
    
    // 获取病历号所对应的缴费状态
    getpaymentInfo(){
      console.log(this.input)
      charge.getpaymentInfo(this.input).then(response => {
        console.log(response.data.data)
        const data = response.data.data;
        this.patientInfo = data.patientInfo;

        this.invoiceCollection = data.paymentInfo.invoiceCollection;
        this.transactionLogs = data.paymentInfo.transactionLogs;

        // 选出待缴费项目用于缴费
        for(var i=0; i<this.invoiceCollection.length;i++){
          var temp = this.invoiceCollection[i].invoiceCode
          if(this.invoiceCollection[i].status==1){
            for(var j=0;j<this.transactionLogs[temp].length;j++){
              if(this.transactionLogs[temp][j].type!="挂号费"&&this.transactionLogs[temp][j].status===1){
                this.chargeItems.push(this.transactionLogs[temp][j])
              }
            }
          }
        }
        // 选出已缴费项目用于退费
        for(var i=0; i<this.invoiceCollection.length;i++){
          var temp = this.invoiceCollection[i].invoiceCode
          if(this.invoiceCollection[i].status==2){
            for(var j=0;j<this.transactionLogs[temp].length;j++){
              if(this.transactionLogs[temp][j].type!="挂号费"&&this.transactionLogs[temp][j].status===2){
                this.withdrawItems.push(this.transactionLogs[temp][j])
              }
            }
          }
        }
        console.log("end")
      }).catch(error => {
        
      })
    }
  }
}
</script>

<style lang="css" scoped>

.search-board{
  width: 400px;
  margin: 2px 10px;
}

.edit-board{
  width: 1200px;
  margin: 2px 10px;
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

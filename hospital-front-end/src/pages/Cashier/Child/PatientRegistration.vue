<template lang="html">
  <div>
    <invoice-code ref="invoiceCode" v-on:listenToChildEvent="showMsgFromChild"></invoice-code>
    <el-card class="input-card" style="margin: 5px 4px;s" shadow="hover" v-loading="loading1">
      <div slot="header">
        <span>挂号</span>
        <el-button style="float:right" type="text" icon="el-icon-document-add" @click="confirmation">挂号</el-button>
        <el-button style="float:right" type="text" icon="el-icon-toilet-paper" @click="invoicePrinterVisible = true">打印</el-button>
        <!-- <el-button style="float:right" type="text" icon="el-icon-toilet-paper" @click="invoicePrinterVisible = true">补打</el-button>
        <el-button style="float:right" type="text" icon="el-icon-printer">重打</el-button> -->
        <el-button style="float:right" type="text" icon="el-icon-refresh-right" @click="refresh">清屏</el-button>
        <el-button style="float:right" type="text" icon="el-icon-camera" @click="scan">扫描</el-button>
      </div>
      <el-form :inline="true">
        <div class="vice-title">
          <span>基础信息</span>
        </div>
        <el-form-item label="病历号">
          <el-input placeholder="病历号" :disabled="true" v-model="registrationForm.registrationId"></el-input>
        </el-form-item>

        <el-form-item label="身份证号">
          <el-input placeholder="身份证号" v-model="registrationForm.idCard"></el-input>
        </el-form-item>

        <el-form-item label="姓名">
          <el-input placeholder="姓名" v-model="registrationForm.name"></el-input>
        </el-form-item>

        <el-form-item label="性别">
          <el-select placeholder="性别" v-model="registrationForm.gender">
            <el-option label="男" value="1"></el-option>
            <el-option label="女" value="0"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="家庭地址">
          <el-input placeholder="家庭地址" v-model="registrationForm.address"></el-input>
        </el-form-item>

        <el-form-item label="出生日期">
          <el-date-picker type="date" placeholder="选择出生日期" default-value="2000-01-01" class="date-selection" v-model="registrationForm.birthdayStr" value-format="yyyy-MM-dd"></el-date-picker>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input placeholder="年龄" :disabled="true" v-model="age"></el-input>
        </el-form-item>

        <div class="vice-title">
          <span>挂号选择</span>
        </div>
        <!-- <el-form-item label="医疗证号">
          <el-input placeholder="医疗证号"></el-input>
        </el-form-item> -->
        <el-form-item label="看诊日期">
          <el-date-picker type="date" placeholder="选择看诊日期" v-model="registrationForm.appointmentDateStr" @change="isRegistrationAvailable" class="date-selection" value-format="yyyy-MM-dd"></el-date-picker>
        </el-form-item>
        <el-form-item label="看诊时段">
          <el-select placeholder="请选择看诊时段" @change="isRegistrationAvailable" v-model="registrationForm.timeSlot">
            <el-option label="上午" value="1"></el-option>
            <el-option label="下午" value="2"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="挂号科室">
          <el-select placeholder="挂号科室" @change="isRegistrationAvailable" v-model="registrationForm.departmentId">
            <el-option v-for="department in departments" :label="department.name" :value="department.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="号别">
          <el-select placeholder="请选择号别" @change="isRegistrationAvailable" v-model="registrationForm.registrationLevelId">
            <el-option label="普通号" value="1"></el-option>
            <el-option label="专家号" value="2"></el-option>
            <el-option label="急诊号" value="3"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="看诊医生">
          <el-select placeholder="看诊医生" v-model="registrationForm.roleId" :disabled="available"> 
            <el-option v-for="doctor in doctors" :label="doctor.userName" :value="doctor.roleId"></el-option> 
          </el-select>
        </el-form-item>

        <div class="vice-title">
          <span>收费信息</span>
        </div>
        <el-form-item label="结算类别">
          <el-select placeholder="请选择结算类别" v-model="registrationForm.payType" @change="isTotalFeeAvailable">
            <el-option label="自费" value="1"></el-option>
            <el-option label="医保卡" value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="病历本">
          <el-checkbox v-model="registrationForm.isBuyCaseBook" @change="isTotalFeeAvailable"></el-checkbox>
        </el-form-item>
        <el-form-item label="应收金额">
          <el-input placeholder="应收金额" :disabled="true" v-model="registrationForm.totalFee"></el-input>
        </el-form-item>
      </el-form>
    </el-card>
    <!-- 挂号信息列表：有退号功能 -->
    <el-card class="input-card" style="margin: 5px 4px;s" shadow="hover">
      <div slot="header">
        <span>挂号信息列表</span>
        <el-button style="float:right" type="text" icon="el-icon-refresh" @click="refreshRegistration">刷新</el-button>
      </div>
      <!-- 挂号信息表 -->
      <div class="">
        <el-table :data="registrationsInfo" style="width: 100%" stripe 
        :default-sort = "{prop: 'id', order: 'descending'}" v-loading="loading2">
          <el-table-column type="expand" fixed="left">
            <template slot-scope="props">
              <el-form label-position="left" class="demo-table-expand">
                <el-form-item label="患者ID">
                  <span>{{ props.row.patient.id }}</span>
                </el-form-item>                
                <el-form-item label="患者住址">
                  <span>{{ props.row.patient.address }}</span>
                </el-form-item>
                <el-form-item label="出生日期">
                  <span>{{ props.row.patient.birthday }}</span>
                </el-form-item>
              </el-form>

              <el-form label-position="right" class="demo-table-expand">
                <el-form-item label="看诊时段">
                  <span>{{ props.row.timeSlot }}</span>
                </el-form-item>                
                <el-form-item label="看诊医生">
                  <span>{{ props.row.roleId }}</span>
                </el-form-item>
                <el-form-item label="是否购买病历本">
                  <span>{{ props.row.buyCaseBook }}</span>
                </el-form-item>
                <el-form-item label="发票号">
                  <span>{{ props.row.transactionLog.invoiceCode }}</span>
                </el-form-item>
              </el-form>              
            </template>
          </el-table-column>

          <el-table-column
            fixed="left" sortable
            label="病历号" prop="id">
          </el-table-column>

          <el-table-column
            label="姓名" prop="patient.name" fixed="left">
          </el-table-column>

          <el-table-column
            label="性别" prop="patient.gender"  width="50">
          </el-table-column>

          <el-table-column
            label="挂号员" prop="cashierId"  width="100">
          </el-table-column>

          <el-table-column
            label="身份证号" prop="patient.idCard" width="150">
          </el-table-column>

          <el-table-column sortable
            label="挂号日期" prop="gmtCreate" width="100">
          </el-table-column>

          <el-table-column
            label="看诊日期" prop="appointmentDate" width="110">
          </el-table-column>

          <el-table-column
            label="结算类别" prop="payType">
          </el-table-column>

          <el-table-column
            label="挂号级别" prop="registrationLevelId">
          </el-table-column>

          <el-table-column
            label="是否已诊" prop="patientCase.status">
          </el-table-column>

          <el-table-column
            label="看诊科室" prop="departmentId">
          </el-table-column>

          <el-table-column
            label="实收" prop="totalFee" width="50">
          </el-table-column>

          <el-table-column fixed="right" label="操作" width="120">
            <template slot-scope="scope">
              <el-button type="text" size="small" v-if="scope.row.normal&&scope.row.patientCase.status==1" @click="withdrawal(scope.row.id, scope.row.appointmentDate, scope.row.timeSlot, scope.row.roleId, scope.row.registrationLevelId, scope.row.departmentId, scope.row.patientCase.status)">
                退号
              </el-button>
              <el-button type="text" disabled size="small" v-if="!scope.row.normal">
                已退号
              </el-button>
              <el-button type="text" disabled size="small" v-if="scope.row.normal&&scope.row.patientCase.status!=1">
                已就诊
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-card>

    <el-dialog title="发票打印" :visible.sync="invoicePrinterVisible" width="70%">
      <div id="printContent" class="wrap">
      <div class="con">
        <h3 style="text-align:center;">熙康云医院 · 挂号单</h3>
        <h6 style="text-align:center;margin-top: -12px;">地址:沈阳市浑南区创新路195号&nbsp;&nbsp;&nbsp;门诊部:024-88886666</h6>
        <hr style="width: 630px;text-align:center;" />
        <h5 style="text-align:left;margin-left: 40px;">
          挂号日期:
          {{this.today}}
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          病历号:
          {{this.registrationForm.registrationId}}
        </h5>
			  <table style="width: 630px;margin:auto;margin-top: -10px;">
					<tr height="32px;">
						<th width="60px;">姓名</th>
						<th width="100px;">{{this.registrationForm.name}}</th>
						<th width="90px;">身份证号</th>
						<th width="200px;">{{this.registrationForm.idCard}}</th>
						<th>性别</th>
						<th>男</th>
					</tr>
					<tr height="32px;">
						<th width="60px;">住址</th>
						<th width="100px;">{{this.registrationForm.address}}</th>
						<th width="90px;">出生日期</th>
						<th width="200px;">{{this.registrationForm.birthdayStr}}</th>
						<th>年龄</th>
						<th>{{this.age}}</th>
					</tr>
					<tr height="32px;">

					</tr>
					<tr height="32px;">
						<th width="50px;">看诊日期</th>
						<th width="100px;">{{this.registrationForm.appointmentDateStr}}</th>
						<th width="50px;">看诊时段</th>
						<th width="50px;">{{this.registrationForm.timeSlot}}</th>
						<th width="50px;">挂号科室</th>
						<th width="50px;">{{this.registrationForm.departmentId}}</th>
					</tr>
          <tr height="32px;">	
            <th width="50px;">号别</th>
						<th width="50px;">{{this.registrationForm.registrationLevelId}}</th>
						<th width="50px;">看诊医生</th>
						<th width="50px;">{{this.registrationForm.roleId}}</th>
					</tr>
          <tr height="32px;">
            
					</tr>

          <tr height="32px;">
						<th width="100px;">结算类别</th>
						<th width="100px;">{{this.registrationForm.payType}}</th>
						<th width="50px;">病历本</th>
						<th width="50px;">{{this.registrationForm.isBuyCaseBook}}</th>
						<th width="100px;">应收金额</th>
						<th width="100px;">{{this.registrationForm.totalFee}}</th>
					</tr>
			  </table>
        <h6 style="text-align:left;margin-top: 5px;margin-left: 30px;">
          挂号员:{{this.currentRoleId}}
        </h6>
      </div>
      <button class="btn no-print" v-print="'#printContent'">打印</button>
      </div>

    </el-dialog>
  </div>
</template>


<script>
import InvoiceCode from './InvoiceCode'
import register from '@/api/register'


export default {
  name: 'PatientRegistration',
  components:{
    'invoice-code': InvoiceCode
  },

  data () {
    return {
      registrationForm: {
        registrationId : "0",
        name: "",
        gender: "",
        birthdayStr: "",
        payType: "",
        idCard: "",
        address: "",
        appointmentDateStr: "",
        timeSlot: "",
        registrationLevelId: "",
        departmentId: "",
        doctorName: "",
        roleId: "",
        totalFee: "",
        isBuyCaseBook: "",
        cashierId: "",
        invoiceCode: "",
      },
      // 发票打印可见
      invoicePrinterVisible: false,
      today: "",

      departments: [],
      doctors: [],
      available: true,
      registrationsInfo: [],

      currentRoleId: "",
      loading1: false,
      loading2: false
      
    }
  },

  computed: {
      age() {
        console.log();
        const day1 = new Date(this.registrationForm.birthdayStr);
        const day2 = new Date(this.getNowFormatDate());
        var age = Math.floor((day2 - day1) / (1000 * 60 * 60 * 24 * 365));
        if(isNaN(age)){
          age = "";
        }
        return age;
      }
  },

  methods: {
    // 扫描
    scan() {

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
    // 退号
    withdrawal(id, appointmentDate, timeSlot, roleId, registrationLevelId, departmentId, patientCaseStatus) {
      this.loading2 = true;
      var transferData={};
      transferData.registrationId=id;
      transferData.appointmentDateStr=appointmentDate;
      transferData.timeSlot=timeSlot;
      transferData.roleId=roleId;
      transferData.registrationLevelId=registrationLevelId;
      transferData.departmentId=departmentId;
      transferData.patientCaseStatus=patientCaseStatus;
      console.log(id);

      transferData.newCashierId = this.currentRoleId;
      register.withdrawal(transferData).then(response => {
        console.log(response.data)
        const data = response.data.data

        if(response.data.code===200){
          this.success("退号");
          this.registrations();
        } else {
          this.fail("退号");
        }
        this.loading2 = false;
      }).finally(response => {
          this.loading = false;
      })

    },

    refreshRegistration() {
      this.registrations();
    },
    
    // 刷新，用于清屏按钮及挂号成功后
    refresh() {
      this.registrationForm.idCard="";
      this.registrationForm.name="";
      this.registrationForm.gender="";
      this.registrationForm.address="";
      this.registrationForm.birthdayStr="";
      this.age="";
      this.registrationForm.timeSlot="";
      this.registrationForm.departmentId="";
      this.registrationForm.registrationLevelId="";
      this.registrationForm.roleId="";
      this.registrationForm.payType="";
      this.registrationForm.isBuyCaseBook="";
      this.registrationForm.totalFee="";

      this.getNextRegistrationId();
      this.$refs.invoiceCode.getNextInvoiceCode();
    },

    getNextRegistrationId(){
      register.getNextRegistrationId().then(response => {
        console.log(response.data)
        const data = response.data.data
        this.registrationForm.registrationId = data;
      }).catch(error => {
        
      })
    },
    
    // 接收从子组件传过来的“当前发票号”
    showMsgFromChild(data){
      console.log(data);
      this.registrationForm.invoiceCode=data;
    },

    // 挂号
    confirmation(registrationForm) {
      this.loading1 = true;
      const currentRoleId = this.$store.getters['user/currentRoleId'];
      this.registrationForm.cashierId = currentRoleId;
      register.confirmation(this.registrationForm).then(response => {
        console.log(response.data)

        if(response.data.code===200){
          this.success("挂号");
          this.invoicePrinterVisible = true;
          this.registrations();
        } else {
          this.fail("挂号");
        }
        this.loading1 = false;
      })
    },

    // 检查是否可以向后台查询可选医生列表
    isRegistrationAvailable() {
      const result = this.registrationForm.timeSlot!==""&&this.registrationForm.departmentId!==""&&this.registrationForm.registrationLevelId!=="";
      console.log(result);
      if(result){
        register.listAvailableDoctors(this.registrationForm).then(response => {
        console.log("显示所有可选医生列表:")
        const data = response.data.data.availableDoctors
        this.doctors = data;
        console.log(this.docters)

        this.available=false;
        }).catch(error => {
          
        })
      }
      return result;
    },

    // 检查应收金额栏是否应该向后台请求查询数据
    isTotalFeeAvailable() {
      const result = this.registrationForm.payType!==""&&this.registrationForm.isBuyCaseBook!==""&&this.registrationForm.roleId!=="";
      if(result){
        register.calculateTotalFee(this.registrationForm).then(response => {
        console.log("计算总金额: " + response.data.data)
        const data = response.data.data;
        this.registrationForm.totalFee = data;
        }).catch(error => {
          // alert("get error")
        })
      }
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

    // 显示所有挂号信息列表
    registrations() {
      register.registrations().then(response => {
        console.log("显示所有挂号信息列表:")
        console.log(response.data)
        const data = response.data.data
        this.registrationsInfo = data;
      }).catch(error => {
        // alert("get error")
      })
    },
  },

  mounted() {
    // 获取下一个可用病历号
    this.getNextRegistrationId();

    // 获取科室列表
    register.listAllDepartments().then(response => {
      console.log(response.data)
      const data = response.data.data
      this.departments = data;
    }).catch(error => {
      // alert("get error")
    })
    // 默认设置看诊日期为今天
    this.today = this.getNowFormatDate();
    this.registrationForm.appointmentDateStr=this.getNowFormatDate();

    // 显示所有挂号信息列表
    this.registrations();

    // 初始化操作员id
    const currentRoleId = this.$store.getters['user/currentRoleId'];
    this.currentRoleId = currentRoleId;
  }  
}

</script>

<style lang="css" scoped>
.vice-title{
  margin-bottom: 20px;
}
.date-selection{
  width: 100%;
}

/*展开行*/
.demo-table-expand {
    font-size: 0;
  }
.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}

/*发票打印*/
		html,body{
		    margin: 0;
		    padding: 0;
		}
		body{
		    font-size: 14px;
		    color: #333;
		}
		@media print {
		    body{-webkit-print-color-adjust:exact;} 
		}
		.fr{
		    float:right;
		}
		.vt{
		    vertical-align: top;
		}
    .wrap {
      margin: 0 auto;
      padding: 20px;
      width: 680px;
			height: 405px;
      border: solid black 3px;
    }

    .form .row {
      padding: 10px 0 0;
    }

    .btn {
      display: block;
      margin: 20px auto;
      padding: 8px 16px;
    }
		table{
			border-collapse:collapse;
		}

		table, td, th{
			border:1px solid black;
		}
</style>

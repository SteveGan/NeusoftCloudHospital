<template lang="html">
  <div>
    <invoice-code></invoice-code>
    <el-card class="input-card" style="margin: 5px 4px;s" shadow="hover">
      <div slot="header">
        <span>挂号</span>
        <el-button style="float:right" type="text" icon="el-icon-document-add">挂号</el-button>
        <el-button style="float:right" type="text" icon="el-icon-printer">补打</el-button>
        <el-button style="float:right" type="text" icon="el-icon-printer">重打</el-button>
        <el-button style="float:right" type="text" icon="el-icon-refresh-right">清屏</el-button>
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
          <el-date-picker type="date" placeholder="选择看诊日期" v-model="registrationForm.appointmentDateStr" class="date-selection"></el-date-picker>
        </el-form-item>
        <el-form-item label="看诊时段">
          <el-select placeholder="请选择看诊时段" @change="isRegistrationAvailable" v-model="registrationForm.timeSlot" value-format="yyyy-MM-dd">
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
            <el-option v-for="doctor in doctors" :label="doctors.name" :value="doctors.id"></el-option> 
          </el-select>
        </el-form-item>

        <div class="vice-title">
          <span>收费信息</span>
        </div>
        <el-form-item label="结算类别">
          <el-select placeholder="请选择结算类别" v-model="registrationForm.payType">
            <el-option label="自费" value="1"></el-option>
            <el-option label="医保卡" value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="病历本">
          <el-checkbox v-model="registrationForm.isBuyCaseBook"></el-checkbox>
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
        <el-button style="float:right" type="text" icon="el-icon-refresh">刷新</el-button>
      </div>
      <!-- 挂号信息表 -->
      <div class="">
        <el-table
          :data="tableData"
          style="width: 100%">
          <el-table-column
            type="selection"
            width="55">
          </el-table-column>
          <el-table-column
            fixed="left"
            label="病历号">
          </el-table-column>
          <el-table-column
            fixed="left"
            label="姓名">
          </el-table-column
            label="出生日期">
          <el-table-column
            label="身份证号">
          </el-table-column>
          <el-table-column
            label="发票号">
          </el-table-column>
          <el-table-column
            label="结算类别">
          </el-table-column>
          <el-table-column
            label="挂号级别">
          </el-table-column>
          <el-table-column
            label="挂号日期">
          </el-table-column>
          <el-table-column
            label="看诊日期">
          </el-table-column>
          <el-table-column
            label="是否已诊">
          </el-table-column>
          <el-table-column
            label="是否收取病历本">
          </el-table-column>
          <el-table-column
            label="状态">
          </el-table-column>
          <el-table-column
            label="实收">
          </el-table-column>
          <el-table-column
            label="看诊科室">
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作"
            width="120">
            <template slot-scope="scope">
              <el-button
                type="text"
                size="small">
                退号
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-card>
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
        name: "王三奇",
        gender: "1",
        birthdayStr: "",
        payType: "",
        idCard: "",
        address: "东北大学",
        appointmentDateStr: "",
        timeSlot: "",
        registrationLevelId: "",
        departmentId: "",
        roleId: "",
        totalFee: "",
        isBuyCaseBook: "",
        cashierId: "",
        invoiceCode: "",
      },

      departments: [],
      doctors: [],
      available: true
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
    registerApi(registrationForm) {

    },

    isRegistrationAvailable() {
      const result = this.registrationForm.timeSlot!==""&&this.registrationForm.departmentId!==""&&this.registrationForm.registrationLevelId!=="";
      console.log(result);
      if(result){
        register.listAvailableDoctors(this.registrationForm).then(response => {
        console.log(response.data)
        const data = response.data.data
        this.doctors = data;

        this.available=false;
        }).catch(error => {
          // alert("get error")
        })
      }
      return result;
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
    }
  },

  mounted() {
    // 获取病历号
    register.getNextRegistrationId().then(response => {
      console.log(response.data)
      const data = response.data.data
      this.registrationForm.registrationId = data;
    }).catch(error => {
      // alert("get error")
    })

    // 获取科室列表
    register.listAllDepartments().then(response => {
      console.log(response.data)
      const data = response.data.data
      this.departments = data;
    }).catch(error => {
      // alert("get error")
    })
    // 默认设置看诊日期为今天
    this.registrationForm.appointmentDateStr=this.getNowFormatDate();
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
</style>

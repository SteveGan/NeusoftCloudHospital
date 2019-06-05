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
        <el-form-item label="病历号">
          <el-input placeholder="病历号"></el-input>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input placeholder="姓名"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-select placeholder="性别">
            <el-option label="男" value="1"></el-option>
            <el-option label="女" value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input placeholder="年龄"></el-input>
        </el-form-item>
        <el-form-item label="出生日期">
          <el-date-picker type="date" placeholder="选择出生日期"  style="width: 100%;"></el-date-picker>
        </el-form-item>
        <el-form-item label="结算类别">
          <el-select placeholder="请选择结算类别">
            <el-option label="自费" value="1"></el-option>
            <el-option label="医保卡" value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="医疗证号">
          <el-input placeholder="医疗证号"></el-input>
        </el-form-item>
        <el-form-item label="身份证号">
          <el-input placeholder="身份证号"></el-input>
        </el-form-item>
        <el-form-item label="看诊日期">
          <el-date-picker type="date" placeholder="选择看诊日期"  style="width: 100%;"></el-date-picker>
        </el-form-item>
        <el-form-item label="号别">
          <el-select placeholder="请选择号别">
            <el-option label="专家号" value="1"></el-option>
            <el-option label="普通号" value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="挂号科室">
          <el-select placeholder="请选择号别">
            <el-option label="专家号" value="1"></el-option>
            <el-option label="普通号" value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="看诊医生">
          <el-select placeholder="选择看诊医生">
            <el-option label="甘教授" value="1"></el-option>
            <el-option label="如来" value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="病历本">
          <el-checkbox></el-checkbox>
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
export default {
  name: 'PatientRegistration',
  components:{
    'invoice-code': InvoiceCode
  }
}
</script>

<style lang="css" scoped>
</style>

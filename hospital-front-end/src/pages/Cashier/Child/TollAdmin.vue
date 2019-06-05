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
            <el-input placeholder="搜索内容" class="input-with-select">
              <el-select slot="prepend" placeholder="病历号" class="select-box" style="width: 100px;">
                <el-option  label="病历号" value="0"></el-option>
                <el-option  label="用户ID" value="1"></el-option>
              </el-select>
              <el-button slot="append" icon="el-icon-search"></el-button>
            </el-input>
          </div>
          <!-- 患者基本信息 -->
          <div class="patient-info">
            <el-form ref="form" label-width="80px" label-position="left">
              <el-form-item label="姓名">
                <el-input></el-input>
              </el-form-item>
              <el-form-item label="病历号">
                <el-input></el-input>
              </el-form-item>
              <el-form-item label="性别">
                <el-input></el-input>
              </el-form-item>
              <el-form-item label="年龄">
                <el-input></el-input>
              </el-form-item>
              <el-form-item label="出生日期">
                <el-input></el-input>
              </el-form-item>
              <el-form-item label="结算类别">
                <el-input></el-input>
              </el-form-item>
            </el-form>
          </div>
        </div>
      </el-card>
    </div>
    <!-- 右侧显示列表，展示已交费和未交费项目 -->
    <div class="edit-board">
      <el-card shadow="hover">
        <div slot="header">
          <span>查询结果</span>
        </div>
        <!-- 主体区域 -->
        <div>
          <!-- 操作按钮 -->
          <div class="action-bar">
            <el-button type="text" icon="el-icon-success">结算</el-button>
            <el-button type="text" icon="el-icon-printer">发票重打印</el-button>
            <el-button type="text" icon="el-icon-printer">发票补打</el-button>
          </div>
          <!-- 缴费项目表 -->
          <div class="列表">
            <el-table
              ref="payItemTable"
              style="width: 100%"
            >
              <el-table-column
                type="selection"
                width="55">
              </el-table-column>
              <el-table-column
                label="项目名称">
              </el-table-column>
              <el-table-column
                label="规格">
              </el-table-column>
              <el-table-column
                label="单价">
              </el-table-column>
              <el-table-column
                label="数量">
              </el-table-column>
              <el-table-column
                label="单位">
              </el-table-column>
              <el-table-column
                label="付数">
              </el-table-column>
              <el-table-column
                label="金额">
              </el-table-column>
              <el-table-column
                label="执行科室">
              </el-table-column>
            </el-table>
          </div>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
import InvoiceCode from './InvoiceCode'
export default {
  name: 'TollAdmin',
  components: {
    'invoice-code': InvoiceCode
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

<template lang="html">
  <el-card :body-style="{padding:'0px'}" style="margin: 5px 4px;s" shadow="hover">
    <!-- 发票号区域 -->
    <div class="invoice-number-region">
      <!-- 门诊发票号 -->
      <div class="">
        <p>当前发票号</p>
      </div>
      <div class="">
        <el-input></el-input>
      </div>
      <div class="">
        <el-button>更新发票号</el-button>
      </div>
    </div>
  </el-card>
</template>

<script>
export default {
  name: 'InvoiceCode'
}
</script>

<style lang="css" scoped>
.invoice-number-region{
  display: flex;
  flex-direction: row;
  align-items: center;
  height: 30px;
  padding: 5px 20px;
}

.invoice-number-region div{
  margin: 2px 5px;
}
</style>

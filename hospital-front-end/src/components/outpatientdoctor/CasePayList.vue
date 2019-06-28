<template>
  <div>
    <el-card>
      <!-- 操作栏 -->
      <div slot="header" class="clearfix">
        <i class="el-icon-notebook-2"></i>
        <span>申请单列表</span>
      </div>
      <!-- 项目列表 -->
      <div class>
        <el-table style="width: 100%" :data="payList">
          <el-table-column label="名称" prop="itemName"></el-table-column>
          <el-table-column label="数量" prop="amount"></el-table-column>
          <el-table-column label="单价" prop="amount" :formatter="getSinglePrice"></el-table-column>
          <el-table-column label="类型" prop="type"></el-table-column>
          <el-table-column label="总金额" prop="totalMoney"></el-table-column>
          <el-table-column label="收费状态" prop="status" :formatter="statusFormatter"></el-table-column>
        </el-table>
      </div>
    </el-card>
  </div>
</template>


<script>
import { payStatusCodeToString } from "@/utils/interpreter";
export default {
  name: "CasePayList",
  props: {
    payList: Array
  },
  methods: {
    statusFormatter(row, column) {
      var statusCode = row.status;
      return payStatusCodeToString(statusCode);
    },
    getSinglePrice(row, column) {
      var totalPrice = row.totalMoney;
      var amount = row.amount;
      return (totalPrice / amount).toFixed(2);
    }
  }
};
</script>


<style lang="css" scoped>
</style>
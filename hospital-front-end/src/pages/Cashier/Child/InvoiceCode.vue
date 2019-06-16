<template lang="html">
  <el-card :body-style="{padding:'0px'}"  style="margin-top: 0;" shadow="hover">
    <!-- 发票号区域 -->
    <div class="invoice-number-region">
      <!-- 门诊发票号 -->
      <div class="">
        <i class="el-icon-caret-right"></i>
        <span>当前发票号</span>
      </div>
      <div class="">
        <el-input v-model="invoiceCode" :disabled="true"></el-input>
      </div>
      <div class="">
        <el-button @click="refresh">更新发票号</el-button>
      </div>
    </div>
  </el-card>
</template>

<script>
import register from '@/api/register'

export default {
  data () {
    return {
      invoiceCode: "0",
    }
  },
  name: 'InvoiceCode',

  methods: {
    // 刷新可用发票号
    refresh() {
      this.getNextInvoiceCode();
    },

    // 获取下一个可用发票号
    getNextInvoiceCode() {
      register.getNextInvoiceCode().then(response => {
        console.log(response.data)
        const data = response.data.data
        this.invoiceCode = data;
        this.$emit("listenToChildEvent", data);
      }).catch(error => {
        // alert("get error")
      })
    }
  },

  mounted() {
    
    this.getNextInvoiceCode();
  },
}

</script>

<style lang="css" scoped>
.invoice-number-region{
  display: flex;
  flex-direction: row;
  align-items: center;
  height: 50px;
  padding: 5px 20px;
}

.invoice-number-region div{
  margin: 2px 5px;
}
</style>

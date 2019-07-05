<template lang="html">
  <div>
    <el-card style="margin: 5px 4px;s" shadow="hover">
      <div slot="header">
        <i class="el-icon-search"></i>
        <span>检索条件</span>
      </div>
      <el-form :inline="true">
          <el-form-item label="起始时间">
              <el-date-picker type="datetime" value-format="yyyy-MM-dd hh:mm:ss" v-model="startDate" placeholder="选择起始时间" class="date-selection"></el-date-picker>
          </el-form-item>
          <el-form-item label="结束时间">
              <el-date-picker type="datetime" value-format="yyyy-MM-dd hh:mm:ss" v-model="endDate" placeholder="选择结束时间" class="date-selection"></el-date-picker>
          </el-form-item>
          <el-form-item label="收款员">
              <el-input v-model="cashierId" placeholder="选输入收费员ID"></el-input>
          </el-form-item>
          <el-button type="text" icon="el-icon-document-add" :loading="loading1" @click="search">查询</el-button>
          <el-button type="text" icon="el-icon-document-add" :loading="loading2" @click="confirm">日结</el-button>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import dailyCheck from '@/api/finicial/dailycheck'
import { successDialog, failDialog } from "@/utils/notification";

export default {
  name: 'DailyCheck',

  data() {
    return{
      startDate: "",
      endDate: "",
      cashierId,

      checkList: [],

      loading1: false,
      loading2: false
    }
  },

  methods: {
    search() {
      this.loading1 = true;
      dailyCheck.checkDailySummary(this.startDate, this.endDate, this.cashierId).then(
        response => {
          this.loading1 = false;
          const data = response.data.data;
          this.checkList = data;
          console.log(this.checkList);

          successDialog("查询成功");
        },
        error => {
          this.loading1 = false;
          failDialog("[查询失败]" + error.data.data.message + "(" + error.data.data.code + ")");
        }
      )
    }

  }
}
</script>

<style lang="css" scoped>
</style>

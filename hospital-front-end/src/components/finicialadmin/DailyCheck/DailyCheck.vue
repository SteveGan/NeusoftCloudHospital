<template lang="html">
  <div>
    <el-card style="margin: 5px 4px;s" shadow="hover">
      <div slot="header">
          <span>检索条件</span>
      </div>
      <el-form :inline="true">
          <el-form-item label="起始时间">
              <el-date-picker type="datetime" value-format="yyyy-MM-dd hh:mm:ss" v-model="startDate" placeholder="选择起始时间" class="date-selection"></el-date-picker>
          </el-form-item>
          <el-form-item label="结束时间">
              <el-date-picker type="datetime" value-format="yyyy-MM-dd hh:mm:ss" v-model="endDate" :picker-options="pickerOptions" placeholder="选择结束时间" class="date-selection"></el-date-picker>
          </el-form-item>
          <el-form-item label="收款员">
              <el-input v-model="currentRoleId"></el-input>
          </el-form-item>
          <el-button type="text" icon="el-icon-document-add" @click="search">日结</el-button>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  name: 'DailyCheck',

  data() {
    return{
      startDate: "",
      endDate: "",
    }
  }
}
</script>

<style lang="css" scoped>
</style>

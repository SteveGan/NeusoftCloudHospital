<template lang="html">
<div class="cashier">
  <el-tabs style="padding-left: 5px;" value="first">
    <el-tab-pane label="门诊挂号管理" name="first">
      <patient-registration></patient-registration>
    </el-tab-pane>
    <el-tab-pane label="门诊收费管理" name="second">
      <toll-admin></toll-admin>
    </el-tab-pane>
    <el-tab-pane label="收费员日结" name="third">
      <daily-summary></daily-summary>
    </el-tab-pane>    
    <el-tab-pane label="日结历史查询" name="fourth">
      <history-search></history-search>
    </el-tab-pane>   
  </el-tabs>
</div>
</template>

<script>
import PatientRegistration from './Child/PatientRegistration'
import TollAdmin from './Child/TollAdmin'
import DailySummary from './Child/DailySummary'
import HistorySearch from './Child/HistorySearch'

export default {
  name: 'Cashier',
  components:{
    'patient-registration': PatientRegistration,
    'toll-admin': TollAdmin,
    'daily-summary': DailySummary,
    'history-search': HistorySearch
  }
}
</script>

<style lang="css" scoped>
.container{
  padding: 0;
  margin: 0;
  min-height: 100%;
  min-width: 100%;
  
}
.cashier{
  background-color: #f2f7f8;
}
</style>

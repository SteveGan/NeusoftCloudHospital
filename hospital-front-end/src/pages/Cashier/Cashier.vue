<template lang="html">
<div class="cashier">
  <el-tabs style="padding-left: 5px;">
    <el-tab-pane label="门诊挂号管理" name="first">
      <patient-registration></patient-registration>
    </el-tab-pane>
    <el-tab-pane label="门诊收费管理" name="second">
    </el-tab-pane>
    <el-tab-pane label="住院登记管理" name="third">角色管理</el-tab-pane>
    <el-tab-pane label="住院费用管理" name="fourth">定时任务补偿</el-tab-pane>
  </el-tabs>
</div>
</template>

<script>
import PatientRegistration from './Child/PatientRegistration'

export default {
  name: 'Cashier',
  components:{
    'patient-registration': PatientRegistration
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
</style>

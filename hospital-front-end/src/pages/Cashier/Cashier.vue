<template lang="html">
<div class="cashier">
  <el-tabs style="padding-left: 5px;" value="second">
    <el-tab-pane label="门诊挂号管理" name="first">
      <patient-registration></patient-registration>
    </el-tab-pane>
    <el-tab-pane label="门诊收费管理" name="second">
      <toll-admin></toll-admin>
    </el-tab-pane>
  </el-tabs>
</div>
</template>

<script>
import PatientRegistration from './Child/PatientRegistration'
import TollAdmin from './Child/TollAdmin'
export default {
  name: 'Cashier',
  components:{
    'patient-registration': PatientRegistration,
    'toll-admin': TollAdmin
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

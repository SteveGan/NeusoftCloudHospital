<template xmlns:v-for="http://www.w3.org/1999/xhtml">
  <el-container class="container">
    <el-header class="container">
      <status-bar></status-bar>
    </el-header>
    <el-main class="container">
      <router-view/>
    </el-main>
  </el-container>
</template>

<script>
import StatusBar from "@/components/common/StatusBar";

export default {
  name: "Home",
  components: {
    "status-bar": StatusBar
  }
};
</script>

<style lang="css" scoped>
.container {
  padding: 0px;
  margin: 0px;
  min-height: 100%;
  min-width: 100%;
}
</style>

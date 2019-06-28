<template>
  <div>
    <el-tabs :stretch="true" value="first">
      <el-tab-pane label="西医" name="first">
        <el-table :data="diagnoseTemplates.modernDiagnoseTemplates">
          <el-table-column label="常用诊断名" prop="name"></el-table-column>
          <el-table-column label="疾病名称" prop="diseaseName"></el-table-column>
          <el-table-column label="ICD编码" prop="icdCode"></el-table-column>
          <el-table-column>
            <template slot-scope="scope">
              <el-button type="text" size="mini" @click="handleUseDisease(scope.row, 1)">使用</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="中医" name="second">
        <el-table :data="diagnoseTemplates.traditionalDiagnoseTemplates">
          <el-table-column label="常用诊断名" prop="name"></el-table-column>
          <el-table-column label="疾病名称" prop="diseaseName"></el-table-column>
          <el-table-column label="ICD编码" prop="icdCode"></el-table-column>
          <el-table-column>
            <template slot-scope="scope">
              <el-button type="text" size="mini" @click="handleUseDisease(scope.row, 2)">使用</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import { listDiagnoseTemplates } from "@/api/diagnoseTemplate";

export default {
  name: "DiagnoseTemplate",
  data() {
    return {
      diagnoseTemplates: {}
    };
  },
  methods: {
    handleUseDisease(row, type) {
      row.type = type;
      this.$emit("give-disease", row);
    }
  },
  mounted: function() {
    console.log("当前的roleId");
    console.log(this.$store.getters["user/currentRoleId"]);
    listDiagnoseTemplates(this.$store.getters["user/currentRoleId"]).then(
      response => {
        this.diagnoseTemplates = response.data.data;
      },
      error => {
        console.log(error);
      }
    );
  }
};
</script>

<style lang="">
</style>


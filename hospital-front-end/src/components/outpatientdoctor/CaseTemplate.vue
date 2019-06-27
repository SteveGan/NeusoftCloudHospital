<template>
  <div>
    <!-- 一个标签页：个人模版，科室模版（包括个人的模版），全院模版 -->
    <el-tabs :stretch="true" value="first">
      <!-- 个人模版 -->
      <el-tab-pane label="个人" name="first">
        <!-- 表格区 -->
        <div>
          <el-table :data="myCaseTemplates">
            <el-table-column type="expand">
              <template slot-scope="props">
                <el-form label-position="top" class="demo-table-expand">
                  <el-form-item label="模版名称">
                    <span>{{props.row.name}}</span>
                  </el-form-item>
                  <el-form-item label="主诉">
                    <span>{{props.row.narrate}}</span>
                  </el-form-item>
                  <el-form-item label="现病史">
                    <span>{{props.row.curDisease}}</span>
                  </el-form-item>
                  <el-form-item label="既往史">
                    <span>{{props.row.pastDisease}}</span>
                  </el-form-item>
                  <el-form-item label="过敏信息">
                    <span>{{props.row.allergy}}</span>
                  </el-form-item>
                  <el-form-item label="体格检查">
                    <span>{{props.row.physicalCondition}}</span>
                  </el-form-item>
                  <el-form-item label="辅助检查">
                    <span>{{props.row.assistDiagnose}}</span>
                  </el-form-item>
                </el-form>
              </template>
            </el-table-column>
            <el-table-column label="模版名称" prop="name" width="150"></el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button type="text" size="mini" @click="handleUseTemplate(scope.row)">使用</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-tab-pane>
      <!-- 科室模版 -->
      <el-tab-pane label="科室" name="second">
        <!-- 表格区 -->
        <div>
          <el-table :data="departmentCaseTemplates">
            <el-table-column type="expand">
              <template slot-scope="props">
                <el-form label-position="top" class="demo-table-expand">
                  <el-form-item label="模版名称">
                    <span>{{props.row.name}}</span>
                  </el-form-item>
                  <el-form-item label="主诉">
                    <span>{{props.row.narrate}}</span>
                  </el-form-item>
                  <el-form-item label="现病史">
                    <span>{{props.row.curDisease}}</span>
                  </el-form-item>
                  <el-form-item label="既往史">
                    <span>{{props.row.pastDisease}}</span>
                  </el-form-item>
                  <el-form-item label="过敏信息">
                    <span>{{props.row.allergy}}</span>
                  </el-form-item>
                  <el-form-item label="体格检查">
                    <span>{{props.row.physicalCondition}}</span>
                  </el-form-item>
                  <el-form-item label="辅助检查">
                    <span>{{props.row.assistDiagnose}}</span>
                  </el-form-item>
                </el-form>
              </template>
            </el-table-column>
            <el-table-column label="模版名称" prop="name" width="150"></el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button type="text" size="mini" @click="handleUseTemplate(scope.row)">使用</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-tab-pane>
      <!-- 全院模版 -->
      <el-tab-pane label="全院" name="third">
        <!-- 表格区 -->
        <div>
          <el-table :data="hospitalCaseTemplates">
            <el-table-column type="expand">
              <template slot-scope="props">
                <el-form label-position="top" class="demo-table-expand">
                  <el-form-item label="模版名称">
                    <span>{{props.row.name}}</span>
                  </el-form-item>
                  <el-form-item label="主诉">
                    <span>{{props.row.narrate}}</span>
                  </el-form-item>
                  <el-form-item label="现病史">
                    <span>{{props.row.curDisease}}</span>
                  </el-form-item>
                  <el-form-item label="既往史">
                    <span>{{props.row.pastDisease}}</span>
                  </el-form-item>
                  <el-form-item label="过敏信息">
                    <span>{{props.row.allergy}}</span>
                  </el-form-item>
                  <el-form-item label="体格检查">
                    <span>{{props.row.physicalCondition}}</span>
                  </el-form-item>
                  <el-form-item label="辅助检查">
                    <span>{{props.row.assistDiagnose}}</span>
                  </el-form-item>
                </el-form>
              </template>
            </el-table-column>
            <el-table-column label="模版名称" prop="name" width="150"></el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button type="text" size="mini" @click="handleUseTemplate(scope.row)">使用</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import { listCaseTemplate } from "@/api/caseTemplate";
import { PassThrough } from "stream";

export default {
  name: "CaseTemplate",
  data() {
    return {
      dialogAddCaseTemplate: false,
      departmentCaseTemplates: [],
      hospitalCaseTemplates: [],
      myCaseTemplates: []
    };
  },
  methods: {
    handleUseTemplate(row) {
      this.$emit("give-template", row);
    }
  },
  mounted: function() {
    listCaseTemplate(this.$store.getters["user/currentRoleId"]).then(
      response => {
        const data = response.data.data;
        this.departmentCaseTemplates = data.departmentCaseTemplates;
        this.hospitalCaseTemplates = data.hospitalCaseTemplates;
        this.myCaseTemplates = data.myCaseTemplates;
      },
      error => {
        console.log("读取病理模版出bug了");
        console.log(error);
      }
    );
  }
};
</script>
<style lang="css" scoped>
</style>

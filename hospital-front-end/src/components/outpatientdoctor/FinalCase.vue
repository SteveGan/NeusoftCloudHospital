<template>
  <div class="outpatient-service-container">
    <div class="service-main-container">
      <!-- 工具栏 -->
      <el-card :body-style="{padding:'0px'}" style="margin-bottom: 5px">
        <el-button type="text" icon="el-icon-refresh-right" round>清屏</el-button>
        <el-button type="text" icon="el-icon-folder-checked" round>暂存</el-button>
        <el-button type="text" icon="el-icon-printer" round>打印</el-button>
        <el-button type="text" icon="el-icon-upload" round>提交</el-button>
      </el-card>
      <div>
        <el-card class="input-card" shadow="hover">
          <div slot="header">
            <span>最终诊断</span>
          </div>
          <!-- 中医诊断 -->
          <div class>
            <!-- 标题以及 + - 按钮 -->
            <div class="diagnose-header">
              <!-- 标题：中医诊断 -->
              <div class>
                <p>中医诊断</p>
              </div>
              <!-- 按钮组 -->
              <div style="margin-left: 10px;">
                <!-- 增加按钮 -->
                <el-button type="primary" icon="el-icon-plus" size="mini" circle></el-button>
                <!-- 减少按钮 -->
                <el-button type="primary" icon="el-icon-minus" size="mini" circle></el-button>
              </div>
            </div>
            <!-- 表格 -->
            <div>
              <el-table
                ref="multipleTable"
                tooltip-effect="dark"
                style="width: 100%"
                @selection-change="handleSelectTraDiagnoses"
              >
                <el-table-column type="selection" width="55"></el-table-column>
                <el-table-column label="ICD编码" prop="icdCode"></el-table-column>
                <el-table-column label="疾病名称" prop="diseaseName"></el-table-column>
                <el-table-column label="发病日期" prop="startTime"></el-table-column>
              </el-table>
            </div>
          </div>
          <!-- 西医诊断 -->
          <div class>
            <!-- 标题以及 + - 按钮 -->
            <div class="diagnose-header">
              <!-- 标题：西医诊断 -->
              <div class>
                <p>西医诊断</p>
              </div>
              <!-- 按钮组 -->
              <div style="margin-left: 10px;">
                <!-- 增加按钮 -->
                <el-button type="primary" icon="el-icon-plus" size="mini" circle></el-button>
                <!-- 减少按钮 -->
                <el-button type="primary" icon="el-icon-minus" size="mini" circle></el-button>
              </div>
            </div>
            <!-- 表格 -->
            <div>
              <el-table
                ref="multipleTable"
                tooltip-effect="dark"
                style="width: 100%"
                @selection-change="handleSelectModDiagnoses"
              >
                <el-table-column type="selection" width="55"></el-table-column>
                <el-table-column label="ICD编码" prop="icdCode"></el-table-column>
                <el-table-column label="疾病名称" prop="diseaseName"></el-table-column>
                <el-table-column label="发病日期" prop="startTime"></el-table-column>
              </el-table>
            </div>
          </div>
        </el-card>
      </div>
    </div>
    <!-- 新增中医诊断dialog -->
    <el-dialog title="新增中医诊断" :visible.sync="dialogAddTraDiagnose" :before-close="handleClose">
      <el-form :model="newTraditionalDisease" label-width="80px">
        <el-form-item label="疾病名称">
          <el-autocomplete
            class="inline-input"
            v-model="newTraditionalDisease.diseaseName"
            :fetch-suggestions="queryTraditionalDiseaseSearch"
            placeholder="请输入疾病名称"
            value-key="diseaseName"
            :highlight-first-item="true"
            @select="handleSelectTraDisease"
          ></el-autocomplete>
        </el-form-item>
        <el-form-item label="ICD编码">
          <el-input v-model="newTraditionalDisease.icdCode"></el-input>
        </el-form-item>
        <el-form-item label="发病日期">
          <el-date-picker
            type="date"
            placeholder="选择日期"
            v-model="newTraditionalDisease.startTime"
            format="yyyy 年 MM 月 dd 日"
            value-format="yyyy-MM-dd"
          ></el-date-picker>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelAdd">取消</el-button>
        <el-button type="primary" @click="addNewTraDisease">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 新增西医诊断dialog -->
    <el-dialog title="新增西医诊断" :visible.sync="dialogAddModDiagnose" :before-close="handleClose">
      <el-form :model="newModernDisease" label-width="80px">
        <el-form-item label="疾病名称">
          <el-autocomplete
            class="inline-input"
            v-model="newModernDisease.diseaseName"
            :fetch-suggestions="queryModernDiseaseSearch"
            placeholder="请输入疾病名称"
            value-key="diseaseName"
            :highlight-first-item="true"
            @select="handleSelectModDisease"
          ></el-autocomplete>
        </el-form-item>
        <el-form-item label="ICD编码">
          <el-input v-model="newModernDisease.icdCode"></el-input>
        </el-form-item>
        <el-form-item label="发病日期">
          <el-date-picker
            type="date"
            placeholder="选择日期"
            v-model="newModernDisease.startTime"
            format="yyyy 年 MM 月 dd 日"
            value-format="yyyy-MM-dd"
          ></el-date-picker>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelAdd">取消</el-button>
        <el-button type="primary" @click="addNewModDisease">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import CaseTemplate from "./CaseTemplate";
import DiagnoseTemplate from "./DiagnoseTemplate";
import { listAllDiseases } from "@/api/disease";
import { delimiter } from "path";
import { constants } from "fs";
import { currentDate } from "@/utils/date";

export default {
  name: "FinalCase",
  components: {
    "case-template": CaseTemplate,
    "diagnose-template": DiagnoseTemplate
  },
  data() {
    return {
      labelPosition: "left",
      dialogAddTraDiagnose: false,
      dialogAddModDiagnose: false,
      traditionalDiseases: [],
      modernDiseases: [],
      newTraditionalDisease: {},
      newModernDisease: {}
    };
  },
  props: {
    value: Object
  },
  computed: {
    finalCase: {
      get() {
        return this.value;
      },
      set(v) {
        this.$emit("input", v);
      }
    }
  },
  methods: {
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then(_ => {
          done();
        })
        .catch(_ => {});
    },
    queryTraditionalDiseaseSearch(queryString, cb) {
      var traditionalDiseases = this.traditionalDiseases;
      var results = queryString
        ? traditionalDiseases.filter(this.createFilter(queryString))
        : traditionalDiseases;
      cb(results);
    },
    queryModernDiseaseSearch(queryString, cb) {
      var modernDiseases = this.modernDiseases;
      var results = queryString
        ? modernDiseases.filter(this.createFilter(queryString))
        : modernDiseases;
      cb(results);
    },
    createFilter(queryString) {
      return disease => {
        return (
          disease.diseaseName
            .toLowerCase()
            .indexOf(queryString.toLowerCase()) === 0
        );
      };
    },
    handleSelectTraDisease(item) {
      console.log(item);
      this.newTraditionalDisease.icdCode = item.icdCode;
    },
    handleSelectModDisease(item) {
      console.log(item);
      this.newModernDisease.icdCode = item.icdCode;
    },
    cancelAdd() {
      this.dialogAddTraDiagnose = false;
      this.newTraditionalDisease = {};
      this.newModernDisease = {};
    },
    addNewTraDisease() {
      this.dialogAddTraDiagnose = false;
      //将新的中医诊断加入当前病历中
      // this.currentCase.traditionalDiagnose.push(
      //   JSON.parse(JSON.stringify(this.newTraditionalDisease))
      // );
      //将新的中医诊断清空
      this.newTraditionalDisease = {};
    },
    addNewModDisease() {
      this.dialogAddModDiagnose = false;
      // this.currentCase.modernDiagnose.push(
      //   JSON.parse(JSON.stringify(this.newModernDisease))
      // );
    }
  }
};
</script>

<style lang="css" scoped>
.side-bar {
  height: 100%;
  padding: 10px;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  background-color: #fafafa;
}

.search-user {
  height: 30px;
  margin-bottom: 20px;
}

.current-user {
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
}

.basic-info {
  padding-top: 4px;
  font-size: 15px;
  margin-right: auto;
}

.info-card {
  margin-bottom: 4px;
}

.outpatient-service-container {
  display: grid;
  grid-template-columns: 70% 30%;
}

.service-main-container {
  grid-column: 1/2;
  margin-left: 2px;
}

.service-side-container {
  grid-column: 2;
  margin-right: 2px;
}

.recipe-service-container {
  display: flex;
  flex-direction: column;
}

.recipe-template {
  display: grid;
  grid-template-columns: 40% 60%;
}

.recipe-browser {
  grid-column: 1/2;
  padding: 2px;
}

.recipe-detail {
  grid-column: 2;
  padding: 2px;
}

.template-tabs {
  position: -webkit-sticky;
  position: sticky;
  top: 0px;
}

.tool-bar {
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  margin-bottom: 5px;
}

.input-card {
  margin-top: 5px;
  margin-right: 10px;
}

.diagnose-header {
  display: flex;
  flex-direction: row;
  align-items: center;
}

.application-number {
  background-color: #597ef7;
  border-radius: 5px;
  font-size: 15px;
  padding: 0px 3px;
  color: white;
}

.service-main-container .el-card {
  margin-right: 5px;
}

.add-recipe-button {
  margin-top: 5px;
  margin-bottom: 5px;
}
</style>
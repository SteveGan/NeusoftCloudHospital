<template lang="html">
  <!-- 门诊病历首页内容 -->
  <div class="outpatient-service-container">
    <el-button @click="logCurrentCase">log当前的病历</el-button>
    <!-- 左侧操作区 -->
    <div class="service-main-container">
      <!-- 工具栏 -->
      <!-- 工具栏 -->
      <el-card :body-style="{padding:'0px'}" style="margin-bottom: 5px">
        <el-button type="text" icon="el-icon-refresh-right" @click="handleClear" round>清屏</el-button>
        <el-button type="text" icon="el-icon-folder-checked" @click="handleSave" round>暂存</el-button>
        <el-button type="text" icon="el-icon-printer" round>打印</el-button>
        <el-button type="text" icon="el-icon-upload" @click="handleSubmit" round>提交</el-button>
      </el-card>
      <!-- 操作 -->
      <div class="">
        <!-- 病史病历 -->
        <el-card class="input-card" shadow="hover">
          <div slot="header">
            <span>病史病历</span>
          </div>
          <el-form :model="currentCase" label-position='left'>
            <p>{{currentCase.diagnoses}}</p>
            <el-form-item label="主诉">
              <el-input v-model="currentCase.narrate" type="textarea" autosize placeholder="请输入内容">
              </el-input>
            </el-form-item>
            <el-form-item label="现病史">
              <el-input v-model="currentCase.curDisease" type="textarea" autosize placeholder="请输入内容">
              </el-input>
            </el-form-item>
            <el-form-item label="既往史">
              <el-input v-model="currentCase.pastDisease" type="textarea" autosize placeholder="请输入内容">
              </el-input>
            </el-form-item>
            <el-form-item  label="过敏信息">
              <el-input v-model="currentCase.allergy" type="textarea" autosize placeholder="请输入内容">
              </el-input>
            </el-form-item>
          </el-form>
        </el-card>
        <el-card class="input-card" shadow="hover">
          <div slot="header">
            <span>检查及结果</span>
          </div>
          <el-form label-position='left' :model="currentCase">
            <el-form-item label="体格检查">
              <el-input v-model="currentCase.physicalCondition" type="textarea" autosize placeholder="请输入内容">
              </el-input>
            </el-form-item>
            <el-form-item label="辅助检查">
              <el-input v-model="currentCase.assistDiagnose" type="textarea" autosize placeholder="请输入内容">
              </el-input>
            </el-form-item>
          </el-form>
        </el-card>
      </el-card>
      <el-card class="input-card" shadow="hover">
        <div slot="header">
          <span>评估诊断</span>
        </div>
        <!-- 中医诊断 -->
        <div class="">
          <!-- 标题以及 + - 按钮 -->
          <div class="diagnose-header">
            <!-- 标题：中医诊断 -->
            <div class="">
              <p>中医诊断</p>
            </div>
            <!-- 按钮组 -->
            <div style="margin-left: 10px;">
              <!-- 增加按钮 -->
              <el-button type="primary" icon="el-icon-plus" size="mini" circle @click="dialogAddTraDiagnose=true"></el-button>
              <!-- 减少按钮 -->
              <el-button type="primary" icon="el-icon-minus" size="mini" circle @click="handleRemoveDiagnoses(currentCase.traditionalDiagnose, selectedTraDiagnoses)"></el-button>
            </div>
          </div>
          <!-- 表格 -->
          <div>
            <el-table
              ref="multipleTable"
              tooltip-effect="dark"
              style="width: 100%"
              :data="currentCase.traditionalDiagnose"
              @selection-change="handleSelectTraDiagnoses">
              <el-table-column
                type="selection"
                width="55">
              </el-table-column>
              <el-table-column
                label="ICD编码"
                prop="icdCode">
              </el-table-column>
              <el-table-column
                label="疾病名称"
                prop="diseaseName">
              </el-table-column>
              <el-table-column
                label="发病日期"
                prop="startTime">
              </el-table-column>
            </el-table>
          </div>
        </div>
        <!-- 西医诊断 -->
        <div class="">
          <!-- 标题以及 + - 按钮 -->
          <div class="diagnose-header">
            <!-- 标题：西医诊断 -->
            <div class="">
              <p>西医诊断</p>
            </div>
            <!-- 按钮组 -->
            <div style="margin-left: 10px;">
              <!-- 增加按钮 -->
              <el-button type="primary" icon="el-icon-plus" size="mini" circle @click="dialogAddModDiagnose=true"></el-button>
              <!-- 减少按钮 -->
              <el-button type="primary" icon="el-icon-minus" size="mini" circle @click="handleRemoveDiagnoses(currentCase.modernDiagnose, selectedModDiagnoses)"></el-button>
            </div>
          </div>
          <!-- 表格 -->
          <div>
            <el-table
              ref="multipleTable"
              tooltip-effect="dark"
              style="width: 100%"
              :data="currentCase.modernDiagnose"
              @selection-change="handleSelectModDiagnoses">
              <el-table-column
                type="selection"
                width="55">
              </el-table-column>
              <el-table-column
                label="ICD编码"
                prop="icdCode">
              </el-table-column>
              <el-table-column
                label="疾病名称"
                prop="diseaseName">
              </el-table-column>
              <el-table-column
                label="发病日期"
                prop="startTime">
              </el-table-column>
            </el-table>
          </div>
        </div>
      </el-card>
      </div>
    </div>
    <!-- 右侧模版区域 -->
    <div class="service-side-container">
      <!-- 导航栏(也就是一个标签页) -->
      <el-tabs type="border-card" class="template-tabs">
        <!-- 门诊首页tab-->
        <el-tab-pane label="病历模版">
          <case-template @give-template="useTemplate"></case-template>
        </el-tab-pane>
        <el-tab-pane label="常用诊断">
          
        </el-tab-pane>
        <el-tab-pane label="历史病历">

        </el-tab-pane>
      </el-tabs>
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
             :highlight-first-item=true
             @select="handleSelectTraDisease"
           ></el-autocomplete>
        </el-form-item>
        <el-form-item label="ICD编码">
          <el-input v-model="newTraditionalDisease.icdCode"></el-input>
        </el-form-item>
        <el-form-item label="发病日期">
          <el-date-picker type="date" 
            placeholder="选择日期" 
            v-model="newTraditionalDisease.startTime" 
            format="yyyy 年 MM 月 dd 日"
            value-format="yyyy-MM-dd">
          </el-date-picker>
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
             :highlight-first-item=true
             @select="handleSelectModDisease"
           ></el-autocomplete>
        </el-form-item>
        <el-form-item label="ICD编码">
          <el-input v-model="newModernDisease.icdCode"></el-input>
        </el-form-item>
        <el-form-item label="发病日期">
          <el-date-picker type="date" 
            placeholder="选择日期" 
            v-model="newModernDisease.startTime" 
            format="yyyy 年 MM 月 dd 日"
            value-format="yyyy-MM-dd">
          </el-date-picker>
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
import { listAllDiseases } from "@/api/disease";
import { delimiter } from "path";
import { constants } from "fs";

export default {
  name: "OutPatientPreDiagnose",
  components: {
    "case-template": CaseTemplate
  },
  data() {
    return {
      labelPosition: "left",
      dialogAddTraDiagnose: false,
      dialogAddModDiagnose: false,
      traditionalDiseases: [],
      modernDiseases: [],
      newTraditionalDisease: {},
      newModernDisease: {},
      selectedTraDiagnoses: [],
      selectedModDiagnoses: []
    };
  },
  props: {
    value: Object
  },
  computed: {
    currentCase: {
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
      this.currentCase.traditionalDiagnose.push(
        JSON.parse(JSON.stringify(this.newTraditionalDisease))
      );
      //将新的中医诊断清空
      this.newTraditionalDisease = {};
    },
    addNewModDisease() {
      this.dialogAddModDiagnose = false;
      this.currentCase.modernDiagnose.push(
        JSON.parse(JSON.stringify(this.newModernDisease))
      );
    },
    handleRemoveDiagnoses(allDiagnoses, selectedDiagnoses) {
      //遍历所有被选中的selecteDiagnoses

      var i;
      for (i = 0; i < selectedDiagnoses.length; i++) {
        console.log("to be deleted:");
        console.log(selectedDiagnoses[i]);
        allDiagnoses.splice(
          allDiagnoses.findIndex(
            disease => disease.icdCode === selectedDiagnoses[i].icdCode
          ),
          1
        );
      }
    },
    handleSelectTraDiagnoses(val) {
      this.selectedTraDiagnoses = val;
      console.log(val);
    },
    handleSelectModDiagnoses(val) {
      this.selectedModDiagnoses = val;
      console.log(val);
    },

    handleSave() {
      this.$emit("saveCase");
    },
    handleSubmit() {
      this.$emit("submitCase");
    },
    handleClear() {
      this.$emit("clearCase");
    },
    logCurrentCase() {
      console.log(this.currentCase);
    },
    //使用病历模版组件中传来的template
    useTemplate(caseTemplate) {
      this.currentCase.narrate = caseTemplate.narrate;
      this.currentCase.curDisease = caseTemplate.curDisease;
      this.currentCase.pastDisease = caseTemplate.pastDisease;
      this.currentCase.allergy = caseTemplate.allergy;
      this.currentCase.physicalCondition = caseTemplate.physicalCondition;
      this.currentCase.traditionalDiagnose = caseTemplate.traditionalDiseases;
      this.currentCase.assistDiagnose = caseTemplate.assistDiagnose;
    }
  },
  mounted: function() {
    // 读取所有的中医疾病
    listAllDiseases(0).then(
      response => {
        const data = response.data.data;
        this.traditionalDiseases = data.disease;
      },
      error => {
        console.log("读取中医疾病出bug了");
      }
    );
    // 读取所有的西医疾病
    listAllDiseases(1).then(
      response => {
        const data = response.data.data;
        this.modernDiseases = data.disease;
      },
      error => {
        console.log("读取西医疾病出bug了");
      }
    );
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
  margin-top: 38px;
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

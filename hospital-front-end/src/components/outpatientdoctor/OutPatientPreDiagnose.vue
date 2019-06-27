<template lang="html">
  <!-- 门诊病历首页内容 -->
  <div class="outpatient-service-container">
    <!-- 左侧操作区 -->
    <div class="service-main-container">
      <!-- 工具栏 -->
      <!-- 工具栏 -->
      <el-card :body-style="{padding:'0px'}" style="margin-bottom: 5px">
        <el-button type="text" icon="el-icon-refresh-right" @click="handleClear" round :disabled="!editable">清屏</el-button>
        <el-button type="text" icon="el-icon-folder-checked" @click="handleSave" round :disabled="!editable">暂存</el-button>
        <el-button type="text" icon="el-icon-printer" round>打印</el-button>
        <el-button type="text" icon="el-icon-upload" @click="handleSubmit" round :disabled="!editable">提交</el-button>
      </el-card>
      <!-- 操作 -->
      <div class="">
        <!-- 病史病历 -->
        <el-card class="input-card" shadow="hover">
          <div slot="header">
            <i class="el-icon-document-copy"></i>
            <span>病史病历</span>
          </div>
          <el-form :model="currentCase" label-position='left' :rules="historyRules">
            <p>{{currentCase.diagnoses}}</p>
            <el-form-item label="主诉" prop="narrate">
              <el-input v-model="currentCase.narrate" type="textarea" autosize placeholder="请输入内容" :disabled="!editable">
              </el-input>
            </el-form-item>
            <el-form-item label="现病史" prop="curDisease">
              <el-input v-model="currentCase.curDisease" type="textarea" autosize placeholder="请输入内容" :disabled="!editable">
              </el-input>
            </el-form-item>
            <el-form-item label="既往史" prop="pastDisease">
              <el-input v-model="currentCase.pastDisease" type="textarea" autosize placeholder="请输入内容" :disabled="!editable">
              </el-input>
            </el-form-item>
            <el-form-item  label="过敏信息" prop="allergy">
              <el-input v-model="currentCase.allergy" type="textarea" autosize placeholder="请输入内容" :disabled="!editable">
              </el-input>
            </el-form-item>
          </el-form>
        </el-card>
        <el-card class="input-card" shadow="hover">
          <div slot="header">
            <i class="el-icon-headset"></i>
            <span>检查及结果</span>
          </div>
          <el-form label-position='left' :model="currentCase" :rules="examinationRules">
            <el-form-item label="体格检查" prop="physicalCondition">
              <el-input v-model="currentCase.physicalCondition" type="textarea" autosize placeholder="请输入内容" :disabled="!editable">
              </el-input>
            </el-form-item>
            <el-form-item label="辅助检查" prop="assistDiagnose">
              <el-input v-model="currentCase.assistDiagnose" type="textarea" autosize placeholder="请输入内容" :disabled="!editable">
              </el-input>
            </el-form-item>
          </el-form>
        </el-card>
      <el-card class="input-card" shadow="hover">
        <div slot="header">
          <i class="el-icon-analysis"></i>
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
              <el-button type="primary" icon="el-icon-plus" size="mini" circle @click="dialogAddTraDiagnose=true" :disabled="!ableEditTraDiagnose || !editable"></el-button>
              <!-- 减少按钮 -->
              <el-button type="primary" icon="el-icon-minus" size="mini" circle @click="handleRemoveDiagnoses(currentCase.traditionalDiagnose, selectedTraDiagnoses)" :disabled="!ableEditTraDiagnose || !editable"></el-button>
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
              <el-button type="primary" icon="el-icon-plus" size="mini" circle @click="dialogAddModDiagnose=true" :disabled="!ableEditModDiagnose || !editable"></el-button>
              <!-- 减少按钮 -->
              <el-button type="primary" icon="el-icon-minus" size="mini" circle @click="handleRemoveDiagnoses(currentCase.modernDiagnose, selectedModDiagnoses)" :disabled="!ableEditModDiagnose || !editable"></el-button>
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
      <el-tabs type="card" class="template-tabs">
        <!-- 门诊首页tab-->
        <el-tab-pane label="病历模版">
          <case-template @give-template="useTemplate"></case-template>
        </el-tab-pane>
        <el-tab-pane label="常用诊断">
          <diagnose-template @give-disease="useDisease"></diagnose-template>
        </el-tab-pane>
        <!-- <el-tab-pane label="历史病历">
        </el-tab-pane> -->
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
import DiagnoseTemplate from "./DiagnoseTemplate";
import { listAllDiseases } from "@/api/disease";
import { delimiter } from "path";
import { constants } from "fs";
import { currentDate } from "@/utils/date";
import { successDialog, failDialog } from "@/utils/notification";

export default {
  name: "OutPatientPreDiagnose",
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
      newModernDisease: {},
      selectedTraDiagnoses: [],
      selectedModDiagnoses: [],
      historyRules: {
        narrate: [{ required: true, message: "请输入主诉", trigger: "blur" }],
        curDisease: [
          { required: true, message: "请输入现病史", trigger: "blur" }
        ],
        pastDisease: [
          { required: true, message: "请输入既往史", trigger: "blur" }
        ],
        allergy: [
          { required: true, message: "请输入过敏信息", trigger: "blur" }
        ]
      },
      examinationRules: {
        physicalCondition: [
          { required: true, message: "请输入体格检查结果", trigger: "blur" }
        ],
        assistDiagnose: [
          { required: true, message: "请输入辅助检查结果", trigger: "blur" }
        ]
      }
    };
  },
  props: {
    value: Object,
    editable: Boolean
  },
  computed: {
    currentCase: {
      get() {
        return this.value;
      },
      set(v) {
        this.$emit("input", v);
      }
    },
    ableEditTraDiagnose: function() {
      if (Object.keys(this.currentCase).length !== 0) {
        if (this.currentCase.modernDiagnose.length === 0) {
          return true;
        } else {
          return false;
        }
      } else {
        return false;
      }
    },
    ableEditModDiagnose: function() {
      if (Object.keys(this.currentCase).length !== 0) {
        if (this.currentCase.traditionalDiagnose.length === 0) {
          return true;
        } else {
          return false;
        }
      } else {
        return false;
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
      this.newTraditionalDisease.icdCode = item.icdCode;
    },
    handleSelectModDisease(item) {
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
    },
    handleSelectModDiagnoses(val) {
      this.selectedModDiagnoses = val;
    },

    handleSave() {
      this.$emit("saveCase", this.ableEditTraDiagnose);
    },
    handleSubmit() {
      if (
        this.currentCase.narrate !== null ||
        this.currentCase.curDisease !== null ||
        this.currentCase.pastDisease !== null ||
        this.currentCase.allergy !== null ||
        this.currentCase.physicalCondition !== null ||
        this.currentCase.assistDiagnose !== null
      ) {
        if (
          this.currentCase.narrate.trim() !== "" ||
          this.currentCase.curDisease.trim() !== "" ||
          this.currentCase.pastDisease.trim() !== "" ||
          this.currentCase.allergy.trim() !== "" ||
          this.currentCase.physicalCondition.trim() !== "" ||
          this.currentCase.assistDiagnose.trim() !== ""
        ) {
          this.$emit("submitCase", this.ableEditTraDiagnose);
        }
      } else {
        failDialog("请输入必填项");
      }
    },
    handleClear() {
      this.$emit("clearCase");
    },
    //使用病历模版组件中传来的template
    useTemplate(caseTemplate) {
      this.currentCase.narrate = caseTemplate.narrate;
      this.currentCase.curDisease = caseTemplate.curDisease;
      this.currentCase.pastDisease = caseTemplate.pastDisease;
      this.currentCase.allergy = caseTemplate.allergy;
      this.currentCase.physicalCondition = caseTemplate.physicalCondition;
      this.currentCase.assistDiagnose = caseTemplate.assistDiagnose;
    },
    //添加常用诊断组件传来的疾病
    useDisease(diagnoseTemplate) {
      //如果是西医疾病,添加到西医诊断中
      if (diagnoseTemplate.type === 1) {
        this.currentCase.modernDiagnose.push({
          icdCode: diagnoseTemplate.icdCode,
          diseaseName: diagnoseTemplate.diseaseName,
          startTime: currentDate()
        });
      } else {
        this.currentCase.traditionalDiagnose.push({
          icdCode: diagnoseTemplate.icdCode,
          diseaseName: diagnoseTemplate.diseaseName,
          startTime: currentDate()
        });
      }
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
  margin-left: 15px;
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
  margin-top: 20px;
  margin-bottom: 0;
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

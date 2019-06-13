<template lang="html">
  <el-container style="height: 90vh;">
    <el-aside width="300px">
      <!-- 侧栏区域 -->
      <div class="side-bar">
        <!-- 搜索区 -->
        <div class="search-user">
          <el-input placeholder="病人病历" class="input-with-select">
            <el-button slot="append" icon="el-icon-search"></el-button>
          </el-input>
        </div>
        <!-- 待诊用户区 -->
        <el-card shadow="hover">
          <div slot="header">
            <span>待就诊</span>
          </div>
          <!-- 待就诊病人名单 -->
          <div class="">
            <!-- 表格 -->
            <el-table
              :data="waitingPatients"
              style="width: 100%">
              <el-table-column
                prop="caseId"
                label="病历号">
              </el-table-column>
              <el-table-column
                prop="name"
                label="患者姓名">
              </el-table-column>
              <el-table-column
                fixed="right"
                width='50px'>
                <template slot-scope="scope">
                  <el-button @click="handlePatientSelect(scope.row)" type="text" size="small">诊治</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-card>

        <!-- 已诊用户区 -->
        <el-card shadow="hover">
          <div slot="header">
            <span>已就诊</span>
          </div>
          <!-- 待就诊病人名单 -->
          <div class="">
            <!-- 表格 -->
            <el-table
              :data="diagnosedPatients"
              style="width: 100%;">
              <el-table-column
                prop="caseId"
                label="病历号">
              </el-table-column>
              <el-table-column
                prop="name"
                label="患者姓名">
              </el-table-column>
              <el-table-column
                fixed="right"
                width='50px'>
              <template slot-scope="scope">
  <el-button @click="handlePatientSelect(scope.row)" type="text" size="small">诊治</el-button>
</template>
              </el-table-column>
            </el-table>
          </div>
        </el-card>
      </div>
    </el-aside>
    <el-main>
      <!-- 当前病人信息 -->
      <el-card shadow="hover" :body-style="{ padding: '5px'}" class="info-card">
        <div class="current-user">
          <!-- 基本信息 -->
          <div class="basic-info">
            <!-- 就诊状态 -->
            <span>就诊状态: {{this.status}} </span>
            <!-- 病历号 -->
            <span>病历号: {{this.selectedCase.caseId}} </span>
            <!-- 姓名 -->
            <span>姓名: {{selectedPatient.name}} </span>
            <!-- 性别 -->
            <span>性别: {{this.gender}} </span>
            <!-- 年龄 -->
            <span>年龄: {{this.selectedPatient.age}} </span>
            <!-- 结算类别 -->
            <span>结算类别: TODO </span>
          </div>
          <!-- 诊毕 -->
          <el-button type="primary">诊毕</el-button>
          </div>
        </div>
      </el-card>
      <!-- 导航栏(也就是一个标签页) -->
      <el-tabs type="border-card" style="overflow:vible">
        <!-- 门诊首页tab-->
        <el-tab-pane label="病历首页">
          <!-- 门诊病历首页内容 -->
          <outpatient-prediagnose 
            @saveCase="onSaveSelectedCase" 
            @submitCase="onSubmitSelectedCase" 
            @clearCase="onClearSelectedCase"
            v-model="selectedCase">
          </outpatient-prediagnose>
        </el-tab-pane>
        <el-tab-pane label="病历确诊">
          <!-- 门诊病历确诊内容 -->
          <final-diagnose 
            @saveCase="onSaveSelectedCase" 
            @submitCase="onSubmitSelectedCase" 
            @clearCase="onClearSelectedCase"
            v-model="selectedCase">
          </final-diagnose >
        </el-tab-pane>
        <el-tab-pane label="检验申请">
          <project-application :type=1 typeName="检验" v-model="selectedCaseExaminations"></project-application>
        </el-tab-pane>
        <el-tab-pane label="检查申请">
          <project-application :type=2 typeName="检查" v-model="selectedCaseInspections"></project-application>
        </el-tab-pane>
        <el-tab-pane label="成药处方" :disabled="disableModRecipe">
          <case-recipe v-model="modernRecipes"></case-recipe>
        </el-tab-pane>
        <el-tab-pane label="草药处方" :disabled="disableTraRecipe">
          <case-recipe v-model="traditionalRecipes"></case-recipe>
        </el-tab-pane>
        <el-tab-pane label="处置单">
          <case-disposition v-model="selectedCaseDispositions"></case-disposition>
        </el-tab-pane>
        <el-tab-pane label="患者账单">
          <el-card>
            <!-- 操作栏 -->
            <div slot="header" class="clearfix">
                <span>申请单列表</span>
            </div>
            <!-- 项目列表 -->
            <div class="">
              <el-table
                style="width: 100%">
                <el-table-column
                  type="selection"
                  width="55">
                </el-table-column>
                <el-table-column
                  label="名称">
                </el-table-column>
                <el-table-column
                  label="规格">
                </el-table-column
                  label="数量">
                <el-table-column
                  label="付数">
                </el-table-column>
                <el-table-column
                  label="单位">
                </el-table-column>
                <el-table-column
                  label="金额">
                </el-table-column>
                <el-table-column
                  label="收费状态">
                </el-table-column>
              </el-table>
            </div>
          </el-card>
        </el-tab-pane>
        <el-tab-pane label="病历模版管理">
          <case-template-admin></case-template-admin>
        </el-tab-pane>
      </el-tabs>
    </el-main>

    <!-- 语音播报 -->
    <div id="bdtts_div_id">
      <audio id="tts_autio_id" autoplay="autoplay">
        <source id="tts_source_id" src="http://tts.baidu.com/text2audio?lan=zh&amp;ie=UTF-8&amp;spd=1&amp;per=0&amp;vol=15&amp;text=" type="audio/mpeg">
        <embed id="tts_embed_id" height="0" width="0" src="">
      </audio>
    </div>
  </el-container>
</template>

<script>
import { listAllPatients } from "@/api/patient";
import { listAllCollections } from "@/api/project";
import { getCaseContent, submitCase, saveCase, clearCase } from "@/api/case";
import { listCaseRecipes } from "@/api/recipe";
import {
  caseStatusCodeToString,
  genderCodeToString
} from "@/utils/interpreter";

import OutPatientPreDiagnose from "@/components/outpatientdoctor/OutPatientPreDiagnose";
import CaseTemplateAdmin from "@/components/outpatientdoctor/CaseTemplateAdmin";
import ProjectApplication from "@/components/outpatientdoctor/ProjectApplication";
import CaseRecipe from "@/components/outpatientdoctor/CaseRecipe";
import CaseDisposition from "@/components/outpatientdoctor/CaseDisposition";
import FinalDiagnose from "@/components/outpatientdoctor/FinalDiagnose";
import { constants } from "fs";

export default {
  name: "OutPatientDoctor",
  data() {
    return {
      waitingPatients: [],
      diagnosedPatients: [],
      selectedPatient: { name: "haha" },
      selectedCase: {},
      selectedCaseExaminations: {},
      selectedCaseInspections: {},
      selectedCaseDispositions: {},
      modernDisease: [],
      traditionalDisease: [],
      traditionalRecipes: {},
      modernRecipes: {},
      disableModRecipe: false,
      disableTraRecipe: false
    };
  },
  computed: {
    status: function() {
      return caseStatusCodeToString(this.selectedCase.status);
    },
    gender: function() {
      return genderCodeToString(this.selectedPatient.gender);
    }
  },
  methods: {
    doTTS(name) {
      var ttsDiv = document.getElementById('bdtts_div_id');
      var ttsAudio = document.getElementById('tts_autio_id');
      var ttsText = name;

      // 文字转语音
      ttsDiv.removeChild(ttsAudio);
      var au1 = '<audio id="tts_autio_id" autoplay="autoplay">';
      var sss = '<source id="tts_source_id" src="http://tsn.baidu.com/text2audio?lan=zh&ie=UTF-8&per=3&spd=14&vol=15&text=' + ttsText + '" type="audio/mpeg">';
      var eee = '<embed id="tts_embed_id" height="0" width="0" src="">';
      var au2 = '</audio>';
      ttsDiv.innerHTML = au1 + sss + eee + au2;

      ttsAudio = document.getElementById('tts_autio_id');

      ttsAudio.play();
    },
    handlePatientSelect(row) {
      // 语音播报
      this.doTTS("请" + row.name + "到" + "妇产科" + "就诊");

      // 将当前的用户设置为被点击的用户
      this.selectedPatient = Object.assign({}, row);
      //请求当前被点击用户的当前病历信息
      getCaseContent({
        roleId: this.$store.getters["user/currentRoleId"],
        caseId: this.selectedPatient.caseId
      }).then(
        response => {
          const caseContent = response.data.data;
          this.selectedCase = Object.assign({}, caseContent);
        },
        error => {
          //暂时不处理
          console.alert("得到病历内容出Bug了");
        }
      );
      //请求当前被点击用户的所有检查项目清单
      listAllCollections(this.selectedPatient.caseId, 1).then(
        response => {
          this.selectedCaseExaminations = Object.assign({}, response.data.data);
          this.selectedCaseExaminations.caseId = this.selectedPatient.caseId;
          console.log("读取到的检查项目数据");
          console.log(response);
        },
        error => {
          //暂时不处理
          console.alert("得到检查内容出Bug了");
        }
      );
      //请求当前被点击用户的所有检验项目清单
      listAllCollections(this.selectedPatient.caseId, 2).then(
        response => {
          this.selectedCaseInspections = Object.assign({}, response.data.data);
          this.selectedCaseInspections.caseId = this.selectedPatient.caseId;
          console.log("读取到的检查项目数据");
          console.log(response);
        },
        error => {
          //暂时不处理
          console.alert("得到检验内容出Bug了");
        }
      );
      //请求当前被点击用户病历的所有处置信息
      listAllCollections(this.selectedPatient.caseId, 3).then(
        response => {
          this.selectedCaseDispositions = Object.assign({}, response.data.data);
          this.selectedCaseDispositions.caseId = this.selectedPatient.caseId;
        },
        error => {
          //暂时不处理
          console.alert("得到处置内容出Bug了");
        }
      );
      //请求当前被点击用户的病历所有的处方
      listCaseRecipes(this.selectedPatient.caseId).then(
        response => {
          console.log("所有的处方信息");
          console.log(response.data.data);
          var caseRecipe = response.data.data;
          caseRecipe.caseId = this.selectedPatient.caseId;
          if (caseRecipe.type === 1) {
            //如果是西医处方
            this.modernRecipes = Object.assign({}, caseRecipe);
            this.disableTraRecipe = true;
            this.disableModRecipe = false;
          } else if (caseRecipe.type == 2) {
            //如果是中医处方
            this.traditionalRecipes = Object.assign({}, caseRecipe);
            this.disableTraRecipe = false;
            this.disableModRecipe = true;
          } else {
            //不存在处方
            caseRecipe.type = 1;
            this.modernRecipes = Object.assign({}, caseRecipe);
            caseRecipe.type = 2;
            this.traditionalRecipes = Object.assign({}, caseRecipe);
            this.disableTraRecipe = false;
            this.disableModRecipe = false;
          }
        },
        error => {
          //暂时不处理
          console.alert("查所有处方出bug了");
        }
      );
    },
    //暂存case的内容
    onSaveSelectedCase(isTraDiagnose) {
      console.log(isTraDiagnose);
      this.selectedCase.diagnoseType = isTraDiagnose ? 0 : 1;
      console.log("save:");
      console.log(this.selectedCase);
      saveCase(this.selectedCase).then(
        response => {
          console.log(response);
        },
        error => {
          console.log(error);
        }
      );
    },
    //上传case的内容
    onSubmitSelectedCase(isTraDiagnose) {
      this.selectedCase.diagnoseType = isTraDiagnose ? 0 : 1;
      console.log("submit:");
      console.log(this.selectedCase);
      submitCase(this.selectedCase).then(
        response => {
          console.log(response);
        },
        error => {
          console.log(error);
        }
      );
    },
    //清空case内容
    onClearSelectedCase() {
      console.log("clear:");
      console.log(this.selectedCase);
      clearCase(this.selectedCase.caseId).then(
        response => {
          console.log(response);
        },
        error => {
          console.log(response);
        }
      );
      this.selectedCase = {};
    }
  },
  components: {
    "outpatient-prediagnose": OutPatientPreDiagnose,
    "case-template-admin": CaseTemplateAdmin,
    "project-application": ProjectApplication,
    "case-recipe": CaseRecipe,
    "case-disposition": CaseDisposition,
    "final-diagnose": FinalDiagnose
  },
  mounted: function() {
    //请求所有待诊病人和已诊病人
    console.log("当前的roleId");
    console.log(this.$store.getters["user/currentRoleId"]);
    listAllPatients(this.$store.getters["user/currentRoleId"]).then(
      response => {
        const data = response.data.data;
        this.waitingPatients = data.waitingPatients;
        this.diagnosedPatients = data.diagnosedPatients;
        // console.log(this.waitingPatients)
      },
      error => {
        console.alert("请求所有病人出Bug了");
      }
    );
    // //请求所有中医疾病和西医疾病
    // listAllDisease().then( response => {
    //   const data = response.data.data
    //   this.modernDisease = data.modernDisease
    //   this.traditionalDisease = data.traditionalDisease
    // }, error => {
    //   //暂时不做处理
    // })
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

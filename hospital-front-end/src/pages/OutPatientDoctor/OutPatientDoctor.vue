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
            <!-- <span>结算类别: TODO </span> -->
          </div>
          <!-- 诊毕 -->
          <el-button type="primary" @click="handleFinish" :disabled="disableFinish">诊毕</el-button>
          </div>
        </div>
      </el-card>
      <!-- 导航栏(也就是一个标签页) -->
      <el-tabs type="border-card" style="overflow:vible" tab-click="testTabClick">
        <!-- 门诊首页tab-->
        <el-tab-pane label="病历首页">
          <!-- 门诊病历首页内容 -->
          <outpatient-prediagnose 
            @saveCase="onSaveSelectedCase" 
            @submitCase="onSubmitSelectedCase" 
            @clearCase="onClearSelectedCase"
            v-model="selectedCase"
            :editable="ableEditPreDiagnose"
            :disabled="disablePreDiagnose">
          </outpatient-prediagnose>
        </el-tab-pane>
        <el-tab-pane label="病历确诊" :disabled="disableFinalDiagnose">
          <final-case v-model="selectedFinalCase" @save-final-case="onSaveFinalCase" @submit-final-case="onSubmitFinalCase" :editable="ableEditFinalDiagnose"></final-case>
        </el-tab-pane>
        <el-tab-pane label="检验申请" :disabled="disableExamination">
          <project-application :type=1 typeName="检验" v-model="selectedCaseExaminations"></project-application>
        </el-tab-pane>
        <el-tab-pane label="检查申请" :disabled="disableInspection">
          <project-application :type=2 typeName="检查" v-model="selectedCaseInspections"></project-application>
        </el-tab-pane>
        <el-tab-pane label="成药处方" :disabled="disableModRecipe">
          <case-recipe v-model="modernRecipes"></case-recipe>
        </el-tab-pane>
        <el-tab-pane label="草药处方" :disabled="disableTraRecipe">
          <case-recipe v-model="traditionalRecipes"></case-recipe>
        </el-tab-pane>
        <el-tab-pane label="处置单" :disabled="disableDisposition">
          <case-disposition v-model="selectedCaseDispositions"></case-disposition>
        </el-tab-pane>
        <el-tab-pane label="患者账单">
          <case-pay-list :payList="selectedPayList"></case-pay-list>
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
import { listFinalDiagnoses, saveFinalDiagnose } from "@/api/finalDiagnose";
import { listPayInfoByCaseId } from "@/api/casePayInfo";

import OutPatientPreDiagnose from "@/components/outpatientdoctor/OutPatientPreDiagnose";
import CaseTemplateAdmin from "@/components/outpatientdoctor/CaseTemplateAdmin";
import ProjectApplication from "@/components/outpatientdoctor/ProjectApplication";
import CaseRecipe from "@/components/outpatientdoctor/CaseRecipe";
import CaseDisposition from "@/components/outpatientdoctor/CaseDisposition";
import FinalCase from "@/components/outpatientdoctor/FinalCase";
import CasePayList from "@/components/outpatientdoctor/CasePayList";
import { constants } from "fs";
import { POINT_CONVERSION_COMPRESSED } from "constants";
import { successDialog, failDialog } from "@/utils/notification";
import { updateOutpatientQueue } from "@/api/notification/outpatientNotification";

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
      selectedFinalCase: {},
      selectedPayList: [],
      modernDisease: [],
      traditionalDisease: [],
      traditionalRecipes: {},
      modernRecipes: {},
      //控制那些子页面可以被访问
      disablePreDiagnose: true,
      disableFinalDiagnose: true,
      disableExamination: true,
      disableInspection: true,
      disableDisposition: true,
      disableFinish: true,
      disableRecipe: true,
      disableModRecipe: true,
      disableTraRecipe: true,
      //病历首页是否可以被修改
      ableEditPreDiagnose: false,
      ableEditFinalDiagnose: false
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
      var ttsDiv = document.getElementById("bdtts_div_id");
      var ttsAudio = document.getElementById("tts_autio_id");
      var ttsText = name;

      // 文字转语音
      ttsDiv.removeChild(ttsAudio);
      var au1 = '<audio id="tts_autio_id" autoplay="autoplay">';
      var sss =
        '<source id="tts_source_id" src="http://tsn.baidu.com/text2audio?lan=zh&ie=UTF-8&per=3&spd=14&vol=15&text=' +
        ttsText +
        '" type="audio/mpeg">';
      var eee = '<embed id="tts_embed_id" height="0" width="0" src="">';
      var au2 = "</audio>";
      ttsDiv.innerHTML = au1 + sss + eee + au2;

      ttsAudio = document.getElementById("tts_autio_id");

      ttsAudio.play();
    },
    handlePatientSelect(row) {
      console.log("被选中的病人");
      console.log(row);

      // 语音播报
      // this.doTTS(
      //   "请" +
      //     row.name +
      //     "到" +
      //     this.$store.getters["user/currentDepartmentName"] +
      //     "就诊"
      // );

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
          console.log("选中的病历初诊：");
          console.log(this.selectedCase);

          const caseStatus = this.selectedCase.status;

          this.disablePreDiagnose = false;
          console.log("当前的状态码：");
          console.log(caseStatus);
          //如果该病历的初诊未被提交，不允许进行其他任何操作
          if (caseStatus === 1 || caseStatus === 2) {
            this.disableFinalDiagnose = true;
            this.disableExamination = true;
            this.disableInspection = true;
            this.disableRecipe = true;
            this.disableFinish = true;
            this.disableDisposition = true;
            this.ableEditPreDiagnose = true;
          } else if (caseStatus === 3) {
            //已诊（未确诊）
            this.disableFinalDiagnose = false;
            this.disableExamination = false;
            this.disableInspection = false;
            this.disableRecipe = true;
            this.disableFinish = true;
            this.disableDisposition = true;
            this.ableEditPreDiagnose = false;
            this.ableEditFinalDiagnose = true;
          } else if (caseStatus === 4) {
            //已确诊
            this.disableFinalDiagnose = false;
            this.disableExamination = false;
            this.disableInspection = false;
            this.disableRecipe = false;
            this.disableFinish = false;
            this.disableDisposition = false;
            this.ableEditPreDiagnose = false;
            this.ableEditFinalDiagnose = false;
          } else {
            //do nothing
          }

          // 如果这是第一次诊断，那么发送消息告诉后端这是第一次请求(大屏幕发生响应变化)
          if (caseStatus === 1) {
            let updateInfo = {
              caseId: this.selectedCase.caseId,
              roleId: this.$store.getters["user/currentRoleId"],
              code: "update"
            };
            console.log("----------更新大屏幕------------");
            console.log(updateInfo);
            updateOutpatientQueue(updateInfo);
          }

          //请求当前被点击用户的病历所有的处方
          listCaseRecipes(this.selectedPatient.caseId).then(
            response => {
              var caseRecipe = response.data.data;
              caseRecipe.caseId = this.selectedPatient.caseId;
              console.log("选中的处方信息：");
              console.log(caseRecipe);
              if (caseRecipe.type === 1) {
                //如果是西医处方
                this.modernRecipes = Object.assign({}, caseRecipe);
                if (!this.disableRecipe) {
                  this.disableTraRecipe = true;
                  this.disableModRecipe = false;
                } else {
                  this.disableTraRecipe = true;
                  this.disableModRecipe = true;
                }
              } else if (caseRecipe.type == 2) {
                //如果是中医处方
                this.traditionalRecipes = Object.assign({}, caseRecipe);
                if (!this.disableRecipe) {
                  this.disableTraRecipe = false;
                  this.disableModRecipe = true;
                } else {
                  this.disableTraRecipe = true;
                  this.disableModRecipe = true;
                }
              } else {
                //不存在处方
                caseRecipe.type = 1;
                this.modernRecipes = Object.assign({}, caseRecipe);
                caseRecipe.type = 2;
                this.traditionalRecipes = Object.assign({}, caseRecipe);
                if (!this.disableRecipe) {
                  this.disableTraRecipe = false;
                  this.disableModRecipe = false;
                } else {
                  this.disableTraRecipe = true;
                  this.disableModRecipe = true;
                }
              }
            },
            error => {
              //暂时不处理
              console.alert("查所有处方出bug了");
            }
          );
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
          console.log("选中的病历检查项目：");
          console.log(this.selectedCaseExaminations);
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
          console.log("选中的病历检验项目：");
          console.log(this.selectedCaseInspections);
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
          console.log("选中的病历的处置信息：");
          console.log(this.selectedCaseDispositions);
        },
        error => {
          //暂时不处理
          console.alert("得到处置内容出Bug了");
        }
      );
      //请求当前被点击用户的最终诊断信息
      listFinalDiagnoses(this.selectedPatient.caseId).then(
        response => {
          this.selectedFinalCase = response.data.data;
        },
        error => {
          console.log(error);
        }
      );
      //请求当前被点击用户的账单
      listPayInfoByCaseId(this.selectedPatient.caseId).then(
        response => {
          console.log("当前用户账单： ");
          console.log(response.data.data);
          this.selectedPayList = response.data.data;
        },
        error => {
          failDialog("查询当前用户账单失败");
        }
      );
    },
    handleFinish() {
      this.selectedCase.status = "5";
      saveCase(this.selectedCase).then(
        response => {
          if (response.data.code === 200) {
            successDialog("诊毕成功");
            this.diagnosedPatients.splice(
              this.diagnosedPatients.findIndex(
                patientCase => patientCase.caseId === this.selectedCase.caseId
              ),
              1
            );
            this.disableFinish = true;
          } else {
            failDialog("诊毕失败");
            this.selectedCase.status = "4";
          }
        },
        error => {
          failDialog("诊毕失败");
        }
      );
    },
    //暂存case的内容
    onSaveSelectedCase(isTraDiagnose) {
      this.selectedCase.diagnoseType = isTraDiagnose ? 0 : 1;
      saveCase(this.selectedCase).then(
        response => {
          if (response.data.code === 200) {
            successDialog("暂存成功");
          } else {
            failDialog("暂存失败");
          }
        },
        error => {
          console.log(error);
        }
      );
    },
    //上传case的内容
    onSubmitSelectedCase(isTraDiagnose) {
      this.selectedCase.diagnoseType = isTraDiagnose ? 0 : 1;
      submitCase(this.selectedCase).then(
        response => {
          if (response.data.code === 200) {
            successDialog("开立成功");
            // 告诉后端更新显示起内容
            let updateInfo = {
              caseId: this.selectedCase.caseId,
              roleId: this.$store.getters["user/currentRoleId"],
              code: "done"
            };
            console.log("----------更新大屏幕------------");
            console.log(updateInfo);
            updateOutpatientQueue(updateInfo);
          } else {
            failDialog("开立失败");
          }
          //更改可访问模块
          this.disableRecipe = true;
          this.disableModRecipe = true;
          this.disableTraRecipe = true;
          this.disableFinalDiagnose = false;
          this.disableInspection = false;
          this.disableExamination = false;
          this.disableDisposition = false;
          this.ableEditPreDiagnose = false;
        },
        error => {
          console.log(error);
        }
      );

      var splicedCase = this.waitingPatients.splice(
        this.waitingPatients.findIndex(
          patientCase => patientCase.caseId === this.selectedCase.caseId
        ),
        1
      );
      this.diagnosedPatients.push(splicedCase[0]);
    },
    //清空case内容
    onClearSelectedCase() {
      clearCase(this.selectedCase.caseId).then(
        response => {
          if (response.code === 200) {
            successDialog("清空成功");
          } else {
            failDialog("清空失败");
          }
        },
        error => {
          console.log(response);
        }
      );
      this.selectedCase = {};
    },
    //存储当前的最终诊断
    onSaveFinalCase(isTraDiagnose) {
      this.selectedFinalCase.diagnoseType = isTraDiagnose ? 0 : 1;
      this.selectedFinalCase.caseId = this.selectedPatient.caseId;
      this.selectedFinalCase.status = 3;
      saveFinalDiagnose(this.selectedFinalCase).then(
        response => {
          if (response.data.code === 200) {
            successDialog("暂存成功");
          } else {
            failDialog("暂存失败");
          }
        },
        error => {
          console.log(error);
        }
      );
    },
    onSubmitFinalCase(isTraDiagnose) {
      this.selectedFinalCase.diagnoseType = isTraDiagnose ? 0 : 1;
      this.selectedFinalCase.caseId = this.selectedPatient.caseId;
      this.selectedFinalCase.status = 4;
      saveFinalDiagnose(this.selectedFinalCase).then(
        response => {
          if (response.data.code === 200) {
            successDialog("提交成功");
          } else {
            failDialog("提交失败");
          }
          // 改变可点击的模块
          this.disableRecipe = false;
          this.disableModRecipe = false;
          this.disableTraRecipe = false;
          this.disableFinish = false;
          this.ableEditFinalDiagnose = false;
        },
        error => {
          console.log(error);
        }
      );
    },
    testTabClick(data) {
      console.log(data);
    }
  },
  components: {
    "outpatient-prediagnose": OutPatientPreDiagnose,
    "case-template-admin": CaseTemplateAdmin,
    "project-application": ProjectApplication,
    "case-recipe": CaseRecipe,
    "case-disposition": CaseDisposition,
    "final-case": FinalCase,
    "case-pay-list": CasePayList
  },
  mounted: function() {
    //请求所有待诊病人和已诊病人
    listAllPatients(this.$store.getters["user/currentRoleId"]).then(
      response => {
        const data = response.data.data;
        this.waitingPatients = data.waitingPatients;
        this.diagnosedPatients = data.diagnosedPatients;
        console.log("等待的用户");
        console.log(this.waitingPatients);
        successDialog("挂号人员数据读取完毕");
      },
      error => {
        failDialog("挂号人员数据读取异常");
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

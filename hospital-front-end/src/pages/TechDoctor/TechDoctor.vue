<template lang="html">
  <el-container style="height: 90vh; background-color: #f2f7f8;">
    <el-aside width="400px">
      <!-- 侧栏区域 -->
      <div class="side-bar">
        <el-card shadow="hover" style="margin: 10px 0px 30px 25px;">
        <!-- 搜索区 -->
        <div slot="header">
          <i class="el-icon-search"></i>
          <span>查询</span>
        </div>
        <div class="search-region">
          <el-date-picker
            align="right"
            type="date"
            v-model="chargeDateStr"
            placeholder="选择日期"
            :picker-options="pickerOptions"
            style="margin-right: 3px;"
            value-format="yyyy-MM-dd"
          ></el-date-picker>
          <el-input placeholder="请输入病历号" class="search-input" v-model="inputCaseId">
            <el-button slot="append" icon="el-icon-search" @click="listPatientByCaseIdOrName"></el-button>
          </el-input>
        </div>
        </el-card>
        <!-- 待登记户区 -->
        <el-card shadow="hover" style="margin: 0px 0px 30px 25px;">
          <div slot="header">
            <i class="el-icon-news"></i>
            <span>待登记病人</span>
          </div>
          <!-- 待登记病人名单 -->
          <div class="">
            <!-- 表格 -->
            <el-table
              :data="waitingList"
              style="width: 100%"
              highlight-current-row @current-change="handleCurrentChange"
              v-loading="loading1">
              <el-table-column
                prop="registration_id"
                label="病历号">
              </el-table-column>
              <el-table-column
                prop="patient_name"
                label="患者姓名">
              </el-table-column>
            </el-table>
          </div>
        </el-card>

        <!-- 已登记项目区 -->
        <el-card shadow="hover" style="margin: 0px 0px 30px 25px;">
          <div slot="header">
            <i class="el-icon-finished"></i>
            <span>已登记项目</span>
          </div>
          <!-- 已登记病人名单 -->
          <div class="">
            <!-- 表格 -->
            <el-table
              :data="checkedInList"
              style="width: 100%"
              highlight-current-row @current-change="handleCheckedInChange"
              v-loading="loading1">
              <el-table-column
                prop="caseId"
                label="病历号">
              </el-table-column>
              <el-table-column
                prop="id"
                label="单号">
              </el-table-column>
              <el-table-column
                prop="projectId"
                label="检查项目">
              </el-table-column>
            </el-table>
          </div>
        </el-card>
      </div>
    </el-aside>
    <el-main>


      <!-- 当前病人信息 -->
      <el-card shadow="hover" style="margin: 0 15px 30px 0;" class="info-card" v-if="patientCard">
        <div class="current-user">
          <!-- 基本信息 -->
          <div class="basic-info">
            <!-- 病历号 -->
            <span>病历号: {{patientInfo.id}} </span>
            <!-- 姓名 -->
            <span>姓名: {{patientInfo.patient.name}} </span>
            <!-- 性别 -->
            <span>性别: {{patientInfo.patient.gender}} </span>
            <!-- 年龄 -->
            <span>出生日期: {{patientInfo.patient.birthday}} </span>
          </div>
          </div>
        </div>
      </el-card>

      <el-card shadow="hover" v-if="itemTable" v-loading="loading2">
      <div slot="header">
        <span>全部项目</span>
      </div>
      <!-- 当前病人待做项目 -->
      <el-table :data="itemList" style="margin: 0 15px 30px 0;">
        <el-table-column type="index" width="50"></el-table-column>
        <el-table-column property="id" abel="项目id" width="120"></el-table-column>
        <el-table-column property="project_id" label="项目代码" width="120"></el-table-column>
        <el-table-column property="project_name" label="项目分类" width="120"></el-table-column>
        <el-table-column property="project_name" label="项目名称" width="120"></el-table-column>
        <el-table-column property="i_status" label="项目状态" width="120"></el-table-column>                
        <el-table-column property="t_status" label="缴费状态"></el-table-column>
        <el-table-column fixed="right" label="操作" width="100">
          <template slot-scope="scope">
            <el-button @click="handleClick(scope.row)" type="text" size="small" v-if="scope.row.t_status==2&&scope.row.i_status==2">登记</el-button>
            <el-button @click="handleClick(scope.row)" type="text" disabled size="small" v-if="scope.row.t_status!==2||scope.row.i_status!==2">登记</el-button>
          </template>
        </el-table-column>Z
      </el-table>
      </el-card>

      <!-- 导航栏(也就是一个标签页) -->
      <el-tabs type="border-card" style="overflow:vible; margin: 0 15px 30px 0;" v-if="resultForm">
        <!-- 结果录入tab-->
        <el-tab-pane label="结果录入">
          <!-- 结果录入模块 -->
          <div class="">
            <!-- 工具栏 -->
            <el-card :body-style="{padding:'0px'}" style="margin-bottom: 5px;">
              <el-button type="text" icon="el-icon-refresh-right" round @click="clear">清屏</el-button>
              <el-button type="text" icon="el-icon-folder-checked" round @click="save">保存</el-button>
              <el-button type="text" icon="el-icon-folder-checked" round @click="submit">提交</el-button>
              <el-button type="text" icon="el-icon-printer" round @click="print">打印</el-button>
            </el-card>
            <!-- 录入表格 -->
            <el-card style="margin-bottom: 5px;">
              <el-form>
                <el-form-item label="检查/检验所见">
                  <el-input
                    type="textarea"
                    autosize
                    placeholder="请输入检查/检验所见"
                    :autosize="{ minRows: 4, maxRows: 100}"
                    v-model="resultDescription"
                    >
                  </el-input>
                </el-form-item>
                <el-form-item label="诊断意见">
                  <el-input
                    type="textarea"
                    autosize
                    placeholder="请输入诊断意见"
                    :autosize="{ minRows: 4, maxRows: 100}"
                    v-model="advice"
                    >
                  </el-input>
                </el-form-item>
                <el-form-item label="上传结果图片">
                </el-form-item>
                <el-upload
                  class="upload-demo"
                  drag
                  action="/api/upload"
                  multiple
                  name="smfile"
                  :on-success="handleImageSuccess">
                  <i class="el-icon-upload"></i>
                  <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                  <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div>
                </el-upload>
              </el-form>
            </el-card>
          </div>
        </el-tab-pane>
        <el-tab-pane label="项目补录">
          
        </el-tab-pane>
      </el-tabs>
    </el-main>
  </el-container>
</template>

<script>
import techDoctor from "@/api/techDoctor";

export default {
  name: "TechDoctor",
  data() {
    return {
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        },
        shortcuts: [
          {
            text: "今天",
            onClick(picker) {
              picker.$emit("pick", new Date());
            }
          },
          {
            text: "昨天",
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24);
              picker.$emit("pick", date);
            }
          },
          {
            text: "一周前",
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit("pick", date);
            }
          }
        ]
      },

      chargeDateStr: "",
      // 当前操作员
      currentRoleId: "",
      inputCaseId: "",

      waitingList: [],
      currentCase: {},
      itemList: [],
      checkedInList: [],

      zhusu: "",
      form: {},
      patientInfo: {},

      //隐藏
      itemTable: false,
      patientCard: false,
      resultForm: false,

      currentProject: {},

      //表格内容
      resultDescription: "",
      advice: "",
      resultImage:
        "http://ww4.sinaimg.cn/large/006tNc79ly1g3v2cgwoaxj30ax07t3z8.jpg",

      loading1: false,
      loading2: false
    };
  },

  methods: {
    handleImageSuccess(res, file) {
      console.log(res);
      this.resultImage = res.data.url;
    },

    // 打印
    print() {},

    // 清屏
    clear() {
      this.resultDescription = "";
      this.advice = "";
      this.resultImage = "";
    },

    // 提交
    submit() {
      this.save();
      var object = {};
      object.collectionId = this.currentProject.id;
      object.projectId = this.currentProject.projectId;
      object.departmentId = this.$store.getters['user/currentDepartmentId'];
      object.caseId = this.currentProject.caseId;
      techDoctor.confirmProject(object).then(response => {
        const data = response.data.data;

        console.log(data);

        if (response.data.code === 200) {
          this.success("提交");
          this.listCheckedInButNotRecordedProject();
          this.resultForm = false;
        } else {
          this.fail("提交");
        }
      });
    },

    // 保存
    save() {
      var object = {};
      object.resultDescription = this.resultDescription;
      object.advice = this.advice;
      object.resultImage = this.resultImage;
      object.collectionId = this.currentProject.id;
      object.projectId = this.currentProject.projectId;
      object.departmentId = this.$store.getters['user/currentDepartmentId'];
      object.caseId = this.currentProject.caseId;
      techDoctor.result(object).then(response => {
        const data = response.data.data;

        console.log(data);

        if (response.data.code === 200) {
          this.success("保存");
        } else {
          this.fail("保存");
        }
      });
    },

    // 显示本科室已登记项目列表
    listCheckedInButNotRecordedProject() {
      var object = {};
      object.departmentId = this.$store.getters['user/currentDepartmentId'];
      object.chargeDateStr = this.chargeDateStr;
      techDoctor.listCheckedInButNotRecordedProject(object).then(response => {
        const data = response.data.data;

        this.checkedInList = data;
        console.log(data);
      });
    },

    // 登记button
    handleClick(row) {
      console.log("here")
      this.loading2 = true;
      var project = {};
      project.departmentId = this.$store.getters['user/currentDepartmentId'];
      project.collectionId = row.id;
      project.projectId = row.project_id;
      project.doctorRoleId = this.currentRoleId;
      project.transactionLogStatus = row.t_status;
      project.projectStatus = row.i_status;
      console.log(project);

      techDoctor
        .checkInProject(project)
        .then(response => {
          const data = response.data.data;
          console.log(data);

          if (response.data.code === 200) {
            this.refreshItemList();
            this.success("登记");
          } else {
            this.fail("登记");
          }
        })
        .finally(response => {
          this.loading2 = false;
        });
    },

    // 刷新待做项目列表
    refreshItemList() {
      this.handleCurrentChange(this.currentCase);
      this.listCheckedInButNotRecordedProject();
    },

    // 选中已登记
    handleCheckedInChange(val) {
      this.resultForm = true;
      this.itemTable = false;
      // // this.patientCard = false;
      // this.patientCard = true;

      this.currentProject = val;
      console.log(val)
      var object = {};
      object.departmentId = this.$store.getters['user/currentDepartmentId'];
      object.collectionId = val.id;
      object.projectId = val.projectId;
      techDoctor.showResult(object).then(response => {
        const data = response.data.data;
        console.log(data);

        this.resultDescription = data.resultDescription;
        this.advice = data.advice;
        this.resultImage = data.resultImage;
      })
    },

    // 选中患者展示待做项目列表
    handleCurrentChange(val) {
      this.currentCase = val;
      console.log(val);
      techDoctor
        .listAllProjectsByCaseId(
          this.chargeDateStr,
          val.registration_id,
          this.$store.getters['user/currentDepartmentId']
        )
        .then(response => {
          const data = response.data.data;
          console.log(data);
          this.itemList = data.projects;
          this.patientInfo = data.patientInfo;

          this.itemTable = true;
          this.patientCard = true;

          this.resultForm = false;

          if (response.data.code === 200) {
            this.success("查询");
          } else {
            this.fail("查询");
          }
        });
    },

    // 根据条件搜索患者
    listPatientByCaseIdOrName() {
      console.log(this.$store.getters['user/currentDepartmentId'])
      this.loading1 = true;
      techDoctor
        .listPatientByCaseIdOrName(
          this.chargeDateStr,
          this.inputCaseId,
          this.$store.getters['user/currentDepartmentId']
        )
        .then(response => {
          const data = response.data.data;
          console.log(data);
          this.waitingList = data;

          if (response.data.code === 200) {
            this.success("查询");
            this.listCheckedInButNotRecordedProject();
          } else {
            this.fail("查询");
          }
        })
        .finally(response => {
          this.loading1 = false;
        });
    },

    // 计算当前日期
    getNowFormatDate() {
      var date = new Date();
      var seperator1 = "-";
      var year = date.getFullYear();
      var month = date.getMonth() + 1;
      var strDate = date.getDate();
      if (month >= 1 && month <= 9) {
        month = "0" + month;
      }
      if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
      }
      var currentdate = year + seperator1 + month + seperator1 + strDate;
      return currentdate;
    },

    // 成功提示
    success(msg) {
      this.$message({
        message: msg + "成功",
        type: "success"
      });
    },

    // 失败提示
    fail(msg) {
      this.$message.error(msg + "失败");
    }
  },

  mounted() {
    // 得到当前操作员id
    const currentRoleId = this.$store.getters["user/currentRoleId"];
    this.currentRoleId = currentRoleId;
    // 时间选择框默认显示今天日期
    this.chargeDateStr = this.getNowFormatDate();

    // 读取常量表
    // this.readConstants();
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
}

.search-user {
  height: 30px;
  margin-bottom: ;
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
.search-region {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  align-items: center;
}
</style>

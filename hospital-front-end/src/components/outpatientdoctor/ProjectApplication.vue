<template>
  <div class="outpatient-service-container">
    <!-- 左侧操作区 -->
    <div class="service-main-container">
      <!-- 申请的操作栏以及表格, 做成一个卡片 -->
      <el-card
        v-for="(collection, index) in caseExaminations.collections"
        v-bind:key="collection.collectionId"
      >
        <!-- 操作栏 -->
        <div slot="header" class="clearfix">
          <span>{{typeName}}申请单 {{index + 1}}</span>
          <el-button
            style="float:right; margin-left: 10px;"
            type="text"
            icon="el-icon-document-add"
            @click="handleAddTemplate(collection)"
          >存为模版</el-button>
          <el-button
            style="float:right; margin-left: 10px;"
            type="text"
            icon="el-icon-s-check"
            @click="handleSubmit(collection, index)"
            :disabled="isEditable[index]"
          >开立</el-button>
          <el-button
            style="float:right; margin-left: 10px;"
            type="text"
            icon="el-icon-delete-solid"
            :disabled="!isEditable[index]"
          >作废</el-button>
          <el-button
            style="float:right; margin-left: 10px;"
            type="text"
            icon="el-icon-folder-checked"
            @click="handleTempSave(collection)"
            :disabled="isEditable[index]"
          >暂存</el-button>
          <el-button
            style="float:right; margin-left: 10px;"
            type="text"
            icon="el-icon-refresh-right"
            :disabled="isEditable[index]"
            @click="handleClear(collection)"
          >清空</el-button>
          <el-button
            style="margin-left: 10px"
            type="text"
            icon="el-icon-circle-plus"
            @click="handleAddProjectDialog(collection)"
            :disabled="isEditable[index]"
          >新增项目</el-button>
        </div>
        <!-- 项目列表 -->
        <div class>
          <el-table style="width: 100%" :data="collection.projects">
            <el-table-column type="expand">
              <template slot-scope="props">
                <div class="collection-detail">
                  <p>项目名称：{{props.row.projectName}}</p>
                  <p>执行部门：{{props.row.departmentName}}</p>
                  <p>检查目的：{{props.row.goal}}</p>
                  <p>检查要求：{{props.row.requirement}}</p>
                </div>
                <div class="table-container">
                  <el-table :data="props.row.items">
                    <el-table-column prop="itemId" label="小项ID"></el-table-column>
                    <el-table-column prop="itemName" label="小项名称"></el-table-column>
                    <el-table-column prop="amount" label="单位数量"></el-table-column>
                  </el-table>
                </div>
              </template>
            </el-table-column>
            <el-table-column prop="projectName" label="项目名称"></el-table-column>
            <el-table-column prop="departmentName" label="部门名称"></el-table-column>
            <el-table-column prop="status" label="执行状态" :formatter="statusFormatter"></el-table-column>
            <el-table-column label="检查结果">
              <template slot-scope="scope">
                <el-button
                  type="text"
                  @click="showResult(collection.collectionId, scope.row.projectId)"
                >查看检查结果</el-button>
              </template>
            </el-table-column>
            <el-table-column>
              <template slot-scope="scope">
                <el-button
                  type="text"
                  @click="handleRemoveProject(scope.row, collection)"
                  icon="el-icon-delete"
                >删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-card>
      <!-- 新增申请项目列表button -->
      <div>
        <el-button type="text" icon="el-icon-plus" @click="handleAddCollection">新增申请项目列表</el-button>
      </div>
    </div>
    <!-- 底部模版区域 -->
    <div class="service-side-container">
      <project-template
        :type="type"
        :typeName="typeName"
        @give-project-template="useProjectTemplate"
      ></project-template>
    </div>
    <!-- 新增项目dialog -->
    <el-dialog
      title="新增检查项目"
      :visible.sync="dialogAddProject"
      :before-close="handleClose"
      width="600px"
    >
      <div>
        <el-card shadow="hover" style="margin-bottom: 10px">
          <div slot="header">
            <span>项目基本信息</span>
          </div>
          <el-form label-position="left" label-width="80px" :model="newProject">
            <el-form-item label="项目名称">
              <el-autocomplete
                class="inline-input"
                v-model="newProject.projectName"
                :fetch-suggestions="queryProjectSearch"
                placeholder="请输入项目名称"
                :highlight-first-item="true"
                @select="handleSelectProject"
                value-key="projectName"
              ></el-autocomplete>
            </el-form-item>
            <el-form-item label="所属部门">
              <el-input style="width: 180px" v-model="newProject.departmentName"></el-input>
            </el-form-item>
            <el-form-item label="检查目的">
              <el-input
                type="textarea"
                :autosize="{ minRows: 2, maxRows: 8}"
                style="width: 300px"
                v-model="newProject.goal"
              ></el-input>
            </el-form-item>
            <el-form-item label="检查要求">
              <el-input
                type="textarea"
                :autosize="{ minRows: 2, maxRows: 8}"
                style="width: 300px"
                v-model="newProject.requirement"
              ></el-input>
            </el-form-item>
          </el-form>
        </el-card>
        <el-card shadow="hover">
          <div slot="header" class>
            <span>添加收费小项</span>
          </div>
          <el-form label-position="left" label-width="80px" :model="newProject">
            <el-autocomplete
              class="inline-input"
              v-model="newItem.itemName"
              :fetch-suggestions="queryItemSearch"
              placeholder="请输入项目名称"
              :highlight-first-item="true"
              @select="handleSelectItem"
              value-key="itemName"
            ></el-autocomplete>
            <el-input-number :min="1" :max="10" label="单位数量" v-model="newItem.amount"></el-input-number>
            <span style="margin: 0px 0px 0px 10px">单位:{{newItem.unit}}</span>
            <el-button
              type="primary"
              icon="el-icon-plus"
              style="margin: 0px 0px 0px 10px"
              circle
              @click="handleAddItem"
            ></el-button>
            <!-- 已经加入的小项表格 -->
            <div class="item-table">
              <el-table :data="newProject.items">
                <el-table-column prop="itemId" label="ID"></el-table-column>
                <el-table-column prop="itemName" label="名称" width="150px"></el-table-column>
                <el-table-column prop="amount" label="数目"></el-table-column>
                <el-table-column prop="unit" label="单位"></el-table-column>
                <el-table-column label="操作">
                  <template slot-scope="scope">
                    <el-button type="text" @click="handleRemoveItem(scope.row)">删除</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </el-form>
        </el-card>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleCancel">取消</el-button>
        <el-button type="primary" @click="handleConfirmAdd">添加</el-button>
      </span>
    </el-dialog>
    <el-dialog title="检查结果" :visible.sync="dialogShowResult" width="600px">
      <el-card shadow="hover">
        <el-form label-position="left" label-width="80px" :model="currentResult">
          <el-form-item label="诊断意见">
            <el-input
              type="textarea"
              :autosize="{ minRows: 2, maxRows: 8}"
              style="width: 300px"
              v-model="currentResult.advice"
              disabled
            ></el-input>
          </el-form-item>
          <el-form-item label="检查检验结果所见">
            <el-input
              type="textarea"
              :autosize="{ minRows: 2, maxRows: 8}"
              style="width: 300px"
              v-model="currentResult.resultDescription"
              disabled
            ></el-input>
          </el-form-item>
          <el-form-item label="检查结果图片">
            <el-image style="width: 100px; height: 100px" :src="currentResult.resultImage"></el-image>
          </el-form-item>
        </el-form>
      </el-card>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleCancel">取消</el-button>
      </span>
    </el-dialog>
    <!-- 存为检查/检验模版 dialog -->
    <el-dialog
      title="添加组套"
      :visible.sync="dialogAddTemplate"
      :before-close="handleClose"
      width="600px"
    >
      <el-card shadow="hover" style="margin-bottom: 10px">
        <div slot="header" class="clearfix">
          <span>组套内容</span>
        </div>
        <div>
          <el-table style="width: 100%" :data="newTemplate.projects">
            <el-table-column type="expand">
              <template slot-scope="props">
                <div class="collection-detail">
                  <p>项目名称：{{props.row.projectName}}</p>
                  <p>执行部门：{{props.row.departmentName}}</p>
                  <p>检查目的：{{props.row.goal}}</p>
                  <p>检查要求：{{props.row.requirement}}</p>
                </div>
                <div class="table-container">
                  <el-table :data="props.row.items">
                    <el-table-column prop="itemId" label="小项ID"></el-table-column>
                    <el-table-column prop="itemName" label="小项名称"></el-table-column>
                    <el-table-column prop="amount" label="单位数量"></el-table-column>
                  </el-table>
                </div>
              </template>
            </el-table-column>
            <el-table-column prop="projectName" label="项目名称"></el-table-column>
            <el-table-column prop="departmentName" label="部门名称"></el-table-column>
          </el-table>
        </div>
      </el-card>
      <el-card shadow="hover">
        <div slot="header">
          <span>权限设置</span>
        </div>
        <el-form :model="newTemplate" label-position="left" label-width="80px">
          <el-form-item label="组套名称">
            <el-input v-model="newTemplate.newName"></el-input>
          </el-form-item>
          <el-form-item label="使用权限">
            <el-select v-model="newTemplate.scope" placeholder="请选择模版权限">
              <el-option
                v-for="scope in scopes"
                :key="scope.value"
                :label="scope.label"
                :value="scope.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-form>
      </el-card>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleCancel">取消</el-button>
        <el-button type="primary" @click="handleConfirmAddTemplate">添加</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  getNewCollectionId,
  listAllProjects,
  listAllItems,
  saveCollection,
  submitCollection,
  getProjectResult
} from "@/api/project";
import { projectStatusCodeToString } from "@/utils/interpreter";
import { PassThrough } from "stream";
import { isContext } from "vm";
import { successDialog, failDialog } from "@/utils/notification";
import ProjectTemplate from "@/components/outpatientdoctor/ProjectTemplate";
import { saveProjectTemplate } from "@/api/projectTemplate";

export default {
  name: "ProjectApplication",
  data() {
    return {
      dialogAddProject: false,
      newProject: { items: [] },
      newItem: {},
      currentCollection: {},
      projects: [],
      items: [],
      addProjectButtonDisabled: [],
      newTemplate: {},
      dialogShowResult: false,
      dialogAddTemplate: false,
      currentResult: {},
      currentResult: {},
      scopes: [
        {
          value: "1",
          label: "个人"
        },
        {
          value: "2",
          label: "部门"
        },
        {
          value: "3",
          label: "全院"
        }
      ]
    };
  },
  props: {
    value: Object,
    type: Number,
    typeName: String
  },
  computed: {
    isEditable: function() {
      console.log("isEditable Updated");
      var collections = this.caseExaminations.collections;
      var isEditable = [];
      var i = 0;
      var length = collections.length;
      for (i = 0; i < length; i++) {
        if (
          collections[i].projects.length !== 0 &&
          collections[i].projects[0].status !== 1
        ) {
          isEditable.push(true);
        } else {
          isEditable.push(false);
        }
      }
      console.log(isEditable);
      return isEditable;
    },

    caseExaminations: {
      get() {
        return this.value;
      },
      set(v) {
        this.$emit("input", v);
      }
    }
  },
  methods: {
    success() {
      this.$message({
        message: "操作成功",
        type: "success"
      });
    },

    // 挂号失败提示
    fail() {
      this.$message.error("操作失败");
    },
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then(_ => {
          done();
        })
        .catch(_ => {});
    },
    handleAddCollection() {
      getNewCollectionId(this.type).then(
        response => {
          const newCollectionId = response.data.data.collectionId;
          this.caseExaminations.collections.push({
            collectionId: newCollectionId,
            projects: []
          });
        },
        error => {
          console.log(error);
        }
      );
    },
    statusFormatter(row, column) {
      let statusCode = row.status;
      return projectStatusCodeToString(statusCode);
    },
    queryProjectSearch(queryString, cb) {
      var projects = this.projects;
      var results = queryString
        ? projects.filter(this.createProjectFilter(queryString))
        : projects;
      cb(results);
    },
    queryItemSearch(queryString, cb) {
      var items = this.items;
      var results = queryString
        ? items.filter(this.createItemFilter(queryString))
        : items;
      cb(results);
    },
    createProjectFilter(queryString) {
      return project => {
        return (
          project.projectName
            .toLowerCase()
            .indexOf(queryString.toLowerCase()) === 0
        );
      };
    },
    createItemFilter(queryString) {
      return item => {
        return (
          item.itemName.toLowerCase().indexOf(queryString.toLowerCase()) === 0
        );
      };
    },
    handleSelectProject(item) {
      this.newProject.departmentName = item.departmentName;
      this.newProject.projectId = item.projectId;
      // 去查询相应project下的可用小项信息
      listAllItems(this.newProject.projectId).then(
        response => {
          this.items = response.data.data;
          console.log(this.items);
        },
        error => {
          console.log(error);
        }
      );
    },
    handleSelectItem(item) {
      this.newItem.itemId = item.itemId;
      this.newItem.unit = item.unit;
      this.newItem.amount = 1;
    },
    handleAddItem() {
      this.newProject.items.push(JSON.parse(JSON.stringify(this.newItem)));
      this.newItem = {};
    },
    handleRemoveItem(row) {
      this.newProject.items.splice(
        this.newProject.items.findIndex(item => {
          item.itemName === row.itemName;
        }),
        1
      );
    },
    handleRemoveProject(row, collection) {
      collection.projects.splice(
        collection.projects.findIndex(project => {
          project.projectName == row.projectName;
        }),
        1
      );
    },
    handleCancel() {
      this.newProject = { items: [] };
      this.newItem = {};
      this.dialogAddProject = false;
      this.dialogShowResult = false;
    },
    handleConfirmAdd() {
      this.dialogAddProject = false;
      const repeatedProject = this.currentCollection.projects.filter(
        project => project.projectName === this.newProject.projectName
      );
      if (repeatedProject.length === 0) {
        this.newProject.status = 1;
        this.currentCollection.projects.push(this.newProject);
        this.newProject = { items: [] };
        successDialog("项目添加成功");
      } else {
        failDialog("失败：该项目已存在");
      }
      this.newItem = {};
    },
    handleAddProjectDialog(collection) {
      this.dialogAddProject = true;
      this.currentCollection = collection;
    },
    handleTempSave(collection) {
      collection.roleId = this.$store.getters["user/currentRoleId"];
      collection.caseId = this.caseExaminations.caseId;
      collection.applicantRoleId = this.$store.getters["user/currentRoleId"];
      collection.collectionType = this.type;
      saveCollection(collection).then(
        response => {
          if (response.data.code === 200) {
            this.success("缴费");
          } else {
            this.fail("缴费");
          }
        },
        error => {
          console.log(error);
        }
      );
    },
    handleSubmit(collection, index) {
      collection.caseId = this.caseExaminations.caseId;
      collection.applicantRoleId = this.$store.getters["user/currentRoleId"];
      collection.collectionType = this.type;
      // 将所有内存的project 状态都改为 2.已开立
      var i;
      var length = collection.projects.length;
      for (i = 0; i < length; i++) {
        collection.projects[i].status = 2;
      }
      submitCollection(collection).then(
        response => {
          if (response.data.code === 200) {
            this.success("缴费");
          } else {
            this.fail("缴费");
          }
          this.$set(this.caseExaminations.collections, index, collection);
        },
        error => {
          console.log(error);
        }
      );
    },
    handleClear(collection) {
      collection.projects = [];
      this.handleTempSave(collection);
    },
    showResult(collectionId, projectId) {
      var data = {
        collectionId: collectionId,
        projectId: projectId,
        projectType: this.type
      };
      getProjectResult(data).then(
        response => {
          this.currentResult = response.data.data.result;
        },
        error => {
          console.log(error);
        }
      );
      this.dialogShowResult = true;
    },
    useProjectTemplate(givenTemplate) {
      console.log("使用了模版");
      givenTemplate.roleId = this.$store.getters["user/currentRoleId"];
      givenTemplate.caseId = this.caseExaminations.caseId;
      givenTemplate.collectionType = givenTemplate.type;
      givenTemplate.applicantRoleId = this.$store.getters["user/currentRoleId"];
      givenTemplate.projects.forEach(project => (project.status = 1));

      console.log(givenTemplate);
      // 想后端请求新的recipe编号;
      getNewCollectionId(this.type).then(
        response => {
          const newCollectionId = response.data.data.collectionId;
          givenTemplate.collectionId = newCollectionId;
          this.caseExaminations.collections.push(givenTemplate);
        },
        error => {
          console.log(error);
        }
      );
    },
    handleAddTemplate(collection) {
      console.log("开始添加模版");
      this.newTemplate = Object.assign({}, collection);
      this.dialogAddTemplate = true;
    },
    handleConfirmAddTemplate() {
      this.newTemplate.roleId = this.$store.getters["user/currentRoleId"];
      this.newTemplate.type = this.type;
      this.newTemplate.departmentId = this.$store.getters[
        "user/currentDepartmentId"
      ];
      console.log("新检查/检验组套：");
      console.log(this.newTemplate);
      saveProjectTemplate(this.newTemplate).then(
        response => {
          successDialog("成功添加模版");
        },
        error => {
          failDialog("模版添加失败");
          console.log(error);
        }
      );
      this.dialogAddTemplate = false;
    }
  },
  mounted: function() {
    listAllProjects(this.type).then(
      response => {
        this.projects = response.data.data;
      },
      error => {
        console.log(error);
      }
    );
  },
  components: {
    "project-template": ProjectTemplate
  }
};
</script>

<style lang="css" scoped>
.outpatient-service-container {
  display: flex;
  flex-direction: column;
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

.template-tabs {
  position: -webkit-sticky;
  position: sticky;
  top: 0px;
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

.table-container {
  border: 1px solid #e8e8e8;
  border-radius: 3px;
  margin-top: 5px;
}

.item-table {
  border: 1px solid #e8e8e8;
  border-radius: 3px;
  margin-top: 5px;
  width: 100%;
}
.collection-detail {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
}
</style>
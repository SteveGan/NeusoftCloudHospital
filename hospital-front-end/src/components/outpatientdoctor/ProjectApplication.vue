<template>
  <div class="outpatient-service-container">
    <!-- 左侧操作区 -->
    <div class="service-main-container">
      <!-- 工具栏
      <el-card :body-style="{padding:'0px'}" style="margin-bottom: 5px;">
        <el-button type="text" icon="el-icon-refresh-right" round>清屏</el-button>
        <el-button type="text" icon="el-icon-folder-checked" round>暂存</el-button>
        <el-button type="text" icon="el-icon-printer" round>打印</el-button>
      </el-card>-->

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
    <!-- 右侧模版区域 -->
    <div class="service-side-container">
      <!-- 导航栏(也就是一个标签页) -->
      <el-tabs type="border-card" class="template-tabs">
        <!-- 门诊首页tab-->
        <el-tab-pane label="病历模版">
          <p>haha</p>
          <p>haha</p>
          <p>haha</p>
          <p>haha</p>
          <p>haha</p>
          <p>haha</p>
          <p>haha</p>
        </el-tab-pane>
        <el-tab-pane label="常用诊断"></el-tab-pane>
        <el-tab-pane label="历史病历"></el-tab-pane>
      </el-tabs>
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
      dialogShowResult: false,
      currentResult: {},
      currentResult: {}
    };
  },
  props: {
    value: Object,
    type: Number,
    typeName: String
  },
  // watch: {
  //   innerCollections: function(newValue, oldValue) {
  //     console.log("change happens");
  //     console.log(newValue);
  //     var collections = newValue.collections;
  //     var isEditable = [];
  //     var i = 0;
  //     var length = collections.length;
  //     for (i = 0; i < length; i++) {
  //       if (
  //         collections[i].projects.length !== 0 &&
  //         collections[i].projects[0].status !== 1
  //       ) {
  //         isEditable.push(true);
  //       } else {
  //         isEditable.push(false);
  //       }
  //     }
  //     this.isEditable = isEditable;
  //   }
  // },
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
      this.newProject.status = 1;
      this.currentCollection.projects.push(this.newProject);
      this.newProject = { items: [] };
      this.newItem = {};
    },
    handleAddProjectDialog(collection) {
      this.dialogAddProject = true;
      this.currentCollection = collection;
    },
    handleTempSave(collection) {
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
  }
};
</script>

<style lang="css" scoped>
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
</style>
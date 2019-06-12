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
        v-for="(collection, index) in caseDispositions.collections"
        v-bind:key="collection.collectionId"
      >
        <!-- 操作栏 -->
        <div slot="header" class="clearfix">
          <span>处置申请单 {{index + 1}}</span>
          <el-button
            style="float:right; margin-left: 10px;"
            type="text"
            icon="el-icon-document-add"
          >存为模版</el-button>
          <el-button
            style="float:right; margin-left: 10px;"
            type="text"
            icon="el-icon-s-check"
            @click="handleSubmit(collection)"
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
            <el-table-column prop="projectName" label="项目名称"></el-table-column>
            <el-table-column prop="departmentName" label="部门名称"></el-table-column>
            <el-table-column prop="status" label="执行状态" :formatter="statusFormatter"></el-table-column>
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
        <el-button type="text" icon="el-icon-plus" @click="handleAddCollection">新增处置申请列表</el-button>
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
          </el-form>
        </el-card>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleCancel">取消</el-button>
        <el-button type="primary" @click="handleConfirmAdd">添加</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  getNewCollectionId,
  listAllProjects,
  listAllItems,
  saveCollection
} from "@/api/project";
import { projectStatusCodeToString } from "@/utils/interpreter";
import { PassThrough } from "stream";

export default {
  name: "CaseDisposition",
  data() {
    return {
      dialogAddProject: false,
      newProject: { items: [] },
      currentCollection: {},
      projects: [],
      addProjectButtonDisabled: []
    };
  },
  props: {
    value: Object
  },
  computed: {
    isEditable: function() {
      var collections = this.caseDispositions.collections;
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
      return isEditable;
    },
    caseDispositions: {
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
    handleAddCollection() {
      // this.collections.push({});
      // console.log(this.collections);
      getNewCollectionId(3).then(
        response => {
          console.log(response.data.data);
          const newCollectionId = response.data.data.collectionId;
          this.caseDispositions.collections.push({
            collectionId: newCollectionId,
            projects: []
          });
        },
        error => {
          console.log(response.data.data);
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
    createProjectFilter(queryString) {
      return project => {
        return (
          project.projectName
            .toLowerCase()
            .indexOf(queryString.toLowerCase()) === 0
        );
      };
    },
    handleSelectProject(item) {
      this.newProject.departmentName = item.departmentName;
      this.newProject.projectId = item.projectId;
      console.log("projectId" + this.newProject.projectId);
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
      this.newProject = {};
      this.dialogAddProject = false;
    },
    handleConfirmAdd() {
      this.dialogAddProject = false;
      this.newProject.status = 1;
      this.currentCollection.projects.push(this.newProject);
      this.newProject = {};
    },
    handleAddProjectDialog(collection) {
      this.dialogAddProject = true;
      this.currentCollection = collection;
    },
    handleTempSave(collection) {
      collection.caseId = this.caseDispositions.caseId;
      collection.applicantRoleId = this.$store.getters["user/currentRoleId"];
      collection.collectionType = 3;
      saveCollection(collection).then(
        response => {
          console.log(response);
        },
        error => {
          console.log(error);
        }
      );
    },
    handleSubmit(collection) {
      collection.caseId = this.caseDispositions.caseId;
      collection.applicantRoleId = this.$store.getters["user/currentRoleId"];
      collection.collectionType = 3;
      // 将所有内存的project 状态都改为 2.已开立
      var i;
      var length = collection.projects.length;
      for (i = 0; i < length; i++) {
        collection.projects[i].status = 2;
      }
      saveCollection(collection).then(
        response => {
          console.log(response);
        },
        error => {
          console.log(error);
        }
      );
    },
    handleClear(collection) {
      collection.projects = [];
      this.handleTempSave(collection);
    }
  },
  mounted: function() {
    listAllProjects(3).then(
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
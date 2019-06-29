<template>
  <div>
    <!-- 导航栏(也就是一个标签页) -->
    <el-tabs type="border-card" class="template-tabs">
      <!-- 处置模版 -->
      <el-tab-pane label="模版">
        <div class="project-template">
          <!-- 左侧browser区 -->
          <div class="project-browser">
            <el-card shadow="hover">
              <el-tabs stretch>
                <el-tab-pane label="个人" name="first">
                  <div class="table-container">
                    <el-table :data="personalTemplates">
                      <el-table-column label="模版名称" prop="name"></el-table-column>
                      <el-table-column label="操作">
                        <template slot-scope="scope">
                          <el-button type="text" @click="handleUseTemplate(scope.row)">使用</el-button>
                          <el-button type="text" @click="handleShowDetail(scope.row)">查看详情</el-button>
                        </template>
                      </el-table-column>
                    </el-table>
                  </div>
                </el-tab-pane>
                <el-tab-pane label="部门" name="second">
                  <div class="table-container">
                    <el-table :data="departmentTemplates">
                      <el-table-column label="模版名称" prop="name"></el-table-column>
                      <el-table-column label="操作">
                        <template slot-scope="scope">
                          <el-button type="text" @click="handleUseTemplate(scope.row)">使用</el-button>
                          <el-button type="text" @click="handleShowDetail(scope.row)">查看详情</el-button>
                        </template>
                      </el-table-column>
                    </el-table>
                  </div>
                </el-tab-pane>
                <el-tab-pane label="全院" name="third">
                  <div class="table-container">
                    <el-table :data="hospitalTemplates">
                      <el-table-column label="模版名称" prop="name"></el-table-column>
                      <el-table-column label="操作">
                        <template slot-scope="scope">
                          <el-button type="text" @click="handleUseTemplate(scope.row)">使用</el-button>
                          <el-button type="text" @click="handleShowDetail(scope.row)">查看详情</el-button>
                        </template>
                      </el-table-column>
                    </el-table>
                  </div>
                </el-tab-pane>
              </el-tabs>
            </el-card>
          </div>
          <!-- 右侧模版内容展示区 -->
          <div class="project-detail">
            <el-card shadow="hover">
              <el-table style="width: 100%" :data="currentTemplate.projects">
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
            </el-card>
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>


<script>
import { listProjectTemplate } from "@/api/projectTemplate";
import { connect } from "net";

export default {
  name: "ProjectTemplate",
  data() {
    return {
      personalTemplates: [],
      departmentTemplates: [],
      hospitalTemplates: [],
      currentTemplate: {}
    };
  },
  props: {
    type: Number,
    typeName: String
  },
  methods: {
    handleShowDetail(row) {
      console.log(row);
      this.currentTemplate = Object.assign({}, row);
    },
    handleUseTemplate(row) {
      this.currentTemplate = Object.assign({}, row);
      this.$emit("give-project-template", this.currentTemplate);
    },
    listAllTemplates(roleId, type) {
      listProjectTemplate(roleId, type).then(
        response => {
          console.log(
            "[[[[[[[[[[[[[   请求project模版 ]]]]]]]]]]]]]]" + this.typeName
          );
          this.personalTemplates = response.data.data.personalTemplates;
          this.hospitalTemplates = response.data.data.hospitalTemplates;
          this.departmentTemplates = response.data.data.departmentTemplates;
        },
        error => {
          console.log(error);
        }
      );
    }
  },
  mounted: function() {
    // 请求所有模版
    listProjectTemplate(
      this.$store.getters["user/currentRoleId"],
      this.type
    ).then(
      response => {
        console.log(
          "[[[[[[[[[[[[[   请求project模版 ]]]]]]]]]]]]]]" + this.typeName
        );
        this.personalTemplates = response.data.data.personalTemplates;
        this.hospitalTemplates = response.data.data.hospitalTemplates;
        this.departmentTemplates = response.data.data.departmentTemplates;
      },
      error => {
        console.log(error);
      }
    );
  }
};
</script>


<style lang="">
.project-template {
  display: grid;
  grid-template-columns: 40% 60%;
}

.project-browser {
  grid-column: 1/2;
  padding: 2px;
}

.project-detail {
  grid-column: 2;
  padding: 2px;
}

.table-container {
  border: 1px solid #f5f5f5;
  border-radius: 3px;
  padding: 0;
}

.collection-detail {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
}

.collection-detail > p {
  font-size: 15px;
}
</style>
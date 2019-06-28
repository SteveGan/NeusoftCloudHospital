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
                          <el-button type="text">使用</el-button>
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
                          <el-button>使用</el-button>
                          <el-button>查看详情</el-button>
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
                          <el-button>使用</el-button>
                          <el-button>查看详情</el-button>
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
              <div slot="header" class="clearfix">
                <div class="collection-detail">
                  <p>项目名称：{{currentTemplate.name}}</p>
                  <p>执行部门：{{currentTemplate.departmentName}}</p>
                  <p>检查目的：{{currentTemplate.goal}}</p>
                  <p>检查要求：{{currentTemplate.requirement}}</p>
                </div>
                <div class="table-container">
                  <el-table :data="currentTemplate.projects">
                    <el-table-column prop="itemId" label="小项ID"></el-table-column>
                    <el-table-column prop="itemName" label="小项名称"></el-table-column>
                    <el-table-column prop="amount" label="单位数量"></el-table-column>
                  </el-table>
                </div>
              </div>
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
      this.$set(this.currentTemplate, "projectName", row.name);
      this.$set(this.currentTemplate, "departmentName", row.depatmentId);
      // this.$set(this.currentTemplate, "goal", row.goal);
      // this.$set(this.currentTemplate, "requirement", row.requirement);
      // this.$set(this.currentTemplate, "items", row.projects);
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
  flex-direction: row;
  justify-content: flex-start;
}

.collection-detail > p {
  font-size: 15px;
}
</style>
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
              <el-tab-pane label="个人" name="first">
                <div class="table-container">
                  <el-table :data="allTemplates">
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
              <el-tab-pane label="部门" name="second">
                <div class="table-container">
                  <el-table :data="allTemplates">
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
                  <el-table :data="allTemplates">
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
            </el-card>
          </div>
          <!-- 右侧模版内容展示区 -->
          <div class="recipe-detail">
            <el-card shadow="hover">
              <div slot="header" class="clearfix">
                <span>{{currentTemplate.name}}</span>
              </div>
            </el-card>
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>


<script>
export default {
  name: "ProjectTemplate",
  data() {
    return {
      allTemplate: {},
      currentTemplate: {}
    };
  },
  props: {
    type: Number,
    typeName: String
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
</style>
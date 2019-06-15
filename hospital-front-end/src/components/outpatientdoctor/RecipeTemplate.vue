<template>
  <div>
    <!-- 导航栏(也就是一个标签页) -->
    <el-tabs type="border-card" class="template-tabs">
      <!-- 门诊首页tab-->
      <el-tab-pane label="处方模版">
        <div class="recipe-template">
          <!-- 左侧browser区 -->
          <div class="recipe-browser">
            <el-card shadow="hover">
              <el-tabs stretch>
                <el-tab-pane label="个人" name="first">
                  <div class="table-container">
                    <el-table>
                      <el-table-column label="处方名称"></el-table-column>
                      <el-table-column label="操作">
                        <template slot-scope="scope">
                          <el-button>使用</el-button>
                          <el-button>查看详情</el-button>
                        </template>
                      </el-table-column>
                    </el-table>
                  </div>
                </el-tab-pane>
                <el-tab-pane label="科室" name="second">
                  <div class="table-container">
                    <el-table>
                      <el-table-column label="处方名称"></el-table-column>
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
                    <el-table>
                      <el-table-column label="处方名称"></el-table-column>
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
          <!-- 右侧处方内容展示区-->
          <div class="recipe-detail">
            <el-card shadow="hover"></el-card>
          </div>
        </div>
      </el-tab-pane>
      <el-tab-pane label="常用药品"></el-tab-pane>
      <el-tab-pane label="建议方案"></el-tab-pane>
      <el-tab-pane label="历史处方"></el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import { listRecipeTemplates } from "@/api/recipeTemplate";

export default {
  name: "RecipeTemplate",
  props: {
    value: Object
  },
  computed: {
    recipeTemplate: {
      get() {
        return this.value;
      },
      set(v) {
        this.$emit("input", v);
      }
    }
  }
};
</script>


<style lang="css" scoped>
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

.table-container {
  border: 1px solid #f5f5f5;
  border-radius: 3px;
  padding: 0;
}
</style>
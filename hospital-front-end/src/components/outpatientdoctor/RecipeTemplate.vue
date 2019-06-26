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
                    <el-table :data="allTemplates.personal">
                      <el-table-column label="处方名称" prop="name"></el-table-column>
                      <el-table-column label="操作">
                        <template slot-scope="scope">
                          <el-button @click="useRecipe(scope.row)">使用</el-button>
                          <el-button @click="showRecipeDetail(scope.row)">查看详情</el-button>
                        </template>
                      </el-table-column>
                    </el-table>
                  </div>
                </el-tab-pane>
                <el-tab-pane label="科室" name="second">
                  <div class="table-container">
                    <el-table :data="allTemplates.department">
                      <el-table-column label="处方名称" prop="name"></el-table-column>
                      <el-table-column label="操作">
                        <template slot-scope="scope">
                          <el-button @click="useRecipe(scope.row)" type="text">使用</el-button>
                          <el-button @click="showRecipeDetail(scope.row)" type="text">查看详情</el-button>
                        </template>
                      </el-table-column>
                    </el-table>
                  </div>
                </el-tab-pane>
                <el-tab-pane label="全院" name="third">
                  <div class="table-container">
                    <el-table :data="allTemplates.hospital">
                      <el-table-column label="处方名称" prop="name"></el-table-column>
                      <el-table-column label="操作">
                        <template slot-scope="scope">
                          <el-button @click="useRecipe(scope.row)">使用</el-button>
                          <el-button @click="showRecipeDetail(scope.row)">查看详情</el-button>
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
            <el-card shadow="hover">
              <div slot="header" class="clearfix">
                <span>{{currentTemplate.name}}</span>
              </div>
              <el-table style="width: 100%" :data="currentTemplate.medicines">
                <el-table-column label="药品名称" prop="medicineName"></el-table-column>
                <el-table-column label="规格" prop="medicineSpecification"></el-table-column>
                <el-table-column label="单次用量" prop="dosage"></el-table-column>
                <el-table-column label="剂型" prop="medicineFormulation"></el-table-column>
                <el-table-column label="频次" prop="frequency"></el-table-column>
                <el-table-column label="数量" prop="amount"></el-table-column>
                <el-table-column label="单位" prop="medicineUnit"></el-table-column>
              </el-table>
            </el-card>
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
  data() {
    return {
      allTemplates: {},
      currentTemplate: {}
    };
  },
  methods: {
    showRecipeDetail(row) {
      this.currentTemplate = row;
    },
    useRecipe(row) {
      this.currentTemplate = row;
      console.log("返回的模版：");
      console.log(this.currentTemplate);
      this.$emit("give-recipe-template", this.currentTemplate);
    }
  },
  props: {
    recipeType: Number
  },
  watch: {
    recipeType: function(newValue, oldValue) {
      console.log("模版类型：");
      console.log(this.recipeType);
      listRecipeTemplates(
        this.$store.getters["user/currentRoleId"],
        this.recipeType
      ).then(
        response => {
          console.log("当前读取到的处方模版");
          console.log(response.data.data);
          this.allTemplates = Object.assign({}, response.data.data);
          console.log(this.allTemplates);
        },
        error => {
          console.log(error);
        }
      );
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
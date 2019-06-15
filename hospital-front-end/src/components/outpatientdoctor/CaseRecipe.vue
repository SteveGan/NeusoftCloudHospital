<template>
  <div class="recipe-service-container">
    <!-- 主操作区 -->
    <div>
      <!-- 一个处方 -->
      <el-card v-for="(recipe, index) in caseRecipe.recipes" v-bind:key="recipe.recipeId">
        <!-- 操作栏 -->
        <div slot="header" class="clearfix">
          <span>处方 {{index + 1}}</span>
          <el-button
            style="float:right; margin-left: 10px;"
            type="text"
            icon="el-icon-document-add"
          >存为模版</el-button>
          <el-button
            style="float:right; margin-left: 10px;"
            type="text"
            icon="el-icon-s-check"
            @click="handleSubmitRecipe(recipe)"
          >开立</el-button>
          <el-button style="float:right; margin-left: 10px;" type="text">作废</el-button>
          <el-button
            style="float:right; margin-left: 10px;"
            type="text"
            icon="el-icon-folder-checked"
            @click="handleSaveRecipe(recipe)"
          >暂存</el-button>
          <el-button
            style="float:right; margin-left: 10px;"
            type="text"
            icon="el-icon-refresh-right"
          >清空</el-button>
          <el-button
            style="margin-left: 10px"
            type="text"
            icon="el-icon-circle-plus"
            @click="handleAddMedicineDialog(recipe)"
          >新增药品</el-button>
        </div>
        <!-- 项目列表 -->
        <div class>
          <el-table style="width: 100%" :data="recipe.medicines">
            <el-table-column label="药品名称" prop="medicineName"></el-table-column>
            <el-table-column label="规格" prop="medicineSpecification"></el-table-column>
            <el-table-column label="单次用量" prop="dosage"></el-table-column>
            <el-table-column label="剂型" prop="medicineFormulation"></el-table-column>
            <el-table-column label="频次" prop="frequency"></el-table-column>
            <el-table-column label="数量" prop="amount"></el-table-column>
            <el-table-column label="单位" prop="medicineUnit"></el-table-column>
            <el-table-column>
              <template slot-scope="scope">
                <el-button
                  type="text"
                  icon="el-icon-delete"
                  @click="handleRemoveDrug(scope.row, recipe)"
                >删除</el-button>
                <el-button type="text" icon="el-icon-edit">编辑</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-card>
      <!-- 添加处方按钮 -->
      <div class="add-recipe-button">
        <el-button type="text" icon="el-icon-plus" round @click="handleAddNewRecipe">新增处方</el-button>
      </div>
    </div>
    <!-- 底部模版区域 -->
    <div>
      <recipe-template v-model="currentRecipeTemplate"></recipe-template>
    </div>
    <!-- 新增药品dialog -->
    <el-dialog
      title="添加药品"
      :visible.sync="dialogAddMedicine"
      :before-close="handleClose"
      width="600px"
    >
      <div>
        <el-card shadow="hover" style="margin-bottom: 10px">
          <el-form label-position="left" label-width="80px" :model="newMedicine">
            <el-form-item label="药品名称">
              <el-autocomplete
                class="inline-input"
                v-model="newMedicine.medicineName"
                :fetch-suggestions="queryMedicineSearch"
                placeholder="请输入药品名称"
                :highlight-first-item="true"
                @select="handleSelectMedicine"
                value-key="name"
              ></el-autocomplete>
            </el-form-item>
            <el-form-item label="数量">
              <el-input-number :min="1" :max="10" v-model="newMedicine.amount"></el-input-number>
            </el-form-item>
            <el-form-item label="频次">
              <el-input-number :min="1" :max="10" v-model="newMedicine.frequency"></el-input-number>
            </el-form-item>
            <el-form-item label="用量">
              <el-input-number :min="1" :max="10" v-model="newMedicine.dosage"></el-input-number>
            </el-form-item>
          </el-form>
        </el-card>
        <span slot="footer" class="dialog-footer">
          <el-button @click="handleCancel">取消</el-button>
          <el-button type="primary" @click="handleConfirmAdd">添加</el-button>
        </span>
      </div>
    </el-dialog>
    <!-- 编辑药品dialog to do-->
  </div>
</template>

<script>
import {
  listAllMedicines,
  getNewRecipeCode,
  saveRecipe,
  submitRecipe
} from "@/api/recipe";
import { listRecipeTemplates } from "@/api/recipeTemplate";
import { constants } from "fs";
import {
  medicineTypeCodeToString,
  medicineTypeToCode
} from "@/utils/interpreter";
import RecipeTemplate from "./RecipeTemplate";

export default {
  name: "CaseRecipe",
  data() {
    return {
      dialogAddMedicine: false,
      newMedicine: {},
      currentRecipe: {},
      medicines: [],
      currentRecipeTemplate: {}
    };
  },
  components: {
    "recipe-template": RecipeTemplate
  },
  props: {
    value: Object
  },
  computed: {
    caseRecipe: {
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
    handleAddMedicineDialog(recipe) {
      this.dialogAddMedicine = true;
      this.currentRecipe = recipe;
    },
    handleRemoveDrug(row, recipe) {
      recipe.medicines.splice(
        recipe.medicines.findIndex(medicine => {
          medicine.medicineName === row.medicineName;
        }),
        1
      );
    },
    queryMedicineSearch(queryString, cb) {
      var medicines = this.medicines;
      var results = queryString
        ? medicines.filter(this.createMedicineFilter(queryString))
        : medicines;
      cb(results);
    },
    createMedicineFilter(queryString) {
      return medicine => {
        return (
          medicine.name.toLowerCase().indexOf(queryString.toLowerCase()) === 0
        );
      };
    },
    handleSelectMedicine(medicine) {
      this.newMedicine.medicineUnit = medicine.unit;
      this.newMedicine.medicineId = medicine.id;
      console.log("药品的类型是" + medicine.type);
      this.newMedicine.medicineType = medicineTypeToCode(medicine.type);
      this.newMedicine.medicineFormulation = medicine.formulation;
      this.newMedicine.medicineSpecification = medicine.specification;
    },
    handleCancel() {
      this.newMedicine = {};
      this.dialogAddMedicine = false;
    },
    handleConfirmAdd() {
      this.dialogAddMedicine = false;
      this.newMedicine.status = 1;
      // // 判断当前的recipe是否已经有了五种药,如果有了
      // // 增加新的recipe，并将当前的recipe变为新的recipe
      // if (this.currentRecipe.medicines.length >= 5)
      //   this.currentRecipe = this.handleAddNewRecipe();
      // console.log("新的recipe");
      // console.log(this.currentRecipe);
      this.currentRecipe.medicines.push(this.newMedicine);
      this.newMedicine = {};
    },
    handleAddNewRecipe() {
      // 向后端请求新的recipe编号
      getNewRecipeCode().then(
        response => {
          this.caseRecipe.recipes.push({
            medicines: [],
            recipeId: response.data.data.recipeId
          });
          newRecipe;
        },
        error => {
          console.log(error);
        }
      );
    },
    handleSaveRecipe(recipe) {
      recipe.caseId = this.caseRecipe.caseId;
      recipe.creatorRoleId = this.$store.getters["user/currentRoleId"];
      saveRecipe(recipe).then(
        response => {
          console.log(response);
        },
        error => {
          console.log(error);
        }
      );
    },
    handleSubmitRecipe(recipe) {
      recipe.caseId = this.caseRecipe.caseId;
      recipe.creatorRoleId = this.$store.getters["user/currentRoleId"];
      //将所有药的状态都改成开立
      var i;
      var length = recipe.medicines.length;
      for (i = 0; i < length; i++) {
        recipe.medicines[i].status = 2;
      }
      submitRecipe(recipe).then(
        response => {
          console.log(response);
        },
        error => {
          console.log(error);
        }
      );
    }
  },
  mounted: function() {
    // 请求所有的西药（暂时这么写） 中:0, 西:1
    listAllMedicines(1).then(
      response => {
        this.medicines = response.data.data;
      },
      error => {
        console.log(error);
      }
    );
    //请求相应的可用处方模版
    listRecipeTemplates(
      this.$store.getters["user/roleId"],
      this.caseRecipe.type
    ).then(
      response => {
        this.currentRecipeTemplate = response.data.data;
        console.log("当前的处方模版：");
        console.log(this.currentRecipeTemplate);
      },
      error => {
        console.log(error);
      }
    );
  }
};
</script>

<style lang="css" scoped>
.service-main-container {
  grid-column: 1/2;
  margin-left: 2px;
}

.recipe-service-container {
  display: flex;
  flex-direction: column;
}

.service-main-container .el-card {
  margin-right: 5px;
}

.add-recipe-button {
  margin-top: 5px;
  margin-bottom: 5px;
}
</style>
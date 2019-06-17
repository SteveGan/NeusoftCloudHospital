<template>
  <div>
    <el-card shadow="hover">
      <div slot="header">
        <span>个人模版管理</span>
      </div>
      <!-- 内容部分 -->
      <div class="business-region">
        <!-- 搜索区以及操作按钮 -->
        <div class="tool-bar">
          <!-- 搜索区 -->
          <div class="search-region">
            <i class="el-icon-search"></i>
            <el-input placeholder="通过模版名称搜索" class="search-bar"></el-input>
          </div>
          <!-- 添加，批量管理按钮 -->
          <div class="button-group">
            <!-- 添加按钮 -->
            <div>
              <el-button icon="el-icon-plus" type="primary" @click="dialogAddCaseTemplate=true">新增模版</el-button>
            </div>
            <!-- 删除按钮 -->
            <div>
              <el-button icon="el-icon-delete" @click="handleRemoveTemplates" plain>删除模版</el-button>
            </div>
          </div>
        </div>
        <!-- 表格区 -->
        <div class="table-region">
          <el-table :data="myCaseTemplates" @selection-change="handleSelectTemplate">
            <el-table-column type="selection"></el-table-column>
            <el-table-column type="expand">
              <template slot-scope="props">
                <el-form label-position="left" class="demo-table-expand">
                  <el-form-item label="模版名称">
                    <span>{{props.row.name}}</span>
                  </el-form-item>
                  <el-form-item label="主诉">
                    <span>{{props.row.narrate}}</span>
                  </el-form-item>
                  <el-form-item label="现病史">
                    <span>{{props.row.curDisease}}</span>
                  </el-form-item>
                  <el-form-item label="既往史">
                    <span>{{props.row.pastDisease}}</span>
                  </el-form-item>
                  <el-form-item label="过敏信息">
                    <span>{{props.row.allergy}}</span>
                  </el-form-item>
                  <el-form-item label="体格检查">
                    <span>{{props.row.physicalCondition}}</span>
                  </el-form-item>
                  <el-form-item label="辅助检查">
                    <span>{{props.row.assistDiagnose}}</span>
                  </el-form-item>
                </el-form>
              </template>
            </el-table-column>
            <el-table-column label="模版名称" prop="name"></el-table-column>
            <el-table-column label="主诉" prop="narrate"></el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button type="text" size="mini" @click="handleEdit(scope.row)">编辑</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
    </el-card>

    <!-- 添加模版dialog -->
    <el-dialog title="新增模版" :visible.sync="dialogAddCaseTemplate" :before-close="handleClose">
      <el-card shadow="hover" class="input-card">
        <div slot="header">
          <span>模版信息</span>
        </div>
        <el-form :model="newCaseTemplate" label-width="80px">
          <el-form-item label="模版名称">
            <el-input v-model="newCaseTemplate.name"></el-input>
          </el-form-item>
          <el-form-item label="使用权限">
            <el-select v-model="newCaseTemplate.scope" placeholder="请选择">
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
      <el-card shadow="hover" class="input-card">
        <div slot="header">
          <span>病史病历</span>
        </div>
        <el-form :model="newCaseTemplate" label-width="80px">
          <el-form-item label="主诉">
            <el-input v-model="newCaseTemplate.narrate"></el-input>
          </el-form-item>
          <el-form-item label="现病史">
            <el-input v-model="newCaseTemplate.curDisease"></el-input>
          </el-form-item>
          <el-form-item label="既往病史">
            <el-input v-model="newCaseTemplate.pastDisease"></el-input>
          </el-form-item>
          <el-form-item label="过敏信息">
            <el-input v-model="newCaseTemplate.allergy"></el-input>
          </el-form-item>
        </el-form>
      </el-card>
      <el-card shadow="hover" class="input-card">
        <div slot="header">
          <span>检查及结果</span>
        </div>
        <el-form :model="newCaseTemplate" label-width="80px">
          <el-form-item label="体格检查">
            <el-input v-model="newCaseTemplate.physicalCondition"></el-input>
          </el-form-item>
          <el-form-item label="辅助检查">
            <el-input v-model="newCaseTemplate.assistDiagnose"></el-input>
          </el-form-item>
        </el-form>
      </el-card>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelAdd">取消</el-button>
        <el-button type="primary" @click="addNewCaseTemplate">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 编辑模版dialog -->
    <el-dialog title="编辑模版" :visible.sync="dialogEditCaseTemplate" :before-close="handleClose">
      <el-card shadow="hover" class="input-card">
        <div slot="header">
          <span>模版信息</span>
        </div>
        <el-form :model="selectedCaseTemplate" label-width="80px">
          <el-form-item label="模版名称">
            <el-input v-model="selectedCaseTemplate.name"></el-input>
          </el-form-item>
          <el-form-item label="使用权限">
            <el-select v-model="selectedCaseTemplate.scope" placeholder="请选择">
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
      <el-card shadow="hover" class="input-card">
        <div slot="header">
          <span>病史病历</span>
        </div>
        <el-form :model="selectedCaseTemplate" label-width="80px">
          <el-form-item label="主诉">
            <el-input v-model="selectedCaseTemplate.narrate"></el-input>
          </el-form-item>
          <el-form-item label="现病史">
            <el-input v-model="selectedCaseTemplate.curDisease"></el-input>
          </el-form-item>
          <el-form-item label="既往病史">
            <el-input v-model="selectedCaseTemplate.pastDisease"></el-input>
          </el-form-item>
          <el-form-item label="过敏信息">
            <el-input v-model="selectedCaseTemplate.allergy"></el-input>
          </el-form-item>
        </el-form>
      </el-card>
      <el-card shadow="hover" class="input-card">
        <div slot="header">
          <span>检查及结果</span>
        </div>
        <el-form :model="selectedCaseTemplate" label-width="80px">
          <el-form-item label="体格检查">
            <el-input v-model="selectedCaseTemplate.physicalCondition"></el-input>
          </el-form-item>
          <el-form-item label="辅助检查">
            <el-input v-model="selectedCaseTemplate.assistDiagnose"></el-input>
          </el-form-item>
        </el-form>
      </el-card>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelAdd">取消</el-button>
        <el-button type="primary" @click="updateSelectedCaseTemplate">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  listCaseTemplate,
  submitNewCaseTemplate,
  updateCaseTemplate
} from "@/api/caseTemplate";

export default {
  name: "CaseTemplateAdmin",
  data() {
    return {
      myCaseTemplates: [],
      newCaseTemplate: {},
      dialogAddCaseTemplate: false,
      selectedCaseTemplate: {},
      dialogEditCaseTemplate: false,
      oldTemplateName: "",
      selectedTemplates: [],
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
  methods: {
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then(_ => {
          done();
        })
        .catch(_ => {});
    },
    cancelAdd() {
      this.dialogAddCaseTemplate = false;
      this.dialogEditCaseTemplate = false;
      this.newCaseTemplate = {};
      this.selectedCaseTemplate = {};
    },
    handleEdit(row) {
      this.selectedCaseTemplate = row;
      this.oldTemplateName = this.selectedCaseTemplate.name;
      this.dialogEditCaseTemplate = true;
    },
    addNewCaseTemplate() {
      this.dialogAddCaseTemplate = false;
      // 将新的病历模版放入当前病历中
      this.myCaseTemplates.push(
        JSON.parse(JSON.stringify(this.newCaseTemplate))
      );

      // 将当前的新模版发送给后端
      this.newCaseTemplate.roleId = this.$store.getters["user/currentRoleId"];
      submitNewCaseTemplate(this.newCaseTemplate).then(
        response => {
          console.log(response);
        },
        error => {
          console.log(error);
        }
      );
      // 将新的病历模版清空
      this.newCaseTemplate = {};
    },
    updateSelectedCaseTemplate() {
      this.dialogEditCaseTemplate = false;
      // 将新的模版发送给后端
      //设置一下 name 和 newName
      this.selectedCaseTemplate.newName = this.selectedCaseTemplate.name;
      this.selectedCaseTemplate.name = this.oldTemplateName;
      //发送
      updateCaseTemplate(this.selectedCaseTemplate).then(
        response => {
          console.log(response);
        },
        error => {
          console.log(error);
        }
      );
      //将newName赋给name
      this.selectedCaseTemplate.name = this.oldTemplateName;
      this.oldTemplateName = "";
    },
    handleSelectTemplate(val) {
      this.selectedTemplates = val;
    },
    handleRemoveTemplates() {
      //遍历所有被选中的templates
      var i;
      for (i = 0; i < this.selectedTemplates.length; i++) {
        this.myCaseTemplates.splice(
          this.myCaseTemplates.findIndex(
            template => template.name === this.selectedTemplates[i].name
          ),
          1
        );
      }
    }
  },
  mounted: function() {
    listCaseTemplate(this.$store.getters["user/currentRoleId"]).then(
      response => {
        this.myCaseTemplates = response.data.data.myCaseTemplates;
      },
      error => {
        console.log("读取病理模版出bug了");
        console.log(error);
      }
    );
  }
};
</script>

<style>
@import "../../styles/admin.css";

.search-bar {
  width: 600px;
}

.input-card {
  margin: 2px;
}
</style>
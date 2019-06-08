<template lang="html">
  <!-- 门诊病历首页内容 -->
  <div class="outpatient-service-container">
    <!-- 左侧操作区 -->
    <div class="service-main-container">
      <!-- 工具栏 -->
      <!-- 工具栏 -->
      <el-card :body-style="{padding:'0px'}" style="margin-bottom: 5px">
        <el-button type="text" icon="el-icon-refresh-right" round>清屏</el-button>
        <el-button type="text" icon="el-icon-folder-checked" round>暂存</el-button>
        <el-button type="text" icon="el-icon-printer" round>打印</el-button>
        <el-button type="text" icon="el-icon-upload" round>提交</el-button>
      </el-card>
      <!-- 操作 -->
      <div class="">
        <!-- 病史病历 -->
        <el-card class="input-card" shadow="hover">
          <div slot="header">
            <span>病史病历</span>
          </div>
          <el-form :model="patientpatientCase" label-position='left'>
            <el-form-item label="主诉">
              <el-input v-model="patientCase.narrate" type="textarea" autosize placeholder="请输入内容">
              </el-input>
            </el-form-item>
            <el-form-item label="现病史">
              <el-input v-model="patientCase.curDisease" type="textarea" autosize placeholder="请输入内容">
              </el-input>
            </el-form-item>
            <el-form-item label="既往史">
              <el-input v-model="patientCase.pastDisease" type="textarea" autosize placeholder="请输入内容">
              </el-input>
            </el-form-item>
            <el-form-item  label="过敏信息">
              <el-input v-model="patientCase.allergy" type="textarea" autosize placeholder="请输入内容">
              </el-input>
            </el-form-item>
          </el-form>
        </el-card>
        <el-card class="input-card" shadow="hover">
          <div slot="header">
            <span>检查及结果</span>
          </div>
          <el-form label-position='left' :model="patientCase">
            <el-form-item label="体格检查">
              <el-input v-model="patientCase.physicalCondition" type="textarea" autosize placeholder="请输入内容">
              </el-input>
            </el-form-item>
            <el-form-item label="辅助检查">
              <el-input v-model="patientCase.assistDiagnose" type="textarea" autosize placeholder="请输入内容">
              </el-input>
            </el-form-item>
          </el-form>
        </el-card>
      </el-card>
      <el-card class="input-card" shadow="hover">
        <div slot="header">
          <span>评估诊断</span>
        </div>
        <!-- 中医诊断 -->
        <div class="">
          <!-- 标题以及 + - 按钮 -->
          <div class="diagnose-header">
            <!-- 标题：中医诊断 -->
            <div class="">
              <p>中医诊断</p>
            </div>
            <!-- 按钮组 -->
            <div style="margin-left: 10px;">
              <!-- 增加按钮 -->
              <el-button type="primary" icon="el-icon-plus" size="mini" circle></el-button>
              <!-- 减少按钮 -->
              <el-button type="primary" icon="el-icon-minus" size="mini" circle></el-button>
            </div>
          </div>
          <!-- 表格 -->
          <div>
            <el-table
              ref="multipleTable"
              tooltip-effect="dark"
              style="width: 100%">
              <el-table-column
                type="selection"
                width="55">
              </el-table-column>
              <el-table-column
                label="ICD编码">
                <template slot-scope="scope"></template>
              </el-table-column>
              <el-table-column
                label="主诊"
                width="120">
              </el-table-column>
              <el-table-column
                label="疑似">
              </el-table-column>
              <el-table-column
                label="发病日期">
              </el-table-column>
            </el-table>
          </div>
        </div>
        <!-- 西医诊断 -->
        <div class="">
          <!-- 标题以及 + - 按钮 -->
          <div class="diagnose-header">
            <!-- 标题：中医诊断 -->
            <div class="">
              <p>中医诊断</p>
            </div>
            <!-- 按钮组 -->
            <div style="margin-left: 10px;">
              <!-- 增加按钮 -->
              <el-button type="primary" icon="el-icon-plus" size="mini" circle></el-button>
              <!-- 减少按钮 -->
              <el-button type="primary" icon="el-icon-minus" size="mini" circle></el-button>
            </div>
          </div>
          <!-- 表格 -->
          <div>
            <el-table
              ref="multipleTable"
              tooltip-effect="dark"
              style="width: 100%">
              <el-table-column
                type="selection"
                width="55">
              </el-table-column>
              <el-table-column
                label="ICD编码">
                <template slot-scope="scope"></template>
              </el-table-column>
              <el-table-column
                label="主诊"
                width="120">
              </el-table-column>
              <el-table-column
                label="疑似">
              </el-table-column>
              <el-table-column
                label="发病日期">
              </el-table-column>
            </el-table>
          </div>
        </div>
      </el-card>
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
  </div>
</template>

<script>
export default {
  name: 'OutPatientPreDiagnose',
  props:{
    patientpatientCase: Object
  }
}
</script>

<style lang="css" scoped>
.side-bar{
  height: 100%;
  padding: 10px;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  background-color:#fafafa;
}

.search-user{
  height: 30px;
  margin-bottom:
}

.current-user{
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
}

.basic-info{
  padding-top: 4px;
  font-size: 15px;
  margin-right: auto;
}

.info-card{
  margin-bottom: 4px;
}

.outpatient-service-container{
  display: grid;
  grid-template-columns: 70% 30%;
}

.service-main-container{
  grid-column: 1/2;
  margin-left: 2px;
}

.service-side-container{
  grid-column: 2;
  margin-right: 2px;
  margin-top: 38px;
}

.recipe-service-container{
  display:flex;
  flex-direction: column;
}

.recipe-template{
  display: grid;
  grid-template-columns: 40% 60%;
}

.recipe-browser{
  grid-column: 1/2;
  padding: 2px;
}

.recipe-detail{
  grid-column: 2;
  padding: 2px;
}



.template-tabs{
  position: -webkit-sticky;
  position: sticky;
  top: 0px;
}

.tool-bar{
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  margin-bottom: 5px;
}

.input-card{
  margin-top: 5px;
  margin-right: 10px;
}

.diagnose-header{
  display: flex;
  flex-direction: row;
  align-items:center;
}

.application-number{
  background-color: #597ef7;
  border-radius: 5px;
  font-size: 15px;
  padding: 0px 3px;
  color: white;
}

.service-main-container .el-card{
  margin-right: 5px;
}

.add-recipe-button{
  margin-top: 5px;
  margin-bottom: 5px;
}
</style>

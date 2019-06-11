<template>
  <div class="outpatient-service-container">
    <!-- 左侧操作区 -->
    <div class="service-main-container">
      <!-- 工具栏
      <el-card :body-style="{padding:'0px'}" style="margin-bottom: 5px;">
        <el-button type="text" icon="el-icon-refresh-right" round>清屏</el-button>
        <el-button type="text" icon="el-icon-folder-checked" round>暂存</el-button>
        <el-button type="text" icon="el-icon-printer" round>打印</el-button>
      </el-card> -->

      <!-- 检验申请的操作栏以及表格, 做成一个卡片 -->
      <el-card>
        <!-- 操作栏 -->
        <div slot="header" class="clearfix">
            <span>检验申请单</span>
            <el-button style="float:right; margin-left: 10px;" type="text" icon="el-icon-document-add">存为模版</el-button>
            <el-button style="float:right; margin-left: 10px;" type="text" icon="el-icon-s-check">开立</el-button>
            <el-button style="float:right; margin-left: 10px;" type="text" icon="el-icon-delete-solid">作废</el-button>
            <el-button style="float:right; margin-left: 10px;" type="text" icon="el-icon-folder-checked">暂存</el-button>
            <el-button style="float:right; margin-left: 10px;" type="text" icon="el-icon-refresh-right">清空</el-button>
            <el-button style="margin-left: 10px;" type="text" icon="el-icon-remove">删除项目</el-button>
            <el-button style="margin-left: 10px" type="text" icon="el-icon-circle-plus">新增项目</el-button>
        </div>
        <!-- 项目列表 -->
        <div class="">
          <el-table style="width: 100%">
            <el-table-column
              type="selection"
              width="55">
            </el-table-column>
            <el-table-column
              label="申请名称">
            </el-table-column>
            <el-table-column
              label="项目名称">
            </el-table-column
              label="执行科室">
            <el-table-column
              label="执行状态">
            </el-table-column>
            <el-table-column
              label="检查结果">
            </el-table-column>
          </el-table>
        </div>
      </el-card>
      <!-- 新增申请项目列表button -->
      <div>
        <el-button type="text" icon="el-icon-plus">新增申请项目列表</el-button>
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
  name: "ExaminationApplication"
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
</style>
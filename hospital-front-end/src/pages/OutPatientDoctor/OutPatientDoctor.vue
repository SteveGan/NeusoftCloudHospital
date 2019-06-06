<template lang="html">
  <el-container style="height: 90vh;">
    <el-aside width="250px">
      <!-- 侧栏区域 -->
      <div class="side-bar">
        <!-- 搜索区 -->
        <div class="search-user">
          <el-input placeholder="病人病历" class="input-with-select">
            <el-button slot="append" icon="el-icon-search"></el-button>
          </el-input>
        </div>
        <!-- 待诊用户区 -->
        <el-card shadow="hover">
          <div slot="header">
            <span>待就诊</span>
          </div>
          <!-- 待就诊病人名单 -->
          <div class="">
            <!-- 表格 -->
            <el-table
              :data="waitingPatients"
              style="width: 100%"
              @row-click="handlePatientTableClick">
              <el-table-column
                prop="caseId"
                label="病历号">
              </el-table-column>
              <el-table-column
                prop="patientName"
                label="患者姓名">
              </el-table-column>
            </el-table>
          </div>
        </el-card>

        <!-- 已诊用户区 -->
        <el-card shadow="hover">
          <div slot="header">
            <span>已就诊</span>
          </div>
          <!-- 待就诊病人名单 -->
          <div class="">
            <!-- 表格 -->
            <el-table
              :data="diagnosedPatients"
              style="width: 100%"
              @row-click="handlePatientTableClick">
              <el-table-column
                prop="caseId"
                label="病历号">
              </el-table-column>
              <el-table-column
                prop="patientName"
                label="患者姓名">
              </el-table-column>
            </el-table>
          </div>
        </el-card>
      </div>
    </el-aside>
    <el-main>
      <!-- 当前病人信息 -->
      <el-card shadow="hover" :body-style="{ padding: '5px'}" class="info-card">
        <div class="current-user">
          <!-- 基本信息 -->
          <div class="basic-info">
            <!-- 就诊状态 -->
            <span>就诊状态: {{this.status}} </span>
            <!-- 病历号 -->
            <span>病历号: {{this.case.caseId}} </span>
            <!-- 姓名 -->
            <span>姓名: {{this.selectedPatient.name}} </span>
            <!-- 性别 -->
            <span>性别: {{this.gender}} </span>
            <!-- 年龄 -->
            <span>年龄: {{this.selectedPatient.age}} </span>
            <!-- 结算类别 -->
            <span>结算类别: TODO </span>
          </div>
          <!-- 诊毕 -->
          <el-button type="primary">诊毕</el-button>
          </div>
        </div>
      </el-card>
      <!-- 导航栏(也就是一个标签页) -->
      <el-tabs type="border-card" style="overflow:vible">
        <!-- 门诊首页tab-->
        <el-tab-pane label="门诊首页">
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
                  <el-form label-position='left'>
                    <el-form-item label="主诉">
                      <el-input type="textarea" autosize placeholder="请输入内容" v-model="zhusu">
                      </el-input>
                    </el-form-item>
                    <el-form-item label="现病史">
                      <el-input type="textarea" autosize placeholder="请输入内容" v-model="zhusu">
                      </el-input>
                    </el-form-item>
                    <el-form-item label="既往史">
                      <el-input type="textarea" autosize placeholder="请输入内容" v-model="zhusu">
                      </el-input>
                    </el-form-item>
                    <el-form-item label="个人史">
                      <el-input type="textarea" autosize placeholder="请输入内容" v-model="zhusu">
                      </el-input>
                    </el-form-item>
                  </el-form>
                </el-card>
                <el-card class="input-card" shadow="hover">
                  <div slot="header">
                    <span>检查及结果</span>
                  </div>
                  <el-form label-position='left'>
                    <el-form-item label="体格检查">
                      <el-input type="textarea" autosize placeholder="请输入内容" v-model="zhusu">
                      </el-input>
                    </el-form-item>
                    <el-form-item label="辅助检查">
                      <el-input type="textarea" autosize placeholder="请输入内容" v-model="zhusu">
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
        </el-tab-pane>
        <el-tab-pane label="检验申请">
          <div class="outpatient-service-container">
            <!-- 左侧操作区 -->
            <div class="service-main-container">
              <!-- 工具栏 -->
              <el-card :body-style="{padding:'0px'}" style="margin-bottom: 5px;">
                <el-button type="text" icon="el-icon-refresh-right" round>清屏</el-button>
                <el-button type="text" icon="el-icon-folder-checked" round>暂存</el-button>
                <el-button type="text" icon="el-icon-printer" round>打印</el-button>
              </el-card>
              <!-- 检验申请的操作栏以及表格, 做成一个卡片 -->
              <el-card>
                <!-- 操作栏 -->
                <div slot="header" class="clearfix">
                    <span>检验申请单</span>
                    <el-button style="float:right" type="text" icon="el-icon-document-add" round>存为模版</el-button>
                    <el-button style="float:right" type="text" icon="el-icon-s-check">开立</el-button>
                    <el-button style="float:right" type="text" icon="el-icon-delete-solid">作废</el-button>
                    <el-button style="float:right" type="text" icon="el-icon-remove">删除项目</el-button>
                    <el-button style="float:right" type="text" icon="el-icon-circle-plus">新增项目</el-button>
                </div>
                <!-- 项目列表 -->
                <div class="">
                  <el-table
                    style="width: 100%">
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
        </el-tab-pane>
        <el-tab-pane label="检查申请">
          <div class="outpatient-service-container">
            <!-- 左侧操作区 -->
            <div class="service-main-container">
              <!-- 工具栏 -->
              <el-card :body-style="{padding:'0px'}" style="margin-bottom: 5px;">
                <el-button type="text" icon="el-icon-refresh-right" round>清屏</el-button>
                <el-button type="text" icon="el-icon-folder-checked" round>暂存</el-button>
                <el-button type="text" icon="el-icon-printer" round>打印</el-button>
              </el-card>
              <!-- 检验申请的操作栏以及表格, 做成一个卡片 -->
              <el-card>
                <!-- 操作栏 -->
                <div slot="header" class="clearfix">
                    <span>检验申请单</span>
                    <el-button style="float:right" type="text" icon="el-icon-document-add" round>存为模版</el-button>
                    <el-button style="float:right" type="text" icon="el-icon-s-check">开立</el-button>
                    <el-button style="float:right" type="text" icon="el-icon-delete-solid">作废</el-button>
                    <el-button style="float:right" type="text" icon="el-icon-remove">删除项目</el-button>
                    <el-button style="float:right" type="text" icon="el-icon-circle-plus">新增项目</el-button>
                </div>
                <!-- 项目列表 -->
                <div class="">
                  <el-table
                    style="width: 100%">
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
        </el-tab-pane>
        <el-tab-pane label="成药处方">
          <div class="recipe-service-container">
            <!-- 主操作区 -->
            <div>
              <!-- 工具栏 -->
              <el-card :body-style="{padding:'0px'}" style="margin-bottom: 5px;">
                <el-button type="text" icon="el-icon-refresh-right" round>清屏</el-button>
                <el-button type="text" icon="el-icon-folder-checked" round>暂存</el-button>
                <el-button type="text" icon="el-icon-printer" round>打印</el-button>
              </el-card>
              <!-- 检验申请的操作栏以及表格, 做成一个卡片 -->
              <el-card>
                <!-- 操作栏 -->
                <div slot="header" class="clearfix">
                    <span>处方 1</span>
                    <el-button style="float:right" type="text" icon="el-icon-s-check">开立</el-button>
                    <el-button style="float:right" type="text" icon="el-icon-delete-solid">删除（作废）</el-button>
                    <el-button style="float:right" type="text" icon="el-icon-remove">增药</el-button>
                    <el-button style="float:right" type="text" icon="el-icon-circle-plus">删药</el-button>
                </div>
                <!-- 项目列表 -->
                <div class="">
                  <el-table
                    style="width: 100%">
                    <el-table-column
                      type="selection"
                      width="55">
                    </el-table-column>
                    <el-table-column
                      label="药品名称">
                    </el-table-column>
                    <el-table-column
                      label="规格">
                    </el-table-column
                      label="单价">
                    <el-table-column
                      label="用法">
                    </el-table-column>
                    <el-table-column
                      label="用量">
                    </el-table-column>
                    <el-table-column
                      label="单位">
                    </el-table-column>
                    <el-table-column
                      label="频次">
                    </el-table-column>
                    <el-table-column
                      label="天数">
                    </el-table-column>
                    <el-table-column
                      label="次数">
                    </el-table-column>
                    <el-table-column
                      label="数量">
                    </el-table-column>
                    <el-table-column
                      label="单位">
                    </el-table-column>
                    <el-table-column
                      label="皮试">
                    </el-table-column>
                    <el-table-column
                      label="皮试结果">
                    </el-table-column>
                    <el-table-column
                      label="自购">
                    </el-table-column>
                    <el-table-column
                      label="用药嘱托">
                    </el-table-column>
                  </el-table>
                </div>
              </el-card>
              <!-- 添加处方按钮 -->
              <div class="add-recipe-button">
                <el-button type="text" icon="el-icon-plus" round>新增处方</el-button>
              </div>
            </div>
            <!-- 底部模版区域 -->
            <div>
              <!-- 导航栏(也就是一个标签页) -->
              <el-tabs type="border-card" class="template-tabs">
                <!-- 门诊首页tab-->
                <el-tab-pane label="处方模版">
                  <div class="recipe-template">
                    <!-- 左侧browser区 -->
                    <div class="recipe-browser">
                      <el-card>
                      </el-card>
                    </div>
                    <!-- 右侧处方内容展示区-->
                    <div class="recipe-detail">
                      <el-card>
                      </el-card>
                    </div>
                  </div>


                </el-tab-pane>
                <el-tab-pane label="常用药品"></el-tab-pane>
                <el-tab-pane label="建议方案"></el-tab-pane>
                <el-tab-pane label="历史处方"></el-tab-pane>
              </el-tabs>
            </div>
          </div>
        </el-tab-pane>
        <el-tab-pane label="草药处方">
          <div class="recipe-service-container">
            <!-- 主操作区 -->
            <div>
              <!-- 工具栏 -->
              <el-card :body-style="{padding:'0px'}" style="margin-bottom: 5px;">
                <el-button type="text" icon="el-icon-refresh-right" round>清屏</el-button>
                <el-button type="text" icon="el-icon-folder-checked" round>暂存</el-button>
                <el-button type="text" icon="el-icon-printer" round>打印</el-button>
              </el-card>
              <!-- 检验申请的操作栏以及表格, 做成一个卡片 -->
              <el-card>
                <!-- 操作栏 -->
                <div slot="header" class="clearfix">
                    <span>处方 1</span>
                    <el-button style="float:right" type="text" icon="el-icon-s-check">开立</el-button>
                    <el-button style="float:right" type="text" icon="el-icon-delete-solid">删除（作废）</el-button>
                    <el-button style="float:right" type="text" icon="el-icon-remove">增药</el-button>
                    <el-button style="float:right" type="text" icon="el-icon-circle-plus">删药</el-button>
                </div>
                <!-- 项目列表 -->
                <div class="">
                  <el-table
                    style="width: 100%">
                    <el-table-column
                      type="selection"
                      width="55">
                    </el-table-column>
                    <el-table-column
                      label="药品名称">
                    </el-table-column>
                    <el-table-column
                      label="规格">
                    </el-table-column
                      label="单价">
                    <el-table-column
                      label="用法">
                    </el-table-column>
                    <el-table-column
                      label="用量">
                    </el-table-column>
                    <el-table-column
                      label="单位">
                    </el-table-column>
                    <el-table-column
                      label="频次">
                    </el-table-column>
                    <el-table-column
                      label="天数">
                    </el-table-column>
                    <el-table-column
                      label="次数">
                    </el-table-column>
                    <el-table-column
                      label="数量">
                    </el-table-column>
                    <el-table-column
                      label="单位">
                    </el-table-column>
                    <el-table-column
                      label="皮试">
                    </el-table-column>
                    <el-table-column
                      label="皮试结果">
                    </el-table-column>
                    <el-table-column
                      label="自购">
                    </el-table-column>
                    <el-table-column
                      label="用药嘱托">
                    </el-table-column>
                  </el-table>
                </div>
              </el-card>
              <!-- 添加处方按钮 -->
              <div class="add-recipe-button">
                <el-button type="text" icon="el-icon-plus" round>新增处方</el-button>
              </div>
            </div>
            <!-- 底部模版区域 -->
            <div>
              <!-- 导航栏(也就是一个标签页) -->
              <el-tabs type="border-card" class="template-tabs">
                <!-- 门诊首页tab-->
                <el-tab-pane label="处方模版">
                  <div class="recipe-template">
                    <!-- 左侧browser区 -->
                    <div class="recipe-browser">
                      <el-card>
                      </el-card>
                    </div>
                    <!-- 右侧处方内容展示区-->
                    <div class="recipe-detail">
                      <el-card>
                      </el-card>
                    </div>
                  </div>


                </el-tab-pane>
                <el-tab-pane label="常用药品"></el-tab-pane>
                <el-tab-pane label="建议方案"></el-tab-pane>
                <el-tab-pane label="历史处方"></el-tab-pane>
              </el-tabs>
            </div>
          </div>
        </el-tab-pane>
        <el-tab-pane label="处置单">
          <div class="outpatient-service-container">
            <!-- 左侧操作区 -->
            <div class="service-main-container">
              <!-- 工具栏 -->
              <el-card :body-style="{padding:'0px'}" style="margin-bottom: 5px;">
                <el-button type="text" icon="el-icon-refresh-right" round>清屏</el-button>
                <el-button type="text" icon="el-icon-folder-checked" round>暂存</el-button>
                <el-button type="text" icon="el-icon-printer" round>打印</el-button>
              </el-card>
              <!-- 处置申请的操作栏以及表格, 做成一个卡片 -->
              <el-card>
                <!-- 操作栏 -->
                <div slot="header" class="clearfix">
                    <span>申请单列表</span>
                    <el-button style="float:right" type="text" icon="el-icon-document-add" round>存为模版</el-button>
                    <el-button style="float:right" type="text" icon="el-icon-s-check">开立</el-button>
                    <el-button style="float:right" type="text" icon="el-icon-delete-solid">作废</el-button>
                    <el-button style="float:right" type="text" icon="el-icon-remove">删除项目</el-button>
                    <el-button style="float:right" type="text" icon="el-icon-circle-plus">新增项目</el-button>
                </div>
                <!-- 项目列表 -->
                <div class="">
                  <el-table
                    style="width: 100%">
                    <el-table-column
                      type="selection"
                      width="55">
                    </el-table-column>
                    <el-table-column
                      label="申请单号">
                    </el-table-column>
                    <el-table-column
                      label="申请时间">
                    </el-table-column
                      label="状态">
                    <el-table-column
                      label="申请人">
                    </el-table-column>
                    <el-table-column
                      label="收费状态">
                    </el-table-column>
                    <el-table-column
                      label="执行状态">
                    </el-table-column>
                    <el-table-column
                      label="金额">
                    </el-table-column>
                  </el-table>
                </div>
              </el-card>
              <!-- 所有可选处置项目 -->
              <el-card>
                <!-- 操作栏 -->
                <div slot="header" class="clearfix">
                    <span>项目列表</span>
                </div>
                <!-- 项目列表 -->
                <div class="">
                  <el-table
                    style="width: 100%">
                    <el-table-column
                      type="selection"
                      width="55">
                    </el-table-column>
                    <el-table-column
                      label="类别">
                    </el-table-column>
                    <el-table-column
                      label="通用名">
                    </el-table-column
                      label="剩余库存">
                    <el-table-column
                      label="执行科室">
                    </el-table-column>
                  </el-table>
                </div>
              </el-card>
            </div>
            <!-- 右侧模版区域 -->
            <div class="service-side-container">
              <!-- 导航栏(也就是一个标签页) -->
              <el-tabs type="border-card" class="template-tabs">
                <!-- 门诊首页tab-->
                <el-tab-pane label="常用项目">
                  <p>haha</p>
                  <p>haha</p>
                  <p>haha</p>
                  <p>haha</p>
                  <p>haha</p>
                  <p>haha</p>
                  <p>haha</p>
                </el-tab-pane>
                <el-tab-pane label="处置模版"></el-tab-pane>
              </el-tabs>
            </div>
          </div>
        </el-tab-pane>
        <el-tab-pane label="患者账单">
          <el-card>
            <!-- 操作栏 -->
            <div slot="header" class="clearfix">
                <span>申请单列表</span>
            </div>
            <!-- 项目列表 -->
            <div class="">
              <el-table
                style="width: 100%">
                <el-table-column
                  type="selection"
                  width="55">
                </el-table-column>
                <el-table-column
                  label="名称">
                </el-table-column>
                <el-table-column
                  label="规格">
                </el-table-column
                  label="数量">
                <el-table-column
                  label="付数">
                </el-table-column>
                <el-table-column
                  label="单位">
                </el-table-column>
                <el-table-column
                  label="金额">
                </el-table-column>
                <el-table-column
                  label="收费状态">
                </el-table-column>
              </el-table>
            </div>
          </el-card>
        </el-tab-pane>
      </el-tabs>
    </el-main>
  </el-container>
</template>

<script>
// import {listAllPatients} from '@/api/patients'

import {caseStatusCodeToString, genderCodeToString} from '@/utils/interpreter'

export default {
  name: 'OutPatientDoctor',
  data () {
    return {
      waitingPatients:[
        {
          caseId: '12313',
          patientName: 'Gangan',
          age: 5
        },
        {
          caseId: '123213',
          patientName: 'Linlin',
          age: 5
        }
      ],
      diagnosedPatients:[
        {
          caseId: '121e12',
          patientName: 'Jiajia',
          age: 5
        }
      ],
      selectedPatient:{},
      modernDisease: [],
      traditionalDisease: [],
      zhusu: '',
      case: {}
    }
  },
  computed: {
    status: function() {
      return caseStatusCodeToString(this.case.status)
    },
    gender: function() {
      return genderCodeToString(this.selectedPatient.gender)
    }
  },
  methods: {
    handlePatientTableClick: row => {
      this.selectedPatient = row
      console.log(row)
    }
  }
  // mounted: function () {
  //     //请求所有待诊病人和已诊病人
  //     listAllPatients(this.$store.getters.currentRoleId).then( response => {
  //       const data = response.data.data
  //       this.waitingPatients = data.waitingPatient
  //       this.diagnosedPatient = data.diagnosedPatient
  //     }, error => {
  //       //暂时不做处理
  //     })
  //     //请求所有中医疾病和西医疾病
  //     listAllDisease().then( response => {
  //       const data = response.data.data
  //       this.modernDisease = data.modernDisease
  //       this.traditionalDisease = data.traditionalDisease
  //     }, error => {
  //       //暂时不做处理
  //     })
  // }
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

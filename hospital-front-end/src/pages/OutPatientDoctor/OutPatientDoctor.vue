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
              style="width: 100%">
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
              style="width: 100%">
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
            <span>就诊状态: 待诊 </span>
            <!-- 病历号 -->
            <span>病历号: 31231 </span>
            <!-- 姓名 -->
            <span>姓名: Gangan </span>
            <!-- 性别 -->
            <span>性别: 男 </span>
            <!-- 年龄 -->
            <span>年龄: 5 </span>
            <!-- 结算类别 -->
            <span>结算类别: 免费 </span>
          </div>
          <!-- 历史病历 -->
          <el-button>历史病历</el-button>
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
          <div class="outpatient-home-page">
            <!-- 左侧操作区 -->
            <div class="home-page-input-region">
              <!-- 工具栏 -->
              <div class="tool-bar">
                <el-button type="danger" icon="el-icon-refresh-right" round>清屏</el-button>
                <el-button type="primary" icon="el-icon-folder-checked" round>暂存</el-button>
                <el-button type="primary" icon="el-icon-printer" round>打印</el-button>
                <el-button type="success" icon="el-icon-upload" round>提交</el-button>
              </div>
              <!-- 操作 -->
              <div class="">
                <!-- 病史病历 -->
                <el-card class="input-card" shadow="hover">
                  <div slot="header">
                    <span>病史病历</span>
                  </div>
                  <el-form model="form" label-position='left'>
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
                  <el-form model="form" label-position='left'>
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
            <div class="home-page-template-region">
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

        <el-tab-pane label="门诊病历">配置管理</el-tab-pane>
        <el-tab-pane label="成药处方">角色管理</el-tab-pane>
        <el-tab-pane label="草药处方">定时任务补偿</el-tab-pane>
        <el-tab-pane label="检查申请">定时任务补偿</el-tab-pane>
        <el-tab-pane label="检验申请">定时任务补偿</el-tab-pane>
        <el-tab-pane label="处置单">定时任务补偿</el-tab-pane>
        <el-tab-pane label="患者账单">定时任务补偿</el-tab-pane>
      </el-tabs>
    </el-main>
  </el-container>
</template>

<script>
export default {
  name: 'OutPatientDoctor',
  data () {
    return {
      waitingPatients:[
        {
          caseId: '12313',
          patientName: 'Gangan'
        },
        {
          caseId: '123213',
          patientName: 'Linlin'
        }
      ],
      diagnosedPatients:[
        {
          caseId: '121e12',
          patientName: 'Jiajia'
        }
      ],
      zhusu: '',
      form: {}
    }
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

.outpatient-home-page{
  display: grid;
  grid-template-columns: 70% 30%;
}

.home-page-input-region{
  grid-column: 1/2;
  margin-left: 2px;
}

.home-page-template-region{
  grid-column: 2;
  margin-right: 2px;
  margin-top: 38px;
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


</style>

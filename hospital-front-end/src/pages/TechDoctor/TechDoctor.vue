<template lang="html">
  <el-container style="height: 90vh;">
    <el-aside width="400px">
      <!-- 侧栏区域 -->
      <div class="side-bar">
        <!-- 搜索区 -->
        <div class="search-user">
          <el-input placeholder="病人病历" class="input-with-select">
            <el-button slot="append" icon="el-icon-search"></el-button>
          </el-input>
        </div>
        <!-- 待登记户区 -->
        <el-card shadow="hover">
          <div slot="header">
            <span>待登记病人</span>
          </div>
          <!-- 待登记病人名单 -->
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
              <el-table-column type="expand" width="30px">
                <template slot-scope="props">
                  <p>hahah</p>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-card>

        <!-- 已登记项目区 -->
        <el-card shadow="hover">
          <div slot="header">
            <span>已登记项目</span>
          </div>
          <!-- 已登记病人名单 -->
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
              <el-table-column
                prop="patientName"
                label="检查项目">
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
          </div>
        </div>
      </el-card>
      <!-- 导航栏(也就是一个标签页) -->
      <el-tabs type="border-card" style="overflow:vible">
        <!-- 结果录入tab-->
        <el-tab-pane label="结果录入">
          <!-- 结果录入模块 -->
          <div class="">
            <!-- 工具栏 -->
            <el-card :body-style="{padding:'0px'}" style="margin-bottom: 5px;">
              <el-button type="text" icon="el-icon-refresh-right" round>清屏</el-button>
              <el-button type="text" icon="el-icon-folder-checked" round>暂存</el-button>
              <el-button type="text" icon="el-icon-printer" round>打印</el-button>
            </el-card>
            <!-- 录入表格 -->
            <el-card style="margin-bottom: 5px;">
              <el-form>
                <el-form-item label="检查/检验所见">
                  <el-input
                    type="textarea"
                    autosize
                    placeholder="请输入检查/检验所见"
                    :autosize="{ minRows: 4, maxRows: 100}"
                    >
                  </el-input>
                </el-form-item>
                <el-form-item label="诊断意见">
                  <el-input
                    type="textarea"
                    autosize
                    placeholder="请输入诊断意见"
                    :autosize="{ minRows: 4, maxRows: 100}"
                    >
                  </el-input>
                </el-form-item>
                <el-form-item label="上传结果图片">
                </el-form-item>
                <el-upload
                  class="upload-demo"
                  drag
                  action="https://jsonplaceholder.typicode.com/posts/"
                  multiple>
                  <i class="el-icon-upload"></i>
                  <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                  <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div>
                </el-upload>
              </el-form>
            </el-card>
          </div>
        </el-tab-pane>
        <el-tab-pane label="项目补录">
          
        </el-tab-pane>
      </el-tabs>
    </el-main>
  </el-container>
</template>

<script>
export default {
  name: 'TechDoctor',
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

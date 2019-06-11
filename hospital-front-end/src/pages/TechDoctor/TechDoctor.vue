<template lang="html">
  <el-container style="height: 90vh;">
    <el-aside width="400px">
      <!-- 侧栏区域 -->
      <div class="side-bar">
        <el-card shadow="hover">
        <!-- 搜索区 -->
        <div slot="header">
          <span>查询</span>
        </div>
        <div class="search-region">
          <el-date-picker
            align="right"
            type="date"
            v-model="projectDateStr"
            placeholder="选择日期"
            :picker-options="pickerOptions"
            style="margin-right: 3px;"
            value-format="yyyy-MM-dd"
          ></el-date-picker>
          <el-input placeholder="请输入病历号" class="search-input" v-model="inputCaseId">
            <el-button slot="append" icon="el-icon-search" @click="listPatientByCaseIdOrName"></el-button>
          </el-input>
        </div>
        </el-card>
        <!-- 待登记户区 -->
        <el-card shadow="hover">
          <div slot="header">
            <span>待登记病人</span>
          </div>
          <!-- 待登记病人名单 -->
          <div class="">
            <!-- 表格 -->
            <el-table
              :data="waitingList"
              style="width: 100%"
              highlight-current-row @current-change="handleCurrentChange">
              <el-table-column
                prop="registration_id"
                label="病历号">
              </el-table-column>
              <el-table-column
                prop="patient_name"
                label="患者姓名">
              </el-table-column>
              <!-- <el-table-column type="expand" width="30px">
                <template slot-scope="props">
                  <p>hahah</p>
                </template>
              </el-table-column> -->
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
import techDoctor from '@/api/techDoctor'

export default {
  name: 'TechDoctor',
  data () {
    return {
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        },
        shortcuts: [
          {
            text: "今天",
            onClick(picker) {
              picker.$emit("pick", new Date());
            }
          },
          {
            text: "昨天",
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24);
              picker.$emit("pick", date);
            }
          },
          {
            text: "一周前",
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit("pick", date);
            }
          }
        ]
      },

      projectDateStr: "",
      currentRoleId: "",
      inputCaseId: "",

      waitingList: [],
      currentCase: {},

      zhusu: '',
      form: {}
    }
  },

  methods: {
    handleCurrentChange(val){
      this.currentCase = val;
    },

    listPatientByCaseIdOrName(){
      techDoctor.listPatientByCaseIdOrName(this.projectDateStr, this.inputCaseId, 125).then(response => {
          const data = response.data.data
          console.log(data);
          this.waitingList = data;

          if(response.data.code===200){
          this.success("查询");
        } else {
          this.fail("查询");
        }
      })
    },

    // 计算当前日期
    getNowFormatDate() {
      var date = new Date();
      var seperator1 = "-";
      var year = date.getFullYear();
      var month = date.getMonth() + 1;
      var strDate = date.getDate();
      if (month >= 1 && month <= 9) {
          month = "0" + month;
      }
      if (strDate >= 0 && strDate <= 9) {
          strDate = "0" + strDate;
      }
      var currentdate = year + seperator1 + month + seperator1 + strDate;
      return currentdate;
    },
  },

  mounted(){
    // 得到当前操作员id
    const currentRoleId = this.$store.getters['user/currentRoleId'];
    this.currentRoleId = currentRoleId;    
    // 时间选择框默认显示今天日期
    this.projectDateStr=this.getNowFormatDate();

    // 读取常量表
    // this.readConstants();
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
.search-region {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  align-items: center;
}
</style>

<template>
<div class="container">
  <div class="drug-station" style="margin: 0px 30px 30px 25px;">
    <!-- 左侧搜索区域 -->
    <div class="left-side">
      <el-card shadow="hover" style="margin-top: 15px;">
        <div slot="header">
          <i class="el-icon-search"></i>
          <span>查询</span>
        </div>
        <div class="search-region">
          <el-date-picker
            align="right"
            type="date"
            v-model="chargeDateStr"
            placeholder="选择日期"
            :picker-options="pickerOptions"
            style="margin-right: 3px;"
            value-format="yyyy-MM-dd"
          ></el-date-picker>
          <el-input placeholder="请输入病历号" class="search-input" v-model="caseId">
            <el-button slot="append" icon="el-icon-search" @click="listAvailableRecipes"></el-button>
          </el-input>
        </div>
        <!-- 结果列表 -->
        <div class="result-table">
          <el-table stripe style="width: 100%" :data="caseList" highlight-current-row @current-change="handleCurrentChange">
            <el-table-column label="病历号" prop="caseId"></el-table-column>
            <el-table-column label="姓名" prop="name"></el-table-column>
          </el-table>
        </div>
      </el-card>
    </div>
    <!-- 右侧操作区 -->
    <div class="right-side">
      <el-card shadow="hover" style="margin-top: 15px;">
        <div slot="header">
          <i class="el-icon-sold-out"></i>
          <span>发药管理</span>
          <el-button style="padding: 0px 0px 0px 10px" type="text" icon="el-icon-upload2" @click="deliverMedicine">发药</el-button>
        </div>
        <!-- body部分 -->
        <div>
          <el-table tooltip-effect="dark" style="width: 100%" :data="deliverList" @selection-change="handleDeliverSelectionChange">>
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column label="处方号" prop="id"></el-table-column>
            <el-table-column label="开单医生" prop="creatorRoleId"></el-table-column>
            <el-table-column label="药品名称" prop="medicineId"></el-table-column>
            <el-table-column label="规格" prop="medicineSpecification"></el-table-column>
            <el-table-column label="剂型" prop="medicineFormulation"></el-table-column>
            <el-table-column label="总数量" prop="amount"></el-table-column>
            <el-table-column label="可退数量" prop="remainAmount"></el-table-column>
            <el-table-column label="总金额" prop="totalMoney"></el-table-column>
            <el-table-column label="缴费状态" prop="transactionLogStatus" :formatter="jiaofeiFormatter"></el-table-column>
            <el-table-column label="处方状态" prop="status" :formatter="chufangFormatter"></el-table-column>
          </el-table>
        </div>
      </el-card>
      <el-card shadow="hover" style="margin-top: 30px">
        <div slot="header">
          <i class="el-icon-sell"></i>
          <span>退药管理</span>
          <el-button style="padding: 0px 0px 0px 10px" type="text" icon="el-icon-download" @click="returnMedicine">退药</el-button>
        </div>
        <!-- body部分 -->
        <div>
          <el-table tooltip-effect="dark" style="width: 100%" :data="returnList" @selection-change="handleReturnSelectionChange">>
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column label="处方号" prop="id"></el-table-column>
            <el-table-column label="开单医生" prop="creatorRoleId"></el-table-column>
            <el-table-column label="药品名称" prop="medicineId"></el-table-column>
            <!-- <el-table-column label="规格" prop="medicineSpecification"></el-table-column> -->
            <!-- <el-table-column label="剂型" prop="medicineFormulation"></el-table-column> -->
            <el-table-column label="总数量" prop="amount"></el-table-column>
            <el-table-column label="可退数量" prop="remainAmount"></el-table-column>
            <el-table-column label="总金额" prop="totalMoney"></el-table-column>
            <el-table-column label="缴费状态" prop="transactionLogStatus" :formatter="jiaofeiFormatter"></el-table-column>
            <el-table-column label="处方状态" prop="status" :formatter="chufangFormatter"></el-table-column>
            <el-table-column label="退药数量" fixed="right" width="150">
              <template slot-scope="scope">
                <el-input-number v-model="scope.row.returnAmount" :min=1 :max="scope.row.remainAmount" label="描述文字"></el-input-number>
              </template>
            </el-table-column>          
          </el-table>
        </div>
      </el-card>
      <el-card shadow="hover" style="margin-top: 30px">
        <div slot="header">
          <i class="el-icon-shopping-bag-2"></i>
          <span>全部</span>
        </div>
        <!-- body部分 -->
        <div>
          <el-table tooltip-effect="dark" style="width: 100%" :data="recipeList">>
            <el-table-column label="处方号" prop="id"></el-table-column>
            <el-table-column label="开单医生" prop="creatorRoleId"></el-table-column>
            <el-table-column label="药品名称" prop="medicineId"></el-table-column>
            <el-table-column label="规格" prop="medicineSpecification"></el-table-column>
            <el-table-column label="剂型" prop="medicineFormulation"></el-table-column>
            <el-table-column label="总数量" prop="amount"></el-table-column>
            <el-table-column label="可退数量" prop="remainAmount"></el-table-column>
            <el-table-column label="总金额" prop="totalMoney"></el-table-column>
            <el-table-column label="缴费状态" prop="transactionLogStatus" :formatter="jiaofeiFormatter"></el-table-column>
            <el-table-column label="处方状态" prop="status" :formatter="chufangFormatter"></el-table-column>
          </el-table>
        </div>
      </el-card>      
    </div>
    <!-- 发药dialog -->
    <el-dialog title="发药" :visible.sync="dialogGiveOutDrug" :before-close="handleClose"></el-dialog>
    <!-- 退药dialog -->
    <el-dialog title="退药" :visible.sync="dialogReturnDrug" :before-close="handleClose">
      <div></div>
    </el-dialog>
  </div>
</div>
</template>

<script>
import pharmacyStation from '@/api/pharmacyStation'
import common from '@/api/common'

export default {
  name: "DrugStation",
  data() {
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
      // 当前操作员
      currentRoleId: "",
      //
      dialogGiveOutDrug: false,
      dialogReturnDrug: false,

      //左上角输入内容
      caseId: "",
      chargeDateStr: "",
      //左上角查询结果
      caseList: [],
      //点击病历查询结果
      recipeList: [],
      recipeInfo: {},

      deliverList: [],
      returnList: [],
      //临时记录选择病历用于发药退药后刷新
      currentCase: {},
      //选中的发药集合
      deliverSelection: [],
      //选中的退药集合
      returnSelection: [],

      //常量表
      constant: {},
      jiaofeiStatus: {
        1: "未缴费",
        2: "已缴费",
        3: "已退费",
        4: "冲正",
        5: "作废",
        6: "冻结"
      },
      chufangStatus: {
        1: "暂存",
        2: "开立",
        3: "作废",
        4: "已取药",
        5: "已退药"
      }
    };
  },
  methods: {

    handleDeliverSelectionChange(val) {
      this.deliverSelection = val;
    },

    handleReturnSelectionChange(val) {
      this.returnSelection = val;
    },

    // 发药
    deliverMedicine(){
      pharmacyStation.deliverMedicine(this.deliverSelection).then(response => {
        const data = response.data.data

        this.handleCurrentChange(this.currentCase)
        if(response.data.code===200){
          this.success("发药");
        } else {
          this.fail("发药");
        }
      }).catch(error => {
        
      })
    },

    // 退药
    returnMedicine() {
      console.log("退药")
      console.log(this.returnSelection)
      pharmacyStation.returnMedicine(this.returnSelection).then(response => {
        const data = response.data.data

        this.handleCurrentChange(this.currentCase)
        if(response.data.code===200){
          this.success("退药");
        } else {
          this.fail("退药");
        }
      }).catch(error => {
        
      })
    },

    // 列出某个病历对应的全部处方
    handleCurrentChange(val) {
      this.currentCase = val;
      const caseId = val.caseId;
      pharmacyStation.listRecipeInfo(caseId).then(response => {
        const data = response.data.data

        this.recipeInfo = data;
        this.recipeList = this.recipeInfo.recipe;
        
        // 过滤可发药列表
        var deliverList = this.recipeList.slice(0);
        for(var i=0; i<deliverList.length; ){
          deliverList[i].deliverRoleId = this.currentRoleId;
          if(!(deliverList[i].status===2&&deliverList[i].transactionLogStatus===2)){
            deliverList.splice(i,1);
          } else {
            i++;
          }
        }
        this.deliverList = deliverList;

        // 过滤可退药列表
        var returnList = this.recipeList.slice(0);
        for(var i=0; i<returnList.length;){
          returnList[i].deliverRoleId = this.currentRoleId;
          if(!((returnList[i].status===4&&returnList[i].transactionLogStatus===2) || 
          (returnList[i].status===5&&returnList[i].remainAmount>0)) ){
            returnList.splice(i,1);
          } else {
            i++;
          }
        }
        this.returnList = returnList;
        
        if(response.data.code===200){
          this.success("查询");
        } else {
          this.fail("查询");
        }
      }).catch(error => {
        
      })
    },

    // 列出筛选后的病历列表
    listAvailableRecipes(){
      pharmacyStation.listAvailableRecipes(this.caseId, this.chargeDateStr).then(response => {
        const data = response.data.data

        this.caseList = data;
        // console.log(this.caseList)
        if(response.data.code===200){
          this.success("查询");
        } else {
          this.fail("查询");
        }
      }).catch(error => {
        
      })
    },

    // 成功提示
    success(msg) {
      this.$message({
        message: msg+'成功',
        type: 'success'
      });
    },
      
    // 失败提示
    fail(msg) {
      this.$message.error(msg+'失败');
    },

    handleClose(done) {
      this.$confirm("确认关闭？")
        .then(_ => {
          done();
        })
        .catch(_ => {});
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

    jiaofeiFormatter(row) {
      const transactionLogStatus = row.transactionLogStatus
      return this.jiaofeiStatus[transactionLogStatus];
    },

    chufangFormatter(row) {
      const status = row.status;
      return this.chufangStatus[status];
    },

    // 读取常量表（暂时为远程读取）
    readConstants(){
      common.listConstantsTree().then(response => {
        const data = response.data.data

        this.constant = data;
        // console.log(this.constant)

        this.jiaofeiStatus = this.constant.缴费状态
        this.yaofangStatus = this.constant.药方状态
      })
    }
  },

  mounted(){
    // 得到当前操作员id
    const currentRoleId = this.$store.getters['user/currentRoleId'];
    this.currentRoleId = currentRoleId;    
    // 时间选择框默认显示今天日期
    this.chargeDateStr=this.getNowFormatDate();

    // 读取常量表
    // this.readConstants();
  }
};
</script>

<style lang="css" scoped>
.container{
  background-color: #f2f7f8;
  height: 100vh;
  margin-top: 0;
}

.drug-station {
  display: grid;
  grid-template-columns: 30% 70%;
}

.left-side {
  margin: 3px;
  grid-column: 1 /2;
}
.right-side {
  margin: 0 0 30px 30px;
  grid-column: 2 / 2;
}

.search-region {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  align-items: center;
}

.result-table {
  border: 1px solid #d9d9d9;
  border-radius: 3px;
  margin-top: 5px;
}
</style>
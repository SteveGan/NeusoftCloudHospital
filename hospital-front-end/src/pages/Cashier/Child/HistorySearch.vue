<template lang="html">
    <div style="height: 100vh">
        <el-card style="margin: 0 30px 0 25px;" shadow="hover">
            <div slot="header">
                <i class="el-icon-search"></i>
                <span>查询条件</span>
            </div>
            <el-form :inline="true">
                <el-form-item label="起始时间">
                    <el-date-picker type="datetime" value-format="yyyy-MM-dd hh:mm:ss" v-model="startDate" placeholder="选择起始时间" class="date-selection"></el-date-picker>
                </el-form-item>
                <el-form-item label="结束时间">
                    <el-date-picker type="datetime" value-format="yyyy-MM-dd hh:mm:ss" v-model="endDate" :picker-options="pickerOptions" placeholder="选择结束时间" class="date-selection"></el-date-picker>
                </el-form-item>
                <el-form-item label="收费员">
                    <el-input v-model="currentRoleId" disabled></el-input>
                </el-form-item>
                <el-button type="text" icon="el-icon-document-add" @click="search">查询</el-button>
            </el-form>
        </el-card>
        <div class="main-container">
            <el-card style="width:400px; margin: 30px 30px 30px 25px;" shadow="hover">
                <div slot="header">
                    <i class="el-icon-paperclip"></i>
                    <span>日结信息列表</span>
                </div>
                <el-table :data="this.summaryList" style="width: 100%" highlight-current-row @current-change="handleCurrentChange">
                    <el-table-column type="index" width="50">
                    </el-table-column>
                    <el-table-column label="收费员" prop="cashierId" width="60">
                    </el-table-column>
                    <el-table-column label="日结日期" prop="gmtCreate">
                    </el-table-column>
                </el-table>
            </el-card>

            <el-card style="margin: 30px 30px 30px 0; width:100%" shadow="hover">
                <div slot="header">
                    <i class="el-icon-attract"></i>
                    <span>日结明细</span>
                </div>
                <el-table :data="this.invoiceList" style="width: 100%">
                    <el-table-column label="发票号" prop="invoiceCode">
                    </el-table-column>
                    <el-table-column label="病历号" prop="registrationId">
                    </el-table-column>
                    <el-table-column label="发票总额" prop="totalMoney">
                    </el-table-column>
                    <el-table-column label="患者姓名" prop="name">
                    </el-table-column>
                    <el-table-column label="结算类别" prop="jiesuanType">
                    </el-table-column>
                </el-table>
            </el-card>
        </div>
    </div>

</template>

<script>
import dailySummary from '@/api/dailySummary'
export default {
    name: 'HistorySearch',
    data() {
        return {
            pickerOptions: {
                disabledDate(time) {
                    return time.getTime() > Date.now();
                }
            },

            // 上方搜索
            startDate: "",
            endDate: "",
            cashierId: "",

            // 当前日期
            currentdate: "",
            // 当前操作员
            currentRoleId: "",

            // 选中日结记录
            summaryList: [],
            invoiceList: [],
        }
    },

    methods: {
        handleCurrentChange(val) {
            console.log("here")
            var object = {};
            object.createDateStr = val.gmtCreate;
            object.cashierId = val.cashierId;

            dailySummary.listInvoiceResults(object).then(response => {
                console.log(response.data.data)
                const data = response.data.data
                this.invoiceList = data;

                if(response.data.code===200){
                    this.success("查询");
                } else {
                    this.fail("查询");
                }
            })
        },

        search(){
            var object = {};
            object.beginDateStr = this.startDate;
            object.endDateStr = this.endDate;
            object.cashierId = this.currentRoleId;   

            dailySummary.listHistorySummaryLogs(object).then(response => {
                console.log(response.data.data)
                const data = response.data.data

                for(var i=0; i<data.length; i++){
                    var origin = data[i].gmtCreate;
                    var prefix = origin.substring(0, 10);
                    var postfix = origin.substring(11, 19);
                    data[i].gmtCreate = prefix + " " + postfix;
                }
                this.summaryList = data;
                

                if(response.data.code===200){
                    this.success("查询");
                } else {
                    this.fail("查询");
                }
            })
        },

        getFirstSummaryDate() {
            dailySummary.getFirstSummaryDate(this.currentRoleId).then(response => {
                console.log(response)
                const data = response.data.data
                this.startDate = data;
            })
        },

        // 计算当前日期
        getNowFormatDate() {
            var date = new Date();
            var seperator1 = "-";
            var seperator2 = ":";
            var month = date.getMonth() + 1<10? "0"+(date.getMonth() + 1):date.getMonth() + 1;
            var strDate = date.getDate()<10? "0" + date.getDate():date.getDate();
            var currentdate = date.getFullYear() + seperator1  + month  + seperator1  + strDate
                    + " "  + date.getHours()  + seperator2  + date.getMinutes()
                    + seperator2 + date.getSeconds();
            return currentdate;
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
    },

    mounted() {
        // 设置结束时间默认为今天
        this.currentdate = this.getNowFormatDate();
        this.endDate = this.currentdate;

        // 初始化操作员id
        const currentRoleId = this.$store.getters['user/currentRoleId'];
        this.currentRoleId = currentRoleId;

        this.getFirstSummaryDate();
    }
}
</script>

<style lang="css" scoped>
.main-container{
  display: flex;
  flex-direction: row;
}
</style>


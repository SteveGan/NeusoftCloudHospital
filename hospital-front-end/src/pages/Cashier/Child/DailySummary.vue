<template lang="html">
    <div style="margin: 0 30px 0 25px; min-height:100vh;">
        <el-card shadow="hover">
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
                <el-form-item label="收款员">
                    <el-input v-model="currentRoleId" disabled></el-input>
                </el-form-item>
                <el-button type="text" icon="el-icon-document-add" @click="search">日结统计</el-button>
            </el-form>
        </el-card>
        <el-card style="margin-top: 30px;" shadow="hover">
            <div slot="header">
                <i class="el-icon-paperclip"></i>
                <span>查询结果</span>
                <el-button type="text" style="float: right;" icon="el-icon-document-add" @click="submit">结算报账</el-button>
                <el-input style="width:150px; float: right; margin-right:30px;" v-model="sumMoney" disabled></el-input>
                <span style="float: right; margin-right:10px;">合计：￥</span>
            </div>
            
            <el-table :data="invoiceList" style="width: 100%" v-loading="loading">
                <el-table-column type="index" width="50"></el-table-column>
                <el-table-column label="发票号" prop="invoiceCode"></el-table-column>
                <el-table-column label="病历号" prop="registrationId"></el-table-column>
                <el-table-column label="发票总额" prop="totalMoney"></el-table-column>
                <el-table-column label="患者姓名" prop="name"></el-table-column>
                <el-table-column label="结算类别" prop="jiesuanType"></el-table-column>
            </el-table>
        </el-card>

        <el-dialog title="日结对账单打印" :visible.sync="dailyPrinterVisible" width="80%">
            <div id="printContent">
                <div>
                    <h3 style="text-align:center;">熙康云医院 · 日结对账单</h3>
                    <h6 style="text-align:center;margin-top: -12px;">地址:沈阳市浑南区创新路195号&nbsp;&nbsp;&nbsp;门诊部:024-88886666</h6>
                    <hr style="width: 630px;text-align:center;" />
                    <h5 style="text-align:left;margin-left: 200px;">
                        结算日期:{{this.currentdate}}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        起始时间:{{this.startDate}}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        结束时间:{{this.endDate}}
                    </h5>

                    <div style="margin: 0 0 30px 200px;">
                        <el-table show-summary :summary-method="getSummaries" :data="lastList" border style="width: 80%">
                        <el-table-column type="index" width="50"></el-table-column>
                        <el-table-column label="发票号" prop="invoiceCode"></el-table-column>
                        <el-table-column label="病历号" prop="registrationId"></el-table-column>
                        <el-table-column label="发票总额" prop="totalMoney"></el-table-column>
                        <el-table-column label="患者姓名" prop="name"></el-table-column>
                        <el-table-column label="结算类别" prop="jiesuanType"></el-table-column>
                        </el-table>
                    </div>

                    <el-form :inline="true" class="demo-form-inline" style="margin: 0 0 30px 200px;">
                        <el-form-item label="操作员">
                            <el-input v-model="currentRoleId" :disabled="true"></el-input>
                        </el-form-item>
                    </el-form>
                </div>
                <button style="text-align:center;" class="btn no-print" v-print="'#printContent'">打印</button>
            </div>
        </el-dialog>
    </div>

</template>

<script>
import dailySummary from '@/api/dailySummary'
import { successDialog, failDialog } from "@/utils/notification";

export default {
    name: 'DailySummary',
    data() {
        return {
            pickerOptions: {
                disabledDate(time) {
                    return time.getTime() > Date.now();
                }
            },

            //常量表
            jiesuanType: {
                1: "自费",
                2: "医保",
                3: "新农合"
            },

            // 上方搜索
            startDate: "",
            endDate: "",
            cashierId: "",

            // 当前日期
            currentdate: "",
            // 当前操作员
            currentRoleId: "",

            // 查询结果
            invoiceList: [],
            // 上次结果
            lastList: [],
            sumMoney: 0,

            loading: false,
            dailyPrinterVisible: false
        }
    },

    methods: {
        // 结算报账
        submit() {
            this.loading = true;
            this.lastList = this.invoiceList;
            var object = {};
            object.invoiceCollection = this.invoiceList;
            object.beginDateStr = this.startDate;
            object.endDateStr = this.endDate;
            object.cashierId = this.currentRoleId;
            object.totalMoney = this.sumMoney;
            object.invoiceCodeBegin = this.invoiceList[0].invoiceCode;
            object.invoiceCodeEnd = this.invoiceList[this.invoiceList.length-1].invoiceCode;

            dailySummary.freezeDailyTransactionLogs(object).then(response => {
                console.log(response.data.data)
                const data = response.data.data

                this.invoiceList = [];
                this.loading = false;
                this.dailyPrinterVisible = true;
                successDialog("结算成功");
                
            }, error => {
                this.loading = false;
                failDialog("[结算失败]" + error.data.data.message + "(" + error.data.data.code + ")");
            })
        },

        // 查询
        search() {
            this.sumMoney = 0;
            var object = {};
            object.beginDateStr = this.startDate;
            object.endDateStr = this.endDate;
            object.cashierId = this.currentRoleId;

            dailySummary.conductDailyTransactionLogs(object).then(response => {
                console.log(response.data.data)
                const data = response.data.data
                this.invoiceList = data;

                if(data!=null){
                    for(var i=0; i<this.invoiceList.length; i++){
                        this.sumMoney+=this.invoiceList[i].totalMoney;
                        this.invoiceList[i].jiesuanType = this.jiesuanType[this.invoiceList[i].payType];
                    }
                    
                    this.sumMoney = this.sumMoney.toFixed(2);
                }
                this.loading = false;
                successDialog("查询成功");

            }, error => {
                this.loading = false;
                failDialog("[查询失败]" + error.data.data.message + "(" + error.data.data.code + ")");
            })
        },

        // 计算当前时间
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

        // 查询收费员上次日结截止时间
        getLastEndDate() {
            dailySummary.getLastEndDate(this.currentRoleId).then(response => {
                console.log(response.data.data)
                const data = response.data.data
                this.startDate = data;
            })
        },

        // 发票表格打印合计项
        getSummaries(param) {
            const { columns, data } = param;
            const sums = [];
            columns.forEach((column, index) => {
                if (index === 0) {
                    sums[index] = '合计';
                    return;
                }
                if (index === 1 || index === 2 || index === 4 || index === 5) {
                    sums[index] = '';
                    return;
                }
                const values = data.map(item => Number(item[column.property]));
                if (!values.every(value => isNaN(value))) {
                    sums[index] = values.reduce((prev, curr) => {
                        const value = Number(curr);
                        if (!isNaN(value)) {
                        return prev + curr;
                        } else {
                        return prev;
                        }
                    }, 0);
                    sums[index] += '';
                } else {
                    sums[index] = 'N/A';
                }
            });

            return sums;
        },
    },

    mounted() {
        // 设置结束时间默认为今天
        this.currentdate = this.getNowFormatDate();
        this.endDate = this.currentdate;


        // 初始化操作员id
        const currentRoleId = this.$store.getters['user/currentRoleId'];
        this.currentRoleId = currentRoleId;

        this.getLastEndDate();
    }
}
</script>

<style lang="css" scoped>

</style>


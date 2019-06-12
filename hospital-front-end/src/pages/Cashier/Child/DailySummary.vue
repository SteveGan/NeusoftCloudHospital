<template lang="html">
    <div>
        <el-card style="margin: 5px 4px;s" shadow="hover">
            <div slot="header">
                <span>查询条件</span>
            </div>
            <el-form :inline="true">
                <el-form-item label="起始时间">
                    <el-date-picker type="datetime" v-model="startDate" placeholder="选择起始时间" class="date-selection"></el-date-picker>
                </el-form-item>
                <el-form-item label="结束时间">
                    <el-date-picker type="datetime" v-model="endDate" :picker-options="pickerOptions" placeholder="选择结束时间" class="date-selection"></el-date-picker>
                </el-form-item>
                <el-form-item label="收款员">
                    <el-input v-model="currentRoleId" disabled></el-input>
                </el-form-item>
            </el-form>
        </el-card>
        <el-card style="margin: 5px 4px;s" shadow="hover">
            <div slot="header">
                <span>查询结果</span>
            </div>
            <el-table :data="this.showedDepartments" style="width: 100%">
                <el-table-column label="发票号" prop="id" width="60">
                </el-table-column>
                <el-table-column label="病历号" prop="code">
                </el-table-column>
                <el-table-column label="发票总额" prop="name">
                </el-table-column>
            </el-table>
        </el-card>
    </div>

</template>

<script>
import dailySummary from '@/api/dailySummary'
export default {
    name: 'DailySummary',
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
            currentRoleId: ""
        }
    },

    methods: {
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
        }
    },

    mounted() {
        // 默认设置看诊日期为今天
        this.today = this.getNowFormatDate();
        this.currentdate = this.getNowFormatDate();
        // 设置结束时间默认为今天
        this.endDate = this.currentdate;


        // 初始化操作员id
        const currentRoleId = this.$store.getters['user/currentRoleId'];
        this.currentRoleId = currentRoleId;
    }
}
</script>

<style lang="css" scoped>

</style>


<template lang="html">
    <div>
        <el-card style="margin: 5px 4px;s" shadow="hover">
            <div slot="header">
                <span>查询条件</span>
            </div>
            <el-form :inline="true">
                <el-form-item label="起始日期">
                    <el-date-picker type="date" v-model="startDate" placeholder="选择起始日期" class="date-selection" value-format="yyyy-MM-dd"></el-date-picker>
                </el-form-item>
                <el-form-item label="结束日期">
                    <el-date-picker type="date" v-model="endDate" placeholder="选择结束日期" class="date-selection" value-format="yyyy-MM-dd"></el-date-picker>
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
export default {
    name: 'DailySummary',
    data() {
        return {
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


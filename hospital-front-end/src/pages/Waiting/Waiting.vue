<template lang="html">
    <el-container>
    <el-header class="header">
        <el-row :gutter="20">
            <el-col :span="4">
                <div class="left-content white">
                    熙康云医院
                </div></el-col>
            <el-col :span="16">
                <div class="center-content white">
                    祝您早日康复！
                </div>
            </el-col>
            <el-col :span="4">
                <div class="right-content white">
                    2019年6月12日
                </div>
            </el-col>
        </el-row>

    </el-header>
    <el-main>Main</el-main>
    </el-container>
</template>

<script>

export default {
    
}
</script>

<style lang="css" scoped>
.header{
    height: 300px;
    background-color: #409EFF;
}
.white{
    color: #ffffff;
}
.left-content{
    flex-direction:column;
    display: flex;
    align-items: center;
    height:200px;
}
.center-content{
    display: flex;
    justify-content: center;
    align-items: center;
}
.right-content{
    display: flex;
    justify-content: flex-end;
    align-items:flex-end;
}
</style>

<template>
  <div class="drug-station">
    <!-- 左侧搜索区域 -->
    <div class="left-side">
      <el-card shadow="hover">
        <div slot="header">
          <span>查询</span>
        </div>
        <div class="search-region">
          <!-- 搜索框：根据病历号查询 -->
          <div>
            <el-input placeholder="请输入病历号" class="search-input">
              <el-button slot="append" icon="el-icon-search"></el-button>
            </el-input>
          </div>
          <!-- 过滤器：收费日期 -->
          <div>
            <el-date-picker
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            ></el-date-picker>
          </div>
          <!-- 结果列表 -->
          <div></div>
        </div>
      </el-card>
    </div>
    <!-- 右侧操作区 -->
    <div class="right-side">
      <el-card shadow="hover">
        <div slot="header">
          <span>发票明细</span>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
export default {
  name: "DrugStation"
};
</script>

<style lang="css" scoped>
.drug-station {
  display: grid;
  grid-template-columns: 35% 65%;
}

.left-side {
  margin: 3px;
  grid-column: 1 /2;
}
.right-side {
  margin: 3px;
  grid-column: 2 / 2;
}

.search-region {
  display: flex;
  flex-direction: column;
  justify-content: start;
  align-items: center;
}

.search-input .el-input-group__prepend {
  background-color: #fff;
}
</style>
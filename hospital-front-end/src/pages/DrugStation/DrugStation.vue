<template>
  <div class="drug-station">
    <!-- 左侧搜索区域 -->
    <div class="left-side">
      <el-card shadow="hover">
        <div slot="header">
          <span>查询</span>
        </div>
        <div class="search-region">
          <el-date-picker
            align="right"
            type="date"
            placeholder="选择日期"
            :picker-options="pickerOptions"
            style="margin-right: 3px;"
          ></el-date-picker>
          <el-input placeholder="请输入病历号" class="search-input">
            <el-button slot="append" icon="el-icon-search"></el-button>
          </el-input>
        </div>
        <!-- 结果列表 -->
        <div class="result-table">
          <el-table stripe style="width: 100%">
            <el-table-column label="病历号"></el-table-column>
            <el-table-column label="姓名"></el-table-column>
          </el-table>
        </div>
      </el-card>
    </div>
    <!-- 右侧操作区 -->
    <div class="right-side">
      <el-card shadow="hover">
        <div slot="header">
          <span>药房明细</span>
          <el-button
            style="padding: 0px 0px 0px 20px"
            type="text"
            icon="el-icon-upload2"
            @click="dialogGiveOutDrug=true"
          >发药</el-button>
          <el-button
            style="padding: 0px 0px 0px 10px"
            type="text"
            icon="el-icon-download"
            @click="dialogReturnDrug=true"
          >退药</el-button>
        </div>
        <!-- body部分 -->
        <div>
          <el-table tooltip-effect="dark" style="width: 100%">
            <el-table-column label="处方号"></el-table-column>
            <el-table-column label="开单医生"></el-table-column>
            <el-table-column label="药品名称"></el-table-column>
            <el-table-column label="规格"></el-table-column>
            <el-table-column label="数量"></el-table-column>
            <el-table-column label="单位"></el-table-column>
            <el-table-column label="总金额"></el-table-column>
            <el-table-column label="厂家"></el-table-column>
            <el-table-column label="单次用量"></el-table-column>
            <el-table-column label="状态"></el-table-column>
            <el-table-column type="selection" width="55"></el-table-column>
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
</template>

<script>
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
      dialogGiveOutDrug: false,
      dialogReturnDrug: false
    };
  },
  methods: {
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then(_ => {
          done();
        })
        .catch(_ => {});
    }
  }
};
</script>

<style lang="css" scoped>
.drug-station {
  display: grid;
  grid-template-columns: 30% 70%;
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
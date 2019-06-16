<template lang="html">
<div class="container">
  <!-- 科目管理功能区，做成卡片的样子 -->
  <el-card class="box-card" shadow="hover">
    <!-- 标题 -->
    <div slot="header" class="clearfix">
      <span style="padding-left: 20px;">费用科目管理</span>
    </div>
    
    <!-- 表单部分 -->
    <div class="business-region">
      <!-- 表单头部：搜索功能区和添加科目按钮 -->
      <div class="tool-bar">
        <!-- 搜索区 -->
        <div class="search-region">
          <el-input placeholder="搜索内容" v-model="input" class="input-with-select">
            <el-select v-model="chosenOption" slot="prepend" placeholder="通过科目ID搜索" class="select-box">
              <el-option  v-for="item in searchOptions"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value">
              </el-option>
            </el-select>
            <el-button slot="append" icon="el-icon-search" @click="search"></el-button>
          </el-input>
        </div>
        <!-- 添加，批量管理按钮 -->
        <div class="button-group">
          <!-- 添加按钮 -->
          <div>
            <el-button type="primary"  @click="refresh">刷新</el-button>
          </div>
          <div>
            <el-button type="primary"  @click="addPriceDialogVisible = true">新增科目</el-button>
          </div>
        </div>
        </div>
        <!-- 列表，展示所有科目或搜索到的科目，后面带有修改/删除按钮-->
        <div class="table-region">
          <el-table :data="this.showedPrices" style="width: 100%">
            <!-- <el-table-column
              type="selection"
              width="55">
            </el-table-column> -->
            <el-table-column label="科目ID" prop="id" width="60">
            </el-table-column>
            <el-table-column label="科目编码" prop="code">
            </el-table-column>
            <el-table-column label="科目名称" prop="name">
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                <el-button
                  size="mini"
                  type="danger"
                  @click="handleDelete(scope.$index, scope.row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>

          <div class="block">
            <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="currentPage"
              :page-sizes="[10, 30, 50, 100]"
              :page-size="currentSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="prices.length">
            </el-pagination>
          </div>
        </div>
      </div>
    </el-card>

    <el-dialog :visible.sync="addPriceDialogVisible" width="60%">
      <price-info-adder>添加科目</price-info-adder>
    </el-dialog>
    <el-dialog :visible.sync="editPriceDialogVisible" width="60%">
      <price-info-editor v-model="currentPrice">编辑科目信息</price-info-editor>
    </el-dialog>
  </div>
</template>

<script>
import price from '@/api/basicinfo/price'
import PriceInfoEditor from './Child/PriceInfoEditor'
import PriceInfoAdder from './Child/PriceInfoAdder'

export default {
  name: 'PriceAdmin',
  components:{
    'price-info-editor': PriceInfoEditor,
    'price-info-adder': PriceInfoAdder
  },

  data() {
    return {
      chosenOption: "",
      input: "",
      addPriceDialogVisible: false,
      editPriceDialogVisible: false,
      searchOptions: [
          {
            value: 'priceId',
            label: '通过科目ID搜索'
          },
          {
            value: 'priceName',
            label: '通过科目名搜索'
          }
        ],
      
      prices: [],
      showedPrices: [],
      currentPage: 1,
      currentSize: 10,
      currentPrice: {}
    }
  },

  methods: {
      // 搜索
      search() {
        price.getPriceById(this.input).then(response => {
          console.log(response.data.data)
          const data = response.data.data;
          this.showedPrices=[];
          this.showedPrices.push(data);

        }).catch(error => {
        
        })
      },
      // 新增/修改 科目信息
      handleEdit(index, row) {
        this.currentPrice = row
        this.editPriceDialogVisible = true
        console.log(this.currentPrice)
      },

      handleDelete(index, row) {
        price.deletePrice(row.id).then(response => {
          console.log(response.data)
          const data = response.data.data;

          if(response.data.code===200){
            this.success("科目删除");
          } else {
            this.fail("科目删除");
          }
          this.refresh();
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
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
        this.currentSize=val;
        this.setShowedPrices();
      },
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`);
        this.currentPage=val;
        this.setShowedPrices();
      },
      refresh() {
        price.listAllPrices().then(response => {
          console.log(response.data)
          const data = response.data.data
          this.prices = data;
          this.setShowedPrices();
        }).catch(error => {
          
        })
      },

      setShowedPrices() {
        console.log(this.currentPage);
        this.showedPrices = this.prices.slice((this.currentPage-1)*this.currentSize, (this.currentPage-1)*this.currentSize+this.currentSize);
      }
  },

  mounted() {
    this.refresh()
  }
}
</script>

<style lang="css" scoped>
@import '../../../../static/css/admin.css';
</style>

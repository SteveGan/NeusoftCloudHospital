<template lang="html">
<div class="container">
  <!-- 结算类别管理功能区，做成卡片的样子 -->
  <el-card class="box-card" shadow="hover">
    <!-- 标题 -->
    <div slot="header" class="clearfix">
      <span style="padding-left: 20px;">结算类别管理</span>
    </div>
    
    <!-- 表单部分 -->
    <div class="business-region">
      <!-- 表单头部：搜索功能区和添加结算类别按钮 -->
      <div class="tool-bar">
        <!-- 搜索区 -->
        <div class="search-region">
          <el-input placeholder="搜索内容" v-model="input" class="input-with-select">
            <el-select v-model="chosenOption" slot="prepend" placeholder="通过结算类别ID搜索" class="select-box">
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
            <el-button type="primary"  @click="addPaytypeDialogVisible = true">新增结算类别</el-button>
          </div>
        </div>
        </div>
        <!-- 列表，展示所有结算类别或搜索到的结算类别，后面带有修改/删除按钮-->
        <div class="table-region">
          <el-table :data="this.showedPaytypes" style="width: 100%">
            <!-- <el-table-column
              type="selection"
              width="55">
            </el-table-column> -->
            <el-table-column label="结算类别ID" prop="id" width="60">
            </el-table-column>
            <el-table-column label="结算类别编码" prop="code">
            </el-table-column>
            <el-table-column label="结算类别名称" prop="name">
            </el-table-column>
            <el-table-column label="结算类别分类(小类)" prop="classification">
            </el-table-column>
            <el-table-column label="结算类别类别(大类)" prop="type">
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
              :total="paytypes.length">
            </el-pagination>
          </div>
        </div>
      </div>
    </el-card>

    <el-dialog :visible.sync="addPaytypeDialogVisible" width="60%" :before-close="handleClose">
      <paytype-adder>添加结算类别</paytype-adder>
    </el-dialog>
    <el-dialog :visible.sync="editPaytypeDialogVisible" width="60%" :before-close="handleClose">
      <paytype-editor v-model="currentPaytype">编辑结算类别信息</paytype-editor>
    </el-dialog>
  </div>
</template>

<script>
import paytype from '@/api/basicinfo/paytype'
import PaytypeEditor from './Child/PaytypeEditor'
import PaytypeAdder from './Child/PaytypeAdder'

export default {
  name: 'PaytypeAdmin',
  components:{
    'paytype-editor': PaytypeEditor,
    'paytype-adder': PaytypeAdder
  },

  data() {
    return {
      input: "",
      addPaytypeDialogVisible: false,
      editPaytypeDialogVisible: false,
      searchOptions: [
          {
            value: 'paytypeId',
            label: '通过结算类别ID搜索'
          },
          {
            value: 'paytypeName',
            label: '通过结算类别名搜索'
          }
        ],
      
      paytypes: [],
      showedPaytypes: [],
      currentPage: 1,
      currentSize: 10,
      currentPaytype: {}
    }
  },

  methods: {
      // 搜索
      search() {
        paytype.getPaytypeById(this.input).then(response => {
          console.log(response.data.data)
          const data = response.data.data;
          this.showedPaytypes=[];
          this.showedPaytypes.push(data);

        }).catch(error => {
        
        })
      },
      // 新增/修改 结算类别信息
      handleEdit(index, row) {
        this.currentPaytype = row
        this.editPaytypeDialogVisible = true
        console.log(this.currentPaytype)
      },

      handleDelete(index, row) {
        paytype.deletePaytype(row.id).then(response => {
          console.log(response.data)
          const data = response.data.data;

          if(response.data.code===200){
            this.success("结算类别删除");
          } else {
            this.fail("结算类别删除");
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
        this.setShowedPaytypes();
      },
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`);
        this.currentPage=val;
        this.setShowedPaytypes();
      },
      refresh() {
        paytype.listAllPaytypes().then(response => {
          console.log(response.data)
          const data = response.data.data
          this.paytypes = data;
          this.setShowedPaytypes();
        }).catch(error => {
          
        })
      },

      setShowedPaytypes() {
        console.log(this.currentPage);
        this.showedPaytypes = this.paytypes.slice((this.currentPage-1)*this.currentSize, (this.currentPage-1)*this.currentSize+this.currentSize);
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

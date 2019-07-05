<template lang="html">
<div class="container">
  <!-- 挂号级别管理功能区，做成卡片的样子 -->
  <el-card class="box-card" shadow="hover">
    <!-- 标题 -->
    <div slot="header" class="clearfix">
      <span style="padding-left: 20px;">挂号级别管理</span>
    </div>
    
    <!-- 表单部分 -->
    <div class="business-region">
      <!-- 表单头部：搜索功能区和添加挂号级别按钮 -->
      <div class="tool-bar">
        <!-- 搜索区 -->
        <div class="search-region">
          <el-input placeholder="搜索内容" v-model="input" class="input-with-select">
            <el-select v-model="chosenOption" slot="prepend" placeholder="通过挂号级别ID搜索" class="select-box">
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
            <el-button type="primary"  @click="addRegistrationLevelDialogVisible = true">新增挂号级别</el-button>
          </div>
          <div>
            <el-button type="primary"  @click="export2Excel">导出</el-button>
          </div>
        </div>
        </div>
        <!-- 列表，展示所有挂号级别或搜索到的挂号级别，后面带有修改/删除按钮-->
        <div class="table-region">
          <el-table :data="this.showedRegistrationLevels" style="width: 100%">
            <!-- <el-table-column
              type="selection"
              width="55">
            </el-table-column> -->
            <el-table-column label="挂号级别ID" prop="id" width="60">
            </el-table-column>
            <el-table-column label="挂号级别编码" prop="code">
            </el-table-column>
            <el-table-column label="挂号级别名称" prop="name">
            </el-table-column>
            <el-table-column label="挂号费" prop="cost">
            </el-table-column>
            <el-table-column label="挂号限额" prop="quota">
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
              :total="registrationlevels.length">
            </el-pagination>
          </div>
        </div>
      </div>
    </el-card>

    <el-dialog :visible.sync="addRegistrationLevelDialogVisible" width="60%">
      <registrationlevel-adder>添加挂号级别</registrationlevel-adder>
    </el-dialog>
    <el-dialog :visible.sync="editRegistrationLevelDialogVisible" width="60%">
      <registrationlevel-editor v-model="currentRegistrationLevel">编辑挂号级别信息</registrationlevel-editor>
    </el-dialog>
  </div>
</template>

<script>
import registrationlevel from '@/api/basicinfo/registrationlevel'
import RegistrationLevelEditor from './Child/RegistrationLevelEditor'
import RegistrationLevelAdder from './Child/RegistrationLevelAdder'

export default {
  name: 'RegistrationLevelAdmin',
  components:{
    'registrationlevel-editor': RegistrationLevelEditor,
    'registrationlevel-adder': RegistrationLevelAdder
  },

  data() {
    return {
      chosenOption: "",
      input: "",
      addRegistrationLevelDialogVisible: false,
      editRegistrationLevelDialogVisible: false,
      searchOptions: [
          {
            value: 'registrationlevelId',
            label: '通过挂号级别ID搜索'
          },
          {
            value: 'registrationlevelName',
            label: '通过挂号级别名搜索'
          }
        ],
      
      registrationlevels: [],
      showedRegistrationLevels: [],
      currentPage: 1,
      currentSize: 10,
      currentRegistrationLevel: {}
    }
  },

  methods: {
      export2Excel() {
　　　　require.ensure([], () => {
　　　　　　　　const { export_json_to_excel } = require('../../../vendor/Export2Excel');
　　　　　　　　const tHeader = ['挂号级别ID', '挂号级别编码', '挂号级别名称', '挂号费', '挂号限额']; //对应表格输出的title
　　　　　　　　const filterVal = ['id', 'code', 'name', 'cost', 'quota']; // 对应表格输出的数据
　　　　　　　　const list = this.registrationlevels;
　　　　　　　　const data = this.formatJson(filterVal, list);
　　　　　　　　export_json_to_excel(tHeader, data, '挂号级别'); //对应下载文件的名字
　　　　})
　　},

　　formatJson(filterVal, jsonData) {
　　　return jsonData.map(v => filterVal.map(j => v[j]))
　　 },  
      // 搜索
      search() {
        registrationlevel.getRegistrationLevelById(this.input).then(response => {
          console.log(response.data.data)
          const data = response.data.data;
          this.showedRegistrationLevels=[];
          this.showedRegistrationLevels.push(data);

        }).catch(error => {
        
        })
      },
      // 新增/修改 挂号级别信息
      handleEdit(index, row) {
        this.currentRegistrationLevel = row
        this.editRegistrationLevelDialogVisible = true
        console.log(this.currentRegistrationLevel)
      },

      handleDelete(index, row) {
        registrationlevel.deleteRegistrationLevel(row.id).then(response => {
          console.log(response.data)
          const data = response.data.data;

          if(response.data.code===200){
            this.success("挂号级别删除");
          } else {
            this.fail("挂号级别删除");
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
        this.setShowedRegistrationLevels();
      },
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`);
        this.currentPage=val;
        this.setShowedRegistrationLevels();
      },
      refresh() {
        registrationlevel.listAllRegistrationLevels().then(response => {
          console.log(response.data)
          const data = response.data.data
          this.registrationlevels = data;
          this.setShowedRegistrationLevels();
        }).catch(error => {
          
        })
      },

      setShowedRegistrationLevels() {
        console.log(this.currentPage);
        this.showedRegistrationLevels = this.registrationlevels.slice((this.currentPage-1)*this.currentSize, (this.currentPage-1)*this.currentSize+this.currentSize);
      }
  },

  mounted() {
    this.refresh();
  }
}
</script>

<style lang="css" scoped>
@import '../../../../static/css/admin.css';
</style>

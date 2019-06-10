<template lang="html">
<div class="container">
  <!-- 科室管理功能区，做成卡片的样子 -->
  <el-card class="box-card" shadow="hover">
    <!-- 标题 -->
    <div slot="header" class="clearfix">
      <span style="padding-left: 20px;">科室管理</span>
    </div>
    
    <!-- 表单部分 -->
    <div class="business-region">
      <!-- 表单头部：搜索功能区和添加科室按钮 -->
      <div class="tool-bar">
        <!-- 搜索区 -->
        <div class="search-region">
          <el-input placeholder="搜索内容" v-model="userInput" class="input-with-select">
            <el-select v-model="chosenOption" slot="prepend" placeholder="通过科室ID搜索" class="select-box">
              <el-option  v-for="item in searchOptions"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value">
              </el-option>
            </el-select>
            <el-button slot="append" icon="el-icon-search"></el-button>
          </el-input>
        </div>
        <!-- 添加，批量管理按钮 -->
        <div class="button-group">
          <!-- 添加按钮 -->
          <div>
            <el-button type="primary"  @click="addUserDialogVisible = true">新增科室</el-button>
          </div>
          <!-- 批量管理按钮 -->
          <div>
            <el-button plain>批量管理</el-button>
          </div>
        </div>
      </div>
      <!-- 列表，展示所有科室或搜索到的科室，后面带有修改/删除按钮-->
      <div class="table-region">
        <el-table :data="this.showedDepartments" style="width: 100%">
          <el-table-column
            type="selection"
            width="55">
          </el-table-column>
          <el-table-column label="科室ID" prop="id" width="60">
          </el-table-column>
          <el-table-column label="科室编码" prop="code">
          </el-table-column>
          <el-table-column label="科室名称" prop="name">
          </el-table-column>
          <el-table-column label="科室分类(小类)" prop="classification">
          </el-table-column>
          <el-table-column label="科室类别(大类)" prop="type">
          </el-table-column>
          <el-table-column>
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
        :total="departments.length">
      </el-pagination>
    </div>
      </div>
    </div>

  </el-card>
</div>
</template>

<script>
import department from '@/api/basicinfo/department'

export default {
  name: 'DepartmentAdmin',

  data() {
    return {
      userInput: "",
      chosenOption:'userId',
      addDepartmentDialogVisible: false,
      editDepartmentDialogVisible: false,
      searchOptions: [
          {
            value: 'userId',
            label: '通过科室ID搜索'
          },
          {
            value: 'userName',
            label: '通过科室名搜索'
          }
        ],
      
      departments: [],
      showedDepartments: [],
      currentPage: 1,
      currentSize: 10
    }
  },

  methods: {
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
        this.currentSize=val;
        this.setShowedDepartments();
      },
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`);
        this.currentPage=val;
        this.setShowedDepartments();
      },
      refresh() {
        department.listAllDepartments().then(response => {
          console.log(response.data)
          const data = response.data.data
          this.departments = data;
          this.setShowedDepartments();
        }).catch(error => {
          
        })
      },

      setShowedDepartments() {
        console.log(this.currentPage);
        this.showedDepartments = this.departments.slice((this.currentPage-1)*this.currentSize, (this.currentPage-1)*this.currentSize+this.currentSize);
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

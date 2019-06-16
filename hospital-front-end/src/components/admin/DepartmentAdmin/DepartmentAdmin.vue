<template lang="html">
<div class="container"  style="height: 600px;">
  <!-- 科室管理功能区，做成卡片的样子 -->
  <el-card class="box-card" shadow="hover">
    <!-- 标题 -->
    <div slot="header" class="clearfix">
      <i class="el-icon-tickets"></i>
      <span style="padding-left: 20px;">科室管理</span>
    </div>
    
    <!-- 表单部分 -->
    <div class="business-region">
      <!-- 表单头部：搜索功能区和添加科室按钮 -->
      <div class="tool-bar">
        <!-- 搜索区 -->
        <div class="search-region">
          <el-input placeholder="搜索内容" v-model="input" class="input-with-select">
            <el-select v-model="chosenOption" slot="prepend" placeholder="通过科室ID搜索" class="select-box">
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
            <el-button type="primary"  @click="addDepartmentDialogVisible = true">新增科室</el-button>
          </div>
        </div>
        </div>
        <!-- 列表，展示所有科室或搜索到的科室，后面带有修改/删除按钮-->
        <div class="table-region">
          <el-table :data="this.showedDepartments" style="width: 100%">
            <!-- <el-table-column
              type="selection"
              width="55">
            </el-table-column> -->
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
              :total="departments.length">
            </el-pagination>
          </div>
        </div>
      </div>
    </el-card>

    <el-dialog :visible.sync="addDepartmentDialogVisible" width="60%" :before-close="handleClose">
      <department-adder>添加科室</department-adder>
    </el-dialog>
    <el-dialog :visible.sync="editDepartmentDialogVisible" width="60%" :before-close="handleClose">
      <department-editor v-model="currentDepartment">编辑科室信息</department-editor>
    </el-dialog>
    <el-backtop target=".page-component__scroll .el-scrollbar__wrap"></el-backtop>
  </div>
</template>


<script>
import department from '@/api/basicinfo/department'
import DepartmentEditor from './Child/DepartmentEditor'
import DepartmentAdder from './Child/DepartmentAdder'

export default {
  name: 'DepartmentAdmin',
  components:{
    'department-editor': DepartmentEditor,
    'department-adder': DepartmentAdder
  },

  data() {
    return {
      input: "",
      addDepartmentDialogVisible: false,
      editDepartmentDialogVisible: false,
      searchOptions: [
          {
            value: 'departmentId',
            label: '通过科室ID搜索'
          },
          {
            value: 'departmentName',
            label: '通过科室名搜索'
          }
        ],
      
      departments: [],
      showedDepartments: [],
      currentPage: 1,
      currentSize: 10,
      currentDepartment: {}
    }
  },

  methods: {
      // 搜索
      search() {
        department.getDepartmentById(this.input).then(response => {
          console.log(response.data.data)
          const data = response.data.data;
          this.showedDepartments=[];
          this.showedDepartments.push(data);

        }).catch(error => {
        
        })
      },
      // 新增/修改 科室信息
      handleEdit(index, row) {
        this.currentDepartment = row
        this.editDepartmentDialogVisible = true
        console.log(this.currentDepartment)
      },

      handleDelete(index, row) {
        department.deleteDepartment(row.id).then(response => {
          console.log(response.data)
          const data = response.data.data;

          if(response.data.code===200){
            this.success("科室删除");
          } else {
            this.fail("科室删除");
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

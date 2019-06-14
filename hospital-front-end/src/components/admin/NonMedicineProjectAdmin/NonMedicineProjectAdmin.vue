<template lang="html">
<div class="container">
  <!-- 非药品收费项目管理功能区，做成卡片的样子 -->
  <el-card class="box-card" shadow="hover">
    <!-- 标题 -->
    <div slot="header" class="clearfix">
      <span style="padding-left: 20px;">非药品收费项目管理</span>
    </div>
    
    <!-- 表单部分 -->
    <div class="business-region">
      <!-- 表单头部：搜索功能区和添加非药品收费项目按钮 -->
      <div class="tool-bar">
        <!-- 搜索区 -->
        <div class="search-region">
          <el-input placeholder="搜索内容" v-model="input" class="input-with-select">
            <el-select v-model="chosenOption" slot="prepend" placeholder="通过非药品收费项目ID搜索" class="select-box">
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
            <el-button type="primary"  @click="addNonmedicineProjectDialogVisible = true">新增非药品收费项目</el-button>
          </div>
        </div>
        </div>
        <!-- 列表，展示所有非药品收费项目或搜索到的非药品收费项目，后面带有修改/删除按钮-->
        <div class="table-region">
          <el-table :data="this.showedNonmedicineProjects" style="width: 100%">
            <!-- <el-table-column
              type="selection"
              width="55">
            </el-table-column> -->
            <el-table-column label="非药品收费项目ID" prop="id" width="60">
            </el-table-column>
            <el-table-column label="非药品收费项目编码" prop="code">
            </el-table-column>
            <el-table-column label="非药品收费项目名称" prop="name">
            </el-table-column>
            <el-table-column label="非药品收费项目分类(小类)" prop="classification">
            </el-table-column>
            <el-table-column label="非药品收费项目类别(大类)" prop="type">
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
              :total="nonmedicineprojects.length">
            </el-pagination>
          </div>
        </div>
      </div>
    </el-card>

    <el-dialog :visible.sync="addNonmedicineProjectDialogVisible" width="60%" :before-close="handleClose">
      <nonmedicineproject-adder>添加非药品收费项目</nonmedicineproject-adder>
    </el-dialog>
    <el-dialog :visible.sync="editNonmedicineProjectDialogVisible" width="60%" :before-close="handleClose">
      <nonmedicineproject-editor v-model="currentNonmedicineProject">编辑非药品收费项目信息</nonmedicineproject-editor>
    </el-dialog>
  </div>
</template>

<script>
import nonmedicineproject from '@/api/basicinfo/nonmedicineproject'
import NonmedicineProjectEditor from './Child/NonmedicineProjectEditor'
import NonmedicineProjectAdder from './Child/NonmedicineProjectAdder'

export default {
  name: 'NonmedicineProjectAdmin',
  components:{
    'nonmedicineproject-editor': NonmedicineProjectEditor,
    'nonmedicineproject-adder': NonmedicineProjectAdder
  },

  data() {
    return {
      input: "",
      addNonmedicineProjectDialogVisible: false,
      editNonmedicineProjectDialogVisible: false,
      searchOptions: [
          {
            value: 'nonmedicineprojectId',
            label: '通过非药品收费项目ID搜索'
          },
          {
            value: 'nonmedicineprojectName',
            label: '通过非药品收费项目名搜索'
          }
        ],
      
      nonmedicineprojects: [],
      showedNonmedicineProjects: [],
      currentPage: 1,
      currentSize: 10,
      currentNonmedicineProject: {}
    }
  },

  methods: {
      // 搜索
      search() {
        nonmedicineproject.getNonmedicineProjectById(this.input).then(response => {
          console.log(response.data.data)
          const data = response.data.data;
          this.showedNonmedicineProjects=[];
          this.showedNonmedicineProjects.push(data);

        }).catch(error => {
        
        })
      },
      // 新增/修改 非药品收费项目信息
      handleEdit(index, row) {
        this.currentNonmedicineProject = row
        this.editNonmedicineProjectDialogVisible = true
        console.log(this.currentNonmedicineProject)
      },

      handleDelete(index, row) {
        nonmedicineproject.deleteNonmedicineProject(row.id).then(response => {
          console.log(response.data)
          const data = response.data.data;

          if(response.data.code===200){
            this.success("非药品收费项目删除");
          } else {
            this.fail("非药品收费项目删除");
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
        this.setShowedNonmedicineProjects();
      },
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`);
        this.currentPage=val;
        this.setShowedNonmedicineProjects();
      },
      refresh() {
        nonmedicineproject.listAllNonmedicineProjects().then(response => {
          console.log(response.data)
          const data = response.data.data
          this.nonmedicineprojects = data;
          this.setShowedNonmedicineProjects();
        }).catch(error => {
          
        })
      },

      setShowedNonmedicineProjects() {
        console.log(this.currentPage);
        this.showedNonmedicineProjects = this.nonmedicineprojects.slice((this.currentPage-1)*this.currentSize, (this.currentPage-1)*this.currentSize+this.currentSize);
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

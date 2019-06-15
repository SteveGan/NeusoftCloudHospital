<template lang="html">
<div class="container">
  <!-- 诊断目录管理功能区，做成卡片的样子 -->
  <el-card class="box-card" shadow="hover">
    <!-- 标题 -->
    <div slot="header" class="clearfix">
      <span style="padding-left: 20px;">诊断目录管理</span>
    </div>
    
    <!-- 表单部分 -->
    <div class="business-region">
      <!-- 表单头部：搜索功能区和添加诊断目录按钮 -->
      <div class="tool-bar">
        <!-- 搜索区 -->
        <div class="search-region">
          <el-input placeholder="搜索内容" v-model="input" class="input-with-select">
            <el-select v-model="chosenOption" slot="prepend" placeholder="通过诊断目录ID搜索" class="select-box">
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
            <el-button type="primary"  @click="addDiseaseDialogVisible = true">新增诊断目录</el-button>
          </div>
        </div>
        </div>
        <!-- 列表，展示所有诊断目录或搜索到的诊断目录，后面带有修改/删除按钮-->
        <div class="table-region">
          <el-table :data="this.showedDiseases" style="width: 100%">
            <!-- <el-table-column
              type="selection"
              width="55">
            </el-table-column> -->
            <el-table-column label="诊断目录ID" prop="id" width="60">
            </el-table-column>
            <el-table-column label="诊断目录编码" prop="code">
            </el-table-column>
            <el-table-column label="诊断目录名称" prop="name">
            </el-table-column>
            <el-table-column label="诊断目录分类(小类)" prop="classification">
            </el-table-column>
            <el-table-column label="诊断目录类别(大类)" prop="type">
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
              :total="diseases.length">
            </el-pagination>
          </div>
        </div>
      </div>
    </el-card>

    <el-dialog :visible.sync="addDiseaseDialogVisible" width="60%" :before-close="handleClose">
      <disease-adder>添加诊断目录</disease-adder>
    </el-dialog>
    <el-dialog :visible.sync="editDiseaseDialogVisible" width="60%" :before-close="handleClose">
      <disease-editor v-model="currentDisease">编辑诊断目录信息</disease-editor>
    </el-dialog>
  </div>
</template>

<script>
import disease from '@/api/basicinfo/disease'
import DiseaseEditor from './Child/DiseaseEditor'
import DiseaseAdder from './Child/DiseaseAdder'

export default {
  name: 'DiseaseAdmin',
  components:{
    'disease-editor': DiseaseEditor,
    'disease-adder': DiseaseAdder
  },

  data() {
    return {
      input: "",
      addDiseaseDialogVisible: false,
      editDiseaseDialogVisible: false,
      searchOptions: [
          {
            value: 'diseaseId',
            label: '通过诊断目录ID搜索'
          },
          {
            value: 'diseaseName',
            label: '通过诊断目录名搜索'
          }
        ],
      
      diseases: [],
      showedDiseases: [],
      currentPage: 1,
      currentSize: 10,
      currentDisease: {}
    }
  },

  methods: {
      // 搜索
      search() {
        disease.getDiseaseById(this.input).then(response => {
          console.log(response.data.data)
          const data = response.data.data;
          this.showedDiseases=[];
          this.showedDiseases.push(data);

        }).catch(error => {
        
        })
      },
      // 新增/修改 诊断目录信息
      handleEdit(index, row) {
        this.currentDisease = row
        this.editDiseaseDialogVisible = true
        console.log(this.currentDisease)
      },

      handleDelete(index, row) {
        disease.deleteDisease(row.id).then(response => {
          console.log(response.data)
          const data = response.data.data;

          if(response.data.code===200){
            this.success("诊断目录删除");
          } else {
            this.fail("诊断目录删除");
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
        this.setShowedDiseases();
      },
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`);
        this.currentPage=val;
        this.setShowedDiseases();
      },
      refresh() {
        disease.listAllDiseases().then(response => {
          console.log(response.data)
          const data = response.data.data
          this.diseases = data;
          this.setShowedDiseases();
        }).catch(error => {
          
        })
      },

      setShowedDiseases() {
        console.log(this.currentPage);
        this.showedDiseases = this.diseases.slice((this.currentPage-1)*this.currentSize, (this.currentPage-1)*this.currentSize+this.currentSize);
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

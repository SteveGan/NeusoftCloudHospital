<template lang="html">
<div class="container">

  <!-- 用户管理功能区，做成卡片的样子 -->
  <el-card class="box-card" shadow="hover">
    <!-- 标题 -->
    <div slot="header" class="clearfix">
      <span style="padding-left: 20px;">用户管理</span>
    </div>
    <!-- 表单部分 -->
    <div class="business-region">
      <!-- 表单头部：搜索功能区和添加用户按钮 -->
      <div class="tool-bar">
        <!-- 搜索区 -->
        <div class="search-region">
          <el-input placeholder="搜索内容" v-model="userInput" class="input-with-select">
            <el-select v-model="chosenOption" slot="prepend" placeholder="通过用户ID搜索" class="select-box">
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
            <el-button type="primary"  @click="addUserDialogVisible = true">新增用户</el-button>
          </div>
          <!-- 批量管理按钮 -->
          <div>
            <el-button plain>批量管理</el-button>
          </div>
        </div>
      </div>
      <!-- 列表，展示所有用户或搜索到的用户，后面带有修改/删除按钮-->
      <div class="table-region">
        <el-table :data="this.usersDisplayed" style="width: 100%">
          <el-table-column
            type="selection"
            width="55">
          </el-table-column>
          <el-table-column type="expand">
            <template slot-scope="props">
              <user-basic-info :userInfo="props.row"></user-basic-info>
            </template>
          </el-table-column>
          <el-table-column
            label="用户ID"
            prop="userId">
          </el-table-column>
          <el-table-column
            label="用户名"
            prop="userName">
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
      </div>
    </div>
  </el-card>

  <el-dialog
    :visible.sync="addUserDialogVisible"
    width="60%"
    :before-close="handleClose">
    <user-info-editor>添加用户</user-info-editor>
  </el-dialog>
  <el-dialog
    :visible.sync="editUserDialogVisible"
    width="60%"
    :before-close="handleClose">
    <user-info-editor :user="currentUser">编辑用户信息</user-info-editor>
  </el-dialog>
</div>
</template>

<script>
import UserBasicInfo from '@/components/common/UserbasicInfo'
import UserInfoEditor from './Child/UserInfoEditor'

export default {
  name: 'UserAdmin',
  components:{
    'user-basic-info': UserBasicInfo,
    'user-info-editor': UserInfoEditor
  },
  computed: {
    usersDisplayed () {
      return this.users.filter(
          data => {
            var isInclude = false
            if(this.chosenOption === 'userId'){
              isInclude = data.userId.toString().toLowerCase().includes(
                this.userInput.toLowerCase()
              )
            }else if(this.chosenOption === 'userName'){
              isInclude = data.userName.toString().toLowerCase().includes(
                this.userInput.toLowerCase()
              )
            }else{
              isInclude = true;
            }
            return !this.userInput || isInclude
          }
        )
    }
  },
  data () {
      return {
        userInput:'',
        chosenOption:'userId',
        addUserDialogVisible: false,
        editUserDialogVisible: false,
        searchOptions: [
          {
            value: 'userId',
            label: '通过用户ID搜索'
          },
          {
            value: 'userName',
            label: '通过用户名搜索'
          }
        ],
        currentUser: {},
        users:[
          {
            "avatar": "http://ww4.sinaimg.cn/large/006tNc79ly1g3hfk9foouj30ho0heqoj.jpg",
            "userName": "帅哥",
            "userId": 10000001,
            "roles":[
              {
                "id": 20000001,
                "userId": 10000001,
                "positionId": 2,
                "positionName": "门诊医生",
                "departmentId": 16,
                "departmentName": "神经外科",
                "titleId": 1,
                "titleName": "主任医师",
                "gmtCreate": null,
                "gmtModified": null
              },
              {
                "id": 20000002,
                "userId": 10000001,
                "positionId": 2,
                "positionName": "医技医生",
                "departmentId": 19,
                "departmentName": "某某科室",
                "titleId": 7,
                "titleName": "大佬",
                "gmtCreate": null,
                "gmtModified": null
              }
            ]
          },
          {
            "avatar": "http://ww4.sinaimg.cn/large/006tNc79ly1g3j7rgt3mnj30u0140hav.jpg",
            "userName": "儿子",
            "userId": 10000002,
            "roles":[
              {
                "id": 20000001,
                "userId": 10000001,
                "positionId": 2,
                "positionName": "门诊医生",
                "departmentId": 16,
                "departmentName": "神经外科",
                "titleId": 1,
                "titleName": "主任医师",
                "gmtCreate": null,
                "gmtModified": null
              },
              {
                "id": 20000002,
                "userId": 10000001,
                "positionId": 2,
                "positionName": "医技医生",
                "departmentId": 19,
                "departmentName": "某某科室",
                "titleId": 7,
                "titleName": "我的儿子",
                "gmtCreate": null,
                "gmtModified": null
              }
            ]
          }
        ]
      }
    },
    methods: {
      handleEdit(index, row) {
        this.currentUser = row
        this.editUserDialogVisible = true
      },
      handleDelete(index, row) {
        console.log(index, row);
      },
      handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
      }
    }
}
</script>

<style lang="css" scoped>
@import '../../../../static/css/admin.css';
</style>

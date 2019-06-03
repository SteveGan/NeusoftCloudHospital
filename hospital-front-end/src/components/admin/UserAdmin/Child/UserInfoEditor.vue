<template lang="html">
<div>
  <!-- 标题 -->
  <div class="title">
    <p><slot></slot></p>
  </div>
  <!-- 内容区 -->
  <div class="">
    <el-form ref="form" :model="form" label-width="80px" label-position='left'>
      <el-form-item label="用户姓名">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="用户ID">
        <el-input v-model="form.id"></el-input>
      </el-form-item>
      <el-form-item label="添加职位">
        <el-button type="primary" icon="el-icon-plus" circle @click="addRole"></el-button>
      </el-form-item>
      <el-form-item
        v-for="(role, index) in form.roles"
        :label="'职位 ' + index"
        :key="role.department"
        :inline="true"
      >
      <el-row :gutter="10">
        <el-col :span="6">
          <el-input v-model="role.departmentName" placeholder="请输入所属部门"></el-input>
        </el-col>
        <el-col :span="6">
          <el-input v-model="role.positionName" placeholder="请输入职位"></el-input>
        </el-col>
        <el-col :span="6">
          <el-input v-model="role.title" placeholder="请输入职称"></el-input>
        </el-col>
        <el-col :span="3">
          <el-button @click.prevent="removeRole(role)">删除</el-button>
        </el-col>
      </el-row>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="onSubmit">立即创建</el-button>
        <el-button>取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</div>
</template>

<script>
export default {
  name: 'UserInfoEditor',
  props:{
    user: Object
  },
  data (){
    return {
      form: {
          avatar:'',
          name: '',
          id: '',
          roles: [
            {
              id: '',
              userId: '',
              positionId: '',
              positionName: '',
              departmentId: '',
              departmentName: '',
              titleId:'',
              titleName:''
            }
          ]
        }
    }
  },
  methods: {
    onSubmit() {
      console.log('submit!');
    },
    addRole() {
      this.form.roles.push({
        departmentName: '',
        positionName: '',
        titleName:''
      })
    },
    removeRole(role){
      var index = this.form.roles.indexOf(role)
      if( index !== -1){
        this.form.roles.splice(index,1)
      }
    }
  }
}
</script>

<style lang="css" scoped>
.title p{
  font: 20px bold;
}
</style>

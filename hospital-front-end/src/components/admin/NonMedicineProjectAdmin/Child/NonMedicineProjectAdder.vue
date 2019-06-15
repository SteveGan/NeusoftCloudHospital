<template lang="html">
<div>
  <!-- 标题 -->
  <div class="title">
    <p><slot></slot></p>
  </div>
  <!-- 内容区 -->
  <div class="">
    <el-form ref="form" :model="form" label-width="80px" label-position='left'>
      <el-form-item label="非药品收费项目编码">
        <el-input v-model="form.code"></el-input>
      </el-form-item>
      <el-form-item label="非药品收费项目名称">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="非药品收费项目分类(小类)">
        <el-input v-model="form.classification"></el-input>
      </el-form-item>
      <el-form-item label="非药品收费项目类别(大类)">
        <el-input v-model="form.type"></el-input>
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
import nonmedicineproject from '@/api/basicinfo/nonmedicineproject'

export default {
  name: 'NonmedicineProjectAdder',
  props:{
    value: Object
  },
  data (){
    return {
      form: {
        code:'',
        name: '',
        classification: '',
        type: ''
      }
    }
  },
  methods: {
    onSubmit() {
      nonmedicineproject.addNonmedicineProject(this.form).then(response => {
        console.log(response.data)
        const data = response.data.data;

        if(response.data.code===200){
          this.success();
        } else {
          this.fail();
        }
      }).catch(error => {
        
      })
    },

    // 添加成功提示
    success() {
      this.$message({
        message: '非药品收费项目添加成功',
        type: 'success'
      });
    },
    
    // 添加失败提示
    fail() {
      this.$message.error('非药品收费项目添加失败');
    },
  }
}
</script>

<style lang="css" scoped>
.title p{
  font: 20px bold;
}
</style>

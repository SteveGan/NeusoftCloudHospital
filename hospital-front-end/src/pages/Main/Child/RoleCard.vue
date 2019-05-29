<template>
  <el-card class="box-card role-card"  shadow="hover" @click="handleClick">
    <div slot="header" class="clearfix">
      <i :class="icon"></i>
      <span>{{realDepartmentName}}</span>
    </div>
    <div>
      <p class="role-info"><span>职位：</span>{{realPositionName}}</p>
    </div>
    <div>
      <p class="role-info"><span>职称：</span>{{realTitleName}}</p>
    </div>
    <div>
    </div>
  </el-card>
</template>

<script>
  export default {
    name: 'RoleCard',
    props: {
      role: Object
    },
    data () {
      return {
        icon: 'el-icon-s-custom',
        departmentName:  '暂无部门名称',
        positionName: '默认职位',
        titleName: '无'
      }
    },
    methods: {
      handleClick () {
        console.log("Clicked")
      },
    },
    computed:{
      realDepartmentName(){
        if(this.departmentName !== '')
          return this.role.departmentName
        else
          return this.departmentName
      },
      realPositionName(){
        if(this.positionName !== '')
          return this.role.positionName
        else
          return this.positionName
      },
      realTitleName(){
        if(this.role.titleName !== '')
          return this.role.titleName
        else
          return this.titleName
      }
    }
  }
</script>

<style>
  .role-card{
    margin: 20px 30px;
    min-width: 250px;
  }
  .role-info{
    font-size: 16px;
  }

</style>

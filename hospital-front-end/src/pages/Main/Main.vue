<template>
  <div class="container">
    <!--  部门与职位部分    -->
    <div>
      <!--  标题："部门与职位"      -->
      <div>
        <p class="title">部门与职位</p>
      </div>
      <!--   内容：一堆卡片    -->
      <div class="card-container">
        <role-card v-for="roleObj in this.$store.state.user.roles" v-bind:role="roleObj"></role-card>
      </div>
    </div>
    <!--  统计数据部分    -->
    <div>
      <!--  标题："数据统计"      -->
      <div>
        <p class="title">历史数据统计</p>
      </div>
    </div>
  </div>
</template>

<script>

  import RoleCard from './Child/RoleCard'

  export default {
    name: 'Main',
    components: {
      'role-card': RoleCard
    }
  }
</script>


<style lang="css" scoped>
  .container{
    padding-left: 20px;
    padding-right: 20px;
  }
  .container div{
    padding-left: 20px;
    padding-right: 20px;
  }
  .card-container{
    display: flex;
    flex-direction: row;
    justify-content: flex-start;
  }
  .title{
    font-size: 27px;
  }
</style>

<template>
  <div>
    <!-- 部门与职位部分 -->
    <div>
      <user-roles :roles="this.$store.state.user.roles"></user-roles>
    </div>

    <!-- 统计数据部分 -->
    <div>
      <el-card shadow="hover" class="main-card">
        <div class="role-area">
          <div>
            <p class="title"><i class="el-icon-s-marketing"></i> 我的统计</p>
          </div>
          <el-card style="margin: 5px 4px;s" shadow="hover">
            <div slot="header">
              <span>今日工作量</span>
              
            </div> 
          </el-card>
          <el-card style="margin: 5px 4px;s" shadow="hover">
            <div slot="header">
              <span>七日工作量</span>
            </div> 
          </el-card>
          <el-card style="margin: 5px 4px;s" shadow="hover">
            <div slot="header">
              <span>月度工作量</span>
            </div>
            <div v-for="(item, index) in thirtyDays">
              <div :id="`myChart${index}`" :style="{width: '300px', height: '300px'}"></div>
            </div>
          </el-card>          

          
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
import UserRoles from "@/components/user/UserRoles/UserRoles";
import statistics from '@/api/finicial/statistics';
import echarts from 'echarts';

export default {
  name: "Main",
  components: {
    "user-roles": UserRoles
  },
  data() {
    return {
      // 当前用户ID
      currentUserId: "",
      // 今天日期
      today: "",
      // 工作量信息
      currentDay: [],
      sevenDays: [],
      thirtyDays: []
    }
  },

  methods: {
    search() {
      this.loading = true;
      statistics.clinicianDoctorStatistics(this.currentUserId, this.today).then(
        response => {
          const data = response.data.data;
          console.log(this.workingInfo);
          this.currentDay = data.currentDay;
          this.sevenDays = data.sevenDays;
          this.thirtyDays = data.thirtyDays;

          for (var i=0; i<this.sevenDays.length; i++){
            this.drawChart(this.sevenDays[i], 'myChart' + i);
          }
          
        }
      )
    },

    // 计算当前日期
    getNowFormatDate() {
      var date = new Date();
      var seperator1 = "-";
      var year = date.getFullYear();
      var month = date.getMonth() + 1;
      var strDate = date.getDate();
      if (month >= 1 && month <= 9) {
          month = "0" + month;
      }
      if (strDate >= 0 && strDate <= 9) {
          strDate = "0" + strDate;
      }
      var currentdate = year + seperator1 + month + seperator1 + strDate;
      return currentdate;
    },

    drawChart(val, id) {
      // 基于准备好的dom，初始化echarts实例
      console.log("id=" + id);
      let myChart = this.$echarts.init(document.getElementById(id))
      // 绘制图表
      myChart.setOption({
        title : {
          text: val.departmentName,
          subtext: val.titleName,
          x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        series : [
          {
              name: '费用占比',
              type: 'pie',
              radius : '55%',
              center: ['50%', '60%'],
              data:[
                  {value:val.挂号费, name:'挂号费'},
                  {value:val.检验费, name:'检验费'},
                  {value:val.检验材料费, name:'检验材料费'},
                  {value:val.超声检查费, name:'超声检查费'},
                  {value:val.超声材料费, name:'超声材料费'},
                  {value:val.放射检查费, name:'放射检查费'},
                  {value:val.放射材料费, name:'放射材料费'},
                  {value:val.西药费, name:'西药费'},
                  {value:val.中成药费, name:'中成药费'},
                  {value:val.中草药费, name:'中草药费'},
                  {value:val.处置费, name:'处置费'},
                  {value:val.处置材料费, name:'处置材料费'},
                  {value:val.检查费, name:'检查费'},
              ],
              itemStyle: {
                  emphasis: {
                      shadowBlur: 10,
                      shadowOffsetX: 0,
                      shadowColor: 'rgba(0, 0, 0, 0.5)'
                  }
              }
          }
        ]
      });
    }
  },

  mounted() {
    // 得到当前操作员id
    const currentUserId = this.$store.getters['user/currentUserId']
    this.currentUserId = currentUserId;    
    // 得到今天日期
    this.today=this.getNowFormatDate();
    this.search();
  }
};
</script>


<style lang="css" scoped>
.container {
  padding-left: 40px;
  padding-right: 40px;
}
.main-box {
  background-color: #f2f7f8;
}
.role-area {
  padding-left: 20px;
  padding-right: 20px;
}
.card-container {
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  flex-wrap: wrap;
}
.title {
  margin-left: 30px;
  font-size: 27px;
}
</style>

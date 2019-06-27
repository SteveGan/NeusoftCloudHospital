<template>
  <el-container class="container">
    <el-header class="header-bar">
      <!-- 医院名称 -->
      <div>
        <img src="@/assets/icons/project_logo_complete.png" class="project-logo">
      </div>
      <!-- 部门名 -->
      <div>
        <p class="department-name">{{curDepartment.name}}</p>
      </div>
      <!-- 时间 -->
      <div style="margin-left: auto;">
        <digital-clock blink="true"></digital-clock>
      </div>
    </el-header>
    <el-main class="main-container">
      <!-- 当前医生 -->
      <div class="doctor-info">
        <p>坐诊医生: {{curDoctor.name}}</p>
      </div>
      <!-- 正在就诊 -->
      <div class="notification-region">
        <p>正在就诊</p>
        <p>{{curPatient.caseId}} {{curPatient.name}}</p>
      </div>
      <!-- 准备就诊 -->
      <div class="notification-region">
        <p>准备就诊</p>
        <p>{{nextPatient.caseId}} {{nextPatient.name}}</p>
      </div>
      <!-- 候诊列表 -->
      <div class="waiting-list">
        <p>候诊队列</p>
        <el-table :data="waitingPatients" class="patient-table" :border="true" :fit="true">
          <el-table-column prop="caseId" label="病历号" width="200" align="center"></el-table-column>
          <el-table-column prop="name" label="患者姓名" width="200" align="center"></el-table-column>
        </el-table>
      </div>
    </el-main>
    <el-footer class="container">
      <div class="footer-region">
        <p>候诊时请保持安静</p>
      </div>
    </el-footer>
    <!-- 链接诊室dialog -->
    <el-dialog title="屏幕链接设置" :visible.sync="connectionDialogVisible" center>
      <el-card shadow="hover">
        <el-form label-position="left" label-width="40px">
          <el-form-item label="部门" style="width: 200px">
            <el-autocomplete
              class="inline-input"
              v-model="curDepartment.name"
              :fetch-suggestions="queryDeparmentSearch"
              placeholder="搜索部门名称"
              :highlight-first-item="true"
              @select="handleSelectDepartment"
              value-key="name"
              style="width: 180px;"
            ></el-autocomplete>
          </el-form-item>
          <el-form-item label="医生" style="width: 200px">
            <el-autocomplete
              class="inline-input"
              v-model="curDoctor.name"
              :fetch-suggestions="queryDoctorSearch"
              placeholder="搜索部门名称"
              :highlight-first-item="true"
              @select="handleSelectDoctor"
              value-key="roleName"
              style="width: 180px;"
            ></el-autocomplete>
          </el-form-item>
        </el-form>
        <el-button type="primary" @click="handleSubscribe">链接</el-button>
      </el-card>
    </el-dialog>
    <!-- 语音播报 -->
    <div id="bdtts_div_id">
      <audio id="tts_autio_id" autoplay="autoplay">
        <source
          id="tts_source_id"
          src="http://tts.baidu.com/text2audio?lan=zh&amp;ie=UTF-8&amp;spd=1&amp;per=0&amp;vol=15&amp;text="
          type="audio/mpeg"
        >
        <embed id="tts_embed_id" height="0" width="0" src>
      </audio>
    </div>
  </el-container>
</template>

<script>
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import DigitalClock from "vue-digital-clock";
import register from "@/api/register";
import { deprecate } from "util";
import { listAllPatients } from "@/api/patient";

export default {
  name: "Caller",
  components: {
    "digital-clock": DigitalClock
  },
  data() {
    return {
      connectionDialogVisible: true,
      curDepartment: {
        name: "",
        id: ""
      },
      curDoctor: {
        name: "",
        roleId: ""
      },
      curPatient: {
        name: "",
        caseId: ""
      },
      nextPatient: {},
      departments: [],
      doctors: [],
      allPatients: [],
      waiting: [
        {
          caseId: "A31331",
          name: "刘雨晴"
        },
        {
          caseId: "A313312",
          name: "刘嘉瑾"
        },
        {
          caseId: "A31334",
          name: "束万阳"
        }
      ]
    };
  },
  computed: {
    waitingPatients: function() {
      return this.allPatients.slice(1, this.allPatients.length);
    }
  },
  mounted: function() {
    // 得到所有科室清单：
    register.listAllDepartments().then(
      response => {
        console.log(response.data.data);
        this.departments = response.data.data;
      },
      error => {
        console.log(error);
      }
    );
  },
  beforeDestroy: function() {
    // // 页面离开时断开连接,清除定时器
    // this.disconnect();
    // clearInterval(this.timer);
  },
  methods: {
    initWebSocket(roleId) {
      this.connection(roleId);
      let self = this;
      // 断开重连机制,尝试发送消息,捕获异常发生时重连
      this.timer = setInterval(() => {
        try {
          self.stompClient.send("test");
        } catch (err) {
          console.log("断线了: " + err);
          self.connection(roleId);
        }
      }, 5000);
    },
    connection(roleId) {
      // 建立连接对象
      this.socket = new SockJS("http://localhost:1923/webSocketServer");
      // 获取STOMP子协议的客户端对象
      this.stompClient = Stomp.over(this.socket);

      // 向服务器发起websocket连接
      this.stompClient.connect(
        {},
        frame => {
          console.log("Connected: " + frame);
          this.stompClient.subscribe(
            "/topic/outpatient/queue/" + roleId,
            response => {
              var data = JSON.parse(response.body);

              // 如果有人被医生叫入诊室
              if (data.code === "update") {
                // 如果过该病人已经被列为正在诊断的患者
                if (data.caseId === this.curPatient.caseId) {
                  // 只播放广播，不更新屏幕
                } else {
                  // 检查当前是否有正在诊断的病人（可能被中途打断）
                  if (this.curPatient.caseId !== "") {
                    // 将当前被诊断的病人放回等待队列的开头
                    this.allPatients.unshift(
                      Object.assign({}, this.curPatient)
                    );
                  }
                  // 如果该病人正在等待状态
                  var index = this.allPatients.findIndex(
                    patient => patient.caseId === data.caseId
                  );
                  this.curPatient = Object.assign({}, this.allPatients[index]);
                  this.allPatients.splice(index, 1);
                  this.nextPatient = this.allPatients[0];
                  console.log("准备就诊变为：");
                  console.log(this.allPatients[0]);

                  this.doTTS(
                    "请" +
                      this.curPatient.name +
                      "到" +
                      this.curDoctor.name +
                      "医生处就诊"
                  );
                  this.doTTS("请" + this.nextPatient.name + "准备");
                }
              }
              // 如果有人初诊完毕，应当被从屏幕上移除
              else if (data.code === "done") {
                // 如果被移除的是正在就诊的患者（按照设计应该只有这种情况）
                if (this.curPatient.caseId === data.caseId) {
                  this.curPatient = Object.assign({}, { caseId: "", name: "" });
                } else {
                  // 如果被移除的是还在等待的患者（可能发生，情况不明）
                  var index = this.allPatients.findIndex(
                    patient => patient.caseId === data.caseId
                  );
                  this.allPatients.splice(index, 1);
                  this.nextPatient = this.allPatients[0];
                }
              }
              // 如果添加新的候诊人员
              else if (data.code === "add") {
                this.allPatients.push(data);
              }
            }
          );
        },
        error => {
          // 连接发生错误时的处理函数
          console.log(error);
        }
      );
    },
    queryDeparmentSearch(queryString, cb) {
      var departments = this.departments;
      var results = queryString
        ? departments.filter(this.createDepartmentFilter(queryString))
        : departments;
      cb(results);
    },
    createDepartmentFilter(queryString) {
      return department => {
        return (
          department.name.toLowerCase().indexOf(queryString.toLowerCase()) === 0
        );
      };
    },
    handleSelectDepartment(department) {
      this.curDepartment = department;
      // 查找该deparment下载的所有医生
      register.listAllDoctorsByDepartmentId(this.curDepartment.id).then(
        response => {
          this.doctors = response.data.data.roles;
        },
        error => {
          console.log(error);
        }
      );
    },
    queryDoctorSearch(queryString, cb) {
      var doctors = this.doctors;
      var results = queryString
        ? doctors.filter(this.createDepartmentFilter(queryString))
        : doctors;
      cb(results);
    },
    createDoctorFilter(queryString) {
      return doctor => {
        doctor.roleName.toLowerCase().indexOf(queryString.toLowerCase()) === 0;
      };
    },
    handleSelectDoctor(doctor) {
      this.curDoctor.name = doctor.roleName;
      this.curDoctor.roleId = doctor.roleId;
    },
    handleSubscribe() {
      // 请求初始数据（当前待诊患者名单表）
      //请求所有待诊病人和已诊病人
      console.log("begin subscribe");
      listAllPatients(this.curDoctor.roleId).then(
        response => {
          const data = response.data.data;
          this.allPatients = data.waitingPatients;
          this.nextPatient = this.allPatients[0];
          this.connectionDialogVisible = false;
          this.initWebSocket(this.curDoctor.roleId);
        },
        error => {
          console.log(error);
        }
      );
    },
    doTTS(text) {
      var ttsDiv = document.getElementById("bdtts_div_id");
      var ttsAudio = document.getElementById("tts_autio_id");
      var ttsText = text;

      // 文字转语音
      ttsDiv.removeChild(ttsAudio);
      var au1 = '<audio id="tts_autio_id" autoplay="autoplay">';
      var sss =
        '<source id="tts_source_id" src="http://tsn.baidu.com/text2audio?lan=zh&ie=UTF-8&per=3&spd=14&vol=15&text=' +
        ttsText +
        '" type="audio/mpeg">';
      var eee = '<embed id="tts_embed_id" height="0" width="0" src="">';
      var au2 = "</audio>";
      ttsDiv.innerHTML = au1 + sss + eee + au2;

      ttsAudio = document.getElementById("tts_autio_id");

      ttsAudio.play();
    }
  }
};
</script>


<style lang="css" scoped>
.project-logo {
  widows: 100px;
  height: 40px;
}
.clock {
  background-color: #0178bc;
  color: #eceff1;
  padding: 3px 3px;
  font-size: 20px;
  font-family: "Menlo", monospace;
}
.container {
  padding: 0px;
  margin: 0px;
  min-height: 100%;
  min-width: 100%;
}
.header-bar {
  padding: 10px;
  margin: 0px;
  min-height: 100%;
  min-width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  align-items: center;
  background-image: linear-gradient(to right, #00bdda 0%, #0178bc 100%);
}
.main-container {
  padding: 0px;
  margin: 0px;
  min-height: 100%;
  min-width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
}
.department-name {
  padding-left: 20px;
  color: #eceff1;
  font-size: 17px;
  font-family: Arial, Helvetica, sans-serif;
}
.doctor-info {
  width: 300px;
  height: 50px;
  left: 0;
  right: 0;
  margin: 20px auto;
  padding-bottom: 10px;
  border-radius: 5px;
  background-image: linear-gradient(to right, #00bdda 0%, #0178bc 100%);
}
.doctor-info p {
  text-align: center;
  color: #eceff1;
  font-size: 20px;
  font-family: Arial, Helvetica, sans-serif;
}
.notification-region {
  width: 300px;
  height: 80px;
  left: 0;
  right: 0;
  margin: 10px auto;
}
.notification-region :first-child {
  text-align: center;
  color: #8c8c8c;
  font-size: 15px;
  font-family: Arial, Helvetica, sans-serif;
}
.notification-region :last-child {
  text-align: center;
  color: #1890ff;
  font-size: 22px;
  font-family: Arial, Helvetica, sans-serif;
}
.waiting-list {
  width: 400px;
  left: 0;
  right: 0;
  margin: 10px auto;
}
.waiting-list p {
  text-align: center;
  color: #8c8c8c;
  font-size: 15px;
  font-family: Arial, Helvetica, sans-serif;
}
.patient-table {
  color: #1890ff;
  width: 400px;
  font-size: 20px;
  font-family: Arial, Helvetica, sans-serif;
  margin: 0 auto;
}
.footer-region {
  margin: 0px;
  min-height: 100%;
  min-width: 100%;
  background-image: linear-gradient(to right, #00bdda 0%, #0178bc 100%);
}
.footer-region p {
  padding-top: 5px;
  text-align: center;
  color: #eceff1;
  font-size: 20px;
  font-family: Arial, Helvetica, sans-serif;
}
</style>
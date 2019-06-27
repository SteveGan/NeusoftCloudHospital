<template>
  <div>
    <el-menu class="el-menu-demo align-end" mode="horizontal" color="#ffffff" @select="handleSelect">
      <el-menu-item class="justify-start" @click="handleClickLogo">
        <img src="@/assets/icons/project_logo_complete.png" class="project-logo">
      </el-menu-item>

      <el-menu-item>
        <el-dropdown>
          <span class="el-dropdown-link">
            {{this.$store.getters['user/currentRoleDescription']}}
            <i class="el-icon-arrow-down el-icon--right el-dropdown-link"></i>
          </span>
          <el-dropdown-menu slot="dropdown" @command="handleSelectRole">
            <el-dropdown-item v-for="role in this.$store.getters['user/roles']" v-bind:key="role.roleId" command="role" >
              {{role.departmentName}}:{{role.positionName}}
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-menu-item>

      <el-submenu index="1">
        <template slot="title">
          <!--   用户头像     -->
          <img :src="avatar" class="avatar">
          <span class="user-name">{{this.$store.state.user.name}}</span>
        </template>
        <el-menu-item index="1-2">
          <i class="el-icon-edit">编辑个人信息</i>
        </el-menu-item>
      </el-submenu>
    </el-menu>
    <el-dialog
      title="编辑个人信息"
      :visible.sync="dialogEditUserInfo"
      :before-close="handleClose"
      width="600px"
    >
      <el-card shadow="hover" style="margin-bottom: 20px;">
        <div slot="header">
          <span>更改头像</span>
        </div>

        <div class="avatar-region">
          <div>
            <img :src="avatar" class="avatar-huge">
          </div>
          <div>
            <el-button
              type="text"
              plain
              id="pick-avatar"
              style="margin-left: 20px; width:100px"
            >修改头像</el-button>
          </div>
        </div>
        <avatar-cropper
          trigger="#pick-avatar"
          @uploaded="handleUploaded"
          upload-url="/api/upload"
          upload-form-name="smfile"
        ></avatar-cropper>
        <span style="padding-left: 400px">
          <el-button type="primary" plain @click="handleConfirmEditAvatar">确认修改</el-button>
        </span>
      </el-card>
      <el-card shadow="hover">
        <div slot="header">
          <span>修改密码</span>
        </div>
        <div>
          <el-form :model="password" label-width="130px">
            <el-form-item label="旧密码">
              <el-input v-model="password.oldPassword" show-password clearable></el-input>
            </el-form-item>
            <el-form-item label="新密码">
              <el-input v-model="password.newPassword" show-password clearable></el-input>
            </el-form-item>
            <el-form-item label="再次输入密码">
              <el-input v-model="password.newPasswordConfirm" show-password clearable></el-input>
            </el-form-item>
            <p style="padding-left: 120px; color:red">{{errorMessage}}</p>
          </el-form>
        </div>
        <span style="padding-left: 400px">
          <el-button type="primary" plain @click="handleConfirmEditPassword">确认修改</el-button>
        </span>
      </el-card>
    </el-dialog>
  </div>
</template>

<script>
import { routeByPositionId } from "@/utils/path";
import AvatarCropper from "vue-avatar-cropper";
import { successDialog, failDialog } from "@/utils/notification";
import { setUserAvatar, updateUserPassword } from "@/api/user";
import { memberExpression } from "@babel/types";
import { setTimeout } from "timers";
import { validatePasswordChange } from "@/utils/validate";

export default {
  name: "StatusBar",
  components: {
    "avatar-cropper": AvatarCropper
  },
  data() {
    return {
      avatar: require("@/assets/images/avatar_default_blue.svg"),
      oldAvatar: require("@/assets/images/avatar_default_blue.svg"),
      dialogEditUserInfo: false,
      password: {
        oldPassword: "",
        newPassword: "",
        newPasswordConfirm: ""
      },
      errorMessage: ""
    };
  },
  methods: {
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then(_ => {
          done();
        })
        .catch(_ => {});
    },
    handleSelectRole(role) {
      console.log(role);
      const positionId = role.positionId;
      console.log("push to ");
      this.$router.push({ path: routeByPositionId(positionId) });
    },
    handleClickLogo() {
      this.$router.push({ path: "/home" });
    },
    handleSelect(key, keyPath) {
      if (key === "1-2") {
        this.dialogEditUserInfo = true;
      }
    },
    handleUploaded(resp) {
      if (resp.code === "success") {
        successDialog("头像上传成功");
        this.oldAvatar = this.avatar;
        this.avatar = resp.data.url;
      } else {
        failDialog("头像上传失败");
      }
    },
    handleConfirmEditAvatar() {
      var avatarInfo = {
        avatarUrl: this.avatar,
        id: this.$store.getters["user/currentUserId"]
      };
      setUserAvatar(avatarInfo).then(
        response => {
          console.log("success");
          console.log(response.data);
        },
        error => {
          failDialog("头像上传失败");
          this.avatar = this.oldAvatar;
        }
      );
    },
    routeToLogin() {
      console.log("routing to login...");
      this.$router.push({ path: "/login" });
    },
    handleConfirmEditPassword() {
      var result = validatePasswordChange(this.password);
      if (result === 1) {
        console.log("sending password");
        this.password.id = this.$store.getters["user/currentUserId"];
        updateUserPassword(this.password).then(
          response => {
            successDialog("密码设置成功，您将返回登陆界面...");
            setTimeout(this.routeToLogin, 2000);
          },
          error => {
            let responseInfo = error.data.data;
            console.log("error:");
            console.log(error.data);
            // 如果该用户ID不存在，虽然这种情况几乎不可能发生，但是还是处理下以防万一，强制返回登录页面
            if (responseInfo.code === 600) {
              this.errorMessage = "该用户不存在";
            }
            // 如果原密码输入错误
            else if (responseInfo.code === 606) {
              this.errorMessage = "原密码验证失败，请重新输入";
            }
            // 如果新密码与原密码输入相同
            else if (responseInfo.code === 600) {
              this.errorMessage = "新密码不可与原密码相同，请重新输入";
            } else {
              console.log(error);
              failDialog("设置密码出现异常:" + error);
            }
          }
        );
      } else if (result === 2) {
        this.errorMessage = "请完整输入以上输入框中信息";
        failDialog("请完整输入以上输入框中信息");
      } else if (result === 3) {
        this.errorMessage = "两次输入新密码内容不一致";
        failDialog("两次输入新密码内容不一致");
      }
    }
  },
  mounted: function() {
    if (this.$store.state.user.avatar !== "") {
      this.avatar = this.$store.state.user.avatar;
      this.oldAvatar = this.$store.state.user.avatar;
    }
  }
};
</script>

<style lang="css" scoped>
.project-logo {
  widows: 100px;
  height: 40px;
}
.avatar {
  width: 35px;
  height: 35px;
  border-radius: 100%;
}

.avatar-huge {
  width: 150px;
  height: 150px;
  border-radius: 100%;
}

.avatar-region {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-content: center;
}

.user-name {
  margin-left: 10px;
}
.align-end {
  display: flex;
  justify-content: flex-end;
  background-image: linear-gradient(to right, #00bdda 0%, #0178bc 100%);
}
.justify-start {
  margin-right: auto;
}
.el-dropdown-link {
  cursor: pointer;
  color: #fff;
}
.el-icon-arrow-down {
  font-size: 12px;
}
.demonstration {
  display: block;
  color: #8492a6;
  font-size: 14px;
  margin-bottom: 20px;
}
</style>

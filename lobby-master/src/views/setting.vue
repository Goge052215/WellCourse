<template>
  <div class="setting-wrap">
    <div class="fs40 mb30 fw600">Settings</div>
    <div class="cp mb20" @click="handleOpenInfo">Personal Data</div>
    <div class="cp mb20" @click="handleOpenPassword">Change Password</div>
    <div class="d-flex item-center mb20">
      <div class="mr96">Dark Theme</div>
      <FromSwitch v-model="theme" @change="changeTheme" />
    </div>
    <div class="cp mb20" @click="aboutDrawer = true">About</div>
    <div><span class="cp" @click="dialogVisible = true">Support & Feedback</span></div>
    <el-drawer
        title="Personal Data"
        :visible.sync="personalDrawer"
        direction="rtl">
      <div class="p20">
        <div class="d-flex item-center mb10">
          <div class="iconfont icon-youxiang"></div>
          <div class="ml2">Email</div>
        </div>
        <div class="fs18 mb20">{{this.$store.state.info.useremail}}</div>
        <div class="mb20">
          <UploadImg v-model="infoForm.headSculpture" />
        </div>
        <div class="d-flex item-center mb10">
          <div class="iconfont icon-yonghu_yonghu"></div>
          <div class="ml2">UserName</div>
        </div>
        <input class="form-input" v-model="infoForm.userName" placeholder="Please enter a username" />
        <div class="text-center mt20">
          <button class="form-button" @click="handleSaveInfo">Save</button>
        </div>
      </div>
    </el-drawer>
    <el-drawer
        title="WellCourse Platform"
        :visible.sync="aboutDrawer"
        direction="rtl">
      <div class="pl20 pr20 pb20">
        <div>
          Version: Beta 1.0<br/><br/>
          ICP No. 粤ICP备2023131243号-1<br/><br/>
          Open sourced on GitHub<br/><br/>
          Technical Support: <a href="https://github.com/Goge052215/WellCourse/tree/master" target="_blank">Click me</a><br/><br/>
          <br/><br/><br/>
          <div class="fw600">WellCourse Team</div><br/>
          All Rights Reserved, 2023<br/>
        </div>
      </div>
      <div class="pa left0 right0 bottom0 p20">A non-profit website set by George Huang and Judy Zhu</div>
    </el-drawer>
    <el-dialog
        title="Support & Feedback"
        :visible.sync="dialogVisible"
        width="500px">
      <div class="d-flex item-center mb5">
        <div class="iconfont icon-youxiang"></div>
        <div class="ml2">Email</div>
      </div>
      <input class="form-input" v-model="subject" placeholder="please enter" />
      <div class="d-flex item-center mt20 mb5">
        <div class="iconfont icon-neirongliebiao"></div>
        <div class="ml2">Content</div>
      </div>
      <textarea class="form-input" v-model="text" placeholder="please enter" />
      <span slot="footer" class="dialog-footer">
        <button class="form-button" @click="handleFeedback">Submit</button>
      </span>
    </el-dialog>
    <el-dialog
        title="Change Password"
        :visible.sync="passwordVisible"
        width="500px">
      <div class="d-flex item-center mb5">
        <div class="iconfont icon-mima"></div>
        <div class="ml2">Old PassWord</div>
      </div>
      <input type="password" class="form-input" v-model="passwordForm.oldPassword" placeholder="please enter" />
      <div class="d-flex item-center mt20 mb5">
        <div class="iconfont icon-mima"></div>
        <div class="ml2">New PassWord</div>
      </div>
      <input type="password" class="form-input" v-model="passwordForm.newPassword" placeholder="please enter" />
      <span slot="footer" class="dialog-footer">
        <button class="form-button" @click="handlePassword">Submit</button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { emailAddSuggestions, userUpdateUserInfo, userUpdateUserPassword } from '@/utils/api'
import FromSwitch from '@/components/switch.vue'

export default {
  name: 'setting',
  data () {
    return {
      theme: false,
      aboutDrawer: false,
      dialogVisible: false,
      subject: '',
      text: '',
      personalDrawer: false,
      infoForm: {
        headSculpture: '',
        userName: ''
      },
      passwordVisible: false,
      passwordForm: {
        oldPassword: '',
        newPassword: ''
      }
    }
  },
  components: {
    FromSwitch,
  },
  mounted () {
    this.theme = localStorage.getItem('theme') === 'open' ? true : false
  },
  methods: {
    changeTheme(val) {
      this.$store.commit("setTheme", val ? 'open' : 'close')
    },
    handleFeedback() {
      if(!this.subject || !this.text) {
        this.$message({
          message: 'Please fill in feedback comments',
        });
        return false
      }
      emailAddSuggestions({
        subject: this.subject,
        text: this.text,
      }).then(() => {
        this.$message.success('Feedback successful, we will handle it in a timely manner')
        this.dialogVisible = false
        this.subject = ''
        this.text = ''
      })
    },
    handleOpenInfo() {
      this.infoForm.headSculpture = this.$store.state.info.headsculpture
      this.infoForm.userName = this.$store.state.info.username
      this.personalDrawer = true
    },
    handleSaveInfo() {
      if(!this.infoForm.userName){
        this.$message.warning('Please enter a UserName')
        return
      }
      userUpdateUserInfo(this.infoForm).then(() => {
        this.$message.success('Successfully modified')
        this.$store.commit('setInfo',{
          ...this.$store.state.info,
          headsculpture: this.infoForm.headSculpture,
          username: this.infoForm.userName
        })
        this.personalDrawer = false
      })
    },
    handleOpenPassword() {
      this.passwordForm = {
        oldPassword: '',
        newPassword: ''
      }
      this.passwordVisible = true
    },
    handlePassword() {
      if(!this.passwordForm.newPassword){
        this.$message.warning('Please enter the password')
        return
      }
      userUpdateUserPassword({ passWord: this.passwordForm.newPassword }).then(() => {
        this.$message.success('Successfully modified, please log in again')
        this.passwordVisible = false
        this.$store.commit('setInfo',{})
        localStorage.removeItem('token')
        this.$router.replace({ path: '/login' })
      })

    }
  }
}
</script>
<style lang="less" scoped>
.setting-wrap{

}
</style>

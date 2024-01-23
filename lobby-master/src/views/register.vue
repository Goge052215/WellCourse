<template>
  <div class="login-wrap">
    <div class="text-center fs40 mt40 mb50">Register</div>
    <div class="text-center">
      <UploadImg v-model="form.headSculpture" />
    </div>
    <div class="d-flex item-center mb5">
      <div class="iconfont icon-yonghu_yonghu"></div>
      <div class="ml2">UserName</div>
    </div>
    <input class="form-input" v-model="form.userName" placeholder="Please enter a username" />
    <div class="d-flex item-center mb5 mt20">
      <div class="iconfont icon-youxiang"></div>
      <div class="ml2">Email</div>
    </div>
    <div class="d-flex item-center">
      <div class="flex1 mr10">
        <input class="form-input" v-model="form.userEmail" placeholder="Please enter your email address" />
      </div>
      <button class="form-button" style="width: 130px;" @click="handleCode">{{codeText}}</button>
    </div>
    <div class="d-flex item-center mb5 mt20">
      <div class="iconfont icon-yanzhengma"></div>
      <div class="ml2">Code</div>
    </div>
    <input class="form-input" maxlength="6" v-model="form.code" placeholder="Please enter your code" />
    <div class="d-flex item-center mt20 mb5">
      <div class="iconfont icon-mima"></div>
      <div class="ml2">Password</div>
    </div>
    <input type="password" class="form-input" v-model="form.passWord" placeholder="Please enter your password" />
    <div class="mt40 text-center">
      <button class="form-button" @click="handleRegister">Register</button>
    </div>
    <div class="mt10 text-center">
      <router-link to="/login">Return to login</router-link>
    </div>
  </div>
</template>

<script>
import { userRegister, userSendEmailCode } from '@/utils/api'
import { validEmail } from '@/utils/validate'

export default {
  name: 'register',
  data () {
    return {
      form: {
        code: '',
        headSculpture: '',
        passWord: '',
        userEmail: '',
        userName: ''
      },
      time: 60,
      codeText: 'Send Code'
    }
  },
  components: {
  },
  mounted () {
  },
  methods: {
    handleCode() {
      if(!validEmail(this.form.userEmail)){
        this.$message({
          message: 'Please enter the correct email address',
          type: 'warning'
        })
        return
      }
      if (this.codeText != 'Send Code') return
      this.codeText = 'loading...'
      userSendEmailCode({
        email: this.form.userEmail,
        type: 1
      }).then(res => {
        console.log(res)
        this.time--
        this.codeText = this.time + 's';
        const interval = window.setInterval(() => {
          if (this.time <= 0) {
            this.time = 60;
            this.codeText = 'Send Code';
            window.clearInterval(interval);
          } else {
            this.time--
            this.codeText = this.time + 's';
          }
        }, 1000);
      })
    },
    handleRegister() {
      if(!this.form.headSculpture){
        this.$message.warning('Please upload your avatar')
        return
      }
      if(!this.form.userName){
        this.$message.warning('Please enter your user name')
        return
      }
      if(!validEmail(this.form.userEmail)){
        this.$message({
          message: 'Please enter the correct email address',
          type: 'warning'
        })
        return
      }
      if(!this.form.code){
        this.$message.warning('Please enter the verification code')
        return
      }
      if(!this.form.passWord){
        this.$message.warning('Please enter the password')
        return
      }
      this.$store.commit("setLoading", true)
      userRegister(this.form).then(() => {
        this.$message.success('Registration successful, please log in first')
        this.$router.push({path: '/login'})
      }).finally(() => {
        this.$store.commit("setLoading", false)
      })
    }
  }
}
</script>
<style lang="less" scoped>
.login-wrap{
  width: 500px;
  margin: 0 auto;
}
</style>

<template>
  <div class="login-wrap">
    <div class="text-center fs40 mt40">Login</div>
    <div class="d-flex item-center mt50 mb5">
      <div class="iconfont icon-youxiang"></div>
      <div class="ml2">Email</div>
    </div>
    <input class="form-input" v-model="email" placeholder="please enter" />
    <div class="d-flex item-center mt50 mb5">
      <div class="iconfont icon-mima"></div>
      <div class="ml2">Password</div>
    </div>
    <input type="password" class="form-input" v-model="password" placeholder="please enter" />
    <div class="mt80 text-center">
      <button class="form-button" @click="handleLogin">Login</button>
    </div>
    <div class="mt20 text-center">
      <router-link to="/register">sign-up</router-link>
      <div class="mt5"></div>
      <router-link to="/retrieve">Forget password</router-link>
    </div>
  </div>
</template>

<script>
import { login } from '@/utils/api'

export default {
  name: 'login',
  data () {
    return {
      email: '',
      password: ''
    }
  },
  components: {
  },
  mounted () {
  },
  methods: {
    handleLogin() {
      if(!this.email || !this.password) {
        this.$message.warning('Please enter the correct email and password');
        return false
      }
      login({
        email: this.email,
        password: this.password
      }).then(res => {
        localStorage.setItem('token', res)
        this.$store.commit("setLoading", true)
        setTimeout(() => {
          this.$store.commit("setLoading", false)
          this.$router.push({path: '/'})
        },1000)
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

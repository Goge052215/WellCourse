<template>
  <div class="sidebar">
    <div class="flex1">
      <div class="cp mt25 block" @click="handleBack" v-if="$route.name && $route.name != 'home'">
        <div class="iconfont icon-fanhui mb4"></div>
        Back
      </div>
      <router-link to="/" class="mt25 block">
        <div class="iconfont icon-home-filling mb4"></div>
        Home
      </router-link>
      <router-link to="/channel" class="mt25 block">
        <div class="iconfont icon-xuexiao_kemu mb4"></div>
        Channel List
      </router-link>
      <router-link to="/userList" class="mt25 block" v-if="$store.state.info.role == 1">
        <div class="iconfont icon-yonghu-yuan mb4"></div>
        User List
      </router-link>
<!--      <router-link to="/forbiddenWords" class="mt25 block" v-if="$store.state.info.role == 1">-->
<!--        <div class="iconfont icon-yonghu-yuan mb4"></div>-->
<!--        Forbidden Words-->
<!--      </router-link>-->
      <router-link to="/article" class="mt25 block" v-else>
        <div class="iconfont icon-line-articlewenzhang mb4"></div>
        My Article
      </router-link>
      <div class="mt25 cp" @click="handleExit">
        <div class="iconfont icon-tuichu mb4"></div>
        Exit
      </div>
    </div>
    <div class="mb30">
      <router-link to="/setting">
        <div class="iconfont icon-shezhi mb4"></div>
        Settings
      </router-link>
    </div>
    <div class="">
      Powered by<br/>WellOS
    </div>
  </div>
</template>

<script>
export default {
  name: 'Sidebar',
  data () {
    return {}
  },
  components: {
  },
  mounted () {
  },
  methods: {
    handleBack() {
      this.$router.back(-1)
    },
    handleExit() {
      this.$confirm('Are you sure you want to log out?', 'Prompt', {
        confirmButtonText: 'Confirm',
        cancelButtonText: 'Cancel',
        type: 'warning'
      }).then(() => {
        localStorage.removeItem('token')
        this.$store.commit('setInfo',{})
        this.$router.push('/login')
      }).catch();
    }
  }
}
</script>

<style lang="less" scoped>
.sidebar{
  display: flex;
  flex-direction: column;
  background: #333333;
  color: #fff;
  width: 100px;
  padding: 20px 0px 10px;
  text-align: center;
  .iconfont{
    font-size: 18px;
  }
}
</style>

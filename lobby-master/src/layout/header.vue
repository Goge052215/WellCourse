<template>
  <div class="header">
    <div class="text-left"></div>
    <div class="fs16 title text-center" :style="{paddingLeft: $route.name == 'login' ? '0px' : '100px'}">Lobby</div>
    <div class="text-right d-flex item-center">
      <div class="flex1"></div>
      <template v-if="$store.state.info.useremail">
        <router-link v-if="$store.state.info.role == 2" :to="{ path: `/release`, query: $route.query }" class="release mr15">Publish</router-link>
        <router-link v-if="$store.state.info.role == 2" to="/notice">
          <div class="iconfont icon-tongzhifill mr15"></div>
        </router-link>
        <div>{{$store.state.info.username}}</div>
      </template>
    </div>
  </div>
</template>

<script>
import moment from "moment"
export default {
  name: 'Header',
  data () {
    return {}
  },
  components: {
  },
  mounted () {
  },
  methods: {
    moment,
  }
}
</script>

<style lang="less" scoped>
.header{
  height: 56px;
  background: #000;
  color: #fff;
  display: flex;
  align-items: center;
  &>div{
    flex: 1;
    padding: 0 20px;
  }
  //.title{
  //  padding-left: 100px;
  //}
  .release{
    cursor: pointer;
    background: #fff;
    border-radius: 6px;
    padding: 5px 15px;
    color: #000;
  }
  .icon-tongzhifill{
    font-size: 20px;
  }
}
</style>

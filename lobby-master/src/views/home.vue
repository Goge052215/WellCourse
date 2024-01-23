<template>
  <div class="home-wrap">
    <div class="text-center fs32 fw600 mt20">Welcome to the WellCourse Platform!</div>
    <div class="text-center mt25 fs16">Choose your way to learn, or to be creative</div>
    <div class="d-flex item-center content-center mt100">
      <router-link to="/channel" class="bg-block shadow-block item">
        <div class="iconfont icon-caidan mb15"></div>
        <div class="fs18 fw600 mb15">Channel List</div>
        <div>Select the existed channels in school which you have joined</div>
      </router-link>
      <div class="bg-block shadow-block item ml40 color-gray">
        <div class="iconfont icon-robot mb15"></div>
        <div class="fs18 fw600 mb15">Bot</div>
        <div>Stay tuned...</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'home',
  data () {
    return {
    }
  },
  components: {
  },
  mounted () {
  },
  methods: {
  }
}
</script>
<style lang="less" scoped>
.home-wrap{
  max-width: 1200px;
  margin: 0 auto;
  .item{
    display: block;
    width: 330px;
    height: 230px;
    padding: 40px 30px;
    text-align: center;
    cursor: pointer;
    .iconfont{
      font-size: 40px;
    }
  }
}
</style>
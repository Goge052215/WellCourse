<template>
  <div class="footer">
    <div></div>
    <div class="box" :class="'left'+leftNumber">ICP备案：粤ICP备2023131243号-1</div>
  </div>
</template>

<script>
export default {
  name: 'Footer',
  computed: {
    leftNumber () {
      return this.$route.name == 'login' ? 0 : 100
    }
  },
  data () {
    return {}
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
.footer{
  position: relative;
  z-index: 100;
  div{
    height: 30px;
  }
  .box{
    position: fixed;
    bottom: 0px;
    right: 0px;
    text-align: center;
    height: 30px;
    line-height: 30px;
    background: #efefef;
  }
}
.black-container{
  .footer .box{
    background: #000;
  }
}
</style>

<template>
  <div class="form-switch" :class="{'form-is-checked': checked }" @click="updateInput">
    <span class="core"></span>
  </div>
</template>

<script>
export default {
  name: 'FormSwitch',
  props: {
    checked: Boolean
  },
  model: {
    prop: "checked",
    event: "change",
  },
  methods: {
    updateInput() {
      this.$emit("change", !this.checked);
    },
  }
}
</script>
<style lang="less" scoped>
.form-switch{
  display: inline-flex;
  align-items: center;
  position: relative;
  font-size: 14px;
  line-height: 20px;
  height: 20px;
  vertical-align: middle;
  &>input{
    position: absolute;
    width: 0;
    height: 0;
    opacity: 0;
    margin: 0;
  }
  .core{
    margin: 0;
    display: inline-block;
    position: relative;
    width: 40px;
    height: 20px;
    border: 1px solid #DCDFE6;
    outline: none;
    border-radius: 10px;
    box-sizing: border-box;
    background: #DCDFE6;
    cursor: pointer;
    transition: border-color .3s, background-color .3s;
    vertical-align: middle;
    &::after{
      content: "";
      position: absolute;
      top: 1px;
      left: 1px;
      border-radius: 100%;
      transition: all .3s;
      width: 16px;
      height: 16px;
      background-color: #fff;
    }
  }
}
.form-is-checked{
  .core{
    border-color: #333333;
    background-color: #333333;
    &::after{
      left: 100%;
      margin-left: -17px;
    }
  }
}
</style>

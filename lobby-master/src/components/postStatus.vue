<template>
  <div v-if="data.status">
    <div v-if="data.status == 2">
      Disabled
      <el-popover
          placement="top-start"
          title="Reason for disabling"
          width="200"
          trigger="hover"
          :content="data.reason">
        <i slot="reference" class="el-icon-question cp"></i>
      </el-popover>
    </div>
    <div v-if="data.status == 3">Deleted</div>
  </div>
</template>

<script>
export default {
  name: 'PostStatus',
  props: {
    data: {
      type: Object,
      default: () => { return {} },
    }
  },
  methods: {
  }
}
</script>
<style lang="less" scoped>
</style>

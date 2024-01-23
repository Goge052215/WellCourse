<template>
  <div>
    <img class="user-head" v-if="url" :src="getFileUrl(url)" :style="{width: `${size}px`, height: `${size}px`}" />
    <div class="iconfont icon-yonghu-yuan" v-else :style="{fontSize: `${size}px`}"></div>
  </div>
</template>

<script>
import { getFileUrl } from '@/utils'

export default {
  name: 'UserHead',
  props: {
    url: String,
    size: {
      type: Number,
      default: 22
    }
  },
  methods: {
    getFileUrl,
  }
}
</script>
<style lang="less" scoped>
.user-head{
  border-radius: 100%;
}
</style>

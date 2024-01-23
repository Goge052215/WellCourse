<template>
  <div class="text-center">
    <span v-if="state == 'loading'">{{loadingText}}</span>
    <span v-if="state == 'noData'">{{noDataText}}</span>
    <span v-if="state == 'noMore'">{{noMoreText}}</span>
    <span v-if="state == 'getMore'" class="cp" @click="handleLoad">{{getMoreText}}</span>
  </div>
</template>

<script>
export default {
  name: 'LoadingState',
  props: {
    state: String,
    loadingText: {
      type: String,
      default: 'LOADING...',
    },
    noDataText: {
      type: String,
      default: 'No Data',
    },
    noMoreText: {
      type: String,
      default: 'No More',
    },
    getMoreText: {
      type: String,
      default: 'Click to load more',
    },
  },
  methods: {
    handleLoad() {
      this.$emit('load')
    },
  }
}
</script>
<style lang="less" scoped>
</style>

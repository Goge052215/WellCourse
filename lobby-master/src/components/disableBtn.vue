<template>
  <div>
    <div class="cp iconfont icon-jinyong" :style="{fontSize: `${size}px`}" title="Disabling" @click="handleDelete"></div>
  </div>
</template>

<script>
import { postEsUpdatePostEsStatus } from '@/utils/api'

export default {
  name: 'disableBtn',
  props: {
    postId: [String, Number],
    size: {
      type: Number,
      default: 12
    }
  },
  methods: {
    async handleDelete() {
      this.$prompt('', `Reason for disabling`, {
        confirmButtonText: 'Confirm',
        cancelButtonText: 'Cancel',
        inputType: 'textarea',
        inputPattern: /\S/,
        inputPlaceholder: 'please enter',
        inputErrorMessage: 'please enter',
        beforeClose: async(action, instance, done) => {
          if (action === 'confirm') {
            instance.confirmButtonLoading = true
            instance.confirmButtonText = 'loading...'
            await postEsUpdatePostEsStatus({
              postId: this.postId,
              reason: instance.inputValue,
            }).then(() => {
              this.$message.success('Disabled successfully!')
              this.$emit('success')
              // this.getList(this.pageNo == 1 ? 1 : this.pageNo - 1)
              done()
            }).finally(() => {
              instance.confirmButtonLoading = false
              instance.confirmButtonText = 'Confirm'
            })
          } else {
            done()
          }
        }
      }).then(() => {}).catch(() => {});
    },
  }
}
</script>
<style lang="less" scoped>
.user-head{
  border-radius: 100%;
}
</style>

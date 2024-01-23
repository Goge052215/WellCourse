<template>
  <div class="upload-img">
    <el-upload
        action
        class="avatar-uploader"
        :accept="accept"
        :show-file-list="false"
        :http-request="handleUpload">
      <img v-if="imageUrl" :src="getFileUrl(imageUrl)" class="avatar">
      <i v-else class="el-icon-plus avatar-uploader-icon"></i>
    </el-upload>
  </div>
</template>

<script>
import { upload } from '@/utils/api'
import { getFileUrl } from '@/utils'


export default {
  name: 'uploadImg',
  model: {
    prop: 'imageUrl',
    event: 'change',
  },
  props: {
    imageUrl: {
      type: String,
      default: ''
    },
    accept: {
      type: String,
      default: 'image/gif,image/jpeg,image/jpg,image/png'
    },
  },
  data() {
    return {
      getFileUrl,
    }
  },
  methods: {
    handleUpload(params) {
      const file = params.file;
      const form = new FormData();
      form.append('file', file);
      upload(form).then(res => {
        this.$emit('change', res.visitUrl);
      })
    },
  }
}
</script>
<style lang="less" scoped>
.upload-img {
  /deep/ .avatar-uploader .el-upload {
    border-radius: 100%;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    background: #fff;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 120px;
    height: 120px;
    line-height: 120px;
    text-align: center;
  }
  .avatar {
    width: 120px;
    height: 120px;
    display: block;
  }
}
</style>

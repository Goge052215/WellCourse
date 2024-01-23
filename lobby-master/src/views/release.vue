<template>
  <div class="release-wrap">
    <div v-if="step == 1">
      <div class="fs40 mb30 fw600">{{title}}</div>
      <div class="d-flex">
        <div class="flex1 mr90">
          <div class="bg-block shadow-block mb20 p20">
            <el-select class="mr20" v-model="schoolId" clearable placeholder="Please select a school" @change="changeSchool">
              <el-option
                  v-for="item in schoolList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
              </el-option>
            </el-select>
            <el-select v-model="disciplineId" clearable placeholder="Please select a subject" @change="changeDiscipline">
              <el-option
                  v-for="item in disciplineList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
              </el-option>
            </el-select>
          </div>
          <div class="bg-block shadow-block p10">
            <div class="mt10 mb10"><input class="form-input" v-model="postTitle" placeholder="Please enter a title" /></div>
            <div class="pr z10"><Wangeditor :content="content" @change="(val) => { content = val }" /></div>
            <div class="pr z1">
              <el-upload
                  ref="uploadFile"
                  class="upload-file"
                  drag
                  action=""
                  :file-list="fileList"
                  :http-request="handleUpload"
                  multiple>
                <i class="el-icon-upload"></i>
                <div class="el-upload__text">Drag the attachment here, or <em>Click to upload</em></div>
              </el-upload>
            </div>
          </div>
        </div>
        <div class="pr">
          <div class="pa left0 fs16" style="top: -40px;">plugin selection</div>
          <div class="bg-block shadow-block mb20 p20">
            <el-select v-model="plugId" clearable placeholder="Please select a component category" @change="changeFirstPlug">
              <el-option
                  v-for="item in plugList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
              </el-option>
            </el-select>
            <el-select class="ml20" v-model="plugParentId" clearable placeholder="Please select a component" @change="changePlugParent">
              <el-option
                  v-for="item in plugParentList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
              </el-option>
            </el-select>
          </div>
          <div class="right-box">
            <div class="bg-block shadow-block plug" v-if="plugParentId && iframeUrl" :style="{width: `${dragWidth}px`, height: '600px'}">
              <div class="drag iconfont icon-tuozhuai" ref="drag" @mousedown="handleMouseDown"></div>
              <div class="drag-box" v-if="dragFlag"></div>
              <iframe :src="iframeUrl" width="100%" height="600" allowfullscreen></iframe>
            </div>
          </div>
        </div>
      </div>
      <div class="text-center mt20">
        <button class="form-button" @click="handleRelease">Release</button>
      </div>
    </div>
    <div class="text-center mt90" v-if="step == 2">
      <div class="iconfont icon-roundcheckfill" style="font-size: 80px;"></div>
      <div class="mt30 mb30 fs30">Successfully published</div>
      <div class="">
        <router-link :to="`/detail?id=${stepId}`" class="form-button inline-block mr20" @click="handleRelease">Go check</router-link>
        <router-link to="/article" class="form-button inline-block" @click="handleRelease">Enter my article</router-link>
      </div>
    </div>
  </div>
</template>

<script>
import { schoolListPage, disciplineListPage, postEsAddPostEs, plugFirstPlugList, plugSecondPlugList, postEsUpdatePostEs, postEsPostEs, upload } from '@/utils/api'
import Wangeditor from '@/components/wangeditor.vue'
import { getListItem } from '@/utils'

export default {
  name: 'release',
  data () {
    return {
      step: 1,
      stepId: '',
      title: 'create a new note',
      detail: {},
      dragFlag: false,
      dragWidthDefault: 600,
      dragWidth: 600,

      attachmentAddress: '',
      postTitle: '',
      content: '',
      schoolId: '',
      schoolList: [],
      disciplineId: '',
      disciplineList: [],
      plugId: '',
      plugList: [],
      plugParentId: '',
      plugParentList: [],
      iframeUrl: '',
      fileList: []
    }
  },
  components: {
    Wangeditor
  },
  mounted () {
    this.$route.query.id && this.getDetail()
    if(this.$route.query.schoolId) {
      this.schoolId = Number(this.$route.query.schoolId)
      this.changeSchool();
    }
    if(this.$route.query.disciplineId) {
      this.disciplineId = Number(this.$route.query.disciplineId)
      this.changeDiscipline()
    }
    this.getSchool()
  },
  beforeDestroy() {
    this.content = ''
  },
  methods: {
    handleUpload(params) {
      const file = params.file;
      const form = new FormData();
      form.append('file', file);
      upload(form).then(res => {
        this.fileList.push({
          name: file.name,
          url: res.visitUrl
        })
      }).catch(() => {
        let uid = file.uid
        let idx = this.$refs.uploadFile.uploadFiles.findIndex(item => item.uid === uid)
        this.$refs.uploadFile.uploadFiles.splice(idx, 1)
      })
    },
    getDetail() {
      this.title = 'edit a note'
      postEsPostEs({id: this.$route.query.id}).then(async res => {
        this.detail = res
        this.fileList = res.postes.attachmentAddress ? JSON.parse(res.postes.attachmentAddress) : []
        this.postTitle = res.postes.postTitle
        this.postTitle = res.postes.postTitle
        res.postes.content = res.postes.content.replaceAll('<img src="', `<img src="${process.env.VUE_APP_API_BASE_IMG_URL}`)
        this.content = res.postes.content
        if (res.postes.schoolId) {
          this.schoolId = res.postes.schoolId
          await disciplineListPage({
            schoolId: this.schoolId,
            pageNo: 1,
            pageSize: 100
          }).then(res => {
            this.disciplineList = res.rows
          })
        }
        if (res.postes.disciplineId) {
          this.disciplineId = res.postes.disciplineId
          await plugFirstPlugList({
            discipineId: this.disciplineId
          }).then(res => {
            this.plugList = res
          })
        }
        if (res.postes.plugId) {
          this.plugId = res.postes.plugId
          await plugSecondPlugList({
            parentId: this.plugId
          }).then(res => {
            this.plugParentList = res
          })
        }
        if (res.postes.plugParentId) {
          this.plugParentId = res.postes.plugParentId
          this.changePlugParent()
        }
      })
    },
    handleMouseDown(event) {
      this.dragFlag = true
      let startx = event.clientX;
      document.onmousemove = ev => {
        if (this.dragFlag) {
          let mouseMoveX = startx - ev.clientX
          if (mouseMoveX > 0) {
            this.dragWidth = this.dragWidthDefault + mouseMoveX
          }
          if(mouseMoveX < 0 && this.dragWidth > 600) {
            this.dragWidth = this.dragWidthDefault - -mouseMoveX
          }
        }
      }
      document.onmouseup = () => {
        this.dragWidthDefault = this.dragWidth
        this.dragFlag = false
        document.onmousemove = null
        document.onmouseup = null
      }
      // 解决拖拽过程中鼠标抬起仍旧处于拖拽状态问题
      document.ondragstart = function(ev) {
        ev.preventDefault();
      };
      document.ondragend = function(ev) {
        ev.preventDefault();
      };
    },
    getSchool() {
      schoolListPage({
        pageNo: 1,
        pageSize: 100
      }).then(res => {
        this.schoolList = res.rows
      })
    },
    changeSchool() {
      this.disciplineId = ''
      this.disciplineList = []
      this.plugId = ''
      this.plugList = []
      this.plugParentId = ''
      this.plugParentList = []
      if (!this.schoolId) return
      disciplineListPage({
        schoolId: this.schoolId,
        pageNo: 1,
        pageSize: 100
      }).then(res => {
        this.disciplineList = res.rows
      })
    },
    changeDiscipline() {
      this.plugId = ''
      this.plugList = []
      this.plugParentId = ''
      this.plugParentList = []
      if (!this.disciplineId) return
      plugFirstPlugList({
        discipineId: this.disciplineId
      }).then(res => {
        this.plugList = res
      })
    },
    changeFirstPlug() {
      this.plugParentId = ''
      this.plugParentList = []
      if (!this.plugId) return
      plugSecondPlugList({
        parentId: this.plugId
      }).then(res => {
        this.plugParentList = res
      })
    },
    changePlugParent() {
      let item = getListItem(this.plugParentId, this.plugParentList)
      this.iframeUrl = item.code
    },
    async handleRelease() {
      const postData = {
        postTitle: this.postTitle,
        content: this.content.replaceAll(process.env.VUE_APP_API_BASE_IMG_URL,''),
        disciplineId: this.disciplineId,
        plugId: this.plugId,
        plugParentId: this.plugParentId,
        attachmentAddress: JSON.stringify(this.fileList),
        postId: this.detail.postes ? this.detail.postes.postId : undefined,
        id: this.detail.postes ? this.detail.postes.id : undefined
      }
      if(this.$badwordsFilter.isProfane(postData.postTitle)){
        this.$message.warning('The title cannot have sensitive words');
        return false
      }
      if(this.$badwordsFilter.isProfane(postData.content)){
        this.$message.warning('Content cannot contain sensitive words');
        return false
      }
      const res = postData.id ? await postEsUpdatePostEs(postData) : await postEsAddPostEs(postData)
      if(res) {
        this.stepId = res
        this.step = 2
      }
    },
  }
}
</script>
<style lang="less" scoped>
.release-wrap{
  .iconfont{
    font-size: 22px;
  }
  .content{
    min-height: 400px;
  }
  .comment{
    padding: 20px;
  }
  .form-input{
    border: none;
  }
  .right-box{
    width: 600px;
    min-width: 600px;
    position: relative;
    z-index: 10;
    .plug{
      position: absolute;
      right: 0;
      top: 0;
      iframe{
        border-radius: 10px;
      }
      .drag-box{
        position: absolute;
        left: 0;
        top: 0;
        bottom: 0;
        right: 0;
        z-index: 20;
      }
      .drag{
        cursor: w-resize;
        z-index: 20;
        position: absolute;
        left: -20px;
        top: 50%;
        font-size: 20px;
      }
    }
  }
  /deep/ .upload-file{
    .el-upload{
      width: 100%;
      .el-upload-dragger{
        width: 100%;
      }
    }
  }
}
</style>
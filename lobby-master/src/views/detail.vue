<template>
  <div class="detail-wrap" v-if="detail.postes">
    <div class="fs40 mb30 fw600">{{detail.postes.disciplineName}}</div>
    <div class="d-flex">
      <div class="flex1 mr90">
        <div class="d-flex item-center mb22">
          <UserHead :url="detail.postes.headsculpture" />
          <div class="fs18 ml10">{{detail.postes.userName}}</div>
        </div>
        <div class="bg-block shadow-block content mb35 p20">
          <div class="mb20 d-flex item-center">
            <div class="fs16 fw600 flex1 mr10">{{detail.postes.postTitle}}</div>
            <template v-if="detail.postes.userEmail == $store.state.info.useremail">
              <router-link :to="`/release?id=${detail.postes.id}`" title="Edit" class="iconfont icon-post mr10" style="font-size: 20px;"></router-link>
              <div class="cp iconfont icon-delete" title="Delete" style="font-size: 20px;" @click="handleDelete(detail.postes.id)"></div>
            </template>
          </div>
<!--          <div style="max-height: 500px;overflow-y: auto;" v-html="detail.postes.content"></div>-->
          <div style="max-height: 500px;overflow-y: auto;" class="pr z10">
            <Editor
                v-model="content"
                :defaultConfig="{readOnly: true}"
                mode="default"
                @onCreated="onCreated"
            />
          </div>
          <div class="mt20">
            <span class="file-tag" v-for="item in detail.postes.attachmentAddress" :key="item.uid" @click="openFile(item)">{{item.name}}</span>
          </div>
          <div class="d-flex item-center mt20">
            <div class="mr10">{{detail.postes.createTime}}</div>
            <PostStatus :data="detail.postes" />
            <div class="flex1"></div>
            <DisableBtn v-if="$store.state.info.role == 1 && detail.postes.status == 1" :postId="detail.postes.postId" :size="14" class="mr20" @success="getDetail" />
            <div @click="handleCancel(2)" class="cp iconfont icon-follow-fill mr5" v-if="detail.collect == 1"></div>
            <div @click="handleAdd(2)" class="cp iconfont icon-follow mr5" v-else></div>
            <div class="mr20">{{detail.postes.numberOfLikes}}</div>
            <div @click="handleCancel(1)" class="cp iconfont icon-appreciate_fill_light mr5" v-if="detail.like == 1"></div>
            <div @click="handleAdd(1)" class="cp iconfont icon-appreciate mr5" v-else></div>
            <div>{{detail.postes.followQuantity}}</div>
          </div>
        </div>
        <div v-for="(item, index) in list" :key="item.id">
          <div class="d-flex item-center mb22">
            <UserHead :url="item.headsculpture" />
            <div class="fs18 ml10">{{item.username}}</div>
          </div>
          <div class="bg-block shadow-block comment mb35">
            <div>{{item.content}}</div>
            <div class="d-flex item-center mt10">
              <div class="fs12">{{ item.createTime | dateFilter }}</div>
              <div class="iconfont icon-delete ml10 cp" v-if="item.userid == $store.state.info.id" @click="handleDeleteComment(list, index)"></div>
              <div class="iconfont icon-comment ml10 cp" @click="handleShowComment(item)"></div>
            </div>
            <div class="pt20" v-if="item.commentShow">
              <textarea class="comment-input" :placeholder="item.commentPlaceholder" v-model="item.commentText" />
              <div class="d-flex item-center">
                <div class="flex1"></div>
                <button @click="handleItemComment(item)" class="form-button" style="height: 40px;line-height: 40px;">Comment</button>
              </div>
            </div>
            <div class="mt20" v-for="(item1,index1) in item.childer" :key="item1.id">
              <div class="d-flex item-center mb10">
                <UserHead :url="item1.headsculpture" />
                <div class="fs18 ml10">{{item1.username}}</div>
              </div>
              <div class="pl32 pr32">
                <div>{{item1.content}}</div>
                <div class="d-flex item-center mt5">
                  <div class="fs12">{{ item1.createTime | dateFilter }}</div>
                  <div class="iconfont icon-delete ml10 cp" v-if="item1.userid == $store.state.info.id" @click="handleDeleteComment(item.childer, index1)"></div>
                </div>
              </div>
            </div>
            <LoadingState v-if="item.state != 'noData'" :state="item.state" @load="getSecondComment(item.id, index)" />
          </div>
        </div>
        <div class="bg-block shadow-block p20">
          <textarea class="comment-input" placeholder="Please enter the comment content" v-model="commentText" />
          <div class="d-flex item-center">
            <div class="flex1"></div>
            <button @click="handleAddComment" class="form-button" style="height: 40px;line-height: 40px;">Comment</button>
          </div>
        </div>
      </div>
      <div class="right-box">
        <div class="bg-block shadow-block plug" :style="{width: `${dragWidth}px`, height: '600px'}">
          <div class="drag iconfont icon-tuozhuai" ref="drag" @mousedown="handleMouseDown"></div>
          <div class="drag-box" v-if="dragFlag"></div>
          <iframe ref="iframe" src="" width="100%" height="600" allowfullscreen></iframe>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { postEsPostEs, likecollectionCancellation, likecollectionIncrease, commentAddComment, commentFirstComment, plugSecondPlugList, postEsDeletePostEs, commentSecondComment, commentDelComment } from '@/utils/api'
import { getFileUrl } from '@/utils'
import { getListItem } from '@/utils'
import { Editor } from '@wangeditor/editor-for-vue'
// 1正常2禁用3已删除
export default {
  name: 'detail',
  data () {
    return {
      editor: null,
      getFileUrl,
      content: '',
      detail: {},
      commentText: '',
      total: 0,
      pageNo: 1,
      pageSize: 100,
      list: [],
      dragFlag: false,
      dragWidthDefault: 600,
      dragWidth: 600,
    }
  },
  components: {
    Editor
  },
  mounted () {
    // if (this.editor.getAllMenuKeys()?.includes('insertFormula')) {
    //   Boot.registerModule(formulaModule)
    // }
    if (this.$route.query.id || this.$route.query.postId) {
      this.getDetail()
    }
  },
  beforeDestroy() {
    const editor = this.editor
    if (editor == null) return
    editor.destroy() // 组件销毁时，及时销毁编辑器
  },
  methods: {
    onCreated(editor) {
      this.editor = Object.seal(editor)
    },
    handleDelete(id) {
      this.$confirm('Are you sure you want to delete it?', 'Prompt', {
        confirmButtonText: 'Confirm',
        cancelButtonText: 'Cancel',
        type: 'warning'
      }).then(async () => {
        await postEsDeletePostEs({id: id}).then(() => {
          this.$message.success('Successfully deleted!')
          this.$router.back(-1)
        });
      }).catch(() => {});
    },
    openFile(item) {
      window.open(getFileUrl(item.url))
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
    getDetail() {
      postEsPostEs({
        id: this.$route.query.id,
        postId: this.$route.query.postId
      }).then(res => {
        res.postes.attachmentAddress = res.postes.attachmentAddress ? JSON.parse(res.postes.attachmentAddress) : []
        res.postes.content = res.postes.content.replaceAll('<img src="', `<img src="${process.env.VUE_APP_API_BASE_IMG_URL}`)
        this.detail = res
        this.content = res.postes.content
        this.getFirstComment()
        if(res.postes.plugId) {
          plugSecondPlugList({
            parentId: res.postes.plugId
          }).then(res1 => {
            this.$nextTick(() => {
              let item = getListItem(res.postes.plugParentId, res1)
              this.$refs.iframe.contentWindow.location.replace(item.code)
            })
          })
        }
      })
    },
    // 1点赞2收藏
    handleCancel(type) {
      likecollectionCancellation({
        postId: this.detail.postes.postId,
        type: type
      }).then(() => {
        if(type == 1) {
          this.detail.like = 0
          this.detail.postes.followQuantity--
        }
        if(type == 2) {
          this.detail.collect = 0
          this.detail.postes.numberOfLikes--
        }
      })
    },
    handleAdd(type) {
      likecollectionIncrease({
        postId: this.detail.postes.postId,
        type: type
      }).then(() => {
        if(type == 1) {
          this.detail.like = 1
          this.detail.postes.followQuantity++
        }
        if(type == 2) {
          this.detail.collect = 1
          this.detail.postes.numberOfLikes++
        }
      })
    },
    handleShowComment(item) {
      item.commentShow = !item.commentShow
      item.commentPlaceholder = `comment ${item.username}`
    },
    handleAddComment() {
      if(!this.commentText){
        this.$message.warning('Please enter the comment content')
        return
      }
      if(this.$badwordsFilter.isProfane(this.commentText)){
        this.$message.warning('Content cannot contain sensitive words');
        return false
      }
      commentAddComment({
        postId: this.detail.postes.postId,
        content: this.commentText,
        parentId: 0
      }).then(res => {
        this.commentText = ''
        this.list.push({
          ...res,
          commentText: '',
          commentShow: false,
          commentPlaceholder: '',
          childer: [],
          state: 'getMore'
        })
      })
    },
    handleItemComment(item) {
      if(!item.commentText){
        this.$message.warning('Please enter the comment content')
        return
      }
      if(this.$badwordsFilter.isProfane(item.commentText)){
        this.$message.warning('Content cannot contain sensitive words');
        return false
      }
      commentAddComment({
        postId: this.detail.postes.postId,
        content: item.commentText,
        parentId: item.id
      }).then(res => {
        item.commentText = ''
        item.commentShow = false
        item.childer.push(res)
      })
    },
    handleDeleteComment(list, index) {
      this.$confirm('Are you sure you want to delete the comment?', 'Prompt', {
        confirmButtonText: 'Confirm',
        cancelButtonText: 'Cancel',
        type: 'warning'
      }).then(async () => {
        await commentDelComment({commentId: list[index].id}).then(() => {
          this.$message.success('Successfully deleted!')
          console.log(list, index)
          list.splice(index, 1)
        });
      }).catch(() => {});
    },
    getFirstComment() {
      commentFirstComment({
        postId: this.detail.postes.postId,
        pageNo: this.pageNo,
        pageSize: this.pageSize
      }).then(res => {
        this.list = res.rows.map(item => {
          item.commentText = ''
          item.commentShow = false
          item.commentPlaceholder = ''
          item.childer = []
          item.state = 'getMore'
          return item
        })
        if(this.list.length > 0) {
          this.getSecondComment(this.list[0].id, 0)
        }
      })
    },
    getSecondComment(id, index) {
      this.list[index].state = 'loading'
      commentSecondComment({
        firstCommentId: id,
        pageNo: this.pageNo,
        pageSize: this.pageSize
      }).then(res1 => {
        if(res1.length == 0) {
          this.list[index].state = 'noData'
        } else {
          this.list[index].state = 'noMore'
        }
        this.list[index].childer = res1
      })
    }
  }
}
</script>
<style lang="less" scoped>
.black-container{
  .detail-wrap{
    /deep/.w-e-text-container{
      border-radius: 10px;
      padding: 10px;
    }
  }
}
.detail-wrap{
  ul,ol{
    margin-left: 20px;
  }
  textarea.form-input{
    resize: none;
    height: 150px;
    padding: 10px;
    line-height: normal;
    font-size: 14px;
    &::-webkit-input-placeholder{
      color: #8f9193;
    }
  }
  .content{
  }
  .comment{
    padding: 20px;
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
}
</style>
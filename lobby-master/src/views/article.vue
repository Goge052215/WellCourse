<template>
  <div class="article-wrap">
    <div class="fs40 mb30 fw600">My Article</div>
    <div class="bg-block shadow-block item" v-for="item in list" :key="item.id">
      <div class="d-flex item-center">
        <router-link :to="`/detail?id=${item.id}&discipline=${$route.query.discipline}`" class="fs20 fw600">{{item.postTitle}}</router-link>
        <div class="operate-btn">
          <div class="d-flex item-center ml20">
            <router-link :to="`/release?id=${item.id}`" title="Edit" class="d-flex item-center cp mr10">
              <div class="iconfont icon-post"></div>
            </router-link>
            <div class="cp iconfont icon-delete" title="Delete" @click="handleDelete(item)"></div>
          </div>
        </div>
      </div>
      <div class="d-flex item-center mt10">
        <div class="mr20">Affiliation：{{item.disciplineName}}</div>
        <div class="mr20">createDate：{{item.createTime}}</div>
        <PostStatus class="mr20" :data="item" />
        <div class="iconfont icon-comment mr5"></div>
        <div class="mr20">{{item.commentNum}}</div>
        <div class="iconfont icon-follow mr5"></div>
        <div class="mr20">{{item.numberOfLikes}}</div>
        <div class="iconfont icon-appreciate mr5"></div>
        <div class="mr20">{{item.followQuantity}}</div>
      </div>
    </div>
    <LoadingState :state="state" @load="getList" />
  </div>
</template>

<script>
import { postEsMyPostEsPage, postEsDeletePostEs } from '@/utils/api'

export default {
  name: 'article',
  data () {
    return {
      state: '',
      total: 0,
      pageNo: 1,
      pageSize: 10,
      list: [],
    }
  },
  components: {
  },
  mounted () {
    this.getList(1)
  },
  methods: {
    getList(pageNo) {
      if(pageNo) {
        this.state = ''
        this.pageNo = pageNo
      }
      if(this.state === 'loading') return
      this.state = 'loading'
      this.$store.commit("setLoading", true)
      this.list = []
      postEsMyPostEsPage({
        pageNo: 1,
        pageSize: 10
      }).then(res => {
        this.pageNo++
        this.total = res.total
        res.rows.map(item => {
          this.list.push(item)
        })
        if (res.total === 0) {
          this.state = 'noData'
        } else if (this.pageNo > res.pages){
          this.state = 'noMore'
        } else {
          this.state = 'getMore'
        }
      }).finally(() => {
        this.$store.commit("setLoading", false)
      })
    },
    handleDelete(item) {
      this.$confirm('Are you sure you want to delete it?', 'Prompt', {
        confirmButtonText: 'Confirm',
        cancelButtonText: 'Cancel',
        type: 'warning'
      }).then(async () => {
        await postEsDeletePostEs({id: item.id}).then(() => {
          this.$message.success('Successfully deleted!')
          this.getList(this.pageNo == 1 ? 1 : this.pageNo - 1)
        });
      }).catch(() => {});
    }
  }
}
</script>
<style lang="less" scoped>
.article-wrap{
  //max-width: 1000px;
  .tabs{
    display: flex;
    border-bottom: #000 1px solid;
    .tabs-item{
      padding: 0 40px;
      height: 40px;
      line-height: 40px;
      background: #fff;
      border-right: #000 1px solid;
      border-top: #000 1px solid;
      cursor: pointer;
      color: #999;
      font-size: 14px;
      &:first-child{
        border-left: #000 1px solid;
      }
    }
    .tabs-item-active{
      color: #000;
    }
  }
  .item{
    display: block;
    margin-bottom: 20px;
    padding: 10px;
    border-radius: 5px;
    .iconfont{
      font-size: 20px;
    }
    .operate-btn{
      display: none;
    }
    &:hover{
      .operate-btn{
        display: block;
      }
    }
  }
}
.black-container{
  .tabs {
    .tabs-item {
      background: #333333;
    }
    .tabs-item-active{
      color: #fff;
    }
  }
}
</style>
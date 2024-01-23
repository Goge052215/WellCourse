<template>
  <div class="article-wrap">
    <div class="fs40 mb30 fw600">{{$route.query.discipline}}</div>
    <div class="mb20">
      <input class="form-input" v-model="postTitle" placeholder="Search content" @keyup.enter="handleSearch" />
    </div>
    <div class="tabs mb20">
      <div class="tabs-item" v-for="item in tabList" :key="item.key" @click="handleTabs(item.key)" :class="{'tabs-item-active': curTab == item.key}">{{item.title}}</div>
    </div>
    <router-link :to="`/detail?id=${item.id}`" class="bg-block shadow-block item" v-for="item in list" :key="item.id">
      <div class="fs20 fw600 mb10">{{item.postTitle}}</div>
      <div class="d-flex item-center">
        <UserHead :url="item.headsculpture" />
        <div class="ml5 mr20">{{item.userName}}</div>
        <div class="iconfont icon-comment mr5"></div>
        <div class="mr20">{{item.commentNum}}</div>
        <div class="iconfont icon-follow mr5"></div>
        <div class="mr20">{{item.numberOfLikes}}</div>
        <div class="iconfont icon-appreciate mr5"></div>
        <div>{{item.followQuantity}}</div>
        <div class="ml20">createDate：{{item.createTime}}</div>
      </div>
    </router-link>
    <LoadingState :state="total == 0 ? 'noData' : ''" />
    <div class="text-right">
      <el-pagination
          :hide-on-single-page="true"
          background
          layout="total, sizes, prev, pager, next, jumper"
          :page-sizes="[20, 50, 100, 200]"
          @size-change="handleSize"
          @current-change="handleCurrent"
          :current-page="pageNo"
          :page-size="pageSize"
          :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
import { postEsPostEsPage } from '@/utils/api'

export default {
  name: 'articleList',
  data () {
    return {
      curTab: 'all',
      tabList: [
        { title: 'All', key: 'all' },
        { title: 'Like', key: 'like' },
        { title: 'Collect', key: 'collect' },
        { title: 'Comment', key: 'comment' },
      ],
      loading: false,
      search: '',
      total: 0,
      pageNo: 1,
      pageSize: 20,
      list: [],
      postTitle: ''
    }
  },
  components: {
  },
  mounted () {
    this.getList()
  },
  methods: {
    handleSize(val) {
      this.pageSize = val
      this.getList()
    },
    handleCurrent(val) {
      this.pageNo = val
      this.getList()
    },
    getList(type) {
      this.$store.commit("setLoading", true)
      if(type == 'reset') this.pageNo = 1
      let postData = {
        title: this.postTitle,
        disciplineId: this.$route.query.disciplineId,
        pageNo: this.pageNo,
        pageSize: this.pageSize
      }
      switch (this.curTab) {
        case 'like':
          postData.followQuantitySort = 'desc'
          break
        case 'collect':
          postData.numberOfLikesSort = 'desc'
          break
        case 'comment':
          postData.commentNumSort = 'desc'
          break
        default:
          break
      }
      postEsPostEsPage(postData).then(res => {
        this.total = res.total
        this.list = res.rows
      }).finally(() => {
        this.$store.commit("setLoading", false)
      })
    },
    handleSearch() {
      this.getList('reset')
    },
    handleTabs(key) {
      this.curTab = key
      this.handleSearch()
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
      text-align: center;
      flex: 1;
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
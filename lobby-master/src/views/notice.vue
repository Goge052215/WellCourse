<template>
  <div class="article-wrap">
    <div class="fs40 mb30 fw600">Notify</div>
    <div class="tabs mb20">
      <div class="tabs-item" v-for="item in tabList" :key="item.key" @click="handleTabs(item.key)" :class="{'tabs-item-active': curTab == item.key}">{{item.title}}</div>
    </div>
    <div class="bg-block shadow-block item" v-for="item in list" :key="item.id" @click="handleItem(item)">
      <template v-if="curTab == 3">
        <div class="d-flex item-center">
          <UserHead :url="item.headsculpture" :size="40" />
          <div class="ml10">
            <div class="d-flex item-center mb5">
              <div>{{item.username}}</div>
              <div class="ml5">comment your</div>
              <router-link :to="`/detail?postId=${item.postId}`" class="ml4 fw600">《{{item.postTitle}}》</router-link>
            </div>
            <div class="mb5">{{item.content}}</div>
            <div class="fs12">{{item.createTime | dateFilter}}</div>
          </div>
        </div>
      </template>
      <template v-if="[1,2].includes(curTab)">
        <div class="d-flex item-center">
          <UserHead :url="item.headsculpture" :size="40" />
          <div class="ml10">
            <div class="d-flex item-center mb2">
              <div>{{item.username}}</div>
              <div class="ml5">{{curTab == 1 ? 'like your' : 'collect your'}}</div>
              <router-link :to="`/detail?postId=${item.postId}`" class="ml4 fw600">《{{item.postTitle}}》</router-link>
            </div>
            <div class="fs12">{{item.createTime | dateFilter}}</div>
          </div>
        </div>
      </template>
      <template v-if="curTab == 4">
        <div class="mb2">{{item.username}}禁用了<router-link :to="`/detail?postId=${item.postId}`" class="ml4 fw600">《{{item.postTitle}}》</router-link>The reason is：{{item.content}}</div>
        <div class="fs12">{{item.createTime | dateFilter}}</div>
      </template>
    </div>
    <div class="text-center">
      <LoadingState :state="state" @load="getList" />
    </div>
  </div>
</template>

<script>
import { messageMessageList, messageReadMessage } from '@/utils/api'

export default {
  name: 'notice',
  data () {
    return {
      curTab: 4,
      tabList: [
        { title: 'System', key: 4 },
        { title: 'Comment', key: 3 },
        { title: 'Like', key: 1 },
        { title: 'Collect', key: 2 },
      ],
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
    this.getList('reset')
  },
  methods: {
    handleItem(item) {
      if(item.readstatus == 0) {
        messageReadMessage({id: item.id}).then(() => {
          item.readstatus = 1
        })
      }
    },
    getList(type) {
      if(type == 'reset') {
        this.state = ''
        this.list = []
        this.pageNo = 1
      }
      if(this.state === 'loading') return
      this.state = 'loading'
      this.$store.commit("setLoading", true)
      messageMessageList({
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        type: this.curTab
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
    handleTabs(key) {
      this.curTab = key
      this.getList('reset')
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
      flex: 1;
      text-align: center;
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
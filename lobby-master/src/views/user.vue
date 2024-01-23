<template>
  <div class="user-wrap" v-if="userData.id">
    <div class="fs20 mb30 fw600">User Info</div>
    <div class="bg-block shadow-block p20 d-flex item-center mb20">
      <UserHead :url="userData.headsculpture" :size="40" />
      <div class="ml10">
        <div class="fs18 fw600 mb5">{{userData.username}}</div>
        <div class="fs16">{{userData.useremail}}</div>
      </div>
    </div>
    <div class="fs20 mb20 fw600">All Articles</div>
    <div class="bg-block shadow-block item" v-for="item in list" :key="item.id">
      <div class="d-flex item-center">
        <router-link :to="`/detail?id=${item.id}&discipline=${$route.query.discipline}`" class="fs20 fw600">{{item.postTitle}}</router-link>
        <div class="operate-btn ml20">
          <DisableBtn v-if="$store.state.info.role == 1 && item.status == 1" :postId="item.postId" :size="20" @success="getList(pageNo == 1 ? 1 : pageNo - 1)" />
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
import { postEsPostEsPageByUser, userUserInfoByEmail } from '@/utils/api'

export default {
  name: 'user',
  data () {
    return {
      userData: {},
      state: '',
      total: 0,
      pageNo: 1,
      pageSize: 10,
      list: []
    }
  },
  components: {
  },
  mounted () {
    if(this.$route.params.id){
      this.getUser()
      this.getList(1)
    }
  },
  methods: {
    getUser() {
      userUserInfoByEmail({userEmail: this.$route.params.id}).then(res => {
        this.userData = res
      })
    },
    getList(pageNo) {
      if(pageNo) {
        this.state = ''
        this.pageNo = pageNo
      }
      if(this.state === 'loading') return
      this.state = 'loading'
      this.$store.commit("setLoading", true)
      this.list = []
      postEsPostEsPageByUser({
        userEmail: this.$route.params.id,
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
  }
}
</script>
<style lang="less" scoped>
.user-wrap{
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
</style>
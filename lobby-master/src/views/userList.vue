<template>
  <div class="channel-wrap">
    <div class="fs40 mb30 fw600">IB Physics Channel User List</div>
    <div class="d-flex">
      <div style="width: 400px;min-width: 400px;">
        <div>User Name:</div>
        <router-link :to="`/user/${item.useremail}`" class="d-flex item-center mt20 cp" v-for="item in list" :key="item.id">
          <UserHead :url="item.headsculpture" :size="30" />
          <div class="ml10">{{item.username}}</div>
        </router-link>
        <LoadingState :state="state" @load="getList" />
      </div>
      <div class="school">
        <div>Position</div>
        <div v-for="item in adminList" :key="item.id" class="mt20">{{item.username}}</div>
      </div>
    </div>
  </div>
</template>

<script>
import { userUserPage } from '@/utils/api'

export default {
  name: 'userList',
  data () {
    return {
      state: '',
      total: 0,
      pageNo: 1,
      pageSize: 10,
      list: [],
      adminList: []
    }
  },
  components: {
  },
  mounted () {
    this.getList(1)
    this.getAdmin()
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
      userUserPage({
        role: 2,
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
    getAdmin() {
      this.$store.commit("setLoading", true)
      this.adminList = []
      userUserPage({
        role: 1,
        pageNo: 1,
        pageSize: 100
      }).then(res => {
        this.adminList = res.rows
      }).finally(() => {
        this.$store.commit("setLoading", false)
      })
    },
  }
}
</script>
<style lang="less" scoped>
.channel-wrap{
  .school{
    width: 400px;
    min-width: 400px;
  }
}
</style>
<template>
  <div class="channel-wrap">
    <div class="fs40 mb30 fw600">Channel List</div>
    <div class="d-flex">
      <div class="flex1 d-flex" v-loading="disciplineLoading">
        <div>
          <router-link :to="`/articleList?disciplineId=${item.id}&schoolId=${school}`" class="d-flex item-center mt20 cp" v-for="item in disciplineList" :key="item.id">
            <div class="iconfont" :class="item.icon"></div>
            <div class="ml10">{{item.name}}</div>
          </router-link>
        </div>
      </div>
      <div class="school">
        <div class="mb12">Your School:</div>
        <div class="school-box" v-loading="schoolLoading">
          <div v-for="item in schoolList" :key="item.id" class="mb12"><button @click="handleSchool(item)" class="form-button">{{item.name}}</button></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { schoolListPage, disciplineListPage } from '@/utils/api'

export default {
  name: 'channel',
  data () {
    return {
      schoolLoading: false,
      school: '',
      schoolList: [],
      disciplineLoading: false,
      discipline: '',
      disciplineList: [],
    }
  },
  components: {
  },
  mounted () {
    this.getSchool()
  },
  methods: {
    getSchool() {
      this.schoolLoading = true
      schoolListPage({
        pageNo: 1,
        pageSize: 100
      }).then(res => {
        this.schoolList = res.rows
        if (this.schoolList[0]) {
          this.school = this.schoolList[0].id
          this.getDiscipline()
        }
      }).finally(() => {
        this.schoolLoading = false
      })
    },
    handleSchool(item) {
      this.school = item.id
      this.getDiscipline()
    },
    getDiscipline() {
      this.discipline = ''
      this.disciplineList = []
      this.disciplineLoading = true
      disciplineListPage({
        schoolId: this.school,
        pageNo: 1,
        pageSize: 100
      }).then(res => {
        this.disciplineList = res.rows
      }).finally(() => {
        this.disciplineLoading = false
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
    .form-button{
      width: 180px;
    }
    .school-box{
      width: 200px;
      max-height: 500px;
      overflow-y: auto;
      &::-webkit-scrollbar{
        display: none;
      }
    }
  }
}
</style>
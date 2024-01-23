<template>
  <div class="article-wrap">
    <div class="fs40 mb30 fw600">{{$route.query.discipline}}</div>
    <div class="mb20">
      <input class="form-input" v-model="postTitle" placeholder="Search content" @keyup.enter="handleSearch" />
    </div>
    <div class="text-right mb20">
      <button class="form-button" @click="handleAdd">Add</button>
    </div>
<!--    <el-tag-->
<!--        v-for="(item, index) in list"-->
<!--        :key="item.id"-->
<!--        type="info"-->
<!--        @close="handleDel(item, index)"-->
<!--        closable>-->
<!--      {{item.word}}-->
<!--    </el-tag>-->
    <el-table
        border
        class="mb20"
        :data="list"
        style="width: 100%">
      <el-table-column
          prop="word"
          label="Sensitive">
      </el-table-column>
      <el-table-column
          prop="createTime"
          label="AddTime">
        <template slot-scope="scope">
          {{ scope.row.createTime | dateFilter }}
        </template>
      </el-table-column>
      <el-table-column
          prop="id"
          label="operate"
          width="180">
        <template slot-scope="scope">
          <button class="form-button" @click="handleDel(scope.row)" style="height: 30px;line-height: 30px;padding: 0px 15px;">Delete</button>
        </template>
      </el-table-column>
    </el-table>
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
import { sensitivePageSensitive, sensitiveAddSensitive, sensitiveDeleteSensitive } from '@/utils/api'

export default {
  name: 'forbiddenWords',
  data () {
    return {
      curTab: 'all',
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
    handleDel(item) {
      this.$confirm('Are you sure you want to delete it?', 'Prompt', {
        confirmButtonText: 'Confirm',
        cancelButtonText: 'Cancel',
        type: 'warning'
      }).then(async () => {
        await sensitiveDeleteSensitive({id: item.id}).then(() => {
          this.$message.success('Successfully deleted!')
          this.getList(this.pageNo == 1 ? 1 : this.pageNo - 1)
        });
      }).catch(() => {});
    },
    async handleAdd() {
      this.$prompt('', `Add sensitive words`, {
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
            await sensitiveAddSensitive({
              word: instance.inputValue
            }).then(() => {
              this.$message.success('Added successfully!')
              this.getList(this.pageNo == 1 ? 1 : this.pageNo - 1)
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
        word: this.postTitle,
        pageNo: this.pageNo,
        pageSize: this.pageSize
      }
      sensitivePageSensitive(postData).then(res => {
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
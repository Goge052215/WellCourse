<template>
  <div id="app">
    <Header />
    <div class="main">
      <Sidebar v-if="$store.state.info.useremail" />
      <div class="container" v-loading="$store.state.loading" :class="{'black-container': this.$store.state.theme == 'open'}">
        <keep-alive :include="include">
          <router-view v-if="$route.meta.keepAlive"></router-view>
        </keep-alive>
        <router-view v-if="!$route.meta.keepAlive"></router-view>

<!--        <router-view v-slot="{ Component }">-->
<!--          <keep-alive>-->
<!--            <component :is="Component" :key="$route.name" v-if="$route.meta.keepAlive" />-->
<!--          </keep-alive>-->
<!--          <component :is="Component" :key="$route.name" v-if="!$route.meta.keepAlive" />-->
<!--        </router-view>-->
        <Footer />
      </div>
    </div>
    <el-backtop target=".container" :bottom="100">
      <div
          style="{
        height: 100%;
        width: 100%;
        background-color: #f2f5f6;
        box-shadow: 0 0 6px rgba(0,0,0, .12);
        text-align: center;
        line-height: 40px;
        color: #1989fa;
      }"
      >
        UP
      </div>
    </el-backtop>
  </div>
</template>

<script>
import Header from '@/layout/header.vue'
import Sidebar from '@/layout/sidebar.vue'
import Footer from '@/layout/footer.vue'

export default {
  name: 'Home',
  data () {
    return {
      include: []
    }
  },
  components: {
    Header,
    Sidebar,
    Footer
  },
  watch: {
    $route(to, from) {
      if (to.meta.keepAlive) {
        !this.include.includes(to.name) && this.include.push(to.name);
      }
      if (from.meta.keepAlive && to.meta.deepth < from.meta.deepth) {
        var index = this.include.indexOf(from.name);
        index !== -1 && this.include.splice(index, 1);
      }
    }
  },
  mounted () {
    // localStorage.getItem('token') && this.$store.commit('setInfo',{ email: localStorage.getItem('email') })
  },
  methods: {
  }
}
</script>
<style lang="less">
.form-input{
  display: block;
  width: 100%;
  background: #fff;
  border: #000 1px solid;
  border-radius: 5px;
  height: 45px;
  line-height: 45px;
  padding: 2px 10px;
  font-size: 14px;
  &::-webkit-input-placeholder{
    color: #8f9193;
  }
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
.comment-input{
  width: 100%;
  display: block;
  height: 100px;
  resize: none;
  border: 0;
  background: none;
  &::-webkit-scrollbar{
    display: none;
  }
  &::-webkit-input-placeholder{
    color: #8f9193;
  }
}
.form-button{
  padding: 0px 30px;
  height: 45px;
  line-height: 45px;
  background: #4f4f4f;
  color: #fff;
  border-radius: 5px;
  cursor: pointer;
  text-align: center;
}
.el-select-dropdown__item.selected{
  color: inherit !important;
}
.el-range-editor.is-active, .el-range-editor.is-active:hover, .el-select .el-input.is-focus .el-input__inner{
  border-color: #000 !important;
}
.el-pagination__sizes .el-input .el-input__inner:hover{
  border-color: #000 !important;
}
.el-input.is-active .el-input__inner, .el-input__inner:focus{
  border-color: #000 !important;
}
.el-pager{
  li{
    color: inherit;
  }
  li:hover {
    color: inherit !important;
  }
  li.active{
    background-color: #4f4f4f !important;
  }
  li.active:hover{
    color: #fff !important;
  }
}
.el-pagination.is-background .el-pager li:not(.disabled).active {
}
.el-loading-mask{
  background: none !important;
}
.color-gray{
  color: #999;
}
.main{
  display: flex;
  height: calc(100vh - 56px);
  overflow: hidden;
  .container{
    position: relative;
    flex: 1;
    overflow: auto;
    padding: 30px 40px;
    background: #efefef;
  }
  .bg-block{
    background: #fff;
    border-radius: 10px;
  }
  .shadow-block{
    box-shadow: 3px 3px 5px #d5d5d5;
  }
  .file-tag{
    padding: 5px 10px;
    margin-right: 10px;
    border: #4f4f4f 1px solid;
    border-radius: 3px;
    cursor: pointer;
  }
  .w-e-text-container{
    border-bottom-left-radius: 10px;
    border-bottom-right-radius: 10px;
  }
  .w-e-toolbar{
    border-top-left-radius: 10px;
    border-top-right-radius: 10px;
  }
  .black-container{
    .el-drawer__header{
      color: #ffff;
    }
    &::-webkit-scrollbar {
      background-color: #000;
      width: 12px;
    }
    &::-webkit-scrollbar-thumb {
      background-color: #4f4f4f;
      border-radius: 6px;
    }
    .w-e-scroll{
      &::-webkit-scrollbar {
        background-color: #000;
        width: 12px;
      }
      &::-webkit-scrollbar-thumb {
        background-color: #4f4f4f;
        border-radius: 6px;
      }
    }
    background: #000;
    color: #fff;
    .color-gray{
      color: #9f9f9f;
    }
    .w-e-text-container{
      background: #505050;
      color: #fff;
      pre{
        code{
          background-color: #000;
          border: #000 1px solid;
          text-shadow: none;
        }
      }
    }
    .w-e-toolbar{
      background: #000;
      border-bottom: #333 1px solid;
    }
    .w-e-bar-divider{
      background: #333;
    }
    .form-input{
      background: #000;
      border: #fff 1px solid;
      color: #fff;
      &::-webkit-input-placeholder{
        color: #8f9193;
      }
    }
    .comment-input{
      color: #fff;
      &::-webkit-input-placeholder{
        color: #8f9193;
      }
    }
    .el-loading-mask{
      background: #000;
      background-color: rgba(0,0,0,.7);
    }
    .bg-block{
      background: #333333;
    }
    .shadow-block{
      box-shadow: 3px 3px 5px #000;
    }
    .el-drawer,.el-dialog{
      background: #333333;
    }
    .el-dialog__title,.el-dialog__body{
      color: #fff;
    }
    .el-input__inner,.el-upload-dragger{
      background: #000;
      color: #fff;
    }
    .el-table{
      color: #fff;
      thead{
        color: #fff;
      }
      th.el-table__cell{
        background: #333;
      }
      tr{
        background: #333;
      }
      .el-table__body tr:hover>td.el-table__cell{
        background-color: #444;
      }
    }
  }
}

</style>

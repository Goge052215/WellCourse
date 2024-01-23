<template>
  <router-view></router-view>
<!--  <transition name="fade">-->
<!--    <router-view v-slot="{ Component }">-->
<!--      <keep-alive>-->
<!--        <component :is="Component" :key="$route.name" v-if="$route.meta.keepAlive" />-->
<!--      </keep-alive>-->
<!--      <component :is="Component" :key="$route.name" v-if="!$route.meta.keepAlive" />-->
<!--    </router-view>-->
<!--  </transition>-->
</template>

<script>
import { defineComponent } from 'vue'
export default defineComponent({
  setup () {
    return {
    }
  },
  components: {
  },
  mounted() {
    console.log(this.$route.meta)
  }
})
</script>

<style lang="less" scoped>
//.fade-enter-active,
//.fade-leave-active {
//  opacity: 1;
//  transition: all 0.2s;
//  transform: translateY(0);
//}
//.fade-enter,
//.fade-leave-to {
//  opacity: 0;
//  transform: translateY(-20px);
//}
</style>

import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)
import { userUserInfo } from '@/utils/api'

export default new Vuex.Store({
  state: {
    fileUrl: process.env.VUE_APP_API_BASE_IMG_URL,
    info: {},
    title: '',
    theme: localStorage.getItem('theme') || 'close',
    loading: false
  },
  mutations: {
    setInfo(state, data) {
      state.info = data
    },
    setLoading(state, data) {
      state.loading = data
    },
    setTheme(state, data) {
      localStorage.setItem('theme', data)
      state.theme = data
    }
  },
  actions: {
    getInfo({ commit }) {
      return new Promise((resolve, reject) => {
        userUserInfo().then(res => {
          commit("setInfo", res)
          resolve(res)
        }).catch(err => {
          reject(err)
        })
      })
    }
  },
  modules: {
  }
})

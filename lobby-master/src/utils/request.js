import axios from 'axios';
import { Message } from 'element-ui';
import router from '@/router'
import store from '@/store'

const request = axios.create({
  // API 请求的默认前缀
  baseURL: process.env.VUE_APP_API_BASE_URL,
  // 携带Cookie发送请求
  // withCredentials: true,
  // 请求超时时间
  timeout: 15000,
});
// 异常拦截处理器
const errorHandler = error => {
  Message.error('接口不存在')
  return Promise.reject(error);
};
// 请求拦截器
const requestHandler = config => {
  if (localStorage.getItem('token')) {
    config.headers['token'] = localStorage.getItem('token')
  }
  if(config.isFormData){
    config.headers["Content-Type"] = "multipart/form-data";
  }
  return config;
};
request.interceptors.request.use(requestHandler, errorHandler);
// 响应拦截器
const responseHandler = response => {
  const { code, msg } = response.data;
  if (code === 200) {
    return response.data.data;
  } else {
    if(code === 401) {
      Message.warning('请先登录')
      store.commit('setInfo',{})
      localStorage.removeItem('token')
      router.replace({ path: '/login' })
    } else {
      Message.warning(msg)
    }
    return Promise.reject(response.data);
  }
};
request.interceptors.response.use(responseHandler, errorHandler);
export default request;

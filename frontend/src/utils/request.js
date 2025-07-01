import axios from 'axios';
import store from '@/store';
import { Loading, Message } from 'element-ui';

// 创建axios实例
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API,
  timeout: 10000 // 请求超时时间
});

let loadingInstance = null;
let requestCount = 0;

// 请求拦截器
service.interceptors.request.use(
  config => {
    // 显示loading
    if (requestCount === 0) {
      loadingInstance = Loading.service({
        lock: true,
        text: '加载中...',
        background: 'rgba(0, 0, 0, 0.7)'
      });
    }
    requestCount++;

    // 添加token
    const token = store.getters.token;
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
  },
  error => {
    // 请求错误处理
    Promise.reject(error);
  }
);

// 响应拦截器
service.interceptors.response.use(
  response => {
    // 关闭loading
    requestCount--;
    if (requestCount === 0 && loadingInstance) {
      loadingInstance.close();
    }

    const res = response.data;
    // 根据业务状态码处理
    if (res.code !== 200) {
      Message({
        message: res.message || 'Error',
        type: 'error',
        duration: 5 * 1000
      });
      return Promise.reject(new Error(res.message || 'Error'));
    } else {
      return res;
    }
  },
  error => {
    // 关闭loading
    requestCount--;
    if (requestCount === 0 && loadingInstance) {
      loadingInstance.close();
    }

    // 处理错误响应
    let errorMessage = '请求失败';
    if (error.response) {
      switch (error.response.status) {
        case 401:
          errorMessage = '未授权，请登录';
          // 这里可以跳转到登录页面
          break;
        case 403:
          errorMessage = '拒绝访问';
          break;
        case 404:
          errorMessage = `请求地址出错: ${error.response.config.url}`;
          break;
        case 408:
          errorMessage = '请求超时';
          break;
        case 500:
          errorMessage = '服务器内部错误';
          break;
        case 501:
          errorMessage = '服务未实现';
          break;
        case 502:
          errorMessage = '网关错误';
          break;
        case 503:
          errorMessage = '服务不可用';
          break;
        case 504:
          errorMessage = '网关超时';
          break;
        case 505:
          errorMessage = 'HTTP版本不受支持';
          break;
        default:
          errorMessage = `连接错误 ${error.response.status}`;
      }
    } else if (error.message.includes('timeout')) {
      errorMessage = '请求超时';
    } else if (error.message === 'Network Error') {
      errorMessage = '网络异常，请检查网络连接';
    }

    Message({
      message: errorMessage,
      type: 'error',
      duration: 5 * 1000
    });

    return Promise.reject(error);
  }
);

export default service;
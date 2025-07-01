import axios from 'axios';
import { Loading, Message } from 'element-ui';

// 创建axios实例
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // api的base_url
  timeout: 10000, // 请求超时时间
});

let loadingInstance = null;

// 请求拦截器
service.interceptors.request.use(
  config => {
    // 在发送请求之前做些什么
    loadingInstance = Loading.service({
      lock: true,
      text: '加载中...',
      spinner: 'el-icon-loading',
      background: 'rgba(0, 0, 0, 0.7)'
    });

    // 如果存在token，则每个请求头都带上token
    const token = localStorage.getItem('token');
    if (token) {
      config.headers['Authorization'] = 'Bearer ' + token;
    }
    return config;
  },
  error => {
    // 对请求错误做些什么
    loadingInstance && loadingInstance.close();
    Message.error('请求超时');
    return Promise.reject(error);
  }
);

// 响应拦截器
service.interceptors.response.use(
  response => {
    loadingInstance && loadingInstance.close();
    const res = response.data;
    // 根据业务状态码处理不同逻辑
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
    loadingInstance && loadingInstance.close();
    console.log('err' + error); // for debug
    let message = '';
    if (error.response) {
      switch (error.response.status) {
        case 400:
          message = '请求错误';
          break;
        case 401:
          message = '未授权，请登录';
          // 这里可以跳转到登录页面
          break;
        case 403:
          message = '拒绝访问';
          break;
        case 404:
          message = `请求地址出错: ${error.response.config.url}`;
          break;
        case 408:
          message = '请求超时';
          break;
        case 500:
          message = '服务器内部错误';
          break;
        case 501:
          message = '服务未实现';
          break;
        case 502:
          message = '网关错误';
          break;
        case 503:
          message = '服务不可用';
          break;
        case 504:
          message = '网关超时';
          break;
        case 505:
          message = 'HTTP版本不受支持';
          break;
        default:
          message = '网络错误';
      }
    } else if (error.message.includes('timeout')) {
      message = '请求超时';
    } else {
      message = '网络连接错误';
    }
    Message({
      message,
      type: 'error',
      duration: 5 * 1000
    });
    return Promise.reject(error);
  }
);

export default service;
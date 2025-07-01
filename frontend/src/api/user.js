import request from './request';

/**
 * 用户登录接口
 * @param {Object} data 登录数据
 * @param {string} data.username 用户名
 * @param {string} data.password 密码
 * @returns {Promise} 返回Promise对象
 */
export const login = async (data) => {
  if (!data.username || !data.password) {
    throw new Error('用户名和密码不能为空');
  }
  return await request({
    url: '/api/users/login',
    method: 'POST',
    data
  });
};

/**
 * 用户注册接口
 * @param {Object} data 注册数据
 * @param {string} data.username 用户名
 * @param {string} data.password 密码
 * @param {string} data.email 邮箱
 * @returns {Promise} 返回Promise对象
 */
export const register = async (data) => {
  if (!data.username || !data.password || !data.email) {
    throw new Error('用户名、密码和邮箱不能为空');
  }
  return await request({
    url: '/api/users/register',
    method: 'POST',
    data
  });
};

/**
 * 获取用户信息接口
 * @returns {Promise} 返回Promise对象
 */
export const getProfile = async () => {
  return await request({
    url: '/api/users/profile',
    method: 'GET'
  });
};

/**
 * 更新用户信息接口
 * @param {Object} data 用户信息数据
 * @param {string} [data.nickname] 昵称
 * @param {string} [data.avatar] 头像
 * @param {string} [data.bio] 个人简介
 * @returns {Promise} 返回Promise对象
 */
export const updateProfile = async (data) => {
  if (!data || Object.keys(data).length === 0) {
    throw new Error('至少需要传递一个更新字段');
  }
  return await request({
    url: '/api/users/profile',
    method: 'PUT',
    data
  });
};

/**
 * 更新密码接口
 * @param {Object} data 密码数据
 * @param {string} data.oldPassword 旧密码
 * @param {string} data.newPassword 新密码
 * @returns {Promise} 返回Promise对象
 */
export const updatePassword = async (data) => {
  if (!data.oldPassword || !data.newPassword) {
    throw new Error('旧密码和新密码不能为空');
  }
  return await request({
    url: '/api/users/password',
    method: 'PUT',
    data
  });
};
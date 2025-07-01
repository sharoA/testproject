import request from './request'

/**
 * 用户登录
 * @param {Object} credentials - 登录凭证
 * @param {string} credentials.username - 用户名
 * @param {string} credentials.password - 密码
 * @returns {Promise} 返回登录结果
 */
export const login = async (credentials) => {
  if (!credentials || !credentials.username || !credentials.password) {
    throw new Error('用户名和密码不能为空')
  }
  return await request({
    url: '/api/users/login',
    method: 'POST',
    data: credentials
  })
}

/**
 * 用户注册
 * @param {Object} userData - 用户注册数据
 * @param {string} userData.username - 用户名
 * @param {string} userData.password - 密码
 * @param {string} userData.email - 邮箱
 * @returns {Promise} 返回注册结果
 */
export const register = async (userData) => {
  if (!userData || !userData.username || !userData.password || !userData.email) {
    throw new Error('用户名、密码和邮箱不能为空')
  }
  return await request({
    url: '/api/users/register',
    method: 'POST',
    data: userData
  })
}

/**
 * 获取用户资料
 * @returns {Promise} 返回用户资料
 */
export const getProfile = async () => {
  return await request({
    url: '/api/users/profile',
    method: 'GET'
  })
}

/**
 * 更新用户资料
 * @param {Object} profileData - 用户资料
 * @param {string} [profileData.username] - 用户名
 * @param {string} [profileData.email] - 邮箱
 * @param {string} [profileData.avatar] - 头像URL
 * @returns {Promise} 返回更新结果
 */
export const updateProfile = async (profileData) => {
  if (!profileData || Object.keys(profileData).length === 0) {
    throw new Error('至少需要提供一个更新字段')
  }
  return await request({
    url: '/api/users/profile',
    method: 'PUT',
    data: profileData
  })
}

/**
 * 更新用户密码
 * @param {Object} passwordData - 密码数据
 * @param {string} passwordData.oldPassword - 旧密码
 * @param {string} passwordData.newPassword - 新密码
 * @returns {Promise} 返回更新结果
 */
export const updatePassword = async (passwordData) => {
  if (!passwordData || !passwordData.oldPassword || !passwordData.newPassword) {
    throw new Error('旧密码和新密码不能为空')
  }
  return await request({
    url: '/api/users/password',
    method: 'PUT',
    data: passwordData
  })
}
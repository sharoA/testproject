<template>
  <div class="login-container">
    <div class="login-wrapper">
      <div class="login-header">
        <h1>欢迎登录</h1>
        <p>请输入您的账号和密码</p>
      </div>
      
      <div class="login-main">
        <el-card shadow="hover" class="login-card">
          <login-form 
            @login="handleLogin"
            @register="handleRegister"
            :loading="loading"
          />
        </el-card>
      </div>
      
      <div class="login-footer">
        <p>© 2023 需求文档 - 一局对接链数优化V0. All Rights Reserved.</p>
      </div>
    </div>
  </div>
</template>

<script>
import LoginForm from '@/components/LoginForm.vue'
import { login } from '@/api/users'
import { setToken } from '@/utils/auth'
import { mapMutations } from 'vuex'

export default {
  name: 'Login',
  components: {
    LoginForm
  },
  data() {
    return {
      loading: false
    }
  },
  methods: {
    ...mapMutations('user', ['SET_USER_INFO']),
    
    async handleLogin(formData) {
      try {
        this.loading = true
        const { data } = await login(formData)
        
        // 保存token
        setToken(data.token)
        
        // 保存用户信息到vuex
        this.SET_USER_INFO(data.userInfo)
        
        // 跳转到首页
        this.$router.push({ path: '/' })
        
        this.$message.success('登录成功')
      } catch (error) {
        console.error('登录失败:', error)
        this.$message.error(error.message || '登录失败，请重试')
      } finally {
        this.loading = false
      }
    },
    
    handleRegister() {
      this.$router.push({ path: '/register' })
    }
  }
}
</script>

<style lang="scss" scoped>
.login-container {
  width: 100%;
  height: 100vh;
  background: #f0f2f5;
  display: flex;
  justify-content: center;
  align-items: center;
  background-image: url('~@/assets/login-bg.jpg');
  background-size: cover;
  background-position: center;
  
  .login-wrapper {
    width: 100%;
    max-width: 450px;
    padding: 20px;
    
    .login-header {
      text-align: center;
      margin-bottom: 30px;
      color: #fff;
      
      h1 {
        font-size: 28px;
        margin-bottom: 10px;
      }
      
      p {
        font-size: 14px;
        opacity: 0.8;
      }
    }
    
    .login-main {
      .login-card {
        border-radius: 8px;
        ::v-deep .el-card__body {
          padding: 30px;
        }
      }
    }
    
    .login-footer {
      margin-top: 20px;
      text-align: center;
      color: rgba(255, 255, 255, 0.7);
      font-size: 12px;
    }
  }
}

@media (max-width: 768px) {
  .login-container {
    .login-wrapper {
      padding: 10px;
      max-width: 90%;
      
      .login-header {
        h1 {
          font-size: 24px;
        }
      }
      
      .login-main {
        .login-card {
          ::v-deep .el-card__body {
            padding: 20px;
          }
        }
      }
    }
  }
}
</style>
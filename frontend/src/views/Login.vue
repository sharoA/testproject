<template>
  <div class="login-container">
    <div class="login-wrapper">
      <div class="login-header">
        <h1>欢迎登录</h1>
        <p>请使用您的账号密码登录系统</p>
      </div>
      
      <div class="login-main">
        <el-card class="login-card">
          <login-form 
            @login-success="handleLoginSuccess"
            @to-register="handleToRegister"
          />
        </el-card>
      </div>
      
      <div class="login-footer">
        <p>© 2023 需求业务集成链优化系统 v1.0.0</p>
      </div>
    </div>
  </div>
</template>

<script>
import LoginForm from '@/components/LoginForm'
import { mapActions } from 'vuex'

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
    ...mapActions(['login']),
    
    async handleLoginSuccess(formData) {
      this.loading = true
      try {
        await this.login(formData)
        this.$router.push('/')
      } catch (error) {
        this.$message.error(error.message || '登录失败')
      } finally {
        this.loading = false
      }
    },
    
    handleToRegister() {
      this.$router.push('/register')
    }
  }
}
</script>

<style lang="scss" scoped>
.login-container {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f0f2f5;
  background-image: linear-gradient(120deg, #a1c4fd 0%, #c2e9fb 100%);
  
  .login-wrapper {
    width: 100%;
    max-width: 420px;
    padding: 20px;
    
    .login-header {
      text-align: center;
      margin-bottom: 30px;
      
      h1 {
        font-size: 28px;
        color: #333;
        margin-bottom: 10px;
      }
      
      p {
        font-size: 14px;
        color: #666;
      }
    }
    
    .login-main {
      .login-card {
        border-radius: 8px;
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
      }
    }
    
    .login-footer {
      margin-top: 30px;
      text-align: center;
      font-size: 12px;
      color: #999;
    }
  }
}

@media (max-width: 768px) {
  .login-container {
    padding: 20px;
    
    .login-wrapper {
      max-width: 100%;
      
      .login-header {
        h1 {
          font-size: 24px;
        }
      }
    }
  }
}
</style>
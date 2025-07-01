<template>
  <div class="register-page">
    <el-row type="flex" justify="center" align="middle" class="register-container">
      <el-col :xs="24" :sm="12" :md="8" :lg="6">
        <el-card class="register-card">
          <div slot="header" class="register-header">
            <h2>用户注册</h2>
          </div>
          <register-form
            @register-success="handleRegisterSuccess"
            @go-to-login="handleGoToLogin"
          />
          <div class="register-footer">
            <el-button type="text" @click="handleGoToLogin">
              已有账号？立即登录
            </el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import RegisterForm from '@/components/RegisterForm'
import { mapActions } from 'vuex'

export default {
  name: 'Register',
  components: {
    RegisterForm
  },
  data() {
    return {
      loading: false
    }
  },
  methods: {
    ...mapActions('user', ['register']),
    
    handleRegisterSuccess(formData) {
      this.loading = true
      this.register(formData)
        .then(() => {
          this.$message.success('注册成功')
          this.$router.push('/login')
        })
        .catch(error => {
          this.$message.error(error.message || '注册失败')
        })
        .finally(() => {
          this.loading = false
        })
    },
    
    handleGoToLogin() {
      this.$router.push('/login')
    }
  }
}
</script>

<style lang="scss" scoped>
.register-page {
  height: 100vh;
  background-color: #f5f7fa;
  display: flex;
  align-items: center;
  justify-content: center;

  .register-container {
    width: 100%;
    padding: 20px;
  }

  .register-card {
    border-radius: 4px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);

    .register-header {
      text-align: center;
      color: #409eff;
    }

    .register-footer {
      margin-top: 20px;
      text-align: center;
    }
  }
}

@media (max-width: 768px) {
  .register-page {
    padding: 0 15px;
  }
}
</style>
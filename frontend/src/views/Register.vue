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
            @register-failed="handleRegisterFailed"
          />
          <div class="register-footer">
            <span>已有账号？</span>
            <el-button type="text" @click="goToLogin">立即登录</el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import RegisterForm from '@/components/RegisterForm.vue';
import { mapActions } from 'vuex';

export default {
  name: 'Register',
  components: {
    RegisterForm
  },
  data() {
    return {
      loading: false
    };
  },
  methods: {
    ...mapActions(['registerUser']),
    
    async handleRegisterSuccess(formData) {
      this.loading = true;
      try {
        await this.registerUser(formData);
        this.$message.success('注册成功！');
        this.$router.push('/login');
      } catch (error) {
        this.$message.error(error.message || '注册失败');
      } finally {
        this.loading = false;
      }
    },
    
    handleRegisterFailed(error) {
      this.$message.error(error.message || '表单验证失败');
    },
    
    goToLogin() {
      this.$router.push('/login');
    }
  }
};
</script>

<style scoped>
.register-page {
  height: 100vh;
  background-color: #f5f7fa;
  display: flex;
  align-items: center;
}

.register-container {
  width: 100%;
  padding: 20px;
}

.register-card {
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.register-header {
  text-align: center;
  color: #409EFF;
  padding: 10px 0;
}

.register-footer {
  margin-top: 20px;
  text-align: center;
  font-size: 14px;
  color: #909399;
}

@media (max-width: 768px) {
  .register-container {
    padding: 10px;
  }
  
  .register-card {
    width: 100%;
  }
}
</style>
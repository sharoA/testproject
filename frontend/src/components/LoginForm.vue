<template>
  <div class="login-form-container">
    <el-form
      ref="loginForm"
      :model="form"
      :rules="rules"
      label-position="top"
      @submit.native.prevent="handleSubmit"
    >
      <el-form-item label="用户名" prop="username">
        <el-input
          v-model="form.username"
          placeholder="请输入用户名"
          prefix-icon="el-icon-user"
        ></el-input>
      </el-form-item>

      <el-form-item label="密码" prop="password">
        <el-input
          v-model="form.password"
          type="password"
          placeholder="请输入密码"
          prefix-icon="el-icon-lock"
          show-password
        ></el-input>
      </el-form-item>

      <el-form-item>
        <el-button
          type="primary"
          :loading="loading"
          native-type="submit"
          class="login-button"
        >
          登录
        </el-button>
      </el-form-item>

      <div class="form-footer">
        <span>还没有账号？</span>
        <el-link type="primary" @click="$router.push('/register')">立即注册</el-link>
      </div>
    </el-form>
  </div>
</template>

<script>
import { login } from '@/api/users';
import { setToken } from '@/utils/auth';

export default {
  name: 'LoginForm',
  data() {
    return {
      form: {
        username: '',
        password: '',
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' },
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' },
        ],
      },
      loading: false,
    };
  },
  methods: {
    handleSubmit() {
      this.$refs.loginForm.validate(async (valid) => {
        if (!valid) {
          return false;
        }

        try {
          this.loading = true;
          const { data } = await login(this.form);
          setToken(data.token);
          
          this.$message.success('登录成功');
          this.$router.push('/');
        } catch (error) {
          let errorMessage = '登录失败，请重试';
          if (error.response && error.response.data && error.response.data.message) {
            errorMessage = error.response.data.message;
          }
          this.$message.error(errorMessage);
        } finally {
          this.loading = false;
        }
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.login-form-container {
  max-width: 400px;
  margin: 0 auto;
  padding: 20px;
  background: #fff;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);

  .login-button {
    width: 100%;
    margin-top: 10px;
  }

  .form-footer {
    margin-top: 20px;
    text-align: center;
    color: #909399;
    font-size: 14px;

    .el-link {
      margin-left: 5px;
    }
  }
}
</style>
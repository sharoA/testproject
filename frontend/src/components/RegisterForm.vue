<template>
  <div class="register-form-container">
    <el-form
      ref="registerForm"
      :model="form"
      :rules="rules"
      label-width="120px"
      class="register-form"
      @submit.native.prevent="handleSubmit"
    >
      <el-form-item label="用户名" prop="username">
        <el-input v-model="form.username" placeholder="请输入用户名"></el-input>
      </el-form-item>

      <el-form-item label="邮箱" prop="email">
        <el-input v-model="form.email" placeholder="请输入邮箱"></el-input>
      </el-form-item>

      <el-form-item label="密码" prop="password">
        <el-input
          v-model="form.password"
          type="password"
          placeholder="请输入密码"
          show-password
        ></el-input>
      </el-form-item>

      <el-form-item label="确认密码" prop="confirmPassword">
        <el-input
          v-model="form.confirmPassword"
          type="password"
          placeholder="请再次输入密码"
          show-password
        ></el-input>
      </el-form-item>

      <el-form-item>
        <el-button
          type="primary"
          :loading="loading"
          native-type="submit"
          class="submit-btn"
        >
          注册
        </el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { register } from "@/api/users";

export default {
  name: "RegisterForm",
  data() {
    const validatePassword = (rule, value, callback) => {
      if (value !== this.form.password) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };

    return {
      loading: false,
      form: {
        username: "",
        email: "",
        password: "",
        confirmPassword: "",
      },
      rules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          { min: 3, max: 20, message: "长度在 3 到 20 个字符", trigger: "blur" },
        ],
        email: [
          { required: true, message: "请输入邮箱地址", trigger: "blur" },
          {
            type: "email",
            message: "请输入正确的邮箱地址",
            trigger: ["blur", "change"],
          },
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { min: 6, max: 20, message: "长度在 6 到 20 个字符", trigger: "blur" },
        ],
        confirmPassword: [
          { required: true, message: "请再次输入密码", trigger: "blur" },
          { validator: validatePassword, trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    handleSubmit() {
      this.$refs.registerForm.validate((valid) => {
        if (valid) {
          this.loading = true;
          register(this.form)
            .then(() => {
              this.$message.success("注册成功！");
              this.$router.push("/login");
            })
            .catch((error) => {
              console.error("注册失败:", error);
              this.$message.error(
                error.response?.data?.message || "注册失败，请稍后重试"
              );
            })
            .finally(() => {
              this.loading = false;
            });
        } else {
          return false;
        }
      });
    },
    resetForm() {
      this.$refs.registerForm.resetFields();
    },
  },
};
</script>

<style scoped>
.register-form-container {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
}

.register-form {
  padding: 30px;
  background: #fff;
  border-radius: 6px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.submit-btn {
  width: 100%;
}
</style>
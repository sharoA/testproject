<template>
  <el-form
    ref="passwordForm"
    :model="form"
    :rules="rules"
    label-width="120px"
    label-position="top"
    @submit.native.prevent="submitForm"
  >
    <el-form-item label="当前密码" prop="currentPassword">
      <el-input
        v-model="form.currentPassword"
        type="password"
        placeholder="请输入当前密码"
        show-password
      ></el-input>
    </el-form-item>

    <el-form-item label="新密码" prop="newPassword">
      <el-input
        v-model="form.newPassword"
        type="password"
        placeholder="请输入新密码"
        show-password
      ></el-input>
    </el-form-item>

    <el-form-item label="确认新密码" prop="confirmPassword">
      <el-input
        v-model="form.confirmPassword"
        type="password"
        placeholder="请再次输入新密码"
        show-password
      ></el-input>
    </el-form-item>

    <el-form-item>
      <el-button
        type="primary"
        :loading="loading"
        native-type="submit"
      >
        修改密码
      </el-button>
      <el-button @click="resetForm">重置</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import { updatePassword } from '@/api/users';
import { validatePassword } from '@/utils/validate';

export default {
  name: 'PasswordForm',
  data() {
    const validateConfirmPassword = (rule, value, callback) => {
      if (value !== this.form.newPassword) {
        callback(new Error('两次输入的密码不一致'));
      } else {
        callback();
      }
    };

    return {
      loading: false,
      form: {
        currentPassword: '',
        newPassword: '',
        confirmPassword: ''
      },
      rules: {
        currentPassword: [
          { required: true, message: '请输入当前密码', trigger: 'blur' },
          { min: 6, max: 20, message: '密码长度在6到20个字符', trigger: 'blur' }
        ],
        newPassword: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
          { validator: validatePassword, trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请确认新密码', trigger: 'blur' },
          { validator: validateConfirmPassword, trigger: 'blur' }
        ]
      }
    };
  },
  methods: {
    submitForm() {
      this.$refs.passwordForm.validate(async valid => {
        if (!valid) {
          return false;
        }

        this.loading = true;
        try {
          await updatePassword({
            oldPassword: this.form.currentPassword,
            newPassword: this.form.newPassword
          });

          this.$message.success('密码修改成功');
          this.resetForm();
        } catch (error) {
          this.$message.error(error.message || '密码修改失败');
        } finally {
          this.loading = false;
        }
      });
    },
    resetForm() {
      this.$refs.passwordForm.resetFields();
    }
  }
};
</script>

<style scoped>
.el-form {
  max-width: 500px;
  margin: 0 auto;
}

.el-button + .el-button {
  margin-left: 10px;
}
</style>
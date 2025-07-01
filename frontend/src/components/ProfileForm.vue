<template>
  <div class="profile-form">
    <el-form
      ref="profileForm"
      :model="form"
      :rules="rules"
      label-width="120px"
      label-position="left"
      @submit.native.prevent="submitForm"
    >
      <el-form-item label="用户名" prop="username">
        <el-input v-model="form.username" disabled></el-input>
      </el-form-item>

      <el-form-item label="邮箱" prop="email">
        <el-input v-model="form.email" type="email"></el-input>
      </el-form-item>

      <el-form-item label="手机号" prop="phone">
        <el-input v-model="form.phone"></el-input>
      </el-form-item>

      <el-form-item label="昵称" prop="nickname">
        <el-input v-model="form.nickname"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button
          type="primary"
          :loading="loading"
          native-type="submit"
        >
          保存修改
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { updateProfile, getProfile } from '@/api/users'

export default {
  name: 'ProfileForm',
  data() {
    const validatePhone = (rule, value, callback) => {
      if (value && !/^1[3-9]\d{9}$/.test(value)) {
        callback(new Error('请输入正确的手机号码'))
      } else {
        callback()
      }
    }

    return {
      loading: false,
      form: {
        username: '',
        email: '',
        phone: '',
        nickname: ''
      },
      rules: {
        email: [
          { required: true, message: '请输入邮箱地址', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
        ],
        phone: [
          { validator: validatePhone, trigger: 'blur' }
        ],
        nickname: [
          { required: true, message: '请输入昵称', trigger: 'blur' },
          { min: 2, max: 16, message: '长度在 2 到 16 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.fetchProfile()
  },
  methods: {
    async fetchProfile() {
      try {
        this.loading = true
        const response = await getProfile()
        this.form = {
          username: response.data.username,
          email: response.data.email,
          phone: response.data.phone,
          nickname: response.data.nickname
        }
      } catch (error) {
        console.error('获取用户信息失败:', error)
        this.$message.error('获取用户信息失败，请稍后重试')
      } finally {
        this.loading = false
      }
    },
    submitForm() {
      this.$refs.profileForm.validate(async valid => {
        if (!valid) {
          this.$message.warning('请填写正确的表单信息')
          return false
        }

        try {
          this.loading = true
          await updateProfile(this.form)
          this.$message.success('个人信息更新成功')
          this.$emit('updated')
        } catch (error) {
          console.error('更新个人信息失败:', error)
          this.$message.error('更新个人信息失败，请稍后重试')
        } finally {
          this.loading = false
        }
      })
    }
  }
}
</script>

<style scoped>
.profile-form {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
}

.el-form-item {
  margin-bottom: 22px;
}
</style>
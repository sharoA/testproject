<template>
  <el-form
    ref="profileForm"
    :model="form"
    :rules="rules"
    label-width="120px"
    label-position="top"
    @submit.native.prevent="submitForm"
  >
    <el-form-item label="用户名" prop="username">
      <el-input v-model="form.username" placeholder="请输入用户名"></el-input>
    </el-form-item>

    <el-form-item label="邮箱" prop="email">
      <el-input v-model="form.email" placeholder="请输入邮箱"></el-input>
    </el-form-item>

    <el-form-item label="手机号" prop="phone">
      <el-input v-model="form.phone" placeholder="请输入手机号"></el-input>
    </el-form-item>

    <el-form-item label="头像">
      <el-upload
        class="avatar-uploader"
        action="/api/upload"
        :show-file-list="false"
        :on-success="handleAvatarSuccess"
        :before-upload="beforeAvatarUpload"
      >
        <img v-if="form.avatar" :src="form.avatar" class="avatar" />
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>
    </el-form-item>

    <el-form-item>
      <el-button
        type="primary"
        :loading="loading"
        native-type="submit"
      >
        保存修改
      </el-button>
      <el-button @click="resetForm">重置</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
export default {
  name: 'ProfileForm',
  data() {
    return {
      loading: false,
      form: {
        username: '',
        email: '',
        phone: '',
        avatar: ''
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 16, message: '长度在 3 到 16 个字符', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入邮箱地址', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
        ],
        phone: [
          { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur' }
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
        const response = await this.$http.get('/api/users/profile')
        this.form = response.data
      } catch (error) {
        this.$message.error('获取用户信息失败: ' + error.message)
      } finally {
        this.loading = false
      }
    },
    submitForm() {
      this.$refs.profileForm.validate(async (valid) => {
        if (!valid) {
          this.$message.error('请检查表单填写是否正确')
          return false
        }

        try {
          this.loading = true
          await this.$http.put('/api/users/profile', this.form)
          this.$message.success('用户信息更新成功')
        } catch (error) {
          this.$message.error('更新用户信息失败: ' + error.message)
        } finally {
          this.loading = false
        }
      })
    },
    resetForm() {
      this.$refs.profileForm.resetFields()
    },
    handleAvatarSuccess(res) {
      this.form.avatar = res.url
      this.$message.success('头像上传成功')
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg' || file.type === 'image/png'
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG/PNG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isJPG && isLt2M
    }
  }
}
</script>

<style scoped>
.avatar-uploader {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  width: 178px;
  height: 178px;
}
.avatar-uploader:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
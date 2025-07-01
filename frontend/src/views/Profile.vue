<template>
  <div class="profile-container">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="18" :lg="18" :xl="18">
        <el-card class="profile-card">
          <div slot="header" class="clearfix">
            <span>个人信息</span>
          </div>
          <profile-form 
            :user-info="userInfo" 
            @submit="handleProfileUpdate"
            :loading="profileLoading"
          />
        </el-card>

        <el-card class="password-card">
          <div slot="header" class="clearfix">
            <span>修改密码</span>
          </div>
          <password-form 
            @submit="handlePasswordUpdate"
            :loading="passwordLoading"
          />
        </el-card>
      </el-col>

      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
        <el-card class="action-card">
          <div slot="header" class="clearfix">
            <span>操作</span>
          </div>
          <el-button 
            type="primary" 
            icon="el-icon-back" 
            @click="goBack"
            class="action-button"
          >
            返回
          </el-button>
          <el-button 
            type="danger" 
            icon="el-icon-switch-button" 
            @click="handleLogout"
            class="action-button"
          >
            退出登录
          </el-button>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import ProfileForm from '@/components/ProfileForm.vue'
import PasswordForm from '@/components/PasswordForm.vue'
import { getUserProfile, updateUserProfile, updateUserPassword } from '@/api/user'
import { removeToken } from '@/utils/auth'

export default {
  name: 'Profile',
  components: {
    ProfileForm,
    PasswordForm
  },
  data() {
    return {
      userInfo: {
        username: '',
        email: '',
        phone: '',
        avatar: ''
      },
      profileLoading: false,
      passwordLoading: false
    }
  },
  created() {
    this.fetchUserProfile()
  },
  methods: {
    async fetchUserProfile() {
      try {
        const { data } = await getUserProfile()
        this.userInfo = data
      } catch (error) {
        console.error('获取用户信息失败:', error)
      }
    },
    async handleProfileUpdate(formData) {
      this.profileLoading = true
      try {
        await updateUserProfile(formData)
        this.$message.success('个人信息更新成功')
        this.fetchUserProfile()
      } catch (error) {
        console.error('更新个人信息失败:', error)
      } finally {
        this.profileLoading = false
      }
    },
    async handlePasswordUpdate(passwordData) {
      this.passwordLoading = true
      try {
        await updateUserPassword(passwordData)
        this.$message.success('密码修改成功')
      } catch (error) {
        console.error('修改密码失败:', error)
      } finally {
        this.passwordLoading = false
      }
    },
    handleLogout() {
      removeToken()
      this.$router.push('/login')
    },
    goBack() {
      this.$router.go(-1)
    }
  }
}
</script>

<style lang="scss" scoped>
.profile-container {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;

  .profile-card, .password-card, .action-card {
    margin-bottom: 20px;
  }

  .action-button {
    display: block;
    width: 100%;
    margin-bottom: 10px;

    &:last-child {
      margin-bottom: 0;
    }
  }

  .clearfix {
    font-weight: bold;
    font-size: 16px;
  }
}

@media (max-width: 768px) {
  .profile-container {
    padding: 10px;
  }
}
</style>
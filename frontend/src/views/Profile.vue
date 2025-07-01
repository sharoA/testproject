<template>
  <div class="profile-container">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
        <el-card class="profile-card">
          <div slot="header" class="clearfix">
            <span>个人信息</span>
          </div>
          <profile-form 
            :user="user" 
            @submit="handleProfileUpdate"
            :loading="profileLoading"
          />
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
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
    </el-row>
  </div>
</template>

<script>
import ProfileForm from '@/components/ProfileForm.vue'
import PasswordForm from '@/components/PasswordForm.vue'
import { getUserProfile, updateUserProfile, updateUserPassword } from '@/api/user'
import { mapGetters } from 'vuex'

export default {
  name: 'Profile',
  components: {
    ProfileForm,
    PasswordForm
  },
  data() {
    return {
      user: {
        name: '',
        email: '',
        avatar: ''
      },
      profileLoading: false,
      passwordLoading: false
    }
  },
  computed: {
    ...mapGetters([
      'token'
    ])
  },
  created() {
    this.fetchUserProfile()
  },
  methods: {
    async fetchUserProfile() {
      try {
        const response = await getUserProfile()
        this.user = response.data
      } catch (error) {
        console.error('获取用户信息失败:', error)
        this.$message.error('获取用户信息失败')
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
        this.$message.error('更新个人信息失败')
      } finally {
        this.profileLoading = false
      }
    },
    async handlePasswordUpdate(passwordData) {
      this.passwordLoading = true
      try {
        await updateUserPassword(passwordData)
        this.$message.success('密码修改成功')
        this.$router.push('/login')
      } catch (error) {
        console.error('修改密码失败:', error)
        this.$message.error('修改密码失败')
      } finally {
        this.passwordLoading = false
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.profile-container {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;

  .profile-card, .password-card {
    margin-bottom: 20px;
  }

  .clearfix {
    font-size: 18px;
    font-weight: bold;
  }
}

@media (max-width: 768px) {
  .profile-container {
    padding: 10px;
    
    .el-col {
      margin-bottom: 15px;
    }
  }
}
</style>
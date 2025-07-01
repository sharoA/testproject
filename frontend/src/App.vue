<template>
  <div id="app">
    <el-container>
      <!-- 导航栏 -->
      <el-header v-if="isAuthenticated">
        <el-menu
          :default-active="activeMenu"
          mode="horizontal"
          background-color="#545c64"
          text-color="#fff"
          active-text-color="#ffd04b"
          @select="handleMenuSelect"
          router
        >
          <el-menu-item index="/profile">用户信息</el-menu-item>
          <el-menu-item style="float: right" @click="handleLogout">
            <i class="el-icon-switch-button"></i>
            退出登录
          </el-menu-item>
        </el-menu>
      </el-header>

      <!-- 主内容区 -->
      <el-main>
        <router-view @login-success="handleLoginSuccess" />
      </el-main>
    </el-container>
  </div>
</template>

<script>
export default {
  name: 'App',
  data() {
    return {
      isAuthenticated: false
    }
  },
  computed: {
    activeMenu() {
      return this.$route.path
    }
  },
  created() {
    this.checkAuth()
  },
  methods: {
    checkAuth() {
      // 这里应该替换为实际的认证检查逻辑
      this.isAuthenticated = !!localStorage.getItem('token')
      if (!this.isAuthenticated && this.$route.path !== '/login' && this.$route.path !== '/register') {
        this.$router.push('/login')
      }
    },
    handleLoginSuccess() {
      this.isAuthenticated = true
      this.$router.push('/profile')
    },
    handleLogout() {
      // 这里应该替换为实际的登出逻辑
      localStorage.removeItem('token')
      this.isAuthenticated = false
      this.$router.push('/login')
    },
    handleMenuSelect(index) {
      // 菜单选择处理
      console.log('Menu selected:', index)
    }
  },
  watch: {
    $route() {
      this.checkAuth()
    }
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  height: 100vh;
}

.el-header {
  padding: 0;
}

.el-menu {
  border-right: none;
}

.el-main {
  padding: 20px;
}

/* 响应式设计 */
@media screen and (max-width: 768px) {
  .el-menu-item {
    padding: 0 10px;
  }
}
</style>
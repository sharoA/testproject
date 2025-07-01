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
          router
        >
          <el-menu-item index="/profile">用户信息</el-menu-item>
          <el-menu-item index="/" disabled>需求业务整合链优化</el-menu-item>
          <el-menu-item style="float: right" @click="handleLogout">退出登录</el-menu-item>
        </el-menu>
      </el-header>

      <!-- 主内容区 -->
      <el-main>
        <router-view/>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex';

export default {
  name: 'App',
  computed: {
    ...mapGetters(['isAuthenticated']),
    activeMenu() {
      return this.$route.path;
    }
  },
  methods: {
    ...mapActions(['logout']),
    handleLogout() {
      this.logout().then(() => {
        this.$router.push('/login');
      });
    }
  },
  watch: {
    isAuthenticated(newVal) {
      if (!newVal && this.$route.path !== '/login' && this.$route.path !== '/register') {
        this.$router.push('/login');
      }
    }
  }
};
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  height: 100%;
}

html, body {
  margin: 0;
  padding: 0;
  height: 100%;
}

.el-container {
  height: 100%;
}

.el-header {
  padding: 0;
}

.el-main {
  padding: 20px;
}

@media screen and (max-width: 768px) {
  .el-menu-item {
    padding: 0 10px;
    font-size: 12px;
  }
}
</style>
<template>
  <div class="app-container">
    <Sidebar />
    <main class="main-content">
      <QuotaList />
    </main>
  </div>
</template>

<script setup>
import Sidebar from './components/Sidebar.vue'
import QuotaList from './components/QuotaList.vue'
</script>

<style>
.app-container {
  display: flex;
  min-height: 100vh;
}

.main-content {
  flex: 1;
  padding: 20px;
}
</style>

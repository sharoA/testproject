
<template>
  <aside class="sidebar">
    <nav>
      <ul>
        <li><button @click="goBack">返回</button></li>
        <li><button @click="exportData">导出</button></li>
        <!-- Add more menu items as needed -->
      </ul>
    </nav>
  </aside>
</template>

<script setup>
const goBack = () => {
  // Implement back navigation logic
  console.log('Back button clicked');
};

const exportData = () => {
  // Implement export logic
  console.log('Export button clicked');
};
</script>

<style>
.sidebar {
  width: 200px;
  background-color: #f5f5f5;
  padding: 20px;
  height: 100vh;
  box-shadow: 2px 0 5px rgba(0,0,0,0.1);
}

.sidebar ul {
  list-style: none;
  padding: 0;
}

.sidebar li {
  margin-bottom: 10px;
}

.sidebar button {
  width: 100%;
  padding: 10px;
  background: #fff;
  border: 1px solid #ddd;
  cursor: pointer;
  text-align: left;
}

.sidebar button:hover {
  background: #f0f0f0;
}
</style>

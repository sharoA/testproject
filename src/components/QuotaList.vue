

<template>
  <div class="quota-list">
    <div class="filter-section">
      <div class="filter-row">
        <label>组织单元名称:</label>
        <input v-model="filters.orgUnit" placeholder="输入组织单元名称" />
      </div>
      <div class="filter-row">
        <label>额度名称:</label>
        <input v-model="filters.quotaName" placeholder="输入额度名称" />
      </div>
      <div class="filter-row">
        <label>额度类型:</label>
        <select v-model="filters.quotaType">
          <option value="链数额度">链数额度</option>
          <option value="云信额度">云信额度</option>
          <option value="全部">全部</option>
        </select>
      </div>
      <div class="filter-buttons">
        <button @click="applyFilters">查询</button>
        <button @click="resetFilters">重置</button>
      </div>
    </div>

    <div class="summary-section">
      <div class="summary-item">
        <span>总额度（元）:</span>
        <span>{{ summary.totalQuota }}</span>
      </div>
      <div class="summary-item">
        <span>已用额度（元）:</span>
        <span>{{ summary.usedQuota }}</span>
      </div>
      <div class="summary-item">
        <span>可用额度（元）:</span>
        <span>{{ summary.availableQuota }}</span>
      </div>
    </div>

    <table class="quota-table">
      <thead>
        <tr>
          <th>组织单元名称</th>
          <th>额度名称</th>
          <th>额度类型</th>
          <th>已分配额度（元）</th>
          <th>已用额度（元）</th>
          <th>可用额度（元）</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(item, index) in filteredData" :key="index">
          <td>{{ item.orgUnit }}</td>
          <td>{{ item.quotaName }}</td>
          <td>{{ item.quotaType }}</td>
          <td>{{ item.allocated }}</td>
          <td>{{ item.used }}</td>
          <td>{{ item.available }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';

// Mock data - will be replaced with API calls
const mockData = [
  { orgUnit: '研发部', quotaName: '研发额度', quotaType: '链数额度', allocated: 100000, used: 30000, available: 70000 },
  { orgUnit: '市场部', quotaName: '市场额度', quotaType: '云信额度', allocated: 80000, used: 20000, available: 60000 },
  { orgUnit: '财务部', quotaName: '财务额度', quotaType: '链数额度', allocated: 120000, used: 40000, available: 80000 },
  { orgUnit: '人力资源', quotaName: '人力额度', quotaType: '云信额度', allocated: 60000, used: 10000, available: 50000 },
];

const filters = ref({
  orgUnit: '',
  quotaName: '',
  quotaType: '链数额度'
});

const data = ref([...mockData]);

const filteredData = computed(() => {
  return data.value.filter(item => {
    const matchesOrgUnit = item.orgUnit.toLowerCase().includes(filters.value.orgUnit.toLowerCase());
    const matchesQuotaName = item.quotaName.toLowerCase().includes(filters.value.quotaName.toLowerCase());
    const matchesQuotaType = filters.value.quotaType === '全部' || item.quotaType === filters.value.quotaType;
    
    return matchesOrgUnit && matchesQuotaName && matchesQuotaType;
  }).sort((a, b) => {
    // Sort by orgUnit ascending, then by quotaType descending
    if (a.orgUnit !== b.orgUnit) {
      return a.orgUnit.localeCompare(b.orgUnit);
    }
    return b.quotaType.localeCompare(a.quotaType);
  });
});

const summary = computed(() => {
  return filteredData.value.reduce((acc, item) => {
    acc.totalQuota += item.allocated;
    acc.usedQuota += item.used;
    acc.availableQuota += item.available;
    return acc;
  }, { totalQuota: 0, usedQuota: 0, availableQuota: 0 });
});

function applyFilters() {
  // Filtering is handled by computed property
}

function resetFilters() {
  filters.value = {
    orgUnit: '',
    quotaName: '',
    quotaType: '链数额度'
  };
}

// Export function (to be implemented)
function exportData() {
  console.log('Exporting data to XLS');
  // Actual export implementation would go here
}

onMounted(() => {
  // Initialize with default data
});
</script>

<style scoped>
.quota-list {
  padding: 20px;
}

.filter-section {
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
  margin-bottom: 20px;
  padding: 15px;
  background: #f9f9f9;
  border-radius: 4px;
}

.filter-row {
  display: flex;
  align-items: center;
  gap: 10px;
}

.filter-buttons {
  display: flex;
  gap: 10px;
  align-items: center;
}

.summary-section {
  display: flex;
  justify-content: space-around;
  margin-bottom: 20px;
  padding: 15px;
  background: #f0f8ff;
  border-radius: 4px;
}

.summary-item {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.quota-table {
  width: 100%;
  border-collapse: collapse;
}

.quota-table th, .quota-table td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}

.quota-table th {
  background-color: #f2f2f2;
}
</style>




import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  server: {
    host: '0.0.0.0',
    port: 54608, host: '0.0.0.0',
    strictPort: true,
    hmr: {
      clientPort: 5173
    }
  }
})


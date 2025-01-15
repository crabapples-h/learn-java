import {defineConfig} from 'vite'
import react from '@vitejs/plugin-react'
import path from 'path';
// vite.config.js 或 vite.config.ts
// https://vite.dev/config/
export default defineConfig({
  plugins: [react(),
  ],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src'), // 设置 '@' 指向 './src' 目录
      'components': path.resolve(__dirname, './src/components'), // 设置 'components' 指向 './src/components' 目录
      // 可以继续添加更多别名
    },
    css: {
      preprocessorOptions: {
        less: {
          javascriptEnabled: true, // 启用 JavaScript 支持（某些组件库可能需要）
        },
      },
    },
  },
})

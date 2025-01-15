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
  },
  css: {
    preprocessorOptions: {
      less: {
        javascriptEnabled: true, // 启用 JavaScript 支持
      },
    },
  },
  server: {
    // 让浏览器 overlay 同时显示警告和错误
    overlay: {
      warnings: false,
      errors: false
    },
    // open: process.platform === "darwin",
    disableHostCheck: false,
    host: "0.0.0.0",
    // port: 8080,
    // https: false,
    hotOnly: false, // See https://github.com/vuejs/vue-cli/blob/dev/docs/cli-service.md#configuring-proxy
    proxy: {
      '/videoability': {
        target: 'http://localhost:9999/', // 接口的域名
        // secure: false,  // 如果是https接口，需要配置这个参数
        changeOrigin: true, // 如果接口跨域，需要进行这个参数配置
        // pathRewrite: {
        // 把 /api 开头的路径替换为 ''
        //     '^/api': ''
        // }
      },
      '/ws': {
        target: 'http://localhost:9093/', // 接口的域名
        ws: true,
        // secure: false,  // 如果是https接口，需要配置这个参数
        changeOrigin: true, // 如果接口跨域，需要进行这个参数配置
        // pathRewrite: {
        // 把 /api 开头的路径替换为 ''
        //     '^/api': ''
        // }
      },
      '/api': {
        target: 'http://localhost:9093/', // 接口的域名
        // secure: false,  // 如果是https接口，需要配置这个参数
        changeOrigin: true, // 如果接口跨域，需要进行这个参数配置
        // pathRewrite: {
        // 把 /api 开头的路径替换为 ''
        //     '^/api': ''
        // }
      },
      '/open-api': {
        target: 'http://localhost:80/', // 接口的域名
        // secure: false,  // 如果是https接口，需要配置这个参数
        changeOrigin: true, // 如果接口跨域，需要进行这个参数配置
        pathRewrite: {
          // 把 /open-api 开头的路径替换为 '/open'
          '^/open-api': '/open'
        }
      },
      '/file': {
        target: 'http://localhost:9093/', // 接口的域名
        // secure: false,  // 如果是https接口，需要配置这个参数
        changeOrigin: true, // 如果接口跨域，需要进行这个参数配置
      }
    },
    // string | Object
    // before: app => {}

  },

})

import { fileURLToPath, URL } from 'node:url'
import { defineConfig, loadEnv } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

export default defineConfig(({ mode }) => {
  const env = loadEnv(mode, process.cwd(), '')

  return {
    plugins: [
      vue(),
      vueDevTools(),
    ],
    resolve: {
      alias: {
        '@': fileURLToPath(new URL('./src', import.meta.url)),
        '@assets': fileURLToPath(new URL('./src/assets', import.meta.url)),
        '@comp': fileURLToPath(new URL('./src/components', import.meta.url)),
        '@public': fileURLToPath(new URL('./public', import.meta.url)),
      },
    },
    build: {
      outDir: 'dist',
      sourcemap: false,
    },
    css: {
      preprocessorOptions: {
        less: {
          javascriptEnabled: true,
        },
      },
    },
    server: {
      host: '0.0.0.0',
      port: 5173,
      proxy: {
        '/api': {
          target: env.VITE_APP_API_BASE_URL || 'http://localhost:9093/',
          secure: false,
          changeOrigin: true,
          ws: true,
        },
        '/open-api': {
          target: env.VITE_APP_OPEN_API_BASE_URL || 'http://localhost:80/',
          secure: false,
          changeOrigin: true,
          rewrite: path => path.replace(/^\/open-api/, '/open'),
        },
      },
    },
  }
})

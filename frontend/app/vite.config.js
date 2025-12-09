import {fileURLToPath, URL} from 'node:url'
import {defineConfig, loadEnv} from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

// https://vite.dev/config/
export default defineConfig(({mode}) => {
    const env = loadEnv(mode, process.cwd(), '')
    console.log("启动环境:", env.VITE_SOME_KEY);
    console.log("启动环境:", env.VITE_APP_API_BASE_URL);
    return {
        plugins: [
            vue(),
            vueDevTools(),
        ],
        resolve: {
            alias: {
                '@': fileURLToPath(new URL('./src', import.meta.url))
            },
        },
        server: {
            host: '0.0.0.0',
            port: 5173,
            proxy: {
                '/api': {
                    target: env.VITE_APP_API_BASE_URL,
                    ws: true,
                    secure: false,
                    changeOrigin: true,
                    // rewrite: (path) => path.replace(/^\/api/, '')
                }
            }
        }
    }
})

import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.tsx'
// import axios from 'axios'
// import request from '@/utils/request'
// antd图标库
// import 'public/iconfont/icon-antd.json'
// lolita图标库
// import 'public/iconfont/icon-lolita.json'
// 可爱图标库
// import 'public/iconfont/icon-cute.json'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <App />
  </StrictMode>,
)

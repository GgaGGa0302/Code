import React from 'react';
import ReactDOM from 'react-dom/client';
// 1. TanStack Query 모듈 불러오기
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import App from './app/App';
import './index.css';

// 2. QueryClient 인스턴스 생성
const queryClient = new QueryClient();

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    {/* 3. QueryClientProvider로 App 감싸기 (client 속성 필수) */}
    <QueryClientProvider client={queryClient}>
      <App />
    </QueryClientProvider>
  </React.StrictMode>
);
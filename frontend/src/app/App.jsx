import React from 'react';
import { BrowserRouter, Routes, Route, Navigate, useLocation } from 'react-router-dom';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { AuthProvider, useAuth } from '../entities/UserEntity';

import { SidebarWidget } from '../widgets/SidebarWidget';
import { HeaderWidget } from '../widgets/HeaderWidget';

import { AuthPage } from '../pages/AuthPage';
import { DashboardPage } from '../pages/DashboardPage';
import { TransferPage } from '../pages/TransferPage';
import { HistoryPage } from '../pages/HistoryPage';

import { ToastContainer } from '../shared/Toast';

const queryClient = new QueryClient();

// 인증된 사용자만 접근 가능한 페이지를 위한 보호막(Guard) 컴포넌트
const ProtectedRoute = ({ children }) => {
  const { user } = useAuth();
  const location = useLocation();
  if (!user.isLoggedIn) {
    return <Navigate to="/auth" state={{ from: location }} replace />;
  }
  return children;
};

// 전체 앱의 레이아웃과 라우팅 구조를 정의
const AppLayout = () => {
  const { user } = useAuth();

  // 로그인 여부에 따라 다른 레이아웃을 보여줌
  if (!user.isLoggedIn) {
    return (
      <div className="bg-slate-50 min-h-screen flex items-center justify-center p-4">
        <Routes>
          <Route path="/auth" element={<AuthPage />} />
          <Route path="*" element={<Navigate to="/auth" />} />
        </Routes>
      </div>
    );
  }

  return (
    <div className="h-screen flex overflow-hidden bg-slate-50">
      <SidebarWidget />
      <div className="flex-1 flex flex-col min-h-0 overflow-hidden">
        <HeaderWidget />
        <main className="flex-1 p-6 overflow-y-auto">
          <div className="max-w-7xl mx-auto w-full">
            <Routes>
              <Route path="/dashboard" element={<ProtectedRoute><DashboardPage /></ProtectedRoute>} />
              <Route path="/transfer" element={<ProtectedRoute><TransferPage /></ProtectedRoute>} />
              <Route path="/history" element={<ProtectedRoute><HistoryPage /></ProtectedRoute>} />
              <Route path="*" element={<Navigate to="/dashboard" />} />
            </Routes>
          </div>
        </main>
      </div>
    </div>
  );
};

export default function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <BrowserRouter>
        <AuthProvider>
          <AppLayout />
          <ToastContainer />
        </AuthProvider>
      </BrowserRouter>
    </QueryClientProvider>
  );
}
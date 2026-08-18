import React from 'react';
import { AuthFeature } from '../features/AuthFeature';

export const AuthPage = () => {
  return (
    <div className="fade-in space-y-6 w-full max-w-md py-12">
      <div className="text-center mb-6">
        <div className="w-16 h-16 bg-brand-600 text-white rounded-2xl mx-auto flex items-center justify-center text-2xl font-bold shadow-xl shadow-brand-500/30 mb-3">
          <i className="fa-solid fa-vault"></i>
        </div>
        <h1 className="text-2xl font-bold text-slate-900">스마트 뱅크</h1>
        <p className="text-xs text-slate-500 mt-1">통합 디지털 계좌 관리 플랫폼에 로그인하세요</p>
      </div>
      <AuthFeature />
    </div>
  );
};
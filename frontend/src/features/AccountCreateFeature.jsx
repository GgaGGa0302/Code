import React, { useState } from 'react';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { accountApi } from '../entities/AccountEntity';
import { useAuth } from '../entities/UserEntity';
import { showToast } from '../shared/Toast';

export const AccountCreateModal = ({ isOpen, onClose }) => {
  const { user } = useAuth();
  const queryClient = useQueryClient();
  const [balance, setBalance] = useState(10000);

  const { mutate: createAccount, isLoading } = useMutation({
    mutationFn: accountApi.createAccount,
    onSuccess: () => {
      showToast.success('신규 계좌가 성공적으로 개설되었습니다!');
      queryClient.invalidateQueries({ queryKey: ['accounts'] }); // 계좌 목록 데이터를 다시 불러오도록 무효화
      onClose();
    },
    onError: () => {
      showToast.error('계좌 개설에 실패했습니다.');
    },
  });

  if (!isOpen) return null;

  const handleSubmit = async (e) => {
    e.preventDefault();
    createAccount({ userId: user.userId, balance: Number(balance) });
  };

  return (
    <div className="fixed inset-0 bg-slate-900/50 backdrop-blur-xs z-50 flex items-center justify-center p-4">
      <div className="bg-white rounded-2xl max-w-sm w-full p-6 shadow-2xl space-y-4 fade-in">
        <div className="flex items-center justify-between border-b border-slate-100 pb-3">
          <h3 className="font-bold text-slate-800 text-sm">신규 계좌 등록</h3>
          <button onClick={onClose} className="text-slate-400 hover:text-slate-600">
            <i className="fa-solid fa-xmark"></i>
          </button>
        </div>
        <form onSubmit={handleSubmit} className="space-y-4">
        
          <div>
            <label className="block text-xs font-semibold text-slate-600 mb-1">초기 입금 시드 금액 (원)</label>
            <input type="number" required min="1000" value={balance} onChange={(e) => setBalance(e.target.value)} className="w-full px-3 py-2 border border-slate-200 rounded-xl text-sm focus:outline-none focus:ring-2 focus:ring-brand-500/30" />
          </div>
          <div className="pt-2 flex space-x-2">
            <button type="button" onClick={onClose} className="flex-1 py-2.5 border border-slate-200 rounded-xl text-xs font-semibold text-slate-600 hover:bg-slate-50">
              취소
            </button>
            <button type="submit" disabled={isLoading} className="flex-1 py-2.5 bg-brand-600 text-white rounded-xl text-xs font-semibold hover:bg-brand-700 shadow-md disabled:opacity-50">
              {isLoading ? '처리 중...' : '등록하기'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};
import React, { useState } from 'react';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { transferApi } from '../entities/TransferEntity';
import { showToast } from '../shared/Toast';

export const TransferExecuteForm = ({ accounts }) => {
  const [mode, setMode] = useState('transfer');
  const [fromAccount, setFromAccount] = useState('');
  const [toAccount, setToAccount] = useState('');
  const [amount, setAmount] = useState('');
  const queryClient = useQueryClient();

  const activeAccounts = accounts.filter((a) => a.accountStatus === 'ACTIVE');

  const { mutate: executeTransaction, isLoading } = useMutation({
    mutationFn: (variables) => {
      // mode에 따라 다른 API 호출
      if (mode === 'transfer') return transferApi.transfer(variables);
      if (mode === 'deposit') return transferApi.deposit(variables);
      return transferApi.withdraw(variables);
    },
    onSuccess: () => {
      showToast.success('거래가 성공적으로 처리되었습니다.');
      // 계좌 목록과 거래 내역 데이터를 새로고침하도록 무효화
      queryClient.invalidateQueries({ queryKey: ['accounts'] });
      queryClient.invalidateQueries({ queryKey: ['transfers'] });
      // 폼 초기화
      setFromAccount('');
      setToAccount('');
      setAmount('');
    },
    onError: (err) => { // 'error' 타입 인자는 더 이상 필요 없습니다.
      showToast.error(err.response?.data?.message || '거래 처리 중 오류가 발생했습니다.');
    },
  });

  const handleSubmit = (e) => {
    e.preventDefault();
    const variables = { fromAccount, toAccount, amount: Number(amount) };
    executeTransaction(variables);
  };

  return (
    <div className="max-w-2xl mx-auto bg-white border border-slate-200 rounded-2xl p-6 shadow-sm">
      <div className="flex bg-slate-100 p-1 rounded-xl mb-6 text-xs font-semibold">
        <button onClick={() => setMode('transfer')} className={`flex-1 py-2.5 rounded-lg text-center transition ${mode === 'transfer' ? 'bg-white text-slate-900 font-bold shadow-xs' : 'text-slate-500'}`}>
          계좌 이체
        </button>
        <button onClick={() => setMode('deposit')} className={`flex-1 py-2.5 rounded-lg text-center transition ${mode === 'deposit' ? 'bg-white text-slate-900 font-bold shadow-xs' : 'text-slate-500'}`}>
          입금
        </button>
        <button onClick={() => setMode('withdraw')} className={`flex-1 py-2.5 rounded-lg text-center transition ${mode === 'withdraw' ? 'bg-white text-slate-900 font-bold shadow-xs' : 'text-slate-500'}`}>
          출금
        </button>
      </div>

      <form onSubmit={handleSubmit} className="space-y-4">
        {mode !== 'deposit' && (
          <div>
            <label className="block text-xs font-semibold text-slate-600 mb-1">출금 계좌선택</label>
            <select value={fromAccount} onChange={(e) => setFromAccount(e.target.value)} required className="w-full px-4 py-2.5 rounded-xl border border-slate-200 text-sm focus:outline-none">
              <option value="">계좌를 선택하세요</option>
              {activeAccounts.map((a) => (
                <option key={a.accountId} value={a.accountNumber}>{a.accountNumber} (잔액: {a.balance?.toLocaleString()}원)</option>
              ))}
            </select>
          </div>
        )}

        {mode !== 'withdraw' && (
          <div>
            <label className="block text-xs font-semibold text-slate-600 mb-1">입금 대상 계좌번호</label>
            {mode === 'deposit' ? (
              <select value={toAccount} onChange={(e) => setToAccount(e.target.value)} required className="w-full px-4 py-2.5 rounded-xl border border-slate-200 text-sm focus:outline-none">
                <option value="">계좌를 선택하세요</option>
                {activeAccounts.map((a) => (
                  <option key={a.accountId} value={a.accountNumber}>{a.accountNumber} (잔액: {a.balance?.toLocaleString()}원)</option>
                ))}
              </select>
            ) : (
              <input type="text" required placeholder="예: 110-001-2222" value={toAccount} onChange={(e) => setToAccount(e.target.value)} className="w-full px-4 py-2.5 rounded-xl border border-slate-200 text-sm focus:outline-none" />
            )}
          </div>
        )}

        <div>
          <label className="block text-xs font-semibold text-slate-600 mb-1">금액 (원)</label>
          <input type="number" min="100" required placeholder="5000" value={amount} onChange={(e) => setAmount(e.target.value)} className="w-full px-4 py-2.5 rounded-xl border border-slate-200 text-sm focus:outline-none" />
        </div>

        <button type="submit" disabled={isLoading} className="w-full bg-brand-600 hover:bg-brand-700 text-white font-semibold py-3 rounded-xl shadow-md text-sm transition disabled:opacity-50">
          {isLoading ? '처리 중...' : mode === 'transfer' ? '이체 실행' : mode === 'deposit' ? '현금 입금' : '현금 출금'}
        </button>
      </form>
    </div>
  );
};
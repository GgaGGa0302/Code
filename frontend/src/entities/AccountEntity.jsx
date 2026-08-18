import React from 'react';
import { api } from '../shared/api';

export const accountApi = {
  getAccounts: async (userId) => {
    const res = await api.get(`/accounts/${userId}`); // The API returns { status, data: [...] }
    return res.data.data;
  },
  createAccount: async (data) => {
    const res = await api.post('/accounts', data); // The API returns { status, data: { ... } }
    return res.data.data;
  },
  closeAccount: async (accountId) => {
    const res = await api.delete(`/accounts/${accountId}`);
    return res.data;
  },
};

export const AccountCard = ({ account, onCloseAction }) => {
  const isClosed = account.accountStatus === 'CLOSED';

  return (
    <div className={`p-5 rounded-2xl border transition relative flex flex-col justify-between ${
      isClosed ? 'bg-slate-50 border-slate-200 opacity-60' : 'bg-white border-slate-200 hover:border-brand-500 hover:shadow-md'
    }`}>
      <div>
        <div className="flex items-center justify-between mb-3">
          <span className="text-xs font-bold text-slate-500 tracking-wider font-mono">{account.accountNumber}</span>
          <span className={`text-[10px] px-2 py-0.5 rounded-full font-bold ${
            isClosed ? 'bg-slate-200 text-slate-600' : 'bg-emerald-100 text-emerald-700'
          }`}>{account.accountStatus}</span>
        </div>
        <div className="text-xs text-slate-400">잔액</div>
        <div className="text-xl font-bold text-slate-900 mt-1">{account.balance.toLocaleString()} 원</div>
      </div>

      <div className="mt-5 pt-3 border-t border-slate-100 flex items-center justify-between text-xs">
        <span className="text-[10px] text-slate-400">{account.createdAt ? account.createdAt.split(' ')[0] : '2026-08-14'}</span>
        {!isClosed ? onCloseAction : <span className="text-slate-400 italic text-[11px]">해지 완료됨</span>}
      </div>
    </div>
  );
};
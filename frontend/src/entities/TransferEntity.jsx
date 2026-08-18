import React from 'react';
import { api } from '../shared/api';

export const transferApi = {
  deposit: async (data) => {
    const res = await api.post('/transfers/deposit', data);
    return res.data;
  },
  withdraw: async (data) => {
    const res = await api.post('/transfers/withdraw', data);
    return res.data;
  },
  transfer: async (data) => {
    const res = await api.post('/transfers', data);
    return res.data;
  },
  getTransfers: async () => {
    const res = await api.get('/transfers');
    return res.data;
  },
};

export const TransferTableRow = ({ transfer }) => {
  const isFailed = transfer.status === 'FAILED';

  return (
    <tr className="hover:bg-slate-50 transition">
      <td className="p-3.5 font-mono text-[11px] text-slate-500">{transfer.transferId?.substring(0, 8)}...</td>
      <td className="p-3.5 font-bold">
        <span className={`px-2 py-0.5 rounded text-[10px] ${
          transfer.transactionType === 'DEPOSIT' ? 'bg-emerald-100 text-emerald-800' :
          transfer.transactionType === 'WITHDRAW' ? 'bg-slate-200 text-slate-800' : 'bg-indigo-100 text-indigo-800'
        }`}>{transfer.transactionType}</span>
      </td>
      <td className="p-3.5 font-mono">{transfer.fromAccount || '-'}</td>
      <td className="p-3.5 font-mono">{transfer.toAccount || '-'}</td>
      <td className="p-3.5 text-right font-bold text-slate-900">{transfer.amount?.toLocaleString()} 원</td>
      <td className="p-3.5 text-center">
        <span className={`px-2 py-0.5 rounded-full text-[10px] font-bold ${
          isFailed ? 'bg-rose-100 text-rose-700' : 'bg-emerald-100 text-emerald-700'
        }`}>{transfer.status}</span>
      </td>
      <td className="p-3.5 text-right text-slate-400 text-[11px]">{transfer.requestedAt}</td>
    </tr>
  );
};
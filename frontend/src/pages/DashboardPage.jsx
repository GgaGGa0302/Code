import React, { useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import { AccountCard, accountApi } from '../entities/AccountEntity';
import { AccountCreateModal } from '../features/AccountCreateFeature';
import { AccountCloseButton } from '../features/AccountCloseFeature';
import { useAuth } from '../entities/UserEntity';

export const DashboardPage = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const { user } = useAuth();

  const { data: accounts = [], isLoading, isError } = useQuery({
    queryKey: ['accounts', user.userId], 
    queryFn: () => accountApi.getAccounts(user.userId),
    enabled: !!user.userId,
    retry: 1, // API 실패 시 재시도를 1번만 하도록 설정
  });

  const activeAccounts = accounts.filter((a) => a.accountStatus === 'ACTIVE');
  const totalBalance = activeAccounts.reduce((sum, a) => sum + (a.balance || 0), 0);

  return (
    <div className="fade-in space-y-6 w-full">
      <div className="grid grid-cols-1 md:grid-cols-3 gap-5">
        <div className="bg-gradient-to-br from-navy-800 to-navy-700 text-white rounded-2xl p-6 shadow-md relative overflow-hidden">
          <div className="absolute right-4 top-4 text-slate-600/30 text-5xl font-black">
            <i className="fa-solid fa-coins"></i>
          </div>
          <span className="text-xs text-slate-300 font-medium">총 자산 합계</span>
          <div className="text-2xl font-bold mt-2 tracking-tight">{totalBalance.toLocaleString()} 원</div>
          <div className="mt-4 flex items-center text-[11px] text-emerald-400">
            <i className="fa-solid fa-shield-halved mr-1.5"></i>
            <span>실시간 잔액 동기화 완료</span>
          </div>
        </div>

        <div className="bg-white border border-slate-200 rounded-2xl p-6 shadow-sm flex flex-col justify-between">
          <div>
            <span className="text-xs text-slate-500 font-medium">보유 계좌 수</span>
            <div className="text-2xl font-bold text-slate-800 mt-2">{activeAccounts.length} 개</div>
          </div>
          <div className="text-[11px] text-slate-400">
            상태: <span className="text-emerald-600 font-semibold">ACTIVE</span> 관리 중
          </div>
        </div>

        <div className="bg-white border border-slate-200 rounded-2xl p-6 shadow-sm flex flex-col justify-between">
          <div>
            <span className="text-xs text-slate-500 font-medium">신규 계좌 발급</span>
            <p className="text-xs text-slate-500 mt-1">원클릭으로 통합 계좌를 개설하세요.</p>
          </div>
          <button onClick={() => setIsModalOpen(true)} className="mt-3 w-full bg-brand-50 hover:bg-brand-100 text-brand-600 border border-brand-200 font-semibold py-2.5 rounded-xl text-xs flex items-center justify-center space-x-2 transition">
            <i className="fa-solid fa-plus"></i>
            <span>신규 계좌 개설</span>
          </button>
        </div>
      </div>

      <div className="bg-white border border-slate-200 rounded-2xl p-6 shadow-sm space-y-4">
        <div className="flex items-center justify-between border-b border-slate-100 pb-4">
          <div>
            <h3 className="font-bold text-slate-800">보유 계좌 목록</h3>
            <p className="text-xs text-slate-400 mt-0.5">실시간 잔액 및 상태를 조회할 수 있습니다.</p>
          </div>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4 min-h-[100px] flex items-center justify-center">
          {isLoading ? (
            <div className="text-center text-slate-500 col-span-full">계좌 정보를 불러오는 중...</div>
          ) : isError ? (
            <div className="text-center text-rose-500 col-span-full">계좌 정보를 불러오는 데 실패했습니다.</div>
          ) : activeAccounts.length > 0 ? (
            activeAccounts.map((acc) => (
              <AccountCard key={acc.accountId} account={acc} onCloseAction={<AccountCloseButton accountId={acc.accountId} />} />
            ))
          ) : (
            <div className="text-center text-slate-400 col-span-full">보유 계좌가 없습니다.</div>
          )}
        </div>
      </div>

      <AccountCreateModal isOpen={isModalOpen} onClose={() => setIsModalOpen(false)} />
    </div>
  );
};
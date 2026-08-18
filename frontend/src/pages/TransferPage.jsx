import React from 'react';
import { useQuery } from '@tanstack/react-query';
import { useAuth } from '../entities/UserEntity';
import { accountApi } from '../entities/AccountEntity';
import { TransferExecuteForm } from '../features/TransferExecuteFeature';

export const TransferPage = () => {
  const { user } = useAuth();
  const { data: accounts = [], isLoading, isError } = useQuery({
    queryKey: ['accounts', user.userId], // 쿼리 키
    queryFn: () => accountApi.getAccounts(user.userId), // 데이터 가져오는 함수
    enabled: !!user.userId,
    retry: 1, // API 실패 시 재시도를 1번만 하도록 설정
  });

  const activeAccounts = accounts.filter((a) => a.accountStatus === 'ACTIVE');

  return (
    <div className="fade-in space-y-6 w-full">
      {isLoading ? (
        <div className="text-center text-slate-500 py-12">계좌 정보를 불러오는 중...</div>
      ) : isError ? (
        <div className="text-center text-rose-500 py-12">계좌 정보를 불러오는 데 실패했습니다.</div>
      ) : activeAccounts.length === 0 ? (
        <div className="text-center text-slate-400 py-12">활성화된 계좌가 없습니다.</div>
      ) : (
        <TransferExecuteForm accounts={activeAccounts} />
      )}
    </div>
  );
};
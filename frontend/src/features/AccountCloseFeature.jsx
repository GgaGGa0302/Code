import React from 'react';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { accountApi } from '../entities/AccountEntity';
import { showToast } from '../shared/Toast';

export const AccountCloseButton = ({ accountId }) => {
  const queryClient = useQueryClient();

  const { mutate: closeAccount, isLoading } = useMutation({
    mutationFn: accountApi.closeAccount,
    onSuccess: () => {
      showToast.success('계좌가 해지(CLOSED) 되었습니다.');
      queryClient.invalidateQueries({ queryKey: ['accounts'] });
    },
    onError: (err) => {
      showToast.error(err.response?.data?.message || '계좌 해지에 실패했습니다.');
    },
  });

  const handleClose = () => {
    if (!window.confirm('정말 해당 계좌를 해지하시겠습니까?')) return;
    closeAccount(accountId);
  };

  return (
    <button onClick={handleClose} disabled={isLoading} className="text-rose-500 hover:text-rose-700 font-semibold text-[11px] flex items-center space-x-1 disabled:opacity-50">
      {isLoading ? <><i className="fa-solid fa-spinner fa-spin"></i><span>처리 중...</span></> : <><i className="fa-regular fa-trash-can"></i><span>해지 (Delete)</span></>}
    </button>
  );
};
import React, { useState, useEffect } from 'react';

const toastInternal = (message, type = 'success') => {
  window.dispatchEvent(new CustomEvent('app-toast', { detail: { message, type } }));
};

export const showToast = {
  success: (message) => toastInternal(message, 'success'),
  error: (message) => toastInternal(message, 'error'),
};

export const ToastContainer = () => {
  const [toasts, setToasts] = useState([]);

  useEffect(() => {
    const handleToast = (e) => {
      const id = Date.now();
      setToasts((prev) => [...prev, { id, ...e.detail }]);
      setTimeout(() => {
        setToasts((prev) => prev.filter((t) => t.id !== id));
      }, 3000);
    };

    window.addEventListener('app-toast', handleToast);
    return () => window.removeEventListener('app-toast', handleToast);
  }, []);

  return (
    <div className="fixed bottom-5 right-5 z-50 space-y-2">
      {toasts.map((toast) => (
        <div
          key={toast.id}
          className={`px-4 py-3 rounded-xl text-xs font-bold text-white shadow-xl transition-all flex items-center space-x-2 ${
            toast.type === 'error' ? 'bg-rose-600' : 'bg-slate-900'
          }`}
        >
          <i className={`fa-solid ${toast.type === 'error' ? 'fa-circle-xmark' : 'fa-circle-check'}`}></i>
          <span>{toast.message}</span>
        </div>
      ))}
    </div>
  );
};
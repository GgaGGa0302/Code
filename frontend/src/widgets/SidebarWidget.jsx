import React from 'react';
import { NavLink } from 'react-router-dom';

const NavItem = ({ to, icon, label }) => {
  const baseClasses = "w-full flex items-center space-x-3 px-4 py-3 rounded-xl text-sm font-medium transition-all";
  const activeClasses = "bg-brand-600 text-white";
  const inactiveClasses = "text-slate-300 hover:bg-slate-800";

  return (
    <NavLink to={to} className={({ isActive }) => `${baseClasses} ${isActive ? activeClasses : inactiveClasses}`}>
      <i className={`${icon} w-5 text-center text-slate-400`}></i>
      <span>{label}</span>
    </NavLink>
  );
};

export const SidebarWidget = () => {
  return (
    <aside className="w-64 bg-navy-800 text-white flex-shrink-0 flex flex-col justify-between shadow-xl">
      <div>
        <div className="p-6 border-b border-slate-700/60">
          <div className="flex items-center space-x-3">
            <div className="w-10 h-10 rounded-xl bg-brand-600 flex items-center justify-center text-white font-bold text-xl shadow-lg shadow-brand-500/30">
              <i className="fa-solid fa-vault"></i>
            </div>
            <div>
              <h1 className="font-bold text-lg leading-tight tracking-wide">스마트 뱅크</h1>
              <span className="text-xs text-slate-400 font-medium">Digital Banking</span>
            </div>
          </div>
        </div>

        <nav className="p-4 space-y-1">
          <NavItem to="/dashboard" icon="fa-solid fa-wallet" label="계좌 관리 메인" />
          <NavItem to="/transfer" icon="fa-solid fa-money-bill-transfer" label="입출금 / 이체 실행" />
          <NavItem to="/history" icon="fa-solid fa-clock-rotate-left" label="거래 & 알림 내역" />
        </nav>
      </div>

      <div className="p-4 border-t border-slate-700/60 text-center">
        <p className="text-xs text-slate-400">© 2026 Smart Bank Inc.</p>
      </div>
    </aside>
  );
};
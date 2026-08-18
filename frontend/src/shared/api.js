import axios from 'axios';

export const api = axios.create({
  // 환경변수가 없으면 기본값으로 로컬 백엔드 주소를 사용합니다.
  baseURL: process.env.REACT_APP_API_URL || 'http://localhost:8080/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem('authToken');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});
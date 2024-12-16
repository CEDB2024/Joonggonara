import React, { useEffect, useState } from "react";
import { Navigate } from "react-router-dom";
import authService from "../services/AuthService";

const PrivateRoute = ({ children }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const checkAuth = async () => {
      const hasToken = authService.isAuthenticated(); // 로컬 스토리지에서 토큰 존재 여부 확인

      if (!hasToken) {
        alert("로그인이 필요합니다."); // 메시지 표시
        setIsAuthenticated(false);
        setLoading(false);
        return;
      }

      // 서버에서 토큰 유효성 검증
      const valid = await authService.verifyToken();
      if (!valid) {
        alert("로그인이 만료되었습니다."); // 메시지 표시
      }
      setIsAuthenticated(valid);
      setLoading(false);
    };

    checkAuth();
  }, []);

  if (loading) {
    return <div>Loading...</div>; // 로딩 중 표시
  }

  return isAuthenticated ? children : <Navigate to="/login" replace />;
};

export default PrivateRoute;

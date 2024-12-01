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
        setIsAuthenticated(false);
        setLoading(false);
        return;
      }

      // 서버에서 토큰 유효성 검증
      const valid = await authService.verifyToken();
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

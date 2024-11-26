import React from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import Login from "./components/login";
import Register from "./components/register";
import PrivateRoute from "./components/privaterouter";


function App() {
  return (
    <Router>
      <Routes>
      <Route path="/" element={<Navigate to="/login" />} />

        {/* 로그인 및 회원가입은 누구나 접근 가능 */}
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />

        {/* 메인 페이지는 인증된 사용자만 접근 가능 */}
        <Route
          path="/main"
          element={
            <PrivateRoute>
              <div>Welcome to the Main Page!</div>
            </PrivateRoute>
          }
        />
      </Routes>
    </Router>
  );
}

export default App;

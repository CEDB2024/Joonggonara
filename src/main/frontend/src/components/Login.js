import React, {useState, useEffect} from "react";
import {useNavigate} from "react-router-dom";
import authService from "../services/AuthService";
import UserService from "../services/UserService";
import Layout from "../global/Layout";
import './login.css';

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  // 로그아웃 함수
  const handleLogout = () => {
    authService.logout();
    navigate("/login"); // 로그인 페이지로 이동
  };

  // 로그인 처리
  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await authService.login({email, password});
      if (response.success) {
        console.log(response);
        localStorage.setItem("userId", response.userId); // userId 저장
        localStorage.setItem("token", response.token); // 인증 토큰 저장

        // 역할 확인
        const role = await UserService.getRole(response.userId);
        if (role === "admin") {
          navigate("/admin"); // admin 페이지로 이동
        } else {
          navigate("/main"); // 메인 페이지로 이동
        }
      } else {
        setError("Invalid email or password");
      }
    } catch (err) {
      setError("Login failed. Please try again.");
    }
  };

  // 컴포넌트가 처음 로드될 때 로그인 상태 확인
  useEffect(() => {
    if (authService.isAuthenticated()) {
      navigate("/main"); // 로그인 상태면 메인 페이지로 이동
    }
  }, [navigate]);

  return (
      <Layout>
        <div className="login-container">
          <div className="login-box">
            <h2>로그인</h2>
            <form onSubmit={handleLogin}>
              <div>
                <label>Email</label>
                <input
                    type="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                />
              </div>
              <div>
                <label>Password</label>
                <input
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />
              </div>
              {error && <p className="error-message">{error}</p>}
              <button type="submit">Login</button>
            </form>
            <p>
              계정이 없으신가요? <a href="/Register">Sign up</a>
            </p>
          </div>
        </div>
      </Layout>
  );
}

export default Login;

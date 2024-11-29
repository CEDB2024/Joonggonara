import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import authService from "../services/authService";

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [userEmail, setUserEmail] = useState(null); // 로그인된 이메일
  const navigate = useNavigate();

  // 로그아웃 함수
  const handleLogout = () => {
    authService.logout();
    setUserEmail(null); // 이메일 상태 초기화
    navigate("/login"); // 로그인 페이지로 이동
  };

  // 로그인 처리
  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await authService.login({ email, password });
      if (response.success) {
        localStorage.setItem("token", response.token); // 인증 토큰 저장
        fetchUserEmail(); // 로그인 성공 후 사용자 이메일 가져오기
        navigate("/main"); // 메인 페이지로 이동
      } else {
        setError("Invalid email or password");
      }
    } catch (err) {
      setError("Login failed. Please try again.");
    }
  };

  // 사용자 이메일 가져오기
  const fetchUserEmail = async () => {
    try {
      const email = await authService.getEmail();
      setUserEmail(email); // 사용자 이메일 상태 저장
    } catch (err) {
      console.error("Failed to fetch email:", err);
    }
  };

  // 컴포넌트가 처음 로드될 때 이메일 가져오기 (로그인 상태 확인)
  useEffect(() => {
    if (authService.isAuthenticated()) {
      fetchUserEmail();
    }
  }, []);

  return (
      <div>
        {userEmail ? (
            // 로그인된 상태
            <div>
              <h2>Welcome, {userEmail}</h2>
              <button onClick={handleLogout}>Logout</button>
            </div>
        ) : (
            // 로그인되지 않은 상태
            <div>
              <h2>Login</h2>
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
                {error && <p style={{ color: "red" }}>{error}</p>}
                <button type="submit">Login</button>
              </form>
              <p>
                Don't have an account? <a href="/register">Sign up</a>
              </p>
            </div>
        )}
      </div>
  );
}

export default Login;

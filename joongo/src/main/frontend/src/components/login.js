import React, { useState, useEffect } from "react";
import authService from "../services/authService"; // Auth 관련 서비스
import { useNavigate } from "react-router-dom";

const Login = () => {
  const [formData, setFormData] = useState({ email: "", password: "" });
  const [error, setError] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    // 컴포넌트가 마운트될 때 토큰 확인
    const checkToken = async () => {
      const token = localStorage.getItem("token");
      if (token) {
        const isValid = await authService.verifyToken(token);
        if (isValid) {
          // 유효한 토큰이 있으면 메인 화면으로 이동
          navigate("/main");
        }
      }
    };
    checkToken();
  }, [navigate]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await authService.login(formData);
      if (response.token) {
        // 토큰 저장
        localStorage.setItem("token", response.token);

        // 메인 화면으로 이동
        navigate("/main");
      } else {
        setError("Invalid email or password");
      }
    } catch (err) {
      setError("An error occurred during login.");
      console.error(err);
    }
  };

  return (
    <div>
      <h2>Login</h2>
      <form onSubmit={handleLogin}>
        <div>
          <label>Email</label>
          <input
            type="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            required
          />
        </div>
        <div>
          <label>Password</label>
          <input
            type="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
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
  );
};

export default Login;

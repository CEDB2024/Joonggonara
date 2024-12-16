import React, { useState } from "react";
import authService from "../services/AuthService";
import { useNavigate } from "react-router-dom";
import "./register.css"; // CSS 추가

function Register() {
  const [formData, setFormData] = useState({
    email: "",
    userPassword: "",
    userName: "",
    nickname: "",
    tel_1: "",
    tel_2: "",
    location: "",
  });

  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleRegister = async (e) => {
    e.preventDefault();
    try {
      console.log(formData);
      const response = await authService.register(formData);
      console.log("Server Response (Full):", response);
      console.log("Server Response (Data):", response.data);
      if (response.success) {
        alert("Registration successful!");
        navigate("/login");
      } else {
        setError("Registration failed. Please try again.");
      }
    } catch (err) {
      setError("Error occurred during registration.");
    }
  };

  return (
    <div className="register-container">
      <div className="register-box">
        <h2>회원 가입</h2>
        <form onSubmit={handleRegister}>
          <div>
            <label>Username</label>
            <input
              type="text"
              name="userName"
              value={formData.userName}
              onChange={handleChange}
              required
            />
          </div>
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
              name="userPassword"
              value={formData.userPassword}
              onChange={handleChange}
              required
            />
          </div>
          <div>
            <label>Nickname</label>
            <input
              type="text"
              name="nickname"
              value={formData.nickname}
              onChange={handleChange}
              required
            />
          </div>
          <div>
            <label>Tel 1(가운데 자리)</label>
            <input
              type="text"
              name="tel_1"
              maxLength={4}
              value={formData.tel_1}
              onChange={handleChange}
              required
            />
          </div>
          <div>
            <label>Tel 2(뒷 자리)</label>
            <input
              type="text"
              name="tel_2"
              maxLength={4}
              value={formData.tel_2}
              onChange={handleChange}
              required
            />
          </div>
          <div>
            <label>지역</label>
            <input
              type="text"
              name="location"
              value={formData.location}
              onChange={handleChange}
              required
            />
          </div>
          {error && <p className="error-message">{error}</p>}
          <button type="submit">회원가입 하기</button>
        </form>
        <p>
          이미 계정이 있으신가요? <a href="/Login">Login</a>
        </p>
      </div>
    </div>
  );
}

export default Register;

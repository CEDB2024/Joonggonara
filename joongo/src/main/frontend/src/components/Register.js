import React, { useState } from "react";
import authService from "../services/AuthService";
import { useNavigate } from "react-router-dom";
import "./register.css";
function Register() {
  const [formData, setFormData] = useState({
    email: "",
    userPassword: "",
    userName: "", // 이름을 userName으로 변경
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
      console.log("Server Response (Full):", response); // 전체 응답 객체 확인
      console.log("Server Response (Data):", response.data); // 데이터만 출력      
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
    <div>
      <h2>Register</h2>
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
          <label>Tel 1</label>
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
          <label>Tel 2</label>
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
          <label>Location</label>
          <input
            type="text"
            name="location"
            value={formData.location}
            onChange={handleChange}
            required
          />
        </div>
        {error && <p style={{ color: "red" }}>{error}</p>}
        <button type="submit">Sign Up</button>
      </form>
      <p>
        Already have an account? <a href="/Login">Login</a>
      </p>
    </div>
  );
}

export default Register;

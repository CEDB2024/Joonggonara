import axios from "axios";

const API_URL = "http://localhost:8080/api/auth"; // Spring Boot API 경로

// 로그인 요청
const login = async (credentials) => {
  const response = await axios.post(`${API_URL}/login`, credentials);
  return response.data; // 서버에서 반환된 데이터
};

// 회원가입 요청
const register = async (userData) => {
  const response = await axios.post(`${API_URL}/register`, userData);
  return response.data;
};

// 토큰 검증 요청
const verifyToken = async () => {
  const token = localStorage.getItem("token");
  if (!token) {
    return false;
  }
  try {
    const response = await axios.post(
      `${API_URL}/verify`,
      {},
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );
    return response.data.valid; // 서버에서 유효성 반환
  } catch (err) {
    console.error("Token verification failed", err);
    return false;
  }
};

// 로그아웃 처리
const logout = () => {
  localStorage.removeItem("token"); // 로컬 스토리지에서 토큰 삭제
};

const authService = {
  login,
  register,
  verifyToken,
  logout,
};

export default authService;

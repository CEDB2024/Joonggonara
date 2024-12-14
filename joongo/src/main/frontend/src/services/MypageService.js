import axios from "axios";

// API 기본 URL
const API_URL = "http://localhost:8080/api";

// Axios 기본 설정 (Interceptor)
axios.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// 사용자가 등록한 상품 조회
const getUserProducts = async (userId) => {
  try {
    const response = await axios.get(`${API_URL}/users/${userId}/products`);
    return response.data;
  } catch (error) {
    console.error("Error fetching user products:", error.response || error.message);
    throw error;
  }
};

// 금액 충전
const chargeMoney = async (userId, amount) => {
  try {
    const response = await axios.post(`${API_URL}/users/${userId}/charge`, { amount });
    return response.data;
  } catch (error) {
    console.error("Error charging money:", error.response || error.message);
    throw error;
  }
};

// 사용자 정보 가져오기
const getUserInfo = async (userId) => {
  try {
    const response = await axios.get(`${API_URL}/users/${userId}/info`);
    return response.data;
  } catch (error) {
    console.error("Error fetching user info:", error.response || error.message);
    throw error;
  }
};

// 서비스 객체
const MypageService = {
  getUserProducts,
  chargeMoney,
  getUserInfo,
};

export default MypageService;

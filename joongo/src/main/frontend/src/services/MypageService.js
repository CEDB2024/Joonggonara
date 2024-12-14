import axios from "axios";

const API_BASE_URL = "http://localhost:8080/api"; // API 기본 경로

// 사용자가 등록한 상품 조회
const getUserProducts = async (userId) => {
  try {
    const response = await axios.get(`${API_BASE_URL}/users/${userId}/products`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`, // 토큰 전달
      },
    });
    return response.data;
  } catch (error) {
    console.error("Error fetching user products:", error);
    throw error;
  }
};

// 금액 충전
const chargeMoney = async (userId, amount) => {
  try {
    const response = await axios.put(`${API_BASE_URL}/users/${userId}/charge`, { amount }, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`, // 토큰 전달
      },
    });
    return response.data;
  } catch (error) {
    console.error("Error charging money:", error);
    throw error;
  }
};

// 사용자 정보 가져오기
const getUserInfo = async (userId) => {
  try {
    const response = await axios.get(`${API_BASE_URL}/users/${userId}/info`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`, // 토큰 전달
      },
    });
    return response.data;
  } catch (error) {
    console.error("Error fetching user info:", error);
    throw error;
  }
};

// 사용자 거래 내역 가져오기
const getOrdersByUserId = async (userId) => {
  try {
    const response = await axios.get(`${API_BASE_URL}/orders/${userId}`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`, // 토큰 전달
      },
    });
    return response.data;
  } catch (error) {
    console.error("Error fetching user orders:", error);
    throw error;
  }
};

// 거래량 순위 가져오기
const getUserRankByTransactionCount = async () => {
  try {
    const response = await axios.get(`${API_BASE_URL}/orders/rank`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`, // 토큰 전달
      },
    });
    return response.data;
  } catch (error) {
    console.error("Error fetching user rank:", error);
    throw error;
  }
};

// 서비스 객체로 내보내기
const MypageService = {
  getUserProducts,
  chargeMoney,
  getUserInfo,
  getOrdersByUserId,
  getUserRankByTransactionCount,
};

export default MypageService;

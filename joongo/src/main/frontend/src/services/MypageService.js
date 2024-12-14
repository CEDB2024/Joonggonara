import axios from "axios";

const API_URL = "http://localhost:8080/api/user";

// 사용자가 등록한 상품 조회
const getUserProducts = async (userId) => {
  try {
    const response = await axios.get(`${API_URL}/${userId}/products`, {
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
    const response = await axios.post(`${API_URL}/${userId}/charge`, {
      amount,
    });
    return response.data;
  } catch (error) {
    console.error("Error charging money:", error);
    throw error;
  }
};

// 서비스 객체로 export
const mypageService = {
  getUserProducts,
  chargeMoney,
};

export default mypageService;

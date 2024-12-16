import axios from "axios";
import getLocalStorage from "../global/LocalStorage";

const API_URL = "http://54.180.53.177:8080/api/users";

// 공통 Axios 요청 함수
const sendRequest = async (method, endpoint, data = null, params = null) => {
  try {
    // 로컬 스토리지에서 토큰 가져오기
    const token = getLocalStorage("token");

    // Axios 요청 설정
    const config = {
      method,
      url: `${API_URL}/${endpoint}`, // API URL과 엔드포인트 결합
      headers: {
        "Content-Type": "application/json",
        ...(token && { Authorization: `Bearer ${token}` }), // 토큰이 있으면 Authorization 헤더 추가
      },
      data,
      params,
    };

    const response = await axios(config);
    return response.data;
  } catch (error) {
    console.error(`[Axios Error]: ${error.response || error.message}`);
    throw new Error("요청 처리 중 오류가 발생했습니다.");
  }
};

// 사용자가 등록한 상품 조회
const getUserProducts = async (userId) => {
  return await sendRequest("get", `${userId}/products`);
};

// 금액 충전
const chargeMoney = async (userId, amount) => {
  return await sendRequest("post", `${userId}/charge`, { amount });
};

// 서비스 객체로 export
const mypageService = {
  getUserProducts,
  chargeMoney,
};

export default mypageService;

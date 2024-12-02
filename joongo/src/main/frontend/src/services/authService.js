import axios from "axios";

const API_URL = "http://localhost:8080/api/auth"; // Spring Boot API 경로

// 로그인 요청
const login = async (credentials) => {
  const response = await axios.post(`${API_URL}/login`, credentials);
  return response.data; // 서버에서 반환된 데이터
};

// 회원가입 요청
const register = async (formData) => {
  try {
    console.log("Sending registration data:", formData); // 요청 데이터 출력
    const response = await axios.post(`${API_URL}/register`, formData);
    console.log("Registration successful:", response.data); // 성공 응답 출력
    return response.data; // 서버에서 반환된 데이터를 전달
  } catch (error) {
    if (error.response) {
      // 서버에서 반환한 에러
      console.error("Server error response:", error.response.status, error.response.data);
    } else if (error.request) {
      // 요청이 서버에 도달하지 못함
      console.error("Request made but no response:", error.request);
    } else {
      // 요청 설정 중 에러
      console.error("Error during request setup:", error.message);
    }
    console.error("Error during registration (full):", error);
    throw error; // 에러를 상위로 전달
  }
};


const verifyToken = async (token = localStorage.getItem("token")) => {
  if (!token) return false;

  try {
    const response = await axios.post(
      "/api/auth/verify",
      {},
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );
    return response.data.valid;
  } catch (err) {
    localStorage.removeItem("token"); // 만료 시 제거
    return false;
  }
};


const isAuthenticated = async () => {
  return await verifyToken(); // 내부적으로 verifyToken 호출
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
  isAuthenticated,
};

export default authService;

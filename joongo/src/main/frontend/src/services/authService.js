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

const isAuthenticated = () => {
  const token = localStorage.getItem("token"); // 로컬 스토리지에서 토큰을 가져옴
  return !!token; // 토큰이 존재하면 true, 아니면 false
};

// 로그아웃 처리
const logout = () => {
  localStorage.removeItem("token"); // 로컬 스토리지에서 토큰 삭제
};

const getEmail = async () => {
  const token = localStorage.getItem("token");
  if (!token) {
    throw new Error("No token found");
  }

  try {
    const response = await axios.get(`${API_URL}/email`, {
      headers: {
        Authorization: `Bearer ${token}`, // Authorization 헤더 설정
      },
    });
    return response.data.email; // 서버에서 반환된 이메일
  } catch (error) {
    console.error("Failed to fetch email", error);
    throw error; // 에러를 상위로 전달
  }
};

const authService = {
  login,
  register,
  verifyToken,
  logout,
  isAuthenticated,
  getEmail,
};

export default authService;

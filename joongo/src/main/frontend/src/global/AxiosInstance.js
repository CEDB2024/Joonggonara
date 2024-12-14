import axios from "axios";

const API_URL = "http://localhost:8080/api/"; // API 베이스 URL

// Axios 인스턴스 생성
const axiosInstance = axios.create({
    baseURL: API_URL,
});

// 요청 인터셉터 설정
axiosInstance.interceptors.request.use(
    (config) => {
        // 로컬 스토리지에서 토큰 가져오기
        const token = localStorage.getItem("token");
        if (token) {
            config.headers["Authorization"] = `Bearer ${token}`;
            console.info(token);
        } else {
            console.error("토큰을 못찾아!~");
        }
        return config;
    },
    (error) => {
        // 요청 에러 처리
        return Promise.reject(error);
    }
);

// 응답 인터셉터 설정 (선택적)
axiosInstance.interceptors.response.use(
    (response) => response,
    (error) => {
        // 예: 토큰 만료 시 처리
        if (error.response && error.response.status === 401) {
            // 토큰 만료 시 로그아웃 로직 추가 가능
            console.error("Unauthorized! Redirecting to login...");
        }
        return Promise.reject(error);
    }
);

export default axiosInstance;

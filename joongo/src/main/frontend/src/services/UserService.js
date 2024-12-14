import axios from "axios";
import getLocalStorage from "../global/LocalStorage";

// 기본 API URL 설정
const API_URL = "http://localhost:8080/api/users";

// 공통 Axios 요청 함수
const sendRequest = async (method, endpoint, data = null, params = null) => {
    try {
        // 로컬 스토리지에서 토큰 가져오기
        const token = getLocalStorage("token");

        // Axios 요청 설정
        const config = {
            method,
            url: `${API_URL}${endpoint}`, // API URL과 엔드포인트 결합
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
        console.error(`[Axios Error]: ${error.response?.status || error.message}`);
        throw new Error("요청 처리 중 오류가 발생했습니다.");
    }
};

// 이메일로 유저 정보 가져오기
const getUserByEmail = async (email) => {
    return await sendRequest("get", "/email", null, { email });
};

// UserService 객체로 관리
const UserService = {
    getUserByEmail,
};

export default UserService;

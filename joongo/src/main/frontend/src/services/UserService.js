import axios from "axios";
import axiosInstance from "../global/AxiosInstance";

const API_URL = "http://localhost:8080/api/users";

const getUserByEmail = async (email) => {
    try {
        const response = await axiosInstance.get(`${API_URL}/email`, {
            params: { email }
        });
        return response.data;
    } catch (error) {
        console.error("[getUserByEmail Error]:", error.response || error.message);
        throw new Error("유저 정보 가져오던 중 오류가 발생했습니다.");
    }
};

// ProductService 객체로 관리
const UserService = {
    getUserByEmail
};

export default UserService;

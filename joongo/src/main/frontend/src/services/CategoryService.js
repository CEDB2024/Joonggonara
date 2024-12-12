import axios from "axios";

const API_URL = "http://localhost:8080/api/categories"; // Spring Boot API 경로

// 카테고리 요청
const getCategoryNames = async () => {
    const response = await axios.get(API_URL); // GET 메서드로 수정
    return response.data; // 서버에서 반환된 데이터
};

const CategoryService = {
    getCategoryNames
};

export default CategoryService;

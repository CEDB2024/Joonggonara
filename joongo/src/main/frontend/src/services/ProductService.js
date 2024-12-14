import axios from "axios";
import axiosInstance from "../global/AxiosInstance";

const API_URL = "http://localhost:8080/api/products"; // Spring Boot API 경로

const getAllProducts = async () => {
    try {
        const response = await axios.get(`${API_URL}/`);
        return response.data;
    } catch (error) {
        console.error("[getAllProducts Error]:", error.response || error.message);
        throw new Error("상품 목록을 불러오는 중 오류가 발생했습니다.");
    }
};

const getAllProductsByCategories = async (categoryId) => {
    try {
        const response = await axiosInstance.get(`${API_URL}/category`, {
            params: { categoryId }, // 쿼리 파라미터로 categoryId 전달
        });
        return response.data; // Axios에서는 응답 데이터가 response.data에 포함됩니다.
    } catch (error) {
        throw new Error("Failed to fetch products: " + error.message);
    }
};

const addProduct = async (productInfo) => {
    try {
        const response = await axiosInstance.post(`${API_URL}/`, productInfo);
        return response.data;
    } catch (error) {
        console.error("[addProduct Error]:", error.response || error.message);
        throw new Error("상품 추가 중 오류가 발생했습니다.");
    }
};

const updateProduct = async (productId, updateInfo) => {
    try {
        const response = await axiosInstance.patch(`${API_URL}/${productId}`, updateInfo);
        return response.data;
    } catch (error) {
        console.error("[updateProduct Error]:", error.response || error.message);
        throw new Error("상품 수정 중 오류가 발생했습니다.");
    }
};

const getProductById = async (productId) => {
    try {
        const response = await axiosInstance.get(`${API_URL}/${productId}`);
        return response.data;
    } catch (error) {
        console.error("[getProductById Error]:", error.response || error.message);
        throw new Error("상품 상세 정보를 불러오는 중 오류가 발생했습니다.");
    }
};

// ProductService 객체로 관리
const ProductService = {
    getAllProducts,
    getAllProductsByCategories,
    addProduct,
    updateProduct,
    getProductById,
};

export default ProductService;

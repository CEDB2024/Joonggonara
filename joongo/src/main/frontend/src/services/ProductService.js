import axios from "axios";
import getLocalStorage from "../global/LocalStorage";

// 기본 API URL 설정
const API_URL = "http://localhost:8080/api";

// 공통 Axios 요청 함수
const sendRequest = async (method, endpoint, data = null, params = null) => {
    try {
        // 로컬 스토리지에서 토큰 가져오기
        const token = getLocalStorage("token");

        // Axios 요청 설정
        const config = {
            method,
            url: `${API_URL}/${endpoint}`,
            headers: {
                ...(token && {Authorization: `Bearer ${token}`}), // 토큰이 있으면 Authorization 헤더 추가
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

// API 함수 정의
const getAllProducts = async () => {
    return await sendRequest("get", "products/");
};

const getAllProductsByCategories = async (categoryId) => {
    return await sendRequest("get", "products/category", null, {categoryId});
};

const addProduct = async (productInfo) => {
    return await sendRequest("post", "products/", productInfo);
};

const updateProduct = async (productId, updateInfo) => {
    return await sendRequest("patch", `products/${productId}`, updateInfo);
};

const getProductById = async (productId) => {
    return await sendRequest("get", `products/${productId}`);
};

const purchaseProduct = async (purchasedInfo) => {
    return await sendRequest("post", `order/`, purchasedInfo);
}
// ProductService 객체로 관리
const ProductService = {
    purchaseProduct,
    getAllProducts,
    getAllProductsByCategories,
    addProduct,
    updateProduct,
    getProductById,
};

export default ProductService;

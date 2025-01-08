import axios from "axios";
import getLocalStorage from "../global/LocalStorage";

const apiUrl = process.env.BASE_URL;
// 기본 API URL 설정
const API_URL = `${apiUrl}/api`;

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
        // 서버 에러 메시지 확인 및 처리
        const errorMessage = error.response?.data?.code || error.message; // 서버에서 보낸 code 또는 기본 에러 메시지
        console.error(`[Axios Error]: ${errorMessage}`);

        // 에러 메시지를 담은 Error 객체 생성 후 throw
        throw new Error(errorMessage);
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
    return await sendRequest("post", `orders/`, purchasedInfo);
}

const deleteProduct = async (productId) => {
    return await sendRequest("post", `products/${productId}/delete`);
}

const selectByTitle = async (productName) => {
    return await sendRequest("get", "products/title", null, {productName});
};

const editProduct = async (product) => {
    return await sendRequest("post", "products/edit", product);
};
// ProductService 객체로 관리
const ProductService = {
    editProduct,
    selectByTitle,
    purchaseProduct,
    getAllProducts,
    getAllProductsByCategories,
    addProduct,
    updateProduct,
    getProductById,
    deleteProduct,
};

export default ProductService;

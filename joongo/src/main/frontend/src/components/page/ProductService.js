import axios from "axios";

const API_URL = "http://localhost:8080/api/products"; // Spring Boot API 경로

const getAllProducts = async () => {
    try {
        const response = await axios.get(`${API_URL}/`);
        return response.data;
    } catch (error) {
        console.error("[getAllProducts Error] ", error);
        throw error; // 에러를 호출한 쪽에서 처리할 수 있도록 다시 던짐
    }
};

const getAllProductsByCategories = async () => {
    try {
        const response = await axios.get(`${API_URL}/categories`);
        return response.data;
    } catch (error) {
        console.error("[getAllProductsByCategories Error] ", error);
        throw error;
    }
};

const addProduct = async (productInfo) => {
    try {
        const response = await axios.post(`${API_URL}/`, productInfo);
        return response.data;
    } catch (error) {
        console.error("[addProduct Error] ", error);
        throw error;
    }
};

const updateProduct = async (productId, updateInfo) => {
    try {
        const response = await axios.patch(`${API_URL}/${productId}`, updateInfo);
        return response.data;
    } catch (error) {
        console.error("[updateProduct Error] ", error);
        throw error;
    }
};

// ProductService 객체로 관리
const ProductService = {
    getAllProducts,
    getAllProductsByCategories,
    addProduct,
    updateProduct
};

export default ProductService;

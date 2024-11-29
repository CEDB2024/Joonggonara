import axios from "axios";

const API_BASE_URL = "http://localhost:8080/api";

export const getUserInfo = async (userId) => {
    const response = await axios.get(`${API_BASE_URL}/users/${userId}`);
    return response.data;
};

export const getUserMoney = async (userId) => {
    const response = await axios.get(`${API_BASE_URL}/users/${userId}/money`);
    return response.data;
};

export const getUserProducts = async (userId) => {
    const response = await axios.get(`${API_BASE_URL}/users/${userId}/products`);
    return response.data;
};

export const getTransactionHistory = async (userId) => {
    const response = await axios.get(`${API_BASE_URL}/users/${userId}/transactions`);
    return response.data;
};

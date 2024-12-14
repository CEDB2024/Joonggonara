import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import AuthService from "../../../services/AuthService";
import UserService from "../../../services/UserService"; // UserService 추가
import ProductService from "../../../services/ProductService";
import Layout from "../../../global/Layout";
import "./CreateProductPage.css";

const CreateProductPage = () => {
    const navigate = useNavigate();
    const [emailError, setEmailError] = useState(null);

    const [formData, setFormData] = useState({
        title: "",
        content: "",
        location: "",
        count: 0,
        price: 0,
        categoryId: "", // 초기값을 빈 문자열로 설정
        userId: null,
        productPicture: null,
    });

    const [userEmail, setUserEmail] = useState("");

    const categories = [
        { id: 1, name: "디지털 기기" },
        { id: 2, name: "가구/인테리어" },
        { id: 3, name: "의류" },
        { id: 4, name: "식물" },
    ];

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleCategoryChange = (e) => {
        const selectedCategoryId = parseInt(e.target.value, 10) || ""; // 선택하지 않은 경우 빈 값 유지
        setFormData({ ...formData, categoryId: selectedCategoryId });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const form = new FormData();
        Object.entries(formData).forEach(([key, value]) => {
            form.append(key, value);
        });

        try {
            const response = await ProductService.addProduct(form);
            console.log("Product created successfully:", response.data);
            navigate("/main");
        } catch (error) {
            console.error("Failed to create product", error);
        }
    };

    useEffect(() => {
        const fetchUserInfo = async () => {
            try {
                const email = await AuthService.getEmail();
                setUserEmail(email);

                const userInfo = await UserService.getUserByEmail(email);
                setFormData((prevData) => ({
                    ...prevData,
                    userId: userInfo.userId,
                }));
            } catch (err) {
                console.error("[Error Fetching User Info]", err);
                setEmailError("유저 정보를 불러오는 중 오류가 발생했습니다.");
            }
        };

        fetchUserInfo();
    }, []);

    return (
        <Layout>
            <div className="create-product-page">
                <header>
                    <h1>상품 등록</h1>
                    {userEmail && <p>유저 이메일: {userEmail}</p>}
                    {emailError && <p className="error">{emailError}</p>}
                </header>
                <form className="product-form" onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label htmlFor="title">제목</label>
                        <input
                            type="text"
                            id="title"
                            name="title"
                            value={formData.title}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="content">내용</label>
                        <textarea
                            id="content"
                            name="content"
                            value={formData.content}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="location">위치</label>
                        <input
                            type="text"
                            id="location"
                            name="location"
                            value={formData.location}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="count">수량</label>
                        <input
                            type="number"
                            id="count"
                            name="count"
                            value={formData.count}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="price">가격</label>
                        <input
                            type="number"
                            id="price"
                            name="price"
                            value={formData.price}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="category">카테고리</label>
                        <select
                            id="category"
                            name="categoryId"
                            value={formData.categoryId}
                            onChange={handleCategoryChange}
                            required
                        >
                            <option value="">카테고리를 선택하세요</option> {/* 기본 옵션 */}
                            {categories.map((category) => (
                                <option key={category.id} value={category.id}>
                                    {category.name}
                                </option>
                            ))}
                        </select>
                    </div>
                    <div className="form-group">
                        <label htmlFor="productPicture">상품 이미지</label>
                        <input
                            type="file"
                            id="productPicture"
                            name="productPicture"
                            onChange={(e) =>
                                setFormData({ ...formData, productPicture: e.target.files[0] })
                            }
                        />
                    </div>
                    <button type="submit" className="submit-button">
                        등록하기
                    </button>
                </form>
            </div>
        </Layout>
    );
};

export default CreateProductPage;
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
        categoryId: 0,
        userId: null, // 유저 ID 초기값 설정
        productPicture: null,
    });

    const [userEmail, setUserEmail] = useState("");

    // 고정된 카테고리 데이터
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
        const selectedCategoryId = parseInt(e.target.value, 10); // 선택된 카테고리 ID
        setFormData({ ...formData, categoryId: selectedCategoryId });
    };

    const handleFileChange = (e) => {
        setFormData({ ...formData, productPicture: e.target.files[0] });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        // FormData 생성
        const form = new FormData();
        Object.entries(formData).forEach(([key, value]) => {
            form.append(key, value);
        });

        try {
            // ProductService를 통해 API 호출
            const response = await ProductService.addProduct(form);
            console.log("Product created successfully:", response.data);

            // 성공 시 페이지 이동
            navigate("/main");
        } catch (error) {
            console.error("Failed to create product", error);
        }
    };

    // 이메일 및 유저 정보 가져오기
    useEffect(() => {
        const fetchUserInfo = async () => {
            try {
                // 이메일 가져오기
                const email = await AuthService.getEmail();
                setUserEmail(email);

                // 유저 정보 가져오기
                const userInfo = await UserService.getUserByEmail(email);
                setFormData((prevData) => ({
                    ...prevData,
                    userId: userInfo.userId, // 받아온 유저 ID를 formData에 추가
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
                    {userEmail && <p>유저 이메일: {userEmail}</p>} {/* 이메일 표시 */}
                    {emailError && <p className="error">{emailError}</p>} {/* 에러 메시지 표시 */}
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
                            <option value="" disabled>
                                카테고리를 선택하세요
                            </option>
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
                            onChange={handleFileChange}
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

import React, { useState, useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import ProductService from "../../../services/ProductService";
import Layout from "../../../global/Layout";
import "./EditProductPage.css";

const EditProductPage = () => {
    const navigate = useNavigate();
    const location = useLocation();
    const { productId } = location.state; // MyPage에서 전달받은 productId

    // 수정할 상품의 초기 데이터
    const [formData, setFormData] = useState({
        title: "",
        content: "",
        location: "",
        count: 0,
        price: 0,
        categoryId: "",
        productPicture: null,
    });

    const [categories] = useState([
        { id: 1, name: "디지털 기기" },
        { id: 2, name: "가구/인테리어" },
        { id: 3, name: "의류" },
        { id: 4, name: "식물" },
    ]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [uploadedFileName, setUploadedFileName] = useState("");

    // 상품 정보 불러오기
    useEffect(() => {
        const fetchProduct = async () => {
            try {
                const product = await ProductService.getProductById(productId);
                setFormData({
                    title: product.title,
                    content: product.content,
                    location: product.location,
                    count: product.count,
                    price: product.price,
                    categoryId: product.categoryId,
                    productPicture: null, // 이미지는 별도로 처리
                });
            } catch (err) {
                console.error("상품 정보를 불러오는 중 오류 발생:", err);
                setError("상품 정보를 불러오는 중 오류가 발생했습니다.");
            } finally {
                setLoading(false);
            }
        };
        fetchProduct();
    }, [productId]);

    // 입력값 변경 핸들러
    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleFileChange = (e) => {
        const file = e.target.files[0];
        setFormData({ ...formData, productPicture: file });
        setUploadedFileName(file ? file.name : "");
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!productId) {
            alert("유효하지 않은 상품 ID입니다.");
            return;
        }

        try {
            const form = new FormData();
            Object.entries(formData).forEach(([key, value]) => {
                form.append(key, value);
            });
            form.append("productId", productId);

            await ProductService.editProduct(form);

            alert("상품이 성공적으로 수정되었습니다.");
            navigate("/mypage"); // 성공 시 마이페이지로 이동
        } catch (error) {
            console.error("상품 수정 실패:", error);
            alert("상품 수정 중 오류가 발생했습니다.");
        }
    };


    if (loading) {
        return (
            <Layout>
                <div className="loading-message">로딩 중...</div>
            </Layout>
        );
    }

    if (error) {
        return (
            <Layout>
                <div className="error-message">{error}</div>
            </Layout>
        );
    }

    return (
        <Layout>
            <div className="create-product-page">
                <header className="page-header">
                    <h1>상품 수정</h1>
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
                            onChange={handleChange}
                            required
                        >
                            <option value="">카테고리를 선택하세요</option>
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
                        {uploadedFileName && <p className="file-name">업로드된 파일: {uploadedFileName}</p>}
                    </div>
                    <button type="submit" className="submit-button">
                        수정하기
                    </button>
                </form>
            </div>
        </Layout>
    );
};

export default EditProductPage;

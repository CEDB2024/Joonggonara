import React, { useEffect, useState } from "react";
import { Link, useSearchParams, useNavigate } from "react-router-dom";
import ProductService from "../../../services/ProductService";
import Layout from "../../../global/Layout";
import "./MainPage.css";

const MainPage = () => {
    const [products, setProducts] = useState([]); // 상품 목록 상태 관리
    const [productError, setProductError] = useState(null); // 상품 에러 상태 관리
    const [searchParams, setSearchParams] = useSearchParams(); // 쿼리 파라미터 관리
    const [searchInput, setSearchInput] = useState(""); // 검색 입력값
    const navigate = useNavigate();

    // 고정된 카테고리 데이터
    const categories = [
        { id: 0, name: "전체" },
        { id: 1, name: "디지털 기기" },
        { id: 2, name: "가구/인테리어" },
        { id: 3, name: "의류" },
        { id: 4, name: "식물" },
    ];

    const fetchProducts = async (categoryId) => {
        try {
            if (categoryId === 0) {
                // 기본값: 전체 상품 가져오기
                const data = await ProductService.getAllProducts();
                setProducts(data);
            } else {
                // 카테고리별 상품 가져오기
                const data = await ProductService.getAllProductsByCategories(categoryId);
                setProducts(data);
            }
            setProductError(null);
        } catch (err) {
            console.error("[MainPage Error - Products]", err);
            setProductError("상품 데이터를 불러오는 중 오류가 발생했습니다.");
        }
    };

    useEffect(() => {
        const categoryId = parseInt(searchParams.get("category")) || 0; // 쿼리 파라미터에서 카테고리 ID 가져오기
        fetchProducts(categoryId);
    }, [searchParams]);

    const handleCategoryClick = (categoryId) => {
        setSearchParams({ category: categoryId }); // 선택한 카테고리 ID를 쿼리 파라미터에 설정
    };

    const handleSearch = async (e) => {
        e.preventDefault(); // 폼 제출 기본 동작 방지
        try {
            if (searchInput.trim().length < 2) {
                alert("검색어는 2글자 이상 입력해주세요.");
                return;
            }
            const results = await ProductService.selectByTitle(searchInput);
            navigate("/search", { state: { products: results } }); // 검색 결과를 `/select`로 전달
        } catch (err) {
            console.error("[Search Error]", err);
            alert("검색 중 오류가 발생했습니다.");
        }
    };

    return (
        <Layout>
            <nav className="nav">
                {categories.map((category) => (
                    <button
                        key={category.id}
                        className="category-button"
                        onClick={() => handleCategoryClick(category.id)}
                    >
                        {category.name}
                    </button>
                ))}
                <form className="search-form" onSubmit={handleSearch}>
                    <input
                        type="text"
                        placeholder="검색어 입력"
                        value={searchInput}
                        onChange={(e) => setSearchInput(e.target.value)}
                        className="search-input"
                    />
                    <button type="submit" className="search-button">
                        검색
                    </button>
                </form>
            </nav>

            <div className="container">
                {productError ? (
                    <p className="error-message">{productError}</p>
                ) : products.length > 0 ? (
                    products.map((product) => (
                        <div className="product-card" key={product.productId}>
                            <Link to={`/product/${product.productId}`}>
                                <img
                                    src={product.image || "https://via.placeholder.com/200x150"}
                                    alt={product.title}
                                    className="product-image"
                                />
                                <h2 className="product-name">{product.title}</h2>
                            </Link>
                            <p className="product-price">₩{product.price.toLocaleString()}</p>
                        </div>
                    ))
                ) : (
                    <p className="loading-message">상품이 없습니다 !</p>
                )}
            </div>
        </Layout>
    );
};

export default MainPage;

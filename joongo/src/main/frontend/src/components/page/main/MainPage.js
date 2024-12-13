import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import ProductService from "../../../services/ProductService";
import CategoryService from "../../../services/CategoryService";
import "./MainPage.css";

const MainPage = () => {
    const [products, setProducts] = useState([]); // 상품 목록 상태 관리
    const [categories, setCategories] = useState([]); // 카테고리 목록 상태 관리
    const [productError, setProductError] = useState(null); // 상품 에러 상태 관리
    const [categoryError, setCategoryError] = useState(null); // 카테고리 에러 상태 관리

    useEffect(() => {
        // 상품 데이터 로드
        const fetchProducts = async () => {
            try {
                const data = await ProductService.getAllProducts();
                setProducts(data); // 상품 목록 상태 업데이트
            } catch (err) {
                console.error("[MainPage Error - Products]", err);
                setProductError("상품 데이터를 불러오는 중 오류가 발생했습니다.");
            }
        };

        // 카테고리 데이터 로드
        const fetchCategories = async () => {
            try {
                const data = await CategoryService.getCategoryNames();
                setCategories(data); // 카테고리 목록 상태 업데이트
            } catch (err) {
                console.error("[MainPage Error - Categories]", err);
                setCategoryError("카테고리 데이터를 불러오는 중 오류가 발생했습니다.");
            }
        };

        fetchProducts();
        fetchCategories();
    }, []);

    return (
        <div className="main-page">
            <header className="header">
                <h1>중고거래 플랫폼</h1>
                <div className="header-right">
                    <div className="mypage">
                        <Link to="/mypage">마이페이지</Link>
                    </div>
                    <div className="create-product">
                        <Link to="/products/new">상품 등록</Link>
                    </div>
                    <div className="search-bar">
                        <input type="text" placeholder="검색어를 입력하세요" />
                        <button>검색</button>
                    </div>
                </div>
            </header>

            <nav className="nav">
                {categoryError ? (
                    <p className="error-message">{categoryError}</p>
                ) : categories.length > 0 ? (
                    categories.map((category) => (
                        <a href="#" key={category.id}>
                            {category.name}
                        </a>
                    ))
                ) : (
                    <p className="loading-message">카테고리를 불러오는 중...</p>
                )}
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
                    <p className="loading-message">상품을 불러오는 중...</p>
                )}
            </div>

            <footer className="footer">
                <p>&copy; 2024 중고거래 플랫폼. All rights reserved.</p>
            </footer>
        </div>
    );
};

export default MainPage;

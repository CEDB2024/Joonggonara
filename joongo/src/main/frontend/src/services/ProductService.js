import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import ProductService from "./ProductService"; // ProductService를 import
import "./MainPage.css";

const MainPage = () => {
    const [products, setProducts] = useState([]); // 상품 목록 상태 관리
    const [error, setError] = useState(null); // 에러 상태 관리

    useEffect(() => {
        // 컴포넌트 로드 시 상품 데이터 로드
        const fetchProducts = async () => {
            try {
                const data = await ProductService.getAllProducts();
                setProducts(data); // 상품 목록 상태 업데이트
            } catch (err) {
                console.error("[MainPage Error] ", err);
                setError("상품 데이터를 불러오는 중 오류가 발생했습니다.");
            }
        };

        fetchProducts();
    }, []);

    return (
        <div className="main-page">
            <header className="header">
                <h1>중고거래 플랫폼</h1>
                <div className="header-right">
                    <div className="mypage">
                        <Link to="/mypage">마이페이지</Link>
                    </div>
                    <div className="search-bar">
                        <input type="text" placeholder="검색어를 입력하세요" />
                        <button>검색</button>
                    </div>
                </div>
            </header>

            <nav className="nav">
                <a href="#">전체 카테고리</a>
                <a href="#">전자기기</a>
                <a href="#">의류</a>
                <a href="#">가구</a>
                <a href="#">스포츠</a>
                <a href="#">도서</a>
            </nav>

            <div className="container">
                {error ? (
                    <p className="error-message">{error}</p>
                ) : products.length > 0 ? (
                    products.map((product) => (
                        <div className="product-card" key={product.id}>
                            <img
                                src={product.image || "https://via.placeholder.com/200x150"}
                                alt={product.name}
                                className="product-image"
                            />
                            <h2 className="product-name">{product.name}</h2>
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

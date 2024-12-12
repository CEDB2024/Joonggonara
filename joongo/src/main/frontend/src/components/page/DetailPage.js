import React, { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import ProductService from "./ProductService";
import "./DetailPage.css";

const ProductDetailPage = () => {
    const { productId } = useParams();
    const [product, setProduct] = useState(null);
    const [selectedQuantity, setSelectedQuantity] = useState(1);
    const [error, setError] = useState(null);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        const fetchProduct = async () => {
            try {
                setIsLoading(true);
                const data = await ProductService.getProductById(productId);
                setProduct(data);
                setError(null);
            } catch (err) {
                console.error("[ProductDetailPage Error]:", err);
                setError("상품 데이터를 불러오는 중 오류가 발생했습니다.");
            } finally {
                setIsLoading(false);
            }
        };

        fetchProduct();
    }, [productId]);

    const handlePurchase = async () => {
        try {
            await ProductService.purchaseProduct(productId, selectedQuantity);
            alert("구매가 완료되었습니다!");
        } catch (err) {
            console.error("[Purchase Error]:", err);
            alert("구매 중 오류가 발생했습니다.");
        }
    };

    if (isLoading) {
        return <div className="loading-message">상품을 불러오는 중...</div>;
    }

    if (error) {
        return <div className="error-message">{error}</div>;
    }

    if (!product) {
        return <div className="error-message">상품 데이터를 찾을 수 없습니다.</div>;
    }

    return (
        <div className="main-page">
            {/* 기존 메인 페이지의 헤더 */}
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

            {/* 상세 페이지 디자인 */}
            <div className="product-detail-container">
                <div className="product-detail-card">
                    <div className="product-image-wrapper">
                        <img
                            src={product.image || "https://via.placeholder.com/400x300"}
                            alt={product.title}
                            className="product-detail-image"
                        />
                    </div>
                    <div className="product-detail-content">
                        <h2 className="product-title">{product.title}</h2>
                        <p className="product-price">
                            <strong>가격:</strong> ₩{product.price.toLocaleString()}
                        </p>
                        <p className="product-description">
                            <strong>설명:</strong> {product.content || "설명이 없습니다."}
                        </p>
                        <p className="product-status">
                            <strong>상태:</strong> {product.productStatus || "상태 미지정"}
                        </p>
                        <p className="product-location">
                            <strong>위치:</strong> {product.location || "위치 미지정"}
                        </p>
                        <p className="product-count">
                            <strong>남은 수량:</strong> {product.count}
                        </p>

                        {/* 수량 선택 */}
                        <div className="quantity-selector">
                            <label htmlFor="quantity">수량 선택:</label>
                            <select
                                id="quantity"
                                value={selectedQuantity}
                                onChange={(e) => setSelectedQuantity(Number(e.target.value))}
                            >
                                {Array.from({ length: product.count }, (_, i) => i + 1).map((num) => (
                                    <option key={num} value={num}>
                                        {num}
                                    </option>
                                ))}
                            </select>
                        </div>

                        {/* 구매 버튼 */}
                        <button
                            className="purchase-button"
                            onClick={handlePurchase}
                            disabled={product.count === 0}
                        >
                            구매하기
                        </button>
                    </div>
                </div>
            </div>

            <footer className="footer">
                <p>&copy; 2024 중고거래 플랫폼. All rights reserved.</p>
            </footer>
        </div>
    );
};

export default ProductDetailPage;

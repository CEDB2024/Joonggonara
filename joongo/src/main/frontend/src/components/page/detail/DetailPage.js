import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import ProductService from "../../../services/ProductService";
import Layout from "../../../global/Layout";
import "./DetailPage.css";

const ProductDetailPage = () => {
    const { productId } = useParams();
    const [product, setProduct] = useState(null);
    const [selectedQuantity, setSelectedQuantity] = useState(1);
    const [error, setError] = useState(null);
    const [isLoading, setIsLoading] = useState(true);

    // 고정된 카테고리 데이터
    const categories = {
        1: "디지털 기기",
        2: "가구/인테리어",
        3: "의류",
        4: "식물",
    };

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
        <Layout>
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
                        <p className="product-category">
                            <strong>카테고리:</strong> {categories[product.categoryId] || "카테고리 미지정"}
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
        </Layout>
    );
};

export default ProductDetailPage;

import React from "react";
import { Link } from "react-router-dom";

import "./MainPage.css";

const MainPage = () => {
    return (
        <div className="main-page">
            <header className="header">
                <h1>중고거래 플랫폼</h1>
                <div className="header-right">
                    <div className="mypage"><Link to="/mypage">마이페이지</Link></div>
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
                <div className="product-card">
                    <img
                        src="https://via.placeholder.com/200x150"
                        alt="상품 이미지"
                        className="product-image"
                    />
                    <h2 className="product-name">상품명</h2>
                    <p className="product-price">₩50,000</p>
                </div>
                <div className="product-card">
                    <img
                        src="https://via.placeholder.com/200x150"
                        alt="상품 이미지"
                        className="product-image"
                    />
                    <h2 className="product-name">상품명</h2>
                    <p className="product-price">₩30,000</p>
                </div>
            </div>

            <footer className="footer">
                <p>&copy; 2024 중고거래 플랫폼. All rights reserved.</p>
            </footer>
        </div>
    );
};

export default MainPage;

import React from "react";
import { Link } from "react-router-dom";
import "./Header.css"; // 스타일 파일 분리
import logo from "./logo.webp"; // logo.webp 파일 경로에 맞게 import

const Header = () => {
    return (
        <header className="header">
            <div className="logo-container">
                <Link to="/main" className="logo-link">
                    <img src={logo} alt="KW마켓 로고" className="logo-icon" />
                    <h1 className="logo-text">KW마켓</h1>
                </Link>
            </div>
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
    );
};

export default Header;

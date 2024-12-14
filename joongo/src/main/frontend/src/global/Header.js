import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import authService from "../services/AuthService";
import "./Header.css"; // 수정된 헤더 전용 CSS
import logo from "./logo.webp"; // logo.webp 파일 경로에 맞게 import

const Header = () => {
    const [isLoggedIn, setIsLoggedIn] = useState(false); // 로그인 상태 관리
    const navigate = useNavigate(); // 페이지 이동용

    // 로그인 상태 확인
    useEffect(() => {
        const checkAuth = async () => {
            const authenticated = authService.isAuthenticated(); // 로컬 스토리지 기반 확인
            setIsLoggedIn(authenticated);
        };
        checkAuth();
    }, []);

    // 로그아웃 핸들러
    const handleLogout = () => {
        authService.logout(); // 토큰 삭제
        setIsLoggedIn(false); // 상태 업데이트
        navigate("/login"); // 로그인 페이지로 이동
    };

    return (
        <header className="header">
            <div className="logo-container">
                <Link to="/main" className="logo-link">
                    <img src={logo} alt="KW마켓 로고" className="logo-icon" />
                    <h1 className="logo-text">KW마켓</h1>
                </Link>
            </div>
            <div className="header-right">
                {/* 검색 바 */}
                <div className="search-bar">
                    <input
                        type="text"
                        placeholder="검색어를 입력하세요"
                        className="search-input"
                    />
                    <button className="search-button">검색</button>
                </div>

                {/* 상품 등록 */}
                <div className="create-product">
                    <Link to="/products/new">상품 등록</Link>
                </div>

                {/* 마이페이지 */}
                <div className="mypage">
                    <Link to="/mypage">마이페이지</Link>
                </div>

                {/* 로그인/로그아웃 버튼 */}
                <div className="auth-buttons">
                    {isLoggedIn ? (
                        <button className="auth-button logout-button" onClick={handleLogout}>
                            로그아웃
                        </button>
                    ) : (
                        <Link to="/login">
                            <button className="auth-button login-button">로그인</button>
                        </Link>
                    )}
                </div>
            </div>
        </header>
    );
};

export default Header;

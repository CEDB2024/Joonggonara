import React, { useEffect, useState } from "react";
import { Navigate } from "react-router-dom";
import UserService from "../services/UserService"; // UserService 추가
import { useNavigate } from "react-router-dom";

const AdminCheckRoute = ({ children }) => {
    const [isAuthorized, setIsAuthorized] = useState(false);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        const checkAuthorization = async () => {
            const userId = localStorage.getItem("userId"); // 로컬 스토리지에서 userId 가져오기
            if (!userId) {
                alert("로그인이 필요합니다.");
                setIsAuthorized(false);
                setLoading(false);
                return;
            }

            try {
                // 역할 확인
                const role = await UserService.getRole(userId);
                if (role === "admin") {
                    setIsAuthorized(true);
                } else {
                    alert("관리자만 접근 가능합니다.");
                    setIsAuthorized(false);
                }
            } catch (error) {
                console.error("Error checking role:", error);
                alert("권한 확인 중 오류가 발생했습니다.");
                navigate("/login")
                setIsAuthorized(false);
            } finally {
                setLoading(false);
            }
        };

        checkAuthorization();
    }, []);

    if (loading) {
        return <div>Loading...</div>; // 로딩 중 표시
    }

    return isAuthorized ? children : <Navigate to="/login" replace />;
};

export default AdminCheckRoute;

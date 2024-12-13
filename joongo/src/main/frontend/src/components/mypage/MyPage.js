import React, { useEffect, useState } from "react";
import axios from "axios";
import "./MyPage.css";

const MyPage = () => {
    const [userData, setUserData] = useState(null); // 사용자 정보 저장
    const [chargeAmount, setChargeAmount] = useState(""); // 충전 금액 입력값

    // API 호출로 사용자 정보 가져오기
    useEffect(() => {
        const fetchUserData = async () => {
            try {
                // Replace with the API endpoint to fetch user data
                const response = await axios.get("http://localhost:8080/api/user"); // 서버에서 유저 정보 가져오기
                setUserData(response.data); // 서버에서 받은 데이터를 저장
            } catch (error) {
                console.error("Error fetching user data:", error);
            }
        };

        fetchUserData();
    }, []);

    // 돈 충전 기능
    const handleCharge = async () => {
        const amount = parseInt(chargeAmount);
        if (!isNaN(amount) && amount > 0) {
            try {
                // 서버에 충전 요청
                const response = await axios.post("http://localhost:8080/api/user/charge", {
                    userId: userData.user_id, // 유저 ID 전달
                    amount: amount,
                });
                alert(`₩${amount} 충전 완료!`);
                setUserData({ ...userData, money: response.data.money }); // 업데이트된 돈으로 상태 갱신
                setChargeAmount(""); // 입력값 초기화
            } catch (error) {
                console.error("Error during charge:", error);
                alert("충전 중 오류가 발생했습니다.");
            }
        } else {
            alert("올바른 금액을 입력해주세요.");
        }
    };

    // 데이터 로딩 중
    if (!userData) {
        return <div>로딩 중...</div>;
    }

    return (
        <div className="mypage">
            <h1>마이페이지</h1>

            {/* 소지 금액 및 충전 */}
            <div className="balance-section">
                <h2>현재 소지 금액: ₩{userData.money.toLocaleString()}</h2>
                <div className="charge">
                    <input
                        type="number"
                        placeholder="충전 금액 입력"
                        value={chargeAmount}
                        onChange={(e) => setChargeAmount(e.target.value)}
                    />
                    <button onClick={handleCharge}>충전</button>
                </div>
            </div>

            {/* 유저 정보 표시 */}
            <div className="user-info">
                <h3>유저 정보</h3>
                <p>이름: {userData.user_name}</p>
                <p>이메일: {userData.email}</p>
                <p>전화번호: {userData.tel_1}-{userData.tel_2}</p>
                <p>위치: {userData.location}</p>
            </div>

            {/* 추가 기능 - 거래 내역, 판매 목록 등 */}
        </div>
    );
};

export default MyPage;

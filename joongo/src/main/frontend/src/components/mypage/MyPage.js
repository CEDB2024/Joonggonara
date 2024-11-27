import React, { useState } from "react";
import "./MyPage.css";

const MyPage = () => {
    // State 관리
    const [money, setMoney] = useState(50000); // 초기 소지 금액
    const [chargeAmount, setChargeAmount] = useState(""); // 충전 금액 입력값

    // 판매 목록 (데모 데이터)
    const sellingItems = [
        { id: 1, name: "중고 노트북", price: 300000 },
        { id: 2, name: "스마트폰", price: 200000 },
    ];

    // 거래 내역 (데모 데이터)
    const transactionHistory = [
        { id: 1, item: "중고책", price: 15000, date: "2024-11-20" },
        { id: 2, item: "의류", price: 25000, date: "2024-11-22" },
    ];

    // 돈 충전 기능
    const handleCharge = () => {
        const amount = parseInt(chargeAmount);
        if (!isNaN(amount) && amount > 0) {
            setMoney(money + amount);
            alert(`₩${amount} 충전 완료!`);
            setChargeAmount(""); // 입력값 초기화
        } else {
            alert("올바른 금액을 입력해주세요.");
        }
    };

    return (
        <div className="mypage">
            <h1>마이페이지</h1>

            {/* 소지 금액 및 충전 */}
            <div className="balance-section">
                <h2>현재 소지 금액: ₩{money.toLocaleString()}</h2>
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

            {/* 내가 판매 중인 물품 */}
            <div className="selling-section">
                <h2>내가 판매 중인 물품</h2>
                <div className="items">
                    {sellingItems.map((item) => (
                        <div key={item.id} className="item-card">
                            <h3>{item.name}</h3>
                            <p>₩{item.price.toLocaleString()}</p>
                        </div>
                    ))}
                </div>
            </div>

            {/* 거래 내역 */}
            <div className="history-section">
                <h2>거래 내역</h2>
                <ul>
                    {transactionHistory.map((history) => (
                        <li key={history.id}>
                            {history.date} - {history.item}: ₩{history.price.toLocaleString()}
                        </li>
                    ))}
                </ul>
            </div>
        </div>
    );
};

export default MyPage;

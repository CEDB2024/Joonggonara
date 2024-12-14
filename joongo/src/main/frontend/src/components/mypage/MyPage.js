import React, { useState, useEffect } from "react";
import "./MyPage.css";
import MypageService from "../../services/MypageService";

const MyPage = () => {
  const [money, setMoney] = useState(0); // 소지 금액
  const [chargeAmount, setChargeAmount] = useState(""); // 충전 금액 입력값
  const [sellingItems, setSellingItems] = useState([]); // 판매 중인 물품
  const [userInfo, setUserInfo] = useState(null); // 사용자 정보 추가

  const userId = localStorage.getItem("userId"); // 로컬스토리지에서 사용자 ID 가져오기

  // 유저 정보 및 판매 아이템 가져오기
  useEffect(() => {
    const fetchData = async () => {
      try {
        // 사용자 정보 가져오기
        const user = await MypageService.getUserInfo(userId);
        setUserInfo(user);

        // 판매 물품 가져오기
        const products = await MypageService.getUserProducts(userId);
        setSellingItems(products);

        // 사용자 소지 금액 가져오기
        const userResponse = await MypageService.chargeMoney(userId, 0); // 금액 충전을 0으로 요청해 현재 금액만 가져옴
        setMoney(userResponse.newBalance || 0);
      } catch (error) {
        console.error("Error fetching data:", error);
        alert("데이터를 가져오는 데 실패했습니다.");
      }
    };

    fetchData();
  }, [userId]);

  // 금액 충전 핸들러
  const handleCharge = async () => {
    if (!chargeAmount || chargeAmount <= 0) {
      alert("올바른 충전 금액을 입력해주세요.");
      return;
    }

    try {
      const response = await MypageService.chargeMoney(userId, parseInt(chargeAmount));
      setMoney(response.newBalance); // 새 소지 금액 업데이트
      alert("충전이 완료되었습니다.");
      setChargeAmount(""); // 입력값 초기화
    } catch (error) {
      console.error("Error charging money:", error);
      alert("충전 중 오류가 발생했습니다.");
    }
  };

  return (
    <div className="mypage">
      <h1>마이페이지</h1>

      {/* 사용자 정보 */}
      {userInfo && (
        <div className="user-info">
          <h2>회원 정보</h2>
          <p>이름: {userInfo.userName}</p>
          <p>닉네임: {userInfo.nickname}</p>
          <p>이메일: {userInfo.email}</p>
          <p>지역: {userInfo.location}</p>
        </div>
      )}

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
          {sellingItems.length > 0 ? (
            sellingItems.map((item) => (
              <div key={item.id} className="item-card">
                <h3>{item.name}</h3>
                <p>₩{item.price.toLocaleString()}</p>
              </div>
            ))
          ) : (
            <p>판매 중인 물품이 없습니다.</p>
          )}
        </div>
      </div>
    </div>
  );
};

export default MyPage;

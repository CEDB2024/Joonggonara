import React, { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import Layout from "../../../global/Layout";
import "./MyPage.css";
import mypageService from "../../../services/MypageService";
import UserService from "../../../services/UserService";
import OrderService from "../../../services/OrderService";
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from "chart.js";
import { Pie } from "react-chartjs-2";


ChartJS.register(ArcElement, Tooltip, Legend);


const MyPage = () => {
  const [money, setMoney] = useState(0); // 소지 금액
  const [chargeAmount, setChargeAmount] = useState(""); // 충전 금액 입력값
  const [sellingItems, setSellingItems] = useState([]); // 판매 중인 물품
  const [buyOrders, setBuyOrders] = useState([]); // 구매 목록
  const [sellOrders, setSellOrders] = useState([]); // 판매 목록
  const [loading, setLoading] = useState(true); // 데이터 로딩 상태
  const [userInfo, setUserInfo] = useState(null); // 사용자 정보
  const navigate = useNavigate();

  useEffect(() => {
    const userId = localStorage.getItem("userId");
    if (!userId) {
      setLoading(false);
      return;
    }

    const fetchData = async () => {
      try {
        const products = await mypageService.getUserProducts(userId);
        setSellingItems(products);

        const userResponse = await UserService.getUserById(userId);
        setMoney(userResponse.money);

        const buyOrders = await OrderService.getOrderByBuyerId(userId);
        setBuyOrders(buyOrders || []);

        const sellOrders = await OrderService.getOrderBySellerId(userId);
        setSellOrders(sellOrders || []);

        const user = await MypageService.getUserInfo(userId);
        setUserInfo(user);

      } catch (error) {
        console.error("Error fetching data:", error);
        alert("데이터를 가져오는 데 실패했습니다.");
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  const handleCharge = async () => {
    const amount = parseInt(chargeAmount, 10);
    if (isNaN(amount) || amount <= 0) {
      alert("올바른 충전 금액을 입력해주세요.");
      return;
    }

    try {
      const userId = localStorage.getItem("userId");
      const response = await mypageService.chargeMoney(userId, amount);

      // 충전된 금액으로 즉시 화면 업데이트
      setMoney(response.newBalance);

      // 최신 유저 정보를 다시 가져와서 반영
      const updatedUser = await UserService.getUserById(userId);
      setMoney(updatedUser.money);

      alert("충전이 완료되었습니다.");
      setChargeAmount("");
    } catch (error) {
      const errorMessage = error.response?.data?.code || "충전 중 오류가 발생했습니다.";
      console.error("Error charging money:", error);
      alert(errorMessage);
    }
  };

    // 파이 차트 데이터 준비
    const chartData = {
      labels: ["판매 가능", "품절", "예약 중"],
      datasets: [
        {
          data: [
            productStatusData.available || 0,
            productStatusData.sold_out || 0,
            productStatusData.reserved || 0,
          ],
          backgroundColor: ["#4caf50", "#f44336", "#ff9800"],
          hoverBackgroundColor: ["#66bb6a", "#e57373", "#ffb74d"],
        },
      ],
    };
  

  const handleEdit = (productId) => {
    navigate(`/edit`, { state: { productId } });
  };

  if (!localStorage.getItem("userId")) {
    return (
        <Layout>
          <div className="mypage">
            <h1>로그인이 필요합니다.</h1>
          </div>
        </Layout>
    );
  }

  if (loading) {
    return (
        <Layout>
          <div className="mypage">
            <h1>로딩 중...</h1>
          </div>
        </Layout>
    );
  }

  return (
      <Layout>
        <div className="mypage">
          <h1>마이페이지</h1>

          {/* 사용자 정보 */}
          {userInfo && (
            <div className="user-info">
              <h2>회원 정보</h2>
              <p>이름: {userInfo.userName}</p>
              <p>이메일: {userInfo.email}</p>
              <p>전화번호: {userInfo.phone}</p>
              <p>지역: {userInfo.location}</p>
            </div>
          )}
          <div className="balance-section">
            <h2>현재 소지 금액</h2>
            <p className="money">{money ? money.toLocaleString() + "원" : ""}</p>
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

          <div className="selling-section">
            <h2>내가 판매 중인 물품</h2>
            <div className="items">
              {sellingItems.length > 0 ? (
                  sellingItems.map((item) => (
                      <div key={item.productId} className="item-card">
                        <h3>{item.title}</h3>
                        <p>{item.price.toLocaleString()}원</p>
                        <p>수량: {item.count}</p>
                        <div className="actions">
                          <Link to={`/product/${item.productId}`}>보기</Link>
                          <button onClick={() => handleEdit(item.productId)}>수정</button>
                        </div>
                      </div>
                  ))
              ) : (
                  <p className="no-items">판매 중인 물품이 없습니다.</p>
              )}
            </div>
          </div>

          <div className="orders-section">
            <h2>구매 목록</h2>
            <div className="scrollable-list">
              {buyOrders.length > 0 ? (
                  buyOrders.map((order) => (
                      <div key={order.order_id} className="order-item">
                        <span className="order-title">{order.title}</span>
                        <span className="order-detail">수량: {order.count}</span>
                        <span className="order-detail">
                    완료 시간: {new Date(order.completed_at).toLocaleString()}
                  </span>
                      </div>
                  ))
              ) : (
                  <p className="no-items">구매한 상품이 없습니다.</p>
              )}
            </div>
          </div>

          <div className="orders-section">
            <h2>판매 목록</h2>
            <div className="scrollable-list">
              {sellOrders.length > 0 ? (
                  sellOrders.map((order) => (
                      <div key={order.order_id} className="order-item">
                        <span className="order-title">{order.title}</span>
                        <span className="order-detail">수량: {order.count}</span>
                        <span className="order-detail">
                    완료 시간: {new Date(order.completed_at).toLocaleString()}
                  </span>
                      </div>
                  ))
              ) : (
                  <p className="no-items">판매한 상품이 없습니다.</p>
              )}
            </div>
          </div>

          <div className="product-status-chart">
            <h2>상품 상태 비율</h2>
            <Pie data={chartData} />
          </div>

        </div>
      </Layout>
  );
};

export default MyPage;

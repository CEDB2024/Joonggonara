import React, { useState, useEffect } from "react";
import Layout from "../../../global/Layout";
import "./AdminPage.css";
import UserService from "../../../services/UserService";
import ProductService from "../../../services/ProductService";

const AdminPage = () => {
    const [users, setUsers] = useState([]); // 유저 목록
    const [products, setProducts] = useState([]); // 상품 목록
    const [loading, setLoading] = useState(true);

    // 카테고리 정보
    const categories = [
        { id: 0, name: "전체" },
        { id: 1, name: "디지털 기기" },
        { id: 2, name: "가구/인테리어" },
        { id: 3, name: "의류" },
        { id: 4, name: "식물" },
    ];

    // 카테고리 ID를 이름으로 변환하는 헬퍼 함수
    const getCategoryName = (categoryId) => {
        const category = categories.find((cat) => cat.id === categoryId);
        return category ? category.name : "미지정";
    };

    useEffect(() => {
        const fetchData = async () => {
            try {
                const userList = await UserService.getAllUsers(); // 모든 유저 가져오기
                setUsers(userList);

                const productList = await ProductService.getAllProducts(); // 모든 상품 가져오기
                setProducts(productList);
            } catch (error) {
                console.error("Error fetching data:", error);
                alert("데이터를 가져오는 중 오류가 발생했습니다.");
            } finally {
                setLoading(false);
            }
        };

        fetchData();
    }, []);

    const deleteUser = async (userId) => {
        try {
            await UserService.deleteUser(userId);
            alert("유저가 삭제되었습니다.");
            window.location.reload(); // 페이지 새로고침
        } catch (error) {
            console.error("Error deleting user:", error);
            alert("유저 삭제 중 오류가 발생했습니다.");
        }
    };

    const deleteProduct = async (productId) => {
        try {
            await ProductService.deleteProduct(productId);
            alert("상품이 삭제되었습니다.");
            window.location.reload(); // 페이지 새로고침
        } catch (error) {
            console.error("Error deleting product:", error);
            alert("상품 삭제 중 오류가 발생했습니다.");
        }
    };

    if (loading) {
        return (
            <Layout>
                <div className="admin-page">
                    <h1>로딩 중...</h1>
                </div>
            </Layout>
        );
    }

    return (
        <Layout>
            <div className="admin-page">
                <h1>관리자 페이지</h1>
                {/* 유저 관리 */}
                <section className="admin-section">
                    <h2>유저 관리</h2>
                    <table className="admin-table">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>이름</th>
                            <th>이메일</th>
                            <th>관리</th>
                        </tr>
                        </thead>
                        <tbody>
                        {users.map((user) => (
                            <tr key={user.userId}>
                                <td>{user.userId}</td>
                                <td>{user.userName}</td>
                                <td>{user.email}</td>
                                <td>
                                    <button onClick={() => deleteUser(user.userId)}>삭제</button>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </section>

                {/* 상품 관리 */}
                <section className="admin-section">
                    <h2>상품 관리</h2>
                    <table className="admin-table">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>제목</th>
                            <th>가격</th>
                            <th>카테고리</th>
                            <th>관리</th>
                        </tr>
                        </thead>
                        <tbody>
                        {products.map((product) => (
                            <tr key={product.productId}>
                                <td>{product.productId}</td>
                                <td>{product.title}</td>
                                <td>{product.price.toLocaleString()}원</td>
                                <td>{getCategoryName(product.categoryId)}</td> {/* 카테고리 이름 표시 */}
                                <td>
                                    <button onClick={() => deleteProduct(product.productId)}>삭제</button>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </section>
            </div>
        </Layout>
    );
};

export default AdminPage;

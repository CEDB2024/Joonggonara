import React from "react";
import {BrowserRouter as Router, Routes, Route, Navigate} from "react-router-dom";
import Login from "./components/Login";
import Register from "./components/Register";
import PrivateRoute from "./components/PrivateRouter";
import MainPage from "./components/page/main/MainPage";
import MyPage from "./components/page/mypage/MyPage";
import ProductDetailPage from "./components/page/detail/DetailPage";
import CreateProductPage from "./components/page/createProduct/CreateProductPage";
import AdminPage from "./components/page/admin/AdminPage";
import AdminCheckRouter from "./components/AdminCheckRouter";
import SearchPage from "./components/page/search/SearchPage";
import EditProductPage from "./components/page/edit/EditProductPage";
function App() {
    return (
        <Router>
            <Routes>
                {/* 기본 경로는 로그인 페이지로 리다이렉트 */}
                <Route path="/" element={<Navigate to="/login"/>}/>

                {/* 공개 라우트 */}
                <Route path="/login" element={<Login/>}/>
                <Route path="/register" element={<Register/>}/>
                <Route path="/product/:productId" element={<ProductDetailPage/>}/>
                <Route path="/main" element={<MainPage/>}/>
                <Route path="/search" element={<SearchPage/>}/>
                <Route path="/edit" element={<EditProductPage/>}/>
                <Route
                    path="/admin"
                    element={
                        <AdminCheckRouter>
                            <AdminPage/>
                        </AdminCheckRouter>
                    }
                />
                <Route
                    path="/mypage"
                    element={
                        <PrivateRoute>
                            <MyPage/>
                        </PrivateRoute>
                    }
                />
                <Route
                    path="/products/new"
                    element={
                        <PrivateRoute>
                            <CreateProductPage/>
                        </PrivateRoute>
                    }
                />
            </Routes>
        </Router>
    );
}

export default App;

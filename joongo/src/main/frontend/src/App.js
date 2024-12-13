import React from "react";
import {BrowserRouter as Router, Routes, Route, Navigate} from "react-router-dom";
import Login from "./components/Login";
import Register from "./components/Register";
import PrivateRoute from "./components/PrivateRouter";
import MainPage from "./components/page/main/MainPage";
import MyPage from "./components/mypage/MyPage";
import ProductDetailPage from "./components/page/detail/DetailPage";
import CreateProductPage from "./components/page/createProduct/CreateProductPage";

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
                       {/* 인증된 사용자만 접근 가능 */}
                       {/*<Route*/}
                       {/*    path="/main"*/}
                       {/*    element={*/}
                       {/*        <PrivateRoute>*/}
                       {/*            <MainPage />*/}
                       {/*        </PrivateRoute>*/}
                       {/*    }*/}
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

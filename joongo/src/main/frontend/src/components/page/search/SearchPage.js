import React, { useState } from "react";
import { useLocation } from "react-router-dom";
import Layout from "../../../global/Layout";
import ProductService from "../../../services/ProductService";
import "./SearchPage.css";

const SearchPage = () => {
    const location = useLocation();
    const initialProducts = location.state?.products || [];
    const [searchInput, setSearchInput] = useState("");
    const [searchResults, setSearchResults] = useState(initialProducts);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const handleSearch = async (e) => {
        e.preventDefault();
        setError(null);
        setLoading(true);

        if (searchInput.trim().length < 2) {
            setError("검색어는 2글자 이상 입력해주세요.");
            setLoading(false);
            return;
        }

        try {
            const results = await ProductService.selectByTitle(searchInput);
            setSearchResults(results);
        } catch (err) {
            console.error("[Search Error]:", err);
            setError("검색 중 오류가 발생했습니다.");
        } finally {
            setLoading(false);
        }
    };

    return (
        <Layout>
            <div className="search-page">
                <header className="header">
                    <h1>검색 페이지</h1>
                </header>

                <form className="search-form" onSubmit={handleSearch}>
                    <input
                        type="text"
                        placeholder="검색어 입력"
                        value={searchInput}
                        onChange={(e) => setSearchInput(e.target.value)}
                        className="search-input"
                    />
                    <button type="submit" className="search-button">
                        검색
                    </button>
                </form>

                {loading && <p className="loading-message">검색 중...</p>}
                {error && <p className="error-message">{error}</p>}

                <div className="results-container">
                    {searchResults.length > 0 ? (
                        searchResults.map((product) => (
                            <div className="product-card" key={product.productId}>
                                <img
                                    src={product.image || "https://via.placeholder.com/200x150"}
                                    alt={product.title}
                                    className="product-image"
                                />
                                <h2 className="product-name">{product.title}</h2>
                                <p className="product-price">₩{product.price.toLocaleString()}</p>
                            </div>
                        ))
                    ) : (
                        !loading &&
                        !error && <p className="no-results-message">검색 결과가 없습니다.</p>
                    )}
                </div>
            </div>
        </Layout>
    );
};

export default SearchPage;

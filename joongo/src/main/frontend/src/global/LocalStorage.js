const getLocalStorage = (key, defaultValue = null) => {
    try {
        const storedValue = localStorage.getItem(key);
        if (storedValue === null) {
            return defaultValue; // 데이터가 없으면 기본값 반환
        }
        // JSON.parse를 사용해 객체/배열 파싱 (실패 시 원본 문자열 반환)
        try {
            return JSON.parse(storedValue);
        } catch (error) {
            return storedValue; // 파싱 실패 시 원본 문자열 반환
        }
    } catch (error) {
        console.error(`[getLocalStorage Error]: ${error.message}`);
        return defaultValue;
    }
};

export default getLocalStorage;
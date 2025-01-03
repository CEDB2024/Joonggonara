import axios from 'axios';

// Axios 인스턴스 생성
const axiosInstance = axios.create({
  baseURL: 'http://your-api-url', // API 기본 URL
  timeout: 5000,                 // 요청 제한 시간 설정
});

// 요청 인터셉터
axiosInstance.interceptors.request.use(
  (config) => {
    // 요청 로그 출력
    console.log('Axios Request:', {
      url: config.url,
      method: config.method,
      headers: config.headers,
      data: config.data,
    });
    return config;
  },
  (error) => {
    console.error('Axios Request Error:', error);
    return Promise.reject(error);
  }
);

// 응답 인터셉터
axiosInstance.interceptors.response.use(
  (response) => {
    // 응답 로그 출력
    console.log('Axios Response:', {
      status: response.status,
      data: response.data,
      headers: response.headers,
    });
    return response;
  },
  (error) => {
    if (error.response) {
      // 서버에서 반환한 에러 응답 로그 출력
      console.error('Axios Response Error:', {
        status: error.response.status,
        data: error.response.data,
        headers: error.response.headers,
      });
    } else if (error.request) {
      // 서버로부터 응답이 없는 경우
      console.error('Axios Request No Response:', error.request);
    } else {
      // 요청 설정 중 에러 발생
      console.error('Axios Error:', error.message);
    }
    return Promise.reject(error);
  }
);

export default axiosInstance;

const AUTH_TOKEN_KEY = 'vibe_auth_token';
const USER_NAME_KEY = 'vibe_user_name';

const auth = {
    // 토큰 저장
    saveToken(token, username) {
        localStorage.setItem(AUTH_TOKEN_KEY, token);
        localStorage.setItem(USER_NAME_KEY, username);
    },

    // 토큰 가져오기
    getToken() {
        return localStorage.getItem(AUTH_TOKEN_KEY);
    },

    // 사용자 이름 가져오기
    getUsername() {
        return localStorage.getItem(USER_NAME_KEY);
    },

    // 로그아웃
    logout() {
        localStorage.removeItem(AUTH_TOKEN_KEY);
        localStorage.removeItem(USER_NAME_KEY);
        window.location.href = '/login';
    },

    // 인증 헤더를 포함한 Fetch
    async fetchWithAuth(url, options = {}) {
        const token = this.getToken();
        const headers = {
            'Content-Type': 'application/json',
            ...options.headers,
        };

        if (token) {
            headers['Authorization'] = `Bearer ${token}`;
        }

        const response = await fetch(url, { ...options, headers });
        
        if (response.status === 403 || response.status === 401) {
            this.logout();
            return null;
        }
        
        return response;
    },

    // 로그인 상태 체크 (로그인 페이지가 아닌데 토큰이 없으면 리다이렉트)
    checkAuth() {
        if (!this.getToken() && window.location.pathname !== '/login' && window.location.pathname !== '/register') {
            window.location.href = '/login';
        }
    }
};

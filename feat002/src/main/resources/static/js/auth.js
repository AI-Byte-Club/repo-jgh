const AUTH_TOKEN_KEY = 'vibe_auth_token';
const USER_NAME_KEY = 'vibe_user_name';
const USER_ROLE_KEY = 'vibe_user_role';

const auth = {
    // 토큰 및 정보 저장
    saveToken(token, username, role) {
        localStorage.setItem(AUTH_TOKEN_KEY, token);
        localStorage.setItem(USER_NAME_KEY, username);
        localStorage.setItem(USER_ROLE_KEY, role);
    },

    // 정보 가져오기
    getToken() { return localStorage.getItem(AUTH_TOKEN_KEY); },
    getUsername() { return localStorage.getItem(USER_NAME_KEY); },
    getRole() { return localStorage.getItem(USER_ROLE_KEY); },

    // 로그아웃
    logout() {
        localStorage.removeItem(AUTH_TOKEN_KEY);
        localStorage.removeItem(USER_NAME_KEY);
        localStorage.removeItem(USER_ROLE_KEY);
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

    // 로그인 상태 체크
    checkAuth() {
        const token = this.getToken();
        const path = window.location.pathname;
        
        // 로그인/회원가입 페이지가 아닌데 토큰이 없으면 로그인으로 이동
        if (!token && path !== '/login' && path !== '/register') {
            window.location.href = '/login';
        }
        // 로그인이 되어 있는데 로그인/회원가입 페이지에 접속하면 메인으로 이동
        if (token && (path === '/login' || path === '/register')) {
            window.location.href = '/';
        }
    }
};

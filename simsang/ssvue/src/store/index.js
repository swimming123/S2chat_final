import axios from 'axios';
import { createStore } from 'vuex';
export default createStore({
  state: {
    // 초기화 localStorage 선언
    id: localStorage.getItem("id") || "",
    token: localStorage.getItem("token") || "",
    membernum: localStorage.getItem("membernum") || "",
  },
  getters: {
    // 인증 했는지 판단해서 토큰이 있으면 true, 없으면 false
    isAuthenticated: state => !!state.token,
    // 로그인한 사용자 값 반환 
    username: state => state.id
  },
  mutations: { // state의 속성값을 변경하는 속성 
    setAuth(state, { id, token, membernum }) {
      state.id = id;
      state.token = token;
      state.membernum = membernum;

      // 받은 토큰과 사용자 아이디를 로컬스토리지에 저장
      localStorage.setItem('id', id);
      localStorage.setItem('token', token);
      localStorage.setItem('membernum', membernum);
    },
    clearAuth(state) {
      state.id = '';
      state.token = '';
      state.membernum = '';
      localStorage.removeItem('id');
      localStorage.removeItem('token');
      localStorage.removeItem('membernum');
    }
  },
  actions: {
    // ${process.env.VUE_APP_BACK_END_URL}/login/logintest
    // 로그인 아이디/비번 입력을 받고 서버로 보낸후에
    // 아직 서버에는 디비 작업을 안했지만 
    login({ commit }, { id, password }) {
      return axios.post(`${process.env.VUE_APP_BACK_END_URL}/login/logintest`, 
        { id, password })
        .then(response => {
          const id = response.data.id; //받은 아이디와
          const token = response.data.accessToken; // 받은 토큰을
          const membernum = response.data.membernum; // 받은 멤버번호

          console.log('Login response:', { id, token, membernum }); // 콘솔에 로그인 응답 출력

          commit('setAuth', { id, token, membernum }) //mutations의 setAuth에게 전달해서 스토리지에 저장하고 
        })
        .catch(error => {
          console.error('Login failed:', error); // 에러 출력
          throw new Error('Invalid credentials');
        })
    },
    logout({ commit, state }) {
      return axios.post(`${process.env.VUE_APP_BACK_END_URL}/login/logout`, {}, {
        headers: {
          'Authorization': `Bearer ${state.token}`
        }
      })
        .then(() => {
          commit('clearAuth'); //mutations의 clearAuth 를 호출   
        })
        .catch(error => {
          console.error('Logout failed', error);
        });
    },
    // setAuth action 추가
    setAuth({ commit }, { id, token, membernum }) {
      console.log('Setting auth:', { id, token, membernum }); // 콘솔에 auth 설정 출력
      commit('setAuth', { id, token, membernum });
    }
  }
});

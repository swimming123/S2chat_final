/*UI개발: 이지영, 이승희 */
<template>
  <div class="main">
    <div class="container">
      <div class="row justify-content-center align-items-center">
        <div class="col-md-6 col-lg-4">
          <h1 class="text-center mb-4">로그인</h1>
          <form @submit.prevent="submitLogin">
            <div class="form-group mb-3">
              <label for="id">아이디</label>
              <input
                type="text"
                class="form-control"
                id="id"
                placeholder="******"
                v-model="id"
                maxlength="10"
              />
            </div>
            <div class="form-group mb-3">
              <label for="pwd">비밀번호</label>
              <input
                type="password"
                class="form-control"
                id="pwd"
                placeholder="******"
                v-model="pwd"
                maxlength="10"
              />
            </div>
            {{ errorMessage }}
            <div class="form-group text-end">
              <button type="submit" class="btn btn-primary me-2">Login</button>
            </div>
            <div class="form-group text-end">
              <button type="button" @click="findid()" class="btn btn-secondary">
                아이디찾기
              </button>
              <button
                type="button"
                @click="findpwd()"
                class="btn btn-secondary"
              >
                비밀번호찾기
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions } from "vuex";
export default {
  data() {
    return {
      id: "",
      pwd: "",
      errorMessage: "",
    };
  },
  methods: {
    ...mapActions(["login"]),
    submitLogin() {
      this.errorMessage = "";
      this.login({ id: this.id, password: this.pwd })
        .then(() => {
          this.$router.push("/");
        })
        .catch((err) => {
          if (err) {
            // 응답 상태 코드에 따라 메시지 설정
            this.errorMessage = "아이디와 비밀번호가 일치하지 않습니다."
            // else {
            //   // 다른 상태 코드에 대한 메시지 설정
            //   this.errorMessage =
            //     err.response.data.message || "로그인 중 오류가 발생했습니다.";
            // }
          } else {
            // 네트워크 오류 또는 서버 응답이 없는 경우
            this.errorMessage =
              "서버에 연결할 수 없습니다. 나중에 다시 시도해 주세요.";
          }
        });
    },
    findid() {
      this.$router.push({ name: "FindId" });
    },
    findpwd() {
      this.$router.push({ name: "FindPwd" });
    },
  },
};
</script>
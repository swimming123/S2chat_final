/*UI개발: 이승희, 이지영 */
<template>
  <div class="main">
    <div class="container mt-3">
      <div class="row justify-content-center align-items-center">
        <div class="col-md-6 col-lg-4">
          <h1 class="text-center mb-4">비밀번호 찾기</h1>

          <div class="main_contact_content">
            <div class="contact_form_area">
              <form action="#" id="formid">
                <div class="tabs">
                  <button
                    :class="{ active: isTab }"
                    @click.prevent="isTab = true"
                  >
                    휴대폰 인증
                  </button>

                  <button
                    :class="{ active: !isTab }"
                    @click.prevent="isTab = false"
                  >
                    이메일 인증
                  </button>
                </div>

                <label
                  for="inputName"
                  style="height: 100%; display: flex; align-items: center"
                  >아이디</label
                >
                <div>
                  <input
                    :type="inputType"
                    class="form-control"
                    id="id"
                    placeholder="아이디를 입력하세요"
                    v-model="id"
                  />
                </div>

                <label
                  for="inputContact"
                  style="
                    height: 100%;
                    display: flex;
                    align-items: center;
                    margin-top: 45px;
                  "
                  >{{ contactLabel }}</label
                >

                <div v-if="isTab">
                  <label
                  for="inputName"
                  style="height: 100%; display: flex; align-items: center"
                  >휴대폰</label
                >
                  <input
                    :type="inputType"
                    class="form-control"
                    id="tel"
                    :placeholder="'휴대폰 번호를 입력하세요.'"
                    v-model="tel"
                  />
                  <button
                    class="w-100 btn btn-primary"
                    type="button"
                    style="margin: 30px 0px"
                    @click="telcheckpwd"
                  >
                    임시 비밀번호 받기
                  </button>
                </div>
                
                <div v-else>
                  <label
                  for="inputName"
                  style="height: 100%; display: flex; align-items: center"
                  >이메일</label
                >
                  <input
                    :type="inputType"
                    class="form-control"
                    id="email"
                    :placeholder="'이메일을 입력해주세요.'"
                    v-model="email"
                  />
                  <button
                    class="w-100 btn btn-primary"
                    type="button"
                    style="margin: 30px 0px"
                    @click="emailcheckpwd"
                  >
                  임시 비밀번호 받기
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import axios from "axios";
export default {
  data() {
    return {
      isTab: true,
      contact: "",
    };
  },
  computed: {
    canRequestCode() {
      return this.id && this.contact;
    },
  },
  methods: {
    requestCode() {
      alert("인증번호가 발송되었습니다.");
    },
    emailcheckpwd() {
      if(!this.id || !this.email){
        alert("아이디와 이메일을 확인해주세요.");
        return
      }
      axios
        .post(`${process.env.VUE_APP_BACK_END_URL}/login/findpwd`, null, {
          params: {
            id: this.id,
            email: this.email,
          },
        })
        .then((res) => {
          if (res.data === 1) {
            alert("가입 내용이 확인되지 않습니다. 이름과 이메일을 확인해주세요.");
          } else {
            this.$router.push({
              name: "FindPwdCheck",
              params: {email:this.email },
            });
          }
        })
        .catch((error) => {
          alert("이메일 발송 오류");
          console.error("회원 데이터 조회 중 오류 발생:", error);
        });
    },
  },
};
</script>

<style>
.tabs {
  display: flex;
  justify-content: space-around;
  margin: 0px 0px 30px 0px;
  font-family: "NotoSansKR";
  padding: auto;
  width: 100%;
}
.tabs button {
  background-color: transparent;
  border: none;
  padding: 10px;
  cursor: pointer;
  font-size: 16px;
  outline: none;
  padding: auto;
  color: black;
  width: 100%;
}
.tabs button.active {
  border-bottom: 2px solid rgb(233, 101, 53);
  color: rgb(233, 101, 53);
}
.form-control {
  font-family: "NotoSansKR";
  height: 45px;
  border: 1px solid gray;
  border-radius: 5px;
  background-color: rgb(235, 230, 230);
}
.form-control:focus {
  border: 1px solid rgb(233, 101, 53);
}
h2 {
  font-size: 40px;
  line-height: 38px;
  font-weight: 600;
  font-family: "NotoSansKR";
  margin: 0;
}
p {
  font: normal 16px "NotoSansKR", arial;
}
label,
a {
  font: bold 16px "NotoSansKR", arial;
  font-weight: bold;
}
.btn1 {
  border: none;
  border-radius: 5px;
  background-color: rgb(139, 135, 133);
  width: 100%;
  height: 50px;
  font: bold 16px "NotoSansKR", arial;
  font-weight: bold;
  color: antiquewhite;
}
.btn1:hover {
  border: none;
  border-radius: 5px;
  background-color: rgb(233, 101, 53);
  width: 100%;
  height: 50px;
  font: bold 16px "NotoSansKR", arial;
  font-weight: bold;
  color: antiquewhite;
}
.btn2 {
  border: 1px solid rgb(233, 101, 53);
  border-radius: 5px;
  width: 100%;
  height: 50px;
  font: bold 16px "NotoSansKR", arial;
  font-weight: bold;
  color: rgb(233, 101, 53);
}
</style>

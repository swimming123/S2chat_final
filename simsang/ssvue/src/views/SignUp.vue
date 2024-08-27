/*UI개발: 이승희, 이지영 */
<template>
  <div class="main">
    <div class="container">
      <div class="row justify-content-center align-items-center">
        <div class="col-md-6 col-xl-4">
          <h1 class="text-center mb-4">회원가입</h1>
          <p style="text-align: right; margin-top: 30px; font-size: small">
            <!--  필수입력사항 -->
          </p>
          <hr style="border-color: gray" />

          <form>
            <div class="row">
              <label for="id">아이디</label>
              <div class="form-group d-flex align-items-center">
                <input
                  type="text"
                  class="form-control"
                  id="id"
                  placeholder="아이디를 입력하세요"
                  v-model="id"
                  maxlength="10"
                />
                <button
                  class="btn btn-primary"
                  type="button"
                  style="white-space: nowrap"
                  @click="idDuplicate"
                >
                  중복확인
                </button>
              </div>
            </div>
            <div class="row">
              <label for="password">비밀번호</label>
              <div class="col-xs-6">
                <input
                  type="password"
                  class="form-control"
                  id="pwd"
                  v-model="pwd"
                  placeholder="비밀번호를 입력하세요"
                />
              </div>
            </div>
            <div class="row">
              <label for="password">비밀번호 확인</label>
              <div class="col-xs-6">
                <input
                  type="password"
                  class="form-control"
                  id="pwdchk"
                  placeholder="비밀번호를 한번 더 입력하세요"
                  v-model="pwdchk"
                  @input="checkPasswordMatch"
                />
              </div>
              <div class="col-xs-3">
                <p
                  v-if="pwdchk && passwordMatch"
                  style="color: green; font-size: 12px"
                >
                  비밀번호가 일치
                </p>
                <p
                  v-if="pwdchk && !passwordMatch"
                  style="color: red; font-size: 12px"
                >
                  비밀번호가 불일치
                </p>
              </div>
            </div>

            <div class="row">
              <label for="inputID">닉네임</label>
              <div class="col-xs-6">
                <input
                  type="text"
                  class="form-control"
                  id="nick"
                  placeholder="닉네임을 입력하세요"
                  v-model="nick"
                />
              </div>
            </div>

            <label for="id">이메일</label>
            <div class="form-group d-flex align-items-center">
              <input
                type="email"
                class="form-control"
                id="email"
                placeholder="예:ict@market.com"
                v-model="email"
                maxlength="30"
                :readonly="emailsend"
              />

              <button
                class="btn btn-primary"
                type="button"
                @click="emailDuplicate"
              >
                인증
              </button>
            </div>
            <div v-if="isLoading" class="loading-overlay">
              <div class="loading-content">
                <p>이메일 전송중... 잠시만 기다려주세요.</p>
                <div class="spinner"></div>
              </div>
            </div>
            <div v-if="timeLeft" style="align-items: normal">
              <div class="form-group d-flex align-items-center">
                <input
                  type="text"
                  class="form-control"
                  id="code"
                  placeholder="인증번호를 입력하세요"
                  v-model="code"
                  :disabled="isCodeDisabled"
                />
                <button
                  class="btn btn-primary col-xs-3"
                  type="button"
                  @click="emailsubmit"
                >
                  인증번호 확인
                </button>
              </div>
              <p
                style="
                  text-align: right;
                  color: rgb(233, 101, 53);
                  font-size: 12px;
                "
              >
                남은 시간: {{ formattedTimeLeft }}
              </p>
            </div>

            <div class="row" v-if="certificationMessage">
              <div class="col-xs-3"></div>
              <div class="col-xs-9">
                <div :class="['error-msg-area', certificationMessage.color]">
                  <p style="font-size: 12px">
                    {{ certificationMessage.text }}
                  </p>
                </div>
              </div>
            </div>

            <div class="row">
              <label for="inputID">휴대폰</label>
              <div class="col-xs-6">
                <input
                  type="text"
                  class="form-control"
                  id="tel"
                  placeholder="숫자만 입력해주세요"
                  v-model="tel"
                />
              </div>
            </div>

            <label for="address">주소</label>
            <div class="form-group d-flex align-items-center">
              <input
                type="text"
                class="form-control"
                id="address"
                placeholder="주소를 입력하세요."
                v-model="selectedAddress"
                maxlength="10"
                readonly
              />

              <button
                class="btn btn-primary"
                type="button"
                style="white-space: nowrap"
                @click="openAddressModal"
              >
                주소 검색
              </button>
            </div>

            <div class="row">
              <label for="inputID" class="col-xs-3"></label>
              <div class="col-xs-6">
                <input
                  v-if="selectedAddress"
                  type="text"
                  class="form-control"
                  placeholder="상세 주소를 입력하세요"
                  v-model="detailaddress"
                />
              </div>
            </div>

            <!-- 모달 컴포넌트 -->
            <address-modal
              v-if="isAddressModalOpen"
              @close="isAddressModalOpen = false"
              @select-address="selectAddress"
            ></address-modal>

            <!--<div class="row">
              <label for="inputID" class="col-xs-3"></label>
              <div class="col-xs-6">
                <p style="font-size: 11px">
                  배송지에 따라 상품 정보가 달라질 수 있습니다.
                </p>
              </div>
            </div> -->

            <!-- 신분증 파일 업로드 -->
            <div class="box text-center mt-3">
              <div class="file-upload">
                <label for="file-upload" class="btn btn-primary">
                  <span class="fas fa-camera"></span> 신분증 등록하기
                  <input
                    type="file"
                    id="file-upload"
                    name="file1"
                    accept="image/*, video/*"
                    @input="updateImage"
                  />
                </label>
              </div>

              <div class="row">
                <img :src="this.imagePreview" style="object-fit: cover" />
                <p v-if="imgloading">잠시만 기다려주세요...</p>
              </div>
              <!-- 신분증 정보 뙇-->
              <div v-if="idpop" class="row d-flex justify-content-center">
                <table class="table mt-4">
                  <tr>
                    <th>이름</th>
                    <td>{{ name }}</td>
                  </tr>
                  <tr>
                    <th>생년월일</th>
                    <td>
                      {{ birth }}
                    </td>
                  </tr>

                  <tr>
                    <th>성별</th>
                    <td>
                      {{ gender }}
                    </td>
                  </tr>
                </table>
              </div>
            </div>
            <!-- 신분증 정보 뙇 끝 -->
            <!-- 캐릭터 선택 -->
            <div class="box text-center mt-3">
              <div class="row d-flex justify-content-center">
                <label for="file-upload">
                  캐릭터 선택하기<br />
                  <div class="my-3">
                    <div v-if="selectedcharacter">
                      <img
                        :src="'./images/' + selectedcharacter"
                        :alt="selectedcharacter"
                        class="rounded-circle"
                        style="height: 100px; width: 100px; object-fit: cover"
                      /><br />
                    </div>
                  </div>
                  <button
                    class="btn btn-primary"
                    type="button"
                    @click="opencharacterModal"
                  >
                    캐릭터 선택
                  </button>
                </label>

                <character-modal
                  v-if="ischaracterModalOpen"
                  @close="ischaracterModalOpen = false"
                  @select-character="selectcharacter"
                ></character-modal>
              </div>
            </div>
            <!--캐릭터 끝-->

            <div class="align-items-center d-flex my-3">
              <button
                class="btn btn-primary m-auto"
                type="button"
                @click="join"
              >
                가입하기
              </button>
            </div>
          </form>
        </div>
      </div>

      <div v-if="popupVisible" class="popup">
        <div class="popup-content">
          <button class="close-btn" @click="hidePopup">닫기</button>
          <h2>회원가입 완료</h2>
          <p>축하합니다! 회원가입이 완료되었습니다.</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import AddressModal from "./AddressModal.vue";
import CharacterModal from "./CharacterModal.vue";

export default {
  data() {
    return {
      pwdchk: "",
      passwordMatch: false, // 비밀번호 일치 여부를 저장할 변수
      code: "",
      formData: new FormData(),
      isCodeDisabled: false,
      timeLeft: 0, // 3분 (180초)
      certificationMessage: null, // 인증 메시지를 저장할 데이터
      IdCertSubmit: false,
      idpop: false,
      isLoading: false,
      isAddressModalOpen: false, // 주소 모달 상태
      ischaracterModalOpen: false,
      selectedAddress: null, // 선택된 주소 저장
      selectedFile: null,
      imagePreview: null,
      name: "",
      birth: "",
      gender: "",
      imgloading: false,
    };
  },

  components: {
    AddressModal,
    CharacterModal,
  },
  computed: {
    formattedTimeLeft() {
      const minutes = Math.floor(this.timeLeft / 60);
      const seconds = this.timeLeft % 60;
      return `${minutes}:${seconds < 10 ? "0" : ""}${seconds}`;
    },
  },
  methods: {
    //비밀번호 체크
    checkPasswordMatch() {
      if (this.pwd !== this.pwdchk) {
        this.passwordMatch = false;
      } else {
        this.passwordMatch = true;
      }
    },
    emailDuplicate() {
      this.isLoading = true;
      axios
        .post(`${process.env.VUE_APP_BACK_END_URL}/api/auth/emailCheck`, null, {
          params: {
            email: this.email,
          },
        })
        .then((res) => {
          alert("Res DAta:" + res.data);
          if (res.data == 0) {
            alert("인증번호가 발송되었습니다.");
            this.startTimer();
            this.emailsend = true;
          } else {
            this.certificationMessage = {
              text: "이미 사용중인 이메일입니다.",
              color: "red",
            };
          }
          this.isLoading = false;
        })
        .catch((error) => {
          alert("인증 번호 발송에 오류가 발생했습니다.");
          console.error("API 호출 에러(인증번호 발송)", error);
          this.isLoading = false;
          return false;
        });
    },
    beforeDestroy() {
      // 컴포넌트 파괴 전 타이머 정리
      if (this.timerInterval) {
        clearInterval(this.timerInterval);
      }
    },
    startTimer() {
      this.isCodeDisabled = false;
      this.timeLeft = 180;
      this.timerInterval = setInterval(() => {
        if (this.timeLeft > 0) {
          this.timeLeft--;
        } else {
          this.isCodeDisabled = true;
          clearInterval(this.timerInterval);
        }
      }, 1000);
    },
    updateImage(event) {
      this.idpop = false;
      this.imgloading = true;
      const fileContent = event.target.files[0];
      this.imagePreview = URL.createObjectURL(fileContent);

      const formData = new FormData();
      formData.append("file", fileContent);
      // axios.post(`http://192.168.92:9000/djgmarket/insert_img`, formData,
      axios
        .post(`http://192.168.92:9000/djgmarket/insert_img`, formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        })
        .then((res) => {
          this.name = res.data.name;
          this.birth = res.data.birth;
          this.gender = res.data.gender;
          this.imgloading = false;
          this.idpop = true;
        })
        .catch((error) => {
          alert("인식이 불가한 이미지입니다.")
          console.error("There was an error!", error);
          this.imgloading = false;
        });
    },

    emailsubmit() {
      // 이메일 인증번호 확인
      axios
        .post(
          `${process.env.VUE_APP_BACK_END_URL}/api/auth/emailCheck/certification`,
          null,
          {
            params: {
              email: this.email,
              code: this.code, // 요청 매개변수
            },
          }
        )
        .then((res) => {
          if (res.data == 1) {
            this.certificationMessage = {
              text: "인증번호가 일치합니다.",
              color: "green",
            };
            this.emailcheck = true;
            this.timeLeft = 0;
          } else {
            this.certificationMessage = {
              text: "인증번호가 일치하지 않습니다.",
              color: "red",
            };
          }
        })
        .catch((error) => {
          console.error("API 호출 에러(인증번호 확인):", error);
        });
    },
    idDuplicate() {
      if (!this.id) {
        alert("아이디를 입력해주세요.");
        return;
      }
      axios
        .post(`${process.env.VUE_APP_BACK_END_URL}/member/idcheck`, null, {
          params: {
            id: this.id,
          },
        })
        .then((res) => {
          if (res.data === 1) {
            alert("이미 사용중인 아이디입니다. 다른 아이디를 입력해주세요.");
          } else {
            alert("사용할 수 있는 아이디입니다.");
            this.idcheck = true;
          }
        })
        .catch((error) => {
          console.error("아이디 중복 확인 에러:", error);
        });
    },
    openAddressModal() {
      this.isAddressModalOpen = true;
    },

    opencharacterModal() {
      this.ischaracterModalOpen = true;
    },
    selectAddress(address) {
      // 여기서 선택된 주소를 사용하여 원하는 작업을 수행합니다.
      this.selectedAddress = address;
      this.isAddressModalOpen = false;
    },
    selectcharacter(character) {
      // 여기서 선택된 주소를 사용하여 원하는 작업을 수행합니다.
      this.selectedcharacter = character;
      this.ischaracterModalOpen = false;
    },
    join() {
      const formData = new FormData();
      // 필수 입력항목 입력하지 않았을때
      if (
        !this.id ||
        !this.pwd ||
        !this.nick ||
        !this.tel ||
        !this.selectedAddress ||
        !this.detailaddress
      ) {
        alert("입력 항목을 모두 입력해주세요.");
        return;
      }

      if (!this.id) {
        alert("아이디를 중복확인해주세요");
        return;
      }

      if (!this.emailcheck) {
        alert("이메일을 인증해주세요");
        return;
      }

      if (!(this.pwdchk && this.passwordMatch)) {
        alert("비밀번호를 확인해주세요.");
        return;
      }
      formData.append("id", this.id);
      formData.append("pwd", this.pwd);
      formData.append("nick", this.nick);
      formData.append("email", this.email);
      formData.append("tel", this.tel);
      formData.append("address", this.address);
      formData.append("detailaddress", this.detailaddress);

      const name = this.name == null ? "" : this.name;
      const age = this.age == null ? "" : this.age;
      const gender = this.gender == null ? "" : this.gender;
      const imgn =
        this.selectedcharacter == null ? "noimg.png" : this.selectedcharacter;
      formData.append("name", name);
      formData.append("age", age);
      formData.append("gender", gender);
      formData.append("imgn", imgn);

      // FormData의 모든 항목에 대해 공백문자 체크
      for (const [key, value] of formData.entries()) {
        if (typeof value === "string") {
          if (/\s/.test(value)) {
            console.log(`${key}에 공백문자가 포함되어 있습니다.`);
            return;
          }
        } else {
          console.log(`${key}의 값이 문자열이 아닙니다.`);
          return;
        }
      }

      axios
        .post(`${process.env.VUE_APP_BACK_END_URL}/member/add`, formData)
        .then((res) => {
          if (res.status == 200) {
            alert("회원가입이 완료되었습니다.");
            this.$router.push("/");
          }
        })
        .catch((error) => {
          console.error("회원가입 에러:", error);
        });
    },
  },
};
</script>
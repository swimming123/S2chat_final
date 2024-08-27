/*UI개발:이승희*/
<template>
  <div class="main">
    <div class="container">
      <div class="row justify-content-center align-items-center w-100">
        <div class="col-md-6 col-xl-4">
          <h1 class="text-center mb-4 mt-5">결제수단 등록하기</h1>
        </div>
        <hr style="border-color: gray" />
        <div class="col-md-6 col-xl-4">
          <!-- 결제수단 선택 -->
          <label for="payment-method">결제수단 선택: </label>
          <select v-model="payment.payway" class="form-select">
            <option value="" disabled>결제수단을 선택하세요</option>
            <option value="card">신용카드</option>
            <option value="bank">계좌이체</option>
          </select>

          <!-- 결제수단 별명 -->
          <div class="mt-3">
            <label for="paynick">결제수단 별명: </label>
            <input type="text" v-model="payment.paynick" class="form-control" />
          </div>

          <!-- 신용카드 입력 필드 -->
          <div v-if="payment.payway === 'card'" class="mt-3">
            <label for="image-upload">신용카드 사진 간편등록: </label>
            <input
              type="file"
              id="image-upload"
              @change="handleFileUpload"
              class="form-control"
            />
            <div v-if="cardInfo.number" class="mt-3">
              <h3>신용카드 정보</h3>
              <div class="mb-2">
                <label>카드 번호:</label>
                <input
                  type="text"
                  v-model="cardInfo.number"
                  class="form-control"
                />
              </div>
              <div class="mb-2" v-if="cardInfo.expiry">
                <label>유효 기간:</label>
                <input
                  type="text"
                  v-model="cardInfo.expiry"
                  class="form-control"
                />
              </div>
              <div class="mb-2" v-if="cardInfo.cvc">
                <label>CVC:</label>
                <input
                  type="text"
                  v-model="cardInfo.cvc"
                  class="form-control"
                />
              </div>
            </div>
          </div>

          <!-- 계좌이체 입력 필드 -->
          <div v-if="payment.payway === 'bank'" class="mt-3">
            <div class="mb-2">
              <label>은행명:</label>
              <input
                type="text"
                v-model="bankInfo.paycorp"
                class="form-control"
              />
            </div>
            <div class="mb-2">
              <label>계좌번호:</label>
              <input
                type="text"
                v-model="bankInfo.paynum"
                class="form-control"
              />
            </div>
          </div>

          <!-- 버튼 영역 -->
          <div class="text-center my-3">
            <button
              class="btn btn-primary"
              type="button"
              @click="handlePayment"
            >
              {{ paymentnum ? "수정" : "추가" }}
            </button>
            <button class="btn btn-secondary" type="button" @click="goBack">
              돌아가기
            </button>
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
      membernum: localStorage.getItem("membernum"),
      paymentnum: null,
      cardInfo: [{number: "", expiry: "", cvc: ""}], // 신용카드 정보
      payment: {
        payway: "", // 결제수단 선택
        paynick: "", // 결제수단 별명
      },
      bankInfo: { paycorp: "", paynum: "" }, // 계좌이체 정보
    };
  },
  created() {
    this.paymentnum = this.$route.query.paymentnum;
    if (this.paymentnum) {
      this.loadPaymentDetails();
    }
  },
  mounted() {
    if (!localStorage.getItem("reloaded")) {
      localStorage.setItem("reloaded", "true");
      window.location.reload(true);
    } else {
      localStorage.removeItem("reloaded");
    }
    this.$nextTick(() => {
      window.scrollTo(0, 0); // 또는 저장된 스크롤 위치
    });
  },
  methods: {
    handleFileUpload(event) {
      const file = event.target.files[0];
      if (file) {
        const formData = new FormData();
        formData.append("file", file);
        axios
          .post(
            `http://192.168.92:9000/djgmarket/detect_card_number`,
            formData,
            {
              headers: {
                "Content-Type": "multipart/form-data",
              },
            }
          )
          .then((response) => {
            this.cardInfo = response.data;
          })
          .catch((error) => {
            console.error("Error uploading file:", error);
          });
      }
    },
    loadPaymentDetails() {
      axios
        .post(`${process.env.VUE_APP_BACK_END_URL}/payment/detail`, null, {
          params: { paymentnum: this.paymentnum },
        })
        .then((response) => {
          const data = response.data;
          this.payment.payway = data.payway;
          this.payment.paynick = data.paynick;
          if (data.payway === "card") {
            this.cardInfo = {
              number: data.paynum,
              expiry: data.payday,
              cvc: data.paycvc,
            };
          } else if (data.payway === "bank") {
            this.bankInfo.paycorp = data.paycorp;
            this.bankInfo.paynum = data.paynum;
          }
        })
        .catch((error) => {
          console.error("Error loading payment details:", error);
        });
    },
    handlePayment() {
      const formdata = new FormData();
      formdata.append("membernum", this.membernum);
      if (this.paymentnum) {
        formdata.append("num", this.paymentnum);
      }
      formdata.append("paynick", this.payment.paynick);
      formdata.append("payway", this.payment.payway);

      if (this.payment.payway === "bank") {
        if (!this.bankInfo.paycorp || !this.bankInfo.paynum) {
          alert("은행명과 계좌번호를 입력해주세요.");
          return;
        }
        formdata.append("paynum", this.bankInfo.paynum);
        formdata.append("paycorp", this.bankInfo.paycorp);
        formdata.append("payday", 0);
        formdata.append("paycvc", 0);

      } else if (this.payment.payway === "card") {
        if (
          !this.cardInfo.number ||
          !this.cardInfo.expiry ||
          !this.cardInfo.cvc
        ) {
          alert("카드 정보를 모두 입력해주세요.");
          return;
        }
        formdata.append("paynum", this.cardInfo.number);
        formdata.append("paycorp",0);
        formdata.append("payday", this.cardInfo.expiry);
        formdata.append("paycvc", this.cardInfo.cvc);
      }
      // 빈 값 처리
      const url = this.paymentnum
        ? `${process.env.VUE_APP_BACK_END_URL}/payment/update`
        : `${process.env.VUE_APP_BACK_END_URL}/payment/add`;
      axios
        .post(url, formdata)
        .then(() => {
          alert(
            this.paymentnum
              ? "결제수단이 수정되었습니다."
              : "결제수단이 추가되었습니다."
          );
          this.goBack();
        })
        .catch((error) => {
          console.error("Error submitting payment information:", error);
          alert("오류가 발생했습니다.");
        });
    },
    goBack() {
      this.$router.go(-1);
    },
  },
};
</script>


<style scoped>
.highlight {
  color: salmon;
  font-weight: bold;
}

.container {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin: 20px;
  font-family: "Malgun Gothic", "맑은 고딕", "Arial", sans-serif;
}

label {
  margin-bottom: 8px;
}

input[type="file"] {
  padding: 10px;
  font-size: 16px;
  font-family: inherit; 
}

input[type="text"] {
  padding: 10px;
  font-size: 16px;
  margin-bottom: 10px;
  font-family: inherit; 
}

h2 {
  margin-top: 20px;
  margin-bottom: 30px;
  font-family: inherit; 
}

button {
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
  margin-bottom: 10px;
  font-family: inherit; 
  background-color: rgb(211, 234, 241);
  border: none;
  transition: background-color 0.3s, color 0.3s;
}

button:hover {
  background-color: salmon; 
  color: white; 
}
</style>

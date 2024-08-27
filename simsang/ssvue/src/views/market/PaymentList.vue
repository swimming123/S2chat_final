/*UI개발:이승희*/
<template>
  <div class="mypage">
    <div class="contact_area">
      <div class="text-center mt-5 w-100">
        <h3 style="color: dimgrey">결제수단</h3>
        <div style="display: grid; justify-content: end">
          <router-link to="/PaymentAdd"
            ><button>결제수단 추가하기</button></router-link
          >
        </div>
      </div>
      <hr style="border-color: gray" />
      <div class="text-center">
        <div class="card-body" v-if="paymentlist.length === 0">
          데이터가 없습니다.
        </div>
        <div
          class="card-body box m-4"
          v-for="payment in paymentlist"
          :key="payment.num"
        >
          <p>결제수단 별명 : {{ payment.paynick }}</p>
          <p>결제 방식 : {{ payment.payway }}</p>
         <p v-if="payment.paycorp">결제사 : {{ payment.paycorp }}</p>
          <p>결제 번호 : {{ payment.paynum }}</p>
          <button class="review-button" @click="paymentupdate(payment.num)">
            수정
          </button>
          <button class="review-button" @click="paymentdelete(payment.num)">
            삭제
          </button>
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
      paymentlist: [],
      selectedOrder: null,
    };
  },
  created() {
    this.paymentList();
  },

  methods: {
    paymentList() {
      axios
        .post(`${process.env.VUE_APP_BACK_END_URL}/payment/list`, null, {
          params: { membernum: this.membernum },
        })
        .then((resp) => {
          this.paymentlist = resp.data;
        })
        .catch((error) => {
          console.error("Error fetching orders:", error);
        });
    },
    paymentupdate(paymentnum) {
      this.$router.push({
        name: "PaymentAdd",
        query: { paymentnum: paymentnum },
      });
    },
    paymentdelete(paymentnum) {
      axios
        .post(`${process.env.VUE_APP_BACK_END_URL}/payment/delete`, null, {
          params: { num: paymentnum },
        })
        .then(() => {
          alert("삭제되었습니다.");
          this.paymentList();
        })
        .catch((error) => console.error("에러가 발생했습니다.", error));
    },
  },
};
</script>

/*UI개발:이승희*/
<template>
  <div class="mypage">
    <div class="contact_area">
      <div class="container">
        <div class="row justify-content-center align-items-center">
          <div class="col-md-6 col-lg-4">
            <div class="text-center">
              <h3 style="color: dimgrey">취소확인</h3>
              <hr style="border-color: gray" />
            </div>
            <div v-if="order">
              <div v-for="(item, index) in order" :key="index">
                <p>주문 날짜: {{ item.odate }}</p>
                <p>주문 번호: {{ item.requestnum }}</p>
                <ul>
                  <li>
                    {{ item.name }} - {{ item.price }}원 x {{ item.count }}
                  </li>
                </ul>
                <hr style="border-color: gray" />
              </div>
              <h3>총 금액: {{ formatCurrency(totalCost) }}</h3>
              <button @click="confirmCancel" class="btn btn-primary">
                취소 확인
              </button>
              <button @click="goBack" class="btn btn-primary">돌아가기</button>
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
      order: null,
      requestnum: "",
      membernum: localStorage.getItem("membernum"),
    };
  },
  created() {
    this.requestnum = this.$route.query.requestnum;
    this.loadOrderDetails();
  },
  computed: {
    totalCost() {
      return this.order.reduce(
        (total, item) => total + item.price * item.count,
        0
      );
    },
  },
  methods: {
    loadOrderDetails() {
      axios
        .post(`${process.env.VUE_APP_BACK_END_URL}/order/list`, null, {
          params: { requestnum: this.requestnum },
        })
        .then((response) => {
          this.order = response.data;
        })
        .catch((error) => {
          console.error("Error fetching order details:", error);
        });
    },
    formatCurrency(value) {
      return new Intl.NumberFormat("ko-KR", {
        style: "currency",
        currency: "KRW",
      }).format(value);
    },
    confirmCancel() {
      const formdata = new FormData();
      formdata.append("membernum", this.membernum);
      formdata.append("requestnum", this.requestnum);
      formdata.append("delivery", "취소");
      axios
        .post(`${process.env.VUE_APP_BACK_END_URL}/order/delivery`, formdata)
        .then(alert("정상적으로 취소되었습니다."), this.goBack())
        .catch((error) => {
          console.error("Error confirming cancel:", error);
        });
    },
    goBack() {
      this.$router.go(-1); // 이전 페이지로 돌아가기
    },
  },
};
</script>

<style scoped>
.cancel-confirmation {
  padding: 20px;
}
</style>

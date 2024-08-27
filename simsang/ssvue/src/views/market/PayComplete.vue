/*UI개발:이승희*/
<template>
  <!-- footer Section -->
  <footer id="join" class="join">
    <!-- 제목 영역 -->
    <div class="container">
      <div class="text-center">
        <div class="cartlist">
          <div class="row">
            <div class="col-md-12 col-sm-12 col-xs-12 info">
              <h1>결제 완료</h1>
              <p>주문 번호: {{ num }}</p>
              <p>주문자 이름: {{}}</p>
              <p>주문 금액: {{ formatCurrency(totalCost) }}원</p>
              <p>주문 감사합니다.</p>
              <router-link to="/MarketHome">
                <button>홈으로 돌아가기</button>
              </router-link>
            </div>
          </div>
        </div>
      </div>
    </div>
  </footer>
</template>

<script>
import axios from "axios";

export default {
  name: "PaymentComplete",
  data() {
    return {
      order: {},  // 딕셔너리 형태로 초기화
      num: null,
    };
  },
  created() {
    this.num = this.$route.query.num;
    this.fetchOrderList();
  },
  computed: {
    totalCost() {
      // 딕셔너리 형태의 데이터에서 값을 배열로 변환
      const orderItems = Object.values(this.order);

      // 배열로 변환한 후 총 금액 계산
      return orderItems.reduce((total, item) => {
        return total + (item.price || 0) * (item.count || 0);
      }, 0);
    },
  },
  methods: {
    fetchOrderList() {
      axios
        .post(`${process.env.VUE_APP_BACK_END_URL}/order/list`, null, {
          params: {
            requestnum: this.num,
          },
        })
        .then((response) => {
          this.order = response.data; // 주문 정보 설정
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
  },
};
</script>
  
<style scoped>
.payment-complete {
  text-align: center;
  margin-top: 50px;
}

.payment-complete h1 {
  color: green;
  margin-bottom: 20px;
}

.payment-complete p {
  font-size: 18px;
  margin: 10px 0;
}

.payment-complete a {
  display: inline-block;
  margin-top: 20px;
  padding: 10px 20px;
  color: #fff;
  background-color: #007bff;
  text-decoration: none;
  border-radius: 5px;
}

.payment-complete a:hover {
  background-color: #0056b3;
}

.cartlist {
  background-color: rgba(255, 255, 255, 0.623);
  border-radius: 20px;
  padding: 30px;
  margin-top: 50px;
  margin-bottom: 50px;
}
</style>
  
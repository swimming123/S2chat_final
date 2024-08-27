/*UI개발:이승희*/
<template>
  <div class="mypage">
    <div class="contact_area">
      <div class="container">
        <div class="row justify-content-center align-items-center">
          <div class="col-md-6 col-lg-4">
            <div class="text-center mt-5">
              <h3 style="color: dimgrey">주문상세</h3>
              <hr style="border-color: gray" />
            </div>
            <div class="text-center">
              <!-- 주문 정보 렌더링 -->
              <div v-if="order">
                <p>
                  <span>주문날짜 {{ request.rdate }}</span> |
                  <span>주문번호 {{ request.requestnum }}</span>
                </p>
                <div
                  class="order-detail"
                  v-for="item in order"
                  :key="item.product"
                >
                  <div class="order-item">
                    <div class="item-detail">
                      <!-- <img
                          :src="item.image"
                          alt="Product Image"
                          class="resized-image"
                        /> -->
                      <div class="align-items-center d-flex">
                        <img
                          :src="getImageUrl(item.img)"
                          class="img-responsive"
                          width="80"
                          height="80"
                          alt=""
                          @error="setDefaultImage($event)"
                        />
                        <p>({{ item.delivery }})</p>
                        <p>{{ item.name }}</p>
                        <p>{{ item.price }}원, {{ item.count }}개</p>
                      </div>
                    </div>
                    <div class="item-action">
                      <button class="beigebtn">배송조회</button>
                      <button class="beigebtn">교환, 반품 신청</button>
                      <!-- 리뷰 작성하기 버튼 (reviewStatus가 null이거나 false일 때만 보임) -->
                      <button
                        v-if="!item.reviewStatus"
                        class="salmonbtn"
                        @click="reviewadd(item.ordernum)"
                      >
                        리뷰 작성하기
                      </button>

                      <!-- 리뷰 수정하기 버튼 (reviewStatus가 true일 때만 보임) -->
                      <button
                        v-if="item.reviewStatus"
                        class="salmonbtn"
                        @click="reviewupdate(item.ordernum)"
                      >
                        리뷰 수정하기
                      </button>
                    </div>
                  </div>
                </div>
              </div>

              <!-- 배송비 정보 -->
              <hr style="border-color: gray" />
              <div class="payment-info">
                <h3 class="my-3">배송비 정보</h3>
                <table class="tab">
                  <tr>
                    <th>배송비</th>
                    <th>무료</th>
                  </tr>
                  <tr>
                    <th>도착예정일</th>
                    <th>{{ request.date }}</th>
                  </tr>
                </table>
              </div>

              <!-- 결제 정보 -->
              <div class="total-price">
                <h3 class="my-3">결제 정보</h3>
                <table class="tab">
                  <tr>
                    <th>상품금액</th>
                    <th>{{ formatCurrency(totalCost) }}원</th>
                  </tr>
                  <tr>
                    <th>할인금액</th>
                    <th>0원</th>
                  </tr>
                  <tr>
                    <th>결제금액</th>
                    <th>{{ formatCurrency(totalCost) }}원</th>
                  </tr>
                </table>
              </div>

              <!-- 추가 정보 및 버튼 -->
              <div class="additional-info">
                <button class="btn btn-primary" @click="saveAsPDF">
                  주문내역 PDF로 저장하기
                </button>
                <button class="btn btn-primary" @click="goBack">목록</button>
              </div>
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
      request: [],
      order: [],
      totalAcount: 0,
      membernum: localStorage.getItem("membernum"),
      requestnum: "",
    };
  },
  computed: {
    nullCart() {
      return this.totalAcount <= 0;
    },
    totalCost() {
      return this.order.reduce((total, item) => {
        return total + item.price * item.count;
      }, 0);
    },
  },
  created() {
    this.requestnum = this.$route.params.id; // 현재 라우터에서 받은 ID 파라미터
    this.fetchRequestDetails(); // 이름을 수정하여 올바르게 메서드를 호출합니다.
    this.fetchOrderList(); // 이름을 수정하여 올바르게 메서드를 호출합니다.
  },
  methods: {
    fetchRequestDetails() {
      axios
        .post(`${process.env.VUE_APP_BACK_END_URL}/request/detail`, null, {
          params: {
            membernum: this.membernum,
            requestnum: this.requestnum,
          },
        })
        .then((response) => {
          this.request = response.data;
        })
        .catch((error) => {
          console.error("Error fetching order details:", error);
        });
    },
    fetchOrderList() {
      axios
        .post(`${process.env.VUE_APP_BACK_END_URL}/order/list`, null, {
          params: {
            requestnum: this.requestnum,
          },
        })
        .then((response) => {
          this.order = response.data;
          this.order.forEach((item) => {
            this.fetchReviewDetail(item);
          });
        })
        .catch((error) => {
          console.error("Error fetching order details:", error);
        });
    },
    fetchReviewDetail(orderItem) {
      axios
        .get(
          `${process.env.VUE_APP_BACK_END_URL}/review/reviewDetail/${orderItem.ordernum}`
        )
        .then((response) => {
          orderItem.reviewStatus = response.data ? true : false;
        })
        .catch((error) => {
          console.error("Error fetching review detail: ", error);
          orderItem.reviewStatus = false;
        });
    },
    saveAsPDF() {
      window.print();
    },
    formatCurrency(value) {
      return new Intl.NumberFormat("ko-KR", {
        style: "currency",
        currency: "KRW",
      }).format(value);
    },
    goBack() {
      this.$router.go(-1); 
    },
    getImageUrl(product_image) {
      if (!product_image) {
        return `/images/product/maketadd_default.png`; 
      }
      const imageUrl = `${process.env.VUE_APP_BACK_END_URL}/uploads/${product_image}`;
      return imageUrl;
    },
    setDefaultImage(event) {
      event.target.src = "/images/noimg.png"; 
    },
    reviewadd(review_num) {
      this.$router.push({
        name: "JimiReviewAdd",
        query: { review_num: review_num },
      });
    },
    reviewupdate(review_num) {
      this.$router.push({
        name: "JimiReviewDetail",
        query: { review_num: review_num },
      });
    },
    getImageUrl(product_image) {
      if (!product_image) {
        return "/images/noimg.png";
      }
      const imageUrl = `${process.env.VUE_APP_BACK_END_URL}/uploads/${product_image}`;
      return imageUrl;
    },
  },
};
</script>


<style scoped>
.tab {
  width: 100%;
}

.tab,
th,
td {
  font-family: "NotoSansKR";
  border: 1px solid #ccc;
  border-radius: 10px;
}

.btnn {
  margin-left: 95%;
}

.div1 {
  width: 50%;
  margin-top: 40px;
  margin-left: 25%;
  border-radius: 10px;
}

.order-item {
  border: 1px solid #ccc;
  border-radius: 10px;
  padding: 10px;
  margin-bottom: 10px;
  display: flex;
  justify-content: space-between;
}

.item-detail {
  display: flex;
  gap: 10px;
}

.item-action {
  display: flex;
  flex-direction: column;
  gap: 5px;
}
</style>

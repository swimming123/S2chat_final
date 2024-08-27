/*UI개발:이승희*/
<template>
  <div class="mypage">
    <div class="contact_area">
      <div class="text-center mt-5">
        <h3 style="color: dimgrey">주문목록</h3>
        <div class="w-100" style="text-align: right">
          <input
            type="text"
            placeholder="주문한 상품을 검색"
            v-model="content"
          />
          <button class="m-2" @click="search">검색</button>
          <button class="m-2" @click="alllist" v-if="searchdata">
            전체목록
          </button>
        </div>
        <hr style="border-color: gray" />
      </div>
      <div class="row">
        <div @click="selectOrder(order)" v-if="!searchdata" class="col-md-9 m-auto">
          <div
            v-if="orders.length === 0"
            class="text-center"
          >
            데이터가 없습니다.
          </div>
          <div
            class="card-body order-item align-items-center d-flex"
            v-for="order in orders"
            :key="order.id"
          >
            <div
              v-if="order.paylist && order.paylist.length > 0"
              class="card-text item-detail"
            >
              <router-link
                :to="{ name: 'OrderDetail', params: { id: order.requestnum } }"
              >
                <h5 class="card-title">
                  {{ order.rdate }} 주문 | 주문번호 : {{ order.requestnum }}
                </h5>
                <div class="card p-2">
                  <p>구입 리스트</p>
                  <div
                    v-for="(pay, index) in order.paylist"
                    :key="index"
                    class="pay-item"
                  >
                    <p>
                      ({{ pay.delivery }}) {{ pay.name }} {{ pay.price }}원 x
                      {{ pay.count }}개
                    </p>
                  </div>
                  총 금액 : {{ formatCurrency(order.totalCost) }}
                </div>
              </router-link>
            </div>
            <div class="item-action">
              <button class="beigebtn">배송조회</button>
              <button
                class="beigebtn"
                @click="
                  orderconfirm(order.requestnum, order.paylist[0].delivery)
                "
              >
                구매확정
              </button>
              <button
                class="salmonbtn"
                @click="
                  ordercancel(order.requestnum, order.paylist[0].delivery)
                "
              >
                취소신청
              </button>
            </div>
          </div>
        </div>

        <div v-if="searchdata">
          <div v-if="searchresult.length === 0" class="text-center">
            데이터가 없습니다.
          </div>

          <div
            class="order-detail"
            v-for="item in searchresult"
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
                  <p>
                    주문날짜 {{ item.odate }} | 주문번호 {{ item.requestnum }}
                  </p>
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
        <!-- <OrderDetail v-if="selectedOrder" :order="selectedOrder" /> -->
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import OrderDetail from "./OrderDetail.vue";

export default {
  components: {
    OrderDetail,
  },
  data() {
    return {
      orders: [], // Fetch orders here
      selectedOrder: null, // 선택된 주문을 저장할 상태
      authToken: "1",
      paylist: [],
      content: "",
      searchdata: false,
      searchresult: [],
      membernum: localStorage.getItem("membernum"),
    };
  },
  created() {
    this.loadCartAndOrder();
  },
  computed: {
    totalCost() {
      return this.orders.reduce((total, order) => {
        if (order.paylist && order.paylist.length > 0) {
          const orderTotal = order.paylist.reduce((orderTotal, pay) => {
            return orderTotal + pay.price * pay.count;
          }, 0);
          return total + orderTotal;
        }
        return total;
      }, 0);
    },
  },
  methods: {
    selectOrder(order) {
      this.selectedOrder = order; // 주문 선택 시 상태 업데이트
    },
    ordercancel(requestnum, delivery) {
      if (
        delivery === "취소" ||
        delivery === "환불" ||
        delivery === "구매확정"
      ) {
        alert("이미 " + delivery + "된 주문입니다.");
        return;
      }
      this.$router.push({
        name: "OrderCancel",
        query: { requestnum: requestnum },
      });
    },
    orderconfirm(requestnum, delivery) {
      if (
        delivery === "취소" ||
        delivery === "환불" ||
        delivery === "구매확정"
      ) {
        alert("이미 " + delivery + "된 주문입니다.");
        return;
      }
      const formdata = new FormData();
      formdata.append("membernum", this.membernum);
      formdata.append("requestnum", requestnum);
      formdata.append("delivery", "구매확정");
      axios
        .post(`${process.env.VUE_APP_BACK_END_URL}/order/delivery`, formdata)
        .then(
          (resp) => alert("구매확정 완료되었습니다."),
          this.loadCartAndOrder()
        )
        .catch((error) => {
          console.error("Error confirming cancel:", error);
        });
    },
    OrderList() {
      return axios
        .post(`${process.env.VUE_APP_BACK_END_URL}/request/list`, null, {
          params: {
            membernum: this.membernum,
          },
        })
        .then((response) => {
          if (Array.isArray(response.data)) {
            this.orders = response.data.map((order) => {
              return {
                ...order,
                rdate: order.rdate || new Date().toISOString(),
                image: order.image || "images/default.png",
              };
            });
          } else {
            console.error("Unexpected response data format:", response.data);
          }
        })
        .catch((error) => {
          console.error("Error fetching orders:", error);
        });
    },
    formatCurrency(value) {
      return new Intl.NumberFormat("ko-KR", {
        style: "currency",
        currency: "KRW",
      }).format(value);
    },
    async fetchPayListForAllOrders() {
      // 각 주문에 대해 axios 요청을 보낼 배열 생성
      const requests = this.orders.map((order) => {
        return axios
          .post(`${process.env.VUE_APP_BACK_END_URL}/order/list`, null, {
            params: {
              requestnum: order.requestnum, // 각 주문의 requestnum 사용
            },
          })
          .then((resp) => {
            const paylist = resp.data;
            const totalCost = paylist.reduce((sum, pay) => {
              return sum + pay.price * pay.count;
            }, 0);
            // 응답 데이터를 주문 객체에 추가
            return {
              ...order,
              paylist,
              totalCost,
            };
          })
          .catch((err) => {
            console.error(
              `Error fetching paylist for order ${order.requestnum}:`,
              err
            );
            return {
              ...order,
              paylist: null,
              totalCost: 0,
            };
          });
      });

      // 모든 요청이 완료될 때까지 기다림
      try {
        const results = await Promise.all(requests);

        // 결과를 사용해 주문 객체에 응답 데이터 저장
        this.orders = results;
      } catch (error) {
        console.error("Error in fetching paylists:", error);
      }
    },
    loadCartAndOrder() {
      this.OrderList()
        .then(() => {
          if (this.orders.length > 0) {
            this.fetchPayListForAllOrders(); // 모든 주문에 대해 paylist를 비동기로 가져옴
          }
        })
        .catch((err) => {
          console.error("loadCartAndOrder 오류: ", err);
        });
    },
    getStatusText(status) {
      switch (status) {
        case 0:
          return "결제완료";
        case 1:
          return "배송중";
        case 2:
          return "배송완료";
        case 3:
          return "구매확정";
        case 8:
          return "취소신청";
        case 9:
          return "취소완료";
        default:
          return "알수없음";
      }
    },
    search() {
      if (!this.content) {
        alert("검색할 내용을 입력해주세요.");
      }
      axios
        .post(`${process.env.VUE_APP_BACK_END_URL}/order/search`, null, {
          params: { membernum: this.membernum, content: this.content },
        })
        .then((resp) => {
          this.searchresult = resp.data;
          this.searchdata = true;
          this.searchresult.forEach((item) => {
            this.fetchReviewDetail(item);
          });
        })
        .catch((error) => {
          console.error("Error cancelling the order:", error);
        });
    },
    fetchReviewDetail(orderItem) {
      axios
        .get(
          `${process.env.VUE_APP_BACK_END_URL}/review/reviewDetail/${orderItem.ordernum}`
        )
        .then((response) => {
          // 리뷰 상태를 해당 주문 항목에 정확히 설정합니다.
          orderItem.reviewStatus = response.data ? true : false;
        })
        .catch((error) => {
          console.error("Error fetching review detail: ", error);
          orderItem.reviewStatus = false; // 오류 발생 시 기본값 설정
        });
    },
    getImageUrl(product_image) {
      if (!product_image) {
        return "/images/noimg.png"; // 기본 이미지를 설정
      }
      const imageUrl = `${process.env.VUE_APP_BACK_END_URL}/uploads/${product_image}`;
      return imageUrl;
    },
    setDefaultImage(event) {
      event.target.src = "/images/noimg.png"; // 기본 이미지 경로 설정
    },
    alllist() {
      this.searchdata = false;
      this.searchresult = false;
    },
  },
};
</script>
<style scoped>
.order-item {
  border: 1px solid #ccc;
  border-radius: 10px;
  padding: 20px;
  margin-bottom: 10px;
  justify-content: space-between;
}

.item-detail {
  display: flex;
  gap: 10px;
}

.item-action {
  width: 200px;
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.card-text {
  text-decoration: none;
}

.card {
  width: 100%;
  max-width: 500px; 
  margin: 0 auto;
}

a, a:visited {
  color: black;
  text-decoration: none;
}

a:hover {
  color: rgb(33, 194, 197); 
  text-decoration: none; 
}
</style>

/*UI개발:이승희*/
<template>
  <div id="join" class="join">
    <div
      class="container"
      style="
        margin-top: 50px;
        display: flex;
        justify-content: center;
        align-items: center;
        background-size: cover;
        background-position: center;
      "
    >
      <div class="row">
        <div class="mypage">
          <div class="box1" style="min-width: 1000px">
            <div class="head_title text-center fixed-header">
              <h1>취소 / 반품 상세보기</h1>
            </div>

            <div class="order-detail box2">
              <div class="order-info">
                <p>
                  <span class="order-date">{{ cancel.odate }}</span>
                  <span class="order-number">
                    | 주문번호 : {{ cancel.ordernum }}</span
                  >
                </p>
              </div>
              <div style="min-width: 500px">
                <div class="order-items">
                  <div class="order-item">
                    <div class="item-details">
                      <p>{{ cancel.odate }} 신청완료</p>
                      <img
                        :src="getImageUrl(cancel.img)"
                        alt="Product Image"
                        class="resized-image"
                         @error="setDefaultImage($event)"
                      />
                      <div>
                        <p>{{ cancel.name }}</p>

                        <p>{{ cancel.price }}원, {{ cancel.count }}개</p>
                      </div>
                    </div>
                    <div class="item-actions"></div>
                  </div>
                </div>
              </div>
              <!-- <div style="min-width: 500px">
                <div class="order-items">
                  <div class="order-item" v-for="item in items" :key="item.id">
                    <div class="item-details">
                      <p>{{ item.date }} 신청완료</p>
                      <img
                        :src="item.image"
                        alt="Product Image"
                        class="resized-image"
                      />
                      <div>
                        <p>{{ item.productName }}</p>
                        <br /><br />
                        <p>{{ item.price }}원, {{ item.quantity }}개</p>
                      </div>
                    </div>
                    <div class="item-actions"></div>
                  </div>
                </div>
              </div> -->
            </div>

            <div class="box2">
              <div class="payment-info">
                <h2>상세정보</h2>
                <br />
                <table class="tab">
                  <tr>
                    <td>교환 접수 일자</td>
                    <td>{{ exchangeDate }}</td>
                  </tr>
                  <tr>
                    <td>교환 접수 번호</td>
                    <td>{{ exchangeNumber }}</td>
                  </tr>
                </table>
              </div>
            </div>

            <div class="box3">
              <div class="total-price">
                <h2>교환 상품 배송 정보</h2>
                <br />
                <table class="tab">
                  <tr>
                    <td>회수인</td>
                    <td>{{ receiver }}</td>
                    <td>택배기사</td>
                    <td>{{ courier }}</td>
                  </tr>
                  <tr>
                    <td>휴대폰</td>
                    <td>{{ phone }}</td>
                    <td>송장번호</td>
                    <td>{{ trackingNumber }}</td>
                  </tr>
                  <tr>
                    <td>연락처</td>
                    <td colspan="3">{{ contact }}</td>
                  </tr>
                  <tr>
                    <td>주소</td>
                    <td colspan="3">{{ address }}</td>
                  </tr>
                </table>
              </div>
            </div>

            <div class="box4" style="min-width: 500px">
              <div class="additional-info">
                <h2>상품 회수정보</h2>
                <br />
                <table class="tab">
                  <tr>
                    <td>상품회수 진행여부</td>
                    <td>{{ returnStatus }}</td>
                    <td>반송장정보</td>
                    <td>{{ returnTrackingNumber }}</td>
                  </tr>
                  <tr>
                    <td>회수인</td>
                    <td>{{ returnReceiver }}</td>
                    <td>택배기사</td>
                    <td>{{ returnCourier }}</td>
                  </tr>
                  <tr>
                    <td>휴대폰</td>
                    <td>{{ returnPhone }}</td>
                    <td>송장번호</td>
                    <td>{{ returnNumber }}</td>
                  </tr>
                  <tr>
                    <td>연락처</td>
                    <td colspan="3">{{ returnContact }}</td>
                  </tr>
                  <tr>
                    <td>주소</td>
                    <td colspan="3">{{ returnAddress }}</td>
                  </tr>
                </table>
              </div>
            </div>

            <div class="box5" style="min-width: 500px">
              <div class="additional-info">
                <h2>회수 예정일 / 요청사항</h2>
                <br />
                <table class="tab">
                  <tr>
                    <td>회수 예정일</td>
                    <td>{{ pickupDate }}</td>
                  </tr>
                  <tr>
                    <td>회수 요청사항</td>
                    <td>{{ pickupRequest }}</td>
                  </tr>
                </table>
              </div>
            </div>

            <div class="box6" style="min-width: 00px">
              <div class="additional-info">
                <h2>교환 사유</h2>
                <br />
                <table class="tab">
                  <tr>
                    <td>교환 사유</td>
                    <td>{{ exchangeReason }}</td>
                  </tr>
                </table>
              </div>
            </div>
            <div class="additional-info">
              <button class="btn btn-primary" @click="saveAsPDF">
                취소내역 PDF로 저장하기
              </button>

              <router-link to="/mypage"
                ><button class="btn btn-primary" @click="goBack">목록</button></router-link
              >
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
      cancel: [],
      orderDate: "2024. 6. 21",
      orderNumber: "",
      items: [
        {
          id: 1,
          date: "2023/6/18",
          productName: "캐리어 에어컨 18.7평형",
          quantity: 1,
          price: "178,930",
          image: "images/air.png",
        },
      ],
      exchangeDate: "2023/10/22",
      exchangeNumber: "910016936628",
      receiver: "테스형",
      courier: "xman",
      phone: "010-1111-1111",
      trackingNumber: "10254089154945",
      contact: "010-1111-1111",
      address: "서울특별시 서초구 서초대로77길 41, 4층 (서초동, 대동Ⅱ)",
      returnStatus: "판매자전달완료",
      returnTrackingNumber: "",
      returnReceiver: "테스형",
      returnCourier: "xman",
      returnPhone: "010-1111-1111",
      returnNumber: "993588293611",
      returnContact: "010-1111-1111",
      returnAddress: "서울특별시 서초구 서초대로77길 41, 4층 (서초동, 대동Ⅱ)",
      pickupDate: "2023/10/23(월)",
      pickupRequest: "문 앞",
      exchangeReason: "상품이 파손되어 배송됨",
    };
  },
  methods: {
    saveAsPDF() {
      window.print();
    },
    fetchCancel() {
      const orderNumber = this.orderNumber;
      axios
        .post(
          `${process.env.VUE_APP_BACK_END_URL}/order/canceldetail/${orderNumber}`
        )
        .then((resp) => {
          this.cancel = resp.data;
        })
        .catch((err) => {
        });
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
    goBack() {
      this.$router.go(-1);
    },
  },
  created() {
    this.orderNumber = this.$route.query.order;
    this.fetchCancel();
  },
};
</script>

<style scoped>
body,
.head_title1,
button,
p,
div,
span,
textarea,
label {
  font-family: "Malgun Gothic", sans-serif;
}

.resized-image {
  width: 100px;
  height: auto;
}

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

.payment-info,
.total-price,
.additional-info {
  text-align: center;
  margin: 0 auto;
  width: 100%;
}

.order-detail {
  padding: 20px;
  margin-top: 20px;
}

.order-info {
  margin-bottom: 20px;
}

.order-items {
  margin-bottom: 20px;
}

.order-item {
  border: 1px solid #ccc;
  border-radius: 10px;
  padding: 10px;
  margin-bottom: 10px;
  display: flex;
  justify-content: space-between;
}

.item-details {
  display: flex;
  gap: 10px;
}

.item-actions {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

h2,
h1 {
  font-family: "NotoSansKR";
  font-size: 40px;
  font-weight: 600;
  margin: 0;
}

p {
  font: normal 16px "NotoSansKR", arial;
}

label,
a {
  font: bold 16px "NotoSansKR", arial;
}

.no-border {
  border: none;
  border-radius: 5px;
  background-color: rgb(255, 255, 255);
}

.review-button {
  background-color: salmon;
  border-radius: 5px;
  color: white;
  border: none;
}

.flex-container {
  display: flex;
  justify-content: space-between;
}

.box1,
.box2,
.box3,
.box4,
.box5,
.box6 {
  padding: 20px;
  margin: 20px 0;
  border-radius: 10px;
  background: #fcfafa69;
  border: 1px solid gray;
}

.bttn {
  width: 300px;
  height: 50px;
  font-weight: bold;
  font-size: large;
}
</style>

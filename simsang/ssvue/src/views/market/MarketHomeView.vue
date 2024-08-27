/*UI개발:이승희*/
<template>
  <div class="main">
    <div class="container">
      <div class="row justify-content-center align-items-center">
        <div class="col-md-6 col-lg-4">
          <h1 class="text-center my-4" style="color: #41c1ba">심상 마켓</h1>
          <!--   -->
        </div>
        <div class="container-fluid">
          <div class="text-center">
            <div class="button-group">
              <button
                class="custom-btn"
                @click="renderComponent('ProductListView')"
              >
                추천상품
              </button>
              <button class="custom-btn" @click="renderComponent('OrderList')">
                주문목록
              </button>
              <button class="custom-btn" @click="renderComponent('CancelList')">
                취소/환불목록
              </button>
              <button class="custom-btn" @click="renderComponent('MarketQnalist')">
                1:1문의
              </button>
              <button class="custom-btn" @click="renderComponent('JimiReviewList')">
                리뷰내역
              </button>
              <!-- <button class="custom-btn" @click="openPaymentMethodsModal">결제수단 관리</button> -->
              <!-- <button class="custom-btn" @click="openReceiptsModal">현금영수증 <br>관리</button> -->
              <button class="custom-btn" @click="renderComponent('CardList')">
                결제수단
              </button>
              <button class="custom-btn" @click="renderComponent('AddressList')">
                배송지
              </button>
            </div>
          </div>
        </div>
        <!-- 조건부 렌더링으로 다양한 컴포넌트 표시 -->
        <div v-if="showComponent === 'ProductListView'">
          <ProductListView />
        </div>
        <div v-if="showComponent === 'OrderList'">
          <OrderList />
        </div>
        <div v-if="showComponent === 'CancelList'">
          <CancelList />
        </div>
        <div v-if="showComponent === 'CancelDetail'">
          <CancelDetail />
        </div>
        <div v-if="showComponent === 'JimiReviewList'">
          <JimiReviewList />
        </div>
        <div v-if="showComponent === 'MemberEditView'">
          <MemberEditView />
        </div>
        <div v-if="showComponent === 'MarketQnalist'">
          <MarketQnalist />
        </div>
        <div v-if="showComponent === 'CardList'">
          <CardList />
        </div>
        <div v-if="showComponent === 'CartView'">
          <CartView />
        </div>
        <div v-if="showComponent === 'AddressList'">
          <AddressList />
        </div>

        <!-- 결제수단 관리 모달 -->
        <div
          class="modal"
          :class="{ 'is-active': isPaymentMethodsModalActive }"
        >
          <div class="modal-background" @click="closePaymentMethodsModal"></div>
          <div class="modal-card">
            <header class="modal-card-head">
              <img
                :src="require('@/../public/images/b.png')"
                alt="Receipt Icon"
                class="modal-logo"
              />
              <p class="modal-card-title">결제수단 관리</p>
              <!-- 등록된 수단 없으면 등록하기 버튼 있으면 등록된 카드 노출 -->
              <button
                class="delete"
                aria-label="close"
                @click="closePaymentMethodsModal"
              ></button>
            </header>
            <section class="modal-card-body">
              <div class="account-section">
                <h4>등록 계좌 1개</h4>
                <div class="account-item">
                  <img
                    :src="require('@/../public/images/b.png')"
                    alt="Receipt Icon"
                    class="modal-image icon"
                  />
                  <div class="account-details">
                    <span>우리은행</span>
                    <span>********9768</span>
                  </div>
                  <button @click="deleteItem('account')" class="btn-delete">
                    삭제
                  </button>
                </div>
              </div>
              <div class="section-divider"></div>
              <div class="card-section">
                <h4>등록 카드 4개</h4>
                <div class="card-item" v-for="card in cards" :key="card.id">
                  <img :src="card.icon" alt="카드 아이콘" class="icon" />
                  <div class="card-details">
                    <span>{{ card.name }}</span>
                    <span>{{ card.number }}</span>
                  </div>
                  <button @click="deleteItem(card.id)" class="btn-delete">
                    삭제
                  </button>
                </div>
              </div>
              <!-- +결제수단등록 버튼 추가 -->
              <div class="text-center">
                <button
                  class="btn btn-primary btn-small"
                  @click="addPaymentMethod"
                >
                  +결제수단등록
                </button>
              </div>
            </section>
          </div>
        </div>

        <!-- 현금영수증 관리 모달 -->
        <div class="modal" :class="{ 'is-active': isReceiptsModalActive }">
          <div class="modal-background" @click="closeReceiptsModal"></div>
          <div class="modal-card">
            <header class="modal-card-head">
              <img
                :src="require('@/../public/images/b.png')"
                alt="Receipt Icon"
                class="modal-logo"
              />
              <p class="modal-card-title">현금영수증 설정</p>
              <button
                class="delete"
                aria-label="close"
                @click="closeReceiptsModal"
              ></button>
            </header>
            <section class="modal-card-body">
              <div class="receipt-option">
                <label for="issue-receipt-checkbox">
                  <input
                    type="checkbox"
                    id="issue-receipt-checkbox"
                    v-model="issueReceipt"
                  />
                  현금영수증 발급
                </label>
              </div>
              <div
                class="receipt-info"
                style="
                  border: 1px solid #ccc;
                  border-radius: 5px;
                  padding: 10px;
                  margin-top: 20px;
                "
              >
                <p style="color: #000; margin: 0">
                  개인소득공제(휴대폰번호) :
                  <input
                    type="text"
                    v-model="phoneNumber"
                    @input="validatePhoneNumber"
                    maxlength="14"
                    style="
                      border: none;
                      outline: none;
                      color: #000;
                      margin-left: 5px;
                    "
                  />
                  <a href="#" style="color: #007bff; float: right">정보변경</a>
                </p>
              </div>
              <div class="receipt-details small-text">
                <p>
                  계좌이체, 무통장입금, 쿠페이 머니로 결제시 등록된 정보로
                  현금영수증이 자동 발급됩니다.
                </p>
              </div>
            </section>
          </div>
        </div>
        
      </div>
      <div class="fixed-cart-button">
          <button class="custom-btn" @click="renderComponent('CartView')">장바구니</button>
        </div>
    </div>
  </div>
</template>

<script>
import AddressList from "./AddressList.vue";
import CancelList from "./CancelList.vue";
import CartView from "./CartView.vue";
import JimiReviewAdd from "./JimiReviewAdd.vue";
import JimiReviewDetail from "./JimiReviewDetail.vue";
import JimiReviewList from "./JimiReviewList.vue";
import MarketQnalist from "./MarketQnalist.vue";
import OrderList from "./OrderList.vue";
import CardList from "./PaymentList.vue";
import ProductListView from "./ProductListView.vue";
import ReviewList from "./ReviewList.vue";

export default {
  data() {
    return {
      isPaymentMethodsModalActive: false,
      isReceiptsModalActive: false,
      issueReceipt: false,
      phoneNumber: "",
      showComponent: "ProductListView",
      cards: [
        {
          id: 1,
          icon: require("@/../public/images/b.png"),
          name: "우리All For Me ...",
          number: "625320******594",
        },
        {
          id: 2,
          icon: require("@/../public/images/b.png"),
          name: "카카오뱅크카드",
          number: "536510******034",
        },
        {
          id: 3,
          icon: require("@/../public/images/b.png"),
          name: "신한카드",
          number: "451842******109",
        },
        {
          id: 4,
          icon: require("@/../public/images/b.png"),
          name: "신한카드",
          number: "451842******780",
        },
      ],
    };
  },
  components: {
    ProductListView,
    OrderList,
    CancelList,
    MarketQnalist,
    CardList,
    ReviewList,
    JimiReviewAdd,
    JimiReviewDetail,
    JimiReviewList,
    AddressList,
    CartView


  },
  methods: {
    renderComponent(component) {
      this.showComponent = component;
    },
    validatePhoneNumber() {
      this.phoneNumber = this.phoneNumber.replace(/\D/g, "");
    },
    openPaymentMethodsModal() {
      this.isPaymentMethodsModalActive = true;
    },
    closePaymentMethodsModal() {
      this.isPaymentMethodsModalActive = false;
    },
    openReceiptsModal() {
      this.isReceiptsModalActive = true;
    },
    closeReceiptsModal() {
      this.isReceiptsModalActive = false;
    },
    deleteItem(id) {
      this.cards = this.cards.filter((card) => card.id !== id);
      alert("삭제되었습니다.");
    },
    openCart() {
      // 장바구니 열기 관련 로직을 추가합니다.
      alert("장바구니 열기 기능은 아직 구현되지 않았습니다.");
    },
  },
};
</script>

<style scoped>


.custom-btn {
  padding: 10px 0px;
  margin: 5px;
  background-color: #41c1ba;
  color: #ffffff;
  text-align: center;
  text-decoration: none;
  border-radius: 5px;
  cursor: pointer;
  font-family: "JejuGothic";
  font-weight: bold;
  width: 150px;
  border: 2px solid #41c1ba;
}

.custom-btn:hover {
  background-color: rgba(255, 255, 255, 0.7);
  color: black;
  border: 2px solid #41c1ba;
}

.button-group {
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
}
.fixed-cart-button {
  position: fixed;
  bottom: 20px;
  right: 20px; 
  z-index: 2000;
}

.fixed-cart-button button {
  background-color: #41c1ba;
  color: #fff; 
  border: none;
  width: 100px;
  height: 100px;
  border-radius: 50%; 
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); 
  cursor: pointer; 
}

.fixed-cart-button button:hover {
  color: #41c1ba;
  background-color: rgb(233, 233, 233);
}
</style>


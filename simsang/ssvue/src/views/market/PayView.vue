/*UI개발:이승희*/
<template>
  <!-- footer Section -->
  <!-- 제목 영역 -->
  <div class="mt-5">
    <div class="cartlist" style="padding: 50px">
      <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
          <h1 class="text-center">결제페이지</h1>
          <hr />
          <div class="row align-items-center">
            <div class="col d-flex justify-content py-3">
              <h3 class="my-auto">받는분 정보</h3>
              <div>
                <button
                  type="button"
                  class="btn btn-primary"
                  style="margin-left: 10px"
                  data-bs-toggle="modal"
                  data-bs-target="#AddressModal"
                >
                  배송지 목록 보기
                </button>
                <!-- 모달 -->
                <div
                  class="modal fade"
                  id="AddressModal"
                  tabindex="-1"
                  aria-labelledby="AddressModalLabel"
                  aria-hidden="true"
                >
                  <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                      <div class="modal-header">
                        <h5 class="modal-title" id="AddressModalLabel">
                          배송지목록
                        </h5>
                        <button
                          type="button"
                          class="btn-close"
                          data-bs-dismiss="modal"
                          aria-label="Close"
                        ></button>
                      </div>
                      <div class="modal-body">
                        <div class="text-center mt-5 w-100">
                          <div style="display: grid; justify-content: end">
                            <router-link
                              to="/AddressAdd"
                              @click.native="closeModal('AddressModal')"
                            >
                              <button class="btn btn-success">
                                배송지 추가하기
                              </button>
                            </router-link>
                          </div>
                        </div>
                        <hr style="border-color: gray" />
                        <div class="text-center">
                          <div
                            class="card-body"
                            v-if="addresslist.length === 0"
                          >
                            데이터가 없습니다.
                          </div>
                          <div
                            class="card-body box m-4"
                            v-for="(address, index) in addresslist"
                            :key="index"
                          >
                            <p>주소별명 : {{ address.addressnick }}</p>
                            <p>주소 : {{ address.address }}</p>
                            <p>상세주소 : {{ address.detailaddress }}</p>
                            <p>요청사항 : {{ address.addrequest }}</p>
                            <button
                              @click="addressupdate(address.num)"
                              class="btn btn-warning"
                            >
                              수정
                            </button>
                            <button
                              @click="addressdelete(address.num)"
                              class="btn btn-danger"
                            >
                              삭제
                            </button>
                            <button
                              @click="addressselect(index)"
                              class="btn btn-danger"
                            >
                              선택
                            </button>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <!-- 모달 끝 -->
              </div>
            </div>
          </div>
          <p>
            받는분 성함 : <input type="text" v-model="address.addressnick" />
          </p>

          <!-- <span>우편번호 : </span><input type="text" v-model="postcode" placeholder="우편번호" disabled><br> -->
          <!-- 스크립트 로딩 완료 후에만 버튼을 활성화합니다 -->

          <p>
            <!-- 우편주소 :
              <input
                type="text"
                id="zip"
                v-model="zip"
                placeholder="우편주소"
                disabled
              /> -->
            배송지주소 :
            <button
              class="btn btn-primary"
              type="button"
              style="white-space: nowrap"
              @click="openAddressModal"
            >
              주소 검색
            </button>
            <input
              type="text"
              id="address"
              v-model="address.address"
              placeholder="도로명주소"
              disabled
            />
            상세주소 :
            <input
              type="text"
              style="width: 500px"
              id="detailaddress"
              placeholder="상세주소"
              v-model="address.detailaddress"
            />
          </p>
          <p>연락처 : <input type="text" id="phone" v-model="phone" /></p>
          <p>
            배송요청사항
            <input type="text" id="request" v-model="address.addrequest" />
          </p>
          <!-- <p>주문자명/이메일/연락처 : {{}} | {{}} | {{}}</p> -->

          <address-modal
            v-if="isAddressModalOpen"
            @close="isAddressModalOpen = false"
            @select-address="selectAddress"
            class="modal fade"
            id="addressModal"
          ></address-modal>

          <hr />

          <h3>주문상품목록 : {{ list.length }} 개</h3>
          <table class="table table-hover mt-3">
            <thead class="table-dark">
              <tr style="background-color: salmon">
                <th>상품이미지</th>
                <th>상품명</th>
                <th>수량</th>
                <th>가격</th>
              </tr>
            </thead>
            <tbody>
              <!-- 장바구니가 0 이면 아래꺼 노출 -->
              <tr v-if="list.length === 0">
                <td colspan="4" class="gotham-font">
                  <p style="font-weight: bold">결제항목이 없습니다.</p>
                </td>
              </tr>
              <tr v-else v-for="(item, index) in list" :key="index">
                <td>
                  <img
                    :src="getImageUrl(item.img)"
                    class="img-responsive"
                    width="80"
                    height="80"
                    alt=""
                    @error="handleImageError($event)"
                  />
                </td>
                <!-- 상품이미지 -->
                <td>{{ item.name }}</td>
                <!-- 상품명 -->
                <td>{{ item.count }}개</td>
                <!-- 수량 -->
                <td>{{ formatCurrency(item.price * item.count) }}</td>
              </tr>
              <tr>
                <td
                  colspan="4"
                  style="text-align: right; font-size: 23px; color: #2e64fe"
                >
                  총 금액 : {{ formatCurrency(totalCost) }}
                </td>
              </tr>
            </tbody>
          </table>

          <div style="font-size: 23px">
            <p>쿠폰할인 : {{}}</p>
            <p>포인트사용 : {{}}</p>
            <p>할인금액 : {{}}</p>

            <h4>최종 주문금액 : {{ formatCurrency(totalCost) }}</h4>
            <hr />
            <div>
              <div>
                <h3>결제수단</h3>
                <!-- 결제수단 선택 -->
                <div class="my-2">
                  <label for="payment-method">결제수단 선택: </label>
                  <button
                    type="button"
                    class="btn btn-primary"
                    style="margin-left: 10px"
                    data-bs-toggle="modal"
                    data-bs-target="#PaymentModal"
                  >
                    결제수단 목록
                  </button>
                  <!-- 모달 -->
                  <div
                    class="modal fade"
                    id="PaymentModal"
                    tabindex="-1"
                    aria-labelledby="PaymentModalLabel"
                    aria-hidden="true"
                  >
                    <div class="modal-dialog modal-lg">
                      <div class="modal-content">
                        <div class="modal-header">
                          <h5 class="modal-title" id="PaymentModalLabel">
                            결제수단 목록
                          </h5>
                          <button
                            type="button"
                            class="btn-close"
                            data-bs-dismiss="modal"
                            aria-label="Close"
                          ></button>
                        </div>
                        <div class="modal-body">
                          <div class="text-center mt-5 w-100">
                            <div style="display: grid; justify-content: end">
                              <router-link to="/PaymentAdd">
                                <button
                                  class="btn btn-success"
                                  @click.native="closeModal('PaymentModal')"
                                >
                                  결제방식 추가하기
                                </button>
                              </router-link>
                            </div>
                          </div>
                          <hr style="border-color: gray" />
                          <div class="text-center">
                            <div
                              class="card-body"
                              v-if="paymentlist.length === 0"
                            >
                              데이터가 없습니다.
                            </div>
                            <div
                              class="card-body box m-4"
                              v-for="(payment, index) in paymentlist"
                              :key="index"
                            >
                              <p>결제수단 별명 : {{ payment.paynick }}</p>
                              <p>결제방식 : {{ payment.payway }}</p>
                              <p>결제회사 : {{ payment.paycorp }}</p>
                              <p>결제 수단번호 : {{ payment.paynum }}</p>
                              <p v-if="payment.payday">
                                카드만료일 : {{ payment.payday }}
                              </p>
                              <p v-if="payment.paycvc">
                                cvc(3자리) : {{ payment.paycvc }}
                              </p>
                              <button
                                @click="paymentupdate(payment.num)"
                                class="btn btn-warning"
                              >
                                수정
                              </button>
                              <button
                                @click="paymentdelete(payment.num)"
                                class="btn btn-danger"
                              >
                                삭제
                              </button>
                              <button
                                @click="paymentselect(index)"
                                class="btn btn-danger"
                              >
                                선택
                              </button>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <!-- 모달 끝 -->
                </div>
                <select v-model="payment.payway" class="form-select">
                  <option value="" disabled>결제수단을 선택하세요</option>
                  <option value="card">신용카드</option>
                  <option value="bank">계좌이체</option>
                </select>
                <!-- 신용카드 입력 필드 -->
                <div v-if="payment.payway === 'card'">
                  <label>카드사명:</label>
                  <input
                    type="text"
                    v-model="payment.paycorp"
                    class="form-control"
                  />
                  <label>카드번호:</label>
                  <input
                    type="text"
                    v-model="payment.paynum"
                    class="form-control"
                  />
                  <label>cvc(3자리):</label>
                  <input
                    type="text"
                    v-model="payment.paycvc"
                    class="form-control"
                  />
                  <label>카드만료기한:</label>
                  <input
                    type="text"
                    v-model="payment.payday"
                    class="form-control"
                  />
                </div>

                <!-- 계좌이체 입력 필드 -->
                <div v-if="payment.payway === 'bank'">
                  <label>은행명:</label>
                  <input
                    type="text"
                    v-model="payment.paycorp"
                    class="form-control"
                  />
                  <label>계좌번호:</label>
                  <input
                    type="text"
                    v-model="payment.paynum"
                    class="form-control"
                  />
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="text-center mt-3">
          <button v-if="isBuying" class="btn btn-primary disabled">
            <div class="spinner-border text-light" role="status">
              <span class="sr-only">Loading...</span>
            </div>
          </button>
          <button v-else class="btn btn-primary" @click.prevent="Payment">
            {{ formatCurrency(totalCost) }}원 결제하기</button
          ><br />
        </div>
      </div>
    </div>
  </div>
</template>



<script>
import axios from "axios";
import AddressModal from "./../AddressModal.vue";

export default {
  name: "PayView",
  data() {
    return {
      isBuying: false,
      list: [],
      payTypes: [
        { val: "card", text: "신용카드" },
        { val: "bank", text: "가상계좌" },
        { val: "normal", text: "일반결제" },
      ],
      productlist: [],
      membernum: localStorage.getItem("membernum"),
      postcode: "",
      roadAddress: "",
      Addresspass: false,
      selectedPayType: "",
      totalCost: 0,
      processing: 0,
      name: "성공",
      phone: "010-1234-7890",
      zip: 12345,
      address: "",
      payment: "",
      detailaddress: "축하드려요",
      request: "문앞으로 가고싶다",
      date: "",
      way: "",
      addresslist: "",
      paymentlist: "",
      isAddressModalOpen: false, // 주소 모달 상태
    };
  },
  components: {
    AddressModal,
  },
  computed: {
    totalCost() {
      if (this.list.length === 0) {
        return 0; // Handle case where list is empty
      }
      this.totalCost = this.list.reduce(
        (total, item) => total + item.price * item.count,
        0
      );
      return this.totalCost;
    },
  },
  mounted() {
    this.loadDaumPostcodeScript();
  },
  created() {
    const productlist = this.$route.query.productnum;
    // JSON 문자열을 객체로 변환
    try {
      this.productlist = JSON.parse(productlist);
    } catch (e) {
      console.error("Failed to parse JSON:", e);
    }
    this.orderlist();
    this.addressList();
    this.paymentList();
  },
  methods: {
    openAddressModal() {
      this.isAddressModalOpen = true;
    },
    selectAddress(address) {
      this.selectedAddress = address;
      this.isAddressModalOpen = false;
    },
    addressList() {
      axios
        .post(`${process.env.VUE_APP_BACK_END_URL}/address/list`, null, {
          params: { membernum: this.membernum },
        })
        .then((resp) => {
          this.addresslist = resp.data;
        })
        .catch((error) => {
          console.error("Error fetching address list:", error);
        });
    },
    addressupdate(addressnum) {
      this.$router.push({
        name: "AddressAdd",
        query: { addressnum: addressnum },
      });
    },
    addressdelete(addressnum) {
      axios
        .post(`${process.env.VUE_APP_BACK_END_URL}/address/delete`, null, {
          params: { addressnum: addressnum },
        })
        .then(() => {
          this.addressList();
          alert("삭제되었습니다.");
        })
        .catch((error) => console.error(error));
    },
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
    buyItem() {
      this.isBuying = true;
      //이니시스 결제 하기 전 결제할 상품에 대한 정보를 가맹점 WAS에 먼저 저장
      this.$axios
        .post(`${this.serverHost}/items`, this.item)
        .then((res) => {
          let item = res.data;
          //TODO : 아임포트 SDK를 이용해서 이니시스 결제
        })
        .catch((err) => {
          console.error("Item Insert Error!", err.response.data.msg);
        });
    },
    orderlist() {
      const productlist = Object.values(this.productlist);
      const formdata = new FormData();
      formdata.append("membernum", this.membernum);
      productlist.forEach((product) => {
        formdata.append("productnum", product.toString()); // 정수를 문자열로 변환하여 추가
      });
      axios
        .post(`${process.env.VUE_APP_BACK_END_URL}/cart/orderlist`, formdata)
        .then((resp) => {
          this.list = resp.data;
        })
        .catch((err) => {
          console.error("Error fetching data: ", err);
        });
    },
    formatCurrency(value) {
      return new Intl.NumberFormat("ko-KR", {
        style: "currency",
        currency: "KRW",
      }).format(value);
    },
    loadDaumPostcodeScript() {
      const script = document.createElement("script");
      script.src =
        "//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js";
      script.onload = () => {
        this.isScriptLoaded = true; // 스크립트가 로드되면 isScriptLoaded를 true로 설정
      };
      document.head.appendChild(script);
    },
    execDaumPostcode() {
      if (window.daum && window.daum.Postcode) {
        new daum.Postcode({
          oncomplete: (data) => {
            // 우편번호 검색 완료 후의 처리 로직
            this.zip = data.sigunguCode;
            this.address = data.address;
            this.Addresspass = true;
          },
        }).open();
      } else {
        console.error("Daum Postcode 스크립트가 로드되지 않았습니다.");
      }
    },
    Payment() {
      const formdata = new FormData();
      formdata.append("membernum", this.membernum);
      formdata.append("address", this.address.address);
      formdata.append("detailaddress", this.address.detailaddress);
      formdata.append("addrequest", this.address.addrequest);
      formdata.append("way", this.payment.payway);
      formdata.append("waycorp", this.payment.paycorp);
      formdata.append("waynum", this.payment.paynum);
      const productlist = Object.values(this.productlist);
      productlist.forEach((product) => {
        formdata.append("productnum", product.toString());
      });
      axios
        .post(`${process.env.VUE_APP_BACK_END_URL}/request/add`, formdata)
        .then((resp) => {
          alert("결제되었습니다.");
          this.$router.push({
            name: "PaymentComplete",
            query: { num: resp.data },
          });
        })
        .catch((err) => {
          console.error("Error fetching data: ", err);
        });
    },
    getImageUrl(product_image) {
      const imageUrl = `${process.env.VUE_APP_BACK_END_URL}/uploads/${product_image}`;
      return imageUrl;
    },
    handleImageError(event) {
      event.target.src = "/images/noimg.png";
    },
    closeModal(modalId) {
    const modalElement = document.getElementById(modalId);
    const modalInstance = bootstrap.Modal.getInstance(modalElement);
    if (modalInstance) {
      modalInstance.hide();
    }
    this.$nextTick(() => {
      const backdrop = document.querySelector('.modal-backdrop');
      if (backdrop) {
        backdrop.remove(); // Ensure the backdrop is removed
      }
    });
  },
    addressselect(index) {
      this.address = Object.assign({}, this.addresslist[index]);
      this.closeModal('AddressModal')
    },
    paymentselect(index) {
      this.payment = Object.assign({}, this.paymentlist[index]);
      this.closeModal('PaymentModal')
    },
  },
};
</script>
<style>
.modal {
  background-color: transparent;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}

.modal-dialog {
  display: flex;
  align-items: center;
  height: 100%;
}

.modal-content {
  border-radius: 20px;
  padding: 20px;
}
</style>
  
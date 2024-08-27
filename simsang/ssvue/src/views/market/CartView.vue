/*UI개발:이승희*/
<template>
  <div class="mypage">
    <div class="contact_area">
      <div class="text-center mt-5">
        <h3 style="color: dimgrey">장바구니</h3>
        <hr style="border-color: gray" />
      </div>

      <div class="row">
        <!--◐◐◐◐◐ Fruits 상품 리스트 시작-->
        <div class="col-md-12 col-sm-12 col-xs-12">
          <!-- col-md-12로 수정 -->
          <div class="d-flex" style="text-align: right">
            <router-link
              to="/products"
              class="btn"
              style="
                text-align: right;
                color: #ff6c6c;
                border: none;
                font: bold 16px 'NotoSansKR', arial;
                border-radius: 10%;
              "
            >
              <i class="fa fa-cart-arrow-down mb-3"></i> 상품목록
            </router-link>
          </div>
          <table class="table table-hover text-center">
            <thead class="table">
              <tr style="background-color: salmon">
                <th></th>
                <th>상품이미지</th>
                <th>상품명</th>
                <th>수량</th>
                <th>가격</th>
              </tr>
            </thead>
            <tbody>
              <!-- 장바구니가 0 이면 아래꺼 노출 -->
              <tr v-if="list.length === 0">
                <td colspan="5" class="gotham-font">
                  <p style="font-weight: bold">장바구니가 비어있습니다.</p>
                  <!-- <button
                      type="button"
                      class="btn btn-outline-success"
                      id="favorite"
                      @click="renderComponent('ProductListView')"
                    >
                      상품 보러가기
                    </button> -->
                  <!-- 만들고 있는 중 0703 -->
                </td>
              </tr>
              <!-- 실제 값 받아올 때 수정 -->
              <tr v-else v-for="(item, index) in list" :key="index">
                <td>
                  <input
                    type="checkbox"
                    id="agree"
                    :value="item.productnum"
                    v-model="selectedValues"
                  />
                </td>
                <!-- 상품이미지 -->
                <td>
                  <img
                    :src="getImageUrl(productlist[index]?.img)"
                    class="img-responsive"
                    width="80"
                    height="80"
                    alt=""
                    @error="setDefaultImage($event)"
                  />
                </td>
                <!-- 상품명 -->
                <td>{{ productlist[index]?.name }}</td>
                <td>
                  <button
                    style="background: white; color: black"
                    @click="countupdate(item.productnum,item.count, -1)"
                  >-</button>
                  {{ item.count }}
                  <button
                    style="background: white; color: black"
                    @click="countupdate(item.productnum,item.count, 1)"
                  >+
                  </button>
                  <button
                  class="btn btn-primary"
                
                    @click="countupdate(item.productnum,0, -1)"
                  >삭제
                  </button>
                </td>
                <!-- 수량 -->
                <td>
                  {{ formatCurrency(productlist[index]?.price * item.count) }}
                </td>
                <!-- 가격 -->
              </tr>
              <tr>
                <td colspan="5">
                  <button
                    @click="order()"
                    class="btn btn-success gotham-font no-border"
                  >
                    결제하기
                  </button>
                  <!-- 결제하기 버튼 수정 -->
                  <!-- <router-link to="/products">
                    <button
                      class="btn btn-warning gotham-font no-border"
                      style="margin-left: 20px"
                    >
                      추가 주문
                    </button>
                  </router-link> -->
                </td>
              </tr>
              <tr>
                <td
                  colspan="5"
                  style="text-align: right; font-size: 23px; color: #2e64fe"
                >
                  총 금액 : {{ formatCurrency(totalCost) }}
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
      <!--끝 ◐◐◐◐◐ Fruits 상품 리스트-->
    </div>
  </div>
</template>
<script>
import axios from "axios";

export default {
  name: "CartView",
  data() {
    return {
      selectAll: false,
      cartItems: [], // 장바구니 목록
      selectedItems: [], // 선택된 항목
      list: [], // 전체 리스트
      currentPage: 1, // 현재 페이지
      totalPages: 0, // 총 페이지 수
      totalAcount: 0,
      selectedValues: [], // 선택된 값들을 저장할 배열
      membernum: localStorage.getItem("membernum"),
      initialized: false, // 초기화 여부를 저장할 변수
      productlist: [],
      authToken: "1",
    };
  },
  created() {
    this.loadCartAndOrder();
  },
  computed: {
    nullCart() {
      return this.totalAcount <= 0;
    },
    totalCost() {
      return this.list.reduce((total, item) => {
        // productlist에서 해당 상품의 가격을 찾습니다.
        const product = this.productlist.find(
          (p) => p.productnum === item.productnum
        );
        if (product && this.selectedValues.includes(item.productnum)) {
          return total + product.price * item.count;
        }
        return total;
      }, 0);
    },
  },
  watch: {
    count() {
      this.nowtotalAcount();
    },
  },
  methods: {
    fetchData() {
      return axios
        .post(`${process.env.VUE_APP_BACK_END_URL}/cart/list`, null, {
          params: {
            membernum: this.membernum,
          },
        })
        .then((resp) => {
          this.list = resp.data;
          this.selectedValues = this.list.map((item) => item.productnum);
          this.initialized = true;
        })
        .catch((err) => {
          console.error("Error fetching data: ", err);
        });
    },
    renderComponent(component) {
      this.showComponent = component;
    },

    orderlist() {
      const prolist = Object.values(this.selectedValues);
      const formdata = new FormData();
      formdata.append("membernum", this.membernum);
      prolist.forEach((product) => {
        formdata.append("productnum", product.toString()); 
      });
      axios
        .post(`${process.env.VUE_APP_BACK_END_URL}/cart/orderlist`, formdata, {
          headers: {
            Authorization: `Bearer ${this.authToken}`,
          },
        })
        .then((resp) => {
          this.productlist = resp.data;
        })
        .catch((err) => {
          console.error("Error fetching order list: ", err);
        });
    },
    loadCartAndOrder() {
      this.fetchData()
        .then(() => {
          if (this.selectedValues.length > 0) {
            this.orderlist();
          }
        })
        .catch((err) => {
          console.error("loadCartAndOrder 오류: ", err);
        });
    },
    setDefaultImage(event) {
      event.target.src = "/images/noimg.png"; 
    },
    countupdate(productnum, count,updown) {
      if (count + updown <= 0) {
        const check = confirm("상품을 삭제하시겠습니까?");
        if (check) {
          axios
            .post(`${process.env.VUE_APP_BACK_END_URL}/cart/delete`, null, {
              params: {
                membernum: this.membernum,
                productnum: productnum,
              },
            })
            .then((resp) => {
              this.fetchData();
            })
            .catch((err) => {
              console.error("Error fetching data: ", err);
            });
        }
      } else {
        axios
          .post(`${process.env.VUE_APP_BACK_END_URL}/cart/update`, null, {
            params: {
              membernum: this.membernum,
              productnum: productnum,
              count: updown,
            },
          })
          .then((resp) => {
            this.fetchData();
          })
          .catch((err) => {
            console.error("Error fetching data: ", err);
          });
      }
    },
    getImageUrl(product_image) {
      if (!product_image) {
        return "/images/noimg.png"; 
      }
      const imageUrl = `${process.env.VUE_APP_BACK_END_URL}/uploads/${product_image}`;
      return imageUrl;
    },
    setDefaultImage(event) {
      event.target.src = "/images/noimg.png";
    },

    order() {
      this.$router.push({
        name: "PayView",
        query: { productnum: JSON.stringify(this.selectedValues) },
      });
    },
    changePage(page) {
      this.currentPage = page;
      this.fetchData();
    },
    href(row) {
      this.$router.push({ name: "DetailView", params: row });
    },
    goToProductList() {
      this.$router.push("/prList");
    },
    nowtotalAcount() {
      this.totalAcount = this.count * this.price;
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
  },
};
</script>

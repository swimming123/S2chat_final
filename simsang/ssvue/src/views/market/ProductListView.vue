<template>
  <div class="mypage">
    <div class="contact_area">
      <div class="text-center mt-5">
        <h3 style="color: dimgrey">상품목록</h3>
        <hr style="border-color: gray" />
      </div>
      <!-- 상품 리스트 시작-->
    </div>
    <div class="row">
      <!-- 필터 메뉴 -->
      <div class="col-md-12">
        <div class="filter-menu">
          <!-- 상품 리스트를 카테고리별로 필터링하기 위한 버튼 그룹 -->
          <ul class="button-group sort-button-group">
            <li
              class="button"
              :class="{ active: activeCategory === 'all' }"
              @click="setCategory('all')"
            >
              전체<span>{{ product.length || 0 }}</span>
            </li>
            <li
              class="button"
              :class="{ active: activeCategory === '진정' }"
              @click="setCategory('진정')"
            >
              진정<span>{{
                product.filter((item) => item.product_category === "진정")
                  .length
              }}</span>
            </li>
            <li
              class="button"
              :class="{ active: activeCategory === '불안' }"
              @click="setCategory('불안')"
            >
              불안<span>{{
                product.filter((item) => item.product_category === "불안")
                  .length
              }}</span>
            </li>
            <li
              class="button"
              :class="{ active: activeCategory === '우울' }"
              @click="setCategory('우울')"
            >
              우울<span>{{
                product.filter((item) => item.product_category === "우울")
                  .length
              }}</span>
            </li>
          </ul>
        </div>
      </div>
    </div>
    <!-- 배너 -->
    <div class="banner-wrapper">
      <div class="banner-content">
        <div
          class="banner-item"
          v-for="item in items"
          :key="'first-' + item.id"
        >
          <img :src="item.image" :alt="item.title" class="img-fluid" />
        </div>
        <div
          class="banner-item"
          v-for="item in items"
          :key="'second-' + item.id"
        >
          <img :src="item.image" :alt="item.title" class="img-fluid" />
        </div>
      </div>
    </div>
    <!-- 상품 리스트 -->
    <div class="row featured isotope">
      <div
        v-for="product in filteredItems"
        :key="product.product_num"
        class="col-md-3 col-sm-6 col-xs-12 featured-items isotope-item"
        :class="product.product_category"
      >
        <div class="product-item">
          <a @click="productDetail(product.product_num)">
            <img
              :src="getImageUrl(product.product_image)"
              class="img-responsive"
              width="255"
              height="322"
              alt=""
              @error="setDefaultImage($event)"
            />
          </a>

          <div class="product-title mt-3">
            <a @click="productDetail(product.product_num)">
              <h3>{{ product.product_name }}</h3>
              <span>₩{{ product.product_price.toLocaleString() || 0 }}</span>
              <!-- 가격을 원화로 표시 -->
            </a>
          </div>
          <button
            @click="
              addToCart(
                product.product_num,
                product.product_name,
                product.product_price
              )
            "
            class="btn btn-primary"
          >
            장바구니 담기
          </button>
        </div>
      </div>
    </div>
    <!-- 끝 상품 리스트-->
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "ProductList",
  data() {
    return {
      product: [],
      activeCategory: "all", // 활성화된 카테고리
      membernum: localStorage.getItem("membernum"),
    };
  },
  computed: {
    // 활성화된 카테고리에 따라 필터링된 항목을 반환
    filteredItems() {
      if (this.activeCategory === "all") {
        return this.product;
      } else {
        return this.product.filter(
          (item) => item.product_category === this.activeCategory
        );
      }
    },
  },
  created() {
    this.productList();
  },
  methods: {
    // 상품 목록을 가져오는 메서드
    productList() {
      axios
        .get(`${process.env.VUE_APP_BACK_END_URL}/product/proList`)
        .then((resp) => {
          this.product = resp.data;
        })
        .catch((err) => {
          console.error(err);
        });
    },
    // 장바구니에 추가하는 메서드
    addToCart(productnum, name) {
      const formdata = new FormData();
      formdata.append("membernum", this.membernum);
      formdata.append("productnum", productnum);
      formdata.append("count", 1);
      axios
        .post(`${process.env.VUE_APP_BACK_END_URL}/cart/add`, formdata)
        .then(() => {
          alert(name + "(이)가 장바구니에 추가되었습니다.");
        })
        .catch((err) => {
          console.error("Error adding to cart: ", err);
          this.checkadd(productnum, name);
        });
    },
    // 장바구니에 제품 수량을 업데이트하는 메서드
    checkadd(productnum, name) {
      axios
        .post(`${process.env.VUE_APP_BACK_END_URL}/cart/update`, null, {
          params: {
            membernum: this.membernum,
            productnum: productnum,
            count: 1,
          },
        })
        .then((resp) => {
          const count = resp.data.count;
          alert(name + "의 수량이 " + count + "개가 되었습니다.");
        })
        .catch((err) => {
          console.error("Error updating cart: ", err);
        });
    },
    // 상품 상세 페이지로 이동하는 메서드
    productDetail(product_num) {
      this.$router.push({
        name: "ProductDetail",
        query: { product_num },
      });
    },
    // 카테고리를 설정하는 메서드
    setCategory(category) {
      this.activeCategory = category;
    },
    // 기본 이미지로 설정하는 메서드
    setDefaultImage(event) {
      event.target.src = "/images/noimg.png"; 
    },
    // 이미지 URL을 반환하는 메서드
    getImageUrl(product_image) {
      if (!product_image) {
        return `/images/product/maketadd_default.png`;
      }
      const imageUrl = `${process.env.VUE_APP_BACK_END_URL}/uploads/${product_image}`;
      return imageUrl;
    },
  },
};
</script>

<style scoped>
/* 스타일 정의 */
.product-item {
  border: 1px solid #ece9e6;
  border-radius: 20px;
  margin-bottom: 20px; 
  padding: 20px;
}

.product-item img {
  border: 1px solid #ece9e6;
  object-fit: contain;
  width: 100%;
}

.product-meta a {
  color: #ece9e6; 
}

.banner-wrapper {
  overflow: hidden;
  white-space: nowrap;
  position: relative;
  width: 100%;
}

.banner-content {
  display: inline-flex;
  width: calc(100% * 2);
  animation: scroll 30s linear infinite;
}

.banner-item {
  display: inline-block;
  width: 300px;
}

.banner-item img {
  display: block;
  width: 100%;
}

@keyframes scroll {
  0% {
    transform: translateX(0);
  }
  100% {
    transform: translateX(-50%); 
  }
}

.filter-menu {
  margin-bottom: 20px;
}

.button-group {
  list-style: none;
  padding: 0;
  margin: 0;
  display: flex;
  justify-content: center;
}

.button {
  background: #f8f8f8;
  padding: 10px 20px;
  margin: 0 5px;
  cursor: pointer;
  border-radius: 5px;
  transition: background 0.3s;
}

.button.active {
  background: #ff7f00;
  color: #fff;
}
</style>

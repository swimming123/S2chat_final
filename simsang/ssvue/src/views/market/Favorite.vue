/*UI개발:이승희*/
<template>
  <section id="special" class="special sections">
    <div class="container">
      <div class="row">
        <!-- 섹션 헤더 -->
        <div class="head_title text-center">
          <h2
            style="color: rgb(241 211 181); font: bold 40px 'NotoSansKR', arial"
          >
            Shopping Cart
          </h2>
        </div>
        <!--◐◐◐◐◐ Fruits 상품 리스트 시작-->
        <div class="col-md-12">
          <div class="titie-section wow fadeInDown animated">
            <h1></h1>
          </div>
        </div>
      </div>
      <!-- 상품 리스트 style.css 797-->
      <div class="row featured isotope">
        <div
          v-for="item in filteredItems"
          :key="item.id"
          class="col-md-3 col-sm-6 col-xs-12"
          :class="item.category"
        >
          <div class="product-item">
            <img
              :src="item.imgSrc"
              class="img-responsive"
              width="200"
              height="300"
            />
            <div class="product-hover">
              <div class="product-meta">
                
                <a @click="addToCart(item.id, item.name, item.price)" style="font: bold 15px 'NotoSansKR', arial;"><i class="pe-7s-cart"></i>장바구니 담기</a>
              </div>
            </div>
            <div class="product-title">
              <a href="#">
                <h3>{{ item.name }}</h3>
                <span style="font: bold 15px 'NotoSansKR', arial"
                  >{{ item.price }}원</span
                >
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import axios from "axios";
export default {
  name: "list",
  data() {
    return {
      list: [
        {
          id: 5,
          name: "석류",
          price: 2000,
          category: "cat-3",
          imgSrc: "images/FruitsListimg1.jpg",
        },
        {
          id: 6,
          name: "롸임",
          price: 3000,
          category: "cat-1",
          imgSrc: "images/FruitsListimg2.jpg",
        },
        {
          id: 7,
          name: "딸기",
          price: 4000,
          category: "cat-1",
          imgSrc: "images/FruitsListimg5.jpg",
        },
        {
          id: 8,
          name: "파파야",
          price: 5000,
          category: "cat-3",
          imgSrc: "images/FruitsListimg4.jpg",
        },
      ], // 전체 리스트
      currentPage: 1, // 현재 페이지
      totalPages: 0, // 총 페이지 수
      activeCategory: "all", // 활성화된 카테고리
      membernum:localStorage.getItem("membernum")
    };
  },
  created() {
    this.fetchData();
  },
  computed: {
    // 활성화된 카테고리에 따라 필터링된 항목을 반환
    filteredItems() {
      if (this.activeCategory === "all") {
        return this.list;
      } else {
        return this.list.filter(
          (item) => item.category === this.activeCategory
        );
      }
    },
  },
  methods: {
    // 데이터 가져오기
    fetchData() {
      // 데이터 가져오는 axios 코드 주석 처리
      // axios.get(`${process.env.VUE_APP_BACK_END_URL}/thismovie0607/uboard/upList , {
      //   params: {
      //     cPage: this.currentPage, // 현재 페이지 전달
      //   }
      // })
      // .then((resp) => {
      //   this.list = resp.data.list; // 데이터 리스트 할당
      //   this.totalPages = resp.data.totalPages; // 총 페이지 수 할당
      // })
      // .catch((err) => {
      //   console.error("Error fetching data: ", err)
      // })
    },
    // 장바구니로 이동
    addToCart(productnum, name, price) {
      const formdata = new FormData();
      formdata.append("name", name);
      formdata.append("price", price);
      formdata.append("productnum", productnum);
      formdata.append("count", 1);
      formdata.append("membernum", this.membernum);
      axios
        .post(`${process.env.VUE_APP_BACK_END_URL}/cart/add`, formdata)
        .then((resp) => {
          alert(name + "가 장바구니에 추가되었습니다.");
        })
        .catch((err) => {
          console.error("Error fetching data: ", err);
        });
    },
    // 페이지 변경 메서드
    changePage(page) {
      this.currentPage = page;
      this.fetchData();
    },
    // 항목 클릭 시 상세보기로 이동
    href(row) {
      this.$router.push({ name: "DetailView", params: row });
    },
    // 상품 페이지로 이동
    goToProducts() {
      this.$router.push("/products");
    },
    // 카테고리 설정
    setCategory(category) {
      this.activeCategory = category;
    },
  },
};
</script>

<style scoped>

.head_title {
  padding-top: 50px; 
  padding-bottom: 80px; 
}

.filter-menu {
  margin-bottom: 10px; 
}

.features {
  padding-top: 80px; 
  padding-bottom: 80px; 
}

.label.label-default {
  font-size: 18px; 
  font-weight: bold; 
}

.form-control::placeholder {
  color: #666666;
  font-family: "굴림", Gulim, "Nanum Gothic", sans-serif; 
}

.product-item {
  border-radius: 5px; 
  margin-bottom: 20px;
}
.product-item img {
  border-bottom: 2px solid #ffffff;
}
</style>
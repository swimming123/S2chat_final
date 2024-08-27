/*UI개발:송지미, 윤수영*/
<template>
  <div class="mypage">
    <!-- 상품 상세 페이지 -->
    <div class="container">
      <h3 style="color: dimgrey; text-align: center">상품 상세페이지</h3>
      <hr style="border-color: gray" />
      <div class="row justify-content-center">
        <div class="col-md-6 col-sm-9 col-xs-12">
          <div class="features_content">
            <img
              v-if="imageSrc"
              :src="imageSrc"
              alt="product Image"
              class="list-image m-auto"
              @error="handleImageError"
              style="
                width: 200px;
                height: 200px;
                object-fit: cover;
                border: 1px solid #ddd;
              "
            />

            <!-- 상품 상세 정보 입력 폼 -->
            <div class="main_contact2">
              <div class="information">
                <form @submit.prevent="submitForm">
                  <span class="label label-default">카테고리</span>
                  <input
                    type="text"
                    class="form-control"
                    v-model="result.product_category"
                    placeholder="상품코드"
                  />

                  <span class="label label-default">상품코드</span>
                  <input
                    type="text"
                    class="form-control"
                    v-model="result.product_num"
                    placeholder="상품코드"
                  />

                  <span class="label label-default">상품명</span>
                  <input
                    type="text"
                    class="form-control"
                    v-model="result.product_name"
                    placeholder="상품명"
                  />

                  <span class="label label-default">상품 가격</span>
                  <input
                    type="text"
                    class="form-control"
                    v-model="result.product_price"
                    placeholder="가격"
                  />

                  <span class="label label-default">상품 수량</span>
                  <input
                    type="text"
                    class="form-control"
                    v-model="result.product_stock"
                    placeholder="수량"
                  />

                  <span class="label label-default">제조사</span>
                  <input
                    type="text"
                    class="form-control"
                    v-model="result.product_manufacturer"
                    placeholder="제조사"
                  />

                  <span class="label label-default">판매자</span>
                  <input
                    type="text"
                    class="form-control"
                    v-model="result.product_registrant"
                    placeholder="판매자"
                  />

                  <span class="label label-default">조회수</span>
                  <input
                    type="text"
                    class="form-control"
                    v-model="result.product_hit"
                    placeholder="조회수"
                  />
                  <div class="d-flex m-auto justify-content-center">
                    <button
                      @click="
                        addToCart(
                          result.product_num,
                          result.product_name,
                          result.product_price
                        )
                      "
                      class="btn btn-primary"
                    >
                      장바구니 담기
                    </button>
                    <button
                      type="button"
                      class="btn btn-primary"
                      @click.prevent="goBack"
                    >
                      목록
                    </button>
                  </div>
                </form>
              </div>
            </div>
          </div>
          <div class="text-center mt-5">
            <h3 style="color: dimgrey; text-align: center">리뷰</h3>
            <hr style="border-color: gray" />
          </div>
          <table class="table table-hover mt-3 custom-table">
            <thead>
              <div class="select text-end">
                <div class="filters">
                  <button class="today" @click="filterByPeriod('today')">
                    오늘
                  </button>
                  <button class="one-month" @click="filterByPeriod('1month')">
                    1개월
                  </button>
                  <button
                    class="three-months"
                    @click="filterByPeriod('3months')"
                  >
                    3개월
                  </button>
                  <button class="six-months" @click="filterByPeriod('6months')">
                    6개월
                  </button>
                  <button class="one-year" @click="filterByPeriod('1year')">
                    1년
                  </button>
                  <button class="three-years" @click="filterByPeriod('3years')">
                    3년
                  </button>
                  <input
                    id="startDate"
                    type="date"
                    class="date-picker"
                    v-model="startDate"
                  />
                  <span>~</span>
                  <input
                    id="endDate"
                    type="date"
                    class="date-picker"
                    v-model="endDate"
                  />
                  <button class="query" @click="fetchReviewData">조회</button>
                </div>
                <div id="review_category" class="dropdown">
                  <button class="dropdown-button">
                    <span>{{ selectedCategory }}</span>
                    <span class="triangle-down"></span>
                  </button>
                  <div class="dropdown-content">
                    <a href="#" @click="filterByCategory('전체')" class="right"
                      >전체</a
                    >
                    <a href="#" @click="filterByCategory('한달사용 리뷰')"
                      >한달사용 리뷰</a
                    >
                    <a href="#" @click="filterByCategory('스토어PICK 리뷰')"
                      >스토어PICK 리뷰</a
                    >
                    <a href="#" @click="filterByCategory('도움준 리뷰')"
                      >도움준 리뷰</a
                    >
                    <a href="#" @click="filterByCategory('판매자 답글 등록')"
                      >판매자 답글 등록</a
                    >
                  </div>
                </div>
              </div>
            </thead>
            <tbody>
              <div class="box text-center">
                <div v-if="review.length === 0">데이터가 없습니다.</div>
                <div
                  v-for="item in review"
                  :key="item.review_num"
                  class="review text-start"
                >
                  <div class="review-content">
                    <h5 id="or">{{ item.review_content }}</h5>
                    <p>{{ item.review_date }}</p>
                    <p id="review_category">{{ item.review_category }}</p>
                    <p id="rating" class="rating">
                      <span v-for="n in Math.round(item.review_stars)" :key="n"
                        >★</span
                      >
                      <span
                        v-for="n in 5 - Math.round(item.review_stars)"
                        :key="'empty-' + n"
                        >☆</span
                      >
                    </p>
                    <p id="content" class="text">
                      {{ item.review_content }}
                    </p>
                  </div>
                  <img
                    v-if="item.review_imagename"
                    :src="getreviewImageUrl(item.review_imagename)"
                    alt="Product Image"
                    class="review-image"
                    @error="handleImageError($event)"
                  />
                  <!-- <button
                  class="edit-button"
                  @click="goToReviewDetail(item.reviewnum)"
                >
                  수정하기
                </button> -->
                </div>
              </div>
            </tbody>
          </table>
        </div>
      </div>
    </div>
    <!-- 리뷰 목록 페이지 -->
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "ProductAndReview",
  data() {
    return {
      membernum: localStorage.getItem("membernum"),
      result: {}, // 상품 상세 정보
      updateMessage: "", // 업데이트 메시지
      selectedFile: null, // 선택된 파일
      imageSrc: null, // 이미지 미리보기 src
      selectedFileName: "", // 선택된 파일 이름
      review: [], // 리뷰 리스트
      startDate: "2024-01-01", // 초기 시작일 설정
      endDate: new Date().toISOString().substr(0, 10), // 초기 종료일 설정
      selectedCategory: "전체",
    };
  },
  created() {
    this.fetchData(this.$route.query.product_num);
    this.fetchReviewData(this.$route.query.product_num);
  },
  methods: {
    getreviewImageUrl(imageName) {
      return imageName
        ? `${process.env.VUE_APP_BACK_END_URL}/uploads/${imageName}`
        : "/images/noimg.png";
    },
    // 상품 상세 정보 가져오기
    fetchData(product_num) {
      if (!product_num || isNaN(product_num)) {
        console.error("유효하지 않은 제품 번호:", product_num);
        return;
      }
      axios
        .get(
          `${process.env.VUE_APP_BACK_END_URL}/product/proDetail/${product_num}`
        )
        .then((resp) => {
          this.result = resp.data; // 데이터 할당
          if (this.result.product_image) {
            this.imageSrc = this.getImageUrl(this.result.product_image); // 기존 이미지 설정
          }
        })
        .catch((err) => {
          console.error(err);
        });
    },
    // 리뷰 데이터 가져오기
    fetchReviewData(product_num) {
      axios
        .post(`${process.env.VUE_APP_BACK_END_URL}/review/reviewpro`,null,{params:{productnum:product_num}})
        .then((response) => {
          if (this.selectedCategory === "전체") {
            this.review = response.data;
          } else {
            this.review = response.data.filter(
              (item) => {
                if (this.selectedCategory === item.reviewcategory) {
                  return item;
                }
              } // 전체 카테고리 선택 시 모든 리뷰 표시
            );
          }
        })
        .catch((error) => {
          console.error("값 안들어옴", error);
        });
    },
    // 이미지 에러 핸들링
    handleImageError(event) {
      event.target.src = "images/noimg.png"; 
    },
    // 장바구니에 추가
    addToCart(product_num, product_name, product_price) {
      // ■■■■■■■■■■■컨트롤러 없음■■■■■■■■■
      // 장바구니 추가 로직
    },

    goBack() {
      this.$router.go(-1);
    },

    goToReviewDetail(reviewnum) {
      this.$router.push({ name: "reviewDetail", params: { reviewnum } });
    },

    filterByPeriod(period) {
      const today = new Date().toISOString().substr(0, 10);
      let startDate, endDate;
      switch (period) {
        case "today":
          startDate = today;
          endDate = today;
          break;
        case "1month":
          startDate = new Date(new Date().setMonth(new Date().getMonth() - 1))
            .toISOString()
            .substr(0, 10);
          endDate = today;
          break;
        case "3months":
          startDate = new Date(new Date().setMonth(new Date().getMonth() - 3))
            .toISOString()
            .substr(0, 10);
          endDate = today;
          break;
        case "6months":
          startDate = new Date(new Date().setMonth(new Date().getMonth() - 6))
            .toISOString()
            .substr(0, 10);
          endDate = today;
          break;
        case "1year":
          startDate = new Date(
            new Date().setFullYear(new Date().getFullYear() - 1)
          )
            .toISOString()
            .substr(0, 10);
          endDate = today;
          break;
        case "3years":
          startDate = new Date(
            new Date().setFullYear(new Date().getFullYear() - 3)
          )
            .toISOString()
            .substr(0, 10);
          endDate = today;
          break;
        default:
          startDate = "2024-01-01";
          endDate = today;
      }
      this.startDate = startDate;
      this.endDate = endDate;
      this.fetchReviewData();
    },

    filterByCategory(category) {
      this.selectedCategory = category;
      this.fetchReviewData();
    },

    getImageUrl(imageName) {
      return `${process.env.VUE_APP_BACK_END_URL}/uploads/${imageName}`;
    },
    // 폼 제출
    submitForm() {
      // ■■■■■■■■■■■컨트롤러 없음■■■■■■■■■
      // 폼 제출 로직
    },
  },
};
</script>

<style scoped>
.mypage {
  margin: 0 auto;
}

.features_content img {
  max-width: 100%;
  height: auto;
}

.custom-table .dropdown {
  position: relative;
  display: inline-block;
}

.custom-table .dropdown-content {
  display: none;
  position: absolute;
  background-color: #f9f9f9;
  min-width: 160px;
  box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
  z-index: 1;
}

.custom-table .dropdown:hover .dropdown-content {
  display: block;
}

.custom-table .dropdown-content a {
  color: black;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
}

.custom-table .dropdown-content a:hover {
  background-color: #f1f1f1;
}

.dropdown-button {
  background-color: #4caf50;
  color: white;
  padding: 10px;
  font-size: 16px;
  border: none;
  cursor: pointer;
  border-radius: 4px;
}

.date-picker {
  margin: 0 5px;
}

.btn {
  margin-top: 10px;
}

.review {
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 10px;
  margin: 10px 0;
  background-color: #f9f9f9;
}

.review-content {
  margin-bottom: 10px;
}

.review-image {
  width: 100px;
  height: 100px;
  object-fit: cover;
  border: 1px solid #ddd;
  margin-bottom: 10px;
}

.edit-button {
  background-color: #007bff;
  color: white;
  border: none;
  padding: 5px 10px;
  border-radius: 4px;
  cursor: pointer;
}

.edit-button:hover {
  background-color: #0056b3;
}
</style>

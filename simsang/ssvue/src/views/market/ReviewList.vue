/*UI개발:송지미, 윤수영*/
<template>
  <div class="mypage">
    <div class="contact_area">
      <div class="text-center mt-5">
        <h3 style="color: dimgrey">마이리뷰</h3>
        <hr style="border-color: gray" />
      </div>
      <table class="table table-hover mt-3 custom-table">
        <thead>
          <div class="select">
            <div class="filters">
              <button class="today" @click="filterByPeriod('today')">
                오늘
              </button>
              <button class="one-month" @click="filterByPeriod('1month')">
                1개월
              </button>
              <button class="three-months" @click="filterByPeriod('3months')">
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
            <!-- 카테고리 -->
            <div id="review_category" class="dropdown">
              <button class="dropdown-button">
                <span>{{ selectedCategory }}</span>
                <!-- 선택된 카테고리 표시 -->
                <span class="triangle-down"></span>
              </button>

              <div class="dropdown-content">
                <a href="#" @click="filterByCategory('전체')">전체</a>
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
          <div class="box" style="min-width: 1000px">
            <!-- 리뷰 표시 -->
            <div v-if="review.length > 0">
              <div v-for="item in review" :key="item.reviewnum" class="review">
                <div class="review-content">
                  <h6 id="review_date"></h6>
                  <h5 id="or">{{ item.reviewnum }}</h5>
                  <h5 id="or">{{ item.reviewcontent }}</h5>
                  <p id="review_category">{{ item.reviewcategory }}</p>
                  <p id="rating" class="rating">
                    <span v-for="n in Math.round(item.reviewstars)" :key="n"
                      >★</span
                    >
                    <span
                      v-for="n in 5 - Math.round(item.reviewstars)"
                      :key="'empty-' + n"
                      >☆</span
                    >
                  </p>
                  <p id="content" class="text">{{ item.reviewcontent }}</p>
                </div>
                <img
                  v-if="item.reviewimagename"
                  :src="item.reviewimagename"
                  alt="product image"
                  class="review-image"
                  id="review_image"
                />

                <!-- 이미지가 없는 경우 -->
                <img
                  v-else
                  src="images/abimg.png"
                  alt="No Image"
                  class="review-image"
                  id="review_image"
                />
                <button
                  class="edit-button"
                  @click="goToReviewDetail(item.reviewnum)"
                >
                  수정하기
                </button>
              </div>
            </div>
            <div v-if="review.length === 0">데이터가 없습니다.</div>
            <div v-else class="text-center">데이터를 불러오는 중입니다...</div>
          </div>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "ReviewSection",
  data() {
    return {
      review: [],

      startDate: "2024-01-01", 
      endDate: new Date().toISOString().substr(0, 10),
      selectedCategory: "전체",
    };
  },
  created() {
    this.fetchReviewData();
  },
  methods: {
    fetchReviewData() {
      axios
        .get(`${process.env.VUE_APP_BACK_END_URL}/reviews/reviewlist`)
        .then((response) => {
          if (this.selectedCategory === "전체") {
            this.review = response.data;
          } else {
            this.review = response.data.filter(
              (item) => {
                if (this.selectedCategory === item.reviewcategory) {
                  return item;
                }
              } 
            );
          }
        })
        .catch((error) => {
          console.error("값 안들어옴", error);
        });
    },
    goToReviewDetail(review_num) {
      this.$router.push({
        path: `/reviewdetail`,
        query: { review_num: review_num },
      });
    },
    setRating(value) {
      this.ratingValue = value;
    },
    filterByPeriod(period) {
      const today = new Date();
      let start = new Date();
      start.setHours(0, 0, 0, 0); 

      if (period === "today") {
        // 오늘 날짜
        this.startDate = today.toISOString().substr(0, 10);
        this.endDate = today.toISOString().substr(0, 10);
      } else if (period === "1month") {
        // 한 달 전 날짜
        start.setMonth(today.getMonth() - 1);
        this.startDate = start.toISOString().substr(0, 10);
        this.endDate = today.toISOString().substr(0, 10);
      } else if (period === "3months") {
        // 세 달 전 날짜
        start.setMonth(today.getMonth() - 3);
        this.startDate = start.toISOString().substr(0, 10);
        this.endDate = today.toISOString().substr(0, 10);
      } else if (period === "6months") {
        // 여섯 달 전 날짜
        start.setMonth(today.getMonth() - 6);
        this.startDate = start.toISOString().substr(0, 10);
        this.endDate = today.toISOString().substr(0, 10);
      } else if (period === "1year") {
        // 일 년 전 날짜
        start.setFullYear(today.getFullYear() - 1);
        this.startDate = start.toISOString().substr(0, 10);
        this.endDate = today.toISOString().substr(0, 10);
      } else if (period === "3years") {
        // 3년 전 날짜
        start.setFullYear(today.getFullYear() - 3);
        this.startDate = start.toISOString().substr(0, 10);
        this.endDate = today.toISOString().substr(0, 10);
      }

      this.fetchReviewData();
    },
    filterByCategory(review_category) {
      this.selectedCategory = review_category;
      this.fetchReviewData();
    },

    updateImage(event) {
      const file = event.target.files[0];
      if (file) {
        const reader = new FileReader();
        reader.onload = (e) => {
          this.selectedImageUrl = e.target.result;
        };
        reader.readAsDataURL(file);
      }
    },
    // updateImage(event) {
    //   axios
    //     .get(`${process.env.VUE_APP_BACK_END_URL}/reviews/uploadImage`)
    //     .then((response) => {

    //     })
    // },
  },
};
</script>

<style scoped>
body,
.review,
.head_title,
.dropdown-button,
.dropdown-content a,
button,
p {
  font-family: "맑은 고딕", "Malgun Gothic", "돋움", "Dotum", sans-serif;
}

.head_title3 {
  padding-bottom: 10px;
}

.head_title3 + .box {
  margin-top: 20px;
}

h2 {
  font-size: 40px;
  line-height: 38px;
  font-weight: 600;
  font-family: "NotoSansKR";
  margin: 0;
}

.box {
  padding: 20px;
  margin: 20px 0;
  border-radius: 10px;
  background: #fcfafa;
  box-shadow: 5px 5px 10px rgba(221, 119, 108, 0.5); 
}

.head_title {
  padding-top: 50px;  
  padding-bottom: 80px;  
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
  font-family: "맑은 고딕", "Malgun Gothic", "돋움", "Dotum", sans-serif;
}

input[type="date"] {
  border: 2px solid #db917b;
  padding: 5px;
  border-radius: 4px;
}

input[type="date"]:focus {
  border-color: #db917b;
  outline: none;
}

.filters {
  display: flex;
  align-items: center;
  gap: 10px;
}

.filters button {
  margin-right: 10px;
  border: none;
}

.filters .today {
  background-color: #f8e8e3;
}

.filters .one-month {
  background-color: #f8e8e3;
}

.filters .three-months {
  background-color: #f8e8e3;
}

.filters .six-months {
  background-color: #f8e8e3;
}

.filters .one-year {
  background-color: #f8e8e3;
}

.filters .three-years {
  background-color: #f8e8e3;
}

.filters .query {
  background-color: #f8e8e3;
}

.filters .dropdown {
  margin-left: auto;
}

.dropdown {
  position: relative;
  display: inline-block;
}

.dropdown-button {
  width: 200px;
  background-color: #f7e6e6;
  border: 1px solid #ddd;
  padding: 4px;
  cursor: pointer;
  font-size: 16px;
  text-align: left;
  justify-content: space-between;
  align-items: center;
  position: relative;
}

.triangle-down {
  width: 0;
  height: 0;
  border-left: 7px solid transparent;
  border-right: 7px solid transparent;
  border-top: 7px solid black;
  position: absolute;
  right: 10px;
  top: 50%;
  transform: translateY(-50%);
}

.dropdown-content {
  width: 220px; 
  display: none;
  position: absolute;
  background-color: #f7e6e4; 
  box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
  z-index: 1;
}

.dropdown-content a {
  color: black;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
}

.dropdown-content a:hover {
  background-color: rgb(236, 163, 141); 
  color: rgb(82, 79, 79); 
}

.dropdown:hover .dropdown-content {
  display: block;
}

.dropdown:hover .dropdown-button {
  background-color: #ddd;
}

.review {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px 0;
  border-bottom: 1px solid #ddd;
}

.review-content {
  flex: 1;
  padding-right: 20px;
}

.review-image {
  max-width: 100px;
  max-height: 100px;
  margin-right: 50px;
}

.edit-button {
  margin-left: auto; 
  background-color: #f2f2f2;
  border: none;
  padding: 10px 20px; 
  cursor: pointer; 
}

.edit-button:hover {
  background-color: #f8e8e3; 
}

.edit-button:active {
  background-color: #72fac1f3;
}

.rating {
  color: rgb(233, 101, 53);
}

.rating-number {
  color: #000;
}
</style>

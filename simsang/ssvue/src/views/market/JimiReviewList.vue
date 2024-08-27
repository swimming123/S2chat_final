/*UI개발:송지미*/
<template>
  <div class="mypage">
    <div class="contact_area">
      <div class="text-center mt-5">
        <h3 style="color: dimgrey">마이리뷰</h3>
        <hr style="border-color: gray" />
      </div>
      <div class="filters">
        <button class="today" @click="filterByPeriod('today')">오늘</button>
        <button class="one-month" @click="filterByPeriod('1month')">
          1개월
        </button>
        <button class="three-months" @click="filterByPeriod('3months')">
          3개월
        </button>
        <button class="six-months" @click="filterByPeriod('6months')">
          6개월
        </button>
        <button class="one-year" @click="filterByPeriod('1year')">1년</button>
        <button class="three-years" @click="filterByPeriod('3years')">
          3년
        </button>
        <input type="date" v-model="startDate" />
        <span>~</span>
        <input type="date" v-model="endDate" />
        <button class="query" @click="fetchReviewData">조회</button>
        <div class="dropdown">
          <button class="dropdown-button">
            <span>{{ selectedCategory }}</span>
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
            <a href="#" @click="filterByCategory('도움준 리뷰')">도움준 리뷰</a>
            <a href="#" @click="filterByCategory('판매자 답글 등록')"
              >판매자 답글 등록</a
            >
          </div>
        </div>
      </div>
      <table class="table table-hover mt-3 custom-table">
        <thead>
          <tr>
            <th>리뷰 날짜</th>
            <th>리뷰 번호</th>
            <th>리뷰 내용</th>
            <th>리뷰 카테고리</th>
            <th>별점</th>
            <th>이미지</th>
            <th>수정하기</th>
          </tr>
        </thead>
        <tbody>
          <tr v-if="review.length === 0">
            <td colspan="10" class="text-center">데이터가 없습니다.</td>
          </tr>
          <tr v-for="item in review" :key="item.review_num">
            <td>{{ item.review_date }}</td>
            <td>{{ item.review_num }}</td>
            <td>{{ item.review_content }}</td>
            <td>{{ item.review_category }}</td>
            <td>
              <span v-for="n in Math.round(item.review_stars)" :key="n">★</span>
              <span
                v-for="n in 5 - Math.round(item.review_stars)"
                :key="'empty-' + n"
                >☆</span
              >
            </td>
            <td>
              <img
                v-if="item.review_imagename"
                :src="getImageUrl(item.review_imagename)"
                alt="Product Image"
                class="review-image"
                @error="handleImageError($event)"
              />
            </td>
            <td>
              <button
                @click="goToReviewDetail(item.review_num)"
                class="edit-button"
              >
                수정하기
              </button>
            </td>
          </tr>
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
    getImageUrl(imageName) {
      if (!imageName) {
        return `/images/noimg.png`;
      }
      if (imageName.startsWith('http')) {
        return imageName;
      }
      const imageUrl = `${process.env.VUE_APP_BACK_END_URL}/uploads/${imageName}`;
      return imageUrl;
    },
    fetchReviewData() {
      axios
        .post(`${process.env.VUE_APP_BACK_END_URL}/review/reviewlist`)
        .then((response) => {
          this.review = response.data;
        })
        .catch((error) => {
          console.error("Error fetching review data", error);
        });
    },
    goToReviewDetail(review_num) {
      this.$router.push({
        path: `/JimiReviewDetail`,
        query: { review_num: review_num },
      });
    },
    handleImageError(event) {
      event.target.src = "/images/product/maketadd_default.png";
    },
    filterByPeriod(period) {
      const today = new Date();
      let start = new Date();
      start.setHours(0, 0, 0, 0);

      switch (period) {
        case "today":
          this.startDate = today.toISOString().substr(0, 10);
          this.endDate = today.toISOString().substr(0, 10);
          break;
        case "1month":
          start.setMonth(today.getMonth() - 1);
          this.startDate = start.toISOString().substr(0, 10);
          this.endDate = today.toISOString().substr(0, 10);
          break;
        case "3months":
          start.setMonth(today.getMonth() - 3);
          this.startDate = start.toISOString().substr(0, 10);
          this.endDate = today.toISOString().substr(0, 10);
          break;
        case "6months":
          start.setMonth(today.getMonth() - 6);
          this.startDate = start.toISOString().substr(0, 10);
          this.endDate = today.toISOString().substr(0, 10);
          break;
        case "1year":
          start.setFullYear(today.getFullYear() - 1);
          this.startDate = start.toISOString().substr(0, 10);
          this.endDate = today.toISOString().substr(0, 10);
          break;
        case "3years":
          start.setFullYear(today.getFullYear() - 3);
          this.startDate = start.toISOString().substr(0, 10);
          this.endDate = today.toISOString().substr(0, 10);
          break;
      }

      this.fetchReviewData();
    },
    filterByCategory(review_category) {
      this.selectedCategory = review_category;
      this.fetchReviewData();
    },
  },
};
</script>

<style scoped>

.mypage {
  padding: 20px;
}

.contact_area {
  margin-top: 60px;
}

.filters {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-bottom: 20px;
}

.filters button,
.filters .query {
  background-color: #f8e8e3;
  border: none;
  padding: 10px 20px;
  cursor: pointer;
}

.filters .query {
  background-color: #f7e6e4;
}

.dropdown {
  position: relative;
  display: inline-block;
}

.dropdown-button {
  background-color: #f7e6e6;
  border: 1px solid #ddd;
  padding: 10px;
  cursor: pointer;
  display: flex;
  justify-content: space-between;
  align-items: center;
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
  display: none;
  position: absolute;
  background-color: #f7e6e4;
  box-shadow: 0px 8px 16px rgba(0, 0, 0, 0.2);
  z-index: 1;
  width: 220px;
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

.table {
  width: 100%;
  border-collapse: collapse;
}

.table th,
.table td {
  padding: 8px;
  border-bottom: 1px solid #ddd;
}

.table tr:hover {
  background-color: #f5f5f5;
}

.review-image {
  max-width: 100px;
  max-height: 100px;
}

.edit-button {
  background-color: #f8e8e3;
  border: none;
  padding: 5px 10px;
  cursor: pointer;
}
</style>

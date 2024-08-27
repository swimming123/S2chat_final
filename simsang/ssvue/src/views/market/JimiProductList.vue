/*UI개발:송지미*/
<template>
  <div class="container">
    <h1>상품관리</h1>
    <div style="text-align: left;">
      <button type="button" class="add-button" @click.prevent="add">상품등록</button>
    </div>
    <br>
    <!-- 상품 관리 테이블 -->
    <table class="table">
      <thead>
        <tr>
          <th>번호</th>
          <th>카테고리</th>
          <th>이미지</th>
          <th>상품명</th>
          <th>가격</th>
          <th>재고</th>
          <th>제조사</th>
          <th>등록자</th>
          <th>조회수</th>
          <th colspan="2">관리</th>
        </tr>
      </thead>
      <tbody>
        <!-- 상품 리스트를 동적으로 출력 -->
        <tr v-for="product in list" :key="product.product_num">
          <td>{{ product.product_num }}</td>
          <td>{{ product.product_category }}</td>
          <td><img :src="getImageUrl(product.product_image)" alt="product Image" class="list-image"
              @error="handleImageError"></td>
          <td>{{ product.product_name }}</td>
          <td>{{ product.product_price }}</td>
          <td>{{ product.product_stock }}</td>
          <td>{{ product.product_manufacturer }}</td>
          <td>{{ product.product_registrant }}</td>
          <td>{{ product.product_hit }}</td>
          <td><button type="button" class="detail-button" @click.prevent="detail(product.product_num)">상세</button></td>
          <td><button type="button" class="delete-button" @click.prevent="remove(product.product_num)">삭제</button></td>
        </tr>
      </tbody>
    </table>
    <div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: "list",
  created() {
    this.fetchData() // 컴포넌트가 생성될 때 데이터 가져오기
  },
  data() {
    return {
      list: [], // 전체 상품 리스트
    }
  },
  methods: {
    fetchData() {
      axios.get(`${process.env.VUE_APP_BACK_END_URL}/product/proList`)
        .then((resp) => {

          this.list = resp.data;
        })
        .catch((err) => {
          console.error(err);
        })
    },
    // 상세 페이지 이동 메서드
    detail(product_num) {
      this.$router.push({
        name: "JimiProductDetail",
        params: { product_num: product_num }
      });

    },
    //등록 페이지 이동 메서드 
    add() {
      this.$router.push({
        name: "JimiProductAdd"
      });
    },
    handleImageError(event) {
      event.target.src = '/images/product/maketadd_default.png';
    },
    getImageUrl(product_image) {
      if (!product_image) {
        return `/images/product/maketadd_default.png`;
      }
      const imageUrl = `${process.env.VUE_APP_BACK_END_URL}/uploads/${product_image}`;
      return imageUrl;
    },
    // 삭제 메서드
    remove(product_num) {
      axios.delete(`${process.env.VUE_APP_BACK_END_URL}/product/proDelete/${product_num}`)
        .then(() => {
          this.fetchData(); // 데이터 다시 가져오기
        })
        .catch((err) => {
          console.error(err);
        });
    },
  }
};
</script>

<style>
.container {
  padding: 20px;
  padding-top: 100px;
}

.table {
  width: 100%;
  border-collapse: collapse;
}

.table th,
.table td {
  border: 1px solid #ddd;
  padding: 8px;
}

.table th {
  background-color: #f2f2f2;
  text-align: center;
}

.list-image {
  width: 100px;
  height: auto;
  object-fit: cover;
}

.detail-button {
  background-color: rgb(97, 196, 97);
  color: white;
  border: none;
  padding: 5px 10px;
  cursor: pointer;
}

.detail-button:hover {
  background-color: darkgreen;
}

.delete-button {
  background-color: salmon;
  color: white;
  border: none;
  padding: 5px 10px;
  cursor: pointer;
}

.delete-button:hover {
  background-color: darkred;
}

.add-button {
  background-color: rgb(78, 191, 206);
  color: white;
  border: none;
  padding: 5px 10px;
  cursor: pointer;
}

.add-button:hover {
  background-color: rgb(94, 190, 219);
}
</style>

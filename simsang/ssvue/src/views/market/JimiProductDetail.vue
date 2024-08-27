/*UI개발:송지미*/
<template>
  <div class="container">
    <h1>상품상세</h1>
    <table class="table">
      <thead>
        <tr>
          <th>항목</th>
          <th>내용</th>
        </tr>
      </thead>
      <tbody>
        <tr v-show="result.product_num !== undefined">
          <td>번호:</td>
          <td>
            <input type="text" class="form-control" id="product_num" name="product_num" disabled
              :value="result.product_num" style="color: green;" />
          </td>
        </tr>

        <tr v-show="result.product_category !== undefined">
          <td>카테고리:</td>
          <td>
            <input type="text" class="form-control" id="product_category" name="product_category"
              v-model="result.product_category" style="color: green;" />
          </td>
        </tr>

        <tr v-show="result.product_image !== undefined">
          <td>이미지:</td> <!-- 이미지 URL이 정의되어 있을 때만 getImageUrl 호출 -->
          <div class="image-container">
            <img :src="getImageUrl(imageSrc)" alt="product Image" class="list-image" @error="handleImageError"
              style="width: 200px; height: 200px; object-fit: cover; border: 1px solid #ddd;">
            <!-- <img v-if="imageSrc" :src="imageSrc" alt="product Image" class="list-image" @error="handleImageError"  style="width: 200px; height: 200px; object-fit: cover; border: 1px solid #ddd;"> -->
            <label for="fileInput" class="custom-file-label">파일 선택</label>
            <input type="file" id="fileInput" @change="onFileChange" class="custom-file-input" />
          </div>
          <p v-if="selectedFileName">{{ selectedFileName }}</p>
        </tr>

        <tr v-show="result.product_name !== undefined">
          <td>상품명:</td>
          <td>
            <input type="text" class="form-control" id="product_name" name="product_name" v-model="result.product_name"
              style="color: green;" />
          </td>
        </tr>

        <tr v-show="result.product_price !== undefined">
          <td>가격:</td>
          <td>
            <input type="text" class="form-control" id="product_price" name="product_price"
              v-model="result.product_price" style="color: green;" />
          </td>
        </tr>

        <tr v-show="result.product_stock !== undefined">
          <td>재고:</td>
          <td>
            <input type="text" class="form-control" id="product_stock" name="product_stock"
              v-model="result.product_stock" style="color: green;" />
          </td>
        </tr>

        <tr v-show="result.product_manufacturer !== undefined">
          <td>제조사:</td>
          <td>
            <input type="text" class="form-control" id="product_manufacturer" name="product_manufacturer"
              v-model="result.product_manufacturer" style="color: green;" />
          </td>
        </tr>

        <tr v-show="result.product_registrant !== undefined">
          <td>등록자:</td>
          <td>
            <input type="text" class="form-control" id="product_registrant" name="product_registrant"
              v-model="result.product_registrant" style="color: green;" />
          </td>
        </tr>

        <tr v-show="result.product_hit !== undefined">
          <td>조회수:</td>
          <td>
            <input type="text" class="form-control" id="product_hit" name="product_hit" disabled
              :value="result.product_hit" style="color: green;" />
          </td>
        </tr>
      </tbody>
      <tfoot>
        <tr>
          <td colspan="2">
            <button type="button" class="btn-update" @click.prevent="update">수정</button>
            <button type="button" class="btn-delete" @click.prevent="remove">삭제</button>
            <button type="button" class="btn-return" @click.prevent="goBack">목록</button>
          </td>

        </tr>
      </tfoot>
    </table>
    <!--Vue 템플릿에 업데이트 메시지를 표시하는 부분-->
    <p v-if="updateMessage" class="update-message">{{ updateMessage }}</p>
  </div>
</template>



<script>
import axios from 'axios';

export default {
  name: "JimiProductDetail",
  created() {
    this.fetchData(this.$route.params.product_num);
  },
  data() {
    return {
      result: {}, // 상품 상세 정보
      updateMessage: '', // 업데이트 메시지
      selectedFile: null, // 선택된 파일
      imageSrc: null, // 이미지 미리보기 src
      selectedFileName: '' // 선택된 파일 이름
    };
  },
  methods: {
    fetchData(product_num) {
      if (!product_num || isNaN(product_num)) {
        console.error("유효하지 않은 제품 번호:", product_num);
        return;
      }
      axios.get(`${process.env.VUE_APP_BACK_END_URL}/product/proDetail/${product_num}`)
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
    handleImageError(event) {
      event.target.src = `/images/product/maketadd_default.png`;
    },
    getImageUrl(product_image) {
      if (!product_image) {
        return `/images/product/maketadd_default.png`;
      }
      if (product_image.startsWith('http')) {
        return product_image;
      }
      const imageUrl = `${process.env.VUE_APP_BACK_END_URL}/uploads/${product_image}`;
      return imageUrl;
    }
    ,
    // 파일 변경 이벤트 핸들러
    onFileChange(e) {
      this.selectedFile = e.target.files[0];
      if (this.selectedFile) {
        this.imageSrc = URL.createObjectURL(this.selectedFile); // 선택된 파일의 미리보기 설정
        this.selectedFileName = this.selectedFile.name; // 파일 이름 설정
      } else {
        this.selectedFileName = '';
      }
    },
    // 수정 메서드
    update() {
      const formData = new FormData();
      formData.append('product', new Blob([JSON.stringify(this.result)], { type: "application/json" }));
      if (this.selectedFile) {
        formData.append('file', this.selectedFile);
      }

      axios.post(`${process.env.VUE_APP_BACK_END_URL}/product/proUpdate`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      })
        .then((resp) => {
          this.updateMessage = '상품이 성공적으로 업데이트되었습니다.';
        })
        .catch((err) => {
          this.updateMessage = '업데이트 중 오류가 발생했습니다.';
        });
    },
    remove() {
      axios.delete(`${process.env.VUE_APP_BACK_END_URL}/product/proDelete/${this.result.product_num}`)
        .then(() => {
          this.goBack();
        })
        .catch((err) => {
          console.error(err);
        });
    },

    goBack() {
      this.$router.go(-1);
    }
  }
};
</script>

<style>
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

.btn-update {
  background-color: rgb(118, 194, 118);
  color: white;
  border: none;
  padding: 5px 10px;
  margin-right: 10px;
  cursor: pointer;
}

.btn-update:hover {
  background-color: darkgreen;
}

.btn-delete {
  background-color: salmon;
  color: white;
  border: none;
  padding: 5px 10px;
  margin-right: 10px;
  cursor: pointer;
}

.btn-delete:hover {
  background-color: darkred;
}

.btn-return {
  background-color: rgb(235, 141, 230);
  color: white;
  border: none;
  padding: 5px 10px;
  cursor: pointer;
}

.btn-return:hover {
  background-color: rgb(189, 108, 192);
}

.image-container {
  text-align: center;
}

.detail-image {
  display: block;
  margin: 0 auto 10px;
  width: 200px;
  height: auto;
  object-fit: cover;
}

.image-container {
  text-align: center;
}

.custom-file-label {
  display: block;
  padding: 5px 10px;
  background-color: #b1d166;
  cursor: pointer;
  margin-top: 10px;
  color: white;
}

.custom-file-label:hover {
  background-color: #f390ca;
}

.custom-file-input {
  display: none;
}

td button {
  margin-right: 10px;
}

.custom-file-input {
  display: none;
}

.custom-file-label {
  display: inline-block;
  padding: 5px 10px;
  background-color: #b1d166;
  cursor: pointer;
  margin-top: 10px;
  color: white;
}

.custom-file-label:hover {
  background-color: #f390ca;
}

.update-message {
  color: salmon;
}
</style>
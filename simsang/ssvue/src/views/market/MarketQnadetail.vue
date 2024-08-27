/*UI개발:이승희*/
<template>
  <div class="row justify-content-center align-items-center">
    <div class="col-md-6 col-lg-4">
      <div class="text-center mt-5">
        <h1 class="display-5 mb-5">마켓 문의 상세보기</h1>
      </div>
      <div class="d-flex justify-content-center">
        <div class="p-lg-5 pe-lg-0">
          <div class="section-title text-start">
            <h2 class="display-5 mb-4">문의 등록시 유의사항</h2>
            <p class="mb-4 pb-2">
              상업적인 사진, 욕설, 음란성 글을 등록할 수 없습니다.
            </p>
          </div>
          <form>
            <div class="row g-3">
              <div class="col-12 col-sm-6" style="display: none">
                <input
                  disabled
                  type="text"
                  class="form-control border-0"
                  id="qnawriter"
                  v-model="formdata.num"
                />
              </div>
              <div class="col-12 col-sm-6" style="display: none">
                <input
                  disabled
                  type="text"
                  class="form-control border-0"
                  id="qnawriter"
                  v-model="formdata.membernum"
                />
              </div>
              <div class="col-12 col-sm-6">
                <input
                  disabled
                  type="text"
                  class="form-control border-0"
                  id="qnawriter"
                  v-model="formdata.name"
                />
              </div>
              <div class="col-12 col-sm-6">
                <input
                  disabled
                  type="date"
                  class="form-control border-0"
                  id="qnawdate"
                  v-model="formdata.qnaquestiondate"
                />
              </div>
              <div class="col-12 col-sm-6">
                <select
                  class="form-select border-0"
                  id="qnacategory"
                  :value="formdata.category"
                  disabled
                >
                  <option
                    value="배송 문의"
                    :selected="formdata.category === '배송 문의'"
                  >
                    배송 문의
                  </option>
                  <option
                    value="상품 문의"
                    :selected="formdata.category === '상품 문의'"
                  >
                    상품 문의
                  </option>
                  <option
                    value="결제 문의"
                    :selected="formdata.category === '결제 문의'"
                  >
                    결제 문의
                  </option>
                  <option
                    value="기타 문의"
                    :selected="formdata.category === '기타 문의'"
                  >
                    기타 문의
                  </option>
                </select>
              </div>
              <div class="col-12">
                <input
                  disabled
                  type="text"
                  class="form-control border-0"
                  id="qnatitle"
                  v-model="formdata.title"
                />
              </div>
              <div class="col-12">
                <textarea
                  disabled
                  class="form-control border-0"
                  style="height: 200px"
                  id="qnacontent"
                  v-model="formdata.content"
                ></textarea>
              </div>
              <div class="col-12">
                <img
                  :src="image"
                  v-if="formdata.imgname !== 'default.png'"
                  class="img-thumbnail"
                  alt="첨부파일"
                />
              </div>
              <div class="col-12 col-sm-8">
                <button
                  class="btn w-100 py-3 simulationIntro3"
                  type="button"
                  @click="goMarketQnaUpdate(formdata.num)"
                >
                  수정하기
                </button>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      formdata: {
        num: "", // 문의 번호
        membernum: localStorage.getItem("membernum"), // 회원 번호
        name: "", // 작성자
        qnaquestiondate: "", // 작성일
        category: "", // 카테고리
        title: "", // 제목
        content: "", // 문의 내용
        imgname: "", // 이미지 이름
        file: "", // 첨부파일
      },
      image: "", // 이미지 경로
    };
  },
  created() {
    this.fetchMarketQna();
  },
  methods: {
    fetchMarketQna() {
      const num = this.$route.params.num;
      axios
        .get(`${process.env.VUE_APP_BACK_END_URL}/uploads/${num}`)
        .then((response) => {
          this.formdata = response.data;
          if (this.formdata.imgname) {
            this.image = `${process.env.VUE_APP_BACK_END_URL}/uploads/${this.formdata.imgname}`;
          }
        })
        .catch((error) => {
          console.error(error);
        });
    },
    goMarketQnaList() {
      this.$router.push({ name: "MarketQnalist" });
    },
    handleFileUpload(event) {
      const file = event.target.files[0];
      if (file) {
        this.formdata.file = URL.createObjectURL(file);
      }
    },
    goMarketQnaUpdate(num) {
      this.$router.push({ path: `/MarketQnaup/${num}` }); 
    },
  },
};
</script>

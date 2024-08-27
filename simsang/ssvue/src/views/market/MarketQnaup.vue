/*UI개발:이승희*/
<template>
  <div class="container">
    <div class="contact_area">
      <div class="text-center w-100">
        <h1 class="display-5 mb-5">마켓 문의 수정하기</h1>
      </div>
      <div class="d-flex justify-content-center">
        <div class="p-lg-5 pe-lg-0" style="width: 800px">
          <div class="section-title text-start">
            <h2 class="display-5 mb-4">문의 수정 시 유의사항</h2>
            <p class="mb-4 pb-2">
              (⊙_(⊙_⊙)_⊙) 클린봇이 상업적인 사진, 욕설, 음란성 문구를 AI 기술로
              감지하고 있습니다.
            </p>
          </div>
          <form @submit.prevent="update">
            <div class="row g-3">
              <div class="col-12 col-sm-6" style="display: none">
                <input
                  type="text"
                  class="form-control border-0"
                  id="qnanum"
                  v-model="formdata.num"
                />
              </div>
              <div class="col-12 col-sm-6">
                <input
                  type="text"
                  class="form-control border-0"
                  id="qnawriter"
                  v-model="formdata.name"
                />
              </div>
              <div class="col-12 col-sm-6">
                <input
                  readonly
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
                  v-model="formdata.category"
                >
                  <option selected>카테고리</option>
                  <option value="배송 문의">배송 문의</option>
                  <option value="상품 문의">상품 문의</option>
                  <option value="결제 문의">결제 문의</option>
                </select>
              </div>
              <div class="col-12">
                <input
                  type="text"
                  class="form-control border-0"
                  id="qnatitle"
                  v-model="formdata.title"
                />
              </div>
              <div class="col-12">
                <textarea
                  class="form-control border-0"
                  style="height: 200px"
                  id="qnacontent"
                  v-model="formdata.content"
                ></textarea>
              </div>
              <div class="col-12">
                <label for="qnafile" class="form-label">첨부파일</label>
                <input
                  @change="onFileChange"
                  class="form-control"
                  type="file"
                  id="qnafile"
                  ref="image"
                />
                <div v-if="imagePreview" class="mt-3" style="object-fit: cover">
                  <img :src="imagePreview" alt="img" class="img-thumbnail" />
                </div>
              </div>
              <div class="col-12 col-sm-6">
                <button class="btn w-100 py-3 simulationIntro3" type="submit">
                  수정하기
                </button>
              </div>
              <div class="col-12 col-sm-6">
                <button
                  class="btn w-100 py-3 simulationIntro3"
                  @click="goMarketQnaList"
                >
                  목록으로
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
        num: "",
        name: "",
        qnaquestiondate: "",
        category: "",
        title: "",
        content: "",
        file: "",
      },
      image: null,
      imagePreview: null, // 이미지 미리보기 URL을 저장하는 변수
      imgdata: "",
      file: "",
    };
  },
  computed: {
    isImgDataValid() {
      try {
        const imgDataParsed = JSON.parse(this.imgdata);
        return imgDataParsed["spam"] === 0;
      } catch (e) {
        return false;
      }
    },
  },
  created() {
    this.fetchQna();
  },
  methods: {
    fetchQna() {
      const num = this.$route.params.num; // URL 파라미터에서 num 값 가져오기
      if (!num) {
        console.error("No num parameter");
        return;
      }
      axios
        .get(`${process.env.VUE_APP_BACK_END_URL}/marketqna/qnadetail/${num}`)
        .then((response) => {
          this.formdata = response.data;
        })
        .catch((error) => {
          console.error(error);
        });
    },
    onFileChange(e) {
      const file = e.target.files[0];
      this.image = file;
      if (file) {
        this.imagePreview = URL.createObjectURL(file);
      }
    },
    update() {
      const formData = new FormData();
      formData.append("qna", JSON.stringify(this.formdata));
      if (this.image) {
        formData.append("file", this.image);
      }

      axios
        .post(
          `${process.env.VUE_APP_BACK_END_URL}/marketqna/qnaupdate`,
          formData,
          {
            headers: {
              "Content-Type": "multipart/form-data",
            },
          }
        )
        .then(() => {
          alert("문의가 성공적으로 수정되었습니다.");
          this.goBack();
        })
        .catch((error) => {
          console.error(error);
          alert("문의 수정 중 오류가 발생했습니다.");
        });
    },
    goMarketQnaList() {
      this.$router.push({ name: "MarketQnalist" });
    }, goBack() {
      window.scrollTo(0, 0); // Scroll to the top of the page
      this.$router.go(-1); // Navigate back
    },
  },
};
</script>

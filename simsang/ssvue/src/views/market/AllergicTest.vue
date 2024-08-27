/*UI개발:이지영*/
<template>
  <!-- footer Section -->
  <footer id="join" class="join">
    <div class="container">
      <div class="row_a">
        <div class="col-sm-5 col-sm-offset-7 col-xs-10 col-xs-offset-2">
          <div class="contact_area wow slideInLeft" data-wow-duration="2s">
            <div class="head_title text-center" style="margin-top: 60px">
              <h3 style="color: dimgrey">식품 알러지 정보 확인</h3>
              <hr style="border-color: gray" />
              <p style="text-align: right; margin: 30px 0px">
                <span class="red">*</span> 알러지 정보를 추가해주세요
              </p>
              <!-- 종류 입력 폼 -->
              <div
                class="row_a"
                v-for="(input, index) in inputs"
                :key="index"
                style="margin-bottom: 30px"
              >
                <label :for="'inputID_' + index" class="col-xs-3">
                  종류 {{ index + 1 }}<span class="red">*</span>
                </label>
                <div class="col-xs-6">
                  <select
                    :id="'inputID_' + index"
                    v-model="inputs[index].value"
                    class="form-control"
                    @keydown.enter.prevent="handleEnter"
                  >
                    <option disabled value="">종류를 선택하세요.</option>
                    <option
                      v-for="(allergen, i) in allergens"
                      :key="i"
                      :value="allergen.value"
                    >
                      {{ allergen.label }}
                    </option>
                  </select>
                </div>
                <div class="col-xs-3">
                  <button
                    style="margin-right: 5%"
                    v-if="index > 0"
                    type="button"
                    @click="removeInput(index)"
                    class="btnfont btn2 small"
                  >
                    삭제
                  </button>
                  <button type="button" @click="addInput" class="btnfont btn2 small">
                    추가
                  </button>
                </div>
              </div>

              <!-- OCR 결과 표시 -->
              <div v-if="ocrResult && ocrResult.warning_message">
                <h5>상품에 표기된 알러지 정보</h5>
                <p>{{ ocrResult.warning_message }}</p>
              </div>

              <!-- 이미지 업로드 및 미리보기 -->
                <div class="main_contact_content">
                  <div class="contact_form_area">
                    <form name="userid" id="userid" method="post" enctype="multipart/form-data">
                      <div class="box">
                        <div class="file-upload" v-if="!idpop">
                          <label for="file-upload">
                            <p style="font-weight: bold">제품정보 등록하기</p>
                            <input
                              type="file"
                              id="file-upload"
                              name="file-upload"
                              accept="image/*"
                              ref="fileInput"
                              @change="handleFileUpload"
                            />
                          </label>
                        </div>

                      <!-- OCR 결과 미리보기 -->
                      <div v-if="idpop">
                        <template v-if="previewImageUrl">
                          <div v-if="showWarning">
                            <img :src="previewImageUrl" class="imgCircle" />
                            <p style="font-weight: bold; color: red">
                              {{ ocrResult.warning_message }}
                            </p>
                          </div>
                          <div v-else>
                            <img :src="previewImageUrl" class="imgCircle" />
                            <p v-if="isBuying">이미지를 분석 중입니다...</p>
                          </div>
                        </template>
                      </div>
                      <!-- OCR 결과 미리보기 끝 -->
                    </div>
                  </form>
                </div>
              </div>

              <!-- 알러지 안내 문구 -->
              <div v-if="ocrResult">
                <p
                  v-if="
                    ocrResult.warning_message ===
                    '알레르기 유발물질에 대한 정보가 없습니다.'
                  "
                  style="font-weight: bold; color: red"
                >
                  <span>사진을 정확하게 인식시켜주세요.</span>
                </p>
                <p
                  v-else-if="!showWarning"
                  style="font-weight: bold; color: green"
                >
                  <span>선택한 재료는 알러지 주의 문구가 뜨지 않고,</span><br />
                  <span>안심하고 드셔도 됩니다.</span>
                </p>

                <div class="file-upload" style="margin-top: 30px">
                  <label for="file-upload">
                    <p style="font-weight: bold">이미지 다시 등록하기</p>
                    <input
                      type="file"
                      id="file-upload"
                      accept="image/*"
                      ref="fileInput"
                      @change="handleFileUpload"
                    />
                  </label>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </footer>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      formData: new FormData(),
      inputs: [{ value: "" }],
      idpop: false,
      ocrResult: null,
      allergens: [
        { label: "우유", value: "우유" },
        { label: "메밀", value: "메밀" },
        { label: "땅콩", value: "땅콩" },
        { label: "대두", value: "대두" },
        { label: "밀", value: "밀" },
        { label: "고등어", value: "고등어" },
        { label: "게", value: "게" },
        { label: "새우", value: "새우" },
        { label: "돼지고기", value: "돼지고기" },
        { label: "아황산류", value: "아황산류" },
        { label: "복숭아", value: "복숭아" },
        { label: "토마토", value: "토마토" },
        { label: "호두", value: "호두" },
        { label: "닭고기", value: "닭고기" },
        { label: "알류", value: "알류" },
        { label: "쇠고기", value: "쇠고기" },
        { label: "오징어", value: "오징어" },
        { label: "조개류", value: "조개류" },
        { label: "잣", value: "잣" },
      ],
      selectedIngredients: [],
      showWarning: false,
      isBuying: false,
      previewImageUrl: "",
    };
  },
  methods: {
    addInput() {
      this.inputs.push({ value: "" });
    },
    removeInput(index) {
      this.inputs.splice(index, 1);
    },
    handleEnter() {
      // Handle enter key press event
    },
    handleFileUpload(event) {
      // Check if any input is empty
      const anyEmpty = this.inputs.every(input => input.value === "");
      if (anyEmpty) {
        alert("알러지 종류를 하나 이상 선택해주세요.");
        event.preventDefault(); // Prevent file upload event
        this.$refs.fileInput.value = ""; // Reset file input
        return;
      }

      // File upload handling
      this.isBuying = true;
      this.ocrResult = "";
      this.showWarning = false;
      const file = event.target.files[0];
      this.formData = new FormData(); // Ensure formData is initialized correctly
      this.formData.append("file-upload", file); // Append the file with the correct key name

      // Read and preview the file
      const reader = new FileReader();
      reader.onload = () => {
        this.previewImageUrl = reader.result;
        this.uploadImage(); // Request OCR result
      };
      reader.readAsDataURL(file);
    },
    uploadImage() {
      this.idpop = true;

      // OCR result request
      axios.post(`http://192.168.92:9000/djgmarket/ocr_process`, this.formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      })
      .then(res => {
        alert("이미지 분석 완료!");
        this.ocrResult = res.data;
        this.isBuying = false;

        // Check selected ingredients for warning messages
        this.selectedIngredients = this.inputs.map(input => input.value);

        // Check if any selected ingredient is included in the warning message
        this.showWarning = this.selectedIngredients.some(ingredient =>
          this.ocrResult.warning_message.includes(ingredient)
        );

        // Update preview image based on OCR result
        if (this.showWarning) {
          this.previewImageUrl = "/images/comfortable.png";
        } else if (this.ocrResult.warning_message === "알레르기 유발물질에 대한 정보가 없습니다.") {
          this.previewImageUrl = "/images/aithinking.jpg";
        } else {
          this.previewImageUrl = "/images/uncomfortable.png";
        }
      })
      .catch(error => {
        console.error("OCR API 호출 에러:", error);
      });
    },
    reloadPage() {
      // Ensure this code executes only in a client-side context
      this.showWarning = false;
    },
  },
};

</script>

<style>
.box {
  border-radius: 10px;
  background-color: rgba(230, 218, 202, 0.562);
  padding: 30px 80px;
  margin: 10px 30px;
}
.form-control {
  font-family: "NotoSansKR";
  height: 45px;
  border: 1px solid gray;
  border-radius: 5px;
  background-color: rgb(235, 230, 230);
}
.form-control:focus {
  border: 1px solid rgb(233, 101, 53);
}

.red {
  color: red;
}

h2 {
  font-size: 40px;
  line-height: 38px;
  font-weight: 600;
  font-family: "NotoSansKR";
  margin: 0;
}

p {
  font: normal 16px "NotoSansKR", arial;
}

.btnfont {
  font: normal 16px "NotoSansKR", arial;
  padding: 7%;
  border-radius: 5px;
  border: none;
  background-color: #a0a0a0;
  color: white;
}

label,
a {
  font: bold 16px "NotoSansKR", arial;
  font-weight: bold;
}

.imgCircle {
  margin: 30px auto;
  width: auto;
  height: auto;
}

.row_a {
  margin: 0px 0px 10px 0px;
  height: 100%;
  display: flex;
  align-items: center;
}

.col-xs-3 {
  padding: 0px;
}

.file-upload {
  text-align: center;
}

.file-upload label {
  display: inline-block;
  padding: 13px 20px;
  background-color: rgb(233, 101, 53);
  border: none;
  color: white;
  border-radius: 5px;
  cursor: pointer;
  width: 80%;
  box-shadow: rgba(230, 218, 202, 0.862);
}

.file-upload button {
  font: bold 16px "NotoSansKR", arial;
  display: inline-block;
  padding: 13px 20px;
  background-color: rgb(233, 101, 53);
  border: none;
  color: white;
  border-radius: 5px;
  cursor: pointer;
  width: 80%;
  box-shadow: rgba(230, 218, 202, 0.862);
}

.file-upload input[type="file"] {
  display: none;
}
</style>

/*UI개발: 박성호 */
<template>
  <!-- 기존 템플릿 코드 -->
  <div class="turnjsMain">
    <div style="margin-top: 60px; text-align: center; margin-bottom: 20px">
      <h1>나의 마음 책</h1>
    </div>
    <div class="turnjsViews">
      <div id="book">
        <!-- 첫 번째 페이지 (책의 표지) -->
        <div class="first_cover" style="background-image: url('images/background_diary.jpg')">
          <div class="first_cover_title" style="
              text-align: center;
              margin-top: 300px;
              color: #b0f7ff;
              text-shadow: 1px 1px 1px #00008c;
            ">
            <h1>나의 일기장</h1>
          </div>
        </div>

        <!-- turn.js 페이지 생성 -->
        <div v-for="(entry, index) in diaryEntries" :key="index" class="page"
          :style="{ backgroundImage: 'url(images/background_paper.jpg)' }">
          <div class="page_content">
            <p style="height: 80px; font-weight: bold; font-size: 26px">
              {{ entry.title }}
            </p>
            <p style="height: 200px; font-weight: bold; font-size: 20px">
              {{ entry.content }} <br> {{ entry.emotion.split('_').join(' ') }}
            </p>
            <!-- 이미지 표시 -->
            <p style="height: 200px; font-weight: bold">
              <img :src="images[entry.diaryimg] ? images[entry.diaryimg] : ''" style="border-radius: 15px"
                class="diary-image" v-if="images[entry.diaryimg]" />
            </p>
            <p style="height: 50px; padding-top: 20px; font-weight: bold">
              {{ formatDate(entry.diarydate) }}
            </p>
            <!-- 수정 및 삭제 버튼 -->
            <div class="button-container">
              <button @click="updateDiary(entry.num)">수정</button>
              <button @click="deleteDiary(entry.num, index)">삭제</button>
            </div>
          </div>
        </div>

        <!-- 마지막 페이지 (책의 뒷면 표지) -->
        <div class="first_cover" style="background-image: url('images/background_diary.jpg')">
          <div class="first_cover_title" style="
              text-align: center;
              margin-top: 300px;
              color: #b0f7ff;
              text-shadow: 1px 1px 1px #00008c;
            ">
            <h1>끝</h1>
          </div>
          <div class="cover"></div>
        </div>
      </div>

      <!-- 페이지 번호 입력 및 제어 UI -->
      <div id="controls">
        <label for="page-number">Page:</label>
        <input type="text" size="3" id="page-number" v-model="currentPage" @input="changePage" />
        of <span id="number-pages">{{ numberOfPages }}</span>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import $ from "jquery";

export default {
  name: "MyPageTurn",
  data() {
    return {
      diaryEntries: [], // 백엔드에서 가져올 일기 목록
      currentPage: 1, // 현재 페이지
      numberOfPages: 0, // 전체 페이지 수
      membernum: localStorage.getItem("membernum"), // 로그인한 사용자의 회원 번호
      diarypk: 0,
      images: {}, // 이미지 데이터를 저장할 객체
    };
  },
  mounted() {
    this.fetchDiaryEntries(); // membernum을 사용하여 일기 목록을 가져옵니다.
  },
  methods: {
   fetchDiaryEntries() {
  axios
    .get(`${process.env.VUE_APP_BACK_END_URL}/diary/diaryList/${this.membernum}`)
    .then((response) => {
      // 일기 목록을 역순으로 정렬
      this.diaryEntries = response.data.reverse();
      this.numberOfPages = this.diaryEntries.length + 2; // 페이지 수 설정

      // 각 일기에 대한 이미지를 가져옵니다.
      this.diaryEntries.forEach((entry) => {
        if (entry.diaryimg) {
          this.fetchDiaryImage(entry.diaryimg);
        }
      });

      // DOM이 완전히 렌더링된 후 turn.js 초기화
      this.$nextTick(() => {
        this.loadTurnJsScript(() => {
          this.initializeTurnJs();
        });
      });
    })
    .catch((error) => {
      console.error("Error fetching diary entries:", error);
    });
}
,
    fetchDiaryImage(diaryimg) {
      if (diaryimg) {
        axios
          .get(`${process.env.VUE_APP_BACK_END_URL}/uploads/${diaryimg}`, {
            responseType: "blob",
          })
          .then((response) => {
            const reader = new FileReader();
            reader.readAsDataURL(response.data);
            reader.onloadend = () => {
              this.images[diaryimg] = reader.result;
            };
          })
          .catch((error) => {
            console.error("Error fetching diary image:", error);
          });
      }
    },



    loadTurnJsScript(callback) {
      const script = document.createElement("script");
      script.src = "/turn.js";
      script.onload = callback;
      document.head.appendChild(script);
    },
    initializeTurnJs() {
      if (typeof $.fn.turn === "function") {
        $("#book").turn({
          width: 1200,
          height: 700,
          autoCenter: true,
          gradients: true,
          acceleration: true,
          when: {
            turned: (event, page) => {
              this.currentPage = page;
            },
          },
        });
      } else {
        console.error("Turn.js is not loaded properly.");
      }
    },
    changePage() {
      $("#book").turn("page", this.currentPage);
    },
    updateDiary(entry) {
      // 일기 수정 기능 구현 로직 (새로운 페이지로 이동하거나 모달을 띄워서 수정할 수 있도록 함)
      this.$router.push({ name: "DiaryAdd", query: { diary: entry } });
    },

    deleteDiary(num, index) {
      // 일기 삭제 기능 구현 로직
      if (confirm("이 일기를 삭제하시겠습니까?")) {
        axios
          .delete(
            `${process.env.VUE_APP_BACK_END_URL}/diary/diaryDelete/${num}`
          )
          .then((response) => {
            alert("일기가 삭제되었습니다.");
            this.diaryEntries.splice(index, 1); // 삭제된 항목을 배열에서 제거
            this.numberOfPages -= 1; // 전체 페이지 수 업데이트
            this.$nextTick(() => {
              $("#book").turn("page", this.currentPage);
            });
          })
          .catch((error) => {
            console.error("Error deleting diary entry:", error);
          });
      }
    },
    formatDate(dateString) {
      const options = { year: 'numeric', month: 'long', day: 'numeric', weekday: 'long' };
      const date = new Date(dateString);
      return date.toLocaleDateString('ko-KR', options);
    },
  },
  watch: {
    currentPage(newPage) {
      $("#book").turn("page", newPage);
    },
  },
};
</script>

<style scoped>
.turnjsMain {
  display: flex;
  flex-direction: column;
}

.turnjsViews {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  margin-bottom: 5%;
}

#book {
  position: relative;
  box-shadow: 5px 5px 10px gray;
}

.cover {
  background-color: rgb(155, 154, 154);
  display: flex;
  justify-content: center;
  align-items: center;
}

.page {
  display: flex;
  justify-content: center;
  align-items: center;
}

#controls {
  position: absolute;
  bottom: 20px;
  left: 50%;
  transform: translateX(-50%);
  background-color: rgba(255, 255, 255, 0.8);
  padding: 10px;
  border-radius: 5px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
}

input[type="text"] {
  text-align: center;
}

.page_content {
  padding: 30px;
  text-align: center;
}

div {
  cursor: pointer;
}

div:hover .highlight:after {
  width: 100%;
}

.highlight {
  display: inline-block;
  position: relative;
}

.highlight:after {
  content: "";
  width: 0;
  height: 10px;
  display: inline-block;
  background: #d9fcdb;
  position: absolute;
  bottom: 0;
  left: 0;
  z-index: -1;
  transition: 0.2s all;
}

.button-container {
  margin-top: 20px;
  display: flex;
  justify-content: center;
  gap: 10px;
}

.button-container button {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  background-color: #007bff;
  color: white;
  font-weight: bold;
  transition: background-color 0.3s;
}

.button-container button:hover {
  background-color: #0056b3;
}

.diary-image {
  max-width: 100%;
  max-height: 200px;
  object-fit: contain;
}
</style>

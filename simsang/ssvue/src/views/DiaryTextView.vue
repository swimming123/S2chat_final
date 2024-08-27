/*UI개발: 김건우 */
<template>
  <div>
    <div class="main">
      <div class="container-xxl py-5">
        <div class="container">
          <div class="position-relative overflow-hidden">
            <div class="section-title text-center">
              <div class="mainb" :class="{ 'rotate': isRotating }">
                <div class="position-relative overflow-hidden"></div>
                <div class="section-title text-center" style="width : 800px;">
                  <div class="d-flex justify-content-center">
                    <transition name="fade-slide-up">
                      <ul v-show="showPills" class="nav nav-pills mb-3" id="pills-tab" role="tablist">
                        <li class="nav-item" role="presentation">
                          <button class="simulationIntro3" id="v-pills-heart-tab" type="button"
                            @click="rotateToPage('DiaryTalkView')">말하기</button>
                        </li>
                      </ul>
                    </transition>
                  </div>
                  <div class="diaryTextView_content-wrapper">
                    <div class="diaryTextView_app">
                      <h1>감정일기장</h1>
                      <!-- 오늘의 감정 선택 영역 -->
                      <div v-if="currentPage === 1" class="diaryTextView_emotion-cards">
                        <label class="diaryTextView_centered-label">오늘의 감정 선택</label>
                        <div class="diaryTextView_cards">
                          <!-- 감정 카드 -->
                          <div v-for="(card, index) in emotionCards" :key="index" class="diaryTextView_card" :class="{
                            selected: isSelectedEmotion(card),
                            [getEmotionColorClass(card.title)]: isSelectedEmotion(card),
                          }" @click="toggleEmotion(card)">
                            <p>{{ card.title }}</p>
                          </div>
                        </div>
                        <div class="diaryTextView_button-container">
                          <button class="diaryTextView_simulationIntro3" @click="nextPage">다음</button>
                        </div>
                      </div>

                      <!-- 오늘의 일기 제목 영역 -->
                      <div v-if="currentPage === 2" class="diaryTextView_entry-form">
                        <label for="title" class="diaryTextView_centered-label">오늘의 일기 제목</label>
                        <input type="text" v-model="newEntry.title" id="title" @input="hidePills" />
                        <div class="diaryTextView_button-container">
                          <button class="diaryTextView_simulationIntro3" @click="prevPage">이전</button>
                          <button class="diaryTextView_simulationIntro3" @click="nextPage">다음</button>
                        </div>
                      </div>

                      <!-- 오늘의 찰칵 페이지 추가 -->
                      <div v-if="currentPage === 3" class="diaryTextView_entry-form">
                        <label for="file" class="diaryTextView_centered-label">오늘의 찰칵</label>
                        <input type="file" id="file" ref="image" @change="previewFile" />
                        <div v-if="filePreview" class="diaryTextView_image-preview">
                          <img :src="filePreview" alt="미리보기" />
                        </div>
                        <div class="diaryTextView_button-container">
                          <button class="diaryTextView_simulationIntro3" @click="prevPage">이전</button>
                          <button class="diaryTextView_simulationIntro3" @click="nextPage">다음</button>
                        </div>
                      </div>

                      <!-- 오늘을 기록하면서 하루를 정리해봐 영역 -->
                      <div v-if="currentPage === 4" class="diaryTextView_entry-form">
                        <label for="content" class="diaryTextView_centered-label">오늘의 한 줄 평</label>
                        <textarea v-model="newEntry.content" id="content" rows="6" @input="hidePills"
                          class="diaryTextView_textarea"></textarea>
                        <div class="diaryTextView_button-container">
                          <button class="diaryTextView_simulationIntro3" @click="prevPage">이전</button>
                          <button class="diaryTextView_simulationIntro3" @click="addEntry">저장</button>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  data() {
    return {
      currentPage: 1,
      newEntry: {
        membernum: localStorage.getItem("membernum"),
        emotion: '',
        week: '',
        title: '',
        content: '',
        diaryimg: ''
      },
      selectedEmotions: [],
      selectedColor: '#ffffff',
      emotionCards: [
        { title: '행복하다' },
        { title: '신난다' },
        { title: '설렌다' },
        { title: '화난다' },
        { title: '속상하다' },
        { title: '짜증난다' },
        { title: '피곤하다' },
        { title: '후회된다' },
        { title: '억울하다' },
        { title: '외롭다' },
        { title: '슬프다' },
        { title: '상처받았다' },
        { title: '긴장된다' },
        { title: '미안하다' },
        { title: '놀랐다' },
        { title: '걱정된다' },
        { title: '불안하다' },
        { title: '고맙다' },
        { title: '반갑다' },
        { title: '뿌듯하다' },
        { title: '만족스럽다' },
        { title: '감동했다' },
        { title: '심심하다' },
        { title: '지친다' },
        { title: '귀찮다' },
      ],
      isRotating: false, // 회전 애니메이션 제어를 위한 상태
      showPills: true, // Pills의 가시성을 제어하는 상태
      image: '',
      filePreview: null, // 이미지 미리보기 URL을 저장하는 변수
      file: "",
      emotions: [],
    };
  },
  methods: {
    // 현재 페이지를 다음 페이지로 변경
    nextPage() {
      if (this.currentPage < 4) {
        this.currentPage += 1;
      }
    },
    // 현재 페이지를 이전 페이지로 변경
    prevPage() {
      if (this.currentPage > 1) {
        this.currentPage -= 1;
      }
    },
    // 새로운 일기 항목 추가
    addEntry() {
      this.newEntry.membernum = localStorage.getItem("membernum");
      this.newEntry.week = 0;
      this.newEntry.emotion = this.emotions.join('_');

      const formData = new FormData();
      formData.append('newEntry', JSON.stringify(this.newEntry));
      if (this.image) {
        formData.append('diaryimg', this.image);
      }

      axios.post(`${process.env.VUE_APP_BACK_END_URL}/diary/diaryUpdate`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      })
        .then(response => {
          this.$router.push({ name: 'MyPageTurn' });
        })
        .catch(error => {
        });
    },

    // 감정 선택 여부 확인
    isSelectedEmotion(emotion) {
      return this.emotions.includes(emotion.title);
    },
    // 감정 토글
    // toggleEmotion(emotion) {
    //   const index = this.selectedEmotions.indexOf(emotion);
    //   const removeEmotion = (emotionToRemove) => {
    //     this.newEntry.emotion = this.newEntry.emotion.filter(emotion => emotion !== emotionToRemove);
    //   };
    //   if (index === -1) {
    //     this.emotions.push(emotion.title);
    //     this.emotions.push(emotion);
    //   } else {
    //     this.selectedEmotions.splice(index, 1);
    //     removeEmotion(emotion.title);
    //   }
    // },
    toggleEmotion(emotion) {
      const index = this.emotions.indexOf(emotion.title);

      if (index === -1) {
        // 감정이 아직 선택되지 않은 경우 추가
        this.emotions.push(emotion.title);
      } else {
        // 감정이 이미 선택된 경우 제거
        this.emotions.splice(index, 1);
      }
    },

    // 감정 카드의 색상 클래스 설정
    getEmotionColorClass(emotionTitle) {
      if (emotionTitle === '행복하다') return 'happy';
      if (emotionTitle === '화난다') return 'angry';
      if (emotionTitle === '외롭다') return 'lonely';
      if (emotionTitle === '감동했다') return 'touched';
      return '';
    },

    // 파일 미리보기
    previewFile(event) {
      this.file = event.target.files[0];
      this.image = event.target.files[0];
      if (this.file && this.file.size > 0) {
        const reader = new FileReader();
        reader.onload = (e) => {
          this.filePreview = e.target.result;
        };
        reader.readAsDataURL(this.file);
      } else {
        console.error('파일이 선택되지 않았습니다');
      }
    },
  },
};
</script>

<style scoped>
.mainb {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
}

.rotate {
  transition: transform 0.5s;
  transform: rotateY(90deg);
}

.diaryTextView_content-wrapper {
  width: 80%;
  max-width: 800px;
  margin: 0 auto;
}

.diaryTextView_app {
  text-align: center;
}

.diaryTextView_emotion-cards {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.diaryTextView_cards {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  margin-top: 20px;
}

.diaryTextView_card {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100px;
  height: 100px;
  margin: 10px;
  background-color: #f0f0f0;
  border-radius: 10px;
  cursor: pointer;
  transition: transform 0.3s, box-shadow 0.3s, background-color 0.3s;
}

.diaryTextView_card p {
  margin: 0;
}

.diaryTextView_card.selected {
  transform: scale(1.1);
  background-color: #ddd;
  box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
}

.diaryTextView_card.selected.happy {
  background-color: #ffeb3b;
  box-shadow: 0 0 20px yellow;
}

.diaryTextView_card.selected.angry {
  background-color: #f44336;
  box-shadow: 0 0 20px red;
}

.diaryTextView_card.selected.lonely {
  background-color: #9e9e9e;
  box-shadow: 0 0 20px gray;
}

.diaryTextView_card.selected.touched {
  background: linear-gradient(to right, red, orange, yellow, green, blue, indigo, violet);
  box-shadow: 0 0 20px rgba(255, 0, 255, 0.5);
}

.diaryTextView_entry-form {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 20px;
}

.diaryTextView_entry-form input,
.diaryTextView_entry-form textarea {
  width: 100%;
  max-width: 500px;
  padding: 10px;
  margin-bottom: 20px;
  border-radius: 5px;
  border: 1px solid #ccc;
}

.diaryTextView_entry-form input {
  height: 50px;
}

.diaryTextView_textarea {
  background-color: #fdfdfd;
  background-image: linear-gradient(to bottom, #cfcfcf 1px, transparent 1px);
  background-size: 100% 24px;
  line-height: 24px;
  padding-left: 10px;
  height: 500px;
  font-family: 'Courier New', Courier, monospace;
}

.diaryTextView_button-container {
  display: flex;
  justify-content: space-between;
  width: 100%;
  max-width: 500px;
}

.diaryTextView_simulationIntro3 {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  background-color: #007bff;
  color: white;
  cursor: pointer;
  transition: background-color 0.3s;
  margin: auto;
}

.diaryTextView_simulationIntro3:hover {
  background-color: #0056b3;
}

.fade-slide-up-enter-active,
.fade-slide-up-leave-active {
  transition: opacity 0.5s, transform 0.5s;
}

.fade-slide-up-enter,
.fade-slide-up-leave-to

/* .fade-slide-up-leave-active in <2.1.8
  {
  opacity: 0;
  transform: translateY(20px);
}
 */
.diaryTextView_image-preview {
  margin: 20px 0;
  text-align: center;
}

.diaryTextView_image-preview img {
  max-width: 100%;
  height: auto;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.container {
  margin-top: -100px;
}

.diaryTextView_image-preview {
  margin: 20px 0;
  text-align: center;
}

.diaryTextView_image-preview img {
  max-width: 100%;
  height: auto;
  border: 1px solid #ccc;
  border-radius: 5px;
}
</style>

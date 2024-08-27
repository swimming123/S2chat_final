/*UI개발: 김건우 */
<template>
  <div>
    <div class="main">
      <div class="position-relative overflow-hidden"></div>
      <div class="section-title text-center">
        <div class="d-flex justify-content-center">
        <ul class="nav nav-pills mb-3" id="pills-tab" role="tablist">
        <li class="nav-item" role="presentation">
        <button class="simulationIntro3" id="v-pills-heart-tab" type="button" @click="DiaryTalkView_Page">말하기</button>
        </li>
        <li class="nav-item" role="presentation">
        <button class="simulationIntro3" id="v-pills-heart-tab" type="button" @click="DiaryTextView_Page">글로쓰기</button>
        </li>
        </ul>
        </div>
        <div class="diaryTextView_content-wrapper">
          <div class="diaryTextView_app">
            <h1>감정일기장</h1>
            <div class="diaryTextView_chatbot-container">
              <div class="diaryTextView_message-list" ref="messageList">
                <div
                  v-for="(message, index) in messages"
                  :key="index"
                  :class="{'diaryTextView_message': true, 'diaryTextView_user': message.type === 'user', 'diaryTextView_bot': message.type === 'bot'}"
                >
                  {{ message.text }}
                  <div class="diaryTextView_message-time">{{ message.time }}</div>
                </div>
              </div>
              <transition name="diaryTextView_fade">
                <div v-if="!isCompleted && !hideInput" class="diaryTextView_input-area">
                  <div v-if="showKeywords" class="diaryTextView_keywords">
                    <button
                      v-for="(keyword, index) in keywords"
                      :key="index"
                      @click="selectKeyword(keyword)"
                      class="diaryTalkView_simulationIntro3"
                    >
                      {{ keyword }}
                    </button>
                  </div>
                  <div v-else class="diaryTextView_input-wrapper">
                    <input
                      v-model="newMessage"
                      @keyup.enter="handleButtonClick"
                      class="diaryTextView_input-box"
                      placeholder="여기에 감정을 입력하세요..."
                    />
                    <button @click="handleButtonClick" class="diaryTalkView_simulationIntro3">{{ buttonText }}</button>
                  </div>
                </div>
              </transition>
            </div>
          </div>

          <div class="diaryTextView_right-section">
            <h1>작년에 오늘은 이랬어</h1>
            <div>
              <label class="diaryTextView_centered-label_two">오늘의 감정 선택</label>
              <div
                class="diaryTextView_card selected diaryTextView_emotion-highlight"
              >
                <p>놀랐다</p>
              </div>
            </div>
            <div class="diaryTextView_color-picker">
              <label class="diaryTextView_centered-label">오늘을 색으로 표현해줘</label>
              <div class="diaryTextView_color-circle-two" style="background-color: yellow;"></div>
            </div>
            <div class="diaryTextView_entry-form">
              <label for="title" class="diaryTextView_centered-label">오늘 하루는 어땠어?</label>
              <input type="text" value="좋았어" id="title" readonly />

              <label for="content" class="diaryTextView_centered-label">오늘을 기록하면서 하루를 정리해봐</label>
              <textarea id="content" rows="6" readonly>2023학년도 대학수학능력시험이 얼마 안 남았는데요. 2022년 11월 17일 드디어 일주일을 앞두고 있습니다!
간절히 준비해온 수험생의 마음을 잘 알기 때문에 주변 가족들이나 지인들도 같은 마음으로 응원을 하고 있어요
</textarea>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      newMessage: '',
      currentQuestionIndex: 0,
      questions: [
        '오늘 하루는 어땠어?',
        '오늘의 감정은?',
        '오늘의 색은?',
        '오늘을 기록해줘~'
      ],
      keywords: [
        '기쁘다', '신난다', '화난다', '속상하다', '짜증난다', '피곤하다', '후회된다',
        '억울하다', '외롭다', '긴장된다', '미안하다', '놀랐다', '걱정된다', '불안하다',
        '고맙다', '반갑다', '뿌듯하다', '만족스럽다', '감동했다', '심심하다', '지친다',
        '귀찮다', '아쉽다', '궁금하다'
      ],
      messages: [
        { type: 'bot', text: '오늘 하루는 어땠어?', time: this.getCurrentTime() }
      ],
      lastYearMessages: [
        { type: 'bot', text: '오늘 하루는 어땠어?', time: '22:00' },
        { type: 'user', text: '나도 모르겠엉', time: '22:01' },
        { type: 'bot', text: '오늘의 감정은?', time: '22:02' },
        { type: 'user', text: '물렁물렁해', time: '22:03' },
        { type: 'bot', text: '오늘의 색은?', time: '22:04' },
        { type: 'user', text: '연한 딸기우유색', time: '22:05' },
        { type: 'bot', text: '오늘을 기록해줘~', time: '22:06' },
        { type: 'user', text: '아몰랑', time: '22:07' }
      ],
      isSending: false,
      buttonText: '말하기',
      showKeywords: false,
      hideInput: false
    };
  },


  methods: {
    getCurrentTime() {
      const now = new Date();
      return `${now.getHours()}:${now.getMinutes() < 10 ? '0' : ''}${now.getMinutes()}`;
    },
    handleButtonClick() {
      if (this.buttonText === '말하기') {
        if (this.currentQuestionIndex === 1) {
          this.sendMessage();
          return; // 바로 전송 후 종료
        } else {
          this.buttonText = '전송';
          this.isSending = true;
        }
      } else if (this.buttonText === '전송') {
        this.sendMessage();
      }
    },
    selectKeyword(keyword) {
      this.newMessage = keyword;
      this.handleButtonClick();
    },
    sendMessage() {
      if (this.newMessage.trim() === '') return;

      const timeString = this.getCurrentTime();

      this.messages.push({
        type: 'user',
        text: this.newMessage,
        time: timeString
      });

      this.newMessage = '';
      this.isSending = false;

      this.$nextTick(() => {
        this.scrollToBottom();
      });

      this.currentQuestionIndex++;
      if (this.currentQuestionIndex < this.questions.length) {
        this.messages.push({
          type: 'bot',
          text: this.questions[this.currentQuestionIndex],
          time: timeString
        });

        this.showKeywords = this.currentQuestionIndex === 1;

        if (this.currentQuestionIndex === this.questions.length - 1) {
          this.buttonText = '말하기';
        } else {
          this.buttonText = '말하기';
        }

        this.$nextTick(() => {
          this.scrollToBottom();
        });
      } else if (this.currentQuestionIndex >= this.questions.length) {
        this.hideInput = true;
      }
    },
    scrollToBottom() {
      const messageList = this.$refs.messageList;
      messageList.scrollTop = messageList.scrollHeight;
    },
    DiaryTalkView_Page() {
      this.$router.push("/DiaryTalkView");
    },
    DiaryTextView_Page() {
      this.$router.push("/DiaryTextView");
    },
  }
};
</script>

<style scoped>
.diaryTextView_content-wrapper {
  display: flex;
  justify-content: center;
  gap: 40px;
  margin: 0 auto;
}

.diaryTextView_app, .diaryTextView_right-section {
  flex: 1;
  max-width: 600px;
}

.diaryTextView_app {
  font-family: 'Arial', sans-serif;
  padding: 20px;
  background: linear-gradient(to right, #f8f9fa, #e9ecef);
  border-radius: 25px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.diaryTextView_centered-label {
  text-align: center;
  font-weight: bold;
  font-size: 1.25em;
  margin: 20px 0;
}
.diaryTextView_centered-label_two {
  text-align: center;
  font-weight: bold;
  font-size: 1.25em;
  margin: 50px 0;
}
.diaryTextView_entry-form {
  text-align: center;
}

.diaryTextView_entry-form label {
  display: block;
  margin: 20px 0;
  font-weight: bold;
  font-size: 1.25em;
}

.diaryTextView_entry-form input,
.diaryTextView_entry-form textarea {
  width: 85%;
  padding: 8px;
  margin: 0 auto 10px;
  border: 1px solid #ccc;
  border-radius: 25px;
  box-sizing: border-box;
  display: block;
}

.diaryTextView_entry-form textarea {
  height: 150px;
}

.diaryTextView_button-container {
  margin-top: 30px;
  display: flex;
  justify-content: flex-end;
}

.diaryTalkView_simulationIntro3 {
  background-color: #fff;
  color: #337DF0;
  font-weight: 1000;
  padding: 10px 20px;
  font-size: 1em;
  border-radius: 25px;
  border: 3px solid #337DF0;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.diaryTalkView_simulationIntro3:hover {
  background-color: #337DF0;
  color: #fff;
  font-weight: 1000;
}

.diaryTextView_emotion-cards {
  margin: 30px 0;
  text-align: center;
}

.diaryTextView_cards {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  justify-content: center;
}

.diaryTextView_card {
  display: inline-block;
  padding: 10px 20px;
  text-align: center;
  cursor: pointer;
  white-space: nowrap;
  border-radius: 25px;
  transition: transform 0.3s, box-shadow 0.3s;
  font-size: 16px;
  border: 1px solid #ddd;
}

.diaryTextView_card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.diaryTextView_card p {
  margin: 0;
  font-weight: bold;
  color: #333;
}

.diaryTextView_card.selected {
  background-color: white;
}

.diaryTextView_yellow-highlight {
  box-shadow: 0 0 12px yellow;
  text-shadow: 0 0 12px yellow;
}

.diaryTextView_red-highlight {
  box-shadow: 0 0 12px red;
  text-shadow: 0 0 12px red;
}

.diaryTextView_blue-highlight {
  box-shadow: 0 0 12px blue;
  text-shadow: 0 0 12px blue;
}

.diaryTextView_gray-highlight {
  box-shadow: 0 0 12px gray;
  text-shadow: 0 0 12px gray;
}

.diaryTextView_green-highlight {
  box-shadow: 0 0 12px green;
  text-shadow: 0 0 12px green;
}

.diaryTextView_lightgreen-highlight {
  box-shadow: 0 0 12px lightgreen;
  text-shadow: 0 0 12px lightgreen;
}

.diaryTextView_purple-highlight {
  box-shadow: 0 0 12px purple;
  text-shadow: 0 0 12px purple;
}

.diaryTextView_orange-highlight {
  box-shadow: 0 0 12px orange;
  text-shadow: 0 0 12px orange;
}

.diaryTextView_card.shine {
  animation: diaryTextView_sparkle 3s;
}

@keyframes diaryTextView_sparkle {
  0% {
    box-shadow: 0 0 12px rgba(51, 125, 240, 0.8);
    text-shadow: 0 0 12px rgba(51, 125, 240, 0.8);
  }
  50% {
    box-shadow: 0 0 20px rgba(51, 125, 240, 1);
    text-shadow: 0 0 20px rgba(51, 125, 240, 1);
  }
  100% {
    box-shadow: 0 0 12px rgba(51, 125, 240, 0.8);
    text-shadow: 0 0 12px rgba(51, 125, 240, 0.8);
  }
}

.diaryTextView_color-picker {
  margin: 30px 0;
  text-align: center;
}

.diaryTextView_color-circle-one {
  margin: 0 auto 10px;
  width: 100px;
  height: 100px;
  border-radius: 50%;
  display: block;
  border: 2px solid #ddd;
  cursor: pointer;
}
.diaryTextView_color-circle-two {
  margin: 0 auto 10px;
  width: 100px;
  height: 100px;
  border-radius: 50%;
  display: block;
  border: 2px solid #ddd;
}

.diaryTextView_right-section {
  flex: 1;
  max-width: 600px;
  font-family: 'Arial', sans-serif;
  padding: 20px;
  background: linear-gradient(to right, #f8f9fa, #e9ecef);
  border-radius: 25px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.diaryTextView_emotion-highlight {
  box-shadow: 0 0 12px yellow;
  text-shadow: 0 0 12px yellow;
  display: block;
  width: 100px;
  margin: -30px auto;
}

.diaryTextView_message-list {
  padding: 15px;
  overflow-y: auto;
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  padding-top: 60px;
  height: 600px;
}

.diaryTextView_message {
  border-radius: 10px;
  padding: 10px;
  margin-bottom: 10px;
  max-width: 80%;
  position: relative;
  display: inline-block;
}

.diaryTextView_message.diaryTextView_user {
  background-color: #e1f5fe;
  align-self: flex-end;
  text-align: right;
}

.diaryTextView_message.diaryTextView_bot {
  background-color: #f1f8e9;
  align-self: flex-start;
  text-align: left;
}

.diaryTextView_message-time {
  font-size: 0.8em;
  color: #888;
  margin-top: 5px;
  text-align: right;
}

.diaryTextView_input-wrapper {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.diaryTextView_input-box {
  flex: 1;
  border: 1px solid #d0d0d0;
  border-radius: 20px;
  padding: 10px;
  font-size: 1em;
  margin-right: 10px;
  outline: none;
}

.diaryTextView_keywords {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-bottom: 10px;
}

.diaryTextView_fade-enter-active,
.diaryTextView_fade-leave-active {
  transition: opacity 0.5s;
}
.diaryTextView_fade-enter,
.diaryTextView_fade-leave-to /* .fade-leave-active in <2.1.8 */ {
  opacity: 0;
}
</style>

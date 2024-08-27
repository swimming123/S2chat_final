/*UI개발: 김건우 */
<template>
  <div class="diaryTextView_chatbot-container">
    <div class="diaryTextView_message-list" ref="messageList">
      <div v-for="(message, index) in messages" :key="index"
        :class="['diaryTextView_message', message.type === 'user' ? 'diaryTextView_user' : 'diaryTextView_bot']">
        <template v-if="message.isTyping">
          <div class="typing-animation">
            <div class="dot"></div>
            <div class="dot"></div>
            <div class="dot"></div>
          </div>
        </template>
        <template v-else>
          {{ message.text }}
          <div class="diaryTextView_message-time">{{ message.time }}</div>
        </template>
      </div>
    </div>
    <transition name="diaryTextView_fade">
      <div v-if="!isCompleted && !hideInput" class="diaryTextView_input-area">
        <textarea v-model="newMessage" @input="handleTypingAndAdjustHeight"
          @keyup.enter="handleButtonClick" class="diaryTextView_input-box"
          placeholder="오늘은 어떤 일이 있었나요?" ref="inputBox"></textarea>
        <div v-if="isRecording" class="audio-control">
          <audio ref="audioPlayer" controls></audio>
        </div>
        <button v-if="newMessage.trim() === ''" @click="handleButtonClick"
          class="diaryTalkView_simulationIntro3">
          녹음하기
        </button>
        <button v-else @click="handleButtonClick" class="diaryTalkView_simulationIntro3">
          전송하기
        </button>
      </div>
    </transition>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      newMessage: '',
      isCompleted: false,
      isRecording: false,
      messages: [{ type: 'bot', text: '오늘의 감정 키워드를 선택해주세요', time: this.getCurrentTime() }],
      currentQuestionIndex: 0,
      content: '', // 새로 추가된 메시지 내용을 누적하는 변수
      keywords: ['기쁘다', '신난다', '화난다', '속상하다', '짜증난다', '피곤하다', '후회된다', '억울하다', '외롭다', '긴장된다', '미안하다', '놀랐다', '걱정된다', '불안하다', '고맙다', '반갑다', '뿌듯하다', '만족스럽다', '감동했다', '심심하다', '지친다', '귀찮다', '슬프다', '상처받았다'],
      showKeywords: true,
      hideInput: false,
      isMicAvailable: false,
      diaryEntries: [],
    };
  },
  methods: {
    async checkMicAvailability() {
      try {
        await navigator.mediaDevices.getUserMedia({ audio: true });
        this.isMicAvailable = true;
      } catch (error) {
        console.error('Microphone access is not available:', error);
      }
    },
    getCurrentTime() {
      const now = new Date();
      return `${now.getHours()}:${String(now.getMinutes()).padStart(2, '0')}`;
    },
    handleButtonClick() {
      if (this.newMessage.trim() === '') {
        // 녹음 시작/종료 로직
        this.isRecording = !this.isRecording;
      } else {
        this.processMessage();
      }
    },
    handleTypingAndAdjustHeight(event) {
      const textarea = event.target;
      textarea.style.height = 'auto';
      textarea.style.height = `${textarea.scrollHeight}px`;
    },    
    handleTyping() {
      clearTimeout(this.typingTimeout);
      this.typingTimeout = setTimeout(() => this.isTyping = false, 3000);
    },
    selectKeyword(keyword) {
      this.newMessage = keyword;
      this.processMessage();
    },
    processMessage() {
      const timeString = this.getCurrentTime();
      this.messages.push({ type: 'user', text: this.newMessage, time: timeString });
      this.newMessage = '';
      this.isRecording = false;
      if (this.newMessage.trim() === '끝') {
        this.saveDiaryEntry();
      } else {
        this.content += ` ${this.newMessage}`.trim();
        this.newMessage = '';
        this.advanceQuestion();
      }
    },
    appendMessage(message) {
      this.messages.push(message);
      this.scrollToBottom();
    },
    advanceQuestion() {
      this.currentQuestionIndex++;
      if (this.currentQuestionIndex < this.questions.length) {
        this.appendMessage({ type: 'bot', text: this.questions[this.currentQuestionIndex], time: this.getCurrentTime() });
        this.showKeywords = this.currentQuestionIndex === 0;
      } else {
        this.buttonText = '끝';
      }
    },
    saveDiaryEntry() {
      this.hideInput = true;

      const entry = {
        title: this.messages[1]?.text || "",
        content: this.content,
        emotions: this.messages.slice(2).map(msg => msg.text)
      };

      const membernum = parseInt(localStorage.getItem("membernum"));
      if (!membernum) {
        alert("회원 번호가 설정되지 않았습니다. 다시 로그인 해주세요.");
        return;
      }

      const today = new Date();
      const weekOfYear = this.getWeekOfYear(today);

      axios.post(`${process.env.VUE_APP_BACK_END_URL}/diary/diaryAdd`, {
        membernum,
        title: entry.title,
        content: entry.content,
        emotions: entry.emotions.join(", "),
        week: weekOfYear,
      })
        .then(response => {
          this.diaryEntries = response.data;
          this.$router.push({ name: 'MyPageTurn' });
        })
        .catch(error => {
          if (error.response) {
            console.error("서버 응답:", error.response.data);
          }
        });
    },
    getWeekOfYear(date) {
      const firstDayOfYear = new Date(date.getFullYear(), 0, 1);
      const pastDaysOfYear = (date - firstDayOfYear) / 86400000;
      return Math.ceil((pastDaysOfYear + firstDayOfYear.getDay() + 1) / 7);
    },
    scrollToBottom() {
      this.$nextTick(() => {
        const messageList = this.$refs.messageList;
        messageList.scrollTop = messageList.scrollHeight;
      });
    },
    async startRecording() {
      if (!this.isMicAvailable) {
        alert('마이크를 사용할 수 없습니다.');
        return;
      }

      try {
        this.audioChunks = [];
        const stream = await navigator.mediaDevices.getUserMedia({ audio: true });
        this.mediaRecorder = new MediaRecorder(stream);

        this.mediaRecorder.ondataavailable = event => {
          this.audioChunks.push(event.data);
        };

        this.mediaRecorder.onstop = async () => {
          const audioBlob = new Blob(this.audioChunks, { type: 'audio/wav' });
          const audioUrl = URL.createObjectURL(audioBlob);
          this.$refs.audioPlayer.src = audioUrl;
          this.$refs.audioPlayer.play();
        };

        this.mediaRecorder.start();
        this.isRecording = true;
      } catch (error) {
        console.error('Error starting recording:', error);
        alert('녹음 시작 중 오류가 발생했습니다.');
      }
    },
    stopRecording() {
      if (this.mediaRecorder) {
        this.mediaRecorder.stop();
        this.isRecording = false;
      }
    }
  },
  mounted() {
    this.checkMicAvailability(); // 마이크 접근 권한 확인
    const membernum = localStorage.getItem("membernum");
    if (!membernum) {
      alert("회원 번호가 설정되지 않았습니다. 다시 로그인 해주세요.");
    }
  }
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
  display: flex;
  justify-content: center;
  gap: 40px;
  margin: 0 auto;
}

.diaryTextView_app,
.diaryTextView_right-section {
  flex: 1;
  max-width: 600px;
  margin-top: 100px;
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
  height: 140px;
}

.diaryTextView_button-container {
  margin-top: 30px;
  display: flex;
  justify-content: flex-end;
}

.diaryTalkView_simulationIntro3 {
  background-color: #337DF0;
  color: #fff;
  padding: 10px 20px;
  font-size: 1em;
  border-radius: 10px;
  border: none;
  cursor: pointer;
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
  height: 577px;
  overflow: hidden;
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
  resize: none;
  line-height: 1.5em;
  min-height: 3em;
  overflow: hidden;
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
.diaryTextView_fade-leave-to {
  opacity: 0;
}

.typing-animation {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 40px;
}

.dot {
  width: 8px;
  height: 8px;
  margin: 0 4px;
  background-color: #888;
  border-radius: 50%;
  animation: typing 1s infinite;
}

@keyframes typing {
  0% {
    opacity: 0.2;
  }

  20% {
    opacity: 1;
  }

  100% {
    opacity: 0.2;
  }
}

.dot:nth-child(1) {
  animation-delay: 0s;
}

.dot:nth-child(2) {
  animation-delay: 0.2s;
}

.dot:nth-child(3) {
  animation-delay: 0.4s;
}

.container-xxl {
  margin-top: -170px;
}

.diaryTalkView_simulationIntro3:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}
.diaryTalkView_simulationIntro3:hover {
  background-color: #285AB8;
}
.diaryTextView_input-area {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px;
  border-top: 1px solid #ddd;
}

.diaryTextView_input-box {
  flex: 1;
  resize: none;
  border: 1px solid #d0d0d0;
  border-radius: 10px;
  padding: 10px;
  font-size: 1em;
  margin-right: 10px;
  min-height: 40px;
  max-height: 200px;
  overflow-y: auto;
}

.diaryTextView_input-area audio {
  flex: 0 0 200px;
}

.diaryTextView_input-area button {
  flex: 0 0 auto;
  padding: 10px 20px;
  margin-left: 10px;
  background-color: #337DF0;
  color: white;
  border-radius: 10px;
  border: none;
  cursor: pointer;
}

.diaryTextView_chatbot-container {
  max-width: 80%;
  display: flex;
  margin-left: 10%;
  flex-direction: column;
  padding: 10px;
  border-radius: 10px;
  background-color: #fff;
}

.audio-control {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 10px;
}
</style>

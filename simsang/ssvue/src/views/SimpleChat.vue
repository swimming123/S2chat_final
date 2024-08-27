/*UI개발: 허호준 */
<template>
  <div>
    <div class="flex-container">
      <div class="image-container flex-item">
        <button @click="isRecording ? stopRecording() : startRecording()" id="recording">
          {{ isRecording ? 'Stop Recording' : 'Start Recording' }}
        </button>
        <button @click="playRecording" id="playRecording" :disabled="!audioChunks.length">
          Play Recording
        </button>
        <audio ref="audioPlayer" controls></audio>
        <div>
          <div v-if="responseData !== null">
            <p>{{ responseData.transcript }}</p>
          </div>
          <div v-else>
            <p>Loading...</p>
          </div>
        </div>
      </div>


      <!-- 채팅 컨테이너 -->
      <div class="chat-container flex-item">
        <div class="chat">
          <div class="messages">
            <div v-for="(message, index) in messages" :key="index" :class="['message', message.type]">
              <strong>{{ message.user }}:</strong> {{ message.text }}
            </div>
          </div>
          <div class="input-container">
            <input type="text" v-model="newMessage" @keyup.enter="sendMessage" class="text-center"
              placeholder="답변을 입력하세요!" />
            <button @click="sendMessage">전송</button>
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
      messages: [], // 채팅 메시지 리스트
      newMessage: '', // 사용자가 입력한 새 메시지
      responses: [], // 사용자 응답 리스트
      surveyQuestions: [], // 설문 질문 리스트
      currentQuestionIndex: 0, // 현재 질문 인덱스
      surveyCode: 'CESD', // 설문 코드
      isRecording: false,
      mediaRecorder: null,
      audioChunks: [],
      responseData: null,
    };
  },
  methods: {
    // 설문 질문을 서버로부터 가져오는 함수
    async fetchSurveyQuestions() {
      try {
        const response = await axios.get(`${process.env.VUE_APP_BACK_END_URL}/survey/getQuestion`);
        this.surveyQuestions = response.data;
        this.addChatbotMessage(this.surveyQuestions[this.currentQuestionIndex].question);
      } catch (error) {
        console.error("Error fetching survey questions:", error);
      }
    },
    // // 챗봇 메시지를 추가하는 함수
    // addChatbotMessage(text) {
    //   this.messages.push({
    //     user: 'Chatbot',
    //     text,
    //     type: 'bot'
    //   });
    //   this.$nextTick(() => {
    //     this.scrollToEnd();
    //   });
    // },
    // 챗봇 메시지를 추가하고 음성을 재생하는 함수
    addChatbotMessage(text) {
      // 메시지를 채팅에 추가
      this.messages.push({
        user: 'Chatbot',
        text,
        type: 'bot'
      });

      // 음성으로 메시지 읽기
      this.speak(text);

      // 메시지를 추가한 후 스크롤을 맨 아래로 이동
      this.$nextTick(() => {
        this.scrollToEnd();
      });
    },

    // 텍스트를 음성으로 변환해주는 함수
    speak(text) {
      const msg = new SpeechSynthesisUtterance(text);
      msg.lang = 'ko-KR'; // 한국어 설정

      // 음성을 재생
      window.speechSynthesis.speak(msg);
    },

    // 메시지를 전송하는 함수
    async sendMessage() {
      if (this.newMessage.trim() !== '') {
        const message = {
          user: 'Me',
          text: this.newMessage,
          type: 'user'
        };
        this.messages.push(message);
        const score = this.calculateScore(this.newMessage);
        this.responses.push({
          qnum: this.currentQuestionIndex + 1,
          respscore: score
        });
        this.newMessage = '';
        this.$nextTick(() => {
          this.scrollToEnd();
        });

        if (this.currentQuestionIndex < this.surveyQuestions.length - 1) {
          this.currentQuestionIndex++;
          setTimeout(() => {
            this.addChatbotMessage(this.surveyQuestions[this.currentQuestionIndex].question);
          }, 300);
        } else {
          await this.sendResponses();
        }
      }
    },
    // 사용자 응답을 서버에 저장하는 함수
    async sendResponses() {
      try {
        const userid = 'user2';
        const responseDate = new Date().toISOString().split('T')[0];
        for (const response of this.responses) {
          await axios.post(`${process.env.VUE_APP_BACK_END_URL}/survey/insertResponse`, {
            userid: userid,
            surveycode: this.surveyCode,
            qnum: response.qnum,
            respscore: response.respscore,
            respdate: responseDate
          });
        }
      } catch (error) {
        console.error("Error saving user response:", error);
      }
    },
    // 사용자가 입력한 메시지의 점수를 계산하는 함수
    calculateScore(text) {
      const positiveWords = ['ㅇ', '네', '응', '매우', '엄청', '맞아'];
      const lowerText = text.toLowerCase();
      return positiveWords.some(word => lowerText.includes(word)) ? 1 : 0;
    },
    // 메시지 창을 자동으로 스크롤하는 함수
    scrollToEnd() {
      const container = this.$el.querySelector('.messages');
      container.scrollTop = container.scrollHeight;
    },
    startRecording() {
      navigator.mediaDevices.getUserMedia({ audio: true })
        .then(stream => {
          this.mediaRecorder = new MediaRecorder(stream);
          this.mediaRecorder.ondataavailable = event => {
            this.audioChunks.push(event.data);
          };
          this.mediaRecorder.onstop = this.handleStopRecording;
          this.mediaRecorder.start();
          this.isRecording = true;
        })
        .catch(error => {
          console.error('Error accessing media devices:', error);
          // 사용자에게 오류 메시지 표시 또는 다른 대응 방안 구현
        });
    },
    stopRecording() {
      if (this.mediaRecorder) {
        this.mediaRecorder.stop();
        this.isRecording = false;
      }
    },
    playRecording() {
      const audioBlob = new Blob(this.audioChunks, { type: 'audio/wav' });
      const audioUrl = URL.createObjectURL(audioBlob);
      const audio = new Audio(audioUrl);
      audio.play();
    },
    handleStopRecording() {
      const audioBlob = new Blob(this.audioChunks, { type: 'audio/wav' });
      const audioUrl = URL.createObjectURL(audioBlob);
      this.$refs.audioPlayer.src = audioUrl;

      const today = this.getTodayDateTimeString();

      const formData = new FormData();
      formData.append("file1", audioBlob, `${today}.wav`);

      axios.post(`http://192.168.92:9000/sschat/word2`, formData, {
        headers: { "Content-Type": "multipart/form-data" },
      })
        .then(response => {
          this.responseData = response.data;
        })
        .catch(error => {
          console.error('Failed to upload', error);
        });
    },
    getTodayDateTimeString() {
      const now = new Date();
      const year = now.getFullYear();
      const month = String(now.getMonth() + 1).padStart(2, '0');
      const day = String(now.getDate()).padStart(2, '0');
      const hours = String(now.getHours()).padStart(2, '0');
      const minutes = String(now.getMinutes()).padStart(2, '0');
      const seconds = String(now.getSeconds()).padStart(2, '0');
      return `${year}-${month}-${day}_${hours}-${minutes}-${seconds}`;
    },

  },
  mounted() {
    window.scrollTo(0, 0);
    this.fetchSurveyQuestions();
  },
};
</script>

<style>
.flex-container {
  margin-top:100px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.flex-item {
  width: 350px;
  height: 700px;
  margin: 10px;
  align-items: center;
  justify-content: center;
}


.image-container {
  border: 1px solid rgba(0, 0, 0, 0.15);
  border-radius: 1rem;
}

.image-container img {
  width: 100%;
  height: auto;
  border-radius: 10px;
}

.chat-container {
  border: 1px solid rgba(0, 0, 0, 0.15);
  border-radius: 1rem;
}

.chat {
  padding: 20px;
  display: flex;
  flex-direction: column;
  height: 100%;
}

.messages {
  flex: 1;
  overflow-y: auto;
  margin-bottom: 20px;
}

.message {
  margin-bottom: 10px;
  padding: 10px;
  border-radius: 5px;
  max-width: 70%;
  clear: both;
}

.message.user {
  background-color: #007bff;
  color: #fff;
  float: right;
  text-align: right;
}

.message.bot {
  background-color: #f1f1f1;
  float: left;
}

.input-container {
  display: flex;
}

input {
  flex: 1;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
}

button {
  padding: 10px 20px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-left: 10px;
}

button:hover {
  background-color: #0056b3;
}
</style>

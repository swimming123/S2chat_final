/*UI개발:이지영*/
<template>
  <div>
    <div class="main">
      <div class="container-xxl">
        <div class="container">
          <div class="section-title text-center">
            <h1 class="display-5 mb-3">{{ categoryText }}</h1>
          </div>
          <div class="row d-flex justify-content-center">
            <div
              class="simulVid1 col-9 row d-flex justify-content-center align-items-center"
            >
              <div class="col-md-4 simuldescription">
                <img
                  class="simulVideo4-1"
                  src="/images/simulation/simulvidexplain1.png"
                />
                <img
                  class="simulVideo4-2"
                  src="/images/simulation/simulvidexplain2.png"
                />
              </div>
              <div class="col-md-4">
                <div class="simulVid2" :style="simulVid2Style">
                  <!-- 카운트다운 표시 -->
                  <div v-if="showCountdown" class="countdown-overlay">
                    <p style="font-size: 200px">{{ countdown }}</p>
                  </div>

                  <!-- GPT 응답을 비디오 상단에 오버레이로 표시 -->
                  <div
                    v-if="gptResponse && !showCountdown"
                    class="gpt-response-overlay"
                    ref="gptResponseOverlay"
                  >
                    {{ gptResponse }}
                  </div>

                  <video
                    v-if="!isCameraOff"
                    ref="videoElement"
                    class="streaming-video"
                    autoplay
                    muted
                  ></video>
                  <!-- 녹화 안내 문구 -->
                  <div
                    v-if="showRecordingHint"
                    class="recording-hint"
                    style="
                      position: absolute;
                      top: 10px;
                      left: 50%;
                      transform: translateX(-50%);
                      background-color: rgba(0, 0, 0, 0.5);
                      color: white;
                      padding: 5px 10px;
                      border-radius: 5px;
                      font-size: 15px;
                      text-align: center;
                    "
                  >
                    통화 버튼을 누르면 녹화가 시작됩니다.
                    <br />
                    마스크나 안경을 착용한 상태면
                    <br />
                    인식률이 떨어질 수 있습니다.
                  </div>
                  <div class="simulSpeech" style="text-align: center">
                    <p>{{ transcript }}</p>
                  </div>
                  <div class="simulvid6">
                    <img
                      v-if="showOpenButton"
                      :class="{
                        disabled: !isSimulationStarted && !isScenarioSet,
                      }"
                      class="open-button"
                      src="/images/simulation/call.png"
                      @click="handleOpenClick"
                      alt="Open"
                    />
                    <img
                      v-if="showCloseButton"
                      class="close-button-simul"
                      src="/images/simulation/stopcall.png"
                      @click="handleCloseClick"
                      alt="Close"
                    />
                    <!-- <img
                      class="nocam-button"
                      src="/images/simulation/novid.png"
                      @click="toggleImage"
                      alt="NoCam"
                    /> -->
                  </div>
                </div>
              </div>
              <div class="col-md-4 text-right description">
                <h5>50자 이내로 상황을 설정하세요.</h5>
                <input
                  type="text"
                  v-model="scenario"
                  class="simulVideo5 text-center"
                  placeholder="구체적인 상황일수록 좋습니다!"
                  :disabled="isSimulationStarted"
                />
                <div class="simulvid6">
                  <button
                    v-show="!isSimulationStarted"
                    @click="startSimulation"
                    class="simulChat1"
                  >
                    상황 설정하기
                  </button>
                  <button @click="resetSimulation" class="reset-button">
                    초기화
                  </button>
                </div>
              </div>
            </div>
            <div class="simulvideo6" style="margin-bottom: 20px">
              <button
                v-if="showExitButton"
                @click="handleExit"
                class="simulVideo3"
              >
                결과 보러가기
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "SimulationChat",
  data() {
    return {
      messages: [],
      newMessage: "",
      scenario: "",
      simulationStarted: false,
      isSimulationStarted: false,
      showCloseButton: false,
      showOpenButton: true,
      showExitButton: true,
      csrfToken: 1,
      userMessageCount: 0,
      countdown: 3,
      countdownInterval: null,
      showCountdown: false,
      gptResponse: "",
      isCameraOff: false,
      alternateImage: "/images/simulation/nocam.png", // 대체 이미지 경로
      originalImage: "/images/simulation/video.png", // 원본 이미지 경로
      showAlternateImage: false,
      isScenarioSet: false, // 상황 설정 여부를 추적,
      showRecordingHint: false, // 녹화 안내 문구 표시 여부
      videoStream: null,
      recordedChunks: [],
      recognition: null,
      transcript: "",
      messageToSend: [],
      showExitButton: false,
    };
  },
  computed: {
    categoryText() {
      const category = this.$route.query.category;
      const categoryTexts = {
        부모: "부모와 대화중",
        형제: "형제와 대화중",
        자매: "자매와 대화중",
        부탁: "친구에게 부탁하는 대화중",
        거절: "친구에게 거절하는 대화중",
        다툼: "친구와 다툼의 상황에서 대화중",
        상사: "상사와 대화중",
        부하직원: "부하직원과 대화중",
        발표: "회사에서 발표중",
      };
      return categoryTexts[category] || "";
    },
    simulVid2Style() {
      return {
        backgroundImage: `url(${
          this.showAlternateImage ? this.alternateImage : this.originalImage
        })`,
      };
    },
  },
  methods: {
    startSpeechRecognition() {
      if (!("webkitSpeechRecognition" in window)) {
        alert("이 브라우저는 음성 인식을 지원하지 않습니다.");
        return;
      }

      this.recognition = new webkitSpeechRecognition();
      this.recognition.continuous = true;
      this.recognition.interimResults = true;
      let speechTimeout = null;

      const resetTimeout = () => {
        clearTimeout(this.speechTimeout);
        this.speechTimeout = setTimeout(() => {
          if (this.transcript.trim() !== "") {
            this.sendMessage(this.transcript.trim());
            this.transcript = "";
          }
        }, 3000);
      };

      this.recognition.onresult = (event) => {
        let interimTranscript = "";
        for (let i = event.resultIndex; i < event.results.length; ++i) {
          if (event.results[i].isFinal) {
            this.transcript += event.results[i][0].transcript;
          } else {
            interimTranscript += event.results[i][0].transcript;
          }
        }
        // 타이머 리셋
        resetTimeout();
      };

      this.recognition.onerror = (event) => {
        console.error("Speech recognition error detected: " + event.error);
      };

      this.recognition.onend = () => {
        // 음성 인식이 중단되면 타이머를 종료
        clearTimeout(speechTimeout);
      };

      this.recognition.start();
    },
    stopSpeechRecognition() {
      this.recognition.stop();
    },
    startCountdown() {
      this.showCountdown = true;
      this.countdown = 3;
      this.countdownInterval = setInterval(() => {
        this.countdown -= 1;
        if (this.countdown <= 0) {
          clearInterval(this.countdownInterval);
          this.countdownInterval = null;
          this.showCountdown = false;
        }
      }, 1000);
    },
    async handleOpenClick() {
      // 상황이 설정되지 않았을 때 알림창을 표시
      if (!this.isSimulationStarted) {
        alert("상황을 먼저 설정해 주세요.");
        return; // 상황이 설정되지 않으면 이후 코드 실행을 중단
      }

      this.startCountdown();
      this.startSpeechRecognition();
      this.showRecordingHint = false;

      // 대기하면서 countdownInterval의 완료를 기다립니다.
      const countdownPromise = new Promise((resolve) => {
        const countdownCheck = setInterval(() => {
          if (this.countdown <= 0) {
            clearInterval(countdownCheck);
            resolve();
          }
        }, 1000);
      });

      // Countdown이 끝난 후 실행됩니다.
      countdownPromise.then(async () => {
        this.showOpenButton = false;
        this.showCloseButton = true;
        this.startRecording();
        this.startSpeechRecognition(); // 음성 인식 시작
        this.transcript = ""; // transcript 초기화
        const additionalData = {
          membernum: localStorage.getItem("membernum"),
          // membernum: localStorage.getItem("id"),
          category: this.$route.query.category || "default",
          type: this.$route.query.type || "default",
          simuldate: new Date().toISOString().split("T")[0],
          title: this.scenario,
        };

        try {
          const response = await axios.post(
            `${process.env.VUE_APP_BACK_END_URL}/simulations/simulationinsert`,
            additionalData,
            {
              headers: {
                "Content-Type": "application/json",
                "X-CSRFToken": this.csrfToken,
              },
            }
          );

          this.simulationNum = response.data;
          const formData = new URLSearchParams();
          formData.append("category", this.$route.query.category || "default");
          formData.append("message", this.scenario);
          const simResponse = await axios.post(
            `http://192.168.92:9000/simulation/simulation_view`,
            formData,
            {
              headers: {
                "Content-Type": "application/x-www-form-urlencoded",
                "X-CSRFToken": this.csrfToken,
              },
            }
          );

          const data = simResponse.data;
          if (data.answer) {
            this.gptResponse = data.answer;
            await this.saveMessageToDB(this.simulationNum, this.scenario, "u");
            await this.saveMessageToDB(this.simulationNum, data.answer, "g");
          } else if (data.error) {
            console.error("Error:", data.error);
          }
        } catch (error) {
          console.error("Error:", error);
        }
      });
    },
    async startSimulation() {
      if (this.scenario.trim()) {
        this.isSimulationStarted = true;
        this.showOpenButton = true; // 상황 설정 버튼 클릭 후 open-button을 활성화
        this.showCloseButton = false; // 처음에는 close 버튼을 숨김
        this.showRecordingHint = true; // 녹화 안내 문구 표시

        // 비디오 스트림 시작
        try {
          navigator.mediaDevices
            .getUserMedia({ video: true, audio: true })
            .then((stream) => {
              this.videoStream = stream;
              this.$refs.videoElement.srcObject = stream;
              this.isCameraStarted = true;

              this.initMediaRecorder();
            })
            .catch((error) => {
              console.error("Error starting streaming:", error);
              alert("Failed to start video streaming");
            });
        } catch (error) {
          console.error("비디오 스트림 시작 오류:", error);
        }

        this.newMessage = "";
      } else {
        alert("상황을 입력하세요!");
      }
    },
    async saveMessageToDB(simulationNum, message, role, timestamp = null) {
      try {
        const data = {
          simulationnum: simulationNum,
          conversation: message,
          status: role,
          timestamp: timestamp,
        };

        const response = await axios.post(
          `${process.env.VUE_APP_BACK_END_URL}/simuldialogs/simuldialoginsert`,
          data,
          {
            headers: {
              "X-CSRFToken": this.csrfToken,
            },
          }
        );
      } catch (error) {
        console.error(
          "DB 저장 오류:",
          error.response ? error.response.data : error.message
        );
      }
    },
    handleCloseClick() {
      this.stopRecording();
      this.showExitButton = true;
    },
    // toggleImage() {
    //   this.showAlternateImage = !this.showAlternateImage;
    // },
    resetSimulation() {
      if (
        confirm(
          "상황설정을 초기화하면 모든 설정이 사라집니다. 정말로 초기화하시겠습니까?"
        )
      ) {
        this.stopStreaming();
        this.stopRecording();
        // 모든 상태 초기화
        this.isSimulationStarted = false;
        this.scenario = ""; // 입력 필드 초기화
        this.recordedChunks = []; // 녹화된 청크 초기화
        this.showOpenButton = false; // Open 버튼 비활성화
        this.showCloseButton = false; // Close 버튼 숨김
        this.showExitButton = false; // Exit 버튼 숨김
        this.isCameraOff = true; // 카메라 상태 초기화
        this.gptResponse = ""; // GPT 응답 초기화
        this.showCountdown = false; // 카운트다운 초기화
        clearInterval(this.countdownInterval); // 카운트다운 인터벌 정리
      }
    },
    updateTranscript(event) {
      this.transcript = event.results[0][0].transcript;

      // 음성 인식된 텍스트가 있을 때 GPT에 전송
      if (this.transcript) {
        this.sendMessage(this.transcript);
      }
    },
    async sendMessage(transcript = null) {
      let messageToSend;
      if (transcript && typeof transcript === "string") {
        messageToSend = transcript;
      } else {
        messageToSend = this.newMessage.trim();
      }
      if (messageToSend) {
        const userMessage = {
          user: "Me",
          text: messageToSend,
          type: "user",
        };
        this.messages.push(userMessage);
        this.userMessageCount++;

        if (this.userMessageCount >= 5) {
          this.showExitButton = true;
        }

        const timestamp = new Date().toISOString();
        const formData = new URLSearchParams();
        formData.append("category", this.$route.query.category || "default");
        formData.append("message", messageToSend);

        try {
          const response = await axios.post(
            `http://192.168.92:9000/simulation/simulation_view`,
            formData,
            {
              headers: {
                "Content-Type": "application/x-www-form-urlencoded",
                "X-CSRFToken": this.csrfToken,
              },
            }
          );

          const data = response.data;
          if (data.answer) {
            this.messages.push({
              user: "S2Chat",
              text: data.answer,
              type: "bot",
            });

            this.gptResponse = data.answer;
            const simulationNum = this.simulationNum;
            await this.saveMessageToDB(
              simulationNum,
              messageToSend,
              "u",
              timestamp
            );
            await this.saveMessageToDB(
              simulationNum,
              data.answer,
              "g",
              timestamp
            );
          } else if (data.error) {
            console.error("Error:", data.error);
          }
        } catch (error) {
          console.error("Error:", error);
        }

        this.newMessage = "";
      }
    },
    async fetchUserConversations() {
      try {
        const response = await axios.get(
          `${process.env.VUE_APP_BACK_END_URL}/simuldialogs/simuldialoglist`,
          {
            params: {
              status: "u",
              simulationnum: this.simulationNum,
            },
          }
        );
        const conversations = response.data;

        if (conversations.length > 0) {
          this.conversations = conversations;
          await this.analyzeSentiment(conversations);
        }
      } catch (error) {
        console.error("대화 내용 가져오기 오류:", error);
      }
    },

    async analyzeSentiment(conversations) {
      try {
        const combinedMessage = conversations
          .map((conv) => conv.conversation)
          .join(" ");

        const response = await axios.post(
          `http://192.168.92:9000/sschat/predict_simul`,
          { predict_sentence: combinedMessage },
          {
            headers: {
              "Content-Type": "application/json",
              "X-CSRFToken": this.csrfToken,
            },
          }
        );

        await this.handleExit(
          response.data.analysis_result,
          response.data.gpt_evaluation
        );
      } catch (error) {
        console.error("감정 분석 오류:", error);
      }
    },

    async handleExit(analysisResult, evaluation) {
      this.stopSpeechRecognition(); // 음성 인식 중지
      this.$router.push({
        path: "/SimulationResultLoading",
        query: {
          simulationnum: this.simulationNum,
          status: "u",
        },
      });

      if (this.recordedChunks.length > 0) {
        const recordedBlob = new Blob(this.recordedChunks, {
          type: "video/webm",
        });
        const formData = new FormData();
        formData.append("video", recordedBlob, "simulvid.webm");

        try {
          // 비디오 업로드 요청
          const uploadResponse = await axios.post(
            `http://192.168.92:9000/simulation/upload_video`,
            formData,
            {
              headers: {
                "Content-Type": "multipart/form-data",
                "X-CSRFToken": this.csrfToken,
              },
            }
          );

          const videoAnalysisResult =
            uploadResponse.data.analysis_result.analysis_result;
          const aggregatedEmotions = this.aggregateEmotions(
            analysisResult.emotion_values,
            videoAnalysisResult.emotion_values
          );

          // evaluation이 undefined일 경우 장고에서 받은 gpt_evaluation을 사용
          const finalEvaluation =
            evaluation || uploadResponse.data.gpt_evaluation;
          // 결과를 서버에 저장
          await this.saveResults(
            aggregatedEmotions,
            videoAnalysisResult,
            finalEvaluation
          );
        } catch (error) {
          console.error("비디오 업로드 오류:", error);
        }
      }
    },

    aggregateEmotions(textEmotionValues = {}, videoEmotionValues = {}) {
      const aggregated = {};

      // 텍스트 감정값 집계
      Object.entries(textEmotionValues).forEach(([name, score]) => {
        if (!aggregated[name]) {
          aggregated[name] = { total: 0, count: 0 };
        }
        aggregated[name].total += score;
        aggregated[name].count += 1;
      });

      // 비디오 감정값 집계
      Object.entries(videoEmotionValues).forEach(([name, score]) => {
        if (!aggregated[name]) {
          aggregated[name] = { total: 0, count: 0 };
        }
        aggregated[name].total += score;
        aggregated[name].count += 1;
      });

      // 평균 점수 계산
      const result = {};
      let totalScore = 0;
      Object.entries(aggregated).forEach(([name, { total, count }]) => {
        const averageScore = Math.round(total / count);
        result[name] = averageScore;
        totalScore += averageScore;
      });

      // 점수 총합을 100으로 맞추기 위해 비율 조정
      if (totalScore > 0) {
        Object.keys(result).forEach((name) => {
          result[name] = Math.round((result[name] / totalScore) * 100);
        });
      }

      return result;
    },
    async saveResults(aggregatedEmotions, analysisResult, evaluation) {
      for (const [name, score] of Object.entries(aggregatedEmotions)) {
        const simulresultVO = {
          simulationnum: this.simulationNum,
          emotionname: name,
          emotionscore: score,
          stressscore: Math.round(analysisResult.stress_score),
          heartrate: analysisResult.heart_rate,
          topemotion: analysisResult.top_emotion,
          topemotionscore: analysisResult.top_emotion_score,
          topemotionimg: analysisResult.top_emotion_frame,
          evaluation: evaluation,
        };

        try {
          const response = await axios.post(
            `${process.env.VUE_APP_BACK_END_URL}/simulresults/simulresultinsert`,
            simulresultVO,
            {
              headers: {
                "Content-Type": "application/json",
              },
            }
          );
        } catch (error) {
          console.error("Spring에 데이터 전송 오류:", error);
        }
      }
    },
    initMediaRecorder() {
      if (this.videoStream) {
        this.mediaRecorder = new MediaRecorder(this.videoStream);
        this.mediaRecorder.ondataavailable = (event) => {
          if (event.data.size > 0) {
            this.recordedChunks.push(event.data);
          }
        };
        this.mediaRecorder.onstop = () => {
        };
      }
    },
    startRecording() {
      if (this.mediaRecorder) {
        this.mediaRecorder.start();
        this.recordingInProgress = true; // 녹화가 진행 중임을 표시
      }
    },
    stopRecording() {
      if (this.mediaRecorder && this.recordingInProgress) {
        this.mediaRecorder.stop();
        this.recordingInProgress = false;
        this.recordedChunks = []; // 녹화된 데이터를 초기화
      }
    },
    stopStreaming() {
      if (this.videoStream) {
        this.videoStream.getTracks().forEach((track) => track.stop());
        this.videoStream = null;
      }
      if (this.mediaRecorder) {
        this.mediaRecorder = null; // 미디어 레코더를 null로 설정
      }
    },
  },
  mounted() {
    window.scrollTo(0, 0);
    const csrfTokenElement = document.querySelector(
      'input[name="csrfmiddlewaretoken"]'
    );
    if (csrfTokenElement) {
      this.csrfToken = csrfTokenElement.value;
    }
  },
  beforeUnmount() {
    this.stopStreaming();
  },
};
</script>

<style scoped>
.simulVid1 {
  position: relative;
  z-index: 1;
}

.simulVid2 {
  position: absolute;
  width: 650px;
  height: 600px;
  margin: 0 auto;
  background-color: #b3b3b3;
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  border-radius: 15px;
  text-decoration: none;
  top: 10px;
  left: 50%;
  transform: translateX(-50%);
  overflow: hidden;
  z-index: 0;
}

.simulVid2.dark-overlay::after {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  border-radius: 15px;
  z-index: 1;
}

.streaming-video {
  position: relative;
  width: 100%;
  height: 100%;
  border-radius: 15px;
  object-fit: cover;
  position: absolute;
  top: 0;
  left: 0;
  z-index: 1;
  transform: scaleX(-1);
}

.open-button,
.close-button-simul {
  position: absolute;
  bottom: 15px;
  left: 50%;
  transform: translateX(-50%);
  width: 50px;
  height: 50px;
  cursor: pointer;
  z-index: 2;
}

.open-button {
  width: 90px;
}

.open-button.disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.simulVideo3 {
  position: relative;
  width: 400px;
  left: 50%;
  transform: translateX(-50%);
  cursor: pointer;
  background-color: #ffffff;
  color: #000000;
  font-weight: bolder;
  padding: 10px 20px;
  font-size: 1em;
  border-radius: 25px;
  border: 5px solid #000000;
  transition: background-color 0.3s ease, color 0.3s ease;
  animation: blink 2s infinite;
  z-index: 2;
  margin-bottom: 20px;
}

.simulVideo3:hover {
  background-color: #000000;
  color: #ffffff;
}

@keyframes blink {
  0% {
    background-color: #ffffff;
    color: #000000;
  }
  50% {
    background-color: #000000;
    color: #ffffff;
  }
  100% {
    background-color: #ffffff;
    color: #000000;
  }
}

.nocam-button {
  position: absolute;
  bottom: 15px;
  right: 10px;
  width: 50px;
  height: 50px;
  cursor: pointer;
  z-index: 2;
}

.simuldescription,
.description {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.simuldescription img {
  margin-right: auto;
}

.description {
  margin-left: 10px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.simulVideo4-1 {
  width: 500px;
  margin-bottom: 370px;
}

.simulVideo4-2 {
  width: 500px;
  margin-top: 50px;
}

.simulVideo5 {
  width: 400px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  background-color: #f9f9f9;
  font-size: 1em;
  color: #333;
  transition: background-color 0.3s ease, border-color 0.3s ease;
}

.simulVideo5:focus {
  background-color: #fff;
  border-color: #007bff;
  outline: none;
}

.simulChat1,
.reset-button {
  padding: 0 20px;
  border: none;
  border-radius: 5px;
  line-height: 50px;
  font-size: 1em;
  cursor: pointer;
}

.simulChat1 {
  margin-top: 20px;
  margin-right: 5px;
  padding: 0 20px;
  border: none;
  border-radius: 5px;
  line-height: 50px;
  background-color: #337df0;
}

.simulChat1:hover {
  background-color: #1362e0;
}

.reset-button {
  background-color: #979797;
  color: #fff;
  margin-top: 20px;
}

.reset-button:hover {
  background-color: #eb4848;
}

.simulvideo6 {
  display: flex;
  gap: 10px;
}

.gpt-response-overlay {
  position: absolute;
  top: 10px;
  left: 50%;
  width: 400px;
  transform: translateX(-50%);
  background-color: rgba(0, 0, 0, 0.5);
  color: white;
  padding: 10px;
  border-radius: 5px;
  z-index: 3;
  text-align: center;
}

.countdown-overlay {
  position: absolute;
  top: 50%;
  width: 210px;
  height: 210px;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: rgba(0, 0, 0, 0.5);
  color: white;
  border-radius: 10px;
  z-index: 3;
  text-align: center;
  font-size: larger;
}
.recording-hint {
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  color: #fff;
  font-size: 20px;
  background: rgba(0, 0, 0, 0.7);
  padding: 10px;
  border-radius: 5px;
  z-index: 1;
}
.simulSpeech {
  position: absolute;
  left: 50%;
  bottom: 13%;
  width: 400px;
  transform: translateX(-50%);
  color: white;
  z-index: 3;
  text-align: center;
}
</style>

/*UI개발:이지영*/
<template>
  <div class="main-container">
    <div class="main">
      <div class="container-xxl-simul py-5">
        <div class="container">
          <div class="section-title text-center">
            <h1 class="display-5 mb-5">{{ categoryText }}</h1>
            <div class="scenario-input-container">
              <input
                type="text"
                v-model="scenario"
                class="text-center"
                placeholder="상황을 입력하세요!"
                :disabled="isSimulationStarted"
              />
              <button
                v-if="!isSimulationStarted"
                @click="startSimulation"
                class="simulChat1"
              >
                시뮬레이션 시작
              </button>
              <button
                v-if="isSimulationStarted"
                @click="resetSimulation"
                class="reset-button"
              >
                초기화
              </button>
            </div>
            <div class="chat-container" v-if="simulationStarted">
              <div class="chat">
                <div class="messages" ref="messagesContainer">
                  <div
                    v-for="(message, index) in messages"
                    :key="index"
                    :class="['message', message.type]"
                  >
                    <strong>{{ message.user }}:</strong> {{ message.text }}
                  </div>
                </div>
                <div class="input-container">
                  <input
                    type="text"
                    v-model="newMessage"
                    class="text-center"
                    placeholder="답변을 입력하세요!"
                    ref="messageInput"
                  />
                  <button class="simulChat2" @click="sendMessage">전송</button>
                </div>
              </div>
            </div>
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
      showCloseButton: true,
      showExitButton: false,
      csrfToken: "",
      userMessageCount: 0,
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
  },
  methods: {
    async startSimulation() {
      if (this.scenario.trim()) {
        this.simulationStarted = true;
        this.isSimulationStarted = true;
        const additionalData = {
          membernum: localStorage.getItem("membernum"),
          //membernum: localStorage.getItem("id")
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

          // `simulationNum`을 API 응답 받기
          this.simulationNum = response.data; // 시뮬레이션 번호 저장

          // 이후의 시뮬레이션 요청
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
            this.messages.push({
              user: "S2Chat",
              text: data.answer,
              type: "bot",
            });

            await this.saveMessageToDB(this.simulationNum, this.scenario, "u");
            await this.saveMessageToDB(this.simulationNum, data.answer, "g");
            await this.scrollToBottom();
          } else if (data.error) {
            console.error("Error:", data.error);
          }
        } catch (error) {
          console.error("Error:", error);
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
          timestamp: timestamp, // 타임스탬프 추가 (선택 사항)
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
    async scrollToBottom() {
      await this.$nextTick();
      const messagesContainer = this.$refs.messagesContainer;
      messagesContainer.scrollTop = messagesContainer.scrollHeight;
    },
    async sendMessage() {
      if (this.newMessage.trim()) {
        const userMessage = {
          user: "Me",
          text: this.newMessage,
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
        formData.append("message", this.newMessage);

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

            const simulationNum = this.simulationNum; 
            await this.saveMessageToDB(
              simulationNum,
              this.newMessage,
              "u",
              timestamp
            );
            await this.saveMessageToDB(
              simulationNum,
              data.answer,
              "g",
              timestamp
            );
            await this.scrollToBottom();
          } else if (data.error) {
            console.error("Error:", data.error);
          }
        } catch (error) {
          console.error("Error:", error);
        }

        this.newMessage = "";
        this.$refs.messageInput.focus();
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
          this.analyzeSentiment(conversations);
        }
      } catch (error) {
        console.error("대화 내용 가져오기 오류:", error);
      }
    },

    async analyzeSentiment(conversations) {
      this.$router.push({
                path: "/SimulationResultLoading",
                query: {
                  simulationnum: this.simulationNum,
                  status: "u",
                },
              });
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

        const analysisResult = response.data.analysis_result;
        const evaluation = response.data.gpt_evaluation;

        const emotionNames = Object.keys(analysisResult.emotion_values);
        const emotionScores = Object.values(analysisResult.emotion_values);

        emotionNames.forEach((name, index) => {
          const simulresultVO = {
            simulationnum: this.simulationNum,
            emotionname: name,
            emotionscore: Math.round(emotionScores[index]),
            stressscore: Math.round(analysisResult.stress_score),
            heartrate: analysisResult.heart_rate,
            topemotion: analysisResult.top_emotion,
            topemotionscore: analysisResult.top_emotion_score,
            topemotionimg: analysisResult.top_emotion_frame,
            evaluation: evaluation,
          };

          axios
            .post(
              `${process.env.VUE_APP_BACK_END_URL}/simulresults/simulresultinsert`,
              simulresultVO,
              {
                headers: {
                  "Content-Type": "application/json",
                },
              }
            )
            .then((response) => {
              console.log("Spring에서 받아온 값", response.data);
            })
            .catch((error) => {
              console.error("Error sending data to Spring:", error);
            });
        });
      } catch (error) {
        console.error("감정 분석 오류:", error);
      }
    },

    handleExit() {
      this.fetchUserConversations();
    },
    resetSimulation() {
      if (
        confirm(
          "시뮬레이션을 초기화하면 채팅 내용이 모두 삭제됩니다. 계속 하시겠습니까?"
        )
      ) {
        this.messages = [];
        this.newMessage = "";
        this.scenario = "";
        this.simulationStarted = false;
        this.isSimulationStarted = false;
        this.userMessageCount = 0;
        this.showExitButton = false;
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
    setTimeout(() => {
      this.showCloseButton = false;
      this.showExitButton = false;
    }, 5000);
  },
};
</script>

<style scoped>
.main-container {
  display: flex;
  justify-content: center;
}

.main {
  display: flex;
  justify-content: center;
  flex-direction: column;
  align-items: center;
}
.scenario-input-container {
  margin-bottom: 20px;
  display: flex;
  justify-content: center;
  gap: 10px;
}

.chat-container {
  width: 1000px;
  display: flex;
  justify-content: center;
}

.chat {
  width: 100%;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 10px;
  height: 600px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.messages {
  max-height: calc(100% - 60px);
  overflow-y: auto;
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
  height: 50px;
  gap: 10px;
}

input {
  flex: 1;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
}

.simulVideo3 {
  margin-top: 50px;
}
.simulChat1 {
  padding: 0 20px;
  height: 100%;
  border: none;
  border-radius: 5px;
  line-height: 50px;
  background-color: #337df0;
}
.simulChat2 {
  background-color: #337df0;
}
.reset-button {
  background-color: #979797;
  color: #fff;
  border: none;
  padding: 10px;
  border-radius: 5px;
  cursor: pointer;
}

.reset-button:hover {
  background-color: #eb4848;
}

.container-xxl-simul {
  margin-bottom: 300px;
}
</style>

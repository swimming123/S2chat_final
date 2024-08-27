/*UI개발:이지영*/
<template>
  <div>
    <div class="main">
      <div class="container-xxl py-5">
        <div class="container">
          <div class="section-title text-center">
            <h1 class="display-5 mb-5">시뮬레이션 진단 결과</h1>
            <!-- 모달창 -->
            <div
              class="modal fade"
              id="resultModal"
              tabindex="-1"
              aria-labelledby="resultModalLabel"
              aria-hidden="true"
            >
              <div class="modal-dialog simulresult-modal-lg">
                <div class="modal-content">
                  <div class="modal-header">
                    <h5 class="modal-title" id="resultModalLabel">
                      시뮬레이션 내역
                    </h5>
                    <button
                      type="button"
                      class="btn-close"
                      data-bs-dismiss="modal"
                      aria-label="Close"
                    ></button>
                  </div>
                  <div class="modal-body scrollable-modal-body">
                    <table class="table">
                      <thead>
                        <tr>
                          <th>번호</th>
                          <th>제목</th>
                          <th>번호</th>
                          <th>카테고리</th>
                          <th>유형</th>
                          <th>날짜</th>
                          <th>액션</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr
                          v-for="(result, index) in modalResults"
                          :key="result.num"
                        >
                          <td>{{ index + 1 }}</td>
                          <td>{{ result.title }}</td>
                          <td>{{ result.num }}</td>
                          <td>{{ result.category }}</td>
                          <td>{{ result.type }}</td>
                          <td>{{ result.simuldate }}</td>
                          <td>
                            <button
                              class="btn btn-info btn-sm"
                              @click="viewDetails(result)"
                            >
                              자세히 보기
                            </button>
                            <button
                              class="btn btn-danger btn-sm"
                              @click="deleteResult(result)"
                            >
                              삭제
                            </button>
                          </td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                  <div class="modal-footer">
                    <button
                      type="button"
                      class="btn btn-secondary"
                      data-bs-dismiss="modal"
                    >
                      닫기
                    </button>
                  </div>
                </div>
              </div>
            </div>
            <div class="mb-4">
              <button class="btn btn-primary" @click="showModal">
                시뮬레이션 내역 선택
              </button>
            </div>

            <!-- 검사결과 항목 시작 -->
            <div class="d-flex justify-content-center">
              <div class="simulRes8">
                <div class="mb-4">
                  <h4 class="simulRes9">[{{ selectedResult.title }}]</h4>
                </div>
                <div class="d-flex justify-content-center">
                  <div class="d-flex mt-4 align-items-start">
                    <!-- 이미지와 내용 영역을 flex로 배치 -->
                    <div class="profile-image d-flex align-items-center">
                      <img
                        :src="
                          simuldjango[0]?.topemotionimg === 'None'
                            ? '/images/simulation/profile.jpg'
                            : simuldjango[0]?.topemotionimg
                        "
                        class="simulRes1"
                        alt="Profile Image"
                      />
                      <!-- 스트레스 지수 관련 코드 -->
                      <div v-if="simuldjango[0]?.stressscore > 0">
                        <div class="section-title text-center mx-3">
                          <h2 class="simulRes4">
                            {{ membernum }}님의
                          </h2>
                          <h2 class="simulRes4">스트레스 지수는</h2>
                        </div>
                        <div class="simulRes2">
                          <p
                            :style="{
                              fontSize: 'larger',
                              fontWeight: 'bolder',
                              color: stressColor,
                            }"
                          >
                            {{ simuldjango[0]?.stressscore }}%
                          </p>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="content-area mt-4 row">
                  <div class="row g-3">
                    <div class="col-6">
                      <canvas id="pie-chart"></canvas>
                    </div>
                    <div class="col-6 simulRes2">
                      <h3 style>가장 높은 점수의 감정은?</h3>
                      <p
                        :style="{
                          fontSize: 'larger',
                          fontWeight: 'bolder',
                          color: emotionColor,
                        }"
                      >
                        {{ simuldjango[0]?.topemotion }}
                        {{ simuldjango[0]?.topemotionscore }}%
                      </p>
                    </div>
                  </div>
                </div>
                <!-- 워드 클라우드를 표시할 영역 -->
                <div>
                  <h2 class="simulRes9 mt-5">대화중 가장 많이 쓴 단어</h2>
                  <div class="content-area mt-4 row">
                    <div class="col-12">
                      <img
                        :src="wordcloudImage"
                        alt="워드 클라우드"
                        style="width: 100%; height: 400px; object-fit: contain;"
                        v-if="wordcloudImage"
                      />
                      <p v-else>워드 클라우드를 생성하는 중입니다...</p>
                    </div>
                  </div>
                </div>
                <div class="section-title text-center row d-flex justify-content-center">
                  <div class="simulRes12 col-md-9">
                  <h2 class="simulRes10">총 평가</h2>
                  <p class="simulRes11 mt-4">
                    {{ simuldjango[0]?.evaluation }}
                  </p>
                </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="section-title text-start mt-4 text-center">
          <button @click="handleStart" class="simulationIntro3">
            시뮬레이션 시작 페이지로
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { Chart, registerables } from "chart.js";
import WordCloud from "wordcloud";
Chart.register(...registerables);

export default {
  name: "SimulationResult",
  data() {
    return {
      simulationResults: [],
      selectedResult: {},
      simuldjango: [],
      simuldresult: {},
      selectedDjango: {},
      modalResults: [],
      wordcloudImage: null,
      simulationnum: "",
      status: "",
      analysisResult: null,
      membernum: localStorage.getItem("nick") || "" 
    };
  },
  computed: {
    stressColor() {
      if (!this.simuldjango[0] || this.simuldjango[0]?.stressscore == null) {
        return "#000000"; // 기본 색상
      }

      const stressScore = this.simuldjango[0]?.stressscore;
      if (stressScore < 30) {
        return "#A2D9A1"; // 낮은 스트레스 - 초록색
      } else if (stressScore < 60) {
        return "#FFB86C"; // 중간 스트레스 - 노란색
      } else {
        return "#FF6F6F"; // 높은 스트레스 - 빨간색
      }
    },
    emotionColor() {
      const emotion = this.simuldjango[0]?.topemotion;
      switch (emotion) {
        case "분노":
          return "#FF6F6F";
        case "불안":
          return "#FFB86C";
        case "행복":
          return "#F0E68C";
        case "중립":
          return "#C0E0C4";
        case "놀람":
          return "#A2D9A1";
        case "슬픔":
          return "#A2C2E0";
        case "공포":
          return "#BFA2D6";
        default:
          return "#000000"; // 기본 색상
      }
    },
  },
  mounted() {
    window.scrollTo(0, 0);
    this.fetchSimulationTable();
    this.simulationnum = this.$route.query.simulationnum;
    this.status = this.$route.query.status;
  },
  methods: {
    async fetchSimulationTable() {
      try {
        const response = await axios.get(
          `${process.env.VUE_APP_BACK_END_URL}/simulations/simulationlist`
        );
        const data = response.data;
        this.simulationResults = data;
        if (this.simulationResults.length > 0) {
          this.selectedResult = this.simulationResults[0];
          this.createCharts();
          await this.fetchUserConversations();
          await this.loadAndOrder();
        }
      } catch (error) {
        console.error("Error fetching simulation results:", error);
      }
    },
    showModal() {
      this.modalResults = this.simulationResults;
      const modal = new bootstrap.Modal(document.getElementById("resultModal"));
      modal.show();
    },
    async viewDetails(result) {
      this.selectedResult = result;
      this.simulationnum = result.num;
      this.createCharts();
      await this.fetchUserConversations();
      await this.fetchSimulResult();

      const modal = bootstrap.Modal.getInstance(
        document.getElementById("resultModal")
      );
      if (modal) {
        modal.hide();
      }
    },
    async deleteResult(result) {
      try {
        await axios.delete(
          `${process.env.VUE_APP_BACK_END_URL}/simulations/simulationdelete/${result.num}`
        );
        await this.fetchSimulationTable();
        await this.fetchSimulResult();
        const modal = bootstrap.Modal.getInstance(
          document.getElementById("resultModal")
        );
        if (modal) {
          modal.hide();
        }
        alert("삭제가 완료되었습니다.");
      } catch (error) {
        console.error("삭제 중 오류 발생:", error);
        alert("삭제 중 오류가 발생했습니다.");
      }
    },
    fetchSimulResult() {
      return axios
        .get(
          `${process.env.VUE_APP_BACK_END_URL}/simulresults/simulresultlist`,
          {
            params: {
              simulationnum: this.simulationnum,
            },
          }
        )
        .then((response) => {
          this.simuldjango = response.data;
        })
        .catch((error) => {
          console.error("Error fetching simulation results:", error);
        });
    },
    createCharts() {
      if (!this.selectedResult || !this.simuldjango) return;

      const ctx = document.getElementById("pie-chart").getContext("2d");
      if (this.chartInstance) {
        this.chartInstance.destroy();
      }

      // 감정 데이터 초기화
      const emotionData = [
        { name: "분노", score: 0 },
        { name: "불안", score: 0 },
        { name: "행복", score: 0 },
        { name: "중립", score: 0 },
        { name: "놀람", score: 0 },
        { name: "슬픔", score: 0 },
        { name: "공포", score: 0 },
      ];
      // 서버에서 받은 데이터를 emotionData로 변환
      this.simuldjango.forEach((result) => {
        const emotion = emotionData.find((e) => e.name === result.emotionname);
        if (emotion) {
          emotion.score = result.emotionscore;
        }
      });

      // 점수에 따라 데이터를 내림차순으로 정렬
      emotionData.sort((a, b) => b.score - a.score);

      // 고정된 색상 배열 정의
      const colorMapping = {
        분노: "#FF6F6F", // 빨간색
        불안: "#FFB86C", // 오렌지색
        행복: "#F0E68C", // 노란색
        중립: "#C0E0C4", // 녹색
        놀람: "#A2D9A1", // 연두색
        슬픔: "#A2C2E0", // 파란색
        공포: "#BFA2D6", // 보라색
      };

      // 감정 이름을 고정된 색상 배열로 매핑
      const backgroundColors = emotionData.map((e) => colorMapping[e.name]);

      // 차트 생성
      this.chartInstance = new Chart(ctx, {
        type: "pie",
        data: {
          labels: emotionData.map((e) => e.name),
          datasets: [
            {
              label: "감정차트",
              backgroundColor: backgroundColors,
              data: emotionData.map((e) => e.score),
            },
          ],
        },
        options: {
          plugins: {
            title: {
              display: true,
              text: "감정차트",
            },
          },
        },
      });
    },

    loadAndOrder() {
      this.fetchSimulResult()
        .then(() => {
          if (this.simuldjango.length > 0) {
            this.createCharts();
          }
        })
        .catch((err) => {
          console.error("loadAndOrder 오류: ", err);
        });
    },
    async fetchUserConversations() {
      try {
        const status = this.status;
        const simulationnum = this.simulationnum;
        const response = await axios.get(
          `${process.env.VUE_APP_BACK_END_URL}/simuldialogs/simuldialoglist`,
          {
            params: {
              status: status,
              simulationnum: simulationnum,
            },
          }
        );
        const conversations = response.data;
        await this.generateWordCloud(conversations);
      } catch (error) {
        console.error("Error fetching user conversations:", error);
      }
    },
    async generateWordCloud(conversations) {
      try {
        const response = await axios.post(
          `http://192.168.92:9000/simulation/generate_wordcloud`,
          {
            conversations: conversations,
          }
        );
        this.wordcloudImage = response.data.wordcloud_image;
      } catch (error) {
        console.error("Error generating word cloud:", error);
        this.wordcloudImage = null;
      }
    },
    createWordCloud(conversations) {
      if (!conversations || conversations.length === 0) {
        this.wordcloudImage = null;
        return;
      }

      const wordCounts = {};
      const allText = conversations.map((conv) => conv.conversation).join(" ");
      const words = allText
        .replace(/[^가-힣ㄱ-ㅎㅏ-ㅣa-z0-9\s]/g, "")
        .split(/\s+/)
        .filter((word) => word.length > 0);

      words.forEach((word) => {
        if (wordCounts[word]) {
          wordCounts[word] += 1;
        } else {
          wordCounts[word] = 1;
        }
      });

      const sortedWords = Object.entries(wordCounts)
        .sort((a, b) => b[1] - a[1])
        .slice(0, 10)
        .map(([word, count]) => [word, count]);

      const canvas = document.createElement("canvas");
      canvas.width = 800;
      canvas.height = 600;
      document.body.appendChild(canvas);

      WordCloud(canvas, {
        list: sortedWords,
        gridSize: 20,
        weightFactor: 50,
        fontFamily: "'Noto Sans KR', sans-serif",
        color: function (word, weight) {
          const colors = [
            "#FF5733",
            "#33FF57",
            "#5733FF",
            "#F5B041",
            "#D4AC0D",
          ];
          return colors[Math.floor(Math.random() * colors.length)];
        },
        rotateRatio: 0.5,
        backgroundColor: "#fff",
        drawOutOfBound: false,
        minSize: 10, // 단어의 최소 크기 설정
        maxSize: 30, // 단어의 최대 크기 설정
        useCanvas: true,
        click: function (word, event) {
          console.log("Clicked word:", word);
        },
      });

      setTimeout(() => {
        this.wordcloudImage = canvas.toDataURL();
        document.body.removeChild(canvas);
      }, 1000);
    },
    handleStart() {
      this.$router.push({
        path: "/SimulationIntro",
      });
    },
  },
};
</script>

<style scoped>
.wordcloud-word {
  display: inline-block;
  font-family: "Noto Sans KR", sans-serif;
  transition: color 0.3s ease, transform 0.3s ease;
}

.wordcloud-word:hover {
  color: #ff5733;
  transform: scale(1.2);
}

.profile-image {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 20px;
}

.simulRes1 {
  width: 400px;
  height: 400px;
  object-fit: cover;
  border-radius: 100%;
}

.section-title.text-center {
  flex: 1;
}

.simulRes2 {
  flex: 0 0 auto;
  text-align: center;
  font-size: 50px;
  margin: auto;
}

.simulRes4 {
  font-size: 40px;
}

.simulRes6 {
  text-align: center;
  font-size: 25px;
  font-weight: normal;
}

.simulRes8 {
  padding: 80px;
  border: solid 1px gray;
  border-radius: 50px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1), 0 6px 20px rgba(0, 0, 0, 0.1);
  margin-bottom: 50px;
}

.simulRes9 {
  font-size: 35px;
  font-weight: bolder;
}

.simulRes10 {
  font-weight: bolder;
  color: #555555;
  margin-top: 40px;
}

.simulRes11 {
  font-size: 28px;
  white-space: pre-wrap;
  word-wrap: break-word;
  overflow-wrap: break-word;
}
.content-area {
  margin: 20px 100px 0px 100px;
}

canvas {
  margin: 20px 0;
}

.card_container {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  width: 200px;
  gap: 24px;
  border: 1px solid #ebeaec;
  box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.3);
  border-radius: 16px;
  padding: 24px;
}

.card_top {
  text-align: center;
  font-weight: bold;
  font-size: 25px;
}

.card_bottom .card_name {
  font-size: 15px;
  color: #727174;
}

.card_bottom .card_date {
  font-size: 12px;
  color: #b4b4b6;
}

.scrollable-modal-body {
  max-height: 70vh;
  overflow-y: auto;
}

.simulRes12{
  background: #cacaca49;
  padding: 10px 50px 30px 50px;
  border-radius: 20px;

}

.simulresult-modal-lg{
  max-width: 70% !important;
}
</style>

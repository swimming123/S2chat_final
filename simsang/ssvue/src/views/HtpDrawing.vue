/*UI개발: 송지미 */
     <template>
      <div>
        <div class="main">
          <div class="container-xxl py-5">
            <div class="container">
              <div class="position-relative overflow-hidden">
                <div class="sub_htp_content">
                  <div class="sub_htp01" style="
                  background: url(../images/sub_htp01bg.png) center center;
                  margin-left: 0;
                  margin-bottom: 40px;
                ">
                    <p class="sub_htp01_font"><span>HTP 검사</span></p>
                    <div class="sub_sub_htp01">
                      <span>H(House), T(Tree), P(Person) 세가지 항목 검사를 통해 내면의
                        심리상태를 보다 자세히 알아볼 수 있는 검사입니다.</span>
                    </div>
                  </div>
                  <!-- 검사 선택 완료 문구-->
                  <div class="sub_htp_con">
                    <div class="sub_htp_content2">
                      <div class="htp_start_font" style="margin-bottom: 18px">
                        {{ selectedTest }} 검사를 선택하셨습니다.
                      </div>
                      <p class="sub_sub_htp02" style="margin-bottom: 18px">
                        {{ testInstruction }}
                      </p>
                      <div class="htp_start_bar" style="margin-bottom: 60px"></div>
                      <div class="htp_boardBox">
                        <div class="htp_board">
                          <!-- 좌측 배치 아이콘_보드판 가로, 세로 선택 -->
                          <div class="htp_tools ">
                            <img src="/images/htp_board_length.png" alt="세로" class="htp_toolIcon"
                              @click="setBoardOrientation('vertical')" />
                            <img src="/images/htp_board_width.png" alt="가로" class="htp_toolIcon"
                              @click="setBoardOrientation('horizontal')" />
                          </div>
                          <!-- 우측 상단 버튼들 -->
                          <div class="htp_tools htp_tools_right">
                            <img src="/images/htp_before_icon.png" alt="이전으로" class="htp_toolIcon" @click="undo" />
                            <img src="/images/htp_return_icon.png" alt="되돌리기" class="htp_toolIcon" @click="redo" />
                            <button class="htp_toolBtn" id="changeLine" @click="openModal">
                              선 선택하기
                            </button>
                            <button class="htp_toolBtn" id="clearCanvas" @click="clearCanvas">
                              다시 그리기
                            </button>
                          </div>
                        </div>
                        <!--그림판-->
                        <div class="htp_board" id="image_area">
                          <canvas id="drawingBoard" ref="drawingBoard" @mousedown="startDrawing" @mouseup="stopDrawing"
                            @mousemove="draw"></canvas>
                        </div>
                        <div id="theimage"></div>
                      </div>
                      <!-- 기존 htp_boardBox 닫기 태그 위치 변경 -->
                      <!--결과 보러가기 버튼-->
                      <div class="htp_btn">
                        <div class="select_items" id="select_items"></div>
                        <button @click="send" class="htp_compleBtn">
                          결과보러가기
                        </button>
                      </div>
                      <!-- 모달_선 변경하기 클릭시 -->
                      <!-- 모달 HTML 수정 -->
                      <div v-if="isModalOpen" class="htp_modal">
                        <div class="htp_modal-content">
                          <span class="htp_close" @click="closeModal">&times;</span>
                          <div class="line-options">
                            <div class="htp_modal_buttons">
                              <button class="lineBtn" @click="changeLine('thin')">
                                <img src="/images/line_img1.png" alt="실선" class="line-img" />
                                실선
                              </button>
                              <button class="lineBtn" @click="changeLine('medium')">
                                <img src="/images/line_img2.png" alt="얇은선" class="line-img" />
                                얇은선
                              </button>
                              <button class="lineBtn" @click="changeLine('thick')">
                                <img src="/images/line_img3.png" alt="거친선" class="line-img" />
                                거친선
                              </button>
                            </div>
                            <p style="color: red">
                              선 선택시 전체 그림의 선이 변경됩니다.
                            </p>
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
import axios from "axios";
export default {
  data() {
    return {
      imageUrls: [],
      isModalOpen: false,
      drawing: false,
      undoStack: [],
      redoStack: [],
      boardOrientation: "horizontal",
      isDrawing: false, // 커서 클릭 시 그리기 상태 추가
      selectedLineType: 1,
      testType: this.$route.params.type || "house",
    };
  },
  computed: {
    selectedTest() {
      switch (this.testType) {
        case "house":
          return "집(House)";
        case "tree":
          return "나무(Tree)";
        case "men":
          return "남자(men)";
        case "female":
          return "여자(female)";
        default:
          return "집(House)";
      }
    },
    testInstruction() {
      switch (this.testType) {
        case "house":
          return "집 그림을 그려 주세요.";
        case "tree":
          return "나무 그림을 그려 주세요.";
        case "men":
          return "남자사람 그림을 그려 주세요.";
        case "female":
          return "여자사람 그림을 그려 주세요.";
        default:
          return "집 그림을 그려 주세요.";
      }
    },
  },
  methods: {
    send() {
      // 캔버스 요소 가져오기
      const canvas = document.getElementById("drawingBoard");

      // 배경을 다시 설정하여 투명 영역이 없도록 함
      const tempCanvas = document.createElement("canvas");
      tempCanvas.width = canvas.width;
      tempCanvas.height = canvas.height;
      const tempContext = tempCanvas.getContext("2d");
      tempContext.fillStyle = "white";
      tempContext.fillRect(0, 0, tempCanvas.width, tempCanvas.height);
      tempContext.drawImage(canvas, 0, 0);

      // 캔버스를 PNG 데이터 URL로 변환
      var dataUrl = tempCanvas.toDataURL("image/png");

      // base64 데이터 디코딩 및 Uint8Array로 변환
      var byteString = window.atob(dataUrl.split(",")[1]);
      var arrayBuffer = new Uint8Array(byteString.length);
      for (var i = 0; i < byteString.length; i++) {
        arrayBuffer[i] = byteString.charCodeAt(i);
      }

      // Blob 생성
      var myBlob = new Blob([arrayBuffer], { type: "image/png" });

      // Blob을 File로 변환
      var file = new File([myBlob], "blobtofile.png");
      console.log(file);

      // FormData 객체 생성 및 데이터 추가
      var formData = new FormData();
      formData.append("image", file);
      console.log(this.testType);

      // Axios를 사용하여 데이터 전송
      axios
        .post(
          `http://192.168.92:9000/htp/${this.testType}`,
          formData,
          {
            headers: {
              "Content-Type": "multipart/form-data",
            },
          }
        )
        .then((response) => {
          //this.imageUrls = response.data.image_urls;
          this.$router.push({
            name: "HtpTestResult",
            params: { type: this.testType },
            query: {
              imageUrls: JSON.stringify(response.data.image_urls),
              res_labels: JSON.stringify(response.data.res_labels),
            },
          });
        })
        .catch((error) => {
          console.error("파일 업로드 실패", error);
        });
    },
    openModal() {
      this.isModalOpen = true;
    },
    closeModal() {
      this.isModalOpen = false;
    },
    clearCanvas() {
      const canvas = this.$refs.drawingBoard;
      const context = canvas.getContext("2d");
      context.clearRect(0, 0, canvas.width, canvas.height);
      this.saveState();
    },
    changeLine(type) {
      this.selectedLineType = type; // 선택된 선 유형 설정
      this.closeModal();
    },
    setBoardOrientation(orientation) {
      console.log("Orientation change to:", orientation); // 디버그용 로그
      this.boardOrientation = orientation;
      this.clearCanvas(); // 그림판을 초기화
      this.$nextTick(() => {
        this.resizeCanvas();
      });
      this.saveState(); // 상태 저장
    },
    resizeCanvas() {
      const canvas = this.$refs.drawingBoard;
      if (!canvas) {
        console.error("Canvas element not found");
        return;
      }
      const context = canvas.getContext("2d");

      if (this.boardOrientation === "horizontal") {
        canvas.width = 1000;
        canvas.height = 600;
      } else {
        canvas.width = 600;
        canvas.height = 800;
      }

      context.clearRect(0, 0, canvas.width, canvas.height);

      canvas.style.width = canvas.width + "px";
      canvas.style.height = canvas.height + "px";
    },
    getCursorPosition(event) {
      const canvas = document.getElementById("drawingBoard");
      const rect = canvas.getBoundingClientRect();
      return {
        x: (event.clientX - rect.left) * (canvas.width / rect.width),
        y: (event.clientY - rect.top) * (canvas.height / rect.height),
      };
    },
    draw(event) {
      if (!this.selectedLineType || !this.isDrawing) return;
      const canvas = this.$refs.drawingBoard;
      const context = canvas.getContext("2d");
      context.lineCap = "round";
      context.strokeStyle = "#000";
      context.lineWidth = this.getLineWidth();

      const pos = this.getCursorPosition(event);
      context.lineTo(pos.x, pos.y);
      context.stroke();
      context.beginPath();
      context.moveTo(pos.x, pos.y);
    },
    startDrawing(event) {
      const pos = this.getCursorPosition(event);
      const canvas = this.$refs.drawingBoard;
      const context = canvas.getContext("2d");
      context.beginPath();
      context.moveTo(pos.x, pos.y);
      this.isDrawing = true; // 그리기 상태 시작
    },
    stopDrawing() {
      const canvas = this.$refs.drawingBoard;
      const context = canvas.getContext("2d");
      context.beginPath();
      this.isDrawing = false; // 그리기 상태 종료
      if (this.saveState) {
        this.saveState();
      }
    },
    saveState() {
      const canvas = this.$refs.drawingBoard;
      this.undoStack.push(canvas.toDataURL());
      this.redoStack = [];
    },

    undo() {
      if (this.undoStack.length > 0) {
        const canvas = this.$refs.drawingBoard;
        const context = canvas.getContext("2d");
        this.redoStack.push(this.undoStack.pop());
        const imageData = this.undoStack[this.undoStack.length - 1];
        const img = new Image();
        img.src = imageData;
        img.onload = () => {
          context.clearRect(0, 0, canvas.width, canvas.height);
          context.drawImage(img, 0, 0);
        };
      }
    },

    redo() {
      if (this.redoStack.length > 0) {
        const canvas = this.$refs.drawingBoard;
        const context = canvas.getContext("2d");
        const imageData = this.redoStack.pop();
        this.undoStack.push(canvas.toDataURL());
        const img = new Image();
        img.src = imageData;
        img.onload = () => {
          context.clearRect(0, 0, canvas.width, canvas.height);
          context.drawImage(img, 0, 0);
        };
      }
    },
    getLineWidth() {
      switch (this.selectedLineType) {
        case "thin":
          return 1;
        case "medium":
          return 3;
        case "thick":
          return 5;
        default:
          return 2; // 기본 선 두께
      }
    },
  },
  mounted() {
    this.$nextTick(() => {
      const canvas = this.$refs.drawingBoard;
      this.resizeCanvas();

      canvas.addEventListener("mousedown", this.startDrawing);
      canvas.addEventListener("mouseup", this.stopDrawing);
      canvas.addEventListener("mousemove", this.draw);

      window.addEventListener("resize", this.resizeCanvas.bind(this));

      this.saveState(); // Initialize the undo stack with the initial state
    });
  },
};
</script>

<style>
.main {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
}

.container-xxl,
.container {
  width: 100%;
  max-width: 1500px;
  padding: 0 15px;
  margin: 0 auto;
}

.sub_htp01 {
  background: url("/public/images/sub_htp01bg.png") center center;
  margin-left: 0;
  margin-bottom: 40px;
  margin-top: 20px;
  height: 220px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.sub_htp01_font {
  font-size: 24px;
  color: white;
  text-align: center;
}

.sub_sub_htp01 {
  color: white;
  text-align: center;
  font-size: 15px;
  margin-top: 10px;
}

.sub_sub_htp02 {
  text-align: center;
  font-size: 15px;
}

.sub_htp_content2 {
  text-align: center;
}

.htp_start_font {
  font-size: 20px;
  color: #2ba8d9;
  font-weight: bold;
}

.htp_start_bar {
  background-color: #2ba8d9;
  height: 2px;
  width: 10%;
  margin: 10px auto;
}

.htp_boardBox {
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 20px auto;
  width: auto;
  height: auto;
  box-sizing: border-box;
  flex-direction: column;
  padding-bottom: 40px;
  overflow: visible;
}

.htp_board {
  width: 100%;
  height: auto;
  overflow: visible;
  box-sizing: border-box;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
}

#drawingBoard {
  width: 100%;
  height: 400px;
  border: 1px solid #000;
  cursor: crosshair;
}

.htp_tools_wrapper {
  display: flex;
  justify-content: space-between;
  width: 100%;
  max-width: 1500px;
  box-sizing: border-box;
  padding: 5px 0;
  margin-bottom: -10px;
}

.htp_toolIcon {
  width: 30px;
  height: 30px;
  cursor: pointer;
  transition: transform 0.3s ease-out;
}

.htp_toolIcon:active {
  transform: scale(1.2);
  animation: pop 0.4s ease-out forwards;
}

@keyframes pop {
  0% {
    box-shadow: 0 0 0 0 rgba(72, 237, 240, 0.5);
  }

  100% {
    box-shadow: 0 0 0 20px #c65c9100;
  }
}

.htp_tools {
  display: flex;
  flex-direction: row;
  gap: 10px;
  align-items: center;
  max-width: 100%;
  box-sizing: border-box;
}

.htp_toolBtn {
  background-color: #ca347a;
  color: white;
  padding: 4px 8px;
  border: none;
  border-radius: 20px;
  cursor: pointer;
  font-size: 12px;
  font-weight: bold;
  text-align: center;
  transition: background-color 0.3s;
  line-height: 1;
  height: 30px;
}

.htp_tools_left {
  display: flex;
  flex-direction: column;
  gap: 10px;
  position: absolute;
  top: 30px;
  left: 30px;
  box-sizing: border-box;
}

.htp_tools_right {
  margin-right: 20px;
}

#changeLine {
  background-color: #3498db;
}

#changeLine:hover {
  background-color: #e38b10;
}

#clearCanvas {
  background-color: #69cdc1;
}

#clearCanvas:hover {
  background-color: #ca347a;
}

.htp_compleBtn,
.htp_delBtn {
  background-color: #2ba8d9;
  color: #fff;
  font-weight: 1000;
  padding: 10px 20px;
  text-decoration: none;
  border-radius: 4px;
  text-align: center;
}

.htp_btn {
  display: flex;
  justify-content: center;
  margin-top: 10px;
}

.htp_modal {
  display: block;
  position: fixed;
  z-index: 1;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgb(0, 0, 0);
  background-color: rgba(0, 0, 0, 0.4);
  padding-top: 60px;
}

.htp_modal-content {
  background-color: #fefefe;
  margin: 5% auto;
  padding: 20px;
  border: 1px solid #888;
  width: 80%;
  max-width: 400px;
  text-align: center;
  border-radius: 10px;
}

.htp_close {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.htp_close:hover,
.htp_close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}

.line-options img {
  width: 100%;
  height: auto;
  margin-bottom: 10px;
}

.htp_modal_buttons {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
  margin-bottom: 10px;
}

.lineBtn {
  background-color: #8be1d9;
  color: white;
  padding: 8px 12px;
  border: none;
  border-radius: 20px;
  cursor: pointer;
  font-size: 14px;
  font-weight: bold;
  text-align: center;
}

.lineBtn:hover {
  background-color: #ca347a;
  color: #ffffff;
  transition: background-color 0.3s, color 0.3s;
}
</style>

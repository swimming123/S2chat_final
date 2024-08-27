/*UI개발: 김건우 */
<template>
  <div>
   <div class="main">
            <div class="container-xxl py-5">
                <div class="container">
                    <div class="position-relative overflow-hidden">                 
                    <div class="section-title text-center">
        <div class="diaryTextView_app">
          <h1>감정일기장</h1>
          <!-- 일기장 컨텐츠 -->
          <div class="diary-content">
            <!-- 모달창 -->
            <div v-if="isModalOpen" id="modal" class="diaryChoose_modal">
              <div class="diaryChoose_modal-content">
                <span @click="closeModal" class="diaryChoose_close">&times;</span>
                <div class="diaryChoose_modal-split">
                  <a href="DiaryTalkView" class="diaryChoose_modal-links diaryChoose_left">
                    <div>말로 쓸래</div>
                  </a>
                  <a href="DiaryTextView" class="diaryChoose_modal-links diaryChoose_right">
                    <div>글로 쓸래</div>
                  </a>
                </div></div>
              </div>
            </div>
            <!-- 일기장 내용을 여기에 추가 -->
          </div>
        </div>
      </div>
    </div>
  </div>
   </div>
      </div>
      
</template>

<script>
export default {
  name: "DiaryTextView",
  data() {
    return {
      isModalOpen: true
    };
  },
  methods: {
    closeModal() {
      this.isModalOpen = false;
    }
  }
};
</script>

<style scoped>
.diaryTextView_app {
  flex: 1;
  max-width: 600px;
  margin: 0 auto;
  font-family: 'Arial', sans-serif;
  padding: 20px;
  background: linear-gradient(to right, #f8f9fa, #e9ecef);
  border-radius: 25px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  height: 80vh;
  overflow: auto;
  margin-bottom: 39px;
  position: relative;
}

.diary-content {
  position: relative;
  width: 100%;
  height: 90%;
}

.diaryChoose_modal {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.6);
  border-radius: 25px;
  animation: fadeIn 0.5s ease forwards;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: scale(0.8);
  }
  to {
    opacity: 1;
    transform: scale(1);
  }
}

.diaryChoose_modal-content {
  background: linear-gradient(135deg, #81d8d0, #6dd5ed);
  padding: 30px;
  border-radius: 10px;
  width: 80%;
  max-width: 400px;
  height: 60%;
  color: white;
  text-align: center;
  display: flex;
  flex-direction: column;
  justify-content: center;
  position: relative;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
  animation: fadeIn 0.5s ease forwards;
}

.diaryChoose_close {
  color: white;
  position: absolute;
  top: 10px;
  right: 10px;
  font-size: 28px;
  font-weight: bold;
  cursor: pointer;
  transition: color 0.3s ease;
}

.diaryChoose_close:hover {
  color: #ff6b6b;
}

.diaryChoose_modal-split {
  display: flex;
  width: 100%;
  height: 100%;
  justify-content: space-between;
}

.diaryChoose_modal-links {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
  text-decoration: none;
  transition: background 0.3s ease, transform 0.3s ease;
  border-radius: 10px;
  margin: 0 10px;
  padding: 10px;
}

.diaryChoose_left {
  border-right: 1px solid rgba(255, 255, 255, 0.5);
}

.diaryChoose_right {
  border-left: none;
}

.diaryChoose_modal-links div {
  color: white;
  font-size: 18px;
  font-weight: bold;
}

.diaryChoose_modal-links:hover {
  background: rgba(255, 255, 255, 0.2);
  transform: scale(1.05);
}

.diaryChoose_modal-links:hover div {
  text-decoration: underline;
}
.container-xxl{
  margin-top: -100px;
}

.container-xxl{
  margin-top: -170px;
}
</style>

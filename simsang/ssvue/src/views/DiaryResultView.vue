/*UI개발: 김건우 */
<template>
  <div class="main">
    <div class="container-xxl py-5">
      <div class="container">
        <div class="position-relative overflow-hidden">
          <div class="section-title text-center">
            <h1>감정일기 결과</h1>
            <div class="diaryResultView_content-wrapper">
              <div class="diaryResultView_app">
                <h2>{{ entry.title }}</h2>
                <div class="diaryResultView_emotions">
                  <span
                    v-for="(emotion, index) in entry.emotions"
                    :key="index"
                    :class="getEmotionColorClass(emotion)"
                  >
                    {{ emotion }}
                  </span>
                </div>
                <p>{{ entry.content }}</p>
                <div class="diaryResultView_button-container">
                  <button class="diaryResultView_simulationIntro3" @click="goBack">돌아가기</button>
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
      entry: {
        title: '',
        content: '',
        emotions: []
      }
    };
  },
  methods: {
    async loadEntry() {
      try {
        const num = this.$route.params.num;
        const response = await axios.get(`http://localhost/finalproject/diary/detail/11`);
        const data = response.data;

        this.entry = {
          title: data.title,
          content: data.content,
          emotions: data.emotions
        };
      } catch (error) {
        console.error('Error fetching diary results:', error);
      }
    },
    getEmotionColorClass(emotion) {
      if (emotion === '행복하다') return 'happy';
      if (emotion === '화난다') return 'angry';
      if (emotion === '외롭다') return 'lonely';
      if (emotion === '감동했다') return 'touched';
      return '';
    },
    goBack() {
      this.$router.push('/DiaryTextView');
    }
  },
  mounted() {
    this.loadEntry();
  }
};
</script>

<style scoped>
.diaryResultView_content-wrapper {
  width: 80%;
  max-width: 800px;
  margin: 0 auto;
}

.diaryResultView_app {
  text-align: center;
}

.diaryResultView_emotions span {
  display: inline-block;
  margin: 0 5px;
  padding: 5px 10px;
  border-radius: 15px;
  color: white;
}

.diaryResultView_emotions .happy {
  background-color: #ffeb3b;
}

.diaryResultView_emotions .angry {
  background-color: #f44336;
}

.diaryResultView_emotions .lonely {
  background-color: #9e9e9e;
}

.diaryResultView_emotions .touched {
  background: linear-gradient(to right, red, orange, yellow, green, blue, indigo, violet);
}

.diaryResultView_button-container {
  margin-top: 30px;
}

.diaryResultView_simulationIntro3 {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  background-color: #007bff;
  color: white;
  cursor: pointer;
  transition: background-color 0.3s;
}

.diaryResultView_simulationIntro3:hover {
  background-color: #0056b3;
}
</style>

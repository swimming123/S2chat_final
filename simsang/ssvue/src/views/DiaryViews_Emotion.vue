/*UI개발: 박성호 */
<template>
  <div class="diaryViews_emotion_Main" >
    <div class="dropdown">
      <button class="simulationIntro44">{{ howEmotion }}</button>
      <div class="dropdown-content">
        <a
          v-for="(link, index) in links"
          :key="index"
          href="#"
          @click.prevent="updateEmotion(link)">
          {{ link }}
        </a>
      </div>
    </div>

    

    <!-- 조건부 렌더링을 사용하여 howEmotion이 기본 값이 아닐 때만 now_Emotion을 표시 -->
    <div class="now_Emotion" v-if="howEmotion !== '나의 어떤 감정의 날을 보고싶나요?'" style="margin-top: 30px;">
      <hr>
      <h1>가장 최근의 <a style="color: #337DF0;">{{ howEmotion }}</a> 이에요!</h1>
    <br>

    <div class="navigation">
      <button class="simulationIntro44">이전</button>
      <h3 style="margin-top: 30px;">2024년 7월 11일</h3>
      <button class="simulationIntro44">이후</button>
    </div>
  
    <h1>{{ today_Title }}</h1>
    <br>

    <table class="diaryEmotion_table" style="margin-top: 20px;">
  <tr class="diaryEmotion_table_header">
    <td colspan="2">
      <h2>일기</h2><br>
    </td>
    <td>
      <h2>분석</h2><br>
    </td>
  </tr>

  <tr>
    <td>
      <div class="inner-content">
        <h4>감정카드</h4>
      </div>
    </td>
    <td>
      <div class="inner-content">
        <h4>감정 색깔</h4>
      </div>
    </td>
    <td>
      <div class="inner-content">
        <h3>보라색을 선택한 사람들은 싸이코래요!</h3>
      </div>
    </td>
  </tr>

  <tr>
    <td>
      <div class="inner-content">
        <img class="card-img-top" src="images/noimg.png" style="width: 250px; height: 300px; margin: 10px auto; display: block;">
      </div>
    </td>
    <td>
      <div class="inner-content">
        <div style="width: 200px; height: 200px; border: 1px solid black; background-color: purple; border-radius: 50%;"></div>
      </div>
    </td>
    <td>
      <div class="inner-content">
        <input type="text" class="form-control" 
                style="width: 700px; height: 300px; "
                placeholder="싸이코 분석 자리" >
      </div>
    </td>
  </tr>

  <tr>
    <td colspan="2">
      <div class="inner-content">
        <h2>돌아보기</h2><br>
        <input type="text" class="form-control" 
        style="width: 700px; height: 300px;"
                placeholder="행복한 오늘은 너무너무 행복한 하루였다. 친구들이랑 행복하게 놀고 나는 노는게 세상에서 제일 행복하다. 그리고 집이 제일 좋다. 내일도 또 놀아야징" >
      </div>
    </td>
    <td>
      <div class="inner-content">
        <h2>일기 분석</h2><br>
        <input type="text" class="form-control" 
        style="width: 700px; height: 300px;"
                placeholder="이번 일기에서 '행복'이라는 단어가 가장 많이 언급이 되었네요~? 행복하세요~" >
                
      </div>
    </td>
  </tr>
</table>

    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      today_Title: '오늘은 생일이라서 너무 즐거웠다!',
      howEmotion: '나의 어떤 감정의 날을 보고싶나요?',
      links: ['행복의 날', '기쁨의 날', '슬픔의 날', '우울의 날'], // 드롭다운 항목들
    };
  },
  methods: {
    updateEmotion(emotion) {
      this.howEmotion = emotion;
    },
  },
};
</script>

<style>

.diaryViews_emotion_Main{
  margin: auto;
  height: 100vh;
  text-align: center;
}

.simulationIntro44 {
  text-align: center;
  margin-top: 30px;
  background-color: #fff;
  color: #41C1BA;
  font-weight: 1000;
  padding: 10px 20px;
  font-size: 1.5em;
  border-radius: 25px;
  border: 3px solid #41C1BA;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.simulationIntro44:hover {
  background-color: #41C1BA;
  color: #fff;
  font-weight: 1000;
}

.dropdown {
  position: relative;
  display: inline-block;
  z-index: 3;
}

.dropdown-content {
  display: none;
  position: absolute;
  top: 100%;
  left: 50%; 
  transform: translateX(-50%);
  min-width: 100%;
  box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
  z-index: 3;
  border-radius: 25px;
  background-color: azure;
}

.dropdown-content a:hover {
  background-color: #41C1BA;
  color: #f9f9f9;
  border-radius: 25px;
}

.dropdown-content a {
  color: black;
  text-align: center;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
}

.dropdown:hover .dropdown-content {
  display: block;
}
.diaryEmotion_table {
  width: 100%;
  border-collapse: separate;
  border-spacing: 0;
  text-align: center;
}
.inner-content {
  margin-top: 50px;
  margin-left: 100px;
  margin-right: 100px;
}


  @media (max-width: 768px) {
  .diaryEmotion_table {
    display: flex;
    flex-direction: column;
  }

  .diaryEmotion_table_header {
    display: none;
  }

  tr {
    display: flex;
    flex-direction: column;
    margin-bottom: 20px;
  }

  td {
    display: block;
    width: 100%;
    box-sizing: border-box;
  }

  td div.inner-content {
    width: auto;
    margin: 0 auto;
  }

  td div.inner-content input.form-control, td div.inner-content div {
    width: 100%;
  }
}
.navigation {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 40px;
  margin-bottom: 60px;
}

</style>

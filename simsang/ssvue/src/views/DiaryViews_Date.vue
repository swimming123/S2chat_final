/*UI개발: 박성호 */
<template>
  <div class="diaryViews_date_Main">
    
    <table class="diaryDate_table" style="margin-top: 20px;">
      <tr>
        <td>
          <h2 style="margin-bottom: 30px;">돌아보고싶은 날을 선택해주세요</h2>
          <DatePicker class="DiaryViews_DatePicker" v-model="date"></DatePicker>
        </td>
      </tr>
      <tr>
        <td>
          <button class="simulationIntro3" id="v-pills-heart-tab" type="button"
                    @click="handleButtonClick"
                  style="margin-top: 30px;">선택완료</button>
        </td>
      </tr>
  </table>
 

  <div v-if="showContent">
    <br><hr>
    <div class="diaryDate_navigation" id="diaryDate_navigation">
      <button class="simulationIntro44">이전</button>
      <h3 style="margin-top: 30px;">2024년 7월 11일</h3>
      <button class="simulationIntro44">이후</button>
    </div>
  
    <h1>{{ today_Title }}</h1>
    <br>

    <table class="diaryDate_table" style="margin-top: 20px;">
  <tr class="diaryDate_table_header">
    <td colspan="2">
      <h2>일기</h2><br>
    </td>
    <td>
      <h2>7월의 감정</h2><br>
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
        <h3>이번달 가장 많았던 기분은 <a style="color: dodgerblue; font-weight: bold;">행복</a>입니다!</h3>
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
        <div style="width: 200px; height: 200px; border: 1px solid black; background-color: dodgerblue; border-radius: 50%;"></div>
      </div>
    </td>
    <td>
      <div class="inner-content">
        <div class="card mb-3" style="max-width: 700px; height: 350px;">
          <div class="row g-0">
            <div class="col-md-4">
              <img src="images/noimg.png" class="img-fluid rounded-start">
            </div>

            <div class="col-md-8">
              <div class="card-body">
                <h2 class="card-title" style="margin-bottom: 20px;">행복...</h2>
                <h3 class="card-text">행복이란 무엇인가..있다가도 없는 것인가...집에가는 것...그것이 행복일지어다...집가자...</h3>
              </div>
            </div>
          </div>
        </div>
    </div>
    </td>
  </tr>

  <tr>
    <td colspan="2">
      <div class="inner-content">
        <h2>돌아보기</h2><br>
        <input type="text" class="form-control" 
        style="width: 700px; height: 300px;"
                placeholder="집에잇는데 집에가고싶은 나는 집돌이 집집집이제일좋아" >
      </div>
    </td>
    <td>
      <div class="inner-content">
        <h2>일기 분석</h2><br>
        <input type="text" class="form-control" 
        style="width: 700px; height: 300px;"
                placeholder="이번 일기에서 '집'이라는 단어가 가장 많이 언급이 되었네요~? 집가세요~" >
                
      </div>
    </td>
  </tr>
</table>
</div>
</div>
  
</template>

<script>
import { Calendar, DatePicker } from 'v-calendar';
import 'v-calendar/style.css';


export default {
  name: 'ChartComponent',
  components: {
    Calendar,
    DatePicker,
  },
  data() {
    return {
      showContent: false,
      today_Title: '오늘은 생일이라서 너무 즐거웠다!',
      date: new Date(),
    };
  },
  methods: {
    handleButtonClick() {
      this.showContent = true;
      this.$nextTick(() => {
        this.scrollToNavigation();
      });
    },
    scrollToNavigation() {
      const targetElement = document.getElementById('diaryDate_navigation');
      if (targetElement) {
        targetElement.scrollIntoView({ behavior: 'smooth', block: 'start' });
      }
    },
  }
};
</script>

<style>
.diaryViews_date_Main{
  margin: auto;
  height: auto;
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
.diaryDate_table {
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
  .diaryDate_table {
    display: flex;
    flex-direction: column;
  }

  .diaryDate_table_header {
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
.diaryDate_navigation {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 40px;
  margin-bottom: 60px;
}

</style>

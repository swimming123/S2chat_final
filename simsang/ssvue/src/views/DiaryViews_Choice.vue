/*UI개발: 박성호 */
<!-- 캘린더 참고 주소 : https://vcalendar.io/calendar/attributes.html 
      npm install @vuepic/vue-datepicker
      npm install v-calendar@next
      npm install chart.js
      npm install vue-chartjs
      npm install highcharts-vue
      npm install highcharts    
      npm install highcharts highcharts-more
      npm install highcharts highcharts-vue 
      npm install highcharts-modules-bubble 
     설치해야함***
-->
<template>
    <div>
        <table class="diaryViews_table" style="margin-top: 20px;">

            <div class="diaryViews_block">
                <div class="diaryViews_trtd" style="margin-right: 100px;">
                    <tr>
                        <td>
                            <h2>나의 시간을 기록해요!</h2><br>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <DatePicker class="DiaryViews_DatePicker" v-model="date"></DatePicker>
                            <br><br><br>
                            <button class="simulationIntro3" style="margin-bottom: 20px;">일기 작성하기</button>

                        </td>
                    </tr>
                </div>

                <div class="diaryViews_trtd">
                    <tr>
                        <td>
                            <h2>최근 일주일의 기분이에요!</h2><br>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <figure class="highcharts-figure" style="width: 700px; height: 500px;">
                                <div id="container"></div>
                            </figure>
                        </td>
                    </tr>
                </div>
            </div>
            </table>

            
            <h2 style="text-align: center;">이번주 추천활동</h2><br>
            <div class="card-container">
              <div class="card">
                <img class="card-img-top" src="images/noimg.png" style="width: 80%; height: 90%; margin: 10px auto; display: block;">
                <div class="card-body">
                  <h4 class="card-title">운동</h4>
                  <p class="card-text">육체를 맑고 건강하게~</p>
                  <button class="btn btn-primary" style="box-shadow: 1px 1px 4px gray;">자세히 보기</button>
                </div>
              </div>
            <div class="card">
              <img class="card-img-top" src="images/noimg.png" style="width: 80%; height: 90%; margin: 10px auto; display: block;">
                <div class="card-body">
                <h4 class="card-title">독서</h4>
                <p class="card-text">정신을 맑고 건강하게~</p>
                <button class="btn btn-primary" style="box-shadow: 1px 1px 4px gray;">자세히 보기</button>
                </div>
            </div>
            <div class="card">
              <img class="card-img-top" src="images/noimg.png" style="width: 80%; height: 90%; margin: 10px auto; display: block;">
                <div class="card-body">
                <h4 class="card-title">수면</h4>
                <p class="card-text">정신과 육체를 맑게~</p>
                <button class="btn btn-primary" style="box-shadow: 1px 1px 4px gray;">자세히 보기</button>
                </div>
            </div>
            <div class="card">
              <img class="card-img-top" src="images/noimg.png" style="width: 80%; height: 90%; margin: 10px auto; display: block;">
                <div class="card-body">
                <h4 class="card-title">퇴근</h4>
                <p class="card-text">정신과 육체를 맑게~</p>
                <button class="btn btn-primary" style="box-shadow: 1px 1px 4px gray;">자세히 보기</button>
                </div>
            </div>
            <div class="card">
              <img class="card-img-top" src="images/noimg.png" style="width: 80%; height: 90%; margin: 10px auto; display: block;">
                <div class="card-body">
                <h4 class="card-title">퇴사</h4>
                <p class="card-text">정신과 육체를 맑게~</p>
                <button class="btn btn-primary" style="box-shadow: 1px 1px 4px gray;">자세히 보기</button>
                </div>
            </div>
            </div>

            <br><br><br>


            <h2 style="text-align: center;">이번주 <a style="color: orange;">ICT Market</a> 추천상품</h2><br>
            <div class="card-container">
                <div class="card">
                  <img class="card-img-top" src="images/noimg.png" style="width: 80%; height: 90%; margin: 10px auto; display: block;">
                  <div class="card-body">
                    <h4 class="card-title">고등어</h4>
                    <p class="card-text">등이 푸르게~</p>
                    <button class="btn711 btn-primary" style="box-shadow: 1px 1px 4px gray;">자세히 보기</button>
                  </div>
                </div>
                <div class="card">
                  <img class="card-img-top" src="images/noimg.png" style="width: 80%; height: 90%; margin: 10px auto; display: block;">
                  <div class="card-body">
                    <h4 class="card-title">소고기</h4>
                    <p class="card-text">소고소고기~</p>
                    <button class="btn711 btn-primary" style="box-shadow: 1px 1px 4px gray;">자세히 보기</button>
                  </div>
                </div>
                <div class="card">
                  <img class="card-img-top" src="images/noimg.png" style="width: 80%; height: 90%; margin: 10px auto; display: block;">
                  <div class="card-body">
                    <h4 class="card-title">비타민</h4>
                    <p class="card-text">비타비타오백~</p>
                    <button class="btn711 btn-primary" style="box-shadow: 1px 1px 4px gray;">자세히 보기</button>
                  </div>
                </div>
                <div class="card">
                  <img class="card-img-top" src="images/noimg.png" style="width: 80%; height: 90%; margin: 10px auto; display: block;">
                  <div class="card-body">
                    <h4 class="card-title">돼지곱창</h4>
                    <p class="card-text">순대도 같이~</p>
                    <button class="btn711 btn-primary" style="box-shadow: 1px 1px 4px gray;">자세히 보기</button>
                  </div>
              </div>
                <div class="card">
                  <img class="card-img-top" src="images/noimg.png" style="width: 80%; height: 90%; margin: 10px auto; display: block;">
                  <div class="card-body">
                    <h4 class="card-title">포크레인</h4>
                    <p class="card-text">포크포크~</p>
                    <button class="btn711 btn-primary" style="box-shadow: 1px 1px 4px gray;">자세히 보기</button>
                  </div>
                </div>
            </div>
              <br>
    </div>
</template>

<script>
import { Calendar, DatePicker } from 'v-calendar';
import 'v-calendar/style.css';
import Highcharts from 'highcharts';
import Highcharts3d from 'highcharts/highcharts-3d';
import HighchartsMore from 'highcharts/highcharts-more';

// Initialize 3D and Bubble modules for Highcharts
Highcharts3d(Highcharts);
HighchartsMore(Highcharts);

export default {
  components: {
    Calendar,
    DatePicker,
  },
  data() {
    return {
      date: new Date(),
    };
  },
  mounted() {
    this.renderChart();
    this.card_func();
  },
  methods: {
    card_func() {
      var cards = document.querySelectorAll('.card');
      cards.forEach(card => {
        card.addEventListener('mousemove', function(e) {
          var rect = card.getBoundingClientRect();
          var x = e.clientX - rect.left; // x 좌표
          var y = e.clientY - rect.top;  // y 좌표

          // 회전 각도 계산
          var centerX = rect.width / 2;
          var centerY = rect.height / 2;
          var rotateY = (x - centerX) / centerX * 15; // 15도까지 회전
          var rotateX = -(y - centerY) / centerY * 15; // 15도까지 회전

          card.style.transform = `perspective(1500px) rotateY(${rotateY}deg) rotateX(${rotateX}deg)`;
        });

        card.addEventListener('mouseleave', function() {
          card.style.transform = `perspective(1500px) rotateY(0deg) rotateX(0deg)`;
        });
      });
    },
    renderChart() {
      Highcharts.chart('container', {
        chart: {
          type: 'bubble',
          plotBorderWidth: 1,
          zooming: {
            type: 'xy'
          }
        },
        xAxis: {
          gridLineWidth: 1,
          accessibility: {
            rangeDescription: 'Range: 0 to 100.'
          }
        },
        yAxis: {
          startOnTick: false,
          endOnTick: false,
          accessibility: {
            rangeDescription: 'Range: 0 to 100.'
          }
        },
        series: [{
          name: '기쁨',
          data: [
            [9, 81, 63],
            [98, 5, 89],
            [51, 50, 73],
            [41, 22, 14],
            [58, 24, 20],
            [78, 37, 34],
            [55, 56, 53],
            [18, 45, 70],
            [42, 44, 28],
            [3, 52, 59],
            [31, 18, 97],
            [79, 91, 63],
            [93, 23, 23],
            [44, 83, 22]
          ],
          marker: {
            fillColor: {
              radialGradient: { cx: 0.4, cy: 0.3, r: 0.7 },
              stops: [
                [0, 'rgba(255,255,255,0.5)'],
                [
                  1,
                  Highcharts.color(
                    Highcharts.getOptions().colors[0]
                  ).setOpacity(0.5).get('rgba')
                ]
              ]
            }
          }
        }, {
          name: '우울',
          data: [
            [42, 38, 20],
            [6, 18, 1],
            [1, 93, 55],
            [57, 2, 90],
            [80, 76, 22],
            [11, 74, 96],
            [88, 56, 10],
            [30, 47, 49],
            [57, 62, 98],
            [4, 16, 16],
            [46, 10, 11],
            [22, 87, 89],
            [57, 91, 82],
            [45, 15, 98]
          ],
          marker: {
            fillColor: {
              radialGradient: { cx: 0.4, cy: 0.3, r: 0.7 },
              stops: [
                [0, 'rgba(255,255,255,0.5)'],
                [
                  1,
                  Highcharts.color(
                    Highcharts.getOptions().colors[1]
                  ).setOpacity(0.5).get('rgba')
                ]
              ]
            }
          }
        },{
          name: '분노',
          data: [
            [50, 20, 10],
            [62, 3, 70],
            [2, 12, 32],
            [30, 45, 9],
            [50, 45, 20],
            [5, 17, 16],
            [6, 86, 32],
            [50, 61,22],
            [61, 44, 56],
            [22, 97,79],
            [85, 53, 50],
            [56, 64,12],
            [51, 31, 12],
            [23, 45, 80]
          ],
          marker: {
            fillColor: {
              radialGradient: { cx: 0.4, cy: 0.3, r: 0.7 },
              stops: [
                [0, 'rgba(255,255,255,1.0)'],
                [
                  1,
                  Highcharts.color(
                    Highcharts.getOptions().colors[2]
                  ).setOpacity(0.5).get('rgba')
                ]
              ]
            }
          }
        }]
      });
    },
  },
};
</script>

<style scoped>
.diaryViews_table {
  width: auto;
  border-collapse: separate;
  border-spacing: 0; 
  text-align: center;

}

@media (max-width: 768px) {
  .diaryViews_table {
    display: block;
  }

  .diaryViews_table td {
    display: block; 
    width: 100%;
    box-sizing: border-box;
    margin-bottom: 15px;
  }
}
.diaryViews_trtd{
    display: inline;
    margin: 100px;
}
.diaryViews_block{
    display: block;
}
.card-container {
  display: flex;
  justify-content: space-around;
  text-align: center;
}

.card {
  width: 250px;
  height: 350px;
  margin: 10px;
  transition: all 0.1s;
  transform-style: preserve-3d;
  border: 2px solid rgb(216, 212, 212);
  border-radius: 15px;
  box-shadow: 1px 1px 4px gray;
}

.card-body {
  text-align: center;
}
.btn711.btn-primary{
    padding: 10px 20px;
    margin: 5px;
    background-color: orange;
    border: orange;
}
.btn711.btn-primary:hover{
    background-color: rgb(235, 235, 233);
    border: rgb(235, 235, 233);
    color: orange;
}
.btn711{
  font-weight: 500;
  transition: 0.5s;
  display: inline-block;
    line-height: 1.5;
    text-align: center;
    text-decoration: none;
    vertical-align: middle;
    cursor: pointer;
    -moz-user-select: none;
    user-select: none;
    font-size: 1rem;
    border-radius: .25rem;
}


</style>

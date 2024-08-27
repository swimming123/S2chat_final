/*UI개발: 박성호 */
<!-- npm install chart.js
     npm install vue-chartjs
     설치해야함 -->
<template>
    <div>
        <table class="diaryViews_table" style="margin-top: 20px;">

            <div class="diaryViews_block">
                <div class="diaryViews_trtd" style="margin-right: 100px;">
                    <tr>
                        <td>
                            <h2>오늘까지 가장 많이 느낀 감정은 
                                <a style="color: dodgerblue;">{{ many_emotion }}</a>이에요!
                            </h2><br>
                        </td>
                    </tr>
                    <tr>
                        <td>
                          <div class="chart-container">
                            <canvas ref="pieChart"></canvas>
                          </div>
                        </td>
                    </tr>
                </div>

                <div class="diaryViews_trtd">
                    <tr>
                        <td>
                            <h2>감정을 기반으로 가장 추천하는 활동은 
                              <a style="color: hotpink;">{{ number_One }}</a>이에요!</h2><br>
                        </td>
                    </tr>
                    <tr>
                        <td>
                          <img class="card-img-top" src="images/noimg.png" style="width: 450px; height: 300px; margin: 10px auto; display: block;">
                          <br><br><br>
                          <h3>숙면은 사랑입니다.<br>주무세요 여러분~~!!!!!!!<br>잠을 자면 행복지수가 100% 상승할거에요!</h3>
                        </td>
                    </tr>
                </div>
            </div>
            </table>

            <br><br><br><br>
            
            <h2 style="text-align: center;">감정 카드</h2><br>
            <div class="card-container">
            <div class="card">
                <img class="card-img-top" src="images/noimg.png" style="width: 245px; height: 300px; margin: 10px auto; display: block;">
                <div class="card-body">
                <h4 class="card-title">행복</h4>
                <p class="card-text">굉장히 행복하셨군요?</p>
                <button class="btn btn-primary" style="box-shadow: 1px 1px 4px gray;">자세히 보기</button>
                </div>
            </div>
            <div class="card">
                <img class="card-img-top" src="images/noimg.png" style="width: 245px; height: 300px; margin: 10px auto; display: block;">
                <div class="card-body">
                <h4 class="card-title">슬픔</h4>
                <p class="card-text">슬퍼도 힘내세요!!</p>
                <button class="btn btn-primary" style="box-shadow: 1px 1px 4px gray;">자세히 보기</button>
                </div>
            </div>
            <div class="card">
                <img class="card-img-top" src="images/noimg.png" style="width: 245px; height: 300px; margin: 10px auto; display: block;">
                <div class="card-body">
                <h4 class="card-title">우울</h4>
                <p class="card-text">우울하다면 밖으로 나가볼까?</p>
                <button class="btn btn-primary" style="box-shadow: 1px 1px 4px gray;">자세히 보기</button>
                </div>
            </div>
            <div class="card">
                <img class="card-img-top" src="images/noimg.png" style="width: 245px; height: 300px; margin: 10px auto; display: block;">
                <div class="card-body">
                <h4 class="card-title">예민</h4>
                <p class="card-text">잠을 자세요~</p>
                <button class="btn btn-primary" style="box-shadow: 1px 1px 4px gray;">자세히 보기</button>
                </div>
            </div>
            <div class="card">
                <img class="card-img-top" src="images/noimg.png" style="width: 245px; height: 300px; margin: 10px auto; display: block;">
                <div class="card-body">
                <h4 class="card-title">기쁨</h4>
                <p class="card-text">모두가 기뻐질거에요~^^</p>
                <button class="btn btn-primary" style="box-shadow: 1px 1px 4px gray;">자세히 보기</button>
                </div>
            </div>
            </div>
              <br>
    </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import { Chart, registerables } from 'chart.js';

// Register Chart.js components
Chart.register(...registerables);

export default {
  name: 'ChartComponent',
  data() {
    return {
      date: new Date(),
      many_emotion: '행복',
      number_One: '숙면'
    };
  },
  mounted() {
    this.createChart();
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
  createChart() {
    const ctx = this.$refs.pieChart.getContext('2d');

    if (ctx) {
      new Chart(ctx, {
        type: 'pie',
        data: {
          labels: ['기쁨', '슬픔', '분노', '놀람', '혐오', '기타', '무감정'],
          datasets: [{
            label: '감정 분포',
            backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#FF5722', '#4CAF50', '#9C27B0', '#795548'],
            data: [12, 19, 3, 5, 2, 8, 15], // 임의의 데이터
          }]
        },
        options: {
          responsive: true,
          plugins: {
            title: {
              display: true,
              text: '한 달 기준 감정 분포',
              font: {
                size: 22 // 제목 글씨 크기
              }
            },
            legend: {
              labels: {
                font: {
                  size: 20 // 범례 글씨 크기
                }
              }
            },
            tooltip: {
              bodyFont: {
                size: 12 // 툴팁의 본문 글씨 크기
              },
              titleFont: {
                size: 14 // 툴팁의 제목 글씨 크기
              }
            }
          },
        }
      });
    } else {
      console.error('Canvas context is not available.');
    }
  }
}
  };
</script>

<style scoped>
.diaryViews_table {
  width: 100%;
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
.chart-container {
  width: 100%;
  max-width: 600px;
  margin: 0 auto;
}

canvas {
  width: 100% !important;
  height: auto !important;
}
</style>

/*UI개발: 박성호 */
<!-- npm install vue3-google-map
    설치필요함
  
  CREATE TABLE emotion (
	num	number	NOT NULL,
	resultnum	number	NOT NULL,
	membernum	number	NOT NULL,
	name	varchar2(50)	NOT NULL,
	score	float	NOT NULL,
	emotiondate date
);

  -->
<template>
    <div class="mypage_main row">
      <div class="mypage_title">
        <h1 style="text-align: center;">마이 다이어리</h1>
      </div>

      <div class="content_wrapper"> <!-- col-10-->
      <div class="right_main_content">
        <span class="mypage_first_menu">
            <!--일주일 + 주로 작성한 시간-->
            <div class="mypage_first_menu_one">
              <figure class="highcharts-figure" style="width: 600px; height: 400px;">
                  <div id="many_write_container"></div>
              </figure>
            </div>

            <!--일주일 바형 차트-->
            <div class="mypage_first_menu_two">
              <div id="weeknd_container" style="width: 600px; height: 400px;"></div>
            </div>

            <!--워드 클라우드-->
            <div class="mypage_first_menu_three">
                <div id="wordcloud_container" style="width: 600px; height: 400px;"></div>
            </div>
        </span>

        <span class="mypage_second_menu">
            <!--한달 감정 하이차트-->
            <div class="mypage_second_menu_one">
              <figure class="highcharts-figure" style="width: 600px; height: 400px;">
                <div id="month_container"></div>
              </figure>
            </div>

            <!--감정 회복 기간-->
            <div class="mypage_second_menu_two">
              <div style="width: 600px; height: 400px; text-align: center;">
                <h2>최근 감정 회복까지 걸린 시간</h2><br><br>
                <h1 style="font-size: 250px; color: dodgerblue;">{{ happyday }}일</h1>
              </div>

            </div>

            <!--감정 분포 하이차트-->
            <div class="mypage_second_menu_three">
                <figure class="highcharts-figure" style="width: 600px; height: 400px;">
                    <div id="container"></div>
                </figure>
            </div>
        </span>
      </div>
    </div>


        <!--추천 활동-->
        <span class="chuchun_play">
        <h2 style="text-align: center;">이번주 추천활동</h2><br>
            <div class="card-container">
              <div class="card" v-for="chuchun_item in chuchun_items.slice(0, 5)" :key="chuchun_item.chuchun_item_img">
                <img class="card-img-top" :src="`images/chuchun_img/${chuchun_item.chuchun_item_img}.jpg`" style="width: 200px; height: 150px; margin: 10px auto; display: block; border-radius: 15px;">
                <div class="card-body">
                  <p class="card-title" style="font-size:20px; font-weight: bold; height: 20px; margin-bottom: 15px;">{{ chuchun_item.chuchun_item_title }}</p>
                  <p class="card-text" style="height: 60px;">{{ chuchun_item.chuchun_item_content }}</p>
                  <button class="btn btn-primary" style="box-shadow: 1px 1px 4px gray;"
                          @click="showModal = true">자세히 보기</button>
                  </div>    
              </div>
            </div>
        </span>

        <!--ICT Market 추천 상품-->
        <span class="chuchun_market">
            <h2 style="text-align: center;">이번주 <a style="color: orange; font-size: 40px;">ICT Market</a> 추천상품</h2><br>
            <div class="card-container">
              <div class="card" v-for="chuchun_market in chuchun_markets.slice(0, 5)" :key="chuchun_market.chuchun_market_img">
                <img class="card-img-top" :src="`images/chuchunMarket/${chuchun_market.chuchun_market_img}.jpg`" style="width: 200px; height: 150px; margin: 10px auto; display: block; border-radius: 15px;">
                  <div class="card-body">
                    <h4 class="card-title" style="font-size:20px; font-weight: bold; height: 20px; margin-bottom: 15px;">{{ chuchun_market.chuchun_market_title }}</h4>
                    <p class="card-text" style="height: 60px;">{{ chuchun_market.chuchun_market_content }}</p>
                    <button class="btn711 btn-primary" style="box-shadow: 1px 1px 4px gray;"
                        @click="showDetails(chuchun_market)">자세히 보기</button>
                  </div>
                </div>
            </div>
        </span>

        <!--추천활동 모달창-->
        <div class="mapModal" id="mapModal">
        <Modal :isVisible="showModal" @update:isVisible="showModal = $event">
          <h2>추천활동 지도</h2>
          <KakaoMap class="kakaomap_modal" v-if="showModal" />
        </Modal>
      </div>


      <div class="mapModal" id="mapModal">
        <Modal :isVisible="marketModal" @update:isVisible="marketModal = $event">
          <h2>상품 추천</h2>
          <div v-if="selectedMarket">
            <img :src="`images/chuchunMarket/${selectedMarket.chuchun_market_img}.jpg`" style="width: 800px; height: 500px; border-radius: 15px; margin-top: 30px;">
            <div style="margin-top: 100px;">
            <h2>{{ selectedMarket.chuchun_market_title }}</h2>
          </div>
          <div style="margin-top: 80px; margin-bottom: 80px;">
            <h3>{{ selectedMarket.chuchun_market_content }}</h3>
          </div>
          <button class="btn711 btn-primary" 
                  style="box-shadow: 1px 1px 4px gray; 
                  background-color: dodgerblue;
                  width: 100%;
                  height: 50px;
                  margin-bottom: 20px;
                  ">ICT 마켓 바로가기</button>
          </div>
        </Modal>
      </div>
    </div>
</template>


<script>
import axios from 'axios';
import Highcharts from 'highcharts';
import Highcharts3d from 'highcharts/highcharts-3d';
import HighchartsMore from 'highcharts/highcharts-more';
import HighchartsVariablePie from 'highcharts/modules/variable-pie';
import HighchartsWordCloud from 'highcharts/modules/wordcloud';
import Modal from '../views/MyPageModal.vue';
import KakaoMap from './MyPageKakao.vue'; // KakaoMap 컴포넌트 경로에 맞게 수정

Highcharts3d(Highcharts);
HighchartsMore(Highcharts);
HighchartsWordCloud(Highcharts);
HighchartsVariablePie(Highcharts);

export default {
  name: 'mapModal',
  components:{
    Modal,
    KakaoMap,
  },
  data(){
    return {
      map : null,
      keyWord : '', // 검색키워드 v-model을 통해 검색단어를 받아옴
      placeArray : [], // 검색결과를 담는 배열
      showModal: false,
      marketModal: false,
      chuchun_items: [], // 추천활동 담을 리스트 
      chuchun_markets: [], // 추천상품 담을 리스트 
      isModalOpen: false,
      selectedEmotion: '',
      piechartData: [], // 도넛 차트 데이터
      happyday: '',
      chartData: [],
      selectedMarket: null,
    };
  },
  mounted() {
        this.$nextTick(() => {
            this.card_func();
            this.renderChart();
            this.wordcloudChart();
            this.variablepieChart();
            this.monthChart();
            this.many_write_Chart();
            this.chuchun_items_func();
            this.chuchun_markets_func();
            this.menu_card_func('65%','75%');
            this.renderVariablePieChart();
            this.happydays();
        });
        
    },
  methods: {
    // 작성요일, 시간 차트
    many_write_Chart() {
    // Axios를 사용하여 데이터를 가져옵니다.
    axios.get(`${process.env.VUE_APP_BACK_END_URL}/emotion/weekemotion?membernum=${membernum}`)
        .then(response => {
            // 서버에서 받은 데이터
            const responseData = response.data;

            // 주어진 요일 목록
            const weekdays = ['월요일', '화요일', '수요일', '목요일', '금요일', '토요일', '일요일'];
            
            // 데이터 초기화
            const weekdayCounts = weekdays.reduce((acc, day) => {
                acc[day] = 0;
                return acc;
            }, {});

            // 감정별 데이터 집계
            const emotionMap = responseData.reduce((acc, item) => {
                if (!acc[item.weekday]) {
                    acc[item.weekday] = {};
                }
                if (!acc[item.weekday][item.name]) {
                    acc[item.weekday][item.name] = 0;
                }
                acc[item.weekday][item.name] += item.count;
                return acc;
            }, {});

            // 요일별 데이터 집계
            responseData.forEach(item => {
                weekdayCounts[item.weekday] = item.weekdaycount;
            });

            // 감정별 데이터 정렬
            const emotions = Array.from(new Set(responseData.map(item => item.name)));
            const emotionData = weekdays.map(day => {
                const emotionsForDay = Object.entries(emotionMap[day] || {});
                return emotionsForDay.reduce((max, [, count]) => Math.max(max, count), 0);
            });

            // 요일별 작성 횟수 데이터
            const weekdaySeries = weekdays.map(day => weekdayCounts[day] || 0);

            // 차트 생성
            Highcharts.chart('many_write_container', {
                chart: {
                    polar: true,
                    type: 'line'
                },
                title: {
                    text: '가장 많이 작성한 요일',
                    align: 'center',
                    x: -80
                },
                pane: {
                    size: '80%'
                },
                xAxis: {
                    categories: weekdays,
                    tickmarkPlacement: 'on',
                    lineWidth: 0
                },
                yAxis: {
                    gridLineInterpolation: 'polygon',
                    lineWidth: 0,
                    min: 0
                },
                tooltip: {
                    shared: true,
                    formatter: function() {
                        let tooltip = `<b>${this.x}</b><br/>`;
                        this.points.forEach(point => {
                            tooltip += `<span style="color:${point.series.color}">${point.series.name}: <b>${point.y}</b>`;
                            if (point.series.name === '최다 감정') {
                                const maxEmotion = Object.entries(emotionMap[this.x] || {}).reduce((max, [emotion, count]) => {
                                    if (count === point.y) return emotion;
                                    return max;
                                }, '');
                                tooltip += ` (${maxEmotion})`;
                            }
                            tooltip += '<br/>';
                        });
                        return tooltip;
                    }
                },
                legend: {
                    align: 'right',
                    verticalAlign: 'middle',
                    layout: 'vertical'
                },
                series: [{
                    name: '작성 횟수',
                    data: weekdaySeries,
                    pointPlacement: 'on'
                }, {
                    name: '최다 감정',
                    data: emotionData,
                    pointPlacement: 'on'
                }],
                responsive: {
                    rules: [{
                        condition: {
                            maxWidth: 500
                        },
                        chartOptions: {
                            legend: {
                                align: 'center',
                                verticalAlign: 'bottom',
                                layout: 'horizontal'
                            },
                            pane: {
                                size: '70%'
                            }
                        }
                    }]
                }
            });
        })
        .catch(error => {
            console.error('데이터를 가져오는 도중 오류가 발생했습니다:', error);
        });
    },
    // 도넛차트
    variablepieChart() {
        axios.get(`${process.env.VUE_APP_BACK_END_URL}/emotion/emotioncount?membernum=${membernum}`)
            .then(response => {
                // 서버에서 받은 데이터
                const responseData = response.data;

                // 데이터 변환
                const transformedData = responseData.map(item => ({
                    name: item.name,
                    y: item.count
                }));

                // 변환된 데이터를 piechartData에 저장
                this.piechartData = transformedData;
                
                // 차트 렌더링
                this.renderVariablePieChart();
            })
            .catch(error => {
                console.error('데이터를 가져오는 도중 오류가 발생했습니다:', error);
            });
    },
    // 도넛차트 데이터 렌더링 하는 함수
    renderVariablePieChart() {
        // 데이터가 준비된 후 차트 렌더링
        const emotionData = this.piechartData.slice(); // 변환된 데이터를 사용

        // 데이터 정렬: y 값이 큰 순서대로 정렬
        emotionData.sort((a, b) => b.y - a.y);

        // z 값을 설정: 가장 큰 y 값일 때 235, 그 다음부터 30씩 감소
        let maxZ = 235;
        emotionData.forEach((item, index) => {
            item.z = maxZ - (index * 30);
            if (item.z < 0) item.z = 0; // z 값이 음수가 되지 않도록 처리
        });

        Highcharts.chart('weeknd_container', {
            chart: {
                type: 'variablepie'
            },
            title: {
                text: '감정 그래프',
                align: 'center'
            },
            tooltip: {
                headerFormat: '',
                pointFormat: '<span style="color:{point.color}">\u25CF</span> <b> ' +
                    '{point.name}</b><br/>' +
                    '횟수: <b>{point.y}</b><br/>'
            },
            series: [{
                minPointSize: 10,
                innerSize: '20%',
                zMin: 0,
                name: '감정',
                borderRadius: 5,
                data: emotionData,
                colors: [
                    '#4caefe',
                    '#3dc3e8',
                    '#2dd9db',
                    '#1feeaf',
                    '#0ff3a0',
                    '#00e887',
                    '#23e274'
                ]
            }]
        });
    },
    // 워드클라우드
    wordcloudChart() {
    axios.get(`${process.env.VUE_APP_BACK_END_URL}/diary/list?membernum=${membernum}`)
        .then(response => {
            // 서버에서 받은 데이터
            const responseData = response.data;
            
            // 모든 일기의 content 필드를 하나의 문자열로 결합
            const text = responseData.map(item => item.content).join(' ') || '';

            // 텍스트를 단어로 분리하고, 빈 단어를 제외하고, 각 단어의 출현 횟수를 계산
            const lines = text.replace(/[():'?0-9]+/g, '').split(/[,\. ]+/g);
            const data = lines.reduce((arr, word) => {
                if (word.trim() === '') return arr; // 빈 단어 제외
                let obj = arr.find(item => item.name === word);
                if (obj) {
                    obj.weight += 1;
                } else {
                    obj = {
                        name: word,
                        weight: 1
                    };
                    arr.push(obj);
                }
                return arr;
            }, []);

            // 워드클라우드 차트 생성
            Highcharts.chart('wordcloud_container', {
                accessibility: {
                    screenReaderSection: {
                        beforeChartFormat: '<h5>{chartTitle}</h5>'
                    }
                },
                series: [{
                    type: 'wordcloud',
                    data: data,
                    name: '횟수'
                }],
                title: {
                    text: '워드클라우드',
                    align: 'center'
                },
                tooltip: {
                    headerFormat: '<span style="font-size: 16px"><b>{point.key}</b>' +
                                  '</span><br>'
                }
            });
        })
        .catch(error => {
            console.error('데이터를 가져오는 도중 오류가 발생했습니다:', error);
        });
    },
    // 월별차트
    monthChart() {
    axios.get(`${process.env.VUE_APP_BACK_END_URL}/emotion/monthEmotion?membernum=${membernum}`)
        .then(response => {
            // 서버에서 받은 데이터
            const responseData = response.data;

            // 월별 데이터를 초기화합니다.
            const months = ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'];
            const dataByMonth = months.map(month => ({
                month,
                values: {}
            }));

            // 감정별 데이터를 초기화합니다.
            const emotionSet = new Set();

            // 데이터를 월별로 분류합니다.
            responseData.forEach(item => {
                const monthIndex = parseInt(item.month) - 1;
                if (monthIndex >= 0 && monthIndex < months.length) {
                    dataByMonth[monthIndex].values[item.name] = (dataByMonth[monthIndex].values[item.name] || 0) + item.count;
                    emotionSet.add(item.name);
                }
            });

            // 시리즈 데이터 구조를 초기화합니다.
            const seriesData = Array.from(emotionSet).map(emotion => {
                return {
                    name: emotion,
                    data: dataByMonth.map(monthData => monthData.values[emotion] || 0)
                };
            });

            // 월별 카테고리 정의
            const categories = months.map(month => `${month}월`);

            // 차트 생성
            Highcharts.chart('month_container', {
                chart: {
                    zooming: {
                        type: 'xy'
                    },
                    type: 'column'
                },
                title: {
                    text: '한달 감정 통계',
                    align: 'center'
                },
                credits: {
                    text: 'Source: ' +
                        '<a href="https://www.yr.no/nb/historikk/graf/5-97251/Norge/Finnmark/Karasjok/Karasjok?q=2023"' +
                        'target="_blank">YR</a>'
                },
                xAxis: [{
                    categories: categories,
                    crosshair: true
                }],
                yAxis: [{ // Primary yAxis
                    title: {
                        text: '총 감정 횟수'
                    }
                }],
                tooltip: {
                    shared: true,
                    formatter: function () {
                        // 툴팁의 내용을 사용자 정의합니다.
                        let tooltipText = '<b>' + this.x + '</b><br/>';
                        let total = 0;
                        this.points.forEach(point => {
                            tooltipText += `${point.series.name}: <b>${point.y}</b><br/>`;
                            total += point.y;
                        });
                        return tooltipText + `<br/><b>총 감정 횟수: ${total}</b>`;
                    }
                },
                legend: {
                    align: 'left',
                    verticalAlign: 'top',
                    backgroundColor: Highcharts.defaultOptions.legend.backgroundColor || 'rgba(255,255,255,0.25)'
                },
                series: seriesData,
                plotOptions: {
                    column: {
                        stacking: 'normal'
                    }
                }
            });
        })
        .catch(error => {
            console.error('데이터를 가져오는 도중 오류가 발생했습니다:', error);
        });
    },
    // 감정회복
    happydays(){
      axios.get(`${process.env.VUE_APP_BACK_END_URL}/emotion/happydays?membernum=${membernum}`)
        .then(res => {
          // 서버에서 받은 데이터
          const resData = res.data;
          this.happyday = resData[0]
        })
        .catch(error => {
          console.error('데이터를 가져오는 도중 오류가 발생했습니다:', error);
        });
    },
    // 통계차트
    renderChart() {
    axios.get(`${process.env.VUE_APP_BACK_END_URL}/emotion/bubbleemotion?membernum=${membernum}`)
        .then(res => {
            const resData = res.data;
            const categories = [];
            const scoreData = [];
            const frequencyData = [];

            resData.forEach(item => {
                categories.push(item.emotion);
                scoreData.push(parseFloat(item.scoreaverage.toFixed(2))); // 소수점 둘째 자리까지 반올림
                frequencyData.push(item.count);
            });

            Highcharts.chart('container', {
                chart: {
                    type: 'column'
                },
                title: {
                    text: '감정별 평균 점수와 총 빈도'
                },
                xAxis: {
                    categories: categories
                },
                yAxis: [{
                    min: 0,
                    title: {
                        text: '평균 점수'
                    }
                }, {
                    title: {
                        text: '총 빈도'
                    },
                    opposite: true
                }],
                series: [{
                    name: '평균 점수',
                    data: scoreData
                }, {
                    name: '총 빈도',
                    data: frequencyData,
                    yAxis: 1
                }]
            });
        })
        .catch(error => console.error(error));
    },
    // 카드 움직이는 함수
    card_func() {
    this.$nextTick(() => {
      // 약간의 지연을 추가하여 DOM이 완전히 렌더링되었는지 확인합니다.
      setTimeout(() => {
        const cards = document.querySelectorAll('.card');

        if (cards.length === 0) {
          console.warn('No .card elements found.');
          return;
        }
        cards.forEach(card => {
          card.addEventListener('mousemove', function(e) {
            if (!card.classList.contains('expanded')) {
              const rect = card.getBoundingClientRect();
              const x = e.clientX - rect.left; // x 좌표
              const y = e.clientY - rect.top;  // y 좌표

              // 회전 각도 계산
              const centerX = rect.width / 2;
              const centerY = rect.height / 2;
              const rotateY = (x - centerX) / centerX * 15; // 15도까지 회전
              const rotateX = -(y - centerY) / centerY * 15; // 15도까지 회전

              card.style.transform = `perspective(1500px) rotateY(${rotateY}deg) rotateX(${rotateX}deg)`;
            }
          });

          card.addEventListener('mouseleave', function() {
            card.style.transform = 'perspective(1500px) rotateY(0deg) rotateX(0deg)';
          });
        });
      }, 100); // 100ms 지연 후 실행
    });
    },
    menu_card_func(xPercent = null, yPercent = null) {
      var menuCards = document.querySelectorAll('.mypage_first_menu_one, .mypage_first_menu_two, .mypage_first_menu_three, .mypage_second_menu_one, .mypage_second_menu_two, .mypage_second_menu_three');
      var expandedCard = null; // 현재 확장된 카드를 추적하기 위한 변수
      var scaleFactor = 1.5; // 카드 확대 비율 (원하는 값으로 변경 가능)

      menuCards.forEach(card => {
        card.addEventListener('mousemove', function(e) {
          if (!card.classList.contains('expanded')) {
            var rect = card.getBoundingClientRect();
            var x = e.clientX - rect.left;
            var y = e.clientY - rect.top;

            var centerX = rect.width / 2;
            var centerY = rect.height / 2;
            var rotateY = (x - centerX) / centerX * 15;
            var rotateX = -(y - centerY) / centerY * 15;

            card.style.transform = `perspective(1500px) rotateY(${rotateY}deg) rotateX(${rotateX}deg)`;
          }
        });

        card.addEventListener('mouseleave', function() {
          if (!card.classList.contains('expanded')) {
            card.style.transform = `perspective(1500px) rotateY(0deg) rotateX(0deg)`;
          }
        });

        card.addEventListener('click', function(e) {
          e.stopPropagation();
          if (card.classList.contains('expanded')) {
            card.classList.remove('expanded');
            card.style.transform = '';
            card.style.position = '';
            card.style.zIndex = '';
            card.style.backgroundColor = ''; // 배경색 초기화
            expandedCard = null;
          } else {
            if (expandedCard) {
              expandedCard.classList.remove('expanded');
              expandedCard.style.transform = '';
              expandedCard.style.position = '';
              expandedCard.style.zIndex = '';
              expandedCard.style.backgroundColor = ''; // 배경색 초기화
            }
            card.classList.add('expanded');
            card.style.position = 'fixed';
            card.style.zIndex = '999';
            card.style.transform = `scale(${scaleFactor})`;
            card.style.backgroundColor = '#ffffff'; // 배경색 설정 (투명도 없이)

            const viewportWidth = window.innerWidth;
            const viewportHeight = window.innerHeight;
            const rect = card.getBoundingClientRect();

            let offsetX = (viewportWidth - rect.width * scaleFactor) / 2;
            let offsetY = (viewportHeight - rect.height * scaleFactor) / 2;

            // 위치 조정 (xPercent와 yPercent가 null이 아니면 그 값으로 설정)
            if (xPercent !== null && yPercent !== null) {
              offsetX = (viewportWidth * (parseInt(xPercent) / 100)) - rect.left - (rect.width * scaleFactor) / 2;
              offsetY = (viewportHeight * (parseInt(yPercent) / 100)) - rect.top - (rect.height * scaleFactor) / 2;
            }

            card.style.left = `${rect.left + offsetX}px`;
            card.style.top = `${rect.top + offsetY}px`;

            expandedCard = card;
          }
        });

        document.addEventListener('click', function(e) {
          if (expandedCard && !expandedCard.contains(e.target)) {
            expandedCard.classList.remove('expanded');
            expandedCard.style.transform = '';
            expandedCard.style.position = '';
            expandedCard.style.zIndex = '';
            expandedCard.style.backgroundColor = ''; // 배경색 초기화
            expandedCard = null;
          }
        });
      });
    },
  //   // 활동 추천
  //   chuchun_items_func() {
  // const formData = new FormData();
  // formData.append("emotion", "행복");
  // axios
  //   .post(`http://192.168.92:9000/emotion/emotion_chuchun`, formData)
  //   .then((res) => {
  //     const data = res.data;
  //     // 객체를 배열로 변환
  //     this.chuchun_items = Object.entries(data).map(([title, content]) => ({
  //       chuchun_item_img: title, // 사진 이름
  //       chuchun_item_title: content.활동,
  //       chuchun_item_content: content.설명,
  //     }));
  //   })
  //   .catch((error) => {
  //     console.error("There was an error!", error);
  //   });
  //   },

  //   // 마켓 추천
  //   chuchun_markets_func() {
  //   const formData = new FormData();
  //   formData.append("emotion", "행복");

  //   axios.post(`http://192.168.92:9000/emotion/market_chuchun`, formData)
  //       .then((res) => {
  //           const data = res.data;
  //           // 객체를 배열로 변환
  //           this.chuchun_markets = Object.entries(data).map(([title, content]) => ({
  //               chuchun_market_img: title, // 사진 이름
  //               chuchun_market_title: content.상품명,
  //               chuchun_market_content: content.상품의효과, // 수정: 객체 속성 이름 맞춤
  //           }));
  //       })
  //       .catch((error) => {
  //           console.error("There was an error!", error);
  //       });
  //   },

    // GPT 응답 추천 활동 함수
    chuchun_items_func() {
        // 첫 번째 axios 요청을 통해 count가 가장 큰 name 찾기
        axios.get('http://192.168.0.74/finalproject/emotion/emotioncount')
            .then(response => {
                // 서버에서 받은 데이터
                const responseData = response.data;

                // 데이터 변환
                const transformedData = responseData.map(item => ({
                    name: item.name,
                    y: item.count
                }));

                // count 값이 가장 큰 name을 찾기
                const maxCountItem = transformedData.reduce((max, item) => 
                    item.y > max.y ? item : max, transformedData[0]);

                // 두 번째 axios 요청에서 찾은 name을 사용하여 formData 구성
                const formData = new FormData();
                formData.append("emotion", maxCountItem.name);

                // 두 번째 axios 요청 실행
                return axios.post(`http://192.168.92:9000/emotion/emotion_chuchun`, formData);
            })
            .then((res) => {
                const data = res.data;
                this.chuchun_items = Object.entries(data).map(([title, content]) => ({
                    chuchun_item_img: title, 
                    chuchun_item_title: content.활동,
                    chuchun_item_content: content.설명,
                }));
            })
            .catch((error) => {
                console.error("There was an error!", error);
            });
    },
    // GPT 응답 마켓 추천 함수
    chuchun_markets_func() {
    // 첫 번째 axios 요청을 통해 count가 가장 큰 name 찾기
    axios.get('http://192.168.0.74/finalproject/emotion/emotioncount')
        .then(response => {
            // 서버에서 받은 데이터
            const responseData = response.data;

            // 데이터 변환
            const transformedData = responseData.map(item => ({
                name: item.name,
                y: item.count
            }));

            // count 값이 가장 큰 name을 찾기
            const maxCountItem = transformedData.reduce((max, item) => 
                item.y > max.y ? item : max, transformedData[0]);

            // 두 번째 axios 요청에서 찾은 name을 사용하여 formData 구성
            const formData = new FormData();
            formData.append("emotion", maxCountItem.name);

            // 두 번째 axios 요청 실행
            return axios.post(`http://192.168.92:9000/emotion/market_chuchun`, formData);
        })
        .then((res) => {
            const data = res.data;
            this.chuchun_markets = Object.entries(data).map(([title, content]) => ({
                chuchun_market_img: title, 
                chuchun_market_title: content.상품명,
                chuchun_market_content: content.상품의효과, 
            }));
        })
        .catch((error) => {
            console.error("There was an error!", error);
        });
    },
    // 모달
    showDetails(market) {
    this.selectedMarket = market;
    this.marketModal = true;
    },
      },
      }
</script>
<style scoped>
.mypage_main{
    margin-top: 150px;
}
.mypage_title{
  text-align: center;
  margin-bottom: 20px;
  width: 100%;

}
.mypage_first_menu {
    display: flex;
    justify-content: space-between;
    align-items: center;
}
.mypage_second_menu {
    display: flex;
    justify-content: space-between; 
    align-items: center; 
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
.chart-container {
  width: 100%;
  max-width: 600px;
  margin: 0 auto;
}
.chuchun_play{
  margin: 30px;
}
.chuchun_market{
  margin: 30px;
}
.mypage_first_menu_one {
  padding: 10px;
  margin: 30px;
  width: 630px;
  height: 450px;
  border: 2px solid rgb(216, 212, 212);
  border-radius: 15px;
  box-shadow: 1px 1px 4px gray;
}
.mypage_first_menu_two {
  padding: 10px;
  margin: 30px;
  width: 630px;
  height: 450px;
  border: 2px solid rgb(216, 212, 212);
  border-radius: 15px;
  box-shadow: 1px 1px 4px gray;
}
.mypage_first_menu_three {
  padding: 10px;
  margin: 30px;
  width: 630px;
  height: 450px;
  border: 2px solid rgb(216, 212, 212);
  border-radius: 15px;
  box-shadow: 1px 1px 4px gray;
}
.mypage_second_menu_one {
  padding: 10px;
  margin: 30px;
  width: 630px;
  height: 450px;
  border: 2px solid rgb(216, 212, 212);
  border-radius: 15px;
  box-shadow: 1px 1px 4px gray;
}
.mypage_second_menu_two {
  padding: 10px;
  margin: 30px;
  width: 630px;
  height: 450px;
  border: 2px solid rgb(216, 212, 212);
  border-radius: 15px;
  box-shadow: 1px 1px 4px gray;
}
.mypage_second_menu_three {
  padding: 10px;
  margin: 30px;
  width: 630px;
  height: 450px;
  border: 2px solid rgb(216, 212, 212);
  border-radius: 15px;
  box-shadow: 1px 1px 4px gray;
}
.left_banner {
    display: flex;
    margin: 30px;
    width: 200px;
    background-color: white;
    padding: 20px;
    width: 200px; 
    height: 1010px; 
    border: 2px solid rgb(216, 212, 212);
    border-radius: 15px;
    box-shadow: 1px 1px 4px gray;
}
.right_main_content {
    margin: 30px;
    padding: 20px;
}
.mapModal{
  text-align: center;
}
</style>

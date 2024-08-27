/*UI개발: 허호준 */
<template>
    <div>
        <div class="main">
            <div class="container-xxl py-5">
                <div class="container">
                    <div class="section-title text-center">
                        <h1 class="display-5 mb-5">간단심리상담 결과</h1>
                        <!-- 검사결과항목시작 -->
                        <div class="d-flex justify-content-center">
                            <div class="simulRes8">
                                <div class="d-flex justify-content-center">
                                    <div class="align-items-start col-lg-6">
                                        <div class="section-title text-center mx-3">
                                            <h3 class="display-5">{{ userName }}님의 </h3>
                                        </div>
                                        <div class="simulRes2">
                                            <h3 class="display-5">{{ surveyName }}</h3>
                                        </div>
                                        <div class="simulRes2">
                                            <h3 class="display-5">진단 결과</h3>
                                        </div>
                                    </div>
                                </div>
                                <div class="content-area mt-4 row">
                                    <div class="col-6 simulRes3">
                                        <h3>점수: {{ totalScore }}</h3>
                                    </div>
                                </div>
                                <div class="row g-3 align-items-center">
                                    <div class="col-6 simulRes3">
                                        <div id="chart" style="margin:auto; width:100%; height:300px;">
                                            <!-- highcharts 렌더링을 위한 div -->
                                        </div>
                                    </div>
                                </div>
                                <div class="content-area mt-4 mb-5 row justify-content-center">
                                    <h3 class="display-5 mb-2 text-center">키워드</h3>
                                    <div class="keywords">
                                        <span v-for="(description, index) in descriptionList" :key="index"
                                            class="keyword-tag">
                                            {{ description.name }}
                                        </span>
                                    </div>
                                </div>
                                <div class="content-area mt-4 mb-5 row">
                                    <h3 class="display-5 mb-2">총 평가</h3>
                                    <div class="keywords">
                                        <span v-for="(cause, index) in causeList" :key="index" class="keyword-tag">
                                            {{ cause.name }}
                                        </span>
                                    </div>
                                </div>
                                <br>
                                <div class="chuchun_play">
                                    <h3 class="display-5 mb-2">이번주 추천활동</h3>
                                    <!-- 수정해야함 -->
                                    <div class="card-container content-area mt-4 row">
                                        <div class="card">
                                            <img class="card-img-top" src="images/survey/walking_img.jpg" alt=""
                                                style="width: 50%; height: 60%; margin: 5px auto; display: block;">
                                            <div class="card-body">
                                                <div v-for="activity in recommendActivity" :key="activity.title"
                                                    class="activity-item">
                                                    <h4>{{ activity.title }}</h4>
                                                    <p>{{ activity.comm }}</p>
                                                </div>
                                                <button class="btn btn-primary" style="box-shadow: 1px 1px 4px gray;"
                                                    @click="showModal = true">자세히 보기
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="section-title text-start mt-4 text-center">
                            <button @click="handleStart" class="simulationIntro3">메인 페이지로</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="mapModal" id="mapModal">
            <Modal :isVisible="showModal" @update:isVisible="showModal = $event">
                <h2>{{ userName }}님께 추천하는 활동입니다</h2>
                <p>{{ userAddr }} 근처에 있네요!</p>
                <div class="kakaoMap" id="kakaoMap" style="width:500px;height:400px;">
                </div>
            </Modal>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
import { ref } from 'vue';
import Highcharts from 'highcharts';
import Modal from '../views/MyPageModal.vue';

export default {
    name: 'mapModal',
    components: {
        Modal,
    },
    data() {
        return {
            showModal: ref(false),
            surveyName: '우울척도',
            userName: '상상이',
            userid: 'user2',
            causeList: [],
            descriptionList: [],
            totalScore: 0,
            emotionGauge: 20,
            recommendActivity: '',
            chartOptions: {
                chart: {
                    type: 'gauge',
                    plotBackgroundColor: null,
                    plotBackgroundImage: null,
                    plotBorderWidth: 0,
                    plotShadow: false,
                    height: '80%',
                },
                title: {
                    text: '',
                },
                pane: {
                    startAngle: -90,
                    endAngle: 89.9,
                    background: null,
                    center: ['50%', '75%'],
                    size: '110%',
                },
                yAxis: {
                    min: 10,
                    max: 40,
                    tickPixelInterval: 40,
                    tickPosition: null,
                    tickColor: Highcharts.defaultOptions.chart.backgroundColor || '#FFFFFF',
                    tickLength: 20,
                    tickWidth: 2,
                    minorTickInterval: null,
                    labels: {
                        distance: 20,
                        style: {
                            fontSize: '14px',
                        },
                    },
                    lineWidth: 0,
                    plotBands: [
                        {
                            from: 10,
                            to: 18.4,
                            color: '#FAED7D',
                            thickness: 20,
                            borderRadius: '50%',
                        },
                        {
                            from: 18.5,
                            to: 23,
                            color: '#47C83E',
                            thickness: 20,
                            borderRadius: '50%',
                        },
                        {
                            from: 23,
                            to: 30,
                            color: '#F361DC',
                            thickness: 20,
                            borderRadius: '50%',
                        },
                        {
                            from: 30,
                            to: 35,
                            color: '#F15F5F',
                            thickness: 20,
                            borderRadius: '50%',
                        },
                        {
                            from: 35,
                            to: 60,
                            color: '#FF0000',
                            thickness: 20,
                            borderRadius: '50%',
                        },
                    ],
                },
                series: [
                    {
                        name: 'emotionGauge',
                        data: [], // 초기 데이터는 비워 둡니다.
                        tooltip: {
                            valueSuffix: '점',
                        },
                        dataLabels: {
                            format: '진단 점수 : {y} ',
                            borderWidth: 0,
                            color:
                                (Highcharts.defaultOptions.title &&
                                    Highcharts.defaultOptions.title.style &&
                                    Highcharts.defaultOptions.title.style.color) ||
                                '#333333',
                            style: {
                                fontSize: '16px',
                            },
                        },
                        dial: {
                            radius: '80%',
                            backgroundColor: 'gray',
                            baseWidth: 12,
                            baseLength: '0%',
                            rearLength: '0%',
                        },
                        pivot: {
                            backgroundColor: 'gray',
                            radius: 6,
                        },
                    },
                ],
            },
        };
    },
    mounted() {
        // Axios를 사용하여 데이터를 가져옵니다.
        axios.get(`${process.env.VUE_APP_BACK_END_URL}/survey/responses?userid=${this.userid}`)
            .then((response) => {
                const data = response.data;

                // CAUSE와 DESCRIPTION을 담을 리스트 초기화
                const causeList = [];
                const descriptionList = [];
                let totalScore = 0;

                // 데이터를 순회하며 CAUSE와 DESCRIPTION 리스트 작성
                data.forEach(item => {
                    if (item.TYPE === 'CAUSE') {
                        causeList.push({ name: item.NAME, count: item.COUNT });
                    } else if (item.TYPE === 'DESCRIPTION') {
                        descriptionList.push({ name: item.NAME, count: item.COUNT });
                    } else if (item.TYPE === 'TOTAL') {
                        totalScore = item.COUNT;
                    }
                });

                // count 값 기준으로 내림차순 정렬
                causeList.sort((a, b) => b.count - a.count);
                descriptionList.sort((a, b) => b.count - a.count);

                // state에 저장
                this.causeList = causeList;
                this.descriptionList = descriptionList;
                this.totalScore = totalScore;

                // 차트를 생성하고 respscore 값을 반영
                this.chartOptions.series[0].data = [this.totalScore];
                this.createCharts();
            })
            .catch((error) => {
                console.error("There was an error fetching the data!", error);
            });

        window.scrollTo(0, 0);
    },
    methods: {
        getRespTotal() {
            // getRespTotal
            switch (this.totalScore) {
                case 1:
                    this.surveyResult = '정상';
                    break;
                case 2:
                    this.surveyResult = '약간 심각';
                    break;
                case 3:
                    this.surveyResult = '매우 심각';
                    break;
                default:
                    this.surveyResult = '제대로 측정되지 않았습니다.';
            }
        },
        createCharts() {
            Highcharts.chart('chart', this.chartOptions);
        },
        goToList() {
            this.$router.push('/list');
        },
        handleStart() {
            this.$router.push('/');
        },
    },
};
</script>

<style>
.profile-image {
    display: flex;
    align-items: center;
    gap: 20px;
}

.simulRes1 {
    width: 200px;
    height: auto;
}

.section-title.text-center {
    flex: 1;
}

.simulRes2 {
    flex: 0 0 auto;
    text-align: center;
    font-size: 80px;
    font-weight: bolder;
}

.simulRes3 {
    margin: auto;
}

.simulRes4 {
    font-size: 30px;
    font-family: "Black Han Sans", sans-serif;
    font-style: normal;

}

.simulRes6 {

    text-align: center;
    font-size: 25px;
    font-weight: bolder;
}

.simulRes7 {
    background-color: #fff;
    color: #337DF0;
    font-weight: 1000;
    padding: 10px 20px;
    font-size: 1em;
    border-radius: 25px;
    border: 3px solid #337DF0;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

.simulRes8 {
    max-width: 75%;
    padding: 80px;
    border: solid 1px;
    border-radius: 50px;
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

.recommend-activity {
    margin-top: 20px;
}

.activity-item {
    margin-top: 10px;
}

.keywords {
    display: flex;
    justify-content: center;
    flex-wrap: wrap;
    gap: 10px;
    margin-top: 10px;
}

.keyword-tag {
    background-color: #f1f1f1;
    border: 1px solid #ccc;
    border-radius: 20px;
    padding: 5px 10px;
    font-size: 16px;
    color: #333;
}
</style>
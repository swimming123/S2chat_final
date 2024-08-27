/*UI개발: 윤수영 */
<template>
    <div>
        <div class="main">
            <div class="container-xxl py-5">
                <div class="container">
                    <div class="position-relative overflow-hidden">
                    </div>
                    <div class="section-title text-center">
                        <h1 class="display-5 mb-5">OO 검사 결과</h1>
                    </div>
                    <!-- 검사결과항목시작 -->
                    <div class="d-flex justify-content-center">

                        <div class="col-lg-6 quote-text py-5">
                            <div class="section-title text-start">
                                <h2 class="display-5 mb-4">OOO님은...</h2>
                                <p class="mb-4 pb-2">
                                    설명설명설명설명설명설명설명설명설명설명설명설명
                                </p>
                            </div>
                            <!-- 카드항목영역시작 -->
                            <div class="p-lg-5 pe-lg-0">
                                <div class="row g-3">
                                    <h3 class="display-6 mb-3">OOO님의 감정은!</h3>
                                    <div class="col-12 col-sm-6">
                                        <div class="card_container">
                                            <div class="card_top">우울</div>
                                            <div class="card_bottom">
                                                <div class="card_name">40%</div>
                                                <div class="card_date">2024.7.18, 검사명</div>
                                            </div>
                                        </div>
                                        <!-- 카드 코드 출처 : https://blog.naver.com/js_ups/222778852112 -->
                                        <!-- 카드 플립 출처 : https://velog.io/@jj123/%EB%A7%8C%EB%93%A4%EA%B8%B0-%EC%B9%B4%EB%93%9C-%ED%94%8C%EB%A6%BD-%EC%95%A0%EB%8B%88%EB%A9%94%EC%9D%B4%EC%85%98 -->
                                    </div>
                                    <div class="col-12 col-sm-6">
                                        <div class="card_container">
                                            <div class="card_top">우울</div>
                                            <div class="card_bottom">
                                                <div class="card_name">40%</div>
                                                <div class="card_date">2024.7.18, 검사명</div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- 카드항목영역시작 -->
                                <!-- 그래프항목영역시작 -->
                                <div class="row g-3">
                                    <div class="col-12">
                                        <div class="section-title text-start ">
                                            <canvas id="bar-chart" width="300" height="230"></canvas>
                                            <h3 class="display-6 mb-3">항목제목</h3>
                                            <p class="mb-4 pb-2">
                                                설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명
                                            </p>
                                        </div>
                                    </div>

                                    <div class="col-12 col-sm-6">
                                        <div class="section-title text-start ">
                                            <canvas id="line-chart" width="300" height="250"></canvas>
                                            <h3 class="display-6 mb-3">항목제목</h3>
                                            <p class="mb-4 pb-2">
                                                설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명
                                            </p>
                                        </div>
                                    </div>
                                    <div class="col-12 col-sm-6">
                                        <div class="section-title text-start">
                                            <canvas id="pie-chart" width="250" height="250"></canvas>
                                            <h2 class="display-6 mb-4">항목제목</h2>
                                            <p class="mb-4 pb-2">
                                                설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명
                                            </p>
                                        </div>
                                    </div>

                                    <div class="col-12">
                                        <div class="section-title text-start ">
                                            <canvas id="radar-chart" width="250" height="250"></canvas>
                                            <h3 class="display-6 mb-3">항목제목</h3>
                                            <p class="mb-4 pb-2">
                                                설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명
                                            </p>
                                        </div>
                                    </div>

                                    <div class="col-12">
                                        <div class="section-title text-start ">
                                            <canvas id="doughnut-chart" width="300" height="250"></canvas>
                                            <h3 class="display-6 mb-3">항목제목</h3>
                                            <p class="mb-4 pb-2">
                                                설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명
                                            </p>
                                        </div>
                                    </div>

                                    <div class="col-12">
                                        <div class="section-title text-start ">
                                            <canvas id="horizontal-bar-chart" width="600" height="250"></canvas>
                                            <h3 class="display-6 mb-3">항목제목</h3>
                                            <p class="mb-4 pb-2">
                                                설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명
                                            </p>
                                        </div>
                                    </div>

                                    <div class="col-12">
                                        <div class="section-title text-start ">
                                            <img src="../../public/images/wordcloud.png" width="600">
                                            <h3 class="display-6 mb-3">항목제목</h3>
                                            <p class="mb-4 pb-2">
                                                설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명
                                            </p>
                                        </div>
                                    </div>
                                </div>
                                <!-- 항목영역시작 -->
                            </div>
                        </div>
                    </div>
                    <!-- 검사결과항목끝 -->
                    <div class="section-title text-start">
                        <button class="btn w-100 py-3 simulationIntro3" href="/">
                            목록으로
                        </button>
                    </div>

                </div>
            </div>
        </div>
    </div>
</template>

<!-- 차트 출처 : https://noanomal.tistory.com/3
실제 데이터 차트 직접 그리면 삭제해도됨 
-->
<script>
import { Chart, registerables } from 'chart.js';

Chart.register(...registerables);

export default {
    mounted() {
        new Chart(document.getElementById("bar-chart"), {
            type: 'bar',
            data: {
                labels: ["Africa", "Asia", "Europe", "Latin America", "North America"],
                datasets: [
                    {
                        label: "Population (millions)",
                        backgroundColor: ["#3e95cd", "#8e5ea2", "#3cba9f", "#e8c3b9", "#c45850"],
                        data: [2478, 5267, 734, 784, 433]
                    }
                ]
            },
            options: {
                plugins: {
                    legend: { display: false },
                    title: {
                        display: true,
                        text: 'Predicted world population (millions) in 2050'
                    }
                }
            }
        });

        new Chart(document.getElementById("line-chart"), {
            type: 'line',
            data: {
                labels: [1500, 1600, 1700, 1750, 1800, 1850, 1900, 1950, 1999, 2050],
                datasets: [
                    {
                        data: [86, 114, 106, 106, 107, 111, 133, 221, 783, 2478],
                        label: "Africa",
                        borderColor: "#3e95cd",
                        fill: false
                    }, {
                        data: [282, 350, 411, 502, 635, 809, 947, 1402, 3700, 5267],
                        label: "Asia",
                        borderColor: "#8e5ea2",
                        fill: false
                    }, {
                        data: [168, 170, 178, 190, 203, 276, 408, 547, 675, 734],
                        label: "Europe",
                        borderColor: "#3cba9f",
                        fill: false
                    }, {
                        data: [40, 20, 10, 16, 24, 38, 74, 167, 508, 784],
                        label: "Latin America",
                        borderColor: "#e8c3b9",
                        fill: false
                    }, {
                        data: [6, 3, 2, 2, 7, 26, 82, 172, 312, 433],
                        label: "North America",
                        borderColor: "#c45850",
                        fill: false
                    }
                ]
            },
            options: {
                plugins: {
                    title: {
                        display: true,
                        text: 'World population per region (in millions)'
                    }
                }
            }
        });

        new Chart(document.getElementById("pie-chart"), {
            type: 'pie',
            data: {
                labels: ["Africa", "Asia", "Europe", "Latin America", "North America"],
                datasets: [{
                    label: "Population (millions)",
                    backgroundColor: ["#3e95cd", "#8e5ea2", "#3cba9f", "#e8c3b9", "#c45850"],
                    data: [2478, 5267, 734, 784, 433]
                }]
            },
            options: {
                plugins: {
                    title: {
                        display: true,
                        text: 'Predicted world population (millions) in 2050'
                    }
                }
            }
        });

        new Chart(document.getElementById("radar-chart"), {
            type: 'radar',
            data: {
                labels: ["Africa", "Asia", "Europe", "Latin America", "North America"],
                datasets: [
                    {
                        label: "1950",
                        fill: true,
                        backgroundColor: "rgba(179,181,198,0.2)",
                        borderColor: "rgba(179,181,198,1)",
                        pointBorderColor: "#fff",
                        pointBackgroundColor: "rgba(179,181,198,1)",
                        data: [8.77, 55.61, 21.69, 6.62, 6.82]
                    }, {
                        label: "2050",
                        fill: true,
                        backgroundColor: "rgba(255,99,132,0.2)",
                        borderColor: "rgba(255,99,132,1)",
                        pointBorderColor: "#fff",
                        pointBackgroundColor: "rgba(255,99,132,1)",
                        data: [25.48, 54.16, 7.61, 8.06, 4.45]
                    }
                ]
            },
            options: {
                plugins: {
                    title: {
                        display: true,
                        text: 'Distribution in % of world population'
                    }
                }
            }
        });

        new Chart(document.getElementById("doughnut-chart"), {
            type: 'doughnut',
            data: {
                labels: ["Africa", "Asia", "Europe", "Latin America", "North America"],
                datasets: [
                    {
                        label: "Population (millions)",
                        backgroundColor: ["#3e95cd", "#8e5ea2", "#3cba9f", "#e8c3b9", "#c45850"],
                        data: [2478, 5267, 734, 784, 433]
                    }
                ]
            },
            options: {
                plugins: {
                    title: {
                        display: true,
                        text: 'Predicted world population (millions) in 2050'
                    }
                }
            }
        });

        new Chart(document.getElementById("horizontal-bar-chart"), {
            type: 'bar',
            data: {
                labels: ["Africa", "Asia", "Europe", "Latin America", "North America"],
                datasets: [
                    {
                        label: "Population (millions)",
                        backgroundColor: ["#3e95cd", "#8e5ea2", "#3cba9f", "#e8c3b9", "#c45850"],
                        data: [2478, 5267, 734, 784, 433]
                    }
                ]
            },
            options: {
                indexAxis: 'y',
                plugins: {
                    legend: { display: false },
                    title: {
                        display: true,
                        text: 'Predicted world population (millions) in 2050'
                    }
                }
            }
        });
        let card = document.querySelector('.card');
        card.addEventListener('click', click);


        function click(event) {
            let elem = event.currentTarget;
            if (elem.style.transform == "rotateY(180deg)") {
                elem.style.transform = "rotateY(0deg)";
            } else {
                elem.style.transform = "rotateY(180deg)";
            }
        }
    }
};
</script>

<style scoped>
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
    font-size: 25px
}

.card_bottom .card_name {
    font-size: 15px;
    color: #727174;
}

.card_bottom .card_date {
    font-size: 12px;
    color: #b4b4b6;
}
</style>

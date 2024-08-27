/*UI개발: 윤수영 */
<template>
    <div>
        <div class="main">
            
            <div class="container-xxl py-5" style="width:60%">
                <div class="d-flex justify-content-center">
                <div class="container">
                    <div class="position-relative overflow-hidden">
                    </div>
                    <div class="section-title text-center">
                        <h1 class="display-5 mb-5">자주 묻는 질문</h1>
                    </div>
                    <br>
                    <br>
                    <div class="row g-4 portfolio-container ">
                        <div class="rounded overflow-hidden">
                            <div class="border border-5 border-light border-top-0 p-4 ">
                                <details>
                                    <summary>Q : 심상챗은 어떤 서비스인가요?</summary>
                                    심상챗은 일상 속에서 느껴지는 나의 감정들을 기록하고 관리해주는 서비스입니다.
                                </details>
                            </div>
                            <div class="border border-5 border-light border-top-0 p-4">
                                <details>
                                    <summary>Q : S2 시뮬레이션 할 때 준비물이 있나요?</summary>
                                    네. 컴퓨터용 웹캠과 마이크가 필요합니다.
                                </details>
                            </div>
                            <div class="border border-5 border-light border-top-0 p-4">
                                <details>
                                    <summary>Q : 마이 다이어리는 어떤 서비스 인가요?</summary>
                                    회원님이 진행하신 그림진단, 시뮬레이션, 일기 목록을 통계로 보여드리는 서비스 입니다.
                                </details>
                            </div>
                       <div class="border border-5 border-light border-top-0 p-4 text-center">
                        <button class="btn w-30 py-3 simulationIntro3 mb-3" @click="gowrite">문의 하러 가기</button>
                        <button class="btn w-30 py-3 simulationIntro3" @click="gomyqna">나의 문의 목록</button>
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
export default {
    methods: {
        gowrite(){
            this.$router.push({name:'Qnawrite'}); 
        },
        gomyqna(){
            this.$router.push({name: 'Qnalist'})
        }
    },
};
    
</script>

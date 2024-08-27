/*UI개발:이승희*/
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
                    <div class="col-12 text-center">
                        <ul class="list-inline mb-5" id="portfolio-flters">
                            <li class="mx-2 active" data-filter="*">All</li>
                            <li class="mx-2" data-filter=".first">General Carpentry</li>
                            <li class="mx-2" data-filter=".second">Custom Carpentry</li>
                        </ul>
                    </div>
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
                                    <summary>Q : 질문입니다.</summary>
                                    답변입니다.
                                </details>
                            </div>
                            <div class="border border-5 border-light border-top-0 p-4">
                                <details>
                                    <summary>Q : 질문입니다.</summary>
                                    답변입니다.
                                </details>
                            </div>
                       <div class="border border-5 border-light border-top-0 p-4 text-center">
                        <button class="btn w-50 py-3 simulationIntro3 mb-3" @click="gowrite">문의 하러 가기</button>
                        <button class="btn w-50 py-3 simulationIntro3" @click="gomyqna">나의 문의 목록</button>
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
            this.$router.push({name:'MarketQnawrite'}); 
        },
        gomyqna(){
            this.$router.push({name: 'MarketQnalist'})
        }
    },
};
    
</script>
/*UI개발: 허호준 */
<template>
    <div>
        <div class="main">
            <div class="container-xxl py-5">
                <div class="container">
                    <div class="position-relative overflow-hidden">
                    </div>
                    <div class="section-title text-center">
    <div class="row">
        <div class="col-md-12">
            <div class="card-body text-center">
            <h2>대화형 상담</h2>
            <img src="/images/sing.gif" style="border-radius: 30px; width:550px">
            <br><br>
            <h3>chatbot : 오늘의 기분은 어떠세요?</h3>
            <br>
            <input type="text" placeholder="기분이 썩 좋지는 않네요." style="width:550px; height:45px;font-size:22px;">
            <br>
            <button type="button" class="btn btn-primary" style="width:275px">녹음/중지</button>
            <router-link to="/SimpleResultLoading"><button type="button" class="btn btn-primary" style="width:275px">결과확인</button></router-link>
            </div>
            
        </div>
        </div>

    </div>
    </div>
    </div>
    </div>
</div>

</template>
/*UI개발: 윤수영 */
<template>
  <div class="main">
    <div class="container-xxl py-5">
      <div class="container">
        <div class="section-title text-center">
          <h1 class="display-5 mb-5">서비스 소개</h1>
        </div>
        <div class="container-fluid bg-light overflow-hidden px-lg-0">
          <div class="container about px-lg-0">
            <div class="row g-0 mx-lg-0">
              <div class="col-lg-6 ps-lg-0">
                <div class="position-relative h-100">
                  <img src="../../public/images/image.png" style="height: 500px;margin-left:60%;"/>

                </div>
              </div>
              <div class="col-lg-6 about-text py-5">
                <div class="p-lg-5 pe-lg-0">
                  <div class="section-title text-start">
                    <h1 class="display-5 mb-4">심상챗은 감정 일기로 나의 마음을 기록하자는 생각에서 생겼습니다...</h1>
                  </div>
                  <p class="mb-4 pb-2">
                    당신의 인생이 삐뚤 빼뚤 놓여진 바느질 같다고 느껴진 적이 있나요? 당신이 가는 길이 바느질이라고 생각 해본적이 있나요?
때때로 우리는 한땀 한땀 나아가는 바느질 같은 인생에서 실이 엉키기도 하고 실이 엉킬 걱정 없는 기계를 보며 부러워도 하고 내 입장을 한탄하기도 합니다.
그럼에도 당신이 걸어온 길에는 한땀 한땀 소중하게 놓여진 바느질이 있습니다. 당신이 걸어온 길이 그 바느질처럼 삐뚤빼뚤 바지런하지는 않지만 그래도 꽤나 촘촘하고,열심히 달려온 당신의 길입니다.
우리가 삐뚤빼뚤 당신이 걸어가는 길에 조금이나마 힘이 되길 원합니다.
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="container">
          <div class="section-title text-center">
            <h1 class="display-5 mb-5">말로 하는 심리진단</h1>
            <p>질문에 대한 답변을 대화로 기록해서 진단해주는 서비스 입니다.</p>
          </div>
          <div class="section-title text-center">
            <h1 class="display-5 mb-5">그림을 그리는 HTP검사</h1>
            <p>집, 나무, 사람을 그리며 나의 현재 심리를 확인하는 검사입니다.</p>
          </div>
          <div class="section-title text-center">
            <h1 class="display-5 mb-5">상황극 서비스</h1>
            <p>내일 상사와 중요한 미팅이 있다거나, 학교에서 발표를 한다거나, <br>
              가족, 연인과 다퉈서 화해해야 한다거나,,,<br>
              지금까지 살면서 '아, 연습했으면 더 말이 잘 나왔겠지?' 하는 적 있었나요?<br>
              여러분 상황에 맞춘 챗봇과 상황극을 해보세요! <br>
              마음이 조금 편해질거에요.
            </p>
          </div>
          <div class="section-title text-center">
            <h1 class="display-5 mb-5">대화로 기록하는 일기장</h1>
            <p>일기는 쓰고싶고, 뭘 써야할지 모르겠다! 하는 사람들 많을거에요<br>
              귀여운 캐릭터와 함께 대화하면서 일기를 써봐요!<br>
              오늘 가장 많이 말했던 키워드, 감정을 분석해서 알려줘요!<br>
            </p>
          </div>
        </div>
        <div class="container">
          <div class="section-title text-center">
            <h1 class="display-5 mb-5">마음의 짐을 덜어줄 귀여운 AI 캐릭터</h1>
            <p>개발자들이 직접 그린 캐릭터와 대화하면서 걱정, 근심, 불안을 덜어봐요</p>
          </div>
          <div class="d-flex justify-content-center align-items-center">
          <div class="testimonial-item text-center">
            <img src="../../public/images/chunbae.png" class="img-fluid bg-light p-2 mx-auto mb-3" style="width: 90px; height: 90px" alt="" />
            <div class="testimonial-text text-center p-4">
              <h3>준배...</h3>
              <h5 class="mb-1">저는 같이 화내드릴게요!!!!!</h5>
              <span class="fst-italic">채팅 매니저</span>
            </div>
          </div>
          <div class="testimonial-item text-center mx-3">
            <img src="../../public/images/cuteeeee.png" class="img-fluid bg-light p-2 mx-auto mb-3" style="width: 90px; height: 90px" alt="" />
            <div class="testimonial-text text-center p-4">
              <h3>귀요미</h3>
              <h5 class="mb-1">전 귀여워요. 귀엽죠? 기분도 좋아질거에요</h5>
              <span class="fst-italic">채팅 매니저</span>
            </div>
          </div>
          <div class="testimonial-item text-center">
            <img src="../../public/images/happyyyy.png" class="img-fluid bg-light p-2 mx-auto mb-3" style="width: 90px; height: 90px" alt="" />
            <div class="testimonial-text text-center p-4">
              <h3>죠이</h3>
              <h5 class="mb-1">행복하다... 행복하다... 행복이 뭐지... </h5>
              <span class="fst-italic">채팅 매니저</span>
            </div>
          </div>
          </div>
      </div>
    </div>
  </div>
  </div>
</template>

<style scoped>
.d-flex {
    display: flex;
    flex-wrap: nowrap;
    gap: 20px;
}

.testimonial-item {
    flex: 1;
    min-width: 200px;
}
</style>
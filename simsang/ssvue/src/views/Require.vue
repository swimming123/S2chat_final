/*UI개발: 윤수영 */
<template>
  <div class="main">
    <div class="container-xxl py-5">
      <div class="container">
        <div class="section-title text-center">
          <h1 class="display-5 mb-5">개인정보 수집 이용 동의서</h1>
        </div>

        <div class="container">
          <div class="row g-4 portfolio-container">
            <div class="rounded overflow-hidden">
              <table class="table text-center">
                <thead>
                  <tr>
                    <th scope="col">수집목적</th>
                    <th scope="col">필수항목</th>
                    <th scope="col">선택항목</th>
                    <th scope="col">보유/이용기간</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>채팅 서비스 활용</td>
                    <td>채팅 내용</td>
                    <td>음성, 영상</td>
                    <td>수집일로부터 1년까지, 회원삭제 요청시 즉시 삭제</td>
                  </tr>
                </tbody>
              </table>

              <p>
                귀하는 위와같이 개인정보를 수집/이용하는데 동의를 거부할 권리가 있습니다.<br>
                필수 수즙 항목에 대한 동의를 거절하는 경우 서비스 이용이 제한될 수 있습니다.<br>
                선택 수집 항목에 동의를 하지 않으시는경우 별도의 불이익은 없으나 일부 서비스 이용이 제한될 수 있습니다.
              </p>
              <div class="col-12 text-center">
                <div class="form-check d-flex justify-content-center align-items-center mb-2">
                  <input class="form-check-input me-2" type="checkbox" value="" id="flexCheckDefault">
                  <label class="form-check-label" for="flexCheckDefault">
                    (필수)개인정보 수집/이용 동의서
                  </label>
                </div>
                <div class="form-check d-flex justify-content-center align-items-center mb-2">
                  <input class="form-check-input me-2" type="checkbox" value="" id="flexCheckChecked">
                  <label class="form-check-label" for="flexCheckChecked">
                    (선택)개인정보 수집/이용 동의서
                  </label>
                </div>
                <button class="btn simulationIntro3">동의합니다</button>

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
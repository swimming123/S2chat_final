/*UI개발: 허호준 */
<template>
  <div>
    <div class="main">
      <div class="container-xxl py-5">
        <div class="container">
          <div class="section-title text-center">
            <img src="/images/simulation/loading.svg" alt="Loading..." />
            <div class="simulLoading1">
              <p class="simulLoading2">결과 분석 중...</p>
              <p class="simulLoading2">잠시만 기다려주세요!</p>
              <p></p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  mounted() {
    // 일정 시간 후에 결과 페이지로 이동
    setTimeout(() => {
      this.$router.push("/SimpleResult");
    }, 4000); // 예: 5초 후에 이동
    window.scrollTo(0, 0);
  },
};
</script>
<style>
.simulLoading1 {
  margin-top: 50px;
  margin-bottom: 200px;
  color: #337df0;
  font-size: 25px;
  font-weight: 1000;
}
.simulLoading2 {
  margin-bottom: 5px;
}
</style>

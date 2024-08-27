/*UI개발:이지영*/
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
import axios from 'axios';

export default {
  name: 'SimulationResultLoading',
  data() {
    return {
      pollInterval: null, // 폴링 제어 타이머
      simuldjango: null, // 서버에서 가져온 데이터
    };
  },
  mounted() {
    window.scrollTo(0, 0);
    this.startPolling();
  },
  beforeDestroy() {
    // 컴포넌트가 파괴되기 전에 폴링 정지
    if (this.pollInterval) {
      clearInterval(this.pollInterval);
    }
  },
  methods: {
    startPolling() {
      this.pollInterval = setInterval(() => {
        this.fetchSimulResult();
      }, 2000); // 2초 간격으로 폴링
    },
    fetchSimulResult() {
      const simulationnum = this.$route.query.simulationnum;
      const status = this.$route.query.status;

      axios
        .get(`${process.env.VUE_APP_BACK_END_URL}/simulresults/simulresultlist`, {
          params: {
            simulationnum: simulationnum,
          },
        })
        .then((response) => {
          if (response.data && response.data.length > 0) {
            // 데이터가 존재하는 경우 결과 페이지로 이동
            this.simuldjango = response.data;
            this.navigateToResultPage(simulationnum, status);
          }
        })
        .catch((error) => {
          console.error("Error fetching simulation results:", error);
        });
    },
    navigateToResultPage(simulationnum, status) {
      if (this.pollInterval) {
        clearInterval(this.pollInterval); // 폴링 정지
      }
      this.$router.push({
        path: "/SimulationResult",
        query: {
          simulationnum: simulationnum,
          status: status,
        },
      });
    },
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
.container-xxl{
  margin-bottom: 3000px;
}
</style>

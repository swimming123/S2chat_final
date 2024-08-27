/*UI개발:이지영*/
<template>
  <div>
    <div class="main">
      <div class="container-xxl py-5" style="margin-bottom: 300px;">
        <div class="container">
          <div class="section-title text-center">
            <h1 class="display-5 mb-5">AI 시뮬레이션</h1>
            <h4 class="simulationintro1 emphasized-text">불안을 자주 느끼는 당신에게 꼭 필요한 서비스</h4>
            <p>
              불안의 요소가 있는 상황들을 시뮬레이션을 통해 불안을 이겨낼 수 있도록 심상챗이 도와드리겠습니다.
            </p>
            <p>여러 상황들 중 선택하여 영상 또는 챗팅으로 시뮬레이션이 가능합니다.</p>
            <video src="/video/simulationIntro.mp4" class="simulationintro2" alt="simulationIntro" muted autoplay playsinline loop></video>
            <br>
            <section class="introduction section-title text-center">
              <h1 class="simulationintro1">사전동의 안내</h1>
              <h5>아래 사전 동의 안내를 읽고 전체 동의가 되어야 시뮬레이션 진행이 가능합니다.</h5>
              <br>
              <div class="simulationIntro5">
                <div class="scrollable-content">
                  <div class="simulationIntro6">
                    <p>▶ 진행 도중 중단 시 진단 결과 확인이 불가합니다. 이에 동의하십니까?</p>
                    <div class="right-align">
                      <label>
                        <input type="radio" value="yes" v-model="answers[0]" /> 동의
                      </label>
                      <label>
                        <input type="radio" value="no" v-model="answers[0]" /> 동의하지 않음
                      </label>
                    </div>
                  </div>
                  <br>
                  <div class="simulationIntro6">
                    <p>▶ 상황극 진행 중 수집된 개인정보는 서비스 향상을 위해 사용됩니다. 이에 동의하십니까?</p>
                    <div class="right-align">
                      <label>
                        <input type="radio" value="yes" v-model="answers[1]" /> 동의
                      </label>
                      <label>
                        <input type="radio" value="no" v-model="answers[1]" /> 동의하지 않음
                      </label>
                    </div>
                  </div>
                  <br>
                  <div class="simulationIntro6">
                    <p>▶ 서비스 이용 중 심리 상담이 필요할 시 전문가와 연결될 수 있습니다. 이에 동의하십니까?</p>
                    <div class="right-align">
                      <label>
                        <input type="radio" value="yes" v-model="answers[2]" /> 동의
                      </label>
                      <label>
                        <input type="radio" value="no" v-model="answers[2]" /> 동의하지 않음
                      </label>
                    </div>
                  </div>
                  <br>
                  <div class="simulationIntro6">
                    <p>▶ 수집된 데이터는 1년 동안 보관되며 이후 자동 삭제됩니다. 이에 동의하십니까?</p>
                    <div class="right-align">
                      <label>
                        <input type="radio" value="yes" v-model="answers[3]" /> 동의
                      </label>
                      <label>
                        <input type="radio" value="no" v-model="answers[3]" /> 동의하지 않음
                      </label>
                    </div>
                  </div>
                  <br>
                </div>
                <div class="simulationIntro6">
                  <div class="right-align mt-2"> <!-- To push the checkbox to the right -->
                    <label class="simulationIntro4">
                      <input type="checkbox" @change="selectAllYes" /> 전체 동의
                    </label>
                  </div>
                </div>
              </div>
              <br>
              <button @click="handleStart" class="simulationIntro3">시뮬레이션 시작</button>
            </section>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'SimulationIntro',
  data() {
    return {
      isHeaderFixed: false,
      answers: ['', '', '', '']
    };
  },
  mounted() {
    window.addEventListener('scroll', this.handleScroll);
    window.scrollTo(0, 0);
  },
  beforeDestroy() {
    window.removeEventListener('scroll', this.handleScroll);
  },
  methods: {
    handleScroll() {
      this.isHeaderFixed = window.scrollY > 60;
    },
    handleStart() {
      if (this.answers.includes('') || this.answers.includes('no')) {
        alert('전체 동의가 되어야 상황극 진행이 가능합니다.');
      } else {
        this.$router.push('/SimulationCategory');
      }
    },
    selectAllYes(event) {
      if (event.target.checked) {
        this.answers = ['yes', 'yes', 'yes', 'yes'];
      } else {
        this.answers = ['', '', '', ''];
      }
    }
  }
};
</script>

<style>
.simulationintro1 {
  margin-bottom: 40px;
  margin-top: 20px;
}

.simulationintro2 {
  width: 100%;
  height: 100%;
  max-width: 1000px;
}

.simulationIntro3 {
  background-color: #fff;
  color: #337DF0;
  padding: 10px 20px;
  font-size: 1em;
  font-weight: 1000;
  border-radius: 25px;
  border: 4px solid #337DF0;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.simulationIntro3:hover {
  background-color: #337DF0;
  color: #fff;
  font-weight: 1000;
}

.simulationIntro4 {
  font-size: 20px;
}

.simulationIntro5 {
  max-width: 800px;
  border: 1px solid gray;
  border-radius: 10px;
  padding: 30px;
  margin: 0 auto;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1), 0 6px 20px rgba(0, 0, 0, 0.1);
}

.scrollable-content {
  height: 200px;
  overflow-y: auto;
  background-color: #f3f3f3;

}

.simulationIntro6 {
  text-align: left;
}

.simulationIntro6 .right-align {
  text-align: right;
}

input[type="radio"] {
  margin-left: 10px;
}

input[type="checkbox"] {
  margin-left: 10px;
}
</style>

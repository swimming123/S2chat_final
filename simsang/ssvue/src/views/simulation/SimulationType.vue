/*UI개발:이지영*/
<template>
  <div>
    <div class="main">
      <div class="container-xxl mb-5 py-5">
        <div class="container">
          <div class="section-title text-center">
            <h1 class="display-5 mb-5">시뮬레이션 고르기</h1>
            <div class="d-flex justify-content-center">
              <div class="row">
                <div>
                  <div class="simulType1 col-md-2">
                    <router-link
                      :to="{ path: '/SimulationVideo', query: { category: this.$route.query.category, type: '영상' } }"
                      class="simulTypea"
                      @click.native="selectType('영상')"
                    >
                      <div
                        class="simulType2"
                        data-text="영상 시뮬레이션"
                        style="background-image: url('/images/simulation/videosimul.png');"
                      ></div>
                    </router-link>
                  </div>
                  <div class="simulType1 col-md-2">
                    <router-link
                      :to="{ path: '/SimulationChat', query: { category: this.$route.query.category, type: '텍스트' } }"
                      class="simulTypea"
                      @click.native="selectType('텍스트')"
                    >
                      <div
                        class="simulType2"
                        data-text="텍스트 시뮬레이션"
                        style="background-image: url('/images/simulation/chatsimul.png');"
                      ></div>
                    </router-link>
                  </div>
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
import axios from 'axios';

export default {
  name: 'SimulationType',
  mounted() {
    window.scrollTo(0, 0);
  },
  methods: {
    async selectType(type) {
      try {
        // 서버에 시뮬레이션 타입 전송
        await axios.post(`http://192.168.92:9000/simulations`, {
          category: this.$route.query.category,
          type: type
        });
      } catch (error) {
        console.error('Error selecting simulation type:', error);
      }
    }
  }
};
</script>

<style>
.simulType1 {
  display: flex;
  justify-content: center;
  padding: 20px;
  width: 750px;
}

.simulType2 {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 600px;
  height: 337.5px;
  margin: 10px;
  background-color: #fff;
  color: transparent;
  font-weight: 500;
  font-size: 2em;
  font-family: "Black Han Sans", sans-serif;
  font-style: normal;
  border: 20px solid #337df0;
  cursor: pointer;
  transition: background-color 0.3s ease, color 0.3s ease;
  text-decoration: none;
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center;
}

.simulType2::before {
  content: attr(data-text);
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: #337df0;
  font-size: 2em;
  white-space: nowrap;
  text-align: center;
  opacity: 0;
  transition: opacity 0.3s ease;
}

.simulType2:hover {
  background-color: #337df0;
  background-image: none;
  color: #fff;
}

.simulType2:hover::before {
  opacity: 1;
}

.simulTypea {
  text-decoration: none;
}
</style>

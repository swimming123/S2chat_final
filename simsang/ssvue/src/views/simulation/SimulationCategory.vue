/*UI개발:이지영*/
<template>
  <div>
    <div class="main">
      <div class="container-xxl py-5" style="margin-bottom: 300px;">
        <div class="container">
          <div class="section-title text-center">
            <h1 class="display-5 mb-5">시뮬레이션 고르기</h1>
            <div class="d-flex justify-content-center align-items-center">
              <div class="description-text left-text">
                <img v-if="leftImage" :src="leftImage" alt="Left Image" class="description-image" />
              </div>
              <div class="simulCatContainer">
                <div class="simulCat3">
                  가족
                  <div class="simulCat1">
                    <router-link
                      :to="{ path: '/SimulationType', query: { category: '부모' } }"
                      class="simulCata"
                      @mouseover="updateLeftImage('/images/simulation/parent.png')"
                      @mouseleave="resetLeftImage"
                      @click="selectCategory('부모')"
                    >
                      <div class="simulCat2">부모</div>
                    </router-link>
                    <router-link
                      :to="{ path: '/SimulationType', query: { category: '형제' } }"
                      class="simulCata"
                      @mouseover="updateLeftImage('/images/simulation/brother.png')"
                      @mouseleave="resetLeftImage"
                      @click="selectCategory('형제')"
                    >
                      <div class="simulCat2">형제</div>
                    </router-link>
                    <router-link
                      :to="{ path: '/SimulationType', query: { category: '자매' } }"
                      class="simulCata"
                      @mouseover="updateLeftImage('/images/simulation/sister.png')"
                      @mouseleave="resetLeftImage"
                      @click="selectCategory('자매')"
                    >
                      <div class="simulCat2">자매</div>
                    </router-link>
                  </div>
                </div>
                <div class="simulCat3">
                  친구
                  <div class="simulCat1">
                    <router-link
                      :to="{ path: '/SimulationType', query: { category: '부탁' } }"
                      class="simulCata"
                      @mouseover="updateLeftImage('/images/simulation/request.png')"
                      @mouseleave="resetLeftImage"
                      @click="selectCategory('부탁')"
                    >
                      <div class="simulCat2">부탁</div>
                    </router-link>
                    <router-link
                      :to="{ path: '/SimulationType', query: { category: '거절' } }"
                      class="simulCata"
                      @mouseover="updateRightImage('/images/simulation/reject.png')"
                      @mouseleave="resetRightImage"
                      @click="selectCategory('거절')"
                    >
                      <div class="simulCat2">거절</div>
                    </router-link>
                    <router-link
                      :to="{ path: '/SimulationType', query: { category: '다툼' } }"
                      class="simulCata"
                      @mouseover="updateRightImage('/images/simulation/fight.png')"
                      @mouseleave="resetRightImage"
                      @click="selectCategory('다툼')"
                    >
                      <div class="simulCat2">다툼</div>
                    </router-link>
                  </div>
                </div>
                <div class="simulCat3">
                  회사
                  <div class="simulCat1">
                    <router-link
                      :to="{ path: '/SimulationType', query: { category: '상사' } }"
                      class="simulCata"
                      @mouseover="updateRightImage('/images/simulation/boss.png')"
                      @mouseleave="resetRightImage"
                      @click="selectCategory('상사')"
                    >
                      <div class="simulCat2">상사</div>
                    </router-link>
                    <router-link
                      :to="{ path: '/SimulationType', query: { category: '부하직원' } }"
                      class="simulCata"
                      @mouseover="updateRightImage('/images/simulation/salaryman.png')"
                      @mouseleave="resetRightImage"
                      @click="selectCategory('부하직원')"
                    >
                      <div class="simulCat2">부하직원</div>
                    </router-link>
                    <router-link
                      :to="{ path: '/SimulationType', query: { category: '발표' } }"
                      class="simulCata"
                      @mouseover="updateRightImage('/images/simulation/presentation.png')"
                      @mouseleave="resetRightImage"
                      @click="selectCategory('발표')"
                    >
                      <div class="simulCat2">발표</div>
                    </router-link>
                  </div>
                </div>
              </div>
              <div class="description-text right-text">
                <img v-if="rightImage" :src="rightImage" alt="Right Image" class="description-image" />
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
  name: 'SimulationCategory',
  mounted() {
    window.scrollTo(0, 0);
  },
  data() {
    return {
      leftImage: '',
      rightImage: ''
    };
  },
  methods: {
    updateLeftImage(image) {
      this.leftImage = image;
    },
    resetLeftImage() {
      this.leftImage = '';
    },
    updateRightImage(image) {
      this.rightImage = image;
    },
    resetRightImage() {
      this.rightImage = '';
    },
    async selectCategory(category) {
      try {
        await axios.post(`${process.env.VUE_APP_BACK_END_URL}/simulations`, {
          category: category,
          type: '일반' 
        });
      } catch (error) {
        console.error('Error selecting category:', error);
      }
    }
  }
};
</script>

<style scoped>
.description-text {
  flex: 1;
  padding: 20px;
  font-size: 1.2em;
  color: #313131;
  text-align: center;
  display: flex;
  align-items: center;
  justify-content: center;
}

.description-image {
  max-width: 100%;
  max-height: 100%;
  object-fit: contain;
}

.left-text {
  text-align: left;
}

.right-text {
  text-align: right;
}

.simulCatContainer {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 10px;
}

.simulCat3 {
  color: #313131;
  font-family: "Black Han Sans", sans-serif;
  font-style: normal;
  font-weight: 500;
  font-size: 1.5em;
  margin-top: 30px;
  flex: 1 1 200px;
}

.simulCat1 {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
}

.simulCat2 {
  display: flex;
  align-items: center;
  justify-content: center;
  font-family: "Black Han Sans", sans-serif;
  font-style: normal;
  text-align: center;
  width: 150px;
  height: 150px;
  margin: 10px;
  background-color: #fff;
  color: #337DF0;
  font-weight: 500;
  font-size: 35px;
  border: 10px solid #337DF0;
  cursor: pointer;
  transition: background-color 0.3s ease;
  text-decoration: none;
}

.simulCat2:hover {
  background-color: #337DF0;
  color: #fff;
  font-weight: 500;
}

.simulCata {
  text-decoration: none;
  display: block;
}
</style>

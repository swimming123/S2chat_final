/*UI개발: 허호준 */
<template>
  <div>
       <div class="main">
          <div class="position-relative overflow-hidden"></div>
                    <div class="section-title text-center">

          <h1 class="display-5 mb-5">간편 심리 검사를 선택하세요</h1>
          <br>
          <h5>간편 심리 검사는 자신의 증상에 대한 정도를 간단히 이해하고, 심리적인 어려움을 점검, 예방하기 위한 차원의 검사입니다.</h5>
          <h5>보다 정확한 진단을 위해서는 전문가와의 검사와 상담이 필요함 을 안내드립니다.</h5>
          <br>
          <div class="d-flex justify-content-center" style="height:600px;">
          <div class="row"  style="width: 65%;">
            <div class="col-md-2 d-flex flex-column align-items-center" v-for="(card, index) in cards" :key="index" :style="{ marginTop: index >= 6 ? '30px' : '0px' }">
              <div class="card" style="width: 14.5rem; height: 13rem;">
                <div style="height: 200px; overflow: hidden; display: flex; justify-content: center; align-items: center;">
                  <img :src="card.image" class="card-img-top" alt="..." style="height: 100%; object-fit: cover;">
                </div>
                <div class="card-body text-center">
                  <button class="btn btn-primary dropdown-hover">{{ card.title }}</button>
                  <ul class="dropdown-menu">
                    <li v-for="(option, i) in card.options" :key="i">
                      <router-link :to="option.route">
                        <button class="dropdown-item">{{ option.text }}</button>
                      </router-link>
                </li>
              </ul>
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
  name: "CardComponent",
  mounted() {
    window.scrollTo(0, 0);
  },
  data() {
    return {
      cards: [
        { title: '우울증', image: '/images/noimg.png', options: [
          { text: '채팅', route: '/Schat' },
          { text: '대화', route: '/Stalk' }
        ] },
        { title: 'ADHD', image: '/images/noimg.png', options: [
          { text: '채팅', route: '/Schat' },
          { text: '대화', route: '/Stalk' }
        ] },
        { title: '스트레스', image: '/images/noimg.png', options: [
          { text: '채팅', route: '/Schat' },
          { text: '대화', route: '/Stalk' }
        ] },
        { title: '공황장애', image: '/images/noimg.png', options: [
          { text: '채팅', route: '/Schat' },
          { text: '대화', route: '/Stalk' }
        ] },
        { title: '분노조절장애', image: '/images/noimg.png', options: [
          { text: '채팅', route: '/Schat' },
          { text: '대화', route: '/Stalk' }
        ] },
        { title: '외상후스트레스', image: '/images/noimg.png', options: [
          { text: '채팅', route: '/Schat' },
          { text: '대화', route: '/Stalk' }
        ] },
        { title: '스마트폰&게임중독', image: '/images/noimg.png', options: [
          { text: '채팅', route: '/Schat' },
          { text: '대화', route: '/Stalk' }
        ] },
        { title: '치매', image: '/images/noimg.png', options: [
          { text: '채팅', route: '/Schat' },
          { text: '대화', route: '/Stalk' }
        ] },
        { title: '산후우울증', image: '/images/noimg.png', options: [
          { text: '채팅', route: '/Schat' },
          { text: '대화', route: '/Stalk' }
        ] },
        { title: '강박증(결벽증)', image: '/images/noimg.png', options: [
          { text: '채팅', route: '/Schat' },
          { text: '대화', route: '/Stalk' }
        ] },
        { title: '번아웃증후군', image: '/images/noimg.png', options: [
          { text: '채팅', route: '/Schat' },
          { text: '대화', route: '/Stalk' }
        ] },
        { title: '관계중독', image: '/images/noimg.png', options: [
          { text: '채팅', route: '/Schat' },
          { text: '대화', route: '/Stalk' }
        ] },
      ]
    }
  }
}
</script>


<style>
/* Dropdown hover styling */
.card-body {
  position: relative;
}
.container-xxl py-5{
  width: 100%;
}

.card-body .dropdown-menu {
  display: none;
  position: absolute;
  top: 100%;
  left: 50%;
  transform: translateX(-50%);
  z-index: 1000;
  float: left;
  min-width: 10rem;
  padding: 0.5rem 0;
  margin: 0.125rem 0 0;
  font-size: 1rem;
  color: #212529;
  text-align: left;
  list-style: none;
  background-color: #fff;
  background-clip: padding-box;
  border: 1px solid rgba(0, 0, 0, 0.15);
  border-radius: 0.25rem;
}

.card-body:hover .dropdown-menu {
  display: block;
}
</style>
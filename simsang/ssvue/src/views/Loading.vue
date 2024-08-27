/*UI개발: 윤수영 */
<template>
  <div class="main">
    <div class="container-xxl py-5">
      <div class="container">
        <div id="load" ref="loadingPage">
          
          <img src="../../public/images/Dancing_kitty.gif" alt="loading">
          <!-- <img src="../../public/images/Cat_licking_paw.gif" alt="loading"> -->
        </div>
      </div>
    </div>
  </div>
</template>
<!-- 이미지 출처 : https://icons8.com/preloaders/en/search/cat -->
 <!-- 로딩중 코드 :  https://junia3.github.io/blog/loading-->
<script>
export default {
  mounted() {
    // this.$refs.loadingPage.style.display = 'none';
  }
}
</script>

<style scoped>
#load {
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  position: fixed;
  display: block;
  opacity: 0.8;
  background: white;
  z-index: 99;
  text-align: center;
}

#load > img {
  position: absolute;
  top: 50%;
  left: 50%;
  z-index: 100;
  transform: translate(-50%, -50%);
}
</style>

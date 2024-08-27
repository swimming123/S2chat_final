/*UI개발: 정준영 */
<template>
  <div class="main">
      <div class="container-xxl py-5">
          <div class="container">
              <div class="position-relative overflow-hidden">
              </div>

                <button class="button3"><router-link to="OpenChatList">뒤로가기</router-link></button>

              <div class="section-title text-center">
                  <h1>마음의 치유가 필요한 사람</h1>
                  <div class="hashtags">
                      <span>#마상</span>
                      <span>#마음의 상처</span>
                      <span>#위로</span>
                      <span>#위안</span>
                      <span>#친구</span>
                      <span>#진심어린말</span>
                      <span>#상담</span>
                      <span>#치료</span>
                  </div>
                  <div class="jy_jy_info-boxes">
                      <div class="jy_info-box">커뮤니티 방장</div>
                      <div class="jy_info-box">커뮤니티 개설일</div>
                  </div>
                  <div class="jy_options">
                      <div class="jy_like">
                          <span>선호도 값</span>
                      </div>
                      <button class="button3">
                        <router-link to="OpenChat">커뮤니티 참여하기</router-link></button>
                  </div>
              </div>
          </div>
      </div>
  </div>
</template>
<style scoped>
.button3 {
  background-color: #fff;
  color: #337DF0;
  font-weight:1000;
  padding: 10px 20px;
  font-size: 1em;
  border-radius: 25px;
  border:3px solid #337DF0;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.button3:hover {
  background-color: #337DF0;
  color: #fff;
  font-weight:1000;
}
.jy_hashtags span {
    display: inline-block;
    margin: 0 0.5rem;
    font-size: 1rem;
}

.jy_options {
    display: flex;
    justify-content: center;
    margin-top: 1rem;
}

.jy_like {
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: #ff69b4;
    padding: 0.5rem 1rem;
    margin-right: 0.5rem;
}
.jy_jy_info-boxes {
    display: flex;
    justify-content: center;
    margin-top: 1rem;
}

.jy_info-box {
    background-color: #f0f0f0;
    color: #333;
    font-weight: 1000;
    padding: 10px 20px;
    font-size: 1em;
    border-radius: 10px;
    border: 1px solid #ccc;
    margin: 0 0.5rem;
    text-align: center;
    transition: background-color 0.3s ease;
}

.jy_info-box:hover {
    background-color: #e0e0e0;
}
</style>

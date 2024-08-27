/*UI개발: 정준영 */
<template>
  <div>
    <div class="main">
      <div class="container-xxl py-5">
        <div class="container" style="width:60%;">
          <div class="position-relative jy_jy_overflow-hidden"></div>
          <!-- 제목 영역 -->
          <button class="button3"><router-link to="/">뒤로가기</router-link></button>
          <div class="jy_section-title text-center">
            <h1>커뮤니티 목록</h1>
          </div>
          
          <div class="jy_item-container" v-for="comm in community" :key="comm.num">
            <!-- 첫 번째 항목 -->
            <div class="jy_card item">
              <div class="jy_jy_card-body">
                <p style="display: none;">{{ comm.num }}</p>
                <p style="display: none;">{{ comm.membernum }}</p>
                <h4 class="jy_item-title">{{comm.name}}</h4>
                <p class="mt-1 mb-1">총 방문수 : {{ comm.people }}</p>
                <p class="mb-1">개설일 : {{ comm.communitydate }}</p>
                <div>
                  <button><span>{{ comm.favorite }}</span></button>
                </div>
                
              </div>
              <div>
                <button class="button3" @click="fetchtalk(comm.num)">채팅하기</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import axios from 'axios'
export default{
    data() {
        return {
          community:{
                num:'',
                membernum:localStorage.getItem("membernum"),
                name:'',
                people:'',
                favorite:'',
                communitydate:''
            },
          }
        
    },
    created() {
      this.fetchcomm();
    },
    methods: {
        fetchcomm(){
          axios.get(`${process.env.VUE_APP_BACK_END_URL}/comm/commlist`)
          .then(response => {
              this.community = response.data;
          })
          .catch(error => {
              console.log(error);
          })
        },
        fetchtalk(num){
          this.$router.push({name:'OpenChat', params: num});
        }
    }
}
</script>
<style>
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
.jy_overflow-hidden {
  overflow: hidden;
}

.jy_section-title {
  margin-bottom: 40px;
}

.jy_item-container {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.jy_card {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #f5f5f5;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.jy_jy_card-body {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.jy_item-title {
  font-weight: bold;
}

.jy_button-container {
  display: flex;
  justify-content: flex-end;
}

.router-link {
  text-decoration: none;
  color: inherit;
}
.jy_like {
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: #ff69b4;
    padding: 0.5rem 1rem;
    margin-right: 0.5rem;
}
</style>

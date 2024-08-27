/*UI개발: 이승희, 이지영 */
<template>
    <div class="main">
      <div class="container mt-3">
        <div class="row justify-content-center align-items-center">
          <div class="col-md-6 col-lg-4">
            <div class="head_title text-center" style="margin-top:60px;">
                  <h4 style="color: dimgrey;">고객님의 계정을 찾았습니다.</h4>
                  <p>아이디 확인 후 로그인해주세요.</p>
                </div>
  
                <div class="main_contact_content">
                  <div class="contact_form_area">
                    <form action="#" id="formid">
                      <div class="row" style="display: flex;">
                        <div class="col-xs-3">
                        <!-- <img src="../../public/images/member.png" class="imgCircle"> -->
                        </div>
                        <div class="col-xs-9" style="margin:auto;">
                        <p>ID : {{ data.id }}<br>가입일 : {{ data.mdate }} </p>
                      </div>
                      </div>
                      <div class="row" style="    
                      height: 45px;">
  
                      </div>
  
                      <div class="row" style="margin-bottom:10px;">
                        <router-link to="/findpwd"><button class="w-100 btn btn-primary" type="button">비밀번호 찾기</button></router-link>
                        </div>
                        <div class="row" style="margin-bottom:180px;">
                          <router-link to="/login"><button class="w-100 btn btn-primary" type="button">로그인</button></router-link>
                        </div>
                    </form>
                  </div>
                </div>
          </div>
        </div>
      </div>
    </div>
  </template>
  <script>
  export default {
    
    data() {
      return {
        data:""
      };
    },
    mounted(){
      this.data = this.$route.params
    },
    methods:{
    }
  }
  </script>
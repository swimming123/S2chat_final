/*UI개발: 이승희, 이지영 */
<template>
    <div class="main">
      <div class="container mt-3">
        <div class="row justify-content-center align-items-center">
          <div class="col-md-6 col-lg-4">
            <div class="head_title text-center" style="margin-top:60px;">
                  <h4 style="color: dimgrey;">고객님의 계정을 찾았습니다.</h4>
                  <p>아이디와 비밀번호를 확인 후 로그인해주세요.</p>
                </div>
  
                <div class="main_contact_content">
                  <div class="contact_form_area">
                    <form action="#" id="formid">
                      <div class="row" style="display: flex;">
                        <div class="col-xs-3">
                        <!-- <img src="../../public/images/member.png" class="imgCircle"> -->
                        </div>
                        <div class="col-xs-9" style="margin:auto;">
                        <p>Email : {{ data.email }}<br> 이메일로 임시 비밀번호가 발송되었습니다.</p>
                     
                      </div>
                      </div>
                      <div class="row" style="    
                      height: 45px;">
  
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
      this.data = this.$route.params;
    },
    methods:{
    }
  }
  </script>
 
<style>

.form-control {
    font-family: 'NotoSansKR';
    height: 45px;
    border:1px solid gray;
    border-radius:  5px;
    background-color: rgb(235, 230, 230);

}
.form-control:focus{
    border:1px solid rgb(233, 101, 53);

}

.router-txt{
  font: normal 16px "NotoSansKR", arial;
  color: rgb(109, 108, 108);
}

h2 {
    font-size: 40px;
    line-height: 38px;
    font-weight: 600;
    font-family: 'NotoSansKR';
    margin: 0;
}

p {
    font: normal 16px "NotoSansKR", arial;
}

label,a {
    font: bold 16px "NotoSansKR", arial;
    font-weight: bold;
}

.btn1{
    border:none;
    border-radius:  5px;
    background-color: rgb(233, 101, 53);
    width:100%;
    height: 50px;
    font: bold 16px "NotoSansKR", arial;
    font-weight: bold;
    color: antiquewhite;
}


.btn2{
    border:1px solid rgb(233, 101, 53);
    border-radius:  5px;
    width:100%;
    height: 50px;
    font: bold 16px "NotoSansKR", arial;
    font-weight: bold;
    color: rgb(233, 101, 53);
}

.imgCircle{
  width:80px;


  background-color: white;
  border-radius: 100%;
}
</style>

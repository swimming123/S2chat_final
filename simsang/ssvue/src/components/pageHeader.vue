<template>
  <div id="header" class="header">
    <!-- Topbar Start -->
    <!-- <div class="container-fluid bg-light p-0">
      <div class="row d-flex justify-content-center align-items-center">
        <div class="d-flex justify-content-center align-items-center">
          <div class="py-1">
            <small>상담전화 : 010 1234 5678</small>
          </div>
        </div>
      </div>
    </div> -->
    <!-- Topbar End -->

    <!-- Navbar Start -->
    <nav class="navbar navbar-expand-lg bg-white navbar-light sticky-top p-0">
      <a href="/" class="navbar-brand d-flex align-items-center px-4 px-lg-5">
        <h1 class="mt-3 text-mint">S2 Chat</h1>
      </a>
      <button
        type="button"
        class="navbar-toggler me-4"
        data-bs-toggle="collapse"
        data-bs-target="#navbarCollapse"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarCollapse">
        <div class="navbar-nav m-auto p-4 p-lg-0">
          <a href="HtpStart" class="nav-item nav-link">S2 그림진단</a>
          <!-- <a href="Schat" class="nav-item nav-link">자가심리진단</a> -->

          <a href="SimulationIntro" class="nav-item nav-link">S2 시뮬레이션</a>
          <a href="OpenChatList" class="nav-item nav-link">마음 나누기</a>
          <a href="Qnainfo" class="nav-item nav-link">Q&A 상담</a>

          <a href="DiaryAdd" class="nav-item nav-link">오늘의 마음 기록</a>
          <a href="MyPageTurn" class="nav-item nav-link">나의 마음 책장</a>
          <a href="mypageview" class="nav-item nav-link">MyㄷrOl어ㄹl...♡</a>
          <!--추후 로그인 session 배우면 마이페이지 대신에 넣을 목록태그-->
          <!-- 드롭다운 메뉴 -->
          <!-- <div class="nav-item dropdown">
            <a href="#" class="nav-link" data-bs-toggle="dropdown"
              >추가 메뉴 <i class="bi bi-chevron-down"></i
            ></a>
            <div class="dropdown-menu rounded m-0">
              <a href="Option1" class="dropdown-item">옵션 1</a>
              <a href="Option2" class="dropdown-item">옵션 2</a>
              <a href="Option3" class="dropdown-item">옵션 3</a>
            </div>
          </div> -->
        </div>

        <!--  <a href="" class="btn btn-primary py-4 px-lg-5 d-none d-lg-block">Get A Quote<i class="fa fa-arrow-right ms-3"></i></a> -->
        <div class="d-flex align-items-center">
          <a href="MarketHome">
            <button class="btn btn-primary market-btn" type="button">심상Market</button>
          </a>
          <template v-if="isAuthenticated">
            <button class="btn btn-primary ms-2" type="button" @click="logout">
              로그아웃
            </button>
            <a href="mypageview">
              <!-- <button class="btn btn-primary ms-2" type="button">마이페이지</button> -->
            </a>
          </template>
          <template v-else>
            <a href="Login">
              <button class="btn btn-primary ms-2" type="button">로그인</button>
            </a>
            <a href="Sign">
              <button class="btn btn-primary ms-2" type="button">회원가입</button>
            </a>
          </template>

          <!-- <c:when test="${sessionScope.sessionID != null }">
              <a >
                <button class="btn btn-primary" type="button">로그아웃</button>
              </a>

              <a >
                <button class="btn btn-primary" type="button">
                  마이페이지
                </button></a
              >
            </c:when> -->
        </div>
      </div>
    </nav>
    <!-- Navbar End -->
  </div>
  <!-- End of header -->
</template>

<script>
import { mapActions, mapGetters } from "vuex";
export default {
  name: "pageHeader",
  computed: {
    ...mapGetters(["isAuthenticated"]),
  },
  methods: {
    ...mapActions(["logout"]),
  },
};
</script>

<style scoped>
/* Ensure the navbar items are aligned properly on smaller screens */
.navbar-nav {
  flex-wrap: nowrap;
}

.navbar-nav .nav-link {
  white-space: nowrap;
}

/* Style the buttons to prevent them from wrapping */
.btn {
  margin: 0 5px; /* Adjust as necessary for spacing */
  white-space: nowrap; /* Prevent text from wrapping */
}

/* Specific style for the Market button */
.market-btn {
  background-color: beige;
  color: slategrey;
  border: 1px solid slategrey;
  white-space: nowrap; /* Prevent text from wrapping */
}

/* Ensure the navbar toggler button doesn't break the layout */
.navbar-toggler {
  border: none;
}

/* Ensure the buttons and text do not wrap */
@media (max-width: 768px) {
  .navbar-nav {
    margin-top: 10px;
  }
  .btn {
    margin-bottom: 10px;
    width: 100%;
  }
}
</style>

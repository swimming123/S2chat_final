/*UI개발: 이승희 */
<style>
.scroll-buttons {
  position: fixed;
  top: 50%;
  right: 50px;
  transform: translateY(-50%);
  display: flex;
  flex-direction: column;
  gap: 30px;
  z-index: 1000;
}

.circle-button {
  padding: 8px;
  border-radius: 50%;
  background-color: white;
  border: 3px solid transparent;
  transition: background-color 0.3s, box-shadow 0.3s;
}

.circle-button:hover {
  background-color: rgba(255, 255, 255, 0.5);
  box-shadow: 0 0 0 3px #ffffff;
  border-color: rgba(0, 0, 0, 0);
}

.circle-button.active {
  background-color: #38b8ad;
  box-shadow: 0 0 0 3px #ffffff;
  border-color: rgba(0, 0, 0, 0);
}

.fixed {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  z-index: 1000;
  background: #fff;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.show {
  display: block;
}

.overlay {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: rgba(25, 209, 194, 0.74);
  color: white;
  opacity: 0;
  transition: opacity 0.3s ease;
  text-align: center;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;
}

.imgbox {
  border: 1px solid rgb(136, 136, 136);
  border-radius: 5rem;
  padding: 30px;
  position: relative;
  overflow: hidden;
  align-items: center;
  justify-content: center;
  height: 100%;
}

.imgbox img {
  height: auto;
  border-radius: 5rem;
}

.imgbox:hover .overlay {
  opacity: 1;
}

.gan {
  justify-content: center;
  margin: 0 auto;
  max-width: 100%;
}
</style>

<template>
  <div>
    <!-- Scroll Buttons -->
    <div class="scroll-buttons">
      <button
        @click="scrollToSection('section1')"
        class="circle-button"
      ></button>
      <button
        @click="scrollToSection('section2')"
        class="circle-button"
      ></button>
      <button
        @click="scrollToSection('section3')"
        class="circle-button"
      ></button>
      <button
        @click="scrollToSection('section4')"
        class="circle-button"
      ></button>
      <button
        @click="scrollToSection('section5')"
        class="circle-button"
      ></button>
    </div>

    <!-- Back to Top Button -->
    <section id="section1" class="section" alt="메인페이지1">
      <div
        class="h-100 d-flex align-items-center justify-content-center"
        style="background-color: #38b8ad; height: 95vh"
      >
        <div class="video-container text-center">
          <video
            src="/video/simsang3.mp4"
            class="video-fluid"
            style="min-height: 100vh; width: 100%; object-fit: contain"
            muted
            autoplay
            playsinline
            loop
          ></video>
        </div>
      </div>
    </section>

    <!-- Feature Start -->
    <section id="section2" class="section" alt="메인페이지2">
      <div class="position-relative">
        <div class="bg-light">
          <div class="row g-0 mx-lg-0">
            <div class="col-lg-6 ps-lg-0" style="min-height: 400px">
              <div class="position-relative h-100">
                <img src=/images/main/drawing4.jpg class="card-img-top"
                style="height: 100vh;" alt=""/>
                <div
                  class="position-absolute top-0 start-0 w-100 h-100 d-flex align-items-center"
                >
                  <div class="container">
                    <div class="row justify-content-center">
                      <div class="col-12 col-lg-8 text-center">
                        <div
                          class="text-end p-5"
                          style="
                            border-radius: 100px;
                            background-color: rgba(
                              255,
                              255,
                              255,
                              0.8
                            );
                          "
                        >
                          <div class="section-title text-center">
                            <h1 class="display-5 mb-4">그림진단</h1>
                            <p class="mb-4 pb-2">
                              H(House), T(Tree), P(Person)<br />
                              세가지 항목 검사를 통해 내면의 심리상태를<br />
                              자세히 알아볼 수 있는 검사입니다.
                            </p>
                            <a
                              href="HtpStart"
                              class="btn btn-primary py-3 px-5"
                            >
                              검사하기
                            </a>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-lg-6 ps-lg-0" style="min-height: 400px">
              <div class="position-relative h-100">
                <img src=/images/main/pen1.jpg class="card-img-top"
                style="height: 100vh;" alt=""/>
                <div
                  class="position-absolute top-0 start-0 w-100 h-100 d-flex align-items-center"
                >
                  <div class="container">
                    <div class="row justify-content-center">
                      <div class="col-12 col-lg-8 text-center">
                        <div
                          class="text-end p-5"
                          style="
                            border-radius: 100px;
                            background-color: rgba(
                              255,
                              255,
                              255,
                              0.8
                            );
                          "
                        >
                          <div class="section-title text-center">
                            <h1 class="display-5 mb-4">설문진단</h1>
                            <p class="mb-4 pb-2">
                              자가진단테스트는 자신의 증상에 대한 정도를 간단히
                              이해하고,<br />
                              심리적인 어려움을 점검, 예방하기 위한 검사입니다.
                            </p>
                            <a
                              href="SimpleSurveyStart"
                              class="btn btn-primary py-3 px-5"
                            >
                              검사하기
                            </a>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Feature Start -->

    <section id="section3" class="section" alt="메인페이지3">
      <div
        class="h-100 d-flex align-items-center justify-content-center position-relative"
        style="height: 95vh"
      >
        <div class="video-container text-center w-100 d-flex">
          <video
            src="/video/Simple.mp4"
            class="card-img-top"
            muted
            autoplay
            loop
            style="height: 100vh; width: 100%; object-fit: cover"
          ></video>
        </div>
        <div
          class="position-absolute top-0 start-0 w-100 h-100 d-flex align-items-center"
        >
          <div class="container">
            <div class="row justify-content-center">
              <div class="col-12 col-lg-6">
                <div
                  class="text-end p-5"
                  style="
                    border-radius: 100px;
                    background-color: rgba(
                      255,
                      255,
                      255,
                      0.8
                    ); 
                  "
                >
                  <div class="section-title text-center">
                    <h1 class="display-5 mb-4">감정일기</h1>
                    <p class="mb-4 pb-2" style="line-height: 2.5">
                      감정일기는 개인의 감정을 기록하고 반성하는 데 도움을 주는
                      일기입니다. <br />이 일기는 정기적으로 감정과 감정에 대한
                      경험을 기록하여 자기 이해를 깊게 하고, <br />스트레스와
                      감정적 어려움을 관리하는 데 도움을 줍니다.
                    </p>
                    <a href="DiaryTalkView" class="btn btn-primary py-3 px-5">
                      일기쓰기
                    </a>
                    <a href="DiaryViews" class="btn btn-primary py-3 px-5">
                      일기보기
                    </a>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Feature Start -->

    <section id="section4" class="section" alt="메인페이지4">
      <div
        class="h-100 d-flex align-items-center justify-content-center position-relative"
        style="height: 100vh"
      >
        <div class="video-container text-center w-100 d-flex">
          <video
            src="/video/movie.mp4"
            class="card-img-top"
            muted
            autoplay
            loop
            style="height: 100vh; width: 100%; object-fit: cover"
          ></video>
        </div>
        <div
          class="position-absolute top-0 start-0 w-100 h-100 d-flex align-items-center"
        >
          <div class="container">
            <div class="row justify-content-center">
              <div class="col-12 col-lg-6">
                <div
                  class="text-end p-5"
                  style="
                    border-radius: 100px;
                    background-color: rgba(
                      255,
                      255,
                      255,
                      0.8
                    );
                  "
                >
                  <div class="section-title text-center">
                    <h1 class="display-5 mb-4">AI 시뮬레이션</h1>
                    <p
                      class="mb-4 pb-2"
                      style="line-height: 2.5; font-weight: 600"
                    >
                      사용자가 다양한 상황에 맞춘 시나리오에서 특정 역할을
                      수행하고, <br />
                      AI와 함께 상호작용하며 연기하는 "체험형 활동"입니다.
                      <br />이 활동은 심리 훈련, 교육, 엔터테인먼트 등의
                      목적으로 활용될 수 있습니다. <br />상황극을 통해 사용자는
                      다양한 역할을 연기하며 새로운 상황에 대한 반응을 연습하고,
                      <br />
                      문제 해결 능력을 기를 수 있습니다.
                    </p>

                    <a
                      href="SimulationIntro"
                      class="btn btn-primary py-3 px-5"
                      style="
                        background-color: beige;
                        border: 2px solid black;
                        color: black;
                        font-weight: bold;
                      "
                    >
                      시뮬레이션 시작
                    </a>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Feature Start -->
    <section id="section5" class="section" alt="메인페이지5">
      <div
        class="d-flex align-items-center justify-content-center position-relative"
        style="min-height: 100vh; background-color: #38b8ad"
      >
        <div class="top-0 start-0 w-100 h-100 d-flex align-items-center">
          <div class="container">
            <div class="row justify-content-center">
              <div
                class="top-0 start-0 w-100 h-100 d-flex align-items-center text-center justify-content-center"
              >
                <div class="text-white py-5 m-5 row justify-content-center">
                  <div class="col-xl-10">
                    <h1 class="display-5 mb-5">사이트 정보</h1>

                    <div
                      class="row bg-white p-5 gan"
                      style="border-radius: 100px; color: black"
                    >
                      <div class="col-lg-4 col-sm-12 position-relative mb-4">
                        <div class="imgbox m-2">
                          <img
                            src="/images/main/now.jpg"
                            class="card-img-top h-100"
                            alt="커뮤니티" style="object-fit: cover;"
                          />
                          <div class="overlay">
                            <h5>커뮤니티</h5>
                            <a href="OpenChat" class="btn btn-primary py-3 px-5"
                              >더 알아보기</a
                            >
                          </div>
                        </div>
                      </div>

                      <div class="col-lg-4 col-sm-12 position-relative mb-4">
                        <div class="imgbox m-2">
                          <img
                            src="/images/main/chat1.png"
                            class="card-img-top h-100"
                            alt="커뮤니티" style="object-fit: contain;"
                          />
                          <div class="overlay">
                            <h5>커뮤니티</h5>
                            <a href="OpenChat" class="btn btn-primary py-3 px-5"
                              >더 알아보기</a
                            >
                          </div>
                        </div>
                      </div>
                      <div class="col-lg-4 col-sm-12 position-relative mb-4">
                        <div class="imgbox m-2">
                          <img
                            src="/images/main/question.png"
                            class="card-img-top h-100"
                            alt="커뮤니티" style="object-fit: contain;"
                          />
                          <div class="overlay">
                            <h5>커뮤니티</h5>
                            <a href="OpenChat" class="btn btn-primary py-3 px-5"
                              >더 알아보기</a
                            >
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
export default {
  name: "Main",
  data() {
    return {
      currentIndex: 0,
      isScrolling: false,
      scrollDelay: 150,
      lastScrollTime: 0,
      ticking: false,
    };
  },
  methods: {
    scrollToSection(sectionId) {
      const section = document.getElementById(sectionId);
      if (section) {
        this.smoothScrollTo(section.offsetTop, 300);
        this.currentIndex = [...document.querySelectorAll(".section")].indexOf(
          section
        );
      }
    },
    smoothScrollTo(targetY, duration) {
      const startY = window.pageYOffset;
      const distance = targetY - startY;
      const startTime = performance.now();

      const scrollStep = (currentTime) => {
        const elapsedTime = currentTime - startTime;
        const progress = Math.min(elapsedTime / duration, 1);
        const easing = this.easeInOutQuad(progress);
        window.scrollTo(0, startY + distance * easing);

        if (elapsedTime < duration) {
          requestAnimationFrame(scrollStep);
        } else {
          this.isScrolling = false;
        }
      };

      this.easeInOutQuad = (t) => (t < 0.5 ? 2 * t * t : -1 + (4 - 2 * t) * t);

      this.isScrolling = true;
      requestAnimationFrame(scrollStep);
    },
    updateButtons() {
      const scrollPosition = window.pageYOffset;
      document.querySelectorAll(".section").forEach((section, index) => {
        const sectionTop = section.offsetTop;
        const sectionBottom = sectionTop + section.offsetHeight;
        const button = document.querySelectorAll(
          ".scroll-buttons .circle-button"
        )[index];
        if (scrollPosition >= sectionTop && scrollPosition < sectionBottom) {
          button.classList.add("active");
          this.currentIndex = index;
        } else {
          button.classList.remove("active");
        }
      });
    },
    handleWheel(event) {
      const now = Date.now();
      if (now - this.lastScrollTime < this.scrollDelay) return;
      this.lastScrollTime = now;

      event.preventDefault();
      if (this.isScrolling) return;

      const sections = document.querySelectorAll(".section");

      if (event.deltaY > 0) {
        this.scrollToSection(sections[this.currentIndex + 1]?.id);
      } else {
        this.scrollToSection(sections[this.currentIndex - 1]?.id);
      }
    },
  },
  mounted() {
    window.addEventListener("scroll", () => {
      if (!this.ticking) {
        window.requestAnimationFrame(() => {
          this.updateButtons();
          this.ticking = false;
        });
        this.ticking = true;
      }
    });

    window.addEventListener("wheel", this.handleWheel, { passive: false });
    this.scrollToSection("section1");
  },
  beforeDestroy() {
    window.removeEventListener("wheel", this.handleWheel);
  },
};
</script>
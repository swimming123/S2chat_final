/*UI개발: 윤수영 */
<template>
  <div>
    <div id="book">
      <div class="cover">
        <h1>The Bible</h1>
      </div>
    </div>

    <div id="controls">
      <label for="page-number">Page:</label> <input type="text" size="3" id="page-number"> of <span
        id="number-pages"></span>
    </div>

  </div>
</template>

<script>
import $ from 'jquery';
import '../../public/assets/js/turn.min.js'; // turn.js의 올바른 경로를 설정

export default {
  name: 'Turnjs',
  data() {
    return {
      numberOfPages: 1000
    };
  },
  mounted() {
    this.initTurnJs();
  },
  methods: {
    addPage(page, book) {
      // 페이지가 이미 책에 있는지 확인
      if (!book.turn('hasPage', page)) {
        // 페이지 요소 생성
        const element = $('<div />', {
          'class': 'page ' + ((page % 2 === 0) ? 'odd' : 'even'),
          'id': 'page-' + page
        }).html('<i class="loader"></i>');

        // 페이지를 책에 추가
        book.turn('addPage', element, page);

        // 데이터 로드 시뮬레이션
        setTimeout(() => {
          element.html('<div class="data">Data for page ' + page + '</div>');
        }, 1000);
      }
    },
    initTurnJs() {
      // turn.js 초기화
      $('#book').turn({
        acceleration: true,
        pages: this.numberOfPages,
        elevation: 50,
        gradients: !$.isTouch,
        when: {
          turning: (e, page, view) => {
            // 현재 책에서 필요한 페이지 범위 얻기
            const range = $(this).turn('range', page);

            // 책에 페이지 추가
            for (let p = range[0]; p <= range[1]; p++) {
              this.addPage(p, $(this));
            }
          },
          turned: (e, page) => {
            $('#page-number').val(page);
          }
        }
      });

      $('#number-pages').html(this.numberOfPages);

      $('#page-number').keydown((e) => {
        if (e.keyCode === 13) {
          $('#book').turn('page', $('#page-number').val());
        }
      });

      $(window).on('keydown', (e) => {
        if (e.target && e.target.tagName.toLowerCase() !== 'input') {
          if (e.keyCode === 37) {
            $('#book').turn('previous');
          } else if (e.keyCode === 39) {
            $('#book').turn('next');
          }
        }
      });
    }
  }
}
</script>

<style scoped>
#book {
  width: 800px;
  height: 600px;
}

#book .turn-page {
  background-color: white;
}

#book .cover {
  background: #333;
}

#book .cover h1 {
  color: white;
  text-align: center;
  font-size: 50px;
  line-height: 500px;
  margin: 0px;
}

#book .loader {
  background-image: url(../../public/images/loader.gif);
  width: 24px;
  height: 24px;
  display: block;
  position: absolute;
  top: 238px;
  left: 188px;
}

#book .data {
  text-align: center;
  font-size: 40px;
  color: #999;
  line-height: 500px;
}

#controls {
  width: 800px;
  text-align: center;
  margin: 20px 0px;
  font: 30px arial;
}

#controls input,
#controls label {
  font: 30px arial;
}

#book .odd {
  background-image: -webkit-linear-gradient(left, #FFF 95%, #ddd 100%);
  background-image: -moz-linear-gradient(left, #FFF 95%, #ddd 100%);
  background-image: -o-linear-gradient(left, #FFF 95%, #ddd 100%);
  background-image: -ms-linear-gradient(left, #FFF 95%, #ddd 100%);
}

#book .even {
  background-image: -webkit-linear-gradient(right, #FFF 95%, #ddd 100%);
  background-image: -moz-linear-gradient(right, #FFF 95%, #ddd 100%);
  background-image: -o-linear-gradient(right, #FFF 95%, #ddd 100%);
  background-image: -ms-linear-gradient(right, #FFF 95%, #ddd 100%);
}
</style>

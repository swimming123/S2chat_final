/*UI개발: 윤수영 */
<template>
  <div>
    <div id="book">
      <div class="page cover">
        <h1>The Bible</h1>
      </div>
      <div class="page">Page 1</div>
      <div class="page">Page 2</div>
      <!-- 페이지를 추가로 넣어 테스트할 수 있습니다 -->
    </div>

    <div id="controls">
      <label for="page-number">Page:</label>
      <input type="text" size="3" id="page-number"> of <span id="number-pages"></span>
    </div>
  </div>
</template>

<script>
import $ from 'jquery';
import 'turn.js'; // turn.js의 올바른 경로를 설정

export default {
  name: 'Turnjs',
  mounted() {
    this.initTurnJs();
  },
  methods: {
    initTurnJs() {
      const numberOfPages = 1000; // 페이지 수 설정

      // 페이지를 추가해주는 함수
      function addPage(page, book) {
        if (!book.turn('hasPage', page)) {
          const element = $('<div />', {
            'class': 'page ' + ((page % 2 === 0) ? 'odd' : 'even'),
            'id': 'page-' + page
          }).html('<i class="loader"></i>');

          book.turn('addPage', element, page);

          // 서버에서 데이터를 가져오는 것으로 가정
          setTimeout(() => {
            element.html('<div class="data">Data for page ' + page + '</div>');
          }, 1000);
        }
      }

      $('#book').turn({
        width: 800,
        height: 600,
        autoCenter: true,
        pages: numberOfPages,
        elevation: 50,
        gradients: !$.isTouch,
        when: {
          turning(e, page, view) {
            const range = $(this).turn('range', page);
            for (let p = range[0]; p <= range[1]; p++) {
              addPage(p, $(this));
            }
          },
          turned(e, page) {
            $('#page-number').val(page);
          }
        }
      });

      $('#number-pages').html(numberOfPages);

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

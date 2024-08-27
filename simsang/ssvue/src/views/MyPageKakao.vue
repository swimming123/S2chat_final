/*UI개발: 박성호 */
<template>
    <!-- 지도와 검색 결과를 감싸는 div -->
    <div class="map_wrap">
      <!-- 지도 표시 영역 -->
      <div id="map" style="width:100%;height:100%;position:relative;overflow:hidden;"></div>
  
      <!-- 검색 결과와 검색폼을 포함하는 div -->
      <div id="menu_wrap" class="bg_white">
        <div class="option">
          <div style="font-size: 25px;">
            <!-- 검색 폼 -->
            <form @submit.prevent="searchPlaces">
              키워드 : <input type="text" v-model="keyword" size="15" />
              <button type="submit">검색하기</button>
            </form>
          </div>
        </div>
        <hr />
        <!-- 검색 결과 목록 -->
        <ul id="placesList"></ul>
        <!-- 페이지 네비게이션 -->
        <div id="pagination"></div>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'KakaoMap',
    data() {
      return {
        markers: [], // 지도에 표시될 마커 배열
        map: null, // Kakao Map 객체
        ps: null, // Kakao Places 서비스 객체
        infowindow: null, // Kakao InfoWindow 객체
        keyword: '헬스장', // 검색 키워드의 초기값
      };
    },
    mounted() {
      // 컴포넌트가 마운트된 후 실행
      if (window.kakao && window.kakao.maps) {
        this.loadMap();
      } else {
        this.loadScript();
      }
    },
    methods: {
      // Kakao Maps API 스크립트를 로드
      loadScript() {
        const script = document.createElement('script');
        script.src = "https://dapi.kakao.com/v2/maps/sdk.js?appkey=7d96ff93232402fd079d4298c487d4b8&libraries=services&autoload=false";
        script.onload = () => {
          window.kakao.maps.load(() => {
            this.loadMap(); // 스크립트 로드 후 지도를 초기화
            // this.searchPlaces(); // 폼 제출 시 검색되므로 주석 처리
          });
        };
        document.head.appendChild(script);
      },
      // 지도 초기화
      loadMap() {
        try {
          const container = document.getElementById('map');
          const options = {
            center: new window.kakao.maps.LatLng(33.450701, 126.570667),
            level: 1 // 기본 줌 레벨 설정
          };
          this.map = new window.kakao.maps.Map(container, options);
          this.ps = new kakao.maps.services.Places();
          this.infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });
        } catch (error) {
          console.error('Failed to initialize the map:', error);
        }
      },
      // 검색 기능
      searchPlaces() {
        if (!this.ps) {
          console.error('Places service is not initialized.');
          return;
        }
  
        const keyword = this.keyword;
  
        if (!keyword.trim()) {
          alert('키워드를 입력해주세요!');
          return;
        }
  
        this.ps.keywordSearch(keyword, this.placesSearchCB.bind(this));
      },
      // 검색 결과 콜백 함수
      placesSearchCB(data, status, pagination) {
        if (status === kakao.maps.services.Status.OK) {
          this.displayPlaces(data); // 검색 결과를 표시
          this.displayPagination(pagination); // 페이지 네비게이션 표시
        } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
          alert('검색 결과가 존재하지 않습니다.');
          return;
        } else if (status === kakao.maps.services.Status.ERROR) {
          alert('검색 결과 중 오류가 발생했습니다.');
          return;
        }
      },
      // 검색 결과를 지도와 목록에 표시
      displayPlaces(places) {
        const listEl = document.getElementById('placesList');
        const menuEl = document.getElementById('menu_wrap');
        const fragment = document.createDocumentFragment();
        const bounds = new kakao.maps.LatLngBounds();
        let listStr = '';
  
        this.removeAllChildNods(listEl); // 기존 결과 삭제
        this.removeMarker(); // 기존 마커 삭제
  
        for (let i = 0; i < places.length; i++) {
          const placePosition = new kakao.maps.LatLng(places[i].y, places[i].x);
          const marker = this.addMarker(placePosition, i); // 마커 추가
          const itemEl = this.getListItem(i, places[i]); // 목록 아이템 생성
  
          bounds.extend(placePosition); // 검색 결과 범위 확장
  
          // 마커에 이벤트 리스너 추가
          kakao.maps.event.addListener(marker, 'mouseover', () => {
            this.displayInfowindow(marker, places[i].place_name);
          });
  
          kakao.maps.event.addListener(marker, 'mouseout', () => {
            this.infowindow.close();
          });
  
          itemEl.onmouseover = () => {
            this.displayInfowindow(marker, places[i].place_name);
          };
  
          itemEl.onmouseout = () => {
            this.infowindow.close();
          };
  
          fragment.appendChild(itemEl); // 목록에 아이템 추가
        }
  
        listEl.appendChild(fragment); // 결과 목록을 DOM에 추가
        menuEl.scrollTop = 0; // 스크롤을 맨 위로 이동
  
        this.map.setBounds(bounds); // 검색 결과가 포함된 영역으로 지도 범위 조정
      },
      // 목록 아이템 생성
      getListItem(index, places) {
        const el = document.createElement('li');
        let itemStr = '<span class="markerbg marker_' + (index + 1) + '"></span>' +
                      '<div class="info">' +
                      '   <h5>' + places.place_name + '</h5>';
  
        if (places.road_address_name) {
          itemStr += '    <span>' + places.road_address_name + '</span>' +
                      '   <span class="jibun gray">' + places.address_name + '</span>';
        } else {
          itemStr += '    <span>' + places.address_name + '</span>';
        }
  
        itemStr += '  <span class="tel">' + places.phone + '</span>' +
                  '</div>';
  
        el.innerHTML = itemStr;
        el.className = 'item';
  
        return el;
      },
      // 마커 추가
      addMarker(position, idx) {
        const imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png';
        const imageSize = new kakao.maps.Size(36, 37);
        const imgOptions = {
          spriteSize: new kakao.maps.Size(36, 691),
          spriteOrigin: new kakao.maps.Point(0, (idx * 46) + 10),
          offset: new kakao.maps.Point(13, 37)
        };
        const markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions);
        const marker = new kakao.maps.Marker({
          position: position,
          image: markerImage
        });
  
        marker.setMap(this.map); // 지도에 마커 추가
        this.markers.push(marker); // 마커 배열에 추가
  
        return marker;
      },
      // 모든 마커 제거
      removeMarker() {
        for (let i = 0; i < this.markers.length; i++) {
          this.markers[i].setMap(null);
        }
        this.markers = []; // 마커 배열 비우기
      },
      // 페이지 네비게이션 표시
      displayPagination(pagination) {
        const paginationEl = document.getElementById('pagination');
        const fragment = document.createDocumentFragment();
        let i;
  
        while (paginationEl.hasChildNodes()) {
          paginationEl.removeChild(paginationEl.lastChild);
        }
  
        for (i = 1; i <= pagination.last; i++) {
          const el = document.createElement('a');
          el.href = "#";
          el.innerHTML = i;
  
          if (i === pagination.current) {
            el.className = 'on';
          } else {
            el.onclick = () => {
              pagination.gotoPage(i);
            };
          }
  
          fragment.appendChild(el);
        }
        paginationEl.appendChild(fragment); // 네비게이션 추가
      },
      // 인포윈도우 표시
      displayInfowindow(marker, title) {
        const content = '<div style="padding:5px;z-index:1;">' + title + '</div>';
  
        this.infowindow.setContent(content);
        this.infowindow.open(this.map, marker); // 마커 위에 인포윈도우 열기
      },
      // 모든 자식 노드 제거
      removeAllChildNods(el) {
        while (el.hasChildNodes()) {
          el.removeChild(el.lastChild);
        }
      }
    }
  };
  </script>

<style>
.map_wrap {
  width: 100%;
  height: 100%;
}

.bg_white {
  background-color: #fff;
  padding: 10px;
  border-radius: 5px;
}

.option {
  margin-bottom: 10px;
}

#placesList {
  list-style: none;
  padding: 0;
}

#placesList .item {
  margin-bottom: 10px;
}

#pagination {
  text-align: center;
}

#pagination a {
  margin: 0 5px;
  text-decoration: none;
}

#pagination a.on {
  font-weight: bold;
}

#menu_wrap {
  position: absolute;
  top: 0;
  left: 0;
  bottom: 0;
  width: 500px;
  margin: 10px 0 5% 10px;
  padding: 5px;
  overflow-y: auto;
  background: rgba(255, 255, 255, 0.7);
  z-index: 1;
  font-size: 12px;
  border-radius: 10px;
}
</style>

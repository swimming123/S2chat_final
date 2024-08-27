/*UI개발:이승희*/
<template>
    <section id="special" class="special sections">
      <div class="container">
        <div class="row">
          <!-- 섹션 헤더 -->
          <div class="head_title text-center wow slideInLeft" data-wow-duration="1.5s">
            <h2 style="color: #ff7f00;">한번에 담는 건강주스</h2>
             
          </div>
          <!--◐◐◐◐◐ Fruits 상품 리스트 시작-->
          <div class="col-md-12">
            <div class="titie-section wow fadeInDown animated">
              <h1></h1>
            </div>
          </div>
        </div>
        <div class="row">
          <!-- 필터 메뉴 -->
          <div class="col-md-12">
            <div class="filter-menu">
              <!-- 과일 리스트를 카테고리별로 필터링하기 위한 버튼 그룹 생성: 열대과일, 멜론류 등으로 나누었음.-->
              <ul class="button-group sort-button-group">
                <li class="button" :class="{ active: activeCategory === 'all' }" @click="setCategory('all')">피로회복<span>4</span></li>
                <li class="button" :class="{ active: activeCategory === 'cat-1' }" @click="setCategory('cat-1')">장 튼튼<span>2</span></li>
                <li class="button" :class="{ active: activeCategory === 'cat-2' }" @click="setCategory('cat-2')">피부미용<span>2</span></li>
                <li class="button" :class="{ active: activeCategory === 'cat-3' }" @click="setCategory('cat-3')">엽록소 충전<span>4</span></li>
              </ul>
            </div>
          </div>
        </div>
        <!-- 상품 리스트 -->
        <div class="row featured isotope">
          <div v-for="(item, index) in filteredItems" :key="item.id" class="col-md-3 col-sm-6 col-xs-12 featured-items isotope-item" :class="item.category">
            <div class="product-item" @mouseover="hover(index)" @mouseleave="leave(index)">
          <div class="image-container">
            <a @click="productDetail">
            <img :src="item.imgSrc" class="img-responsive main-image" width="255" height="322" alt="">
            </a>
          </div>
          <div class="hover-images" v-if="item.isHovered">
            <img v-for="(img, i) in item.additionalImages" :key="i" :src="img" class="img-responsive">
          </div>
      <div class="sell-meta">
        <span class="rank">{{ index + 1 }}위</span>
        <span class="views">{{ item.views }} 조회수</span>
      </div>
      <div class="product-title">
        <a @click="productDetail">
          <h3>{{ item.name }}</h3>
        </a>
      </div>
        <div class="cart-icon" @click="addToCart">
          <i class="fa fa-shopping-cart"></i>
        </div>
    </div>
          </div>
        </div>
        <!--끝 ◐◐◐◐◐ Fruits 상품 리스트-->
      </div>
    </section>
  </template>
  <script>
  export default {
    data() {
      return {
        list: [
          { id: 1, name: '베리베리레몬디톡스',  category: 'all', imgSrc: 'images/juice/juice.png', additionalImages: ['images/juice/strawberry.png', 'images/juice/lemon.png', 'images/juice/blueberry.png', 'images/juice/mint.png'], isHovered: false, views: 124 },
          { id: 2, name: '그리너리엽록소', category: 'cat-1', imgSrc: 'images/juice/green.png', additionalImages: ['images/juice/strawberry.png', 'images/juice/lemon.png', 'images/juice/blueberry.png', 'images/juice/mint.png'], isHovered: false, views: 108 },
          { id: 3, name: '석류랑오렌지라니', category: 'cat-1', imgSrc: 'images/juice/orange.png', additionalImages: ['images/juice/strawberry.png', 'images/juice/lemon.png', 'images/juice/blueberry.png', 'images/juice/mint.png'], isHovered: false, views: 96 },
          { id: 4, name: '미녀는석류를좋아해', category: 'cat-3', imgSrc: 'images/juice/pink.png', additionalImages: ['images/juice/strawberry.png', 'images/juice/lemon.png', 'images/juice/blueberry.png', 'images/juice/mint.png'], isHovered: false, views: 78 },
        ], // 전체 리스트
        currentPage: 1, // 현재 페이지
        totalPages: 0, // 총 페이지 수
        activeCategory: 'all', // 활성화된 카테고리
      }
    },
    computed: {
      // 활성화된 카테고리에 따라 필터링된 항목을 반환
      filteredItems() {
        if (this.activeCategory === 'all') {
          return this.list;
        } else {
          return this.list.filter(item => item.category === this.activeCategory);
        }
      }
    },
    methods: {
      // 장바구니로 이동
      addToCart() {
        this.$router.push('/cart');
      },
      // 상품상세로
      productDetail() {
        this.$router.push('/productDetail');
      },
      // 카테고리 설정
      setCategory(category) {
        this.activeCategory = category;
      },
      // hover 상태 관리
      hover(index) {
        this.list[index].isHovered = true;
      },
      leave(index) {
        this.list[index].isHovered = false;
      }
    }
  }
  </script>

 <style scoped>
 .filter-menu .button, h2{
   font-family: 'NotoSansKR';
 }
 p {
     font-family: 'JejuGothic';
 }

 .head_title {
     padding-top: 50px; 
     padding-bottom: 10px; 
 }
 
 .filter-menu {
     margin-bottom: 10px; 
 }
 
 .features {
     padding-top: 80px;
     padding-bottom: 80px;
 }
 
 .label.label-default {
     font-size: 18px;
     font-weight: bold;
 }
 
 .form-control::placeholder {
     color: #666666;
     font-family: '굴림', Gulim, 'Nanum Gothic', sans-serif;
 }
 
 .product-item {
     border: 2px solid #ece9e6;
     border-radius: 5px; 
     margin-bottom: 20px;
     position: relative;
 }
 
 .product-item img {
     border-bottom: 2px solid #ece9e6; 
 }
 
 .product-meta a {
     color: #ece9e6; 
 }
 
 .image-container {
     position: relative;
     height: auto; 
 }
 
 .hover-images {
     position: absolute;
     top: 0;
     left: 0;
     width: 100%;
     height: calc(100% - 37px); 
     display: grid;
     grid-template-columns: 1fr 1fr;
     grid-template-rows: 1fr 1fr;
     gap: 2px;
     background: rgba(255, 255, 255, 0.8);
     z-index: 10;
 }
 
 .hover-images img {
     width: 100%;
     height: 100%;
     object-fit: cover;
 }
 
 .cart-icon {
     position: absolute;
     bottom: 10px;
     right: 10px;
     cursor: pointer;
 }
 
 .sell-meta {
     position: absolute;
     top: 10px;
     left: 10px;
 }
 
 .sell-meta .rank {
     font-size: 18px;
     font-weight: bold;
     color: #ff7f00;
     
 }
 
 .sell-meta .views {
     font-size: 14px;
     color: #666666;
     margin-left: 175px;
 }
 
 </style>
 
 
 
  
  
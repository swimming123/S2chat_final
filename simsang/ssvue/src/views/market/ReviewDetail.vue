/*UI개발:송지미, 윤수영*/
<template>
    <section id="features_reviewlist" class="features_reviewlist sections">
        <div class="container">
            <div class="row">
                <!-- 페이지 이름 -->
                <div class="head_title1 text-center wow slideInLeft" data-wow-duration="1.5s">
                    <h2 style="color: #808080;">리뷰 상세보기</h2>
                </div>
                <div class="box">
                    <div class="review-info">
                        <img src="images/camera-icon.png" alt="Upload Icon">
                        <div>
                            포토/동영상 첨부 시 포인트 450원
                        </div>
                    </div>
                    <!-- 리뷰 상품 정보 표시 -->
                    <div class="review-product-info">
                        <img :src="selectedImageUrl" alt="Product Image">
                        <div>
                            <span id="review_num"></span>
                            <!-- <span class="store" id="order_num">{{ reviewDetail.order_num }}</span>
                            <span class="store" id="product_num">{{ reviewDetail.product_num }}</span> -->
                            <span class="product-title1" id="review_category">{{ reviewDetail.reviewcategory }}</span>
                        </div>
                    </div>
                    <!-- 별점 평가 -->
                    <div class="rating" id="review_star">
                        <div><strong style="color: #000;">상품은 만족하셨나요?</strong></div>
                        <div class="stars">
                            <i v-for="n in 5" :key="n" class="fas fa-star" :class="{ selected: n <= reviewDetail.reviewstars }" @click="setRating(n)" ></i>
                        </div>
                        <div>선택하세요.</div>
                    </div>
                    <!-- 리뷰 내용 입력 -->
                    <div class="comment">
                        <div style="text-align: center;"><strong style="color: #000;">어떤 점이 좋았나요?</strong></div>
                        <div class="textarea-wrapper">
                            <textarea v-model="reviewDetail.reviewcontent" placeholder="최소 10자 이상 입력해주세요."></textarea>
                            <div id="review_content" class="char-counter">{{ reviewDetail.reviewcontent.length }}/5,000</div>
                        </div>
                    </div>
                    <!-- 이미지 업로드 -->
                    <div class="file-upload" id="review_image">
                        <!-- 이미지가 선택되면 선택된 이미지 보여주기 -->
                        <div v-if="reviewDetail.reviewimagename" class="image-container">
                            <img :src="selectedImageUrl" alt="review_image">
                        </div>
                        <label for="file-upload">
                            <i class="fas fa-camera"></i> 사진/동영상 첨부하기
                            <input type="file" id="file-upload" accept="image/*, video/*" @change="updateImage">
                        </label>
                    </div>

                    <!-- 경고 메시지 -->
                    <div class="warning">
                        상품과 무관한 사진/동영상을 첨부한 리뷰는 통보없이 삭제 및 적립 혜택이 회수됩니다.
                    </div>
                    <div class="button-container">
                        <button class="cancel" @click="$router.push('/reviewlist')">취소</button>
                        <button class="delete" @click="deleteReview">삭제</button>
                        <button class="submit" @click="updateReview">수정</button>
                    </div>
                </div>
            </div>
        </div>
    </section>
</template>

<script>
import axios from 'axios';

export default {
    name: 'ReviewDetail',
    data() {
        return {
            reviewDetail: {
                review_num: "",
                reviewcategory: "",
                product_num: "",
                order_num: "",
                reviewstars: 0,
                reviewcontent: "",
                reviewimage: "",
            },
            selectedImageUrl: "/images/free-icon-photos.png",
        };
    },
    created() {
        this.fetchReviewDetail();
    },
    methods: {
        fetchReviewDetail() {
            const num = parseInt(this.$route.query.review_num);
            axios
                .get(`${process.env.VUE_APP_BACK_END_URL}/reviews/reviewDetail/${num}`)
                .then((response) => {
                    this.reviewDetail = response.data;
                    this.selectedImageUrl = this.reviewDetail.reviewimagename || "/images/free-icon-photos.png";
                })
                .catch((error) => {
                    console.error("Error fetching review detail: ", error);
                });
        },
        updateImage(event) {
            const file = event.target.files[0];
            const formData = new FormData();
            formData.append('file', file);

            axios.post(`${process.env.VUE_APP_BACK_END_URL}/reviews/uploadImage`, formData)
                .then((response) => {
                    
                    this.reviewDetail.reviewimagename = response.data; // assuming response.data is the image URL
                    this.selectedImageUrl = response.data; // update selectedImageUrl with the new image URL
                })
                .catch((error) => {
                    console.error('Error uploading image:', error);
                });
        },
        setRating(value) {
            this.reviewDetail.reviewstars = value;
        },
        deleteReview() {
            const num = parseInt(this.$route.query.review_num);
            axios
                .delete(`${process.env.VUE_APP_BACK_END_URL}/reviews/reviewDelete/${num}`)
                .then(() => {
                    this.$router.push("/reviewlist");
                })
                .catch((error) => {
                    console.error("Error deleting review: ", error);
                });
        },
        updateReview() {
            const num = parseInt(this.$route.query.review_num);
            this.reviewDetail.reviewimagename=this.selectedImageUrl;
            axios
                .post(`${process.env.VUE_APP_BACK_END_URL}/reviews/reviewUpdate/${num}`, this.reviewDetail)
                .then((response) => {
                    this.$router.push("/reviewlist");
                })
                .catch((error) => {
                    console.error("Error updating review: ", error);
                });
        },
    },
};
</script>


<style scoped>
@import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css');
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap');

body, .head_title1, button, p, div, span, textarea, label {
    font-family: 'NotoSansKR', sans-serif;
}

.head_title1 {
    padding-bottom: 10px;
}

.head_title1 + .box {
    margin-top: 10px;
}

h2 {
	font-size: 40px;
	line-height: 38px;
	font-weight: 600;
	font-family: 'NotoSansKR';
	margin: 0;
}

.box {
    padding: 20px;
    margin: 20px 0;
    background: #fcfafa;
    border-radius: 7px;
    box-shadow: 5px 5px 10px rgba(221, 119, 108, 0.5);
}

textarea::placeholder {
    color: #666666;
}
.container {
    max-width: 500px;
    margin: 0 auto;
    padding: 20px;
}
.header, .review-info, .review-product-info, .rating, .comment, .file-upload, .warning, .button-container {
    margin-bottom: 20px;
}
.header {
    text-align: center;
    font-size: 1.5em;
    font-weight: bold;
}
.review-info {
    background-color: #f0f0f0;
    padding: 10px;
    border-radius: 5px;
    display: flex;
    align-items: center;
}
.review-info img {
    max-width: 50px;
    max-height: 50px;
    margin-right: 10px;
}
.review-info div {
    flex-grow: 1;
}
.review-product-info {
    display: flex;
    align-items: center;
}
.review-product-info img {
    width: 50px;
    height: 50px;
    margin-right: 10px;
}
.store {
    display: block;
    color: #999;
}
.product-title1 {
    display: block;
    color: #000;
}
.image-container {
    text-align: center;
    margin: 10px 0;
}
.image-container img {
    max-width: 100%;
    height: auto;
    border-radius: 10px;
}
.rating {
    text-align: center;
}
.rating span {
    font-size: 1.2em;
    margin-right: 5px;
}
.rating .stars {
    display: flex;
    justify-content: center;
    margin: 10px 0;
}
.stars i {
    font-size: 2em;
    cursor: pointer;
    color: #ccc;
}
.stars i.selected {
    color: rgb(233, 101, 53);
}
.comment {
    position: relative;
}
.textarea-wrapper {
    position: relative;
}
.comment textarea {
    width: 100%;
    height: 100px;
    padding: 10px;
    border-radius: 5px;
    border: 1px solid #ddd;
    box-sizing: border-box;
}
.char-counter {
    position: absolute;
    bottom: 10px;
    right: 10px;
    font-size: 0.9em;
    color: #666666;
}
.file-upload {
    text-align: center;
}
.file-upload label {
    display: inline-block;
    padding: 10px 20px;
    background-color: #f0f0f0;
    border: 1px solid #ddd;
    border-radius: 5px;
    cursor: pointer;
}
.file-upload input[type="file"] {
    display: none;
}
.warning {
    text-align: center;
    font-size: 0.9em;
    color: #d9534f;
}
.button-container {
    display: flex;
    justify-content: center;
    gap: 10px;
}
.button-container button {
    padding: 10px 20px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}
.button-container .cancel {
    background-color: #dddbdb;
}
.button-container .submit {
    background-color: rgb(233, 101, 53);
    color: white;  
}

.button-container .delete {
    background-color: rgb(101, 199, 118);
    color: white;  
}
</style>

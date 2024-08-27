/*UI개발: 윤수영 */
<template>
    <div>
        <div class="main">
            <div class="container-xxl py-5">
                <div class="container">
                    <div class="position-relative overflow-hidden">
                    </div>
                    <div class="section-title text-center">
                        <h1 class="display-5 mb-5">문의 상세보기</h1>
                    </div>
                    <div class="d-flex justify-content-center">
                        <div class="p-lg-5 pe-lg-0" style="width:800px;">
                            <div class="section-title text-start">
                                <h2 class="display-5 mb-4">문의 등록시 유의사항</h2>
                                <p class="mb-4 pb-2">
                                    상업적인 사진, 욕설, 음란성 글을 등록할 수 없습니다.
                                </p>
                            </div>
                            <form>
                                <div class="row g-3">
                                    <div class="col-12 col-sm-6" style="display: none;">
                                        <input disabled type="text" class="form-control border-0" id="qnawriter"
                                            v-model="formdata.num" />
                                    </div>
                                    <div class="col-12 col-sm-6" style="display: none;">
                                        <input disabled type="text" class="form-control border-0" id="qnawriter"
                                            v-model="formdata.membernum" />
                                    </div>
                                    <div class="col-12 col-sm-6">
                                        <input disabled type="text" class="form-control border-0" id="qnawriter"
                                            v-model="formdata.name" />
                                    </div>
                                    <div class="col-12 col-sm-6">
                                        <input disabled type="date" class="form-control border-0" id="qnawdate"
                                            v-model="formdata.qnaquestiondate" />
                                    </div>
                                    <div class="col-12 col-sm-6">
                                        <select class="form-select border-0" id="qnacategory" :value="formdata.category"
                                            disabled>
                                            <option value="심리진단" :selected="formdata.category === '심리진단'">심리진단</option>
                                            <option value="서비스문의" :selected="formdata.category === '서비스문의'">서비스문의
                                            </option>
                                            <option value="회원관련" :selected="formdata.category === '회원관련'">회원관련</option>
                                            <option value="상품문의" :selected="formdata.category === '상품문의'">상품문의</option>
                                            <option value="기타문의" :selected="formdata.category === '기타문의'">기타문의</option>
                                        </select>
                                    </div>
                                    <div class="col-12">
                                        <input disabled type="text" class="form-control border-0" id="qnatitle"
                                            v-model="formdata.title" />
                                    </div>
                                    <div class="col-12">
                                        <textarea disabled class="form-control border-0" style="height: 200px"
                                            id="qnacontent" v-model="formdata.content"></textarea>
                                    </div>
                                    <div class="col-12">
                                        <img :src="image" class="img-thumbnail" alt="첨부파일">
                                    </div>

                                    <div class="col-12 col-sm-8">
                                        <button class="btn w-100 py-3 simulationIntro3" type="button"
                                            @click="goqnaup(formdata.num)">
                                            수정하기
                                        </button>
                                    </div>
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
import axios from 'axios';

export default {
    data() {
        return {
            formdata: {
                num: '',
                membernum: localStorage.getItem("membernum"),
                name: '',
                qnaquestiondate: '',
                category: '',
                title: '',
                content: '',
                imgname: '',
                file: ''
            },
            image: ''
        };
    },
    created() {
        this.fetchQna();
    },
    methods: {
        fetchQna() {
            const num = this.$route.params.num;
            axios.get(`${process.env.VUE_APP_BACK_END_URL}/qna/qnadetail/${num}`)
                .then(response => {
                    console.log("API Response:", response.data); // API 응답 확인
                    this.formdata = response.data;
                    if (this.formdata.imgname && this.formdata.imgname !== 'default.png') {
                        this.image = `${process.env.VUE_APP_BACK_END_URL}/uploads/${this.formdata.imgname}`;
                        console.log("Image URL: " + this.image);
                    }
                })
                .catch(error => {
                    console.log(error);
                });
        },
        goqnainfo() {
            this.$router.push({ name: 'Qnalist' });
        },
        handleFileUpload(event) {
            const file = event.target.files[0];
            if (file) {
                this.formdata.file = URL.createObjectURL(file);
            }
        },
        goqnaup(num) {
            this.$router.push({ path: `/Qnaup/${num}` });
        },
    },
};
</script>

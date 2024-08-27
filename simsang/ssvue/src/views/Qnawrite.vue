/*UI개발: 윤수영 */
<template>
    <div>
        <div class="main">
            <div class="container-xxl py-5">
                <div class="container">
                    <div class="position-relative overflow-hidden"></div>
                    <div class="section-title text-center">
                        <h1 class="display-5 mb-5">문의하기</h1>
                    </div>
                    <div class="d-flex justify-content-center">
                        <div class="p-lg-5 pe-lg-0" style="width:800px;">
                            <div class="section-title text-start">
                                <h2 class="mb-4">문의 등록시 유의사항</h2>
                                <p class="mb-4 pb-2">
                                    (⊙_(⊙_⊙)_⊙) 클린봇이 상업적인 사진, 욕설, 음란성 문구를 AI 기술로 감지하고 있습니다.
                                </p>
                            </div>
                            <form @submit.prevent="add">
                                <div class="row g-3">
                                    <div class="col-12 col-sm-6" style="display: none;">
                                        <input type="text" class="form-control border-0" v-model="qna.num"
                                            placeholder="qna 번호" readonly />
                                    </div>
                                    <div class="col-12 col-sm-6" style="display: none;">
                                        <input type="text" class="form-control border-0" v-model="qna.membernum"
                                            placeholder="회원 번호" readonly />
                                    </div>
                                    <div class="col-12 col-sm-6">
                                        <input type="text" class="form-control border-0" v-model="qna.name"
                                            placeholder="회원 아이디" readonly />
                                    </div>
                                    <div class="col-12 col-sm-6">
                                        <input type="date" class="form-control border-0" v-model="qna.qnaquestiondate"
                                            placeholder="작성일" readonly />
                                    </div>
                                    <div class="col-12 col-sm-6">
                                        <select class="form-select border-0" v-model="qna.category">
                                            <option value="" selected disabled hidden>카테고리를 선택해주세요</option>
                                            <option value="심리진단">심리진단</option>
                                            <option value="서비스문의">서비스문의</option>
                                            <option value="회원관련">회원관련</option>
                                            <option value="상품문의">상품문의</option>
                                            <option value="기타문의">기타문의</option>
                                        </select>
                                    </div>
                                    <div class="col-12">
                                        <input type="text" class="form-control border-0" v-model="qna.title"
                                            placeholder="제목을 입력해주세요." />
                                    </div>
                                    <div class="col-12">
                                        <textarea class="form-control border-0" style="height: 200px"
                                            v-model="qna.content"></textarea>
                                    </div>


                                    <div class="col-12">
                                        <label for="qnafile" class="form-label">첨부파일</label>
                                        <input @change="onFileChange" class="form-control" type="file" id="qnafile"
                                            ref="image">
                                        <div v-if="imagePreview" class="mt-3" style="object-fit: cover;">
                                            <img :src="imagePreview" alt="img" class="img-thumbnail">
                                        </div>
                                    </div>
                                    <div class="p-4 text-center row">
                                        <button class="btn w-25 py-3 simulationIntro3 col" type="submit"
                                            :disabled="!isImgDataValid">
                                            등록하기
                                        </button>
                                        <button class="btn w-25 py-3 simulationIntro3 col" @click.prevent="goqnainfo">
                                            목록으로
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
            qna: {
                num: '',
                membernum: localStorage.getItem("membernum"),
                name: localStorage.getItem("id"),
                qnaquestiondate: '',
                category: '',
                title: '',
                content: '',
                imgname: '',
                status: '답변대기중',
            },
            image: null,
            imagePreview: null,
            imgdata: "",
            file: "",

        };
    },
    computed: {
        isImgDataValid() {
            try {
                const imgDataParsed = JSON.parse(this.imgdata);
                return imgDataParsed["spam"] === 0;
            } catch (e) {
                return false;
            }
        }
    },
    methods: {
        getTodayDate() {
            const today = new Date();
            const year = today.getFullYear();
            const month = String(today.getMonth() + 1).padStart(2, '0');
            const day = String(today.getDate()).padStart(2, '0');
            return `${year}-${month}-${day}`;
        },
        onFileChange(e) {
            const formData = new FormData();
            this.file = e.target.files[0];
            formData.append("file1", this.file);
            axios.post(`http://192.168.0.92:9000/thisisspam/insert_img`, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            })
                .then((response) => {
                    this.image = this.file;
                    if (this.file) {
                        this.imagePreview = URL.createObjectURL(this.file);
                    }
                    console.log(response.data);
                    this.imgdata = JSON.stringify(response.data, null, 2);
                    this.imgname = e.target.files[0];
                })
                .catch((error) => {
                    console.error(error);
                    alert('이미지 처리 중 오류가 발생했습니다.');
                });
        },
        add() {
            if (!this.qna.title) {
                alert('제목을 입력해주세요.');
                return;
            }
            if (!this.qna.content) {
                alert('내용을 입력해주세요.');
                return;
            }
            if (!this.qna.category) {
                alert('카테고리를 선택해주세요.');
                return;
            }

            const formData = new FormData();
            formData.append('qna', JSON.stringify(this.qna));
            if (this.image) {
                formData.append('file', this.image);
            }

            axios.post(`${process.env.VUE_APP_BACK_END_URL}/qna/qnaadd`, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            })
                .then(response => {
                    this.qna = response.data;
                    this.$router.push(`/Qnalist`);
                })
                .catch(err => {
                    console.log("err" + err);
                });
        },
        goqnainfo() {
            this.$router.push({ name: 'Qnalist' });
        }
    },
    mounted() {
        this.qna.qnaquestiondate = this.getTodayDate();
    }
};
</script>

/*UI개발: 윤수영 */
<template>
    <div>
        <div class="main">
            <div class="container-xxl py-5">
                <div class="container">
                    <div class="position-relative overflow-hidden">
                    </div>
                    <div class="section-title text-center">
                        <h1 class="display-5 mb-5">문의 수정 하기</h1>
                    </div>
                    <div class="d-flex justify-content-center">
                        <div class="p-lg-5 pe-lg-0" style="width:800px;">
                            <div class="section-title text-start">
                                <h2 class="display-5 mb-4">문의 등록시 유의사항</h2>
                                <p class="mb-4 pb-2">
                                    (⊙_(⊙_⊙)_⊙) 클린봇이 상업적인 사진, 욕설, 음란성 문구를 AI 기술로 감지하고 있습니다.
                                </p>
                            </div>
                            <form>
                                <div class="row g-3">
                                    <div class="col-12 col-sm-6" style="display: none;">
                                        <input type="text" class="form-control border-0" id="qnanum" 
                                        v-model="formdata.num"/>
                                    </div>
                                    <div class="col-12 col-sm-6">
                                        <input type="text" class="form-control border-0" id="qnawriter" 
                                        v-model="formdata.name"/>
                                    </div>
                                    <div class="col-12 col-sm-6">
                                        <input readonly type="date" class="form-control border-0" id="qnawdate" 
                                        v-model="formdata.qnaquestiondate"/>
                                    </div>
                                    <div class="col-12 col-sm-6">
                                        <select class="form-select border-0" id="qnacategory" 
                                        v-model="formdata.category">
                                            <option selected>카테고리</option>
                                            <option value="심리진단">심리진단</option>
                                            <option value="서비스문의">서비스문의</option>
                                            <option value="회원관련">회원관련</option>
                                            <option value="상품문의">상품문의</option>
                                            <option value="기타문의">기타문의</option>
                                        </select>
                                    </div>
                                    <div class="col-12">
                                        <input type="text" class="form-control border-0" id="qnatitle" 
                                        v-model="formdata.title"/>
                                    </div>
                                    <div class="col-12">
                                        <textarea class="form-control border-0" style="height: 200px" id="qnacontent"
                                            v-model="formdata.content"></textarea>
                                    </div>
                                    <div class="col-12">
                                        <label for="qnafile" class="form-label">첨부파일</label>
                                        <input @change="onFileChange" class="form-control" type="file" id="qnafile"
                                            ref="image">
                                        <div v-if="formdata.imgname" class="mt-3" style="object-fit: cover;">
                                            <img :src="file" alt="img" class="img-thumbnail">
                                        </div>
                                    </div>
                                    <div class="p-4 text-center row">
                                        <button class="btn w-35 py-3 simulationIntro3 col"
                                         @click.prevent="update">수정하기</button>
                                        <button class="btn w-35 py-3 simulationIntro3 col"
                                         @click="goqnalist">목록으로</button>
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
                name: '',
                qnaquestiondate: '',
                category: '',
                title: '',
                content: '',
                imgname:'',
                file: ''
            },
            image: null,
            imagePreview: null,
            imgdata: "",
            file:"",
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
    created() {
        this.fetchQna();
    },
    methods: {
        fetchQna() {
            const num = this.$route.params.num;
            if (!num) {
                console.error('No num parameter');
                return;
            }
            axios.get(`${process.env.VUE_APP_BACK_END_URL}/qna/qnadetail/${num}`)
            .then((response) => {
                this.formdata = response.data;
                console.log("이미지 기본 보이기 이전 콘솔")
                if(this.formdata.imgname){
                    this.file = `${process.env.VUE_APP_BACK_END_URL}/uploads/${this.formdata.imgname}`;
                    console.log("this.file:",this.file)
                }
                console.log("qnadetail", this.formdata);
            })
            .catch(error => {
                console.log(error);
            });
        },
        onFileChange(e) {
            const formData = new FormData();
            this.file = e.target.files[0];
            this.image = e.target.files[0];
            this.imgname = e.target.files[0];
            formData.append("file1", this.file);
            formData.append("category", this.imageName);
            axios.post(`http://192.168.0.92:9000/thisisspam/insert_img`, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            })
            .then((response) => {
                console.log("이미지 확인 완료");
                this.file = URL.createObjectURL(e.target.files[0]);
                console.log(response.data);
                this.imgdata = JSON.stringify(response.data, null, 2);
            })
            .catch((error) => {
                console.error(error);
                alert('이미지 처리 중 오류가 발생했습니다.');
            });
        },
        update() {
            const formData = new FormData();
            formData.append('qna', JSON.stringify(this.formdata));
            if (this.image) {
                formData.append('file', this.image);
            }

            axios.post(`${process.env.VUE_APP_BACK_END_URL}/qna/qnaupdate`, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            })
            .then(() => {
                alert('Q&A 상태 및 답변이 성공적으로 수정되었습니다.');
                this.$router.push('/Qnalist');
            })
            .catch(error => {
                console.log(error);
                console.log("이미지",this.image);
                alert('Q&A 상태 및 답변 수정 중 오류가 발생했습니다.');
            });
        },


        goqnalist() {
            this.$router.push({ path: `/Qnalist/` });
        },
    },
};
</script>

/*UI개발: 윤수영 */
<template>
    <div>
        <div class="main">
            <div class="container-xxl py-5">
                <div class="container">
                    <div class="section-title text-center">
                        <h1 class="display-5 mb-5">나의 문의</h1>
                    </div>

                    <div class="d-flex justify-content-center">
                        <div class="row g-4 portfolio-container" style="width:60%;">
                            <div class="rounded overflow-hidden">
                                <table class="table">
                                    <thead>
                                        <tr>
                                            <th scope="col" class="qnath">문의순서</th>
                                            <th scope="col" class="qnath">카테고리</th>
                                            <th scope="col" class="qnatitle">제목</th>
                                            <th scope="col" class="qnath">작성날짜</th>
                                            <th scope="col" class="qnath">답변여부</th>
                                            <th scope="col" class="qnath">관리</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr v-for="(formdata, index) in formDatas" :key="formdata.num">
                                            <th scope="row"><router-link :to="`/Qnadetail/${formdata.num}`">{{ index + 1 }}</router-link></th>
                                            <td>{{ formdata.category }}</td>
                                            <td>{{ formdata.title }}</td>
                                            <td>{{ formdata.qnaquestiondate }}</td>
                                            <td>{{ formdata.status }}</td>
                                            <td>
                                                <button class="btn w-10 py-0 simulationIntro3" @click="godelete(formdata.num)">삭제</button>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
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
            formDatas: [],
            membernum: localStorage.getItem("membernum")
        };
    },
    created(){
        this.fetchqna();
    },
    methods:{
        fetchqna(){
            axios.get(`${process.env.VUE_APP_BACK_END_URL}/qna/qnalist/${this.membernum}`)
            .then(response => {
                this.formDatas = response.data;
                
            })
            .catch(error => {
                console.log(response.data);
                console.log(error);
            });
        },
        godelete(num){
            if (confirm("정말 삭제하시겠습니까?")) {
                axios.delete(`${process.env.VUE_APP_BACK_END_URL}/qna/qnadelete/${num}`)
                .then(() => {
                    this.fetchqna();
                })
                .catch((err) => {
                    console.error(err);
                });
            }
        }
    }
};
</script>

<style scoped>
.qnath {
    width: 100px;
    text-align: center;
    white-space: nowrap;
}
.qnatitle {
    width: 200px;
    text-align: left;
    white-space: nowrap;
}
button.simulationIntro3 {
    min-width: 60px;
    white-space: nowrap;
}
</style>

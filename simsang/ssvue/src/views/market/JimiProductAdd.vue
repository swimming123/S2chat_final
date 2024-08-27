/*UI개발:송지미*/
<template>
    <div class="container">
        <h1>상품등록</h1>
        <!-- 상품 등록 테이블 -->
        <table class="table">
            <thead>
                <tr>
                    <th>항목</th>
                    <th>내용</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>카테고리</td>
                    <td><input type="text" v-model="products.product_category"></td>
                </tr>
                <tr>
                    <td>이미지</td>
                    <td>
                        <div class="image-upload">
                            <img :src="imageSrc" class="product-image" alt="product image">
                            <input type="file" @change="handleFileChange" class="custom-file-input">
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>상품명</td>
                    <td><input type="text" v-model="products.product_name"></td>
                </tr>
                <tr>
                    <td>가격</td>
                    <td><input type="text" v-model="products.product_price"></td>
                </tr>
                <tr>
                    <td>재고</td>
                    <td><input type="text" v-model="products.product_stock"></td>
                </tr>
                <tr>
                    <td>제조사</td>
                    <td><input type="text" v-model="products.product_manufacturer"></td>
                </tr>
                <tr>
                    <td>등록자</td>
                    <td><input type="text" v-model="products.product_registrant"></td>
                </tr>
            </tbody>
            <tfoot>
                <tr>
                    <td colspan="2">
                        <!-- 등록하기 버튼과 목록으로 링크 -->
                        <button class="btn btn-salmon" @click.prevent="add">등록하기</button>
                        <router-link to="JimiProductList" class="btn btn-green">목록으로</router-link>
                    </td>
                </tr>
            </tfoot>
        </table>
    </div>
</template>

<script>
import axios from 'axios'; // Axios를 사용하여 HTTP 요청을 처리
const defaultImage = '/images/product/maketadd_default.png'; // 기본 이미지 경로

export default {
    data() {
        return {
            imageSrc: defaultImage,
            // 상품 정보를 저장하는 객체
            products: {
                product_category: '',
                product_image: '',
                product_name: '',
                product_price: '',
                product_stock: '',
                product_manufacturer: '',
                product_registrant: ''
            },
            // 이미지 미리보기를 위한 기본 이미지 경로
            imageSrc: defaultImage,
            // 선택된 파일을 저장하는 변수
            selectedFiles: null,
            // 읽기 전용 상태를 관리하는 변수
            isReadonly: false,
        };
    },
    methods: {
        // 파일 선택 시 호출되는 메서드
        handleFileChange(event) {
            this.selectedFiles = event.target.files || [];
            if (this.selectedFiles.length > 0) {
                const file = this.selectedFiles[0];
                this.imageSrc = URL.createObjectURL(file);
                this.products.product_image = file;
            } else {
                this.imageSrc = defaultImage;
            }
        },
        // 상품 등록 메서드
        add() {
            const formData = new FormData();
            formData.append("file", this.products.product_image);
            formData.append("product_category", this.products.product_category);
            formData.append("product_name", this.products.product_name);
            formData.append("product_price", this.products.product_price);
            formData.append("product_stock", this.products.product_stock);
            formData.append("product_manufacturer", this.products.product_manufacturer);
            formData.append("product_registrant", this.products.product_registrant);

            console.log("FormData 내용:", ...formData.entries()); // formData 내용을 콘솔에 출력하여 확인

            axios.post(`${process.env.VUE_APP_BACK_END_URL}/product/proAdd`, formData, {
                headers: { "Content-Type": "multipart/form-data" },
            })
                .then((response) => {
                    if (response == null) {
                        alert('데이터 업로드 중 오류가 발생했습니다.');
                    } else {
                        alert('데이터가 성공적으로 업로드되었습니다.');
                        this.$router.replace({ name: 'JimiProductList' });
                    }
                })
                .catch(error => {
                    console.error(error);
                });
        }
        ,
        // 읽기 전용 상태 토글 메서드
        toggleReadonly() {
            this.isReadonly = !this.isReadonly;
        }
    }
};
</script>


<style scoped>
.container {
    padding: 20px;
    padding-top: 100px;
}

.table {
    width: 100%;
    border-collapse: collapse;
}

.table th,
.table td {
    border: 1px solid #ddd;
    padding: 8px;
}

.table th {
    background-color: #f2f2f2;
    text-align: center;
}

.image-upload {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100%;
}

.product-image {
    width: 200px;
    height: auto;
    margin-right: 10px;
    border: 1px solid #ddd;
}

.custom-file-input {
    display: inline-block;
    padding: 10px;
    cursor: pointer;
    width: 100%;
    border: 2px solid #ddd;
    background-color: white;
    color: #333;
}

.custom-file-input::-webkit-file-upload-text {
    color: salmon;
}

.custom-file-input::-moz-file-upload-text {
    color: salmon;
}

.btn {
    padding: 10px 20px;
    border: none;
    border-radius: 4px;
    color: white;
    cursor: pointer;
    text-decoration: none;
    margin: 5px;
    display: inline-block;
    text-align: center;
}

.btn-salmon {
    background-color: salmon;
}

.btn-green {
    background-color: green;
}
</style>
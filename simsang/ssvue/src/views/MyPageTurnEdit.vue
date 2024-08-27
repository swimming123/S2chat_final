
/*UI개발: 박성호 */
<template>
  <div class="edit-diary">
    <h2>일기 수정하기</h2>
    <form @submit.prevent="updateDiary">
      <div class="form-group">
        <label for="title">제목:</label>
        <input type="text" id="title" v-model="diary.title" required />
      </div>
      <div class="form-group">
        <label for="content">내용:</label>
        <textarea id="content" v-model="diary.content" rows="10" required></textarea>
      </div>
      <div class="form-group">
        <label for="emotion">감정 상태:</label>
        <input type="text" id="emotion" v-model="diary.emotion" required />
      </div>
      <div class="form-group">
        <label for="week">주간 정보:</label>
        <input type="text" id="week" v-model="diary.week" required />
      </div>
      <div class="form-group">
        <label for="diaryfile">이미지 파일:</label>
        <input type="file" id="diaryfile" @change="onFileChange" />
      </div>
      <div class="form-actions">
        <button type="submit">저장하기</button>
        <button type="button" @click="cancelEdit">취소</button>
      </div>
    </form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'EditDiary',
  data() {
    return {
      diary: {
        num: null,
        title: '',
        content: '',
        emotion: '',
        week: '',
        diaryfile: null,
      },
      selectedFile: null,
    };
  },
  created() {
    // 라우터에서 전달받은 다이어리 정보를 바인딩
    if (this.$route.params.diary) {
      this.diary = { ...this.$route.params.diary };
    }
  },
  methods: {
    onFileChange(event) {
      this.selectedFile = event.target.files[0];
    },
    updateDiary() {
      // FormData를 사용하여 파일과 데이터를 함께 전송
      let formData = new FormData();
      formData.append('num', this.diary.num);
      formData.append('title', this.diary.title);
      formData.append('content', this.diary.content);
      formData.append('emotion', this.diary.emotion);
      formData.append('week', this.diary.week);
      if (this.selectedFile) {
        formData.append('diaryfile', this.selectedFile);
      }

      axios.post(`${process.env.VUE_APP_BACK_END_URL}/diary/diaryUpdate`, formData)
        .then(response => {
          alert('일기가 성공적으로 수정되었습니다.');
          this.$router.push({ name: 'MyPageTurn' });
        })
        .catch(error => {
          console.error('Error updating diary entry:', error);
        });
    },
    cancelEdit() {
      this.$router.push({ name: 'MyPageTurn' });
    }
  }
};
</script>

<style scoped>
.edit-diary {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  background-color: #fff;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
}

.form-group input,
.form-group textarea {
  width: 100%;
  padding: 8px;
  box-sizing: border-box;
}

.form-actions {
  display: flex;
  justify-content: space-between;
}

button {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  background-color: #007bff;
  color: white;
  font-weight: bold;
  transition: background-color 0.3s;
}

button:hover {
  background-color: #0056b3;
}

button[type="button"] {
  background-color: #6c757d;
}

button[type="button"]:hover {
  background-color: #5a6268;
}
</style>

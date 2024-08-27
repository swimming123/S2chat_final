/*UI개발: 허호준 */
<template>
  <div class="modal-overlay" @click.self="close">
    <div class="modal-content">
      <span class="close-btn" @click="close">&times;</span>
      <h2>캐릭터 선택</h2>
      <p class="mt-3 m-0">여러분의 파트너를 골라주세요.</p>
      <!-- 캐릭터 선택 내용 추가 -->
      <div class="m-auto">
        <div
          v-for="(item, index) in character"
          :key="index"
          class="character-item row my-5"
          @click="selectCharacter(item.img)"
        >
          <div class="col-12 d-flex align-items-center justify-content-center" style="cursor: pointer;">
            <img
              :src="'./images/' + item.img"
              :alt="item.name"
              class="rounded-circle"
              style="height: 100px; width: 100px; object-fit: cover"
              
            />
            <div class="ms-3">
              <p>이름 : {{ item.name }}</p>
              <p>반갑소</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
export default {
  name: "CharacterModal",
  data() {
    return {
      character: [{"name":"심이","img":"cuteeeee.png"},{"name":"상이","img":"sing.gif"},{"name":"이이","img":"happyyyy.png"},{"name":"춘배","img":"chunbae.png"}] ,
    };
  },
  methods: {
    close() {
      this.$emit("close");
    },
    selectCharacter(character) {
      // 캐릭터 선택 로직
      this.$emit("select-character", character);
      this.close();
    },
  },
};
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  position: relative;
  background: #fff;
  border-radius: 8px;
  width: 400px;
  height: auto;
  text-align: center;
  overflow: scroll;
}

.close-btn {
  position: absolute;
  top: 10px;
  right: 10px;
  cursor: pointer;
}

</style>

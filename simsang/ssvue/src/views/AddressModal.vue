/*UI개발: 이승희 */
<template>
    <div class="modal-overlay" @click.self="close">
      <div class="modal">
        <button class="close-button" @click="close">×</button>
        <div id="postcode"></div>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    mounted() {
    // 우편번호 팝업을 열어서 주소를 선택하는 로직
      new daum.Postcode({
        oncomplete: (data) => {
            this.$emit('select-address', data.address);
        }
      }).open({
        popupName: 'postcodePopup'
      });
    },
    methods: {
      close() {
        this.$emit('close');
      },
    },
  };
  </script>
  
  <style>
  .modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .modal {
    background-color: white;
    padding: 20px;
    border-radius: 5px;
    position: relative;
  }
  .close-button {
    position: absolute;
    top: 10px;
    right: 10px;
    border: none;
    background: none;
    font-size: 20px;
    cursor: pointer;
  }
  </style>
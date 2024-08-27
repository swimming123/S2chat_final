/*UI개발: 박성호 */
<template>
  <div class="modal-overlay" v-if="isVisible" @click="closeModal">
    <div class="modal-content" @click.stop>
      <slot>
      </slot>
      <button @click="closeModal" style="height: 60px;">Close</button>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue';

export default {
  name: 'Modal',
  props: {
    isVisible: {
      type: Boolean,
      required: true
    }
  },
  setup(props, { emit }) {
    const closeModal = () => {
      emit('update:isVisible', false);
    };

    return {
      closeModal
    };
  }
};
</script>

<style>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}
.modal-content {
  background: white;
  padding: 20px;
  border-radius: 5px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  height: 80%;
}
</style>

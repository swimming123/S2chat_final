/*UI개발: 정준영 */
<template>
  <div class="chat-container">
    <h2>마음 나누기</h2>
    <div>
      <input v-model="message" @keyup.enter="sendMessage" placeholder="Type a message" />
      <button @click="sendMessage">Send</button>
    </div>
    <div id="chatBox" class="chat-box">
      <p v-for="(msg, index) in messages" :key="index">{{ msg }}</p>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      message: '',
      messages: [],
      socket: null,
      isConnecting: false,
      sessionId: null
    };
  },
  mounted() {
    this.connectWebSocket();
  },
  methods: {
    connectWebSocket() {
      this.socket = new WebSocket("ws://192.168.0.58/finalproject/ws");
      this.socket.addEventListener("open", () => {
      });

      this.socket.addEventListener("message", (event) => {
        this.messages.push(event.data);
        this.scrollToBottom();
      });

      this.socket.addEventListener("close", (event) => {
        setTimeout(this.connectWebSocket, 1000); // 재연결 시도
      });

      this.socket.addEventListener("error", (error) => {
        console.error("WebSocket error:", error);
      });
    },
    sendMessage() {
      if (this.message.trim() !== '') {
        this.socket.send(this.message);
        this.message = '';
      }
    },
    scrollToBottom() {
      this.$nextTick(() => {
        const chatBox = document.getElementById('chatBox');
        chatBox.scrollTop = chatBox.scrollHeight;
      });
    }
  }
};
</script>

<style scoped>
.chat-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: #f0f0f0;
  padding: 20px;
}

h2 {
  color: #333;
}

input {
  width: 300px;
  padding: 10px;
  margin: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

button {
  padding: 10px 20px;
  border: none;
  background-color: #28a745;
  color: white;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #218838;
}

.chat-box {
  width: 300px;
  height: 400px;
  overflow-y: auto;
  border: 1px solid #ccc;
  padding: 10px;
  background-color: white;
  border-radius: 5px;
  margin-top: 20px;
}
</style>

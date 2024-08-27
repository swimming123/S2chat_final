const { defineConfig } = require("@vue/cli-service");
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port: 3000,
    proxy: {
      '/api': {
        target: 'http://localhost:8080', // Spring Boot 백엔드의 포트 번호로 수정
        changeOrigin: true,
        pathRewrite: { '^/api': '' },
      },
    },
  },
});
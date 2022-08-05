const { defineConfig } = require('@vue/cli-service')

module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port: 8080,
    proxy: {
      "/": {
        target: "http://localhost:8081",
        changeOrigin: true,
        ws: false,
      }
    }
  }
})

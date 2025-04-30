const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer:{
    port: 8090,
    proxy:{
      '/server8080':{
        target:'http://localhost:8080',
        changeOrigin:true,
        pathRewrite:{
          '^/server8080':''
        }
      }
    }
  }
})

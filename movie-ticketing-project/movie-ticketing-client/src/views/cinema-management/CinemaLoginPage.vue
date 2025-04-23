<template>
    <div class="containter"> 
      <CinemaRegister v-if="showRegister" @switch-another="showAnother"/>
      <CinemaLogin v-else @switch-another="showAnother"/>
    </div>
  </template>
  
  <script>
  import CinemaLogin from '@/components/cinema-management/CinemaLogin.vue';
  import CinemaRegister from '@/components/cinema-management/CinemaRegister.vue';
  export default {
      name: 'CustomerLoginPage',
      components: {
          CinemaRegister,
          CinemaLogin
      },
      data(){
          return{
              showRegister:false
          }
      },
      methods:{
          showAnother(){
              this.showRegister = !this.showRegister
          }
      }
  }
  </script>
  
  <style>
  .containter{
      height: 100vh;
      background:url(https://img.axureshop.com/db/65/7f/db657fd4b7a84b5aa76b1c2d09d02108/images/0_1%E7%99%BB%E5%BD%95/u1.png) no-repeat center center fixed;
      background-size: cover;
      display: flex;
      justify-content: center;
      align-items: center;
  }
  </style>
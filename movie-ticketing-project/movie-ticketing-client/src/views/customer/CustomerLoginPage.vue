<template>
  <div class="containter"> 
    <CustomerRegisterVue v-if="showRegister" @switch-another="showAnother"/>
    <CustomerLoginVue v-else @switch-another="showAnother"/>
  </div>
</template>

<script>
import CustomerRegisterVue from '@/components/customer/CustomerRegister.vue'
import CustomerLoginVue from '@/components/customer/CustomerLogin.vue'

export default {
    name: 'CustomerLoginPage',
    components: {
        CustomerRegisterVue,
        CustomerLoginVue
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
<template>
  <div class="containter">
    <CustomerRegisterVue v-if="showRegister" @switch-another="showAnother" />
    <CustomerLoginVue v-else @switch-another="showAnother" />
  </div>
</template>

<script>
import CustomerRegisterVue from "@/components/customer/CustomerRegister.vue";
import CustomerLoginVue from "@/components/customer/CustomerLogin.vue";

export default {
  name: "CustomerLoginPage",
  components: {
    CustomerRegisterVue,
    CustomerLoginVue,
  },
  data() {
    return {
      showRegister: false,
    };
  },
  methods: {
    showAnother() {
      this.showRegister = !this.showRegister;
    },
  },
};
</script>

<style>
@import "@/styles/login-background.css";
</style>

<template>
  <div class="containter">
    <CinemaRegister v-if="showRegister" @switch-another="showAnother" />
    <CinemaLogin v-else @switch-another="showAnother" />
  </div>
</template>

<script>
import CinemaLogin from "@/components/cinema-management/CinemaLogin.vue";
import CinemaRegister from "@/components/cinema-management/CinemaRegister.vue";
export default {
  name: "CustomerLoginPage",
  components: {
    CinemaRegister,
    CinemaLogin,
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

<style scoped>
@import "@/styles/login-background.css";
</style>

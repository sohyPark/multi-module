<template>
  <div id="app">
    <HeaderMenu />
    <router-view/>
  </div>
</template>

<script>

import Login from './components/Login.vue'
import HeaderMenu from "./components/common/HeaderMenu";

export default {
  name: 'App',
  components: {
    Login,
    HeaderMenu
  }
}
</script>

<style>
html,body{padding:0; margin:0;}
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin:0; padding:0;
}

</style>

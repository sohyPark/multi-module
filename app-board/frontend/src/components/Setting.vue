<template>
  <div>
    <b-nav tabs >
      <b-nav-item active-class="active" to="/setting/user" >사용자 관리</b-nav-item>
      <b-nav-item active-class="active" to="/setting/board">게시판 관리</b-nav-item>
    </b-nav>
    <b-card-body>
      <router-view>
      </router-view>
    </b-card-body>

  </div>
</template>

<script>
export default {
  name: "Setting",
  props: ["name"],
  mounted() {
    this.$router.replace('/setting/user');
  }
}
</script>

<style scoped>

</style>

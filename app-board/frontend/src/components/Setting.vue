<template>
  <div>
    <b-tabs>
      <b-tab active style="width: 200px" title="사용자 관리" to="/user"></b-tab>
      <b-tab title="게시판 관리" to="/board"></b-tab>
    </b-tabs>
    <router-view>
    </router-view>
  </div>
</template>

<script>
export default {
  name: "Setting",
  data: function () {
    return {
      header: {headers: {"Content-type": "application/json"}},
      userList: []
    }
  },
}
</script>

<style scoped>

</style>

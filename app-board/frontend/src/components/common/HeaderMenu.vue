<template>
  <header>
    <div class="menuWrap">
      <ul class="menu">
        <li>
          <router-link to="/news">해커 뉴스</router-link>
        </li>
        <li v-for="item in menus">
          <router-link :to="item.path">item.name</router-link>
        </li>
        <li>
          <router-link to="/setting">설정</router-link>
        </li>
        <li >
          <button class="btn btn-outline-primary"
            @click="logout">LOGOUT</button>
        </li>
      </ul>
    </div>
  </header>

</template>
<script>
export default {
  name: "HeaderMenu",
  data: function () {
    return {
      menus: [],
      isAdmin: false
    }
  },
  methods: {
    logout: function () {
      this.$axios.post('/board/logout')
        .then((response) => {
          this.$router.replace('/login');
        })
        .catch((ex) => {
          console.log(ex);
        })
    },
    getBoards: function () {
      const token = this.$cookie.get('token');
      this.$axios.get('/board/boards', {
        headers: {'jwt-auth-token': token}
      })
        .then((response) => {
          if (response.status === 200) {
            this.menus = response.data;
          }
        })
        .catch((ex) => {
          console.log(ex);
        })
    },
  },
  created() {
    this.getBoards();
  }
}
</script>

<style scoped>
header {
  width: 100%;
  text-align: center;
  position: relative;
  height: 120px;
  border-bottom: 1px solid #35495e
}

header h1 {
  position: absolute;
  top: 0;
  left: 100px;
}

header ul.menu:after {
  display: block;
  clear: both;
  content: '';
}

header ul.menu {
  position: absolute;
  top: 20px;
  right: 50px;
}

header ul.menu li {
  float: left;
  padding: 10px 20px;
  list-style: none;
}

a {
  text-decoration: none;
  color: #333;
}
</style>

<template>
  <div class="login">
    <label>
      <input class="form-control" style="width: 250px" type="text" v-model="email" placeholder="baemin@wooahan.com">
    </label>
    <label>
      <input class="form-control" style="width: 250px; margin-top: 10px" type="password" v-model="password" placeholder="password">
    </label>
    <button class="btn btn-primary" style="width: 250px; margin-top: 10px" @click="loginSubmit">LOGIN</button>
    <button class="btn btn-primary" style="width: 250px; margin-top: 10px" @click="signinSubmit">SIGN IN</button>
  </div>
</template>

<script>
export default {
  name: "Login.vue",
  data: function () {
    return {
      email: '',
      password: ''
    }
  },
  methods: {
    signinSubmit: function () {

      const params = {
        email: this.email,
        password: this.password
      }

      const header = {headers: {"Content-type": "application/json"}};
      this.$axios.post('/board/signin', params, header)
        .then(response => {
          if (response.status === 200) {
            const token = response.headers['jwt-auth-token'];
            this.$cookie.set("token", token);
            const data = response.data;
            this.$cookie.set("user", JSON.stringify(response.data));
            this.$router.replace('/doc');
          } else {
            const errorMessage = JSON.stringify(response.data);
            alert(errorMessage);
          }
        })
        .catch((ex) => {
          console.log(ex);
        })
    },
    loginSubmit: function () {
      if (!this.email) {
        alert("이메일을 입력해주세요.");
        return;
      }
      // if (!this.email.contains("@")) {
      //   alert("이메일형식이 올바르지 않습니다.");
      //   return;
      // }

      if (!this.password) {
        alert("비밀번호를 입력해주세요.");
        return;
      }

      const params = {
        email: this.email,
        password: this.password
      }

      const header = {headers: {"Content-type": "application/json"}};
      this.$axios.post('/board/login', params, header)
        .then(response => {
          if (response.status === 200) {
            const token = response.headers['jwt-auth-token'];
            this.$cookie.set("token", token);
            const data = response.data;
            this.$cookie.set("user", JSON.stringify(response.data));
            this.$router.replace('/doc');
          } else {
            const errorMessage = JSON.stringify(response.data);
            alert(errorMessage);
          }
        })
        .catch((ex) => {
          console.log(ex);
        })
    }
  }
}
</script>

<style scoped>
.login {
  position: absolute;
  width: 100%;
  top: 50%;
  left: 50%;
}
</style>

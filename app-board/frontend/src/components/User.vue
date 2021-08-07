<template>
  <div style="margin-top: 50px; min-height: 500px; margin-left: 50px; margin-right: 50px">
    <SortedTable :values="userList" style="margin-top: 50px">
      <thead style="font-size: smaller">
      <tr>
        <th scope="col" style="text-align: center">
          <SortLink name="title">권한</SortLink>
        </th>
        <th scope="col" style="text-align: center; width: 200px">
          <SortLink name="type">이름</SortLink>
        </th>
        <th scope="col" style="text-align: center; width: 300px">
          <SortLink name="title">이메일</SortLink>
        </th>
        <th scope="col" style="text-align: center">
          <SortLink name="title">랭킹</SortLink>
        </th>
      </tr>
      </thead>
      <tbody slot="body" slot-scope="sort" style="font-size: smaller; text-align: center">
      <tr v-for="item in userList" :key="item.id">
        <td>{{ item.auth }}</td>
        <td hidden style="width: 200px">{{ item.name }}</td>
        <td>{{ item.email }}</td>
        <td>{{ item.rank }}</td>
      </tr>
      </tbody>
    </SortedTable>
    <div style="float: left; display: flex">
      <b-form-group style="margin-left: 20px">
        <b-form-radio v-model="addLayer.auth" name="some-radios" value="1"> 관리자</b-form-radio>
        <b-form-radio v-model="addLayer.auth" name="some-radios" value="2"> 사용자</b-form-radio>
      </b-form-group>
      <b-input style="width: 250px; margin-left: 20px" placeholder="이름"></b-input>
      <b-input style="width: 350px; margin-left: 20px" placeholder="baemin@wooahan.com"></b-input>
      <b-input style="width: 350px; margin-left: 20px" type="password" placeholder="비밀번호"></b-input>
      <button class="btn btn-primary" style="width: 100px; font-size: smaller; float: right; margin-left: 20px" @click="clickAddUser">사용자 추가</button>
    </div>
  </div>

<!--  사용자 추가 -->
<!--  <div v-if="addLayer.show" class="modal-mask">-->
<!--    <div class="modal-wrapper">-->
<!--      <div class="modal-container">-->
<!--        <div class="modal-body">-->
<!--        </div>-->
<!--      </div>-->
<!--    </div>-->
<!--  </div>-->
</template>

<script>
export default {
  name: "User",
  data: function () {
    return {
      header: {headers: {"Content-type": "application/json"}},
      userList: [],
      addLayer: {
        show: false,
        name: "",
        email: "",
        password: "",
        auth: 2
      }
    }
  },
  methods: {
    getUserList: function () {
      if (!this.addLayer.name) {
        alert("이름을 입력해주세요.");
        return;
      }
      if (!this.addLayer.email) {
        alert("이메일을 입력해주세요.");
        return;
      }
      if (!this.addLayer.password) {
        alert("비밀번호를 입력해주세요.");
        return;
      }

      const token = this.$cookie.get('token');
      this.$axios.get('/board/users', {
        headers: {'jwt-auth-token': token}
      })
        .then((response) => {
          // this.tokenValidationChk(response.data)
          if (response.status === 200) {
            console.log(response.data)
            this.userList = response.data;
          }
        })
        .catch((ex) => {
          console.log(ex);
        })
    },
    clickAddUser: function () {
      this.addLayer.show = true;
    },
    addPost: function () {
      const params = {
        name: this.addLayer.name,
        mail: this.addLayer.mail,
        password: this.addLayer.password,
        auth: this.addLayer.auth
      };

      this.$axios.post('/board/users', params, this.header)
        .then((response) => {
          const status = response.status;
          if (status === 200) {
            alert("사용자가 추가 되었습니다.");
            this.addLayer.show = false;
            this.getUserList();
          } else {
            alert(response.data);
          }
        })
        .catch((ex) => {
          console.log(ex);
        })
    },
  },
  created() {
    this.getUserList();
  }
}
</script>

<style scoped>

</style>

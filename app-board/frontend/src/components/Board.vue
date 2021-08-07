<template>
  <div>
    <button class="btn btn-primary" style="width: 100px; font-size: smaller; float: right; margin-right: 20px" @click="clickAddUser">사용자 추가</button>
  </div>
<!--  <SortedTable :values="boardList" style="margin-top: 50px">-->
<!--    <thead style="font-size: smaller">-->
<!--    <tr>-->
<!--      <th scope="col" style="text-align: center">-->
<!--        <SortLink name="type">이름</SortLink>-->
<!--      </th>-->
<!--      <th scope="col" style="text-align: center">-->
<!--        <SortLink name="title">설명</SortLink>-->
<!--      </th>-->
<!--      <th scope="col" style="text-align: center">-->
<!--        <SortLink name="title">숨김여</SortLink>-->
<!--      </th>-->
<!--    </tr>-->
<!--    </thead>-->
<!--    <tbody slot="body" slot-scope="sort" style="font-size: smaller; text-align: center">-->
<!--    <tr v-for="item in boardList" :key="item.id">-->
<!--      <td hidden>{{ item.id }}</td>-->
<!--      <td>{{ item.name }}</td>-->
<!--      <td>{{ item.description }}</td>-->
<!--      <td>{{ item.active }}</td>-->
<!--    </tr>-->
<!--    </tbody>-->
<!--  </SortedTable>-->
</template>

<script>
export default {
name: "Board",
  data: function () {
    return {
      header: {headers: {"Content-type": "application/json"}},
      boardList: [],
      addLayer: {
        show: false,
        name: "",
        email: "",
        password: "",
        auth: 0
      }
    }
  },
  methods: {
    getBoardList: function () {
      const token = this.$cookie.get('token');
      this.$axios.get('/board/boards', {
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
    this.getBoardList();
  }
}
</script>

<style scoped>

</style>

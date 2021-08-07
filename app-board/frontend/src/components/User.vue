<template>
  <div style="margin-top: 50px; min-height: 500px; margin-left: 50px; margin-right: 50px">
    <SortedTable :values="userList" style="margin-top: 50px">
      <thead style="font-size: me">
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
          <SortLink name="title">상태</SortLink>
        </th>
        <th scope="col" style="text-align: center">
        </th>
      </tr>
      </thead>
      <tbody slot="body" slot-scope="sort" style="font-size: medium; text-align: center">
      <tr v-for="(item, index) in userList" :key="item.id">
        <!--권한-->
        <div v-if="item.editable">
          <b-form-group  style="margin-left: 20px">
            <b-form-radio v-model="addLayer.auth" name="some-radios" value="1"> 관리자</b-form-radio>
            <b-form-radio v-model="addLayer.auth" name="some-radios" value="2"> 사용자</b-form-radio>
          </b-form-group>
        </div>
        <div v-else>
          <td>{{ item.auth|authFilter }}</td>
        </div>

        <!--이름-->
        <b-input v-if="item.editable">{{ item.name }}</b-input>
        <td v-else style="width: 200px">{{ item.name }}</td>
        <td>{{ item.email }}</td>
        <td>{{ item.active|activeFilter }}</td>
        <td>
          <b-button @click="clickEdit(index)">편집</b-button>
        </td>
      </tr>
      </tbody>
    </SortedTable>
    <div style="float: left; display: flex">
      <b-form-group style="margin-left: 20px">
        <b-form-radio v-model="addLayer.auth" name="some-radios" value="1"> 관리자</b-form-radio>
        <b-form-radio v-model="addLayer.auth" name="some-radios" value="2"> 사용자</b-form-radio>
      </b-form-group>
      <b-input v-model="addLayer.name" style="width: 250px; margin-left: 20px" placeholder="이름"></b-input>
      <b-input v-model="addLayer.email" style="width: 350px; margin-left: 20px"
               placeholder="baemin@wooahan.com"></b-input>
      <b-input v-model="addLayer.password" style="width: 350px; margin-left: 20px" type="password"
               placeholder="비밀번호"></b-input>
      <button class="btn btn-primary" style="width: 100px; font-size: smaller; float: right; margin-left: 20px"
              @click="addUser">사용자 추가
      </button>
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
        name: "",
        email: "",
        password: "",
        auth: 1
      }
    }
  },
  filters: {
    authFilter: function (val) {
      if (val === 1) {
        return "관리자";
      } else if (val === 2) {
        return "사용자";
      } else {
        return "-";
      }
    },
    activeFilter: function (val) {
      if (val === 0) {
        return "비활성";
      } else if (val === 1) {
        return "활성";
      } else {
        return "-";
      }
    }
  },
  methods: {
    getUserList: function () {
      const token = this.$cookie.get('token');
      this.$axios.get('/board/users', {
        headers: {'jwt-auth-token': token}
      })
        .then((response) => {
          // this.tokenValidationChk(response.data)
          if (response.status === 200) {
            this.userList = response.data;
            this.initialize();
          } else {
            alert(response.data)
          }
        })
        .catch((ex) => {
          console.log(ex);
        })
    },
    initialize: function () {
      this.addLayer = {
        name: "",
        email: "",
        password: "",
        auth: 1
      }
    },
    addUser: function () {
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

      const params = {
        name: this.addLayer.name,
        email: this.addLayer.email,
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
    updateUser: function (item) {
      this.$axios.put('/board/users/' + item.id, item, this.header)
        .then((response) => {
          this.tokenValidationChk(response.data);
          const status = response.status;
          if (status === 200) {
            let alertMessage;
            if (isApproval === 1) {
              alertMessage = "결재 승인이 완료되었습니다.";
            } else if (isApproval === 2) {
              alertMessage = "결재 거절이 완료되었습니다.";
            }
            alert(alertMessage);
            this.detailLayer.show = false;
            this.detailLayer.approvalList = [];
            this.detailLayer.comments = null;
            this.getDocList();
          } else {
            alert(response.data);
          }
        })
        .catch((ex) => {
          console.log(ex);
        })
    },
    clickEdit: function (idx) {
      this.userList.forEach((value, index) => {
        if (idx === index) {
          value.editable === 'undefined' || value.editable === false ? value.editable = true : value.editable = false;
        } else {
          value.editable = false;
        }
      })
    }
  },
  created() {
    this.getUserList();
  }
}
</script>

<style scoped>

</style>

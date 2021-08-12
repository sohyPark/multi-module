<template>
  <div style="margin-top: 50px; min-height: 500px; margin-left: 50px; margin-right: 50px">
    <div>
      <button class="btn btn-primary"
                style="width: 100px; font-size: smaller; float: right"
                v-b-modal.modal-add-user>사용자 추가
      </button>
    </div>
    <SortedTable :values="userList" style="margin-top: 50px">
      <thead style="font-size: medium">
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
          <b-form-group style="margin-left: 20px">
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
          <b-button v-if="item.editable" @click="updateUser(item)">저장</b-button>
          <b-button v-else @click="clickEdit(index)">편집</b-button>
        </td>
      </tr>
      </tbody>
    </SortedTable>

    <b-modal
      id="modal-add-user"
      ref="modal"
      title="사용자 추가"
      @show="initialize"
      @hidden="initialize"
      @ok="userOk"
    >
      <form ref="form" @submit.stop.prevent="userSubmit">
        <b-form-group
          label="권한"
          label-for="role-radios">
          <div style="display: flex; margin-bottom: 20px">
            <b-form-radio
              v-model="role"
              name="role-radios"
              style="margin-right: 20px"
              value="1">
              관리자
            </b-form-radio>
            <b-form-radio
              v-model="role"
              name="role-radios"
              value="2">
              사용자
            </b-form-radio>
          </div>

        </b-form-group>

        <b-form-group
          label="이름"
          label-for="name-input"
          invalid-feedback="이름을 입력해주세요."
          :state="nameState"
          style="margin-bottom: 20px"
        >
          <b-form-input
            ref="ref-name"
            id="name-input"
            v-model="name"
            placeholder="홍길동"
            :state="nameState"
            required
          ></b-form-input>
        </b-form-group>

        <b-form-group
          label="이메일"
          label-for="email-input"
          invalid-feedback="이메일을 입력해주세요."
          :state="emailState"
          style="margin-bottom: 20px"
        >
          <b-form-input
            ref="ref-email"
            id="email-input"
            v-model="email"
            placeholder="baemin@wooahan.com"
            :state="emailState"
            required
          ></b-form-input>
        </b-form-group>

        <b-form-group
          label="비밀번호"
          label-for="password-input"
          invalid-feedback="비밀번호를 입력해주세요."
          :state="passwordState"
          style="margin-bottom: 20px"
        >
          <b-form-input
            ref="ref-password"
            id="email-input"
            v-model="password"
            type="password"
            placeholder="비밀번호"
            :state="passwordState"
            required
          ></b-form-input>
        </b-form-group>
      </form>
    </b-modal>

  </div>
</template>

<script>
export default {
  name: "User",
  data: function () {
    return {
      role: 1,
      name: '',
      nameState: null,
      email: '',
      emailState: null,
      passwordState: null,
      password: "",

      header: {headers: {"Content-type": "application/json"}},
      userList: [],
      addLayer: {
        show: false,
        nameState: null,
        name: "",
        emailState: null,
        email: "",
        passwordState: null,
        password: "",
        auth: 1
      },
      modalShow: false
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
    checkFormValidity() {

      this.nameState = this.$refs['ref-name'].value !== ''
      this.emailState = this.$refs['ref-email'].value !== ''
      this.passwordState = this.$refs['ref-password'].value !== ''

      return !(!this.nameState || !this.emailState || !this.passwordState);
    },
    getUserList: function () {
      const token = this.$cookie.get('token');
      this.$axios.get('/admin/users', {
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
      this.role = 1;
      this.name =  '';
      this.email = '';
      this.password = '';
    },

    userOk(bvModalEvt) {
      // Prevent modal from closing
      bvModalEvt.preventDefault()
      this.userSubmit()
    },
    userSubmit() {
      if (!this.checkFormValidity()) {
        return
      }
      this.addUser();
      this.$nextTick(() => {
        this.$bvModal.hide('modal-prevent-closing')
      })
    },
    addUser: function () {

      const params = {
        name: this.name,
        email: this.email,
        password: this.password,
        auth: this.role
      };

      this.$axios.post('/admin/users', params, this.header)
        .then((response) => {
          const status = response.status;
          if (status === 200) {
            alert("사용자가 추가 되었습니다.");
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

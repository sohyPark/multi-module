<template>
  <div style="margin-top: 50px; min-height: 500px; margin-left: 50px; margin-right: 50px">
    <SortedTable :values="boardList" style="margin-top: 50px">
      <thead style="font-size: medium">
      <tr>
        <th scope="col" style="text-align: center;">
          <SortLink name="name">게시판 이름</SortLink>
        </th>
        <th scope="col" style="text-align: center">
          <SortLink name="description">게시판 설명</SortLink>
        </th>
        <th scope="col" style="text-align: center">
          <SortLink name="active">상태</SortLink>
        </th>
      </tr>
      </thead>
      <tbody slot="body" slot-scope="sort" style="font-size: medium; text-align: center">
      <tr v-for="(item, index) in boardList" :key="item.id">
        <!--이름-->
        <b-input v-if="item.editable">{{ item.name }}</b-input>
        <td v-else style="width: 200px">{{ item.name }}</td>
        <td>{{ item.description }}</td>
        <td>
          <b-button @click="clickEdit(index)">편집</b-button>
        </td>
      </tr>
      </tbody>
    </SortedTable>
    <div style="float: left; display: flex">
      <b-input v-model="addLayer.name" style="width: 250px; margin-left: 20px" placeholder="게시판 이름"></b-input>
      <b-input v-model="addLayer.description" style="width: 550px; margin-left: 20px" placeholder="게시판 설명"></b-input>
      <button class="btn btn-primary" style="width: 100px; font-size: smaller; float: right; margin-left: 20px" @click="addBoard">게시판 추가
      </button>
    </div>
  </div>

</template>

<script>
export default {
name: "Board",
  data: function () {
    return {
      header: {headers: {"Content-type": "application/json"}},
      boardList: [],
      addLayer: {
        name: "",
        description: ""
      }
    }
  },
  methods: {
    getBoardList: function () {
      const token = this.$cookie.get('token');
      this.$axios.get('/admin/boards', {
        headers: {'jwt-auth-token': token}
      })
        .then((response) => {
          // this.tokenValidationChk(response.data)
          if (response.status === 200) {
            console.log(response.data)
            this.boardList = response.data;
          }
        })
        .catch((ex) => {
          console.log(ex);
        })
    },
    addBoard: function () {
      const params = {
        name: this.addLayer.name,
        description: this.addLayer.description
      };
      this.$axios.post('/admin/boards', params, this.header)
        .then((response) => {
          const status = response.status;
          if (status === 200) {
            alert("게시판이 추가 되었습니다.");
            this.getBoardList();
            this.initialize();
          } else {
            alert(response.data);
          }
        })
        .catch((ex) => {
          console.log(ex);
        })
    },
    initialize: function () {
      this.addLayer = {
        name: "",
        description: ""
      }
    }
  },
  created() {
    this.getBoardList();
  }
}
</script>

<style scoped>

</style>

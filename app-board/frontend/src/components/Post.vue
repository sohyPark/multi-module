<template>
  <div class="post">
    <div>
      <button class="btn btn-light" style="width: 80px; margin-left: 20px; font-size: smaller" @click="getPostList">조회</button>
    </div>
    <div>
      <button class="btn btn-primary" style="width: 100px; font-size: smaller; float: right" @click="clickAddPost">게시물 등록</button>
    </div>

    <div style="margin-top: 50px; min-height: 500px">
      <SortedTable :values="postList" style="margin-top: 50px">
        <thead style="font-size: smaller">
        <tr>
          <th scope="col" style="text-align: center">
            <SortLink name="type">no</SortLink>
          </th>
          <th scope="col" style="text-align: center">
            <SortLink name="title">제목</SortLink>
          </th>
          <th scope="col" style="text-align: center">
            <SortLink name="regName">등록자</SortLink>
          </th>
          <th scope="col" style="text-align: center">
            <SortLink name="regDatetime">등록일</SortLink>
          </th>
        </tr>
        </thead>
        <tbody slot="body" slot-scope="sort" style="font-size: smaller; text-align: center">
        <tr v-for="item in postList" :key="item.id">
          <td>{{ item.id }}</td>
          <td>{{ item.title }}</td>
          <td>{{ item.user.name }}</td>
          <td>{{ item.createdDatetime }}</td>
<!--          <td>-->
<!--            <button class="btn btn-dark" style="font-size: x-small" @click="openDetailDialog(item)">수정</button>-->
<!--          </td>-->
<!--          <td>-->
<!--            <button class="btn btn-dark" style="font-size: x-small" @click="openDetailDialog(item)">숨기기</button>-->
<!--          </td>-->
          <td>
            <button class="btn btn-dark" style="font-size: x-small" @click="clickPostDetail(item.id)">상세</button>
            <router-link :to="{name: 'Query', query: {name: 'post', id: item.id}}">상세</router-link>
          </td>
        </tr>
        </tbody>
      </SortedTable>
    </div>

    <v-pagination v-model="filter.page" :page-count="total" @change="getPostList"
                  style="position: absolute; left: 45%">
    </v-pagination>

    <!--    게시물 등록-->
    <div v-if="addLayer.show" class="modal-mask">
      <div class="modal-wrapper">
        <div class="modal-container">
          <div class="modal-body">
            <div>
              <input class="form-control" v-model="addLayer.title"
                     placeholder="제목을 입력해주세요."
                     style="margin-top: 20px; font-size: smaller">
              <textarea class="text-area" v-model="addLayer.contents"
                        placeholder="내용을 입력해주세요."
                        style="width: 100%; margin-top: 20px; height: 200px; font-size: smaller"></textarea>
            </div>
          </div>

          <div class="modal-footer" style="display: block">
            <div style="margin-top: 20px">
              <button class="btn btn-primary" style="width: 49%; float: left; font-size: smaller" @click="addPost">등록
              </button>
              <button class="btn btn-dark" style="width: 49%; float: right; font-size: smaller"
                      @click="clickPostCancel">닫기
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!--게시물 상세-->
<!--      <div v-if="detailLayer.show" class="modal-mask">-->
<!--        <div class="modal-wrapper">-->
<!--          <div class="modal-container">-->
<!--            <div class="modal-body">-->
<!--              <div style="width: 70%; float: left">-->
<!--                <span>[{{detailLayer.doc.type}}]{{detailLayer.doc.title}}</span>-->
<!--                <textarea class="text-area" v-model="detailLayer.doc.contents" readonly-->
<!--                          style="width: 100%; margin-top: 20px; height: 200px; font-size: smaller"></textarea>-->
<!--              </div>-->
<!--            </div>-->

<!--            <div class="modal-footer" style="display: block">-->
<!--              <div style="margin-top: 20px">-->
<!--                <button class="btn btn-dark" style="width: 100%; font-size: smaller" @click="closeDetailDialog">닫기</button>-->
<!--              </div>-->
<!--            </div>-->
<!--          </div>-->
<!--        </div>-->
<!--      </div>-->
  </div>


</template>

<script>
import Vue from 'vue'
import vPagination from 'vue-plain-pagination';

Vue.component('vPagination', vPagination)

export default {
  name: "Post",
  data: function () {
    return {
      header: {headers: {"Content-type": "application/json"}},
      total: 0,
      filter: {
        page: 0,
        size: 3
      },
      addLayer: {
        show: false,
        title: "",
        contents: ""
      },
      detailLayer: {
        show: false,
        post: {}
      },
      postList: []
    }
  },
  methods: {
    getPostList: function () {
      const token = this.$cookie.get('token');
      this.$axios.get('/board/posts', {
        headers: {'jwt-auth-token': token},
        params: {
          page: this.filter.page,
          size: this.filter.size
        }
      }).then((response) => {
        //this.tokenValidationChk(response.data)
        if (response.status === 200) {
          const total = response.data.total;
          const size = this.filter.size;

          this.total = Math.ceil(total / size);
          this.postList = response.data.list;
        }
      }).catch((ex) => {
        console.log(ex);
      })
    },
    clickAddPost: function () {
      this.addLayer.show = true;
    },
    clickPostCancel: function () {
      this.addLayer.show = false;
      this.addLayer.contents = "";
      this.addLayer.title = "";
    },
    clickPostDetail: function (id) {
      this.getPost(id);
      this.detailLayer.show = true;
    },
    addPost: function () {
      const params = {
        title: this.addLayer.title,
        contents: this.addLayer.contents
      };

      this.$axios.post('/board/posts', params, this.header)
        .then((response) => {
          const status = response.status;
          if (status === 200) {
            alert("게시물이 등록되었습니다.");
            this.addLayer.show = false;
            this.getPostList();
          } else {
            alert(response.data);
          }
        }).catch((ex) => {
        console.log(ex);
      })
    },
    getPost: function (id) {
      this.$axios.post('/board/posts/' + id, this.header)
        .then((response) => {
          const status = response.status;
          if (status === 200) {
            this.detailLayer.post = response.data;
          }
        }).catch((ex) => {
        console.log(ex);
      })
    }
  },
  created() {
    this.getPostList();
  }
}
</script>

<style scoped>
.post {
  padding: 50px;
}

.modal-mask {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, .5);
  display: table;
  transition: opacity .3s ease;
}

.modal-wrapper {
  display: table-cell;
  vertical-align: middle;
}

.modal-container {
  width: 80%;
  height: 70%;
  margin: 0px auto;
  padding: 20px 30px;
  background-color: #fff;
  border-radius: 2px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, .33);
  transition: all .3s ease;
  font-family: Helvetica, Arial, sans-serif;
}

.modal-header h3 {
  margin-top: 0;
  color: #42b983;
}

.modal-body {
  margin: 20px 0;
}
</style>

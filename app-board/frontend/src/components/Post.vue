<template>
  <div class="post">
<!--    <div style="float: right">-->
<!--      <span>{{user.name}}({{user.id}})님이 접속하였습니다.</span>-->
<!--    </div>-->

    <div>
<!--      <input class="form-control" v-model="dialogLayer.title"-->
<!--             placeholder="검색어 입력해주세요."-->
<!--             style="margin-top: 20px; font-size: smaller">-->

      <button class="btn btn-dark" style="width: 80px; margin-left: 20px; font-size: smaller" @click="getPostList">조회</button>
    </div>

    <div>
      <button class="btn btn-primary" style="width: 100px; font-size: smaller; float: right" @click="addPost">게시물 등록</button>
    </div>

    <div style="margin-top: 50px; min-height: 500px">
      <SortedTable :values="postList" style="margin-top: 50px">
        <thead style="font-size: smaller">
        <tr>
          <th scope="col" style="text-align: center">
            <SortLink name="type">분류</SortLink>
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
          <td>{{item.id}}</td>
          <td>{{item.title}}</td>
          <td>{{item.user.name}}({{item.user.id}})</td>
          <td>{{item.createdDate}}</td>
          <td>
            <button class="btn btn-dark" style="font-size: x-small" @click="openDetailDialog(item)">결재 보기</button>
          </td>
        </tr>
        </tbody>
      </SortedTable>
    </div>

    <v-pagination v-model="filter.page" :page-count="total" @change="getDocList"
                  style="position: absolute; left: 45%">
    </v-pagination>

    <!--게시물 등-->
    <div v-if="dialogLayer.show" class="modal-mask">
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
              <button class="btn btn-primary" style="width: 49%; float: left; font-size: smaller" @click="addPost">등록</button>
              <button class="btn btn-dark" style="width: 49%; float: right; font-size: smaller" @click="closeDialog">닫기</button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!--게시물 상세-->
<!--    <div v-if="detailLayer.show" class="modal-mask">-->
<!--      <div class="modal-wrapper">-->
<!--        <div class="modal-container">-->
<!--          <div class="modal-body">-->
<!--            <div style="width: 70%; float: left">-->
<!--              <span>[{{detailLayer.doc.type}}]{{detailLayer.doc.title}}</span>-->
<!--              <textarea class="text-area" v-model="detailLayer.doc.contents" readonly-->
<!--                        style="width: 100%; margin-top: 20px; height: 200px; font-size: smaller"></textarea>-->
<!--            </div>-->
<!--            <div style="width: 30%; float: right; overflow:auto">-->
<!--              <SortedTable :values="detailLayer.approvalList" style="margin-top: 50px">-->
<!--                <tbody slot="body" slot-scope="sort" style="font-size: smaller">-->
<!--                <tr v-for="item in detailLayer.approvalList" :key="item.userSeq">-->
<!--                  <td style="vertical-align: middle;">{{item.user.name}}({{item.user.id}})</td>-->
<!--                  <td v-if="item.isMine && item.status === 0">-->
<!--                    <button class="btn btn-dark" style="font-size: x-small" @click="updateDoc(1, detailLayer.doc.docSeq)">승인</button>-->
<!--                  </td>-->
<!--                  <td v-if="item.isMine && item.status === 0">-->
<!--                    <button class="btn btn-dark" style="font-size: x-small" @click="updateDoc(2, detailLayer.doc.docSeq)">거절</button>-->
<!--                  </td>-->
<!--                  <td v-if="item.status === 1">-->
<!--                    <span style="width: 50px; font-size: smaller" class="btn btn-success">승인</span>-->
<!--                  </td>-->
<!--                  <td v-if="item.status === 2">-->
<!--                    <span style="width: 50px; font-size: smaller" class="btn btn-danger">거절</span>-->
<!--                  </td>-->
<!--                  <br>-->
<!--                  <td v-if="item.isMine && item.status === 0">-->
<!--                    <input class="form-control" placeholder="코멘트 입력 가능" v-model="detailLayer.comments">-->
<!--                  </td>-->
<!--                  <td v-else-if="item.status > 0">-->
<!--                    <span>{{item.comments}}</span>-->
<!--                  </td>-->
<!--                </tr>-->
<!--                </tbody>-->
<!--              </SortedTable>-->
<!--            </div>-->
<!--          </div>-->

<!--          <div class="modal-footer" style="display: block">-->
<!--            <div style="margin-top: 20px">-->
<!--              <button class="btn btn-dark" style="width: 100%; font-size: smaller" @click="closeDetailDialog">닫기</button>-->
<!--            </div>-->
<!--          </div>-->
<!--        </div>-->
<!--      </div>-->
<!--    </div>-->
  </div>


</template>

<script>
export default {
  name: "Post",
  data: function () {
    return {
      filter: {
        page: 0,
        size: 3
      },
      addLayer: {
        title,
        contents,
        boadId,
        userId
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
      })
      .then((response) => {
        this.tokenValidationChk(response.data)
        if (response.status == 200) {
          const total = response.data.total;
          const size = this.filter.size;

          this.total = Math.ceil(total / size);
          this.postList = response.data.list;
        }
      })
      .catch((ex) => {
        console.log(ex);
      })
    },
    addPost: function () {
      const params = {
        title: this.dialogLayer.title,
        contents: this.dialogLayer.contents,
        type: this.dialogLayer.type,
        approvalUserList: approvalUserList,
        userSeq: this.user.userSeq
      };

      this.$axios.post('/api/doc', params, header)
        .then((response) => {
          const status = response.status;
          if (status === 200) {
            alert("결재 요청이 성공하였습니다.");
            this.dialogLayer.show = false;
            this.getDocList();

            return;
          } else {
            alert(response.data);
          }
        })
        .catch((ex) => {
          console.log(ex);
        })
    },
    tokenValidationChk: function (data) {
      if (data.hasOwnProperty('isToken')) {
        const isToken = data.isToken;
        if ('false' === isToken) {
          this.$cookie.delete('toke');
          this.$router.replace('/login');
        }
      }
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
</style>

<template>
  <div style="margin-top: 50px; min-height: 500px">
    <h3 style="text-align: center; color: #2bc1bc">Hacker Latest News</h3>
    <SortedTable :values="newsList" style="margin-top: 50px">
      <thead style="font-size: smaller">
      <tr>
        <th scope="col" style="text-align: center">
          <SortLink name="type">no</SortLink>
        </th>
        <th scope="col" style="text-align: center">
          <SortLink name="title">type</SortLink>
        </th>
        <th scope="col" style="text-align: center">
          <SortLink name="title">title</SortLink>
        </th>
      </tr>
      </thead>
      <tbody slot="body" slot-scope="sort" style="font-size: smaller; text-align: center">
      <tr v-for="item in newsList" :key="item.id">
        <td hidden>{{item.id}}</td>
        <td hidden>{{item.url}}</td>
        <td>{{ item.type }}</td>
        <td>{{ item.title }}</td>
      </tr>
      </tbody>
    </SortedTable>
  </div>
</template>

<script>
export default {
  name: "HackerNews",
  data: function () {
    return {
      header: {headers: {"Content-type": "application/json"}},
      newsList: []
    }
  },
  methods: {
    getNewsList: function () {
      const token = this.$cookie.get('token');
      this.$axios.get('/board/news', {
        headers: {'jwt-auth-token': token}
      })
        .then((response) => {
          debugger
          console.log(response)
         // this.tokenValidationChk(response.data)
          if (response.status === 200) {
            console.log(response.data)
            this.newsList = response.data;
          }
        })
        .catch((ex) => {
          console.log(ex);
        })
    },
  },
  created() {
    this.getNewsList();
  }
}
</script>

<style scoped>

</style>

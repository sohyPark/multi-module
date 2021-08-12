<template>
  <div>



    <b-form-textarea
      id="textarea"
      v-model="comment"
      placeholder="댓글을 등록해보세요."
      rows="3"
      max-rows="5"
    ></b-form-textarea>
    <button></button>
  </div>

</template>

<script>
export default {
  name: "PostDetail",
  data: function () {
    return {
      comment: ''
    }
  },
}
</script>

<style scoped>

</style>

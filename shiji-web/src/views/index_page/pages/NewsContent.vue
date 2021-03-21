<template>
  <iframe
    id="map"
    ref="iframe"
    :src="srcs[$route.query.id]"
    frameborder="0"
    width="100%"
    height="887px"
  />
</template>

<script>
export default {
  name: 'NewsContent',
  data: function() {
    return {
      srcs: {
        0: 'https://www.allhistory.com/article/5d5bb69ad47fa20001dd1168',
        1: 'https://www.allhistory.com/article/5d5bbe14d47fa20001dd11da',
        2: 'https://www.allhistory.com/article/5d5532723ae20d0001fe0ccc',
        3: 'https://www.allhistory.com/article/5cbbd0703ae20d0001b2e01a',
        4: 'https://www.allhistory.com/article/5c43d3d53ae20d0001b69246',
        5: 'https://www.allhistory.com/article/5c78e9ef3ae20d00012e5b98',
        6: 'https://www.allhistory.com/article/5ce549c7efe5550001122639'
      }
    }
  },
  mounted() {
    console.log(this.$route.query.id)
  }
}
</script>

<style scoped>

</style>

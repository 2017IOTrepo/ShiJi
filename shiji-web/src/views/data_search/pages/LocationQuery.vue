<template>
  <div>
    <div>
      <h1>
        基于地理位置的搜索
      </h1>
      <el-input
        v-model="input"
        placeholder="请输入查询内容"
        style="width: 20%"
      />
      <el-button icon="el-icon-search" @click="search_handler">
        搜索
      </el-button>
    </div>
    <iframe
      id="map"
      ref="iframe"
      :src="query"
      frameborder="0"
      scrolling="no"
      width="100%"
      height="800px"
    />
  </div>
</template>

<script>
const baseurl = 'http://127.0.0.1:9000/html/map'
export default {
  name: 'LocationQuery',
  data: function() {
    return {
      query: 'http://127.0.0.1:9000/html/map',
      input: ''
    }
  },
  mounted() {
    this.query = baseurl
  },
  methods: {
    search_handler() {
      this.query = baseurl + '?query=' + this.input
      this.$message.success('已搜索成功')
    }
  }
}
</script>

<style scoped>
#map{
  margin-left: 0px;
}
</style>

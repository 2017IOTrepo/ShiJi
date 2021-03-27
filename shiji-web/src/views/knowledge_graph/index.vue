<template>
  <!-- 右侧 -->
  <div class="mind-con">
    <div class="mind-top clearfix">
      <el-input
        v-model="nodename"
        class="input"
        placeholder="请输入关键词"
        @keyup.enter.native="getdomaingraph"
      />
      <el-button @click="getdomaingraph">
        搜索
      </el-button>
      <el-button
        @click="updateGraph"
      >
        刷新
      </el-button>
      <el-button
        class="svg-a-sm"
        @click="requestFullScreen"
      >
        全屏
      </el-button>
      <el-button
        type="primary"
        @click="exportPic"
      >
        导出图片
      </el-button>
      <el-button type="primary">
        导出CSV
      </el-button>
    </div>
    <!-- 底部over -->
    <div id="container" class="container" />
  </div>
  <!-- 右侧over -->
</template>

<script>
import html2canvas from 'html2canvas'
import * as d3 from 'd3'

export default {
  name: 'KnowledageGraph',
  data: function() {
    return {
      cnt: 0,
      link: null,
      node: null,
      nodeText: null,
      simulation: null,
      nodename: '',
      url: '',
      width: 600,
      height: 600,
      nodes: [
      ],
      links: [
      ],
      marker: null,
      svg: null
    }
  },
  mounted() {
  },
  methods: {
    getdomaingraph() {
    },
    requestFullScreen() {
      var element = document.getElementById('container')
      if (element.requestFullscreen) {
        element.requestFullscreen()
      } else if (element.mozRequestFullScreen) {
        element.mozRequestFullScreen()
      } else if (element.webkitRequestFullScreen) {
        element.webkitRequestFullScreen()
      } else if (element.msRequestFullscreen) {
        element.msRequestFullscreen()
      }
    },
    exportPic() {
      html2canvas(document.querySelector('#container')).then(function(canvas) {
        var a = document.createElement('a')
        a.href = canvas.toDataURL('image/png') // 将画布内的信息导出为png图片数据
        var timestamp = Date.parse(new Date())
        a.download = timestamp // 设定下载名称
        a.click() // 点击触发下载
      })
    }
  }
}

</script>

<style lang="scss">
</style>

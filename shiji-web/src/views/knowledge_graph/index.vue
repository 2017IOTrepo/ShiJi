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
import Axios from 'axios'

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
        { 'id': 'Myriel', 'group': 1 },
        { 'id': 'Napoleon', 'group': 1 }
      ],
      links: [
        { 'source': 'Napoleon', 'target': 'Myriel', 'value': 1 }
      ],
      marker: null,
      svg: null
    }
  },
  mounted() {
    this.initGraph()
  },
  methods: {
    initGraph() {
      this.simulation = d3.forceSimulation(this.nodes)
        .force('link', d3.forceLink(this.links).id(d => d.id).distance(200))
        .force('colide', d3.forceCollide().radius(() => 60))
        .force('charge', d3.forceManyBody().strength(-100))
        .force('center', d3.forceCenter(this.width / 2, this.height / 2))

      this.svg = d3.select('.container')
        .append('svg')
        .attr('viewBox', [0, 0, this.width, this.height])

      this.link = this.svg
        .selectAll('path')
        .data(this.links)
        .attr('stroke', '#999')
        .attr('stroke-opacity', 0.6)
        .join('path')
        .attr('stroke-width', d => Math.sqrt(d.value))
        .attr('class', 'link')

      this.node = this.svg
        .selectAll('circle')
        .data(this.nodes)
        .attr('stroke', '#999')
        .attr('stroke-width', 2)
        .attr('stroke-opacity', 0.6)
        .attr('opacity', 0.8)
        .join('circle')
        .attr('class', 'node')
        .attr('r', 20)
        .on('click', function(d) {
          // console.log(d.id)
        })
        .on('dblclick', function(d) {
          console.log(d.id)
        })
        .call(this.drag(this.simulation))

      this.nodeText = this.svg.append('g')
        .selectAll('text')
        .data(this.nodes)
        .join('text')
        .attr('dx', -25)
        .attr('dy', 30)
        .attr('class', 'node-name')
        .attr('fill', 'black')
        .attr('font-family', '微软雅黑')
        .text(function(d) {
          return d.id
        })

      // 箭头
      this.marker =
        this.svg.append('marker')
          .attr('id', 'resolved')
          .attr('markerUnits', 'userSpaceOnUse')
          .attr('viewBox', '0 -5 10 10') // 坐标系的区域
          .attr('refX', 38) // 箭头坐标
          .attr('refY', -1)
          .attr('markerWidth', 10) // 标识的大小
          .attr('markerHeight', 10)
          .attr('orient', 'auto') // 绘制方向，可设定为：auto（自动确认方向）和 角度值
          .attr('stroke-width', 2) // 箭头宽度
          .join('path')
          .attr('d', 'M0,-5L10,0L0,5') // 箭头的路径
          .attr('fill', '#aaa') // 箭头颜色

      this.simulation.on('tick', () => {
        this.link
          .attr('x1', d => d.source.x)
          .attr('y1', d => d.source.y)
          .attr('x2', d => d.target.x)
          .attr('y2', d => d.target.y)
          .attr('d', function(d) {
            return 'M ' + d.source.x + ' ' + d.source.y + ' L ' + d.target.x + ' ' + d.target.y
          })
          .attr('marker-end', 'url(#resolved)')

        this.node
          .attr('cx', d => d.x)
          .attr('cy', d => d.y)

        this.nodeText
          .attr('x', d => d.x)
          .attr('y', d => d.y)
      })
    },
    updateGraph(res) {
      const links = res.nodes
      const nodes = res.links

      this.link = this.link
        .data(links)
        .enter()
        .append('line')
        .attr('stroke', '#999')
        .attr('stroke-opacity', 0.6)
        .attr('stroke-width', d => Math.sqrt(d.value))
        .merge(this.link)
        .attr('class', 'link')

      this.node = this.node
        .data(nodes)
        .enter()
        .append('circle')
        .attr('class', 'node')
        .attr('r', 20)
        .attr('fill', this.color)
        .merge(this.node)
        .on('click', function(d) {
          console.log(d.id)
        })
        .call(this.drag(this.simulation))

      this.node.append('title')
        .text(d => d.id)

      this.nodeText = this.nodeText
        .data(nodes)
        .enter()
        .append('text')
        .merge(this.nodeText)
        .attr('dx', -25)
        .attr('dy', 30)
        .attr('class', 'node-name')
        .attr('fill', 'black')
        .attr('font-family', '微软雅黑')
        .text(function(d) {
          return d.id
        })

      this.simulation.nodes(nodes)
      this.simulation.force('link').links(links)
      this.simulation.alpha(1).restart()
    },
    color(d) {
      return d3.schemeCategory10[d.group % 10]
    },
    drag(simulation) {
      function dragstarted(d) {
        if (!d3.event.active) simulation.alphaTarget(0.002).restart()
        d.fx = d.x
        d.fy = d.y
      }

      function dragged(d) {
        d.fx = d3.event.x
        d.fy = d3.event.y
      }

      function dragended(d) {
        if (!d3.event.active) simulation.alphaTarget(0)
        d.fx = null
        d.fy = null
      }

      return d3.drag()
        .on('start', dragstarted)
        .on('drag', dragged)
        .on('end', dragended)
    },
    getdomaingraph() {
      // this.url = 'http://localhost:9000/html/demo'
      Axios.get('https://api.ownthink.com/kg/knowledge?entity=' +
        encodeURIComponent(this.nodename), {
        timeout: 3000
      })
        .then(
          function(response) {
            const res = response.data.data
            this.updateGraph()
          }.bind(this)
        )
        .catch(function(err) {
          throw err
        })
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
  .mind-con{
    margin: 1.2%;
  }
  .el-input{
    width: 400px;
  }

  .links line {
    stroke: #999;
    stroke-opacity: 0.6;
  }

  .nodes circle {
    stroke: #fff;
    stroke-width: 1.5px;
  }

  /*d3显示传播路径风格*/
  .link {
    stroke: #aaa;
    stroke-width: 1px;
  }

  .link.resolved {
    stroke-dasharray: 0, 2 1;
  }

  .node {
    cursor: move;
    fill: rgba(104, 189, 246, 1.0);
    stroke: rgba(92, 168, 219, 0.9);
    stroke-width: 3px;
  }

  .node.fixed {
    fill: rgba(104, 189, 246, 1.0);
  }

  text {
    font: 12px Microsoft YaHei;
    pointer-events: none;
  }

  .linetext {
    font-size: 12px Microsoft YaHei;
  }
</style>

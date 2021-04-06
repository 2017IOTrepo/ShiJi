<template>
  <!-- 右侧 -->
  <div class="main">
    <div class="mind-top">
      <el-input
        v-model="query"
        class="input"
        placeholder="请输入关键词"
        @keyup.enter.native="getGraphData"
      />
      <el-select v-model="limit" placeholder="单次查询数量(默认为20)">
        <el-option
          v-for="(v, i) in limit_list"
          :key="i"
          class="limit_option"
          :label="v"
          :value="v"
        />
      </el-select>
      <el-button @click="getGraphData">
        搜索
      </el-button>
      <el-button class="svg-a-sm" @click="requestFullScreen">
        全屏
      </el-button>
      <el-button
        :disabled="
          option.series[0].data.length === 0 &&
            option.series[0].links.length === 0
        "
        type="primary"
        @click="exportPic"
      >
        导出图片
      </el-button>
      <el-button
        :disabled="
          option.series[0].data.length === 0 &&
            option.series[0].links.length === 0
        "
        type="primary"
        @click="exportCSV"
      >
        导出CSV
      </el-button>
    </div>
    <!-- <div id="container" class="container" /> -->
    <div id="container">
      <IEcharts
        :option="option"
        :resizable="true"
        @ready="onReady"
        @dblclick="ondblclick"
        @mouseup="onMouseUp"
      />
    </div>
  </div>
  <!-- 右侧over -->
</template>

<script>
import { getNode, expandRelationship } from '@/api/kg'
import IEcharts from 'vue-echarts-v3/src/full.js'
import { arrayNodeUnique } from '@/utils/index'
import { parseTime } from '@/utils/index'

export default {
  name: 'KnowledageGraph',
  components: { IEcharts },
  data: function() {
    return {
      limit: 20,
      limit_list: [20, 30, 50, 100],
      query: '',
      option: {
        title: {},
        tooltip: {
          formatter: function(params) {
            if (params.dataType === 'node') {
              const data = JSON.parse(params.data.value)
              const s = [
                '节点名:' + params.data.name
              ]

              if (data !== null) {
                console.log(data)
                s.push('姓:' + data.fname)
                s.push('氏:' + data.lname)
                s.push('名:' + data.name)
                s.push('生活时间:' + data.life_time)
                s.push('工作时间:' + data.work_time)
                s.push('国籍:' + data.country)
                s.push('工作单位:' + data.work_country)
                s.push('是否国君:' + data.if_monarch)
                s.push('谥号:' + data.posthumous_name)
                s.push('备注:' + data.comments)
              }
              return s.join('<br/>')
            }
            return '关系:' + params.data.relationship
          }
        },
        animationDurationUpdate: 1500,
        animationEasingUpdate: 'quinticInOut',
        backgroundColor: '#fff',
        series: [
          {
            type: 'graph',
            layout: 'force',
            symbolSize: 50,
            roam: true,
            focusNodeAdjacency: false,
            label: {
              show: true,
              fontFamily: 'Microsoft YaHei',
              formatter: function(params) {
                return params.data.name
              }
            },
            draggable: true,
            edgeSymbol: ['arrow'],
            edgeSymbolSize: [1, 20],
            edgeLabel: {
              fontSize: 20,
              show: true,
              formatter: function(params) {
                return params.data.relationship
              }
            },
            itemStyle: {
              borderColor: '#fff',
              borderWidth: 1,
              shadowBlur: 10,
              shadowColor: 'rgba(0, 0, 0, 0.3)',
              // 鼠标放上去有阴影效果
              emphasis: {
                shadowColor: '#000000',
                shadowOffsetX: 0,
                shadowOffsetY: 0,
                shadowBlur: 40
              }
            },
            data: [],
            links: [],
            lineStyle: {
              opacity: 0.9,
              width: 2,
              curveness: 0.01
            },
            force: {
              edgeLength: [200, 500, 700],
              repulsion: 300,
              gravity: 0
            }
          }
        ]
      },
      instance: null,
      chart: null
    }
  },
  mounted() {},
  methods: {
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
      var a = document.createElement('a')
      a.href = this.instance.getDataURL('image/png') // 将画布内的信息导出为png图片数据
      var timestamp = Date.parse(new Date())
      a.download = timestamp // 设定下载名称
      a.click() // 点击触发下载
    },
    exportCSV() {
      import('@/utils/excel').then(excel => {
        let tHeader = ['Id', '名字', '附加值']
        let filterVal = ['id', 'name', 'value']
        let list = this.option.series[0].data
        let data = this.formatJson(filterVal, list)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: 'node_export',
          autoWidth: true,
          bookType: 'csv'
        })

        tHeader = ['Id', '源', '目标', '关系']
        filterVal = ['id', 'source', 'target', 'relationship']
        list = this.option.series[0].links
        data = this.formatJson(filterVal, list)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: 'edge_export',
          autoWidth: true,
          bookType: 'csv'
        })
      })
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v => filterVal.map(j => {
        if (j === 'timestamp') {
          return parseTime(v[j])
        } else {
          return v[j]
        }
      }))
    },
    getGraphData() {
      var _this = this

      new Promise((resolve, reject) => {
        getNode(this.limit, this.query)
          .then(response => {
            _this.option.series[0].data = response.data
          })
          .catch(error => {
            reject(error)
          })
      })
    },
    onReady(instance, ECharts) {
      this.instance = instance
      this.chart = ECharts
      document.getElementById('container').style.height =
        window.innerHeight + 'px'
    },
    updateGraph(id) {
      var _this = this

      new Promise((resolve, reject) => {
        expandRelationship(this.limit, id)
          .then(response => {
            const { node, edge } = response.data
            Array.prototype.push.apply(node, _this.option.series[0].data)
            _this.option.series[0].data = arrayNodeUnique(node)
            Array.prototype.push.apply(edge, _this.option.series[0].links)
            const tmp = arrayNodeUnique(edge)
            const map = {}
            tmp.forEach(item => {
              if (map[[item.source, item.target]] === undefined) {
                map[[item.source, item.target]] = 1
              } else {
                map[[item.source, item.target]] = map[[item.source, item.target]] + 1
              }
            })
            tmp.map(item => {
              item.lineStyle = { curveness: (map[[item.source, item.target]] - 1) * 0.1 }
            })
            _this.option.series[0].links = tmp
          })
          .catch(error => {
            reject(error)
          })
      })
    },
    ondblclick(params) {
      if (params.dataType === 'node') {
        console.log(params)
        this.updateGraph(params.data.id)
      }
    },
    onMouseUp(params) {
      if (params.dataType === 'node') {
        this.option.series[0].data[params.dataIndex].x = params.event.offsetX
        this.option.series[0].data[params.dataIndex].y = params.event.offsetY
        this.option.series[0].data[params.dataIndex].fixed = true
      }
    }
  }
}
</script>

<style lang="scss">
.main {
  margin: 1%;
}

.input {
  width: 20%;
}

.container {
  width: 100%;
  height: 100%;
}
</style>

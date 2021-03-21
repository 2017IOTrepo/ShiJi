<template>
  <div>
    <el-container style="height: 900px">
      <el-container>
        <el-header>
          <div class="search_content">
            <el-input
              v-model="input"
              placeholder="请输入查询内容"
              style="width: 30%; margin-top: 2%"
            />
            <el-button icon="el-icon-search" @click="search_handler">
              搜索
            </el-button>
          </div>
        </el-header>
        <el-main>
          <div class="data-list">
            <el-card class="b-card">
              <h2>
                本书全书数据如下:
              </h2>
              <h3>
                实词词频:
              </h3>
              <h4>
                {{ (allData.real_word / allData.sum).toFixed(6) }}
              </h4>
              <h3>
                虚词词频:
              </h3>
              <h4>
                {{ (allData.empty_word / allData.sum).toFixed(6) }}
              </h4>
              <h3>
                动词词频:
              </h3>
              <h4>
                {{ (allData.var / allData.sum).toFixed(6) }}
              </h4>
              <h3>
                语气词词频:
              </h3>
              <h4>
                {{ (allData.modal / allData.sum).toFixed(6) }}
              </h4>
            </el-card>
            <el-card class="b-card">
              <h2>
                {{ selectRow.title }}中{{ sw }}数据如下:
              </h2>
              <h3>
                实词词频:
              </h3>
              <h4>
                {{ (chapterWordData.real_word / chapterWordData.sum).toFixed(6) }}
              </h4>
              <h3>
                虚词词频:
              </h3>
              <h4>
                {{ (chapterWordData.empty_word / chapterWordData.sum).toFixed(6) }}
              </h4>
              <h3>
                动词词频:
              </h3>
              <h4>
                {{ (chapterWordData.var / chapterWordData.sum).toFixed(6) }}
              </h4>
              <h3>
                语气词词频:
              </h3>
              <h4>
                {{ (chapterWordData.modal / chapterWordData.sum).toFixed(6) }}
              </h4>
            </el-card>
          </div>
          <IEcharts
            :option="options"
            :resizable="true"
            :styles="{height: '350px'}"
          />
        </el-main>
      </el-container>
      <el-aside width="400px" class="aside">
        <el-table
          class="table"
          :data="chapterData"
          fit
          highlight-current-row
          style="width: 100%"
        >
          <el-table-column label="章节列表">
            <template slot-scope="{row}">
              {{ row.title }}
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="{row}">
              <el-button @click="detailButtonClickHandler(row)">
                查看详情
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-aside>
    </el-container>
  </div>
</template>

<script>
import { getChapterList } from '@/api/book'
import { allChapterCnt, getBookChartData, getChapterCnt } from '@/api/search'
import IEcharts from 'vue-echarts-v3/src/full.js'
import echarts from 'echarts'

export default {
  name: 'SingleWordContent',
  components: { IEcharts },
  data: function() {
    return {
      queryBookId: 0,
      input: '',
      sw: '',
      chapterData: [],
      allData: {},
      chapterWordData: {},
      selectRow: {},
      options: {
        tooltip: {
          trigger: 'axis',
          position: function(pt) {
            return [pt[0], '10%']
          }
        },
        title: {
          left: 'center',
          text: '该词在本书中各章的词频'
        },
        toolbox: {
          feature: {
            dataZoom: {
              yAxisIndex: 'none'
            },
            restore: {},
            saveAsImage: {}
          }
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: [0]
        },
        yAxis: {
          type: 'value',
          boundaryGap: [0, '100%']
        },
        dataZoom: [{
          type: 'inside',
          start: 0,
          end: 10
        }, {
          start: 0,
          end: 10,
          handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
          handleSize: '80%',
          handleStyle: {
            color: '#fff',
            shadowBlur: 3,
            shadowColor: 'rgba(0, 0, 0, 0.6)',
            shadowOffsetX: 2,
            shadowOffsetY: 2
          }
        }],
        series: [
          {
            name: '章节',
            type: 'line',
            smooth: true,
            symbol: 'none',
            sampling: 'average',
            itemStyle: {
              color: 'rgb(255, 70, 131)'
            },
            areaStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                offset: 0,
                color: 'rgb(255, 158, 68)'
              }, {
                offset: 1,
                color: 'rgb(255, 70, 131)'
              }])
            },
            data: [0]
          }
        ]
      }
    }
  },
  mounted() {
    this.queryBookId = this.$route.query.bid
    new Promise((resolve, reject) => {
      getChapterList(this.queryBookId).then(response => {
        const { data } = response
        this.chapterData = data
        this.selectRow = data[0]
        this.updateChapterCnt()
        resolve()
      }).catch(error => {
        reject(error)
      })
    })

    new Promise((resolve, reject) => {
      allChapterCnt(this.queryBookId).then(response => {
        const { data } = response
        this.allData = data
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },
  methods: {
    updateChapterCnt() {
      new Promise((resolve, reject) => {
        getChapterCnt(this.selectRow.id, this.sw).then(response => {
          const { data } = response
          this.chapterWordData = data
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },
    updateChart() {
      new Promise((resolve, reject) => {
        getBookChartData(this.queryBookId, this.sw).then(response => {
          const { data } = response
          const td = data
          const x = []
          const y = []
          for (const tdElement of td) {
            x.push(tdElement.title)
            y.push(tdElement.freq)
          }
          this.options.xAxis.data = x
          this.options.series[0].data = y
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },
    search_handler() {
      this.sw = this.input
      this.updateChapterCnt()
      this.updateChart()
    },
    detailButtonClickHandler(row) {
      this.selectRow = row
      this.updateChapterCnt()
    }
  }
}
</script>

<style lang="scss" scoped>
  .aside {
    margin: 3%;
  }

  .search_content {
    margin-bottom: 5%;
    margin-left: 1%;
  }

  .data-list {
    display: grid;
    grid-template-columns: repeat(auto-fill, 550px);
    justify-content: space-around;
  }
</style>

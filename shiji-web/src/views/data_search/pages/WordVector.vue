<template>
  <keep-alive>
    <div>
      <div class="container">
        <img v-if="showIcon" src="@/assets/png_icons/LOGO.png" null="null">
        <br>
        <el-input
          v-model="input"
          placeholder="请输入查询内容"
        />
        <el-select
          v-model="book"
          filterable
          placeholder="请选择古籍"
        >
          <el-option
            v-for="item in bookList"
            :key="item.id"
            :label="item.book_name"
            :value="item.book_name"
          />
        </el-select>
        <el-button icon="el-icon-search" @click="search_handler">
          搜索
        </el-button>
      </div>
      <div v-if="!showIcon">
        <el-row style="height: 900px">
          <el-col :span="16">
            <IEcharts
              :option="options"
              style="height: 500px"
            />
          </el-col>
          <el-col :span="8">
            <IEcharts
              :option="wordCloud"
              style="height: 500px"
            />
          </el-col>

        </el-row>
      </div>
    </div>
  </keep-alive>
</template>

<script>
import IEcharts from 'vue-echarts-v3/src/full.js'
import { getBookShelf } from '@/api/book'

export default {
  name: 'WordVector',
  components: {
    IEcharts
  },
  data: function() {
    return {
      showIcon: true,
      input: '',
      bookList: [],
      book: '',
      options:
          {
            color: ['#db3333'],
            tooltip: {
              trigger: 'axis',
              axisPointer: { // 坐标轴指示器，坐标轴触发有效
                type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
              }
            },
            grid: {
              left: '3%',
              right: '4%',
              bottom: '3%',
              containLabel: true
            },
            xAxis: [
              {
                type: 'category',
                data: ['史记', '司马迁', '文学家', '汉', '额', '骸骨', '头颅', '计策', '叩头', '变改', '易变'],
                axisTick: {
                  alignWithLabel: true
                }
              }
            ],
            yAxis: [
              {
                type: 'value'
              }
            ],
            series: [
              {
                name: '词向量值',
                type: 'bar',
                barWidth: '60%',
                data: [10000, 6181, 4386, 4055, 2467, 2244, 1898, 1484, 1112, 965, 847]
              }
            ]
          },
      wordCloud: {
        tooltip: {},
        series: [
          {
            type: 'wordCloud',
            gridSize: 2,
            sizeRange: [12, 50],
            rotationRange: [-90, 90],
            shape: 'pentagon',
            width: 600,
            height: 400,
            drawOutOfBound: true,
            textStyle: {
              normal: {
                color: function() {
                  return (
                    'rgb(' +
                      [
                        Math.round(Math.random() * 160),
                        Math.round(Math.random() * 160),
                        Math.round(Math.random() * 160)
                      ].join(',') +
                      ')'
                  )
                }
              },
              emphasis: {
                shadowBlur: 10,
                shadowColor: '#333'
              }
            },
            data: [
              {
                name: '史记',
                value: 10000,
                textStyle: {
                  normal: {
                    color: 'black'
                  },
                  emphasis: {
                    color: 'red'
                  }
                }
              },
              {
                name: '司马迁',
                value: 6181
              },
              {
                name: '文学家',
                value: 4386
              },
              {
                name: '汉',
                value: 4055
              },
              {
                name: '额',
                value: 2467
              },
              {
                name: '骸骨',
                value: 2244
              },
              {
                name: '头颅',
                value: 1898
              },
              {
                name: '计策',
                value: 1484
              },
              {
                name: '叩头',
                value: 1112
              },
              {
                name: '变改',
                value: 965
              },
              {
                name: '易变',
                value: 847
              }
            ]
          }
        ]
      }
    }
  },
  mounted() {
    new Promise((resolve, reject) => {
      getBookShelf().then(response => {
        const { data } = response
        this.bookList = data
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },
  methods: {
    search_handler() {
      if (this.input === '' || this.book === '') {
        this.$message({
          message: '请填写空白选项!',
          type: 'warning',
          duration: 4000
        })
      } else {
        this.showIcon = false
      }
    }
  }
}
</script>

<style scoped>
  img {
    width: 312px;
    height: 402px;
    margin-left: 30%;
  }

  .el-input {
    width: 20%;
    margin-left: 20%;
  }

  .echarts {
    width: 800px;
    height: 800px;
    margin: 0 auto;
  }
</style>

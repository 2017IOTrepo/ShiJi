<template>
  <el-row class="map_container">
    <el-col :span="12">
      <iframe
        id="map"
        ref="iframe"
        :src="src"
        frameborder="0"
        scrolling="no"
        width="100%"
        height="600px"
      >
        您当前的浏览器不支持页面上的功能，请升级您当前的浏览器版本或使用谷歌浏览器访问当前页面
      </iframe>
      <el-slider
        v-model="year"
        :min="nowYearRangeMid - 200"
        :max="nowYearRangeMid + 200"
        :marks="mapMarks"
        show-input
        @input="yearRangeChangeHandler"
      />
      <div class="div_container" />
    </el-col>
    <el-col :span="12">
      <div>
        <el-card
          class="right-card"
          shadow="hover"
        >
          <div>
            <p v-if="optionP.series[0].data.length === 0">
              暂无数据
            </p>
            <IEcharts
              v-if="optionP.series[0].data.length !== 0"
              :option="optionP"
              :resizable="true"
              :styles="{height: '200px'}"
            />
          </div>
        </el-card>
        <el-card
          class="right-card"
          shadow="hover"
        >
          <div>
            <p v-if="optionM.series[0].data.length === 0">
              暂无数据
            </p>
            <IEcharts
              v-if="optionM.series[0].data.length !== 0"
              :option="optionM"
              :resizable="true"
              :styles="{height: '200px'}"
            />
          </div>
        </el-card>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import { chooseProvince, chooseDatas } from '@/utils'
import IEcharts from 'vue-echarts-v3/src/full.js'

export default {
  name: 'MapChange',
  components: {
    IEcharts
  },
  data: function() {
    const y = -2070
    return {
      year: y,
      // 现在的年
      nowYearRangeMid: y,
      // 地图点
      mapMarks: {
        '-2070': '夏',
        '-1600': '商',
        '-1046': '西周',
        '-771': '东周(春秋)',
        '-453': '东周(战国)',
        '-221': '秦',
        '-206': '西汉',
        '24': '东汉',
        '280': '西晋',
        '316': '东晋',
        '420': '南北朝',
        '589': '隋',
        '619': '唐',
        '960': '北宋',
        '1127': '南宋',
        '1279': '元朝',
        '1368': '明朝',
        '1644': '清',
        '1949': '中华人民共和国'
      },
      src: '',
      optionP: {
        title: {
          text: '该朝代城市人口分布柱状图及总人数(单位: 万人)'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: { // 坐标轴指示器，坐标轴触发有效
            type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
          },
          formatter: function(params) {
            var tar = params[0]
            return tar.name + '<br/>' + tar.seriesName + ' : ' + tar.value + '万人'
          }
        },
        xAxis: {
          type: 'category',
          data: []
        },
        yAxis: {
          type: 'value'
        },
        series: [{
          name: '总人数',
          data: [],
          type: 'bar',
          showBackground: true,
          backgroundStyle: {
            color: 'rgba(220, 220, 220, 0.8)'
          },
          itemStyle: {
            normal: {
              label: {
                show: true,
                textStyle: {
                  color: '#fff'
                },
                position: 'insideTop',
                formatter(p) {
                  return p.value > 0 ? p.value + '万人' : ''
                }
              }
            }
          }
        }]
      },
      optionM: {
        title: {
          text: '该朝代城市赋税分布柱状图及总税额(单位: 万两)'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: { // 坐标轴指示器，坐标轴触发有效
            type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
          },
          formatter: function(params) {
            console.log(params)
            var tar = params[0]
            return tar.name + '<br/>' + tar.seriesName + ' : ' + tar.value + '万两'
          }
        },
        xAxis: {
          type: 'category',
          data: []
        },
        yAxis: {
          type: 'value'
        },
        series: [{
          name: '总税额',
          data: [],
          type: 'bar',
          showBackground: true,
          backgroundStyle: {
            color: 'rgba(220, 220, 220, 0.8)'
          },
          itemStyle: {
            normal: {
              label: {
                show: true,
                textStyle: {
                  color: '#fff'
                },
                position: 'insideTop',
                formatter(p) {
                  return p.value > 0 ? p.value + '万两' : ''
                }
              }
            }
          }
        }]
      }
    }
  },
  mounted() {
  },
  methods: {
    yearRangeChangeHandler(val) {
      if (val > -1970 && val < 1849) {
        this.nowYearRangeMid = val
      }
      this.optionM.series[0].data = chooseDatas(10, 100, 5)
      this.optionP.series[0].data = chooseDatas(10, 100, 5)
      this.optionM.xAxis.data = chooseProvince(5)
      this.optionP.xAxis.data = chooseProvince(5)
      if (val < -2070) {
        this.src = 'http://119.3.166.63:9000/dy/chuanshuo'
        this.optionM.series[0].data = []
        this.optionP.series[0].data = []
        this.optionM.xAxis.data = []
        this.optionP.xAxis.data = []
      }
      if (val >= -2070 && val < -1600) {
        this.src = 'http://119.3.166.63:9000/dy/xia'
        this.optionM.series[0].data = chooseDatas(10, 100, 5)
        this.optionP.series[0].data = chooseDatas(10, 100, 5)
        this.optionM.xAxis.data = chooseProvince(5)
        this.optionP.xAxis.data = chooseProvince(5)
      }
      if (val >= -1600 && val < -1046) {
        this.src = 'http://119.3.166.63:9000/dy/shang'
      }
      if (val >= -1046 && val < -771) {
        this.src = 'http://119.3.166.63:9000/dy/xizhou'
      }
      if (val >= -771 && val < -453) {
        this.src = 'http://119.3.166.63:9000/dy/xizhou'
      }
      if (val >= -453 && val < -221) {
        this.src = 'http://119.3.166.63:9000/dy/xizhou'
      }
      if (val >= -221 && val < 206) {
        this.src = 'http://119.3.166.63:9000/dy/qin'
      }
      if (val >= -206 && val < 24) {
        this.src = 'http://119.3.166.63:9000/dy/xihan'
      }
      if (val >= 24 && val < 280) {
        this.src = 'http://119.3.166.63:9000/dy/xihan'
      }
      if (val >= 280 && val < 316) {
        this.src = 'http://119.3.166.63:9000/dy/xijin'
      }
      if (val >= 316 && val < 420) {
        this.src = 'http://119.3.166.63:9000/dy/dongjin'
      }
      if (val >= 420 && val < 589) {
        this.src = 'http://119.3.166.63:9000/dy/nanbeichao'
      }
      if (val >= 589 && val < 619) {
        this.src = 'http://119.3.166.63:9000/dy/sui'
      }
      if (val >= 619 && val < 960) {
        this.src = 'http://119.3.166.63:9000/dy/tang'
      }
      if (val >= 960 && val < 1127) {
        this.src = 'http://119.3.166.63:9000/dy/beisong'
      }
      if (val >= 1127 && val < 1279) {
        this.src = 'http://119.3.166.63:9000/dy/nansong'
      }
      if (val >= 1279 && val < 1368) {
        this.src = 'http://119.3.166.63:9000/dy/yuan'
      }
      if (val >= 1368 && val < 1644) {
        this.src = 'http://119.3.166.63:9000/dy/ming'
      }
      if (val >= 1644 && val < 1912) {
        this.src = 'http://119.3.166.63:9000/dy/qing'
      }
      if (val >= 1949) {
        this.src = 'http://119.3.166.63:9000/dy/gongheguo'
        this.optionM.series[0].data = []
        this.optionP.series[0].data = []
        this.optionM.xAxis.data = []
        this.optionP.xAxis.data = []
      }
    }
  }
}
</script>

<style scoped lang="scss">
  .map_container{
    margin: 1%;
    .div_container{
      margin: 2%;
    }

    .chart{
      width: 100%;
      height: 100%;
    }

    .right-card{
      margin: 3%;
      height: 200px;
    }
  }
</style>

<template>
  <keep-alive>
    <el-container style="height: 900px">
      <el-container>
        <el-main>
          <div class="chart-container">
            <chart :data="data" height="100%" width="100%" />
          </div>
        </el-main>
      </el-container>
      <el-aside width="400px">
        <div class="search_content">
          <el-input
            v-model="input"
            placeholder="请输入查询内容"
            style="width: 60%"
          />
          <el-button icon="el-icon-search" @click="search_handler">
            搜索
          </el-button>
        </div>
        <el-card
          v-for="(chapter, index) in chapterData"
          :key="index"
          shadow="hover"
        >
          <img :src="chapter.front" null="null" height="260px" width="200px">
          <h2>{{ chapter.book_name }}</h2>
          <p>
            章节: {{ chapter.title }}
            作者: {{ chapter.author }}
          </p>
          <a :href="'http://localhost:8081/#/chapter?cpid=' + chapter.id+'&id=' + chapter.b_id">
            查看更多
          </a>
        </el-card>
      </el-aside>
    </el-container>
  </keep-alive>
</template>

<script>
import Chart from '../components/MixChart'
import { searchKWChapter } from '@/api/word'
import { randomNum } from '@/utils/index'

export default {
  name: 'TimeQuery',
  components: {
    Chart
  },
  data: function() {
    return {
      input: '',
      tmp: {},
      chapterData: [],
      data: [
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0
      ]
    }
  },
  methods: {
    search_handler(id) {
      console.log(this.tmp)
      if (this.input in this.tmp) {
        this.chapterData = this.tmp[this.input].chapterData
        this.data = this.tmp[this.input].data
      } else {
        new Promise((resolve, reject) => {
          searchKWChapter(this.input).then(response => {
            const { data } = response
            this.chapterData = data
            this.$message({
              message: '查询成功!',
              type: 'success',
              duration: 3000
            })

            if (this.input === '史记') {
              this.data = [
                70,
                197,
                245,
                260,
                179,
                143,
                154,
                325,
                528,
                332,
                244,
                408,
                143,
                154,
                54,
                325,
                528,
                44,
                325,
                528,
                33,
                154
              ]
            } else {
              const tmp = []
              for (let k = 0; k < 22; k++) {
                tmp.push(randomNum(10, 200))
              }
              this.data = tmp
            }

            const cdata = this.data
            const chapterData = this.chapterData

            const tp = {
              'data': cdata,
              chapterData
            }

            this.tmp[this.input] = tp

            resolve()
          }).catch(error => {
            reject(error)
          })
        })
      }
    }
  }
}
</script>

<style scoped>
  .chart-container{
    position: relative;
    width: 100%;
    height: calc(100vh - 84px);
  }
  .search_content {
    margin-top: 30px;
    margin-bottom: 10px;
  }
</style>

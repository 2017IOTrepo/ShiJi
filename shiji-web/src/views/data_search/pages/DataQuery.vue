<template>
  <keep-alive>
    <el-container>
      <el-header>
        <el-select v-model="timeData" placeholder="请选择选取的朝代">
          <el-option
            v-for="item in timeSelect"
            :key="item"
            :lable="item"
            :value="item"
          />
        </el-select>
        <el-select v-model="typeData" placeholder="请选择选取的数据类型">
          <el-option
            v-for="item in typeSelect"
            :key="item"
            :lable="item"
            :value="item"
          />
        </el-select>
        <el-button icon="el-icon-search" @click="search_handler">
          搜索
        </el-button>
      </el-header>
      <el-container>
        <el-main>
          <el-card>
            <el-table
              :data="listData"
              fit
              highlight-current-row
              style="width: 100%;"
            >
              <el-table-column label="朝代">
                {{ timeData }}
              </el-table-column>
              <el-table-column :label="h2">
                <template slot-scope="{row}">
                  {{ row.years }}
                </template>
              </el-table-column>
              <el-table-column :label="h3">
                <template slot-scope="{row}">
                  {{ row.dataAll }}
                </template>
              </el-table-column>
              <el-table-column :label="h4">
                <template slot-scope="{row}">
                  {{ row.dataAvg }}
                </template>
              </el-table-column>
              <el-table-column label="查看详情">
                <template slot-scope="{row}">
                  <el-button @click="detailButtonClickHandler(row.book)">
                    查看详情
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-card>
        </el-main>
        <el-aside width="400px">
          <el-card
            shadow="hover"
          >
            <el-image :src="clickBook.front || require('@/assets/imgs/noCover.jpeg')" null="null" />
            <h2>{{ clickBook.book_name }}</h2>
            <p>
              章节: {{ clickBook.title }}
              作者: {{ clickBook.author }}
            </p>
            <a :href="'http://119.3.166.63:8081/#/chapter?cpid=' + clickBook.id+'&id=' + clickBook.b_id">
              查看更多
            </a>
          </el-card>
        </el-aside>
      </el-container>
    </el-container>
  </keep-alive>
</template>

<script>

import { realAreaData, realPeopleData, timeSelect } from '@/store/datas'
import { getChapterDetail } from '@/api/book'

export default {
  name: 'DataQuery',
  data() {
    return {
      timeData: '',
      timeSelect: timeSelect,
      typeData: '',
      typeSelect: [
        '人口',
        '耕地面积'
      ],
      listData: {},
      h2: '年代',
      h3: '户数(单位:户)',
      h4: '人口数(单位:人)',
      clickBook: {}
    }
  },
  mounted() {

  },
  methods: {
    search_handler() {
      let v = false
      if (this.timeData === '') {
        this.$message({
          message: '请填写朝代!',
          type: 'warning',
          duration: 3000
        })
        v = true
      }
      if (this.typeData === '') {
        this.$message({
          message: '请填写数据类型!',
          type: 'warning',
          duration: 4000
        })
        v = true
      }

      if (v) {
        return
      }

      if (this.typeData === '耕地面积') {
        this.listData = realAreaData[this.timeData]
        this.h3 = '总耕地(单位:亩)'
        this.h4 = '人均耕地(单位:亩)'
      } else {
        this.listData = realPeopleData[this.timeData]
        this.h3 = '户数(单位:户)'
        this.h4 = '人口数(单位:人)'
      }
    },
    detailButtonClickHandler(val) {
      new Promise((resolve, reject) => {
        getChapterDetail(val).then(response => {
          const { data } = response
          this.clickBook = data
          console.log(this.clickBook)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    }
  }
}
</script>

<style scoped>
  .el-header {
    margin-top: 20px;
    margin-left: 20px;
  }
  .el-select {
    margin-right: 10px;
  }

  .el-image {
    width: 200px;
    height: 290px;
  }
</style>

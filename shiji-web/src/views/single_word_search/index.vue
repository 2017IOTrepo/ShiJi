<template>
  <div>
    <h1 class="title">
      请选择需要进行统计的书籍
    </h1>
    <div class="wrapper">
      <div
        v-for="book in shelf"
        :key="book.id"
        class="book"
        @click="toDetail(book.id)"
      >
        <div class="cover-img">
          <img
            class="cover"
            :src="book.front || require('@/assets/imgs/noCover.jpeg')"
            alt=""
          >
        </div>
        <div class="info" @click="toDetail(book.id)">
          <div class="name">{{ book.book_name }}</div>
          <div class="sub">
            <div class="author">
              {{ book.author }}
            </div>
            <div class="dot">•</div>
            <div class="size">类型:{{ book.type }}</div>
          </div>
          <!--<div class="dur-chapter">已读：{{ book.durChapterName }}</div>-->
          <!--<div class="last-chapter">最新：{{ book.lastChapterName }}</div>-->
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getBookShelf } from '@/api/book'

export default {
  name: 'SingleWordSearch',
  data: function() {
    return {
      shelf: {}
    }
  },
  mounted() {
    new Promise((resolve, reject) => {
      getBookShelf().then(response => {
        const { data } = response
        this.shelf = data
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },
  methods: {
    toDetail(bookId) {
      this.$router.push({
        path: '/single_world_search/search_content',
        query: {
          bid: bookId
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
  .title {
    margin-left: 4%;
  }
  .wrapper {
    display: grid;
    grid-template-columns: repeat(auto-fill, 380px);
    justify-content: space-around;
    grid-gap: 10px;

    .book {
      user-select: none;
      display: flex;
      cursor: pointer;
      margin-bottom: 18px;
      padding: 24px 24px;
      width: 360px;
      flex-direction: row;
      justify-content: space-around;

      .cover-img {
        width: 84px;
        height: 112px;

        .cover {
          width: 84px;
          height: 112px;
        }
      }

      .info {
        display: flex;
        flex-direction: column;
        justify-content: space-around;
        align-items: left;
        height: 112px;
        margin-left: 20px;
        flex: 1;

        .name {
          width: fit-content;
          font-size: 16px;
          font-weight: 700;
          color: #33373D;
        }

        .sub {
          display: flex;
          flex-direction: row;
          font-size: 12px;
          font-weight: 600;
          color: #6b6b6b;

          .dot {
            margin: 0 7px;
          }
        }
      }
    }
  }

  .cover {
    height: 200px;
    width: 150px;
  }
</style>

<template>
  <div class="index-wrapper">
    <div class="navigation-wrapper">
      <div class="navigation-title">
        阅读
      </div>
      <div class="navigation-sub-title">
        清风不识字，何故乱翻书
      </div>
      <div class="search-wrapper">
        <el-input
          size="mini"
          placeholder="搜索书籍"
          v-model="search"
          class="search-input"
        >
          <i slot="prefix" class="el-input__icon el-icon-search"></i>
        </el-input>
      </div>
      <div class="recent-wrapper">
        <div class="recent-title">
          最近阅读
        </div>
        <div class="reading-recent">
          <el-tag
            v-for="(book, index) in readingRecent"
            :key="index"
            type="warning"
            class="recent-book"
            @click="toDetail(book.id, book.name)"
            :class="{ 'no-point': book.id == '' }"
            style="margin: 2%"
          >
            {{ book.name }}
          </el-tag>
        </div>
      </div>
    </div>
    <div class="shelf-wrapper" ref="shelfWrapper">
      <div class="books-wrapper">
        <div class="wrapper">
          <div
            class="book"
            v-for="book in shelf"
            :key="book.id"
            @click="toDetail(book.id, book.book_name)"
          >
            <div class="cover-img">
              <img
                class="cover"
                :src="book.front || require('../assets/imgs/noCover.jpeg')"
                alt=""
              />
            </div>
            <div class="info" @click="toDetail(book.id, book.book_name)">
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
    </div>
  </div>
</template>

<script>
import "../assets/fonts/shelffont.css";
import Axios from "axios";

export default {
  data() {
    return {
      search: "",
      readingRecent: []
    };
  },
  mounted() {
    //获取最近阅读书籍
    if (this.shelf.length == 0) {
      this.loading = this.$loading({
        target: this.$refs.shelfWrapper,
        lock: true,
        text: "正在获取书籍信息",
        spinner: "el-icon-loading",
        background: "rgb(247,247,247)"
      });
      const that = this;
      Axios.get("http://119.3.166.63:9000/book/get_book_shelf", {
        timeout: 3000
      })
        .then(function(response) {
          that.loading.close();
          that.$store.commit("increaseBookNum", response.data.data.length);
          that.$store.commit("addBooks", response.data.data);
        })
        .catch(function(error) {
          that.loading.close();
          that.$message.error("后端连接失败");
          throw error;
        });
    }

    this.readingRecent = JSON.parse(localStorage.getItem("readingRecent"));
    if (this.readingRecent == null) {
      this.readingRecent = [];
    }
  },
  methods: {
    updateHistory(data) {
      this.readingRecent = JSON.parse(localStorage.getItem("readingRecent"));
      if (this.readingRecent == null) {
        this.readingRecent = [];
      }
      if (this.readingRecent.length >= 10) {
        this.readingRecent.shift();
      }
      let end = true;
      for (let readingRecentElement of this.readingRecent) {
        if (readingRecentElement.id === data.id) {
          readingRecentElement = data;
          end = false;
        }
      }
      if (end) {
        this.readingRecent.push(data);
      }
      localStorage.setItem("readingRecent", JSON.stringify(this.readingRecent));
    },
    toDetail(bookId, bookName) {
      sessionStorage.setItem("bookId", bookId);
      sessionStorage.setItem("bookName", bookName);
      this.$router.push({
        path: "/chapter"
      });
      this.updateHistory({ id: bookId, name: bookName });
    }
  },
  computed: {
    shelf() {
      return this.$store.state.shelf;
    },
    connectStatus() {
      return this.$store.state.connectStatus;
    }
  }
};
</script>

<style lang="stylus" scoped>
.index-wrapper {
  height: 100%;
  width: 100%;
  display: flex;
  flex-direction: row;

  .navigation-wrapper {
    width: 260px;
    min-width: 260px;
    padding: 48px 36px;
    background-color: #F7F7F7;

    .navigation-title {
      font-size: 24px;
      font-weight: 500;
      font-family: FZZCYSK;
    }

    .navigation-sub-title {
      font-size: 16px;
      font-weight: 300;
      font-family: FZZCYSK;
      margin-top: 16px;
      color: #b1b1b1;
    }

    .search-wrapper {
      .search-input {
        border-radius: 50%;
        margin-top: 24px;

        >>> .el-input__inner {
          border-radius: 50px;
          border-color: #E3E3E3;
        }
      }
    }

    .recent-wrapper {
      margin-top: 36px;

      .recent-title {
        font-size: 14px;
        color: #b1b1b1;
        font-family: FZZCYSK;
      }

      .reading-recent {
        margin: 18px 0;

        .recent-book {
          font-size: 10px;
          // font-weight: 400;
          // margin: 12px 0;
          // font-weight: 500;
          // color: #6B7C87;
          cursor: pointer;
          // padding: 6px 18px;
        }
      }
    }

    .setting-wrapper {
      margin-top: 36px;

      .setting-title {
        font-size: 14px;
        color: #b1b1b1;
        font-family: FZZCYSK;
      }

      .no-point {
        pointer-events: none;
      }

      .setting-connect {
        font-size: 8px;
        margin-top: 16px;
        // color: #6B7C87;
        cursor: pointer;
      }
    }

    .bottom-icons {
      position: fixed;
      bottom: 0;
      height: 120px;
      width: 260px;
      align-items: center;
      display: flex;
      flex-direction: row;
    }
  }

  .shelf-wrapper {
    padding: 48px 48px;
    width: 100%;

    >>>.el-icon-loading {
      font-size: 36px;
      color: #B5B5B5;
    }

    >>>.el-loading-text {
      font-weight: 500;
      color: #B5B5B5;
    }

    .books-wrapper {
      height: 100%;
      overflow: scroll;

      .wrapper {
        display: grid ;
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

            .intro, .dur-chapter, .last-chapter {
              color: #969ba3;
              font-size: 13px;
              margin-top: 3px;
              font-weight: 500;
              word-wrap: break-word;
              overflow: hidden;
              text-overflow: ellipsis;
              display: -webkit-box;
              -webkit-box-orient: vertical;
              -webkit-line-clamp: 1;
              text-align: left;
            }
          }
        }

        .book:hover {
          background: rgba(0, 0, 0, 0.1);
          transition-duration: 0.5s;
        }
      }

      .wrapper:last-child {
        margin-right: auto;
      }
    }

    .books-wrapper::-webkit-scrollbar {
      width: 0 !important;
    }
  }
}
</style>

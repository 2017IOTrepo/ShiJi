<template>
  <div
    class="chapter-wrapper"
    :style="bodyTheme"
    :class="{ night: isNight, day: !isNight }"
  >
    <div class="tool-bar" :style="leftBarTheme">
      <div class="tools">
        <el-popover
          placement="right"
          :width="cataWidth"
          trigger="click"
          :visible-arrow="false"
          v-model="popCataVisible"
          popper-class="pop-cata"
        >
          <PopCata @getContent="getContent" ref="popCata" class="popup" />

          <div
            class="tool-icon"
            :class="{ 'no-point': noPoint }"
            slot="reference"
          >
            <div class="iconfont">
              &#58905;
            </div>
            <div class="icon-text">目录</div>
          </div>
        </el-popover>
        <el-popover
          placement="bottom-right"
          width="470"
          trigger="click"
          :visible-arrow="false"
          v-model="readSettingsVisible"
          popper-class="pop-setting"
        >
          <ReadSettings class="popup" />

          <div
            class="tool-icon"
            :class="{ 'no-point': noPoint }"
            slot="reference"
          >
            <div class="iconfont">
              &#58971;
            </div>
            <div class="icon-text">设置</div>
          </div>
        </el-popover>
        <div class="tool-icon" @click="toShelf">
          <div class="iconfont">
            &#58892;
          </div>
          <div class="icon-text">书架</div>
        </div>
        <div class="tool-icon" :class="{ 'no-point': noPoint }" @click="toTop">
          <div class="iconfont">
            &#58914;
          </div>
          <div class="icon-text">顶部</div>
        </div>
        <div
          class="tool-icon"
          :class="{ 'no-point': noPoint }"
          @click="toBottom"
        >
          <div class="iconfont">
            &#58915;
          </div>
          <div class="icon-text">底部</div>
        </div>
      </div>
    </div>
    <div class="read-bar" :style="rightBarTheme">
      <div class="tools">
        <div
          class="tool-icon"
          :class="{ 'no-point': noPoint }"
          @click="toLastChapter"
        >
          <div class="iconfont">
            &#58920;
          </div>
        </div>
        <div
          class="tool-icon"
          :class="{ 'no-point': noPoint }"
          @click="toNextChapter"
        >
          <div class="iconfont">
            &#58913;
          </div>
        </div>
      </div>
    </div>
    <div class="chapter-bar"></div>
    <div class="chapter" ref="content" :style="chapterTheme">
      <div class="content">
        <div class="top-bar" ref="top"></div>
        <div class="title" ref="title" v-if="show">{{ title }}</div>
        <Pcontent :carray="content" />
        <div class="bottom-bar" ref="bottom"></div>
      </div>
    </div>
  </div>
</template>

<script>
import PopCata from "../components/PopCatalog.vue";
import ReadSettings from "../components/ReadSettings.vue";
import Pcontent from "../components/Content.vue";
import Axios from "axios";
import jump from "../plugins/jump";
import config from "../plugins/config";
export default {
  components: {
    PopCata,
    Pcontent,
    ReadSettings
  },
  created() {
    var config = JSON.parse(localStorage.getItem("config"));
    if (config != null) this.$store.commit("setConfig", config);
  },
  beforeCreate() {
    let config = JSON.parse(localStorage.getItem("config"));
    if (config != null) this.$store.commit("setConfig", config);
  },
  mounted() {
    this.loading = this.$loading({
      target: this.$refs.content,
      lock: true,
      text: "正在获取内容",
      spinner: "el-icon-loading",
      background: "rgba(0,0,0,0)"
    });
    //获取书籍数据
    const that = this;
    let pam = this.$route.query.cpid;
    let bId = this.$route.query.id;
    if (pam == null || bId == null) {
      let bookId = sessionStorage.getItem("bookId");
      var book = JSON.parse(localStorage.getItem(bookId));
      if (book == null) {
        book = {
          bookId: bookId,
          index: 0
        };
        localStorage.setItem(bookId, JSON.stringify(book));
      }
      this.getCatalog(bookId).then(
        res => {
          let catalog = res.data.data;
          // TODO: 需要根据真实章节进行排序
          book.catalog = catalog;
          that.$store.commit("setReadingBook", book);
          var index = that.$store.state.readingBook.index || 0;
          this.getContent(index);
        },
        err => {
          that.loading.close();
          that.$message.error("获取书籍目录失败");
          throw err;
        }
      );
    } else {
      this.getCatalog(bId).then(
        res => {
          let catalog = res.data.data;
          // TODO: 需要根据真实章节进行排序
          let idx = 0;
          for (let i = 0; i < catalog.length; i++) {
            if (catalog[i].id == pam) {
              idx = i;
            }
          }
          sessionStorage.setItem("bookId", bId);
          var book = JSON.parse(localStorage.getItem(bId));
          if (book == null) {
            book = {
              bookId: bId,
              index: idx
            };
            localStorage.setItem(bId, JSON.stringify(book));
          }
          book.index = idx;
          book.catalog = catalog;
          that.$store.commit("setReadingBook", book);
          var index = that.$store.state.readingBook.index || 0;
          this.getContent(index);
        },
        err => {
          that.loading.close();
          that.$message.error("获取书籍目录失败");
          throw err;
        }
      );
    }
  },
  watch: {
    chapterName(to) {
      this.title = to;
    },
    content() {
      this.$store.commit("setContentLoading", false);
    },
    theme(theme) {
      if (theme == 6) {
        this.isNight = true;
      } else {
        this.isNight = false;
      }
    },
    bodyColor(color) {
      this.bodyTheme.background = color;
    },
    chapterColor(color) {
      this.chapterTheme.background = color;
    },
    readWidth(width) {
      this.chapterTheme.width = width;
      let leftToolMargin = -((parseInt(width) + 130) / 2 + 68) + "px";
      let rightToolMargin = -((parseInt(width) + 130) / 2 + 52) + "px";
      this.leftBarTheme.marginLeft = leftToolMargin;
      this.rightBarTheme.marginRight = rightToolMargin;
    },
    popupColor(color) {
      this.leftBarTheme.background = color;
      this.rightBarTheme.background = color;
    },
    readSettingsVisible(visible) {
      if (!visible) {
        let configText = JSON.stringify(this.$store.state.config);
        localStorage.setItem("config", configText);
      }
    }
  },
  data() {
    return {
      title: "",
      content: [],
      noPoint: true,
      isNight: this.$store.state.config.theme == 6,
      bodyTheme: {
        background: config.themes[this.$store.state.config.theme].body
      },
      chapterTheme: {
        background: config.themes[this.$store.state.config.theme].content,
        width: this.$store.state.config.readWidth - 130 + "px"
      },
      leftBarTheme: {
        background: config.themes[this.$store.state.config.theme].popup,
        marginLeft: -(this.$store.state.config.readWidth / 2 + 68) + "px"
      },
      rightBarTheme: {
        background: config.themes[this.$store.state.config.theme].popup,
        marginRight: -(this.$store.state.config.readWidth / 2 + 52) + "px"
      }
    };
  },
  computed: {
    catalog() {
      return this.$store.state.catalog;
    },
    windowHeight() {
      return window.innerHeight;
    },
    contentHeight() {
      return this.$refs.content.offsetHeight;
    },
    popCataVisible: {
      get() {
        return this.$store.state.popCataVisible;
      },
      set(value) {
        this.$store.commit("setPopCataVisible", value);
      }
    },
    readSettingsVisible: {
      get() {
        return this.$store.state.readSettingsVisible;
      },
      set(value) {
        this.$store.commit("setReadSettingsVisible", value);
      }
    },
    config() {
      return this.$store.state.config;
    },
    theme() {
      return this.config.theme;
    },
    bodyColor() {
      return config.themes[this.config.theme].body;
    },
    chapterColor() {
      return config.themes[this.config.theme].content;
    },
    popupColor() {
      return config.themes[this.config.theme].popup;
    },
    readWidth() {
      return this.$store.state.config.readWidth - 130 + "px";
    },
    cataWidth() {
      return this.$store.state.config.readWidth - 33;
    },
    show() {
      return this.$store.state.showContent;
    }
  },
  methods: {
    getCatalog(bookId) {
      return Axios.get(
        "http://119.3.166.63:9000/book/get_chapter_list?from=" +
          encodeURIComponent(bookId)
      );
    },
    getContent(index) {
      //展示进度条
      this.$store.commit("setShowContent", false);
      if (!this.loading.visible) {
        this.loading = this.$loading({
          target: this.$refs.content,
          lock: true,
          text: "正在获取内容",
          spinner: "el-icon-loading",
          background: "rgba(0,0,0,0)"
        });
      }
      //保存阅读进度
      let bookId = sessionStorage.getItem("bookId");
      let book = JSON.parse(localStorage.getItem(bookId));
      book.index = index;
      localStorage.setItem(bookId, JSON.stringify(book));
      this.$store.state.readingBook.index = index;
      let chapterId = this.$store.state.readingBook.catalog[index].id;
      let chapterName = this.$store.state.readingBook.catalog[index].title;
      this.title = chapterName;
      //强制滚回顶层
      jump(this.$refs.top, { duration: 0 });
      let that = this;
      Axios.get(
        "http://119.3.166.63:9000/book/get_book_content?id=" +
          encodeURIComponent(chapterId)
      ).then(
        res => {
          let data = res.data.data.content;
          let dataArray = data.split("\n\n");
          let contentData = "";
          if (dataArray.length > 1) {
            contentData = ("　　" + dataArray[1]).split("\n");
          } else {
            contentData = ("　　" + dataArray[0]).split("\n");
          }
          that.content = contentData;
          this.$store.commit("setContentLoading", true);
          that.loading.close();
          that.noPoint = false;
          that.$store.commit("setShowContent", true);
        },
        err => {
          that.$message.error("获取章节内容失败");
          that.content = "　　获取章节内容失败！";
          throw err;
        }
      );
    },
    toTop() {
      jump(this.$refs.top);
    },
    toBottom() {
      jump(this.$refs.bottom);
    },
    toNextChapter() {
      this.$store.commit("setContentLoading", true);
      let index = this.$store.state.readingBook.index;
      index++;
      this.getContent(index);
    },
    toLastChapter() {
      this.$store.commit("setContentLoading", true);
      let index = this.$store.state.readingBook.index;
      index--;
      this.getContent(index);
    },
    toShelf() {
      this.$router.push("/");
    }
  }
};
</script>

<style lang="stylus" scoped>
>>>.pop-setting {
  margin-left: 68px;
  top: 0;
}

>>>.pop-cata {
  margin-left: 10px;
}

.chapter-wrapper {
  padding: 0 4%;
  flex-direction: column;
  align-items: center;

  >>>.no-point {
    pointer-events: none;
  }

  .tool-bar {
    position: fixed;
    top: 0;
    left: 50%;
    z-index: 100;

    .tools {
      display: flex;
      flex-direction: column;

      .tool-icon {
        font-size: 18px;
        width: 58px;
        height: 48px;
        text-align: center;
        padding-top: 12px;
        cursor: pointer;
        outline: none;

        .iconfont {
          font-family: iconfont;
          width: 16px;
          height: 16px;
          font-size: 16px;
          margin: 0 auto 6px;
        }

        .icon-text {
          font-size: 12px;
        }
      }
    }
  }

  .read-bar {
    position: fixed;
    bottom: 0;
    right: 50%;
    z-index: 100;

    .tools {
      display: flex;
      flex-direction: column;

      .tool-icon {
        font-size: 18px;
        width: 42px;
        height: 31px;
        padding-top: 12px;
        text-align: center;
        align-items: center;
        cursor: pointer;
        outline: none;
        margin-top: -1px;

        .iconfont {
          font-family: iconfont;
          width: 16px;
          height: 16px;
          font-size: 16px;
          margin: 0 auto 6px;
        }
      }
    }
  }

  .chapter-bar {
    .el-breadcrumb {
      .item {
        font-size: 14px;
        color: #606266;
      }
    }
  }

  .chapter {
    font-family: 'Microsoft YaHei', PingFangSC-Regular, HelveticaNeue-Light, 'Helvetica Neue Light', sans-serif;
    text-align: left;
    padding: 0 65px;
    min-height: 100vh;
    width: 670px;
    margin: 0 auto;

    >>>.el-icon-loading {
      font-size: 36px;
      color: #B5B5B5;
    }

    >>>.el-loading-text {
      font-weight: 500;
      color: #B5B5B5;
    }

    .content {
      font-size: 18px;
      line-height: 1.8;
      overflow: hidden;
      font-family: 'Microsoft YaHei', PingFangSC-Regular, HelveticaNeue-Light, 'Helvetica Neue Light', sans-serif;

      .title {
        margin-bottom: 57px;
        font: 24px / 32px PingFangSC-Regular, HelveticaNeue-Light, 'Helvetica Neue Light', 'Microsoft YaHei', sans-serif;
      }

      .bottom-bar, .top-bar {
        height: 64px;
      }
    }
  }
}

.day {
  >>>.popup {
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  }

  >>>.tool-icon {
    border: 1px solid rgba(0, 0, 0, 0.1);
    margin-top: -1px;
    color: #000;

    .icon-text {
      color: rgba(0, 0, 0, 0.4);
    }
  }

  >>>.chapter {
    border: 1px solid #d8d8d8;
    color: #262626;
  }
}

.night {
  >>>.popup {
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.48), 0 0 6px rgba(0, 0, 0, 0.16);
  }

  >>>.tool-icon {
    border: 1px solid #444;
    margin-top: -1px;
    color: #666;

    .icon-text {
      color: #666;
    }
  }

  >>>.chapter {
    border: 1px solid #444;
    color: #666;
  }

  >>>.popper__arrow {
    background: #666;
  }
}
</style>

<template>
  <el-container>
    <div>
      <el-row :gutter="20" style="margin-top: 30px">
        <el-col :span="6">
          <el-card
            shadow="hover"
          >
            <el-image
              src="//pic.allhistory.com/T10aWCB7ET1RCvBVdK.jpeg?height=384&width=512"
              null="null"
              @click="cardClickHandler(0)"
            />
            <h3>新手也能get的大唐流行妆</h3>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card
            shadow="hover"
          >
            <el-image
              src="//pic.allhistory.com/T12zECBCDT1RCvBVdK.jpeg?height=384&width=512"
              null="null"
              @click="cardClickHandler(1)"
            />
            <h3>最后的匈奴王城：被北魏拓跋焘所灭，城池毁于北宋</h3>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card
            shadow="hover"
          >
            <el-image
              src="//pic.allhistory.com/T1FzxCByVT1RCvBVdK.jpeg?height=384&width=512"
              null="null"
              @click="cardClickHandler(2)"
            />
            <h3>比殷墟更早！为什么说良渚遗址是中华五千年文明的实证？</h3>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card
            shadow="hover"
          >
            <el-image
              src="//pic.allhistory.com/T15RACB5bT1RCvBVdK.jpeg?height=384&width=512"
              null="null"
              @click="cardClickHandler(3)"
            />
            <h3>“不落之城”钓鱼城：蒙古铁骑的折鞭之处</h3>
          </el-card>
        </el-col>
      </el-row>
      <el-row :gutter="20" style="margin-top: 30px">
        <el-col :span="8">
          <el-card
            shadow="hover"
          >
            <el-image
              src="//pic.allhistory.com/T1bFJCB7DT1RCvBVdK.jpeg?height=384&width=512"
              null="null"
              @click="cardClickHandler(4)"
            />
            <h3>朱元璋起义——从底层到顶层的逆袭神话</h3>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card
            shadow="hover"
          >
            <el-image
              src="//pic.allhistory.com/T1CNJCBgxT1RCvBVdK.jpeg?height=384&width=512"
              null="null"
              @click="cardClickHandler(5)"
            />
            <h3>商代名相伊尹：到底是社稷之臣，还是篡位之贼？</h3>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card
            shadow="hover"
          >
            <el-image
              src="//pic.allhistory.com/T1waKCBTbj1RCvBVdK.png?height=384&width=512"
              null="null"
              @click="cardClickHandler(6)"
            />
            <h3>冥冥之中同命运：黄公望与他的《富春山居图》</h3>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </el-container>
</template>

<script>
export default {
  name: 'HistoryNews',
  methods: {
    cardClickHandler(val) {
      this.$router.push({
        path: '/news/content',
        query: {
          id: val
        }
      })
    }
  }
}
</script>

<style scoped lang="scss">
.el-image{
  height: 384px;
  width: 512px;
}
</style>

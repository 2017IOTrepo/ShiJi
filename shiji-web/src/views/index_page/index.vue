<template>
  <div class="pg">
    <panel-group @handleSetComponent="handleSetComponent" />
    <transition name="component-fade" mode="in-out">
      <keep-alive>
        <component :is="indexComponent" />
      </keep-alive>
    </transition>
  </div>
</template>

<script>
import PanelGroup from './components/PanelGroup'
import MapChange from './pages/MapChange.vue'
import HistoryData from './pages/HistoryData.vue'
import HistoryNews from './pages/HistoryNews.vue'

const indexComponentList = {
  'map-change': MapChange,
  'history-data': HistoryData,
  'history-news': HistoryNews
}

export default {
  name: 'IndexPage',
  components: {
    PanelGroup
  },
  data: function() {
    return {
      indexComponent: MapChange
    }
  },
  methods: {
    handleSetComponent(type) {
      this.indexComponent = indexComponentList[type]
    }
  }
}
</script>

<style lang="scss">
.pg {
  align-content: center;
}

.component-fade-enter-active,
.component-fade-leave-active {
  transition: opacity 0.3s ease;
}
.component-fade-enter, .component-fade-leave-to
    /* .component-fade-leave-active for below version 2.1.8 */ {
  opacity: 0;
}
</style>

<template>
  <div class="app-container">
    <el-tabs v-model="active" type="border-card">
      <el-tab-pane name="first">
        <span slot="label"><i class="el-icon-s-finance" /> 流速、温度、酸碱度</span>
        <threshold-card
          :title="'流速'"
          :max="8"
          :step="0.5"
          :suggest-min="0"
          :suggest-max="4"
          :threshold="thresholds['streamspeed']"
          :modified="modified"
        />
        <p>&nbsp;</p>
        <threshold-card
          :title="'流速'"
          :min="-5"
          :max="40"
          :step="1"
          :suggest-min="-5"
          :suggest-max="35"
          :threshold="thresholds['temp']"
          :modified="modified"
        />
        <p>&nbsp;</p>
        <threshold-card
          :title="'酸碱度'"
          :min="2"
          :max="12"
          :step="0.5"
          :suggest-min="4"
          :suggest-max="10"
          :threshold="thresholds['ph']"
          :modified="modified"
        />
        <p>&nbsp;</p>
      </el-tab-pane>
      <el-tab-pane label="总磷、氨氮、溶解氧" name="second">
        <threshold-card
          :title="'总磷'"
          :max="1.0"
          :step="0.1"
          :suggest-min="0"
          :suggest-max="0.6"
          :threshold="thresholds['phosphorus']"
          :modified="modified"
        />
        <p>&nbsp;</p>
        <threshold-card
          :title="'氨氮'"
          :max="5.0"
          :step="0.5"
          :suggest-min="0"
          :suggest-max="3.0"
          :threshold="thresholds['nh3']"
          :modified="modified"
        />
        <p>&nbsp;</p>
        <threshold-card
          :title="'溶解氧'"
          :max="30"
          :step="1"
          :suggest-min="1"
          :suggest-max="25"
          :threshold="thresholds['oxy']"
          :modified="modified"
        />
        <p>&nbsp;</p>
      </el-tab-pane>
      <el-tab-pane label="COD、电导率、浊度" name="fourth">
        <threshold-card
          :title="'COD'"
          :max="80"
          :step="5"
          :suggest-min="0"
          :suggest-max="50"
          :threshold="thresholds['cod']"
          :modified="modified"
        />
        <p>&nbsp;</p>
        <threshold-card
          :title="'电导率'"
          :max="150"
          :step="10"
          :suggest-min="0"
          :suggest-max="100"
          :threshold="thresholds['cond']"
          :modified="modified"
        />
        <p>&nbsp;</p>
        <threshold-card
          :title="'浊度'"
          :max="800"
          :step="20"
          :suggest-min="0"
          :suggest-max="600"
          :threshold="thresholds['turb']"
          :modified="modified"
        />
        <p>&nbsp;</p>
      </el-tab-pane>
    </el-tabs>

  </div>
</template>

<script>
import { modifyThreshold, listThresholds } from '@/api/alarm'

import ThresholdCard from './components/ThresholdCard'

export default {
  name: 'EquipThreshold',
  components: { ThresholdCard },
  data() {
    return {
      active: 'first',
      thresholds: {
        'temp': {},
        'streamspeed': {},
        'phosphorus': {},
        'nh3': {},
        'ph': {},
        'cod': {},
        'oxy': {},
        'turb': {},
        'cond': {}
      }
    }
  },
  created: function() {
    listThresholds().then(response => {
      response.data.forEach(t => {
        this.thresholds[t.indicator] = t
      })
    })
  },
  methods: {
    modified: function(i, bottom, top) {
      // TODO
    }
  }
}
</script>

<style lang="scss" scoped>
</style>

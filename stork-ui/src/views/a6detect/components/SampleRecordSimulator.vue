<template>
  <el-dialog title="模拟采样" :visible.sync="dialogVisible" width="40%">
    <el-alert
      title="当前系统为评估版本，为便于使用，提供此功能以手动模拟采样，实际会根据排放情况自动采样，无需人工干预。"
      type="error"
      style="margin-bottom:20px"
    />
    <el-form label-width="80px">
      <el-form-item label="辖区">
        <el-select v-model="newone.region" placeholder="请选择辖区">
          <el-option
            v-for="r in data.regions"
            :key="r.id"
            :label="r.name"
            :value="r.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="水站">
        <el-select v-model="newone.station" placeholder="请选择水站">
          <el-option
            v-for="s in stations"
            :key="s.id"
            :label="s.name"
            :value="s.id"
          />
        </el-select>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="add">生成采样记录</el-button>
      <!-- @click="profileSubmit" -->
    </span>
  </el-dialog>
</template>
<script>
import { listStationByRegionId } from '@/api/station'
import { addSimulatedSampleRecord } from '@/api/detect'

export default {

  name: 'SampleRecordSimulator',

  props: {
    // 父组件传入的信息
    data: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      dialogVisible: false,
      newone: {
        region: null,
        station: null
      },
      todo: {
        complete: null
      },
      stations: []
    }
  },
  computed: {
  },
  watch: {
    'newone.region': function(val) {
      this.newone.station = null
      if (val == null) {
        return
      }
      listStationByRegionId(val).then(response => {
        this.stations = response.data
      })
    }
  },
  methods: {
    open: function(todo) {
      this.dialogVisible = true
      Object.assign(this.todo, todo)
    },
    add: function() {
      addSimulatedSampleRecord(this.newone).then(resp => {
        this.todo.complete(resp.data)
        this.dialogVisible = false
      })
    }
  }

}
</script>
<style lang="scss">
</style>

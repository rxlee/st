<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col style="margin-bottom:12px">
        <el-alert
          v-if="!isRiverChief"
          title="您不是河长，可查看本功能，但不建议执行任何业务。同时，您需要选择一个辖区才能查看采样记录。（河长则无需选择）"
          type="error"
        />
      </el-col>
      <el-col>
        <el-form :inline="true">
          <el-form-item label="辖区">
            <el-select v-model="current.region" placeholder="请选择辖区">
              <el-option
                v-for="r in regions"
                :key="r.id"
                :label="r.name"
                :value="r.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="采样记录类型">
            <el-radio-group v-model="current.type" size="small">
              <el-radio-button label="undetected">未送检</el-radio-button>
              <el-radio-button label="detected" disabled>已送检</el-radio-button>
              <el-radio-button label="all" disabled>全部</el-radio-button>
            </el-radio-group>
          </el-form-item>
          <el-form-item>
            <el-button type="warning" @click="startSimulate">模拟产生一次采样</el-button>
          </el-form-item>
        </el-form>
      </el-col>
      <el-col>
        <el-table :data="records">
          <el-table-column label="辖区" prop="rname" />
          <el-table-column label="水站" prop="sname" />
          <el-table-column label="样品瓶" prop="bottle" width="90" />
          <el-table-column label="采样时间" prop="stime" width="160">
            <template slot-scope="scope">{{ scope.row.stime | fmt }}</template>
          </el-table-column>
          <el-table-column label="操作" width="90">
            <template slot-scope="scope">
              <el-link v-if="scope.row.detectId == null" type="primary" @click="startDispatch(scope)">
                送检
              </el-link>
              <el-tag v-if="scope.row.detectId != null">已送检</el-tag>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>

    <sample-record-simulator ref="simulator" :data="{ regions: regions }" />

    <detect-dispatch-dialog ref="dispatch" />
  </div>
</template>
<script>
import { mapGetters } from 'vuex'
import { formatDate } from '@/api/common'
import SampleRecordSimulator from './components/SampleRecordSimulator'
import DetectDispatchDialog from './components/DetectDispatchDialog'
import { listRegion } from '@/api/region'
import { listUndetectedSampleRecorsInRegion } from '@/api/detect'
export default {
  name: 'TaskDispatch',
  components: { SampleRecordSimulator, DetectDispatchDialog },
  filters: {
    fmt: function(date) {
      return formatDate(new Date(date), 'yyyy-MM-dd hh:mm')
    }
  },
  data() {
    return {
      regions: [],
      current: {
        region: null,
        type: 'undetected'
      },
      records: []
    }
  },
  computed: {
    ...mapGetters([
      'name',
      'roles'
    ]),
    // 当前用户是否河长
    isRiverChief: function() {
      return this.roles.indexOf('river-chief') >= 0
    }
  },
  watch: {
    'current.region': function(val) {
      this.listUndetected()
    }
  },
  created: function() {
    if (!this.isRiverChief) {
      this.$alert('您不是河长，可查看本功能，但不建议执行任何业务。同时，系统无法自动确定您的辖区，您需要选择一个辖区才能查看采样记录。（河长则无需选择）', '提醒', {
        confirmButtonText: '知道了'
      })
    }
    listRegion().then(response => {
      this.regions = response.data
    })
  },
  methods: {
    listUndetected: function() {
      if (this.current.region == null) return
      listUndetectedSampleRecorsInRegion(this.current.region).then(response => {
        this.records = response.data
      })
    },
    addSimulateSampleRecordComplete: function(record) {
      this.listUndetected()
    },
    startSimulate: function() {
      this.$refs.simulator.open({ complete: this.addSimulateSampleRecordComplete })
    },
    startDispatch: function(scope) {
      this.$refs.dispatch.open({ index: scope.$index, data: scope.row, complete: this.completeDispatch })
    },
    completeDispatch: function() {
      this.listUndetected()
    }
  }
}
</script>

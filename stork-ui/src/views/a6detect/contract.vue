<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="24">
        <el-card>
          <div slot="header" class="clearfix">
            <span>检测服务签约</span>
          </div>
          <el-form label-width="240px">
            <el-form-item label="甲方（辖区）">
              <el-select v-model="newone.region" placeholder="请选择辖区">
                <el-option
                  v-for="r in regions"
                  :key="r.id"
                  :label="r.name"
                  :value="r.id"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="乙方（检测机构）">
              <el-select v-model="newone.corp" placeholder="请选择签约机构">
                <el-option
                  v-for="s in corps"
                  :key="s.id"
                  :label="s.name"
                  :value="s.id"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="乙方执行人员（送样）">
              <el-select v-model="newone.delivery" :disabled="newone.corp == null" placeholder="请选择人员">
                <el-option
                  v-for="s in staffs"
                  :key="s.uid"
                  :label="s.uname"
                  :value="s.uid"
                />
              </el-select>
            </el-form-item>

            <el-form-item label="合同起止日期">
              <el-date-picker
                v-model="newone.time"
                type="datetimerange"
                range-separator="至"
                start-placeholder="起始日期"
                end-placeholder="截止日期"
              />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onSubmit">保 存</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
      <el-col v-for="(contract, i) in contracts" :key="contract.id" :span="8">
        <contract-card
          :contract="contract"
          :index="i + 1"
        />
      </el-col>
    </el-row>
  </div>
</template>
<script>
import ContractCard from './components/ContractCard'
import { listRegion } from '@/api/region'
import { listContract, listAllCorps, listDeliveriesInCorp, addContract } from '@/api/detect'
import { mapGetters } from 'vuex'
export default {
  name: 'DetectContract',
  components: { ContractCard },
  data() {
    return {
      newone: {
        corp: null,
        region: null,
        delivery: null,
        from: null,
        to: null,
        time: null
      },
      staffs: [],
      contracts: [],
      corps: [],
      regions: []
    }
  },
  computed: {
    ...mapGetters([
      'uid',
      'name'
    ])
  },
  watch: {
    'newone.region': function(val) {
      listContract(val).then(response => {
        this.contracts = response.data
      })
    },
    'newone.corp': function(val) {
      listDeliveriesInCorp(val).then(response => {
        this.staffs = response.data
      })
    },
    'newone.time': function(val) {
      this.newone.from = val[0].getTime()
      this.newone.to = val[1].getTime()
    }
  },
  created: function() {
    // TODO 取当前用户所在辖区，固定辖区
    listAllCorps().then(response => {
      this.corps = response.data
    })
    listRegion().then(response => {
      this.regions = response.data
    })
  },

  methods: {
    onSubmit: function() {
      // 送样人员未添加
      addContract(this.newone).then(response => {
        listContract(this.newone.region).then(response => {
          this.contracts = response.data
        })
      })
    }
  }
}
</script>

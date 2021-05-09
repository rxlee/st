<template>
  <el-dialog title="样品送检" :visible.sync="dialogVisible" width="40%">
    <el-alert
      title="只需要选择送样人，其他信息为自动填写"
      type="success"
      style="margin-bottom:20px"
    />
    <el-form label-width="120px">
      <el-form-item label="辖区">
        <el-input v-model="todo.data.rname" readonly />
      </el-form-item>
      <el-form-item label="水站">
        <el-input v-model="todo.data.sname" readonly />
      </el-form-item>
      <el-form-item label="样品瓶编号">
        <el-input v-model="todo.data.bottle" readonly />
      </el-form-item>
      <el-form-item label="送样人">
        <el-select v-model="newone.delivery" placeholder="请选择水站">
          <el-option
            v-for="d in deliveries"
            :key="d.uid"
            :label="d.uname + ' / ' + d.uphone"
            :value="d.uid"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="检测服务机构">
        <el-input v-model="newone.corp" readonly />
      </el-form-item>
    </el-form>
    <el-alert
      v-if="!isRiverChief"
      title="您不是河长，所以会看到此提示。不建议您派发任务，确要派发则会记录您为派发人。虽然后续任务跟踪不会受影响，但任务统计会把此任务排除在河长的工作量以外。"
      type="error"
    />
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="add">派发任务</el-button>
      <!-- @click="profileSubmit" -->
    </span>
  </el-dialog>
</template>
<script>
import { mapGetters } from 'vuex'
import { listDeliveriesInRegion, dispatchTask } from '@/api/detect'

export default {

  name: 'DetectDispatchDialog',

  props: {
  },
  data() {
    return {
      dialogVisible: false,
      deliveries: [], // 可选的送样的人列表
      newone: {
        delivery: null,
        corp: null
      },
      todo: {
        index: null,
        data: {
          // region: null,
          // station: null,
        },
        complete: null
      },
      stations: []
    }
  },
  computed: {
    ...mapGetters([
      'uid',
      'roles'
    ]),
    // 当前用户是否河长
    isRiverChief: function() {
      return this.roles.indexOf('river-chief') >= 0
    }
  },
  watch: {
    'newone.delivery': function(val) {
      if (val == null) {
        this.newone.corp = ''
      } else {
        for (let i = 0; i < this.deliveries.length; i++) {
          const d = this.deliveries[i]
          if (d.uid === val) {
            this.newone.corp = d.cname
            return
          }
        }
      }
    }
  },
  created: function() {
  },
  methods: {
    open: function(todo) {
      // 传进来的todo.data是当前选中的采样记录
      Object.assign(this.todo, todo)
      listDeliveriesInRegion(todo.data.rid).then(resp => {
        this.deliveries = resp.data
        console.log(this.deliveries)
      })
      this.dialogVisible = true
    },
    add: function() {
      dispatchTask({
        record: this.todo.data.id,
        delivery: this.newone.delivery,
        dispatcher: this.uid
      }).then(resp => {
        this.todo.complete()
        this.dialogVisible = false
      })
    }
  }

}
</script>
<style lang="scss">
</style>

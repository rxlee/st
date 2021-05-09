<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogVisible"
    width="40%"
  >
    <el-form ref="form" :model="todo.data" :rules="formRules" label-width="96px">
      <el-form-item label="全称" prop="name">
        <el-input v-model="todo.data.name" placeholder="必填，水站完整名称" />
      </el-form-item>
      <el-form-item label="简称" prop="alias">
        <el-input v-model="todo.data.alias" placeholder="必填，便于沟通和显示的简称，12字以内" />
      </el-form-item>
      <el-form-item label="编号" prop="code">
        <el-input v-model="todo.data.code" placeholder="非必填，确定后可改" />
      </el-form-item>
      <el-form-item label="备注说明">
        <el-input v-model="todo.data.memo" type="textarea" :rows="2" placeholder="非必填，附加的备注和说明" />
      </el-form-item>
      <el-divider />
      <el-form-item label="所属辖区">
        <el-select v-model="todo.data.rid" filterable placeholder="非必填，若能确定请选择">
          <el-option :value="null">暂不设定</el-option>
          <el-option
            v-for="r in data.regions"
            :key="r.id"
            :label="r.name"
            :value="r.id"
          >
            {{ r.name }}
          </el-option>
        </el-select>
      </el-form-item>
      <el-divider />
      <el-form-item label="硬件S/N">
        <el-input v-model="todo.data.sn" placeholder="专业信息，非必填" />
      </el-form-item>
      <el-form-item label="主监控S/N">
        <el-input v-model="todo.data.vsn" placeholder="专业信息，非必填" />
      </el-form-item>
      <el-form-item label="主监控TK">
        <el-input v-model="todo.data.vtoken" type="textarea" :rows="2" placeholder="专业信息，非必填" />
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">放 弃</el-button>
      <el-button type="primary" @click="submit">保 存</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { Message } from 'element-ui'
import { addStation, modifyStationProfile } from '@/api/station'

export default {
  name: 'StationProfileDialog',
  props: {
    // 父组件传入的信息
    data: {
      type: Object,
      default: function() { return {} }
    }
  },
  data() {
    return {
      dialogVisible: false,
      todo: { // 表示本组件做什么
        index: null, // 如果是在修改，这表示正在修改表格中的第几行，完成以后上层组件要据此刷新
        data: {}, // 如果实在修改，这表示要修改的数据原值
        complete: null // 新增或修改后的回调函数
      },
      isModifying: false,
      formRules: {
        name: [
          { required: true, message: '请输入全称', trigger: 'blur' },
          { min: 3, max: 40, message: '长度在 3 到 40 个字符', trigger: 'blur' }
        ],
        alias: [
          { required: true, message: '请输入简称', trigger: 'blur' },
          { min: 3, max: 12, message: '长度在 3 到 12 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  computed: {
    title: function() {
      return this.isModifying ? '修改水站' : '新增水站'
    }
  },
  created: function() {
  },
  methods: {
    // 开始新建。给上级组件调用的，本组件内不要调用
    startCreate: function(todo) {
      this.todo.data = { name: '', alias: '', code: '', memo: '', rid: null, sn: null, vsn: '', vtoken: '' }
      this.isModifying = false
      Object.assign(this.todo, todo)
      this.dialogVisible = true
    },
    // 开始修改。给上级组件调用的，本组件内不要调用
    startModify: function(todo) {
      Object.assign(this.todo, todo)
      this.isModifying = true
      this.dialogVisible = true
    },
    submit: function() {
      this.isModifying ? this.modifySubmit() : this.createSubmit()
    },
    createSubmit: function() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          addStation(this.todo.data).then(resp => {
            this.todo.complete(resp.data)
            this.dialogVisible = false
          })
        } else {
          this.formError()
          return false
        }
      })
    },
    modifySubmit: function() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          modifyStationProfile(this.todo.data.id, this.todo.data).then(resp => {
            this.todo.complete(this.todo.index)
            this.dialogVisible = false
          })
        } else {
          this.formError()
          return false
        }
      })
    },
    formError: function() {
      Message({
        message: '错误：信息填写有误，请根据提示修改',
        type: 'warning',
        duration: 5 * 1000
      })
    }
  }
}
</script>

<style lang="scss">
</style>

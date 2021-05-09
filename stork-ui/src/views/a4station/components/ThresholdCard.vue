<template>
  <el-card>
    <div slot="header" class="clearfix">
      <span>
        <i class="el-icon-d-caret" />
        {{ title }}
        （原设定为：{{ origin[0] }} ~ {{ origin[1] }}）
      </span>
      <el-button style="float: right;" type="primary" size="small">保存</el-button>
    </div>
    <div style="padding:20px 60px">
      <el-slider
        v-model="value"
        range
        show-tooltip
        show-stops
        :marks="marks"
        :min="min"
        :max="max"
        :step="step"
      />
    </div>
  </el-card>
</template>

<script>

export default {
  props: {
    title: { type: String, required: true }, // 中文显示标题
    min: { type: Number, default: 0 },
    suggestMin: { type: Number, default: null },
    max: { type: Number, required: true },
    suggestMax: { type: Number, default: null },
    step: { type: Number, required: true },
    threshold: { type: Object, required: true }, // 初始treshold对象
    modified: { type: Function, required: true } // 修改后的回调函数
  },
  data() {
    return {
      value: [0, 0],
      origin: [],
      marks: {}
    }
  },
  watch: {
    threshold: function(t) {
      this.value = [t.bottom, t.top]
      this.origin = [t.bottom, t.top]
    },
    min: function(t) {
      this.updateMarks('min')
    },
    max: function(t) {
      this.updateMarks('max')
    },
    suggestMin: function(s) {
    },
    suggestMax: function(s) {
    }
  },
  created: function() {
    this.updateMarks('create')
  },
  methods: {
    save: function() {
      this.modified(this.threshold.id, this.value)
    },
    updateMarks: function(by) {
      console.log('this.updateMarks()', by)
      if (this.min !== null) this.marks[this.min] = this.min + ''
      if (this.max !== null) this.marks[this.max] = this.max + ''
      if (this.suggestMin !== null) this.marks[this.suggestMin] = { style: { color: '#1989FA' }, label: this.$createElement('strong', '建议下限 ' + this.suggestMin) }
      if (this.suggestMax !== null) this.marks[this.suggestMax] = { style: { color: '#1989FA' }, label: this.$createElement('strong', '建议上限 ' + this.suggestMax) }
    }
  }
}
</script>

<style lang="scss">
</style>

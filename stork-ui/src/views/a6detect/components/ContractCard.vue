<template>
  <el-card style="margin-bottom: 20px">
    <div slot="header" class="clearfix">
      <i class="el-icon-s-shop" /> 合同{{ index }}
    </div>
    <el-form label-width="90px">
      <el-form-item label="机构名称:">
        {{ contract.cname }}
      </el-form-item>
      <el-form-item label="辖区名称:">
        {{ contract.rname }}
      </el-form-item>
      <el-form-item label="开始时间:">
        {{ contract.startTime | formatDate("yyyy-MM-dd hh:mm") }}
      </el-form-item>
      <el-form-item label="结束时间:">
        {{ contract.endTime | formatDate("yyyy-MM-dd hh:mm") }}
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
import { formatDate } from '@/api/common'
export default {

  name: 'ContractCard',
  filters: {
    formatDate: function(date, fmt) {
      return formatDate(new Date(date), fmt)
    }
  },
  props: {
    // 父组件传入的合同信息
    contract: {
      type: Object,
      required: true
    },
    // 父组件传入的水站在列表中的索引，用于修改后回传指明修改的是哪一个
    index: {
      type: Number,
      required: true
    }
  }
}
</script>

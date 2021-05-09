<template>
  <el-card style="margin-bottom:20px;">
    <div slot="header" class="clearfix">
      <span>修改：我的头像照片</span>
    </div>

    <div class="user-profile">
      <div class="box-center">
        <pan-thumb :image="user.avatar" :height="'240px'" :width="'240px'" :hoverable="true">
          <el-button>选择新相片...</el-button>
        </pan-thumb>
      </div>
      <div class="box-center">
        
      </div>
    </div>

    <div class="user-bio">

      <p>&nbsp;</p>
      <div>
        <el-button type="default" size="small">放弃</el-button>
        <el-button type="primary" size="small">保存</el-button>
      </div>
    </div>
  </el-card>
</template>

<script>
import PanThumb from '@/components/PanThumb'

export default {
  components: { PanThumb },
  props: {
    user: {
      type: Object,
      default: () => {
        return {
          name: '',
          telephone: '',
          avatar: '',
          role: ''
        }
      }
    }// user
  }, // props
  computed: {
    roleName: function() {
      switch (this.user.role) {
        case 'admin': return '超级管理员'
        default: return '未知'
      }
    }
  } // computed
}
</script>

<style lang="scss" scoped>
.box-center {
  margin: 0 auto;
  display: table;
}

.text-muted {
  color: #777;
}

.user-profile {
  .user-name {
    font-weight: bold;
  }

  .box-center {
    padding-top: 10px;
  }

  .user-role {
    padding-top: 10px;
    font-weight: 400;
    font-size: 14px;
  }

  .box-social {
    padding-top: 30px;

    .el-table {
      border-top: 1px solid #dfe6ec;
    }
  }

  .user-follow {
    padding-top: 20px;
  }
}

.user-bio {
  margin-top: 20px;
  color: #606266;

  span {
    padding-left: 4px;
  }

  .user-bio-section {
    font-size: 14px;
    padding: 15px 0;

    .user-bio-section-header {
      border-bottom: 1px solid #dfe6ec;
      padding-bottom: 10px;
      margin-bottom: 10px;
      font-weight: bold;
    }
  }
}
</style>

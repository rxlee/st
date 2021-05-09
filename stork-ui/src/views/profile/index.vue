<template>
  <div class="app-container">
    <div v-if="user">
      <el-row :gutter="20">

        <el-col :span="8" :xs="24">
          <el-card style="margin-bottom:20px;">
            <div slot="header" class="clearfix">
              <span>我的基本信息</span>
            </div>

            <div class="user-profile">
              <div class="box-center">
                <pan-thumb :image="user.avatar" :height="'160px'" :width="'160px'" :hoverable="false">
                  <div>Hello</div>
                </pan-thumb>
              </div>
            </div>
            <div class="user-profile">
              <div class="">
                <div style="width:100px;height:100px">
                  <div :style="{backgroundImage: `url(${user.avatar})`}" class="pan-thumb" />
                </div>
              </div>
            </div>

            <div class="user-bio">
              <div class="user-education user-bio-section">
                <div class="user-bio-section-header"><svg-icon icon-class="education" /><span>姓名</span></div>
                <div class="user-bio-section-body">
                  <div class="text-muted">
                    {{ user.name }}
                  </div>
                </div>
              </div>
              <div class="user-education user-bio-section">
                <div class="user-bio-section-header"><svg-icon icon-class="education" /><span>手机号</span></div>
                <div class="user-bio-section-body">
                  <div class="text-muted">
                    {{ user.role === 'admin' ? '-' : user.telephone }}
                  </div>
                </div>
              </div>
              <div class="user-education user-bio-section">
                <div class="user-bio-section-header"><svg-icon icon-class="education" /><span>角色</span></div>
                <div class="user-bio-section-body">
                  <div class="text-muted">
                    {{ roleName }}（{{ user.role }}）
                  </div>
                </div>
              </div>

              <p>&nbsp;</p>
              <div>
                <el-button type="primary" size="small" :disabled="modifying == 'telephone'" @click="modify('telephone')">修改手机号</el-button>
                <el-button type="primary" size="small" :disabled="modifying == 'avatar'" @click="modify('avatar')">修改相片</el-button>
                <el-button type="primary" size="small" :disabled="modifying == 'password'" @click="modify('password')">修改密码</el-button>
              </div>

            </div>
          </el-card>
        </el-col>

        <el-col v-show="modifying == 'password'" :span="10" :xs="24">
          <modify-password :user="user" />
        </el-col>
        <el-col v-show="modifying == 'avatar'" :span="10" :xs="24">
          <modify-avatar :user="user" />
        </el-col>
        <el-col v-show="modifying == 'telephone'" :span="10" :xs="24">
          <modify-telephone :user="user" />
        </el-col>

      </el-row>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import PanThumb from '@/components/PanThumb'
import ModifyPassword from './components/ModifyPassword'
import ModifyAvatar from './components/ModifyAvatar'
import ModifyTelephone from './components/ModifyTelephone'

export default {
  name: 'Profile',
  components: { PanThumb, ModifyPassword, ModifyTelephone, ModifyAvatar },
  data() {
    return {
      user: {},
      modifying: ''
    }
  },
  computed: {
    ...mapGetters([
      'name',
      'avatar',
      'roles',
      'telephone'
    ]),
    roleName: function() {
      switch (this.user.role) {
        case 'admin': return '超级管理员'
        default: return '未知'
      }
    }
  },
  created() {
    this.getUser()
  },
  methods: {
    getUser() {
      this.user = {
        name: this.name,
        role: this.roles[0],
        telephone: this.telephone,
        avatar: this.avatar
      }
    },
    modify(which) {
      this.modifying = which
    }
  }
}
</script>


<style lang="scss" scoped>
.pan-thumb {
  width: 100%;
  height: 100%;
  background-position: center center;
  background-size: cover;
  border-radius: 50%;
  overflow: hidden;
}
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

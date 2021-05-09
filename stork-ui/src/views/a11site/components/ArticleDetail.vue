<template>
  <div class="createPost-container">
    <el-form ref="postForm" :model="postForm" :rules="rules" class="form-container">

      <sticky :z-index="10" :class-name="'sub-navbar '+postForm.status">
        <el-button v-loading="loading" style="margin-left: 10px;" type="success" @click="submitForm">
          发布
        </el-button>
      </sticky>

      <div class="createPost-main-container">
        <el-row :gutter="40">
          <el-col :span="16">
            <el-form-item style="margin-bottom: 20px;" prop="title">
              <MDinput v-model="postForm.title" :maxlength="25" name="name" required>
                标题（不超过25个字）
              </MDinput>
              <span v-if="postForm.title.length < 26 && postForm.title.length > 0" style="color:#666">还能输入{{25 - postForm.title.length}} 个字</span>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <div style="margin-top: 35px">
              <el-radio-group v-model="postForm.category" size="small">
                <el-radio-button label="notice">公告</el-radio-button>
                <el-radio-button label="praise">表扬</el-radio-button>
                <el-radio-button label="punish">处罚</el-radio-button>
              </el-radio-group>
            </div>
          </el-col>
        </el-row>

        <el-form-item style="margin-bottom: 30px;" prop="content">
          <textarea v-model="postForm.content" style="width:100%;min-height:400px" required />
          <span v-if="postForm.content.length < 1001 && postForm.content.length > 0" style="color:#666">还能输入{{ 1000 - postForm.content.length}} 个字</span>
        </el-form-item>

      </div>
    </el-form>
  </div>
</template>

<script>
import Sticky from '@/components/Sticky' // 粘性header组件
import MDinput from '@/components/MDinput'
import { fetchArticle, addArticle } from '@/api/article'

const defaultForm = {
  title: '', // 文章题目
  content: '', // 文章内容
  image_uri: '', // 文章图片
  category: 'notice',
  id: undefined
}

export default {
  name: 'ArticleDetail',
  components: { Sticky, MDinput },
  props: {
    isEdit: {
      type: Boolean,
      default: false
    }
  },
  data() {
    const validateTitle = (rule, value, callback) => {
      if (value.length > 25) {
        callback(new Error('标题最多25个字'))
      } else if (value.length < 1) {
        callback(new Error('标题不可留空'))
      } else {
        callback()
      }
    }
    const validateContent = (rule, value, callback) => {
      console.log('content=', value)
      if (value.length > 1000) {
        callback(new Error('正文最多1000个字'))
      } else if (value === null || value.length < 1) {
        callback(new Error('正文不可留空'))
      } else {
        callback()
      }
    }
    return {
      postForm: Object.assign({}, defaultForm),
      loading: false,
      rules: {
        title: [{ validator: validateTitle }],
        content: [{ validator: validateContent }]
      }
    }
  },
  created() {
    if (this.isEdit) {
      const id = this.$route.params && this.$route.params.id
      this.fetchData(id)
    }
  },
  methods: {
    fetchData(id) {
      fetchArticle(id).then(response => {
        this.postForm = response.data
        this.postForm.title += `   Article Id:${this.postForm.id}`
      }).catch(err => {
        console.log(err)
      })
    },
    submitForm() {
      this.$refs.postForm.validate(valid => {
        if (valid) {
          this.loading = true
          addArticle(this.postForm).then(response => {
            console.log(response.data)
          })
          this.$notify({
            title: '成功',
            message: '发布文章成功',
            type: 'success',
            duration: 3000
          })
          this.loading = false
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
@import "~@/styles/mixin.scss";

.createPost-container {
  position: relative;

  .createPost-main-container {
    padding: 0 45px 20px 50px;
  }
}

</style>

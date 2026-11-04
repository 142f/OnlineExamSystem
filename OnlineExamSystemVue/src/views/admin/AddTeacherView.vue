<template>
  <div class="oes-page">
    <PageHeader title="添加教师" subtitle="创建教师账号，教师可进行组卷、改卷与成绩管理">
      <template #actions>
        <el-button icon="el-icon-back" @click="back()">返回列表</el-button>
      </template>
    </PageHeader>

    <div class="oes-card form-card">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row :gutter="16">
          <el-col :span="12"><el-form-item label="姓名" prop="teacherName"><el-input v-model="form.teacherName" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="学院" prop="institute"><el-input v-model="form.institute" /></el-form-item></el-col>
          <el-col :span="12">
            <el-form-item label="性别" prop="sex">
              <el-radio-group v-model="form.sex">
                <el-radio label="男">男</el-radio>
                <el-radio label="女">女</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12"><el-form-item label="联系方式" prop="tel"><el-input v-model="form.tel" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="身份证号" prop="cardId"><el-input v-model="form.cardId" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="职称" prop="type"><el-input v-model="form.type" placeholder="如：讲师" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="初始密码" prop="pwd"><el-input v-model="form.pwd" type="password" show-password /></el-form-item></el-col>
        </el-row>

        <div class="form-actions">
          <el-button @click="back()">取 消</el-button>
          <el-button type="primary" :loading="submitting" @click="onSubmit()">立即创建</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import PageHeader from '@/components/common/PageHeader'

export default {
  components: { PageHeader },
  data() {
    return {
      submitting: false,
      form: {
        teacherName: '',
        institute: '',
        sex: '男',
        tel: '',
        pwd: '',
        cardId: '',
        type: ''
      },
      rules: {
        teacherName: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
        institute: [{ required: true, message: '请输入学院', trigger: 'blur' }],
        tel: [{ required: true, message: '请输入联系方式', trigger: 'blur' }],
        pwd: [{ required: true, message: '请输入初始密码', trigger: 'blur' }]
      }
    }
  },
  methods: {
    onSubmit() {
      this.$refs.form.validate(valid => {
        if (!valid) return
        this.submitting = true
        this.$axios({ url: '/api/teacher', method: 'post', data: { ...this.form } })
          .then(res => {
            if (res.data.code == 200) {
              this.$message.success('教师创建成功')
              this.$router.push({ path: '/teacherManage' })
            } else {
              this.$message.error(res.data.message || '创建失败')
            }
          })
          .catch(() => this.$message.error('创建失败'))
          .finally(() => {
            this.submitting = false
          })
      })
    },
    back() {
      this.$router.push({ path: '/teacherManage' })
    }
  }
}
</script>

<style scoped>
.form-card {
  max-width: 900px;
}
.form-actions {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
  padding-top: 8px;
}
</style>

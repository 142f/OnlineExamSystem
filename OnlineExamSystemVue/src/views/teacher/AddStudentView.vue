<template>
  <div class="oes-page">
    <PageHeader title="添加学生" subtitle="创建学生账号，初始密码用于学生首次登录">
      <template #actions>
        <el-button icon="el-icon-back" @click="back()">返回列表</el-button>
      </template>
    </PageHeader>

    <div class="oes-card form-card">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="姓名" prop="studentName"><el-input v-model="form.studentName" placeholder="请输入姓名" /></el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别" prop="sex">
              <el-radio-group v-model="form.sex">
                <el-radio label="男">男</el-radio>
                <el-radio label="女">女</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="学院" prop="institute"><el-input v-model="form.institute" placeholder="如：软件工程学院" /></el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="专业" prop="major"><el-input v-model="form.major" placeholder="如：软件工程" /></el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="年级" prop="grade"><el-input v-model="form.grade" placeholder="如：2024" /></el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="班级" prop="clazz"><el-input v-model="form.clazz" placeholder="如：1" /></el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系方式" prop="tel"><el-input v-model="form.tel" placeholder="手机号" /></el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="身份证号" prop="cardId"><el-input v-model="form.cardId" placeholder="18 位身份证号" /></el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱" prop="email"><el-input v-model="form.email" placeholder="用于接收通知" /></el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="初始密码" prop="pwd"><el-input v-model="form.pwd" type="password" show-password placeholder="学生登录密码" /></el-form-item>
          </el-col>
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
        studentName: '',
        grade: '',
        major: '',
        clazz: '',
        institute: '',
        tel: '',
        email: '',
        pwd: '',
        cardId: '',
        sex: '男',
        role: 2
      },
      rules: {
        studentName: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
        institute: [{ required: true, message: '请输入学院', trigger: 'blur' }],
        major: [{ required: true, message: '请输入专业', trigger: 'blur' }],
        grade: [{ required: true, message: '请输入年级', trigger: 'blur' }],
        clazz: [{ required: true, message: '请输入班级', trigger: 'blur' }],
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
        this.$axios({ url: '/api/student', method: 'post', data: { ...this.form } })
          .then(res => {
            if (res.data.code == 200) {
              this.$message.success('学生创建成功')
              this.$router.push({ path: '/studentManage' })
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
      this.$router.push({ path: '/studentManage' })
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

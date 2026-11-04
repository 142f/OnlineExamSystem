<template>
  <div class="oes-page">
    <PageHeader title="修改密码" subtitle="修改后请使用新密码重新登录" />

    <div class="oes-card pwd-card">
      <el-form :model="ruleForm2" status-icon :rules="rules2" ref="ruleForm2" label-width="100px">
        <el-form-item label="新密码" prop="pass">
          <el-input type="password" v-model="ruleForm2.pass" autocomplete="off" show-password placeholder="请输入新密码" />
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPass">
          <el-input type="password" v-model="ruleForm2.checkPass" autocomplete="off" show-password placeholder="请再次输入新密码" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="submitting" @click="submitForm('ruleForm2')">提 交</el-button>
          <el-button @click="resetForm('ruleForm2')">重 置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import PageHeader from '@/components/common/PageHeader'

export default {
  components: { PageHeader },
  data() {
    const validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'))
      } else {
        if (this.ruleForm2.checkPass !== '') {
          this.$refs.ruleForm2.validateField('checkPass')
        }
        callback()
      }
    }
    const validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.ruleForm2.pass) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      submitting: false,
      ruleForm2: { pass: '', checkPass: '' },
      rules2: {
        pass: [{ validator: validatePass, trigger: 'blur' }],
        checkPass: [{ validator: validatePass2, trigger: 'blur' }]
      }
    }
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (!valid) return
        const studentId = this.$cookies.get('cid')
        this.submitting = true
        this.$axios({ url: '/api/studentPWD', method: 'put', data: { pwd: this.ruleForm2.pass, studentId } })
          .then(res => {
            if (res.data && res.data.code == 200) {
              this.$message.success('密码修改成功，请重新登录')
              this.resetForm(formName)
            } else {
              this.$message.error((res.data && res.data.message) || '修改失败')
            }
          })
          .catch(() => this.$message.error('修改失败'))
          .finally(() => {
            this.submitting = false
          })
      })
    },
    resetForm(formName) {
      this.$refs[formName].resetFields()
    }
  }
}
</script>

<style scoped>
.pwd-card {
  max-width: 520px;
}
</style>

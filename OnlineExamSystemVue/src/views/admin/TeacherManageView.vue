<template>
  <div class="oes-page">
    <PageHeader title="教师管理" subtitle="维护教师账号、所属学院与职称信息">
      <template #actions>
        <el-button type="primary" icon="el-icon-plus" @click="$router.push('/addTeacher')">添加教师</el-button>
      </template>
    </PageHeader>

    <div class="oes-table-card">
      <div class="oes-toolbar">
        <el-input v-model="keyword" placeholder="按姓名 / 学院筛选" prefix-icon="el-icon-search" clearable />
        <div class="oes-toolbar__spacer"></div>
        <el-button icon="el-icon-refresh" @click="getTeacherInfo()">刷新</el-button>
      </div>

      <el-table v-loading="loading" :data="filteredRecords" size="medium">
        <el-table-column prop="teacherId" label="工号" width="120" />
        <el-table-column prop="teacherName" label="姓名" width="120">
          <template slot-scope="scope">
            <span class="cell-strong">{{ scope.row.teacherName }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="institute" label="所属学院" min-width="160" show-overflow-tooltip />
        <el-table-column prop="sex" label="性别" width="80" align="center" />
        <el-table-column prop="tel" label="联系电话" width="130" />
        <el-table-column prop="email" label="邮箱" min-width="180" show-overflow-tooltip />
        <el-table-column prop="type" label="职称" width="120">
          <template slot-scope="scope">
            <span class="oes-tag">{{ scope.row.type }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="170" fixed="right">
          <template slot-scope="scope">
            <el-button type="text" icon="el-icon-edit" @click="checkGrade(scope.row.teacherId)">编辑</el-button>
            <el-button type="text" class="danger-text" icon="el-icon-delete" @click="deleteById(scope.row.teacherId)">删除</el-button>
          </template>
        </el-table-column>
        <template slot="empty">
          <EmptyState text="暂无教师数据" />
        </template>
      </el-table>

      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pagination.current"
        :page-sizes="[6, 10]"
        :page-size="pagination.size"
        layout="total, sizes, prev, pager, next, jumper"
        :total="pagination.total">
      </el-pagination>
    </div>

    <el-dialog title="编辑教师信息" :visible.sync="dialogVisible" width="520px" :before-close="handleClose">
      <el-form ref="form" :model="form" label-width="90px">
        <el-form-item label="姓名"><el-input v-model="form.teacherName" /></el-form-item>
        <el-form-item label="学院"><el-input v-model="form.institute" /></el-form-item>
        <el-form-item label="性别"><el-input v-model="form.sex" /></el-form-item>
        <el-form-item label="电话号码"><el-input v-model="form.tel" /></el-form-item>
        <el-form-item label="密码"><el-input v-model="form.pwd" placeholder="留空表示不修改" /></el-form-item>
        <el-form-item label="身份证号"><el-input v-model="form.cardId" /></el-form-item>
        <el-form-item label="职称"><el-input v-model="form.type" /></el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" :loading="submitting" @click="submit()">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import PageHeader from '@/components/common/PageHeader'
import EmptyState from '@/components/common/EmptyState'

export default {
  components: { PageHeader, EmptyState },
  data() {
    return {
      loading: false,
      submitting: false,
      keyword: '',
      pagination: {
        current: 1,
        total: null,
        size: 6,
        records: []
      },
      dialogVisible: false,
      form: {}
    }
  },
  computed: {
    filteredRecords() {
      const list = this.pagination.records || []
      const kw = this.keyword.trim()
      if (!kw) return list
      return list.filter(t =>
        (t.teacherName || '').indexOf(kw) >= 0 || (t.institute || '').indexOf(kw) >= 0
      )
    }
  },
  created() {
    this.getTeacherInfo()
  },
  methods: {
    getTeacherInfo() {
      this.loading = true
      this.$axios(`/api/teachers/${this.pagination.current}/${this.pagination.size}`)
        .then(res => {
          this.pagination = res.data.data
        })
        .catch(() => {})
        .finally(() => {
          this.loading = false
        })
    },
    handleSizeChange(val) {
      this.pagination.size = val
      this.getTeacherInfo()
    },
    handleCurrentChange(val) {
      this.pagination.current = val
      this.getTeacherInfo()
    },
    checkGrade(teacherId) {
      this.dialogVisible = true
      this.$axios(`/api/teacher/${teacherId}`).then(res => {
        this.form = res.data.data
      })
    },
    deleteById(teacherId) {
      this.$confirm('确定删除当前教师吗？删除后无法恢复', 'Warning', {
        confirmButtonText: '确定删除',
        cancelButtonText: '算了，留着吧',
        type: 'warning'
      })
        .then(() => {
          this.$axios({ url: `/api/teacher/${teacherId}`, method: 'delete' }).then(() => {
            this.$message.success('删除成功')
            this.getTeacherInfo()
          })
        })
        .catch(() => {})
    },
    submit() {
      this.submitting = true
      this.$axios({ url: '/api/teacher', method: 'put', data: { ...this.form } })
        .then(res => {
          if (res.data.code == 200) {
            this.$message.success('修改成功')
            this.dialogVisible = false
            this.getTeacherInfo()
          } else {
            this.$message.error(res.data.message || '修改失败')
          }
        })
        .catch(() => this.$message.error('修改失败'))
        .finally(() => {
          this.submitting = false
        })
    },
    handleClose() {
      this.dialogVisible = false
    }
  }
}
</script>

<style scoped>
.cell-strong {
  font-weight: 600;
  color: var(--oes-text);
}
.danger-text {
  color: var(--oes-danger);
}
</style>

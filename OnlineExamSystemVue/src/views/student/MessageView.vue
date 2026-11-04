<template>
  <div class="oes-page">
    <PageHeader title="交流区" subtitle="发布留言、回复同学，教师与学生均可参与讨论" />

    <div class="msg-layout">
      <div class="oes-card msg-compose">
        <h3>发布留言</h3>
        <el-input v-model="title" placeholder="留言标题" clearable maxlength="40" show-word-limit />
        <el-input v-model="content" type="textarea" :rows="5" placeholder="留言内容（不少于 5 个字）" maxlength="300" show-word-limit />
        <el-button type="primary" icon="el-icon-s-promotion" :loading="submitting" @click="submit()">提交留言</el-button>
        <p class="msg-compose__tip">文明发言，留言会公开展示给所有用户</p>
      </div>

      <div class="msg-list" v-loading="loading">
        <div class="oes-card msg-item" v-for="(data, index) in msg" :key="index"
             @mouseenter="enter(index)" @mouseleave="leave(index)">
          <div class="msg-item__head">
            <span class="msg-item__title">
              <i class="el-icon-chat-dot-square"></i> {{ data.title }}
            </span>
            <span class="msg-item__time">{{ data.time }}</span>
          </div>
          <p class="msg-item__content">{{ data.content }}</p>

          <div class="msg-item__replies" v-if="data.replays && data.replays.length">
            <div class="msg-reply" v-for="(replayData, index2) in data.replays" :key="index2">
              <i class="el-icon-chat-line-round"></i>
              <span>{{ replayData.replay }}</span>
              <span class="msg-reply__time">{{ replayData.replayTime }}</span>
            </div>
          </div>

          <div class="msg-item__actions">
            <el-button type="text" icon="el-icon-chat-dot-round" @click="replay(data.id)" v-if="flag && index == current">回复</el-button>
            <span class="msg-item__count">{{ (data.replays || []).length }} 条回复</span>
          </div>
        </div>

        <EmptyState v-if="!loading && msg.length === 0" text="还没有留言，来发布第一条吧" icon="el-icon-chat-dot-square" />

        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
          :current-page="pagination.current" :page-sizes="[4, 6, 8, 10]" :page-size="pagination.size"
          layout="total, sizes, prev, pager, next, jumper" :total="pagination.total">
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
import PageHeader from '@/components/common/PageHeader'
import EmptyState from '@/components/common/EmptyState'

export default {
  components: { PageHeader, EmptyState },
  data() {
    return {
      flag: false,
      current: 0,
      title: '',
      content: '',
      submitting: false,
      loading: false,
      pagination: { current: 1, total: null, size: 4 },
      msg: []
    }
  },
  created() {
    this.getMsg()
  },
  methods: {
    getMsg() {
      this.loading = true
      this.$axios(`/api/messages/${this.pagination.current}/${this.pagination.size}`)
        .then(res => {
          if (res.data.code == 200) {
            this.msg = res.data.data.records
            this.pagination = res.data.data
          }
        })
        .catch(() => {})
        .finally(() => {
          this.loading = false
        })
    },
    handleSizeChange(val) {
      this.pagination.size = val
      this.getMsg()
    },
    handleCurrentChange(val) {
      this.pagination.current = val
      this.getMsg()
    },
    enter(index) {
      this.flag = true
      this.current = index
    },
    leave() {
      this.flag = false
    },
    submit() {
      if (!this.title || !this.content) {
        this.$message.error('留言标题或内容不能为空')
        return
      }
      this.submitting = true
      this.$axios({
        url: '/api/message',
        method: 'post',
        data: { title: this.title, content: this.content, time: new Date() }
      })
        .then(res => {
          if (res.data.code == 200) {
            this.$message.success('留言成功')
            this.title = ''
            this.content = ''
          }
        })
        .catch(() => this.$message.error('留言失败'))
        .finally(() => {
          this.submitting = false
          this.getMsg()
        })
    },
    replay(messageId) {
      this.$prompt('回复留言', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern: /^[\s\S]*.*[^\s][\s\S]*$/,
        inputErrorMessage: '回复不能为空'
      })
        .then(({ value }) => {
          this.$axios({
            url: '/api/replay',
            method: 'post',
            data: { replay: value, replayTime: new Date(), messageId: messageId }
          }).then(() => {
            this.$message.success('回复成功')
            this.getMsg()
          })
        })
        .catch(() => {})
    }
  }
}
</script>

<style scoped>
.msg-layout {
  display: grid;
  grid-template-columns: 360px minmax(0, 1fr);
  gap: 16px;
  align-items: start;
}

.msg-compose {
  display: flex;
  flex-direction: column;
  gap: 12px;
  position: sticky;
  top: 16px;
}

.msg-compose h3 {
  margin: 0 0 4px;
  font-size: 15px;
  color: var(--oes-text);
}

.msg-compose__tip {
  margin: 0;
  font-size: 12px;
  color: var(--oes-text-muted);
}

.msg-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
  min-height: 200px;
}

.msg-item {
  padding: 18px 20px;
}

.msg-item__head {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 12px;
}

.msg-item__title {
  font-size: 15px;
  font-weight: 600;
  color: var(--oes-text);
}

.msg-item__time {
  font-size: 12px;
  color: var(--oes-text-muted);
  white-space: nowrap;
}

.msg-item__content {
  margin: 10px 0;
  font-size: 14px;
  line-height: 1.7;
  color: var(--oes-text-secondary);
  white-space: pre-wrap;
}

.msg-item__replies {
  display: flex;
  flex-direction: column;
  gap: 6px;
  margin: 10px 0;
  padding: 10px 12px;
  background: #f8fafd;
  border-radius: var(--oes-radius-sm);
}

.msg-reply {
  display: flex;
  align-items: baseline;
  gap: 8px;
  font-size: 13px;
  color: var(--oes-text-secondary);
}

.msg-reply i {
  color: var(--oes-primary);
}

.msg-reply__time {
  margin-left: auto;
  font-size: 12px;
  color: var(--oes-text-muted);
}

.msg-item__actions {
  display: flex;
  align-items: center;
  justify-content: space-between;
  min-height: 24px;
}

.msg-item__count {
  font-size: 12px;
  color: var(--oes-text-muted);
}

@media (max-width: 1100px) {
  .msg-layout {
    grid-template-columns: 1fr;
  }
  .msg-compose {
    position: static;
  }
}
</style>

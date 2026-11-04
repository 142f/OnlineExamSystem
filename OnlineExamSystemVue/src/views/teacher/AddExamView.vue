<template>
  <div class="oes-page">
    <PageHeader title="添加作业" subtitle="选择试卷并配置作业时间、范围与须知">
      <template #actions>
        <el-button icon="el-icon-back" @click="$router.push('/selectExam')">返回列表</el-button>
      </template>
    </PageHeader>

    <div class="oes-card form-card">
      <el-form ref="form" :model="form" label-width="100px">
        <el-row :gutter="16">
          <el-col :span="12"><el-form-item label="作业名称"><el-input v-model="form.source" placeholder="如：计算机网络" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="作业类型"><el-input v-model="form.type" placeholder="如：期末考试" /></el-form-item></el-col>
          <el-col :span="24"><el-form-item label="介绍"><el-input v-model="form.description" placeholder="一句话说明本次作业" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="所属学院"><el-input v-model="form.institute" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="所属专业"><el-input v-model="form.major" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="年级"><el-input v-model="form.grade" /></el-form-item></el-col>
          <el-col :span="12">
            <el-form-item label="提交日期">
              <el-date-picker v-model="form.examDate" value-format="yyyy-MM-dd" placeholder="选择日期" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12"><el-form-item label="持续时间"><el-input v-model="form.totalTime" placeholder="单位：分钟" /></el-form-item></el-col>
          <el-col :span="24"><el-form-item label="作业提示"><el-input type="textarea" :rows="3" v-model="form.tips" placeholder="考生须知，如：禁止切屏" /></el-form-item></el-col>
        </el-row>

        <div class="form-actions">
          <el-button @click="cancel()">取 消</el-button>
          <el-button type="primary" @click="onSubmit()">立即创建</el-button>
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
            form: {
                //表单数据初始化
                source: null,
                description: null,
                institute: null,
                major: null,
                grade: null,
                examDate: null,
                totalTime: null,
                totalScore: null,
                type: null,
                tips: null,
                paperId: null,
            },
        };
    },
    methods: {
        formatTime(date) {
            //日期格式化
            let year = date.getFullYear();
            let month =
                date.getMonth() + 1 < 10
                    ? "0" + (date.getMonth() + 1)
                    : date.getMonth() + 1;
            let day =
                date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
            let hours =
                date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
            let minutes =
                date.getMinutes() < 10
                    ? "0" + date.getMinutes()
                    : date.getMinutes();
            let seconds =
                date.getSeconds() < 10
                    ? "0" + date.getSeconds()
                    : date.getSeconds();
            // 拼接
            return (
                year +
                "-" +
                month +
                "-" +
                day +
                " " +
                hours +
                ":" +
                minutes +
                ":" +
                seconds
            );
        },
        onSubmit() {
            if (this.form.source == null || this.form.source == "") {
                this.$message({
                    message: "作业名称不能为空",
                    type: "error",
                });
                return;
            }
            if (this.form.description == null || this.form.description == "") {
                this.$message({
                    message: "介绍不能为空",
                    type: "error",
                });
                return;
            }
            if (this.form.institute == null || this.form.institute == "") {
                this.$message({
                    message: "所属学院不能为空",
                    type: "error",
                });
                return;
            }
            if (this.form.major == null || this.form.major == "") {
                this.$message({
                    message: "所属专业不能为空",
                    type: "error",
                });
                return;
            }
            if (this.form.grade == null || this.form.grade == "") {
                this.$message({
                    message: "年级不能为空",
                    type: "error",
                });
                return;
            }
            if (this.form.examDate == null || this.form.examDate == "") {
                this.$message({
                    message: "作业提交日期不能为空",
                    type: "error",
                });
                return;
            }
            if (this.form.totalTime == null || this.form.totalTime == "") {
                this.$message({
                    message: "持续时间不能为空",
                    type: "error",
                });
                return;
            }
            if (this.form.type == null || this.form.type == "") {
                this.$message({
                    message: "作业类型不能为空",
                    type: "error",
                });
                return;
            }
            if (this.form.tips == null || this.form.tips == "") {
                this.$message({
                    message: "作业提示不能为空",
                    type: "error",
                });
                return;
            }
            this.$axios(`/api/examManagePaperId`).then((res) => {
                console.log(res)
                if(res.data.data == null || res.data.data.paperId == null) {
                    res.data.data = {
                        paperId : 1000
                    }
                }
                this.form.paperId = res.data.data.paperId + 1; //实现paperId自增1
                this.$axios({
                    url: "/api/exam",
                    method: "post",
                    data: {
                        ...this.form,
                    },
                }).then((res) => {
                    if (res.data.code == 200) {
                        this.$message({
                            message: "数据添加成功",
                            type: "success",
                        });
                        this.$router.push({ path: "/selectExam" });
                    }
                });
            });
        },
        cancel() {
            //取消按钮
            this.form = {};
        },
    },
};
</script>
<style scoped>
.form-card {
  max-width: 960px;
}
.form-actions {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
  padding-top: 8px;
}
</style>

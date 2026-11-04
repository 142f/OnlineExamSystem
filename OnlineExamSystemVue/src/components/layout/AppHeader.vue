<!-- 顶部信息栏 -->
<template>
    <header class="oes-header">
        <el-dialog :append-to-body="true" title="修改密码" :visible.sync="dialogVisible" width="420px">
            <el-form status-icon ref="ruleForm2" label-width="90px" class="pwd-form">
                <el-form-item label="旧密码" prop="pass">
                    <el-input type="password" v-model="oldPsw" autocomplete="off" placeholder="请输入旧密码"></el-input>
                </el-form-item>
                <el-form-item label="新密码" prop="pass">
                    <el-input type="password" v-model="newPsw" autocomplete="off" placeholder="请输入新密码"></el-input>
                </el-form-item>
                <el-form-item label="确认新密码" prop="checkPass">
                    <el-input type="password" v-model="confirmNewPsw" autocomplete="off" placeholder="请再次输入新密码"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="resetPsw">确 定</el-button>
            </span>
        </el-dialog>

        <div class="oes-header__brand">
            <i class="iconfont icon-r-setting oes-header__logo"></i>
            <span class="oes-header__title">作业互评系统后台</span>
        </div>

        <div class="oes-header__right">
            <div class="oes-header__user" @click.stop="showSetting()">
                <span class="oes-header__name">{{ user.userName }}</span>
                <img src="@/assets/img/userimg.png" class="oes-header__avatar" ref="img" />
                <transition name="fade">
                    <div class="oes-user-menu" ref="out" v-show="login_flag">
                        <div class="oes-user-menu__item" v-if="role == 0" @click="dialogVisible = true">
                            <i class="el-icon-lock"></i> 修改密码
                        </div>
                        <div class="oes-user-menu__item oes-user-menu__item--danger" @click="exit()">
                            <i class="el-icon-switch-button"></i> 退出登录
                        </div>
                    </div>
                </transition>
            </div>
        </div>
    </header>
</template>

<script>
import { mapState, mapMutations } from "vuex";
export default {
    data() {
        return {
            login_flag: false,
            user: {
                userName: null,
                userId: null,
            },
            dialogVisible: false,
            oldPsw: "",
            newPsw: "",
            confirmNewPsw: "",
            role: 0,
        };
    },
    created() {
        this.getUserInfo();
        this.role = this.$cookies.get("role");
    },
    computed: mapState(["flag", "menu"]),
    methods: {
        resetPsw() {
            if (this.oldPsw == "") {
                this.$message("请输入旧密码");
                return;
            }
            if (this.newPsw == "") {
                this.$message("请输入新密码");
                return;
            }
            if (this.confirmNewPsw != this.newPsw) {
                this.$message("两次新密码不一致");
                return;
            }
            this.$axios(
                `/api/admin/resetPsw/${this.user.userId}/${this.oldPsw}/${this.newPsw}`
            ).then((res) => {
                let status = res.data.code;
                if (status == 200) {
                    if (res.data.data != true) {
                        this.$message(res.data.data);
                    } else {
                        this.$message("修改成功");
                        this.dialogVisible = false;
                        this.oldPsw = "";
                        this.newPsw = "";
                        this.confirmNewPsw = "";
                    }
                }
            });
        },
        showSetting() {
            this.login_flag = !this.login_flag;
        },
        ...mapMutations(["toggle"]),
        getUserInfo() {
            let userName = this.$cookies.get("cname");
            let userId = this.$cookies.get("cid");
            this.user.userName = userName;
            this.user.userId = userId;
        },
        index() {
            this.$router.push({ path: "/index" });
        },
        exit() {
            let role = this.$cookies.get("role");
            this.$router.push({ path: "/" });
            this.$cookies.remove("cname");
            this.$cookies.remove("cid");
            this.$cookies.remove("role");
            this.$cookies.remove("rb_token");
            this.$cookies.remove("rb_role");
            if (role == 0) {
                this.menu.pop();
            }
        },
    },
};
</script>

<style scoped>
.fade-enter-active,
.fade-leave-active {
    transition: opacity 0.2s;
}
.fade-enter,
.fade-leave-to {
    opacity: 0;
}

.oes-header {
    position: relative;
    z-index: 1000;
    display: flex;
    align-items: center;
    justify-content: space-between;
    height: var(--oes-header-height);
    padding: 0 24px;
    color: #fff;
    background: linear-gradient(90deg, #2b4de0 0%, #3d7eff 100%);
    box-shadow: 0 2px 12px rgba(43, 77, 224, 0.28);
}

.oes-header__brand {
    display: flex;
    align-items: center;
    gap: 10px;
    min-width: 0;
}

.oes-header__logo {
    font-size: 28px;
    color: #fff;
}

.oes-header__title {
    font-size: 20px;
    font-weight: 600;
    letter-spacing: 1px;
    white-space: nowrap;
}

.oes-header__right {
    display: flex;
    align-items: center;
    gap: 16px;
}

.oes-header__user {
    position: relative;
    display: flex;
    align-items: center;
    gap: 10px;
    padding: 6px 10px;
    border-radius: var(--oes-radius-sm);
    cursor: pointer;
    transition: background-color 0.2s;
}

.oes-header__user:hover {
    background: rgba(255, 255, 255, 0.16);
}

.oes-header__name {
    font-size: 14px;
    font-weight: 500;
}

.oes-header__avatar {
    width: 36px;
    height: 36px;
    border-radius: 50%;
    border: 2px solid rgba(255, 255, 255, 0.6);
}

.oes-user-menu {
    position: absolute;
    top: 52px;
    right: 0;
    min-width: 150px;
    padding: 6px;
    color: var(--oes-text);
    background: #fff;
    border-radius: var(--oes-radius);
    box-shadow: var(--oes-shadow);
}

.oes-user-menu__item {
    display: flex;
    align-items: center;
    gap: 8px;
    padding: 9px 12px;
    font-size: 13px;
    border-radius: var(--oes-radius-sm);
    cursor: pointer;
}

.oes-user-menu__item:hover {
    background: var(--oes-primary-light);
    color: var(--oes-primary);
}

.oes-user-menu__item--danger:hover {
    background: #fef2f2;
    color: var(--oes-danger);
}

.pwd-form {
    padding-right: 10px;
}

@media (max-width: 768px) {
    .oes-header__title {
        font-size: 16px;
    }
    .oes-header__name {
        display: none;
    }
}
</style>

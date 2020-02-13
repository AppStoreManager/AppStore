<template>
    <div class="login_container">
      <img src="../assets/bg.jpg" alt="" class="bgp"/>
      <div class="login_box">
        <!--头像区域-->
        <div class="avatar_box">
          <img src="../assets/logo.png" alt=""/>
        </div>
        <el-tabs v-model="loginRole" @tab-click="loginRoleSelect">
          <el-tab-pane label="开发者" name="0">
          </el-tab-pane>
          <el-tab-pane label="管理员" name="1">
          </el-tab-pane>
        </el-tabs>
        <el-form ref="loginFormRef" :model="loginForm" :rules="loginForm_rules" label-width="0px" class="login_form">
          <el-form-item prop="username">
            <el-input v-model="loginForm.username" prefix-icon="el-icon-user-solid"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input v-model="loginForm.password" placeholder="请输入密码" prefix-icon="el-icon-lock" show-password></el-input>
          </el-form-item>
          <el-form-item class="btn">
            <el-button type="primary" @click="validate" plain>登录</el-button>
            <el-button type="primary" plain>注册</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
</template>

<script>
export default {
  data () {
    return {
      loginForm: {
        username: '',
        password: ''
      },
      loginForm_rules: {
        username: [
          { required: true, message: '请输入登录用户名', trigger: 'blur' }
          /* { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' } */
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 3, message: '长度至少为3 个字符', trigger: 'blur' }
        ]
      },
      loginRole: '0',
      loginRoleIndex: 0,
      loginPath: ['login', 'login2']

    }
  },
  methods: {
    validate: function () {
      this.$refs.loginFormRef.validate(async valid => {
        if (!valid) return
        const { data: res } = await this.$http.post(this.loginPath[this.loginRoleIndex], this.loginForm)
        console.log(res)
        if (res.meta.status !== 200) {
          return this.$message.error('登录失败')
        } else {
          this.$message.success('登录成功')
        }
        // 1.将登录成功之后的token，保存到客户端的sessionStorage中
        window.sessionStorage.setItem('token', res.data.token)
        // 2.通过编程式导航跳到后台主页，路由地址是:/home
        await this.$router.push('/home')
      })
    },
    loginRoleSelect () {
      this.loginRoleIndex = parseInt(this.activeName)
      console.log(this.loginRoleIndex)
    }
  }
}
</script>

<style lang="less" scoped>
 .login_container {
   /* background-color: #2b4b6b; */
   height: 100%;
 }
 .bgp {
   width: 100%;
   height: 100%;
 }

  .login_box {
    width: 450px;
    height: 300px;
    background-color: white;
    border-radius: 3px;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    .avatar_box {
      height: 130px;
      width: 130px;
      border: 1px solid white;
      border-radius: 50%;
      padding: 10px;
      box-shadow: 0 0 10px #d3d4d6;
      position: absolute;
      left: 50%;
      transform: translate(-50%, -60%);
      background-color: #ffffff;
      img{
        width: 100%;
        height: 100%;
        border-radius: 50%;
        background-color: #e9e9eb;
      }
    }
  }

  .login_form {
    position: absolute;
    bottom: 0;
    width:100%;
    padding: 0 20px;
    box-sizing: border-box;
  }

  .btn {
    display: flex;
    justify-content: flex-end;
  }

  .el-tabs {
    background-color: transparent;
    width: 95%;
    margin-left: 10px;
    text-align: center;
    position: absolute;
    transform: translate(0,22px);
    font-size: larger;
  }
</style>

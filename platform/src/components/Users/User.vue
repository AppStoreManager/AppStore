<template>
  <div>
    <!--面包屑导航区域-->
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/welcome' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item><a href="/">用户管理</a></el-breadcrumb-item>
      <el-breadcrumb-item>用户列表</el-breadcrumb-item>
    </el-breadcrumb>

    <!--卡片视图区-->
    <el-card>
      <div>
        <span class="title">用户信息</span>
        <el-row :gutter="20">
          <el-col :span="7">
            <el-input
              placeholder="请输入内容"
              v-model="queryInfo.query"
            clearable @clean="getUserList">
            </el-input>
            <!--实现查询功能的函数-->
            <el-button slot="append"
              icon="el-icon-search" @click="getUserList"></el-button>
          </el-col>
          <el-col :span="4">
            <el-button type="primary" @click="addDialogVisible = true">添加用户</el-button>
          </el-col>
        </el-row>
          <!--用户信息表格-->
        <el-table :data="userList" stripe style="width: 100%" align="left">
          <el-table-column type="#"></el-table-column>
          <el-table-column label="用户名" prop="username"></el-table-column>
          <el-table-column label="电话" prop="mobile"></el-table-column>
          <el-table-column label="邮箱" prop="email"></el-table-column>
          <el-table-column label="角色" prop="role_name"></el-table-column>
          <el-table-column label="状态">
            <!--作用于插槽会覆盖prop-->
            <template slot-scope="scope">
              <el-switch v-model="scope.row.mg_state" @change="userStateChange(scope.row)"></el-switch>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="200px">
            <template slot-scope="scope">
              <el-button type="primary" icon="el-icon-edit" size="mini" @click="showEditDialog(scope.row.id)"></el-button>
              <el-button type="danger" icon="el-icon-delete" size="mini" @click="removeUserById(scope.row.id)"></el-button>
              <el-tooltip effect="dark" content="分配角色" placement="top">
                <el-button type="warning" icon="el-icon-setting" size="mini" @click="allotRole(scope.row)">
                </el-button>
              </el-tooltip>
            </template>
          </el-table-column>
        </el-table>
        <!--分页功能-->
        <el-pagination
          :page-sizes="[1, 3, 5, 10]"
          :page-size="queryInfo.pagesize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        >
        </el-pagination>
      </div>
    </el-card>

    <!--添加用户的对话框-->
    <el-dialog title="添加用户" :visible.sync="addDialogVisible"
    width="50%" @close="addDialogClose">
      <el-form :model="newUser" :rules="addUserRules" ref="addUserRef" label-width="70px" >
        <el-form-item label="用户名" prop="username">
          <el-input v-model="newUser.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="newUser.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="mobile">
          <el-input v-model="newUser.mobile" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="newUser.email" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="info" @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addUser">确 定</el-button>
      </div>
    </el-dialog>
    <!--修改用户的对话框-->
    <el-dialog title="修改用户" :visible.sync="editDialogVisible" width="50%" @close="editDialogClose">
      <el-form ref="editUserRef" label-width="70px" :model="editUser" :rules="editFormRules">
        <el-form-item label="用户名">
          <el-input v-model="editUser.username" disabled></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="mobile">
          <el-input v-model="editUser.mobile"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="editUser.email"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="info" @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editUserInfo">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="分配角色" :visible.sync="roleDialogVisible" width="50%" @close="roleDialogClose">
      <div>
        <p>当前的用户: {{userInfo.username}}</p>
        <p>当前的角色: {{userInfo.role_name}}</p>
        <p>分配新角色:
          <el-select v-model="selectedRoleId" placeholder="请选择">
            <el-option v-for="item in rolesList" :key="item.id" :label="item.roleName" :value="item.id">
            </el-option>
          </el-select>
        </p>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="info" @click="roleDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="roleDialogSave">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data () {
    // 验证邮箱的规则
    const checkEmail = (rule, value, cb) => {
      const regEmail = /^\w+@\w+(\.\w+)+$/
      if (regEmail.test(value)) {
        return cb()
      }
      // 返回一个错误提示
      cb(new Error('请输入合法的邮箱'))
    }
    // 验证手机号码的规则
    const checkMobile = (rule, value, cb) => {
      const regMobile = /^1[34578]\d{9}$/
      if (regMobile.test(value)) {
        return cb()
      }
      // 返回一个错误提示
      cb(new Error('请输入合法的手机号码'))
    }
    return {
      // 获取用户列表的参数对象
      queryInfo: {
        query: '',
        pagenum: 1,
        pagesize: 10
      },
      userList: [],
      total: 0,
      // 添加用户
      addDialogVisible: false,
      newUser: {
        username: '',
        mobile: '',
        password: '',
        email: ''
      },
      addUserRules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 10, message: '用户名长度在3~10个字节', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 3, max: 10, message: '密码长度在3~10个字节', trigger: 'blur' }
        ],
        mobile: [
          { required: true, message: '请输入手机', trigger: 'blur' },
          { validator: checkMobile, trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { validator: checkEmail, trigger: 'blur' }
        ]
      },
      // 编辑用户
      editDialogVisible: false,
      editUser: {},
      editFormRules: {
        email: [
          { required: true, message: '请输入用户邮箱', trigger: 'blur' },
          { validator: checkEmail, trigger: 'blur' }
        ],
        mobile: [
          { required: true, message: '请输入用户手机', trigger: 'blur' },
          { validator: checkMobile, trigger: 'blur' }
        ]
      },
      // 分配角色对话框
      roleDialogVisible: false,
      // 需要被分配角色的用户信息
      userInfo: {},
      // 角色列表
      rolesList: [],
      // 选中的新角色id
      selectedRoleId: ''
    }
  },
  created () {
    this.getUserList()
  },
  methods: {
    async getUserList () {
      const { data: res } = await this.$http.get('users', { params: this.queryInfo })
      console.log(res)
      this.userList = res.data.users
      this.total = res.data.total
      this.queryInfo.pagenum = res.data.pagenum
      console.log(this.userList)
    },
    // 监听page大小改变的事件
    handleSizeChange (newSize) {
      console.log(newSize)
      this.queryInfo.pagesize = newSize
    },
    // 监听页码值改变的事件
    handleCurrentChange (newPage) {
      this.queryInfo.pageNum = newPage
    },
    // 更改用户状态
    async userStateChange (userInfo) {
      console.log(userInfo)
      const { data: res } = await this.$http.put('welcome')
      if (res.state !== 200) {
        userInfo.mg_state = !userInfo.mg_state
        this.$message.error('更新用户状态失败')
      } else {
        this.$message.success('更新用户状态成功')
      }
    },
    // 监听添加用户对话框的关闭事件
    addDialogClose () {
      this.$refs.addUserRef.resetFields()
    },
    addUser () {
      this.$refs.addUserRef.validate(async valid => {
        if (!valid) return
        const { data: res } = await this.$http.post('users', this.newUser)
        if (res.meta.status !== 201) {
          this.$message.error('添加新用户失败')
        } else {
          this.$message.success('添加新用户成功')
          this.addDialogVisible = false
          this.getUserList()
        }
      })
    },
    // 编辑用户信息
    editDialogClose () {
      this.$refs.editUserRef.resetFields()
    },
    async showEditDialog (id) {
      console.log(id)
      const { data: res } = await this.$http.get('users/' + id)
      if (res.meta.status !== 200) {
        return this.$message.error('查询用户信息失败')
      }
      this.editUser = res.data
      this.editDialogVisible = true
    },
    editUserInfo () {
      this.$refs.editUserRef.validate(async valid => {
        if (!valid) return
        const { data: res } = await this.$http.put('users/' + this.editUser.id, {
          email: this.editUser.email,
          mobile: this.editUser.mobile })
        if (res.meta.status !== 200) return this.$message.error('编辑用户数据不成功')
        this.editDialogVisible = false
        this.getUserList()
        this.$message.success('更新用户数据成功')
      })
    },
    // 删除用户
    async removeUserById (id) {
      const confirmResult = await this.$confirm('此操作将永久删除用户信息，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(err => err)
      /* .then(() => {
        this.$message({
          type: 'success',
          message: '删除成功!'
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      }) */
      console.log(confirmResult)
      if (confirmResult !== 'confirm') {
        return this.$message.info('已取消删除')
      }
      const { data: res } = await this.$http.delete('users/' + id)
      if (res.meta.status !== 200) return this.$message.error('删除用户失败')
      this.$message.success('删除用户成功')
      this.getUserList()
    },
    // 分配角色
    async allotRole (userInfo) {
      this.userInfo = userInfo
      // 获取角色列表
      const { data: res } = await this.$http.get('roles')
      if (res.meta.status !== 200) return this.$message.error('获取角色失败')
      this.rolesList = res.data
      console.log(this.rolesList)
      this.roleDialogVisible = true
    },
    roleDialogClose () {
      this.userInfo = {}
      this.selectedRoleId = ''
    },
    async roleDialogSave () {
      if (!this.selectedRoleId) {
        return this.$message.error('请选择要分配的角色')
      }
      // 不成功
      const { data: res } = await this.$http.post('users/' + this.userInfo.id + '/role',
        { rid: this.selectedRoleId })
      console.log(this.userInfo.id)
      console.log(this.selectedRoleId)
      console.log(res)
      if (res.meta.status !== 200) return this.$message.error('分配角色失败')
      this.$message.success('分配角色成功')
      this.getUserList()
      this.roleDialogVisible = false
    }
  }
}
</script>

<style lang="less" scoped>
  .title{
    font-size: xx-large;
    font-family: "微软雅黑 Light", monospace;
  }
</style>

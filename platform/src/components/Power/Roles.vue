<template>
  <div>
    <!--面包屑导航区域-->
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/welcome' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item><a href="/">权限管理</a></el-breadcrumb-item>
      <el-breadcrumb-item>角色列表</el-breadcrumb-item>
    </el-breadcrumb>
    <!--卡片视图-->
    <el-card>
      <!--添加角色按钮区域-->
      <el-button type="primary">添加角色</el-button>
      <div>
        <el-table :data="rolesList" style="width: 100%">
          <el-table-column type="expand">
            <template slot-scope="props">
              <el-form label-position="left" inline class="demo-table-expand">
                <el-row :class="['bottom', i1 === 0 ? 'top':'']" v-for="(item1,i1) in props.row.children"
                        :key="item1.id" class="center">
                  <!--渲染一级权限-->
                  <el-col :span="5">
                    <el-tag type="primary"
                            closable @close="removeRightById(props.row, item1.id)">{{item1.authName}}</el-tag>
                    <i class="el-icon-caret-right"></i>
                  </el-col>
                  <!--渲染二级、三级权限-->
                  <el-col :span="19">
                    <el-row :class="[i2 === 0 ? '' : 'top']" v-for="(item2, i2) in item1.children" :key="item2.id" class="center">
                      <el-col :span="6">
                        <el-tag type="success"
                                closable @close="removeRightById(props.row, item2.id)">{{item2.authName}}</el-tag>
                        <i class="el-icon-caret-right"></i>
                      </el-col>
                      <el-col :span="18">
                        <el-tag type="warning" v-for="item3 in item2.children" :key="item3.id"
                                closable @close="removeRightById(props.row, item3.id)">
                          {{item3.authName}}
                        </el-tag>
                      </el-col>
                    </el-row>
                  </el-col>
                </el-row>
              </el-form>
            </template>
          </el-table-column>
          <el-table-column label="角色名称" prop="roleName"></el-table-column>
          <el-table-column label="角色描述" prop="roleDesc"></el-table-column>
          <el-table-column label="操作" width="300px">
            <template slot-scope="scope">
              <el-button type="primary" icon="el-icon-edit" size="mini">编辑</el-button>
              <el-button type="danger" icon="el-icon-delete" size="mini">删除</el-button>
              <el-button type="warning" icon="el-icon-setting" size="mini"
                         @click="showSetRightDialog(scope.row)">分配权限</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-card>
    <el-dialog title="提示" :visible.sync="setRightDialogVisible" width="40%" center @close="clearLeafKeys">
      <el-tree :data="rightList" :props="treeProps" show-checkbox node-key="id"
               :default-expand-all="true" :default-checked-keys="leafKeys" ref="treeRef">
      </el-tree>
      <span slot="footer" class="dialog-footer">
    <el-button @click="setRightDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="addRights">确 定</el-button>
  </span>
    </el-dialog>

  </div>
</template>

<script>
export default {
  data () {
    return {
      rolesList: [],
      rightList: [],
      setRightDialogVisible: false,
      // 定义树形结构表格的基础属性
      treeProps: {
        label: 'authName', // 文本显示
        children: 'children' // 下一级的标识
      },
      leafKeys: [],
      // 即将要新增权限的角色id
      role: 0

    }
  },
  created () {
    this.getRoleList()
  },
  methods: {
    async getRoleList () {
      const { data: res } = await this.$http.get('roles')
      console.log(res)
      if (res.meta.status !== 200) return this.$message.error('获取角色信息失败')
      this.rolesList = res.data
    },
    async removeRightById (role, id) {
      const confirmResult = await this.$confirm('此操作将永久删除该权限, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(err => err)
      if (confirmResult !== 'confirm') {
        return this.$message.info('已取消删除权限')
      }
      const { data: res } = await this.$http.delete('roles/' + role.id + '/rights/' + id)
      if (res.meta.status !== 200) {
        return this.$message.error('删除权限失败')
      }
      /* this.getRoleList() */
      role.children = res.data
      this.$message.success('删除权限成功')
    },
    async showSetRightDialog (role) {
      this.role = role.id
      const { data: res } = await this.$http.get('rights/tree')
      if (res.meta.status !== 200) return this.$message.error('获取权限信息失败')
      this.rightList = res.data
      console.log(this.rightList)
      this.getLeafKeys(role, this.leafKeys)
      this.setRightDialogVisible = true
    },
    // 通过递归形式获得角色下所有三级权限
    getLeafKeys (node, arr) {
      if (!node.children) {
        return arr.push(node.id)
      }
      node.children.forEach(item => this.getLeafKeys(item, arr))
    },
    clearLeafKeys () {
      this.leafKeys = []
    },
    async addRights () {
      const keys = [
        ...this.$refs.treeRef.getCheckedKeys(),
        ...this.$refs.treeRef.getHalfCheckedKeys()
      ]
      const idStr = keys.join(',')
      const { data: res } = await this.$http.post('roles/' + this.role + '/rights', { rids: idStr })
      if (res.meta.status !== 200) {
        return this.$message.error('分配权限失败')
      }
      this.$message.success('分配权限成功')
      this.getRoleList()
      this.setRightDialogVisible = false
    }
  }
}
</script>

<style lang="less" scoped>
  .el-tag{
    margin: 7px
  }
  .top{
    border-top: 1px solid #eee
  }
  .bottom{
    border-bottom: 1px solid #eee
  }
  .center{
    display: flex;
    align-items: center;
  }
</style>

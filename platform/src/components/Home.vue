<template>
<!--  主界面区域  -->
  <el-container class="home-container">
    <el-header class="el-header" height="70px">
      <div>
        <img src="../assets/heima.png" alt="" >
        <span>App信息管理平台</span>
      </div>
      <el-button type="info" @click="logout">退出</el-button>
    </el-header>
    <el-container>
      <el-aside :width="isCollapse ? '64px' : '200px'" >
        <!--侧边栏菜单区域-->
        <el-menu background-color="#2b4b6b" text-color="#fff" active-text-color="#ffd04b"
                 :collapse="isCollapse" :collapse-transition="true" router
        default-active="$route.path">
          <!--动态绑定实现点击菜单栏后有高亮，$route.path-->
          <!--一级菜单-->
          <el-submenu :index="item.id + ''" v-for="item in menuList" :key="item.id">
            <!--一级菜单模板区-->
            <template slot="title">
              <!--图标-->
              <i class="el-icon-location"></i>
              <span>{{item.authName}}</span>
            </template>
            <!--二级菜单-->
            <el-menu-item :index="'/' + subItem.path" v-for="subItem in item.children" :key="subItem.id"
            @click="saveNavState">
              <i class="el-icon-menu"></i>
              <span>{{subItem.authName}}</span>
            </el-menu-item>
          </el-submenu>
          <!--<el-submenu index="1">
            &lt;!&ndash;一级菜单模板区&ndash;&gt;
            <template slot="title">
              &lt;!&ndash;图标&ndash;&gt;
              <i class="el-icon-location"></i>
              <span>App信息列表</span>
            </template>
            &lt;!&ndash;二级菜单&ndash;&gt;
            <el-menu-item index="1-1">
              <i class="el-icon-location"></i>
              <span>App基础信息</span>
            </el-menu-item>
            <el-menu-item index="1-2">
              <i class="el-icon-location"></i>
              <span>App版本信息</span>
            </el-menu-item>
          </el-submenu>
          <el-submenu index="2">
            &lt;!&ndash;一级菜单模板区&ndash;&gt;
            <template slot="title">
              &lt;!&ndash;图标&ndash;&gt;
              <i class="el-icon-location"></i>
              <span>App信息修改</span>
            </template>
            &lt;!&ndash;二级菜单&ndash;&gt;
            <el-menu-item index="2-1">
              <i class="el-icon-location"></i>
              <span>App基础信息修改</span>
            </el-menu-item>
            <el-menu-item index="2-2">
              <i class="el-icon-location"></i>
              <span>App版本信息修改</span>
            </el-menu-item>
          </el-submenu>
          <el-submenu index="3">
            &lt;!&ndash;一级菜单模板区&ndash;&gt;
            <template slot="title">
              &lt;!&ndash;图标&ndash;&gt;
              <i class="el-icon-location"></i>
              <span>App列表</span>
            </template>
            &lt;!&ndash;二级菜单&ndash;&gt;
            <template slot="appList"></template>
          </el-submenu>-->
        </el-menu>
      </el-aside>
      <el-container>
        <el-main class="el-main">
          <!--路由占位符-->
          <router-view></router-view>
        </el-main>
        <el-footer class="el-footer" align="center">
          <el-row :gutter="5" style="margin-top: 12px; margin-left: 12px" type="flex" justify="center">
            <el-col :span="6">
              <el-badge :value="12" class="item">
                <el-button size="small">评论</el-button>
              </el-badge>
            </el-col>
            <el-col :span="6">
              <el-badge :value="12" class="item">
                <el-button size="small">评论</el-button>
              </el-badge>
            </el-col>
            <el-col :span="6">
              <el-badge :value="12" class="item" type="primary">
                <el-button size="small">评论</el-button>
              </el-badge>
            </el-col>
            <el-col :span="6">
              <el-badge :value="12" class="item" type="warning">
                <el-button size="small">评论</el-button>
              </el-badge>
            </el-col>
          </el-row>
        </el-footer>
      </el-container>
    </el-container>
  </el-container>
</template>

<script>
export default {
  data () {
    return {
      menuList: [],
      isCollapse: false
    }
  },
  created () {
    this.getMenuList()
  },
  methods: {
    logout: function () {
      window.sessionStorage.clear()
      this.$router.push('/login')
    },
    async getMenuList () {
      const { data: res } = await this.$http.get('menus')
      if (res.meta.status !== 200) return this.$message.error(res.meta.msg)
      console.log(res)
      this.menuList = res.data
      console.log(this.menuList)
    },
    // 点击按钮，切换菜单的折叠与展开
    toggleButtonCollapse () {
      console.log(this.isCollaspe)
      this.isCollaspe = !this.isCollaspe
    },
    saveNavState () {

    }
  }
}
</script>

<style lang="less" scoped>
  .home-container{
    height: 100%;
  }
  .el-header{
    background-color: #2c3e50;
    display: flex;
    justify-content: space-between;
    padding-left: 0;
    align-items: center;
    color: #ffffff;
    font-size: 20px;
    font-family: 华文新魏, monospace;
    > div {
      display: flex;
      align-items: center;
      span {
        margin-left: 15px;
      }
    }
  }
  .el-aside{
    background-color: #2b4b6b;
  }
  el.main{
    background-color: #ffffff;
  }
  .el-footer{
    background-color: #f4f4f5;
  }
  .el-menu{
    border-right: none;
  }
  .toggleButton{
    background-color: #4a5064;
    font-size: 12px;
    line-height: 24px;
    color: #fff;
    text-align: center;
    letter-spacing: 0.2em;
    cursor: pointer;
  }
</style>

<template>
  <div>
    <!--面包屑导航区域-->
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/welcome' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item><a href="/">商品管理</a></el-breadcrumb-item>
      <el-breadcrumb-item>分类参数</el-breadcrumb-item>
    </el-breadcrumb>
    <!--卡片视图区-->
    <el-card>
      <div>
        <el-tabs v-model="infoModify" @tab-click="modifyInformation">
          <el-tab-pane label="App基本信息修改" name="first">
            <el-row :gutter="30">
              <el-col :span="10" >
                <el-card :data="basicInfo">
                  <div>
                    <span style="margin-bottom: 40%">基本信息</span>
                    <el-button style="float: right; padding: 3px 0" type="text" @click="basicInfoChange">修改信息</el-button>
                  </div>
                  <el-divider content-position="center"><el-icon class="el-icon-data-analysis"></el-icon></el-divider>
                  <span class="basicInfoTitle">名称: </span>
                  <p class="basicInfoContent">{{basicInfo.name}}</p>
                  <el-divider></el-divider>
                  <span class="basicInfoTitle">开发者: </span>
                  <p class="basicInfoContent">{{basicInfo.author}}</p>
                  <el-divider></el-divider>
                  <span class="basicInfoTitle">链接: </span>
                  <p class="basicInfoContent">{{basicInfo.url}}</p>
                  <el-divider></el-divider>
                  <span class="basicInfoTitle">基本信息说明: </span>
                  <p class="basicInfoContent">{{basicInfo.basicInformation}}</p>
                </el-card>
              </el-col>
              <el-col :span="2">
                <div></div>
              </el-col>
              <el-col :span="12">
                <div>
                  <el-collapse v-model="editBasicInfo" @change="handleChange" >
                    <el-collapse-item title="名称 Name" name="1" class="editInfoTitle">
                      <div>
                        <el-input v-model="editInfo.name" prefix-icon="el-icon-s-check" class="editInfo"></el-input>
                      </div>
                    </el-collapse-item>
                    <el-collapse-item title="开发者 Developer" name="2">
                      <div>
                        <el-input v-model="editInfo.author" prefix-icon="el-icon-user-solid"></el-input>
                      </div>
                    </el-collapse-item>
                    <el-collapse-item title="链接 URL" name="3">
                      <el-input v-model="editInfo.url" prefix-icon="el-icon-platform-eleme"></el-input>
                    </el-collapse-item>
                    <el-collapse-item title="基本信息说明 Basic Information" name="4">
                      <el-input v-model="editInfo.basicInformation" prefix-icon="el-icon-info"></el-input>
                    </el-collapse-item>
                  </el-collapse>
                  <el-button type="info" @click="reEditInfo" size="small" class="editInfoButton1">重置</el-button>
                  <el-button type="primary" @click="editInfoSend" size="small" class="editInfoButton2">确定</el-button>
                </div>
              </el-col>
            </el-row>
          </el-tab-pane>
          <el-tab-pane label="App版本信息修改" name="second">
            <el-row :gutter="30">
              <el-col :span="7" >
                <div class="block">
                  <el-timeline>
                    <el-timeline-item
                      v-for="(version, index) in allVersion" :key="index" :timestamp="version.time">
                      <h2>{{version.name + version.activity}}</h2>
                      <p>版本号: {{version.versionId}}</p>
                    </el-timeline-item>
                  </el-timeline>
                </div>
              </el-col>
              <el-col :span="2">
                <div></div>
              </el-col>
              <el-col :span="17">
                <div>
                  <el-table :data="allVersion" border style="width: 90%" ref="multipleTable">
                    <el-table-column type="selection" :index="index"></el-table-column>
                    <el-table-column prop="name" label="名称" width="180">
                    </el-table-column>
                    <el-table-column prop="versionId" label="版本号">
                    </el-table-column>
                    <el-table-column prop="time" label="上传时间">
                    </el-table-column>
                    <el-table-column prop="state" label="审核状态">
                    </el-table-column>
                  </el-table>
                  <el-button type="info" icon="el-icon-edit" size="mini" class="versionBtn" @click="toggleSelection(index)"></el-button>
                  <el-tooltip effect="dark" content="发布" placement="top">
                    <el-button type="primary" icon="el-icon-upload2" size="mini" class="versionBtn" @click="toggleSelection(index)"></el-button>
                  </el-tooltip>
                  <el-tooltip effect="dark" content="下架" placement="top">
                    <el-button type="warning" icon="el-icon-download" size="mini" class="versionBtn" @click="toggleSelection(index)"></el-button>
                  </el-tooltip>
                  <div class="versionBtn">

                  </div>
                </div>
              </el-col>
            </el-row>
          </el-tab-pane>
        </el-tabs>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  data () {
    return {
      cateList: [],
      cateProps: {
        value: 'cat_id',
        label: 'cat_name',
        children: 'children'
      },
      selectedCate: '',
      // 选项卡记录
      infoModify: 'first',
      basicInfo: {
        basicInformation: '',
        name: '',
        url: '',
        author: ''
      },
      editBasicInfo: [],
      editInfo: {
        basicInformation: '',
        name: '',
        url: '',
        author: ''
      },
      allVersion: [
        {
          activity: 'app发布',
          name: 'aaa',
          versionInformation: 'asdfsasdfasdf',
          versionId: '1.0.x',
          time: '2020-02-03'
        }],
      versionInfo: {
        activity: 'app发布',
        name: 'aaa',
        versionInformation: 'asdfsasdfasdf',
        versionId: '1.0.x',
        time: '2020-02-03'
      },
      index: 0
    }
  },
  created () {
    this.getCateList()
  },
  methods: {
    async getCateList () {
      const { data: res } = await this.$http.get('categories')
      if (res.meta.status !== 200) return this.$message.error('获取商品分类信息失败')
      this.cateList = res.data
      console.log(this.cateList)
      console.log(this.cateList[0].children)
    },
    handleChange () {
      console.log(this.selectedCate)
    },
    modifyInformation () {
      console.log(this.infoModify)
    },
    basicInfoChange () {
      this.editBasicInfo = ['1', '2', '3', '4']
    },
    reEditInfo () {
      this.editInfo = {}
    },
    editInfoSend () {
      console.log(this.editInfo)
      // 发送修改信息到数据库
    },
    toggleSelection (rows) {
      /* if (rows) {
        rows.forEach(row => {
          this.$refs.multipleTable.toggleRowSelection(row);
        });
      } else {
        this.$refs.multipleTable.clearSelection();
      } */
      console.log(rows)
    }
  }
}
</script>

<style lang="less" scoped>
  .category {
    margin: 15px 0
  }
  .editInfoButton1 {
    margin: 15px;
    transform: translate(300%, 0);
  }
  .editInfoButton2 {
    margin: 15px;
    transform: translate(300%, 0);
  }
  .versionBtn {
    margin: 15px;
    transform: translate(300%, 0);
  }
</style>

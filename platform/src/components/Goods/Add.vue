<template>
  <div>
    <!--面包屑导航区域-->
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/welcome' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item><a href="/">商品管理</a></el-breadcrumb-item>
      <el-breadcrumb-item>添加商品</el-breadcrumb-item>
    </el-breadcrumb>
    <!--卡片视图区-->
    <el-card style="height: 800px">
      <el-alert title="添加应用软件" type="info" show-icon center :closable="false">
      </el-alert>
      <el-steps :space="200" :active="activeIndex - 1" finish-status="success" align-center style="margin:15px;">
        <el-step title="基本信息"></el-step>
        <el-step title="应用描述"></el-step>
        <el-step title="版本信息"></el-step>
        <el-step title="上传源码"></el-step>
        <el-step title="完成"></el-step>
      </el-steps>
      <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="200px" style="height: auto">
        <el-tabs :tab-position="'left'" v-model="activeIndex" :before-leave="beforeTabLeave">
          <el-tab-pane label="基本信息" name="1" style="height: 800px;">
            <el-form-item label="应用名称" prop="name" size="medium">
              <el-input v-model="addForm.name" style="margin: 0px" clearable class="input"></el-input>
            </el-form-item>
            <el-form-item label="开发者" prop="author" size="medium">
              <el-input v-model="addForm.author" clearable class="input"></el-input>
            </el-form-item>
          </el-tab-pane>
          <el-tab-pane label="应用描述" name="2" style="height: 800px;">
            <el-form-item label="应用分类" size="medium" prop="category">
              <el-select v-model="addForm.categories" placeholder="请选择" multiple>
                <el-option-group v-for="group in categoryOptions" :key="group.label" :label="group.label">
                  <el-option v-for="item in group.options" :key="item.value" :label="item.label" :value="item.value">
                  </el-option>
                </el-option-group>
              </el-select>
            </el-form-item>
            <el-form-item label="应用描述" prop="basicInformation" size="medium">
              <el-input type="textarea" autosize placeholder="请输入内容" v-model="addForm.basicInformation" class="desc">
              </el-input>
            </el-form-item>
          </el-tab-pane>
          <el-tab-pane label="版本信息" name="3" style="height: 800px;">
            <el-form-item label="应用版本格式" size="medium" prop="version">
              <el-select v-model="addForm.versionStyle" placeholder="请选择">
                <el-option v-for="item in versionOptions" :key="item.value" :label="item.label" :value="item.value">
                  <span style="float: left">{{ item.label }}</span>
                  <span style="float: right; color: #8492a6; font-size: 13px">{{ item.value }}</span>
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="应用版本信息描述" prop="versionInformation" size="medium" error>
              <el-input type="textarea" autosize placeholder="请输入内容" v-model="addForm.versionInformation" class="desc">
              </el-input>
            </el-form-item>
          </el-tab-pane>
          <el-tab-pane label="上传源码" name="4" style="height: 800px;">
            <el-form-item label="链接" size="medium" prop="upload">
              <el-input v-model="addForm.url" style="width: 400px" clearable class="input1"></el-input>
              <el-upload :action="uploadURL" :on-preview="handlePreview"
                         :on-remove="handleRemove" :before-remove="beforeRemove" multiple :limit="3"
                         :on-exceed="handleExceed" class="upload" :headers="headerObj">
                <el-button size="small" type="primary" style="margin-top: 10px;">点击上传</el-button>
                <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
              </el-upload>
            </el-form-item>
          </el-tab-pane>
          <el-tab-pane label="确认页面" name="5">
            <el-form-item style="align-content: center">
              <span>
                <p><i class="el-icon-cpu"></i>  应用名: {{addForm.name}}</p>
              </span>
              <span>
                <p><i class="el-icon-user"></i>  开发者: {{addForm.author}}</p>
              </span>
              <span>
                <p><i class="el-icon-info"></i>  基础信息说明: {{addForm.basicInformation}}</p>
              </span>
              <span>
                <p><i class="el-icon-aim"></i>  版本: {{'1.0.0'}}</p>
              </span>
              <span>
                <p><i class="el-icon-s-operation"></i>  版本信息说明: {{addForm.versionInformation}}</p>
              </span>
              <el-button type="info" @click="clearAddForm" class="btn" size="small">重置</el-button>
              <el-button type="primary" @click="submitAddForm" class="btn" size="small">确认</el-button>
            </el-form-item>
          </el-tab-pane>
        </el-tabs>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  data () {
    return {
      activeIndex: '1',
      addForm: {
        name: '',
        author: '',
        basicInformation: '',
        versionInformation: '',
        url: '',
        categories: [],
        versionStyle: ''
      },
      addFormRules: {
        name: [
          { required: true, message: '请输入应用名称', trigger: 'blur' }],
        author: [
          { required: true, message: '请输入开发者', trigger: 'blur' }],
        basicInformation: [
          { required: true, message: '请输入应用基础信息说明', trigger: 'blur' },
          { min: 4, message: '请至少输入四个字符', trigger: 'blur' }],
        versionInformation: [
          { required: true, message: '请输入应用版本信息说明', trigger: 'blur' },
          { min: 4, message: '请至少输入四个字符', trigger: 'blur' }],
        category: [
          { required: true, message: '请选择分类', trigger: 'blur' }],
        version: [
          { required: true, message: '请选择版本格式', trigger: 'blur' }],
        upload: [
          { required: true, message: '请输入上传源码链接', trigger: 'blur' }]
      },
      categoryOptions: [{
        label: '热门城市',
        options: [{
          value: 'Shanghai',
          label: '上海'
        }, {
          value: 'Beijing',
          label: '北京'
        }]
      }, {
        label: '城市名',
        options: [{
          value: 'Chengdu',
          label: '成都'
        }, {
          value: 'Shenzhen',
          label: '深圳'
        }, {
          value: 'Guangzhou',
          label: '广州'
        }, {
          value: 'Dalian',
          label: '大连'
        }]
      }],
      versionOptions: [{
        label: '热门城市',
        options: [{
          value: 'Shanghai',
          label: '上海'
        }, {
          value: 'Beijing',
          label: '北京'
        }]
      }, {
        label: '城市名',
        options: [{
          value: 'Chengdu',
          label: '成都'
        }, {
          value: 'Shenzhen',
          label: '深圳'
        }, {
          value: 'Guangzhou',
          label: '广州'
        }, {
          value: 'Dalian',
          label: '大连'
        }]
      }],
      uploadURL: 'http://127.0.0.1:8888/api/private/v1/upload',
      fileList: [{ name: 'food.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100' },
        { name: 'food2.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100' }],
      headerObj: {
        Authorization: window.sessionStorage.getItem('token')
      },
      error: {
        author: ''
      }
    }
  },
  methods: {
    // 处理删除图片的操作
    handleRemove (file, fileList) {
      console.log(file, fileList)
    },
    // 处理图片预览效果
    handlePreview (file) {
      console.log(file)
    },
    handleExceed (files, fileList) {
      this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`)
    },
    beforeRemove (file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`)
    },
    beforeTabLeave (activeName, oldActiveName) {
      return true
      /* console.log('即将离开的标签页名字是:' + oldActiveName)
      console.log('即将进入的标签页名字是:' + activeName)
      if (activeName < oldActiveName) {
        return true
      }
      if (oldActiveName === '1' && this.addForm.author === '' && this.addForm.name === '') {
        this.$message.error('请先完成当前页的信息输入')
        return false
      }
      if (oldActiveName === '2' &&
        this.addForm.categories === [] && this.addForm.basicInformation === '') {
        this.$message.error('请先完成当前页的信息输入')
        return false
      }
      if (oldActiveName === '3' &&
        this.addForm.versionStyle === '' && this.addForm.versionInformation === '') {
        this.$message.error('请先完成当前页的信息输入')
        return false
      }
      if (oldActiveName === '4' &&
        this.addForm.url === '') {
        this.$message.error('请先完成当前页的信息输入')
        return false
      }
      this.$message.success('系统已为您保存信息') */
    },
    clearAddForm () {
      this.addForm = {}
      this.activeIndex = '1'
      this.$refs.addFormRef.resetField()
    },
    submitAddForm () {
      this.$refs.addFormRef.validate((valid) => {
        if (valid) {
          console.log(this.addForm)
          this.$message.success('创建应用成功')
          this.$http.push('/goods')
        } else {
          this.$message.error('创建应用失败')
        }
      })
    }
  },
  watch: {
    'addForm.author' () {
      this.$refs.authorRef.$emit('el.form.change')
    }
  }
}
</script>

<style lang="less" scoped>
  .el-steps {
    position: center;
  }
  .el-step__title {
    font-size: 10px;
  }
  .input {
    width: 50%;
    margin-top: -50px;
    height: 2px;
  }
  .input1 {
    width: 50%;
    margin-top: -100px;
    height: 2px;
    margin-bottom: auto;
  }
  .desc {
    width: 50%;
    margin-top: -50px;
  }
  .el-form-item {
    margin-bottom: -25px;
  }
  .el-row {
    box-sizing: unset;
  }
  .upload {
    width: 400px;
    position: absolute;
    transform: translate(0, 50px);
  }
  .btn {
    margin: 5px;
    transform: translate(200px, 0);
  }
</style>

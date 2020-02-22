<template>
  <div>
    <!--面包屑导航区域-->
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/welcome' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item><a href="/">数据统计</a></el-breadcrumb-item>
      <el-breadcrumb-item>数据报表</el-breadcrumb-item>
    </el-breadcrumb>
    <!--卡片视图区-->
    <el-card>
      <!--2.准备一个放图标的区域，用id识别-->
      <div id="main" style="width: 800px; height: 400px;">
        <!--<scripts type="text/javascript">
        </scripts>-->
      </div>
    </el-card>
  </div>
</template>

<script>
// 1.导入echarts
import echarts from 'echarts'
import _ from 'lodash'
export default {
  data () {
    return {
      options: {
        title: {
          text: '用户来源'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            label: {
              backgroundColor: '#E9EEF3'
            }
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [
          {
            boundaryGap: false
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ]
      }
    }
  },
  created () {

  },
  // 此时页面已经渲染完毕了
  async mounted () {
    // 3.基于准备好的dom初始化echarts的实例
    const myChart = echarts.init(document.getElementById('main'))

    const { data: res } = await this.$http.get('reports/type/1')
    if (res.meta.status !== 200) return this.$message.error('获取折线图数据失败')

    // 4.准备数据和配置项
    const result = _.merge(res.data, this.options)
    // 5.展示数据
    myChart.setOption(result)
  },
  methods: {

  }
}
</script>

<style lang="less" scoped>

</style>

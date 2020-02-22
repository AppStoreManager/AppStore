<template>
  <div>
    <!--<span class="demonstration" style="align-content: center">欢迎来到App管理平台</span>-->
    <el-carousel height="300px" type="card" width="80%" class="carousel" :interval="4000">
      <el-carousel-item v-for="item in img" :key="item.id">
        <img :src="item.view" class="img"/>
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<script>
export default {
  data () {
    return {
      img: [
        { id: 0, view: require('../assets/main1.jpg') },
        { id: 1, view: require('../assets/main2.jpg') },
        { id: 2, view: require('../assets/main3.jpg') },
        { id: 3, view: require('../assets/main4.jpg') }
      ]
    }
  }
}
</script>

<style lang="less" scoped>
  .el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 150px;
    margin: 0;
  }

  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }

  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }

  .carousel {
    transform: translate(0, 10%);
  }

  .img {
    width: 100%;
    height: 100%;
  }
</style>

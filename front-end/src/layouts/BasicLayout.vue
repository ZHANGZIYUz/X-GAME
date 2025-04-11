<template>
  <van-nav-bar
      :title="title"
      left-arrow
      @click-left="onClickLeft"
      @click-right="onClickRight"
  >
    <template #right>
      <van-icon name="search" size="18"/>
    </template>
  </van-nav-bar>
  <div id="content">
    <router-view/>
  </div>
  <van-tabbar v-if="showTab" route @change="onChange">
    <van-tabbar-item to="/" icon="home-o" name="index">主页</van-tabbar-item>
    <van-tabbar-item to="/team" icon="friends-o" name="team">组队</van-tabbar-item>
    <van-tabbar-item to="/game/communicate" icon="search" name="communication">游戏社区</van-tabbar-item>
<!--    <van-tabbar-item to="/user/game/range" icon="search" name="score">游戏打分</van-tabbar-item>-->
    <van-tabbar-item to="/user" icon="setting-o" name="user">个人</van-tabbar-item>
  </van-tabbar>
</template>

<script setup lang="ts">
import {useRoute, useRouter} from "vue-router";
import {computed, ref} from "vue";
import routes from "../config/route";
const route = useRoute();

const router = useRouter();
const DEFAULT_TITLE = '游戏交流平台';
const title = ref(DEFAULT_TITLE);

const showTab = computed(() => !['/user/login', '/user/register'].includes(route.path));

/**
 * 根据路由切换标题
 */
router.beforeEach((to, from) => {
  const toPath = to.path;
  const route = routes.find((route) => {
    return toPath == route.path;
  })
  title.value = route?.title ?? DEFAULT_TITLE;
})

const onClickLeft = () => {
  router.back();
};

const onClickRight = () => {
  router.push('/search')
};

</script>

<style scoped>
#content {
  padding-bottom: 50px;
}
</style>

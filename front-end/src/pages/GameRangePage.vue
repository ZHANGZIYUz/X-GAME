<template>
  <van-picker visible-item-count="10"
              title="请选择游戏大类"
              :columns="columns"
              @confirm="onConfirm"
              @cancel="onCancel"
              @change="onChange"
  />

</template>

<script setup lang="ts">
import {Toast} from "vant";
import {useRouter} from "vue-router";
import {onMounted, ref} from "vue";
import {getCurrentUser} from "../services/user";

const router = new useRouter();
const user = ref();

const columns = ['大型多人在线角色扮演游戏 (MMORPG)', '多人在线战术竞技游戏 (MOBA)',
  '第一人称射击游戏 (FPS)', '生存游戏 (Survival Games)', '沙盒游戏 (Sandbox Games)',
  '战略游戏 (Strategy Games)', '竞速游戏 (Racing Games)', '卡牌游戏 (Card Games)',
  '音乐节奏游戏 (Rhythm Games)', '体育游戏 (Sports Games)', '动作角色扮演游戏 (Action RPG)',
  '动作冒险游戏 (Action-Adventure)', '格斗游戏 (fighting Games)'];

onMounted(async () => {
  user.value = await getCurrentUser();
  if (user.value === null) {
    Toast.fail('请先登录!');
    await router.push('/user/login');
  }
});

const onConfirm = (value, index) => {
  router.push({
    path: '/user/game',
    query: {
      type: index,
    },
  });
};
const onChange = (value, index) => {
  Toast(`当前选择: ${value}`);
};
const onCancel = () => Toast('取消');
</script>

<style scoped>

</style>
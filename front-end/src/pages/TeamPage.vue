<template>
  <div id="teamPage">
    <van-search v-model="searchText" placeholder="搜索队伍" @search="onSearch"/>
    <van-tabs v-model:active="active" @change="onTabChange">
      <van-tab title="我创建的队伍" name="myCreateTeam"/>
      <van-tab title="我加入的队伍" name="myJoinTeam"/>
      <van-tab title="公开队伍" name="public"/>
<!--      <van-tab title="加密队伍" name="private"/>-->
    </van-tabs>
    <div style="margin-bottom: 16px"/>
    <van-button class="add-button" type="primary" icon="plus" @click="toAddTeam"/>
    <team-card-list :teamList="teamList"/>
    <van-empty v-if="teamList?.length < 1" description="数据为空"/>
  </div>
</template>

<script setup lang="ts">

import {useRouter} from "vue-router";
import {onMounted, ref} from "vue";
import myAxios from "../plugins/myAxios";
import {Toast} from "vant";
import {getCurrentUser} from "../services/user";
import TeamCardList from "../components/TeamCardList.vue";

const active = ref('public')
const router = useRouter();
const searchText = ref('');

const user = ref();

onMounted(async () => {
  user.value = await getCurrentUser();
  if (user.value === null) {
    Toast.fail('请先登录!');
    await router.push('/user/login');
  }
})

/**
 * 切换查询状态
 * @param name
 */
const onTabChange = (name) => {
  if (name === 'public') {
    listTeam(searchText.value, 0);
  }
  // else if (name === 'private') {
  //   listTeam(searchText.value, 2);
  // }
  else if (name === 'myCreateTeam') {
    listMyCreateTeam();
  } else if (name === 'myJoinTeam') {
    listMyJoinTeam();
  }
}

// 跳转到创建队伍页
const toAddTeam = () => {
  router.push({
    path: "/team/add"
  })
}

const teamList = ref([]);

/**
 * 搜索队伍
 * @param val
 * @param status
 * @returns {Promise<void>}
 */
const listTeam = async (val = '', status = 0) => {
  const res = await myAxios.get("/team/list", {
    params: {
      searchText: val,
      pageNum: 1,
      status,
    },
  });
  if (res?.code === 0) {
    teamList.value = res.data;
  } else {
    Toast.fail('加载队伍失败，请刷新重试');
  }
}

const listMyJoinTeam = async () => {
  const res = await myAxios.get("/team/list/my/join", {
    params: {
      searchText: searchText.value,
      pageNum: 1,
    },
  });
  if (res?.code === 0) {
    teamList.value = res.data;
  } else {
    Toast.fail('加载队伍失败，请刷新重试');
  }
}

const listMyCreateTeam = async () => {
  const res = await myAxios.get("/team/list/my/create", {
    params: {
      searchText: searchText.value,
      pageNum: 1,
    },
  });
  if (res?.code === 0) {
    teamList.value = res.data;
  } else {
    Toast.fail('添加好友失败');
  }
}

// 页面加载时只触发一次
onMounted(() => {
  listTeam();
})

const onSearch = (val) => {
  listTeam(val);
};

</script>

<style scoped>
.add-button {
  position: fixed;
  right: 20px;
  bottom: 100px;
  z-index: 1000; /* 设置较大的z-index以确保按钮显示在最前 */
}
</style>

<template>
  <form action="/">
    <van-search
        v-model="searchText"
        show-action
        placeholder="请输入要搜索的标签"
        @search="onSearch"
        @cancel="onCancel"
    />
  </form>
  <van-divider content-position="left">已选标签</van-divider>
  <div v-if="activeIds.length === 0">请选择标签</div>
  <van-row gutter="16" style="padding: 0 16px">
    <van-col v-for="tag in activeIds">
      <van-tag closeable size="small" type="primary" @close="doClose(tag)">
        {{ tag }}
      </van-tag>
    </van-col>
  </van-row>
  <van-divider content-position="left">选择标签</van-divider>
  <van-tree-select
      v-model:active-id="activeIds"
      v-model:main-active-index="activeIndex"
      :items="tagList"
  />
  <div style="padding: 12px">
    <van-button block type="primary" @click="doSearchResult">搜索</van-button>
  </div>
</template>

<script setup lang="ts">
import {onMounted, ref} from 'vue';
import {useRouter} from "vue-router";
import {getCurrentUser} from "../services/user";
import {Toast} from "vant";

const router = useRouter()

const user = ref();

const searchText = ref('');

const originTagList = [
  {
    text: '性别',
    children: [
      {text: '男', id: '男'},
      {text: '女', id: '女'},
    ],
  },
  {
    text: '游戏类型',
    children: [
      {text: '大型多人在线角色扮演游戏', id: '大型多人在线角色扮演游戏'},
      {text: '多人在线战术竞技游戏', id: '多人在线战术竞技游戏'},
      {text: '第一人称射击游戏', id: '第一人称射击游戏'},
      {text: '生存游戏', id: '生存游戏'},
      {text: '沙盒游戏', id: '沙盒游戏'},
      {text: '战略游戏', id: '角色战略游戏'},
      {text: '竞速游戏', id: '竞速游戏'},
      {text: '卡牌游戏', id: '卡牌游戏'},
      {text: '音乐节奏游戏', id: '音乐节奏游戏'},
      {text: '体育游戏', id: '体育游戏'},
      {text: '动作角色扮演游戏', id: '动作角色扮演游戏'},
      {text: '动作冒险游戏', id: '动作冒险游戏'},
      {text: '格斗游戏', id: '格斗游戏'},
    ],
  },
]

onMounted(async () => {
  user.value = await getCurrentUser();
  if (user.value === null) {
    Toast.fail('请先登录!');
    await router.push('/user/login');
  }
})

// 标签列表
let tagList = ref(originTagList);

/**
 * 搜索过滤
 * @param val
 */
const onSearch = (val) => {
  tagList.value = originTagList.map(parentTag => {
    const tempChildren = [...parentTag.children];
    const tempParentTag = {...parentTag};
    tempParentTag.children = tempChildren.filter(item => item.text.includes(searchText.value));
    return tempParentTag;
  });

}
const onCancel = () => {
  searchText.value = '';
  tagList.value = originTagList;
};

// 已选中的标签
const activeIds = ref([]);
const activeIndex = ref(0);

// 移除标签
const doClose = (tag) => {
  activeIds.value = activeIds.value.filter(item => {
    return item !== tag;
  })
}

/**
 * 执行搜索
 */
const doSearchResult = () => {
  router.push({
    path: '/user/list',
    query: {
      tags: activeIds.value
    }
  })
}

</script>

<style scoped>

</style>

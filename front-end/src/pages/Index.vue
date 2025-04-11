<template>
  <div class="homepage">
    <van-tag class="center-tag" plain type="primary" size="large">欢迎进入该游戏交流平台! {{ user?.username }}</van-tag>
    <van-notice-bar
        left-icon="volume-o"
        style="margin-top: 15px"
        text="这是一个游戏匹配交流平台，你可以找到志同道合的朋友，与他们建立联系并一起组队玩游戏！"
        class="notice-bar"
    />

    <van-swipe :autoplay="3000" lazy-render class="image-swipe">
      <van-swipe-item v-for="image in images" :key="image">
        <img class="swipe-image" :src="image"/>
      </van-swipe-item>
    </van-swipe>

    <van-cell :title="friendListTitle" class="user-recommendation-cell"/>
    <van-card
        v-for="user in userList"
        :desc="user.userProfile"
        :title="`${user.username}`"
        :thumb="user.gender === 0 ? female : male"
    >
      <template #tags>
        <van-tag plain type="danger" v-for="tag in user.tags" style="margin-right: 8px;margin-top: 8px">
          {{ tag }}
        </van-tag>
      </template>
      <template #footer>
        <van-button size="mini" @click="deleteUser(user)">删除好友</van-button>
        <van-button size="mini" @click="connectUser(user.id)">联系我</van-button>
      </template>
    </van-card>
  </div>
</template>

<script setup lang="ts">
import {computed, onMounted, ref} from 'vue';
import myAxios from "../plugins/myAxios";
import {Toast} from "vant";
import {getCurrentUser} from "../services/user";
import {useRouter} from "vue-router";
import female from "../assets/female.png";
import male from "../assets/male.png";

const userList = ref([]);
const router = new useRouter();
const user = ref();

/**
 * 轮播照片地址
 */
const images = [
  'src/assets/front1.png',
  'src/assets/front2.png',
  'src/assets/front3.png',
  'src/assets/front4.png',
];

const friendListTitle = computed(() => {
  return userList.value.length === 0 ? '该用户好友为空，请先添加好友' : '好友列表:';
});

onMounted(async () => {
  user.value = await getCurrentUser();
  if (user.value === null) {
    Toast.fail('请先登录!');
    await router.push('/user/login');
  }
  const userListData = await myAxios.post('/user/friend/search')
      .then(function (response) {
        console.log('/user/search/tags succeed,response如下', response);
        console.log('response的data如下：', response.data)
        return response?.data;
      })
      .catch(function (error) {
        console.error('/user/search/tags error', error);
        Toast.fail('请求失败');
      })
  if (userListData) {
    userListData.forEach(user => {
      if (user.tags) {
        user.tags = JSON.parse(user.tags);
      }
    })
    userList.value = userListData;
    console.log('userList如下：', userList);
  }
});

const connectUser = (id) => {
  if (id === user.value.id) {
    Toast.fail("用户不能跟自己聊天");
  } else if (id !== user.value.id) {
    router.push({
      path: '/user/chat',
      query: {
        userId: id,
      },
    });
  }
};

const deleteUser = async (user) => {
  const res = await myAxios.delete('/user/friend/delete', {
    params: {
      friendUserId: user.id,
    }
  })
  if (res?.code === 0) {
    Toast.success('删除好友成功');
    window.location.reload();
  } else {
    Toast.fail(res.description);
  }
}
</script>

<style scoped>
.homepage {
  padding: 20px; /* 增加整体内边距 */
}

.notice-bar {
  background-color: #f0f8ff; /* 改变通知栏的背景颜色 */
  color: #333; /* 改变通知栏的文字颜色 */
  border-radius: 4px; /* 添加圆角效果 */
  margin-bottom: 16px; /* 增加下边距 */
}

.image-swipe {
  margin: 20px 0; /* 增加上下边距 */
}

.swipe-image {
  width: 100%; /* 图片自适应宽度 */
  border-radius: 8px; /* 圆角效果 */
}

.center-tag {
  display: block;
  margin: 0 auto; /* 水平居中 */
  text-align: center; /* 标签内文字居中 */
}

.user-recommendation-cell {
  margin-top: 20px; /* 增加上边距 */
}
</style>

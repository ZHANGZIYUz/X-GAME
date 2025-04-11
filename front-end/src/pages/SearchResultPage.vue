<template>
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
      <van-button size="mini" @click="addFriend(user)">添加好友</van-button>
      <van-button size="mini" @click="connectUser(user.id)">联系我</van-button>
    </template>
  </van-card>
</template>

<script setup lang="ts">
import {onMounted, ref} from 'vue';
import {useRoute, useRouter} from "vue-router";
import myAxios from "../plugins/myAxios";
import {Toast} from "vant";
import qs from 'qs';
import female from '../assets/female.png';
import male from '../assets/male.png';
import {getCurrentUser} from "../services/user";


const route = useRoute();
const {tags} = route.query;
const friendUserId = ref();
const router = useRouter();
const user = ref();

const userList = ref([]);

onMounted(async () => {
  user.value = await getCurrentUser();
  const userListData = await myAxios.get('/user/search/tags', {
    params: {
      tagNameList: tags
    },
    paramsSerializer: params => {
      return qs.stringify(params, {indices: false})
    }
  })
      .then(function (response) {
        console.log('/user/search/tags succeed,response如下', response);
        console.log('response的data如下：', response.data)
        return response?.data;
      })
      .catch(function (error) {
        console.error('/user/search/tags error', error);
        Toast.fail('请求失败');
      })
  console.log('userListData如下：', userListData)
  if (userListData) {
    userListData.forEach(user => {
      if (user.tags) {
        user.tags = JSON.parse(user.tags);
      }
    })
    userList.value = userListData;
    console.log('userList如下：', userList);
  }
})

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

const addFriend = async (user) => {
  const res = await myAxios.post("/user/friend/add", null, {
    params: {
      friendUserId: user.id,
    }
  });
  if (res?.code === 0) {
    Toast.success('添加好友成功');
  } else {
    Toast.fail(res.description);
  }
}
</script>

<style scoped>

</style>

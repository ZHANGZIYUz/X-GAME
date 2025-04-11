<template>
  <template v-if="user">
    <van-cell title="当前用户" :value="user?.username"/>
    <van-cell title="修改信息" is-link to="/user/update"/>
    <van-cell title="注销账号" @click="deleteAccount"/>
    <van-cell title="用户登出" @click="showDialog"/>
  </template>
</template>

<script setup lang="ts">
import {useRouter} from "vue-router";
import {onMounted, ref} from "vue";
import {getCurrentUser} from "../services/user";
import {Dialog, Toast} from 'vant';
import myAxios from "../plugins/myAxios";

const user = ref();

const router = useRouter();

onMounted(async () => {
  user.value = await getCurrentUser();
  if (user.value === null) {
    Toast.fail('请先登录!');
    await router.push('/user/login');
  }
})

const showDialog = () => {
  Dialog.confirm({
    title: '提示',
    message:
        '你确定要退出该账号么？',
  })
      .then(() => {
        console.log('对话框已关闭');
        myAxios.post('/user/logout');
        router.push('/user/login');
      })
      .catch(() => {
        // on cancel
      });
}

const deleteAccount = async () => {
  Dialog.confirm({
    title: '注销用户',
    message:
        '你确定要注销该账号么',
  })
      .then(() => {
        const res = myAxios.get('/user/deleteAccount')
        if (res.code === 0 && res.data) {
          Toast.success('注销账号成功');
          router.push('/user/login');
        } else {
          Toast.fail("删除失败");
        }
      })
      .catch(() => {
      });

};
</script>

<style scoped>

</style>

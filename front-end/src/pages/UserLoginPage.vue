<template>
  <van-tag
      type="primary"
      size="large"
      style="margin-top: 10px; margin-left: 25px; margin-bottom: 10px; font-size: 20px; font-weight: bold; color: #fff; text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.5);">
    欢迎来到
    <span style="
    font-size: 24px;
    font-weight: 900;
    background: linear-gradient(90deg, #ff8a00, #e52e71, #9c27b0);
    -webkit-background-clip: text;
    color: transparent;
    text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.6);
  ">
    X-GAME
  </span>
    游戏交流平台
  </van-tag>
  <van-image
      width="200"
      height="200"
      :src="image"
      style="margin-left: 90px"
  />

  <van-form @submit="onSubmit">
    <van-cell-group inset>
      <van-field
          v-model="userAccount"
          name="userAccount"
          label="账号"
          placeholder="请输入账号"
          :rules="[{ required: true, message: '请填写用户名' }]"
      />
      <van-field
          v-model="userPassword"
          type="password"
          name="userPassword"
          label="密码"
          placeholder="请输入密码"
          :rules="[{ required: true, message: '请填写密码' }]"
      />
    </van-cell-group>
    <div style="margin: 16px;">
      <van-button round block type="primary" native-type="submit">
        登录
      </van-button>
    </div>
    <div style="margin: 16px;">
      <van-button round block type="primary" @click="toRegister">
        如果没有账号，请点击此处注册
      </van-button>
    </div>
  </van-form>
</template>

<script setup lang="ts">
import {useRoute, useRouter} from "vue-router";
import {ref} from "vue";
import myAxios from "../plugins/myAxios";
import {Toast} from "vant";
import image from "../assets/image.png"

const router = useRouter();
const route = useRoute();

const userAccount = ref('');
const userPassword = ref('');

const onSubmit = async () => {
  const res = await myAxios.post('/user/login', {
    userAccount: userAccount.value,
    userPassword: userPassword.value,
  })
  console.log(res, '用户登录');
  if (res.code === 0 && res.data) {
    Toast.success('登录成功');
    // 跳转到之前的页面
    const redirectUrl = route.query?.redirect as string ?? '/';
    window.location.href = redirectUrl;
  } else {
    Toast.fail(res.message);
  }
};

const toRegister = () => {
  router.push('/user/register');
}

</script>

<style scoped>

</style>

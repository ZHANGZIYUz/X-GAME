<template>
  <van-form @submit="onSubmit">
    <van-cell-group inset>
      <van-field
          v-model="username"
          name="username"
          label="用户名"
          placeholder="用户名"
          :rules="[{ required: true, message: '请填写用户名' }]"
      />
      <van-field
          v-model="userAccount"
          name="userAccount"
          label="账号"
          placeholder="账号"
          :rules="[{ required: true, message: '请填写账号' }]"
      />
      <van-field
          v-model="password"
          type="password"
          name="userPassword"
          label="密码"
          placeholder="密码"
          :rules="[{ required: true, message: '请填写密码' }]"
      />
      <van-field
          v-model="confirmPassword"
          type="password"
          name="checkPassword"
          label="再次输入密码"
          placeholder="请再次输入密码"
          :rules="[
        { required: true, message: '请再次输入密码' },
      ]"
      />
      <van-field name="gender" label="性别">
        <template #input>
          <van-radio-group v-model="gender" direction="horizontal">
            <van-radio name="1">男</van-radio>
            <van-radio name="0">女</van-radio>
          </van-radio-group>
        </template>
      </van-field>
      <van-field
          v-model="message"
          name="userProfile"
          rows="2"
          autosize
          label="个人简介"
          type="textarea"
          maxlength="40"
          placeholder="请输入个人简介"
          show-word-limit
      />
      <van-tag plain type="primary" style="margin-bottom: 15px">请从下面这些标签中最多选择5个喜欢的游戏类型</van-tag>
      <van-checkbox-group v-model="tags" direction="horizontal" :max="5">
        <van-checkbox name="大型多人在线角色扮演游戏">大型多人在线角色扮演游戏 (MMORPG)</van-checkbox>
        <van-checkbox name="多人在线战术竞技游戏">多人在线战术竞技游戏 (MOBA)</van-checkbox>
        <van-checkbox name="第一人称射击游戏">第一人称射击游戏 (FPS)</van-checkbox>
        <van-checkbox name="生存游戏">生存游戏 (Survival Games)</van-checkbox>
        <van-checkbox name="沙盒游戏">沙盒游戏 (Sandbox Games)</van-checkbox>
        <van-checkbox name="战略游戏">战略游戏 (Strategy Games)</van-checkbox>
        <van-checkbox name="竞速游戏">竞速游戏 (Racing Games)</van-checkbox>
        <van-checkbox name="卡牌游戏">卡牌游戏 (Card Games)</van-checkbox>
        <van-checkbox name="音乐节奏游戏">音乐节奏游戏 (Rhythm Games)</van-checkbox>
        <van-checkbox name="体育游戏">体育游戏 (Sports Games)</van-checkbox>
        <van-checkbox name="动作角色扮演游戏">动作角色扮演游戏 (Action RPG)</van-checkbox>
        <van-checkbox name="动作冒险游戏">动作冒险游戏 (Action-Adventure)</van-checkbox>
        <van-checkbox name="格斗游戏">格斗游戏 (fighting Games)</van-checkbox>
      </van-checkbox-group>
    </van-cell-group>
    <div style="margin: 16px;">
      <van-button round block type="primary" native-type="submit">
        注册
      </van-button>
    </div>
  </van-form>

</template>

<script setup lang="ts">

import {ref} from "vue";
import myAxios from "./myAxios";
import {Toast} from "vant";
import {useRouter} from "vue-router";

const router = new useRouter();
const username = ref('');
const userAccount = ref('');
const password = ref('');
const confirmPassword = ref('');
const gender = ref('1');
const userProfile = ref('');
const tags = ref<string[]>([]); // 用户喜欢的游戏标签

const onSubmit = async (values) => {
  if (password.value !== confirmPassword.value) {
    Toast.fail('两次输入的密码不一致');
    return;
  }
  const user = {
    username: username.value,
    userAccount: userAccount.value,
    userPassword: password.value,
    gender: Number(gender.value),
    userProfile: userProfile.value,
    tags: tags.value.join(','),
    createTime: new Date(),
    userRole: 0
  };
  try {
    // 发送注册请求
    const res = await myAxios.post('/user/register', user);
    if (res.code === 0) {
      Toast.success('注册成功');
      await router.push('/user/login');
    } else {
      Toast.fail('注册失败：' + res.description);
    }
  } catch (error) {
    console.log(error);
  }
};

</script>

<style scoped>

</style>
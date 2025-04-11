<template>
  <div class="chat-container">
    <van-cell title="当前聊天对象" :value="userData?.username"/>
    <!-- 聊天消息展示 -->
    <div class="messages">
      <div v-for="(msg, index) in messages" :key="index" class="message">
        <span>{{ msg }}</span>
      </div>
    </div>

    <!-- 发送消息输入框 -->
    <van-cell-group inset>
      <van-field
          v-model="newMessage"
          placeholder="请输入消息..."
          clearable
          label="消息"
          @keyup.enter="sendMessage"
      >
        <template #button>
          <van-button size="small" type="primary" @click="sendMessage">发送</van-button>
        </template>
      </van-field>
    </van-cell-group>
  </div>
</template>

<script setup lang="ts">
import {onMounted, onUnmounted, ref} from 'vue';
import {useRoute} from "vue-router";
import myAxios from "../plugins/myAxios";
import {getCurrentUser} from "../services/user";

const route = useRoute();
const user = ref();
const currentUser = ref();
const userData = ref([]);
const newMessage = ref(''); // 发送的新消息
const messages = ref<string[]>([]); // 消息列表
let ws: WebSocket | null = null; // WebSocket 连接

// 建立 WebSocket 连接
onMounted(async () => {
  currentUser.value = await getCurrentUser();
  try {
    const id = route.query.userId; // 假设 id 是动态获取的
    const response = await myAxios.get("/user/searchOne", {
      params: {
        userOneId: id
      },
    });
    userData.value = response.data; //将请求返回的数据赋值给 userData
  } catch (error) {
    console.error("获取用户数据失败", error);
  }

  const records = await myAxios.get("/user/chat/record",{
    params:{
      currentUser: currentUser.value.username,
      receiveUser: userData?.value.username
    }
  });

  messages.value = records.data;

  ws = new WebSocket("ws://localhost:8080/api/user/chat");

  ws.onmessage = (event) => {
    messages.value.push(event.data); // 推入新消息到 messages 列表
  };

  ws.onclose = () => {
    console.log("WebSocket连接已关闭");
  };
});

// 发送消息
const sendMessage = () => {
  if (newMessage.value.trim() !== '' && ws) {
    const messageData = {
      username: userData?.value.username,
      content: newMessage.value
    };
    ws.send(JSON.stringify(messageData)); // 通过 WebSocket 发送消息
    newMessage.value = ''; // 发送后清空输入框
  }
};

// 在组件卸载时关闭 WebSocket 连接
onUnmounted(() => {
  if (ws) {
    ws.close();
    ws = null;
  }
});
</script>

<style>
.chat-container {
  display: flex;
  flex-direction: column;
  height: 85vh;
}

.messages {
  flex: 1;
  overflow-y: auto;
  padding: 16px;
  background-color: #f5f5f5;
}

.message {
  padding: 8px;
  margin-bottom: 8px;
  background-color: #e1f5fe;
  border-radius: 4px;
}

.van-field {
  margin-top: auto;
  padding: 16px;
  background-color: white;
}
</style>

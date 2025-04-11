<template>
  <!-- 分区选择器 -->
  <van-tabs v-model="activeSection" @change="fetchComments">
    <van-tab title="主机游戏" name="ps">
      <van-list
          finished-text="没有更多评论了"
          @load="fetchComments"
      >
        <van-cell
            v-for="comment in comments"
            :key="comment.id"
            :title="comment.userName"
            :label="comment.content"
        >
          <template #right-icon>
            <van-button
                size="small"
                type="primary"
                @click="addFriend(comment)"
            >
              添加好友
            </van-button>
          </template>
        </van-cell>
      </van-list>
      <div>
        <van-button
            type="primary"
            @click="showDialog"
            style="width: 90%;margin-left: 14px;margin-top: 10px; border-radius: 24px; font-size: 16px; padding: 12px 0; background: linear-gradient(90deg, #4facfe, #00f2fe);"
        >
          添加评论
        </van-button>
        <van-button
            type="primary"
            @click="gameRate"
            style="width: 90%; margin-left: 14px;margin-top: 10px; border-radius: 24px; font-size: 16px; padding: 12px 0; background: linear-gradient(90deg, #4facfe, #00f2fe);"
        >
          游戏打分
        </van-button>
        <van-dialog
            v-model:show="dialogVisible"
            show-confirm-button
            show-cancel-button
            confirm-button-text="提交"
            cancel-button-text="取消"
            @confirm="submitComment(section)"
        >
          <div style="text-align: center; margin-bottom: 10px; color: #333;">
            <p style="font-size: 16px; font-weight: 600; margin: 0;">请填写您的评论</p>
          </div>
          <input
              v-model="commentText"
              placeholder="请输入评论"
              style="width: 90%; padding: 8px; margin-top: 10px; margin-left: 8px; border: 1px solid #ccc; border-radius: 4px;"
          />
        </van-dialog>
      </div>
    </van-tab>
    <van-tab title="pc游戏" name="pc">
      <van-list
          finished-text="没有更多评论了"
          @load="fetchComments"
      >
        <van-cell
            v-for="comment in comments"
            :key="comment.id"
            :title="comment.userName"
            :label="comment.content"
        >
          <template #right-icon>
            <van-button
                size="small"
                type="primary"
                @click="addFriend(comment)"
            >
              添加好友
            </van-button>
          </template>
        </van-cell>
      </van-list>
      <div>
        <van-button
            type="primary"
            @click="showDialog"
            style="width: 90%; margin-left: 14px;margin-top: 10px;; border-radius: 24px; font-size: 16px; padding: 12px 0; background: linear-gradient(90deg, #4facfe, #00f2fe);"
        >
          添加评论
        </van-button>
        <van-button
            type="primary"
            @click="gameRate"
            style="width: 90%; margin-left: 14px;margin-top: 10px; border-radius: 24px; font-size: 16px; padding: 12px 0; background: linear-gradient(90deg, #4facfe, #00f2fe);"
        >
          游戏打分
        </van-button>
        <van-dialog
            v-model:show="dialogVisible"
            show-confirm-button
            show-cancel-button
            confirm-button-text="提交"
            cancel-button-text="取消"
            @confirm="submitComment(section)"
        >
          <div style="text-align: center; margin-bottom: 10px; color: #333;">
            <p style="font-size: 16px; font-weight: 600; margin: 0;">请填写您的评论</p>
          </div>
          <input
              v-model="commentText"
              placeholder="请输入评论"
              style="width: 90%; padding: 8px; margin-top: 10px; margin-left: 8px; border: 1px solid #ccc; border-radius: 4px;"
          />
        </van-dialog>
      </div>
    </van-tab>
    <van-tab title="手机游戏" name="phone">
      <van-list
          finished-text="没有更多评论了"
          @load="fetchComments"
      >
        <van-cell
            v-for="comment in comments"
            :key="comment.id"
            :title="comment.userName"
            :label="comment.content"
        >
          <template #right-icon>
            <van-button
                size="small"
                type="primary"
                @click="addFriend(comment)"
            >
              添加好友
            </van-button>
          </template>
        </van-cell>
      </van-list>
      <div>
        <van-button
            type="primary"
            @click="showDialog"
            style="width: 90%; margin-left: 14px;margin-top: 10px; border-radius: 24px; font-size: 16px; padding: 12px 0; background: linear-gradient(90deg, #4facfe, #00f2fe);"
        >
          添加评论
        </van-button>
        <van-button
            type="primary"
            @click="gameRate"
            style="width: 90%; margin-left: 14px;margin-top: 10px; border-radius: 24px; font-size: 16px; padding: 12px 0; background: linear-gradient(90deg, #4facfe, #00f2fe);"
        >
          游戏打分
        </van-button>
        <van-dialog
            v-model:show="dialogVisible"
            show-confirm-button
            show-cancel-button
            confirm-button-text="提交"
            cancel-button-text="取消"
            @confirm="submitComment(section)"
        >
          <div style="text-align: center; margin-bottom: 10px; color: #333;">
            <p style="font-size: 16px; font-weight: 600; margin: 0;">请填写您的评论</p>
          </div>
          <input
              v-model="commentText"
              placeholder="请输入评论"
              style="width: 90%; padding: 8px; margin-top: 10px; margin-left: 8px; border: 1px solid #ccc; border-radius: 4px;"
          />
        </van-dialog>
      </div>
    </van-tab>
    <van-tab title="二次元游戏" name="ecy">
      <van-list
          finished-text="没有更多评论了"
          @load="fetchComments"
      >
        <van-cell
            v-for="comment in comments"
            :key="comment.id"
            :title="comment.userName"
            :label="comment.content"
        >
          <template #right-icon>
            <van-button
                size="small"
                type="primary"
                @click="addFriend(comment)"
            >
              添加好友
            </van-button>
          </template>
        </van-cell>
      </van-list>
      <div>
        <van-button
            type="primary"
            @click="showDialog"
            style="width: 90%; margin-left: 14px;margin-top: 10px; border-radius: 24px; font-size: 16px; padding: 12px 0; background: linear-gradient(90deg, #4facfe, #00f2fe);"
        >
          添加评论
        </van-button>
        <van-button
            type="primary"
            @click="gameRate"
            style="width: 90%; margin-left: 14px;margin-top: 10px; border-radius: 24px; font-size: 16px; padding: 12px 0; background: linear-gradient(90deg, #4facfe, #00f2fe);"
        >
          游戏打分
        </van-button>
        <van-dialog
            v-model:show="dialogVisible"
            show-confirm-button
            show-cancel-button
            confirm-button-text="提交"
            cancel-button-text="取消"
            @confirm="submitComment(section)"
        >
          <div style="text-align: center; margin-bottom: 10px; color: #333;">
            <p style="font-size: 16px; font-weight: 600; margin: 0;">请填写您的评论</p>
          </div>
          <input
              v-model="commentText"
              placeholder="请输入评论"
              style="width: 90%; padding: 8px; margin-top: 10px; margin-left: 8px; border: 1px solid #ccc; border-radius: 4px;"
          />
        </van-dialog>
      </div>
    </van-tab>
  </van-tabs>
</template>

<script setup lang="ts">
import {onMounted, ref} from 'vue';
import {Toast} from 'vant';
import MyAxios from "../plugins/myAxios";
import myAxios from "../plugins/myAxios";
import {getCurrentUser} from "../services/user";
import {useRouter} from "vue-router";

const dialogVisible = ref(false);
const commentText = ref('');

const activeSection = ref(1);
const comments = ref([]);
const loading = ref(false);
const currentSectionId = ref();
const user = ref();
const router = new useRouter();

// 显示对话框的方法
const showDialog = () => {
  dialogVisible.value = true;
};

// 提交评论的方法
const submitComment = async (name) => {
  try {
    if (name === 'ps') {
      currentSectionId.value = 1;
    } else if (name === 'pc') {
      currentSectionId.value = 2;
    } else if (name === 'phone') {
      currentSectionId.value = 3;
    } else if (name === 'ecy') {
      currentSectionId.value = 4;
    }
    const response = await MyAxios.get('/user/add/comment', {
      params: {
        content: commentText.value,
        type: currentSectionId.value,
      }
    });
    if (response.code === 0) {
      Toast.success('评论已提交');
      window.location.reload();
      commentText.value = ''; // 清空输入框
      dialogVisible.value = false; // 关闭弹窗
    }
  } catch (error) {
    Toast.fail('提交失败，请重试');
  }
};

// 获取评论的函数
const fetchComments = async (name) => {
  if (name === 'ps') {
    currentSectionId.value = 1;
  } else if (name === 'pc') {
    currentSectionId.value = 2;
  } else if (name === 'phone') {
    currentSectionId.value = 3;
  } else if (name === 'ecy') {
    currentSectionId.value = 4;
  }
  try {
    const response = await MyAxios.get('/user/search/comment', {
      params: {
        type: currentSectionId.value,
      }
    });
    comments.value = response.data;
  } catch (error) {
    Toast.fail('获取评论失败');
  }
};

// 添加好友的函数
const addFriend = async (comment) => {
  const res = await myAxios.post("/user/friend/add", null, {
    params: {
      friendUserId: comment.userId,
    }
  });
  if (res?.code === 0) {
    Toast.success('添加好友成功');
  } else {
    Toast.fail(res.description);
  }
}

const gameRate = () => {
  router.push('/user/game/range')
}

// 页面加载时自动获取初始分区的评论
onMounted(async () => {
  user.value = await getCurrentUser();
  if (user.value === null) {
    Toast.fail('请先登录!');
    await router.push('/user/login');
  }
  await fetchComments();
});
// onMounted(fetchComments);
</script>

<style scoped>
/* 可以根据需要自定义样式 */
</style>

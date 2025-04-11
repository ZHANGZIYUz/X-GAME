<template>
  <div>
    <van-card
        v-for="game in gameList"
        :key="game.id"
        :title="game.gameName"
        :thumb="game.avatarUrl"
        class="game-card"
    >
      <template #desc>
        <div class="custom-desc">
          {{ game.profile }}
        </div>
      </template>

    <template #title>
        <div class="game-title">
          <span>{{ game.gameName }}</span>
          <span class="average-score">平均分：{{ game.averageScore.toFixed(1) }}</span>
        </div>
      </template>
      <template #footer>
        <van-config-provider :theme-vars="themeVars">
          <van-form>
            <van-field name="rate" label="评分" label-width="30" label-align="center">
              <template #input>
                <van-rate v-model="game.rate" />
                <van-button size="small" style="width: 100px;margin-left: 40px" @click="doScore(game)" block type="primary" native-type="submit">
                  打分
                </van-button>
              </template>
            </van-field>
          </van-form>
        </van-config-provider>
      </template>
    </van-card>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { useRoute, useRouter } from "vue-router";
import myAxios from "../plugins/myAxios";
import { Toast } from "vant";
import { getCurrentUser } from "../services/user";
import qs from "qs";

const route = useRoute();
const router = useRouter();

const themeVars = {
  rateIconFullColor: '#007aff',  // 蓝色填充星星
  buttonPrimaryBackgroundColor: '#007aff',  // 蓝色按钮背景
  buttonPrimaryBorderColor: '#007aff',  // 蓝色按钮边框
};

const gameList = ref([]);
const user = ref();
const gameType = ref<number | null>(null);

onMounted(async () => {
  gameType.value = route.query.type;
  user.value = await getCurrentUser();
  if (user.value === null) {
    Toast.fail('请先登录!');
    await router.push('/user/login');
  }

  const gameListData = await myAxios.get('/user/search/games', {
    params: { gameType: gameType.value }
  }).then(response => {
    console.log('/user/search/games succeed, response如下', response);
    return response?.data;
  }).catch(error => {
    console.error('/user/search/games error', error);
    Toast.fail('请求失败');
  });

  if (gameListData) {
    gameListData.forEach(game => game.rate = 1);
    gameList.value = gameListData;
  }
});

const doScore = (game) => {
  myAxios.post('/user/game/update', qs.stringify({
    gameId: game.id,
    score: game.rate,
  })).then(response => {
    console.log('/user/game/update succeed, response如下', response);
    Toast.success('打分成功');
    window.location.reload();
  }).catch(error => {
    console.error('/user/game/update error', error);
    Toast.fail('请求失败');
  });
};
</script>

<style scoped>
.game-card:hover {
  transform: scale(1.02); /* 悬停时放大效果 */
}

.game-title {
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: #007aff;
}

.average-score {
  font-weight: bold;
  color: #007aff; /* 设置平均分的颜色 */
}

.custom-desc {
  white-space: normal; /* 允许换行 */
  overflow: hidden; /* 隐藏超出部分 */
  text-overflow: ellipsis; /* 省略号处理 */
  max-height: 4.0em; /* 根据需要设置最大高度，调整显示行数 */
}
</style>

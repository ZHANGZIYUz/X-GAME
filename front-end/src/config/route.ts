// 定义一些路由
import Index from "../pages/Index.vue";
import Team from "../pages/TeamPage.vue";
import UserPage from "../pages/UserPage.vue";
import UserUpdatePage from "../pages/UserUpdatePage.vue";
import SearchPage from "../pages/SearchPage.vue";
import SearchResultPage from "../pages/SearchResultPage.vue";
import UserEditPage from "../pages/UserEditPage.vue";
import UserLoginPage from "../pages/UserLoginPage.vue";
import TeamAddPage from "../pages/TeamAddPage.vue";
import TeamUpdatePage from "../pages/TeamUpdatePage.vue";
import GamePage from "../pages/GamePage.vue";
import GameRangePage from "../pages/GameRangePage.vue";
import UserRegisterPage from "../plugins/UserRegisterPage.vue";
import TeamMemberPage from "../pages/TeamMemberPage.vue";
import UserChatPage from "../pages/UserChatPage.vue";
import GameCommunicationPage from "../pages/GameCommunicationPage.vue";


const routes = [
    {path: '/', component: Index},
    {path: '/team', title: '找队伍', component: Team},
    {path: '/team/add', title: '创建队伍', component: TeamAddPage},
    {path: '/team/update', title: '更新队伍', component: TeamUpdatePage},
    {path: '/team/search/member', title: '查找队伍成员', component: TeamMemberPage},
    {path: '/user', title: '个人信息', component: UserPage},
    {path: '/search', title: '找伙伴', component: SearchPage},
    {path: '/user/list', title: '用户列表', component: SearchResultPage},
    {path: '/user/edit', title: '编辑信息', component: UserEditPage},
    {path: '/user/login', title: '登录', component: UserLoginPage},
    {path: '/user/update', title: '更新信息', component: UserUpdatePage},
    {path: '/user/game', title: '游戏打分', component: GamePage},
    {path: '/user/game/range', title: '游戏分类', component: GameRangePage},
    {path: '/user/register', title: '用户注册', component: UserRegisterPage},
    {path: '/user/chat', title: '用户聊天', component: UserChatPage},
    {path: '/game/communicate', title: '游戏社区', component: GameCommunicationPage},
]

export default routes;

# X-GAME-
基于Vue3 + Spring Boot的移动端网站，实现了玩家搜索，按标签搜索玩家，和玩家加好友，在线聊天，游戏评分以 及论坛讨论等功能。

介绍视频：https://www.bilibili.com/video/BV1M2DQYcE2p/?vd_source=e671b63866212be16b768915b214872c

首先是该平台的登陆页面如下，用户可以选择登陆或者注册，该系统选择使用session存放用户的登录态（因为实现起来比较方便），并且写了一个方法专门获得用户的登录态，这样就可以确保用户是否处于登录状态了。

![image](https://github.com/user-attachments/assets/87ef62bb-b696-408e-8679-11f21bc48f95)

然后就是登录到这个系统里的具体页面了，该系统一共分为四个部分，首先是第一个部分的展示，在该页面用户可以看到自己添加的好友，并且跟他聊天，这里用的websocket实现了实时聊天的功能，右上角的搜索按钮可以根据标签进行用户的搜索。

![image](https://github.com/user-attachments/assets/c7023301-47bb-4303-8777-4181cc257ceb)

![image](https://github.com/user-attachments/assets/48f62971-53d4-49f4-a59b-a44cd471170f)

![image](https://github.com/user-attachments/assets/03418a6b-6648-46c0-bef5-16b577812747)

然后就是组队功能的实现。具体页面如下图所示，可以选择跟认识的人活着不认识人一起组队一起玩游戏。

![image](https://github.com/user-attachments/assets/b2302ab3-7d77-49f3-87a0-0a803638262c)

然后就是游戏社区页面，在这个页面不仅可以根据分区进行发言，比如你想找一个一起玩某某游戏的好友，你就可以在对应的分区下面发言，看有没有想跟你一起玩游戏的同学一起加好友玩。

![image](https://github.com/user-attachments/assets/d70ca828-aa35-433e-940d-223297949d2a)

最后一个页面就是个人页面了，在这个页面你可以选择修改用户的基础信息或者用户登出，也就是清空你存放在session里的登录态。

![image](https://github.com/user-attachments/assets/792c06d4-9d94-47cf-9404-d43f6aadcbd4)



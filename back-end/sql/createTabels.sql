create table game
(
    gameName     varchar(256) null comment '游戏名称',
    id           bigint auto_increment comment 'id'
        primary key,
    avatarUrl    varchar(256) null comment '游戏头像',
    number       int          null comment '打分人数',
    averageScore float        null comment '游戏平均分',
    type         int          not null comment '游戏类型',
    profile      varchar(256) null comment '游戏简介'
)
    comment '游戏';

create table team
(
    id          bigint auto_increment comment 'id'
        primary key,
    name        varchar(256)                       not null comment '队伍名称',
    description varchar(1024)                      null comment '描述',
    maxNum      int      default 1                 not null comment '最大人数',
    expireTime  datetime                           null comment '过期时间',
    userId      bigint                             null comment '用户id（队长 id）',
    status      int      default 0                 not null comment '0 - 公开，1 - 私有，2 - 加密',
    password    varchar(512)                       null comment '密码',
    createTime  datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime  datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    isDelete    tinyint  default 0                 not null comment '是否删除',
    hasJoinNum  int                                not null comment '队内已有人数'
)
    comment '队伍';

create table user
(
    username     varchar(256)                       null comment '用户昵称',
    id           bigint auto_increment comment 'id'
        primary key,
    userAccount  varchar(256)                       null comment '账号',
    avatarUrl    varchar(256)                       null comment '用户头像',
    gender       tinyint                            null comment '性别',
    userPassword varchar(512)                       null comment '密码',
    userStatus   int      default 0                 not null comment '状态 0-正常',
    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    isDelete     tinyint  default 0                 not null comment '是否删除',
    userRole     int      default 0                 not null comment '用户角色 0 - 普通用户 1 - 管理员',
    tags         varchar(1024)                      null comment '标签列表json',
    userProfile  varchar(512)                       null comment '个人简介'
)
    comment '用户';

create table user_friend_relation
(
    id           bigint auto_increment comment 'id'
        primary key,
    mainUserId   bigint                             null comment '主用户id',
    friendUserId bigint                             null comment '主用户好友id',
    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    isDelete     tinyint  default 0                 not null comment '是否删除'
)
    comment '用户好友列表';

create table user_team
(
    id         bigint auto_increment comment 'id'
        primary key,
    userId     bigint                             null comment '用户id',
    teamId     bigint                             null comment '队伍id',
    joinTime   datetime                           null comment '加入时间',
    createTime datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    isDelete   tinyint  default 0                 not null comment '是否删除'
)
    comment '用户队伍关系';

create table chat_record
(
    record      varchar(256) null comment '聊天记录',
    id          bigint auto_increment comment 'id'
        primary key,
    sendUser    varchar(256) null comment '发送用户',
    receiveUser varchar(256) null comment '接收用户'
)
    comment '聊天记录';

create table comment_record
(
    comment varchar(256) null comment '评论',
    id      bigint auto_increment comment 'id'
        primary key,
    type    int          not null comment '游戏类型',
    owner   varchar(256) null comment '评论发起人'
)
    comment '社区评论';

create table tag
(
    id         bigint auto_increment comment 'id'
        primary key,
    tagName    varchar(256)                       null comment '标签名称',
    userId     bigint                             null comment '用户 id',
    parentId   bigint                             null comment '父标签 id',
    isParent   tinyint                            null comment '0-不是, 1-父标签',
    createTime datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    isDelete   tinyint  default 0                 not null comment '是否删除',
    constraint uniIdx__tagName
        unique (tagName)
)
    comment '标签';

create index Idx__userId
    on tag (userId);
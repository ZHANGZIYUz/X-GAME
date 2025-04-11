/**
 * 用户类别
 */
export type UserType = {
    id: number;
    username: string;
    userAccount: string;
    avatarUrl?: string;
    userProfile?: string;
    gender:number;
    userRole: number;
    tags: string;
    createTime: Date;
};

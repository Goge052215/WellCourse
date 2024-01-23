import request from '@/utils/request';

// 敏感词分页
export async function sensitivePageSensitive(data) {
  return request.post('/sensitive/pageSensitive', data);
}

// 删除敏感词
export async function sensitiveDeleteSensitive(data) {
  return request.post('/sensitive/deleteSensitive', data);
}

// 添加敏感词
export async function sensitiveAddSensitive(data) {
  return request.post('/sensitive/addSensitive', data);
}

// 第一级插件
export async function plugFirstPlugList(data) {
  return request.post('/plug/firstPlugList', data);
}

// 查询插件详情
export async function plugPlugInfo(data) {
  return request.post('/plug/plugInfo', data);
}

// 第二级插件
export async function plugSecondPlugList(data) {
  return request.post('/plug/secondPlugList', data);
}

// 登录 email, password
export async function login(data) {
  return request.post('/login', data);
}

// 取消点赞收藏
export async function likecollectionCancellation(data) {
  return request.post('/likecollection/cancellation', data);
}

// 添加点赞收藏
export async function likecollectionIncrease(data) {
  return request.post('/likecollection/increase', data);
}

// 建议
export async function emailAddSuggestions(data) {
  return request.post('/email/addSuggestions', data);
}

// 添加评论
export async function commentAddComment(data) {
  return request.post('/comment/addComment', data);
}

// 删除评论
export async function commentDelComment(data) {
  return request.post('/comment/delComment', data);
}

// 第一级评论
export async function commentFirstComment(data) {
  return request.post('/comment/firstComment', data);
}

// 第二级评论
export async function commentSecondComment(data) {
  return request.post('/comment/secondComment', data);
}

// 新增帖子
export async function postEsAddPostEs(data) {
  return request.post('/postEs/addPostEs', data);
}

// 删除帖子
export async function postEsDeletePostEs(data) {
  return request.post('/postEs/deletePostEs', data);
}

// 我的帖子列表
export async function postEsMyPostEsPage(data) {
  return request.post('/postEs/myPostEsPage', data);
}

// 修改帖子
export async function postEsUpdatePostEs(data) {
  return request.post('/postEs/updatePostEs', data);
}

// 帖子详情
export async function postEsPostEs(data) {
  return request.post('/postEs/postEs', data);
}

// 帖子列表
export async function postEsPostEsPage(data) {
  return request.post('/postEs/postEsPage', data);
}

// 用户详情页列表
export async function postEsPostEsPageByUser(data) {
  return request.post('/postEs/postEsPageByUser', data);
}

// 禁用帖子
export async function postEsUpdatePostEsStatus(data) {
  return request.post('/postEs/updatePostEsStatus', data);
}


// 图片附件上传
export async function upload(data) {
  return request({
    url: '/upload',
    method: 'post',
    data,
    isFormData: true
  });
}

// 清空消息
export async function messageDeleteAll(data) {
  return request.post('/message/deleteAll', data);
}

// 消息列表
export async function messageMessageList(data) {
  return request.post('/message/messageList', data);
}

// 读消息
export async function messageReadMessage(data) {
  return request.post('/message/readMessage', data);
}

// 学校科目
export async function schoolSchoolDisciplinePage(data) {
  return request.post('/school/schoolDisciplinePage', data);
}

// 学校分页列表
export async function schoolListPage(data) {
  return request.post('/school/listPage', data);
}

// 学科分页列表listPage
export async function disciplineListPage(data) {
  return request.post('/discipline/listPage', data);
}

// 用户注册
export async function userRegister(data) {
  return request.post('/user/register', data);
}

// 找回密码
export async function userRetrievePassword(data) {
  return request.post('/user/retrievePassword', data);
}

// 发送邮箱验证码
export async function userSendEmailCode(data) {
  return request.post('/user/sendEmailCode', data);
}

// 修改用户信息
export async function userUpdateUserInfo(data) {
  return request.post('/user/updateUserInfo', data);
}

// 修改用户密码
export async function userUpdateUserPassword(data) {
  return request.post('/user/updateUserPassword', data);
}

// 修改用户状态
export async function userUpdateUserStatus(data) {
  return request.post('/user/updateUserStatus', data);
}

// 查询用户信息
export async function userUserInfo(data) {
  return request.post('/user/userInfo', data);
}

// 用户列表
export async function userUserPage(data) {
  return request.post('/user/userPage', data);
}

// 查询用户信息
export async function userUserInfoByEmail(data) {
  return request.post('/user/userInfoByEmail', data);
}
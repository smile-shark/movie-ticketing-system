# 电影购票系统（movie-ticketing-system）
电影购票系统：包含客户端、电影院管理端、平台管理端

项目参考：https://cloud.axureshop.com/48L08O

## 数据库结构设计图

![数据库结构设计图](./image/看吧电影数据库设计.jpg)

## 需求分析

### 用户端

1. 登录（页面）
   1. 输入用户邮箱和密码进行登录
      1. 验证邮箱和密码的格式正确
      2. 后端验证账户：
         1. 账户释放存在
         2. 账户是否可用
         3. 账户密码是否正确
         4. 返回账户数据
      3. 将返回的账户数据存储在浏览器本地
   2. 记住我：
      1. 功能实现：将状态记录到本地浏览器，进入登录页面的时候进行读取
2. 注册（页面）
   1. 输入邮箱和密码进行注册
      1. 获取验证码：
         1. 验证邮箱格式正确性	
            1. 弹出图片验证码进行真人验证
         2. 发送邮件到用户的信箱
      2. 邮箱密码格式验证
      3. 同意协议验证
      4. 后台创建账户流程：
         1. 判断是否已有该账户
         2. 判断验证码是否正确
         3. 密码的合法性
         4. 设置初始的用户名和头像
      5. 注册成功后将数据存储到浏览器本地，然后转跳到登录页面进行自动登录
3. 首页（页面）
4. 影片（页面）
5. 影院（页面）
6. 搜索展示（页面）

### 影院管理端

### 平台管理端






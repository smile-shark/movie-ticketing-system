# 电影购票系统（movie-ticketing-system）
页面布局参考：https://cloud.axureshop.com/48L08O

> 电影购票系统是一个综合性的在线平台，旨在为用户提供便捷的电影购票服务，同时为电影院和平台管理员提供高效的管理工具。该系统分为三个主要部分：用户端、影院管理端和平台管理端。
>

##### 后端配置文件构成

**aliyunoss-config.properties：**阿里云oss服务配置：

* 因为安全策略，这个文件是没有上传到git库的

* ```
  oss.endpoint=oss-cn-chengdu.aliyuncs.com # 我使用的是成都的
  oss.bucketName=public-file-container #容器名称
  oss.fileUrlPrefix=https://public-file-container.oss-cn-chengdu.aliyuncs.com/ # 我使用的是成都的
  oss.accessKeyId=accessKeyId # 唯一的标识符
  oss.accessKeySecret=accessKeySecret # 密钥
  ```

**email-config.properties：**邮件的发送配置

* 因为隐私问题，这个文件是没有上传到git库的

* ```
  email.username=邮箱
  email.password=应用密码
  mail.smtp.host=smtp服务器
  mail.smtp.port=端口
  mail.smtp.auth=true
  mail.smtp.starttls.enable=true
  ```

**KEY.properties：**密钥配置

* ```
  AES_KEY=movie-ticketing-project # AES加密密钥
  JWT_KEY=b8942cc3878a41659aaacc9b151399c4 # JWT加密密钥
  JWT_EXPIRATION_TIME=36000000 # JWT有效时长
  ```

**mybaits-config.properties：**数据库配置

* ```
  datasource.host=localhost # 需要修改可以更换
  datasource.port=3306 # 需要修改可以更换
  datasource.database=movie_ticketing_system # 项目指定的数据库名是这个在表结构中有
  datasource.another=useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8&allowPublicKeyRetrieval=true
  
  druid.driverClassName=com.mysql.cj.jdbc.Driver
  druid.url=jdbc:mysql://${datasource.host:localhost}:${datasource.port:3306}/${datasource.database}?${datasource.another}
  druid.username=root # 需要修改可以更换
  druid.password=123456 # 需要修改可以更换
  ```

## 数据库结构设计图

![数据库结构设计图](./image/看吧电影数据库设计.jpg)

## 需求分析

### 用户端：（多端可用）

为了扩大用户群体、提升用户体验和适应移动互联网发展趋势，我设计了适配web端(`2560px`)的和移动端的ui(`768px`)

**移动端部分展示图：**

<div style="display: flex;">
  <img src="image\image-20250509090845735.png" alt="image-20250509090845735" style="zoom:50%;">
  <img src="image/image-20250508175908929.png" alt="image-20250508175908929" style="zoom:50%;">
</div>

登录（页面）

1. 1. ![image-20250418171649543](./image/image-20250418171649543.png)
   2. 输入用户邮箱和密码进行登录
      1. 验证邮箱和密码的格式正确
      2. 后端验证账户：
         1. 账户是否存在
         2. 账户是否可用
         3. 账户密码是否正确
         4. 返回账户数据
      3. 将返回的账户数据存储在浏览器本地
   3. 记住我：
      1. 功能实现：将状态记录到本地浏览器，进入登录页面的时候进行读取
   
2. 注册（页面）
   1. 
   2. 输入邮箱和密码进行注册
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
   1. ![image-20250418172236419](./image/image-20250418172236419.png)

4. 影片（页面）
   1. ![image-20250421104637900](./image/image-20250421104637900.png)

5. 评分榜（页面）

   ![image-20250507100335319](./image/image-20250507100335319.png)

6. 搜索展示（页面）

### 影院管理端

首页：

![image-20250507100506054](./image/image-20250507100506054.png)

放映厅管理：
![image-20250507100555806](image/image-20250507100555806.png)

售票状况：

![image-20250507100647662](image/image-20250507100647662.png)

影院信息管理：


### 平台管理端

1. 登录（页面）
   1. 输入设定账号和密码进行登录
      1. 验证账号和密码的格式正确
      2. 后端验证账户：
         1. 账户是否存在
         2. 账户密码是否正确
         3. 返回账户数据
      3. 将返回的账户数据存储在浏览器本地
   2. 记住我：
      1. 功能实现：将状态记录到本地浏览器，进入登录页面的时候进行读取

2. 首页（页面）

   ![image-20250507173958165](image/image-20250507173958165.png)

3. 电影管理：
   1. 电影信息列表（页面）：分页，可根据信息查询

   2. 新增电影（页面）：默认一个模板，可以修改和上传图片
      1. ![image-20250418172415146](./image/image-20250418172415146.png)

   3. 电影信息校正（页面）：没有选择需要修改的电影，就让用户选择电影

   4. 电影类型管理

      ![image-20250422170754458](./image/image-20250422170754458.png)

4. 用户管理：
   1. 用户列表
      1. ![image-20250422170849720](./image/image-20250422170849720.png)
   2. 用户详情
      1. ![image-20250422170915235](./image/image-20250422170915235.png)

5. 其他管理:
   1. 用户轮动管理：
      1. ![image-20250422170955201](./image/image-20250422170955201.png)

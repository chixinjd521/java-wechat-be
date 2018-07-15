# java-wechat-be

## 简介

> 使用 java 编写的微信后台 web 项目，其主要功能是对 [Mojo-Weixin](https://github.com/sjdy521/Mojo-Weixin) 的 [Openwx](https://github.com/sjdy521/Mojo-Weixin/blob/master/Controller-API.md) 接口进行后台封装，提供用户登录校验、websocket和跨域权限配置等功能。
> 
> 前端使用 [vue-wechat-fe](https://github.com/dadiyang/vue-wechat-fe) 项目

## 已实现功能

1. 用户登录
2. 微信扫码登录
3. 实时显示当前微信状态
4. 信息收发和群发
5. 查看好友信息
6. 好友分组
7. 通过好友显示名称筛选，快速定位
8. 群发且由 websocket 实时反馈群发进度


## 安装方式

### 依赖

1. [Mojo-weixin](https://github.com/sjdy521/Mojo-Weixin)
2. [**maven**](http://maven.apache.org/download.cgi)
3. redis
4. sqlite

### 安装 (Linux/MacOS)

本项目通过 [**maven**](http://maven.apache.org/download.cgi) 来管理，使用**`jetty`**插件的方式来启动。**pom.xml** 文件中已经指定了 aliyun 镜像，以加快依赖加载速度。

* 安装 [Mojo-weixin](https://github.com/sjdy521/Mojo-Weixin)，此项目有非常好的文档，请直接参照安装并启动

* 安装 maven

```bash
# 下载安装包
wget http://mirror.bit.edu.cn/apache/maven/maven-3/3.5.2/binaries/apache-maven-3.5.2-bin.tar.gz
# 解压
tar -zxvf apache-maven-3.5.2-bin.tar.gz 
cd apache-maven-3.5.2-bin.tar.gz 
# 拿到当前路径，假设得到的路径是 /var/local/apache-maven-3.5.2
pwd
# 配置环境变量
vim /etc/profile
# 在 /etc/profile 文件后面添加 MAVEN_HOME 设置刚刚拿到的路径
export MAVEN_HOME=/var/local/apache-maven-3.5.2
export MAVEN_HOME
export PATH=$PATH:$MAVEN_HOME/bin
# 加载
source /etc/profile
# 验证结果
mvn -version
```


* 安装 redis

```bash
# centos
yum install redis
# ubuntu
apt-get install redis
# macOS
brew install redis
# 启动
redis-server /etc/redis.conf
```

* sqlite Linux系统一般都内置了 sqlite，无需另外安装

启动

```bash
# 克隆本项目
git clone https://github.com/dadiyang/java-wechat-be.git
# 进入项目根目录
cd java-wechat-be
# 在当前用户根目录创建配置文件目录
mkdir ~/java-wechat-be
# 将需要自定义配置的配置文件复制过去
cp -r src/main/resources/conf ~/java-wechat-be/
# 启动
mvn jetty:run
```
默认端口是 8282，项目启动后可以访问 http://localhost:8282/home 进入项目

### 配置文件说明：

#### server.properties - 项目配置文件

* **cryptRule** 密码认证密钥
* **psSignKeyPre** 用户签名存放前缀
* **authCookieName** 登录成功后给 cookie 写入的键名
* **openwxServer** [Mojo-weixin](https://github.com/sjdy521/Mojo-Weixin) 的服务端地址
* **allowOrigins** 允许跨域的域名
* **cdnPath** js/css等静态资源文件所在远程地址，为空则读取 webapp/resources
* **debugUser** 用于调试的用户账号密码，加上此配置则只支持单用户，且不会读取数据库进行用户校验
* **loginUrl** 身份检验不过通，跳转到的登录链接

#### redis.properties - redis服务相关配置

只需关注：

* **redis.host** 服务地址
* **redis.port** 服务端口号
* **redis.password** 密码
* **redis.db** 数据库编号

#### jdbc.properties - 数据库相关配置

默认使用 sqlite 做为数据库，目前主要用于存放用户信息，可以根据自己的习惯选择不同的数据库，目前可选  mysql，其他数据库需要自己引入驱动

#### wx.db - sqlite数据库

包含 system_user 表，里面存放了一个默认的用户

>账号：dadiyang
>
>密码：ps1234

建表语句

```sql
CREATE TABLE system_user(
  `id`  INTEGER PRIMARY KEY AUTOINCREMENT,
  `username` VARCHAR(30) NOT NULL,
  `password` VARCHAR(32) NOT NULL,
  `real_name` VARCHAR(10) NOT NULL,
  `phone` VARCHAR(11) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `create_time` TIMESTAMP NOT NULL, 
  `update_time` TIMESTAMP default (datetime('now', 'localtime')),
  `is_disabled` TINYINT(1) NOT NULL DEFAULT 0
);
```

新增用户 sql，其中密码使用 MD5 加密，加密方式: ***MD5(密码{密钥})***

例如：默认的密钥是 sys，密码为 ps1234，则数据库中的密码为: **ps1234{dadiyang}** 的 MD5值

```sql
INSERT INTO 
system_user(`username`, `PASSWORD`, `real_name`, `phone`, `email`, `create_time`, `update_time`, `is_disabled`) 
VALUES('sys', '00fd846cccdf3b67536359a6d3cb9ef6', '未知数', '13899990000', 'dadiyang@aliyun.com', julianday('now', 'localtime'), julianday('now', 'localtime'), 0);
```



# QQBot 
 因为CoolQ停止运营 本项目暂停维护

> 基于CoolQ HTTP API(https://cqhttp.cc/) 插件实现的Java版本机器人。

## 环境搭建和框架
1. Redis
2. MySQL
3. Docker
4. JDK11
5. Spring Boot 2.1.3
## 依赖三方
1. 百度AI
2. 图灵API
3. 翻译API 
## 现有如下功能
*  给BOT下所有QQ群里的所有成员点赞 默认开启 （相传点赞功能容易封号）
*  每天向群友和私聊进行早安和晚安的问候
*  每天午夜查询游戏内在线玩家并通报到QQ群并对服务器控制台发送提示消息
*  查看Minecraft游戏内的在线玩家
*  使用Minecraft RCON协议与控制台进行通讯
*  群复读功能并随机根据群员复读次数而复读 默认开启
*  使用翻译API 支持日语模式
*  使用百度AI SDK 识别图片
*  使用图灵API 可以在群里@bot或私聊进行聊天
*  使用/help查看关于bot的功能
## 咕咕咕（待开发）
* 使用集中配置
* websocket和HTTP请求，可进行配置随意切换
* Minecraft RCON Console 通讯Byte处理待优化
* Minecraft 服务器 Web 官网待开发
* MySQL多数据源配置

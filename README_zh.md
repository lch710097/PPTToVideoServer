

简体中文 | [English](README.md)


# 🎨 PPTToVideoServer
> PPTToVideoServer,一个基于SpringBoot开发的PPT转数字人视频.您可以上传一段声音、一个头像、一个PPT。可以将PPT讲解文本转为设置的头像和声音讲解的视频。

<b>Try it online👉：[http://ppt.quickmap.net](http://ppt.quickmap.net) 暂时不支持导出视频</b>

<b>演示视频： </b>


<video id="video" controls="" preload="none" poster="封面">
      <source id="mp4" src="/public/jiangjie.mp4" type="video/mp4">
</video>


# ✨ 项目特色
1. 易开发：基于SpringBoot + Mysql 构建，功能扩展更方便。

# 🚀 安装
```

mysql中创建数据库 ppttovideodb
将sql/ppttovideodb.sql导入数据库
修改src/main/resources/application.properties中的数据库参数 和服务端口
AI 声音克隆的服务地址(整理完成后，将上传下载包到网盘)
AI 数字人的服务地址(整理完成后，将上传下载包到网盘)
deepseek 的服务地址和密匙 模型

maven install

java -jar ppttovhvideo-0.0.1-SNAPSHOT.jar 启动服务

前端已经打包在static下面，
 
```
浏览器访问：http://127.0.0.1:8888

如果需要修改前端访问[https://github.com/lch710097/PPTToVideo]

# 📚 功能列表
### 基础功能
- 上传PPT
- 设置PPT页面讲解文稿
- AI生成讲解文稿
- 修改、删除PPT页
- 设置虚拟人、虚拟人位置
- 设置语言
- 导出视频
- 管理虚拟人
- 管理语言
- 用户管理
- AI生成PPT参见 [https://github.com/pipipi-pikachu/PPTist](https://github.com/pipipi-pikachu/PPTist)
 

 

# 📄 版权声明/开源协议
[Apache License 2.0](https://github.com/lch710097/PPTToVideoServer/blob/master/LICENSE) | Copyright © 2020-PRESENT [lch710097](https://github.com/lch710097)
 
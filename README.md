<p align="center">
    <img src='/public/vite.svg' />
</p>

<p align="center">
    <a href="https://github.com/lch710097/PPTToVideoServer/stargazers" target="_black"><img src="https://img.shields.io/github/stars/lch710097/PPTToVideoServer?logo=github" alt="stars" /></a>
    <a href="https://github.com/lch710097/PPTToVideoServer/network/members" target="_black"><img src="https://img.shields.io/github/forks/lch710097/PPTToVideoServer?logo=github" alt="forks" /></a>
    <a href="https://github.com/lch710097/PPTToVideoServer/blob/master/LICENSE" target="_black"><img src="https://img.shields.io/github/license/lch710097/PPTToVideoServer?color=%232DCE89&logo=github" alt="license" /></a>
     <a href="https://github.com/lch710097/PPTToVideoServer/PPTist/issues" target="_black"><img src="https://img.shields.io/github/issues-closed/lch710097/PPTToVideoServer.svg" alt="issue"></a>
</p>

[简体中文](README_zh.md) | English


# 🎨 PPT2VHVideoServer
> PPT2VHVideo, A SpringBoot application. This application change ppt to   Digital Human video.

<b>Try it online👉：[http://ppt.quickmap.net](http://ppt.quickmap.net)</b>

<b>Explain video: </b>

<video id="video" controls="" preload="none" poster="封面">
      <source id="mp4" src="/public/jiangjie.mp4" type="video/mp4">
</video>


# ✨ Highlights
1. <b>Easy Development</b>: Built with SpringBoot and MySQL.
 

# 🚀 Installation
```
maven install


mysql create db "ppttovideodb"
import sql/ppttovideodb.sql
change "src/main/resources/application.properties" db params and port
set AI  voice clone server ( quark: 链接：https://pan.quark.cn/s/fa26cc167e7b  提取码：PEv7 )  need 6G GPU to run 
set AI   Digital Human Server ( quark:链接：https://pan.quark.cn/s/82decf325fdc 提取码：rbd6)  need 16G GPU to run
deepseek serverurl  、key 、model

maven install

java -jar ppttovhvideo-0.0.1-SNAPSHOT.jar start server

front end package under "static",
 
```
Browser access : http://127.0.0.1:8888 

If you want modify front end visit  [https://github.com/lch710097/PPTToVideo]

# 📚 Features
### Basic Features
- Upload PPT
- Set PPT Page Content
- Upload Voice
- Upload Protait
- Export to Video  
- AI generator content
- AI generator PPT [https://github.com/pipipi-pikachu/PPTist](https://github.com/pipipi-pikachu/PPTist)

# 👀 FAQ
Some common problems: [FAQ](/doc/Q&A.md)

 

# 📄 License
[Apache License 2.0](https://github.com/lch710097/PPTToVideoServer/blob/master/LICENSE) | Copyright © 2020-PRESENT [lch710097](https://github.com/lch710097)
 
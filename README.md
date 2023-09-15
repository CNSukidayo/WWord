<h1 align="center" >
    <b>WWord</b>
</h1>
<p align="center">
    <a href="https://openjdk.org/projects/jdk/17/"><img alt="GitHub last commit" src="https://img.shields.io/badge/JDK-17-red.svg?style=flat&logo=Oracle&labelColor=2B9C4C&color=DC2500" /></a>
    <a href="https://github.com/CNSukidayo/WWord/commits"><img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/CNSukidayo/WWord" /></a>
    <a href="https://github.com/CNSukidayo/WWord/blob/master/LICENSE"><img alt="GitHub last commit" src="https://img.shields.io/badge/license-GPL 3.0-4EB1BA" /></a>
</p>

### 介绍

万语词,正如其名它致力于提供一套统一的逻辑完成任意语言词汇的学习.
引入独创的单词标记技术方便用户更好地对遗忘知识进行查漏补缺,
用户可以在该平台发布Markdown格式的帖子分享语言学习中的各种心得.
项目基于SpringBoot+Mybatis实现,采用Docker容器化部署.
包含管理员模块、权限模块、核心功能模块、搜索模块等.

### 软件架构

![软件架构](images/design.png)

### 模块划分

* **注册中心**:nacos
* **网关gateway**:Gateway 聚合所有的接口,统一接受处理前端的请求;并且在转发前异步调用权限模块进行鉴权.
* **公共模块**:将所有服务模块需要的功能单独抽离(包括全局异常响应和异常处理,过滤器).
* **实体类模块**:通过拆分为前端、公共、后端三个部分的实体类模块,使得系统各层之间相互独立解耦.
* **鉴权模块**:用户注册、获取用户信息、判断用户是否有目标接口权限、角色管理、角色分配接口权限等.
* **核心模块**:包括用户收藏夹功能管理、markdown帖子管理
* **管理员模块**:单词划分管理、导入单词管理
* **搜索模块**:ElasticSearch单词搜索、更新ES列表

### 界面展示
[仓库地址:https://github.com/CNSukidayo/AnyLanguageWord](https://github.com/CNSukidayo/AnyLanguageWord)
<table border="0px">
    <tr>
        <img src="images/home_page_1.png" alt="登陆界面" />
    </tr>       
    <tr>
        <img src="images/home_page_2.png" alt="背词界面" />
    </tr>    
    <tr>
        <img src="images/home_page_3.png" alt="收藏夹和markdown显示" />
    </tr>    
</table>
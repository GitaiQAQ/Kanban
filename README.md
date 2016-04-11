# PhuckQQ

## 关于本系列

> 本系列将采用
>
> MUST：`java`/`lua`
>
> MAYBE：`C++`/`JavaScript`
>
> 等语言开发,建议先去补完 **MUST** 部分

## 前言

为了完善看板娘（kanban[at]gitai.me），而开启本坑(kanban3)，直接用`Xposed`注入jvm虚拟机，暴露相关方法，采用`Lua`作为插件

> **看板娘**基于webQQ协议的由nodejs(初代目)/python(二代目)支持的可拓展的机器人姬
>
> **Xposed** 框架是一款可以在不修改APK的情况下影响程序运行（修改系统）的框架服务，基于它可以制作出许多功能强大的模块，且在功能不冲突的情况下同时运作。 当前，Per APP Setting（为每个应用设置单独的dpi或修改权限）、Cydia、XPrivacy（防止隐私泄露）、对原生Launcher替换图标等应用或功能均基于此框架。([少数派](http://sspai.com/24538))
>
> **Lua**  是一门强大、快速、轻量的嵌入式脚本语言。它由巴西里约热内卢 Pontifical Catholic 大学的 PUC-Rio 团队 开发。 Lua 是一个 自由软件， 广泛应用于世界上无数产品和项目。(http://www.lua.org/)

 **注意**：考虑以下问题

 1. 反射(invoke)的特殊性
 2.  QQ 频繁升级
 3. Android的代码混淆
 4. 其他

在此选择 [`QQ日本版-4.6.17`](http://www.coolapk.com/apk/com.tencent.qq.kddi) 或者 [`QQ国际版-5.0.11`](http://www.coolapk.com/apk/com.tencent.mobileqqi) 实现需求

## 计划

1. [![MVP](https://img.shields.io/badge/MVP-100%25-brightgreen.svg)](../04/mvp)
2. [![SendMessage](https://img.shields.io/badge/SendMessage-100%25-brightgreen.svg)](../11/createTextMessageToshow)
3. [![ReceiveMessage](https://img.shields.io/badge/ReceiveMessage-100%25-brightgreen.svg)](../12/MessageHandlerHook)
4. [![Unpack](https://img.shields.io/badge/Unpack-0%25-yellowgreen.svg)]()
5. [![Pack](https://img.shields.io/badge/Pack-0%25-yellowgreen.svg)]()
6. [![Ops](https://img.shields.io/badge/Ops-0%25-yellowgreen.svg)]()
7. [![Common Interface](https://img.shields.io/badge/CI-0%25-yellowgreen.svg)]()


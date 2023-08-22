# 微信OPEN SDK Compose Multiplatform封装

**<font color=red>这个library只是对官方SDK的一个封装，在阅读本文档前，请先仔细阅读[官方文档](https://developers.weixin.qq.com/doc/oplatform/Mobile_App/Access_Guide/iOS.html)</font>**

## 怎么配置？

在:shared模块下的build.gradle.kts增加如下配置

```kts
implementation("")
```

## iOS

根据[官方文档](https://developers.weixin.qq.com/doc/oplatform/Mobile_App/Access_Guide/iOS.html)
配置好Universal Links、URL scheme、LSApplicationQueriesSchemes

### API

在程序入口处调用register方法注册APP到微信，可以设置selfCheck参数是否开启自检

```kotlin
Wechat.register(appID, universalLink, selfCheck = false)
```

#### 分享与收藏

```kotlin
/**
 * 分享
 *
 * @param mediaMessage [MediaMessage]和官方属性是一一对应的WXMediaMessage，其中mediaObject也是和官方一一对应的
 * @param scene [WXScene]分享场景
 */
Wechat.share(mediaMessage, scene)
```
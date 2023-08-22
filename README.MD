# 微信OPEN SDK Compose Multiplatform封装

## 使用之前
**<font color=red>这个library只是对官方SDK的一个封装，在阅读本文档前，请先仔细阅读[官方文档](https://developers.weixin.qq.com/doc/oplatform/Mobile_App/Access_Guide/iOS.html)</font>**

当前集成的Open SDK版本
- Android:6.8.24
- iOS:2.0.2

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

#### 支付
```kotlin
/**
     * 拉起支付
     *
     * @param partnerId
     * @param prepayId
     * @param packageStr
     * @param nonceStr
     * @param timeStamp
     * @param sign
     */
Wechat.pay(partnerId,prepayId,packageStr,nonceStr, timeStamp,sign)
```


#### 微信登录
```kotlin
/**
     *
     *
     * @param state 用于保持请求和回调的状态，授权请求后原样带回给第三方。该参数可用于防止 csrf 攻击（跨站请求伪造攻击），建议第三方带上该参数，可设置为简单的随机数加 session 进行校验。在state传递的过程中会将该参数作为url的一部分进行处理，因此建议对该参数进行url encode操作，防止其中含有影响url解析的特殊字符（如'#'、'&'等）导致该参数无法正确回传。
     *
     */
Wechat.auth( state)
```

#### 拉起小程序

```kotlin
/**
     * 拉起小程序
     *
     * @param userName 拉起的小程序的username
     * @param miniProgramType 拉起小程序的类型[MiniProgramType]
     * @param path 拉起小程序页面的可带参路径，不填默认拉起小程序首页，对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"。
     */
Wechat.launchMiniProgram(userName, miniProgramType, path)
```

#### 拉起微信客服
```kotlin

/**
     * 拉起微信客服
     *
     * @param corpId 企业ID
     * @param url 客服URL
     */
Wechat.launchCustomerService(corpId,url)
```
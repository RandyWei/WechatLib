# 微信OPEN SDK For Compose Multiplatform

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

### iOS

根据[官方文档](https://developers.weixin.qq.com/doc/oplatform/Mobile_App/Access_Guide/iOS.html)
配置好Universal Links、URL scheme、LSApplicationQueriesSchemes

### Android

如果需要混淆代码，为了保证 sdk 的正常使用，需要在 proguard.cfg 加上下面两行配置：
```text
-keep class com.tencent.mm.opensdk.** {
    *;
}

-keep class com.tencent.wxop.** {
    *;
}

-keep class com.tencent.mm.sdk.** {
    *;
}
```

## API

在程序入口处调用register方法注册APP到微信

```kotlin
//iOS,可以设置selfCheck参数是否开启自检
Wechat.register(appID, universalLink, selfCheck = false)
//android
Wechat.register(context, appId)
```

### 分享与收藏

```kotlin
/**
 * 分享
 *
 * @param mediaMessage [MediaMessage]和官方属性是一一对应的WXMediaMessage，其中mediaObject也是和官方一一对应的
 * @param scene [WXScene]分享场景
 */
Wechat.share(mediaMessage, scene)
```

### 支付
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


### 微信登录
```kotlin
/**
     *
     *
     * @param state 用于保持请求和回调的状态，授权请求后原样带回给第三方。该参数可用于防止 csrf 攻击（跨站请求伪造攻击），建议第三方带上该参数，可设置为简单的随机数加 session 进行校验。在state传递的过程中会将该参数作为url的一部分进行处理，因此建议对该参数进行url encode操作，防止其中含有影响url解析的特殊字符（如'#'、'&'等）导致该参数无法正确回传。
     *
     */
Wechat.auth( state)
```

### 拉起小程序

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

### 拉起微信客服
```kotlin

/**
     * 拉起微信客服
     *
     * @param corpId 企业ID
     * @param url 客服URL
     */
Wechat.launchCustomerService(corpId,url)
```

## 对象说明

### [MediaMessage](Wechat/src/commonMain/kotlin/icu/bughub/kit/multiplatform/wechat/MediaMessage.kt)（微信媒体消息内容）说明

这个对应官方的WXMediaMessage

|字段|类型|含义|备注|
|-|-|-|-|
|sdkVer|Int|版本号|仅Android平台|
|title|String|消息标题|限制长度不超过512Bytes|
|description|String|描述内容|限制长度不超过1KB|
|thumbData|ByteArray|缩略图的二进制数据|限制内容大小不超过32KB|
|mediaObject|MediaObject|多媒体数据对象|可以为[TextObject](#textobject-mediaobject-的派生类用于描述一个文本对象)、[ImageObject](#imageobject-mediaobject-的派生类用于描述一个文本对象)、[VideoObject]((#videoobject-mediaobject-的派生类用于描述一个文本对象))、[WebpageObject]((#webpageobject-mediaobject-的派生类用于描述一个文本对象))、[MusicVideoObject]((#musicvideoobject-mediaobject-的派生类用于描述一个文本对象))|
|messageExt|String|额外信息|mediaObject为WXMusicVideoObject时，从微信音乐播放器内跳回，会携带该参数，长度不能超过2k。（iOS、Android双平台要一致）|
|thumbDataHash|String|消息缩略图数据的sha256|用于签名，详情可见 [OpenSDK 分享签名开发手册](https://developers.weixin.qq.com/doc/oplatform/Mobile_App/Share_and_Favorites/OpenSDK_Signature.html)|
|msgSignature|String|消息签名|详情可见 [OpenSDK 分享签名开发手册](https://developers.weixin.qq.com/doc/oplatform/Mobile_App/Share_and_Favorites/OpenSDK_Signature.html)|

### [TextObject](Wechat/src/commonMain/kotlin/icu/bughub/kit/multiplatform/wechat/MediaObject.kt) (MediaObject 的派生类，用于描述一个文本对象)

对应官方的WXTextObject，iOS版本也进行了封装

|字段|类型|含义|备注|
|-|-|-|-|
|text|String|文本数据|长度需大于 0 且不超过 10KB|

### [ImageObject](Wechat/src/commonMain/kotlin/icu/bughub/kit/multiplatform/wechat/MediaObject.kt) (MediaObject 的派生类，用于描述一个图片对象)

对应官方的WXImageObject

|字段|类型|含义|备注|
|-|-|-|-|
|imageData|ByteArray|图片的二进制数据|内容大小不超过 1MB|
|imagePath|String|图片的本地路径|对应图片内容大小不超过 25MB|
|imgDataHash|String|图片二进制数据的sha256|用于签名，详情可见 [OpenSDK 分享签名开发手册](https://developers.weixin.qq.com/doc/oplatform/Mobile_App/Share_and_Favorites/OpenSDK_Signature.html)|

### [VideoObject](Wechat/src/commonMain/kotlin/icu/bughub/kit/multiplatform/wechat/MediaObject.kt) (MediaObject 的派生类，用于描述一个视频对象)

对应官方的WXVideoObject
**注意：videoUrl 和 videoLowBandUrl 不能同时为空**

|字段|类型|含义|备注|
|-|-|-|-|
|videoUrl|String|视频链接|限制长度不超过 10KB|
|videoLowBandUrl|String|供低带宽的环境下使用的视频链接|限制长度不超过 10KB|

### [WebpageObject](Wechat/src/commonMain/kotlin/icu/bughub/kit/multiplatform/wechat/MediaObject.kt) (MediaObject 的派生类，用于描述一个网页对象)

对应官方的WXWebpageObject

|字段|类型|含义|备注|
|-|-|-|-|
|webpageUrl|String|html 链接|限制长度不超过 10KB|

### [MiniProgramObject](Wechat/src/commonMain/kotlin/icu/bughub/kit/multiplatform/wechat/MediaObject.kt) (MediaObject 的派生类，用于描述一个小程序对象)

对应官方的WXMiniProgramObject

|字段|类型|含义|备注|
|-|-|-|-|
|webpageUrl|String|兼容低版本的网页链接|限制长度不超过 10KB|
|userName|String|小程序的原始 id|html 链接|小程序原始 ID 获取方法：登录小程序管理后台-设置-基本设置-账号信息|
|path|String|小程序的 path|小程序页面路径；对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"|
|withShareTicket|Boolean|是否使用带 shareTicket 的分享|通常开发者希望分享出去的小程序被二次打开时可以获取到更多信息，例如群的标识。可以设置 withShareTicket 为 true，当分享卡片在群聊中被其他用户打开时，可以获取到 shareTicket，用于获取更多分享信息。详见[小程序获取更多分享信息](https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/share.html) ，最低客户端版本要求：6.5.13|
|miniprogramType|MiniprogramType|小程序的类型，默认正式版|正式版: MiniprogramType.Release;
测试版: MiniprogramType.Test;
预览版: MiniprogramType.Preview|

### [MusicVideoObject](Wechat/src/commonMain/kotlin/icu/bughub/kit/multiplatform/wechat/MediaObject.kt) (MediaObject 的派生类，用于描述一个音乐视频对象)

对应官方的WXMusicVideoObject

|字段|类型|含义|备注|
|-|-|-|-|
|musicUrl|String|音频网页的 URL 地址|必填，不能为空，限制长度不超过 10KB|
|musicDataUrl|String|音频数据的 URL 地址|必填，不能为空，限制长度不超过 10KB|
|singerName|String|歌手名|必填，不能为空，限制长度不超过1KB|
|duration|UInt|歌曲时长|必填，单位：毫秒|
|songLyric|String|歌词|建议填写，限制长度不超过32KB|
|hdAlbumThumbFilePath|String|高清专辑图本地文件路径|选填，文件限制长度不超过1MB，仅Android平台|
|hdAlbumThumbData|ByteArray|高清专辑封面图|选填，大小不超过1MB，仅iOS平台|
|albumName|String|音乐专辑名|选填|
|musicGenre|String|音乐流派|选填|
|issueDate|ULong|发行时间Unix时间戳|选填，单位：秒|
|identification|String|音乐标识符|建议填写，用户在微信音乐播放器跳回应用时会携带该参数，可用于唯一标识一首歌，微信侧不理解|
|hdAlbumThumbFileHash|String|高清专辑封面图sha256|用于签名，详情可见 [OpenSDK 分享签名开发手册](https://developers.weixin.qq.com/doc/oplatform/Mobile_App/Share_and_Favorites/OpenSDK_Signature.html)|
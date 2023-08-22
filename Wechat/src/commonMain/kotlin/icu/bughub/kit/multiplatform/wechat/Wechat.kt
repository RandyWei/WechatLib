package icu.bughub.kit.multiplatform.wechat

expect object Wechat {
    /**
     * 分享
     *
     * @param mediaMessage [MediaMessage]和官方属性是一一对应的WXMediaMessage，其中mediaObject也是和官方一一对应的
     * @param scene [WXScene]分享场景
     */
    fun share(mediaMessage: MediaMessage, scene: WXScene)

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
    fun pay(
        partnerId: String,
        prepayId: String,
        packageStr: String,
        nonceStr: String,
        timeStamp: UInt,
        sign: String
    )

    /**
     *
     *
     * @param appId 应用唯一标识，在微信开放平台提交应用审核通过后获得
     * @param state 用于保持请求和回调的状态，授权请求后原样带回给第三方。该参数可用于防止 csrf 攻击（跨站请求伪造攻击），建议第三方带上该参数，可设置为简单的随机数加 session 进行校验。在state传递的过程中会将该参数作为url的一部分进行处理，因此建议对该参数进行url encode操作，防止其中含有影响url解析的特殊字符（如'#'、'&'等）导致该参数无法正确回传。
     *
     */
    fun auth(appId: String, state: String?)
}
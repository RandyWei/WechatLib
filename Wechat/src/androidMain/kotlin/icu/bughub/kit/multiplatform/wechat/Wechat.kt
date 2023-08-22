package icu.bughub.kit.multiplatform.wechat

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram
import com.tencent.mm.opensdk.modelbiz.WXOpenCustomerServiceChat
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory

actual object Wechat {

    private lateinit var api: IWXAPI

    fun register(context: Context, appId: String) {
        api = WXAPIFactory.createWXAPI(context, appId, false)
        api.registerApp(appId)
    }

    /**
     * 分享
     *
     * @param mediaMessage
     * @param scene
     */
    actual fun share(mediaMessage: MediaMessage, scene: WXScene) {

    }

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
    actual fun pay(

        appId: String,
        partnerId: String,
        prepayId: String,
        packageStr: String,
        nonceStr: String,
        timeStamp: UInt,
        sign: String
    ) {
        val req = PayReq()
        req.appId = appId
        req.partnerId = partnerId
        req.prepayId = prepayId
        req.packageValue = packageStr
        req.nonceStr = nonceStr
        req.timeStamp = timeStamp.toString()
        req.sign = sign
        api.sendReq(req)
    }

    /**
     *
     *

     * @param state 用于保持请求和回调的状态，授权请求后原样带回给第三方。该参数可用于防止 csrf 攻击（跨站请求伪造攻击），建议第三方带上该参数，可设置为简单的随机数加 session 进行校验。在state传递的过程中会将该参数作为url的一部分进行处理，因此建议对该参数进行url encode操作，防止其中含有影响url解析的特殊字符（如'#'、'&'等）导致该参数无法正确回传。
     *
     */
    actual fun auth(state: String?) {
        val req = SendAuth.Req()
        req.scope = "snsapi_userinfo"
        req.state = state
        api.sendReq(req)
    }

    /**
     * 拉起小程序
     *
     * @param userName 拉起的小程序的username
     * @param miniProgramType 拉起小程序的类型
     * @param path 拉起小程序页面的可带参路径，不填默认拉起小程序首页，对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"。
     */
    actual fun launchMiniProgram(
        userName: String,
        miniProgramType: MiniProgramType,
        path: String?
    ) {
        val req = WXLaunchMiniProgram.Req()
        req.userName = userName
        req.path = path
        req.miniprogramType = miniProgramType.ordinal

        api.sendReq(req)
    }

    /**
     * 拉起微信客服
     *
     * @param corpId 企业ID
     * @param url 客服URL
     */
    actual fun launchCustomerService(corpId: String, url: String) {
        val req = WXOpenCustomerServiceChat.Req()
        req.corpId = corpId
        req.url = url
        api.sendReq(req)
    }
}
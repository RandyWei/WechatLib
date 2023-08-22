package icu.bughub.kit.multiplatform.wechat

import cocoapods.WechatOpenSDK_XCFramework.WXApi
import cocoapods.WechatOpenSDK_XCFramework.WXLogLevelDetail

actual object Wechat {

    /**
     * 注册微信SDK
     *
     * @param appID
     * @param universalLink
     * @param selfCheck 是否开启自检，默认是false。也就是官方的checkUniversalLinkReady
     */
    fun register(appID: String, universalLink: String, selfCheck: Boolean = false) {
        if (selfCheck) {
            //如果开启了自检，则在register之前打开log，后续可以根据Log排查问题
            WXApi.startLogByLevel(WXLogLevelDetail) {
                println("WeChatSDK:$it")
            }
        }
        WXApi.registerApp(appID, universalLink)
        if (selfCheck) {
            //调用自检函数
            WXApi.checkUniversalLinkReady { step, result ->
                println("step:${step} , ${result?.success} , ${result?.errorInfo} , ${result?.suggestion}")
            }
        }
    }

    /**
     * 分享文本
     *
     * @param text
     * @param scene
     */
    actual fun shareText(text: String, scene: WXScene) {
    }
}
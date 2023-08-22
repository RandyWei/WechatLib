package icu.bughub.kit.multiplatform.wechat

actual object Wechat {
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
        partnerId: String,
        prepayId: String,
        packageStr: String,
        nonceStr: String,
        timeStamp: UInt,
        sign: String
    ) {
    }
}
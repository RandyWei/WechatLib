package icu.bughub.kit.multiplatform.wechat

expect object Wechat {
    /**
     * 分享
     *
     * @param mediaMessage [MediaMessage]和官方属性是一一对应的WXMediaMessage，其中mediaObject也是和官方一一对应的
     * @param scene [WXScene]分享场景
     */
    fun share(mediaMessage: MediaMessage, scene: WXScene)
}
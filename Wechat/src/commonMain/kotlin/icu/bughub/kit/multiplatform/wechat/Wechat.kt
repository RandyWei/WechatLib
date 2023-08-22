package icu.bughub.kit.multiplatform.wechat

expect object Wechat {
    /**
     * 分享
     *
     * @param mediaMessage
     * @param scene
     */
    fun share(mediaMessage: MediaMessage, scene: WXScene)
}
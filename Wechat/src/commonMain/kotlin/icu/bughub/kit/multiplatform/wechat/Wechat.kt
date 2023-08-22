package icu.bughub.kit.multiplatform.wechat

expect object Wechat {
    /**
     * 分享文本
     *
     * @param text
     * @param scene
     */
    fun shareText(text: String, scene: WXScene)
}
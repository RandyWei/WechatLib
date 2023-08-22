package icu.bughub.kit.multiplatform.wechat

import cocoapods.WechatOpenSDK_XCFramework.SendMessageToWXReq
import cocoapods.WechatOpenSDK_XCFramework.WXApi
import cocoapods.WechatOpenSDK_XCFramework.WXImageObject
import cocoapods.WechatOpenSDK_XCFramework.WXLogLevelDetail
import cocoapods.WechatOpenSDK_XCFramework.WXMediaMessage
import cocoapods.WechatOpenSDK_XCFramework.WXMiniProgramObject
import cocoapods.WechatOpenSDK_XCFramework.WXMiniProgramType
import cocoapods.WechatOpenSDK_XCFramework.WXMiniProgramTypePreview
import cocoapods.WechatOpenSDK_XCFramework.WXMiniProgramTypeRelease
import cocoapods.WechatOpenSDK_XCFramework.WXMiniProgramTypeTest
import cocoapods.WechatOpenSDK_XCFramework.WXMusicVideoObject
import cocoapods.WechatOpenSDK_XCFramework.WXVideoObject
import cocoapods.WechatOpenSDK_XCFramework.WXWebpageObject
import platform.Foundation.NSData
import platform.darwin.nil

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
     * 分享
     *
     * @param media
     * @param scene
     */
    actual fun share(mediaMessage: MediaMessage, scene: WXScene) {
        val req = SendMessageToWXReq()
        if (mediaMessage.mediaObject is TextObject) {
            if (null == mediaMessage.mediaObject) {
                throw NullPointerException("分享的文本为空")
            }
            req.bText = true
            req.text = (mediaMessage.mediaObject as TextObject).text
        } else {
            val message = WXMediaMessage()
            message.title = mediaMessage.title
            message.setDescription(mediaMessage.description)
            message.thumbData = mediaMessage.thumbData?.toNSData()
            message.messageExt = mediaMessage.messageExt
            message.thumbDataHash = mediaMessage.thumbDataHash
            message.msgSignature = mediaMessage.msgSignature

            if (null == mediaMessage.mediaObject || (mediaMessage.mediaObject as ImageObject).imageData == null) {
                throw NullPointerException("分享的Media为空")
            }

            when (mediaMessage.mediaObject) {

                is ImageObject -> {
                    val imageObject = WXImageObject()
                    val imgObj = mediaMessage.mediaObject as ImageObject
                    imageObject.imageData = imgObj.imageData!!.toNSData()
                    imageObject.imgDataHash = imgObj.imgDataHash
                    message.mediaObject = imageObject
                }

                is VideoObject -> {
                    val videoObject = WXVideoObject()
                    val videoObj = mediaMessage.mediaObject as VideoObject
                    //此处应该如何断言？
//                    assert(videoObj.videoUrl.isEmpty() && videoObj.videoLowBandUrl.isNotEmpty())
                    videoObject.videoUrl = videoObj.videoUrl
                    videoObject.videoLowBandUrl = videoObj.videoLowBandUrl
                }

                is WebpageObject -> {
                    val webpageObject = WXWebpageObject()
                    webpageObject.webpageUrl =
                        (mediaMessage.mediaObject as WebpageObject).webpageUrl
                    message.mediaObject = webpageObject
                }

                is MiniProgramObject -> {
                    val obj = mediaMessage.mediaObject as MiniProgramObject
                    val miniObject = WXMiniProgramObject()
                    miniObject.webpageUrl = obj.webpageUrl
                    miniObject.userName = obj.userName
                    miniObject.path = obj.path
                    miniObject.hdImageData = obj.hdImageData?.toNSData()
                    miniObject.withShareTicket = obj.withShareTicket

                    when (obj.miniprogramType) {
                        MiniProgramType.Release -> miniObject.miniProgramType =
                            WXMiniProgramTypeRelease

                        MiniProgramType.Test -> miniObject.miniProgramType = WXMiniProgramTypeTest
                        MiniProgramType.Preview -> miniObject.miniProgramType =
                            WXMiniProgramTypePreview
                    }

                    message.mediaObject = miniObject

                }

                is MusicVideoObject -> {
                    val obj = mediaMessage.mediaObject as MusicVideoObject
                    val mvObject = WXMusicVideoObject()
                    mvObject.musicUrl = obj.musicUrl
                    mvObject.musicDataUrl = obj.musicDataUrl
                    mvObject.singerName = obj.singerName
                    mvObject.duration = obj.duration //音乐时长，毫秒
                    obj.hdAlbumThumbData?.let {
                        mvObject.hdAlbumThumbData = it.toNSData() //高清专辑封面图
                    }
                    mvObject.albumName = obj.albumName
                    mvObject.songLyric = obj.songLyric
                    mvObject.musicGenre = obj.musicGenre
                    mvObject.issueDate = obj.issueDate //发行时间Unix时间戳
                    mvObject.identification = obj.identification //音乐标识符
                    mvObject.hdAlbumThumbFileHash = obj.hdAlbumThumbFileHash
                }

                else -> {}
            }
            req.message = message
        }


        req.scene = scene.ordinal
        WXApi.sendReq(req)
        {}

    }
}
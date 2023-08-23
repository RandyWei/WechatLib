package icu.bughub.kit.multiplatform.wechat

interface EventHandler {
    fun onReq(req: BaseReq)

    fun onResp(resp: BaseResp)
}
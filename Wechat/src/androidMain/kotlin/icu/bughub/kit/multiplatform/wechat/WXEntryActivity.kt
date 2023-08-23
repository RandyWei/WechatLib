package icu.bughub.kit.multiplatform.wechat

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler

class WXEntryActivity : Activity(), IWXAPIEventHandler {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Wechat.api.handleIntent(intent, this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        Wechat.api.handleIntent(intent, this)
    }

    override fun onReq(p0: BaseReq?) {
        val req = BaseReq(
            p0?.transaction,
            p0?.openId,
            p0?.type,
            p0?.checkArgs()
        )
        Wechat.eventHandlers.forEach {
            it.onReq(
                req
            )
        }
    }

    override fun onResp(p0: BaseResp?) {
        var extMsg = ""
        var code = ""
        when (p0?.type) {
            ConstantsAPI.COMMAND_LAUNCH_WX_MINIPROGRAM -> {
                extMsg = (p0 as WXLaunchMiniProgram.Resp).extMsg
            }

            ConstantsAPI.COMMAND_SENDAUTH -> {
                code = (p0 as SendAuth.Resp).code
            }

            else -> {}
        }

        val resp = BaseResp(
            code,
            extMsg,
            p0?.errCode,
            p0?.errStr,
            p0?.transaction,
            p0?.openId,
            p0?.type,
            p0?.checkArgs()
        )
        Wechat.eventHandlers.forEach { it.onResp(resp) }
    }
}
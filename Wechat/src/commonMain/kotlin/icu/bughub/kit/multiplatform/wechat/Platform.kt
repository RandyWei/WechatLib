package icu.bughub.kit.multiplatform.wechat

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("maven-publish")
}

group = "icu.bughub.kit"
version = "0.0.1"

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "11.0"
        framework {
            baseName = "Wechat"
        }

        //配置微信open sdk，固定版本
        pod("WechatOpenSDK-XCFramework"){
            version = "2.0.2"
        }

        //处理微信SDK没有module问题
        //参考文档：https://kotlinlang.org/docs/native-cocoapods.html#module-not-found
        tasks.named<org.jetbrains.kotlin.gradle.tasks.DefFileTask>("generateDefWechatOpenSDK_XCFramework")
            .configure {
                doLast {
                    outputFile.writeText(
                        """
                language = Objective-C
                headers = WXApi.h
            """.trimIndent()
                    )
                }
            }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                //put your multiplatform dependencies here
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                api("com.tencent.mm.opensdk:wechat-sdk-android:6.8.24")
            }
        }
    }
}

android {
    namespace = "icu.bughub.kit.multiplatform.wechat"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
    }
}

package me.marthia.negar.di

import androidx.multidex.BuildConfig
import com.developersancho.framework.base.app.NetworkConfig

class JetNetworkConfig : NetworkConfig() {
    override fun baseUrl(): String {
        return ""
    }

    override fun timeOut(): Long {
        return 30L
    }

    override fun isDev(): Boolean {
        return BuildConfig.DEBUG
    }
}
package me.marthia.negar.framework.presentation.root

import android.app.Application
import com.developersancho.framework.base.app.AppInitializer
import com.developersancho.framework.base.app.CoreApplication
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApp : CoreApplication() {


    @Inject
    lateinit var initializer: AppInitializer

    override fun onCreate() {
        super.onCreate()
        initializer.init(this)
    }
}
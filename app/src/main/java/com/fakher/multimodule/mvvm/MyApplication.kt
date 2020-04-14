package com.fakher.multimodule.mvvm

import android.app.Application
import com.facebook.stetho.Stetho
import com.fakher.multimodule.mvvm.di.ApplicationComponent
import com.fakher.multimodule.mvvm.di.ApplicationModule
import com.fakher.multimodule.mvvm.di.DaggerApplicationComponent
import com.fakher.multimodule.mvvm.di.DaggerComponentProvider

class MyApplication : Application(),
    DaggerComponentProvider {

    override val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
            .applicationModule(
                ApplicationModule(
                    this
                )
            )
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)
    }
}
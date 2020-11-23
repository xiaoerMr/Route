package com.component.route

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter

class RouteApplication :Application() {
    override fun onCreate() {
        super.onCreate()

        //路由
        initARouter()
    }

    private fun initARouter() {
        if (BuildConfig.DEBUG){
            ARouter.openLog()
            ARouter.openDebug()
        }
       ARouter.init(this)
    }
}

// 多渠道 打包： https://www.jianshu.com/p/533240d222d3
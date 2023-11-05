package com.tieuvy.android.base

import android.util.Log
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import timber.log.Timber


/**
- Create by :Vy Hùng
- Create at :31,October,2023
 **/

object Logg {

    private val configLogger = PrettyFormatStrategy
        .newBuilder()
        .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
        .methodCount(0)         // (Optional) How many method line to show. Default 2
        .methodOffset(7)
        .tag("Class: ")// (Optional) Hides internal method calls up to offset. Default 5
        .build()

    init {
        Logger.addLogAdapter(AndroidLogAdapter(configLogger))
    }

    fun initLogger(enabled: Boolean) {
        if (enabled) {
            Timber.plant(object : Timber.DebugTree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    val name = tag?.split("\$")
                    val className = name?.firstOrNull()
                    val method = name?.lastOrNull()
                    Logger.log(priority, className, "Method: [$method]" + ": \n" + message, t)
                    //Log.e("ABC", tag.toString() )
                }

                override fun createStackElementTag(element: StackTraceElement): String {
                    val className = super.createStackElementTag(element)
                    return className ?: ""
                }
            })
        }
    }
}

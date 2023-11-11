package com.atom.android.lebo

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import com.atom.android.lebo.data.di.apiModule
import com.atom.android.lebo.data.di.dataSourceModule
import com.atom.android.lebo.data.di.databaseModule
import com.atom.android.lebo.data.di.networkModule
import com.atom.android.lebo.data.di.repositoryModule
import com.atom.android.lebo.data.di.sharedPreferencesModule
import com.atom.android.lebo.di.viewModelModule
import com.atom.android.lebo.utils.constants.Constant
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class LeBoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        createChanelNotification()
        startKoin {
            androidContext(this@LeBoApplication)
            modules(
                networkModule,
                sharedPreferencesModule,
                apiModule,
                dataSourceModule,
                repositoryModule,
                viewModelModule,
                databaseModule
            )
        }
    }

    private fun createChanelNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelFB = NotificationChannel(
                Constant.NOTIFICATION.CHANNEL_ID,
                Constant.NOTIFICATION.CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()
            channelFB.apply {
                setSound(
                    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION),
                    audioAttributes
                )
                enableLights(true)
                enableVibration(true)
            }
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(channelFB)
        }
    }
}

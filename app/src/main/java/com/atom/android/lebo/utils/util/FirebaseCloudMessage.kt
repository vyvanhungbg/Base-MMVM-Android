package com.atom.android.lebo.utils.util

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLinkBuilder
import com.atom.android.lebo.R
import com.atom.android.lebo.ui.main.MainActivity
import com.atom.android.lebo.utils.constants.ApiConstant
import com.atom.android.lebo.utils.constants.Constant
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.Date

class FirebaseCloudMessage : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val notification = message.notification
        val data = message.data
        notification?.let {
            val title = notification.title
            val body = notification.body
            val id = data[ApiConstant.FILED.ID]
            sendNotification(title, body, id)
        }
    }

    private fun sendNotification(title: String?, message: String?, id: String?) {

        val pendingIntent = NavDeepLinkBuilder(this)
            .setGraph(R.navigation.navigation_bottom_main)
            .setDestination(R.id.navigation_bill)
            .setArguments(bundleOf(Constant.BUNDLED.BILL to id?.toInt()))
            .setComponentName(MainActivity::class.java)
            .createPendingIntent()

        val sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, Constant.NOTIFICATION.CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.image_book_logo)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(sound)
        val notification: Notification = notificationBuilder.build()
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
        notificationManager?.notify(Date().time.toInt(), notification)
    }
}

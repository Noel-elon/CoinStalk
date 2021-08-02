package com.example.coinstalk.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.example.coinstalk.views.MainActivity
import com.example.coinstalk.R
import com.example.coinstalk.StalkCoin


fun sendNotification(context: Context, coin: StalkCoin, userAlias: String) {
    val notificationManager = context
        .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
        && notificationManager.getNotificationChannel(CHANNEL_ID) == null
    ) {
        val appName = context.getString(R.string.app_name)
        val channel = NotificationChannel(
            CHANNEL_ID,
            appName,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
    }

    val pendingIntent = NavDeepLinkBuilder(context)
        .setComponentName(MainActivity::class.java)
        .setGraph(R.navigation.nav_graph)
        .setDestination(R.id.favouritesFragment)
        .createPendingIntent()

    val notification = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle("Hey $userAlias!")
        .setContentText(notificationMessage(coin))
        .setAutoCancel(true)
        .addAction(
            NotificationCompat.Action(
                null,
                context.getString(R.string.notif_button),
                pendingIntent
            )
        )
        .build()

    notificationManager.notify(NOTIF_ID, notification)
}

fun createNotifChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationChannel = NotificationChannel(
            CHANNEL_ID,
            context.getString(R.string.channel_name),

            NotificationManager.IMPORTANCE_HIGH
        )
            .apply {
                setShowBadge(false)
            }

        notificationChannel.enableVibration(true)
        notificationChannel.description =
            context.getString(R.string.channel_description)

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(notificationChannel)
    }
}



private fun notificationMessage(coin: StalkCoin): String {
    return "Check how ${coin.symbol} and your favs are faring today!"
}
package com.avh.message

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.app.PendingIntent

import android.content.Intent
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import com.avh.message.R


open class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val channelId = "My_Channel_ID"
        val notificationId = 1
        createNotificationChannel(channelId)


val btn = findViewById<Button>(R.id.sendbtn)
        btn.setOnClickListener {
            if (Build.VERSION.SDK_INT >= 24) {
                // Create an instance of remote input builder
                val remoteInput: RemoteInput = RemoteInput.Builder("KEY_TEXT_REPLY")
                    .run {
                        setLabel("Write your message here")
                        build()
                    }

                // Create an intent
                val intent = Intent(this, MainActivity::class.java)
                intent.action = "REPLY_ACTION"
                intent.putExtra("KEY_NOTIFICATION_ID", notificationId)
                intent.putExtra("KEY_CHANNEL_ID", channelId)
                intent.putExtra("KEY_MESSAGE_ID", 1)

                // Create a pending intent for the reply button
                val replyPendingIntent: PendingIntent = PendingIntent.getBroadcast(
                    this,
                    101,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )

                // Create reply action and add the remote input
                var action: NotificationCompat.Action = NotificationCompat.Action.Builder(
                    R.drawable.ic_launcher_background,
                    "Reply",
                    replyPendingIntent
                ).addRemoteInput(remoteInput)
                    .setAllowGeneratedReplies(true)
                    .build()


                // Build a notification and add the action
                val builder = NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("Developers")
                    .setContentText("Hi! How are you?")
                    .addAction(action)

                // Finally, issue the notification
                NotificationManagerCompat.from(this).apply {
                    notify(notificationId, builder.build())
                }
            }
        }
    }

    private fun createNotificationChannel(channelId:String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "My Channel"
            val channelDescription = "Channel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(channelId,name,importance)
            channel.apply {
                description = channelDescription
            }

            // Finally register the channel with system
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
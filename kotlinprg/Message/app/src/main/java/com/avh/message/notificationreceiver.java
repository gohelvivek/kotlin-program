package com.avh.message;
import android.R;
import android.widget.Toast;
import android.app.RemoteInput;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.content.Context;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


    class NotificationReceiver<override> extends MainActivity{

        override fun NotificationReceiver(context Context, intent Intent) {
        intent?.apply {
        if ("REPLY_ACTION".equals(action)){
        val message = replyMessage(.this)
        val messageId = getIntExtra("KEY_MESSAGE_ID",0)
        Toast.makeText(context,"$messageId : $message",Toast.LENGTH_LONG).show()
        }

        context?.apply {
        val notificationId = getIntExtra("KEY_NOTIFICATION_ID",0)
        val channelId = getStringExtra("KEY_CHANNEL_ID")

        // Build a notification and add the action
        val builder = NotificationCompat.Builder(this, channelId.toString())
        .setSmallIcon(R.drawable.ic_secure)
        .setContentTitle("Successful")
        .setContentText("Message sent!")

        // Finally, issue the notification
        NotificationManagerCompat.from(this).apply {
        notify(notificationId, builder.build())
        }
        }
        }
        }


private fun replyMessage(intent: Intent): CharSequence? {
        val remoteInput = RemoteInput.getResultsFromIntent(intent)
        return remoteInput.getCacheDir("KEY_TEXT_REPLY");
        }
        }
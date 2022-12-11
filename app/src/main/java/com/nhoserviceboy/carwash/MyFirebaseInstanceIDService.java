package com.nhoserviceboy.carwash;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.nhoserviceboy.carwash.Activity.SplashActivity;

import java.util.HashMap;
import java.util.Map;


public class MyFirebaseInstanceIDService extends FirebaseMessagingService
{
    Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    private static final String ACTION_STOP_SERVICE ="ACTION_STOP_SERVICE" ;
    @Override
    public void onNewToken(@NonNull String s)
    {
        super.onNewToken(s);
        //Map<String,Object> tokenData=new HashMap<>();
        //tokenData.put("token",s);
        //FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
        //firebaseFirestore.collection("DeviceToken").document().set(tokenData);

        SharedPreferences sharedPref = getSharedPreferences("application", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("tokenId",s);
        editor.apply();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage)
    {
        super.onMessageReceived(remoteMessage);
        showFirebaseMessage(remoteMessage.getData().get("title"), remoteMessage.getData().get("body"));

        Log.d("","dfjjdhfu"+remoteMessage.getData().get("title"));
        Intent i=new Intent(this, Custom.class);
        i.putExtra("NotificationName",remoteMessage.getData().get("title"));
        i.putExtra("NotificationMessage", remoteMessage.getData().get("body"));
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(i);
        Log.d("","messaging "+remoteMessage.getData().get("title"));
        //Toast.makeText(this, ""+remoteMessage.getNotification().getTitle(), Toast.LENGTH_SHORT).show();
    }

  @RequiresApi(api = Build.VERSION_CODES.O)
  public void showFirebaseMessage(String title, String message)
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            startNotification_afterAPI26(title,message);
            Log.d("","adsadfff"+title);
        }
        else
        {
            startNotification_underAPI26(title,message);
            Log.d("","adsadffffff"+title);
        }
    }
    private void startNotification_underAPI26(String title,String message)
    {
    Intent notificationIntent = new Intent(this, MainActivity.class);
    notificationIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT );
    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
     /*   NotificationCompat.Builder;*/
        Notification notification = new NotificationCompat.Builder(this, "NOTIFICATION_CHANNEL_ID")
            .setCustomContentView(getSmallContentView(title,message))
            .setCustomBigContentView(getBigContentView(title,message))
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)
            .setContentTitle(title)
            .setOngoing(true)
            .setPriority(Notification.PRIORITY_MAX)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setAutoCancel(true)
                .setSound(alarmSound)
            .build();
       /* NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification.build());
      */  startForeground(1, notification);

}

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startNotification_afterAPI26(String title,String message)
    {
        try {


            Intent notificationIntent = new Intent(this, SplashActivity.class);
            notificationIntent.setFlags(Intent.FLAG_FROM_BACKGROUND |Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent,  0);
            NotificationChannel chan = new NotificationChannel("NOTIFICATION_CHANNEL_ID", "channelName", NotificationManager.IMPORTANCE_LOW);
            chan.setLightColor(Color.BLUE);
            chan.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            assert manager != null;
            manager.createNotificationChannel(chan);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "NOTIFICATION_CHANNEL_ID");
            Notification notification = notificationBuilder.setOngoing(true)
                    .setPriority(NotificationManager.IMPORTANCE_LOW)
                    .setCategory(Notification.EXTRA_MEDIA_SESSION)
                    .setCustomContentView(getSmallContentView(title,message))
                    .setCustomBigContentView(getBigContentView(title,message))
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pendingIntent)
                    .setContentTitle("43788456")
                    .setNumber(345765743)
                    .setOngoing(true)
                    .setAutoCancel(true)
                    .setPriority(Notification.PRIORITY_MAX)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setSound(alarmSound)
                    .build();

            startForeground(1, notification);
            /*NotificationManager managerr= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0, notificationBuilder.build());*/
        } catch (Exception e)
        {
            Log.d("","bug"+e);
        }
    }


    private RemoteViews getSmallContentView(String title,String message)
    {
        RemoteViews mContentViewSmall = null;
        if (mContentViewSmall == null) {
            mContentViewSmall = new RemoteViews(getPackageName(),R.layout.sdp_example);
            setUpRemoteView(mContentViewSmall,title,message);
        }
      // updateRemoteViews(mContentViewSmall);
        return mContentViewSmall;
    }
    private RemoteViews getBigContentView(String title,String message)
    {
        RemoteViews mContentViewBig=null;
        if (mContentViewBig == null)
        {
            mContentViewBig = new RemoteViews(getPackageName(), R.layout.notification_expanded);
            setUpRemoteView(mContentViewBig,title,message);
        }
       // updateRemoteViews(mContentViewBig);
        return mContentViewBig;
    }
    private void setUpRemoteView(RemoteViews remoteView,String title,String message)
    {
        Intent closeIntent = new Intent(this, Custom.class);

        closeIntent.setAction(ACTION_STOP_SERVICE);
        PendingIntent pcloseIntent = PendingIntent.getService(this, 0, closeIntent, 0);//PendingIntent.FLAG_UPDATE_CURRENT
        remoteView.setOnClickPendingIntent(R.id.carimage2, pcloseIntent);
    }
    /*private void updateRemoteViews(RemoteViews remoteView) {

        if (currentMusic != null) {
            remoteView.setTextViewText(R.id.lblWidgetCurrentMusicName, "Music name");
            remoteView.setTextViewText(R.id.lblWidgetCurrentArtistName, "Music Artist");
        }
        remoteView.setImageViewResource(R.id.btnWidgetPlayPauseMusic, isMusicPlaying == true ? R.drawable.vector_pause : R.drawable.vector_play);

        if(theMusicApplication.global_programIsReady)
        {
            Bitmap album = (Bitmap)your_image;
            if (album == null) {
                remoteView.setImageViewResource(R.id.imgWidgetAlbumArt, R.mipmap.ic_launcher);
            } else {
                remoteView.setImageViewBitmap(R.id.imgWidgetAlbumArt, album);
            }
        }
    }*/

}

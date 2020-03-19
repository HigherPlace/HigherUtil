package com.higher.util.test;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        NotifyUtil.init(MainActivity.this);
//        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("type", 1);
//                intent.putExtra("msgId", 1);
//                Uri uri = Uri.parse("android.resource://com.androidbook.samplevideo/" + R.raw.xfjb);
//
//                PendingIntent pi = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//                NotifyUtil.buildSimple(11, R.mipmap.ic_launcher, "新消息", "消息内容", pi)
//                        .setHeadup()
//                        .setSoundUri(uri)
//                        .setAction(false, true, true)
//                        .setLockScreenVisiablity(NotificationCompat.VISIBILITY_PUBLIC)//设置锁屏的时候要展示的内容，展示全部内容
//                        .show();
//            }
//        });
    }
}

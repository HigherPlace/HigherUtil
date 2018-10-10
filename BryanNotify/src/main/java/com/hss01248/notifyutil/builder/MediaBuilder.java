package com.hss01248.notifyutil.builder;

import android.app.PendingIntent;

public class MediaBuilder extends BaseBuilder {

    @Override
    public void build() {
        super.build();
//        NotificationCompat.MediaStyle style = new NotificationCompat.MediaStyle();
//        style.setMediaSession(new MediaSessionCompat(NotifyUtil.context,"MediaSession",
//                new ComponentName(NotifyUtil.context, Intent.ACTION_MEDIA_BUTTON),null).getSessionToken());
//        //设置要现实在通知右方的图标 最多三个
//        style.setShowActionsInCompactView(2,3);
//        style.setShowCancelButton(true);
//        cBuilder.setStyle(style);
//        cBuilder.setShowWhen(false);
    }

    @Override
    public BaseBuilder addBtn(int icon, CharSequence text, PendingIntent pendingIntent) {
        return super.addBtn(icon, text, pendingIntent);
    }
}

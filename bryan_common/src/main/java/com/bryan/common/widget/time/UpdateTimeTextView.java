package com.bryan.common.widget.time;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Spanned;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.bryan.common.utils.WeakHandler;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by bryan on 2018/7/17 0017.
 */
public class UpdateTimeTextView extends AppCompatTextView {
    private String TAG = "UpdateTimeTextView";
    private Thread runnable;
    private boolean mBoolean = true;

    private float mScale = 1.0f;

    private int firstSize = 60;
    private int secondSize = 28;

    WeakHandler handler = new WeakHandler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            String times = (String) msg.obj;
            String[] time = times.split("&");
            StringBuilder sbTime = new StringBuilder("<myfont></<myfont>");
            sbTime.append("<myfont  size='" + (int) (firstSize * mScale) + "px'>").append(time[0]).append("</myfont><br>");
            sbTime.append("<myfont  size='" + (int) (secondSize * mScale) + "px' color='#fffc00'>").append(time[1]).append("</myfont>");
            sbTime.append("<myfont  size='" + (int) (secondSize * mScale) + "px' color='#fffc00'>").append(time[2]).append("</myfont>");
            Spanned spanned = Html.fromHtml(sbTime.toString(), null, new HtmlTagHandler("myfont"));
            UpdateTimeTextView.this.setText(spanned);
            return false;
        }
    });
    private String DEFAULT_TIME_FORMAT = "HH:mm:ss&yyyy.MM.dd";

    public void setmScale(float mScale) {
        this.mScale = mScale;
    }

    public UpdateTimeTextView(Context context) {
        super(context);
    }

    public UpdateTimeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();//加载方法
    }

    public UpdateTimeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mBoolean = false;
    }

    /**
     * 更新时间
     */
    private void init() {
        runnable = new Thread() {
            @Override
            public void run() {
                while (mBoolean) {
                    Calendar mCalendar = Calendar.getInstance();
                    SimpleDateFormat dateFormatter = new SimpleDateFormat(DEFAULT_TIME_FORMAT);
                    String time = dateFormatter.format(Calendar.getInstance().getTime());//获取当前时间
                    String mWay = String.valueOf(mCalendar.get(Calendar.DAY_OF_WEEK));//获取星期
                    if ("1".equals(mWay)) {
                        mWay = "天";
                    } else if ("2".equals(mWay)) {
                        mWay = "一";
                    } else if ("3".equals(mWay)) {
                        mWay = "二";
                    } else if ("4".equals(mWay)) {
                        mWay = "三";
                    } else if ("5".equals(mWay)) {
                        mWay = "四";
                    } else if ("6".equals(mWay)) {
                        mWay = "五";
                    } else if ("7".equals(mWay)) {
                        mWay = "六";
                    }
                    String tiems = time + "& 星期" + mWay;
                    Message msg = new Message();
                    msg.what = 100;
                    msg.obj = tiems;
                    handler.sendMessage(msg);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        runnable.start();
    }

    //根据毫秒时间获取格式化的提示 按自己实际要求来写
    private String convertTimeToFormat(long timeMills) {
        long curTime = Calendar.getInstance().getTimeInMillis();
        long time = (curTime - timeMills) / (long) 1000;//已经将单位转换成秒

        if (time > 0 && time < 60) {
            return "刚刚";
        } else if (time >= 60 && time < 3600) {
            return time / 60 + "分钟前";
        } else if (time >= 3600 && time < 3600 * 24) {
            return time / 3600 + "小时前";
        } else if (time >= 3600 * 24 && time < 3600 * 24 * 30) {
            return time / 3600 / 24 + "天前";
        } else if (time >= 3600 * 24 * 30 && time < 3600 * 24 * 30 * 12) {
            return time / 3600 / 24 / 30 + "个月前";
        } else if (time >= 3600 * 24 * 30 * 12) {
            return time / 3600 / 24 / 30 / 12 + "年前";
        } else {
            return "刚刚";
        }
    }

}

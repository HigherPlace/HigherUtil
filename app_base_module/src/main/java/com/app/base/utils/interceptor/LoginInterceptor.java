//package com.ctx.base.utils.interceptor;
//
//import android.content.Context;
//
//import com.alibaba.android.arouter.facade.Postcard;
//import com.alibaba.android.arouter.facade.annotation.Interceptor;
//import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
//import com.alibaba.android.arouter.facade.template.IInterceptor;
//import com.alibaba.android.arouter.launcher.ARouter;
//import com.bryan.common.utils.log.LogUtils;
//
///**
// * //登陆拦截器
// * Created by bryan on 2017/11/27 0027.
// */
//@Interceptor(priority = 8, name = "测试用拦截器")
//public class LoginInterceptor implements IInterceptor {
//
//    Context mContext;
//
//    @Override
//    public void process(Postcard postcard, InterceptorCallback callback) {
//        if (postcard.getPath().contains("/third/ThirdActivity")) {
//            //或者使用contain,可以在path上下文章，某一些列的功能都需要登陆才能进行操作
//            //拦截器，拦截对应的path
//            //如果要跳转到模块二
//            if (true) {
//                ARouter.getInstance().build("/main/login")
//                        .withString("path", postcard.getPath())
//                        .navigation();
//                callback.onInterrupt(new RuntimeException("我觉得有点异常"));
//            } else {
//                callback.onContinue(postcard);
//            }
//
//        } else {
//            callback.onContinue(postcard);
//        }
//
//    }
//
//    @Override
//    public void init(Context context) {
//        mContext = context;
//        LogUtils.i("初始化拦截器");
//    }
//}

package com.bryan.common.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;

/**
 * 操作输入法的工具类。可以方便的关闭和显示输入法.
 *
 * @date 2015/5/27
 */
public class KeyBoardUtil {
    private static KeyBoardUtil instance;
    private InputMethodManager mInputMethodManager;

    /**
     * 供Activity调用
     *
     * @param activity
     */
    public static void hideFrom(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        // 找到当前获得焦点的 view，从而可以获得正确的窗口 token
        View view = activity.getCurrentFocus();
        // 如果没有获得焦点的 view，创建一个新的，从而得到一个窗口的 token
        if (view == null) {
            view = new View(activity);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 供fragment调用
     *
     * @param context
     * @param view
     */
    public static void hideFrom(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private KeyBoardUtil(Activity activity) {
        mInputMethodManager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    public static KeyBoardUtil getInstance(Activity activity) {
        if (instance == null) {
            instance = new KeyBoardUtil(activity);
        }
        return instance;
    }

    /**
     * 强制显示输入法
     */
    public void show(Activity activity) {
        show(activity.getWindow().getCurrentFocus());
    }

    public void show(View view) {
        mInputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        //下面这句代码非常关键
        mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 强制关闭输入法
     */
    public void hide(Activity activity) {
        hide(activity.getWindow().getCurrentFocus());
    }

    public void hide(View view) {
        if (view == null || view.getWindowToken() == null)
            return;//防止界面还没初始化完成就去调用这个方法
        mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 如果输入法已经显示，那么就隐藏它；如果输入法现在没显示，那么就显示它
     */
    public void showOrHide() {
        mInputMethodManager.toggleSoftInput(0,
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 判断输入法是否已打开 true:输入法已打开
     */
    public boolean isOpen() {
        return mInputMethodManager.isActive();
    }


    /**
     * @param root         最外层布局，需要调整的布局
     * @param scrollToView 被键盘遮挡的scrollToView，滚动root,使scrollToView在root可视区域的底部
     */
    public static void controlKeyboardLayout(final View root, final View scrollToView) {
        final int[] location = new int[2];
        // 获取scrollToView在窗体的坐标
        scrollToView.getLocationOnScreen(location);
        // 注册一个回调函数，当在一个视图树中全局布局发生改变或者视图树中的某个视图的可视状态发生改变时调用这个回调函数。
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                // 获取root在窗体的可视区域
                root.getWindowVisibleDisplayFrame(rect);
                // 当前视图最外层的高度减去现在所看到的视图的最底部的y坐标
                int rootInvisibleHeight = root.getRootView().getHeight() - rect.bottom;
                Log.i("tag", "最外层的高度" + root.getRootView().getHeight());
                // 若rootInvisibleHeight高度大于100，则说明当前视图上移了，说明软键盘弹出了(Ps:100太小了，有些手机有虚拟键盘，这个值不够大)
                if (rootInvisibleHeight > 300) {
                    //软键盘弹出来的时候
                    // 计算root滚动高度，使scrollToView在可见区域的底部
                    int srollHeight = (location[1] + scrollToView.getHeight()) - rect.bottom;
                    root.scrollTo(0, srollHeight);
                } else {
                    // 软键盘没有弹出来的时候
                    root.scrollTo(0, 0);
                }
            }
        });
    }

    /**
     * 修复InputMethodManager的内存泄漏--->"mCurRootView", "mServedView", "mNextServedView"
     *
     * @param destContext
     */
    public static void fixInputMethodManagerLeak(Context destContext) {
        if (destContext == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) destContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        String[] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView", "mLastSrvView"};
        Field f = null;
        Object obj_get = null;
        for (int i = 0; i < arr.length; i++) {
            String param = arr[i];
            try {
                f = imm.getClass().getDeclaredField(param);
                if (f.isAccessible() == false) {
                    f.setAccessible(true);
                }
                obj_get = f.get(imm);
                if (obj_get != null && obj_get instanceof View) {
                    View v_get = (View) obj_get;
                    if (v_get.getContext() == destContext) { // 被InputMethodManager持有引用的context是想要目标销毁的
                        f.set(imm, null); // 置空，破坏掉path to gc节点
                    } else {
                        // 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了
                        Log.d("KeyBoardUtil", "fixInputMethodManagerLeak break, context is not suitable, get_context=" + v_get.getContext() + " dest_context=" + destContext);
                        break;
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

}

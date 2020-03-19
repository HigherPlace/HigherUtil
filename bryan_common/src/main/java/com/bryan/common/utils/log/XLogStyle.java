package com.bryan.common.utils.log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrintStyle;

public class XLogStyle extends PrintStyle {

    @Nullable
    @Override
    public String beforePrint() {
        String top = "";
        top += "╔════════════════════════════════════════════════════════════════════════════";
        top += "\n║Thread:" + Thread.currentThread().getName();
        top += "\n║────────────────────────────────────────────────────────────────────────────";
        final StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        String format = format(stack);
        top += "\n" + format;
        top += "║────────────────────────────────────────────────────────────────────────────";
        return top;
    }

    @NonNull
    @Override
    protected String printLog(String message, int line, int wholeLineCount) {
        String prefix = "║";
        if (wholeLineCount == 1) {
            return prefix + message;
        }

        if (line == wholeLineCount - 1) {
            // last
            prefix += message;
        } else {
            prefix += message;
        }
        return prefix;
    }

    @Nullable
    @Override
    protected String afterPrint() {
        return "╚════════════════════════════════════════════════════════════════════════════";
    }

//    private String format(StackTraceElement[] stackTrace) {
//        StringBuilder sb = new StringBuilder(256);
//        if (stackTrace == null || stackTrace.length == 0) {
//            return null;
//        } else if (stackTrace.length == 1) {
//            return "\t─ " + stackTrace[0].toString();
//        } else {
//            int index = Logger.STACK_OFFSET + getSettings().methodOffset;
////            int N = stackTrace.length > 20 ? 20 : stackTrace.length
//            for (int i = index, N = stackTrace.length; i < N - index; i++) {
//                if (i != N - index - 1) {
//                    sb.append("║\t├ ");
//                    sb.append(stackTrace[i].toString());
//                } else {
//                    sb.append("║\t└ ");
//                    sb.append(stackTrace[i].toString());
//                    sb.append("\n");
//                }
//
//            }
//            return sb.toString();
//        }
//    }

    private String format(StackTraceElement[] stackTrace) {
        StringBuilder sb = new StringBuilder(256);
        if (stackTrace == null || stackTrace.length == 0) {
            return null;
        } else if (stackTrace.length == 1) {
            return "\t─ " + stackTrace[0].toString();
        } else {
            int index = Logger.STACK_OFFSET + getSettings().methodOffset;
            for (int i = index, N = stackTrace.length; i < N - index; i++) {
                if (i != N - index - 1) {
                    sb.append("║\t├ ");
                    sb.append(stackTrace[i].toString());
                } else {
                    sb.append("║\t└ ");
                    sb.append(stackTrace[i].toString());
                }
                sb.append("\n");
            }
            return sb.toString();
        }
    }
}

package com.app.base.utils.net.bean;

import org.xutils.common.Callback;

/**
 * Created by Administrator on 2016/5/27.
 */
public class ResponseBean {
    private int status;
    private String message;
    private String result;//data
    private String allSuccessInfo;
    private Throwable throwable;
    private boolean isShowToast = true;
    private String url;
    private int total;


    public boolean isShowToast() {
        return isShowToast;
    }

    public void setShowToast(boolean showToast) {
        isShowToast = showToast;
    }

    private Callback.CancelledException cancelledException;

    public ResponseBean() {
    }

    public ResponseBean(int status, String message) {
        this.status = status;
        this.message = message;
    }


    public String getAllSuccessInfo() {
        return allSuccessInfo;
    }

    public void setAllSuccessInfo(String allSuccessInfo) {
        this.allSuccessInfo = allSuccessInfo;
    }

    public int getStatus() {
        return status;
    }

    public ResponseBean setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message != null ? message : "";
    }

    public ResponseBean setMessage(String message) {
        this.message = message;
        return this;
    }

    public Callback.CancelledException getCancelledException() {
        return cancelledException;
    }

    public ResponseBean setCancelledException(Callback.CancelledException cancelledException) {
        this.cancelledException = cancelledException;
        return this;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public ResponseBean setThrowable(Throwable throwable) {
        this.throwable = throwable;
        return this;
    }

    public String getResult() {
        return result != null ? result : "";
    }

    public ResponseBean setResult(String result) {
        this.result = result;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public ResponseBean setUrl(String url) {
        this.url = url;
        return this;
    }

    public int getTotal() {
        return total;
    }

    public ResponseBean setTotal(int total) {
        this.total = total;
        return this;
    }
}

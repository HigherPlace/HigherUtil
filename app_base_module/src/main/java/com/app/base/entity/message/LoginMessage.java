package com.app.base.entity.message;

import com.app.base.utils.user.AccountBean;

/**
 * 登陆信息
 * Created by bryan on 2018/2/26 0026.
 */
public class LoginMessage {
    private boolean hasLogin;
    private AccountBean accountBean;

    public LoginMessage(boolean hasLogin, AccountBean accountBean) {
        this.hasLogin = hasLogin;
        this.accountBean = accountBean;
    }

    public boolean isHasLogin() {
        return hasLogin;
    }

    public void setHasLogin(boolean hasLogin) {
        this.hasLogin = hasLogin;
    }

    public AccountBean getAccountBean() {
        return accountBean;
    }

    public void setAccountBean(AccountBean accountBean) {
        this.accountBean = accountBean;
    }
}

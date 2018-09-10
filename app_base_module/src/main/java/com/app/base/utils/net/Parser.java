package com.app.base.utils.net;


import com.app.base.utils.net.bean.RequestBean;
import com.app.base.utils.net.bean.ResponseBean;

/**
 * Created by zwj on 2016/12/9.
 * 用于成功回调后自定义解析数据（比如后台定义了一些与默认字段不一致的其他字段，如status、message等通用字段）
 */

public interface Parser {
    void parse(RequestBean requestBean, String result, ResponseBean responseBean);
}

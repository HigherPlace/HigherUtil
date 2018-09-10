package com.app.base.module.base.view;

import com.app.base.module.base.model.BaseTabViewState;
import com.bryan.mvp.base.view.MvpView;

/**
 * Created by bryan on 2018/2/25 0025.
 */

public interface BaseTabView extends MvpView {
    void render(BaseTabViewState viewState);
}

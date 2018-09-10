package com.app.base.module.base.model;

import java.util.List;

/**
 * Created by bryan on 2018/2/25 0025.
 */
public interface BaseTabViewState {

    final class RefreshSuccess<T> implements BaseTabViewState {
        private final List<T> result;

        public RefreshSuccess(List<T> result) {
            this.result = result;
        }

        public List<T> getResult() {
            return result;
        }
    }

    final class refreshFail implements BaseTabViewState {
        private final String tip;

        public refreshFail(String tip) {
            this.tip = tip;
        }

        public String getTip() {
            return tip;
        }
    }

    final class LoadMoreSuccess<T> implements BaseTabViewState {
        private final List<T> result;

        public LoadMoreSuccess(List<T> result) {
            this.result = result;
        }

        public List<T> getResult() {
            return result;
        }
    }

    final class LoadMoreFail implements BaseTabViewState {
        private final String tip;

        public LoadMoreFail(String tip) {
            this.tip = tip;
        }

        public String getTip() {
            return tip;
        }
    }


}
